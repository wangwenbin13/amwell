package com.amwell.vo;

import java.io.Serializable;

/**
 * 线路修改发车时间VO
 *
 */
public class LineTimeChange implements Serializable{
	
	private String changeid;
	private String linebaseid;
	private String linetime;
	private String ismsgsend;
	private String worktime;
	private String isfinish;
	private String createBy;
	private String createOn;
	public String getChangeid() {
		return changeid;
	}
	public void setChangeid(String changeid) {
		this.changeid = changeid;
	}
	public String getLinebaseid() {
		return linebaseid;
	}
	public void setLinebaseid(String linebaseid) {
		this.linebaseid = linebaseid;
	}
	public String getLinetime() {
		return linetime;
	}
	public void setLinetime(String linetime) {
		this.linetime = linetime;
	}
	public String getIsmsgsend() {
		return ismsgsend;
	}
	public void setIsmsgsend(String ismsgsend) {
		this.ismsgsend = ismsgsend;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}
	public String getIsfinish() {
		return isfinish;
	}
	public void setIsfinish(String isfinish) {
		this.isfinish = isfinish;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateOn() {
		return createOn;
	}
	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
	

}
