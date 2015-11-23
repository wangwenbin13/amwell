package com.pig84.ab.vo;

import java.math.BigDecimal;

/**
 * 
 * @author wangwenbin
 * 2014-11-26
 * 包车实体
 */
public class BcOrderEntiry {

	/**
	 * 主健ID
	 */
	private String orderId;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 线路ID
	 */
	private String bc_line_id;

	/**
	 * 商家ID
	 */
	private String businessId;

	/**
	 * 乘客ID
	 */
	private String passengerId;

	/**
	 * 总价
	 */
	private BigDecimal totalPrice;
	
	private BigDecimal price;//decimal(16,2) NULL车票单价(实际支付)
	private String couponGiftId;//varchar(35) NULL优惠ID
	private BigDecimal giftMoney;//decimal(16,2) NULL优惠金额
	private BigDecimal giftLeftMoney;//decimal(16,2) NULL优惠券未使用金额

	/**
	 * 订单状态,1:待调度 2：已调度(同时输入司机、车辆) 3：已过期
	 */
	private Integer orderStatus;

	/**
	 * 司机姓名,多个司机以英文逗号隔开
	 */
	private String driverName;

	/**
	 * 司机手机号码,多个手机号码以英文逗号隔开
	 */
	private String driverTelephone;

	/**
	 * 车牌号，多个车牌号以英文逗号隔开
	 */
	private String carNo;

	/**
	 * 乘客支付方式，1:支付宝 2：银联
	 */
	private Integer payModel;

	/**
	 * 支付时间
	 */
	private String payTime;

	/**
	 * 退票规则ID
	 */
	private String returnRuleId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBc_line_id() {
		return bc_line_id;
	}

	public void setBc_line_id(String bcLineId) {
		bc_line_id = bcLineId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverTelephone() {
		return driverTelephone;
	}

	public void setDriverTelephone(String driverTelephone) {
		this.driverTelephone = driverTelephone;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public Integer getPayModel() {
		return payModel;
	}

	public void setPayModel(Integer payModel) {
		this.payModel = payModel;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getReturnRuleId() {
		return returnRuleId;
	}

	public void setReturnRuleId(String returnRuleId) {
		this.returnRuleId = returnRuleId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCouponGiftId() {
		return couponGiftId;
	}

	public void setCouponGiftId(String couponGiftId) {
		this.couponGiftId = couponGiftId;
	}

	public BigDecimal getGiftMoney() {
		return giftMoney;
	}

	public void setGiftMoney(BigDecimal giftMoney) {
		this.giftMoney = giftMoney;
	}

	public BigDecimal getGiftLeftMoney() {
		return giftLeftMoney;
	}

	public void setGiftLeftMoney(BigDecimal giftLeftMoney) {
		this.giftLeftMoney = giftLeftMoney;
	}

}
