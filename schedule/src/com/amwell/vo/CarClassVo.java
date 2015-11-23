package com.amwell.vo;

import org.springframework.util.StringUtils;

import com.amwell.entity.base.BaseEntity;

public class CarClassVo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8699627251903197371L;

	private String lineClassId;
	
	private String orderDate;
	
	private String lineBaseId;
	
	private String lineTime;
	
	private int lineSubType;
	
	private String startname;
	
	private String endname;

	public String getLineClassId() {
		return lineClassId;
	}

	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getLineTime() {
		return lineTime;
	}

	public void setLineTime(String lineTime) {
		if(StringUtils.hasText(lineTime)){
			int tempLineTime = Integer.parseInt(lineTime);
			this.lineTime =tempLineTime>=60?tempLineTime/60+"小时"+(tempLineTime%60>0?tempLineTime%60+"分钟":""):tempLineTime+"分钟";
		}else{
			this.lineTime = lineTime;
		}
	}

	public int getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(int lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getStartname() {
		return startname;
	}

	public void setStartname(String startname) {
		this.startname = startname;
	}

	public String getEndname() {
		return endname;
	}

	public void setEndname(String endname) {
		this.endname = endname;
	}

}
