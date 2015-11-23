package com.pig84.ab.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ICouponDao;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_10;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_6;

/**
 * 优惠券相关
 * @author 
 *
 */
@Repository
public class CouponDaoImpl extends BaseDao implements ICouponDao {
	private Object[] args = null;

	/**根据用户电话查询所有的未过期并未使用优惠券**/
	@Override
	public List<AppVo_6> getCoupon(Connection conn,QueryRunner qr,String couponDetailId,String userId,String telephone, int pageSize,
			int currentPage,String totalPrcie) {
		List<AppVo_6> list=new ArrayList<AppVo_6>();
		if(StringUtils.isBlank(userId) && StringUtils.isBlank(telephone)) {
			return list;
		}
		StringBuffer sql=new StringBuffer(
				   "SELECT cast(ci.couponValue as decimal(4,0)) AS a1,cast(ci.couponCon as decimal(4,0)) AS a2,'2' AS a3,left(cgp.effectiveTime,10) AS a4,left(cgp.expirationTime,10) AS a5,ci.couponName AS a6 FROM " +
				   "coupon_group_passenger_detail cgp LEFT JOIN coupon_info ci ON cgp.couponId = ci.id " +
				   "WHERE cgp.useState = 'unused' " +
		           "AND cgp.expirationTime >= '"+MyDate.Format.DATE.now()+"'");
		List<String> condition=new ArrayList<String>();
		if(StringUtils.isNotBlank(couponDetailId)){
			sql.append(" and cgp.id = ? ");
			condition.add(couponDetailId);
		}
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and cgp.passengerId = ? ");
			condition.add(userId);
		}
		if(StringUtils.isNotBlank(telephone)){
			sql.append(" and cgp.telephone = ? ");
			condition.add(telephone);
		}
		if(StringUtils.isNotBlank(totalPrcie)){
			sql.append("  AND ci.couponCon<=? ");
			condition.add(totalPrcie);
		}
		sql.append(" ORDER BY cgp.effectiveTime,cgp.expirationTime,ci.couponName ");
		
		if(null==conn||null==qr){
			if(pageSize==0){//不分页查询
				list = queryList(AppVo_6.class, sql.toString(),condition.toArray());
			}
			else{//分页查询
				list = queryByPage(AppVo_6.class, sql.toString(),currentPage, pageSize,condition.toArray());
			}
		}
		else{
			try {
				if(pageSize==0){//不分页查询
					list = qr.query(conn, sql.toString(), new BeanListHandler<AppVo_6>(AppVo_6.class), condition.toArray());
				}
				else{//分页查询
					sql.append(" limit ?,? ");
					condition.add((currentPage-1)*pageSize+"");
					condition.add(pageSize+"");
					list = qr.query(conn, sql.toString(), new BeanListHandler<AppVo_6>(AppVo_6.class), condition.toArray());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 查询可用优惠券列表
	 */
	public List<AppVo_10> getUsableCoupon(String userId,String telephone,String orderPrice,int pageSize,int currentPage){
		List<AppVo_10> list= new ArrayList<AppVo_10>();
		
		if(StringUtils.isBlank(userId) && StringUtils.isBlank(telephone)){
			return list;
		}
		
		StringBuffer sql=new StringBuffer(
				   "SELECT cast(ci.couponValue as decimal(4,0)) AS a1,cast(ci.couponCon as decimal(4,0)) AS a2,'2' AS a3,left(cgp.effectiveTime,10) AS a4,"+
		           "left(cgp.expirationTime,10) AS a5,cgp.id as a6,ci.couponName AS a7,CURDATE() as a8 FROM " +
				   "coupon_group_passenger_detail cgp LEFT JOIN coupon_info ci ON cgp.couponId = ci.id " +
				   "WHERE cgp.useState = 'unused' " +
				   "AND cgp.effectiveTime <= '"+MyDate.Format.DATE.now()+"' " +
				   "AND cgp.expirationTime >= '"+MyDate.Format.DATE.now()+"'");
		List<String> condition=new ArrayList<String>();
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and cgp.passengerId = ? ");
			condition.add(userId);
		}
		if(StringUtils.isNotBlank(telephone)){
			sql.append(" and cgp.telephone = ? ");
			condition.add(telephone);
		}
		if(StringUtils.isNotBlank(orderPrice)){
			sql.append(" and ci.couponCon <= ? ");
			condition.add(orderPrice);
		}
		sql.append(" ORDER BY cgp.expirationTime,ci.couponValue ");
		if(pageSize==0){//不分页查询
			list = queryList(AppVo_10.class, sql.toString(),condition.toArray());
		}
		else{//分页查询
			list = queryByPage(AppVo_10.class, sql.toString(),currentPage, pageSize,condition.toArray());
		}
		return list;
	}
	
	/**
	 * 修改优惠券使用状态（支付时使用优惠券，支付成功则修改优惠券状态）
	 * @param vo
	 * @return
	 */
	public int updateCouponState(Connection conn,QueryRunner qr,String useState,String orderNo,String useTime,String couponDetailId){
		String sql=null;
		int flag=0;
		if(StringUtils.isNotBlank(couponDetailId)){
			sql="UPDATE coupon_group_passenger_detail SET useState = ?,orderId = ?,useTime = ? WHERE id = ?";
			args=new Object[4];
			args[0]=useState;
			args[1]=orderNo;
			args[2]=useTime;
			args[3]=couponDetailId;
		}
		else{
			sql="UPDATE coupon_group_passenger_detail SET useState = ?,orderId = null,useTime = null WHERE orderId = ?";
			args=new Object[2];
			args[0]=useState;
			args[1]=orderNo;
		}
		if(null==conn||null==qr){
			flag = executeSQL(sql, args);
		}
		else{
			try {
				flag = qr.update(conn, sql, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/** 根据订单号查找对应的优惠券信息 **/
	public String findCouponDetailId(String outTradeNo) {
		String sql = " SELECT id FROM coupon_group_passenger_detail WHERE orderId = ? ";
		args = new Object[1];
		args[0] = outTradeNo;
		String couponTeleId = queryBean(String.class, sql, args);
		return couponTeleId;
	}

	/**
	 * 查询在有效期内的可用优惠券列表
	 *   . 按结束时间由近到远排序
	 *   . 相同时间的，按优惠券价格排序，由小到大
	 */
	public List<AppVo_6> getMyUsableCoupon(String userId,String telephone,int currentIndex,int theCount){
		List<AppVo_6> list= new ArrayList<AppVo_6>();
		
		if(StringUtils.isBlank(userId) && StringUtils.isBlank(telephone)){
			return list;
		}
		
		StringBuffer sql=new StringBuffer(
				   "SELECT cast(ci.couponValue as decimal(4,0)) AS a1,cast(ci.couponCon as decimal(4,0)) AS a2,ci.couponName AS a3,left(cgp.effectiveTime,10) AS a4,left(cgp.expirationTime,10) AS a5 FROM " +
				   "coupon_group_passenger_detail cgp LEFT JOIN coupon_info ci ON cgp.couponId = ci.id " +
				   "WHERE cgp.useState = 'unused' " +
				   "AND cgp.effectiveTime <= '"+MyDate.Format.DATE.now()+"' " +
				   "AND cgp.expirationTime >= '"+MyDate.Format.DATE.now()+"'");
		List<String> condition=new ArrayList<String>();
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and cgp.passengerId = ? ");
			condition.add(userId);
		}
		if(StringUtils.isNotBlank(telephone)){
			sql.append(" and cgp.telephone = ? ");
			condition.add(telephone);
		}
		sql.append(" ORDER BY cgp.expirationTime,ci.couponValue ");
		
		//手动分页
		sql.append(" limit "+currentIndex+","+theCount);
		list = queryList(AppVo_6.class, sql.toString(),condition.toArray());
		return list;
	}
	
	/**
	 * 查询未到有效期开始时间的优惠券列表
	 *    . 按开始时间由近到远
	 *    . 相同时间的，按优惠券价格排序，由小到大
	 */
	public List<AppVo_6> getUnStartCoupon(String userId,String telephone,int currentIndex,int theCount){
		List<AppVo_6> list= new ArrayList<AppVo_6>();
		
		if(StringUtils.isBlank(userId) && StringUtils.isBlank(telephone)){
			return list;
		}
		
		StringBuffer sql=new StringBuffer(
				   "SELECT cast(ci.couponValue as decimal(4,0)) AS a1,cast(ci.couponCon as decimal(4,0)) AS a2,ci.couponName AS a3,left(cgp.effectiveTime,10) AS a4,left(cgp.expirationTime,10) AS a5 FROM " +
				   "coupon_group_passenger_detail cgp LEFT JOIN coupon_info ci ON cgp.couponId = ci.id " +
				   "WHERE cgp.useState = 'unused' " +
				   "AND cgp.effectiveTime > '"+MyDate.Format.DATE.now()+"' ");
		List<String> condition=new ArrayList<String>();
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and cgp.passengerId = ? ");
			condition.add(userId);
		}
		if(StringUtils.isNotBlank(telephone)){
			sql.append(" and cgp.telephone = ? ");
			condition.add(telephone);
		}
		sql.append(" ORDER BY cgp.effectiveTime,ci.couponValue ");
		
		//手动分页
		sql.append(" limit "+currentIndex+","+theCount);
		list = queryList(AppVo_6.class, sql.toString(),condition.toArray());
		return list;
	}
	
	
	/**
	 * 查询已过期的优惠券列表
	 *    . 按结束时间由近到远
	 */
	public List<AppVo_6> getExpiredCoupon(String userId,String telephone,int currentIndex,int theCount){
		List<AppVo_6> list= new ArrayList<AppVo_6>();
		
		if(StringUtils.isBlank(userId) && StringUtils.isBlank(telephone)) {
			return list;
		}
		
		StringBuffer sql=new StringBuffer(
				   "SELECT cast(ci.couponValue as decimal(4,0)) AS a1,cast(ci.couponCon as decimal(4,0)) AS a2,ci.couponName AS a3,left(cgp.effectiveTime,10) AS a4,left(cgp.expirationTime,10) AS a5 FROM " +
				   "coupon_group_passenger_detail cgp LEFT JOIN coupon_info ci ON cgp.couponId = ci.id " +
				   "WHERE cgp.useState = 'expired' " +
				   "AND cgp.expirationTime < '"+MyDate.Format.DATE.now()+"' ");
		List<String> condition=new ArrayList<String>();
		if(StringUtils.isNotBlank(userId)){
			sql.append(" and cgp.passengerId = ? ");
			condition.add(userId);
		}
		if(StringUtils.isNotBlank(telephone)){
			sql.append(" and cgp.telephone = ? ");
			condition.add(telephone);
		}
		sql.append(" ORDER BY cgp.expirationTime desc ");
		
		//手动分页
		sql.append(" limit "+currentIndex+","+theCount);
		list = queryList(AppVo_6.class, sql.toString(),condition.toArray());
		return list;
	}
	
	/**获取优惠券面值信息**/
	public AppVo_1 getCouponValueById(String counponTeleId){
//		String sql="SELECT gg.giftValue AS a1 FROM gf_coupon_passenger gcp,gf_coupon_gift gcg,gf_gifts gg " +
//				"WHERE gcp.couponGiftId = gcg.couponGiftId AND gcg.giftPriId = gg.giftPriId AND gcp.counponTeleId = ?";
//		Object[] params = new Object[1];
//		params[0]=counponTeleId;
//		return queryBean(AppVo_1.class, sql,params);
		
		String sql="SELECT b.couponValue AS a1 FROM coupon_group_passenger_detail a LEFT JOIN coupon_info b ON a.couponGroupId = b.couponGroupId AND a.couponId = b.id WHERE a.id = ?";
		Object[] params = new Object[1];
		params[0]=counponTeleId;
		return queryBean(AppVo_1.class, sql,params);
	}
	
	/**统计与泡椒游戏相关的已发放优惠券总金额**/
	public AppVo_1 getCouponValTotal(){
		//查询所有的优惠券发放id
		String sql="SELECT id AS a1 FROM coupon_group_grant WHERE sendCouponType = 'user get' AND startTime <= '"+MyDate.Format.DATE.now()+"' AND endTime >= '"+MyDate.Format.DATE.now() +
				"' AND modeOfExecution = 'send' and selectPass = 'newUser'";
		List<AppVo_1> vo1=queryList(AppVo_1.class, sql);
			
		if(null==vo1||vo1.isEmpty()){
			return null;
		}
		
		//泡椒游戏名称
		String gameNames = PropertyManage.get("game_names");
		List<String> gameNameList=null;
		if(StringUtils.isNotBlank(gameNames)){
			gameNameList=Arrays.asList(gameNames.split(","));
		}
		if(null==gameNameList||gameNameList.isEmpty()){
			return null;
		}

		StringBuffer sb=new StringBuffer();
		List<String> l=new ArrayList<String>();
		for (AppVo_1 appVo_1 : vo1) {
			sb.append("?,");
			l.add(appVo_1.getA1());
		}
		
		//根据优惠券发放id查询对应的规则
		sql="SELECT couponGroupGrantId as a1,theValue as a2 FROM coupon_rule WHERE couponGroupGrantId in("+sb.substring(0,sb.length()-1)+")";
		List<AppVo_2> ruleList=queryList(AppVo_2.class, sql, l.toArray());
		if(null==ruleList||ruleList.isEmpty()){
			return null;
		}
		
		//根据优惠券发放id查询已发放的优惠券总金额
		sql="SELECT b.grantId as a1,SUM(a.couponValue) AS a2 FROM coupon_info a LEFT JOIN coupon_group_passenger_detail b ON a.couponGroupId = b.couponGroupId AND a.id = b.couponId "
				+ "WHERE b.grantId in("+sb.substring(0,sb.length()-1)+") group by b.grantId";
		List<AppVo_2> valList=queryList(AppVo_2.class, sql,  l.toArray());
		if(null==valList||valList.isEmpty()){
			return null;
		}
		
		//保存已发放的优惠券总金额
		BigDecimal bd=new BigDecimal("0.00");
		for (String str : l) {
			//保存一个发放下的规则信息
			List<AppVo_2> ruleTemp=new ArrayList<AppVo_2>();
			for (AppVo_2 appVo_2 : ruleList) {
				if(str.equals(appVo_2.getA1())){
					ruleTemp.add(appVo_2);
				}
			}
			//判断规则信息里是否指定泡椒游戏名称
			boolean flag=false;
			for (AppVo_2 appVo_2 : ruleTemp) {
				if(gameNameList.contains(appVo_2.getA2())){
					flag=true;
					break;
				}
			}
			//如果有指定泡椒游戏名称，则累加已发放优惠券金额
			if(flag){
				for (AppVo_2 appVo_2 : valList) {
					if(str.equals(appVo_2.getA1())&&StringUtils.isNotBlank(appVo_2.getA2())){
						bd=bd.add(new BigDecimal(appVo_2.getA2()));
					}
				}
			}
		}
		
		AppVo_1 vo=new AppVo_1();
		vo.setA1(bd.toString());
		return vo;
	}
}
