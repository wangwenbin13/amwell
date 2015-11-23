package com.util.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性配置
 * 
 * @author shawn.zheng
 * 
 */
public class PropertiesConfig {
	
	public static final String TICKET_CODE;
	// 项目名称
	public static final String PROJECT_NAME;
	// 页面数据条数
	public static final String PAGESIZE;
	// 乘车流程链接
	public static final String HELP_CHENG_CHE_URL;
	// 彩生活是否需要签名验证
	public static final String CAI_SHENG_HUO_NEED_LOGIN_SIGN_CHECK;

	public static final String API_SERVER_URL;
	// 服务器地址
	public static final String SERVER_IP;
	// 下发短信服务地址
	public static final String MESSAGE_IP;
	// APP后台地址
	public static final String APP_IP;
	// 运营平台地址
	public static final String CUSTOMLINE_IP;
	// 下载图片的根目录地址
	public static final String IMAGE_IP;
	// 安卓APP下载地址
	public static final String ANDROID_APP_DOWNLOAD_URL;
	// iosAPP下载地址
	public static final String IOS_APP_DOWNLOAD_URL;
	// 问卷活动是否进行
	public static final String QUESTIONNAIRE_FLAG;

	// 包车线路乘客每日发布的总数
	public static final String MAX_PUBLISH_BCLINE_COUNT;

	// 百度地图API(根据ip搜索城市)
	public static final String MAP_SEARCH_BY_REGION;

	// 百度地图API(根据经纬度搜索地区)
	public static final String MAP_SEARCH_BY_LOCATION;

	// 微信支付后台方式 test normal
	public static final String WEIXIN_PAY_TYPE;
	// 微信token
	public static final String WEIXIN_TOKEN;
	// 微信appId
	public static final String WEIXIN_APPID;
	// 微信Secret
	public static final String WEIXIN_SECRET;
	// 微信支付商户
	public static final String WEIXIN_PARTNERID;
	// 微信商户密钥
	public static final String WEIXIN_PARTNERSECRET;
	// 商户支付密钥
	public static final String WEIXIN_PAY_SIGN_KEY;
	// 百度统计的key;
	public static final String BAIDU_STATISTICAL_KEY;

	// 系统启动的时候加载配置文件
	static {
		Properties properties = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/info.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// common
		PAGESIZE = properties.getProperty("PAGESIZE");
		HELP_CHENG_CHE_URL = properties.getProperty("HELP_CHENG_CHE_URL");
		ANDROID_APP_DOWNLOAD_URL = properties.getProperty("ANDROID_APP_DOWNLOAD_URL");
		IOS_APP_DOWNLOAD_URL = properties.getProperty("IOS_APP_DOWNLOAD_URL");
		QUESTIONNAIRE_FLAG = properties.getProperty("QUESTIONNAIRE_FLAG");
		MAP_SEARCH_BY_REGION = properties.getProperty("MAP_SEARCH_BY_REGION");
		MAP_SEARCH_BY_LOCATION = properties.getProperty("MAP_SEARCH_BY_LOCATION");
		MAX_PUBLISH_BCLINE_COUNT = properties.getProperty("MAX_PUBLISH_BCLINE_COUNT");
		CAI_SHENG_HUO_NEED_LOGIN_SIGN_CHECK = properties.getProperty("CAI_SHENG_HUO_NEED_LOGIN_SIGN_CHECK");
		
		TICKET_CODE = properties.getProperty("TICKET_CODE");
		PROJECT_NAME = properties.getProperty("PROJECT_NAME");
		API_SERVER_URL = properties.getProperty("API_SERVER_URL");
		SERVER_IP = properties.getProperty("SERVER_IP");
		MESSAGE_IP = properties.getProperty("MESSAGE_IP");
		APP_IP = properties.getProperty("APP_IP");
		CUSTOMLINE_IP = properties.getProperty("CUSTOMLINE_IP");
		IMAGE_IP = properties.getProperty("IMAGE_IP");
		// 微信配置
		WEIXIN_PAY_TYPE = properties.getProperty("WEIXIN_PAY_TYPE");
		WEIXIN_TOKEN = properties.getProperty("WEIXIN_TOKEN");
		WEIXIN_APPID = properties.getProperty("WEIXIN_APPID");
		WEIXIN_SECRET = properties.getProperty("WEIXIN_SECRET");
		WEIXIN_PARTNERID = properties.getProperty("WEIXIN_PARTNERID");
		WEIXIN_PARTNERSECRET = properties.getProperty("WEIXIN_PARTNERSECRET");
		WEIXIN_PAY_SIGN_KEY = properties.getProperty("WEIXIN_PAY_SIGN_KEY");
		BAIDU_STATISTICAL_KEY = properties.getProperty("BAIDU_STATISTICAL_KEY");
	}
}