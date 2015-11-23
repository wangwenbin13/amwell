package com.action;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.rowset.JoinRowSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.bean.PayBean;
import com.service.AliService;
import com.utils.Bean;
import com.utils.Http;
import com.utils.Json;
import com.utils.PropertyManage;
import com.utils.AliUtil.AlipayNotify;

/**
 * 支付宝相关业务
 * @author zhangqiang
 * @time 2015-09-16 14:47:57
 */
@ParentPackage("no-interceptor")
@Namespace("/ali_pay")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class AliPayAction extends BaseAction {
	
	/**
	 * 构建支付宝订单
	 * @param orderNo 商品订单号
	 * @return -1：失败   其他：签名
	 * @throws IOException 
	 */
	@Action(value = "bulidAliPayOrder", results = { @Result(type = "json") })
	public String bulidAliPayOrder() throws IOException{
		String orderNo = request.getParameter("orderNo"); //商品订单号
		String orderInfoUrl = PropertyManage.get("ORDER_INFO_URL");//获取商品详细地址
		Bean vo = new Bean();
		if(StringUtils.isEmpty(orderNo)){
			vo.setResult("-1");
			write(vo);
			return null;
		}
		
		String response = Http.post(orderInfoUrl, "orderNo",orderNo);
		
		//获取商品信息异常
		if(StringUtils.isEmpty(response)){
			vo.setResult("-1");
			write(vo);
			return null;
		}
		PayBean bean = new PayBean();
		bean = Json.fromJson(response, PayBean.class);
		
		//获取商品信息异常
		if(StringUtils.isEmpty(bean.getDesc()) || StringUtils.isEmpty(bean.getOrderNo()) || 
		   StringUtils.isEmpty(bean.getPrice()) ||StringUtils.isEmpty(bean.getTitle())){
			vo.setResult("-1");
			write(vo);
			return null;
		}
		String aliResult = AliService.bulidPayOrder(bean);
		if(StringUtils.isEmpty(aliResult)){
			vo.setResult("-1");
			write(vo);
			return null;
		}
		vo.setResult(aliResult);
		write(vo);
		return null;
	}
	
	/**
	 * 支付宝回调地址（第三方回调）
	 * @return
	 */
	@Action(value = "aliReturnUrl", results = { @Result(type = "json") })
	public String aliReturnUrl(){
		try {

			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
			// 商户订单号(商户传过去的订单号)
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 交易金额
			String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

			String orderInfoUrl = PropertyManage.get("Notice_Merchant_Url");//商户支付通知地址
			Map<String,String> args = new HashMap<>();
			args.put("orderNo",out_trade_no);
			args.put("tradeOrderNo", trade_no);
			args.put("fee", total_fee);
			args.put("payModel", "1");//支付宝支付
			
			if (AlipayNotify.verify(params)) {// 验证成功
				if (trade_status.equals("TRADE_FINISHED")) {//已经支付过，或者已经退过款
					args.put("tradeType", "0");
				} else if (trade_status.equals("TRADE_SUCCESS")) {//交易成功
					args.put("tradeType", "1");
				}
				response.getWriter().println("success"); // 请不要修改或删除
			} else {// 验证失败
				args.put("tradeType", "2");
				response.getWriter().println("fail");
			}
			Http.post(orderInfoUrl, args);//通知商户支付结果
		} catch (Exception e) {
		}
		return null;
	}
}
