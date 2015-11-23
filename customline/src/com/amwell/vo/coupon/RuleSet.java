package com.amwell.vo.coupon;

import java.util.List;
import com.amwell.dao.coupon.ICouponRuleDao;
import com.amwell.vo.PassengerInfoEntity;

public class RuleSet {
	
	private ICouponRuleDao couponRuleDao;

	private List<Rule> rules;

	/**
	 * 返回过滤后的用户id
	 * @param passengers
	 * @return
	 */
	public List<PassengerInfoEntity> filter(List<PassengerInfoEntity> passengers) {
		List<PassengerInfoEntity> list=passengers;
		try {
			for (Rule rule : rules) {
				if(null==list||list.size()==0){
					break;
				}
				list=rule.filter(this.couponRuleDao,list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public ICouponRuleDao getCouponRuleDao() {
		return couponRuleDao;
	}

	public void setCouponRuleDao(ICouponRuleDao couponRuleDao) {
		this.couponRuleDao = couponRuleDao;
	}
}
