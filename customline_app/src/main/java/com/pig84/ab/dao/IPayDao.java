package com.pig84.ab.dao;

import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.Payment.OrderRRZ;

/**
 * 支付相关业务
 * @author 张强
 * @time 2015-09-17 11:59:59
 *
 */
public interface IPayDao {

	/**根据订单号获取订单详细**/
	public LeaseBaseInfo getLeaseBaseInfoByOrderNo(String orderNo);

	/**
	 * 退票检查
	 * @param orderNo  订单号
	 * @param localIds 座位主键ID(多个用‘，’分割)
	 * @param passengerId 用户ID
	 * @return
	 */
	public OrderRRZ checkOrder(String orderNo, String localIds,String passengerId);

	/**
	 * 根据座位主键ID获取对应的班次ID,返回多个班次,用","分隔
	 * @param localIds localIds 座位主键ID(多个用‘，’分割)
	 * @return
	 */
	public String queryLineClassIds(String localIds);
}
