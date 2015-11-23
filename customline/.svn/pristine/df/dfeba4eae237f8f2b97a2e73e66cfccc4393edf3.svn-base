package com.amwell.dao.coupon;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponInfo;

/**
 * 优惠券信息
 * @author gxt
 *
 */
public interface ICouponInfoDao {
	
	/**
	 * 保存或修改优惠券
	 * @param coupon
	 * @return
	 */
	int updateCoupon(CouponInfo coupon);
	
	/**
	 * 删除优惠券（逻辑删除）
	 * @param couponIds
	 * @return
	 */
	int delCoupon(String couponIds);
	
	
	/**
	 * 多条件查询优惠券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
	
	/**
	 * 解除组合券与原优惠券的关联
	 * @param coupon
	 * @return
	 */
	int updateRelation(String couponGroupId);

	/**
	 * 查询优惠券对应的组合券信息
	 * @param couponId
	 * @param couponName
	 * @return
	 */
	List<CouponInfo> getCouponGroup(String couponId,String couponName,String effectiveTime,String expirationTime);
	
	/**
	 * 查询同一组合券中优惠券面值是否已经存在
	 * @param couponName
	 * @return
	 */
	List<CouponInfo> getCouponInfo(String couponValue,String couponGroupId);
	
	/**
	 * 根据组合券查询优惠券信息
	 * @param couponId
	 * @param couponName
	 * @return
	 */
	List<CouponInfo> getCouponByGroup(String couponGroupId,String couponGroupName);
	
	/**
	 * 获取所有系统发放类型的优惠券
	 * @return
	 */
	List<CouponInfo> getSysSendCoupon(String couponGroupGrantId);
	
	/**
	 * 根据优惠券名称查询优惠券id和数量
	 * @param couponName
	 * @return
	 */
	String getCouponIds(String[] couponName);
	
	/**优惠券数据迁移**/
	String updateData();
}
