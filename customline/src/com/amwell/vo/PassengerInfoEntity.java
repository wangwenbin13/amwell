package com.amwell.vo;


//default package

/**
 * PassengerInfo entity. @author MyEclipse Persistence Tools
 * 乘客信息表
 */

public class PassengerInfoEntity implements java.io.Serializable {
	private static final long serialVersionUID = 8985637164438817639L;
	private String passengerId;//乘客ID
	private String displayId;//乘客回显ID
	private String nickName;//昵称
	private Short sex;//性别 0:男 1:女
	private Short accountStatus;//帐号状态 0:启用 1:禁用
	private String telephone;//手机号码
	private String headerPicUrl;//乘客头像URL
	private Short blackFlag;//乘客拉黑标志 0:正常 1:黑户
	private String passWord;//乘客密码
	private String registerTime;//注册时间
	private String twoCodeValue;//二维码唯一标识值
	private String twoCodePicUrl;//二维码图片存放地址
	private Short bindStatus;//乘客类型标识：0-app注册  1-微信注册
	
	private String income;//收入总额(不做数据库字段)
	private String outlay;//支出总额(不做数据库字段)
	private String theBalance;//账户余额(不做数据库字段)
	private Integer orderCount;//订单数量(不做数据库字段)
	private Integer returnedOrderCount;//退票数量(不做数据库字段)
	private Integer changeOrderCount;//改签数量(不做数据库字段)
	
	private String cityCode;//int(11) NULL市级编码;
	
	private String terminal;//varchar(5) NULL登录设备类型（1-android 2-ios 3-微信）
	private String sourcefrom;//tinyint(4) NULL用户来源（0：小猪巴士 1：蛇口 2：彩生活）

	// Constructors

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/** default constructor */
	public PassengerInfoEntity() {
	}

	/** minimal constructor */
	public PassengerInfoEntity(String passengerId, String displayId, String nickName,
			String telephone, String passWord, String registerTime,
			String twoCodeValue) {
		this.passengerId = passengerId;
		this.displayId = displayId;
		this.nickName = nickName;
		this.telephone = telephone;
		this.passWord = passWord;
		this.registerTime = registerTime;
		this.twoCodeValue = twoCodeValue;
	}

	/** full constructor */
	public PassengerInfoEntity(String passengerId, String displayId, String nickName,
			Short sex, Short accountStatus, String telephone,
			String headerPicUrl, Short blackFlag, String passWord,
			String registerTime, String twoCodeValue, String twoCodePicUrl) {
		this.passengerId = passengerId;
		this.displayId = displayId;
		this.nickName = nickName;
		this.sex = sex;
		this.accountStatus = accountStatus;
		this.telephone = telephone;
		this.headerPicUrl = headerPicUrl;
		this.blackFlag = blackFlag;
		this.passWord = passWord;
		this.registerTime = registerTime;
		this.twoCodeValue = twoCodeValue;
		this.twoCodePicUrl = twoCodePicUrl;
	}

	// Property accessors

	public String getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getDisplayId() {
		return this.displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Short getSex() {
		return this.sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Short getAccountStatus() {
		return this.accountStatus;
	}

	public void setAccountStatus(Short accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHeaderPicUrl() {
		return this.headerPicUrl;
	}

	public void setHeaderPicUrl(String headerPicUrl) {
		this.headerPicUrl = headerPicUrl;
	}

	public Short getBlackFlag() {
		return this.blackFlag;
	}

	public void setBlackFlag(Short blackFlag) {
		this.blackFlag = blackFlag;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getTwoCodeValue() {
		return this.twoCodeValue;
	}

	public void setTwoCodeValue(String twoCodeValue) {
		this.twoCodeValue = twoCodeValue;
	}

	public String getTwoCodePicUrl() {
		return this.twoCodePicUrl;
	}

	public void setTwoCodePicUrl(String twoCodePicUrl) {
		this.twoCodePicUrl = twoCodePicUrl;
	}

	public String getTheBalance() {
		return theBalance;
	}

	public void setTheBalance(String theBalance) {
		this.theBalance = theBalance;
	}

	public Short getBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(Short bindStatus) {
		this.bindStatus = bindStatus;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getReturnedOrderCount() {
		return returnedOrderCount;
	}

	public void setReturnedOrderCount(Integer returnedOrderCount) {
		this.returnedOrderCount = returnedOrderCount;
	}

	public Integer getChangeOrderCount() {
		return changeOrderCount;
	}

	public void setChangeOrderCount(Integer changeOrderCount) {
		this.changeOrderCount = changeOrderCount;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getOutlay() {
		return outlay;
	}

	public void setOutlay(String outlay) {
		this.outlay = outlay;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getSourcefrom() {
		return sourcefrom;
	}

	public void setSourcefrom(String sourcefrom) {
		this.sourcefrom = sourcefrom;
	}
}