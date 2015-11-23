package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;

/**
 * 电话号码规则
 * @author gxt
 *
 */
public class TelephoneRule extends Rule {

	public TelephoneRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event){
		if(event==Event.SINGLEHANDLE||event==Event.TIMINGHANDLE){//第三方操作和第三方定时时不发放该规则对应的优惠券
			return null;
		}
		String[] strs=getTheValue().split(";");
		List<String> telephoneList=Arrays.asList(strs);
		if(telephoneList.contains(passenger.getTelephone())){//规则中的电话号码与用户电话号码相匹配
			return Arrays.asList(passenger);
		}
		return null;
	}
}
