package com.amwell.vo;

import java.io.Serializable;

public class LineCostSetVo implements Serializable {

	private static final long serialVersionUID = -2262745977336452847L;
	
	private String id;//int(10) NOT NULL主键ID
	private String lineBaseId;//varchar(35) NULL线路id
	private String theMode;//varchar(16) NULL成本分摊方式（fixed base price:固定底价 commission:佣金分成）
	private String costSharing;//decimal(10,2) NULL成本分摊（固定底价时输入数字，佣金分成是输入百分数）
	private String startTime;//varchar(10) NULL有效期开始时间（日期）
	private String endTime;//varchar(10) NULL有效期结束时间（日期）
	private String createBy;//varchar(35) NULL创建人
	private String createOn;//varchar(19) NULL创建时间
	private String updateBy;//varchar(35) NULL修改人
	private String updateOn;//varchar(19) NULL修改时间
	
	private String cityName;//所属地区
	private String brefName;//供应商
	private String lineSubType;//线路类型
	private String lineName;//线路名称
	private String orderStartTime;//发车时间
	private String startStationName;//起点
	private String endStationName;//终点
	private String orderPrice;//通票价格
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLineBaseId() {
		return lineBaseId;
	}
	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	public String getTheMode() {
		return theMode;
	}
	public void setTheMode(String theMode) {
		this.theMode = theMode;
	}
	public String getCostSharing() {
		return costSharing;
	}
	public void setCostSharing(String costSharing) {
		this.costSharing = costSharing;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getBrefName() {
		return brefName;
	}
	public void setBrefName(String brefName) {
		this.brefName = brefName;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
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
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
}
