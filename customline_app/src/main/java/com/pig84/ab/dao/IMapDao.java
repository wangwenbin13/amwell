package com.pig84.ab.dao;

import java.util.List;

import com.pig84.ab.vo.ParamVo;
import com.pig84.ab.vo.Rect;
import com.pig84.ab.vo.StationInfo;
import com.pig84.ab.vo.StationUnion;
import com.pig84.ab.vo.bean.AppVo_1_list;

public interface IMapDao {

	/**根据两组经纬度值获得该范围内的站点**/
	AppVo_1_list queryNearStation(Rect rect,String cityName,String userid,boolean flag);

	/**获取最大的搜索范围**/
	int queryMaxBusScope();

	/**根据经纬度范围查找站点信息**/
	List<StationInfo> queryStationByLatLon(Rect rect,String cityName);

	/**根据经纬度范围查找站点信息--两表联合查询**/
	public List<StationUnion> queryUnionByLatLon(Rect rect, String cityName,String stationName);

	/**根据经纬度范围查找站点信息--line_user_application_station_new(招募信息)**/
	List<StationUnion> queryUserAppByLatLon(Rect rect, ParamVo paramVo);
}
