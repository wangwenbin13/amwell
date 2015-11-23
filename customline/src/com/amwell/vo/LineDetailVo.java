package com.amwell.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 线路详情VO（上下班和自由行）
 * 
 * @author Administrator
 * 
 */
public class LineDetailVo {

	/**
	 * 线路信息ID
	 */
	private String lineBaseId;

	/**
	 * 线路名称
	 */
	private String lineName;

	private String provinceCode;

	private String cityCode;

	/**
	 * 起始区域
	 */
	private String startArea;

	/**
	 * 结束区域
	 */
	private String endArea;

	private String linePicUrl;

	private List<StationInfo> stationList;

	/**
	 * 预计行程时间(分)
	 */
	private int lineTime;

	/**
	 * 预计行程公里
	 */
	private int lineKm;

	/**
	 * 线路类型 0:招募 1:非招募
	 */
	private int lineType;

	/**
	 * 线路子类型 0:上下班 1:旅游
	 */
	private int lineSubType;

	// 班次信息
	private List<LineClassVo> lineClassList;

	/**
	 * 单次原价
	 */
	private String originalPrice;

	/**
	 * 单次现价
	 */
	private String orderPrice;

	/**
	 * 折扣率
	 */
	private int discountRate;

	/**
	 * 备注
	 */
	private String remark;

	private String passengerTel;

	private String passengerCoupon;

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

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getStartArea() {
		return startArea;
	}

	public void setStartArea(String startArea) {
		this.startArea = startArea;
	}

	public String getEndArea() {
		return endArea;
	}

	public void setEndArea(String endArea) {
		this.endArea = endArea;
	}

	public String getLinePicUrl() {
		return linePicUrl;
	}

	public void setLinePicUrl(String linePicUrl) {
		this.linePicUrl = linePicUrl;
	}

	public List<StationInfo> getStationList() {
		return stationList;
	}

	public void setStationList(List<StationInfo> stationList) {
		this.stationList = stationList;
	}

	public int getLineTime() {
		return lineTime;
	}

	public void setLineTime(int lineTime) {
		this.lineTime = lineTime;
	}

	public int getLineKm() {
		return lineKm;
	}

	public void setLineKm(int lineKm) {
		this.lineKm = lineKm;
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

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<LineClassVo> getLineClassList() {
		return lineClassList;
	}

	public void setLineClassList(List<LineClassVo> lineClassList) {
		this.lineClassList = lineClassList;
	}

	public String getPassengerTel() {
		return passengerTel;
	}

	public void setPassengerTel(String passengerTel) {
		this.passengerTel = passengerTel;
	}

	public String getPassengerCoupon() {
		return passengerCoupon;
	}

	public void setPassengerCoupon(String passengerCoupon) {
		this.passengerCoupon = passengerCoupon;
	}

}
