package com.pig84.ab.weixin.conf;

import java.io.InputStream;
import java.util.PropertyResourceBundle;


public class WeiXinPayConfig {

	/**微信开发平台应用id*/
	public static String KEY_APP_ID;
	/**应用对应的凭证*/
	public static String KEY_APP_SECRET;
	/**应用对应的密钥*/
	public static String KEY_APP_KEY;
	/**财付通(微信)商户号*/
	public static String KEY_PARTNER;
	/**财付通(微信)商户号对应的密钥*/
	public static String KEY_PARTNER_KEY;
	/**获取access_token对应的url*/
	public static String KEY_TOKENURL;
	/**常量固定值 */
	public static String KEY_GRANT_TYPE;
	/**获取预支付id的接口url*/
	public static String KEY_GATEURL;
	/**access_token常量值*/
	public static String KEY_ACCESS_TOKEN;
	/**签名算法常量值*/
	public static String KEY_SIGN_METHOD;
	/**接收财付通(微信)通知的URL  小猪巴士*/
	public static String KEY_NOTIFY_WEIXIN_URL;
	/**接收财付通(微信)通知的URL  包车**/
	public static String KEY_NOTICE_WEIXIN_BC_URL;
	/**新版微信支付通知URL 上下班**/
	public static String KEY_NOTIFY_WEIXIN_SHANGXIABN_URL_NEW;
	/**新版微信支付通知URL 包车**/
	public static String KEY_NOTIFY_WEIXIN_BAOCHE_URL_NEW;
	
	//初始化
	private static String APP_ID = "weixin.app.id";//微信开发平台应用id
	private static String APP_SECRET = "weixin.app.secret";//应用对应的凭证
	//应用对应的密钥
	private static String APP_KEY = "weixin.app.key";
	private static String PARTNER = "weixin.app.partner";//财付通商户号
	private static String PARTNER_KEY = "weixin.app.partner.key";//商户号对应的密钥
	private static String TOKENURL = "weixin.app.tokenurl";//获取access_token对应的url
	private static String GRANT_TYPE = "weixin.app.grant.type";//常量固定值 
	private static String GATEURL = "weixin.app.gateurl";//获取预支付id的接口url
	private static String ACCESS_TOKEN = "weixin.app.access.token";//access_token常量值
	private static String SIGN_METHOD = "weixin.app.sign.method";//签名算法常量值
	private static String NOTIFY_WEIXIN_URL = "weixin.app.notify.weixin.url";//接收财付通(微信)通知的URL 小猪巴士
	private static String NOTICE_WEIXIN_BC_URL = "weixin.app.notify.weixin.bc.url";//接收财付通(微信)通知的URL 包车
	private static String NOTIFY_WEIXIN_SHANGXIABN_URL_NEW = "new.weixin.app.notify.shangxiaban.url";//新版微信支付通知URL 上下班
	private static String NOTIFY_WEIXIN_BAOCHE_URL_NEW = "new.weixin.app.notifu.baoche.url";//新版微信支付通知URL 包车
	
	private static final String CONF_FILE_NAME = "info.properties";
	
	static {
		
        try {
			InputStream fis = WeiXinPayConfig.class.getClassLoader().getResourceAsStream(CONF_FILE_NAME);
			PropertyResourceBundle props = new PropertyResourceBundle(fis);
			KEY_APP_ID = props.getString(APP_ID);
			KEY_APP_SECRET = props.getString(APP_SECRET);
			KEY_APP_KEY = props.getString(APP_KEY);
			KEY_PARTNER = props.getString(PARTNER);
			KEY_PARTNER_KEY = props.getString(PARTNER_KEY);
			KEY_TOKENURL = props.getString(TOKENURL);
			KEY_GRANT_TYPE = props.getString(GRANT_TYPE);
			KEY_GATEURL = props.getString(GATEURL);
			KEY_ACCESS_TOKEN = props.getString(ACCESS_TOKEN);
			KEY_SIGN_METHOD = props.getString(SIGN_METHOD);
			KEY_NOTIFY_WEIXIN_URL = props.getString(NOTIFY_WEIXIN_URL);
			KEY_NOTICE_WEIXIN_BC_URL = props.getString(NOTICE_WEIXIN_BC_URL);
			KEY_NOTIFY_WEIXIN_SHANGXIABN_URL_NEW = props.getString(NOTIFY_WEIXIN_SHANGXIABN_URL_NEW);
			KEY_NOTIFY_WEIXIN_BAOCHE_URL_NEW = props.getString(NOTIFY_WEIXIN_BAOCHE_URL_NEW);
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
}
