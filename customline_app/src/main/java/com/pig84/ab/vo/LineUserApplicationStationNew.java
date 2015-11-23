package com.pig84.ab.vo;

/**
 *站点_用户申请站点
 */
@SuppressWarnings("all")
public class LineUserApplicationStationNew implements java.io.Serializable {

	private String appliStationId; 
	private String stationName;
	private String loni;
	private String lati;
	private String createOn;
	public String getAppliStationId() {
		return appliStationId;
	}
	public void setAppliStationId(String appliStationId) {
		this.appliStationId = appliStationId;
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
	public String getCreateOn() {
		return createOn;
	}
	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}
	
	
}
