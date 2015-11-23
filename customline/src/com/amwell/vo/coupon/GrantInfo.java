package com.amwell.vo.coupon;

import java.util.List;
import com.amwell.vo.PassengerInfoEntity;

public class GrantInfo {
	//规则集合
	private RuleSet ruleSet;
	//发放优惠券执行类
	private CouponGrant couponGrant;

	public int promote(List<PassengerInfoEntity> passengers) {
		//过滤用户
		List<PassengerInfoEntity> passengerIds = ruleSet.filter(passengers);
		if(null!=passengerIds&&passengerIds.size()>0){
			//为过滤用户发放优惠券
			int flag=couponGrant.send(passengerIds);
			return flag;//flag为大于0时表示发放成功，-3时标识没有领取机会
		}
		else{
			return -4;//标识没有符合条件的用户
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
