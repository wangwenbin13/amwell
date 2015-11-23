package com.util.constants;

/**
 * web接口
 * 
 * @author shawn.zheng
 * 
 */
public class WebServiceConstants {
	// 发送短信接口
	public static final String SEND_MESSAGE_URL = PropertiesConfig.MESSAGE_IP
			+ "msg/msg/postSendMsg.action";

	// 微信支付通知接口
	public static final String WEIXIN_NOTIFY_URL = PropertiesConfig.SERVER_IP
			+ PropertiesConfig.PROJECT_NAME + "/v1/order/payNotifyUrl";
	
	// 微信支付通知接口
	public static final String WEIXIN_ANNNOTIFY_URL = PropertiesConfig.SERVER_IP
				+ PropertiesConfig.PROJECT_NAME + "/games/anniversary/payNotifyUrl";

	// 获取用户openId
	public static final String BUSINESS_GET_USER_OPENID = PropertiesConfig.SERVER_IP
			+ PropertiesConfig.PROJECT_NAME + "/weixinAccess/getOpenId";

	// 订单按次购买接口
	public static final String CREATE_ORDER_BY_TIME = PropertiesConfig.APP_IP
			+ "customline_app/app_book/bookLineByDays.action";
	
	//彩生活登录对接接口
	public static final String CAI_SHENG_HUO_LOGIN = PropertiesConfig.APP_IP
			+ "customline_app/app_caishenghuo/caiShengHuoLogin.action";

	// 获取车辆位置的接口
	public static final String GET_GPS_INFO = PropertiesConfig.APP_IP
			+ "customline_app/app_GPS/getGPSInfo.action";

	// 订单包月购买接口
	public static final String CREATE_ORDER_BY_MONTH = PropertiesConfig.APP_IP
			+ "customline_app/app_book/bookLineByMonth.action";

	// 同步论坛用户名接口
	public static final String SYNC_DISCUZ_USER = PropertiesConfig.APP_IP
			+ "customline_app/app_loginAndRegister/weixinUserSupport.action";

	// 支付成功添加收入统计
	public static final String ADD_STAT_TOTAL = PropertiesConfig.CUSTOMLINE_IP
			+ "customline/addStatTotalHttpAction/addStatTotal.action";
	
	// 取消订单以后默认返回的界面；
	public static final String CANCEL_PAY_RESET_URL = PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/line/detail";
	// 票务信息的地址；
	public static final String TICKET_URL = PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/user/ticket";
	
	//支付宝支付成功后端跳转接口
	public static final String ALI_NOTIFY_BACK_URL = PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/order/payNotifyAli";	
	//支付宝支付成功前端跳转接口
	public static final String ALI_NOTIFY_FRONT_URL = PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/order/payCallbackAli";
	// 支付宝取消支付；
	public static final String ALI_CANCEL_URL = PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME + "/v1/order/cancelAli";
	
	//银联支付成功后端跳转接口
	public static final String UNION_NOTIFY_BACK_URL = PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME +"/v1/order/payNotifyUni";
	//银联支付成功前端跳转接口
	public static final String UNION_NOTIFY_FRONT_URL = PropertiesConfig.SERVER_IP + PropertiesConfig.PROJECT_NAME +"/v1/order/payCallbackUni";
	
	//百度地图根据ip获取所在城市
	public static final String BAI_DU_IP_TO_CITY_URL = "http://api.map.baidu.com/location/ip?ak=2zxiYgQK3DGAd2VS51sd4acL&coor=bd09ll";

}
