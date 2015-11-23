package com.pig84.ab.dao;

public interface IGPSDao {
	
	/**根据设备号获取位置信息**/
	public String getGpsInfo(String No,String lineBaseId);
	
	/**根据车牌号获取设备号**/
	public String getBusPosition(String No);

}