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
	 * 线路名称
	 */
	private String lineName;

	/**
	 * 预计行程时间(分)
	 */
	private int lineTime;
	/**
	 * 线路类型  0:招募 1:非招募
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
	 * 价格（元/次）
	 */
//	private int orderPrice;
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
	private int recruitFixTime;
	/**
	 * 招募非固定时间
	 */
	private int recruitNoFixTime;
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

	public int getRecruitFixTime() {
		return recruitFixTime;
	}

	public void setRecruitFixTime(int recruitFixTime) {
		this.recruitFixTime = recruitFixTime;
	}

	public int getRecruitNoFixTime() {
		return recruitNoFixTime;
	}

	public void setRecruitNoFixTime(int recruitNoFixTime) {
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

	/**
	 * 线路类型:0:招募;1:非招募
	 */
	public static enum LineType {
		WORK(0, "上下班"), TRAVEL(1, "自由行"),RECRUIT(2,"招募");
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
