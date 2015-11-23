package com.pig84.ab.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IPassengerInfoDao;
import com.pig84.ab.vo.PassengerInfo;

/**
 * 乘客信息相关
 * @author 
 *
 */
@Repository
public class PassengerInfoDaoImpl extends BaseDao implements IPassengerInfoDao {
	
	/**
	 * 根据id查询乘客对象
	 * @param id
	 * @return
	 */
	public PassengerInfo getPassengerById(String id,String telephone) {
		PassengerInfo passenger = null;
		if(StringUtils.isNotBlank(id)){
			passenger = queryBeanById(PassengerInfo.class, id,"passenger_info","passengerId");
		}
		else{
			String sql="select * from passenger_info where telephone = '"+telephone+"'";
			passenger = queryBean(PassengerInfo.class, sql);
		}
		return passenger;
	}
}
