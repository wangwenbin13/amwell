package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class DriverVo extends BaseEntity {

	private static final long serialVersionUID = 9131417345799723073L;

	private String driverId;

	private String driverName;

	private int sex;
	
	private String telephone;

	/**
	 * 排班状态：0：未排班，1：已排班
	 */
	private int scheduleCount;

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
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

	public int getScheduleCount() {
		return scheduleCount;
	}

	public void setScheduleCount(int scheduleCount) {
		this.scheduleCount = scheduleCount;
	}
}
