package com.pig84.ab.vo;

import java.util.HashMap;
import java.util.Map;

import com.pig84.ab.dao.impl.StationInfoDao;
import com.pig84.ab.utils.ApplicationContextHolder;
import com.pig84.ab.utils.RedisUtil;
import com.pig84.ab.utils.Utils;

import redis.clients.jedis.Jedis;

/**
 * 站点信息
 * 
 * @author shawn.zheng
 *
 */
public class StationInfo {

	private static int CACHE_MAXAGE = 7200; // cache最大有效时间(2小时)，以秒为单位

	private static final String STATION_CACHE_PREFIX = "station1_";
	
	/**
	 * id
	 */
	private String id;

	/**
	 * 线路id
	 */
	private String lineId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 百度经纬度
	 */
	private Coord coord;
	private double baiduLng;
	private double baiduLat;

	/**
	 * gps经纬度
	 */
	//private Coord gpsCoord;
	private double gpsLng;
	private double gpsLat;

	/**
	 * 站点类型 1-上车点 0-下车点 2-途径点
	 */
	private int type;

	/**
	 * 排序序号
	 */
	private int sortNum;

	/**
	 * 可用状态 0-可用 1-不可用
	 */
	private int available;

	/**
	 * 加载站点数据
	 * 
	 * @param stationInfoDao
	 */
	public void load(String stationId) {
		StationInfoDao stationInfoDao = ApplicationContextHolder.getBean(StationInfoDao.class);
		try (Jedis cache = RedisUtil.getJedis()) {
			String cacheStationId = STATION_CACHE_PREFIX + stationId;
			Map<String,String> map = cache.hgetAll(cacheStationId);
			if (map == null||map.isEmpty()) {
				StationInfo stationInfo = stationInfoDao.getById(stationId);
				if(stationInfo!=null){
					map = fillMap(stationInfo);
					cache.hmset(cacheStationId, Utils.removeEmpty(map));
					cache.expire(cacheStationId, CACHE_MAXAGE);
					this.id = stationInfo.getId();
					this.lineId = stationInfo.getLineId();
					this.name = stationInfo.getName();
					this.baiduLng = stationInfo.getBaiduLng();
					this.baiduLat = stationInfo.getBaiduLat();
					Coord coord = new Coord(this.baiduLng, this.baiduLat);
					this.coord = coord;
				}
			} else {
				this.id = map.get("id");
				this.lineId = map.get("lineId");
				this.name = map.get("name");
				this.baiduLng = Double.valueOf(map.get("baiduLng"));
				this.baiduLat = Double.valueOf(map.get("baiduLat"));
				Coord coord = new Coord(this.baiduLng, this.baiduLat);
				this.coord = coord;
			}
		}
	}

	/**
	 * 把对象转换为map
	 * @param stationInfo
	 * @return
	 */
	private static Map<String, String> fillMap(StationInfo stationInfo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", stationInfo.getId());
		map.put("lineId", stationInfo.getLineId());
		map.put("name", stationInfo.getName());
		map.put("baiduLng",String.valueOf(stationInfo.getBaiduLng()));
		map.put("baiduLat", String.valueOf(stationInfo.getBaiduLat()));
		return map;
	}

	StationInfo(String id, String lineId, String name, double baiduLng,double baiduLat) {
		super();
		this.id = id;
		this.lineId = lineId;
		this.name = name;
		this.baiduLng = baiduLng;
		this.baiduLat = baiduLat;
		Coord coord = new Coord(this.baiduLng,this.baiduLat);
		this.coord=coord;
	}
	
	public StationInfo(String id){
		this.id = id;
	}
	
	public StationInfo(){}

	public String getId() {
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public double getBaiduLng() {
		return baiduLng;
	}

	public void setBaiduLng(double baiduLng) {
		this.baiduLng = baiduLng;
	}

	public double getBaiduLat() {
		return baiduLat;
	}

	public void setBaiduLat(double baiduLat) {
		this.baiduLat = baiduLat;
	}

	public double getGpsLng() {
		return gpsLng;
	}

	public void setGpsLng(double gpsLng) {
		this.gpsLng = gpsLng;
	}

	public double getGpsLat() {
		return gpsLat;
	}

	public void setGpsLat(double gpsLat) {
		this.gpsLat = gpsLat;
	}

	public String getName() {
		if(this.name==null){
			load(this.id);
		}
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

}
