package com.amwell.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.dao.IDriverInfoDao;
import com.amwell.entity.Search;
import com.amwell.service.IDriverInfoService;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.DriverWithdrawAskforVo;

/**
 * 乘客信息service实现类
 * @author 龚雪婷
 *
 */
@Service("driverInfoService")
public class DriverInfoServiceImpl implements IDriverInfoService {

	@Autowired
	private IDriverInfoDao dao;
	
	/**
	 * 分页查询司机列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> listByPage(Search search,int currentPage,int pageSize){
		return dao.listByPage(search, currentPage, pageSize);
	}
	
	/**
	 * 分页查询司机提现申请列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> driverApplyListByPage(Search search,int currentPage, int pageSize){
		return dao.driverApplyListByPage(search, currentPage, pageSize);
	}
	
	/**
	 * 分页查询司机奖励列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> driverPaymentListByPage(Search search,int currentPage, int pageSize){
		return dao.driverPaymentListByPage(search, currentPage, pageSize);
	}
	
	/**
	 * 分页查询司机报站明细
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> driverBroadcastListByPage(Search search,int currentPage, int pageSize){
		return dao.driverBroadcastListByPage(search, currentPage, pageSize);
	}
	
	/**
	 * 根据id查询司机对象
	 * @param id
	 * @return
	 */
	public DriverInfoEntity getDriverById(String id) {
		return dao.getDriverById(id);
	}
	
	/**
	 * 保存或者修改司机信息
	 * @param driverInfo
	 * @return
	 */
	public int saveOrUpdateDriver(DriverInfoEntity driverInfo){
		return dao.saveOrUpdateDriver(driverInfo);
	}
	
	/**
	 * 根据id删除司机信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * @param id
	 * @return
	 */
	public int deleteDriverById(String id){
		return dao.deleteDriverById(id);
	}
	
	/**
	 * 根据id查询司机提现申请详情
	 * @param id
	 * @return
	 */
	public DriverWithdrawAskforVo getDriverApplyById(String askforId){
		return dao.getDriverApplyById(askforId);
	}
	
	/**
	 * 后台处理提现申请信息
	 * @param userName
	 * @param askforId
	 * @return
	 */
	public int updateDriverWithdrawAskfor(String userName,String askforId){
		return dao.updateDriverWithdrawAskfor(userName, askforId);
	}
}
