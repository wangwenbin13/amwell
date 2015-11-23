package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;
import com.amwell.vo.PassengerInfoEntity;

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
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		List<PassengerInfoEntity> list=new ArrayList<PassengerInfoEntity>();
		for (PassengerInfoEntity passenger : passengers) {
			if(null!=passenger.getSex()&&getTheValue().equals(passenger.getSex().toString())){//规则中的性别与用户性别相匹配
				list.add(passenger);
			}
		}
		return list;
	}
}
