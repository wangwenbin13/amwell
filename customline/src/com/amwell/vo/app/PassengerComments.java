package com.amwell.vo.app;

/**
 * 乘客_乘客评论表
 */

@SuppressWarnings("all")
public class PassengerComments implements java.io.Serializable {


	private String commentId;
	private String commentTime;
	private String passengerId;
	private String lineBaseId;
	private String leaseOrderId;
	private String commentContext;
	private int starPoint;
	private String advinces;

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentTime() {
		return this.commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getLineBaseId() {
		return this.lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getLeaseOrderId() {
		return this.leaseOrderId;
	}

	public void setLeaseOrderId(String leaseOrderId) {
		this.leaseOrderId = leaseOrderId;
	}

	public String getCommentContext() {
		return this.commentContext;
	}

	public void setCommentContext(String commentContext) {
		this.commentContext = commentContext;
	}

	public int getStarPoint() {
		return starPoint;
	}

	public void setStarPoint(int starPoint) {
		this.starPoint = starPoint;
	}

	public String getAdvinces() {
		return advinces;
	}

	public void setAdvinces(String advinces) {
		this.advinces = advinces;
	}

}