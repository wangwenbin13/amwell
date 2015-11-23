package com.amwell.service.coupon.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.base.DaoSupport;
import com.amwell.dao.coupon.ICouponGroupPassengerDetailDao;
import com.amwell.entity.Search;
import com.amwell.service.coupon.ICouponGroupPassengerDetailService;

/**
 * 用户优惠券详情
 * @author gxt
 *
 */
@Service("couponGroupPassengerDetailService")
public class CouponGroupPassengerDetailServiceImpl extends DaoSupport implements ICouponGroupPassengerDetailService {
	
	@Autowired
	private ICouponGroupPassengerDetailDao dao;
	
	/**
	 * 多条件查询用户优惠券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize){
		return dao.getListByCondition(search, currentPage, pageSize);
	}
}
