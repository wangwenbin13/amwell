package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class OrderMaxTicketCountVo extends BaseEntity {

	private static final long serialVersionUID = -679329064281241678L;
	
	private int orderMaxTicketCount;

	public int getOrderMaxTicketCount() {
		return orderMaxTicketCount;
	}

	public void setOrderMaxTicketCount(int orderMaxTicketCount) {
		this.orderMaxTicketCount = orderMaxTicketCount;
	}

}
