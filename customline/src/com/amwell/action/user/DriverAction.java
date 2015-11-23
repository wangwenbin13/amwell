package com.amwell.action.user;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.commons.StringUtil;
import com.amwell.entity.LeaseOrderModelVo;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IDriverInfoService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.DriverBroadcastVo;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.DriverWithdrawAskforVo;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.SysAdminVo;

/**
 * 运营平台-用户管理-司机管理
 * @author 龚雪婷
 *
 */
@ParentPackage("user-finit")
@Namespace("/operationDriver")
//@SuppressWarnings("serial")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class DriverAction extends BaseAction {
	//司机service
	@Autowired
	private IDriverInfoService service;
	
	//司机对象
	private DriverInfoEntity driverInfoEntity;
	
	//提现申请对象
	private DriverWithdrawAskforVo driverWithdrawAskforVo;
	
	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 5;

	/**
	 * 司机管理
	 */
	@Action(value="driverManager",results={@Result(name="success",location="../../view/user/driverManager.jsp")})
	public String driverManager()throws Exception{
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		Object theTab = super.session.get("theTab");
		if (theTab instanceof String) {
			super.request.setAttribute("theTab", theTab);
			super.session.remove("theTab");
		}
		return SUCCESS;
	}
	
	/**
	 * 加载司机列表
	 */
	@Action(value = "driverList", results = { @Result(name = "success", location = "../../view/user/driverList.jsp") })
	public String list(){
		try{
			if(session.get("toDetailCurrentPageIndex")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("driverList_Cond");
			}
			/*当前页*/
			Object cpi = super.session.get("toDetailCurrentPageIndex");
			if (null != cpi) {
				if (cpi instanceof Integer) {
					this.currentPageIndex = (Integer) cpi;
					super.session.remove("toDetailCurrentPageIndex");
				}
			} 
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("driverList_Cond"):search);
			session.put("driverList_Cond", search);
			if(null==search||StringUtils.isBlank(search.getField05())){
				/*每页显示条数*/
				pageSize = 10;
			}
			else{
				/*每页显示条数*/
				pageSize = Integer.parseInt(search.getField05());
			}
			
			//集合对象
			map = service.listByPage(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象

			request.setAttribute("theTab","1");//司机列表
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 提现申请
	 */
	@Action(value="driverApply",results={@Result(name="success",location="../../view/user/driverApply.jsp")})
	public String driverApply()throws Exception{
		
		try{
			if(session.get("toDetailCurrentPageIndex")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("driverList_Cond");
			}
			/*当前页*/
			Object cpi = super.session.get("toDetailCurrentPageIndex");
			if (null != cpi) {
				if (cpi instanceof Integer) {
					this.currentPageIndex = (Integer) cpi;
					super.session.remove("toDetailCurrentPageIndex");
				}
			} 
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("driverList_Cond"):search);
			session.put("driverList_Cond", search);
			if(null==search||StringUtils.isBlank(search.getField06())){
				/*每页显示条数*/
				pageSize = 10;
			}
			else{
				/*每页显示条数*/
				pageSize = Integer.parseInt(search.getField06());
			}
			
			//集合对象
			map = service.driverApplyListByPage(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象

			request.setAttribute("theTab","2");//提现申请
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 发放奖励
	 */
	@Action(value="driverPayment",results={@Result(name="success",location="../../view/user/driverPayment.jsp")})
	public String driverPayment()throws Exception{
		
		try{
			if(session.get("toDetailCurrentPageIndex")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("driverList_Cond");
			}
			/*当前页*/
			Object cpi = super.session.get("toDetailCurrentPageIndex");
			if (null != cpi) {
				if (cpi instanceof Integer) {
					this.currentPageIndex = (Integer) cpi;
					super.session.remove("toDetailCurrentPageIndex");
				}
			} 
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("driverList_Cond"):search);
			session.put("driverList_Cond", search);
			if(null==search||StringUtils.isBlank(search.getField04())){
				/*每页显示条数*/
				pageSize = 10;
			}
			else{
				/*每页显示条数*/
				pageSize = Integer.parseInt(search.getField04());
			}
			
			//集合对象
			map = service.driverPaymentListByPage(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象

			request.setAttribute("theTab","3");//发放奖励
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查看司机详情
	 * @return
	 */
	@Action(value = "driverDetail", results = { @Result(name = "success", location = "../../view/user/driverDetail.jsp") })
	public String detail(){
		try {
			String driverId=request.getParameter("driverId");
			driverInfoEntity=service.getDriverById(driverId);
			if(null!=driverInfoEntity&&StringUtils.isNotBlank(driverInfoEntity.getIdNumber())){
				String str=driverInfoEntity.getIdNumber().substring(6,14);
				String birthday=str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6);
				long theLong=new Date().getTime()-MyDate.strToDate(birthday).getTime();
				float value = theLong/1000/60/60/24/365;
				Integer d=Math.round(value);
				driverInfoEntity.setAge(d.toString());
			}
			super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
			// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
			String theTab = super.request.getParameter("theTab");
			if (StringUtils.isNotBlank(theTab)) {
				super.getSession().put("theTab", theTab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 体现申请详情
	 */
	@Action(value="driverApplyDetail",results={@Result(name="success",location="../../view/user/driverApplyDetail.jsp")})
	public String driverApplyDetail()throws Exception{
		try {
			String askforId=request.getParameter("askforId");
			driverWithdrawAskforVo=service.getDriverApplyById(askforId);
			super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
			
			// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
			String theTab = super.request.getParameter("theTab");
			if (StringUtils.isNotBlank(theTab)) {
				super.getSession().put("theTab", theTab);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	/**
	 * 发放奖励-发放
	 */
	@Action(value="driverPaymentPlay",results={@Result(name="success",location="../../view/user/driverPaymentPlay.jsp")})
	public String driverPaymentPlay()throws Exception{
		String driverPaymentId = request.getParameter("driverPaymentId");
		
		super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		String theTab = super.request.getParameter("theTab");
		if (StringUtils.isNotBlank(theTab)) {
			super.getSession().put("theTab", theTab);
		}
		return SUCCESS;
	}
	
	/**
	 * 导出Excel
	 */
	@Action(value="exportExcel")
	public void exportExcel(){
		try {
			getResponse().setContentType("application/msexcel;charset=GBK");
			map = service.listByPage(search,currentPageIndex,Integer.MAX_VALUE);
			String title = "司机列表"; //导出列表头名称
		    String[] hearders = new String[] {
	 	    		"注册时间(年月日时分秒)", "所属商家", "司机姓名","性别","登录手机号码","奖励账户","帐号状态"};//表头数组
		    //address 临时作为操作人
		    String[] fields = new String[] {
	 	    		"createOn", "businessName", "driverName", "sex", "telephone","totalAmount","accountStatus"};//对象属性数组
		    
		    List<DriverInfoEntity> lists = (List) map.get("list");
		    if(null!=lists && lists.size()>0){
		    	for(DriverInfoEntity di:lists){
		    		if("1".equals(di.getAccountStatus())){
		    			di.setAccountStatus("禁用");
		    		}
		    		if("0".equals(di.getAccountStatus())){
		    			di.setAccountStatus("启用");
		    		}
		    		if("0".equals(di.getSex())){
		    			di.setSex("男");
		    		}
		    		else{
		    			di.setSex("女");
		    		}
		    	}
		    }
		    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
		    JsGridReportBase report;
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出提现申请Excel
	 */
	@Action(value="exportDriverApplyExcel")
	public void exportDriverApplyExcel(){
		try {
			getResponse().setContentType("application/msexcel;charset=GBK");
			map = service.driverApplyListByPage(search,currentPageIndex,Integer.MAX_VALUE);
			String title = "提现申请列表"; //导出列表头名称
		    String[] hearders = new String[] {
	 	    		"注册时间(年月日时分)", "申请司机", "登录手机号码","所属商家","提现金额","提现方式","帐号","姓名/昵称","支付状态"};//表头数组
		    
		    //address 临时作为操作人
		    String[] fields = new String[] {
	 	    		"askforTime", "driverName", "telephone", "businessName", "amount","withdrawType","withdrawAccount","driverName","handleType"};//对象属性数组
		    
		    List<DriverWithdrawAskforVo> lists = (List) map.get("list");
		    if(null!=lists && lists.size()>0){
		    	for(DriverWithdrawAskforVo dwa:lists){
		    		if("1".equals(dwa.getWithdrawType())){
		    			dwa.setWithdrawType("支付宝");
		    		}
		    		if("2".equals(dwa.getWithdrawType())){
		    			dwa.setWithdrawType("微信");
		    		}
		    		if("3".equals(dwa.getWithdrawType())){
		    			dwa.setWithdrawType("银联");
		    		}
		    		if("0".equals(dwa.getHandleType())){
		    			dwa.setHandleType("未支付");
		    		}
		    		else{
		    			dwa.setHandleType("已支付");
		    		}
		    	}
		    }
		    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
		    JsGridReportBase report;
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出司机报站明细Excel
	 * http://192.168.9.85:8080/customline/operationDriver/exportDriverBroadcastExcel.action
	 */
	@Action(value="exportDriverBroadcastExcel")
	public void exportDriverBroadcastExcel(){
		try {
			/*getResponse().setContentType("application/msexcel;charset=GBK");
			search=new Search();
			search.setField01("2015-07");
			map = service.driverBroadcastListByPage(search,currentPageIndex,Integer.MAX_VALUE);
			String title = "7月司机奖励明细统计"; //导出列表头名称
		    String[] hearders = new String[] {
	 	    		"日期", "司机姓名", "司机电话","线路名称","起点","终点","起点报站","终点报站","线路发车时间","实际发车时间","上座率"};//表头数组
		    //address 临时作为操作人
		    String[] fields = new String[] {
	 	    		"orderDate", "driverName", "telephone", "lineName", "startStation","endStation","startStationB","endStationB","orderStartTime","actualStartTime","upperLimb"};//对象属性数组
		    
		    List<DriverBroadcastVo> lists = (List) map.get("list");
		    System.out.println("list size==="+lists.size());
		    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
		    JsGridReportBase report;
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);*/
			
			
			getResponse().setContentType("application/msexcel;charset=GBK");
			search=new Search();
			search.setField01("2015-08");
			map = service.driverBroadcastListByPage(search,currentPageIndex,Integer.MAX_VALUE);
			String title = "8月司机奖励明细统计"; //导出列表头名称
		    List<DriverBroadcastVo> lists = (List) map.get("list");
//		    lists = setListValue(lists);
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
			String[] cellNames = {"日期","商户简称","司机姓名", "司机电话","线路名称","起点","终点","起点报站","终点报站","线路发车时间","实际发车时间","上座率"};
//			,"座位数","买票数"
			for(int i = 0;i<cellNames.length;i++){
				cell = row.createCell((short) i);
				cell.setCellValue(cellNames[i]);
				cell.setCellStyle(style);
			}

			// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
			for (int i = 0; i < lists.size(); i++) {
				row = sheet.createRow((int) (i + 1));
				DriverBroadcastVo stat = (DriverBroadcastVo) lists.get(i);
				// 第四步，创建单元格，并设置值
				cell = row.createCell((short) 0);
				cell.setCellValue(stat.getOrderDate());
				cell.setCellStyle(style);
				
				cell = row.createCell((short) 1);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getBrefName());
				
				cell = row.createCell((short) 2);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getDriverName());
				
				cell = row.createCell((short) 3);
				cell.setCellValue(stat.getTelephone());
				cell.setCellStyle(style);
				
				cell = row.createCell((short) 4);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getLineName());
				
				cell = row.createCell((short) 5);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getStartStation());
				
				cell = row.createCell((short) 6);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getEndStation());
				
				cell = row.createCell((short) 7);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getStartStationB());
				
				cell = row.createCell((short) 8);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getEndStationB());
				
				cell = row.createCell((short) 9);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getOrderStartTime());
				
				cell = row.createCell((short) 10);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getActualStartTime());
				
				cell = row.createCell((short) 11);
				cell.setCellStyle(style);
				cell.setCellValue(stat.getUpperLimb());
				
//				cell = row.createCell((short) 11);
//				cell.setCellStyle(style);
//				cell.setCellValue(stat.getOrderSeats());
//				
//				cell = row.createCell((short) 12);
//				cell.setCellStyle(style);
//				cell.setCellValue(stat.getTotalBuyPeople());
			}
			
			row = sheet.createRow((int) (lists.size() + 1));
			cell = row.createCell((short)0);
			cell.setCellValue((double) lists.size() + 1);
			cell.setCellStyle(style);

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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 后台处理提现申请信息
	 * @return
	 */
	@Action(value = "updateDriverWithdrawAskfor", results = { @Result(type="json") })
	public String updateDriverWithdrawAskfor(){
		try {
			String returnStr=null;
			
			String askforId=request.getParameter("askforId");
			
			if(StringUtils.isBlank(askforId)||StringUtils.isBlank(askforId)){
				returnStr="信息丢失，请刷新列表重新操作！";
			}
			else{
				SysAdminVo sysAdmin = (SysAdminVo) this.request.getSession().getAttribute("userInfo");
				int returnNum=service.updateDriverWithdrawAskfor(sysAdmin.getUserName(), askforId);
				if(returnNum>0){
					returnStr="ok";
				}
				else{
					returnStr="操作失败，请刷新列表重新操作！";
				}
			}
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(returnStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DriverInfoEntity getDriverInfoEntity() {
		return driverInfoEntity;
	}

	public void setDriverInfoEntity(DriverInfoEntity driverInfoEntity) {
		this.driverInfoEntity = driverInfoEntity;
	}

	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public DriverWithdrawAskforVo getDriverWithdrawAskforVo() {
		return driverWithdrawAskforVo;
	}

	public void setDriverWithdrawAskforVo(
			DriverWithdrawAskforVo driverWithdrawAskforVo) {
		this.driverWithdrawAskforVo = driverWithdrawAskforVo;
	}
}
