package com.amwell.service;

import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.DriverWithdrawAskforVo;

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
	 * 分页查询司机提现申请列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> driverApplyListByPage(Search search,int currentPage, int pageSize);
	
	/**
	 * 分页查询司机奖励列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> driverPaymentListByPage(Search search,int currentPage, int pageSize);
	
	/**
	 * 分页查询司机报站明细
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> driverBroadcastListByPage(Search search,int currentPage, int pageSize);
	
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
	int saveOrUpdateDriver(DriverInfoEntity driverInfo);
	
	/**
	 * 根据id删除司机信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * @param id
	 * @return
	 */
	int deleteDriverById(String id);
	
	/**
	 * 根据id查询司机提现申请详情
	 * @param id
	 * @return
	 */
	public DriverWithdrawAskforVo getDriverApplyById(String askforId);
	
	/**
	 * 后台处理提现申请信息
	 * @param userName
	 * @param askforId
	 * @return
	 */
	public int updateDriverWithdrawAskfor(String userName,String askforId);
}
