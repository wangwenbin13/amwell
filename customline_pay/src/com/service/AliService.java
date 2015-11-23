package com.service;

import java.net.URLEncoder;

import com.bean.PayBean;
import com.utils.PropertyManage;
import com.utils.AliUtil.AlipayConfig;
import com.utils.AliUtil.RSA;


/**
 * 支付宝相关service接口
 * @author 张强
 * @time 2015-09-16 15:02:59
 */
public class AliService {

	/**构建阿里支付订单**/
	public static String bulidPayOrder(PayBean bean) {
		String result = payZfb(bean.getPrice(), bean.getTitle(),bean.getDesc(),bean.getOrderNo(),bean.getLimitTime(),PropertyManage.get("Ali_Pay_Return_Url"));
		return result;
	}

	


/******************************************************************支付宝附加方法**************************************************************************/	
	
	
	
	//组装订单信息
	private static String  payZfb(String price,String title,String desc,String orderNo,String time,String url) {
		String infor = makeOrder(price,title,desc,orderNo,time,url);
		String sign = RSA.sign(infor, AlipayConfig.private_key,AlipayConfig.input_charset);
		sign = URLEncoder.encode(sign);
		return infor += "&sign=\"" + sign + "\"&" + getSignType();
	}

	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}

	// subject="珍珠项链 【2元包邮】韩版 韩国 流行饰品太阳花小巧雏菊 珍珠项链2M15"
	// body="【2元包邮】韩版 韩国 流行饰品太阳花小巧雏菊 珍珠项链2M15" price="一口价:0.01"
	@SuppressWarnings("deprecation")
	private static String makeOrder(String price,String title,String desc,String orderNo, String time,String url) {

		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(AlipayConfig.partner);//合作商ID
		sb.append("\"&out_trade_no=\"");
		sb.append(orderNo);
		sb.append("\"&subject=\"");
		sb.append(title);
		sb.append("\"&body=\"");
		sb.append(desc);
		sb.append("\"&total_fee=\"");
		sb.append(price);//价格
		sb.append("\"&it_b_pay=\"");
		sb.append(time);//超时时间
		sb.append("\"&notify_url=\"");
		// 网址需要做URL编码
		sb.append(URLEncoder.encode(url));//回调函数
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(AlipayConfig.DEFAULT_SELLER);//收款人

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"");

		return new String(sb);
	}
}
