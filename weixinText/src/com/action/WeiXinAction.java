package com.action;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/** 
* @author 作者 : wangwenbin
* @version 创建时间：2015年11月6日 下午3:19:33 
* 类说明 
*/

@ParentPackage("no-interceptor")
@Namespace("/weixin")
public class WeiXinAction extends BaseAction{

	private String appId;
	private String nonceStr;
	private String signature;
	private String timestamp;
	private String shareUrl;
	
	@Action(value="share",results={@Result(name="success",location="../../../shareIndex.jsp")})
	public String share() {
		appId = "wx5825150f69a49413";
		Random random = new Random();
		nonceStr = MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
		timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		shareUrl = "www.baidu.com";
		return SUCCESS;
	}


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	
	
}
