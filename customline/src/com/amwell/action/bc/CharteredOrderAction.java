package com.amwell.action.bc;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.entity.BcOrderVo;
import com.amwell.entity.BcOutVo;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ICharteredOrderService;
import com.amwell.utils.StringUtils;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;

/**
 * 包车订单Action
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/charteredOrder")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class CharteredOrderAction extends BaseAction {

	private static final long serialVersionUID = 4533284202797809643L;
	
	/**
	 * 包车订单
	 */
	@Autowired
	private ICharteredOrderService charteredOrderService;
	
	/**
	 * 包车订单Vo集合
	 */
	private List<BcOrderVo> bcOrderVos;
	
	/**
	 * 金额合计
	 */
	private BigDecimal totalMoney;
	
	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 10;
	
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 *集合大小 
	 */
	private Integer listSize = 0;
	
	/**
	 * 包车订单Vo
	 */
	private BcOrderVo bcOrderVo;
	
	/**
	 * 页面控制时间字段--控制页面能选择的最大时间
	 */
	private String maxDate;
	
	/**
	 * 页面跳转Action,前辍以forward开头
	 * @return
	 */
	@Action(value = "forwardxxxPage", results = { @Result(name = "success", location = "../../view/line/addLines.jsp") })
	public String forwardxxxPage(){
		return SUCCESS;
	}
	
	/**
	 * 获取包车订单列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "charteredOrderList", results = { @Result(name = "success", location = "../../view/bcLine/bsOrderList.jsp") })
	public String charteredOrderList(){
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		map = charteredOrderService.getBcOrderList(search, currentPageIndex, pageSize);
		bcOrderVos = (List<BcOrderVo>) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = bcOrderVos==null?0:bcOrderVos.size();
		return SUCCESS;
	}
	
	/**
	 * 包车订单详情
	 * @return
	 */
	@Action(value = "charteredOrderDetail", results = { @Result(name = "success", location = "../../view/bcLine/bsOrderDetail.jsp") })
	public String charteredOrderDetail(){
		bcOrderVo = charteredOrderService.getBcOrderDetail(bcOrderVo);
		return SUCCESS;
	}
	
	/**
	 * 导出 Excel
	 */
	@SuppressWarnings("unchecked")
	@Action(value="exportExcel")
	public void exportExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = charteredOrderService.getBcOrderList(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "包车订单列表"; //导出列表头名称
	    String[] hearders = new String[] {
 	    		"下单时间", "订单号", "用户昵称","联系电话","包车类型","包车方式","出发时间","返程时间","包车商家","金额（元）","支付状态","支付方式"};//表头数组
	    String[] fields = new String[] {
 	    		"payTime", "orderNo", "nickName", "telephone", "businessType","charteredMode","startTime","returnTime","brefName","totalPrice","orderStatus","payModel"};//对象属性数组
	    List<BcOrderVo> lists = (List<BcOrderVo>) map.get("list");
	    if(null!=lists && lists.size()>0){
	    	for(BcOrderVo bcOrderVo :lists){
	    		
	    		/** 
	    		 * 包车类型
	    		 *1:活动包车 2:商务接送 3:过港接送
	    		 */
	    		String businessType = "";
	    		if("1".equals(bcOrderVo.getBusinessType())){
	    			businessType = "活动包车";
	    		}else if("2".equals(bcOrderVo.getBusinessType())){
	    			businessType = "商务接送";
	    		}else if("3".equals(bcOrderVo.getBusinessType())){
	    			businessType = "过港接送";
	    		}
	    		bcOrderVo.setBusinessType(businessType);
	    		
	    		/**
	    		 * 包车方式
	    		 * 1：单趟 2:往返 3:包天
	    		 */
	    		String charteredMode = "";
	    		if("1".equals(bcOrderVo.getCharteredMode())){
	    			charteredMode = "单趟";
	    		}else if("2".equals(bcOrderVo.getCharteredMode())){
	    			charteredMode = "往返";
	    		}else if("3".equals(bcOrderVo.getCharteredMode())){
	    			charteredMode = "包天";
	    		}
	    		bcOrderVo.setCharteredMode(charteredMode);
	    		
	    		/**
	    		 *支付状态
	    		 * 1:待调度 2：已调度(同时输入司机、车辆) 3：已完成
	    		 */
	    		String orderStatus = "";
	    		if("1".equals(bcOrderVo.getOrderStatus())){
	    			orderStatus = "待调度";
	    		}else if("2".equals(bcOrderVo.getOrderStatus())){
	    			orderStatus = "已调度";
	    		}else if("3".equals(bcOrderVo.getOrderStatus())){
	    			orderStatus = "已完成";
	    		}else if("4".equals(bcOrderVo.getOrderStatus())){
	    			orderStatus = "已退票";
	    		}
	    		bcOrderVo.setOrderStatus(orderStatus);
	    		
	    		/**
	    		 * 支付方式
	    		 * 1:支付宝 2：银联
	    		 */
	    		String payModel = "";
	    		if("1".equals(bcOrderVo.getPayModel())){
	    			payModel = "支付宝";
	    		}else if("2".equals(bcOrderVo.getPayModel())){
	    			payModel = "银联";
	    		}else if("3".equals(bcOrderVo.getPayModel())){
	    			payModel = "微信";
	    		}else if("4".equals(bcOrderVo.getPayModel())){
	    			payModel = "(APP)微信";
	    		}
	    		bcOrderVo.setPayModel(payModel);
	    	}
	    }
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
	 * 财务统计总页面
	 * @return
	 */ 
	@Action(value = "forwardFinancialPage", results = { @Result(name = "success", location = "../../view/bcLine/forwardFinancialPage.jsp") })
	public String forwardFinancialPage(){
		return SUCCESS;
	}
	
	/**
	 * 财务统计-日收入统计列表
	 * @return
	 */ 
	@Action(value = "getDayIncomeList", results = { @Result(name = "success", location = "../../view/bcLine/dayIncomeList.jsp") })
	public String getDayIncomeList(){
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search || (StringUtils.isEmpty(search.getField01()) && StringUtils.isEmpty(search.getField02()))){
			Date date = new Date();
			search = new Search();
			Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE,-1);//把日期往前退一天.整数往后推,负数往前移动
		    date=calendar.getTime();
			String date_s = TimeFormat.format(date);
			search.setField01(date_s);
			search.setField02(date_s);
			maxDate = date_s;
		}
		map = charteredOrderService.getBcOrderDayInComeList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		totalMoney =(BigDecimal) map.get("totalMoney");
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	
	/**
	 * 导出 日收入统计Excel
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Action(value="exportDayInComeListExcel")
	public void exportDayInComeListExcel() throws IOException, ParseException{
		getResponse().setContentType("application/msexcel;charset=GBK");
		/**
		 * 默认是查询昨天的数据
		 */
		/**
		 * 控制页面能选择的最大时间
		 */
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search || (StringUtils.isEmpty(search.getField01()) && StringUtils.isEmpty(search.getField02()))){
			Date date = new Date();
			search = new Search();
			Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE,-1);//把日期往前退一天.整数往后推,负数往前移动
		    date=calendar.getTime();
			String date_s = TimeFormat.format(date);
			search.setField01(date_s);
			search.setField02(date_s);
			
		}
		map = charteredOrderService.getBcOrderDayInComeList(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "日收入统计列表"; //导出列表头名称
	    List<BcOrderVo> lists = (List) map.get("list");
	    for(BcOrderVo bcOrderVo :lists){
    		/** 
    		 * 包车类型
    		 *1:活动包车 2:商务接送 3:过港接送
    		 */
    		String businessType = "";
    		if("1".equals(bcOrderVo.getBusinessType())){
    			businessType = "活动包车";
    		}else if("2".equals(bcOrderVo.getBusinessType())){
    			businessType = "商务接送";
    		}else if("3".equals(bcOrderVo.getBusinessType())){
    			businessType = "过港接送";
    		}
    		bcOrderVo.setBusinessType(businessType);
	    }
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
		String[] cellNames = {"序号","时间","订单号","用户名称","联系电话","包车类型","上车点","下车点","人数","车辆数","出发时间","返程时间","包车商家","收款金额（元）"};
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
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderNo());
			cell = row.createCell((short) 3);
			cell.setCellValue(stat.getNickName());
			cell.setCellStyle(style);
			cell = row.createCell((short) 4);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTelephone());
			cell = row.createCell((short) 5);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBusinessType());
			cell = row.createCell((short) 6);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBeginAddress());
			cell = row.createCell((short) 7);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getEndAddress());
			cell = row.createCell((short) 8);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTotalPassengers());
			cell = row.createCell((short) 9);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTotalCars());
			cell = row.createCell((short) 10);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getStartTime());
			cell = row.createCell((short) 11);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getReturnTime());
			cell = row.createCell((short) 12);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBrefName());
			cell = row.createCell((short) 13);
			cell.setCellStyle(style);
			cell.setCellValue(String.valueOf(stat.getTotalPrice()));
		}
		row = sheet.createRow((int) (lists.size() + 1));
		cell = row.createCell((short)0);
		cell.setCellValue((double) lists.size() + 1);
		cell.setCellStyle(style);
		cell = row.createCell((short)12);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		cell = row.createCell((short)13);
		totalMoney =(BigDecimal) map.get("totalMoney");
		cell.setCellValue(String.valueOf(totalMoney));
		cell.setCellStyle(style);
		String agent = request.getHeader("User-Agent");
		 if (null!=agent && agent.indexOf("Firefox") > -1) { 
		    	title = new String(title.getBytes("utf-8"),"iso-8859-1");
         }else{
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
	 * 财务统计-日支出统计列表
	 * @return
	 */ 
	@Action(value = "getDayExpendList", results = { @Result(name = "success", location = "../../view/bcLine/dayExpendList.jsp") })
	public String getDayExpendList(){
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search || (StringUtils.isEmpty(search.getField01()) && StringUtils.isEmpty(search.getField02()))){
			Date date = new Date();
			search = new Search();
			Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE,-1);//把日期往前退一天.整数往后推,负数往前移动
		    date=calendar.getTime();
			String date_s = TimeFormat.format(date);
			search.setField01(date_s);
			search.setField02(date_s);
			maxDate = date_s;
		}
		map = charteredOrderService.getBcOrderDayOutComeList(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		totalMoney =(BigDecimal) map.get("totalMoney");
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	
	/**
	 * 导出 日支出统计列表Excel
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@Action(value="exportDayExpendList")
	public void exportDayExpendList() throws IOException, ParseException{
		getResponse().setContentType("application/msexcel;charset=GBK");
		/**
		 * 默认是查询昨天的数据
		 */
		/**
		 * 控制页面能选择的最大时间
		 */
		
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search || (StringUtils.isEmpty(search.getField01()) && StringUtils.isEmpty(search.getField02()))){
			Date date = new Date();
			search = new Search();
			Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE,-1);//把日期往前退一天.整数往后推,负数往前移动
		    date=calendar.getTime();
			String date_s = TimeFormat.format(date);
			search.setField01(date_s);
			search.setField02(date_s);
		}
		map = charteredOrderService.getBcOrderDayOutComeList(search, currentPageIndex, pageSize);
		String title = "日支出统计列表"; //导出列表头名称
	    List<BcOutVo> lists = (List) map.get("list");
	    for(BcOutVo bcOutVo :lists){
    		/** 
    		 * 包车类型
    		 *1:活动包车 2:商务接送 3:过港接送
    		 */
    		String businessType = "";
    		if("1".equals(bcOutVo.getBusinessType())){
    			businessType = "活动包车";
    		}else if("2".equals(bcOutVo.getBusinessType())){
    			businessType = "商务接送";
    		}else if("3".equals(bcOutVo.getBusinessType())){
    			businessType = "过港接送";
    		}
    		bcOutVo.setBusinessType(businessType);
    		/**
    		 * 包车方式
    		 * 1：单趟 2:往返 3:包天
    		 */
    		String charteredMode = "";
    		if("1".equals(bcOutVo.getCharteredMode())){
    			charteredMode = "单趟";
    		}else if("2".equals(bcOutVo.getCharteredMode())){
    			charteredMode = "往返";
    		}else if("3".equals(bcOutVo.getCharteredMode())){
    			charteredMode = "包天";
    		}
    		bcOutVo.setCharteredMode(charteredMode);
    		/**
    		 * 支出方式
    		 * 1:退票
    		 */
    		String bcOutType = "";
    		if("1".equals(bcOutVo.getBc_out_type())){
    			bcOutType = "退票";
    		}
    		bcOutVo.setBc_out_type(bcOutType);
	    }
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
		String[] cellNames = {"序号","时间","订单号","用户名称","联系电话","包车类型","上车点","下车点","人数","车辆数","包车方式","出发时间","返程时间","包车商家","金额（元）","支出原因"};
		for(int i = 0;i<cellNames.length;i++){
			cell = row.createCell((short) i);
			cell.setCellValue(cellNames[i]);
			cell.setCellStyle(style);
		}
		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
		for (int i = 0; i < lists.size(); i++) {
			row = sheet.createRow((int) (i + 1));
			BcOutVo stat = (BcOutVo) lists.get(i);
			// 第四步，创建单元格，并设置值
			cell = row.createCell((short) 0);
			cell.setCellValue((double) i + 1);
			cell.setCellStyle(style);
			cell = row.createCell((short) 1);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOperateTime());
			cell = row.createCell((short) 2);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getOrderNo());
			cell = row.createCell((short) 3);
			cell.setCellValue(stat.getNickName());
			cell.setCellStyle(style);
			cell = row.createCell((short) 4);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTelephone());
			cell = row.createCell((short) 5);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBusinessType());
			cell = row.createCell((short) 6);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBeginAddress());
			cell = row.createCell((short) 7);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getEndAddress());
			cell = row.createCell((short) 8);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTotalPassengers());
			cell = row.createCell((short) 9);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getTotalCars());
			cell = row.createCell((short) 10);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getCharteredMode());
			cell = row.createCell((short) 11);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getStartTime());
			cell = row.createCell((short) 12);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getReturnTime());
			cell = row.createCell((short) 13);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBrefName());
			cell = row.createCell((short) 14);
			cell.setCellStyle(style);
			cell.setCellValue(String.valueOf(stat.getBc_out_money()));
			cell = row.createCell((short) 15);
			cell.setCellStyle(style);
			cell.setCellValue(stat.getBc_out_type());
		}
		row = sheet.createRow((int) (lists.size() + 1));
		cell = row.createCell((short)0);
		cell.setCellValue((double) lists.size() + 1);
		cell.setCellStyle(style);
		cell = row.createCell((short)14);
		cell.setCellValue("合计");
		cell.setCellStyle(style);
		cell = row.createCell((short)15);
		totalMoney =(BigDecimal) map.get("totalMoney");
		cell.setCellValue(String.valueOf(totalMoney));
		cell.setCellStyle(style);
		String agent = request.getHeader("User-Agent");
		 if (null!=agent && agent.indexOf("Firefox") > -1) { 
		    	title = new String(title.getBytes("utf-8"),"iso-8859-1");
         }else{
         	title = String.valueOf(URLEncoder.encode(title, "UTF-8"));
         }
		String sFileName = title + ".xls";
		String attachment = "attachment;filename=\""+sFileName + "\"";
		getResponse().setHeader("Content-Disposition", attachment);
		getResponse().setHeader("Connection", "close");
		getResponse().setHeader("Content-Type", "application/vnd.ms-excel");
		wb.write(getResponse().getOutputStream());
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
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

	public BcOrderVo getBcOrderVo() {
		return bcOrderVo;
	}

	public void setBcOrderVo(BcOrderVo bcOrderVo) {
		this.bcOrderVo = bcOrderVo;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public List<BcOrderVo> getBcOrderVos() {
		return bcOrderVos;
	}

	public void setBcOrderVos(List<BcOrderVo> bcOrderVos) {
		this.bcOrderVos = bcOrderVos;
	}
	
	
}
