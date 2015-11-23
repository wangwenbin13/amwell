package com.action.games;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bean.games.fcz.FczOwner;
import com.bean.games.fcz.FczSetting;
import com.service.games.fcz.FczService;
import com.service.v1.UserService;
import com.util.common.MyDate;
import com.util.common.PostHttpClient;
import com.util.common.StringUtil;
import com.util.constants.PropertiesConfig;
import com.util.weixin.Sha1Util;
import com.util.weixin.WeixinConstants;
import com.util.weixin.WeixinMenuUtil;

import net.sf.json.JSONObject;

/**
 * 打boss
 * 
 * @author shawn.zheng
 */
@RequestMapping("/beatboss")
@Controller
public class Beatboss {

	private static Logger log = Logger.getLogger(Beatboss.class.getName());

	@Autowired
	private UserService userService;

	@Autowired
	private FczService fczService;

	private static String INDEX_URL;

	private static FczAccessToken fczAccessToken;

	static {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(PropertiesConfig.SERVER_IP).append(PropertiesConfig.PROJECT_NAME);
			builder.append("/beatboss/index");
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
		ModelAndView mv = new ModelAndView("beatboss/index");
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

	/**
	 * 判断可玩性
	 */
	@RequestMapping("/available")
	@ResponseBody
	public String available(String openId) {
		log.info("\n--->available");
		log.info("openId=" + openId);
		FczSetting setting = fczService.getSetting(new FczSetting());
		long nowTime = System.currentTimeMillis();
		long startTime = MyDate.strToUTCLong(setting.getStartTime(), "yyyy-MM-dd");
		long endTime = MyDate.strToUTCLong(setting.getEndTime(), "yyyy-MM-dd");
		if (nowTime < startTime || nowTime > endTime) {
			return "overtime";
		}
		FczOwner owner = fczService.getOwnerByOpenId(openId);
		if (owner == null) {
			return "available";
		}
		if (owner.getAvailablePlayNum() <= 0) {
			return "overnum";
		}
		return "available";
	}

	/**
	 * 页面2（游戏）
	 */
	@RequestMapping("/game")
	public ModelAndView game(String openId, HttpServletRequest request) {
		log.info("\n--->game");
		log.info("openId=" + openId);
		ModelAndView mv = new ModelAndView("beatboss/game");
		mv.addObject("openId", openId);
		FczOwner owner = fczService.getOwnerByOpenId(openId);
		if (owner != null && owner.getAvailablePlayNum() <= 0) {
			mv = new ModelAndView("beatboss/index");
		}
		shareJsParam(request, mv);
		return mv;
	}

	@RequestMapping(value = "/owner", method = RequestMethod.POST)
	@ResponseBody
	public void owner(String totalPoint, String openId, HttpSession session) {
		log.info("\n--->owner");
		log.info("openId=" + openId);
		log.info("totalPoint=" + totalPoint);
		FczOwner owner = fczService.getOwnerByOpenId(openId);
		int nowPoint = Integer.parseInt(totalPoint);
		if (owner == null) {
			owner = new FczOwner();
			owner.setAvailablePlayNum(1);
			String nickname = (String) session.getAttribute("nickname");
			log.info("nickname=" + nickname);
			owner.setNickname(nickname);
			owner.setIsShare("0");
			owner.setStoreId(StringUtil.generateSequenceNo());
			owner.setTotalPoint(nowPoint);
			owner.setOpenId(openId);
			fczService.addOwner(owner);
		} else {
			int availablePlayNum = owner.getAvailablePlayNum();
			log.info("availablePlayNum=" + availablePlayNum);
			if (availablePlayNum <= 0) {
				return;
			}
			owner.setAvailablePlayNum(availablePlayNum - 1);
			int oldTotalPoint = owner.getTotalPoint();
			log.info("oldTotalPoint=" + oldTotalPoint);
			if (oldTotalPoint < nowPoint) {
				owner.setTotalPoint(nowPoint);
			}
			fczService.updateOwner(owner);
		}
	}

	/**
	 * 第三个页面(结果页)
	 */
	@RequestMapping(value = "/result")
	public ModelAndView result(String openId, HttpServletRequest request) {
		log.info("\n--->result");
		log.info("openId=" + openId);
		ModelAndView mv = new ModelAndView("beatboss/result");
		mv.addObject("openId", openId);
		FczOwner owner = fczService.getOwnerByOpenId(openId);
		mv.addObject("owner", owner);
		List<FczOwner> list = fczService.listOwner(new FczOwner());
		mv.addObject("owners", list);
		int totalCount = fczService.countAllOwner();
		log.info("totalCount=" + totalCount);
		int lessCount = fczService.countOwnerByCondition(owner.getTotalPoint());
		Double persent = Double.valueOf(((lessCount * 10) / totalCount));
		log.info("persent=" + persent.intValue());
		if (totalCount == 0) {
			mv.addObject("beyond", 0);
		} else {
			mv.addObject("beyond", persent.intValue() + 90);
		}
		shareJsParam(request, mv);
		return mv;
	}

	@RequestMapping(value = "/getReward", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String getReward(String openId) {
		log.info("\n--->getReward");
		log.info("openId=" + openId);
		JSONObject user = userService.getByOpenId(UserService.WEIXIN_CHANNEL, openId);
		if (user == null || !user.containsKey("a6") || StringUtils.isEmpty(user.getString("a6"))) {
			return "nologin";
		}
		FczOwner owner = fczService.getOwnerByOpenId(openId);
		String result = "";
		if (owner == null) {
			return "";
		}
		String prideName = owner.getPrideName();
		if (!StringUtils.isEmpty(prideName)) {
			return prideName;
		}
		int totalPoint = owner.getTotalPoint();
		log.info("totalPoint=" + totalPoint);
		if (totalPoint > 300 && totalPoint < 400) {
			result = "小猪巴士5元代金券";
			owner.setPrideName(result);
			owner.setTelephone(user.getString("a6"));
			owner.setAvailablePlayNum(0);
		} else if (totalPoint > 400) {
			result = "小猪巴士10元代金券";
			owner.setPrideName(result);
			owner.setAvailablePlayNum(0);
			owner.setTelephone(user.getString("a6"));
		}
		fczService.updateOwner(owner);
		return result;
	}

	@RequestMapping(value = "/addAvailableNum")
	@ResponseBody
	public String addAvailableNum(String openId) {
		log.info("\n--->addAvailableNum");
		log.info("openId=" + openId);
		String result = "";
		FczOwner owner = fczService.getOwnerByOpenId(openId);
		if (owner == null) {
			return result;
		}
		String prideName = owner.getPrideName();
		log.info("prideName=" + prideName);
		if (!StringUtils.isEmpty(prideName)) {
			return result;
		}
		String isShare = owner.getIsShare();
		log.info("isShare=" + isShare);
		if (StringUtils.isEmpty(isShare)) {
			owner.setAvailablePlayNum(owner.getAvailablePlayNum() + 1);
			owner.setIsShare("1");
			fczService.updateOwner(owner);
			result = "success";
		} else {
			Integer shareNum = Integer.parseInt(isShare);
			owner.setAvailablePlayNum(owner.getAvailablePlayNum() + 1);
			owner.setIsShare(String.valueOf(shareNum + 1));
			fczService.updateOwner(owner);
			result = "success";
		}
		return result;
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

	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public ModelAndView setting() {
		ModelAndView mv = new ModelAndView("beatboss/setting");
		FczSetting setting = fczService.getSetting(null);
		mv.addObject("setting", setting);
		return mv;
	}

	@RequestMapping(value = "/setting", method = RequestMethod.POST)
	@ResponseBody
	public String setting(FczSetting fczSetting) {
		fczService.saveSetting(fczSetting);
		return "success";
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
