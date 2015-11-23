package com.amwell.entity;

import java.util.List;

/**
 * 
 * @author wangwenbin
 *
 * 2014-10-21
 */
/**
 * 退款Vo
 */
public class ReturnTicketVo {

	/**
	 * 订单ID
	 */
	private String leaseOrderId;
	
	/**
	 * 订单号
	 */
	private String leaseOrderNo;
	
	/**
	 * 乘车班次
	 */
	private String orderStartTime;
	
	/**
	 * 乘客回显ID
	 */
	private String displayId;
	
	/**
	 * 乘客昵称
	 */
	private String nickName;
	
	/**
	 * 联系方式
	 */
	 private String telephone;
	 
	 /**
	  * 乘客ID
	  */
	 private String passengerId;
	 
	 /**
	  * 线路ID
	  */
	 private String lineBaseId;
	
	/**
	 * 线路类型  0:上下班 1:旅游
	 */
	private String lineSubType;
	
	/**
	 * 线路名称
	 */
	private String lineName;
	
	/**
	 * 金额
	 */
	private String payMoney;
	
	/**
	 * 班次日期对应用户订单Vo
	 * @return
	 */
	private List<OrderLocalVo> orderLocalVos;
	
	/**
	 * 订单对应的所有班次的集合
	 * @return
	 */
	private List<OrderLocalVo> totalOrderLocalVos;
	
	/**购买次数**/
	private Integer buySize;

	public String getLeaseOrderNo() {
		return leaseOrderNo;
	}

	public void setLeaseOrderNo(String leaseOrderNo) {
		this.leaseOrderNo = leaseOrderNo;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(String lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public String getLeaseOrderId() {
		return leaseOrderId;
	}

	public void setLeaseOrderId(String leaseOrderId) {
		this.leaseOrderId = leaseOrderId;
	}

	public List<OrderLocalVo> getOrderLocalVos() {
		return orderLocalVos;
	}

	public void setOrderLocalVos(List<OrderLocalVo> orderLocalVos) {
		this.orderLocalVos = orderLocalVos;
	}

	public List<OrderLocalVo> getTotalOrderLocalVos() {
		return totalOrderLocalVos;
	}

	public void setTotalOrderLocalVos(List<OrderLocalVo> totalOrderLocalVos) {
		this.totalOrderLocalVos = totalOrderLocalVos;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public Integer getBuySize() {
		return buySize;
	}

	public void setBuySize(Integer buySize) {
		this.buySize = buySize;
	}

	
}