package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.dao.ICouponRuleDao;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_1;

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
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event){
		if(event==Event.SINGLEHANDLE||event==Event.TIMINGHANDLE){//第三方操作和第三方定时时不发放该规则对应的优惠券
			return null;
		}
		this.couponRuleDao=(ICouponRuleDao)obj;
		List<AppVo_1> voList=couponRuleDao.getLeaseBase(passenger.getPassengerId(),getTheValue());
		PassengerInfo p=getResult(voList, passenger);
		return null==p?null:Arrays.asList(p);
	}
}
