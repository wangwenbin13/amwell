package com.amwell.vo.gf;

import java.io.Serializable;

/**
 * 
 * @author wangwebin
 *
 * 2015-3-10
 */
/**
 * 优惠券统计实体Vo
 */
public class CouponStatVo implements Serializable{

	/**优惠券总数**/
	private Long couponTotal;
	
	/**已使用的优惠券数量**/
	private Long couponUser;
	
	/**未使用的优惠券数量**/
	private Long couponUnUser;
	
	/**优惠券总金额**/
	private Long couponTotalMon;
	
	/**已使用的优惠券金额**/
	private Long couponUserMon;
	
	/**未使用的优惠券金额**/
	private Long couponUnUserMon;
	
	/**优惠券已领总量**/
	private Long couponGetTotal;
	
	/**已领中已使用的优惠券数量**/
	private Long couponGetUser;
	
	/**已领中未使用的优惠券数量**/
	private Long couponGetUnUser;
	
	/**优惠券已领总金额**/
	private Long couponGetMon;
	
	/**已领中已使用的优惠券金额**/
	private Long couponGetUserMon;
	
	/**已领中未使用的优惠券金额**/
	private Long couponGetUnUserMon;
	
	/**有效时间(开始)**/
	private String effectiveTime;
	
	/**有效时间(结束)**/
	private String expirationTime;

	public Long getCouponTotal() {
		return couponTotal;
	}

	public void setCouponTotal(Long couponTotal) {
		this.couponTotal = couponTotal;
	}

	public Long getCouponUser() {
		return couponUser;
	}

	public void setCouponUser(Long couponUser) {
		this.couponUser = couponUser;
	}

	public Long getCouponUnUser() {
		return couponUnUser;
	}

	public void setCouponUnUser(Long couponUnUser) {
		this.couponUnUser = couponUnUser;
	}

	public Long getCouponTotalMon() {
		return couponTotalMon;
	}

	public void setCouponTotalMon(Long couponTotalMon) {
		this.couponTotalMon = couponTotalMon;
	}

	public Long getCouponUserMon() {
		return couponUserMon;
	}

	public void setCouponUserMon(Long couponUserMon) {
		this.couponUserMon = couponUserMon;
	}

	public Long getCouponUnUserMon() {
		return couponUnUserMon;
	}

	public void setCouponUnUserMon(Long couponUnUserMon) {
		this.couponUnUserMon = couponUnUserMon;
	}

	public Long getCouponGetTotal() {
		return couponGetTotal;
	}

	public void setCouponGetTotal(Long couponGetTotal) {
		this.couponGetTotal = couponGetTotal;
	}

	public Long getCouponGetUser() {
		return couponGetUser;
	}

	public void setCouponGetUser(Long couponGetUser) {
		this.couponGetUser = couponGetUser;
	}

	public Long getCouponGetUnUser() {
		return couponGetUnUser;
	}

	public void setCouponGetUnUser(Long couponGetUnUser) {
		this.couponGetUnUser = couponGetUnUser;
	}

	public Long getCouponGetMon() {
		return couponGetMon;
	}

	public void setCouponGetMon(Long couponGetMon) {
		this.couponGetMon = couponGetMon;
	}

	public Long getCouponGetUserMon() {
		return couponGetUserMon;
	}

	public void setCouponGetUserMon(Long couponGetUserMon) {
		this.couponGetUserMon = couponGetUserMon;
	}

	public Long getCouponGetUnUserMon() {
		return couponGetUnUserMon;
	}

	public void setCouponGetUnUserMon(Long couponGetUnUserMon) {
		this.couponGetUnUserMon = couponGetUnUserMon;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}


}
