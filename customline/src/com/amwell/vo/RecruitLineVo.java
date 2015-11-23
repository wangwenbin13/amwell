package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class RecruitLineVo extends BaseEntity {

	private static final long serialVersionUID = -7352446738656970917L;

	private String lineBaseId;

	private int lineSubType;
	
	private String lineName;

    private String startStationName;
	
	private String endStationName;
	
	private String stationInfoes;

	private String orderPrice;

	private String updateOn;

	private int applyTotal;

	private String userName;

	private int lineStatus;
	
	private String temp;//临时存站点名称
	
	private String cityName;//城市名称

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	
	public int getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(int lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public String getStationInfoes() {
		return stationInfoes;
	}

	public void setStationInfoes(String stationInfoes) {
		this.stationInfoes = stationInfoes;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

	public int getApplyTotal() {
		return applyTotal;
	}

	public void setApplyTotal(int applyTotal) {
		this.applyTotal = applyTotal;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

}
