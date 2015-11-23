package com.amwell.vo.gf.temp;

import java.util.ArrayList;
import java.util.List;

import com.amwell.entity.base.BaseEntity;

/**
 * 优惠券信息迁移临时表
 */
public class CouponAndGiftTemp extends BaseEntity {
	private String couponGiftId;//varchar(35) NOT NULL主键ID
	private String couponId;//varchar(35) NOT NULL优惠劵主键ID
	private String giftPriId;//varchar(35) NOT NULL礼品主键ID
	
	private CouponTemp couponTemp;
	private GiftTemp giftTemp;
	private List<CouponPassengerTemp> list=new ArrayList<CouponPassengerTemp>();
	public String getCouponGiftId() {
		return couponGiftId;
	}
	public void setCouponGiftId(String couponGiftId) {
		this.couponGiftId = couponGiftId;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getGiftPriId() {
		return giftPriId;
	}
	public void setGiftPriId(String giftPriId) {
		this.giftPriId = giftPriId;
	}
	public CouponTemp getCouponTemp() {
		return couponTemp;
	}
	public void setCouponTemp(CouponTemp couponTemp) {
		this.couponTemp = couponTemp;
	}
	public GiftTemp getGiftTemp() {
		return giftTemp;
	}
	public void setGiftTemp(GiftTemp giftTemp) {
		this.giftTemp = giftTemp;
	}
	public List<CouponPassengerTemp> getList() {
		return list;
	}
	public void setList(List<CouponPassengerTemp> list) {
		this.list = list;
	}
}
