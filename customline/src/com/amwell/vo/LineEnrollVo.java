package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class LineEnrollVo extends BaseEntity {

	private static final long serialVersionUID = -6359197908752669222L;

	private String enrollId;
	
	private String presetTime;
	
	private String lineBaseId;
	
	private String passengerId;
	
	private int wstatus;
	
	private String backup;
	
	private String ustation;
	
	private String dstation;
	
	private String applyName;
	
	private String applyTel;
	
	private int applyNum;
	
	private int source;

	private String remark;
	
	public String getEnrollId() {
		return enrollId;
	}

	public void setEnrollId(String enrollId) {
		this.enrollId = enrollId;
	}

	public String getPresetTime() {
		return presetTime;
	}

	public void setPresetTime(String presetTime) {
		this.presetTime = presetTime;
	}

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public int getWstatus() {
		return wstatus;
	}

	public void setWstatus(int wstatus) {
		this.wstatus = wstatus;
	}

	public String getBackup() {
		return backup;
	}

	public void setBackup(String backup) {
		this.backup = backup;
	}

	public String getUstation() {
		return ustation;
	}

	public void setUstation(String ustation) {
		this.ustation = ustation;
	}

	public String getDstation() {
		return dstation;
	}

	public void setDstation(String dstation) {
		this.dstation = dstation;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyTel() {
		return applyTel;
	}

	public void setApplyTel(String applyTel) {
		this.applyTel = applyTel;
	}

	public int getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(int applyNum) {
		this.applyNum = applyNum;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
