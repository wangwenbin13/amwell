package com.amwell.vo.pc;

import com.amwell.entity.base.BaseEntity;

public class PcLineValidityVo extends BaseEntity {
	
	private static final long serialVersionUID = -6374464992722597484L;

	private String id;

	/**
	 * 拼车线路有效期,单位:天
	 */
	private int validityDays;
	/**
	 * 创建人
	 */
	private String operateBy;
	/**
	 * 创建时间
	 */
	private String operateOn;

	private String userName;

	private String loginName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getValidityDays() {
		return validityDays;
	}

	public void setValidityDays(int validityDays) {
		this.validityDays = validityDays;
	}

	public String getOperateBy() {
		return operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

	public String getOperateOn() {
		return operateOn;
	}

	public void setOperateOn(String operateOn) {
		this.operateOn = operateOn;
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

}
