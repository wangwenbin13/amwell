package com.amwell.service.coupon.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.base.DaoSupport;
import com.amwell.dao.coupon.ICouponRuleDao;
import com.amwell.entity.Search;
import com.amwell.service.coupon.ICouponRuleService;
import com.amwell.vo.coupon.CouponRule;

/**
 * 优惠券发放规则信息
 * @author gxt
 *
 */
@Service("couponRuleService")
public class CouponRuleServiceImpl extends DaoSupport implements ICouponRuleService {
	
	@Autowired
	private ICouponRuleDao couponRuleDao;

	/**
	 * 保存或修改组合券
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCouponRule(CouponRule couponRule){
		return couponRuleDao.updateCouponRule(couponRule);
	}
	
	/**
	 * 多条件查询组合券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search, int currentPage,
			int pageSize) {
		return couponRuleDao.getListByCondition(search, currentPage, pageSize);
	}
	
	/**
	 * 根据发放id查询发放规则
	 * @param couponGroupGrantId
	 * @return
	 */
	public List<CouponRule> getCouponRule(String couponGroupGrantId){
		return couponRuleDao.getCouponRule(couponGroupGrantId);
	}
}
