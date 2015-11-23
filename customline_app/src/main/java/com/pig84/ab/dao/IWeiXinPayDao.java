package com.pig84.ab.dao;

import java.math.BigDecimal;

import com.pig84.ab.vo.BcOrderEntiry;

/**
 * 
 * @author wangwenbin
 *
 * 2014-12-20
 */
/**
 * 微信支付
 */
public interface IWeiXinPayDao {

	/**微信支付付款返回结果（支付）**/
	String successByWeiXinPay(String orderNo, String money,String type,String thirdOrderId);

	/**付款返回结果（支付）包车**/
	String saveBcOrder(BcOrderEntiry order);

	/**
	 * 注意交易单不要重复处理
	 * @param bcOrderNo
	 * @return
	 */
	boolean getBcOrderByBidIdIsExits(String bcOrderNo);

	/**注意交易单不要重复处理--小猪巴士**/
	boolean getLeasePayByOrderNoIsExits(String orderNo,String type);

	/**如果使用了优惠券，更新gf_coupon_passenger***/
	void updateGfPassenger(String counponTeleId, String orderNo);

	/**根据订单号查找对应的优惠券信息**/
	String findCouponTeleId(String outTradeNo);

	/**根据bcLineId查找对应的订单价格**/
	BigDecimal getOrderPrice(String bcLineId,String businessId);

}
