package com.pig84.ab.weixin.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.pig84.ab.weixin.client.TenpayHttpClient;
import com.pig84.ab.weixin.conf.WeiXinPayConfig;
import com.pig84.ab.weixin.util.JsonUtil;
import com.pig84.ab.weixin.util.MD5Util;
import com.pig84.ab.weixin.util.Sha1Util;

public class PrepayIdRequestHandler extends RequestHandler {

	public PrepayIdRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	/**
	 * 创建签名SHA1
	 * 
	 * @param signParams
	 * @return
	 * @throws Exception
	 */
	public String createSHA1Sign() {
		StringBuffer sb = new StringBuffer();
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));
		String appsign = Sha1Util.getSha1(params);
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "sha1 sb:" + params);
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "app sign:" + appsign);
		return appsign;
	}

	// 提交预支付
	public String sendPrepay() throws JSONException {
		String prepayid = "";
		StringBuffer sb = new StringBuffer("{");
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"appkey".equals(k)) {
				sb.append("\"" + k + "\":\"" + v + "\",");
			}
		}
		String params = sb.substring(0, sb.lastIndexOf(","));
		params += "}";

		String requestUrl = super.getGateUrl();
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "requestUrl:"
				+ requestUrl);
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
		if (httpClient.callHttpPost(requestUrl, params)) {
			resContent = httpClient.getResContent();
			if (2 == resContent.indexOf("prepayid")) {
				prepayid = JsonUtil.getJsonValue(resContent, "prepayid");
			}
			this.setDebugInfo(this.getDebugInfo() + "\r\n" + "resContent:"
					+ resContent);
		}
		return prepayid;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	public String createSignMd5() {
		StringBuffer sb = new StringBuffer();
		Set<?> es = super.getAllParameters().entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key="+WeiXinPayConfig.KEY_PARTNER_KEY);
//		sb.append("key="+WeiXinPayConfig.KEY_APP_KEY);
//		String str = sb.toString();
//		try {
//			str = new String(str.getBytes(), "ISO-8859-1");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
//		String sign = MD5Util.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return sign;

	}
	/**
	 * 第二次签名
	 */
	public String secondCreateSignMd5() {
		StringBuffer sb = new StringBuffer();
		Set<?> es = super.getAllParameters().entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key="+WeiXinPayConfig.KEY_PARTNER_KEY);
		String str = "sign str\n"+sb.toString()+"\n\n";
		String sign = MD5Util.MD5Encode(str,null).toUpperCase();
		return sign;
		
	}
}
