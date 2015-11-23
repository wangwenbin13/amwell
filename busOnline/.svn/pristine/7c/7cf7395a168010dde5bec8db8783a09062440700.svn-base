package com.action.games;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.service.v1.UserService;
import com.util.common.PostHttpClient;
import com.util.constants.PropertiesConfig;
import com.util.weixin.Sha1Util;
import com.util.weixin.WeixinConstants;
import com.util.weixin.WeixinMenuUtil;

import net.sf.json.JSONObject;

@RequestMapping("/lonelyTest")
@Controller
public class LonelyTest {
	
	private static Logger log = Logger.getLogger(Beatboss.class.getName());
	
	@Autowired
	private UserService userService;
	
	private static String INDEX_URL;
	
	private static FczAccessToken fczAccessToken;
	
	static {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(PropertiesConfig.SERVER_IP).append(PropertiesConfig.PROJECT_NAME);
			builder.append("/lonelyTest/index");
			INDEX_URL = WeixinMenuUtil.getWeixinMenuDirectURLWithUserInfo(builder.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@RequestMapping("/indexUrl")
	@ResponseBody
	public String indexUrl() {
		return INDEX_URL;
	}
	
	/**
	 * 页面1（活动说明）
	 */
	@RequestMapping("/index")
	public ModelAndView index(String openId, String code, HttpServletRequest request) {
		log.info("\n--->index");
		log.info("openId=" + openId);
		log.info("code=" + code);
		ModelAndView mv = new ModelAndView("lonelytest/index");
		Map<String, String> user = null;
		HttpSession session = request.getSession();

		try {
			if (StringUtils.isEmpty(openId)) {
				openId = (String) session.getAttribute("openId");
				log.info("openId in session =" + openId);
				if (StringUtils.isEmpty(openId)) {
					user = userService.loadUserFullInfoFromWeChat(code);
					session.setAttribute("nickname", user.get("nickname"));
					openId = user.get("openId");
					log.info("openId=" + openId);
					session.setAttribute("openId", openId);
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		mv.addObject("openId", openId);
		shareJsParam(request, mv);
		return mv;
	}
	
	private void shareJsParam(HttpServletRequest request, ModelAndView mv) {
		String referUrl = null;
		referUrl = request.getRequestURL().toString();
		if (!StringUtils.isEmpty(request.getQueryString())) {
			referUrl = referUrl + "?" + request.getQueryString();
		}
		WeixinJsParam weixinJsParam = createWeixinJsParam(referUrl);
		mv.addObject("nonceStr", weixinJsParam.getNonceStr());
		mv.addObject("signature", weixinJsParam.getSignature());
		mv.addObject("timestamp", weixinJsParam.getTimestamp());
		mv.addObject("shareUrl", INDEX_URL);
	}
	
	public WeixinJsParam createWeixinJsParam(String referUrl) {
		WeixinJsParam weixinJsParam = null;
		if (fczAccessToken == null) {
			getTokenFromWechat();
		} else {
			if ((System.currentTimeMillis() - fczAccessToken.getUpdateTime()) > 7000000) {
				getTokenFromWechat();
			}
		}
		weixinJsParam = generateWeixinJsParam(fczAccessToken.getJs_api_token(), referUrl);
		return weixinJsParam;
	}
	
	private WeixinJsParam generateWeixinJsParam(String ticket, String referUrl) {
		String noncestr = Sha1Util.getNonceStr();
		String timestamp = Sha1Util.getTimeStamp();
		String message = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ referUrl;
		String signature = Sha1Util.getSha1(message);
		return new WeixinJsParam(timestamp, noncestr, signature);
	}
	
	private String getTokenFromWechat() {
		String access_token = null;
		String url = WeixinConstants.WEIXIN_GET_ACCESS_TOKEN_URL;
		String result = PostHttpClient.getHttpReq(url);
		if (!StringUtils.isEmpty(result)) {
			JSONObject jsonObject = JSONObject.fromObject(result);
			if (jsonObject.containsKey("access_token")) {
				access_token = jsonObject.getString("access_token");
				String GET_JS_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
				url = GET_JS_TICKET + access_token;
				String resultJSON = PostHttpClient.getHttpReq(url);
				JSONObject resultJsonObj = JSONObject.fromObject(resultJSON);
				String ticket = null;
				if (resultJsonObj.containsKey("ticket")) {
					ticket = resultJsonObj.getString("ticket");
				}
				if (access_token != null) {
					fczAccessToken = new FczAccessToken(access_token, ticket, System.currentTimeMillis());
				}
			}
		}
		return access_token;
	}

	public class WeixinJsParam implements Serializable {
		private static final long serialVersionUID = 5546842115983838401L;

		private String timestamp;

		private String nonceStr;

		private String signature;

		public WeixinJsParam() {
		}

		public WeixinJsParam(String timestamp, String nonceStr, String signature) {
			this.timestamp = timestamp;
			this.nonceStr = nonceStr;
			this.signature = signature;
		}

		public String getTimestamp() {
			return timestamp;
		}

		public String getNonceStr() {
			return nonceStr;
		}

		public String getSignature() {
			return signature;
		}

	}

	public class FczAccessToken {

		private String access_token;

		private String js_api_token;

		private long updateTime;

		public FczAccessToken(String access_token, String js_api_token, long updateTime) {
			this.access_token = access_token;
			this.js_api_token = js_api_token;
			this.updateTime = updateTime;
		}

		public String getAccess_token() {
			return access_token;
		}

		public String getJs_api_token() {
			return js_api_token;
		}

		public long getUpdateTime() {
			return updateTime;
		}

	}

}
