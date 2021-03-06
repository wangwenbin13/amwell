package com.service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.bean.PayBean;
import com.utils.WeiXinUtil.PostXmlUtil;
import com.utils.WeiXinUtil.PrepayIdRequestHandler;
import com.utils.WeiXinUtil.WXUtil;
import com.utils.WeiXinUtil.WeiXinPayConfig;


/**
 * 微信相关service接口
 * @author 张强
 * @time 2015-09-21 15:22:29
 */
public class WeiXinService {

	/**构建微信支付订单（old）**/
	public static String bulidPayOrder(PayBean bean,HttpServletRequest request, HttpServletResponse response) {
			String resutl = bulidPayOrderByWeiXinNew(bean.getPrice(),bean.getOrderNo(), bean.getTitle(),bean.getDesc(), request, response);
		return resutl;
	}

	


/******************************************************************微信附加方法**************************************************************************/	
	
	/**新版微信支付--小猪**/
	public static String bulidPayOrderByWeiXinNew(String toPay, String orderNo,String title,String desc,HttpServletRequest request, HttpServletResponse response) {
		String prepay_id = "";
		
		try {
			Map<String, String> maps = getWeiXinConNew(request,response,orderNo,toPay,title,desc);
			String text = PostXmlUtil.doPostPay("https://api.mch.weixin.qq.com/pay/unifiedorder",maps.get("xml").toString().trim());
			prepay_id = xmlElements(text);
			return prepay_id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prepay_id;
	}
	
	/**
	 * @param 
	 * orderNo 商户订单号  
	 * price 商品价格（以分为单位）
	 * title 商品标题
	 * desc  商品描述
	 * 组装微信订单数据(新版)
	 */
	private static Map<String, String> getWeiXinConNew(HttpServletRequest request,HttpServletResponse response,String orderNo,String price,String title,String desc){
		StringBuilder xml = new StringBuilder();
		Map<String, String> maps = new HashMap<String, String>();
		
		xml.append("<xml version=\"1.0\" encoding=\"UTF-8\">");
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
		xml.append("<appid>"+WeiXinPayConfig.KEY_APP_ID+"</appid>"); //公众账号ID
		prepayReqHandler.setParameter("appid", WeiXinPayConfig.KEY_APP_ID);
		String body = desc;
		String notify_url = WeiXinPayConfig.KEY_NOTIFY_WEIXIN_RETURN_URL;
		xml.append("<body>"+body+"</body>");//商品或支付单简要描述
		prepayReqHandler.setParameter("body", body);
		
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
	
	/***解析xml字符串**/
	public static String xmlElements(String xmlDoc) {
		String result = "";
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
                	result = et.getValue();
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
	
}
