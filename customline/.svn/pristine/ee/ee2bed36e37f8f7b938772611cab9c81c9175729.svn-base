package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ICouponDao;
import com.amwell.entity.Search;
import com.amwell.service.ICouponService;
import com.amwell.vo.gf.CouponVo;


@Service("couponService")
public class CouponServiceImpl implements ICouponService {

	@Autowired
	private ICouponDao couponDao;
	
	public int addCoupon(CouponVo vo) {
		return couponDao.addCoupon(vo);
	}

	public Map<String, Object> getList(Search search, int currentPageIndex,
			int pageSize) {
		return couponDao.getList(search,currentPageIndex,pageSize);
	}

	public int closeCoupon(String couponId) {
		return couponDao.closeCoupon(couponId);
	}

	public boolean checkCouponGetState(String couponId) {
		return couponDao.checkCouponGetState(couponId);
	}

	public CouponVo getCoupon(String couponId) {
		return couponDao.getCoupon(couponId);
	}

	public int updateCoupon(CouponVo vo) {
		return couponDao.updateCoupon(vo);
	}

	public long getIssueNum(CouponVo vo) {
		return couponDao.getIssueNum(vo);
	}

}
