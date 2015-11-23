package com.pig84.ab.vo;

/**
 * 消息信息表
 * @author Administrator
 *
 */
@SuppressWarnings("all")
public class SysMsgInfo implements java.io.Serializable{

	private String msgId;
	private String theModule;//所属模块（1.小猪巴士 2.包车）
	private String msgType;
	private String msgSubType;
	private String msgTitle;
	private String msgContext;
	private String msgTarget;
	private String createBy;
	private String createOn;
	private String updateBy;
	private String updateOn;
	private String initTime;
	private String issend;
	private String picUrl;
	
	
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgSubType() {
		return msgSubType;
	}
	public void setMsgSubType(String msgSubType) {
		this.msgSubType = msgSubType;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgContext() {
		return msgContext;
	}
	public void setMsgContext(String msgContext) {
		this.msgContext = msgContext;
	}
	public String getMsgTarget() {
		return msgTarget;
	}
	public void setMsgTarget(String msgTarget) {
		this.msgTarget = msgTarget;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
	public String getInitTime() {
		return initTime;
	}
	public void setInitTime(String initTime) {
		this.initTime = initTime;
	}
	public String getIssend() {
		return issend;
	}
	public void setIssend(String issend) {
		this.issend = issend;
	}
	public String getTheModule() {
		return theModule;
	}
	public void setTheModule(String theModule) {
		this.theModule = theModule;
	}
}
