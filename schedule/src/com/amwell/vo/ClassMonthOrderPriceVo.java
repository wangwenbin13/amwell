package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class ClassMonthOrderPriceVo extends BaseEntity {

	private static final long serialVersionUID = 6023371063889876333L;

	private String month;

	private int totalDays;

	private String totalPrice;

	private String discountRate;

	private String discountPrice;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

}
