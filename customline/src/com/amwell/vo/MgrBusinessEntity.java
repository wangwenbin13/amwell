package com.amwell.vo;
// default package

/**
 * MgrBusiness entity. @author MyEclipse Persistence Tools
 */

public class MgrBusinessEntity implements java.io.Serializable {

	// Fields

	private String businessId;
	/**
	 * 商户名称
	 */
	private String name;
	/**
	 * 商户简称
	 */
	private String brefName;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系电话
	 */
	private String contactsPhone;
	/**
	 * 省份编码
	 */
	private String provinceCode;
	/**
	 * 地区编码
	 */
	private String areaCode;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 登录密码
	 */
	private String loginPassword;
	/**
	 * 认证状态
	 * 0:已认证    1:未认证   
	 */
	private Integer certifyStatus;
	/**
	 * 帐号状态
	 * 0:启用     1:禁用
	 */
	private Integer accountStatus;
	private String accounStatu;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建人  存ID
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private String createOn;
	/**
	 * 修改人 存ID
	 */
	private String updateBy;
	/**
	 * 修改时间
	 */
	private String updateOn;
	
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 业务类型(1.上下班 2.包车 3.两者都支持)
	 */
	private Integer businessType;
	


	/** default constructor */
	public MgrBusinessEntity() {
	}


	public String getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrefName() {
		return this.brefName;
	}

	public void setBrefName(String brefName) {
		this.brefName = brefName;
	}

	public String getContacts() {
		return this.contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return this.contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}


	public Integer getCertifyStatus() {
		return certifyStatus;
	}

	public void setCertifyStatus(Integer certifyStatus) {
		this.certifyStatus = certifyStatus;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateOn() {
		return this.updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

	public String getAccounStatu() {
		return accounStatu;
	}

	public void setAccounStatu(String accounStatu) {
		this.accounStatu = accounStatu;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

}