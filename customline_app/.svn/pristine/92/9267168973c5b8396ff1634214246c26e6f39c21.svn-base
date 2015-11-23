package com.pig84.ab.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pig84.ab.vo.bean.AppVo_12;

/**
 * 
 * @author wangwenbin
 *
 * 2014-12-20
 */
/**
 * 微信支付
 */
public interface IWeiXinPayService {

	/**
	 * 构建微信订单内容（支付）包车
	 * @param valueOf
	 * @param orderNo
	 * @param userid
	 * @return
	 */
	AppVo_12 bulidPayBcOrderByWeiXin(String valueOf, String bcBiddingId, String userid,HttpServletRequest request,HttpServletResponse response,String counponTeleId);

	/**
	 * 构建微信订单内容（支付）小猪巴士
	 * @param valueOf
	 * @param orderNo
	 * @return
	 */
	AppVo_12 bulidPayOrderByWeiXin(String valueOf, String orderNo,HttpServletRequest request,HttpServletResponse response);

	/**微信支付付款返回结果（支付） 小猪巴士**/
	String successByWeiXinPay(String outTradeNo, String totalFee,String type,String thirdOrderId);

	/**微信支付付款返回结果（支付）包车**/
	String toDoSaveBcOrder(String bcLineId, String businessId, String userId,
			BigDecimal bigDecimal, int i,String counponTeleId);

	/**
	 * 注意交易单不要重复处理
	 * @param outTradeNo
	 * @return
	 */
	boolean getBcOrderByBidIdIsExits(String outTradeNo);

	/**注意交易单不要重复处理--小猪巴士**/
	boolean getLeasePayByOrderNoIsExits(String outTradeNo,String type);

	/**如果使用了优惠券，更新gf_coupon_passenger***/
	void updateGfPassenger(String counponTeleId, String orderNo);

	/**根据订单号查找对应的优惠券信息**/
	String findCouponTeleId(String outTradeNo);

	/**微信退款**/
	public int returnFromWX(String leaseOrderNo, String realReturn,HttpServletRequest request,HttpServletResponse response);

	/**新版微信支付**/
	public AppVo_12 bulidPayOrderByWeiXinNew(String toPay, String orderNo,
			HttpServletRequest request, HttpServletResponse response);

	/**包车支付**/
	public AppVo_12 bulidPayBcOrderByWeiXinNew(String toPay, String orderNo,
			String userid, HttpServletRequest request,
			HttpServletResponse response, String counponTeleId);


}
