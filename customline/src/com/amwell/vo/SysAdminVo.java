package com.amwell.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class SysAdminVo implements Serializable,HttpSessionBindingListener {
	//用户ID
	private String userId;
	//用户姓名
	private String userName;
	//登陆名称
	private String loginName;
	//密码
	private String passWord;
	//部门ID
	private String departmentId;
	//联系电话
	private String telephone;
	//账号状态0:无效 1:有效
	private int status;
	//系统类型(0:运营平台 1:调度平台)
	private int sysType;
	//备注
	private String remark;
	//创建人
	private String createBy;
	//创建时间
	private String createOn;
	//更新人
	private String updateBy;
	//更新时间
	private String updateOn;
	//用户的角色ID
	private String roleId;
	//用户的角色名称
	private String roleName;
	//省份编码
	private String provinceCode;
	//城市编码
	private String cityCode;
	//城市名称
	private String cityName;
	
	//角色对应的角色
	private List<SysRoleVo>  sysRoleList = new ArrayList<SysRoleVo>();
	//用户对应的权限
	private List<SysPermissionVo> sysPermissions = new ArrayList<SysPermissionVo>();
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateOn() {
		return createOn;
	}
	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

	

	public List<SysRoleVo> getSysRoleList() {
		return sysRoleList;
	}
	public void setSysRoleList(List<SysRoleVo> sysRoleList) {
		this.sysRoleList = sysRoleList;
	}
	public List<SysPermissionVo> getSysPermissions() {
		return sysPermissions;
	}

	public void setSysPermissions(List<SysPermissionVo> sysPermissions) {
		this.sysPermissions = sysPermissions;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	// >>
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysAdminVo other = (SysAdminVo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	//当对象从Session中移除掉时候触发
	public void valueUnbound(HttpSessionBindingEvent event) {
		
		HttpSession session  = event.getSession();
		ServletContext context = session.getServletContext();
		
		Map<SysAdminVo,HttpSession> map = (Map<SysAdminVo, HttpSession>) context.getAttribute("usermap");
		map.remove(this);
		
		
	}
	
	//当对象存入Session时触发的方法
	public void valueBound(HttpSessionBindingEvent event) {
		
		HttpSession session  = event.getSession();
		ServletContext context = session.getServletContext();
		
		Map<SysAdminVo,HttpSession> map = (Map<SysAdminVo, HttpSession>) context.getAttribute("usermap");
		
		if(null == map ){
			return;
		}
		
		//支持同一账号登陆
		HttpSession exitsSession = map.get(this);
		if(exitsSession != null){// >> 说明当前账号已登录
			//exitsSession.invalidate();
		}
		map.put(this, session);
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
