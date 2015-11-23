package com.amwell.service;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.LineClassEntity;

/**
 * 司机信息service接口类
 * @author 龚雪婷
 *
 */
public interface IDriverInfoService {
	/**
	 * 分页查询司机列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> listByPage(Search search,int currentPage, int pageSize);
	
	/**
	 * 根据id查询司机对象
	 * @param id
	 * @return
	 */
	DriverInfoEntity getDriverById(String id);
	
	/**
	 * 保存或者修改司机信息
	 * @param driverInfo
	 * @return
	 */
	int saveOrUpdateDriver(DriverInfoEntity driverInfo,String userId);
	
	/**
	 * 根据id删除司机信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * @param id
	 * @return
	 */
	int deleteDriverById(String id,String userId);
	
	/**
	 * 根据司机id查询当前排班信息
	 * @param driverId
	 * @return
	 */
	Map<String,Object> lineClass(Search search,String driverId);
	
	/**
	 * 根据司机id查询历史排班信息
	 * @param driverId
	 * @param pageSize
	 * @return
	 */
   Map<String,Object> lineClassPage(Search search,String driverId,int p,int pageSize);
   
   /**
    * 根据电话号码查询司机信息
    * @param telPhone
    * @return
    */
   List<DriverInfoEntity> getDriverByTel(String telPhone);
   
   /**
    * 根据司机id查询班次信息
    * @param driverId
    * @return
    */
   List<LineClassEntity> getLineClassByDriverId(String driverId);
}
