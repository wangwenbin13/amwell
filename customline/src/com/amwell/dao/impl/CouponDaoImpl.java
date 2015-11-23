package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;
import com.amwell.dao.ICouponDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.gf.CouponVo;

@Repository("couponDao")
public class CouponDaoImpl extends DaoSupport implements ICouponDao{

	private static final Logger log = Logger.getLogger(CouponDaoImpl.class);
	
	public int addCoupon(CouponVo vo) {
		Connection conn = MyDataSource.getConnect();
		if(null==conn){
			throw new NullPointerException("conn is null.");
		}
		if(null==vo){
			throw new NullPointerException("CouponVo is null.");
		}
		int flag = -1;
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
            flag = this.insert(conn, qr, vo);
            conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				flag =-9999;
			} catch (SQLException e1) {
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return flag;
	}

	
	private int insert(Connection conn,QueryRunner qr,CouponVo vo) throws Exception{
		int flag = -1;
		String couponId = StringUtil.generateSequenceNo();
		if(ArrayUtils.isNotEmpty(vo.getGiftIds())){
			//插入优惠券
			String insertCouponSql = "insert into gf_coupon(couponId,link,couponName,couponType,sendCouponType,selectPass,upLineTime,downLineTime,effectiveTime,expirationTime,issueNum,limitNum,provinceCode,cityCode,cityName,createBy,createOn) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			flag = qr.update(conn, insertCouponSql, new Object[]{couponId,vo.getLink(),vo.getCouponName(),vo.getCouponType(),vo.getSendCouponType(),vo.getSelectPass(),vo.getUpLineTime(),vo.getDownLineTime(),vo.getEffectiveTime(),vo.getExpirationTime(),vo.getIssueNum(),vo.getLimitNum(),vo.getProvinceCode(),vo.getCityCode(),vo.getCityName(),vo.getCreateBy(),vo.getCreateOn()});
			List<String> couponGiftIdList = new ArrayList<String>();
			if(flag>0&&ArrayUtils.isNotEmpty(vo.getGiftIds())){
				//插入优惠券礼品关联表
				int count = 0;
				String giftPriId=null;
				String giftSql ="select giftPriId from gf_gifts where giftId=?";
				String insertCouponGiftSql = "insert into gf_coupon_gift(couponGiftId,couponId,giftPriId) values(?,?,?)";
				for(String giftId : vo.getGiftIds()){                  
					giftPriId = qr.query(conn, giftSql, new ResultSetHandler<String>(){
						public String handle(ResultSet rs)
								throws SQLException {
							if(rs.next()){
								return rs.getString(1);
							}
							return null;
						}}, new Object[]{giftId});
					if(StringUtils.hasText(giftPriId)){
						String couponGiftId = StringUtil.generateSequenceNo();
						count+=qr.update(conn, insertCouponGiftSql, new Object[]{couponGiftId,couponId,giftPriId});
						couponGiftIdList.add(couponGiftId);
						//更新礼品状态
						String updateGiftSql = "update gf_gifts set state=1 where giftPriId=?";
						qr.update(conn, updateGiftSql, new Object[]{giftPriId});
					}
					
				}
				flag = count==vo.getGiftIds().length?1:-1;
			}
			int getState =vo.getSendCouponType()==2?1:0;
			if(flag>0&&3==vo.getSelectPass()&&StringUtils.hasText(vo.getTeles())){
				//用户类型为自定义用户时，插入优惠券乘客关联表
				String[] telephones = vo.getTeles().split(";");
				String insertCouponTelSql = "insert into gf_coupon_passenger(counponTeleId,passengerId,telephone,couponGiftId,getState,getTime) values(?,?,?,?,?,?)";
				String queryPassengerSql = "select passengerId from passenger_info where telephone=?";
//				queryPassengerSql+=" and cityCode=?";
//				int cityCode = vo.getCityCode();
				String passengerId = null;
				int count = 0;
				List<Object> queryPassengerParams = null;
				for(String telephone : telephones){
					queryPassengerParams = new ArrayList<Object>();
					queryPassengerParams.add(telephone);
					passengerId = qr.query(conn, queryPassengerSql, new ResultSetHandler<String>(){
						public String handle(ResultSet rs)
								throws SQLException {
							if(rs.next()){
								return rs.getString(1);
							}
							return null;
						}}, queryPassengerParams.toArray());
					if(StringUtils.hasText(passengerId)&&false==CollectionUtils.isEmpty(couponGiftIdList)){
						for(String couponGiftId : couponGiftIdList){
							for(int i=0;i<vo.getLimitNum();i++){
								//按限领份数循环插入数据
								count+=qr.update(conn, insertCouponTelSql, new Object[]{StringUtil.generateSequenceNo(),passengerId,telephone,couponGiftId,getState,MyDate.getMyDateLong()});
							}
						}
						
					}else{
						throw new Exception("passengerId or couponGiftIdList is null.");
					}
				}
				flag=count;
			}
		}
		return flag;
	}
	
	
	public Map<String, Object> getList(Search search, int currentPageIndex,int pageSize) {
        super.finit("gf_coupon a INNER JOIN sys_admin b INNER JOIN gf_coupon_gift c on a.couponId=c.couponId INNER JOIN gf_gifts d on c.giftPriId=d.giftPriId");
		StringBuffer sql = new StringBuffer("select a.*,b.userName,d.giftType  from gf_coupon a INNER JOIN sys_admin b INNER JOIN gf_coupon_gift c on a.couponId=c.couponId INNER JOIN gf_gifts d on c.giftPriId=d.giftPriId where 1=1 and a.createBy=b.userId");
	    List<Object> paramList = new ArrayList<Object>();
	    if(null!=search){
	    	if(StringUtils.hasText(search.getField01())){
	    		//优惠券名称
	    		sql.append(" and couponName like ?");
	    		paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()));
		    }
	    	if(StringUtils.hasText(search.getField02())){
	    		//有效期开始时间
	    		sql.append(" and effectiveTime>?");
	    		paramList.add(search.getField02());
	    	}
	    	if(StringUtils.hasText(search.getField03())){
	    		//有效期结束时间
	    		sql.append(" and expirationTime<?");
	    		paramList.add(search.getField03());
	    	}
	    }
	    sql.append(" order by a.createOn desc");
        Object[] params = paramList.toArray(new Object[]{});
		List<CouponVo> list = super.tableDao.queryByPage(CouponVo.class, sql.toString(), currentPageIndex,pageSize, params);
		Map<String, Object> res = new HashMap<String,Object>();
		Page page = new Page(list,sql.toString(),currentPageIndex,pageSize,params);
		res.put("page", page);
		res.put("list",list);
		return res;
	}

	public int closeCoupon(String couponId) {
		
		Connection conn = MyDataSource.getConnect();
		if(null==conn){
			throw new NullPointerException("conn is null.");
		}
		if(false==StringUtils.hasText(couponId)){
			throw new NullPointerException("couponId is null.");
		}
		int flag = -1;
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			//更新优惠券下线时间为系统当前时间，关闭之后，用户无法领取，已领取的可继续使用
			String now = MyDate.getMyDateLong();
			now = now.substring(0, now.lastIndexOf(":"));
			String updateCouponSql ="update gf_coupon set downLineTime=? where couponId=?";
			flag = qr.update(conn, updateCouponSql, new Object[]{now,couponId});
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				flag =-9999;
			} catch (SQLException e1) {
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return flag;
	}

	public boolean checkCouponGetState(String couponId) {
		super.finit("gf_coupon_gift a INNER JOIN gf_coupon_passenger b on a.couponGiftId=b.couponGiftId");
        String sql ="select b.counponTeleId from gf_coupon_gift a INNER JOIN gf_coupon_passenger b on a.couponGiftId=b.couponGiftId where b.getState=1 and a.couponId=?";
        int count = super.tableDao.queryCount(sql, new Object[]{couponId});
        return count>0?true:false;
	}

	public CouponVo getCoupon(String couponId) {
		Connection conn = MyDataSource.getConnect();
		if(null==conn){
			throw new NullPointerException("conn is null.");
		}
		if(false==StringUtils.hasText(couponId)){
			throw new NullPointerException("couponId is null.");
		}
		CouponVo result = null;
		try {
			QueryRunner qr = new QueryRunner();
			String sql = "select a.*,c.giftPriId,c.giftId,c.giftName,c.giftType,c.giftValue from gf_coupon a inner join gf_coupon_gift b on a.couponId=b.couponId inner join gf_gifts c on c.giftPriId=b.giftPriId where a.couponId=?";
			List<CouponVo> cList = qr.query(conn, sql, new BeanListHandler<CouponVo>(CouponVo.class), new Object[]{couponId});
			if(false==CollectionUtils.isEmpty(cList)){
			    result = cList.get(0);	
			    if(null!=result){
			    	  //查询自定义用户，条件为用户领取，且用户类型为自定义用户
				    if(result.getSendCouponType()==1&&result.getSelectPass()==3){
				    	sql = "select DISTINCT b.telephone from  gf_coupon_gift a INNER JOIN gf_coupon_passenger b on a.couponGiftId=b.couponGiftId  where b.getState=0 and  a.couponId=? ";
				    	List<CouponVo> telList = qr.query(conn, sql, new BeanListHandler<CouponVo>(CouponVo.class), new Object[]{couponId});
				    	if(false==CollectionUtils.isEmpty(telList)){
				    		StringBuffer tels = new StringBuffer();
				    		for(CouponVo c : telList){
				    			if(null!=c){
				    				tels.append(c.getTelephone()).append(";");
				    			}
				    			
				    		}
				    		if(tels.length()>0){
				    			result.setTeles(tels.substring(0,tels.length()-1));
				    		}
				    	}
				    }
			    }
			  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	   return result;
	}

	public int updateCoupon(CouponVo vo) {
		Connection conn = MyDataSource.getConnect();
		if(null==conn){
			throw new NullPointerException("conn is null.");
		}
		if(null==vo){
			throw new NullPointerException("CouponVo is null.");
		}
		if(false==StringUtils.hasText(vo.getCouponId())){
			throw new NullPointerException("couponId is null.");
		}
		int flag = -1;
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			Object[] couponIdPara = new Object[]{vo.getCouponId()};
			
			//判断是优惠券礼品是否有人领取，如果有人领取，则直接返回，若无人领取，则直接删除优惠券所有信息，并重新插入相关数据
			String checkGetStateSql ="select count(1) from gf_coupon_gift a INNER JOIN gf_coupon_passenger b on a.couponGiftId=b.couponGiftId where b.getState=1 and a.couponId=?";
			int getCount = qr.query(conn, checkGetStateSql, new ResultSetHandler<Integer>(){
				public Integer handle(ResultSet rs) throws SQLException {
					if(rs.next()){
						return rs.getInt(1);
					}
					return 0;
			}}, couponIdPara);
			if(getCount>0){
				log.error("优惠券["+vo.getCouponId()+"]已有用户领取，不可修改.");
				throw new IllegalStateException("优惠券["+vo.getCouponId()+"]已有用户领取，不可修改.");
			}
			
			log.debug("开始删除优惠券["+vo.getCouponId()+"]的相关数据...");
			
			String delCouponPassengerSql="delete from gf_coupon_passenger where getState=0 and couponGiftId in (select couponGiftId from gf_coupon_gift where couponId=?)";
			qr.update(conn, delCouponPassengerSql, couponIdPara);
			log.debug("删除优惠券乘客未领取数据成功.");
			
			String delCouponGiftSql ="delete from gf_coupon_gift where couponId=?";
			qr.update(conn, delCouponGiftSql, couponIdPara);
			log.debug("删除优惠券礼品关联数据成功.");
			
			String delCouponSql ="delete from gf_coupon where couponId=?";
			qr.update(conn, delCouponSql, couponIdPara);
			log.debug("删除优惠券数据成功.");
			
			log.debug("删除优惠券["+vo.getCouponId()+"]的所有相关数据成功");
			
			flag = this.insert(conn, qr, vo);
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				flag =-9999;
			} catch (SQLException e1) {
			}
		}finally{
			if(null!=conn){
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return flag;
	}


	public long getIssueNum(CouponVo vo) {
		long result = 0;
		Connection conn = MyDataSource.getConnect();
		if(null==conn){
			throw new NullPointerException("conn is null.");
		}
		if(false==StringUtils.hasText(vo.getCouponId())){
			throw new NullPointerException("couponId is null.");
		}
		int sendCouponType = vo.getSendCouponType();
		int selectPass = vo.getSelectPass();
		QueryRunner qr = new QueryRunner();
		String sql =null;
		Object qObj = null;
		try {
			if(sendCouponType==1){//用户领取
				result = vo.getIssueNum();
			}
			else if(sendCouponType==2){//系统发放
            	if(selectPass==0){
            		//全部用户,如果优惠券含有城市信息
            		sql ="select count(1) from passenger_info a where LEFT(a.registerTime,16)<?";
            		List<Object> paramList = new ArrayList<Object>();
            		paramList.add(vo.getDownLineTime());
            		if(vo.getCityCode()>0){
            			sql +=" and a.cityCode=?";
            			paramList.add(vo.getCityCode());
            		}
            		qObj = qr.query(conn, sql, new ResultSetHandler<Long>(){
						public Long handle(ResultSet rs)
								throws SQLException {
							if(rs.next()){
								return rs.getLong(1);
							}
							return null;
						}}, paramList.toArray());
            		if(qObj instanceof Integer){
            			result = (Integer)qObj*vo.getLimitNum();
            		}else if(qObj instanceof Long){
            			result = (Long)qObj*vo.getLimitNum();
            		}else {
            			System.out.println("qObj cast exception:"+qObj);
            		}
            	}else if(selectPass==1){
            		//新用户
            		sql = "select count(1) from passenger_info a where LEFT(a.registerTime,16)<? and NOT EXISTS(select 1 from lease_base_info where ispay=1 and passengerId=a.passengerId)";
            		List<Object> paramList = new ArrayList<Object>();
            		paramList.add(vo.getDownLineTime());
            		if(vo.getCityCode()>0){
            			sql +=" and a.cityCode=?";
            			paramList.add(vo.getCityCode());
            		}
            		qObj = qr.query(conn, sql, new ResultSetHandler<Long>(){
						public Long handle(ResultSet rs)
								throws SQLException {
							if(rs.next()){
								return rs.getLong(1);
							}
							return null;
						}}, paramList.toArray());
            		if(qObj instanceof Integer){
            			result = (Integer)qObj*vo.getLimitNum();
            		}else if(qObj instanceof Long){
            			result = (Long)qObj*vo.getLimitNum();
            		}else {
            			System.out.println("qObj cast exception:"+qObj);
            		}
            	}else if(selectPass==3){
            		//自定义用户
            		sql = "select count(1) from gf_coupon_passenger a  INNER JOIN gf_coupon_gift b on a.couponGiftId=b.couponGiftId where b.couponId=?";
            		qObj = qr.query(conn, sql, new ResultSetHandler<Long>(){
						public Long handle(ResultSet rs)
								throws SQLException {
							if(rs.next()){
								return rs.getLong(1);
							}
							return null;
						}}, new Object[]{vo.getCouponId()});
            		if(qObj instanceof Integer){
            			result = (Integer)qObj;
            		}else if(qObj instanceof Long){
            			result = (Long)qObj;
            		}else {
            			System.out.println("qObj cast exception:"+qObj);
            		}
            	}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}

	
}
