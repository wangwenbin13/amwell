package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.gf.CouponVo;

public interface ICouponService {

	int addCoupon(CouponVo vo);

	Map<String, Object> getList(Search search, int currentPageIndex,int pageSize);

	int closeCoupon(String couponId);

	boolean checkCouponGetState(String couponId);

	CouponVo getCoupon(String couponId);

	int updateCoupon(CouponVo vo);

	long getIssueNum(CouponVo vo);

}
