package com.amwell.vo.linetrack;

/**
 * 线路轨迹表(百度地图获取)
 * 
 * @author shawn.zheng
 * 
 */
public class LineTrackMap {
	/**
	 * 数据库主键
	 */
	private String lineTrackId;

	/**
	 * 线路Id
	 */
	private String lineBaseId;

	/**
	 * 站点Id
	 */
	private String stationInfoId;

	/**
	 * 经度（百度坐标系）
	 */
	private Double lon;

	/**
	 * 纬度（百度坐标系）
	 */
	private Double lat;

	/**
	 * 经度（GPS坐标系）
	 */
	private Double lon_gps;

	/**
	 * 纬度（GPS坐标系）
	 */
	private Double lat_gps;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 是否是站点（0：否 1：是）
	 */
	private String isStation;

	public String getLineTrackId() {
		return lineTrackId;
	}

	public void setLineTrackId(String lineTrackId) {
		this.lineTrackId = lineTrackId;
	}

	public String getLineBaseId() {
		return lineBaseId;
	}

	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}

	public String getStationInfoId() {
		return stationInfoId;
	}

	public void setStationInfoId(String stationInfoId) {
		this.stationInfoId = stationInfoId;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon_gps() {
		return lon_gps;
	}

	public void setLon_gps(Double lonGps) {
		lon_gps = lonGps;
	}

	public Double getLat_gps() {
		return lat_gps;
	}

	public void setLat_gps(Double latGps) {
		lat_gps = latGps;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIsStation() {
		return isStation;
	}

	public void setIsStation(String isStation) {
		this.isStation = isStation;
	}

}
