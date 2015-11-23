package com.amwell.vo;

/**
 * 短信消息管理
 *
 */
public class MarketingVo {
	
	
	private String msgId;//消息ID
	
	private Integer theModule;//所属模块（1.小猪巴士 2.包车）
	                
	private int msgType;//消息类型：0.短信消息  1.软件消息
	
	private int msgSubType;//消息类型子分类：软件消息：0.待定（未做） 短信消息：0.系统消息，1.人工发送
	
	private String msgTiTLE;//信息标题
	
	private String msgContext;//信息内容
	
	private int msgTarget;//用户类型：0.乘客 1.司机 2.商家
	
	private String initTime;//定时发送时间
	
	private String issend;//发送状态（0：已发送 1：未发送）
	
	private String orderDate;//乘车日期
	
	private String orderStartTime;//发车时间
	
	private String lineName;//线路名称
	
	private String picUrl;//附带图片Url
	
	
	private String createBy;
	
	private String createOn;
	
	private String updateBy;
	
	private String updateOn;
	
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getMsgSubType() {
		return msgSubType;
	}

	public void setMsgSubType(int msgSubType) {
		this.msgSubType = msgSubType;
	}

	public String getMsgTiTLE() {
		return msgTiTLE;
	}

	public void setMsgTiTLE(String msgTiTLE) {
		this.msgTiTLE = msgTiTLE;
	}

	public String getMsgContext() {
		return msgContext;
	}

	public void setMsgContext(String msgContext) {
		this.msgContext = msgContext;
	}

	public int getMsgTarget() {
		return msgTarget;
	}

	public void setMsgTarget(int msgTarget) {
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

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getTheModule() {
		return theModule;
	}

	public void setTheModule(Integer theModule) {
		this.theModule = theModule;
	}

	

}
