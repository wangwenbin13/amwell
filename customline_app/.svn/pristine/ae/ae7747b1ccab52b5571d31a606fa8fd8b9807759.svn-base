package com.pig84.ab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IPassengerInfoDao;
import com.pig84.ab.service.IPassengerInfoService;
import com.pig84.ab.vo.PassengerInfo;

/**
 * 乘客信息相关
 * @author 
 *
 */
@Service("passengerService")
@Repository
public class PassengerInfoServiceImpl extends BaseDao implements IPassengerInfoService {
	
	@Autowired
	private IPassengerInfoDao passengerDao;
	
	/**
	 * 根据id查询乘客对象
	 * @param id
	 * @return
	 */
	public PassengerInfo getPassengerById(String id,String telephone) {
		return passengerDao.getPassengerById(id,telephone);
	}
}
