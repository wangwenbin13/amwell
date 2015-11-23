package com.amwell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IPassengerRegistDao;
import com.amwell.service.IPassengerRegistService;
import com.amwell.vo.PassengerVo;

@Service("passengerRegistService")
public class PassengerRegistServiceImpl implements IPassengerRegistService {

	@Autowired
	private IPassengerRegistDao passengerRegistDao;
	
	/* 
	 * 手动（批量）注册用户
	 */
	public int registPassenger(List<PassengerVo> pList) {
		return passengerRegistDao.registPassenger(pList);
	}

	/* 
	 * 检测电话号码是否有重复
	 */
	public List<PassengerVo> checkTelephone(List<PassengerVo> pList) {
		return passengerRegistDao.checkTelephone(pList);
	}


	/* 
	 *查询是电话号码 是否已存在
	 */
	public int queryPhone(String phone) {
		return passengerRegistDao.queryPhone(phone);
	}

}
