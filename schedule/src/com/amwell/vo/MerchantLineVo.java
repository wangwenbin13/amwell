package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class MerchantLineVo extends BaseEntity{

	
	private static final long serialVersionUID = 2739012584031455566L;

	private String lineBaseId;
	
	private int lineType;
	
	private int lineSubType;
	
	private String lineName;
	
	private String startname;
	
	private String endname;
	
	private String orderPrice;
	
	private int lineStatus;

	/**
	 * 调度时间
	 */
	private String createOn;

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	
	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	public int getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(int lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getStartname() {
		return startname;
	}

	public void setStartname(String startname) {
		this.startname = startname;
	}

	public String getEndname() {
		return endname;
	}

	public void setEndname(String endname) {
		this.endname = endname;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
	
}
