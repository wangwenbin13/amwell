package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

/**
 * 系统活动表
 */
public class SysDiscountVo extends BaseEntity {

	private static final long serialVersionUID = -928253904556136564L;

	private String id; // 主键

	private String disprice; // 活动价格

	private String distimes; // 活动次数

	private String iswork; // 是否有效（1:有效 2:无效）

	private String createby; // 创建人

	private String createon; // 创建时间

	private String remark; // 备注

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisprice() {
		return disprice;
	}

	public void setDisprice(String disprice) {
		this.disprice = disprice;
	}

	public String getDistimes() {
		return distimes;
	}

	public void setDistimes(String distimes) {
		this.distimes = distimes;
	}

	public String getIswork() {
		return iswork;
	}

	public void setIswork(String iswork) {
		this.iswork = iswork;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getCreateon() {
		return createon;
	}

	public void setCreateon(String createon) {
		this.createon = createon;
	}
}