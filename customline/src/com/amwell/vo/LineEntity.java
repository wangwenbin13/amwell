package com.amwell.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class LineEntity implements Serializable {

	private static final long serialVersionUID = -2262745977336452847L;

	/**
	 * 线路信息ID
	 */
	private String lineBaseId;

	/**
	 * 线路站点信息，多个站点之间用逗号隔开
	 */
	private String stationInfoes;

	/**
	 * 线路名称
	 */
	private String lineName;

	/**
	 * 省级编码
	 */
	private String provinceCode;

	/**
	 * 市级编码
	 */
	private String cityCode;

	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 起始区域
	 */
	private String startArea;

	/**
	 * 结束区域
	 */
	private String endArea;

	/**
	 * 线路图片
	 */
	private String linePicUrl;

	/**
	 * 预计行程时间(分)
	 */
	private int lineTime;

	/**
	 * 线路类型 0:招募 1:非招募
	 */
	private int lineType;

	/**
	 * 线路子类型 0:上下班 1:旅游
	 */
	private int lineSubType;

	/**
	 * 招募指标人数
	 */
	private int passengers;

	/**
	 * 置顶排序字段
	 */
	private int displayFlag;

	/**
	 * 状态
	 */
	private int lineStatus;

	/**
	 * 商户ID
	 */
	private String businessId;

	/**
	 * 单次原价
	 */
	private BigDecimal originalPrice;

	/**
	 * 单次现价
	 */
	private BigDecimal orderPrice;

	/**
	 * 折扣率
	 */
	private int discountRate;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 固定时间Flag
	 */
	private int fixTimeFlag;

	/**
	 * 招募固定时间
	 */
	private String recruitFixTime;

	/**
	 * 招募非固定时间
	 */
	private String recruitNoFixTime;

	/**
	 * 招募截止报名时间
	 */
	private String deadlineTime;

	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private String createOn;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 修改时间
	 */
	private String updateOn;

	/**
	 * 招募开启时间
	 */
	private String recruitStartTime;

	/**
	 * 预计行程公里
	 */
	private int lineKm;

	private String passengerTel;

	private String passengerCoupon;

	/**
	 * 用户招募Id(用于把用户招募线路转换为平台招募线路)
	 */
	private String applicationId;

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getStationInfoes() {
		return stationInfoes;
	}

	public void setStationInfoes(String stationInfoes) {
		this.stationInfoes = stationInfoes;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public int getLineTime() {
		return lineTime;
	}

	public void setLineTime(int lineTime) {
		this.lineTime = lineTime;
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

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public int getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(int displayFlag) {
		this.displayFlag = displayFlag;
	}

	public int getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
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

	public int getFixTimeFlag() {
		return fixTimeFlag;
	}

	public void setFixTimeFlag(int fixTimeFlag) {
		this.fixTimeFlag = fixTimeFlag;
	}

	public String getRecruitFixTime() {
		return recruitFixTime;
	}

	public void setRecruitFixTime(String recruitFixTime) {
		this.recruitFixTime = recruitFixTime;
	}

	public String getRecruitNoFixTime() {
		return recruitNoFixTime;
	}

	public void setRecruitNoFixTime(String recruitNoFixTime) {
		this.recruitNoFixTime = recruitNoFixTime;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

	public String getRecruitStartTime() {
		return recruitStartTime;
	}

	public void setRecruitStartTime(String recruitStartTime) {
		this.recruitStartTime = recruitStartTime;
	}

	public int getLineKm() {
		return lineKm;
	}

	public void setLineKm(int lineKm) {
		this.lineKm = lineKm;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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

	/**
	 * 线路类型:0:招募;1:非招募
	 */
	public static enum LineType {
		WORK(0, "上下班"), TRAVEL(1, "自由行"), RECRUIT(2, "招募");
		private int id;
		private String text;

		private LineType(int id, String text) {
			this.id = id;
			this.text = text;
		}

		public static String getText(int id) {
			for (LineType l : LineType.values()) {
				if (l.getId() == id) {
					return l.getText();
				}
			}
			return "";
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	/**
	 * 固定时间标志 0:固定时间 1:非固定时间
	 */
	public static enum TimeFlag {
		FIXATION(0, "固定时间"), NON_FIXATION(1, "非固定时间");
		private int id;
		private String text;

		private TimeFlag(int id, String text) {
			this.id = id;
			this.text = text;
		}

		public static String getText(int id) {
			for (TimeFlag t : TimeFlag.values()) {
				if (t.getId() == id) {
					return t.getText();
				}
			}
			return "";
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}
}
