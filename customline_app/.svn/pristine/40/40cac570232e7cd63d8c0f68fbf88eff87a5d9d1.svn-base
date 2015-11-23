package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;

/**
 * 用户来源规则
 * @author gxt
 *
 */
public class SourceFromRule extends Rule {

	public SourceFromRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event){
		if(event==Event.SINGLEHANDLE||event==Event.TIMINGHANDLE){//第三方操作和第三方定时时不发放该规则对应的优惠券
			return null;
		}
		if(getTheValue().equals(passenger.getSourcefrom())){//规则中的用户来源与用户来源相匹配
			return Arrays.asList(passenger);
		}
		return null;
	}
}
