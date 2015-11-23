package com.amwell.vo;

import org.springframework.util.StringUtils;

import com.amwell.commons.PropertyManage;

public class AdvertImageVo {
	
	private String image_id;
	
	private String imageUrl;  //ftp相对地址
	
	private String fullImageUrl;
	
	private String imageResolution;  //图片分辨率
	
	private String adConfigId;  //广告配置ID
	
	private String picTitle;  //图片显示抬头


	public String getImage_id() {
		return image_id;
	}

	public void setImage_id(String image_id) {
		this.image_id = image_id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		if(StringUtils.hasText(imageUrl)){
			String httpIp = PropertyManage.getInfoProperty("http.pic.ip");//IP地址	
			if(StringUtils.hasText(httpIp)){
				this.fullImageUrl = httpIp+imageUrl;
			}
	    }
		String[] titles = imageUrl.split("/");
		this.picTitle = titles[titles.length-1];
	}

	public String getImageResolution() {
		return imageResolution;
	}

	public void setImageResolution(String imageResolution) {
		this.imageResolution = imageResolution;
	}

	public String getAdConfigId() {
		return adConfigId;
	}

	public void setAdConfigId(String adConfigId) {
		this.adConfigId = adConfigId;
	}

	public String getFullImageUrl() {
		return fullImageUrl;
	}

	public void setFullImageUrl(String fullImageUrl) {
		this.fullImageUrl = fullImageUrl;
	}

	public String getPicTitle() {
		return picTitle;
	}

	public void setPicTitle(String picTitle) {
		this.picTitle = picTitle;
	}

}
