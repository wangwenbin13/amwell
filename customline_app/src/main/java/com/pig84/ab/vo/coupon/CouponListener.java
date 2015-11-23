package com.pig84.ab.vo.coupon;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pig84.ab.dao.ICouponRuleDao;
import com.pig84.ab.service.IPassengerInfoService;
import com.pig84.ab.utils.ApplicationContextHolder;
import com.pig84.ab.utils.Event;
import com.pig84.ab.utils.Event.Listener;
import com.pig84.ab.vo.PassengerInfo;

/**
 * Coupon Listener.
 * 
 * @author GuoLin
 *
 */
public class CouponListener implements Listener {
	private Logger logger = LoggerFactory.getLogger(CouponListener.class);
	
	@Override
	public void trigger(Event event, Map<String, Object> data) {
		IPassengerInfoService service1 = ApplicationContextHolder.getBean(IPassengerInfoService.class);
		PassengerInfo user = null;

		//是否需要验证优惠券总数
		boolean isValidateTotalCount=false;  
		//游戏名称
		String gameName=null;
		
		if (event == Event.REGISTER) {
			user = (PassengerInfo) data.get("user");
			logger.info("注册，用户手机：{}，开始优惠券发放",user.getTelephone());
		} else if (event == Event.BUYTICKET) {
			String userid = (String) data.get("userId");
			user = service1.getPassengerById(userid, null);
			logger.info("买票，用户手机：{}，开始优惠券发放",user.getTelephone());
		} else if (event == Event.SINGLEHANDLE) {
			isValidateTotalCount=true;
			user = (PassengerInfo) data.get("user");
			gameName=(String) data.get("gameName");
			logger.info("第三方操作，用户手机：{}，开始优惠券发放",user.getTelephone());
		} else if (event == Event.TIMINGHANDLE) {
			user = new PassengerInfo();
			logger.info("第三方定时，开始优惠券发放");
		}
		
		if(null!=user){
			//查询所有符合条件的规则
			ICouponRuleDao couponRuleDao=(ICouponRuleDao) ApplicationContextHolder.getBean("couponRuleDaoImpl");
			List<GrantInfo> grantList=null;
			if(StringUtils.isNotBlank(gameName)){
				grantList=couponRuleDao.getGrantInfo(gameName);
			}
			else{
				grantList=couponRuleDao.getGrantInfo(user.getPassengerId(),isValidateTotalCount);
			}
			if(null!=user&&(null!=grantList&&grantList.size()>0)){
				//发放优惠券
				for (GrantInfo grantInfo : grantList) {
					grantInfo.promote(user,event);
				}
			}
		}
	}

	
	public static void init() {
		CouponListener listener = new CouponListener();
		Event.registerListener(Event.REGISTER, listener);
		Event.registerListener(Event.BUYTICKET, listener);
		Event.registerListener(Event.SINGLEHANDLE, listener);
		Event.registerListener(Event.TIMINGHANDLE, listener);
	}

}