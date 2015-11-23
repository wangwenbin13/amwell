package com.pig84.ab.vo.driver;

/**
 * 司机账户明细表
 */

public class DriverAccountDetail implements java.io.Serializable {
	private String id;//varchar(35) NOT NULL账户明细ID
	private String driverId;//varchar(35) NOT NULL司机ID
	private String handleType;//tinyint(4) NULL操作类型（1.收入 2.提现）
	private String incomeType;//tinyint(4) NULL收入来源（1.活动奖励 2.点赞奖励）
	private String withdrawType;//tinyint(4) NULL提现方式（1.支付宝 2.微信 3.银联）
	private String amount;//decimal(6,2) NULL金额
	private String handleTime;//varchar(64) NULL操作时间
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
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	public String getWithdrawType() {
		return withdrawType;
	}
	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}
}
