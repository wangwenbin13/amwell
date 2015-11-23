package com.amwell.vo.coupon;

import java.util.List;
import com.amwell.dao.coupon.ICouponRuleDao;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.app.bean.AppVo_1;

/**
 * 推荐人数规则
 * @author gxt
 *
 */
public class RecommendNumberRule extends Rule {
	
	private ICouponRuleDao couponRuleDao;

	public RecommendNumberRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		this.couponRuleDao=(ICouponRuleDao)obj;
		List<AppVo_1> voList=couponRuleDao.getRecommendNumber(getTheValue(),getTheCondition());
		return getResult(voList, passengers);
	}
}
