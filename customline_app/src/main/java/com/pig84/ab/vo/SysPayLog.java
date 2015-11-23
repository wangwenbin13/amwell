package com.pig84.ab.vo;

/**
 * 下单充值记录表
 */

@SuppressWarnings("all")
public class SysPayLog implements java.io.Serializable {

	private String localId; //主键
	private String moneyLimit;//金额
	private String type;//类型（1：银联    2：支付宝    3：财付通）  
	private String passengerid;//乘客ID
	private String optime;		//充值时间
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getMoneyLimit() {
		return moneyLimit;
	}
	public void setMoneyLimit(String moneyLimit) {
		this.moneyLimit = moneyLimit;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassengerid() {
		return passengerid;
	}
	public void setPassengerid(String passengerid) {
		this.passengerid = passengerid;
	}
	public String getOptime() {
		return optime;
	}
	public void setOptime(String optime) {
		this.optime = optime;
	}

	
}
