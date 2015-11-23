package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.ICouponStatDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.gf.CouponIdGifValueVo;
import com.amwell.vo.gf.CouponStatVo;
import com.amwell.vo.gf.CouponUserRecordVo;
import com.amwell.vo.gf.CouponVo;
/**
 * 
 * @author wangwenbin
 *
 * 2015-3-10
 */
/**
 * 优惠券统计相关
 */
@Repository("couponStatDao")
public class CouponStatDaoImpl extends DaoSupport implements ICouponStatDao{
	
	private static final Logger log = Logger.getLogger(CouponStatDaoImpl.class);

	/**统计优惠券数量**/
	public CouponStatVo queryCouponStatVo(Search search) {
		CouponStatVo couponStatVo = new CouponStatVo();
		Connection conn=null;
		try{
			conn =MyDataSource.getConnect();
			QueryRunner qr = new QueryRunner();
			String cond = "";
			List<Object> paramList = new ArrayList<Object>();
			if(search!=null){
				if(!StringUtils.isEmpty(search.getField01())){
					cond+= " AND cou.couponName like ? ";
					paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
				}
				if(!StringUtils.isEmpty(search.getField02())){
					cond+= " and left(cou.effectiveTime,10) >=? ";
					paramList.add(search.getField02().trim());
				}
				if(!StringUtils.isEmpty(search.getField03())){
					cond+= " and left(cou.expirationTime,10) <=? ";
					paramList.add(search.getField03().trim());
				}
			}
			Object[] params = paramList.toArray(new Object[]{});
			
			/**(用户领取)优惠券数量**/
			sql = " SELECT sum(issueNum) as couponTotal FROM gf_coupon as cou WHERE cou.sendCouponType =1 " +cond;
			Long couponTotal = qr.query(conn,sql, new LongResultSetHandler(), params);
			
			/**(系统发放)优惠券数量=下线时间之前实时用户数量*发放份数**/
			/**如果选择对应的城市进行系统发放时，发放量统计了所有用户量，应该是对应城市的用户量**/
			sql = " SELECT cou.couponId,cou.downLineTime,cou.limitNum,cou.selectPass,cou.cityCode From  gf_coupon as cou WHERE cou.sendCouponType = 2 "+cond;
			List<CouponVo> couponVos = qr.query(conn,sql, new BeanListHandler<CouponVo>(CouponVo.class), params);
			/**下线时间之前对应的乘客的数量**/
			Long count = 0L;
			/**优惠券ID对应的乘客数量maps**/
			Map<String,Long> maps = new HashMap<String,Long>();
			Long couponPass = 0L;
			if(null!=couponVos && couponVos.size()>0){
				for(CouponVo couponVo : couponVos){
					if(couponVo.getSelectPass()==0){
						/**0:全部用户**/
						sql = " SELECT count(*) FROM passenger_info WHERE registerTime<= '"+couponVo.getDownLineTime()+"' AND cityCode = "+couponVo.getCityCode();
						couponPass = qr.query(conn,sql, new LongResultSetHandler());
						maps.put(couponVo.getCouponId(), couponPass*couponVo.getLimitNum());
						count = count+couponPass*couponVo.getLimitNum();
					}
					if(couponVo.getSelectPass()==1){
						/**1:新用户**/
						/**到优惠券下线之前都没有购买过票的用户都属于新用户**/
						sql = " SELECT count(*) FROM passenger_info AS passengerInfo  "+
						" WHERE 1=1 AND passengerInfo.passengerId NOT IN ( SELECT passengerId FROM lease_base_info AS leab WHERE leab.ispay = 1 ) AND passengerInfo.registerTime<= '"+couponVo.getDownLineTime()+"' AND passengerInfo.cityCode = "+couponVo.getCityCode();
						couponPass = qr.query(conn,sql, new LongResultSetHandler());
						maps.put(couponVo.getCouponId(), couponPass*couponVo.getLimitNum());
						count = count+couponPass*couponVo.getLimitNum();
					}
					if(couponVo.getSelectPass()==3){
						/**3:自定义用户**/
						sql = " SELECT count(*) FROM gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON cougif.couponGiftId = pass.couponGiftId "+
							" WHERE 1=1 AND cougif.couponId =  '"+couponVo.getCouponId()+"'";
						couponPass = qr.query(conn,sql, new LongResultSetHandler());
						maps.put(couponVo.getCouponId(), couponPass);
						count = count+couponPass;
					}
				}
			}
			
			
			/**优惠券数量**/
			couponTotal = (couponTotal+count);
			
			/**(用户领取)优惠券总金额**/
			sql = " SELECT sum(cou.issueNum*gif.giftValue) as couponTotalMon FROM gf_coupon as cou LEFT JOIN gf_coupon_gift as cougif ON cou.couponId = cougif.couponId LEFT JOIN gf_gifts AS gif ON cougif.giftPriId = gif.giftPriId " +
					"WHERE cou.sendCouponType = 1 " +cond;
			Long couponTotalMon = qr.query(conn,sql, new LongResultSetHandler(), params);
			
			/**(系统发放)优惠券总金额 = (优惠券的)面值*该优惠券对应的乘客数量+....**/
			sql = " SELECT gif.giftValue,cougif.couponId FROM gf_gifts AS gif LEFT JOIN gf_coupon_gift AS cougif ON gif.giftPriId = cougif.giftPriId ";
			List<CouponIdGifValueVo> couponIdGifValueVos = qr.query(conn,sql, new BeanListHandler<CouponIdGifValueVo>(CouponIdGifValueVo.class));
			if(null!=couponIdGifValueVos && couponIdGifValueVos.size()>0){
				for(CouponIdGifValueVo couponIdGifValueVo :couponIdGifValueVos){
					if(null!=maps.get(couponIdGifValueVo.getCouponId())){
						couponTotalMon = (couponIdGifValueVo.getGiftValue())*(maps.get(couponIdGifValueVo.getCouponId()))+couponTotalMon;
					}
				}
			}
			
			
			/**优惠券使用数量**/
			sql = " SELECT count(*) FROM gf_coupon_passenger as pass LEFT JOIN "+
				" gf_coupon_gift as cougif ON pass.couponGiftId = cougif.couponGiftId "+
				" LEFT JOIN gf_coupon AS cou ON cou.couponId = cougif.couponId WHERE useState = 1 "+cond;
			long couponUser = qr.query(conn,sql, new LongResultSetHandler(), params);
			
			/**优惠券使用金额**/
			sql = " SELECT sum(gif.giftValue) as couponUserMon FROM gf_coupon_passenger as pass LEFT JOIN "+
				" gf_coupon_gift as cougif ON pass.couponGiftId = cougif.couponGiftId LEFT JOIN gf_coupon AS cou ON cou.couponId = cougif.couponId "+
				" LEFT JOIN gf_gifts AS gif ON cougif.giftPriId = gif.giftPriId WHERE useState = 1 "+cond;
			Long couponUserMon = qr.query(conn,sql, new LongResultSetHandler(), params);
			
			/**优惠卷已领数量(不包含系统发放)**/
			sql = " SELECT count(*) FROM gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON cougif.couponGiftId = pass.couponGiftId "+
				" LEFT JOIN gf_coupon AS cou ON cougif.couponId = cou.couponId WHERE getState = 1 "+cond;
			long couponGetTotal = qr.query(conn,sql, new LongResultSetHandler(), params);
			
			/**优惠券已领金额**/
			sql = " SELECT sum(gif.giftValue) as couponGetMon FROM gf_coupon_passenger as pass LEFT JOIN "+
				" gf_coupon_gift as cougif ON pass.couponGiftId = cougif.couponGiftId LEFT JOIN gf_coupon AS cou ON cou.couponId = cougif.couponId "+
				" LEFT JOIN gf_gifts AS gif ON cougif.giftPriId = gif.giftPriId WHERE getState = 1 "+cond;
			Long couponGetMon = qr.query(conn,sql, new LongResultSetHandler(), params);
			
			couponStatVo.setCouponTotal(couponTotal);
			couponStatVo.setCouponTotalMon(couponTotalMon);
			couponStatVo.setCouponUser(couponUser);
			couponStatVo.setCouponUserMon(couponUserMon);
			couponStatVo.setCouponUnUser(couponTotal - couponUser);
			couponStatVo.setCouponUnUserMon(couponTotalMon - couponUserMon);
			couponStatVo.setCouponGetTotal(couponGetTotal);
			couponStatVo.setCouponGetMon(couponGetMon);
			couponStatVo.setCouponGetUser(couponUser);
			couponStatVo.setCouponGetUserMon(couponUserMon);
			couponStatVo.setCouponGetUnUser(couponGetTotal - couponUser);
			couponStatVo.setCouponGetUnUserMon(couponGetMon - couponUserMon);
			
			
		}catch (Exception e) {
			log.info("统计优惠券数量异常");
			e.printStackTrace();
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return couponStatVo;
	}

	/**某一优惠券的数量统计**/
	public CouponStatVo queryCouponStatVoByCouponId(Search search) {
		if (search==null) {
			search = new Search();
		}
		CouponStatVo couponStatVo = new CouponStatVo();
		Connection conn=null;
		try{
			conn =MyDataSource.getConnect();
			QueryRunner qr = new QueryRunner();
			String cond = "";
			List<Object> paramList = new ArrayList<Object>();
			if(search!=null){
				if(!StringUtils.isEmpty(search.getField01())){
					cond+= " AND cougif.couponId = ? ";
					paramList.add(search.getField01().trim());
				}
				/***领取时间段**/
				if(!StringUtils.isEmpty(search.getField05())){
					cond+= " and left(pass.userTime,10) >=? ";
					paramList.add(search.getField05().trim());
				}
				if(!StringUtils.isEmpty(search.getField06())){
					cond+= " and left(pass.userTime,10) <=? ";
					paramList.add(search.getField06().trim());
				}
			}
			
			Object[] params = paramList.toArray(new Object[]{});
			
			sql = " SELECT sendCouponType FROM gf_coupon as cou WHERE 1=1 AND cou.couponId = '"+search.getField01().trim()+"'";
			Long sendCouponType = qr.query(conn,sql, new LongResultSetHandler());
			
			Long couponTotal = 0L;
			Long couponGetTotal = 0L;
			
			/**优惠券的部分数据**/
			sql = " SELECT effectiveTime,expirationTime,downLineTime,cou.limitNum,cou.selectPass,cou.cityCode FROM gf_coupon as cou WHERE 1=1  AND cou.couponId = '"+search.getField01().trim()+"'";
			CouponVo couponVo = qr.query(conn, sql, new BeanHandler<CouponVo>(CouponVo.class));
			/**页面搜索的时间条件(优惠券的有效时间)**/
			if(1L==sendCouponType){
				/**用户领取**/
				/**优惠券数量(不随时间的改变而改变)**/
				sql = " SELECT issueNum as couponTotal FROM gf_coupon as cou WHERE 1=1  AND cou.couponId = '"+search.getField01().trim()+"'";
				couponTotal = qr.query(conn,sql, new LongResultSetHandler());
				/**已领数量**/
				sql = " SELECT count(*) FROM gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON cougif.couponGiftId = pass.couponGiftId "+
					" WHERE 1=1 AND pass.getState = 1 AND cougif.couponId = '"+search.getField01().trim()+"'";
				couponGetTotal = qr.query(conn,sql,new LongResultSetHandler());
			}else{
				/**系统发放**/
				/**先查找用户**/
				/**找出该优惠券下线以前的所有注册用户,该用户数量就是这个优惠券的发行数量**/
				if(couponVo.getSelectPass()==0){
					/**0:全部用户**/
					sql = " SELECT count(*) FROM passenger_info WHERE registerTime<= '"+couponVo.getDownLineTime()+"' AND cityCode = "+couponVo.getCityCode();
				}
				if(couponVo.getSelectPass()==1){
					/**1:新用户**/
					/**到优惠券下线之前都没有购买过票的用户都属于新用户**/
					sql = " SELECT count(*) FROM passenger_info AS passengerInfo  "+
					" WHERE 1=1 AND passengerInfo.passengerId NOT IN ( SELECT passengerId FROM lease_base_info AS leab WHERE leab.ispay = 1 ) AND passengerInfo.registerTime<= '"+couponVo.getDownLineTime()+"' AND passengerInfo.cityCode = "+couponVo.getCityCode();
				}
				if(couponVo.getSelectPass()==3){
					/**3:自定义用户**/
					sql = " SELECT count(*) FROM gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON cougif.couponGiftId = pass.couponGiftId "+
						" WHERE 1=1 AND cougif.couponId =  '"+search.getField01().trim()+"'";
				}
				couponTotal = qr.query(conn,sql, new LongResultSetHandler());
				if(couponVo.getSelectPass()!=3){
					/**0:全部用户   1:新用户**/
					couponTotal = couponTotal*couponVo.getLimitNum();
					couponGetTotal = couponTotal;
				}else{
					/**3:自定义用户**/
					couponGetTotal = couponTotal;
				}
			}
			sql = " SELECT gif.giftValue FROM gf_coupon AS cou LEFT JOIN gf_coupon_gift AS cougif ON cou.couponId = cougif.couponId "+
				" LEFT JOIN gf_gifts AS gif ON gif.giftPriId = cougif.giftPriId WHERE cou.couponId = '"+search.getField01().trim()+"'";
			/**优惠券的面值**/
			Long giftValue = qr.query(conn,sql,new LongResultSetHandler());
			
			/**已使用数量**/
			sql = " SELECT count(*) FROM gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON cougif.couponGiftId = pass.couponGiftId "+
				" WHERE 1=1 AND pass.useState = 1 "+cond;
			Long couponUser = qr.query(conn,sql,new LongResultSetHandler(), params);
			
			couponStatVo.setCouponTotal(couponTotal);
			couponStatVo.setCouponTotalMon(couponTotal*giftValue);
			couponStatVo.setCouponGetTotal(couponGetTotal);
			couponStatVo.setCouponGetMon(couponGetTotal*giftValue);
			couponStatVo.setCouponUser(couponUser);
			couponStatVo.setCouponUserMon(couponUser*giftValue);
			couponStatVo.setCouponGetUser(couponUser);
			couponStatVo.setCouponGetUserMon(couponUser*giftValue);
			couponStatVo.setCouponUnUser(couponTotal-couponUser);
			couponStatVo.setCouponUnUserMon((couponTotal-couponUser)*giftValue);
			couponStatVo.setCouponGetUnUser(couponGetTotal-couponUser);
			couponStatVo.setCouponGetUnUserMon((couponGetTotal-couponUser)*giftValue);
			/**有效时间**/
			couponStatVo.setEffectiveTime(couponVo.getEffectiveTime());
			couponStatVo.setExpirationTime(couponVo.getExpirationTime());
		}catch(Exception e){
			log.info("某一优惠券的数量统计异常");
			e.printStackTrace();
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return couponStatVo;
	}

	/**优惠券使用记录**/
	public Map<String, Object> queryCouponUserRecordList(Search search,
			int curPageIndex, int pageSize) {
		String tables = " gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON cougif.couponGiftId = pass.couponGiftId "+
			" LEFT JOIN gf_coupon AS cou ON cou.couponId = cougif.couponId LEFT JOIN gf_gifts AS gif ON gif.giftPriId = cougif.giftPriId "+
			" LEFT JOIN passenger_info AS passengerInfo ON pass.passengerId = passengerInfo.passengerId "+
			" LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = pass.orderId LEFT JOIN line_base_info AS linb ON linb.lineBaseId = leab.lineBaseId  ";
		super.finit(tables);
		String selectFileds = " passengerInfo.displayId,passengerInfo.telephone,gif.giftName as couponName,pass.userTime,"+
			" pass.useState,pass.theModule,pass.orderId,linb.lineName ";
	    List<Object> paramList = new ArrayList<Object>();
	    String cond = "";
	    if(null!=search){
	    	if(!StringUtils.isEmpty(search.getField01())){
	    		//优惠卷ID
	    		cond += " and cou.couponId = ? ";
	    		paramList.add(search.getField01());
	    	}
	    	if(!StringUtils.isEmpty(search.getField02())){
	    		//线路名称
	    		cond += " and linb.lineName like ? ";
	    		paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
		    }
	    	if(!StringUtils.isEmpty(search.getField03())){
	    		//用户账号
	    		cond += " and passengerInfo.telephone like ? ";
	    		paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
	    	}
	    	if(!StringUtils.isEmpty(search.getField04()) && !"2".equals(search.getField04())){
	    		//使用状态
	    		cond += " and pass.useState = ? ";
	    		paramList.add(search.getField04());
	    	}
	    	/***领取时间段**/
			if(!StringUtils.isEmpty(search.getField05())){
				cond+= " and left(pass.userTime,10) >=? ";
				paramList.add(search.getField05().trim());
			}
			if(!StringUtils.isEmpty(search.getField06())){
				cond+= " and left(pass.userTime,10) <=? ";
				paramList.add(search.getField06().trim());
			}
	    }
	    String sql = "";
	    sql = " SELECT " +selectFileds+" FROM "+tables+" WHERE 1=1 " +cond;
	    System.out.println(sql);
        Object[] params = paramList.toArray(new Object[]{});
		List<CouponUserRecordVo> list = super.tableDao.queryByPage(CouponUserRecordVo.class, sql.toString(), curPageIndex,pageSize, params);
		Map<String, Object> res = new HashMap<String,Object>();
		Page page = new Page(list,sql.toString(),curPageIndex,pageSize,params);
		res.put("page", page);
		res.put("list",list);
		return res;
	}

}
