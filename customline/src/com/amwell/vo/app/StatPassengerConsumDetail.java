package com.amwell.vo.app;
// default package

/**
 * StatPassengerConsumDetail entity. @author MyEclipse Persistence Tools
 */

public class StatPassengerConsumDetail implements java.io.Serializable {


	private String detailconsumId;
	private String lineClassId;
	private String lineBaseId;
	private String consumeModel;
	private String leaseOrderId;
	private String consumeLimit;
	public String getDetailconsumId() {
		return detailconsumId;
	}
	public void setDetailconsumId(String detailconsumId) {
		this.detailconsumId = detailconsumId;
	}
	public String getLineClassId() {
		return lineClassId;
	}
	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}
	public String getLineBaseId() {
		return lineBaseId;
	}
	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	public String getConsumeModel() {
		return consumeModel;
	}
	public void setConsumeModel(String consumeModel) {
		this.consumeModel = consumeModel;
	}
	public String getLeaseOrderId() {
		return leaseOrderId;
	}
	public void setLeaseOrderId(String leaseOrderId) {
		this.leaseOrderId = leaseOrderId;
	}
	public String getConsumeLimit() {
		return consumeLimit;
	}
	public void setConsumeLimit(String consumeLimit) {
		this.consumeLimit = consumeLimit;
	}

}