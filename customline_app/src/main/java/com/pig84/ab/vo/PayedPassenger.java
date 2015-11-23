package com.pig84.ab.vo;
/**
 * 已购票的乘客信息
 * 
 * @author shawn.zheng
 *
 */
public class PayedPassenger {
	
	/**
	 * 电话号码
	 */
	private String telephone;
	
	/**
	 * 上车点名称
	 */
	private String ustationName;
	
	/**
	 * 下车点名称
	 */
	private String dstationName;
	
	/**
	 * 站点名称列表
	 */
	private String stationNameArray;
	
	/**
	 * 起始站名
	 */
	private String startStationName;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getUstationName() {
		return ustationName;
	}

	public void setUstationName(String ustationName) {
		this.ustationName = ustationName;
	}

	public String getDstationName() {
		return dstationName;
	}

	public void setDstationName(String dstationName) {
		this.dstationName = dstationName;
	}

	public String getStationNameArray() {
		return stationNameArray;
	}

	public void setStationNameArray(String stationNameArray) {
		this.stationNameArray = stationNameArray;
	}

	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	
	
}
