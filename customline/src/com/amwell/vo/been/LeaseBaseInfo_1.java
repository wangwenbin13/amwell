package com.amwell.vo.been;

public class LeaseBaseInfo_1 {
	public String leaseOrderId;  //订单ID
	public String leaseOrderNo;  //订单号
	public String lineBaseId;    //线路信息ID
	public Float totalCount;     //费用总计(折前价格)
	public Integer discountRate; //优惠折扣率
	public Integer ridesInfo;    //乘车次数
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
	public String getLineBaseId() {
		return lineBaseId;
	}
	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	public Float getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Float totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}
	public Integer getRidesInfo() {
		return ridesInfo;
	}
	public void setRidesInfo(Integer ridesInfo) {
		this.ridesInfo = ridesInfo;
	}
	
	
	
	
	
}
