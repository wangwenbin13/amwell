package com.amwell.entity;

import java.math.BigDecimal;
/**
 * 
 * @author wangwenbin
 *
 * 2014-12-2
 */
/**
 * 包车退票历史Vo
 */
public class BcReturnHistoryVo {

	/**
	 * 包车退票ID
	 */
	private String bcReturnId;
	
	/**
	 * 包车订单号
	 */
	private String orderNo;
	
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 联系方式
	 */
	private String telephone;
	
	/**
	 * 包车类型
	 * 1:活动包车 2:商务接送 3:过港接送
	 */
	private String businessType;
	
	/**
	 * 包车方式
	 * 1：单趟 2:往返 3:包天
	 */
	private String charteredMode;
	
	/**
	 * 出发时间
	 */
	private String startTime;
	
	/**
	 * 返程时间
	 */
	private String returnTime;
	
	/**
	 * 包车商家
	 */
	private String brefName;
	
	/**
	 * 实际退款金额
	 */
	private BigDecimal bcReturnMoney;
	
	/**
	 * 操作时间
	 */
	private String operateTime;
	
	/**
	 * 操作人
	 */
	private String userName;
	
	/**
	 * 上车点
	 * @return
	 */
	private String beginAddress;
	
	/**
	 * 下车点
	 * @return
	 */
	private String endAddress;
	
	/**
	 * 总人数
	 * @return
	 */
	private Integer totalPassengers;
	
	/**
	 * 总车辆数
	 * @return
	 */
	private Integer totalCars;
	
	/**
	 * 用车联系人
	 * @return
	 */
	private String contacts;
	
	/**
	 * 联系电话
	 * @return
	 */
	private String contactPhone;
	
	/**
	 * 是否需要发票
	 * 0:不需要发票 1:需要发票
	 * @return
	 */
	private Integer needInvoice;
	
	/**
	 * 乘客回显ID
	 * @return
	 */
	private String displayId;
	
	/**
	 * 实际退款费率
	 * @return
	 */
	private String realPer;
	
	/**
	 * 车龄 0:不限 1:1年以内 2:3年以内 3:5年以内
	 * @return
	 */
	private Integer carAge;

	public String getBcReturnId() {
		return bcReturnId;
	}

	public void setBcReturnId(String bcReturnId) {
		this.bcReturnId = bcReturnId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCharteredMode() {
		return charteredMode;
	}

	public void setCharteredMode(String charteredMode) {
		this.charteredMode = charteredMode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getBrefName() {
		return brefName;
	}

	public void setBrefName(String brefName) {
		this.brefName = brefName;
	}

	public BigDecimal getBcReturnMoney() {
		return bcReturnMoney;
	}

	public void setBcReturnMoney(BigDecimal bcReturnMoney) {
		this.bcReturnMoney = bcReturnMoney;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBeginAddress() {
		return beginAddress;
	}

	public void setBeginAddress(String beginAddress) {
		this.beginAddress = beginAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	public Integer getTotalPassengers() {
		return totalPassengers;
	}

	public void setTotalPassengers(Integer totalPassengers) {
		this.totalPassengers = totalPassengers;
	}

	public Integer getTotalCars() {
		return totalCars;
	}

	public void setTotalCars(Integer totalCars) {
		this.totalCars = totalCars;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Integer needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getRealPer() {
		return realPer;
	}

	public void setRealPer(String realPer) {
		this.realPer = realPer;
	}

	public Integer getCarAge() {
		return carAge;
	}

	public void setCarAge(Integer carAge) {
		this.carAge = carAge;
	}
	
}
