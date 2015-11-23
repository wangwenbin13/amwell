package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.dao.ICouponRuleDao;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;

/**
 * 推荐时间规则
 * @author gxt
 *
 */
public class RecommendTimeRule extends Rule {
	
	private ICouponRuleDao couponRuleDao;

	public RecommendTimeRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event){
		if(event==Event.SINGLEHANDLE||event==Event.TIMINGHANDLE){//第三方操作和第三方定时时不发放该规则对应的优惠券
			return null;
		}
		if(event==Event.BUYTICKET){//买票时才需要检查推荐时间
			this.couponRuleDao=(ICouponRuleDao)obj;
			List<PassengerInfo> voList=couponRuleDao.getReOldPassenger(passenger.getPassengerId(),getTheValue(),getTheCondition());
			if(null!=voList&&voList.size()==1){
				return Arrays.asList(voList.get(0));
			}
		}
		return null;
	}
}
