package com.pig84.ab.vo;

/**
 * 乘客_乘客帮助反馈表
 */

public class PassengerHelpFeedback implements java.io.Serializable {


	private String feedbackId;
	private String passengerId;
	private String feedbackTime;
	private String feedbackContext;
	private String phoneNo;
	private String status;
	
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
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


	
}
