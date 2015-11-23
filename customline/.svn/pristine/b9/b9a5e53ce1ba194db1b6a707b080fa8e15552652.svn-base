package com.amwell.action.sys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonZtreeUtil;
import com.amwell.commons.KickOutLoginUser;
import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.entity.Search;
import com.amwell.service.IPermissionService;
import com.amwell.service.IRolePermissionService;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysPermissionVo;
import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("user-finit")
@Namespace("/rolePermissionManager")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SystemRolePermissionManagerAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8220417782122301516L;

	//角色权限Service
	@Autowired
	private IRolePermissionService rolePermissionService;
	
	//注入权限Service
	@Autowired
	private IPermissionService permissionService;
	
	private SysRoleEntity sysRoleEntity = new SysRoleEntity();
	
	private SysRoleVo sysRoleVo;
	
	private String ztreeJson;
	
	//存放查询参数条件
	private Search search = new Search();
	/**
	 * 每页显示的记录数
	 */
	private int  pageSize = 10;
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private String currentPage = "1";
	
	/**
	 * TO_角色列表页面
	 */
	@Action(value="toList",results={@Result(name="success",location="/WEB-INF/view/syssetting/roleManagerList.jsp")})
	public String toAdministratorManagerList()throws Exception{
		if(search!=null){
			QueryHelper query = new QueryHelper("FROM sys_role r,sys_admin a")
					.addSelectClause("SELECT r.roleId,r.roleName,r.remark,r.sysType,a.userName,r.createOn")
					.addCondition(" r.createBy =a.userId",new Object[]{} )
					.addCondition("r.sysType = ?", 0)
					.addCondition(search != null && search.getField01()!= null && !"".equals(search.getField01()),"r.createOn >= ?", search.getField01())
					.addCondition(search != null && search.getField02()!= null && !"".equals(search.getField02()),"r.createOn <= ?", MyDate.getNextDay(search.getField02(),"1"))
					.addCondition(search != null && search.getField03() != null && !"".equals(search.getField03()),"r.roleName like ?","%"+search.getField03()+"%")
					.addCondition(search != null && search.getField04() != null && !"".equals(search.getField04()),"a.userName like ?","%"+search.getField04()+"%")
					.addOrderProperty("r.createOn", false)
					.addLimitClause(Integer.parseInt(currentPage), pageSize);
				PageBean pageBean = rolePermissionService.getPageBean(Integer.parseInt(currentPage),pageSize,query);
				ActionContext.getContext().getValueStack().push(pageBean);
		}
		return SUCCESS;
	}
	
	
	/**
	 * TO_添加角色页面
	 */
	@Action(value="toAdd",results={@Result(name="success",location="/WEB-INF/view/syssetting/roleManagerToAdd.jsp")})
	public String toAdministratorManagerAdd()throws Exception{
		
		//获得系统的所有权限
		List<SysPermissionVo> sysPermissionAll = permissionService.queryAllSysPermission(0);
		
		//获取权限树
		ztreeJson = JsonZtreeUtil.getJsonZtree(null,false,sysPermissionAll);
		return SUCCESS;
	}
	
	/**
	 * 添加/修改_角色及对应的权限
	 */
	@Action(value="rolePermissionAddOrUpdate",results = { @Result( type = "json") })
	public String rolePermissionAddOrUpdate()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		//获得选中权限的code值
		String permissionCodes = request.getParameter("permissionCodes");
		if(permissionCodes.indexOf(",")<0){
			pw.print("noPermission");
			return null;
		}
		//
		String[] permissionArray = permissionCodes.split(",");
		//
		List<SysPermissionVo> permissionsAll = permissionService.queryAllSysPermission(0);
		//存放权限ID的集合
		List<String> permissinIds = new ArrayList<String>();
		
		for(int i=0;i<permissionsAll.size();i++){
			SysPermissionVo all = permissionsAll.get(i);
			for(int j=0;j<permissionArray.length;j++){
				if(permissionArray[j].equals(all.getCode())){
					permissinIds.add(all.getPowerId());
				}
			}
		}
		
		boolean flag = false;
		
		if(null != sysRoleVo.getRoleId() && !"".equals(sysRoleVo.getRoleId())){
			//修改
			sysRoleEntity.setUpdateBy(admin.getUserId());// >> 修改人
			sysRoleEntity.setRoleId(sysRoleVo.getRoleId());// >> 修改的角色ID
			sysRoleEntity.setRoleName(sysRoleVo.getRoleName());//修改的角色名称
			sysRoleEntity.setRemark(sysRoleVo.getRemark());// >> 修改的备注
			
			flag  = rolePermissionService.updateRolePermissionData(sysRoleEntity,permissinIds,0);
			
			//修改角色将拥有该角色的在线登陆人踢下线
			//KickOutLoginUser.kickOutByRoleId(httpSession.getServletContext(), sysRoleVo.getRoleId());
		}else{
			//增加
			sysRoleEntity.setCreateBy(admin.getUserId());// >> 创建人
			sysRoleEntity.setRoleName(sysRoleVo.getRoleName());//修改的角色名称
			sysRoleEntity.setRemark(sysRoleVo.getRemark());// >> 修改的备注
			flag  = rolePermissionService.insertRolePermissionData(sysRoleEntity,permissinIds,0);
		}
		
		if(flag){
			pw.print("yes");
		}else{
			pw.print("no");
		}
		return null;
	}
	
	/**
	 * TO_修改角色页面
	 */
	@Action(value="toUpdate",results={@Result(name="success",location="/WEB-INF/view/syssetting/roleManagerToUpdate.jsp")})
	public String toAdministratorManagerUpdate()throws Exception{
		
		//角色ID必须传过过来
		if(null != sysRoleEntity.getRoleId() && !"".equals(sysRoleEntity.getRoleId())){
			
			
			sysRoleVo = rolePermissionService.querySysRoleInfoByRoleId(sysRoleEntity.getRoleId(),0);
			//获得系统的所有权限
			List<SysPermissionVo> sysPermissionAll = permissionService.queryAllSysPermission(0);
			
			//获取权限树
			ztreeJson = JsonZtreeUtil.getJsonZtree(sysRoleVo.getSysPermissionList(),false,sysPermissionAll);
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * TO_查看角色页面
	 */
	@Action(value="toShow",results={@Result(name="success",location="/WEB-INF/view/syssetting/roleManagerToDetail.jsp")})
	public String toAdministratorManagerShow()throws Exception{
		if(null != sysRoleEntity.getRoleId() && !"".equals(sysRoleEntity.getRoleId())){
			
			sysRoleVo = rolePermissionService.querySysRoleInfoByRoleId(sysRoleEntity.getRoleId(),0);
			//获得系统的所有权限
			List<SysPermissionVo> sysPermissionAll = permissionService.queryAllSysPermission(0);
				
			//获取权限树
			ztreeJson = JsonZtreeUtil.getJsonZtree(sysRoleVo.getSysPermissionList(),true,sysPermissionAll);
		}
		return SUCCESS;
	}
	
	/**
	 * 验证角色名称是否重复
	 * @return yes存在,no不存在
	 */
	@Action(value="roleNameIsRepetition",results = { @Result( type = "json") })
	public String roleNameIsRepetition()throws IOException{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		String roleName = request.getParameter("roleName").trim();
		if(StringUtils.isEmpty(roleName)){
			pw.print("isNull");
			return null;
		}
		boolean flag = rolePermissionService.roleNameIsRepetition(roleName, 0);
		
		if(flag){
			pw.print("yes");
		}else{
			pw.print("no");
		}
		return null;
	}
	
	/**
	 * 删除角色
	 * @return
	 * @throws Exception
	 */
	@Action(value="deleteRole",results = { @Result( type = "json") })
	public String deleteRole()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		String ids = request.getParameter("ids");
		
		//获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		if(null != ids){
	
			String flag = rolePermissionService.deleteRoleAndPermissionByRoleIds(admin.getUserId(),ids,0);
			pw.print(flag);
			return null;
		}
		pw.print("noID");
		return null;
	}
	//
	


	public String getZtreeJson() {
		return ztreeJson;
	}


	public void setZtreeJson(String ztreeJson) {
		this.ztreeJson = ztreeJson;
	}


	public Search getSearch() {
		return search;
	}


	public void setSearch(Search search) {
		this.search = search;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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


	public SysRoleEntity getSysRoleEntity() {
		return sysRoleEntity;
	}


	public void setSysRoleEntity(SysRoleEntity sysRoleEntity) {
		this.sysRoleEntity = sysRoleEntity;
	}


	public SysRoleVo getSysRoleVo() {
		return sysRoleVo;
	}


	public void setSysRoleVo(SysRoleVo sysRoleVo) {
		this.sysRoleVo = sysRoleVo;
	}

	
	
	
	
	
}
