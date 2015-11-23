package com.amwell.vo;
/**
 * 发送类型和短信商家信息表
 * @author Administrator
 *
 */
public class SysAppSend {
	private String id;//varchar(32) NOT NULL
	private String sendType;//tinyint(4) NULL发送类型：1.短信发送 2.站内发送
	private String business;//tinyint(2) NULL商家类型：1.亿美 2：国都
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
}
