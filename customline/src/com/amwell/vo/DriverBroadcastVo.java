package com.amwell.vo;

/**
 * 司机报站信息
 */

public class DriverBroadcastVo implements java.io.Serializable {
	
	private static final long serialVersionUID = -5915639493055052407L;
	
	private String orderDate;//日期
	private String brefName;//商户简称
	private String driverName;//司机姓名
	private String telephone;//司机电话
	private String lineName;//线路名称
	private String startStation;//起点
	private String endStation;//终点
	private String startStationB;//起点报站
	private String endStationB;//终点报站
	private String orderStartTime;//线路发车时间
	private String actualStartTime;//实际发车时间
	private String upperLimb;//上座率
	
	private String dispatchStatus;//tinyint(4) NULL发车状态（0.未发车 1.已发车 2.行程结束）
	private String currentStationName;//varchar(64) NULL当前站名
	private String lineBaseId;//线路id
	private String lineClassId;//班次id
	private String orderSeats;//座位数
	private String totalBuyPeople;//买票人数
	
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getEndStation() {
		return endStation;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public String getStartStationB() {
		return startStationB;
	}
	public void setStartStationB(String startStationB) {
		this.startStationB = startStationB;
	}
	public String getEndStationB() {
		return endStationB;
	}
	public void setEndStationB(String endStationB) {
		this.endStationB = endStationB;
	}
	public String getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public String getActualStartTime() {
		return actualStartTime;
	}
	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	public String getUpperLimb() {
		return upperLimb;
	}
	public void setUpperLimb(String upperLimb) {
		this.upperLimb = upperLimb;
	}
	public String getDispatchStatus() {
		return dispatchStatus;
	}
	public void setDispatchStatus(String dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}
	public String getCurrentStationName() {
		return currentStationName;
	}
	public void setCurrentStationName(String currentStationName) {
		this.currentStationName = currentStationName;
	}
	public String getLineBaseId() {
		return lineBaseId;
	}
	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	public String getLineClassId() {
		return lineClassId;
	}
	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}
	public String getOrderSeats() {
		return orderSeats;
	}
	public void setOrderSeats(String orderSeats) {
		this.orderSeats = orderSeats;
	}
	public String getTotalBuyPeople() {
		return totalBuyPeople;
	}
	public void setTotalBuyPeople(String totalBuyPeople) {
		this.totalBuyPeople = totalBuyPeople;
	}
	public String getBrefName() {
		return brefName;
	}
	public void setBrefName(String brefName) {
		this.brefName = brefName;
	}
}