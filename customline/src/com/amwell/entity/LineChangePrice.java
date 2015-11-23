package com.amwell.entity;
/**
 * 线路修改通票价格信息
 * @author shawn.zheng
 *
 */
public class LineChangePrice {
	/**
	 * 存储id
	 */
	private String storeId;
	
	/**
	 * 线路id
	 */
	private String lineId;
	
	/**
	 * 生效时间 格式 2015-08-05 00:00:00
	 */
	private String effectiveTime;
	
	/**
	 * 旧的线路通票价格
	 */
	private String oldPrice;
	
	/**
	 * 新的线路通票价格
	 */
	private String newPrice;
	
	/**
	 * 处理状态 0-待处理 1-已处理
	 */
	private String handleStatus;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}

	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	
}
