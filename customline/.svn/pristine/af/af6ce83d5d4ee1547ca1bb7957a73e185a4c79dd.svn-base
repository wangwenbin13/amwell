package com.amwell.vo;

import org.springframework.util.StringUtils;

import com.amwell.entity.base.BaseEntity;

public class RecruitLineForOuterVo extends BaseEntity {

	private static final long serialVersionUID = 3598126152634885745L;

	private String lineBaseId;

	private String recruitStartTime;

	private String startPoint;

	private String accessPoint;

	private String endPoint;

	private String orderPrice;
	
	private String lineKm;
	
	private String orderSeats;
	
	private String lineTime;
	
	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getRecruitStartTime() {
		return recruitStartTime;
	}

	public void setRecruitStartTime(String recruitStartTime) {
		this.recruitStartTime = recruitStartTime;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getAccessPoint() {
		return accessPoint;
	}

	public void setAccessPoint(String accessPoint) {
		this.accessPoint = accessPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getLineKm() {
		return lineKm;
	}

	public void setLineKm(String lineKm) {
		this.lineKm = lineKm;
	}

	public String getOrderSeats() {
		return orderSeats;
	}

	public void setOrderSeats(String orderSeats) {
		this.orderSeats = orderSeats;
	}

	public String getLineTime() {
		return lineTime;
	}

	public void setLineTime(String lineTime) {
		if(StringUtils.hasText(lineTime)){
			int tempLineTime = Integer.parseInt(lineTime);
			this.lineTime ="全程预计花费"+(tempLineTime>=60?tempLineTime/60+"小时"+(tempLineTime%60>0?tempLineTime%60+"分钟":""):tempLineTime+"分钟");
		}else{
			this.lineTime = lineTime;
		}
	}
}
