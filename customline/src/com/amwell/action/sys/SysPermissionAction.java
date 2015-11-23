package com.amwell.action.sys;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonZtreeUtil;
import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.service.IPermissionService;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("user-finit")
@Namespace("/permission")
@SuppressWarnings("all")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SysPermissionAction extends BaseAction{
	
	@Autowired
	private IPermissionService permissionService;
	private String ztreeJson;
	/**
	 * 每页显示的记录数
	 */
	private int  pageSize = 10;
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private String currentPage = "1";
	
	
	private List<SysPermissionVo> sysPermissions;
	
	private SysPermissionEntity sysPermissionEntity = new SysPermissionEntity();
	private SysPermissionVo sysPermissionVo;

	/**
	 * TO_列表页面
	 */
	@Action(value="toList",results={@Result(name="success",location="/WEB-INF/view/syssetting/permissionList.jsp")})
	public String toList(){
		QueryHelper query = new QueryHelper("FROM sys_power ")
		.addSelectClause("SELECT powerID,CODE,NAME,fid,sysType,url,createBy,createOn,updateBy,updateOn,iconUrl,sortFlag")
		.addOrderProperty("createOn", false)
		.addOrderProperty("sysType", false)
		.addLimitClause(Integer.parseInt(currentPage), pageSize);


		PageBean pageBean = permissionService.getPageBean(Integer.parseInt(currentPage),pageSize,query);
		
		ActionContext.getContext().getValueStack().push(pageBean);
		
		return SUCCESS;
	}
	
	/**
	 * TO_添加页面
	 */
	@Action(value="toAdd",results={@Result(name="success",location="/WEB-INF/view/syssetting/permissionAdd.jsp")})
	public String toAdd(){
		sysPermissions = permissionService.queryAllSysPermission(0);
		return SUCCESS;
	}
	/**
	 * 添加
	 */
	@Action(value="add",results = { @Result( type = "json") })
	public String add() throws Exception{
		boolean b = false;
		if(null != sysPermissionVo){
			if(sysPermissionVo.getCode() != null && 
					sysPermissionVo.getFid() != null && 
					sysPermissionVo.getName() != null &&
					sysPermissionVo.getIconUrl() != null && 
					sysPermissionVo.getSysType() != -1 && 
					sysPermissionVo.getUrl() != null ){
				
				SysPermissionVo fpermission = permissionService.queryPermissionByCode(sysPermissionVo.getFid(),sysPermissionVo.getSysType());
				if(null == fpermission){
					fpermission = new SysPermissionVo();
					fpermission.setSortFlag(1);
				}
				sysPermissionEntity.setCode(sysPermissionVo.getCode());
				sysPermissionEntity.setFid(sysPermissionVo.getFid());
				sysPermissionEntity.setName(sysPermissionVo.getName());
				sysPermissionEntity.setIconUrl(sysPermissionVo.getIconUrl());
				sysPermissionEntity.setUrl(sysPermissionVo.getUrl());
				sysPermissionEntity.setSortFlag(fpermission.getSortFlag()+1);
				
				List<SysPermissionEntity> permissionList = new ArrayList<SysPermissionEntity>();
				permissionList.add(sysPermissionEntity);
				b = permissionService.insertPermissionData(permissionList, sysPermissionVo.getSysType());
				
			}
			
		}
		
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		pw.write(String.valueOf(b));
		
		return null;
	}
	
	
	/**
	 * 验证权限名称是否存在在
	 * 
	 * @return
	 */
	@Action(value="permissionNameIsExist",results = { @Result( type = "json") })
	public String permissionNameIsExist()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		String permissionName = request.getParameter("permissionName");
		boolean isExist = true;
		if(null != permissionName){
			SysPermissionVo permisionVo = permissionService.queryPermissionByName(permissionName, 0);
			if(null == permisionVo){
				isExist = !isExist;
			}
		}
		pw.write(String.valueOf(isExist));
		return null;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getZtreeJson() {
		return ztreeJson;
	}

	public void setZtreeJson(String ztreeJson) {
		this.ztreeJson = ztreeJson;
	}

	public List<SysPermissionVo> getSysPermissions() {
		return sysPermissions;
	}

	public void setSysPermissions(List<SysPermissionVo> sysPermissions) {
		this.sysPermissions = sysPermissions;
	}

	public SysPermissionVo getSysPermissionVo() {
		return sysPermissionVo;
	}

	public void setSysPermissionVo(SysPermissionVo sysPermissionVo) {
		this.sysPermissionVo = sysPermissionVo;
	}

	
	
	
}
