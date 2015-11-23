package com.pig84.ab.vo.coupon;

/**
 * 用户优惠券详情信息
 * @author gxt
 *
 */
public class CouponGroupPassengerDetail {
	private String id;//int(11) NOT NULL主键ID
	private String passengerId;//varchar(35) NOT NULL用户Id
	private String grantId;//int(11) NOT NULL发放Id
	private String couponGroupId;//int(11) NOT NULL组合券批次号
	private String couponId;//int(11) NOT NULL优惠券Id
	private String getTime;//varchar(19) NULL领取时间
	private String effectiveTime;//varchar(19) NULL使用的有效时间(开始)
	private String expirationTime;//varchar(19) NULL使用的有效时间(结束)
	private String useState;//varchar(7) NULL使用状态 unused:未使用 used:已使用 expired:已过期
	private String orderId;//varchar(35) NULL订单ID
	private String useTime;//varchar(19) NULL使用时间
	
	private String telephone;//用户电话
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getGrantId() {
		return grantId;
	}
	public void setGrantId(String grantId) {
		this.grantId = grantId;
	}
	public String getCouponGroupId() {
		return couponGroupId;
	}
	public void setCouponGroupId(String couponGroupId) {
		this.couponGroupId = couponGroupId;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
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
	public String getUseState() {
		return useState;
	}
	public void setUseState(String useState) {
		this.useState = useState;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
