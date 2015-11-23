package com.amwell.service.coupon;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponRule;

/**
 * 优惠券发放规则信息
 * @author gxt
 *
 */
public interface ICouponRuleService {
	
	/**
	 * 保存或修改规则
	 * @param coupon
	 * @return
	 */
	int updateCouponRule(CouponRule couponRule);
	
	
	/**
	 * 多条件查询规则列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
	
	/**
	 * 根据发放id查询发放规则
	 * @param couponGroupGrantId
	 * @return
	 */
	List<CouponRule> getCouponRule(String couponGroupGrantId);
}
