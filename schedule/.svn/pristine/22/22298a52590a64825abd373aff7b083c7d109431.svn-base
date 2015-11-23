package com.amwell.vo;

import java.util.ArrayList;
import java.util.List;

import com.amwell.entity.base.BaseEntity;

public class SysRoleVo extends BaseEntity{
	private static final long serialVersionUID = 7417882941331955967L;
	//角色ID
	private String roleId;
	//角色名称
	private String roleName;
	//备注
	private String remark;
	//系统类型(0:运营平台 1:调度平台)
	private int sysType;
	//创建人(用户的ID字段)
	private String createBy;
	//创建时间
	private String createOn;
	//修改人(用户的ID字段)
	private String updateBy;
	//用于查询的时候存放操作人
	private String userName;
	//修改时间
	private String updateOn;
	
	//角色对应的权限集合
	private List<SysPermissionVo> SysPermissionList= new ArrayList<SysPermissionVo>();
	
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSysType() {
		return sysType;
	}
	public void setSysType(int sysType) {
		this.sysType = sysType;
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
	public List<SysPermissionVo> getSysPermissionList() {
		return SysPermissionList;
	}
	public void setSysPermissionList(List<SysPermissionVo> sysPermissionList) {
		SysPermissionList = sysPermissionList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
