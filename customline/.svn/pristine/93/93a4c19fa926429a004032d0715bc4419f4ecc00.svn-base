package com.amwell.action.line;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amwell.action.BaseAction;
import com.amwell.commons.ExcelUtil;
import com.amwell.commons.MyDate;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILineCostSetService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.LineCostSetVo;
import com.amwell.vo.SysAdminVo;

/**
 * 运营平台线路成本设置相关Action
 * 
 * @author gongxueting
 */
@ParentPackage("user-finit")
@Namespace("/lineCostSet")
@Scope("prototype")
@SuppressWarnings("all")
public class LineCostSetAction extends BaseAction {

	private static final Logger log = Logger.getLogger(LineCostSetAction.class);

	private static final long serialVersionUID = -551285202592371884L;

	@Autowired
	private ILineCostSetService lineCostSetService;

	private int currentPageIndex = 0;

	private int pageSize = 15;
	
	private List<LineCostSetVo> lineCostSetList=null;

	
	/**
	 * 获取所有线路成本设置
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getAllLineAndCost", results = { @Result(name = "success", location = "../../view/line/lineCostSetList.jsp") })
	public String getAllLineAndCost() {
		try {
			//1.点击上一页下一页分页时，存在p值，需要从session中取条件值 2.进入历史页面点返回时，session中存在myPageIndex值，需要从session中取条件值
			if(request.getParameter("p") != null || super.session.get("myPageIndex") != null){
				Object lineSearch = super.session.get("lineCostSetSearch");
				if (lineSearch instanceof Search) {
					this.search = (Search) lineSearch;
					super.session.remove("lineCostSetSearch");
			 	}
			}
			
			Object cpi = super.session.get("myPageIndex");
			if (null != cpi) {
				this.currentPageIndex = Integer.valueOf(cpi.toString());
				super.session.remove("myPageIndex");
			} else {
				currentPageIndex = request.getParameter("p") == null ? 0 : Integer.parseInt(request.getParameter("p"));
			}
			
			super.session.put("lineCostSetSearch", search);
			map = lineCostSetService.getListByCondition(search, currentPageIndex, pageSize);
			list = (List) map.get("list");// 数据对象
			page = (Page) map.get("page");// 分页对象
		} catch (Exception e) {
			log.info("加载线路成本设置列表",e);
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
			//导出类型（n：当前成本  nh：当前和历史成本）
			String exportType=request.getParameter("exportType");
			if("n".equals(exportType)){
				map = lineCostSetService.getListByCondition(search,currentPageIndex,Integer.MAX_VALUE);
			}
			else{
				map = lineCostSetService.getListAndHistory(search,currentPageIndex,Integer.MAX_VALUE);
			}
			String title = "线路成本列表"; //导出列表头名称
		    String[] hearders = new String[] {
	 	    		"所属地区", "供应商", "线路类型","线路名称","发车时间","起点/终点","成本","有效期"};//表头数组
		    
		    //address 临时作为操作人
		    String[] fields = new String[] {
	 	    		"cityName", "brefName", "lineSubType", "lineName", "orderStartTime","startStationName","orderPrice","startTime"};//对象属性数组
		    
		    List<LineCostSetVo> lists = (List) map.get("list");
		    if(null!=lists && lists.size()>0){
		    	for(LineCostSetVo di:lists){
		    		if("0".equals(di.getLineSubType())){
		    			di.setLineSubType("上下班");
		    		}
		    		else{
		    			di.setLineSubType("自由行");
		    		}
		    		di.setStartStationName(di.getStartStationName()+"--->"+di.getEndStationName());
		    		di.setOrderPrice(di.getCostSharing());
		    		if(StringUtils.isNotBlank(di.getStartTime())&&StringUtils.isNotBlank(di.getEndTime())){
		    			di.setStartTime(di.getStartTime()+"至"+di.getEndTime());
		    		}
		    	}
		    }
		    ExcelUtil.export(lists, title, hearders, fields, getResponse(), request, new LineCostSetVo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加或修改线路成本设置
	 * @return
	 * @throws Exception
	 */
	@Action(value="lineCostSetUpdate",results = { @Result( type = "json") })
	public String lineCostSetUpdate()throws Exception{
		
        try {
        	session.put("myPageIndex",request.getParameter("myPageIndex"));
        	
        	String lineCostSetId = request.getParameter("lineCostSetId");
    		String lineBaseId = request.getParameter("lineBaseId");
    		String theMode = request.getParameter("theMode");
    		String costSharing = request.getParameter("costSharing");
    		String startTime = request.getParameter("startTime");
    		String endTime = request.getParameter("endTime");
    		

    		//获取用户的登陆信息
    		SysAdminVo admin = (SysAdminVo) session.get("userInfo");
    		
    		LineCostSetVo vo=new LineCostSetVo();
    		vo.setLineBaseId(lineBaseId);
    		vo.setTheMode(theMode);
    		vo.setCostSharing(costSharing);
    		vo.setStartTime(startTime);
    		vo.setEndTime(endTime);
    		if(StringUtils.isNotBlank(lineCostSetId)){
    			vo.setId(lineCostSetId);
    			vo.setUpdateBy(admin.getUserId());
    			vo.setUpdateOn(MyDate.getMyDateLong());
    		}
    		else{
    			vo.setCreateBy(admin.getUserId());
    			vo.setCreateOn(MyDate.getMyDateLong());
    		}
    		
    		int flag=lineCostSetService.updateLineCostSet(vo);

    		HttpServletResponse response = getResponse();
    		response.setContentType("text/html;charset=GBK");
    		PrintWriter pw = response.getWriter();
    		pw.print(flag);
		} catch (Exception e) {
			log.info("添加或修改线路成本设置",e);
		}
		return null;
	}
	
	/**
	 * 获取线路成本设置历史信息
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getLineAndCostHistory", results = { @Result(name = "success", location = "../../view/line/lineCostSetHistory.jsp") })
	public String getLineAndCostHistory() {
        try {
        	session.put("myPageIndex",request.getParameter("myPageIndex"));
    		String lineBaseId = request.getParameter("lineBaseId");
    		lineCostSetList=lineCostSetService.getLineAndCostHistory(lineBaseId);
		} catch (Exception e) {
			log.info("获取线路成本设置历史列表",e);
		}
		return SUCCESS;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<LineCostSetVo> getLineCostSetList() {
		return lineCostSetList;
	}

	public void setLineCostSetList(List<LineCostSetVo> lineCostSetList) {
		this.lineCostSetList = lineCostSetList;
	}
}
