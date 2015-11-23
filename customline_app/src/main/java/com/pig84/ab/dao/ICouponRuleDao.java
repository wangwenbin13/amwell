package com.pig84.ab.dao;

import java.util.List;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.coupon.CouponInfo;
import com.pig84.ab.vo.coupon.GrantInfo;

/**
 * 优惠券发放规则信息
 * @author gxt
 *
 */
public interface ICouponRuleDao {
	
	/**
	 * 根据城市id获取买过票的用户id
	 * @param lineBaseId
	 * @return
	 */
	List<AppVo_1> getLeaseBase(String passengerId,String lineBaseId);
	
	/**
	 * 根据注册时间、条件获取在指定时间范围内注册的用户id
	 * @param registTime
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getPassenger(String passengerId,String registTime,String theCondition);
	
	/**
	 * 根据推荐时间、条件获取在指定时间范围内推荐的用户id
	 * @param recommendTime
	 * @param theCondition
	 * @return
	 */
	List<PassengerInfo> getReOldPassenger(String passengerId,String recommendTime,String theCondition);
	
	List<PassengerInfo> getReNewPassenger(String telephone,String recommendTime,String theCondition);
	
	/**
	 * 根据购票次数、条件获取在指定购票数范围内的用户id
	 * @param ticketNumber
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getTicketNumber(String passengerId,String ticketNumber,String theCondition);
	
	/**
	 * 根据购票时间、条件获取在指定时间范围内买票的用户id
	 * @param ticketTime
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getTicketPassenger(String passengerId,String ticketTime,String theCondition);
	
	/**
	 * 根据推荐人数、条件获取在指定推荐人数范围内的用户id
	 * @param recommendNumber
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getRecommendNumber(String passengerId,String recommendNumber,String theCondition);
	
	/**
	 * 查询规则信息
	 * isValidateTotalCount  是否需要验证优惠券总数
	 * @return
	 */
	List<GrantInfo> getGrantInfo(String passengerId,boolean isValidateTotalCount);
	
	List<GrantInfo> getGrantInfo(String gameName);
	
	/**
	 * 获取所有系统发放类型的优惠券
	 * @return
	 */
	List<CouponInfo> getSysSendCoupon(String couponGroupGrantId);
}
