package com.pig84.ab.service;

import com.pig84.ab.vo.Coord;
import com.pig84.ab.vo.bean.AppVo_1_list;

public interface IMapService {

	/**获取某个点某个范围内的附近站点位置点**/
	AppVo_1_list queryNearStation(Coord coord, String cityName, String userid,boolean flag);

}
