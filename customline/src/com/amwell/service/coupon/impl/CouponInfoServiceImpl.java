package com.amwell.service.coupon.impl;

import java.util.List;
import java.util.Map;

//import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.base.DaoSupport;
import com.amwell.dao.coupon.ICouponInfoDao;
import com.amwell.entity.Search;
import com.amwell.service.coupon.ICouponInfoService;
import com.amwell.vo.coupon.CouponInfo;

/**
 * 优惠券信息
 * @author gxt
 *
 */
@Service("couponInfoService")
public class CouponInfoServiceImpl extends DaoSupport implements ICouponInfoService {

	@Autowired
	private ICouponInfoDao couponDao;
	
	/**
	 * 保存或修改优惠券
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCoupon(CouponInfo coupon) {
		return couponDao.updateCoupon(coupon);
	}

	/**
	 * 删除优惠券（逻辑删除）
	 * @param couponIds
	 * @return
	 */
	@Override
	public int delCoupon(String couponIds) {
		return couponDao.delCoupon(couponIds);
	}
	
	/**
	 * 多条件查询优惠券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search, int currentPage,
			int pageSize) {
		return couponDao.getListByCondition(search, currentPage, pageSize);
	}

	/**
	 * 解除组合券与原优惠券的关联
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateRelation(String couponGroupId) {
		return couponDao.updateRelation(couponGroupId);
	}
	
	/**
	 * 根据优惠券名称查询优惠券信息
	 * @param couponName
	 * @return
	 */
	@Override
	public List<CouponInfo> getCouponInfo(String couponName,String condition){
		return couponDao.getCouponInfo(couponName, condition);
	}
	
	/**
	 * 根据优惠券名称查询优惠券id和数量
	 * @param couponName
	 * @return
	 */
	public String getCouponIds(String[] couponName){
		return couponDao.getCouponIds(couponName);
	}
	
	/**优惠券数据迁移**/
	public String updateData(){
		return couponDao.updateData();
	}
}
