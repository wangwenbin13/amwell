package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

// default package

/**
 * MgrBusiness entity. @author MyEclipse Persistence Tools
 */

public class MgrBusinessEntity extends BaseEntity {

	private static final long serialVersionUID = -9046734163790723301L;
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
	private Integer provinceCode;
	/**
	 * 地区编码
	 */
	private Integer areaCode;
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

	// Constructors

	/** default constructor */
	public MgrBusinessEntity() {
	}

	/** minimal constructor */
	public MgrBusinessEntity(String businessId, String name, String brefName,
			String loginName, String loginPassword) {
		this.businessId = businessId;
		this.name = name;
		this.brefName = brefName;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
	}

	/** full constructor */
	public MgrBusinessEntity(String businessId, String name, String brefName,
			String contacts, String contactsPhone, Integer provinceCode,
			Integer areaCode, String address, String loginName,
			String loginPassword, Integer certifyStatus, Integer accountStatus,
			String remark, String createBy, String createOn, String updateBy,
			String updateOn) {
		this.businessId = businessId;
		this.name = name;
		this.brefName = brefName;
		this.contacts = contacts;
		this.contactsPhone = contactsPhone;
		this.provinceCode = provinceCode;
		this.areaCode = areaCode;
		this.address = address;
		this.loginName = loginName;
		this.loginPassword = loginPassword;
		this.certifyStatus = certifyStatus;
		this.accountStatus = accountStatus;
		this.remark = remark;
		this.createBy = createBy;
		this.createOn = createOn;
		this.updateBy = updateBy;
		this.updateOn = updateOn;
	}

	// Property accessors

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

	public Integer getProvinceCode() {
		return this.provinceCode;
	}

	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Integer getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(Integer areaCode) {
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

}