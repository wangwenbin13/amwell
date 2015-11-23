package com.utils.WeiXinUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessTokenRequestHandler extends RequestHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessTokenRequestHandler.class);

	private static String access_token = "";
	
	private AccessTokenRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	/**
	 * 获取凭证access_token
	 * @return
	 */
	public static String getAccessToken() {
		String requestUrl = WeiXinPayConfig.KEY_TOKENURL + "?grant_type=" + WeiXinPayConfig.KEY_GRANT_TYPE + "&appid="
				+ WeiXinPayConfig.KEY_APP_ID + "&secret=" + WeiXinPayConfig.KEY_APP_SECRET;
		String resContent = "";
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setMethod("GET");
		httpClient.setReqContent(requestUrl);
		if (httpClient.call()) {
			resContent = httpClient.getResContent();
			if (resContent.indexOf(WeiXinPayConfig.KEY_ACCESS_TOKEN) > 0) {
				access_token = JsonUtil.getJsonValue(resContent, WeiXinPayConfig.KEY_ACCESS_TOKEN);
			} else {
				logger.warn("获取access_token值返回错误: {}", requestUrl);
			}
		} else {
			logger.warn("后台调用通信失败: responseCode={}, errInfo={}", httpClient.getResponseCode(), httpClient.getErrInfo());
			// 有可能因为网络原因，请求已经处理，但未收到应答。
		}

		return access_token;
	}

}
