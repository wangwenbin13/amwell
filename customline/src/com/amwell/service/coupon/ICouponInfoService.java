package com.amwell.service.coupon;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponInfo;

/**
 * 优惠券信息
 * @author gxt
 *
 */
public interface ICouponInfoService {


	/**
	 * 保存或修改优惠券
	 * @param coupon
	 * @return
	 */
	int updateCoupon(CouponInfo coupon);
	
	/**
	 * 删除优惠券（逻辑删除）
	 * @param couponIds
	 * @return
	 */
	int delCoupon(String couponIds);
	
	
	/**
	 * 多条件查询优惠券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
	
	/**
	 * 解除组合券与原优惠券的关联
	 * @param coupon
	 * @return
	 */
	int updateRelation(String couponGroupId);
	
	/**
	 * 根据优惠券名称查询优惠券信息
	 * @param couponName
	 * @return
	 */
	List<CouponInfo> getCouponInfo(String couponName,String condition);
	
	/**
	 * 根据优惠券名称查询优惠券id和数量
	 * @param couponName
	 * @return
	 */
	String getCouponIds(String[] couponName);

	/**优惠券数据迁移**/
	String updateData();
}
