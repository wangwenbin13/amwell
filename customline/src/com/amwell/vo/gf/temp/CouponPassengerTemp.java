package com.amwell.vo.gf.temp;

import com.amwell.entity.base.BaseEntity;

/**
 * 优惠券信息迁移临时表
 */
public class CouponPassengerTemp extends BaseEntity {
	private String counponTeleId;//varchar(35) NOT NULL主键ID
	private String passengerId;//varchar(35) NOT NULL用户Id
	private String telephone;//varchar(16) NOT NULL用户电话号码
	private String couponGiftId;//varchar(35) NOT NULL优惠券礼品的主键ID
	private String getState;//tinyint(4) NULL0:未领取 1：已领取
	private String useState;//tinyint(4) NULL使用状态 0:未使用 1:已使用 2:已过期
	private String theModule;//tinyint(4) NULL所属模块(1.上下班 2.包车 )
	private String orderId;//varchar(35) NULL订单ID
	private String userTime;//varchar(19) NULL使用时间
	private String lookState;//tinyint(4) NULL查看状态：0.未查看 1.已查看
	private String getTime;//varchar(19) NULL领取时间
	public String getCounponTeleId() {
		return counponTeleId;
	}
	public void setCounponTeleId(String counponTeleId) {
		this.counponTeleId = counponTeleId;
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
	public String getCouponGiftId() {
		return couponGiftId;
	}
	public void setCouponGiftId(String couponGiftId) {
		this.couponGiftId = couponGiftId;
	}
	public String getGetState() {
		return getState;
	}
	public void setGetState(String getState) {
		this.getState = getState;
	}
	public String getUseState() {
		return useState;
	}
	public void setUseState(String useState) {
		this.useState = useState;
	}
	public String getTheModule() {
		return theModule;
	}
	public void setTheModule(String theModule) {
		this.theModule = theModule;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserTime() {
		return userTime;
	}
	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}
	public String getLookState() {
		return lookState;
	}
	public void setLookState(String lookState) {
		this.lookState = lookState;
	}
	public String getGetTime() {
		return getTime;
	}
	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}
}
