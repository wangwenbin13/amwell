package com.action.v1.views;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.alipay.AliPayService;
import com.service.unionpay.FrontConsumeService;
import com.service.v1.OrderService;
import com.unionpay.acp.sdk.SDKConfig;
import com.util.common.BusCommonUtil;
import com.util.constants.WebServiceConstants;

@Controller
@RequestMapping("/v1/order")
public class OrderAction {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AliPayService aliPayService;

	/**
	 * 判断该用户是否存在待支付的订单
	 * @param time: 发车时间
	 * @param id: 订单id
	 * @return String
	 */
	@RequestMapping(value = "/buy", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String buy(String lineId, String classId, String splitId, String uid, String gifId, String openId, 
			HttpServletRequest request, HttpServletResponse response) {
System.out.println(" -------------------------------- buy -------------------------------- ");
		JSONObject res = null;
		if (StringUtils.isEmpty(lineId)) {
			res = makeError("a4", "LineId is null!");
		} else if (StringUtils.isEmpty(classId)) {
			res = makeError("a4", "ClassId is null!");
		} else if (StringUtils.isEmpty(splitId)) {
			res = makeError("a4", "SplitId is null!");
		} else if (StringUtils.isEmpty(uid)) {
			res = makeError("a4", "User is null!");
		} else {
			res = orderService.buyTickerByDay(lineId, classId, splitId, uid, gifId);
System.out.println(res);
			if (res.has("a1")) {
				if (res.getString("a1").equals("0")) {
					res = makeError("a4", "请登录后进行支付!");
				} else if (res.has("a2")) {
					String orderId = res.getString("a2");
					if (orderId.equals("1")) {
						res = makeError("a4", "低于1元的班次只能购买一次!");
					} else if (orderId.equals("2")) {
						res = makeError("a4", "无效班次!");
					} else if (orderId.equals("3")) {
						res = makeError("a4", "座位不足!");
					} else if (orderId.equals("4")) {
						res = makeError("a4", "线路信息错误!");
					} else if (orderId.equals("5")) {
						res = makeError("a4", "系统错误!");
					} else if (orderId.equals("6")) {
						res = makeError("a4", "无效优惠券!");
					} else if (res.has("a3")) {
						JSONObject detail = orderService.orderDetail(orderId);
System.out.println(detail);
						try {
							checkDetail(detail);
							JSONObject a5 = orderService.handleGenParam("小猪上下班", detail.getString("a3"), detail.getDouble("a5"),
									WebServiceConstants.WEIXIN_NOTIFY_URL, openId, request, response);
							// 微信支付
							if (BusCommonUtil.checkWeixin(request)) {
								res.put("a5", a5);
							}
						} catch(Exception exc) {
							res = makeError("a4", exc.getMessage());
						}
					}
				}
			}
		}
		return res.toString(2);
	}
	
	@RequestMapping(value = "/pay", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String orderPay(String orderId, String type) {
		try {
			return orderBy3rd(type, orderId);
		} catch(Exception exc) {
			exc.printStackTrace();
			return exc.getMessage();
		}
	}
	
	/** 检验订单； */
	private void checkDetail(JSONObject detail) throws Exception {
		if (detail.has("a1")) {
			String detailStat = detail.getString("a1");
			if (detailStat.equals("0")) {
				throw new Exception("订单不存在！");
			} else if (detailStat.equals("1")) {
				throw new Exception("非按次订单！");
			} else if (detailStat.equals("4")) {
				throw new Exception("已经失效的订单！");
			} else if (detailStat.equals("5")) {
				throw new Exception("已经被取消的订单！");
			}
		} else {
			throw new Exception("订单生成失败!");
		}
	}
	
	/** 生成json的异常信息； */
	private JSONObject makeError(String key, String msg) {
		JSONObject res = new JSONObject();
		res.put(key, msg);
		return res;
	}
	
	/** 阿里的取消交易接口； */
	@RequestMapping("/cancelAli")
	public void cancelAli(String orderId, HttpServletRequest request, HttpServletResponse response) {
		JSONObject order = orderService.orderDetail(orderId);
		StringBuffer url = new StringBuffer();
		url.append(WebServiceConstants.CANCEL_PAY_RESET_URL);
		url.append("?id=");
		url.append(order.getString("a10"));
		url.append("&sid=");
		url.append(order.getString("a12"));
		try {
			response.sendRedirect(url.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 阿里支付通知接口（展示）
	 * @param request: request请求
	 * @param response: response响应
	 * @throws Exception
	 */
	@RequestMapping(value = "/payCallbackAli", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String payCallbackAli(String orderId, String result, HttpServletResponse response) throws Exception {
		StringBuffer redirect = new StringBuffer();
		redirect.append("<script>");
		if (result.equals("success")) {
			redirect.append("var user = eval('(' + localStorage.getItem('MyUserInfo') + ')');location.href='");
			redirect.append(WebServiceConstants.TICKET_URL);
			redirect.append("?id=");
			redirect.append(orderId);
			redirect.append("&uid=' + user['a2'];");
		} else {
			redirect.append("location.href='");
			JSONObject order = orderService.orderDetail(orderId);
			redirect.append(WebServiceConstants.CANCEL_PAY_RESET_URL);
			redirect.append("?id=");
			redirect.append(order.getString("a10"));
			redirect.append("&sid=");
			redirect.append(order.getString("a12"));
			redirect.append("';");
		}
		redirect.append("</script>");
		return redirect.toString();
	}
	
	/**
	 * 银联支付通知接口（展示）
	 * @param request: request请求
	 * @param response: response响应
	 * @throws Exception
	 */
	@RequestMapping(value = "/payCallbackUni", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public String payCallbackUni(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String respMsg = request.getParameter("respMsg");
		String orderId = request.getParameter("orderId");
		StringBuffer redirect = new StringBuffer();
		redirect.append("<script>");
		if (respMsg.equals("success")) {
			redirect.append("var user = eval('(' + localStorage.getItem('MyUserInfo') + ')');location.href='");
			redirect.append(WebServiceConstants.TICKET_URL);
			redirect.append("?id=");
			redirect.append(orderId);
			redirect.append("&uid=' + user['a2'];");
		} else {
			redirect.append("location.href='");
			JSONObject order = orderService.orderDetail(orderId);
			redirect.append(WebServiceConstants.CANCEL_PAY_RESET_URL);
			redirect.append("?id=");
			redirect.append(order.getString("a10"));
			redirect.append("&sid=");
			redirect.append(order.getString("a12"));
			redirect.append("';");
		}
		redirect.append("</script>");
		return redirect.toString();
	}
	
	/**
	 * 微信支付通知接口
	 * @param request: request请求
	 * @param response: response响应
	 * @throws Exception
	 */
	@RequestMapping("/payNotifyUrl")
	public void payNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.handlePayNotifyV3(request, response);
	}
	
	/**
	 * 支付宝支付通知接口
	 * @param request: request请求
	 * @param response: response响应
	 * @throws Exception
	 */
	@RequestMapping("/payNotifyAli")
	public void payNotifyAli(HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.handlePayNotifyAli(request);
	}
	
	/**
	 * 银联支付通知接口
	 * @param request: request请求
	 * @param response: response响应
	 * @throws Exception
	 */
	@RequestMapping("/payNotifyUni")
	public void payNotifyUni(String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderService.handlePayNotifyUni(request);
	}
	
	/** 第三方支付； 
	 * @throws Exception */
	private String orderBy3rd(String payType, String orderId) throws Exception {
		// 判断是否有存在的，获取需要现金支付的支付记录
		JSONObject lease = orderService.orderDetail(orderId);
System.out.println(lease);
//  {"a1":"2","a10":"150709172534957138","a11":"2015-08-14","a12":"15070917253565139","a13":"","a14":"","a15":"",
//		"a2":"150814120710656372","a3":"150814120710656371","a4":"2015-08-14 12:07:10","a5":"0.01","a6":"1","a7":"HQ100",
// 		"a8":"2015-08-14 12:07:21","a9":"5","list":[{"a1":"16:00","a2":"08.14#五#0"}],"list1":[]}
/*
a1:订单状态：（0:订单不存在    1：非按次订单    2：待支付    3：交易成功    4：已失效     5：已取消    6：已评价   7：待评价）
a2:订单ID    a3:订单号     a4:下单时间      a5:支付金额     a6:乘车次数   a7:线路名称    a8:系统时间     a9:有效时长（默认15分钟）
a10:线路ID  a11:订单有效时长  a12,子线路ID  
list:(a1:发车时间     a2:乘坐日期       （例：08.29#五#状态（1：改签中，其他正常）,08.30#六#状态（1：改签中，其他正常））
 */

		if (lease != null && lease.has("a1") && !StringUtils.isEmpty(lease.getString("a1")) && 
				lease.getString("a1").equals("2") && lease.has("a3") && !StringUtils.isEmpty(lease.getString("a3")) && 
				lease.has("a5") && lease.getDouble("a5") > 0) {
			String orderNo = orderId, leaseId = lease.getString("a2");
			double orderAmount = lease.getDouble("a5");
			// 生产支付的请求参数
			if ("1".equals(payType)) {
				// 支付宝支付参数构建
System.out.println("支付宝支付参数构建");
				String formHtml = aliPayService.generateAliPayParam(orderNo, leaseId, String.valueOf(orderAmount));
System.out.println(formHtml);
/*
<form id="orderDetailFrom" name="orderDetailFrom" action="http://wappaygw.alipay.com/service/rest.htm?_input_charset=utf-8" method="get">
	<input type="hidden" name="sign" value="4a35db704e014d2f363ab1570bb40372"/>
	<input type="hidden" name="sec_id" value="MD5"/>
	<input type="hidden" name="v" value="2.0"/>
	<input type="hidden" name="_input_charset" value="utf-8"/>
	<input type="hidden" name="req_data" value="<auth_and_execute_req><request_token>20150817b4b588c5c9c0923493777bb0be346017</request_token></auth_and_execute_req>"/>
	<input type="hidden" name="service" value="alipay.wap.auth.authAndExecute"/>
	<input type="hidden" name="partner" value="2088511429153115"/>
	<input type="hidden" name="format" value="xml"/>
*/
				return formHtml;
			} else if ("2".equals(payType)) {
				// 银联支付参数构建
System.out.println("银联支付参数构建");
				StringBuffer sb = new StringBuffer();
				sb.append("<form action=\"");
				sb.append(SDKConfig.getConfig().getFrontRequestUrl());
				sb.append("\" method=\"post\">");
				Map<String, String> submitFromData = FrontConsumeService.handle(orderNo, leaseId, orderAmount);
System.out.println(" ============================= submitFromData ============================= ");
				Iterator<String> keys = submitFromData.keySet().iterator();
				while(keys.hasNext()) {
					String key = keys.next();
					sb.append("<input type=\"hidden\" name=\"");
					sb.append(key);
					sb.append("\" value=\"");
					sb.append(submitFromData.get(key));
					sb.append("\"/>");
				}
				sb.append("</form>");
System.out.println(" ============================= ");
/*
<form id="orderDetailFrom" action="${requestFrontUrl}" method="post">
    <!-- 以下为银联支付参数 -->
    <input type="hidden" name="txnType" id="txnType" value="${submitFromData.txnType}"/>
	<input type="hidden" name="frontUrl" id="frontUrl" value="${submitFromData.frontUrl}"/>
	<input type="hidden" name="currencyCode" id="currencyCode" value="${submitFromData.currencyCode}"/>
	<input type="hidden" name="channelType" id="channelType" value="${submitFromData.channelType}"/>
	<input type="hidden" name="merId" id="merId" value="${submitFromData.merId}"/>
	<input type="hidden" name="txnSubType" id="txnSubType" value="${submitFromData.txnSubType}"/>
	<input type="hidden" name="txnAmt" id="txnAmt" value="${submitFromData.txnAmt}"/>
	<input type="hidden" name="version" id="version" value="${submitFromData.version}"/>
	<input type="hidden" name="signMethod" id="signMethod" value="${submitFromData.signMethod}"/>
	<input type="hidden" name="backUrl" id="backUrl" value="${submitFromData.backUrl}"/>
	<input type="hidden" name="certId" id="certId" value="${submitFromData.certId}"/>
	<input type="hidden" name="encoding" id="encoding" value="${submitFromData.encoding}"/>
	<input type="hidden" name="bizType" id="bizType" value="${submitFromData.bizType}"/>
	<input type="hidden" name="signature" id="signature" value="${submitFromData.signature}"/>
	<input type="hidden" name="orderId" id="orderId" value="${submitFromData.orderId}"/>
	<input type="hidden" name="accessType" id="accessType" value="${submitFromData.accessType}"/>
	<input type="hidden" name="txnTime" id="txnTime" value="${submitFromData.txnTime}"/>
  </form>
 */
System.out.println(sb.toString());
				return sb.toString();
			}
		}
		return null;
	}
}
