package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.dao.ICouponRuleDao;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_1;

/**
 * 购票时间规则
 * @author gxt
 *
 */
public class TicketTimeRule extends Rule {
	
	private ICouponRuleDao couponRuleDao;

	public TicketTimeRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event){
		if(event==Event.SINGLEHANDLE||event==Event.TIMINGHANDLE){//第三方操作和第三方定时时不发放该规则对应的优惠券
			return null;
		}
		this.couponRuleDao=(ICouponRuleDao)obj;
		List<AppVo_1> voList=couponRuleDao.getTicketPassenger(passenger.getPassengerId(),getTheValue(),getTheCondition());
		PassengerInfo p=getResult(voList, passenger);
		return null==p?null:Arrays.asList(p);
	}
}
