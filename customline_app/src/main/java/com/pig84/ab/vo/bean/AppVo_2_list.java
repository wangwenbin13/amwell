package com.pig84.ab.vo.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pig84.ab.vo.SeatInfoTemp;

/**
 * app实体bean
 * @author Administrator
 *
 */
public class AppVo_2_list implements Serializable {

	private String a1;
	private String a2;
	protected List list;
	private List<SeatInfoTemp> seat = new ArrayList<SeatInfoTemp>();
	
	
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
	public List<SeatInfoTemp> getSeat() {
		return seat;
	}
	public void setSeat(List<SeatInfoTemp> seat) {
		this.seat = seat;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
}
