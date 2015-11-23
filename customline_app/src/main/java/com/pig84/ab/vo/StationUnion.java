package com.pig84.ab.vo;

import java.io.Serializable;
/**
 * 
 * @author wangwenbin
 *
 */
/**
 * pb_station 和 line_user_application_station_new联合实体
 */
public class StationUnion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**站点ID**/
	private String id;
	
	/**站点名字**/
	private String stationName;
	
	/**经度**/
	private String loni;
	
	/**纬度**/
	private String lati;
	
	/**类型  1:发布线路   2:招募线路**/
	private String type;
	
	/**距离**/
	private String distance;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public void setLoni(String loni) {
		this.loni = loni;
	}

	public void setLati(String lati) {
		this.lati = lati;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getStationName() {
		return stationName;
	}

	public String getLoni() {
		return loni;
	}

	public String getLati() {
		return lati;
	}

	
}
