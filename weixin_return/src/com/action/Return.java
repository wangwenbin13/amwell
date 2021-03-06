package com.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.utils.PostXmlUtil;
import com.utils.PrepayIdRequestHandler;
import com.utils.StringUtil;
import com.utils.WXUtil;


@ParentPackage("no-interceptor")
@Namespace("/return")
public class Return extends BaseAction{

	private String type;
	
	@Action(value="returnType",results={@Result(name="success",location="../../../reurnType.jsp")})
	public String returnType() {
		type = request.getParameter("type");
		return SUCCESS;
	}
	
	@Action(value = "doReturn", results = { @Result(type = "json") })
	public String doReturn() {
		String leaNo = request.getParameter("leaNo");
		String totalPrice = request.getParameter("totalPrice");
		String realReturn = request.getParameter("returnMoney");
		String type = request.getParameter("type");
		String appId = null;//公众账号ID
		String partner = null;//商户号
		String partnerKey = null;
		String url = null;//证书地址
		if("3051".equals(type)){
			//微信(APP)
			appId = "wx00a756acf10b9960";//weixin.app.id
			partner = "1225903501";//weixin.app.partner
			partnerKey = "6b0030d786927ae4add0fe300c3f5551";//weixin.app.partner.key
			url = "D:/zs/1225903501/apiclient_cert.p12";
		}else if("331".equals(type)){
			appId = "wx6f7e049b0596bd6b";
			partner = "1220365101";
			partnerKey = "71b00d23cce9a57bd6f6302cd5fb5410";
			url = "D:/zs/10026331/apiclient_cert.p12";
		}
		int statu = returnFromWX(leaNo.trim(),realReturn.trim(),request,response,appId,partner,totalPrice.trim(),partnerKey,url);
		if(1==statu){
			write("success");
		}else if(2==statu){
			write("订单号不存在");
		}else if(3==statu){
			write("退款金额大于支付金额");
		}else{
			write("error");
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	/**微信退款**/
	private int returnFromWX(String leaseOrderNo, String realReturn,HttpServletRequest request,HttpServletResponse response,String appId
			,String partner,String totalPrice,String partnerKey,String url ) {
		StringBuilder xml = new StringBuilder();
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
		xml.append("<xml>");
		xml.append("<appid>"+appId+"</appid>"); //公众账号ID
		prepayReqHandler.setParameter("appid", appId);
		
		xml.append("<mch_id>"+partner+"</mch_id>");//商户号
		prepayReqHandler.setParameter("mch_id", partner);
		
		String nonce_str = WXUtil.getNonceStr();
		xml.append("<nonce_str>"+nonce_str+"</nonce_str>");//随机字符串
		prepayReqHandler.setParameter("nonce_str",nonce_str);
		
		xml.append("<op_user_id>"+partner+"</op_user_id>");//操作员  操作员帐号, 默认为商户号
		prepayReqHandler.setParameter("op_user_id",partner);
		
		xml.append("<out_trade_no>"+leaseOrderNo+"</out_trade_no>");//商户订单号
		prepayReqHandler.setParameter("out_trade_no",leaseOrderNo);
		
		String returnNo = StringUtil.generateSequenceNo();
		xml.append("<out_refund_no>"+returnNo+"</out_refund_no>");//商户退款单号    商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
		prepayReqHandler.setParameter("out_refund_no",returnNo);
		
		
		BigDecimal refund_fee = new BigDecimal(realReturn).multiply(new BigDecimal("100"));
		xml.append("<refund_fee>"+String.valueOf(refund_fee.intValue())+"</refund_fee>");//退款总金额      单位为分，只能为整数
		prepayReqHandler.setParameter("refund_fee",String.valueOf(refund_fee.intValue()));
		
		BigDecimal total_fee = new BigDecimal(totalPrice).multiply(new BigDecimal("100"));
		xml.append("<total_fee>"+String.valueOf(total_fee.intValue())+"</total_fee>");//订单总金额
		prepayReqHandler.setParameter("total_fee",String.valueOf(total_fee.intValue()));
		
		
		//生成签名
		String sign = prepayReqHandler.createSignMd5(partnerKey);
		xml.append("<sign>"+sign+"</sign>");//签名
		xml.append("</xml>");
		String weixinurl = "https://api.mch.weixin.qq.com/secapi/pay/refund";//微信退款的路径
		int statu = 0;
		try {
			statu = PostXmlUtil.doPost(weixinurl,xml.toString().trim(),url,partner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return statu;
	}
	
}
