package com.amwell.vo;

/**
 * 
 * @author shawn.zheng
 *
 */
public class LineStationVo {
	private static final long serialVersionUID = 1828497655367644666L;

	/**
	 * 站点ID
	 */
	private String stationInfoId;

	/**
	 * 站点名称
	 */
	private String stationName;

	/**
	 * 到达时刻
	 */
	private String arriveTime;

	/**
	 * 站点名后缀
	 */
	private String stationSuffix;

	/**
	 * 纬度
	 */
	private String loni;

	/**
	 * 经度
	 */
	private String lati;

	private String loni_gps;

	private String lati_gps;

	/**
	 * 站点图片URL
	 */
	private String picUrl;

	/**
	 * 停靠站点标志 0:停靠 1:不停靠
	 */
	private int flag;

	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private String createOn;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private String updateOn;

	/**
	 * 物理地址
	 */
	private String address;

	/**
	 * 线路类型 1-上车点 0-下车点 2-引导点
	 */
	private String stationType;

	private String tipdesc;

	public String getStationInfoId() {
		return stationInfoId;
	}

	public void setStationInfoId(String stationInfoId) {
		this.stationInfoId = stationInfoId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLoni() {
		return loni;
	}

	public void setLoni(String loni) {
		this.loni = loni;
	}

	public String getLati() {
		return lati;
	}

	public void setLati(String lati) {
		this.lati = lati;
	}

	public String getLoni_gps() {
		return loni_gps;
	}

	public void setLoni_gps(String loniGps) {
		loni_gps = loniGps;
	}

	public String getLati_gps() {
		return lati_gps;
	}

	public void setLati_gps(String latiGps) {
		lati_gps = latiGps;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStationType() {
		return stationType;
	}

	public void setStationType(String stationType) {
		this.stationType = stationType;
	}

	public String getStationSuffix() {
		return stationSuffix;
	}

	public void setStationSuffix(String stationSuffix) {
		this.stationSuffix = stationSuffix;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getTipdesc() {
		return tipdesc;
	}

	public void setTipdesc(String tipdesc) {
		this.tipdesc = tipdesc;
	}

}
