package com.amwell.vo.app;

/**
 * 线路_包月信息（运营规划）
 */
@SuppressWarnings("all")
public class LinePassengerMonth implements java.io.Serializable {
	private String monthId;
	private String classTime;
	private String months;
	private String lineBaseId;
	private String createBy;
	private String createOn;
	private String updateBy;
	private String updateOn;

	public String getMonthId() {
		return this.monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
	}

	public String getClassTime() {
		return this.classTime;
	}

	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}

	public String getMonths() {
		return this.months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getLineBaseId() {
		return this.lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateOn() {
		return this.updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

}