package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;

/**
 * 性别规则
 * @author gxt
 *
 */
public class SexRule extends Rule {

	public SexRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event){
		if(event==Event.SINGLEHANDLE||event==Event.TIMINGHANDLE){//第三方操作和第三方定时时不发放该规则对应的优惠券
			return null;
		}
		if(null!=passenger.getSex()&&getTheValue().equals(passenger.getSex().toString())){//规则中的性别与用户性别相匹配
			return Arrays.asList(passenger);
		}
		return null;
	}
}
