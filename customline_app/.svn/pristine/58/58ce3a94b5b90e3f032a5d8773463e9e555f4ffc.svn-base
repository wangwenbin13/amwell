package com.pig84.ab.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ICouponGroupPassengerDetailDao;
import com.pig84.ab.vo.coupon.CouponGroup;

/**
 * 用户优惠券详情
 * @author gxt
 *
 */
@Repository(value="couponGroupPassengerDetailDaoImpl")
public class CouponGroupPassengerDetailDaoImpl extends BaseDao implements ICouponGroupPassengerDetailDao {

	/**
	 * 批量添加优惠券
	 * @param sql
	 * @return
	 */
	@Override
	public int updateCouponDetailBatch(String sql){
		return executeSQL(sql);
	}
	
	/**
	 * 查询用户已发放的优惠券总数
	 * @param grantId
	 * @param couponGroupId
	 * @param passengerId
	 * @return
	 */
	@Override
	public int getCouponDetail(String grantId,String couponGroupId,String passengerId){
		StringBuffer sql=new StringBuffer("SELECT id FROM coupon_group_passenger_detail WHERE couponGroupId = ?");
		List<String> condition=new ArrayList<String>();
		condition.add(couponGroupId);
		if(StringUtils.isNotBlank(grantId)){
			sql.append(" and grantId = ? ");
			condition.add(grantId);
		}
		if(StringUtils.isNotBlank(passengerId)){
			sql.append(" AND passengerId = ? ");
			condition.add(passengerId);
		}
		return queryCount(sql.toString(), condition.toArray());
	}
	
	/**
	 * 根据组合券id查询组合券信息
	 * @param couponGroupId
	 * @return
	 */
	@Override
	public CouponGroup getCouponGroupById(String couponGroupId){
		String sql="SELECT couponGroupID,couponGroupName,couponGroupCount,averageNum FROM coupon_group WHERE couponGroupID = ?";
		Object[] params=new Object[1];
		params[0]=couponGroupId;
		return queryBean(CouponGroup.class, sql, params);
	}
}
