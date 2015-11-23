package com.service.v1;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.weixin.RequestHandler;
import com.service.alipay.config.AlipayConfig;
import com.service.alipay.util.AlipayNotify;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.util.common.PostHttpClient;
import com.util.constants.PropertiesConfig;
import com.util.weixin.MessageUtil;
import com.util.weixin.Sha1Util;
import com.util.weixin.WeixinConstants;
import com.util.weixin.WeixinContentUtil;

/**
 *  /\_/\
 * (=+_+=)
 *   ( ).
 * @author sunjiaxiang
 * 貌似这是一个提供各种和订单有关数据获取处理的类哟。
 */
@Service
@Transactional
public class OrderService {

	// 生成订单的API
		public String BUY_TICKET_BY_DAY_API = "/app_book/bookLineByDays_new.action";
		// 查询订单详情的API
		public String ORDER_DETAIL_API = "/app_book/orderInfoByDays.action";
		// 微信支付成功以后修改订单状态的API
		public String PAY_SUCCESS_API = "/app_weixinReturn/weixinUpdateStatu.action";
	
	
	/**
	 * 处理微信支付通知业务
	 * @param request: request请求
	 * @param response: response响应
	 */
	public void handlePayNotifyV3(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> dataMap = MessageUtil.parseXml(request);
		String return_code = dataMap.get("return_code");
System.out.println("handlePayNotifyV3 return_code : " + return_code);
		// 判断签名及结果
		if (return_code != null && "SUCCESS".equals(return_code)) {
			String result_code = dataMap.get("result_code");
System.out.println("handlePayNotifyV3 : result_code : " + result_code);
			if (result_code != null && result_code.equals("SUCCESS")) {
				// 商户订单号
				String out_trade_no = dataMap.get("out_trade_no");
				// 金额,以分为单位
				String total_fee = dataMap.get("total_fee");
				paySuccessCallback(out_trade_no, total_fee);
			}
		}
	}
	
	/** 阿里的支付回调； */
	public void handlePayNotifyAli(HttpServletRequest request) throws Exception {
		// 获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			StringBuffer valueStr = new StringBuffer();
			for (int i = 0; i < values.length; ) {
				valueStr.append(values[i ++]);
				if (i < values.length)
					valueStr.append(".");
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr.toString());
		}
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)
		//RSA签名解密
		if (AlipayConfig.sign_type.equals("0001"))
			params = AlipayNotify.decrypt(params);
		if (AlipayNotify.verifyNotify(params)) {
			Document doc_notify_data = DocumentHelper.parseText(request.getParameter("notify_data"));
			String trade_status = doc_notify_data.selectSingleNode( "//notify/trade_status" ).getText();
			//判断该笔订单是否在商户网站中已经做过处理
			//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
			//如果有做过处理，不执行商户的业务程序
			if (trade_status.equals("TRADE_FINISHED")) {
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
System.out.println("支付宝支付成功 : TRADE_FINISHED");
				paySuccessAli(doc_notify_data);
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
System.out.println("支付宝支付成功 : TRADE_SUCCESS");
				paySuccessAli(doc_notify_data);
			}
		}
	}
	
	/** 支付宝支付成功以后的回调； */
	private void paySuccessAli(Document doc) {
		String out_trade_no = doc.selectSingleNode( "//notify/out_trade_no" ).getText();
		String price = doc.selectSingleNode( "//notify/price" ).getText();
		paySuccessCallback(out_trade_no, price);
	}
	
	/** 银联的支付回调； */
	public void handlePayNotifyUni(HttpServletRequest request) throws Exception {
		String encoding = request.getParameter(SDKConstants.param_encoding);
		Iterator keys = request.getParameterMap().keySet().iterator();
		Map<String, String> valideData = new HashMap<String, String>(); 
System.out.println(" ============================= handlePayNotifyUni ============================= ");
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (!StringUtils.isEmpty(key)) {
				String value = request.getParameter(key);
				try {
					value = new String(value.getBytes("ISO-8859-1"), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				valideData.put(key, value);
			}
System.out.println(key + " : " + request.getParameter(key));
		}
System.out.println(" ============================= ");
		if (!SDKUtil.validate(valideData, encoding)) {
System.out.println("验证签名结果[失败].");
		} else {
			System.out.println(valideData.get("orderId")); // 其他字段也可用类似方式获取
System.out.println("验证签名结果[成功].");
			paySuccessUni(request);
		}
	}

	/** 支付宝支付成功以后的回调； */
	private void paySuccessUni(HttpServletRequest request) {
		String orderId = request.getParameter("orderId");
		String price = request.getParameter("txnAmt");
		paySuccessCallback(orderId, price);
	}
	
	/** 微信支付成功以后调用以改变订单状态的接口； */
	private String paySuccessCallback(String id, String money) {
		String res = null;
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("leaseOrderNo", id);
		Double moneyValue = Double.valueOf(money);
		postParam.put("money", String.valueOf(moneyValue/100));
		try {
			res = PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + PAY_SUCCESS_API, postParam);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 查看订单详情
	 * @param id: 订单ID
	 * @return 订单详情
	 */
	public JSONObject orderDetail(String id) {
		JSONObject res = null;
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("orderNo", id);
		try {
			res = JSONObject.fromObject(PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + ORDER_DETAIL_API, postParam));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			res = new JSONObject();
			res.put("a4", "Server error!");
		}
		return res;
	}
	
	/**
	 * 生成订单
	 * @param lineId: 线路ID
	 * @param classId: 班次ID
	 * @param splitId: 拆分线路ID
	 * @param uId: 用户ID
	 * @param gifId: 优惠券ID
	 * @return 购买结果；
	 */
	public JSONObject buyTickerByDay(String lineId, String classId, String splitId, String uId, String gifId) {
		JSONObject res = null;
		Map<String, String> postParam = new HashMap<String, String>();
		postParam.put("lineBaseid", lineId);
		postParam.put("lineClassIds", classId);
		postParam.put("lineSplitId", splitId);
		postParam.put("passengerId", uId);
		postParam.put("gifId", gifId);
		try {
			res = JSONObject.fromObject(PostHttpClient.sendPost(PropertiesConfig.API_SERVER_URL + BUY_TICKET_BY_DAY_API, postParam));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			res = new JSONObject();
			res.put("a4", "Server error!");
		}
		return res;
	}
	
	/**
	 * 生成微信支付的参数（V3）；
	 * @param productName 商品名
	 * @param out_trade_no 订单号
	 * @param money 商品总价
	 * @param weixin_notify_url 微信通知路径
	 * @throws Exception
	 */
	public JSONObject handleGenParam(String productName, String out_trade_no, double money, String weixin_notify_url, String openId,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
System.out.println(" =================================== handleGenParam =================================== ");
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(PropertiesConfig.WEIXIN_APPID, PropertiesConfig.WEIXIN_SECRET,
				PropertiesConfig.WEIXIN_PARTNERSECRET, PropertiesConfig.WEIXIN_SECRET);
		String noncestr = Sha1Util.getNonceStr();
System.out.println("noncestr : " + noncestr);
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", PropertiesConfig.WEIXIN_APPID);
		packageParams.put("mch_id", PropertiesConfig.WEIXIN_PARTNERID);
		packageParams.put("nonce_str", noncestr);
		packageParams.put("body", productName);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("total_fee", (String.valueOf((int) (money * 100))));
		packageParams.put("spbill_create_ip", request.getRemoteAddr());
		packageParams.put("notify_url", weixin_notify_url);
		packageParams.put("trade_type", "JSAPI");
		packageParams.put("openid", openId);
		String sign = reqHandler.createSign(packageParams);
System.out.println("sign : " + sign);
		packageParams.put("sign", sign);
		String url = WeixinConstants.UNIFIED_ORDER;
		String xml = WeixinContentUtil.createXML(packageParams);
System.out.println(xml);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap = PostHttpClient.postHttpXMLReq(url, xml);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
System.out.println(resultMap);
		String prepay_id = null;
		if ((Integer) resultMap.get("statusCode") == 200) {
			String xmlString = (String) resultMap.get("responseMsg");
			Map<String, String> dataMap = WeixinContentUtil.parseXML(xmlString);
			prepay_id = dataMap.get("prepay_id");
		}
System.out.println("prepay_id : " + prepay_id);
		String timeStamp = Sha1Util.getTimeStamp();
		packageParams = new TreeMap<String, String>();
		packageParams.put("appId", PropertiesConfig.WEIXIN_APPID);
		packageParams.put("timeStamp", timeStamp);
		packageParams.put("nonceStr", noncestr);
		packageParams.put("package", "prepay_id=" + prepay_id);
		packageParams.put("signType", "MD5");
		sign = reqHandler.createSign(packageParams);
System.out.println("sign : " + sign);
		
		// 公众号Id
		JSONObject res = new JSONObject();
		res.put("appId", PropertiesConfig.WEIXIN_APPID);
		res.put("timestamp", timeStamp);
		res.put("noncestr", noncestr);
		res.put("package", "prepay_id=" + prepay_id);
		res.put("sign", sign);
		res.put("signType", "MD5");
System.out.println(res);
System.out.println(" =================================== handleGenParam end =================================== ");
		return res;
	}
}
