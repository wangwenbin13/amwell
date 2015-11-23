package com.pig84.ab.vo;

/**
 * app版本表
 */

@SuppressWarnings("all")
public class AppVersion implements java.io.Serializable {

	private String appId;
	private String vsn;
	private String type;
	private String url;
	private String vtime;
	private String flag;
	private String ismust;
	private String info;
	
	
	
	public String getIsmust() {
		return ismust;
	}
	public void setIsmust(String ismust) {
		this.ismust = ismust;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getVtime() {
		return vtime;
	}
	public void setVtime(String vtime) {
		this.vtime = vtime;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getVsn() {
		return vsn;
	}
	public void setVsn(String vsn) {
		this.vsn = vsn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
