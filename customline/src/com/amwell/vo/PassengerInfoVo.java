package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class PassengerInfoVo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -598334198145358783L;

	private String passengerId;
	
	private String presetTime;

	private String displayId;

	private String nickName;

	// 性别 0:男 1:女
	private int sex;

	private String telephone;
	
	private String sendPhoneNo;

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

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSendPhoneNo() {
		return sendPhoneNo;
	}

	public void setSendPhoneNo(String sendPhoneNo) {
		this.sendPhoneNo = sendPhoneNo;
	}
	
	
}
