package com.pig84.ab.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.annotations.SerializedName;
import com.pig84.ab.dao.impl.StationInfoDao;
import com.pig84.ab.utils.ApplicationContextHolder;
import com.pig84.ab.utils.RedisUtil;
import com.pig84.ab.utils.Utils;

import redis.clients.jedis.Jedis;

/**
 * 线路_线路基本信息表
 * @author zhangqiang
 *
 */
@SuppressWarnings("all")
public class LineBaseInfo implements java.io.Serializable {
	
	
	private static int CACHE_MAXAGE = 7200; // cache最大有效时间(2小时)，以秒为单位

	private static final String LINE_CACHE_PREFIX = "line_";
	
	private static final String FIRST_STATION_CACHE_PREFIX = "first_station1_";

	@SerializedName("a111111111")
	private String lineBaseId; 			//基本线路ID
	private String lineName;			//线路名称
	private String lineTime;			//预计行程时间(分)
	private String lineType;			//线路类型 0:招募 1:非招募
	private String lineSubType;			//线路子类型 0:上下班 1:旅游
	private String passengers;			//招募类型线路中设置的招募指标人数 非招募类型线路值空
	private String displayFlag;			//置顶排序字段
	private String lineStatus;			//线路类型：1（非招募） 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除 --->线路类型：0（招募） 0:招募前 1：招募中 2：关闭 3：删除
	private String businessId;			//商户ID （指派给商户的ID）
	private String orderPrice;			//价格(元/次)
	private String discountRate;		//折扣率
	private String remark;				//备注
	private String fixTimeFlag;			//（招募）固定时间Flag 0:固定时间 1：非固定时间
	private String recruitFixTime;		//招募固定时间 12345 表示礼拜 1，2，3，4，5
	private String recruitNoFixTime;	//招募非固定时间 例如2014年8月11号--->20140811
	private String deadlineTime;		//招募报名截至日期
	private String createBy;			//创建人
	private String createOn;			//创建时间
	private String updateBy;			//修改人
	private String updateOn;			//修改时间
	private String recruitStartTime;	//招募开始时间
	private String lineKm;				//预计行程里程
	private String linePicUrl;			//线路图片
	
	private String stationNames;        //站台集合，用“-”隔开
	private String fromStation;			//起点站台
	private String toStation;			//终点站台
	private String pCount;				//已定人数（用于招募已报名人数）
	private String isSign;				//是否报名（用于招募路线中当前用户是否报名）
	private String originalPrice;		//原始价格
	
	private String orderStartTime;      //发车时间
	private String price;               //班次价格
	private String youhui;              //优惠标识 1:有优惠 0:无优惠
	
	// XXX 因为BaseDao会根据字段名拼出getter方法，所以特意与getter方法名不一致
	private List<StationInfo> stationsList = null;
	
	public LineBaseInfo(String lineBaseId){
		this.lineBaseId = lineBaseId;
	}
	
	private void load() {
		if (loadFromCache()) {
			return;
		}
		loadFromDb();
		saveToCache();
	}
	
	public LineBaseInfo(){}
	
	private boolean loadFromCache() {
		try (Jedis cache = RedisUtil.getJedis()) {
			String key = "LINE_" + this.lineBaseId;
			Map<String, String> map = cache.hgetAll(key);
			if (map == null || map.isEmpty()) {
				return false;
			}
			String stationIdsText = map.get("stations");
			String[] stationIds = StringUtils.split(stationIdsText, ",");
			this.stationsList = new ArrayList<StationInfo>(stationIds.length);
			for (String stationId : stationIds) {
				stationsList.add(new StationInfo(stationId));
			}
			return true;
		}
	}
	
	private void loadFromDb() {
		StationInfoDao dao = ApplicationContextHolder.getBean(StationInfoDao.class);
		this.stationsList = dao.listByLineId(this.lineBaseId);
	}
	
	private void saveToCache() {
		String key = "LINE_" + this.lineBaseId;
		try (Jedis cache = RedisUtil.getJedis()) {
			StationInfoDao dao = ApplicationContextHolder.getBean(StationInfoDao.class);
			List<StationInfo> stations = dao.listByLineId(this.lineBaseId);
			List<String> ids = new ArrayList<String>();
			for (StationInfo station : stations) {
				ids.add(station.getId());
			}
			String stationIdsText = StringUtils.join(ids, ",");
			cache.hset(key, "stations", stationIdsText);
			cache.expire(key, CACHE_MAXAGE);
		}
	}
	
	public List<StationInfo> getStations() {
		if (this.stationsList == null) {
			load();
		}
		return this.stationsList;
	}
	
	public StationInfo getFirstStation(){
		StationInfoDao stationInfoDao = ApplicationContextHolder.getBean(StationInfoDao.class);
		StationInfo stationInfo = null;
		try (Jedis cache = RedisUtil.getJedis()) {
			String firstStationCacheId = FIRST_STATION_CACHE_PREFIX + this.lineBaseId;
			Map<String, String> map = cache.hgetAll(firstStationCacheId);
			if (map == null||map.isEmpty()) {
				stationInfo = stationInfoDao.getLineFirstStation(this.lineBaseId);
				if(stationInfo!=null){
					map = fillMap(stationInfo);
					cache.hmset(firstStationCacheId, Utils.removeEmpty(map));
					cache.expire(firstStationCacheId, CACHE_MAXAGE);
				}else{
					stationInfo = new StationInfo();
				}
			} else {
				double baiduLng = 0.0;
				if(!StringUtils.isEmpty(map.get("baiduLng"))){
					baiduLng = Double.valueOf(map.get("baiduLng"));
				}
				double baiduLat = 0.0;
				if(!StringUtils.isEmpty(map.get("baiduLat"))){
					baiduLat = Double.valueOf(map.get("baiduLat"));
				}
				stationInfo = new StationInfo(map.get("id"),map.get("lineId"),map.get("name"),baiduLng,baiduLat);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return stationInfo;
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
		return map;
	}
	
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getLinePicUrl() {
		return linePicUrl;
	}
	public void setLinePicUrl(String linePicUrl) {
		this.linePicUrl = linePicUrl;
	}
	public String getIsSign() {
		return isSign;
	}
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	public String getRecruitStartTime() {
		return recruitStartTime;
	}
	public void setRecruitStartTime(String recruitStartTime) {
		this.recruitStartTime = recruitStartTime;
	}
	public String getLineKm() {
		return lineKm;
	}
	public void setLineKm(String lineKm) {
		this.lineKm = lineKm;
	}
	public String getpCount() {
		return pCount;
	}
	public void setpCount(String pCount) {
		this.pCount = pCount;
	}
	public String getFromStation() {
		return fromStation;
	}
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	public String getToStation() {
		return toStation;
	}
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}
	
	
	
	public String getStationNames() {
		return stationNames;
	}
	public void setStationNames(String stationNames) {
		this.stationNames = stationNames;
	}
	public String getLineBaseId() {
		return lineBaseId;
	}
	public void setLineBaseId(String lineBaseId) {
		this.lineBaseId = lineBaseId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineTime() {
		return lineTime;
	}
	public void setLineTime(String lineTime) {
		this.lineTime = lineTime;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getLineSubType() {
		return lineSubType;
	}
	public void setLineSubType(String lineSubType) {
		this.lineSubType = lineSubType;
	}
	public String getPassengers() {
		return passengers;
	}
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	public String getLineStatus() {
		return lineStatus;
	}
	public void setLineStatus(String lineStatus) {
		this.lineStatus = lineStatus;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFixTimeFlag() {
		return fixTimeFlag;
	}
	public void setFixTimeFlag(String fixTimeFlag) {
		this.fixTimeFlag = fixTimeFlag;
	}
	public String getRecruitFixTime() {
		return recruitFixTime;
	}
	public void setRecruitFixTime(String recruitFixTime) {
		this.recruitFixTime = recruitFixTime;
	}
	public String getRecruitNoFixTime() {
		return recruitNoFixTime;
	}
	public void setRecruitNoFixTime(String recruitNoFixTime) {
		this.recruitNoFixTime = recruitNoFixTime;
	}
	public String getDeadlineTime() {
		return deadlineTime;
	}
	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public String getYouhui() {
		return youhui;
	}
	public void setYouhui(String youhui) {
		this.youhui = youhui;
	}

	
}
