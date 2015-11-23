package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.dao.ISysDiscountDao;
import com.amwell.vo.SysDiscountVo;

@Repository("sysDiscountDao")
public class SysDiscountDaoImpl extends DaoSupport implements ISysDiscountDao {

	public SysDiscountVo getSysDiscountDetail() {
		super.finit("sys_discount");
		String sql = "select * from sys_discount limit 1";
		return super.tableDao.queryBean(SysDiscountVo.class, sql);
	}

	public int updateSysDiscount(SysDiscountVo vo) {
		Connection conn = MyDataSource.getConnect();
		int flag = -1;
		if (null != conn && null != vo) {
			try {
				conn.setAutoCommit(false);
				QueryRunner qr = new QueryRunner();
				String sql = "update sys_discount set disprice=?,distimes=?,iswork=? where id=?";
				qr.update(conn, sql, new Object[]{vo.getDisprice(),vo.getDistimes(),vo.getIswork(),vo.getId()});
				flag=1;
				conn.commit();
			} catch (Exception e) {
				flag=-9999;
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}

		}
		return flag;
	}

}
