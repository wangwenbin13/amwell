package com.amwell.action.pc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
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
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.service.IPassengerRegistService;
import com.amwell.service.IPinCheLineImportService;
import com.amwell.vo.pc.LineStationCarVo;

/**
 * 拼车线路导入Action
 * @author Administrator
 *
 */
@ParentPackage("json-default")
@Namespace("/pinCheLineImport")
@Scope("prototype")
public class PinCheLineImportAction extends BaseAction {

	private static final long serialVersionUID = 7078720943384553680L;

	private static final Logger log = Logger.getLogger(PinCheLineImportAction.class);
	
	private static final int LAST_CELL_NUM = 20;
	
	private static final String DELIMITER="@";
	
	private static final List WEEKDAY_LIST = CollectionUtils.arrayToList(new String[]{"1","2","3","4","5","6","7"});
	
	@Autowired
	private IPinCheLineImportService pinCheLineImportService;
	
	@Autowired
	private IPassengerRegistService passengerRegistService;
	
	private File excelFile;
	
	@Action(value = "forwardImportPage", results = { @Result(name = "success", location = "../../view/pcLine/pcLineImport.jsp") })
	public String forwardImportPage(){
		return SUCCESS;
	}
	
	@Action(value = "existsTelephone", results={@Result(type="json")})
    public String existsTelephone() throws IOException{
		String json ="success";
		String telephone = super.request.getParameter("telephone");
		int count = passengerRegistService.queryPhone(telephone);
		if(count>0){
			json ="error";
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
    }
    
	@Action(value = "addLine", results={@Result(type="json")})
	public String addLine() throws IOException
	{
		String json ="error";
		LineStationCarVo lineStationCar = this.createLineStationCarVo();
		if(lineStationCar!=null){
			int flag = pinCheLineImportService.addLine(lineStationCar);
			if(flag>0){
				json ="success";
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
    
	private LineStationCarVo createLineStationCarVo() {
		LineStationCarVo v = null;
		try {
			v = new LineStationCarVo();
			v.setCarColor(super.request.getParameter("carColor"));
			v.setCarModel(super.request.getParameter("carModel"));
			v.setCarNumber(super.request.getParameter("carNumber"));
			String carSeats = super.request.getParameter("carSeats");
			if(StringUtils.hasText(carSeats)){
				v.setCarSeats(Integer.parseInt(carSeats));
			}
			v.setCityName(super.request.getParameterValues("cityName"));
			v.setGoTime(super.request.getParameter("goTime"));
			v.setLati(super.request.getParameterValues("lati"));
			String lineKm = super.request.getParameter("lineKm");
			if(StringUtils.hasText(lineKm)){
				v.setLineKm(new BigDecimal(lineKm));
			}
			v.setLineSubType(Integer.parseInt(super.request.getParameter("lineSubType")));
			String lineTime = super.request.getParameter("lineTime");
			if(StringUtils.hasText(lineTime)){
				v.setLineTime(Integer.parseInt(lineTime));
			}
			v.setLineType(Integer.parseInt(super.request.getParameter("lineType")));
			v.setLoni(super.request.getParameterValues("loni"));
			v.setNickName(super.request.getParameter("nickName"));
			v.setPublisherFlag(Integer.parseInt(super.request.getParameter("publisherFlag")));
			String realPrice = super.request.getParameter("realPrice");
			if(StringUtils.hasText(realPrice)){
				v.setRealPrice(new BigDecimal(realPrice));
			}
			if(v.getLineSubType()==2){
				v.setReturnTime(super.request.getParameter("returnTime"));
			}
			v.setSex(Integer.parseInt(super.request.getParameter("sex")));
			v.setStationName(super.request.getParameterValues("stationName"));
			v.setTelephone(super.request.getParameter("telephone"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}

	@Action(value = "upload", results={@Result(type="json")})
	public String upload() throws IOException, JSONException{
		String json ="error";
		List<LineStationCarVo> excelData;
		try {
			excelData = this.readExcelFile(excelFile);
			if(false==CollectionUtils.isEmpty(excelData)){
				int flag = pinCheLineImportService.importPinCheLine(excelData);
				if(flag>0){
					json ="success";
				}
			}else{
				log.error("no excel data");
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			json = e.getLocalizedMessage();
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	private List<LineStationCarVo> readExcelFile(File excelFile)throws Exception {
		List<LineStationCarVo> result = new ArrayList<LineStationCarVo>();
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
			LineStationCarVo v = null;
			String cellValue = null;
			for(int i=startRowNum;i<=lastRowNum;i++){
				row = sheet.getRow(i);
				if(row==null){
					log.error("row is null.");
					continue;
				}
                v=new LineStationCarVo();
				for(int j=0;j<=LAST_CELL_NUM;j++){
					cell = row.getCell(j);
					cellValue = this.getCellValue(cell);
					if(StringUtils.hasText(cellValue)&&!"null".equals(cellValue)){
						this.setLineStationCarVo(v,i,j,cellValue);
					}
				}
				//验证LineStationCarVo对象数据
				if(this.validateLineStationCarVo(v)){
					result.add(v);
				}
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
		return result;
	}

	private boolean validateLineStationCarVo(LineStationCarVo v) {
		if(StringUtils.hasText(v.getTelephone())){
			return true;
		}
		return false;
	}

	private LineStationCarVo setLineStationCarVo(LineStationCarVo v,int row,int column,String cellValue) {
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
			case 3:
				//v.setProvinceName(cellValue);
				break;
			case 4:
				//v.setCityName(cellValue);
				break;
			case 5:
				if(StringUtils.hasText(cellValue)){
					try {
						v.setLineKm(new BigDecimal(cellValue));
					} catch (Exception e) {
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"里程数数据格式错误");
					}
				}
				break;
			case 6:
				if(StringUtils.hasText(cellValue)){
					try {
						v.setLineTime(Integer.parseInt(cellValue));
					} catch (Exception e) {
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"行程时间格式错误");
					}
				}
				break;
			case 7:
				if(("上下班").equals(cellValue)||("长途").equals(cellValue)){
					v.setLineType(cellValue.equals("上下班")?1:2);
				}else{
					log.error("第"+(row+1)+"行第"+(column+1)+"列"+"线路类型输入值错误");
				}
				break;
			case 8:
				if("单程".equals(cellValue)||"往返".equals(cellValue)){
					v.setLineSubType(cellValue.equals("单程")?1:2);
				}else{
					log.error("第"+(row+1)+"行第"+(column+1)+"列"+"线路子类型输入值错误");
				}
				break;
			case 9:
				if(StringUtils.hasText(cellValue)){
					try {
						v.setRealPrice(new BigDecimal(cellValue));
					} catch (Exception e) {
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"价格输入值错误");
					}
				}else{
					    log.error("第"+(row+1)+"行第"+(column+1)+"列"+"价格输入值为空");
				}
				break;
			case 10:
				if(v.getLineType()==1&&StringUtils.hasText(cellValue)){
					//上下班时间格式为 HH:mm
					try{
						new SimpleDateFormat("HH:mm").parse(cellValue);
						v.setGoTime(cellValue);
					}catch(Exception e){
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"去程时间输入值为空");
					}
				}else{
					 log.error("第"+(row+1)+"行第"+(column+1)+"列"+"去程时间输入值为空");
				}
				if(v.getLineType()==2&&StringUtils.hasText(cellValue)){
					//上途时间格式为 yyyy-MM-dd HH:mm
					try{
						new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(cellValue);
						v.setGoTime(cellValue);
					}catch(Exception e){
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"去程时间输入值为空");
					}
				}else{
					log.error("第"+(row+1)+"行第"+(column+1)+"列"+"去程时间输入值为空");
				}
				break;
			case 11:
				if(StringUtils.hasText(cellValue)){
					if(v.getLineType()==1){
						//上下班时间格式为 HH:mm
						try{
							new SimpleDateFormat("HH:mm").parse(cellValue);
							v.setReturnTime(cellValue);
						}catch(Exception e){
							log.error("第"+(row+1)+"行第"+(column+1)+"列"+"返程时间输入值错误");
						}
					}
					if(v.getLineType()==2){
						//上下班时间格式为 HH:mm
						try{
							new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(cellValue);
							v.setReturnTime(cellValue);
						}catch(Exception e){
							log.error("第"+(row+1)+"行第"+(column+1)+"列"+"返程时间输入值错误");
						}
					}
				}
				break;
			case 12:
				if(v.getLineType()==1&&StringUtils.hasText(cellValue)){
					String[] arr = cellValue.split(DELIMITER);
					boolean isOk= true;
					if(ArrayUtils.isNotEmpty(arr)&&arr.length<=7){
						for(int t=0;t<arr.length;t++){
							if(false==WEEKDAY_LIST.contains(arr[t])){
								isOk=false;
							}
						}
						if(isOk){
							v.setRepeatTime(this.changeWeekDay(cellValue));
						}else{
							log.error("第"+(row+1)+"行第"+(column+1)+"列"+"重复时间输入值错误");
						}
					}else{
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"重复时间输入值错误");
					}
				}
				break;
			case 13:
				if(StringUtils.hasText(cellValue)){
					String[] arr = cellValue.split(DELIMITER);
					if(ArrayUtils.isNotEmpty(arr)){
						v.setStationName(arr);
						//v.setStartStationName(arr[0]);
						//v.setEndStationName(arr[arr.length-1]);
					}else{
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"线路途经点数据错误");
					}
				}else{
					log.error("第"+(row+1)+"行第"+(column+1)+"列"+"线路途经点数据错误");
				}
				break;
			case 14:
				if(StringUtils.hasText(cellValue)){
					String[] arr = cellValue.split(DELIMITER);
					if(ArrayUtils.isNotEmpty(arr)){
						v.setLoni(arr);
					}else{
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"经度数据错误");
					}
				}else{
					log.error("第"+(row+1)+"行第"+(column+1)+"列"+"经度数据错误");
				}
				break;
			case 15:
				if(StringUtils.hasText(cellValue)){
					String[] arr = cellValue.split(DELIMITER);
					if(ArrayUtils.isNotEmpty(arr)){
						v.setLati(arr);
					}else{
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"纬度数据错误");
					}
				}else{
					log.error("第"+(row+1)+"行第"+(column+1)+"列"+"纬度数据错误");
				}
				break;
			case 16:
				if(StringUtils.hasText(cellValue)){
					v.setCarModel(cellValue);
				}
				break;
			case 17:
				if(StringUtils.hasText(cellValue)){
					v.setCarNumber(cellValue);
				}
				break;
			case 18:
				if(StringUtils.hasText(cellValue)){
					v.setCarColor(cellValue);
				}
				break;
			case 19:
				if(StringUtils.hasText(cellValue)){
					try {
						v.setCarSeats(Integer.parseInt(cellValue));
					} catch (Exception e) {
						log.error("第"+(row+1)+"行第"+(column+1)+"列"+"座位数错误");
					}
				}
				break;
			}
		return v;
	}

	private String changeWeekDay(String cellValue) {
		StringBuffer res = new StringBuffer();
		String[] arr = cellValue.split(DELIMITER);
		if(ArrayUtils.isNotEmpty(arr)){
			for(String s : arr){
				if("1".equals(s)){
					res.append("周一");
				}else if("2".equals(s)){
					res.append("周二");
				}else if("3".equals(s)){
					res.append("周三");
				}else if("4".equals(s)){
					res.append("周四");
				}else if("5".equals(s)){
					res.append("周五");
				}else if("6".equals(s)){
					res.append("周六");
				}else if("7".equals(s)){
					res.append("周日");
				}
				res.append("、");
			}
		}
		return res.length()>0?res.substring(0, res.length()-1):"";
	}
	
	
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
	
	
	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
}
