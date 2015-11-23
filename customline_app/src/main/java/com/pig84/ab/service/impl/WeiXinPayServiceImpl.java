package com.pig84.ab.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import com.pig84.ab.dao.ILeaseDao;
import com.pig84.ab.dao.IWeiXinPayDao;
import com.pig84.ab.service.ICouponService;
import com.pig84.ab.service.IWeiXinPayService;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PostXmlUtil;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.BcOrderEntiry;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_12;
import com.pig84.ab.weixin.conf.WeiXinPayConfig;
import com.pig84.ab.weixin.handler.AccessTokenRequestHandler;
import com.pig84.ab.weixin.handler.PackageRequestHandler;
import com.pig84.ab.weixin.handler.PrepayIdRequestHandler;
import com.pig84.ab.weixin.util.WXUtil;
/**
 * 
 * @author wangwenbin
 *
 * 2014-12-20
 */
@Service("weiXinPayService")
public class WeiXinPayServiceImpl implements IWeiXinPayService{
	
	private static final Logger logger = LoggerFactory.getLogger(WeiXinPayServiceImpl.class);

	@Autowired
	private IWeiXinPayDao weiXinPayDao;
	
	@Autowired
	private ILeaseDao leaseOrderDao;
	
	@Autowired
	private ICouponService giftService;

	/***
	 * 包车支付
	 */
	public AppVo_12 bulidPayBcOrderByWeiXin(String orderPirce, String bcBiddingId,
			String userid,HttpServletRequest request,HttpServletResponse response,String counponTeleId) {
		AppVo_12 voPojo = getWeiXinCon(request,response,bcBiddingId,orderPirce,"baoche",counponTeleId);
		return voPojo;
	}

	/**
	 * 小猪巴士支付
	 */
	public AppVo_12 bulidPayOrderByWeiXin(String orderPirce, String orderNo,HttpServletRequest request,HttpServletResponse response) {
		String time = leaseOrderDao.getOrderTime(orderNo);//订单失效时间
		AppVo_12 voPojo = getWeiXinCon(request,response,orderNo,orderPirce,"xiaozhubus",null);
		if(null!=voPojo){
			voPojo.setA9(time);
		}
		return voPojo;
	}
	
	/**
	 * 组装微信订单数据
	 */
	private AppVo_12 getWeiXinCon(HttpServletRequest request,HttpServletResponse response,String orderNo,String price,String typeName,String counponTeleId){
		PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);//生成package的请求类 
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
		//ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);
		AppVo_12 voPojo = new AppVo_12();//返回客户端支付参数的请求类
		packageReqHandler.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);

//		String xml_body = "";
		//获取token值 
		String token = AccessTokenRequestHandler.getAccessToken();
		if (!"".equals(token)) {
			//设置package订单参数
			packageReqHandler.setParameter("bank_type", "WX");//银行渠道
			String body = "";
			String notify_url = "";
			if("baoche".equals(typeName)){
				body = "包车支付";
				notify_url = WeiXinPayConfig.KEY_NOTICE_WEIXIN_BC_URL;
				if(!StringUtils.isEmpty(counponTeleId)){
					packageReqHandler.setParameter("counponTeleId", counponTeleId); //优惠券ID
					AppVo_1 vo_1=giftService.getCouponValueById(counponTeleId);
					BigDecimal giftValue=new BigDecimal(vo_1.getA1());
					BigDecimal thePrice=new BigDecimal(price);
					if(thePrice.compareTo(giftValue)<=0){//总价小于或等于优惠券面值
						/**-1表示小于，0是等于，1是大于**/
						price = "0.00";
					}
					else{//总价大于等于优惠券面值
						price=thePrice.subtract(giftValue).toString();
					}
				}
			}else{
				body = "小猪巴士-车票";
				notify_url = WeiXinPayConfig.KEY_NOTIFY_WEIXIN_URL;
			}
			String type = PropertyManage.get("run_type");
			if("1".equals(type)){
				//测试环境
				body = "test|"+body;
			}
			packageReqHandler.setParameter("body", body); //商品描述   
			packageReqHandler.setParameter("notify_url", notify_url); //接收财付通通知的URL
			packageReqHandler.setParameter("partner", WeiXinPayConfig.KEY_PARTNER); //商户号 
			packageReqHandler.setParameter("out_trade_no", orderNo); //商家订单号
			BigDecimal total_fee = new BigDecimal(price).multiply(new BigDecimal("100"));
			packageReqHandler.setParameter("total_fee", String.valueOf(total_fee.intValue())); //商品金额,以分为单位
			packageReqHandler.setParameter("spbill_create_ip","192.168.1.1"); //订单生成的机器IP，指用户浏览器端IP  
			packageReqHandler.setParameter("fee_type", "1"); //币种，1人民币   66
			packageReqHandler.setParameter("input_charset", "UTF-8"); //字符编码

			//获取package包
			String packageValue = "";
			try {
				packageValue = packageReqHandler.getRequestURL();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			//32位内的随机串，防重发
			String noncestr = WXUtil.getNonceStr();
			//时间戳，为 1970 年 1 月 1 日 00:00 到请求发起时间的秒数
			String timestamp = WXUtil.getTimeStamp();
			String traceid = "";
			////设置获取prepayid支付参数
			prepayReqHandler.setParameter("appid", WeiXinPayConfig.KEY_APP_ID);
			prepayReqHandler.setParameter("appkey", WeiXinPayConfig.KEY_APP_KEY);
			prepayReqHandler.setParameter("noncestr", noncestr);
			prepayReqHandler.setParameter("package", packageValue);
			prepayReqHandler.setParameter("timestamp", timestamp);
			prepayReqHandler.setParameter("traceid", traceid);

			//生成获取预支付签名
			String sign = prepayReqHandler.createSHA1Sign();
			//增加非参与签名的额外参数
			prepayReqHandler.setParameter("app_signature", sign);
			prepayReqHandler.setParameter("sign_method",
					WeiXinPayConfig.KEY_SIGN_METHOD);
			String gateUrl = WeiXinPayConfig.KEY_GATEURL + token;
			prepayReqHandler.setGateUrl(gateUrl);

			//获取prepayId
			String prepayid = "";
			try {
				prepayid = prepayReqHandler.sendPrepay();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			//吐回给客户端的参数
			if (null != prepayid && !"".equals(prepayid)) {
				//输出参数列表
				voPojo.setA10(WeiXinPayConfig.KEY_APP_ID);
				voPojo.setA8(WeiXinPayConfig.KEY_APP_KEY);
				voPojo.setA3(noncestr);
				voPojo.setA4("Sign=" + packageValue);
				voPojo.setA5("Sign=WXPay");
				voPojo.setA6(prepayid);
				voPojo.setA7(timestamp);
				voPojo.setA2("0");
				voPojo.setA11(WeiXinPayConfig.KEY_PARTNER);
				voPojo.setA12(sign);
			} else {
				voPojo.setA2("-1");
			}
		} else {
			voPojo.setA2("-1");
		}
		return voPojo;
	}

	/**微信支付付款返回结果（支付） 小猪巴士**/
	public String successByWeiXinPay(String orderNo, String money,String type,String thirdOrderId) {
		return weiXinPayDao.successByWeiXinPay(orderNo,money,type,thirdOrderId);
	}

	/**
	 * 微信支付付款返回结果（支付）包车
	 */
	public String toDoSaveBcOrder(String bcLineId, String businessId,
			String passengerId, BigDecimal totalPrice, int payModel,String counponTeleId) {
		BcOrderEntiry order=new BcOrderEntiry();
		order.setBc_line_id(bcLineId);
		order.setBusinessId(businessId);
		order.setPassengerId(passengerId);
		
		Float money_t = Float.valueOf(String.valueOf(totalPrice));//微信，分为单位
		Float realMoney = money_t/100;
		totalPrice = new BigDecimal(String.valueOf(realMoney));
//		order.setTotalPrice(totalPrice);
		
		order.setOrderStatus(1);//订单状态,1:待调度 2：已调度(同时输入司机、车辆) 3：已过期
		order.setPayModel(payModel);//乘客支付方式，1:支付宝 2：银联
		order.setPayTime(MyDate.Format.DATETIME.now());
		
		/**优惠信息***/
		if(null!=counponTeleId&&!"".equals(counponTeleId)){
			//根据优惠券id查询优惠券金额
			AppVo_1 vo_1=giftService.getCouponValueById(counponTeleId);
			BigDecimal giftValue=new BigDecimal(vo_1.getA1());
			BigDecimal thePrice=null;
			BigDecimal giftMoney=null;
			BigDecimal giftLeftMoney=null;
			totalPrice = weiXinPayDao.getOrderPrice(bcLineId,businessId); //获得订单的(报价)总价
			if(totalPrice.compareTo(giftValue)==1){//总价大于优惠券面值
				thePrice=totalPrice.subtract(giftValue);
				giftMoney=giftValue;
				giftLeftMoney=new BigDecimal("0");
			}
			else{//总价小于等于优惠券面值
				thePrice=new BigDecimal("0");
				giftMoney=totalPrice;
				giftLeftMoney=giftValue.subtract(totalPrice);
			}
			
			
			order.setTotalPrice(thePrice);//车票单价(实际支付)
			order.setCouponGiftId(counponTeleId);//优惠ID
			order.setGiftMoney(giftMoney);//优惠金额
			order.setGiftLeftMoney(giftLeftMoney);//优惠券未使用金额
			order.setPrice(totalPrice);//订单的(报价)总价
		}
		else{
			order.setTotalPrice(totalPrice);//车票单价(实际支付)
			order.setPrice(totalPrice);
		}
		/**优惠信息***/
		logger.info("======优券惠信息==========TotalPrice="+order.getTotalPrice()+","+"counponTeleId=="+order.getCouponGiftId());
		
		return weiXinPayDao.saveBcOrder(order);
	}

	/**
	 * 注意交易单不要重复处理
	 */
	public boolean getBcOrderByBidIdIsExits(String bcOrderNo) {
		return weiXinPayDao.getBcOrderByBidIdIsExits(bcOrderNo);
	}

	/***
	 * 注意交易单不要重复处理--小猪巴士
	 */
	public boolean getLeasePayByOrderNoIsExits(String orderNo,String type) {
		return weiXinPayDao.getLeasePayByOrderNoIsExits(orderNo,type);
	}

	/**如果使用了优惠券，更新gf_coupon_passenger***/
	public void updateGfPassenger(String counponTeleId, String orderNo) {
		weiXinPayDao.updateGfPassenger(counponTeleId,orderNo);
		
	}

	/**根据订单号查找对应的优惠券信息**/
	public String findCouponTeleId(String outTradeNo) {
		return weiXinPayDao.findCouponTeleId(outTradeNo);
	}

	/**微信退款**/
	public int returnFromWX(String leaseOrderNo, String realReturn,HttpServletRequest request,HttpServletResponse response) {
		StringBuilder xml = new StringBuilder();
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
		xml.append("<xml>");
		xml.append("<appid>"+WeiXinPayConfig.KEY_APP_ID+"</appid>"); //公众账号ID
		prepayReqHandler.setParameter("appid", WeiXinPayConfig.KEY_APP_ID);
		
		xml.append("<mch_id>"+WeiXinPayConfig.KEY_PARTNER+"</mch_id>");//商户号
		prepayReqHandler.setParameter("mch_id", WeiXinPayConfig.KEY_PARTNER);
		
		String nonce_str = WXUtil.getNonceStr();
		xml.append("<nonce_str>"+nonce_str+"</nonce_str>");//随机字符串
		prepayReqHandler.setParameter("nonce_str",nonce_str);
		
		xml.append("<op_user_id>"+WeiXinPayConfig.KEY_PARTNER+"</op_user_id>");//操作员  操作员帐号, 默认为商户号
		prepayReqHandler.setParameter("op_user_id",WeiXinPayConfig.KEY_PARTNER);
		
		xml.append("<out_trade_no>"+leaseOrderNo+"</out_trade_no>");//商户订单号
		prepayReqHandler.setParameter("out_trade_no",leaseOrderNo);
		
		String returnNo = IdGenerator.seq();
		xml.append("<out_refund_no>"+returnNo+"</out_refund_no>");//商户退款单号    商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		prepayReqHandler.setParameter("out_refund_no",returnNo);
		
		
		BigDecimal refund_fee = new BigDecimal(realReturn).multiply(new BigDecimal("100"));
		xml.append("<refund_fee>"+String.valueOf(refund_fee.intValue())+"</refund_fee>");//退款总金额      单位为分，只能为整数
		prepayReqHandler.setParameter("refund_fee",String.valueOf(refund_fee.intValue()));
		
		//获取订单总价
		String totalPrice = leaseOrderDao.queryPrice(leaseOrderNo.replace("`", ""));
//		String totalPrice = "0.05";
		BigDecimal total_fee = new BigDecimal(totalPrice).multiply(new BigDecimal("100"));
		xml.append("<total_fee>"+String.valueOf(total_fee.intValue())+"</total_fee>");//订单总金额
		prepayReqHandler.setParameter("total_fee",String.valueOf(total_fee.intValue()));
		
		
//		//`1225903501201506246064133072
//		xml.append("<transaction_id>1225903501201506246064133072</transaction_id>");
//		prepayReqHandler.setParameter("transaction_id", "1225903501201506246064133072");
		
		//生成签名
		String sign = prepayReqHandler.createSignMd5();
		xml.append("<sign>"+sign+"</sign>");//签名
		xml.append("</xml>");
		String url = PropertyManage.get("weixin.app.returnurl");//微信退款的路径
		int statu = 0;
		try {
			statu = PostXmlUtil.doPost(url,xml.toString().trim());
			logger.info("退款{}成功 状态：{}", returnNo, statu);
		} catch (Exception e) {
			logger.error("退款失败", e);
		}
		
		return statu;
	}

	/**新版微信支付--小猪**/
	public AppVo_12 bulidPayOrderByWeiXinNew(String toPay, String orderNo,HttpServletRequest request, HttpServletResponse response) {
		AppVo_12 voPojo = new AppVo_12();//返回客户端支付参数的请求类
		try {
			Map<String, String> maps = getWeiXinConNew(request,response,orderNo,toPay,"xiaozhubus",null);
			String text = PostXmlUtil.doPostPay("https://api.mch.weixin.qq.com/pay/unifiedorder",maps.get("xml").toString().trim());
			voPojo = xmlElements(text);
			maps.put("prepay_id",voPojo.getA6());
			voPojo.setA7(maps.get("timestamp"));
			 //时间戳，为 1970 年 1 月 1 日 00:00 到请求发起时间的秒数
			String timestamp = WXUtil.getTimeStamp();
			String noncestr =  WXUtil.getNonceStr();
			maps.put("timestamp", timestamp);
			maps.put("noncestr", noncestr);
			String sign = againSign(request,response,maps);
			voPojo.setA12(sign);
			voPojo.setA7(timestamp);
			voPojo.setA3(noncestr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return voPojo;
	}
	
	/***解析xml字符串**/
	public AppVo_12 xmlElements(String xmlDoc) {
		
		AppVo_12 voPojo = new AppVo_12();//返回客户端支付参数的请求类
		
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            Element et = null;
            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(i);//循环依次得到子元素
                if("prepay_id".equals(et.getName())){
                	voPojo.setA6(et.getValue());
                }
            }
            voPojo.setA5("Sign=WXPay");
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voPojo;
    }
	
	/**
	 * 组装微信订单数据(新版)
	 */
	private Map<String, String> getWeiXinConNew(HttpServletRequest request,HttpServletResponse response,String orderNo,String price,String typeName,String counponTeleId){
		StringBuilder xml = new StringBuilder();
		Map<String, String> maps = new HashMap<String, String>();
		
		xml.append("<xml version=\"1.0\" encoding=\"UTF-8\">");
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
		xml.append("<appid>"+WeiXinPayConfig.KEY_APP_ID+"</appid>"); //公众账号ID
		prepayReqHandler.setParameter("appid", WeiXinPayConfig.KEY_APP_ID);
		String body = "";
		String notify_url = "";
		if("baoche".equals(typeName)){
//			body = "PigBus Tickets";
			body = "小猪包车";
			notify_url = WeiXinPayConfig.KEY_NOTIFY_WEIXIN_BAOCHE_URL_NEW;
			if(!StringUtils.isEmpty(counponTeleId)){
				AppVo_1 vo_1=giftService.getCouponValueById(counponTeleId);
				BigDecimal giftValue=new BigDecimal(vo_1.getA1());
				BigDecimal thePrice=new BigDecimal(price);
				if(thePrice.compareTo(giftValue)<=0){//总价小于或等于优惠券面值
					/**-1表示小于，0是等于，1是大于**/
					price = "0.00";
				}
				else{//总价大于等于优惠券面值
					price=thePrice.subtract(giftValue).toString();
				}
			}
		}else{
			body = "小猪上下班";
			notify_url = WeiXinPayConfig.KEY_NOTIFY_WEIXIN_SHANGXIABN_URL_NEW;
		}
		String type = PropertyManage.get("run_type");
		if("1".equals(type)){
			//测试环境
			body = "test|"+body;
		}
		
		xml.append("<body>"+body+"</body>");//商品或支付单简要描述
		prepayReqHandler.setParameter("body", body);
		
		if(StringUtils.isEmpty(counponTeleId)){
			counponTeleId = "";
		}
		
		xml.append("<input_charset>UTF-8</input_charset>");
		prepayReqHandler.setParameter("input_charset", "UTF-8");
		
		xml.append("<mch_id>"+WeiXinPayConfig.KEY_PARTNER+"</mch_id>");//商户号
		prepayReqHandler.setParameter("mch_id", WeiXinPayConfig.KEY_PARTNER);
		
		String nonce_str = WXUtil.getNonceStr();
		xml.append("<nonce_str>"+nonce_str+"</nonce_str>");//随机字符串
		prepayReqHandler.setParameter("nonce_str",nonce_str);
		
		xml.append("<notify_url>"+notify_url+"</notify_url>");//接收微信支付异步通知回调地址
		prepayReqHandler.setParameter("notify_url", notify_url);
		
		xml.append("<out_trade_no>"+orderNo+"</out_trade_no>");//商户订单号
		prepayReqHandler.setParameter("out_trade_no",orderNo);
		
		xml.append("<spbill_create_ip>127.0.0.1</spbill_create_ip>");//订单生成的机器IP，指用户浏览器端IP  
		prepayReqHandler.setParameter("spbill_create_ip","127.0.0.1"); 
		
		BigDecimal total_fee = new BigDecimal(price).multiply(new BigDecimal("100"));
		xml.append("<total_fee>"+String.valueOf(total_fee.intValue())+"</total_fee>");
		prepayReqHandler.setParameter("total_fee", String.valueOf(total_fee.intValue())); //商品金额,以分为单位
		
		xml.append("<trade_type>APP</trade_type>");//交易类型
		prepayReqHandler.setParameter("trade_type", "APP");
		
		
		//生成签名
		String sign = prepayReqHandler.createSignMd5();
		xml.append("<sign>"+sign+"</sign>");//签名
		xml.append("</xml>");
		String xmlString = "";
		try {
			xmlString = new String(xml.toString().getBytes("UTF-8"),"ISO8859-1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		maps.put("xml",xmlString);
		return maps;
	}

	/**新版微信支付--包车支付**/
	public AppVo_12 bulidPayBcOrderByWeiXinNew(String toPay, String orderNo,
			String userid, HttpServletRequest request,
			HttpServletResponse response, String counponTeleId) {
		AppVo_12 voPojo = new AppVo_12();//返回客户端支付参数的请求类
		try {
			Map<String, String> maps  = getWeiXinConNew(request,response,orderNo,toPay,"baoche",counponTeleId);
			String text = PostXmlUtil.doPostPay("https://api.mch.weixin.qq.com/pay/unifiedorder",maps.get("xml").toString().trim());
			voPojo = xmlElements(text);
			maps.put("prepay_id",voPojo.getA6());
			voPojo.setA7(maps.get("timestamp"));
			 //时间戳，为 1970 年 1 月 1 日 00:00 到请求发起时间的秒数
			String timestamp = WXUtil.getTimeStamp();
			String noncestr =  WXUtil.getNonceStr();
			maps.put("timestamp", timestamp);
			maps.put("noncestr", noncestr);
			String sign = againSign(request,response,maps);
			voPojo.setA12(sign);
			voPojo.setA7(timestamp);
			voPojo.setA3(noncestr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voPojo;
	}

	/**再次签名**/
	private String againSign(HttpServletRequest request,
			HttpServletResponse response,Map<String, String> maps) {
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
		prepayReqHandler.setParameter("appid", WeiXinPayConfig.KEY_APP_ID);
		prepayReqHandler.setParameter("noncestr",maps.get("noncestr"));
		prepayReqHandler.setParameter("package", "Sign=WXpay");
		prepayReqHandler.setParameter("partnerid", WeiXinPayConfig.KEY_PARTNER);
		prepayReqHandler.setParameter("prepayid", maps.get("prepay_id"));
		prepayReqHandler.setParameter("timestamp", maps.get("timestamp"));
		//生成签名
		String sign = prepayReqHandler.secondCreateSignMd5();
		return sign;
	}
}
