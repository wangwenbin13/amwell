package com.amwell.service.coupon.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.base.DaoSupport;
import com.amwell.dao.coupon.ICouponGroupDao;
import com.amwell.entity.Search;
import com.amwell.service.coupon.ICouponGroupService;
import com.amwell.vo.coupon.CouponGroup;

/**
 * 组合券信息
 * @author gxt
 *
 */
@Service("couponGroupService")
public class CouponGroupServiceImpl extends DaoSupport implements ICouponGroupService {
	
	@Autowired
	private ICouponGroupDao couponGroupDao;

	/**
	 * 保存或修改组合券
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCouponGroup(CouponGroup couponGroup){
		return couponGroupDao.updateCouponGroup(couponGroup);
	}
	
	/**
	 * 多条件查询组合券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search, int currentPage,
			int pageSize) {
		return couponGroupDao.getListByCondition(search, currentPage, pageSize);
	}
	
	/**
	 * 查询组合券批次号
	 * @return
	 */
	public String getCouponGroupID(){
		return couponGroupDao.getCouponGroupID();
	}
	
	/**
	 * 根据发放id查询优惠券信息
	 * @param couponGroupGrantId
	 * @return
	 */
	public CouponGroup getCouponGroup(String couponGroupGrantId){
		return couponGroupDao.getCouponGroup(couponGroupGrantId);
	}
	
	/**
	 * 根据组合券id查询组合券信息
	 * @param couponGroupId
	 * @return
	 */
	public CouponGroup getCouponGroupById(String couponGroupId){
		return couponGroupDao.getCouponGroupById(couponGroupId);
	}
	
	/**
	 * 多条件查询优惠券统计列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getCouponStatistics(Search search,int currentPage, int pageSize){
		return couponGroupDao.getCouponStatistics(search, currentPage, pageSize);
	}
}
