package com.pig84.ab.dao;

import com.pig84.ab.vo.PassengerInfo;



/**
 * 乘客信息相关接口
 * @author 
 *
 */
public interface IPassengerInfoDao {
	
	/**
	 * 根据id查询乘客对象
	 * @param id
	 * @return
	 */
	PassengerInfo getPassengerById(String id,String telephone);
}
