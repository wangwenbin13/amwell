package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;
import com.amwell.vo.PassengerInfoEntity;

/**
 * 设备类型规则
 * @author gxt
 *
 */
public class TerminalRule extends Rule {

	public TerminalRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		List<PassengerInfoEntity> list=new ArrayList<PassengerInfoEntity>();
		for (PassengerInfoEntity passenger : passengers) {
			if(getTheValue().equals(passenger.getTerminal())){//规则中的设备类型与用户登录设备类型相匹配
				list.add(passenger);
			}
		}
		return list;
	}
}
