package com.pig84.ab.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * 线路搜索条件
 * 
 * @author shawn.zheng
 *
 */
public class LineSearchCondition {

	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 一页记录数
	 */
	private int pageSize = 5;

	/**
	 * 线路类型 1:上下班 2:自由行 3:招募
	 */
	private String lineType;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 城市名
	 */
	private String cityName = "深圳市";

	/**
	 * 区域名
	 */
	private String areaName;

	/**
	 * 起点站
	 */
	private String sStation;

	/**
	 * 终点站
	 */
	private String eStation;

	/**
	 * 站点名称
	 */
	private String stationName;

	public LineSearchCondition(String currentPage, String pageSize, String lineType, String userId, String cityName,
			String areaName, String sStation, String eStation, String stationName) {
		if (currentPage != null && !currentPage.equals("")) {
			this.currentPage = Integer.valueOf(currentPage);
		}
		if (pageSize != null && !pageSize.equals("")) {
			this.pageSize = Integer.valueOf(pageSize);
		}
		this.lineType = lineType;
		this.userId = userId;
		if (!StringUtils.isEmpty(cityName)) {
			cityName = cityName.replace("市", "");
		}
		this.cityName = cityName;
		this.areaName = areaName;
		this.sStation = sStation;
		this.eStation = eStation;
		if (!StringUtils.isEmpty(stationName)) {
			stationName = stationName.trim().replace("'", "");
		}
		this.stationName = stationName;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getLineType() {
		return lineType;
	}

	public String getUserId() {
		return userId;
	}

	public String getCityName() {
		return cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public String getsStation() {
		return sStation;
	}

	public String geteStation() {
		return eStation;
	}

	public String getStationName() {
		return stationName;
	}
	
	

}
