package com.pig84.ab.vo;

/**
 *线路_班次信息表
 */
@SuppressWarnings("all")
public class LineClassInfo implements java.io.Serializable {

	private String lineClassId;			//班次ID
	private String orderStartTime;		//发车时间
	private String orderSeats;			//座位数
	private String orderDate;			//排班日期
	private String lineBaseId;			//线路信息ID
	private String driverId;			//司机ID
	private String vehicleId;			//车辆ID
	private String freeSeat;			//剩余座位数
	private String temp;
	private String price;				//实际支付金额
	private String lineBusinessId;		//运营商ID
	
	
	private String dispatchStatus;//tinyint(4) NULL发车状态（0.未发车 1.已发车 2.行程结束）
	private String currentStationName;//varchar(64) NULL当前站名
	private String totalTime;//varchar(64) NULL实际行程总时长
	private String isDelay;//tinyint(4) NULL是否延误（0.延误 1.未延误）
	private String delayMsgId;//varchar(35) NULL延误消息id
	private String delayTime;//varchar(35) NULL延误时长(单位分钟)
	
	
	

	public String getLineBusinessId() {
		return lineBusinessId;
	}

	public void setLineBusinessId(String lineBusinessId) {
		this.lineBusinessId = lineBusinessId;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getFreeSeat() {
		return freeSeat;
	}

	public void setFreeSeat(String freeSeat) {
		this.freeSeat = freeSeat;
	}

	public String getLineClassId() {
		return this.lineClassId;
	}

	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}

	public String getOrderStartTime() {
		return this.orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getOrderSeats() {
		return this.orderSeats;
	}

	public void setOrderSeats(String orderSeats) {
		this.orderSeats = orderSeats;
	}

	public String getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getLineBaseId() {
		return this.lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}

	public String getDelayMsgId() {
		return delayMsgId;
	}

	public void setDelayMsgId(String delayMsgId) {
		this.delayMsgId = delayMsgId;
	}

	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
}
