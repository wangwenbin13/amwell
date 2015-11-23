package com.amwell.action.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.entity.FinancialEntity;
import com.amwell.entity.LeaseOrderModelVo;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.financial.IFinancialService;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.SysAdminVo;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 新版财务相关
 */
@SuppressWarnings("all")
@ParentPackage("user-finit")
@Namespace("/financial")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class FinancialAction extends BaseAction{

	private static final long serialVersionUID = 1L;

	@Autowired
	private IFinancialService financialService;
	
	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 50;
	
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 *集合大小 
	 */
	private Integer listSize = 0;
	
	/**
	 * 计算昨天的收入
	 */
	@Action(value="countInCome",results = { @Result( type = "json") })
	public String countInCome() throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    calendar.add(Calendar.DATE,-1);//昨天
	    Date starttime=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String yesterday = sdf.format(starttime);
		int statu = financialService.countInCome(yesterday);
		getResponse().getWriter().print("success");
		return null;
	}
	
	
	/**
	 * 供应商结算明细表
	 */
	@Action(value="querySupplierList",results={@Result(name="success",location="../../view/financial/supplierList.jsp")})
	public String querySupplierList()throws Exception{
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		SysAdminVo sysAdmin = (SysAdminVo) request.getSession().getAttribute("userInfo");
		String cityName = sysAdmin.getCityName();
		request.setAttribute("cityName", cityName);
		if(!StringUtils.isEmpty(cityName)){
			if(search==null){
				search = new Search();
			}
			search.setField06(cityName);
		}
		map = financialService.querySupplierList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		search = (Search) map.get("search");
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	/**
	 * 导出Excel
	 */
	@Action(value="exportSupplierExcel")
	public void exportSupplierExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = financialService.querySupplierList(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "供应商结算明细表"; //导出列表头名称
	    List<FinancialEntity> lists = (List) map.get("list");
	    HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		
		//设置单元格长度
		sheet.setDefaultColumnWidth((short) 20);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell ;
		String[] cellNames = {"序号","日期", "商家","城市", "线路名称","线路","车牌","票价","实际收款金额","优惠金额","乘车人数"};
		for(int i = 0;i<cellNames.length;i++){
			cell = row.createCell((short) i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < lists.size(); i++) {
			row = sheet.createRow((int) (i + 1));
			FinancialEntity stat = (FinancialEntity) lists.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((double) i + 1);
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 1);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderDate());
			
			cell = row.createCell((short) 2);
			cell.setCellValue(stat.getBrefName());
			cell.setCellStyle(style);

			cell = row.createCell((short) 3);
			cell.setCellValue(stat.getCityName());
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 4);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getLineName());
			
			cell = row.createCell((short) 5);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBstation());
			
			cell = row.createCell((short) 6);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getVehicleNumber());
			
			cell = row.createCell((short) 7);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getClassPrice());
			
			cell = row.createCell((short) 8);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getPrice());
			
			cell = row.createCell((short) 9);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTodayGifMoney());
			
			cell = row.createCell((short) 10);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getByBusNum());
			
		}
		
		String agent = request.getHeader("User-Agent");
		 try {
			if (null!=agent && agent.indexOf("Firefox") > -1) 
			 { 
			    	title = new String(title.getBytes("utf-8"),"iso-8859-1");
			 } 
			 else
			 {
			 	title = String.valueOf(URLEncoder.encode(title, "UTF-8"));
			 }
			String sFileName = title + ".xls";
			String attachment = "attachment;filename=\""+sFileName + "\"";
			getResponse().setHeader("Content-Disposition", attachment);
			getResponse().setHeader("Connection", "close");
			getResponse().setHeader("Content-Type", "application/vnd.ms-excel");
			
			wb.write(getResponse().getOutputStream());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 报表
	 */
	@Action(value="queryReportList",results={@Result(name="success",location="../../view/financial/reportList.jsp")})
	public String queryReportList()throws Exception{
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		SysAdminVo sysAdmin = (SysAdminVo) request.getSession().getAttribute("userInfo");
		String cityName = sysAdmin.getCityName();
		request.setAttribute("cityName", cityName);
		if(!StringUtils.isEmpty(cityName)){
			if(search==null){
				search = new Search();
			}
			search.setField07(cityName);
		}
		map = financialService.queryReportList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		search = (Search) map.get("search");
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	/**
	 * 导出Excel
	 */
	@Action(value="exportReportExcel")
	public void exportReportExcel(){
		
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = financialService.queryReportList(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "线路运营日报"; //导出列表头名称
	    List<FinancialEntity> lists = (List) map.get("list");
	    HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		
		//设置单元格长度
		sheet.setDefaultColumnWidth((short) 20);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell ;
		String[] cellNames = {"序号","日期", "商家","城市", "线路名称","司机姓名","司机电话","车牌","发车时间","起点","终点","座位数","出票数","退票数","改签数","实际上车数","上座率","评价得分","投诉原因前一","投诉原因前二","投诉原因前三"};
		for(int i = 0;i<cellNames.length;i++){
			cell = row.createCell((short) i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < lists.size(); i++) {
			row = sheet.createRow((int) (i + 1));
			FinancialEntity stat = (FinancialEntity) lists.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((double) i + 1);
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 1);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderDate());
			
			cell = row.createCell((short) 2);
			cell.setCellValue(stat.getBrefName());
			cell.setCellStyle(style);

			cell = row.createCell((short) 3);
			cell.setCellValue(stat.getCityName());
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 4);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getLineName());
			
			cell = row.createCell((short) 5);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getDriverName());
			
			cell = row.createCell((short) 6);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTelephone());
			
			cell = row.createCell((short) 7);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getVehicleNumber());
			
			cell = row.createCell((short) 8);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderStartTime());
			
			cell = row.createCell((short) 9);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBstation());
			
			cell = row.createCell((short) 10);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getEstation());
			
			cell = row.createCell((short) 11);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderSeats());
			
			cell = row.createCell((short) 12);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBuyNum());
			
			cell = row.createCell((short) 13);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getReturnNum());
			
			cell = row.createCell((short) 14);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getChangeNum());
			
			cell = row.createCell((short) 15);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getByBusNum());
			
			cell = row.createCell((short) 16);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getAttendance());
			
			cell = row.createCell((short) 17);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getScore());
			
			cell = row.createCell((short) 18);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getAdvOne());
			
			cell = row.createCell((short) 19);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getAdvTwo());
			
			cell = row.createCell((short) 20);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getAdvThree());
			
		}
		
		String agent = request.getHeader("User-Agent");
		 try {
			if (null!=agent && agent.indexOf("Firefox") > -1) 
			 { 
			    	title = new String(title.getBytes("utf-8"),"iso-8859-1");
			 } 
			 else
			 {
			 	title = String.valueOf(URLEncoder.encode(title, "UTF-8"));
			 }
			String sFileName = title + ".xls";
			String attachment = "attachment;filename=\""+sFileName + "\"";
			getResponse().setHeader("Content-Disposition", attachment);
			getResponse().setHeader("Connection", "close");
			getResponse().setHeader("Content-Type", "application/vnd.ms-excel");
			
			wb.write(getResponse().getOutputStream());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}
	
	
}
