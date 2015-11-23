package com.pig84.ab.vo.Payment;

import java.io.Serializable;

/** 
* @author 作者 : wangwenbin
* @version 创建时间：2015年10月8日 下午4:19:04 
* 退票订单检查结果实体类
*/
public class OrderRRZ implements Serializable{

	private static final long serialVersionUID = -6287746934033021491L;

	/**返回结果:
	 * 0:可以退票    1：离发车时间小于1小时，不可退票  2:优惠订单,不可退
	 * 3：已经退票  4:已过了发车时间,车票已过期 5:没有登陆  6:非法改签(不是购票用户) 7:座位主键为空
	 * **/
	private String result;
	
	/**支付方式
	 * 0：无 1：余额支付，2：财付通 3：银联 4：支付宝 5:微信 6:(APP)微信 7:payPal**/
	private String type;
	
	/**第三方交易订单号**/
	private String thirdparty;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getThirdparty() {
		return thirdparty;
	}

	public void setThirdparty(String thirdparty) {
		this.thirdparty = thirdparty;
	}
	
	
}
