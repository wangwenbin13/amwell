package com.pig84.ab.vo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * app实体bean
 * @author Administrator
 *
 */
public class AppVo_3_list implements Serializable {

	private String a1;
	private String a2;
	private String a3;
	protected List list;
	
	
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
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public String getA3() {
		return a3;
	}
	public void setA3(String a3) {
		this.a3 = a3;
	}
}
