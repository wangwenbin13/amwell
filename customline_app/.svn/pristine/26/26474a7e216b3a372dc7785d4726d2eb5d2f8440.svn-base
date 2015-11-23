package com.pig84.ab.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.UserIdMapDao;
import com.pig84.ab.utils.Conn;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.vo.UserIdMap;

@Repository
public class UserIdMapDaoImpl implements UserIdMapDao {

	private static final Logger logger = Logger.getLogger(UserIdMapDaoImpl.class);

	@Override
	public void add(UserIdMap userIdMap) {
		Connection conn = null;
		Object[] params = null;
		String sql = null;
		QueryRunner queryRunner = new QueryRunner();
		try {
			conn = Conn.get();
			sql = "select * from user_id_map where passengerId=? and clientId=? and channelId=?";
			params = new Object[3];
			params[0] = userIdMap.getPassengerId();
			params[1] = userIdMap.getClientId();
			params[2] = userIdMap.getChannelId();
			UserIdMap oldMap = queryRunner.query(conn, sql, new BeanHandler<UserIdMap>(UserIdMap.class), params);
			if (oldMap == null) {
				sql = "insert into user_id_map(localId,passengerId,clientId,channelId) values(?,?,?,?)";
				params = new Object[4];
				params[0] = IdGenerator.seq();
				params[1] = userIdMap.getPassengerId();
				params[2] = userIdMap.getClientId();
				params[3] = userIdMap.getChannelId();
				queryRunner.update(conn, sql, params);
			}
		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
		} 
	}

	@Override
	public UserIdMap getByClientIdAndChannelId(String clientId, String channelId) {
		Connection conn = null;
		Object[] params = null;
		String sql = null;
		QueryRunner queryRunner = new QueryRunner();
		UserIdMap userIdMap = null;
		try {
			conn = Conn.get();
			sql = "select * from user_id_map where clientId=? and channelId=?";
			params = new Object[2];
			params[0] = clientId;
			params[1] = channelId;
			userIdMap = queryRunner.query(conn, sql, new BeanHandler<UserIdMap>(UserIdMap.class), params);
		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
		} 
		return userIdMap;
	}

}
