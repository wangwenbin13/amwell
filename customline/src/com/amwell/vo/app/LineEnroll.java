package com.amwell.vo.app;

/**
 * 线路_招募报名表
 */

@SuppressWarnings("all")
public class LineEnroll implements java.io.Serializable {

	private String enrollId; //主键
	private String presetTime;//预定时间
	private String lineBaseId;//线路ID
	private String passengerId;//乘客ID


	public String getEnrollId() {
		return this.enrollId;
	}

	public void setEnrollId(String enrollId) {
		this.enrollId = enrollId;
	}

	public String getPresetTime() {
		return this.presetTime;
	}

	public void setPresetTime(String presetTime) {
		this.presetTime = presetTime;
	}

	public String getLineBaseId() {
		return this.lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

}