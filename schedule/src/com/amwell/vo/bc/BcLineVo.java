package com.amwell.vo.bc;

import org.springframework.util.StringUtils;

import com.amwell.commons.DateHelper;
import com.amwell.entity.base.BaseEntity;

public class BcLineVo extends BaseEntity {

	private static final long serialVersionUID = -2525261426521320179L;
	//竞价ID
	private String businessBiddingId;
	//包车线路ID
	private String bc_line_id;
	//商户ID
	private String businessId;
	//竞价创建时间
	private String createOn;
	//业务类型
	private int businessType;
	
	private String expectPrice;
	
	private String startTime;

	private String returnTime;

	private int carAge;
	
	private String beginAddress;

	private String endAddress;

	private int totalPassengers;

	private int totalCars;

	private int charteredMode;
	
	private String journeyRemark;

	private int lineStatus;

	private int offerStatus;

	private String nowTime;

	private String expireTime;

	private String remainingTime;

	private String needInvoice;
	
	private String invoiceHeader;
	
	private String passengerId;
	
	private String telephone;
	
	private int sourceFrom;
	
	public String getBusinessBiddingId() {
		return businessBiddingId;
	}

	public void setBusinessBiddingId(String businessBiddingId) {
		this.businessBiddingId = businessBiddingId;
	}

	public String getBc_line_id() {
		return bc_line_id;
	}

	public void setBc_line_id(String bc_line_id) {
		this.bc_line_id = bc_line_id;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}
	
	public String getExpectPrice() {
		return expectPrice;
	}

	public void setExpectPrice(String expectPrice) {
		this.expectPrice = expectPrice;
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
	
	public int getCarAge() {
		return carAge;
	}

	public void setCarAge(int carAge) {
		this.carAge = carAge;
	}

	public String getBeginAddress() {
		return beginAddress;
	}

	public void setBeginAddress(String beginAddress) {
		this.beginAddress = null!=beginAddress?beginAddress.trim().replaceAll("[\\t\\n\\r]", ""):beginAddress;
	}

	public String getEndAddress() {
		return endAddress;
	}

	public void setEndAddress(String endAddress) {
		this.endAddress = null!=endAddress?endAddress.trim().replaceAll("[\\t\\n\\r]", ""):endAddress;
	}

	public int getTotalPassengers() {
		return totalPassengers;
	}

	public void setTotalPassengers(int totalPassengers) {
		this.totalPassengers = totalPassengers;
	}

	public int getTotalCars() {
		return totalCars;
	}

	public void setTotalCars(int totalCars) {
		this.totalCars = totalCars;
	}

	public int getCharteredMode() {
		return charteredMode;
	}

	public void setCharteredMode(int charteredMode) {
		this.charteredMode = charteredMode;
	}
	public String getJourneyRemark() {
		return journeyRemark;
	}

	public void setJourneyRemark(String journeyRemark) {
		this.journeyRemark = journeyRemark;
	}

	public int getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

	public int getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(int offerStatus) {
		this.offerStatus = offerStatus;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
		setRemainingTime(null);
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
		setRemainingTime(null);
	}

	public String getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(String remainingTime) {
		if(StringUtils.hasText(expireTime)&&StringUtils.hasText(nowTime)){
			this.remainingTime = String.valueOf(DateHelper.getDatesDiffMillisecond(expireTime, nowTime, DateHelper.FORMAT_FULL_DATE_TIME)/1000);
		}
	}

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public int getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(int sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

}
