package com.amwell.vo.gf;

import com.amwell.entity.base.BaseEntity;

/**
 * 优惠券礼品实体,一张优惠券可关联多张礼品
 */
public class CouponGiftVo extends BaseEntity {

	private static final long serialVersionUID = -6445097204397295064L;

	private String couponGiftId;

	private String couponId;

	private String giftPriId;

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

}
