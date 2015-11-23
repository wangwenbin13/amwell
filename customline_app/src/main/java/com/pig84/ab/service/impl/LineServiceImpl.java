package com.pig84.ab.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.ILineDao;
import com.pig84.ab.dao.IMapDao;
import com.pig84.ab.dao.IStationInfoDao;
import com.pig84.ab.dao.impl.RePeat;
import com.pig84.ab.service.ILineService;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.Coord;
import com.pig84.ab.vo.LineBaseInfo;
import com.pig84.ab.vo.LineSearchCondition;
import com.pig84.ab.vo.LineSplitInfo;
import com.pig84.ab.vo.LineUnion;
import com.pig84.ab.vo.ParamVo;
import com.pig84.ab.vo.PassengerSearchLine;
import com.pig84.ab.vo.Rect;
import com.pig84.ab.vo.StationInfo;
import com.pig84.ab.vo.StationUnion;
import com.pig84.ab.vo.bean.AppVo3_Comp;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_14;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_Comp;

/**
 * 线路相关
 * 
 * @author zhangqiang
 *
 */
@Service("lineService")
@SuppressWarnings("unchecked")
public class LineServiceImpl implements ILineService {
	
	private static final Comparator<StationUnion> STATION_UNION_COMPARTOR = new Comparator<StationUnion>() {
		@Override
		public int compare(StationUnion o1, StationUnion o2) {
			double c1 = Double.valueOf(o1.getDistance());
			double c2 = Double.valueOf(o2.getDistance());
			if(c1>c2){
				return 1;
			}else if(c1==c2){
				return 0;
			}else{
				return -1;
			}
		}

	};

	@Autowired
	private ILineDao lineDao;

	@Autowired
	private IStationInfoDao stationInfoDao;

	@Autowired
	private IMapDao mapDao;

	@Autowired
	private RePeat rePeat;

	/**
	 * 获取线路列表
	 */
	public List<LineBaseInfo> getLineInfoByPage(LineSearchCondition searchCondition) {
		return lineDao.getLineInfoByPage(searchCondition);
	}

	/** 获取城市列表 **/
	public List<AppVo_1> getCityList(String cityName) {
		return lineDao.getCityList(cityName);
	}

	/** 一般用户或没登陆用户 **/
	public List<LineBaseInfo> getLineInfoByPageNotIncludeHW(LineSearchCondition searchCondition) {
		return lineDao.getLineInfoByPageNotIncludeHW(searchCondition);
	}

	/** 获取线路发车时间 **/
	public String getStartTime(String linebaseid) {
		return lineDao.getStartTime(linebaseid);
	}

	/** 获取线路的条数 **/
	@Override
	public int queryLineCount(boolean flag, String lineType, String cityName, String areaName, String stationName) {
		return lineDao.queryLineCount(flag, lineType, cityName, areaName, stationName);
	}

	/**************************************************************************
	 * 新增接口（V2.0）
	 ***************************************************************************/

	/** 购票历史 **/
	public List<AppVo_15> getHistoryLine(String imei, Coord coord, String cityName, String passengerid) {
		return lineDao.getHistoryLine(imei, coord, cityName, passengerid);
	}

	/** 拆分线路列表 **/
	public List<AppVo_14> getSplitLine(ParamVo paramVo) {
		addPassengerSearchLine(paramVo);
		return searchSplitLine(paramVo, 1000);
	}

	/** 添加乘客线路搜索记录 **/
	private int addPassengerSearchLine(ParamVo paramVo) {
		// 先判断数据是否存在--存在了则不添加
		int status = lineDao.isexist(paramVo);
		if (0 == status) {
			PassengerSearchLine pa = new PassengerSearchLine(paramVo.getsName(),paramVo.getsLon(),paramVo.getsLat(),paramVo.geteName(),paramVo.geteLon(),paramVo.geteLat(),MyDate.Format.DATETIME.now());
			status = lineDao.addPassengerSearchLine(pa);
		}
		return status;
	}

	/** 组合stationVo和aroundVo(即:精确查找和范围查找)-去掉重复数据 **/
	private List<AppVo_14> appendNotSameList(List<AppVo_14> stationVo, List<AppVo_14> aroundVo) {
		List<AppVo_14> list = new ArrayList<AppVo_14>();
		if (null == stationVo || stationVo.size() == 0) {
			stationVo = new ArrayList<AppVo_14>();
		}
		list.addAll(stationVo);
		if (null == aroundVo || aroundVo.size() == 0) {
			aroundVo = new ArrayList<AppVo_14>();
		}
		list.addAll(aroundVo);
		for (int i = 0; i < list.size(); i++) // 外循环是循环的次数
		{
			for (int j = list.size() - 1; j > i; j--) // 内循环是 外循环一次比较的次数
			{

				if (list.get(i).getA9().equals(list.get(j).getA9())) {
					list.remove(j);
				}

			}
		}
		return list;
	}

	/** 组合stationVo和aroundVo(即:精确查找和范围查找)-去掉重复数据 **/
	private List<LineUnion> appendNotSameListAndUserApp(List<LineUnion> stationVo, List<LineUnion> aroundVo) {
		List<LineUnion> list = new ArrayList<LineUnion>();
		if (null == stationVo || stationVo.size() == 0) {
			stationVo = new ArrayList<LineUnion>();
		}
		list.addAll(stationVo);
		if (null == aroundVo || aroundVo.size() == 0) {
			aroundVo = new ArrayList<LineUnion>();
		}
		list.addAll(aroundVo);
		for (int i = 0; i < list.size(); i++) // 外循环是循环的次数
		{
			for (int j = list.size() - 1; j > i; j--) // 内循环是 外循环一次比较的次数
			{

				if (list.get(i).getId().equals(list.get(j).getId())) {
					list.remove(j);
				}

			}
		}
		return list;
	}

	/** 把两个集合组合成新数据 **/
	private AppVo_2 duplicate(List<LineSplitInfo> sids, List<LineSplitInfo> aroundSids) {
		AppVo_2 vo = new AppVo_2();
		List<LineSplitInfo> list = new ArrayList<LineSplitInfo>();
		if (null == sids || sids.size() == 0) {
			sids = new ArrayList<LineSplitInfo>();
		}
		if (null == aroundSids || aroundSids.size() == 0) {
			aroundSids = new ArrayList<LineSplitInfo>();
		}
		list.addAll(sids);
		list.addAll(aroundSids);
		String lineBaseIds = "";
		String stations = "";
		/** 拼接站点字符串 **/
		if (null != list && list.size() > 0) {
			for (LineSplitInfo v1 : list) {
				lineBaseIds += v1.getLinebaseid() + ",";
				stations += v1.getBstation() + "," + v1.getEstation() + ",";
			}
		}
		/** 去重 **/
		String a1 = rePeat.quChong(lineBaseIds);
		String a2 = rePeat.quChong(stations);
		vo.setA1(a1);
		vo.setA2(a2);
		return vo;
	}

	/** 把两个集合组合成新数据 **/
	private AppVo_3 duplicateLineUnion(List<LineUnion> sids) {
		AppVo_3 vo = new AppVo_3();
		List<LineUnion> list = new ArrayList<LineUnion>();
		if (null == sids || sids.size() == 0) {
			sids = new ArrayList<LineUnion>();
		}
		list.addAll(sids);
		String lineBaseIds = "";
		String stations = "";
		String ids = "";
		/** 拼接站点字符串 **/
		if (null != list && list.size() > 0) {
			for (LineUnion v1 : list) {
				if (!StringUtils.isEmpty(v1.getLinebaseid())) {
					lineBaseIds += v1.getLinebaseid() + ",";
				}
				if (!StringUtils.isEmpty(v1.getBstation()) && !StringUtils.isEmpty(v1.getEstation())) {
					stations += v1.getBstation() + "," + v1.getEstation() + ",";
				}
				if (!StringUtils.isEmpty(v1.getId())) {
					ids += v1.getId() + ",";
				}
			}
		}
		/** 去重 **/
		String a1 = rePeat.quChong(lineBaseIds);
		String a2 = rePeat.quChong(stations);
		String a3 = rePeat.quChong(ids);
		vo.setA1(a1);
		vo.setA2(a2);
		vo.setA3(a3);
		return vo;
	}

	/** 附近站点搜索(拆分线路) **/
	public List<AppVo_14> getNearSplitLine(ParamVo paramVo) {

		int maxBusScope = 800; // 单位：米
		List<AppVo_14> list = searchNearSplitLine(paramVo, maxBusScope);
		if (null == list || list.size() == 0) {
			list = searchNearSplitLine(paramVo, 1000);
		}
		return list;
	}

	/** 根据距离搜索附近站点(拆分线路) **/
	private List<AppVo_14> searchNearSplitLine(ParamVo paramVo, int maxBusScope) {
		// 起点(根据经纬度)
		List<StationInfo> poiStar = null;
		// 起点的经纬度范围
		if(null==paramVo){
			return null;
		}
		if(StringUtils.isEmpty(paramVo.getsLat()) || StringUtils.isEmpty(paramVo.getsLon())){
			return null;
		}
		Coord coord = Coord.valueOf(paramVo.getsLon(), paramVo.getsLat());
		Rect sRect = coord.aroundRect(maxBusScope);
		// 根据经纬度范围查找站点信息
		poiStar = mapDao.queryStationByLatLon(sRect, paramVo.getCityName());
		List<AppVo_3> list = null;
		if (!StringUtils.isEmpty(paramVo.getsName())) {
			list = stationInfoDao.searchStation(paramVo.getsName());
			if (null == list || list.size() == 0) {
				return null;
			}
		}
		// 找出存在这些站点的线路
		List<LineSplitInfo> sids = lineDao.getNearSplitLine(poiStar, list, paramVo);
		String lineBaseIds = "";
		if (null != sids && sids.size() > 0) {
			for (LineSplitInfo linSplitInfo : sids) {
				lineBaseIds += linSplitInfo.getLinebaseid() + ",";
			}
			lineBaseIds = rePeat.quChong(lineBaseIds);
		} else {
			return null;
		}
		// 根据站点线路ID获取对应的线路信息
		List<LineBaseInfo> lineBaseInfos = lineDao.queryLineBaseInfoById(lineBaseIds, null);
		if (null == lineBaseIds || lineBaseInfos.size() == 0) {
			return null;
		}
		// 把两个集合组合成新数据
		AppVo_2 vo2 = duplicate(sids, null);

		// 获取所有线路的起点
		Map<String, Object> stationMap = rePeat.queryLineBaseInfoStartStation(lineBaseInfos);
		String startStationList = (String) stationMap.get("startStation");
		// 增加了单独起点标识的新集合
		lineBaseInfos = (List<LineBaseInfo>) stationMap.get("lineBaseInfos");

		// 获得需要查询站点名称的所有站点ID
		String allStationIds = rePeat.quChong(vo2.getA2() + startStationList);
		// 以站点ID为key,站点为value的map
		Map<String, StationInfo> maps = new HashMap<String, StationInfo>();

		// 根据站点ID获取站点信息
		List<StationInfo> stationInfos = stationInfoDao.queryStationById(allStationIds);

		if (null != stationInfos && stationInfos.size() > 0) {
			for (StationInfo StationInfo : stationInfos) {
				maps.put(StationInfo.getId(), StationInfo);
			}
		}

		// 线路ID为key,线路信息为value的map
		Map<String, LineBaseInfo> lineStartMap = new HashMap<String, LineBaseInfo>();
		if (null != lineBaseInfos && lineBaseInfos.size() > 0) {
			for (LineBaseInfo lineBaseInfo : lineBaseInfos) {
				// 线路ID--始发站点
				lineStartMap.put(lineBaseInfo.getLineBaseId(), lineBaseInfo);
			}
		}

		// 组装数据(根据站查找)
		List<AppVo_14> stationVo = RePeat.queryPutValueTogether(sids, maps, lineStartMap, 0, paramVo);
		if (null != stationVo && stationVo.size() > 0) {
			// 对集合对象进行排序
			AppVo3_Comp comparator = new AppVo3_Comp();
			Collections.sort(stationVo, comparator);
		}
		return stationVo;
	}

	/** 根据范围搜索 **/
	private List<AppVo_14> searchSplitLine(ParamVo paramVo, int maxBusScope) {

		// 根据名字完全匹配成功
		// 起点(根据名字)
		List<StationInfo> sList = stationInfoDao.queryStationByName(paramVo.getsName());
		// 终点(根据名字)
		List<StationInfo> eList = stationInfoDao.queryStationByName(paramVo.geteName());

		// 起点(招募线路) --根据名字
		// List<LineUserApplicationStationNew> appsList =
		// lineDao.queryLineUserApplicationStationNewByName(paramVo.getsName());
		// 终点(招募线路) --根据名字
		// List<LineUserApplicationStationNew> appeList =
		// lineDao.queryLineUserApplicationStationNewByName(paramVo.geteName());

		/** 上下班车最大搜索范围 **/
		// int maxBusScope = mapDao.queryMaxBusScope(); //单位：公里

		Integer sourceBy = 0;// 排序依据 0:没有 1:起点经纬度 2:终点经纬度
		// 起点(根据经纬度)
		List<StationInfo> poiStar = null;
		// (招募)起点
		// List<LineUserApplicationStationNew> poiAppStar = null;
		// 起点的经纬度范围
		Coord sCoord = Coord.valueOf(paramVo.getsLon(), paramVo.getsLat());
		Rect sRect = sCoord == null ? null : sCoord.aroundRect(maxBusScope);

		if ("0".equals(paramVo.getsLat()) || "0".equals(paramVo.getsLon())) {
			// 起点经纬度为0,只根据名字查找
			poiStar = sList;
		} else {
			// 根据经纬度范围查找站点信息
			poiStar = mapDao.queryStationByLatLon(sRect, paramVo.getCityName());
		}

		// 终点(根据经纬度)
		List<StationInfo> poiEnd = null;
		// (招募)终点
		// 终点的经纬度范围
		Coord eCoord = Coord.valueOf(paramVo.geteLon(), paramVo.geteLat());
		Rect eRect = eCoord == null ? null : eCoord.aroundRect(maxBusScope);
		
		if ("0".equals(paramVo.geteLat()) || "0".equals(paramVo.geteLon())) {
			// 终点经纬度为0,只根据名字查找
			poiEnd = eList;
			// poiAppEnd = appeList;
		} else {
			// 根据经纬度范围查找站点信息
			poiEnd = mapDao.queryStationByLatLon(eRect, paramVo.getCityName());
			// poiAppStar =
			// mapDao.queryLineUserApplicationStationNewByLatLon(s_values,paramVo.getCityName());
		}

		List<LineSplitInfo> sids = null;// (根据起点,终点)线路拆分表line_split_info信息

		List<LineSplitInfo> aroundSids = null;// (范围内的)线路拆分表line_split_info信息

		// null;//(根据起点,终点)线路拆分表line_user_application_station_new信息
		//
		// List<LineUserApplicationStationNew> aroundAppSids =
		// null;//(范围内的)线路拆分表line_user_application_station_new信息

		// 根据传入的参数判断搜索条件
		if (StringUtils.isEmpty(paramVo.getsName()) || StringUtils.isEmpty(paramVo.geteName())) {
			// 起点或终点有一个为空

			if (!StringUtils.isEmpty(paramVo.getsName())) {
				// 起点不为空

				// 根据起点信息和条件获得线路拆分表line_split_info的主键ID：sid不重复
				sids = lineDao.querySplieSid(sList, 1, paramVo.getFlag());
				aroundSids = lineDao.querySplieSid(poiStar, 1, paramVo.getFlag());
				sourceBy = 1;
			} else {
				// 终点不为空

				// 根据终点信息和条件获得线路拆分表line_split_info的主键ID：sid不重复
				sids = lineDao.querySplieSid(eList, 2, paramVo.getFlag());
				aroundSids = lineDao.querySplieSid(poiEnd, 2, paramVo.getFlag());
				sourceBy = 2;
			}
		} else {
			// 根据起点和终点查询线路拆分表line_split_info的主键ID：sid不重复 --起点终点结果都不为空
			if ((null == sList || sList.size() == 0) && null != eList && eList.size() > 0) {
				// sids = lineDao.querySplieSid(eList,2,paramVo.getFlag());
			} else if (null != sList && sList.size() > 0 && (null == eList || eList.size() == 0)) {
				// sids = lineDao.querySplieSid(sList,1,paramVo.getFlag());
			} else {
				if (null != sList && sList.size() > 0 && null != eList && eList.size() > 0) {
					sids = lineDao.querySplieSid(sList, eList, paramVo.getFlag());
				}
			}
			if ((null == poiStar || poiStar.size() == 0) && null != poiEnd && poiEnd.size() > 0) {
				// aroundSids =
				// lineDao.querySplieSid(poiEnd,2,paramVo.getFlag());
			} else if (null != poiStar && poiStar.size() > 0 && (null == poiEnd || poiEnd.size() == 0)) {
				// aroundSids =
				// lineDao.querySplieSid(poiStar,1,paramVo.getFlag());
			} else {
				if (null != poiStar && poiStar.size() > 0 && null != poiEnd && poiEnd.size() > 0) {
					aroundSids = lineDao.querySplieSid(poiStar, poiEnd, paramVo.getFlag());
				}
			}

		}

		// 把两个集合组合成新数据
		AppVo_2 vo2 = duplicate(sids, aroundSids);

		// 根据站点线路ID获取对应的线路信息
		List<LineBaseInfo> lineBaseInfos = lineDao.queryLineBaseInfoById(vo2.getA1(), null);
		if (null == lineBaseInfos || lineBaseInfos.size() == 0) {
			return null;
		}

		// 获取所有线路的起点
		Map<String, Object> stationMap = rePeat.queryLineBaseInfoStartStation(lineBaseInfos);
		String startStationList = (String) stationMap.get("startStation");
		// 增加了单独起点标识的新集合
		lineBaseInfos = (List<LineBaseInfo>) stationMap.get("lineBaseInfos");

		// 获得需要查询站点名称的所有站点ID
		String allStationIds = rePeat.quChong(vo2.getA2() + startStationList);

		// 根据站点ID获取站点信息
		List<StationInfo> stationInfos = stationInfoDao.queryStationById(allStationIds);

		// 以站点ID为key,站点为value的map
		Map<String, StationInfo> maps = new HashMap<String, StationInfo>();

		if (null != stationInfos && stationInfos.size() > 0) {
			for (StationInfo StationInfo : stationInfos) {
				maps.put(StationInfo.getId(), StationInfo);
			}
		}

		// 线路ID为key,线路信息为value的map
		Map<String, LineBaseInfo> lineStartMap = new HashMap<String, LineBaseInfo>();
		if (null != lineBaseInfos && lineBaseInfos.size() > 0) {
			for (LineBaseInfo lineBaseInfo : lineBaseInfos) {
				// 线路ID--始发站点
				lineStartMap.put(lineBaseInfo.getLineBaseId(), lineBaseInfo);
			}
		}

		// 组装数据(根据站查找)
		List<AppVo_14> stationVo = RePeat.queryPutValueTogether(sids, maps, lineStartMap, sourceBy, paramVo);
		// 组装数据(根据范围查找)
		List<AppVo_14> aroundVo = RePeat.queryPutValueTogether(aroundSids, maps, lineStartMap, sourceBy, paramVo);
		// 组合stationVo和aroundVo(即:精确查找和范围查找)-去掉重复数据
		List<AppVo_14> voList = appendNotSameList(stationVo, aroundVo);
		if (null != voList && voList.size() > 0) {
			// 对集合对象进行排序
			// 根据时间排序
			AppVo3_Comp comparator1 = new AppVo3_Comp();
			Collections.sort(voList, comparator1);
			// 根据距离排序
			AppVo_Comp comparator = new AppVo_Comp();
			Collections.sort(voList, comparator);
		}
		return voList;
	}

	/** 商家介绍 **/
	public AppVo_1 geBusinessRemark(String businessId) {
		return lineDao.geBusinessRemark(businessId);
	}

	/** 申请添加班车 **/
	public int addApplyClass(String passengerId, String lineClassId) {
		return lineDao.addApplyClass(passengerId, lineClassId);
	}

	/** 根据线路ID获取站点信息 **/
	public List<AppVo_5> getStationsByLineId(String lineId) {
		return lineDao.getStationsByLineId(lineId);
	}

	/** 获取车辆当前站点信息 **/
	public AppVo_6 getBusPosition(String lineId, String stationId) {
		return lineDao.getBusPosition(lineId, stationId);
	}

	/** 用户发起线路 **/
	public String addUserLine(String passengerid, String stime, String xtime, String haddr, String caddr, Coord hco, Coord cco, String km, String cityName) {
		return lineDao.addUserLine(passengerid, stime, xtime, haddr, caddr, hco, cco, km, cityName);
	}

	/** 招募报名 **/
	public String enrollLine(String passengerid, String lineId) {
		return lineDao.enrollLine(passengerid, lineId);
	}

	/** 招募线路详细 **/
	public AppVo_15 enrollLineInfo(String passengerid, String lineId) {
		return lineDao.enrollLineInfo(passengerid, lineId);
	}

	/** 我的招募线路 **/
	public List<AppVo_15> enrollLineList(String passengerid, String type, String currentPage, String pageSize) {
		return lineDao.enrollLineList(passengerid, type, currentPage, pageSize);
	}

	/** 加上招募线路的线路搜索(范围搜索) **/
	private List<AppVo_14> getSplitLineAndUserAppJuLi(ParamVo paramVo, Integer maxScope) {
		List<AppVo_14> voList = new ArrayList<AppVo_14>();
		// 根据名字完全匹配成功
		// 起点(根据名字)--两表联合查询
		List<StationUnion> sList = stationInfoDao.queryUnionByName(paramVo.getsName());
		// 终点(根据名字)--两表联合查询
		List<StationUnion> eList = stationInfoDao.queryUnionByName(paramVo.geteName());

		Integer sourceBy = 0;// 排序依据 0:没有 1:起点经纬度 2:终点经纬度

		// 起点(根据经纬度)--两表联合查询
		List<StationUnion> poiStar = null;
		// 起点的经纬度范围--两表联合查询
		String lat = paramVo.getsLat();
		String lon = paramVo.getsLon();
		Coord sCoord = Coord.valueOf(lon, lat);
		Rect sRect = sCoord == null ? null : sCoord.aroundRect(maxScope);
		
		if ("0".equals(lat) || "0".equals(lon)) {
			// 起点经纬度为0,只根据名字查找
			poiStar = sList;
		} else {
			// 根据经纬度范围查找站点信息--两表联合查询
			poiStar = mapDao.queryUnionByLatLon(sRect, paramVo.getCityName(), null);
		}

		// 终点(根据经纬度)
		List<StationUnion> poiEnd = null;
		// 终点的经纬度范围
		Coord eCoord = Coord.valueOf(paramVo.geteLon(), paramVo.geteLat());
		Rect eRect = eCoord == null ? null : eCoord.aroundRect(maxScope);

		if ("0".equals(paramVo.geteLat()) || "0".equals(paramVo.geteLon())) {
			// 终点经纬度为0,只根据名字查找
			poiEnd = eList;
		} else {
			// 根据经纬度范围查找站点信息
			poiEnd = mapDao.queryUnionByLatLon(eRect, paramVo.getCityName(), null);
		}

		List<LineUnion> sids = null;// (根据起点,终点)--两表联合查询

		List<LineUnion> aroundSids = null;// (范围内的)--两表联合查询

		// 根据传入的参数判断搜索条件
		if (StringUtils.isEmpty(paramVo.getsName()) || StringUtils.isEmpty(paramVo.geteName())) {
			// 起点或终点有一个为空

			if (!StringUtils.isEmpty(paramVo.getsName())) {
				// 起点不为空

				// 根据起点信息条件--两表联合查询
				sids = lineDao.queryUnionSplieAndUserApp(sList, 1, paramVo.getFlag());
				aroundSids = lineDao.queryUnionSplieAndUserApp(poiStar, 1, paramVo.getFlag());
				sourceBy = 1;
			} else {
				// 终点不为空

				// 根据终点信息条件--两表联合查询
				sids = lineDao.queryUnionSplieAndUserApp(eList, 2, paramVo.getFlag());
				aroundSids = lineDao.queryUnionSplieAndUserApp(poiEnd, 2, paramVo.getFlag());
				sourceBy = 2;
			}
		} else {
			// 根据起点和终点条件查询线路--两表联合查询
			if (null != sList && sList.size() > 0 && null != eList && eList.size() > 0) {
				sids = lineDao.queryUnionSplieAndUserApp(sList, eList, paramVo.getFlag());
			}
			if (null != poiStar && poiStar.size() > 0 && null != poiEnd && poiEnd.size() > 0) {
				aroundSids = lineDao.queryUnionSplieAndUserApp(poiStar, poiEnd, paramVo.getFlag());
			}
		}

		// 优先列出发布线路

		List<LineUnion> fabu = new ArrayList<LineUnion>();// 发布的集合
		List<LineUnion> zhaomu = new ArrayList<LineUnion>();// 招募的线路

		// 将发布线路和招募线路分开
		if (null != sids && sids.size() > 0) {
			for (LineUnion l : sids) {
				if ("1".equals(l.getType())) {
					fabu.add(l);
				} else if ("2".equals(l.getType())) {
					zhaomu.add(l);
				}
			}
		}

		if (null != aroundSids && aroundSids.size() > 0) {
			for (LineUnion l : aroundSids) {
				if ("1".equals(l.getType())) {
					fabu.add(l);
				} else if ("2".equals(l.getType())) {
					zhaomu.add(l);
				}
			}
		}

		// 根据id和type找出对应的发布线路

		// 组合成新数据
		fabu = appendNotSameListAndUserApp(fabu, null);
		AppVo_3 vo3 = duplicateLineUnion(fabu);

		// 根据站点线路ID获取对应的线路信息
		List<LineBaseInfo> lineBaseInfos = lineDao.queryLineBaseInfoById(vo3.getA1(), null);
		// if(null==lineBaseInfos || lineBaseInfos.size()==0){
		// return null;
		// }

		// 获取所有线路的起点
		Map<String, Object> stationMap = rePeat.queryLineBaseInfoStartStation(lineBaseInfos);
		String startStationList = (String) stationMap.get("startStation");
		// 增加了单独起点标识的新集合
		lineBaseInfos = (List<LineBaseInfo>) stationMap.get("lineBaseInfos");

		// 获得需要查询站点名称的所有站点ID
		String allStationIds = rePeat.quChong(vo3.getA2() + startStationList);

		// 根据站点ID获取站点信息
		List<StationInfo> stationInfos = stationInfoDao.queryStationById(allStationIds);

		// 以站点ID为key,站点为value的map
		Map<String, StationInfo> maps = new HashMap<String, StationInfo>();

		if (null != stationInfos && stationInfos.size() > 0) {
			for (StationInfo StationInfo : stationInfos) {
				maps.put(StationInfo.getId(), StationInfo);
			}
		}

		// 线路ID为key,线路信息为value的map
		Map<String, LineBaseInfo> lineStartMap = new HashMap<String, LineBaseInfo>();
		if (null != lineBaseInfos && lineBaseInfos.size() > 0) {
			for (LineBaseInfo lineBaseInfo : lineBaseInfos) {
				// 线路ID--始发站点
				lineStartMap.put(lineBaseInfo.getLineBaseId(), lineBaseInfo);
			}
		}

		// 组装数据(根据站查找)
		List<AppVo_14> stationVo = RePeat.queryPutValueTogetherLineUnion(fabu, maps, lineStartMap, sourceBy, paramVo);

		// 根据id和type找出对应的招募线路的报名人数和是否报名

		// 找出招募线路的站点
		zhaomu = appendNotSameListAndUserApp(zhaomu, null);
		AppVo_3 zhaomu2 = duplicateLineUnion(zhaomu);
		// 招募线路的站点
		String zhaomuList = zhaomu2.getA2();
		// 根据招募站点ID获取站点信息
		List<StationUnion> zhaomuStationInfos = lineDao.queryZhaoMuStationById(zhaomuList);
		// 以站点ID为key,StationUnion为value的map
		Map<String, StationUnion> zhaomumap = new HashMap<String, StationUnion>();

		if (null != zhaomuStationInfos && zhaomuStationInfos.size() > 0) {
			for (StationUnion stationInfo : zhaomuStationInfos) {
				zhaomumap.put(stationInfo.getId(), stationInfo);
			}
		}

		// 查看用户是否有登录,如果有的话,查看是否对招募线路进行了报名
		List<LineUnion> appLyLine = lineDao.queryUserApplyLine(paramVo.getPassengerId());

		Map<String, String> appMap = new HashMap<String, String>();

		if (null != appLyLine && appLyLine.size() > 0) {
			for (LineUnion lineUnion : appLyLine) {
				appMap.put(lineUnion.getId(), "1");
			}
		}

		// 组装招募线路信息
		List<AppVo_14> zhaomuV = RePeat.queryPutValueTogeterZhaoMu(zhaomu, zhaomumap, sourceBy, paramVo, appMap);
		// 将发布和招募合在一起
		if (null != stationVo && stationVo.size() > 0) {
			// 对集合对象进行排序

			// 根据时间排序
			AppVo3_Comp comparator1 = new AppVo3_Comp();
			Collections.sort(stationVo, comparator1);
			// 根据距离排序
			AppVo_Comp comparator = new AppVo_Comp();
			Collections.sort(stationVo, comparator);
		}
		voList = appendNotSameList(stationVo, null);
		if (null != zhaomuV && zhaomuV.size() > 0) {
			// 对集合对象进行排序

			// 根据时间排序
			AppVo3_Comp comparator1 = new AppVo3_Comp();
			Collections.sort(zhaomuV, comparator1);
			// 根据距离排序
			AppVo_Comp comparator = new AppVo_Comp();
			Collections.sort(zhaomuV, comparator);
			voList.addAll(zhaomuV);
		}

		return voList;
	}
	
	/** 加上招募线路的线路搜索(分页) **/
	public List<AppVo_14> getSplitLineAndUserAppByPage(ParamVo paramVo) {
		List<AppVo_14> voList = new ArrayList<AppVo_14>();
		int maxScope = 800; // 单位：米
		voList = getSplitLineAndUserAppByPage(paramVo, maxScope);
		if ((null == voList || voList.size() == 0) && (0 == paramVo.getCurrentPage() && ("1".equals(paramVo.getType())))) {
			// 添加乘客线路搜索记录
			addPassengerSearchLine(paramVo);
			voList = getSplitLineAndUserAppByPage(paramVo, 1000);
		}
		return voList;
	}
	
	/** 加上招募线路的线路搜索(范围搜索)--(分页) **/
	private List<AppVo_14> getSplitLineAndUserAppByPage(ParamVo paramVo, Integer maxScope) {
		List<AppVo_14> voList = new ArrayList<AppVo_14>();
		Integer sourceBy = 0;// 排序依据 0:没有 1:起点经纬度 2:终点经纬度

		// 起点的经纬度范围--查询
		Coord sCoord = Coord.valueOf(paramVo.getsLon(), paramVo.getsLat());
		Rect sRect = sCoord == null ? null : sCoord.aroundRect(maxScope);
		
		Coord eCoord = Coord.valueOf(paramVo.geteLon(), paramVo.geteLat());
		Rect eRect = eCoord == null ? null : eCoord.aroundRect(maxScope);

		//发布线路
		if("1".equals(paramVo.getType())){

			// 起点(根据经纬度)--查询
			List<StationUnion> poiStar = null;
			
			if ("0".equals(paramVo.getsLat()) || "0".equals(paramVo.getsLon())) {
				// 起点经纬度为0,只根据名字查找
				poiStar = null;
			} else {
				// 根据经纬度范围查找站点信息--两表联合查询
				poiStar = mapDao.queryUnionByLatLon(sRect, paramVo.getCityName(), paramVo.getStationName());
			}
			
			// 终点(根据经纬度)
			List<StationUnion> poiEnd = null;
			
			if ("0".equals(paramVo.geteLat()) || "0".equals(paramVo.geteLon())) {
				// 终点经纬度为0,只根据名字查找
				poiEnd = null;
			} else {
				// 根据经纬度范围查找站点信息
				poiEnd = mapDao.queryUnionByLatLon(eRect, paramVo.getCityName(), paramVo.getStationName());
			}
			
			
			Map<String, Object> armaps = aroundSids(poiStar, poiEnd, paramVo, 1);
			
			List<LineUnion> aroundSids = (List<LineUnion>) armaps.get("list");
			sourceBy = (Integer) armaps.get("sourceBy");

			AppVo_3 vo3 = duplicateLineUnion(aroundSids);

			// 根据站点线路ID获取对应的线路信息
			List<LineBaseInfo> lineBaseInfos = lineDao.queryLineBaseInfoById(vo3.getA1(), null);

			// 获取所有线路的起点
			Map<String, Object> stationMap = rePeat.queryLineBaseInfoStartStation(lineBaseInfos);
			String startStationList = (String) stationMap.get("startStation");
			// 增加了单独起点标识的新集合
			lineBaseInfos = (List<LineBaseInfo>) stationMap.get("lineBaseInfos");

			// 获得需要查询站点名称的所有站点ID
			String allStationIds = rePeat.quChong(vo3.getA2() + startStationList);

			// 根据站点ID获取站点信息
			List<StationInfo> stationInfos = stationInfoDao.queryStationById(allStationIds);

			// 以站点ID为key,站点为value的map
			Map<String, StationInfo> maps = new HashMap<String, StationInfo>();

			if (null != stationInfos && stationInfos.size() > 0) {
				for (StationInfo StationInfo : stationInfos) {
					maps.put(StationInfo.getId(), StationInfo);
				}
			}

			// 线路ID为key,线路信息为value的map
			Map<String, LineBaseInfo> lineStartMap = new HashMap<String, LineBaseInfo>();
			if (null != lineBaseInfos && lineBaseInfos.size() > 0) {
				for (LineBaseInfo lineBaseInfo : lineBaseInfos) {
					// 线路ID--始发站点
					lineStartMap.put(lineBaseInfo.getLineBaseId(), lineBaseInfo);
				}
			}

			// 组装数据(根据站查找)
			List<AppVo_14> stationVo = RePeat.queryPutValueTogetherLineUnion(aroundSids, maps, lineStartMap, sourceBy, paramVo);
			voList.addAll(stationVo);
		}else if("2".equals(paramVo.getType())){
			
			// 起点(根据经纬度)--查询
			List<StationUnion> poiStar = null;
			if ("0".equals(paramVo.getsLat()) || "0".equals(paramVo.getsLon())) {
				// 起点经纬度为0,只根据名字查找
				poiStar = null;
			} else {
				// 根据经纬度范围查找站点信息--line_user_application_station_new(招募信息)
				poiStar = mapDao.queryUserAppByLatLon(sRect, paramVo);
			}
			
			// 终点(根据经纬度)
			List<StationUnion> poiEnd = null;
			if ("0".equals(paramVo.geteLat()) || "0".equals(paramVo.geteLon())) {
				// 终点经纬度为0,只根据名字查找
				poiEnd = null;
			} else {
				// 根据经纬度范围查找站点信息
				poiEnd = mapDao.queryUserAppByLatLon(eRect, paramVo);
			}
			
			Map<String, Object> armaps = aroundSids(poiStar, poiEnd, paramVo, 2);
			
			List<LineUnion> aroundSids = (List<LineUnion>) armaps.get("list");
			sourceBy = (Integer) armaps.get("sourceBy");
			// 找出招募线路的站点
			aroundSids = appendNotSameListAndUserApp(aroundSids, null);
			AppVo_3 zhaomu2 = duplicateLineUnion(aroundSids);
			// 招募线路的站点
			String zhaomuList = zhaomu2.getA2();
			// 根据招募站点ID获取站点信息
			List<StationUnion> zhaomuStationInfos = lineDao.queryZhaoMuStationById(zhaomuList);
			// 以站点ID为key,StationUnion为value的map
			Map<String, StationUnion> zhaomumap = new HashMap<String, StationUnion>();

			if (null != zhaomuStationInfos && zhaomuStationInfos.size() > 0) {
				for (StationUnion stationInfo : zhaomuStationInfos) {
					zhaomumap.put(stationInfo.getId(), stationInfo);
				}
			}

			// 查看用户是否有登录,如果有的话,查看是否对招募线路进行了报名
			List<LineUnion> appLyLine = lineDao.queryUserApplyLine(paramVo.getPassengerId());

			Map<String, String> appMap = new HashMap<String, String>();

			if (null != appLyLine && appLyLine.size() > 0) {
				for (LineUnion lineUnion : appLyLine) {
					appMap.put(lineUnion.getId(), "1");
				}
			}
			// 组装招募线路信息
			List<AppVo_14> zhaomuV = RePeat.queryPutValueTogeterZhaoMu(aroundSids, zhaomumap, sourceBy, paramVo, appMap);
			voList.addAll(zhaomuV);
			
		}
		
		return voList;
	}

	/**
	 * 获取附近线路
	 * @param poiStar
	 * @param poiEnd
	 * @param paramVo
	 * @param type   1:(发布线路) 2：(招募线路)
	 * @return  
	 */
	private Map<String, Object> aroundSids(List<StationUnion> poiStar, List<StationUnion> poiEnd, ParamVo paramVo,int type) {
		
		Integer sourceBy = 0;//排序依据 0:没有 1:起点经纬度 2:终点经纬度
		
		Map<String, Object> maps = new HashMap<String,Object>();
		//算出所有有效站点的和当前点的距离
		if(null!=poiStar && poiStar.size()>0){
			poiStar = RePeat.countDist(poiStar,paramVo,sourceBy);
		}else{
			sourceBy = 2;
		}
		
		if(null!=poiEnd && poiEnd.size()>0){
			poiEnd = RePeat.countDist(poiEnd,paramVo,sourceBy);
		}
		
		if(sourceBy!=2){
			//按距离排序--起点
			Collections.sort(poiStar, STATION_UNION_COMPARTOR);
		}else{
			//按距离排序--终点
			Collections.sort(poiEnd, STATION_UNION_COMPARTOR);
		}
		
		List<LineUnion> aroundSids = null;
		
		// 根据传入的参数判断搜索条件
		if (null==poiStar || poiStar.size()==0 || null==poiEnd || poiEnd.size()==0) {
			// 起点或终点有一个为空

			if (null!=poiStar && poiStar.size()>0) {
				// 起点不为空

				// 根据起点信息条件--查询(只有起点或者终点)
				aroundSids = lineDao.queryUnionSplieAndUserAppByPage(poiStar, 1, paramVo.getFlag(),type,paramVo.getCurrentPage(),paramVo.getPageSize());
			} else {
				// 终点不为空

				// 根据终点信息条件--查询(只有起点或者终点)
				aroundSids = lineDao.queryUnionSplieAndUserAppByPage(poiEnd, 2, paramVo.getFlag(),type,paramVo.getCurrentPage(),paramVo.getPageSize());
			}
		} else {
			if (null != poiStar && poiStar.size() > 0 && null != poiEnd && poiEnd.size() > 0) {
				//根据站点点信息条件--查询(起点终点都有)
				aroundSids = lineDao.queryUnionSplieAndUserAppByPage(poiStar, poiEnd, paramVo.getFlag(),type,paramVo.getCurrentPage(),paramVo.getPageSize());
			}
		}
		maps.put("list", aroundSids);
		maps.put("sourceBy", sourceBy);
		return maps;
	}
	
	
}
