package com.amwell.action.bc;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.amwell.entity.BcOrderVo;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ICharteredOrderService;
import com.amwell.utils.StringUtils;

/**
 * @author wangwenbin
 *
 * 2014-8-14
 */
/**
 * 月收入ACTION
 */
@ParentPackage("user-finit")
@Namespace("/bcMonthIncomeAction")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class BcMonthIncomeAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 907140228324726479L;

	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 10;
	
	/**
	 * (按日期)记录数的下标起始数 limit 0,10中的0
	 */
	private Integer dateCurrentPageIndex = 0;
	
	/**
	 *(按日期)集合大小 
	 */
	private Integer dateListSize = 0;
	
	/**
	 * (按日期)集合
	 */
	private List<BcOrderVo> dateStatTotalEntities;
	
	/**
	 * (按日期)Page
	 */
	private Page datePage;
	
	/**
	 * (按日期)金额合计
	 */
	private BigDecimal dateTotalMoney;
	
	/**
	 * (按商家)记录数的下标起始数 limit 0,10中的0
	 */
	private Integer businessCurrentPageIndex = 0;
	
	/**
	 *(按商家)集合大小 
	 */
	private Integer businessListSize = 0;
	
	/**
	 * (按商家)集合
	 */
	private List<BcOrderVo> businessStatTotalEntities;
	
	/**
	 * (按商家)Page
	 */
	private Page businessPage;
	
	/**
	 * (按商家)金额合计
	 */
	private BigDecimal businessTotalMoney;
	
	
	/**
	 * 包车订单
	 */
	@Autowired
	private ICharteredOrderService charteredOrderService;
	/**
	 * 页面控制时间字段--控制页面能选择的最大时间
	 */
	private String maxDate;
	
	
	/**
	 * 财务统计-月财务统计列表
	 */
	@Action(value="getMonthIncomeList",results={@Result(name="success",location="../../view/bcLine/monthIncomeList.jsp")})
	public String getMonthIncomeList()throws Exception{
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		String date_s = TimeFormat.format(date);
		maxDate = date_s;
		/**
		 * 默认是当前月份
		 */
		if(null==search || (StringUtils.isEmpty(search.getField01()) )){
			search = new Search();
			search.setField01(date_s);
		}
		return SUCCESS;
	}
	
	/**
	 * 按日期月度账目统计报表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Action(value="getDateTable",results={@Result(name="success",location="../../view/bcLine/dateTable.jsp")})
	public String getDateTable()throws Exception{
		dateCurrentPageIndex = request.getParameter("dateCurrentPageIndex")==null?0:Integer.parseInt(request.getParameter("dateCurrentPageIndex"));
		map = charteredOrderService.queryDateBcOrderList(search, dateCurrentPageIndex, pageSize);
		dateStatTotalEntities = (List) map.get("dataList");//数据对象
		datePage = (Page) map.get("datePage");//分页对象
		dateListSize = dateStatTotalEntities==null?0:dateStatTotalEntities.size();
		dateTotalMoney =(BigDecimal) map.get("dateTotalMoney");
		return SUCCESS;
	}
	
	/**
	 * 按商家月度账目统计报表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value="getBusinessTable",results={@Result(name="success",location="../../view/bcLine/businessTable.jsp")})
	public String getBusinessTable()throws Exception{
		businessCurrentPageIndex = request.getParameter("businessCurrentPageIndex")==null?0:Integer.parseInt(request.getParameter("businessCurrentPageIndex"));
		map = charteredOrderService.queryBusinessBcOrderList(search, businessCurrentPageIndex, pageSize);
		businessStatTotalEntities = (List) map.get("businessList");//数据对象
		businessPage = (Page) map.get("businessPage");//分页对象
		businessListSize = businessStatTotalEntities==null?0:businessStatTotalEntities.size();
		businessTotalMoney =(BigDecimal) map.get("businessTotalMoney");
		return SUCCESS;
	}
	
	
	/**
	 * 导出 按日期月度账目统计报表Excel
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@Action(value="exportDateTable")
	public void exportDateTable() throws IOException, ParseException{
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = charteredOrderService.queryDateBcOrderList(search, 0, Integer.MAX_VALUE);
		String title = "按日期月度账目统计列表"; //导出列表头名称
	    List<BcOrderVo> lists = (List<BcOrderVo>) map.get("dataList");
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
		String[] cellNames = {"序号","日期", "收入(元)"};
		for(int i = 0;i<cellNames.length;i++){
			cell = row.createCell((short) i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < lists.size(); i++) {
			row = sheet.createRow((int) (i + 1));
			BcOrderVo stat = (BcOrderVo) lists.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((double) i + 1);
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 1);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getPayTime());
			
			cell = row.createCell((short) 2);
			cell.setCellValue(String.valueOf(stat.getTotalPrice()));
			cell.setCellStyle(style);
			
		}
		
		row = sheet.createRow((int) (lists.size() + 1));
		cell = row.createCell((short)0);
		cell.setCellValue((double) lists.size() + 1);
		cell.setCellStyle(style);
		
		BigDecimal totalMoney =(BigDecimal) map.get("dateTotalMoney");
		cell = row.createCell((short)2);
		cell.setCellValue("合计(元)："+String.valueOf(totalMoney));
		cell.setCellStyle(style);
		
//		response.setHeader("Content-Disposition", "attachment;filename="
//				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));\
		String agent = request.getHeader("User-Agent");
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
	}
	
	
	/**
	 * 导出 按商家月度账目统计报表Excel
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Action(value="exportBusinessTable")
	public void exportBusinessTable() throws IOException, ParseException{
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = charteredOrderService.queryBusinessBcOrderList(search, 0, Integer.MAX_VALUE);
		String title = "按商家月度账目统计列表"; //导出列表头名称
	    List<BcOrderVo> lists = (List) map.get("businessList");
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
		String[] cellNames = {"序号","商家", "收入(元)"};
		for(int i = 0;i<cellNames.length;i++){
			cell = row.createCell((short) i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < lists.size(); i++) {
			row = sheet.createRow((int) (i + 1));
			BcOrderVo stat = (BcOrderVo) lists.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((double) i + 1);
			cell.setCellStyle(style);
			
			cell = row.createCell((short) 1);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBrefName());
			
			cell = row.createCell((short) 2);
			cell.setCellValue(String.valueOf(stat.getTotalPrice()));
			cell.setCellStyle(style);
			
		}
		
		row = sheet.createRow((int) (lists.size() + 1));
		cell = row.createCell((short)0);
		cell.setCellValue((double) lists.size() + 1);
		cell.setCellStyle(style);
		
		BigDecimal totalMoney =(BigDecimal) map.get("businessTotalMoney");
		cell = row.createCell((short)2);
		cell.setCellValue("合计(元)："+String.valueOf(totalMoney));
		cell.setCellStyle(style);
		
//		response.setHeader("Content-Disposition", "attachment;filename="
//				.concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));\
		String agent = request.getHeader("User-Agent");
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
	}
	
	
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getDateCurrentPageIndex() {
		return dateCurrentPageIndex;
	}
	public void setDateCurrentPageIndex(Integer dateCurrentPageIndex) {
		this.dateCurrentPageIndex = dateCurrentPageIndex;
	}
	public Integer getDateListSize() {
		return dateListSize;
	}
	public void setDateListSize(Integer dateListSize) {
		this.dateListSize = dateListSize;
	}
	public List<BcOrderVo> getDateStatTotalEntities() {
		return dateStatTotalEntities;
	}
	public void setDateStatTotalEntities(List<BcOrderVo> dateStatTotalEntities) {
		this.dateStatTotalEntities = dateStatTotalEntities;
	}
	public Page getDatePage() {
		return datePage;
	}
	public void setDatePage(Page datePage) {
		this.datePage = datePage;
	}
	public BigDecimal getDateTotalMoney() {
		return dateTotalMoney;
	}
	public void setDateTotalMoney(BigDecimal dateTotalMoney) {
		this.dateTotalMoney = dateTotalMoney;
	}
	public Integer getBusinessCurrentPageIndex() {
		return businessCurrentPageIndex;
	}
	public void setBusinessCurrentPageIndex(Integer businessCurrentPageIndex) {
		this.businessCurrentPageIndex = businessCurrentPageIndex;
	}
	public Integer getBusinessListSize() {
		return businessListSize;
	}
	public void setBusinessListSize(Integer businessListSize) {
		this.businessListSize = businessListSize;
	}
	public List<BcOrderVo> getBusinessStatTotalEntities() {
		return businessStatTotalEntities;
	}
	public void setBusinessStatTotalEntities(
			List<BcOrderVo> businessStatTotalEntities) {
		this.businessStatTotalEntities = businessStatTotalEntities;
	}
	public Page getBusinessPage() {
		return businessPage;
	}
	public void setBusinessPage(Page businessPage) {
		this.businessPage = businessPage;
	}
	public BigDecimal getBusinessTotalMoney() {
		return businessTotalMoney;
	}
	public void setBusinessTotalMoney(BigDecimal businessTotalMoney) {
		this.businessTotalMoney = businessTotalMoney;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	
	
}
