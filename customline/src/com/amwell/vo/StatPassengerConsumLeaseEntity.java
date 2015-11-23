package com.amwell.vo;

import java.math.BigDecimal;

/**
 * 
 * @author wangwenbin
 *
 * 2014-10-23
 */
/**
 * 统计_用户订单消费表
 */
public class StatPassengerConsumLeaseEntity {

	/**
	 * 订单消费ID
	 */
	private String orderConsumId;
	
	/**
	 * 乘客ID
	 */
	private String passengerId;
	
	/**
	 * 订单号
	 */
	private String leaseOrderNo;
	
	/**
	 * 订单付款时间
	 */
	private String leasePayTime;
	
	/**
	 * 订单额度
	 */
	private BigDecimal leaseLimit;
	
	/**
	 * 商户ID
	 */
	private String businessId;

	public String getOrderConsumId() {
		return orderConsumId;
	}

	public void setOrderConsumId(String orderConsumId) {
		this.orderConsumId = orderConsumId;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getLeaseOrderNo() {
		return leaseOrderNo;
	}

	public void setLeaseOrderNo(String leaseOrderNo) {
		this.leaseOrderNo = leaseOrderNo;
	}

	public String getLeasePayTime() {
		return leasePayTime;
	}

	public void setLeasePayTime(String leasePayTime) {
		this.leasePayTime = leasePayTime;
	}

	public BigDecimal getLeaseLimit() {
		return leaseLimit;
	}

	public void setLeaseLimit(BigDecimal leaseLimit) {
		this.leaseLimit = leaseLimit;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	
}
