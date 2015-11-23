package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class PassengerStationVo extends BaseEntity{

	private static final long serialVersionUID = 6129134651382228170L;

	private String passengerId;
	
	private String presetTime;

	private String displayId;

	private String nickName;

	// 性别 0:男 1:女
	private String sex;

	private String telephone;
	
	private String ustationName;
	
	private String dstationName;

	private int applyNum;
	
	private String remark;
	
	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getPresetTime() {
		return presetTime;
	}

	public void setPresetTime(String presetTime) {
		this.presetTime = presetTime;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUstationName() {
		return ustationName;
	}

	public void setUstationName(String ustationName) {
		this.ustationName = ustationName;
	}

	public String getDstationName() {
		return dstationName;
	}

	public void setDstationName(String dstationName) {
		this.dstationName = dstationName;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
