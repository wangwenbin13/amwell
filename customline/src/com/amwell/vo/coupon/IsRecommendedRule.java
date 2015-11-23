package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;
import com.amwell.dao.coupon.ICouponRuleDao;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.app.bean.AppVo_1;

/**
 * 是否被推荐规则
 * @author gxt
 *
 */
public class IsRecommendedRule extends Rule {
	
	private ICouponRuleDao couponRuleDao;

	public IsRecommendedRule(String ruleName, String theCondition, String theValue) {
		super(ruleName, theCondition, theValue);
	}

	@Override
	public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers) {
		this.couponRuleDao=(ICouponRuleDao)obj;
		List<PassengerInfoEntity> list=new ArrayList<PassengerInfoEntity>();
		boolean valueFlag=false;
		if(getTheValue().equals("1")){
			valueFlag=true;
		}
		List<AppVo_1> voList=couponRuleDao.getIsRecommended();
		if(null!=voList&&voList.size()>0){
			for (PassengerInfoEntity passenger : passengers) {
				for (AppVo_1 appVo_1 : voList) {
					if((appVo_1.getA1().equals(passenger.getPassengerId())&&valueFlag)||(!appVo_1.getA1().equals(passenger.getPassengerId())&&!valueFlag)){
						list.add(passenger);
					}
				}
			}
		}
		return list;
	}
}
