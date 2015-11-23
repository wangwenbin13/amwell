package com.amwell.service;

import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.VehicleInfoEntity;

/**
 * 车辆信息service接口类
 * @author 龚雪婷
 *
 */
public interface IVehicleInfoService {

	/**
	 * 分页查询车辆列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> listByPage(Search search,int currentPage, int pageSize);

	/**
	 * 修改车辆信息
	 * @param vehicleInfo
	 * @return
	 */
	int saveOrUpdateVehicle(VehicleInfoEntity vehicleInfo);
	
	/**
	 * 根据id查询车辆对象
	 * @param id
	 * @return
	 */
	VehicleInfoEntity getVehicleById(String id);
	
	/**
	 * 根据id删除车辆信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * @param id
	 * @return
	 */
	int deleteVehicleById(String id);
}
