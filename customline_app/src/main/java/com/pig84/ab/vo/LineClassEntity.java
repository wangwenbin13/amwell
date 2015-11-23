package com.pig84.ab.vo;


/**
 * 线路班次实体类
 * 
 * @author Administrator
 * 
 */
public class LineClassEntity {

	/**
	 * 班次ID
	 */
	private String lineClassId;
	/**
	 * 发车时间
	 */
	private String orderStartTime;
	/**
	 * 座位数,默认为50
	 */
	private int orderSeats = 50;
	/**
	 * 排班日期
	 */
	private String orderDate;

	/**
	 * 线路信息ID
	 */
	private String lineBaseId;

	/**
	 * 司机ID
	 */
	private String driverId;

	/**
	 * 车辆ID
	 */
	private String vehicleId;

	public LineClassEntity() {

	}

	public String getLineClassId() {
		return lineClassId;
	}

	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public int getOrderSeats() {
		return orderSeats;
	}

	public void setOrderSeats(int orderSeats) {
		this.orderSeats = orderSeats;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

}
