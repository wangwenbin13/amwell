package com.pig84.ab.vo.coupon;

import java.util.List;
import com.pig84.ab.utils.Event;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.thirdParties.Hlc;

/**
 * 第三方定时规则
 * @author gxt
 *
 */
public class TimingHandleRule extends Rule {

	public TimingHandleRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event) {
		if(event==Event.TIMINGHANDLE){
			//汇理财获取用户
			String hlc_user = PropertyManage.get("hlc_user");
			if(this.getTheValue().equals(hlc_user)){
				List<PassengerInfo> list=Hlc.hlc_user();
				if(null!=list&&list.size()>0){
					return list;
				}
			}
		}
		return null;
	}
}
