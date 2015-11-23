package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.gf.CouponStatVo;

/**
 * 
 * @author wangwenbin
 *
 * 2015-3-10
 */
/**
 * 优惠券统计相关
 */
public interface ICouponStatDao {

	/**统计优惠券数量**/
	CouponStatVo queryCouponStatVo(Search search);

	/**某一优惠券的数量统计**/
	CouponStatVo queryCouponStatVoByCouponId(Search search);

	/**优惠券使用记录**/
	Map<String, Object> queryCouponUserRecordList(Search search,
			int curPageIndex, int pageSize);

}
