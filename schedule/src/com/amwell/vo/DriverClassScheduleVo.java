package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class DriverClassScheduleVo extends BaseEntity {


	private static final long serialVersionUID = 2510165697482717548L;
	
	private String lineBaseId;

	private String lineClassId;

	private String orderStartTime;
	
	private String orderDate;
	
	private String lineTime;

	private int lineSubType;

	private String startname;

	private String endname;
	
	private String vehicleNumber;

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

	public String getLineTime() {
		return lineTime;
	}

	public void setLineTime(String lineTime) {
		this.lineTime = lineTime;
	}

	public int getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(int lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getStartname() {
		return startname;
	}

	public void setStartname(String startname) {
		this.startname = startname;
	}

	public String getEndname() {
		return endname;
	}

	public void setEndname(String endname) {
		this.endname = endname;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
}
