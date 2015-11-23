package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class UserLineStationCount extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3972584033762122068L;

	private String address;

	private int applyCount;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(int applyCount) {
		this.applyCount = applyCount;
	}

}
