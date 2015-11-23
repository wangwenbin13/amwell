package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.amwell.vo.PassengerInfoEntity;

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
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		String[] strs=getTheValue().split(";");
		List<String> telephoneList=Arrays.asList(strs);
		List<PassengerInfoEntity> list=new ArrayList<PassengerInfoEntity>();
		for (PassengerInfoEntity passenger : passengers) {
			if(StringUtils.isNotBlank(passenger.getTelephone())&&telephoneList.contains(passenger.getTelephone())){//规则中的电话号码与用户电话号码相匹配
				list.add(passenger);
			}
		}
		return list;
	}
}
