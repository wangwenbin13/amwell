package com.pig84.ab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.IMapDao;
import com.pig84.ab.service.IMapService;
import com.pig84.ab.vo.Coord;
import com.pig84.ab.vo.Rect;
import com.pig84.ab.vo.bean.AppVo_1_list;

@Service("mapService")
public class MapServiceImpl implements IMapService{
	
	
	@Autowired
	private IMapDao mapDao;

	/**获取某个点某个范围内的附近站点位置点**/
	@Override
	public AppVo_1_list queryNearStation(Coord coord, String cityName, String userid, boolean flag) {
		if (coord == null) {
			return null;
		}
		/**上下班车最大搜索范围**/
		int maxBusScope = mapDao.queryMaxBusScope();   //单位：公里
		Rect rect = coord.aroundRect(maxBusScope * 1000);
		return mapDao.queryNearStation(rect, cityName, userid, flag);
	}

}
