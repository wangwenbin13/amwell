package com.bean.games.fcz;

/**
 * 复仇者token
 * 
 * @author shawn_zheng
 * 
 */
public class FczAccessToken {
	/**
	 * 存储Id
	 */
	private String storeId;

	/**
	 * access_token
	 */
	private String access_token;

	/**
	 * js_api_token
	 */
	private String js_api_token;

	private long updateTime;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}

	public String getJs_api_token() {
		return js_api_token;
	}

	public void setJs_api_token(String jsApiToken) {
		js_api_token = jsApiToken;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

}
