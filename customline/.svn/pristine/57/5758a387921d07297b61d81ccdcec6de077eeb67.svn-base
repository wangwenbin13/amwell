package com.amwell.dao.coupon;

import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupPassengerDetail;

/**
 * 用户优惠券详情
 * @author gxt
 *
 */
public interface ICouponGroupPassengerDetailDao {
	
	/**
	 * 保存或修改用户优惠券详情
	 * @param detail
	 * @return
	 */
	int updateCouponDetail(CouponGroupPassengerDetail detail);
	
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
	
	/**
	 * 多条件查询用户优惠券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
}
