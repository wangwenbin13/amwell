package com.pig84.ab.vo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class QueryTicketResult {
	
	/**
	 * a1(0:没有登录 1:登录)
	 */
	@SerializedName("a1")
	private String a1;
	
	/**
	 * a2:日期
	 */
	@SerializedName("a2")
	private String a2;
	
	/**
	 * 车票列表
	 */
	@SerializedName("list")
	private List<TicketInfo> list;

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

	public List<TicketInfo> getList() {
		return list;
	}

	public void setList(List<TicketInfo> list) {
		this.list = list;
	}
	
	

}
