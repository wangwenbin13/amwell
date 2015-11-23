package com.amwell.vo.app;

/**
 * 乘客订单座位信息表
 */

@SuppressWarnings("all")
public class LineBusinessOrder implements java.io.Serializable {

	private String localId;					//序号ID 
	private String lineClassId;				//班次ID
	private String passengerId;				//乘客ID
	private String leaseOrderId;			//订单ID 
	private String businessId;				//商户ID
	private String tickets;					//订购张数
	private String doCount;					//已经刷卡次数
	public String getLocalId() {
		return localId;
	}
	public void setLocalId(String localId) {
		this.localId = localId;
	}
	public String getLineClassId() {
		return lineClassId;
	}
	public void setLineClassId(String lineClassId) {
		this.lineClassId = lineClassId;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getLeaseOrderId() {
		return leaseOrderId;
	}
	public void setLeaseOrderId(String leaseOrderId) {
		this.leaseOrderId = leaseOrderId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getTickets() {
		return tickets;
	}
	public void setTickets(String tickets) {
		this.tickets = tickets;
	}
	public String getDoCount() {
		return doCount;
	}
	public void setDoCount(String doCount) {
		this.doCount = doCount;
	}

	
}