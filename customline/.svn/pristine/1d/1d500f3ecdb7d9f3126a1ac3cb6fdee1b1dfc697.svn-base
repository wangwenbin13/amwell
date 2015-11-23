package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;
import com.amwell.vo.PassengerInfoEntity;

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
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		List<PassengerInfoEntity> list=new ArrayList<PassengerInfoEntity>();
		for (PassengerInfoEntity passenger : passengers) {
			if("equal".equals(getTheCondition())){
				if(getTheValue().equals(passenger.getCityCode())){//规则中的城市与用户所属城市相匹配
					list.add(passenger);
				}
			}
            if("notEq".equals(getTheCondition())){
            	if(!getTheValue().equals(passenger.getCityCode())){//规则中的城市与用户所属城市相匹配
    				list.add(passenger);
    			}
			}
		}
		return list;
	}
}
