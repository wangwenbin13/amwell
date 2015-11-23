package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.dao.IUtilsDao;
import com.amwell.entity.Search;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_4;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 获取特殊数据的工具
 */
@Repository(value = "utilsDao")
public class UtilsDaoImpl extends DaoSupport implements IUtilsDao {

	/**
	 * 获取购买过指定线路的乘客电话
	 */
	public String queryPassengerTelHasBuyLine() {
		super.finit("passenger_info");
		sql = " SELECT telephone FROM " 
				+ "(SELECT passengerId FROM lease_base_info WHERE ispay =1 and lineBaseId IN " 
				+	"(SELECT lineBaseId from line_base_info WHERE lineName IN ('LS201','LS101')) GROUP BY passengerId) AS b "
				+	"LEFT JOIN  passenger_info AS pass ON pass.passengerId = b.passengerId  ";
		List<String> teles = tableDao.queryList(String.class, sql);
		String telephones = "";
		if(null!=teles && teles.size()>0){
			for(String tel : teles){
				telephones = telephones+","+tel;
			}
		}
		return telephones;
	}

	/**将所有的乘客ID放到im_user_info表里面**/
	public void fromPassengerToImUserInfo() {
		super.finit("passenger_info");
		sql = " SELECT passengerId FROM passenger_info ";
		List<String> passengerIds = tableDao.queryList(String.class, sql);
		super.finit("im_user_info");
		sql = " alter table im_user_info AUTO_INCREMENT = 1000 ";
		tableDao.executeSQL(sql);
		Connection conn=null;
		try {
			conn =MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			System.out.println("====开始====");
			System.out.println("====执行中```````");
			if(null!=passengerIds && passengerIds.size()>0){
				for(String passengerId : passengerIds){
					sql = " INSERT INTO im_user_info(passengerId) VALUES(?) ";
					qr.update(conn, sql, new Object[]{passengerId});
				}
			}
			System.out.println("===结束==");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=conn && conn.isClosed()==false){
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**读取财务数据并保存到数据库**/
	public void fromCaiWuExcelToDb(List<AppVo_2> appweixin,
			List<AppVo_2> weixin, List<AppVo_2> yinlian, List<AppVo_2> zhifubao) {
		super.finit("stat_temp");
		Connection conn=null;
		try {
			conn =MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			sql = " INSERT INTO stat_temp(orderNo,price,type) VALUES(?,?,?) ";
			//app微信
			if(null!=appweixin && appweixin.size()>0){
				for(AppVo_2 vo : appweixin){
					qr.update(conn, sql, new Object[]{vo.getA1(),vo.getA2(),0});
				}
			}
			//微信
			if(null!=weixin && weixin.size()>0){
				for(AppVo_2 vo : weixin){
					qr.update(conn, sql, new Object[]{vo.getA1(),vo.getA2(),1});
				}
			}
			//银联
			if(null!=yinlian && yinlian.size()>0){
				for(AppVo_2 vo : yinlian){
					qr.update(conn, sql, new Object[]{vo.getA1(),vo.getA2(),2});
				}
			}
			//支付宝
			if(null!=zhifubao && zhifubao.size()>0){
				for(AppVo_2 vo : zhifubao){
					qr.update(conn, sql, new Object[]{vo.getA1(),vo.getA2(),3});
				}
			}
			System.out.println("===结束==");
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				if(null!=conn && conn.isClosed()==false){
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**查处存在第三方,但是不存在平台的收入数据**/
	public List<AppVo_4> queryNotInLocalLeases(Search search) {
		List<AppVo_4> lists = null;
		super.finit("stat_temp");
		if(null!=search){
			sql = " SELECT orderNo AS a1,price AS a2 FROM stat_temp WHERE orderNo NOT IN ( SELECT leaseOrderNo FROM lease_base_info WHERE LEFT(leaseOrderTime,10)>=? AND LEFT(leaseOrderTime,10)<=? AND ispay = 1 ) AND type = ? ";
			lists = tableDao.queryList(AppVo_4.class, sql, new Object[]{search.getField02(),search.getField03(),search.getField01()});
		}
		return lists;
	}
	
	/****
	 * 删除对应的数据
	 */
	public void deleteDate() {
		super.finit("stat_temp");
		tableDao.executeSQL("TRUNCATE table stat_temp ");
	}

}
