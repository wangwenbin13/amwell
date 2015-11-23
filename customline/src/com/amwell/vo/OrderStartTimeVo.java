package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class OrderStartTimeVo extends BaseEntity {

	private static final long serialVersionUID = 2311497200317904374L;
	
	private String orderStartTime;

	public OrderStartTimeVo(){
		
	}
	
	public OrderStartTimeVo(String orderStartTime){
		this.orderStartTime = orderStartTime;
	}
	
	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result+ ((orderStartTime == null) ? 0 : orderStartTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderStartTimeVo other = (OrderStartTimeVo) obj;
		if (orderStartTime == null) {
			if (other.orderStartTime != null)
				return false;
		} else if (!orderStartTime.equals(other.orderStartTime))
			return false;
		return true;
	}

	
}
