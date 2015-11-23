package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;
/**
 * 角色实体类
 * @author 胡双
 *
 */
public class SysRoleEntity extends BaseEntity{
	
	private static final long serialVersionUID = -482159136686664273L;
	
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
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
}
