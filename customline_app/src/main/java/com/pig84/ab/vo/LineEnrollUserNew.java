package com.pig84.ab.vo;

/**
 * 线路_招募报名表
 */

@SuppressWarnings("all")
public class LineEnrollUserNew implements java.io.Serializable {

	private String eid; //主键
	private String passengerId;
	private String applicationId;
	private String apptime;
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApptime() {
		return apptime;
	}
	public void setApptime(String apptime) {
		this.apptime = apptime;
	}
}
