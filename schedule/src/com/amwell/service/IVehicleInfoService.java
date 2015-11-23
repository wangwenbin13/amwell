package com.amwell.service;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.VehicleInfoEntity;

/**
 * 车辆信息service接口类
 * 
 * @author 龚雪婷
 *
 */
public interface IVehicleInfoService {

	/**
	 * 分页查询车辆列表
	 * 
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> listByPage(Search search, int currentPage, int pageSize);

	/**
	 * 修改车辆信息
	 * 
	 * @param vehicleInfo
	 * @return
	 */
	int saveOrUpdateVehicle(VehicleInfoEntity vehicleInfo, String userId);

	/**
	 * 根据id查询车辆对象
	 * 
	 * @param id
	 * @return
	 */
	VehicleInfoEntity getVehicleById(String id);

	/**
	 * 根据id删除车辆信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * 
	 * @param id
	 * @return
	 */
	int deleteVehicleById(String id, String userId);

	/**
	 * 根据车辆id查询当前排班信息
	 * 
	 * @param driverId
	 * @return
	 */
	Map<String, Object> lineClass(Search search, String vehicleId);

	/**
	 * 根据车辆id查询历史排班信息
	 * 
	 * @param driverId
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> lineClassPage(Search search, String vehicleId, int p, int pageSize);

	/**
	 * 根据车牌号查询车辆信息
	 * 
	 * @param vehicleNumber
	 * @return
	 */
	List<VehicleInfoEntity> getVehicleByNumber(String vehicleNumber);

	/**
	 * 根据车辆id查询班次信息
	 * 
	 * @param driverId
	 * @return
	 */
	List<LineClassEntity> getLineClassByVehicleId(String vehicleId);
}
