package com.amwell.commons;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


/**
 * 数据源配置
 * 读取的工程下的context.xml中的数据源配置
 * 可以读取dbcp等数据源
 * @author zhangqiang
 *
 */
public class MyDataSource {
	static Logger log = Logger.getLogger(MyDataSource.class.getName());
	private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	private static DataSource dataSource;
	
	static {
		initDataSource();
	}
	
	/**
	 * 初始化数据源(容器自带连接池模式)
	 */
	private final static void initDataSource(){
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context)initContext.lookup("java:/comp/env");
			dataSource = (DataSource)envContext.lookup("jdbc/customline");
		} catch (NamingException e) {
			log.error("初始化数据源异常");
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * 获得连接
	 * @throws Exception 
	 * @throws NamingException
	 * @throws SQLException
	 */
	public final static Connection getConnect(){
		Connection con = conns.get();
		try {
			if (con == null || con.isClosed()) {
				con = dataSource.getConnection();
				conns.set(con);
			}
		} catch (SQLException e) {
			log.error("获得连接异常");
			e.printStackTrace();
		} 
		return con;
	}
	
	/**
	 * 关闭数据库连接
	 */
	public final static void closeConnect(){
		Connection con = conns.get();
		try {
			if (con != null && !con.isClosed()) {
				con.setAutoCommit(true);
				con.close();
			}
		} catch (SQLException e) {
			log.error("关闭数据库连接异常");
			e.printStackTrace();
		}
	}
}
