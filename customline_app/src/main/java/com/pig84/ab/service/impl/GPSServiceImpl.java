package com.pig84.ab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.IGPSDao;
import com.pig84.ab.service.IGPSService;

@Service
public class GPSServiceImpl implements IGPSService{

	@Autowired
	private IGPSDao gpsDao;
	
	/**根据设备号获取位置信息**/
	public String getGpsInfo(String No,String lineBaseId){
		return gpsDao.getGpsInfo(No,lineBaseId);
	}
	
	/**根据车牌号获取设备号**/
	public String getBusPosition(String No){
		return gpsDao.getBusPosition(No);
	}

}
