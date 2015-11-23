package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ICouponStatDao;
import com.amwell.entity.Search;
import com.amwell.service.ICouponStatService;
import com.amwell.vo.gf.CouponStatVo;

/**
 * 
 * @author wangwenbin
 *
 * 2015-3-10
 */
/**
 * 优惠卷统计相关
 */
@Service("couponStatService")
public class CouponStatServiceImpl implements ICouponStatService{

	@Autowired
	private ICouponStatDao couponStatDao;

	/**统计优惠券数量**/
	public CouponStatVo queryCouponStatVo(Search search) {
		return couponStatDao.queryCouponStatVo(search);
	}

	/**某一优惠券的数量统计**/
	public CouponStatVo queryCouponStatVoByCouponId(Search search) {
		return couponStatDao.queryCouponStatVoByCouponId(search);
	}

	/**优惠券使用记录**/
	public Map<String, Object> queryCouponUserRecordList(Search search,
			int curPageIndex, int pageSize) {
		return couponStatDao.queryCouponUserRecordList(search,curPageIndex,pageSize);
	}
}
