package com.pig84.ab.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.pig84.ab.alipay.AlipayNotify;
import com.pig84.ab.cache.UserCache;
import com.pig84.ab.service.IAliPayService;
import com.pig84.ab.service.IBookService;
import com.pig84.ab.service.ICouponService;
import com.pig84.ab.service.IDriverService;
import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.service.IRecommendAwardService;
import com.pig84.ab.service.IWeiXinPayService;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_12;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.weixin.client.ClientResponseHandler;
import com.pig84.ab.weixin.client.TenpayHttpClient;
import com.pig84.ab.weixin.conf.WeiXinPayConfig;
import com.pig84.ab.weixin.handler.RequestHandler;
import com.pig84.ab.weixin.handler.ResponseHandler;

/**
 * 支付相关
 * 
 * @author zhangqiang
 * 
 */
@ParentPackage("no-interceptor")
@Namespace("/app_pay")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class PayAction extends BaseAction {

	@Autowired
	private IAliPayService aliPayService;// 支付宝

	@Autowired
	private IWeiXinPayService weiXinPayService;

	@Autowired
	private IBookService bookService;

	@Autowired
	private ILoginAndRegisterService loginAndRegisterService;

	@Autowired
	private ICouponService giftService;

	@Autowired
	private IDriverService driverService;

	@Autowired
	private IRecommendAwardService recommendAwardService;

	/**
	 * 构建充值订单详细
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "bulidRechargeOrder", results = { @Result(type = "json") })
	public String bulidRechargeOrder() throws IOException {
		String money = request.getParameter("money");// 充值金额
		String type = request.getParameter("type");// 第三方类型（1：中国银联 2：支付宝
		// 3：财付通(微信)）
		String userid = "";
		AppVo_2 vo = new AppVo_2();

		User appUser = UserCache.getUser();
		if (appUser != null) {
			vo.setA1("1");// 已登录
			userid = appUser.getPassengerId();

			if ("2".equals(type)) {// 支付宝
				String result = aliPayService.bulidRechargeOrderByAli(userid,money);
				if ("".equals(result)) {
					vo.setA2("-1");// 系统错误
				} else {
					vo.setA2(result);// 订单内容
				}
			}

		} else {
			vo.setA1("0");// 未登录
		}
		write(vo);
		return null;
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(
			final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}

	/**
	 * 支付宝充值返回结果（充值）
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "monitorAliPayResult", results = { @Result(type = "json") })
	public String monitorAliPayResult() throws IOException {

		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

		// 商户订单号(商户传过去的订单号)
		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");

		if (AlipayNotify.verify(params)) {// 验证成功
			// ////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if (trade_status.equals("TRADE_FINISHED")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序

				// 注意：
				// 该种交易状态只在两种情况下出现
				// 1、开通了普通即时到账，买家付款成功后。
				// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				/** 添加本地金额 **/
				logger.debug("支付宝调用本地充值方法");
				String flag = aliPayService.addMoney(out_trade_no);

				// 注意：
				// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			}

			response.getWriter().println("success"); // 请不要修改或删除
			// ////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			response.getWriter().println("fail");
		}

		return null;
	}

	/**
	 * 构建支付订单
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "bulidPayOrder", results = { @Result(type = "json") })
	public String bulidPayOrder() throws IOException {
		String payType = request.getParameter("payType");// 支付类别（1.表示包车支付，空表示小猪巴士支付）
		String type = request.getParameter("type");// 支付（0：余额支付 1：中国银联 2：支付宝
		// 3:payPal支付
		// 3：财付通(微信)）
		String userid = "";// 用户id
		String telephone = "";// 用户电话
		String orderNo = request.getParameter("orderNo");// 订单号
		String counponTeleId = request.getParameter("counponTeleId");// 用户优惠券id
		String version = request.getParameter("version");// version(空:旧版本 1：新版本)

		AppVo_2 vo = new AppVo_2();
		AppVo_12 voPojo = new AppVo_12();
		User appUser = UserCache.getUser();

		if (appUser != null) {
			vo.setA1("1");// 已登录
			voPojo.setA1("1");// 已登录
			userid = appUser.getPassengerId();
			telephone = appUser.getTelephone();
		} else {
			vo.setA1("0");// 未登录
			voPojo.setA1("0");// 未登录
			if ("3".equals(type)) {
				write(voPojo);
			} else {
				write(vo);
			}
			return null;
		}

		/** 支付宝支付 **/
		if ("2".equals(type)) {

			String orderPrice_ = "0.00";
			if ("1".equals(payType)) {
				// 根据报价id查询报价总价
				AppVo_4 v_3 = bookService.getBcBiddingById(orderNo);
				orderPrice_ = v_3.getA3();

				if (null != counponTeleId && !"".equals(counponTeleId)) {
					// 获取优惠券面值
					AppVo_1 vo_1 = giftService
							.getCouponValueById(counponTeleId);
					BigDecimal leftPrice = new BigDecimal(orderPrice_)
							.subtract(new BigDecimal(vo_1.getA1()));
					orderPrice_ = leftPrice.toString();
				}
			} else {
				// 根据订单编号查询订单价格
				orderPrice_ = bookService.getOrderPrice(orderNo);
			}
			String money_ = bookService.getUserBalance(userid);

			Float orderPirce = Float.valueOf(orderPrice_);// 订单价格
			Float money = Float.valueOf(money_);// 余额
			float toPay = 0.0f;// 需要走第三方的金额

			BigDecimal b1 = new BigDecimal(Float.toString(orderPirce));
			BigDecimal b2 = new BigDecimal(Float.toString(money));
			toPay = b1.subtract(b2).floatValue();

			if ("1".equals(payType)) {// 包车支付
				String result = "-1";// 流水号

				if (toPay > 0) {
					result = aliPayService.bulidPayBcOrderByAli(String
							.valueOf(orderPirce), orderNo, counponTeleId);
					if ("-1".equals(result)) {
						vo.setA2("-1");// 系统错误
						logger.warn("支付宝支付时，获取支付宝订单号失败，包车线路id：{} 用户ID: {}",
								orderNo, userid);
					} else {
						vo.setA2(result);
					}
				} else {
					vo.setA2("-1");// 系统错误
					logger.warn("支付宝支付时，当前用户余额大于订单金额，包车线路id：{} 用户ID: {}",
							orderNo, userid);
				}
			} else {// 小猪巴士支付
				String result = "-1";// 流水号

				if (toPay > 0) {
					result = aliPayService.bulidPayOrderByAli(String
							.valueOf(toPay), orderNo);
					if ("-1".equals(result)) {
						vo.setA2("-1");// 系统错误
						logger.warn("支付宝支付时，获取支付宝订单号失败，订单号：{} 用户ID: {}",
								orderNo, userid);
					} else {
						vo.setA2(result);
					}
				} else {
					vo.setA2("-1");// 系统错误
					logger.warn("支付宝支付时，当前用户余额大于订单金额，订单号：{} 用户ID: {}", orderNo,
							userid);
				}
			}
			write(vo);
			return null;
		} else if ("3".equals(type)) {

			/** 微信支付 */
			String orderPrice_ = "0.00";
			if ("1".equals(payType)) {
				// 根据报价id查询报价总价
				AppVo_4 v_3 = bookService.getBcBiddingById(orderNo);
				orderPrice_ = v_3.getA3();
			} else {
				// 根据订单编号查询订单价格
				orderPrice_ = bookService.getOrderPrice(orderNo);
			}
			String money_ = bookService.getUserBalance(userid);

			Float orderPirce = Float.valueOf(orderPrice_);// 订单价格
			Float money = Float.valueOf(money_);// 余额
			float toPay = 0.00f;// 需要走第三方的金额

			BigDecimal b1 = new BigDecimal(Float.toString(orderPirce));
			BigDecimal b2 = new BigDecimal(Float.toString(money));
			toPay = b1.subtract(b2).floatValue();
			if ("1".equals(payType)) {// 包车支付
				if (toPay > 0) {
					if (StringUtils.isEmpty(version)) {
						voPojo = weiXinPayService.bulidPayBcOrderByWeiXin(
								String.valueOf(orderPirce), orderNo, userid,
								request, response, counponTeleId);
					} else if ("1".equals(version)) {
						// //新版微信支付
						voPojo = weiXinPayService.bulidPayBcOrderByWeiXinNew(
								String.valueOf(orderPirce), orderNo, userid,
								request, response, counponTeleId);
					}
					voPojo.setA1("1");
					logger.info("包车订单号：{}", orderNo);
					// System.out.println("支付成功--包车订单号：{}"+orderNo);
					if ("-1".equals(voPojo.getA2())) {
						logger.warn("微信支付时，获取微信订单号失败，包车线路id：{} 用户ID: {}",
								orderNo, userid);
					} else {
						if (!StringUtils.isEmpty(counponTeleId)) {
							/** 如果使用了优惠券，更新gf_coupon_passenger **/
							weiXinPayService.updateGfPassenger(counponTeleId,
									orderNo);
						}
					}
				}
			} else {// 小猪巴士支付
				if (toPay > 0) {
					if (StringUtils.isEmpty(version)) {
						voPojo = weiXinPayService.bulidPayOrderByWeiXin(String
								.valueOf(toPay), orderNo, request, response);
					} else if ("1".equals(version)) {
						// 新版微信支付
						voPojo = weiXinPayService.bulidPayOrderByWeiXinNew(
								String.valueOf(toPay), orderNo, request, response);
					}
					voPojo.setA1("1");
				}
			}
			voPojo.setA4("0");
			write(voPojo);
			// System.out.println("支付流程走完");
		} else if ("4".equals(type)) {
			payPalPrice(payType,orderNo,userid);
		}
		return null;
	}
	/**
	 * 获取价格
	 * @param payType
	 * @param orderNo
	 * @param userid
	 */
	private void payPalPrice(String payType, String orderNo, String userid) {
		/** 微信支付 */
		String orderPrice_ = "0.00";
		if ("1".equals(payType)) {
			// 根据报价id查询报价总价
			AppVo_4 v_3 = bookService.getBcBiddingById(orderNo);
			orderPrice_ = v_3.getA3();
		} else {
			// 根据订单编号查询订单价格
			orderPrice_ = bookService.getOrderPrice(orderNo);
		}
		AppVo_2 v2 = new AppVo_2();
		v2.setA1("1");
		v2.setA2(orderPrice_);
		write(v2);
	}

	/** 小猪巴士支付成功后，处理圈子用户关系信息 **/
	public void addImGroupUser(String orderNo) {
		driverService.addImGroupUser(orderNo);
	}

	/**
	 * 支付宝支付返回结果（支付）
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "monitorAliPayResult_pay", results = { @Result(type = "json") })
	public String monitorAliPayResult_pay() {
		try {

			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}

			
			//trade_no:2015081300001000700061544633
			
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//

			// 商户订单号(商户传过去的订单号)
			String out_trade_no = new String(request.getParameter(
					"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			String trade_status = new String(request.getParameter(
					"trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 交易金额
			String total_fee = new String(request.getParameter("total_fee")
					.getBytes("ISO-8859-1"), "UTF-8");

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if (AlipayNotify.verify(params)) {// 验证成功
				// ////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					// 注意：
					// 该种交易状态只在两种情况下出现
					// 1、开通了普通即时到账，买家付款成功后。
					// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					//处理回调成功以后的订单相关数据
					payReturnSuccess(out_trade_no,total_fee,"4",trade_no);
				}

				response.getWriter().println("success"); // 请不要修改或删除
				// ////////////////////////////////////////////////////////////////////////////////////////
			} else {// 验证失败
				response.getWriter().println("fail");
			}
		} catch (Exception e) {
			// FIXME Find a way to remove this try/catch block
		}

		return null;
	}

	/**
	 * 微信支付返回结果（支付）---小猪巴士
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "monitorWeiXinPayResult_pay", results = { @Result(type = "json") })
	public String monitorWeiXinPayResult_pay() throws Exception {
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);

		// 判断签名
		if (resHandler.isTenpaySign()) {
			// 通知id
			String notify_id = resHandler.getParameter("notify_id");

			// 创建请求对象
			RequestHandler queryReq = new RequestHandler(null, null);
			// 通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			// 应答对象
			ClientResponseHandler queryRes = new ClientResponseHandler();

			// 通过通知ID查询，确保通知来至财付通
			queryReq.init();
			queryReq.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);
			queryReq
					.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
			queryReq.setParameter("partner", WeiXinPayConfig.KEY_PARTNER);
			queryReq.setParameter("notify_id", notify_id);

			// 通信对象
			httpClient.setTimeOut(5);
			// 设置请求内容
			httpClient.setReqContent(queryReq.getRequestURL());
			// 后台调用
			if (httpClient.call()) {
				// 设置结果参数
				queryRes.setContent(httpClient.getResContent());
				queryRes.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);

				// 获取返回参数
				String retcode = queryRes.getParameter("retcode");

				/** 交易状态 支付结果：0—成功 **/
				String trade_state = queryRes.getParameter("trade_state");

				/** 交易模式 1-即时到账 **/
				String trade_mode = queryRes.getParameter("trade_mode");

				/** 商户订单号(商户传过去的订单号) */
				String out_trade_no = new String(request.getParameter(
						"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

				/** 交易金额 */
				String total_fee = new String(request.getParameter("total_fee")
						.getBytes("ISO-8859-1"), "UTF-8");

				String type = "6";//微信APP端
				// 判断签名及结果
				if (queryRes.isTenpaySign() && "0".equals(retcode)
						&& "0".equals(trade_state) && "1".equals(trade_mode)) {
					payReturnSuccess(out_trade_no, total_fee,type,null);
					resHandler.sendToCFT("Success");
				}
			}
		}
		return null;
	}

	/**
	 * 微信支付返回结果（支付）---包车
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "monitorWeiXinPayResultBc_pay", results = { @Result(type = "json") })
	public String monitorWeiXinPayResultBc_pay() throws Exception {
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);

		// 判断签名
		if (resHandler.isTenpaySign()) {

			// 通知id
			String notify_id = resHandler.getParameter("notify_id");

			// 创建请求对象
			RequestHandler queryReq = new RequestHandler(null, null);
			// 通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			// 应答对象
			ClientResponseHandler queryRes = new ClientResponseHandler();

			// 通过通知ID查询，确保通知来至财付通
			queryReq.init();
			queryReq.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);
			queryReq
					.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
			queryReq.setParameter("partner", WeiXinPayConfig.KEY_PARTNER);
			queryReq.setParameter("notify_id", notify_id);

			// 通信对象
			httpClient.setTimeOut(5);
			// 设置请求内容
			httpClient.setReqContent(queryReq.getRequestURL());
			// 后台调用
			if (httpClient.call()) {
				// 设置结果参数
				queryRes.setContent(httpClient.getResContent());
				queryRes.setKey(WeiXinPayConfig.KEY_PARTNER_KEY);

				// 获取返回参数
				String retcode = queryRes.getParameter("retcode");

				/** 交易状态 支付结果：0—成功 **/
				String trade_state = queryRes.getParameter("trade_state");

				/** 交易模式 1-即时到账 **/
				String trade_mode = queryRes.getParameter("trade_mode");

				/** 商户订单号(商户传过去的订单号) */
				String out_trade_no = new String(request.getParameter(
						"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

				/** 交易金额 */
				String total_fee = new String(request.getParameter("total_fee")
						.getBytes("ISO-8859-1"), "UTF-8");

				/** 优惠券信息 **/
				String counponTeleId = weiXinPayService
						.findCouponTeleId(out_trade_no);

				// 判断签名及结果
				if (queryRes.isTenpaySign() && "0".equals(retcode)
						&& "0".equals(trade_state) && "1".equals(trade_mode)) {
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
						String flag = weiXinPayService.toDoSaveBcOrder(
								bcLineId, businessId, userId, new BigDecimal(
										total_fee), 4, counponTeleId);// (APP)微信支付
						if (!"1".equals(flag)) {
							response.getWriter().println("fail");
						}
					}

					resHandler.sendToCFT("Success");
				}

			}
		}
		return null;
	}

	@Action(value = "toPayTest", results = { @Result(type = "json") })
	public String toPayTest() {
		String orderNo = request.getParameter("orderNo");// 订单号
		weiXinPayService.bulidPayOrderByWeiXinNew(String.valueOf(3), orderNo, request, response);
//		AppVo_4 info = bookService.getOrderTimeAndUserPhone(orderNo);// a1:手机号码
		return null;
	}
	
	/**处理回调成功以后的订单相关数据**/
	private int payReturnSuccess(String out_trade_no,String total_fee,String type,String trade_no){
		
		//支付方式 0：无 1：余额支付，2：财付通 3：银联 4：支付宝 5:微信 6:(APP)微信
		
		int statu = 0;
		// 注意交易单不要重复处理--小猪巴士
		boolean exits = weiXinPayService.getLeasePayByOrderNoIsExits(out_trade_no,type);
		if (exits) {
			String flag = weiXinPayService.successByWeiXinPay(out_trade_no, total_fee,type,trade_no);
			if ("0".equals(flag)) {
				AppVo_4 info = bookService.getOrderTimeAndUserPhone(out_trade_no);// a1:手机号码
				// a2:时间串
				// a3:从哪里到哪里

//				String context = "亲，您的车票 (" + info.getA3()+ ") 已经买好了，发车时间：" + info.getA2() + "，始发站："
//						+ info.getA4()+ "，请提前5-10分钟在上车点等候，凭有效电子车票乘车【小猪巴士】";
				
				String context = "亲，您的车票 ("+info.getA2()+info.getA3()
				+ ") 买好啦!请提前5分钟在上车点等候，凭有效电子车票乘车【小猪巴士】";
				
				
//				亲，您的车票 (7:30 万科城北-香年广场) 买好啦！请提前5分钟在上车点等候，凭有效电子车票乘车【小猪巴士】
					
				/** 判断是不是华为员工 **/
				boolean isHuaWei = loginAndRegisterService.judgeUserType(info.getA1());

				if (isHuaWei) {
					/** 判断是否送对应的补贴信息 **/
					AppVo_3 appV3 = loginAndRegisterService
							.juderSendAllowance(info.getA1(),
									out_trade_no);
					if (null != appV3 && appV3.getA1().equals("1")) {
						/** 发送华为对应的信息 **/
						context += "此票价由财富之舟补贴" + appV3.getA3()+ "元，由小猪巴士补贴" + appV3.getA2() + "元";
					}
				}
				/** 判断订单是否已存在收入统计之中，存在，说明已经发送过了，则不发送短信 **/
				int count = bookService.isExitsInStatTotal(out_trade_no);
				if (count <= 0) {
					new Message(context).sendTo(info.getA1());
				}
				String statistics = PropertyManage.get("statistics");
				/** 添加收入统计 **/
				statu = bookService.addStatTotal(out_trade_no);
			}
		}
			
		return statu;
	}
	
}
