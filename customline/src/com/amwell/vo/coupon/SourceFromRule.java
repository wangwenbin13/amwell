package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;
import com.amwell.vo.PassengerInfoEntity;

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
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		List<PassengerInfoEntity> list=new ArrayList<PassengerInfoEntity>();
		for (PassengerInfoEntity passenger : passengers) {
			if(getTheValue().equals(passenger.getSourcefrom())){//规则中的用户来源与用户来源相匹配
				list.add(passenger);
			}
		}
		return list;
	}
}
