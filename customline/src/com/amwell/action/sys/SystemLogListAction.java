package com.amwell.action.sys;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.dao.ISysLogDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ISysLogService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.SysLogVo;


/**
 * 操作日志
 * @author chenlongfeng
 *
 */
@ParentPackage("user-finit")
@Namespace("/logList")
@SuppressWarnings("all")
public class SystemLogListAction extends BaseAction{
	
	@Autowired
	ISysLogService iSysLogService; //操作日志
	
	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPage = 0;
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 15;
	
	/**
	 * 分页数据量
	 */
	private Integer listSize=0;
	


	/**
	 * TO_操作日志页面
	 */
	@Action(value="toLogList",results={@Result(name="success",location="/WEB-INF/view/syssetting/logList.jsp")})
	public String toLogList()throws Exception{
		
		currentPage = request.getParameter("currentPage")==null?0:Integer.parseInt(request.getParameter("currentPage"));
	
		if(search==null){
			Search search=new Search();
		}
		map=iSysLogService.getSysLogList(search, currentPage, pageSize);
		list=(List) map.get("list");
		page=(Page) map.get("page");
		if(list!=null && !list.isEmpty()){
			listSize=list.size();
		}else{
			listSize=0;
		}
		return SUCCESS;
	}
	
	/**
	 * 导出Excel
	 */
	@Action(value="exportExcel")
	public void exportExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = iSysLogService.getSysLogList(search, currentPage, Integer.MAX_VALUE);
		String title = "操作日志列表"; //导出列表头名称
	    String[] hearders = new String[] {
 	    		"登录名", "用户姓名", "用户IP地址","用户动作","操作结果","操作时间"};//表头数组
	    String[] fields = new String[] {
 	    		"loginName", "userName", "userIp", "action", "operateResult","operateTime"};//对象属性数组
	    List<SysLogVo> lists = (List) map.get("list");
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
	
	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
