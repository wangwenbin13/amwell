package com.pig84.ab.vo.driver;

/**
 * 司机反馈表
 */

public class DriverFeedback implements java.io.Serializable {


	private String feedbackId;
	private String driverId;
	private String feedbackTime;
	private String feedbackContext;
	private String phoneNo;
	
	public String getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getFeedbackContext() {
		return feedbackContext;
	}
	public void setFeedbackContext(String feedbackContext) {
		this.feedbackContext = feedbackContext;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
}
