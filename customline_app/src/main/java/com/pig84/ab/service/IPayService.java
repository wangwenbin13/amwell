package com.pig84.ab.service;

import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.Payment.OrderRRZ;
import com.pig84.ab.vo.bean.AppVo_5;

/**
 * 支付相关业务
 * @author 张强
 * @time 2015-09-17 11:59:59
 *
 */
public interface IPayService {

	/**根据订单号获取订单信息**/
	public LeaseBaseInfo getLeaseBaseInfoByOrderNo(String orderNo);

	/**
	 * 退票检查
	 * @param orderNo  订单号
	 * @param localIds 座位主键ID(多个用‘，’分割)
	 * @param appUser 用户信息
	 * @return
	 */
	public OrderRRZ checkOrder(String orderNo, String localIds,User appUser);

	/**
	 * 退票相关金额
	 * @param orderNo 订单号
	 * @param localIds 座位主键ID(多个用‘，’分割)
	 * @return
	 */
	public AppVo_5 queryReturnRelMon(String orderNo, String localIds);

	/**
	 * 根据座位主键ID获取对应的班次ID,返回多个班次,用","分隔
	 * @param localIds localIds 座位主键ID(多个用‘，’分割)
	 * @return
	 */
	public String queryLineClassIds(String localIds);

}
