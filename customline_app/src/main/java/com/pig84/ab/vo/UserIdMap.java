package com.pig84.ab.vo;

/**
 * 用户Id映射
 * 
 * @author shawn.zheng
 *
 */
public class UserIdMap {
	/**
	 * 存储主键
	 */
	private String localId;

	/**
	 * 乘客id
	 */
	private String passengerId;

	/**
	 * 渠道号
	 */
	private String channelId;

	/**
	 * 第三方用户Id
	 */
	private String clientId;

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}
