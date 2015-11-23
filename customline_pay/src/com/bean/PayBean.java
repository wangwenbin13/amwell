package com.bean;


/**
 * 支付宝订单详细
 * @author zhangqiang
 * @time 2015-09-16 17:16:10
 */
public class PayBean {
	

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
	public PayBean(String orderNo, String title, String desc, String price, String limitTime) {
		super();
		this.orderNo = orderNo;
		this.title = title;
		this.desc = desc;
		this.price = price;
		this.limitTime = limitTime;
	}
	public PayBean() {
		super();
	}
	
}
