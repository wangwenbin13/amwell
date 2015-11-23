package com.pig84.ab.vo.driver;

/**
 * 司机提现申请表
 */

public class DriverWithdrawAskfor implements java.io.Serializable {
	private String id;//varchar(35) NOT NULL提现申请ID
	private String driverId;//varchar(35) NOT NULL司机ID
	private String withdrawType;//tinyint(4) NULL提现方式（1.支付宝 2.微信 3.银联）
	private String withdrawAccount;//varchar(100) NULL提现账户
	private String driverName;//varchar(64) NULL司机姓名
	private String amount;//decimal(6,2) NULL提现金额
	private String askforTime;//varchar(64) NULL申请时间
	private String handleType;//tinyint(4) NULL申请处理状态（0.未处理 1.已处理）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getWithdrawType() {
		return withdrawType;
	}
	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}
	public String getWithdrawAccount() {
		return withdrawAccount;
	}
	public void setWithdrawAccount(String withdrawAccount) {
		this.withdrawAccount = withdrawAccount;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAskforTime() {
		return askforTime;
	}
	public void setAskforTime(String askforTime) {
		this.askforTime = askforTime;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
}
