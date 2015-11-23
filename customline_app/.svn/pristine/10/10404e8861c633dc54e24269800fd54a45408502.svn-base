package com.pig84.ab.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection.
 * 
 * @author zhangqiang
 *
 */
public class Conn {

	private static final Logger logger = LoggerFactory.getLogger(Conn.class);

	private static final ThreadLocal<Connection> HOLDER = new ThreadLocal<Connection>();

	private static final DataSource DS = createDataSource();
	
	/**
	 * Get connection.
	 * @return Retrieved connection, <code>null</code> if failed.
	 */
	public final static Connection get(){
		Connection conn = HOLDER.get();
		try {
			if (conn == null || conn.isClosed()) {
				conn = DS.getConnection();
				HOLDER.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			logger.error("Get connection failed.", e);
			return null;
		} 
	}
	
	/**
	 * Close connection.
	 */
	public final static void close(){
		Connection conn = HOLDER.get();
		try {
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Close connection failed.", e);
		}
	}

	private final static DataSource createDataSource() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			return (DataSource) envContext.lookup("jdbc/customline");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
