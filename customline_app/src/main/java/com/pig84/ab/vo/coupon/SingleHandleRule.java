package com.pig84.ab.vo.coupon;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pig84.ab.utils.Event;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.thirdParties.Hlc;

/**
 * 第三方操作规则
 * @author gxt
 *
 */
public class SingleHandleRule extends Rule {

	public SingleHandleRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfo> filter(Object obj,PassengerInfo passenger,Event event) {
		if(event==Event.SINGLEHANDLE){
			//汇理财注册
			String hlc_regist = PropertyManage.get("hlc_regist");
			if(this.getTheValue().equals(hlc_regist)){
				//1：校验不过，非法调用
				//2：手机号码已经注册
				//3：程序处理异常
				//0000：成功
				String result=Hlc.hlc_regist(hlc_regist, passenger);
				if("2".equals(result)){//如果返回2，则说明用户已在汇理财注册，可发优惠券
					return Arrays.asList(passenger);
				}
			}
			
			//泡椒游戏名称
			String gameNames = PropertyManage.get("game_names");
			if(StringUtils.isNotBlank(gameNames)){
				List<String> gameNameList=Arrays.asList(gameNames.split(","));
				if(null!=gameNameList&&gameNameList.size()>0){
					if(gameNameList.contains(this.getTheValue())){
						return Arrays.asList(passenger);
					}
				}
			}
		}
		return null;
	}
}
