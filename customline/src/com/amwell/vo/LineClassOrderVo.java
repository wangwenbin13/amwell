package com.amwell.vo;

import com.amwell.entity.base.BaseEntity;

public class LineClassOrderVo extends BaseEntity {

	private static final long serialVersionUID = 3377762986416454490L;

	private String localId;

	private String leaseOrderId;

	private String leaseOrderNo;

	private String lineClassId;

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getLeaseOrderId() {
		return leaseOrderId;
	}

	public void setLeaseOrderId(String leaseOrderId) {
		this.leaseOrderId = leaseOrderId;
	}

	public String getLeaseOrderNo() {
		return leaseOrderNo;
	}

	public void setLeaseOrderNo(String leaseOrderNo) {
		this.leaseOrderNo = leaseOrderNo;
	}

	public String getLineClassId() {
		return lineClassId;
	}

	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}

}
