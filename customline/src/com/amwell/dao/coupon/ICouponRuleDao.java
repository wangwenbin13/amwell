package com.amwell.dao.coupon;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.coupon.CouponRule;
import com.amwell.vo.coupon.GrantInfo;

/**
 * 优惠券发放规则信息
 * @author gxt
 *
 */
public interface ICouponRuleDao {
	
	/**
	 * 保存或修改规则
	 * @param coupon
	 * @return
	 */
	int updateCouponRule(CouponRule couponRule);
	
	/**
	 * 删除规则信息
	 * @param grantId
	 * @return
	 */
	int deleteCouponRule(String grantId);
	
	
	/**
	 * 多条件查询规则列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
	
	/**
	 * 根据城市id获取买过票的用户id
	 * @param lineBaseId
	 * @return
	 */
	List<AppVo_1> getLeaseBase(String lineBaseId);
	
	/**
	 * 根据注册时间、条件获取在指定时间范围内注册的用户id
	 * @param registTime
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getPassenger(String registTime,String theCondition);
	
	/**
	 * 查询规则信息
	 * @return
	 */
//	List<GrantInfo> getGrantInfo(String couponGroupGrantId);
	GrantInfo getGrantInfo(String couponGroupGrantId);
	
	/**
	 * 根据购票次数、条件获取在指定购票数范围内的用户id
	 * @param ticketNumber
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getTicketNumber(String ticketNumber,String theCondition);
	
	/**
	 * 根据推荐人数、条件获取在指定推荐人数范围内的用户id
	 * @param recommendNumber
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getRecommendNumber(String recommendNumber,String theCondition);
	
	/**
	 * 获取被推荐用户id
	 * @return
	 */
	List<AppVo_1> getIsRecommended();
	
	/**
	 * 根据发放id查询发放规则
	 * @param couponGroupGrantId
	 * @return
	 */
	List<CouponRule> getCouponRule(String couponGroupGrantId);
	
	/**
	 * 根据推荐时间、条件获取在指定时间范围内推荐的用户id
	 * @param recommendTime
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getRePassenger(String type,String recommendTime,String theCondition);
	
	/**
	 * 获取推荐用户id
	 * @return
	 */
	List<AppVo_1> getIsDoRecommend(String passengerId);
	
	/**
	 * 根据购票时间、条件获取在指定时间范围内买票的用户id
	 * @param ticketTime
	 * @param theCondition
	 * @return
	 */
	List<AppVo_1> getTicketPassenger(String ticketTime,String theCondition);

}
