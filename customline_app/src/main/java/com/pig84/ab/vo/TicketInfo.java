package com.pig84.ab.vo;

import com.google.gson.annotations.SerializedName;

/**
 * 车票信息
 * @author shawn.zheng
 *
 */
public class TicketInfo {
	
	/**
	 * a1:子线路ID
	 */
	@SerializedName("a1")
	private String a1;
	
	/**
	 * a2(1:有优惠  0:没有优惠)
	 */
	@SerializedName("a2")
	private String a2;
	
	/**
	 * a3:线路ID 
	 */
	@SerializedName("a3")
	private String a3;
	
	/**
	 * a4:全程距离
	 */
	@SerializedName("a4")
	private String a4;
	
	/**
	 * a5:全程时间
	 */
	@SerializedName("a5")
	private String a5;
	
	/**
	 * a6:订单价格
	 */
	@SerializedName("a6")
	private String a6;
	
	/**
	 * 主线路的起点
	 */
	@SerializedName("a7")
	private String a7;
	
	/**
	 * a8:发车时间
	 */
	@SerializedName("a8")
	private String a8;
	
	/**
	 *  a9:子线路的(上车点)起点
	 */
	@SerializedName("a9")
	private String a9;
	
	/**
	 * a10:子线路的(下车点)终点
	 */
	@SerializedName("a10")
	private String a10;
	
	/**
	 * 订单号
	 */
	@SerializedName("a11")
	private String a11;
	
	/**
	 *  a12(0:当天无车票  1:当天有车票))
	 */
	@SerializedName("a12")
	private String a12;
	
	/**
	 * 
	 * 线路价格
	 */
	@SerializedName("a13")
	private String a13;

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA8() {
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11() {
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getA12() {
		return a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public String getA13() {
		return a13;
	}

	public void setA13(String a13) {
		this.a13 = a13;
	}

	
}
