package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;
import com.pig84.ab.utils.Event;
import com.pig84.ab.vo.PassengerInfo;

/**
 * 城市规则
 * @author gxt
 *
 */
public class CityRule extends Rule {

	public CityRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event) {
		if(event==Event.SINGLEHANDLE||event==Event.TIMINGHANDLE){//第三方操作和第三方定时时不发放该规则对应的优惠券
			return null;
		}
		if ("equal".equals(getTheCondition()) && getTheValue().equals(passenger.getCityCode()) || 
				"notEq".equals(getTheCondition()) && !getTheValue().equals(passenger.getCityCode())) {//规则中的城市与用户所属城市相匹配
			return Arrays.asList(passenger);
		}
		return null;
	}
	
	public static void main(String[] args) {
		PassengerInfo p=null;
		List<PassengerInfo> l=Arrays.asList(p);
		System.out.println(l.get(0));
	}
}
