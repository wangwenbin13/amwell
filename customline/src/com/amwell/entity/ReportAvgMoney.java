package com.amwell.entity;

import java.io.Serializable;

/** 
* @author 作者 : wangwenbin
* @version 创建时间：2015年9月23日 下午5:29:08 
* 平均金额
*/

public class ReportAvgMoney implements Serializable{

	private static final long serialVersionUID = -5570608394282585776L;

	/**城市**/
	private String cityName;
	
	/**日期**/
	private String orderDate;
	
	/**订单总金额**/
	private String total;
	
	/**支付总金额**/
	private String pay;
	
	/**支付数量**/
	private String payNum;
	
	/**支付平均金额**/
	private String avgpay;
	
	/**未支付总金额**/
	private String unpay;
	
	/**未支付数量**/
	private String unpayNum;
	
	/**未支付平均金额**/
	private String avgunpay;
	
	/**城市编号**/
	private String cityCode;
	
	/**省份编号**/
	private String provinceCode;
	

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}

	public String getAvgpay() {
		return avgpay;
	}

	public void setAvgpay(String avgpay) {
		this.avgpay = avgpay;
	}

	public String getUnpay() {
		return unpay;
	}

	public void setUnpay(String unpay) {
		this.unpay = unpay;
	}

	public String getUnpayNum() {
		return unpayNum;
	}

	public void setUnpayNum(String unpayNum) {
		this.unpayNum = unpayNum;
	}

	public String getAvgunpay() {
		return avgunpay;
	}

	public void setAvgunpay(String avgunpay) {
		this.avgunpay = avgunpay;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	
}
