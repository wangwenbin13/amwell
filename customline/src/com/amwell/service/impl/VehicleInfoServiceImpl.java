package com.amwell.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.dao.IVehicleInfoDao;
import com.amwell.entity.Search;
import com.amwell.service.IVehicleInfoService;
import com.amwell.vo.VehicleInfoEntity;

/**
 * 车辆信息service实现类
 * @author 龚雪婷
 *
 */
@Service("vehicleInfoService")
public class VehicleInfoServiceImpl implements IVehicleInfoService {

	@Autowired
	private IVehicleInfoDao dao;
	
	/**
	 * 分页查询车辆列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> listByPage(Search search,int currentPage,int pageSize){
		return dao.listByPage(search, currentPage, pageSize);
	}
	
	
	/**
	 * 根据id查询车辆对象
	 * @param id
	 * @return
	 */
	public VehicleInfoEntity getVehicleById(String id) {
		return dao.getVehicleById(id);
	}
	
	/**
	 * 保存或者修改车辆信息
	 * @param vehicleInfo
	 * @return
	 */
	public int saveOrUpdateVehicle(VehicleInfoEntity vehicleInfo){
		return dao.saveOrUpdateVehicle(vehicleInfo);
	}
	
	/**
	 * 根据id删除车辆信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * @param id
	 * @return
	 */
	public int deleteVehicleById(String id){
		return dao.deleteVehicleById(id);
	}
}
