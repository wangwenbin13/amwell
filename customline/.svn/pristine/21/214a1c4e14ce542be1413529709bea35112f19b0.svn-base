package com.amwell.dao.impl;

import java.util.Map;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.IVehicleInfoDao;
import com.amwell.entity.Search;
import com.amwell.vo.VehicleInfoEntity;

/**
 * 车辆dao实现类
 * @author 龚雪婷
 *
 */
@Repository
public class VehicleInfoDaoImpl extends DaoSupport implements IVehicleInfoDao {

	/**
	 * 分页查询车辆列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> listByPage(Search search,int p,int pageSize){
//		String cond = " where 1 = 1 ";
//		if(search!= null){
//			if(search.getField01()!= null && !search.getField01().equals("")){
//				cond+= " and username like '%"+search.getField01()+"%'";
//			}
//			if(search.getField02()!= null && !search.getField02().equals("")){
//				cond+= " and pwd like '%"+search.getField02()+"%'";
//			}
//			
//			if(search.getField03()!= null && !search.getField03().equals("")){
//				cond+= " and realname like '%"+search.getField03()+"%'";
//			}
//		}
//		
//		tableDao.setPageSize(pageSize);
//		sql = "select * from uservo "+cond;
//		list = tableDao.queryByPage(UserVo.class, sql,p);
//		page = new Page(list,sql,p,pageSize);
//		map.put("list", list);
//		map.put("page", page);
		return map;
	}
	
	
	/**
	 * 根据id查询车辆对象
	 * @param id
	 * @return
	 */
	public VehicleInfoEntity getVehicleById(String id) {
//		super.finit("uservo");
//		UserVo user = tableDao.queryBeanFromCache(UserVo.class, "select * from uservo where rec_id = '"+id+"'", "vo_test");
//		
//		UserVo user1 = tableDao.queryBeanById(UserVo.class, id,"test_id");
//		return tableDao.queryBean(UserVo.class,"select * from uservo where test_id = '"+id+"'");
		return null;
	}
	
	/**
	 * 保存或者修改车辆信息
	 * @param vehicleInfo
	 * @return
	 */
	public int saveOrUpdateVehicle(VehicleInfoEntity vehicleInfo){
		return 0;
	}
	
	/**
	 * 根据id删除车辆信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * @param id
	 * @return
	 */
	public int deleteVehicleById(String id){
		return 0;
	}
}
