package com.utils.AliUtil;

import com.utils.PropertyManage;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-11
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088511429153115";
	// 商户的私钥
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANLePKoXYtllerwG3kmkY9s0UBM+W8zWc2V6sFPIHO1zfv9XwBXU0ZvO+5ejTF5BYZN+Au0xirbD9I4eUg6xc0lPvXI1WE1u69unyzDyuxc42cAdn7Oa2ugV3vcsOkQT//hLyy8FxvHTXSj/72TES3GKvlYRBgPZteEwk3d6gLu3AgMBAAECgYBvhscDFxda78c4R1GuFVoTB9oKM4MOFaY21+sGhOJ33AwhNOINKeAqzhBXJTEw8NeQ0KbvmLJK890WhYbU6pmUCMU8J/ICUnBz7XYT4OF7JLZLqwGHhNpLVJv54g4KXiU145ikI2CiQBgG4h4hsDd1EF4Bi3U5ExK2vsm7afJu4QJBAPBc1GI8ngJfseEv7C+qC5z9bFZO5z0cFIr8je4wc40mI4tOgFHUKXU+ZtYRu0N1kw7ze4GlbmtvVlK3vfdlU1kCQQDgli9Tw6WVRBtBnwZ+hKFE9zkMPnpl2y+Lt3t4RF1THEWRiknna50vnkkzceSKj6qybFkfs4FiT/uLkHa72/WPAkEAoq/SOKOatzSaKzbKgAByV9HXsBs7vErzRJdoYV19+H1l9XYuuDQH04lnBWF/BEviO25lm/yi3ii/nsEq3PZCEQJBAJqP4YxlVlyu3Y9A0WhbAN4EwpchnHzi5wrhwLCZZBzhlSM/p/MDhc8SwVANbvrJOvHGTWPa1w2A/B4l0wEgtRMCQGUjK1Wi2Oy7vCj+9eOI2OIJj1GuYTFmXkIUG88fP1ZV5OuYIVaxbenDVVfdnmRhFka8sim1ePg7UP1gUxKIzCg=";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
//	MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDS3jyqF2LZZXq8Bt5JpGPbNFATPlvM1nNlerBTyBztc37/V8AV1NGbzvuXo0xeQWGTfgLtMYq2w/SOHlIOsXNJT71yNVhNbuvbp8sw8rsXONnAHZ+zmtroFd73LDpEE//4S8svBcbx010o/+9kxEtxir5WEQYD2bXhMJN3eoC7twIDAQAB
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	//支付宝收款账号
	public static String DEFAULT_SELLER = "2946795312@qq.com";
	
	//支付宝返回结果URL
	public static String Ali_Pay_Return_Url = PropertyManage.get("Ali_Pay_Return_Url");
	
	
}
