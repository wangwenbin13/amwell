package com.amwell.action.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import net.sf.oval.ConstraintViolation;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.ValidateHelper;
import com.amwell.service.IPassengerRegistService;
import com.amwell.vo.PassengerVo;

/**
 * 运营平台线路相关Action
 * @author huxiaojun
 */
@ParentPackage("json-default")
@Namespace("/passengerRegist")
@Scope("prototype")
public class PassengerRegistAction extends BaseAction {

	private static final long serialVersionUID = -2686609972236580864L;

	private static final Logger log = Logger.getLogger(PassengerRegistAction.class);
	
	private static final int LAST_CELL_NUM = 3;
	

	
	@Autowired
	private IPassengerRegistService passengerRegistService;
	
	private File excelFile;
	
	private int wrongPhoneNO;
	
	private int samePhoneNO;
	
	/**
	 * 单个手动添加乘客
	 * @return
	 */
	@Action(value = "addPassenger", results={@Result(type="json")})
	public String addPassenger() throws IOException{
		String json="error";
		String provinceCode = super.request.getParameter("provinceCode");
		String cityCode = super.request.getParameter("cityCode");
		if(StringUtils.hasText(provinceCode)&&StringUtils.hasText(cityCode)){
			String telephone=request.getParameter("contactsPhone");
			String sex=request.getParameter("sex");
			String nickName=request.getParameter("nickName");
			List<PassengerVo> passList=new ArrayList<PassengerVo>();
			PassengerVo bean=new PassengerVo();
			bean.setProvinceCode(provinceCode);
			bean.setCityCode(cityCode);
			bean.setTelephone(telephone);
			bean.setSex(Integer.parseInt(sex));
			// 此处判断手机号码格式验证
			Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
			Matcher m = p.matcher(telephone);
			if(!m.matches()){
				json="telephone";
				getResponse().setContentType("text/html;charset=UTF-8");
				getResponse().getWriter().print(json);
				return null;
			}
			int flag=passengerRegistService.queryPhone(telephone);
			if(flag<1 ){
			if(nickName==null||!StringUtils.hasText(nickName)){
				bean.setNickName(telephone);
			}else{
				bean.setNickName(nickName);
			}
			passList.add(bean);
			passengerRegistService.registPassenger(passList);
			json="success";
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	
	/**
	 * 批量导入添加乘客
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@Action(value = "upload", results={@Result(type="json")})
	public String upload() throws IOException{
		getResponse().setContentType("text/html;charset=UTF-8");
		String json ="error";
		String provinceCode = super.request.getParameter("provinceCode");
		String cityCode = super.request.getParameter("cityCode");
		if(StringUtils.hasText(provinceCode)&&StringUtils.hasText(cityCode)){
			Map<String, List<PassengerVo>> maps=new HashMap<String, List<PassengerVo>>();
			List<PassengerVo> excelData = null;
			try {
				maps= this.readExcelFile(excelFile);
				excelData=(List) maps.get("result");
				List<PassengerVo> newList = new ArrayList<PassengerVo>(excelData.size());
				for(PassengerVo pv : excelData){
					pv.setProvinceCode(provinceCode);
					pv.setCityCode(cityCode);
					newList.add(pv);
				}
				passengerRegistService.registPassenger(newList);
				json="success";
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
				json = e.getLocalizedMessage();
			}
			map.put("json",json);
			List<PassengerVo> wrongPhoneList=(List) maps.get("wrongPhoneList");
			List<PassengerVo> samePhoneList=(List) maps.get("samePhoneList");
			if(false==CollectionUtils.isEmpty(wrongPhoneList)||false==CollectionUtils.isEmpty(samePhoneList)){
				json="errorData";
				super.request.getSession().setAttribute("wrongPhoneList", wrongPhoneList);
				super.request.getSession().setAttribute("samePhoneList", samePhoneList);
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	
	/**
	 * 读取导入EXCEL文件数据和校验
	 */
	private Map<String, List<PassengerVo>> readExcelFile(File excelFile)throws Exception {
		Map<String, List<PassengerVo>> passMap=new HashMap<String, List<PassengerVo>>();
		List<PassengerVo> wrongPhoneList=new ArrayList<PassengerVo>();
		List<PassengerVo> samePhoneList=new ArrayList<PassengerVo>();
		List<PassengerVo> result = new ArrayList<PassengerVo>();
		List<String> phones=new ArrayList<String>();
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(excelFile));
			//默认读取第一个sheet
			Sheet sheet= wb.getSheetAt(0);
			Row firstRow = sheet.getRow(0);
			short firstRowLastCellNum = firstRow.getLastCellNum();
			if(firstRowLastCellNum!=LAST_CELL_NUM){
				throw new Exception("模板文件错误");
			}
			//最后的行号，0开始
			int lastRowNum = sheet.getLastRowNum();
			//默认从第二行开始读取数据，即起始行号为1
			int startRowNum = 1;
			if(lastRowNum<startRowNum){
				throw new Exception("表格内无数据");
			}
			Row row = null;
			Cell cell = null;
			PassengerVo v = null;
			String cellValue = null;
			for(int i=startRowNum;i<=lastRowNum;i++){
				row = sheet.getRow(i);
				if(row==null){
					log.error("row is null.");
					continue;
				}
                v=new PassengerVo();
				for(int j=0;j<=LAST_CELL_NUM;j++){
					cell = row.getCell(j);
					cellValue = this.getCellValue(cell);
					if(StringUtils.hasText(cellValue)&&!"null".equals(cellValue)){
						this.setPassengerVo(v,i,j,cellValue);
					}
				}
				if(null==v.getTelephone()){
					v.setTelephone("");
				}
				// 此处判断手机号码格式验证
				Pattern p = Pattern.compile("^(1[0-9])\\d{9}$");
				Matcher m = p.matcher(v.getTelephone());
				if(!m.matches()){
					wrongPhoneList.add(v);//错误手机号码集合
					continue;
				}
				String phone =v.getTelephone();
				int count=passengerRegistService.queryPhone(phone);
				
				if(count > 0 ||phones.contains(v.getTelephone())){
					samePhoneList.add(v);//相同手机号码集合
					continue;
				}
//				//验证LineStationCarVo对象数据
//				if(this.ValidatePassengerVo(v)){
				if(v.getNickName()==null){
					v.setNickName(v.getTelephone());
				}
				if(v.getSex()==null){
					v.setSex(0);
				}
					
				result.add(v);//正确手机号码集合
				phones.add(v.getTelephone());
				
			}
		} catch (InvalidFormatException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("非Excel文件");
		} catch (FileNotFoundException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("Excel文件未找到");
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("I/O异常");
		}catch(Exception e){
			throw new Exception(e);
		}
		//读取并验证数据合法性
		passMap.put("wrongPhoneList", wrongPhoneList);
		passMap.put("samePhoneList", samePhoneList);
		passMap.put("result", result);
		return passMap;
	}

	/**
	 * 校验
	 */
	private boolean ValidatePassengerVo(PassengerVo v) {

		
		List<ConstraintViolation> violations = ValidateHelper.validate(v);
		if(CollectionUtils.isEmpty(violations)){
			return false;
		}
//		if(v.getNickName()==null && "".equals(v.getNickName())){
//			v.setNickName(v.getTelephone());
//		}

		return true;
	}

	/**
	 * 读取EXCEL里的数据
	 */
	private PassengerVo setPassengerVo(PassengerVo v,int row,int column,String cellValue) {
		cellValue = cellValue.trim();
		switch(column){
		case 0:
			if(StringUtils.hasText(cellValue)){
				v.setTelephone(cellValue);
			}else{
				log.error("第"+(row+1)+"行第"+(column+1)+"列"+"手机号码输入为空");
			}
			break;
		case 1:
				v.setNickName(cellValue);
			break;
		case 2:
			v.setSex(cellValue.equals("男")?0:1);
			break;
		}
	    return v;
	}
	
	/**
	 * 获取单元格内的内容 
	 */
	private String getCellValue(Cell cell){
		DecimalFormat df = new DecimalFormat("0");// 格式化数字
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
		String cellValue =null;
		if(null!=cell){
			switch (cell.getCellType()){
			  case Cell.CELL_TYPE_STRING:
				   cellValue = cell.getRichStringCellValue().getString();
				   break;
			  case Cell.CELL_TYPE_NUMERIC:
				  //此处只处理一种数字数据格式
				  if ("@".equals(cell.getCellStyle().getDataFormatString())) {
					  cellValue = df.format(cell.getNumericCellValue());
				  } 
				  else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
					  cellValue = df.format(cell.getNumericCellValue());
				  } else {
					  cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				  }
				  break;
			}
		}
		return cellValue;
	}
	
	
	@Action(value = "registPassenger",results = { @Result( type = "json") })
	public String  registPassenger() throws IOException{
		String json = "error";
		List<PassengerVo> pList = this.createPassengerList();
		//验证数据
		List<PassengerVo> haveRegistList = passengerRegistService.checkTelephone(pList);
		if(CollectionUtils.isEmpty(haveRegistList)){
			
		}else{
			
		}
		
		int flag = passengerRegistService.registPassenger(pList);
		if(flag==pList.size()){
			json ="success";
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	private List<PassengerVo> createPassengerList() {
		List<PassengerVo> pList = new ArrayList<PassengerVo>();
		//获取参数
		
		//验证手机格式
		return pList;
	}
	
	
	
	/**
	 * 导出错误用户信息跳转
	 * @return
	 */
	@Action(value = "popfailAdd", results = { @Result(name = "success", location = "../../view/user/pop-failAdd.jsp") })
	public String popfailAdd(){
		List<PassengerVo> wronglist=(List)super.request.getSession().getAttribute("wrongPhoneList");
		List<PassengerVo> sameList=(List)super.request.getSession().getAttribute("samePhoneList");
		wrongPhoneNO=wronglist.size()+sameList.size();
		return SUCCESS;
	}
	
	
	/**
	 * 导出数据
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@Action(value="exportErrorData")
	public void exportErrorData(){
		List<PassengerVo> wronglist=(List)super.request.getSession().getAttribute("wrongPhoneList");
		List<PassengerVo> sameList=(List)super.request.getSession().getAttribute("samePhoneList");
		if(CollectionUtils.isEmpty(wronglist)&&CollectionUtils.isEmpty(sameList)){
			log.debug("无导出数据");
			return ;
		}
		try {
			String[] headers = new String[] {"手机号","昵称","性别"};
			HSSFWorkbook workBook=new HSSFWorkbook();
			HSSFSheet sheet1=workBook.createSheet();
			//创建第一个sheet
			workBook.setSheetName(0, "格式错误");  
			
			HSSFHeader header1 = sheet1.getHeader();  
		    header1.setCenter("sheet");  
		    HSSFRow headerRow = sheet1.createRow(0);  
	        HSSFCellStyle headstyle = workBook.createCellStyle();  
	        HSSFFont headfont = workBook.createFont();  
	        headfont.setColor(HSSFColor.BLACK.index);  
	        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        headstyle.setFont(headfont);  
	        for (int i = 0; i < headers.length; i++) {  
	            HSSFCell headerCell = headerRow.createCell(i);  
	            headerCell.setCellStyle(headstyle);  
	            // 设置cell的值  
	            headerCell.setCellValue(headers[i]);  
	            headerCell.setCellStyle(headstyle);  
	        } 
	        int rowIndex = 1; 
	        if(false==CollectionUtils.isEmpty(wronglist)){
	  	        for(PassengerVo v : wronglist){
	  	        	if(v==null){
	  	        		continue;
	  	        	}
	  	        	if(null==v.getSex()||v.getSex()==0){
	  	    			v.setSexStr("男");
	  	    		}else{
	  	    			v.setSexStr("女");
	  	    		}
	  	        	HSSFRow row = sheet1.createRow(rowIndex); 
	  	        	for(int i=0;i<headers.length;i++){
	  	        		 HSSFCell cell = row.createCell(i);  
	  	        		 if(i==0){
	  	        			 cell.setCellValue(v.getTelephone());
	  	        			 sheet1.setColumnWidth(i, 22*256); 
	  	        		 }
	  	        		 if(i==1){
	  	        			 cell.setCellValue(v.getNickName());
	  	        			 sheet1.setColumnWidth(i,22*256); 
	  	        		 }
	  	        		 if(i==2){
	  	        			 cell.setCellValue(v.getSexStr());
	  	        			 sheet1.setColumnWidth(i, 22*256); 
	  	        		 }
	  	        	}
	  	        	rowIndex++;
	  	        }
	        }
	        HSSFSheet sheet2=workBook.createSheet();
			//创建第一个sheet
			workBook.setSheetName(1, "重复号码");  
			
			HSSFHeader header2 = sheet2.getHeader();  
		    header2.setCenter("sheet");  
		    HSSFRow headerRow2 = sheet2.createRow(0);  
	        headfont.setColor(HSSFColor.BLACK.index);  
	        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	        headstyle.setFont(headfont);  
	        for (int i = 0; i < headers.length; i++) {  
	            HSSFCell headerCell = headerRow2.createCell(i);  
	            headerCell.setCellStyle(headstyle);  
	            // 设置cell的值  
	            headerCell.setCellValue(headers[i]);  
	            headerCell.setCellStyle(headstyle);  
	        } 
	        rowIndex = 1;  
	        if(false==CollectionUtils.isEmpty(sameList)){
	        	for(PassengerVo v : sameList){
	        		if(v==null){
	  	        		continue;
	  	        	}
	        		if(null==v.getSex()||v.getSex()==0){
	  	    			v.setSexStr("男");
	  	    		}else{
	  	    			v.setSexStr("女");
	  	    		}
		        	HSSFRow row = sheet2.createRow(rowIndex); 
		        	for(int i=0;i<headers.length;i++){
		        		 HSSFCell cell = row.createCell(i);  
		        		 if(i==0){
		        			 cell.setCellValue(v.getTelephone());
		        			 sheet2.setColumnWidth(i, 22*256); 
		        		 }
		        		 if(i==1){
		        			 cell.setCellValue(v.getNickName());
		        			 sheet2.setColumnWidth(i, 22*256); 
		        		 }
		        		 if(i==2){
		        			 cell.setCellValue(v.getSexStr());
		        			 sheet2.setColumnWidth(i, 22*256); 
		        		 }
		        	}
		        	rowIndex++;
		        }
	        }
	        super.request.getSession().removeAttribute("wrongPhoneList");
	        super.request.getSession().removeAttribute("samePhoneList");
	        HttpServletResponse response = super.getResponse();
	        // 清空response  
	        //response.reset();  
	        response.setContentType("application/octet-stream"); 
	        // 设置response的Header  
	        response.addHeader("Content-Disposition", "attachment;filename="+new String("乘客批量导入错误列表.xls".getBytes("utf-8"),"ISO-8859-1")); 
	        OutputStream out = response.getOutputStream();    
	        workBook.write(out);
	        out.flush();  
            out.close();     
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导入Excel模板
	 * @throws IOException
	 */
	@Action(value="downloadTemplate")
	public void downloadTemplate() throws IOException{
		String rootPath = super.request.getRealPath("/");
		String fileName="DownloadPassengerRegist.xlsx";
		String path =rootPath.replace("\\", "/")+"template"+"/"+fileName;
		File file = new File(path);
		// 以流的形式下载文件。  
        InputStream fis = new BufferedInputStream(new FileInputStream(path));  
        byte[] buffer = new byte[fis.available()];  
        fis.read(buffer);  
        fis.close();  
        HttpServletResponse response = super.getResponse();
        // 清空response  
        response.reset();  
        // 设置response的Header  
        response.addHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"ISO-8859-1")); 
        response.addHeader("Content-Length", "" + file.length());  
        OutputStream out = new BufferedOutputStream(response.getOutputStream());    
        response.setContentType("application/octet-stream");  
        out.write(buffer);  
        out.flush();  
        out.close(); 
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}


	public int getWrongPhoneNO() {
		return wrongPhoneNO;
	}


	public void setWrongPhoneNO(int wrongPhoneNO) {
		this.wrongPhoneNO = wrongPhoneNO;
	}


	public int getSamePhoneNO() {
		return samePhoneNO;
	}


	public void setSamePhoneNO(int samePhoneNO) {
		this.samePhoneNO = samePhoneNO;
	}
	
}
