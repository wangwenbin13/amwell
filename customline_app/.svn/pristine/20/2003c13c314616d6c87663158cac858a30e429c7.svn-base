package com.pig84.ab.service;


public interface IAliPayService {

	/**构建支付宝订单内容（充值）**/
	public String bulidRechargeOrderByAli(String userid,String money);
	
	/**支付宝充值成功，添加金额**/
	public String addMoney(String orderNo);
	
	/**构建支付宝订单内容（支付）**/
	public String bulidPayOrderByAli(String money,String orderNo);
	
	/**构建支付宝订单内容（支付）包车**/
	public String bulidPayBcOrderByAli(String money,String bcBiddingId,String counponTeleId);
	
}
