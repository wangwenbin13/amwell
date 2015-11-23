package com.amwell.vo.coupon;

/**
 * 优惠券规则信息
 * @author gxt
 *
 */
public class CouponRule {
	private String id;//int(11) NOT NULL主键ID
	private String ruleName;//varchar(35) NOT NULL规则名称
	private String theCondition;//varchar(5) NOT NULL条件：gt:大于 equal:等于 lt:小于
	private String theValue;//varchar(35) NOT NULL规则内容
	private String couponGroupGrantId;//int(11) NOT NULL组合券发放ID
	private String createBy;//varchar(35) NOT NULL创建人
	private String createOn;//varchar(19) NOT NULL创建时间
	
	private String couponGroupName;//组合券名称
	private String userName;//用户名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getCouponGroupGrantId() {
		return couponGroupGrantId;
	}
	public void setCouponGroupGrantId(String couponGroupGrantId) {
		this.couponGroupGrantId = couponGroupGrantId;
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
	public String getCouponGroupName() {
		return couponGroupName;
	}
	public void setCouponGroupName(String couponGroupName) {
		this.couponGroupName = couponGroupName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTheCondition() {
		//gt:大于 equal:等于 lt:小于
		/*if("gt".equals(this.theCondition)){
			this.theCondition="大于";
		}
		else if("equal".equals(this.theCondition)){
			this.theCondition="等于";
		}
		else if("lt".equals(this.theCondition)){
			this.theCondition="小于";
		}*/
		return theCondition;
	}
	public void setTheCondition(String theCondition) {
		this.theCondition = theCondition;
	}
	public String getTheValue() {
		return theValue;
	}
	public void setTheValue(String theValue) {
		this.theValue = theValue;
	}
}
