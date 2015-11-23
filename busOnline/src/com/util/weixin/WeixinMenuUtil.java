package com.util.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.util.constants.PropertiesConfig;
import com.util.constants.WebServiceConstants;

/**
 * 微信菜单工具类
 * 
 * @author shawn.zheng
 */
public class WeixinMenuUtil {
	private static Logger log = Logger
			.getLogger(WeixinMenuUtil.class.getName());

	/** 拼车 **/
	public static final String REDIRECT_PIN_CHE = "pinChe";
	// 上下班
	public static final String REDIRECT_SHANG_XIA_BAN = "shangXiaBan";
	// 自由行
	public static final String REDIRECT_ZI_YOU_XING = "ziYouXing";
	// 申请专线
	public static final String REDIRECT_REQUEST_LINE = "requestLine";
	// 当前车票
	public static final String REDIRECT_CURRENT_ORDER_LIST = "currentOrderList";
	// 历史车票
	public static final String REDIRECT_HISTORY_ORDER_LIST = "historyOrderList";
	// 手机绑定
	public static final String REDIRECT_BIND_MOBILE = "bindMobile";
	// 帮助中心
	public static final String REDIRECT_HELP_CENTER = "helpCenter";
	// 小猪社区
	public static final String REDIRECT_CLUB = "club";
	// 优惠活动
	public static final String REDIRECT_YOUHUI = "youhui";
	// 发起新线路
	public static final String APPLY_NEW_LINE = "applyNewLine";
	// 跳转到报名页面
	public static final String GO_SIGN_UP = "goSignUp";

	public static final String APPLY_REDIRECT1 = "applyRedirect1";
	public static final String APPLY_REDIRECT2 = "applyRedirect2";
	public static final String APPLY_REDIRECT3 = "applyRedirect3";
	public static final String APPLY_REDIRECT4 = "applyRedirect4";

	public static JSONObject createMenuJSON()
			throws UnsupportedEncodingException {
		JSONObject button = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject button1 = createMainButton1();
		JSONObject button2 = createMainButton2();
		JSONObject button3 = createMainButton3();
		array.add(button1);
		array.add(button3);
		array.add(button2);
		button.put("button", array);
		return button;
	}

	/**
	 * 创建第一个主按钮
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static JSONObject createMainButton1()
			throws UnsupportedEncodingException {
		JSONObject button = new JSONObject();
		/*
		button.put("name", "我要坐车");
		JSONArray subArray = new JSONArray();
		JSONObject subButton1 = createSingleButton("搭伴坐车",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/pinCheAction/toPinCheSearch"));
		JSONObject subButton2 = createSingleButton("上下班巴士",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/lineBaseInfo/list4Weixin?lineSubType=0"));
		JSONObject subButton3 = createSingleButton("我要包车",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/charteredLineAction/toBaoCheFirst"));
		JSONObject subButton4 = createSingleButton("待开通班车",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/lineBaseInfo/requestLine"));
		subArray.add(subButton1);
		subArray.add(subButton3);
		subArray.add(subButton2);
		subArray.add(subButton4);
		button.put("sub_button", subArray);
		*/
		// button = createSingleButton("我要坐车", getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/lineBaseInfo/list4Weixin?lineSubType=0"));
		button = createSingleButton("我要坐车", getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/line/workday"));
		return button;
	}

	/**
	 * 创建第二主按钮
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static JSONObject createMainButton2()
			throws UnsupportedEncodingException {
		JSONObject button = new JSONObject();
		button = createSingleButton("我的票", getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/user/home"));
		
		// JSONObject subButton1 = createSingleButton("小猪春运行", getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + "liveApp/ticket/index")); 
		// JSONObject subButton2 = createSingleButton("发送消息", getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/charteredLineAction/sendInfo"));
/*
		JSONObject subButton1 = createSingleButton("会员登录",
				getWeixinMenuURL(REDIRECT_BIND_MOBILE));

		JSONObject subButton2 = createSingleButton("我的车票",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP
						+ PropertiesConfig.PROJECT_NAME + "/myTicketAction/myTicketList"));

		JSONObject subButton3 = createSingleButton("巴士订单",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP
						+ PropertiesConfig.PROJECT_NAME + "/leaseBaseInfo/list4Weixin?checkType=1"));

		JSONObject subButton4 = createSingleButton("包车订单",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP
						+ PropertiesConfig.PROJECT_NAME + "/charteredLineListAction/toMyBc"));

		JSONObject subButton5 = createSingleButton("我的优惠券",
				getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP
						+ PropertiesConfig.PROJECT_NAME + "/coupon/couponList"));

		JSONArray subArray = new JSONArray();
		subArray.add(subButton1);
		subArray.add(subButton2);
		subArray.add(subButton3);
		subArray.add(subButton4);
		subArray.add(subButton5);
		button.put("sub_button", subArray);
		*/
		return button;
	}

	/**
	 * 创建第三个主按钮
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static JSONObject createMainButton3()
			throws UnsupportedEncodingException {
		JSONObject button = new JSONObject();
		button.put("name", "互动中心");
		JSONArray subArray = new JSONArray();
		JSONObject subButton1 = createSingleButton("争做巴主", "http://mp.weixin.qq.com/s?__biz=MzA4NjMwNDIzNA==&mid=205924023&idx=1&sn=e2a517b0676d64d2d2ca8b56728b74ab&scene=1&key=c468684b929d2be2e73912438d4b6dbd592ab8f3ec568383513848d9649e3fdcee98c5e0e4ad0aa6db90bdd9b083c252&ascene=1&uin=MTk1MTc1NDk1&devicetype=webwx&version=70000001&pass_ticket=CpDY%2BMbvcPO8SSMX5xJxGTe%2Boi%2B%2BZfteOCcCcmd%2BBEQ%3D");
		JSONObject subButton2 = createSingleButton("梦幻巴士", "http://mp.weixin.qq.com/s?__biz=MzA4NjMwNDIzNA==&mid=207143657&idx=1&sn=ae473ccd1e9be2d9d82f2bd3ca0a63dd#rd");
		// JSONObject subButton3 = createSingleButton("小猪社区", "http://bbs.zuchezaixian.com/plugin.php?id=wechat:access");
		JSONObject subButton4 = createClickButton("下载APP", "downloadApp");
		JSONObject subButton5 = createClickButton("帮助中心", "helpCenter");
		// JSONObject subButton6 = createSingleButton("谍中谍", getWeixinMenuDirectURL(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/games/impossible/index"));
		JSONObject subButton7 = createSingleButton("孤独测试", getWeixinMenuDirectURLWithUserInfo(PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/lonelyTest/index"));
		
		subArray.add(subButton1);
		subArray.add(subButton2);
		// subArray.add(subButton3);
		subArray.add(subButton4);
		subArray.add(subButton5);
		// subArray.add(subButton6);
		subArray.add(subButton7);
		button.put("sub_button", subArray);
		return button;
	}

	/**
	 * 创建单个普通按钮
	 * 
	 * @param name
	 * @param url
	 * @return
	 */
	private static JSONObject createSingleButton(String name, String url) {
		JSONObject singleButton = new JSONObject();
		singleButton.put("type", "view");
		singleButton.put("name", name);
		singleButton.put("url", url);
		return singleButton;
	}
	
	private static JSONObject createClickButton(String name, String key) {
		JSONObject singleButton = new JSONObject();
		singleButton.put("type", "click");
		singleButton.put("name", name);
		singleButton.put("key", key);
		return singleButton;
	}

	/**
	 * 组装菜单链接
	 * 
	 * @param redirectFlag
	 *            具体业务请求标识符
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getWeixinMenuURL(String redirectFlag)
			throws UnsupportedEncodingException {
		String getOpenIdURL = WebServiceConstants.BUSINESS_GET_USER_OPENID;
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ PropertiesConfig.WEIXIN_APPID
				+ "&redirect_uri="
				+ URLEncoder.encode(getOpenIdURL, "utf-8")
				+ "&response_type=code&scope="
				+ WeixinConstants.WEIXIN_SNSAPI_BASE
				+ "&state="
				+ redirectFlag
				+ "#wechat_redirect";
		log.info("url=" + url);
		return url;
	}

	public static String getWeixinMenuDirectURL(String redirectURL) throws UnsupportedEncodingException {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ PropertiesConfig.WEIXIN_APPID
				+ "&redirect_uri="
				+ URLEncoder.encode(redirectURL, "utf-8")
				+ "&response_type=code&scope="
				+ WeixinConstants.WEIXIN_SNSAPI_BASE
				+ "&state=1#wechat_redirect";
log.info("url=" + url);
		return url;
	}

	public static String getWeixinMenuDirectURLWithUserInfo(String redirectURL)
			throws UnsupportedEncodingException {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
				+ PropertiesConfig.WEIXIN_APPID
				+ "&redirect_uri="
				+ URLEncoder.encode(redirectURL, "utf-8")
				+ "&response_type=code&scope="
				+ WeixinConstants.WEIXIN_SNSAPI_USERINFO
				+ "&state=1#wechat_redirect";
log.info("url=" + url);
		return url;
	}

	public static void main(String[] args) {
		String url = null;
		try {
			url = getWeixinMenuDirectURL("http://192.168.9.159:8080/busOnline/lineBaseInfo/list4Weixin?lineSubType=0");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(url);
	}

	/**
	 * 获取发起新线路页面地址
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getApplyNewLineURL()
			throws UnsupportedEncodingException {
		return getWeixinMenuURL(APPLY_NEW_LINE);
	}

	public static String getApplyDirectURL(String type)
			throws UnsupportedEncodingException {
		if (type.equals("0")) {
			return getWeixinMenuURL(APPLY_REDIRECT1);
		} else if (type.equals("1")) {
			return getWeixinMenuURL(APPLY_REDIRECT2);
		} else if (type.equals("2")) {
			return getWeixinMenuURL(APPLY_REDIRECT3);
		} else if (type.equals("3")) {
			return getWeixinMenuURL(APPLY_REDIRECT4);
		} else {
			return null;
		}
	}

	/**
	 * 获取添加报名的页面地址
	 * 
	 * @param lineBaseId
	 *            线路id
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getGoSignUpURL(String lineBaseId)
			throws UnsupportedEncodingException {
		return getWeixinMenuURL(GO_SIGN_UP + lineBaseId);
	}
}
