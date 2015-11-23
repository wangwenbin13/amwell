package com.amwell.vo;


import com.amwell.entity.base.BaseEntity;


public class UserLineEntity extends BaseEntity {

	private static final long serialVersionUID = -7211427578895405783L;

	private String applicationId;

	private String applicationTime;

	private String passengerId;

	private String lineType;

	private String startAddress;

	private String endAddress;

	private String startTime;

	private String remark;

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 线路子类型 0:上下班 1:自由行
	 */
	public static enum LineType {
		WORK(0, "上下班"), TRAVEL(1, "自由行");
		private int id;
		private String text;

		private LineType(int id, String text) {
			this.id = id;
			this.text = text;
		}

		public static String getText(int id) {
			for (LineType l : LineType.values()) {
				if (l.getId() == id) {
					return l.getText();
				}
			}
			return "";
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

}
