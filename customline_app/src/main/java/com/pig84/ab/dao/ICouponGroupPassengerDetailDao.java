package com.pig84.ab.dao;

import com.pig84.ab.vo.coupon.CouponGroup;


/**
 * 用户优惠券详情
 * @author gxt
 *
 */
public interface ICouponGroupPassengerDetailDao {
	
	/**
	 * 批量添加优惠券
	 * @param sql
	 * @return
	 */
	int updateCouponDetailBatch(String sql);
	
	
	/**
	 * 查询用户已发放的优惠券总数
	 * @param grantId
	 * @param couponGroupId
	 * @param passengerId
	 * @return
	 */
	int getCouponDetail(String grantId,String couponGroupId,String passengerId);
	
	/**
	 * 根据组合券id查询组合券信息
	 * @param couponGroupId
	 * @return
	 */
	CouponGroup getCouponGroupById(String couponGroupId);
}
