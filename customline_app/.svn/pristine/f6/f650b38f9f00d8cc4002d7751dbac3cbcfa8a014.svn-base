package com.pig84.ab.dao;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pig84.ab.utils.Conn;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.SqlBuilder;

/**
 * 数据库操作基础类
 * 
 * @author zhangqiang
 * 
 */
public class BaseDao {

	private static Logger logger = LoggerFactory.getLogger(BaseDao.class);
	private static final Class<?>[] PRIMITIVES = new Class<?>[] { Long.class, Integer.class, String.class,
			java.util.Date.class, java.sql.Date.class, java.sql.Timestamp.class };

	private static final int DEFAULT_PAGESIZE = 20;

	private final static QueryRunner queryRunner = new QueryRunner(true);
	
	@SuppressWarnings("rawtypes")
	private static final ColumnListHandler columnListHandler = new ColumnListHandler() {
		@Override
		protected Object handleRow(ResultSet rs) throws SQLException {
			Object obj = super.handleRow(rs);
			return (obj instanceof BigInteger) ? ((BigInteger) obj).longValue() : obj;
		}
	};

	@SuppressWarnings("rawtypes")
	private static final ScalarHandler scaleHandler = new ScalarHandler() {
		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Object obj = super.handle(rs);
			return (obj instanceof BigInteger) ? ((BigInteger) obj).longValue() : obj;
		}
	};

	public <T> T queryBean(Class<T> beanClass, String sql, Object... params) {
		Connection conn = Conn.get();
		try {
			@SuppressWarnings("unchecked")
			ResultSetHandler<T> handler = isPrimitive(beanClass) ? scaleHandler : new BeanHandler<T>(beanClass);
			T obj = (T) queryRunner.query(conn, sql, handler, params);
			if (logger.isDebugEnabled()) {
				String logSql = String.format(sql.replaceAll("\\?", "%s"), params);
				logger.debug("Execute SQL: {}", logSql);
			}
			return obj;
		} catch (SQLException e) {
			logger.error("SQL execute failed.", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(Class<T> beanClass, String sql, Object... params) {
		Connection conn = Conn.get();
		try {
			ResultSetHandler<List<T>> handler = isPrimitive(beanClass) ? columnListHandler
					: new BeanListHandler<T>(beanClass);
			List<T> list = queryRunner.query(conn, sql, handler, params);
			if (logger.isDebugEnabled()) {
				String logSql = String.format(sql.replaceAll("\\?", "%s"), params);
				logger.debug("Execute SQL: {}", logSql);
			}
			return list.isEmpty() ? null : list;
		} catch (SQLException e) {
			logger.error("SQL execute failed.", e);
			return null;
		}
	}

	public <T> List<T> queryByPage(Class<T> beanClass, String sql, int page, int pageSize, Object... params) {
		if (pageSize <= 0) {
			pageSize = DEFAULT_PAGESIZE;
		}
		int start = page * pageSize;
		int limit = pageSize;
		return queryList(beanClass, sql + " LIMIT ?,?", ArrayUtils.addAll(params, new Object[] { start, limit }));
	}

	public int queryCount(String sql, Object... params) {
		Connection conn = Conn.get();
		String query = "SELECT COUNT(*) AS TOTAL FROM (" + sql + ") t";
		try {
			Map<String, Object> map = queryRunner.query(conn, query, new MapHandler(), params);
			return map.isEmpty() ? 0 : NumberUtils.toInt(map.values().iterator().next().toString(), 0);
		} catch (SQLException e) {
			logger.error("Query count failed.", e);
			return 0;
		}
	}

	public int executeSQL(String sql, Object... params) {
		Connection conn = Conn.get();
		try {
			if (logger.isDebugEnabled()) {
				String logSql = String.format(sql.replaceAll("\\?", "%s"), params);
				logger.debug("Execute SQL: {}", logSql);
			}
			return queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			logger.error("SQL execute failed.", e);
			return -1;
		}
	}

	public <T> int updateData(Object bean, String tableName,String pri_name) {
		String getMethodName;
		String setMethodName;
		SqlBuilder sb = new SqlBuilder();
		if (StringUtils.isNotEmpty(pri_name)) {
			String primaryKey = pri_name.substring(0, 1).toUpperCase() + pri_name.substring(1);
			getMethodName = "get" + primaryKey;
			setMethodName = "set" + primaryKey;
		} else {
			getMethodName = "getRec_id";
			setMethodName = "setRec_id";
		}
		try {
			Method method = bean.getClass().getMethod(getMethodName, new Class[] {});
			Object value = method.invoke(bean, new Object[] {});
			String sql;
			if (value == null || "".equals(value)) {
				String idvalue = IdGenerator.seq();
				Method method2 = bean.getClass().getMethod(setMethodName, new Class[] { String.class }); // 得到set方法
				method2.invoke(bean, new Object[] { idvalue }); // 调用setXXXX方法
				sql = sb.getInsertSQL(bean, tableName);
			} else {
				sql = sb.getUpdateSQL(bean, tableName, pri_name);
			}
			Object[] params = sb.getParams();
			return executeSQL(sql, params);
		} catch (Exception e) {
			logger.error("Update data failed.", e);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T queryBeanById(Class<T> beanClass, String id,String tableName, String primaryKey) {
		Connection conn = Conn.get();
		try {
			String sql = String.format("SELECT * FROM %s WHERE %s = ?", tableName, primaryKey);
			ResultSetHandler<T> handler = isPrimitive(beanClass) ? scaleHandler : new BeanHandler<T>(beanClass);
			return (T) queryRunner.query(conn, sql, handler, id);
		} catch (SQLException e) {
			logger.error("Query bean by ID failed.", e);
			return null;
		}
	}

	private static boolean isPrimitive(Class<?> cls) {
		return cls.isPrimitive() || ArrayUtils.contains(PRIMITIVES, cls);
	}

}
