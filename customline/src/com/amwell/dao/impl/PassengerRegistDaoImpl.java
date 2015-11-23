package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.DESCryptUtil;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.Sha1Utils;
import com.amwell.commons.StringUtil;
import com.amwell.dao.IPassengerRegistDao;
import com.amwell.vo.PassengerVo;
import com.amwell.vo.SysDiscountVo;

@Repository("passengerRegistDao")
public class PassengerRegistDaoImpl extends DaoSupport implements IPassengerRegistDao {

	private static final Logger log = Logger.getLogger(PassengerRegistDaoImpl.class);
	
	/* 
	 * 手动（批量）注册用户
	 */
	public int registPassenger(List<PassengerVo> pList) {
		int result = 0;
		Connection  conn = MyDataSource.getConnect();
		if(conn==null){
			throw new IllegalStateException("connection is null!");
		}
		if(CollectionUtils.isEmpty(pList)){
			throw new IllegalArgumentException("pList is null.");
		}
		try {
			conn.setAutoCommit(false);
			QueryRunner qr=new QueryRunner();
			for(PassengerVo bean : pList){
				//查询手机号是否注册
				String querySql="select count(1) from passenger_info where telephone=?";
				Long count = qr.query(conn, querySql, new ResultSetHandler<Long>(){
					public Long handle(ResultSet rs) throws SQLException {
						if(rs.next()){
							return rs.getLong(1);
						}
						return null;
			    }}, new Object[]{bean.getTelephone()});
				if(null!=count&&count instanceof Long){
					Long countLong = (Long) count;
					if(countLong==0){
						//查询最大的displayId
						String displayId = null;
						String max_displayIdSql = "SELECT MAX(CAST(displayid AS SIGNED INTEGER)) as displayId FROM passenger_info";
						displayId = qr.query(conn, max_displayIdSql, new ResultSetHandler<String>(){
							public String handle(ResultSet rs) throws SQLException {
								if(rs.next()){
									return rs.getString(1);
								}
								return null;
							}}, new Object[]{});
						if(false==StringUtils.hasText(displayId)){
							displayId = "10000";
						}else{
							displayId=String.valueOf(Integer.valueOf(displayId)+1);
						}
						//二维码
						String twoCodeValue = StringUtil.generateSequenceNo();
						//乘客ID
						String passengerId = StringUtil.generateSequenceNo();
						//生成密码
						String password = Sha1Utils.encrypt(bean.getTelephone()+"&"+bean.getTelephone().substring(5, 11));
						String comments =DESCryptUtil.des(bean.getTelephone().substring(5, 11));
						String cityName = null;
						if(StringUtils.hasText(bean.getProvinceCode())&&StringUtils.hasText(bean.getCityCode())){
							//查询城市名称
							String queryCitySql="select areaName from sys_area where fdCode=? and arearCode=?";
							String obj = qr.query(conn, queryCitySql, new ResultSetHandler<String>() {
								public String handle(ResultSet rs)
										throws SQLException {
									if(rs.next()){
										return rs.getString(1);
									}
									return null;
								}
							}, new Object[]{bean.getProvinceCode(),bean.getCityCode()});
							if(null!=obj){
							   cityName = obj;
							}
						}
						//注册乘客
						String insertSql="INSERT INTO passenger_info (passengerId,displayId,nickName,sex,accountStatus,telephone,blackFlag,passWord,registerTime,twoCodeValue,bindStatus,comments,provinceCode,cityCode,cityName)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						int flag =qr.update(conn, insertSql, new Object[]{passengerId,displayId,bean.getNickName(),bean.getSex(),0,bean.getTelephone(),0,password,MyDate.getMyDateLong(),twoCodeValue,"0",comments,bean.getProvinceCode(),bean.getCityCode(),cityName});
                        //为乘客增加活动信息
						//查询活动信息
						String disSql = "SELECT * FROM sys_discount WHERE iswork = '1' LIMIT 1";
						List<SysDiscountVo> disCountList = qr.query(conn, disSql, new BeanListHandler<SysDiscountVo>(SysDiscountVo.class), new Object[]{});
						SysDiscountVo discountVo = null;
						if(false==CollectionUtils.isEmpty(disCountList)){
							discountVo=disCountList.get(0);
						}
						if(flag>0&&discountVo!=null){
							for (int i = 0; i < Integer.valueOf(discountVo.getDistimes()); i++) {
								String insertDisSql ="INSERT INTO passenger_discount_info(id,passengerid,discountid,createOn,disstatus,remark,disprice) VALUES(?,?,?,?,?,?,?)";
								qr.update(conn, insertDisSql, new Object[]{StringUtil.generateSequenceNo(),passengerId,discountVo.getId(),MyDate.getMyDateLong(),0,discountVo.getRemark(),discountVo.getDisprice()});
							}
						}
						result+=flag;
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		}finally{
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
		return result;
	}

	/* 
	 * 检测电话号码是否有重复
	 */
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public List<PassengerVo> checkTelephone(List<PassengerVo> pList) {
		List<PassengerVo> result = new ArrayList<PassengerVo>();
		Connection  conn = MyDataSource.getConnect();
		if(conn==null){
			throw new IllegalStateException("connection is null!");
		}
		if(CollectionUtils.isEmpty(pList)){
			throw new IllegalArgumentException("pList is null.");
		}
		try {
			QueryRunner qr = new QueryRunner();
			String sql ="select count(1) from passenger_info where telephone=?";
			for(PassengerVo p :pList){
				Object count = qr.query(conn, sql, new Object[]{p.getTelephone()}, new ResultSetHandler(){
					public Object handle(ResultSet rs) throws SQLException {
						if(rs.next()){
							return rs.getObject(1);
						}
						return null;
			    }});
				if(null!=count&&count instanceof Long){
					Long countLong = (Long) count;
					if(countLong>0){
						result.add(p);
					}
				}else{
					log.error("count is null or not Long instance");
				}
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		finally{
			try {
				conn.close();
			} catch (Exception e2) {
				log.error(e2.getLocalizedMessage());
			}
		}
		return result;
	}

	/* 
	 *查询是电话号码 是否已存在
	 */
	public int queryPhone(String phone) {
		super.finit("passenger_info");
		String sql="select 1 from passenger_info where telephone = ?";
		int count = super.tableDao.queryCount(sql, new Object[]{phone});
		return count;
	}

}
