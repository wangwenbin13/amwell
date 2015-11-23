package com.util.weixin;

import com.util.constants.PropertiesConfig;

/**
 * 微信常量类
 * 
 * @author shawn.zheng
 * 
 */
public class WeixinConstants {

	// 获取用户基础详情
	public static final String WEIXIN_SNSAPI_BASE = "snsapi_base";

	// 获取用户详细详情
	public static final String WEIXIN_SNSAPI_USERINFO = "snsapi_userinfo";

	// 获取微信access_token请求地址
	public static final String WEIXIN_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ PropertiesConfig.WEIXIN_APPID + "&secret=" + PropertiesConfig.WEIXIN_SECRET;

	// 创建微信自定义菜单请求
	public static final String WEIXIN_CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

	// 获取用户openId前半段
	public static final String WEIXIN_GET_USER_OPENID_PRE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
			+ PropertiesConfig.WEIXIN_APPID + "&secret=" + PropertiesConfig.WEIXIN_SECRET + "&code=";

	// 获取用户openId后半段
	public static final String WEIXIN_GET_USER_OPENID_AFTER = "&grant_type=authorization_code";

	// 获取用户信息
	public static final String WEIXIN_GET_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";

	// 微信支付通知接口
	public static final String DELIVER_NOTIFY_URL = "https://api.weixin.qq.com/pay/delivernotify";

	// 统一支付接口
	public static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	

}
