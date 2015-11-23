package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.dao.ICouponRuleDao;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;

public class RuleSet {
	
	private ICouponRuleDao couponRuleDao;
	
	private List<Rule> rules;

	/**
	 * 返回过滤后的用户id
	 * @param passengers
	 * @return
	 */
	public List<PassengerInfo> filter(PassengerInfo passenger,Event event) {
		List<PassengerInfo> p=null;
		try {
			if(event==Event.TIMINGHANDLE){
				p=Arrays.asList(passenger);
				for (Rule rule : rules) {
					if(null==p||p.isEmpty()){
						break;
					}
					p=rule.filter(couponRuleDao,passenger,event);
				}
			}
			else{
				PassengerInfo temp=passenger;
				for (Rule rule : rules) {
					if(null==temp){
						break;
					}
					p=rule.filter(couponRuleDao,temp,event);
					if(null==p||p.isEmpty()){
						temp=null;
					}
					else{
						temp=p.get(0);
					}
				}
				if(null!=temp){
					p=Arrays.asList(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
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
