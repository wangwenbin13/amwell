package com.pig84.ab.vo.Payment;

/**
 * 上下班商品信息
 */
public class PayBean implements java.io.Serializable {

	private String orderNo;   //商品订单号
	private String title;	//商品标题
	private String desc;		//商品描述
	private String price;		//商品价格
	private String limitTime;	//支付过期时间
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
}
