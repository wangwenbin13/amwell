package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class MerchantOrderVo extends BaseEntity {

	private static final long serialVersionUID = -9061895118838629085L;

	private String orderStartTime;
	
	private String lineSubType;

	private String lineName;

	private String lineClassId;
	
	private String driverName;

	private String vehicleBrand;

	private String vehicleNumber;

	private int orderCount;
	
	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(String lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
	public String getLineClassId() {
		return lineClassId;
	}

	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

}
