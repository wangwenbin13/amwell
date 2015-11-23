package com.amwell.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.amwell.commons.MyDataSource;
import com.amwell.commons.StringUtil;
import com.amwell.dao.ISysDao;

@Repository("sysDao")
@SuppressWarnings("unchecked")
public abstract class SysDaoImpl<T> implements ISysDao<T> {

	private Class<T> clazz;

	public SysDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) type.getActualTypeArguments()[0];

	}

	/**
	 * 根据Sql和参数查询
	 * 
	 * @param sql
	 * @param params
	 * @return 如果查到结果就返回，否则返回空集合
	 */

	public List<T> queryList(String sql, Object... params) {
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		try {
			Logger.getLogger(SysDaoImpl.class).debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return (List<T>) qr.query(conn, sql, new BeanListHandler<T>(clazz), params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询集合" + clazz + "异常:" + e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public T queryObject(String sql, Object... params) {
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		try {
			Logger.getLogger(SysDaoImpl.class).debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return (T) qr.query(conn, sql, new BeanHandler<T>(clazz), params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询" + clazz + "异常:" + e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 修改
	 * 
	 * @param sql语句
	 * @param params
	 *            参数数组
	 */
	public boolean updateObject(String sql, Object... params) {
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		try {
			Logger.getLogger(SysDaoImpl.class).debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			int result = qr.update(conn, sql, params);
			if (1 == result) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("修改" + clazz + "异常:" + e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 批量操作
	 * 
	 */
	public boolean batchOperator(String sql, Object[]... params) {
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		try {
			conn.setAutoCommit(false);
			qr.batch(conn, sql, params);
			conn.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("批量操作" + clazz + "异常:" + e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除记录
	 * 
	 * @param sql
	 * @param params
	 */
	public boolean deleteObject(String sql, Object... params) {
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		try {
			Logger.getLogger(SysDaoImpl.class).debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			int result = qr.update(conn, sql, params);
			if (1 == result) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("删除" + clazz + "异常:" + e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 多条SQL操作
	 * 
	 * @param sql
	 * @param
	 * 
	 */
	public boolean tranOperator(List<String> sqlList, List<Object[]> paramsList) {
		if (sqlList.size() != paramsList.size()) {
			throw new RuntimeException("SQL语句和参数数组不一样");
		}
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		int size = sqlList.size();
		try {
			conn.setAutoCommit(false);
			for (int i = 0; i < size; i++) {
				Logger.getLogger(SysDaoImpl.class).debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
						+ StringUtil.replaceSql(new StringBuilder(sqlList.get(i)), paramsList.get(i),
								paramsList.get(i) == null ? 0 : paramsList.get(i).length)
						+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				qr.update(conn, sqlList.get(i), paramsList.get(i));
			}
			conn.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException("多条事务处理" + clazz + "异常:" + e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询总数，传入Sql和参数
	 * 
	 * @param query
	 * @param params
	 * @return
	 */
	public int queryCountByCustomSqlAndParams(String query, Object... params) {
		int total = 0;
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		try {
			Map<String, Object> map = (Map<String, Object>) qr.query(conn, query, new MapHandler(), params);

			Logger.getLogger(SysDaoImpl.class).debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(query), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Set<Entry<String, Object>> set = map.entrySet();// 取得键值对的对象set集合
			for (Entry<String, Object> en : set) {// 遍历键值对的集合
				String n = en.getValue().toString();
				total = Integer.parseInt(n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询总数" + clazz + "异常:" + e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return total;
	}
}
