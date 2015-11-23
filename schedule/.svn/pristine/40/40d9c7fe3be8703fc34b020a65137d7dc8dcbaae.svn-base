package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import com.amwell.commons.MyDataSource;
import com.amwell.dao.IBusinessDao;
import com.amwell.vo.MgrBusinessEntity;

@Repository("businessDao")
public class BusinessDaoImpl implements IBusinessDao {

	public MgrBusinessEntity getBusinessById(String id) throws SQLException {
		Connection conn = MyDataSource.getConnect();
		String sql = "select * from mgr_business where businessId=?";
		Object[] params = new Object[1];
		params[0] = id;
		QueryRunner queryRunner = new QueryRunner();
		return queryRunner.query(conn, sql, new BeanHandler<MgrBusinessEntity>(MgrBusinessEntity.class), params);
	}

}
