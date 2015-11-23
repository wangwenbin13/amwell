package com.amwell.vo.coupon;

import java.util.List;
import com.amwell.dao.coupon.ICouponRuleDao;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.app.bean.AppVo_1;

/**
 * 线路规则
 * @author gxt
 *
 */
public class LineRule extends Rule {
	
	private ICouponRuleDao couponRuleDao;

	public LineRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		this.couponRuleDao=(ICouponRuleDao)obj;
		List<AppVo_1> voList=couponRuleDao.getLeaseBase(getTheValue());
		return getResult(voList, passengers);
	}
}
