package com.amwell.vo;

import org.springframework.util.StringUtils;

import com.amwell.commons.PropertyManage;
import com.amwell.entity.base.BaseEntity;

public class AdvertManageVo extends BaseEntity {

	private static final long serialVersionUID = -5028695639432756602L;

	private String ad_id;

	private int clientType; // 客户端类型 1:andriod 2:ios 3:anroid+ios

	private String versionNO; // 版本ID，对应app_version的主健ID

	private String effectiveTime; // 生效时间,java时间格式：yyyy-MM-dd HH:mm

	private String expirationTime; // 过期时间,java时间格式：yyyy-MM-dd HH:mm

	private String urlLink;

	private String operateBy;

	private String operateOn;

	private int adStatus; // 0:无效 1:有效 2:已过期 3:已删除

	private String thumbnail; // 缩略图
	
	private String fullThumbnail;

	private String userName;
	
	private String adTitle;
	
	private int urlSign;

	public String getAd_id() {
		return ad_id;
	}

	public void setAd_id(String ad_id) {
		this.ad_id = ad_id;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public String getVersionNO() {
		return versionNO;
	}

	public void setVersionNO(String versionNO) {
		this.versionNO = versionNO;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}

	public String getOperateBy() {
		return operateBy;
	}

	public void setOperateBy(String operateBy) {
		this.operateBy = operateBy;
	}

	public String getOperateOn() {
		return operateOn;
	}

	public void setOperateOn(String operateOn) {
		this.operateOn = operateOn;
	}

	public int getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(int adStatus) {
		this.adStatus = adStatus;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
	    this.thumbnail =thumbnail;
	    if(StringUtils.hasText(thumbnail)){
	    	String httpIp = PropertyManage.getInfoProperty("http.pic.ip");//IP地址	
			if(StringUtils.hasText(httpIp)){
				this.fullThumbnail = httpIp+thumbnail;
			}
	    }
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullThumbnail() {
		return fullThumbnail;
	}

	public void setFullThumbnail(String fullThumbnail) {
		this.fullThumbnail = fullThumbnail;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public int getUrlSign() {
		return urlSign;
	}

	public void setUrlSign(int urlSign) {
		this.urlSign = urlSign;
	}

	
}
