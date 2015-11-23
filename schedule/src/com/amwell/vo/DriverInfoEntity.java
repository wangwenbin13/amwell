package com.amwell.vo;
//default package

/**
 * DriverInfo entity. @author MyEclipse Persistence Tools
 * 司机信息表
 */

public class DriverInfoEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = -5915639493055052407L;
	private String driverId;//司机ID
	private String driverName;//司机姓名
	private String telephone;//司机电话
	private Short sex;//性别 0:男 1:女
	private String birthday;//出生年月
	private String driverNo;//驾驶证号码
	private Short driverType;//驾驶证类型 0:C1 1:B1 2:B2 3:A1 4:A2
	private String inspectionDate;//年检日期
	private String idNumber;//身份证号
	private String entryDate;//入职日期
	private Short accountStatus;//0:启用 1:禁用 2.删除
	private String passWord;//登录密码
	private String businessId;//商户ID
	private String createBy;//创建人
	private String createOn;//创建时间
	private String updateBy;//修改人
	private String updateOn;//修改时间
	
	private String businessName;//商户名称简称(冗余属性，用于列表显示，不做表字段)
	private String age;//存年龄(不做表字段)
	
	private String comments;//varchar(256) NULL开发人员备用字段

	// Constructors

	/** default constructor */
	public DriverInfoEntity() {
	}

	/** minimal constructor */
	public DriverInfoEntity(String driverId, String driverName, String telephone,
			String passWord, String businessId) {
		this.driverId = driverId;
		this.driverName = driverName;
		this.telephone = telephone;
		this.passWord = passWord;
		this.businessId = businessId;
	}

	/** full constructor */
	public DriverInfoEntity(String driverId, String driverName, String telephone,
			Short sex, String birthday, String driverNo, Short driverType,
			String inspectionDate, String idNumber, String entryDate,
			Short accountStatus, String passWord, String businessId,
			String createBy, String createOn, String updateBy, String updateOn) {
		this.driverId = driverId;
		this.driverName = driverName;
		this.telephone = telephone;
		this.sex = sex;
		this.birthday = birthday;
		this.driverNo = driverNo;
		this.driverType = driverType;
		this.inspectionDate = inspectionDate;
		this.idNumber = idNumber;
		this.entryDate = entryDate;
		this.accountStatus = accountStatus;
		this.passWord = passWord;
		this.businessId = businessId;
		this.createBy = createBy;
		this.createOn = createOn;
		this.updateBy = updateBy;
		this.updateOn = updateOn;
	}

	// Property accessors

	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return this.driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Short getSex() {
		return this.sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDriverNo() {
		return this.driverNo;
	}

	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}

	public Short getDriverType() {
		return this.driverType;
	}

	public void setDriverType(Short driverType) {
		this.driverType = driverType;
	}

	public String getInspectionDate() {
		return this.inspectionDate;
	}

	public void setInspectionDate(String inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getEntryDate() {
		return this.entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public Short getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(Short accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
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

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}