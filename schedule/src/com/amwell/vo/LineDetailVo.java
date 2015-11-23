package com.amwell.vo;

import java.util.List;

import org.springframework.util.StringUtils;

import com.amwell.entity.base.BaseEntity;

public class LineDetailVo extends BaseEntity {

	private static final long serialVersionUID = -3576919033946817582L;

	private String lineBaseId;

	private String lineName;

	private String lineTime;

	private int lineType;

	private int lineSubType;

	private String orderPrice;

	private int lineStatus;

	private String remark;

	private List<String> stationList;

	private List<LineClassEntity> lineClassList;

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
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

	public int getLineType() {
		return lineType;
	}

	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	public int getLineSubType() {
		return lineSubType;
	}

	public void setLineSubType(int lineSubType) {
		this.lineSubType = lineSubType;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getStationList() {
		return stationList;
	}

	public void setStationList(List<String> stationList) {
		this.stationList = stationList;
	}

	public List<LineClassEntity> getLineClassList() {
		return lineClassList;
	}

	public void setLineClassList(List<LineClassEntity> lineClassList) {
		this.lineClassList = lineClassList;
	}
}
