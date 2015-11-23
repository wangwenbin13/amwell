package com.pig84.ab.vo.coupon;

import java.util.List;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;

public class GrantInfo {
	//规则集合
	private RuleSet ruleSet;
	//发放优惠券执行类
	private CouponGrant couponGrant;

	public int promote(PassengerInfo passenger,Event event) {
		//过滤用户
		List<PassengerInfo> p = ruleSet.filter(passenger,event);
		if(null!=p&&p.size()>0){
			for (PassengerInfo passengerInfo : p) {
				//为过滤用户发放优惠券
				couponGrant.send(passengerInfo);
			}
			return 1;
		}
		else{
			return -2;
		}
		
	}

	public RuleSet getRuleSet() {
		return ruleSet;
	}

	public void setRuleSet(RuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}

	public CouponGrant getCouponGrant() {
		return couponGrant;
	}

	public void setCouponGrant(CouponGrant couponGrant) {
		this.couponGrant = couponGrant;
	}
}
