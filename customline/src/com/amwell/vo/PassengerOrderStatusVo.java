package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class PassengerOrderStatusVo extends BaseEntity {

	private static final long serialVersionUID = 6685015709938502736L;

	private String lineClassId;

	private String orderDate;

	private int remainTickets;

	private int orderCount;

	private int status;

	public String getLineClassId() {
		return lineClassId;
	}

	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getRemainTickets() {
		return remainTickets;
	}

	public void setRemainTickets(int remainTickets) {
		this.remainTickets = remainTickets;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
