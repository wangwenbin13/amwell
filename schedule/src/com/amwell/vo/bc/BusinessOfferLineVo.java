package com.amwell.vo.bc;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.amwell.commons.DateHelper;
import com.amwell.entity.base.BaseEntity;

/**
 * 商户待报价列表
 * @author Administrator
 */
public class BusinessOfferLineVo extends BaseEntity {

	private static final long serialVersionUID = -7145942691802017074L;

	private String bc_line_id;

	private String businessId;

	private String createOn;

	private int businessType;

	private String startTime;

	private String returnTime;

	private String beginAddress;

	private String endAddress;

	private int totalPassengers;

	private int totalCars;

	private int charteredMode;

	private int lineStatus;

	private int offerStatus;
	
	private String nowTime;
	
	private String expireTime;
	
	private String remainingTime;
	
	private String needInvoice;
	
	private BigDecimal totalCost;

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
			this.remainingTime=DateHelper.getDatesDiffString(expireTime, nowTime, DateHelper.FORMAT_FULL_DATE_TIME);
		}
	}

	public String getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}


	public BigDecimal getTotalCost() {
		return totalCost;
	}


	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	
}
