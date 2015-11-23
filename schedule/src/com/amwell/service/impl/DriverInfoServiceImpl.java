package com.amwell.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IDriverInfoDao;
import com.amwell.entity.Search;
import com.amwell.service.IDriverInfoService;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.LineClassEntity;

/**
 * 乘客信息service实现类
 * 
 * @author 龚雪婷
 *
 */
@Service("service")
// @Service("driverInfoService")
public class DriverInfoServiceImpl implements IDriverInfoService {

	@Autowired
	private IDriverInfoDao dao;

	/**
	 * 分页查询司机列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> listByPage(Search search, int currentPage, int pageSize) {
		return dao.listByPage(search, currentPage, pageSize);
	}

	/**
	 * 根据id查询司机对象
	 * 
	 * @param id
	 * @return
	 */
	public DriverInfoEntity getDriverById(String id) {
		return dao.getDriverById(id);
	}

	/**
	 * 保存或者修改司机信息
	 * 
	 * @param driverInfo
	 * @return
	 */
	public int saveOrUpdateDriver(DriverInfoEntity driverInfo, String userId) {
		return dao.saveOrUpdateDriver(driverInfo, userId);
	}

	/**
	 * 根据id删除司机信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * 
	 * @param id
	 * @return
	 */
	public int deleteDriverById(String id, String userId) {
		return dao.deleteDriverById(id, userId);
	}

	/**
	 * 根据司机id查询当前排班信息
	 * 
	 * @param driverId
	 * @return
	 */
	public Map<String, Object> lineClass(Search search, String driverId) {
		return dao.lineClass(search, driverId);
	}

	/**
	 * 根据司机id查询历史排班信息
	 * 
	 * @param driverId
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> lineClassPage(Search search, String driverId, int p, int pageSize) {
		return dao.lineClassPage(search, driverId, p, pageSize);
	}

	/**
	 * 根据电话号码查询司机信息
	 * 
	 * @param telPhone
	 * @return
	 */
	public List<DriverInfoEntity> getDriverByTel(String telPhone) {
		return dao.getDriverByTel(telPhone);
	}

	/**
	 * 根据司机id查询班次信息
	 * 
	 * @param driverId
	 * @return
	 */
	public List<LineClassEntity> getLineClassByDriverId(String driverId) {
		return dao.getLineClassByDriverId(driverId);
	}
}
