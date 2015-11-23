package com.amwell.action.sys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import com.amwell.commons.KickOutLoginUser;
import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.entity.Search;
import com.amwell.service.IRolePermissionService;
import com.amwell.service.ISysAdminService;
import com.amwell.service.ISysAreaService;
import com.amwell.service.ISysLogService;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysAdminEntity;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.SysRoleVo;
import com.opensymphony.xwork2.ActionContext;
/**
 * 管理员管理
 * @author 胡双
 *
 */
@ParentPackage("user-finit")
@Namespace("/adminManager")
@SuppressWarnings("all")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SystemAdministratorInfoManagerAction extends BaseAction {
	
	/**
	 * 区域服务
	 */
	@Autowired
	private ISysAreaService sysAreaService;
	
	// >>注入管理员Service
	@Autowired
	private ISysAdminService sysAdminService;
	// >>注入角色权限Service
	@Autowired
	private IRolePermissionService rolePermissionService;
	@Autowired
	ISysLogService iSysLogService; //操作日志
	
	private List<SysArea> proSysAreas;
	
	private SysAdminVo sysAdminVo ;
	private SysAdminEntity sysAdminEntity = new SysAdminEntity();
	
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
	 * TO_管理员列表页面
	 *
	 */
	@Action(value="toList",results={@Result(name="success",location="/WEB-INF/view/syssetting/adminManagerList.jsp")})
	public String toAdministratorManagerList()throws Exception{
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof String) {
				this.currentPage = (String) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPage = request.getParameter("currentPage") == null ? "1": request.getParameter("currentPage");
		}
		
		/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
		search = (Search) (session.get("search") == null?search:session.get("search"));
		if(null==search){
			search=new Search();
		}
		
		QueryHelper query = new QueryHelper(" FROM (SELECT a.deleteFlag,a.userId,a.userName,a.loginName,a.telephone,a.status,a.sysType,a.createOn,(SELECT username FROM sys_admin WHERE userId = a.createBy) createby,r.roleName" 
						+" FROM sys_admin a,sys_userrole ur,sys_role r"
						+" WHERE a.userId = ur.userId AND ur.roleId = r.roleId ) t")
				.addSelectClause("SELECT t.userId,t.userName,t.loginName,t.telephone,t.status,t.sysType,t.createOn,t.createBy,t.roleName,t.deleteFlag")
				.addCondition(search != null && search.getField01()!= null && !"".equals(search.getField01()),"t.createOn >= ?", search.getField01())
				.addCondition(search != null && search.getField02()!= null && !"".equals(search.getField02()),"t.createOn <= ?", MyDate.getNextDay(search.getField02(),"1"))
				.addCondition(search != null && search.getField03() != null && !"".equals(search.getField03()),"(t.userName like ? Or t.loginName like ?)",new Object[]{"%"+search.getField03()+"%","%"+search.getField03()+"%"})
				.addCondition(search != null && search.getField04() != null && !"".equals(search.getField04()),"t.createBy like ?","%"+search.getField04()+"%")
				.addCondition("t.sysType = ?", 0)
				.addCondition("t.deleteFlag = ?", 0)
				.addOrderProperty("t.createOn", false)
				.addLimitClause(Integer.parseInt(currentPage), pageSize);
		
		
		PageBean pageBean = sysAdminService.getPageBean(Integer.parseInt(currentPage),pageSize,query);
		this.session.put("search", search);
		ActionContext.getContext().getValueStack().push(pageBean);
		
		return SUCCESS;
	}
	
	
	/**
	 * TO_添加管理员页面
	 */
	@Action(value="toAdd",results={@Result(name="success",location="/WEB-INF/view/syssetting/adminManagerToAdd.jsp")})
	public String toAdministratorManagerAdd()throws Exception{
		
		// >> 准备数据 系统当前的所有角色
		sysAdminVo = new SysAdminVo();
		List<SysRoleVo>  sysRoleList = rolePermissionService.queryAllSysRole(0);
		sysAdminVo.setSysRoleList(sysRoleList);
		
		// 加载省份数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = sysAreaService.querySysArea(sysArea);
		
		return SUCCESS;
	}
	
	/**
	 * 添加_管理员信息
	 */
	@Action(value="administratorManagerAdd",results = { @Result( type = "json") })
	public String administratorManagerAdd()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		sysAdminEntity.setUserName(sysAdminVo.getUserName()); // >> 用户姓名
		sysAdminEntity.setLoginName(sysAdminVo.getLoginName()); //>> 登陆名
		sysAdminEntity.setPassWord(sysAdminVo.getPassWord());// >>  密码
		sysAdminEntity.setDepartmentId("");// >>  部门ID
		sysAdminEntity.setTelephone(sysAdminVo.getTelephone()); // >> 电话号码
		sysAdminEntity.setStatus(sysAdminVo.getStatus());// >>  账号状态
		sysAdminEntity.setRemark(sysAdminVo.getRemark());// >> 备注
		sysAdminEntity.setCreateBy(admin.getUserId()); // >> 操作人
		sysAdminEntity.setProvinceCode(sysAdminVo.getProvinceCode());
		sysAdminEntity.setCityCode(sysAdminVo.getCityCode());
		if(!StringUtils.isEmpty(sysAdminVo.getProvinceCode())&&!StringUtils.isEmpty(sysAdminVo.getCityCode())){
			SysArea area = sysAreaService.queryBeanByCode(sysAdminVo.getCityCode());
			sysAdminEntity.setCityName(area.getAreaName());
		}
		// >> 角色ID
		String roleId = sysAdminVo.getRoleId();
		boolean flag = sysAdminService.insertAdminData(sysAdminEntity, 0,roleId);
		if(flag){
			pw.print("yes");
			return null;
		}else{
			pw.print("no");
			return null;
		}
	}
	
	/**
	 * TO_修改管理员页面
	 */
	@Action(value="toUpdate",results={@Result(name="success",location="/WEB-INF/view/syssetting/adminManagerToUpdate.jsp")})
	public String toAdministratorManagerUpdate()throws Exception{
		
		if(sysAdminVo.getUserId() != null && !"".equals(sysAdminVo.getUserId())){
			
			sysAdminEntity.setUserId(sysAdminVo.getUserId());
			// >> 通过用户ID和系统类型查询管理员信息
			sysAdminVo = sysAdminService.queryAdminByUserIdAndSysId(sysAdminEntity.getUserId(),0);
			List<SysRoleVo>  sysRoleList = rolePermissionService.queryAllSysRole(0);
			sysAdminVo.setSysRoleList(sysRoleList);
			
			
		}
		
		// 加载省份数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = sysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	
	/**
	 * 修改_管理员信息
	 */
	@Action(value="administratorManagerUpdate",results = { @Result( type = "json") })
	public String administratorManagerUpdate()throws Exception{
		
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		//获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		sysAdminEntity.setUserId(sysAdminVo.getUserId()); // >> 修改的userID
		sysAdminEntity.setUserName(sysAdminVo.getUserName()); // >> 用户姓名
		sysAdminEntity.setLoginName(sysAdminVo.getLoginName()); //>> 登陆名
		sysAdminEntity.setPassWord(sysAdminVo.getPassWord());// >>  密码
		sysAdminEntity.setDepartmentId("");// >>  部门ID
		sysAdminEntity.setTelephone(sysAdminVo.getTelephone()); // >> 电话号码
		sysAdminEntity.setStatus(sysAdminVo.getStatus());// >>  账号状态
		sysAdminEntity.setRemark(sysAdminVo.getRemark());// >> 备注
		sysAdminEntity.setUpdateBy(admin.getUserId()); // >> 操作人
		sysAdminEntity.setProvinceCode(sysAdminVo.getProvinceCode());
		sysAdminEntity.setCityCode(sysAdminVo.getCityCode());
		if(!StringUtils.isEmpty(sysAdminVo.getProvinceCode())&&!StringUtils.isEmpty(sysAdminVo.getCityCode())){
			SysArea area = sysAreaService.queryBeanByCode(sysAdminVo.getCityCode());
			sysAdminEntity.setCityName(area.getAreaName());
		}
		
		// >> 角色ID
		String roleId = sysAdminVo.getRoleId();
		boolean flag = sysAdminService.updateAdminData(sysAdminEntity, 0,roleId);
		
		// >> 该管理员在线需要踢掉该管理员
		/*ServletContext context = httpSession.getServletContext();
		SysAdminVo currentUpdateAdmin = sysAdminService.queryAdminByLoginNameAndPassword(sysAdminVo.getLoginName(), 0);
		Map<SysAdminVo,HttpSession> map = (Map<SysAdminVo, HttpSession>) context.getAttribute("usermap");
		HttpSession session = map.get(currentUpdateAdmin);*/
		
		//KickOutLoginUser.kickOutByUserId(httpSession.getServletContext(), sysAdminVo.getUserId());
		
		/*if(session!=null){
			//说明是登陆状态
			session.invalidate();
		}*/
		
		if(flag){
			pw.print("yes");
			return null;
		}else{
			pw.print("no");
			return null;
		}
		
	
	}
	
	/**
	 * TO_查看管理员详情页面
	 */
	@Action(value="toShow",results={@Result(name="success",location="/WEB-INF/view/syssetting/adminManagerToDetail.jsp")})
	public String toAdministratorManagerShow()throws Exception{
		if(sysAdminVo.getUserId() != null && !"".equals(sysAdminVo.getUserId())){
			
			sysAdminEntity.setUserId(sysAdminVo.getUserId());
			// >> 通过用户ID和系统类型查询管理员信息
			sysAdminVo = sysAdminService.queryAdminByUserIdAndSysId(sysAdminEntity.getUserId(),0);
			List<SysRoleVo>  sysRoleList = rolePermissionService.queryAllSysRole(0);
			sysAdminVo.setSysRoleList(sysRoleList);
		}
		// 加载省份数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = sysAreaService.querySysArea(sysArea);
		super.getSession().put("toDetailCurrentPageIndex",this.currentPage);
		return SUCCESS;
	}
	
	/**
	 * 重置密码
	 */
	@Action(value="resetPassword",results = { @Result( type = "json") })
	public String resetPassword()throws Exception{
		String loginName = request.getParameter("loginName");
		String userId = request.getParameter("userId");
		
		if( userId != null && !"".equals(userId)){
			HttpServletResponse response = getResponse();
			response.setContentType("text/html;charset=GBK");
			PrintWriter pw = response.getWriter();
			
			//获取用户的登陆信息
			HttpSession httpSession = ServletActionContext.getRequest().getSession();
			SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
			
			sysAdminEntity.setUserId(userId);
			sysAdminEntity.setLoginName(loginName);
			sysAdminEntity.setPassWord("888888");
			sysAdminEntity.setUpdateBy(admin.getUserId());
			boolean flag = sysAdminService.resetPassword(sysAdminEntity,0);
			
			// 踢掉用户
			//KickOutLoginUser.kickOutByUserId(httpSession.getServletContext(), userId);
			
			if(flag){
				pw.print("yes");
			}else{
				pw.print("no");
			}
		}
		return null;
	}

	/**
	 * 验证登陆名称是否存在
	 * @return yes存在,no不存在
	 */
	@Action(value="loginNameIsRepetition",results = { @Result( type = "json") })
	public String loginNameIsRepetition()throws IOException{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		String loginName = request.getParameter("loginName").trim();
		if(org.apache.commons.lang3.StringUtils.isEmpty(loginName)){
			pw.print("isNull");
			return null;
		}
		boolean flag = sysAdminService.loginNameIsRepetition(loginName, 0);
		if(flag){
			pw.print("yes");
		}else{
			pw.print("no");
		}
		return null;
	}
	
	
	/**
	 * 删除管理员
	 * @return
	 */
	@Action(value="deleteAdmin",results = { @Result( type = "json") })
	public String deleteAdmin()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		//获取用户的登陆信息
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		
		String userids = request.getParameter("userids");
		if(null != userids && userids.length()>0){
			String flag = sysAdminService.deleteAdmin(admin,userids,0);	
			// 踢掉用户
			String[] idsA = userids.split(":");
			for(int i=0;i<idsA.length;i++){
				//KickOutLoginUser.kickOutByUserId(httpSession.getServletContext(), idsA[i]);
			}
			pw.print(flag);
			return null;
		}
		
		return null;
	}

	public SysAdminVo getSysAdminVo() {
		return sysAdminVo;
	}


	public void setSysAdminVo(SysAdminVo sysAdminVo) {
		this.sysAdminVo = sysAdminVo;
	}


	public SysAdminEntity getSysAdminEntity() {
		return sysAdminEntity;
	}


	public void setSysAdminEntity(SysAdminEntity sysAdminEntity) {
		this.sysAdminEntity = sysAdminEntity;
	}


	public Search getSearch() {
		return search;
	}


	public void setSearch(Search search) {
		this.search = search;
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


	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}


	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}
	
	
}
