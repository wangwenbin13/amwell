package com.pig84.ab.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_10;
import com.pig84.ab.vo.bean.AppVo_6;

/**
 * 新版优惠券相关接口
 * @author 
 *
 */
public interface ICouponDao {
	
	/**根据用户电话查询所有的未使用优惠券**/
	public List<AppVo_6> getCoupon(Connection conn,QueryRunner qr,String couponDetailId,String userId,String telephone,int pageSize,int currentPage,String totalPrcie);
	
	/**
	 * 查询可用优惠券列表
	 */
	public List<AppVo_10> getUsableCoupon(String userId,String telephone,String orderPrice,int pageSize,int currentPage);
	
	/**
	 * 修改优惠券使用状态（支付时使用优惠券，支付成功则修改优惠券状态）
	 * @param vo
	 * @return
	 */
	public int updateCouponState(Connection conn,QueryRunner qr,String useState,String orderNo,String useTime,String couponDetailId);
	
	/** 根据订单号查找对应的优惠券信息 **/
	public String findCouponDetailId(String outTradeNo);
	
	/**
	 * 查询在有效期内的可用优惠券列表
	 *   . 按结束时间由近到远排序
	 *   . 相同时间的，按优惠券价格排序，由小到大
	 */
	public List<AppVo_6> getMyUsableCoupon(String userId,String telephone,int currentIndex,int theCount);
	
	/**
	 * 查询未到有效期开始时间的优惠券列表
	 *    . 按开始时间由近到远
	 *    . 相同时间的，按优惠券价格排序，由小到大
	 */
	public List<AppVo_6> getUnStartCoupon(String userId,String telephone,int currentIndex,int theCount);
	
	/**
	 * 查询已过期的优惠券列表
	 *    . 按结束时间由近到远
	 */
	public List<AppVo_6> getExpiredCoupon(String userId,String telephone,int currentIndex,int theCount);
	
	/**获取优惠券面值信息**/
	public AppVo_1 getCouponValueById(String counponTeleId);
	
	/**统计与泡椒游戏相关的已发放优惠券总金额**/
	public AppVo_1 getCouponValTotal();
}
