package com.amwell.vo.gf.temp;

import com.amwell.entity.base.BaseEntity;

/**
 * 优惠券信息迁移临时表
 */
public class CouponTemp extends BaseEntity {
	private String couponId;//varchar(35) NOT NULL主键ID
	private String link;//varchar(256) NULL链接地址
	private String couponName;//varchar(32) NULL优惠劵名称
	private String couponType;//tinyint(4) NULL推广位置 0:全部渠道 1:APP渠道 2:微信渠道
	private String sendCouponType;//tinyint(4) NULL发放方式 1:用户领取 2:系统发放
	private String selectPass;//tinyint(4) NOT NULL选择用户 0:全部用户 1:新用户 2:15日未登录用户 3:自定义用户 4.推荐有奖用户
	private String upLineTime;//varchar(19) NULL上线时间
	private String downLineTime;//varchar(19) NULL下线时间
	private String effectiveTime;//varchar(19) NULL使用的有效时间(开始)
	private String expirationTime;//varchar(19) NULL使用的有效时间(结束)
	private String issueNum;//int(10) NULL发行数量
	private String limitNum;//int(10) NULL限领数量
	private String provinceCode;//int(11) NULL省级编码
	private String cityCode;//int(11) NULL市级编码
	private String cityName;//varchar(32) NULL城市名称
	private String createBy;//varchar(35) NOT NULL创建人
	private String createOn;//varchar(19) NOT NULL创建时间
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getSendCouponType() {
		return sendCouponType;
	}
	public void setSendCouponType(String sendCouponType) {
		this.sendCouponType = sendCouponType;
	}
	public String getSelectPass() {
		return selectPass;
	}
	public void setSelectPass(String selectPass) {
		this.selectPass = selectPass;
	}
	public String getUpLineTime() {
		return upLineTime;
	}
	public void setUpLineTime(String upLineTime) {
		this.upLineTime = upLineTime;
	}
	public String getDownLineTime() {
		return downLineTime;
	}
	public void setDownLineTime(String downLineTime) {
		this.downLineTime = downLineTime;
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
	public String getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(String issueNum) {
		this.issueNum = issueNum;
	}
	public String getLimitNum() {
		return limitNum;
	}
	public void setLimitNum(String limitNum) {
		this.limitNum = limitNum;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateOn() {
		return createOn;
	}
	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
}
