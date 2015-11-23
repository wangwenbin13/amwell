package com.amwell.vo;

public class LineCarMsgVo {
	private String lineBaseId;  //线路ID
	private String orderStartTime; //发车时间
	private String lineName;     //线路名称
	private String GPSNo;        //GPS
	private String vehicleNumber; //车牌号
	public String getLineBaseId() {
		return lineBaseId;
	}
	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	public String getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getGPSNo() {
		return GPSNo;
	}
	public void setGPSNo(String gPSNo) {
		GPSNo = gPSNo;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
}
