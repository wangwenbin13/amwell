package com.amwell.service;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.app.PassengerInfo;

/**
 * 乘客信息service接口类
 * @author 龚雪婷
 *
 */
public interface IPassengerInfoService {

	/**
	 * 分页查询乘客列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> listByPage(Search search,int currentPage, int pageSize,String sourcefrom);

	/**
	 * 修改乘客信息
	 * @param passengerInfo
	 * @return
	 */
	int updatePassenger(PassengerInfoEntity passengerInfo,String userId);
	
	/**
	 * 根据id查询乘客对象
	 * @param id
	 * @return
	 */
	PassengerInfoEntity getPassengerById(String id);
	
	/**
	 * 查看乘客未乘坐的订单信息
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> passengerOrderList(Search search,int currentPage, int pageSize);

	/**查询所有的乘客的号码和ID**/
	List<PassengerInfoEntity> getAllPassengerInfo();

	/**更新密码**/
	void updatePass(PassengerInfo passengerInfo);

	/**
	 * 验证手机号码用户是否存在
	 * @param cityCode 
	 * @return
	 */
	String checktelephone(String telephone, String cityCode);

	/**重设用户密码
	 * @param passengerId
	 * @param userId 
	 * @return
	 */
	int resetPsPassword(String passengerId, String userId);
	
	
	
}
