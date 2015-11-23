package com.amwell.service.coupon;

import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponGroup;

/**
 * 组合券信息
 * @author gxt
 *
 */
public interface ICouponGroupService {
	
	/**
	 * 保存或修改组合券
	 * @param coupon
	 * @return
	 */
	int updateCouponGroup(CouponGroup couponGroup);
	
	
	/**
	 * 多条件查询组合券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
	
	/**
	 * 多条件查询优惠券统计列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getCouponStatistics(Search search,int currentPage, int pageSize);
	
	/**
	 * 查询组合券批次号
	 * @return
	 */
	String getCouponGroupID();
	
	/**
	 * 根据发放id查询优惠券信息
	 * @param couponGroupGrantId
	 * @return
	 */
	CouponGroup getCouponGroup(String couponGroupGrantId);
	
	/**
	 * 根据组合券id查询组合券信息
	 * @param couponGroupId
	 * @return
	 */
	CouponGroup getCouponGroupById(String couponGroupId);

}
