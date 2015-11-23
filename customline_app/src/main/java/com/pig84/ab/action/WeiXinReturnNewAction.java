package com.pig84.ab.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pig84.ab.service.IBookService;
import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.service.IWeiXinPayService;
import com.pig84.ab.utils.Conn;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.Json;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.PostXmlUtil;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.weixin.conf.WeiXinPayConfig;
import com.pig84.ab.weixin.handler.PrepayIdRequestHandler;
import com.pig84.ab.weixin.handler.ResponseHandler;
import com.pig84.ab.weixin.util.WXUtil;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 新版微信支付结果回调
 */
@ParentPackage("no-interceptor")
@Namespace("/app_weixinReturn")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class WeiXinReturnNewAction extends BaseAction {

	@Autowired
	private IWeiXinPayService weiXinPayService;

	@Autowired
	private IBookService bookService;

	@Autowired
	private ILoginAndRegisterService loginAndRegisterService;

	/**
	 * (新版)微信支付返回结果（支付）---小猪巴士
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "monitorWeiXinPayResult_payNew", results = { @Result(type = "json") })
	public String monitorWeiXinPayResult_payNew() throws Exception {

		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(response, request);
		resHandler.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);
		String return_code = resHandler.getParameter("return_code");// 返回状态码
		String type = "6";// 微信APP端
		if ("SUCCESS".equals(return_code.toUpperCase())) {
			// 判断签名
			if (resHandler.isTenpaySignNew()) {
				/** 商户订单号(商户传过去的订单号) */
				String out_trade_no = resHandler.getParameter("out_trade_no");
				/** 交易金额 */
				String total_fee = resHandler.getParameter("total_fee");

				// 注意交易单不要重复处理--小猪巴士
				boolean exits = weiXinPayService.getLeasePayByOrderNoIsExits(
						out_trade_no, type);

				if (exits) {
					String flag = weiXinPayService.successByWeiXinPay(
							out_trade_no, total_fee, type, null);
					if ("0".equals(flag)) {
						AppVo_4 info = bookService
								.getOrderTimeAndUserPhone(out_trade_no);// a1:手机号码
						// a2:时间串
						// a3:从哪里到哪里

						String context = "亲，您的车票 ("+info.getA2()+info.getA3()
						+ ") 买好啦!请提前5分钟在上车点等候，凭有效电子车票乘车【小猪巴士】";

						/** 判断是不是华为员工 **/
						boolean isHuaWei = loginAndRegisterService
								.judgeUserType(info.getA1());

						if (isHuaWei) {
							/** 判断是否送对应的补贴信息 **/
							AppVo_3 appV3 = loginAndRegisterService
									.juderSendAllowance(info.getA1(),
											out_trade_no);
							if (null != appV3 && appV3.getA1().equals("1")) {
								/** 发送华为对应的信息 **/
								context += "此票价由财富之舟补贴" + appV3.getA3()
										+ "元，由小猪巴士补贴" + appV3.getA2() + "元";
							}

						}
						/** 判断订单是否已存在收入统计之中，存在，说明已经发送过了，则不发送短信 **/
						int count = bookService
								.isExitsInStatTotal(out_trade_no);
						if (count <= 0) {
							new Message(context).sendTo(info.getA1());
						}
						String statistics = PropertyManage
								.get("statistics");
						/** 添加收入统计 **/
						int statu = bookService.addStatTotal(out_trade_no);
					}
				}
				resHandler
						.sendToCFT("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
			}
		}
		return null;
	}

	/**
	 * (新版)微信支付返回结果（支付）---包车
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "monitorWeiXinPayResultBc_payNew", results = { @Result(type = "json") })
	public String monitorWeiXinPayResultBc_payNew() throws Exception {

		// System.out.println("进入包车回调方法======》");
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(response, request);
		resHandler.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);
		String return_code = resHandler.getParameter("return_code");// 返回状态码
		if ("SUCCESS".equals(return_code.toUpperCase())) {
			// 判断签名
			if (resHandler.isTenpaySignNew()) {
				// /**商户订单号(商户传过去的订单号)*/
				// String out_trade_no = new
				// String(resHandler.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
				//				
				// /**交易金额*/
				// String total_fee = new
				// String(resHandler.getParameter("total_fee").getBytes("ISO-8859-1"),"UTF-8");

				/** 商户订单号(商户传过去的订单号) */
				String out_trade_no = resHandler.getParameter("out_trade_no");
				/** 交易金额 */
				String total_fee = resHandler.getParameter("total_fee");

				/** 优惠券信息 **/
				String counponTeleId = weiXinPayService
						.findCouponTeleId(out_trade_no);
				// 根据报价id查询报价总价
				AppVo_4 vo = bookService.getBcBiddingById(out_trade_no);

				// 注意交易单不要重复处理
				boolean exits = weiXinPayService
						.getBcOrderByBidIdIsExits(out_trade_no);

				if (exits) {
					String bcLineId = vo.getA1();// 包车信息id
					String businessId = vo.getA2();// 商家id
					String userId = vo.getA4();// 登录用户id

					// 保存包车订单信息
					String flag = weiXinPayService.toDoSaveBcOrder(bcLineId,
							businessId, userId, new BigDecimal(total_fee), 4,
							counponTeleId);// (APP)微信支付
					if (!"1".equals(flag)) {
						response.getWriter().println("fail");
					}
				}
				resHandler
						.sendToCFT("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
				// System.out.println("回调完成====>");
			}
		}
		return null;
	}

	/**
	 * 微信端的回调--只是改状态和发短信
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "weixinUpdateStatu", results = { @Result(type = "json") })
	public String weixinUpdateStatu() throws Exception {
		String leaseOrderNo = request.getParameter("leaseOrderNo");// 订单号
		String money = request.getParameter("money");// 支付金额
		if (StringUtils.isEmpty(leaseOrderNo)) {
			write("error");
		}
		String type = "5";// 微信端
		boolean exits = weiXinPayService.getLeasePayByOrderNoIsExits(
				leaseOrderNo, type);
		if (exits) {
			String flag = weiXinPayService.successByWeiXinPay(leaseOrderNo,
					money, type, null);
			if ("0".equals(flag)) {
				AppVo_4 info = bookService
						.getOrderTimeAndUserPhone(leaseOrderNo);// a1:手机号码
				// a2:时间串
				// a3:从哪里到哪里

				String context = "亲，您的车票 ("+info.getA2()+info.getA3()
				+ ") 买好啦!请提前5分钟在上车点等候，凭有效电子车票乘车【小猪巴士】";

				/** 判断是不是华为员工 **/
				boolean isHuaWei = loginAndRegisterService.judgeUserType(info
						.getA1());

				if (isHuaWei) {
					/** 判断是否送对应的补贴信息 **/
					AppVo_3 appV3 = loginAndRegisterService.juderSendAllowance(
							info.getA1(), leaseOrderNo);
					if (null != appV3 && appV3.getA1().equals("1")) {
						/** 发送华为对应的信息 **/
						context += "此票价由财富之舟补贴" + appV3.getA3() + "元，由小猪巴士补贴"
								+ appV3.getA2() + "元";
					}

				}
				/** 判断订单是否已存在收入统计之中，存在，说明已经发送过了，则不发送短信 **/
				int count = bookService.isExitsInStatTotal(leaseOrderNo);
				if (count <= 0) {
					new Message(context).sendTo(info.getA1());
				}
				String statistics = PropertyManage
						.get("statistics");
				/** 添加收入统计 **/
				int statu = bookService.addStatTotal(leaseOrderNo);
			}
		}
		return null;
	}

	@Action(value = "payPalReturn", results = { @Result(type = "json") })
	public String payPalReturn() {
		try {
			Enumeration en = request.getParameterNames();
			String str = "cmd=_notify-validate";
			while (en.hasMoreElements()) {
				String paramName = (String) en.nextElement();
				String paramValue = request.getParameter(paramName);
				str = str + "&" + paramName + "="
						+ URLEncoder.encode(paramValue, "utf-8");
				// 此处的编码一定要和自己的网站编码一致，不然会出现乱码，paypal回复的通知为‘INVALID’
			}
			// 建议在此将接受到的信息 str 记录到日志文件中以确认是否收到 IPN 信息
			// 将信息 POST 回给 PayPal 进行验证
			// 设置 HTTP 的头信息
			// 在 Sandbox 情况下，设置：
			String url = PropertyManage.get("paypal.conform");
			URL u = new URL(url);
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);
			uc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(str);
			pw.close();
			// 接受 PayPal 对 IPN 回发的回复信息
			BufferedReader in = new BufferedReader(new InputStreamReader(uc
					.getInputStream()));
			String res = in.readLine();
			in.close();
			// 该付款明细所有变量可参考：
			// https://www.paypal.com/IntegrationCenter/ic_ipn-pdt-variable-reference.html
			String itemName = request.getParameter("item_name");// 商品名
			String itemNumber = request.getParameter("item_number");// 购买数量
			String paymentStatus = request.getParameter("payment_status");// 交易状态
			String paymentDate = request.getParameter("payment_date");// 交易时间
			String paymentAmount = request.getParameter("mc_gross");// 交易钱数
			String paymentCurrency = request.getParameter("mc_currency");// 货币种类
			String txnId = request.getParameter("txn_id");// 交易id
			String custom = request.getParameter("custom");// 我们的订单号
			String receiverEmail = request.getParameter("receiver_email");// 收款人email
			String payerEmail = request.getParameter("payer_email");// 付款人email
			String ack = "None";

			if (res == null || res == "")
				res = "0";
			// …
			// 获取 PayPal 对回发信息的回复信息，判断刚才的通知是否为 PayPal 发出的
			if (res.equals("VERIFIED")) {
				payReturnExecutive(paymentAmount, custom, txnId);
				ack = "VERIFIED";
			} else if (res.equals("INVALID")) {
				// 非法信息，可以将此记录到您的日志文件中以备调查
				ack = "INVALID";
			} else {
				// 处理其他错误
				ack = "OTHER";
			}
		} catch (Exception e) {
		}
		return null;
	}

	private void payReturnExecutive(String total_fee, String out_trade_no,
			String thirdOrderId) {
		String type = "7";// paypal支付
		boolean exits = weiXinPayService.getLeasePayByOrderNoIsExits(
				out_trade_no, type);
		if (exits) {
			String flag = weiXinPayService.successByWeiXinPay(out_trade_no,
					total_fee, type, thirdOrderId);
			if ("0".equals(flag)) {
				AppVo_4 info = bookService
						.getOrderTimeAndUserPhone(out_trade_no);
				String context = "亲，您的车票 ("+info.getA2()+info.getA3()
				+ ") 买好啦!请提前5分钟在上车点等候，凭有效电子车票乘车【小猪巴士】";

				/** 判断订单是否已存在收入统计之中，存在，说明已经发送过了，则不发送短信 **/
				int count = bookService.isExitsInStatTotal(out_trade_no);
				if (count <= 0) {
					new Message(context).sendTo(info.getA1());
				}
				String statistics = PropertyManage
						.get("statistics");
				/** 添加收入统计 **/
				int statu = bookService.addStatTotal(out_trade_no);
			}
		}
	}
	
	@Action(value = "returnAll", results = { @Result(type = "json") })
	public String returnAll() throws Exception {
		Connection conn = Conn.get();
		QueryRunner queryRunner = new QueryRunner(true);
		String sql = " SELECT leaseOrderNo AS a1,backMoney AS a2,totalPrice AS a3,tel AS a4,type AS a5 FROM returnBack WHERE LENGTH(statu)=0 ";
		List<AppVo_6> list = queryRunner.query(conn, sql, new BeanListHandler<AppVo_6>(AppVo_6.class));
		HttpServletResponse response = ServletActionContext.getResponse();
		String leaseOrderNo = "";
		String realReturn = "";
		String totalPrice = "";
		String type = "";
		String tel = "";
		conn.setAutoCommit(false);
		if(!list.isEmpty()){
			for(int i=0;i<list.size();i++){
				type = list.get(i).getA5().trim();
				if("(APP)微信".equals(type)){
					leaseOrderNo = list.get(i).getA1().trim();
					realReturn = list.get(i).getA2().trim();
					//获取订单总价
					totalPrice = list.get(i).getA3().trim();
					tel = list.get(i).getA4();
					System.out.println(leaseOrderNo+":"+realReturn+",:"+totalPrice);
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
					
					
					BigDecimal total_fee = new BigDecimal(totalPrice).multiply(new BigDecimal("100"));
					xml.append("<total_fee>"+String.valueOf(total_fee.intValue())+"</total_fee>");//订单总金额
					prepayReqHandler.setParameter("total_fee",String.valueOf(total_fee.intValue()));
					
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
					
					if(statu==1){
						sql = " UPDATE returnBack SET statu = 1 WHERE leaseOrderNo = ? AND tel = ? ";
						List<String> params = new ArrayList<String>();
						params.add(leaseOrderNo);
						params.add(tel);
						queryRunner.update(conn, sql, params.toArray());
					}
					
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			response.getWriter().write("success");
		}
		return null;
	}

}
