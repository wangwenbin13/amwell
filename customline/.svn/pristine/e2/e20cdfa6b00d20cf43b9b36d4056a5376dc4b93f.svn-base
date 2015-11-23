package com.amwell.action.order;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
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

import com.amwell.action.BaseAction;
import com.amwell.service.IUtilsService;
import com.amwell.utils.StringUtils;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_4;

/**
 * 
 * @author wangwenbin
 *
 */
@ParentPackage("user-finit")
@Namespace("/compareOther")
public class CompareOtherAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	private File excel;
	
	private String excelFileName;
	
	@Autowired
	private IUtilsService utilsService;
	
	/**上传可接受的文件类型**/
	public static final String[] ACCEPT_FILE_TYPES = new String[]{"xls","xlsx"};
	
	/**
	 *跳转到第三方对比页面
	 * @return
	 */
	@Action(value = "toCompareView", results = { @Result(name = "success", location = "../../view/order/toCompareView.jsp") })
	public String toCompareView(){
		return SUCCESS;
	}
	
	/**
	 * 导入Excel信息
	 * @return
	 * @throws Exception
	 */
	@Action(value = "importExcel", results={@Result(type="json")})
	public String importExcel() throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		
		List<AppVo_2> appweixin = new ArrayList<AppVo_2>();  //财付通-微信APP
		List<AppVo_2> weixin = new ArrayList<AppVo_2>();  //微信支付
		List<AppVo_2> yinlian = new ArrayList<AppVo_2>();  //银联
		List<AppVo_2> zhifubao = new ArrayList<AppVo_2>();  //支付宝
		File excelFile = excel;
		String lowerPhotoFileName = StringUtils.lowerCase(excelFileName);
		if(!FilenameUtils.isExtension(lowerPhotoFileName, ACCEPT_FILE_TYPES)){
			getResponse().getWriter().print("0");
		}else{
			Integer type = Integer.parseInt(request.getParameter("type"));
			Workbook wb = null;
			Map<Integer,String> map = new HashMap<Integer,String>();
			try {
				wb = WorkbookFactory.create(new FileInputStream(excelFile));
				Sheet sheet = wb.getSheetAt(0);
				// 得到总行数
				int rowNum = sheet.getLastRowNum();
				Row row = sheet.getRow(0);
				Cell cell = null;
				String cellValue = null;
				
				// 正文内容应该从第二行开始,第一行为表头的标题
				for (int i = 1; i <= rowNum; i++) {
					row = sheet.getRow(i);
					if(null==row){
						continue;
					}
					for(int j = 0;j<row.getLastCellNum();j++){
						cell = row.getCell(j);
						if(null!=cell){
							switch (cell.getCellType()){
							  case Cell.CELL_TYPE_STRING:
								   cellValue = cell.getRichStringCellValue().getString();
								   break;
							  case Cell.CELL_TYPE_NUMERIC:
								  //此处只处理一种数字数据格式
								  cellValue = String.valueOf(cell.getNumericCellValue());
								  break;
							default:cellValue = "0";
							}
						}else{
							cellValue = "0";
						}
						map.put(j, cellValue);
					}
				   AppVo_2 part = new AppVo_2();
				   if(type==0){
					   if(!StringUtils.isEmpty(map.get(2)) && !"总退款金额".equals(map.get(2).trim()) && !"0.0".equals(map.get(2).trim()) && !StringUtils.isEmpty(map.get(6)) && isNum(map.get(6))){
						   //财付通-微信APP
						   part.setA1(map.get(2).replace("`", "").trim());
						   part.setA2(map.get(6));
						   appweixin.add(part);
					   }
				   }else if(type==1){
					   if(!StringUtils.isEmpty(map.get(2)) && !"总退款金额".equals(map.get(2).trim()) && !"0.0".equals(map.get(2).trim()) && isNum(map.get(6))){
						   //微信支付
						   part.setA1(map.get(2).replace("`", "").trim());
						   part.setA2(map.get(6));
						   weixin.add(part);
					   }
				   }else if(type==2){
					   if(!StringUtils.isEmpty(map.get(1)) && !"0".equals(map.get(1).trim()) && isNum(map.get(4))){
						   if(!"交易失败".equals(map.get(6).trim())){
							   //银联
							   part.setA1(map.get(1).replace("`", "").trim());
							   part.setA2(map.get(4));
							   yinlian.add(part);
						   }
					   }
				   }else if(type==3){
					   if(!StringUtils.isEmpty(map.get(2)) && !"0.0".equals(map.get(6).trim()) && !"0".equals(map.get(6).trim()) && isNum(map.get(6))){
						   //支付宝
						   part.setA1(map.get(2).replace("`", "").trim());
						   System.out.println(map.get(6));
						   part.setA2(map.get(6));
						   zhifubao.add(part);
					   }
				   }
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			utilsService.fromCaiWuExcelToDb(appweixin, weixin, yinlian, zhifubao);
			getResponse().getWriter().print("success");
		}
		
		return null;
	}
		
	/**判断一个字符串是不是数字**/
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	/**
	 * 导出Excel
	 */
	@Action(value="exportExcel")
	public void exportExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		String title = null; //导出列表头名称
		if(null!=search){
			if("0".equals(search.getField01())){
				//财付通 --微信APP
				title = "财付通 微信APP";
			}else if("1".equals(search.getField01())){
				//微信
				title = "微信";
			}else if("2".equals(search.getField01())){
				//银联
				title = "银联";
			}else if("3".equals(search.getField01())){
				//支付宝
				title = "支付宝";
			}
		}
		List<AppVo_4> lists = utilsService.queryNotInLocalLeases(search);
	    String[] hearders = new String[] {"订单号", "价格"};//表头数组
	    String[] fields = new String[] {"a1", "a2"};//对象属性数组
	    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
	    JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 工具方法
	 * @return
	 * @throws Exception 
	 */
	@Action(value = "excuseUtil",results={@Result(type="json")})
	public String excuseUtil() throws Exception{
		String telephones = utilsService.queryPassengerTelHasBuyLine();
		System.out.println(telephones);
		return null;
	}
	
	/****
	 * 清空stat_temp数据
	 * @return
	 */
	@Action(value = "deleteDate",results={@Result(type="json")})
	public String deleteDate() throws Exception{
		utilsService.deleteDate();
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print("success");
		return null;
	}

	public File getExcel() {
		return excel;
	}

	public void setExcel(File excel) {
		this.excel = excel;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
	
}
