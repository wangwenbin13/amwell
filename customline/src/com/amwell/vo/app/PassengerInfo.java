package com.amwell.vo.app;


/**
 * 乘客信息表
 */
@SuppressWarnings("all")
public class PassengerInfo implements java.io.Serializable {

	private String passengerId;			//乘客ID
	private String displayId;			//回显ID
	private String nickName;			//昵称
	private String sex;					//性别（0:男 1:女     ）
	private String accountStatus;		//帐号状态 0:启用 1:禁用 
	private String telephone;			//手机号码   
	private String headerPicUrl;		//乘客头像URL
	private String blackFlag;			//乘客拉黑标志 0:正常 1:黑户
	private String passWord;			//乘客密码
	private String registerTime;		//注册时间
	private String twoCodeValue;		//二维码唯一标识值
	private String twoCodePicUrl;		//二维码图片存放地址
	private String flag;				//标识（用于登录）
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getDisplayId() {
		return displayId;
	}
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getHeaderPicUrl() {
		return headerPicUrl;
	}
	public void setHeaderPicUrl(String headerPicUrl) {
		this.headerPicUrl = headerPicUrl;
	}
	public String getBlackFlag() {
		return blackFlag;
	}
	public void setBlackFlag(String blackFlag) {
		this.blackFlag = blackFlag;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getTwoCodeValue() {
		return twoCodeValue;
	}
	public void setTwoCodeValue(String twoCodeValue) {
		this.twoCodeValue = twoCodeValue;
	}
	public String getTwoCodePicUrl() {
		return twoCodePicUrl;
	}
	public void setTwoCodePicUrl(String twoCodePicUrl) {
		this.twoCodePicUrl = twoCodePicUrl;
	}

}