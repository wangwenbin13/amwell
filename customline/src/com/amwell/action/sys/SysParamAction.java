package com.amwell.action.sys;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.service.ISysParamService;
import com.amwell.vo.SysParamVo;

@ParentPackage("user-finit")
@Namespace("/sysParam")
@SuppressWarnings("all")
@Scope("prototype")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SysParamAction extends BaseAction {

	@Autowired
	private ISysParamService sysParamService;

	private int currentPageIndex = 0;

	private int pageSize = 10;

	private SysParamVo sysParamVo;

	@Action(value = "getSysParamList", results = { @Result(name = "success", location = "../../view/syssetting/sysParamList.jsp") })
	public String getSysParamList() {
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0: Integer.parseInt(request.getParameter("currentPageIndex"));
		map = sysParamService.getSysParamList(search, currentPageIndex,this.pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	@Action(value = "addSysParam", results = { @Result(type = "json") })
	public String addSysParam() throws IOException {
		String json = "error";
		String validateRes =sysParamService.validateSysParam(sysParamVo); 
		if("Ok".equals(validateRes)){
			int flag = sysParamService.addSysParam(sysParamVo);
			if(flag>0){
				json ="success";
			}
		}
		else{
			json = validateRes;
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	@Action(value = "updateSysParam", results = { @Result(type = "json") })
	public String updateSysParam() throws IOException{
		String json = "error";
		String validateRes =sysParamService.validateSysParam(sysParamVo); 
		if("Ok".equals(validateRes)){
			int flag = sysParamService.updateSysParam(sysParamVo);
			if(flag>0){
				json ="success";
			}
		}
		else{
			json = validateRes;
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
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

	public SysParamVo getSysParamVo() {
		return sysParamVo;
	}

	public void setSysParamVo(SysParamVo sysParamVo) {
		this.sysParamVo = sysParamVo;
	}

}
