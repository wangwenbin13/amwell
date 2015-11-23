package com.pig84.ab.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IGPSDao;
import com.pig84.ab.utils.CacheUtil;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.LineClassInfo;
import com.pig84.ab.vo.bean.AppVo_1;

@Repository
public class GPSDaoImpl extends BaseDao implements IGPSDao{
	
	/**根据设备号获取位置信息**/
	public String getGpsInfo(String No,String lineBaseId){
        //String sql = SELECT * FROM line_class_info WHERE vehicleid = (SELECT vehicleId FROM vehicle_info WHERE GPSNo = '12024084' AND delFlag = '0' LIMIT 1) AND linebaseid = '' AND delFlag = '0' AND orderdate = '2015-07-06'
		String sql = "SELECT * FROM line_class_info WHERE linebaseid = ? AND delFlag = '0' AND orderdate = ? limit 1";
		Object[] params = new Object[2];
		params[0] = lineBaseId;
		params[1] = MyDate.Format.DATE.now();
		LineClassInfo clazz = queryBean(LineClassInfo.class, sql, params);
		if(clazz==null){
			return "";
		}
		
		String nowTime = MyDate.Format.DATETIME.now();//当前时间
		String busTime = clazz.getOrderDate()+" "+clazz.getOrderStartTime()+":00";//发车时间
		int diff = MyDate.diffMinutes(nowTime ,busTime);
		if(diff>=-30 && diff<= 119){
			String info = CacheUtil.getCache(No);
			if(StringUtils.isEmpty(info)){
				return "";
			}else{
				return info;
			}
		}else{
			return null;
		}
	}
	
	/**根据车牌号获取设备号**/
	public String getBusPosition(String No){
		String sql = "select GPSNo as a1 from vehicle_info where vehicleNumber = ? AND delFlag ='0'";
		Object[] params = new Object[1];
		params[0] = No;
		AppVo_1 vo = queryBean(AppVo_1.class, sql,params);
		
		if (vo!=null && vo.getA1()!=null && !"".equals(vo.getA1())) {
			return vo.getA1();
		}else{
			return null;
		}
	}

}