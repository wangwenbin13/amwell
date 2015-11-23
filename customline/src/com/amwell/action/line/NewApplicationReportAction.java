package com.amwell.action.line;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.domain.NewApplicationReport;
import com.amwell.entity.Page;
import com.amwell.service.ISysAreaService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.ApplyReturn;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.SysArea;
import com.google.gson.GsonBuilder;

@ParentPackage("user-finit")
@Namespace("/newApplicationReport")
@Scope("prototype")
@SuppressWarnings("all")
public class NewApplicationReportAction extends BaseAction{
	
	private static final Logger logger = Logger.getLogger(NewApplicationReportAction.class);
	
	@Autowired
	private ISysAreaService iSysAreaService;
	
	/**
	 * 区域服务
	 */
	@Autowired
	private ISysAreaService sysAreaService;
	
	/**
	 * 前往定制记录页面
	 * @return
	 */
	@Action(value = "toPage", results = { @Result(name = "success", location = "../../view/line/application_report_page.jsp") })
	public String toPage(){
		return "success";
	}
	
	/**
	 * 定制线路列表
	 * 
	 * @return
	 */
	@Action(value = "search", results = { @Result(name = "success", location = "../../view/line/application_report.jsp") })
	public String search() {
		int pageSize = 15;
		String currentPage = request.getParameter("p");
		if(StringUtils.isEmpty(currentPage)||currentPage.equals("0")){
			currentPage = "1";
		}
		String startDate = request.getParameter("startDate");
		logger.info("date="+startDate);
		if(StringUtils.isEmpty(startDate)){
			startDate = MyDate.formatTime(System.currentTimeMillis()-(1000L*3600*24*7), "yyyy-MM-dd");
		}
		String isHandle = request.getParameter("isHandle");
		logger.info("isHandle="+isHandle);
		String endDate = request.getParameter("endDate");
		logger.info("endDate="+endDate);
		String cityName = request.getParameter("cityName");
		logger.info("cityName="+cityName);
		String provinceCode = request.getParameter("provinceCode");
		logger.info("provinceCode="+provinceCode);
		if(StringUtils.isEmpty(provinceCode)){
			provinceCode = "19";
		}
		if(StringUtils.isEmpty(cityName)){
			cityName = "深圳市";
		}
		List<NewApplicationReport> newApplicationReportList = NewApplicationReport.search(startDate,endDate, cityName, isHandle, Integer.valueOf(currentPage), pageSize);
		int total = NewApplicationReport.count(startDate,endDate, cityName, isHandle);
		logger.info("total="+total);
		logger.info("currentPage="+currentPage);
		logger.info("pageSize="+pageSize);
		Page page = new Page(total,(Integer.parseInt(currentPage)-1)*pageSize,pageSize);
		request.setAttribute("list",newApplicationReportList);
		request.setAttribute("isHandle",isHandle);
		request.setAttribute("cityName",cityName);
		request.setAttribute("provinceCode",provinceCode);
		request.setAttribute("startDate",startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("page",page);
		// 加载省份数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		List<SysArea> proSysAreas = sysAreaService.querySysArea(sysArea);
		request.setAttribute("proSysAreas", proSysAreas);
		return "success";
	}
	
	/**
	 * 导出 Excel
	 */
	@Action(value="exportExcel")
	public void exportExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		int pageSize = Integer.MAX_VALUE;
		String currentPage = "1";
		String startDate = request.getParameter("startDate");
		logger.info("date="+startDate);
		if(StringUtils.isEmpty(startDate)){
			startDate = MyDate.formatTime(System.currentTimeMillis()-(1000L*3600*24), "yyyy-MM-dd");
		}
		String endDate = request.getParameter("endDate");
		logger.info("endDate="+endDate);
		String cityName = request.getParameter("cityName");
		logger.info("cityName="+cityName);
		String telephone = request.getParameter("telephone");
		logger.info("telephone="+telephone);
		List<NewApplicationReport> newApplicationReportList = NewApplicationReport.search(startDate,endDate, cityName, telephone, Integer.valueOf(currentPage), pageSize);
		String title = "定制记录列表"; 
	    String[] hearders = new String[] {
 	    		"线路Id", "城市","上车点","下车点","报名人数"};//表头数组
	    String[] fields = new String[] {
 	    		"applicationId", "cityName", "ustation", "dstation", "enrollNum"};//对象属性数组
	    TableData td = ExcelUtils.createTableData(newApplicationReportList, ExcelUtils.createTableHeader(hearders),fields);
	    JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 导出该条线路某端时间的报名用户的手机号列表*/
	@Action(value="exportTelehoneList")
	public void exportTelephoneList()throws IOException{
		String applicationId = request.getParameter("applicationId");
		logger.info("applicationId="+applicationId);
		List<PassengerInfoEntity> passengerInfoList = NewApplicationReport.listTelephoneByApplication(applicationId);
		logger.info("telephoneList="+passengerInfoList);
		PrintWriter pw = getResponse().getWriter();
		if(CollectionUtils.isEmpty(passengerInfoList)){
			return;
		}
		logger.info("telephone's size="+passengerInfoList.size());
		pw.println("报名号码列表：<br/><br/>");
		for(PassengerInfoEntity passengerInfoEntity : passengerInfoList){
			pw.println(passengerInfoEntity.getTelephone()+"<br/>");
		}
	}
	
	/**结束一条线路*/
	@Action(value="handle")
	public void handle(){
		String result = null;
		try{
			String applicationId = request.getParameter("applicationId");
			NewApplicationReport.handle(applicationId);
			result = "success";
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			result = "error";
		}
		try {
			getResponse().getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
