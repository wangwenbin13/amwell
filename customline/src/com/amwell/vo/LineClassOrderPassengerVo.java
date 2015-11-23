package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class LineClassOrderPassengerVo extends BaseEntity {

	private static final long serialVersionUID = -1365895080056121344L;

	private String orderDate;

	private String displayId;

	private String nickName;

	private String telephone;

	private String leaseOrderTime;

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getDisplayId() {
		return displayId;
	}

	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLeaseOrderTime() {
		return leaseOrderTime;
	}

	public void setLeaseOrderTime(String leaseOrderTime) {
		this.leaseOrderTime = leaseOrderTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineClassOrderPassengerVo other = (LineClassOrderPassengerVo) obj;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		return true;
	}

}
