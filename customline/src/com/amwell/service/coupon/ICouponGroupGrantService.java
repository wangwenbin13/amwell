package com.amwell.service.coupon;

import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponGroupGrant;

/**
 * 组合券发放信息
 * @author gxt
 *
 */
public interface ICouponGroupGrantService {
	
	/**
	 * 查询最大id
	 * @return
	 */
	String getMaxId();
	
	/**
	 * 保存或修改组合券发放
	 * @param coupon
	 * @return
	 */
	int updateCouponGroupGrant(CouponGroupGrant couponGroupGrant);
	
	int updateGrant(CouponGroupGrant couponGroupGrant);
	
	/**
	 * 保存或修改组合券发放(带事物处理)
	 * @param coupon
	 * @return
	 */
	int updateCouponGroupGrantT(CouponGroupGrant couponGroupGrant);
	
	/**
	 * 发放优惠券
	 * @return
	 */
	int addCouponDetail(CouponGroupGrant couponGroupGrant);
	
	int addCouponDetail(String couponGroupGrantId);
	
	/**
	 * 多条件查询组合券发放列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
	
	/**
	 * 根据发放id查询发放对象
	 * @param couponGroupGrantId
	 * @return
	 */
	CouponGroupGrant getInfoById(String couponGroupGrantId);
	
	/**
	 * 根据发放id查询用户发放详情
	 * @param couponGroupGrantId
	 * @return
	 */
	Map<String, Object> getDetail(String couponGroupGrantId,int currentPage, int pageSize);

}
