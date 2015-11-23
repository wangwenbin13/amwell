package com.amwell.dao;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.app.PassengerInfo;

/**
 * 乘客dao接口类
 * @author 龚雪婷
 *
 */
public interface IPassengerInfoDao {

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

	/**查询所有乘客的号码和ID**/
	List<PassengerInfoEntity> getAllPassengerInfo();
	
	/**查询所有乘客的id、城市、电话、注册时间、设备类型、用户来源、性别**/
    List<PassengerInfoEntity> getAllPassenger();

	/**更新密码**/
	void updatePass(PassengerInfo passengerInfo);

	/**
	 * 通过电话号码查询乘客信息
	 */
	PassengerInfo getPassengerId(String phone,String cityCode);

	/**重设用户密码
	 * @param passengerId
	 * @param userId 
	 * @return
	 */
	int resetPsPassword(String passengerId, String userId);

	/**查询号码的乘客是否存在
	 * @param telephone
	 * @return
	 */
	int checkPassengerPhone(String telephone);

}
