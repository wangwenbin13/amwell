package com.amwell.base;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.amwell.commons.DBException;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.PropertyManage;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;

/**
 * 数据库操作基础类
 * 
 * @author zhangqiang
 * 
 */
@SuppressWarnings("all")
public class BaseDao {
	static Logger log = Logger.getLogger(BaseDao.class.getName());
	private final static QueryRunner queryRunner = new QueryRunner(true);
	private String dbType = "0";// 0：Mysql数据库；1：Oracle数据库
	private String tableName;
	private int pageSize = 20;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public BaseDao(String table) {
		this.tableName = table;
		this.pageSize = 20;
	}

	@SuppressWarnings("unchecked")
	private final ColumnListHandler columnListHandler = new ColumnListHandler() {
		@Override
		protected Object handleRow(ResultSet rs) throws SQLException {
			Object obj = super.handleRow(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}
	};

	@SuppressWarnings("unchecked")
	private final ScalarHandler scaleHandler = new ScalarHandler() {
		@Override
		public Object handle(ResultSet rs) throws SQLException {
			Object obj = super.handle(rs);
			if (obj instanceof BigInteger)
				return ((BigInteger) obj).longValue();
			return obj;
		}
	};

	public boolean excuteSqls_trc(String[] sqls) {
		return new BaseDao_trc().excuteSqls(sqls);
	}

	/**
	 * 数据类型转换
	 */
	private final List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>() {
		private final long serialVersionUID = 1L;

		{
			add(Long.class);
			add(Integer.class);
			add(String.class);
			add(java.util.Date.class);
			add(java.sql.Date.class);
			add(java.sql.Timestamp.class);
		}
	};

	private final boolean isPrimitive(Class<?> cls) {
		return cls.isPrimitive() || PrimitiveClasses.contains(cls);
	}

	public <T> T queryBean(Class<T> beanClass, String sql) {
		return (T) queryBean(beanClass, sql, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T queryBean(Class<T> beanClass, String sql, Object... params) {
		Connection conn = MyDataSource.getConnect();
		try {
			T obj = (T) queryRunner.query(conn, sql, isPrimitive(beanClass) ? scaleHandler : new BeanHandler(beanClass),
					params);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return obj;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public <T> List<T> queryList(Class<T> beanClass, String sql) {
		return (List<T>) queryList(beanClass, sql, null);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryList(Class<T> beanClass, String sql, Object... params) {
		Connection conn = MyDataSource.getConnect();
		try {
			List<T> objList = (List<T>) queryRunner.query(conn, sql,
					isPrimitive(beanClass) ? columnListHandler : new BeanListHandler(beanClass), params);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			if (objList.size() > 0) {
				return objList;
			} else {
				return null;
			}
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
	}

	public <T> List<T> queryFromMysql(Class<T> beanClass, String sql, int page, int count) {
		List<T> objList = (List<T>) queryFromMysql(beanClass, sql, page, count, null);
		if (objList.size() > 0) {
			return objList;
		} else {
			return null;
		}

	}

	public <T> List<T> queryFromMysql(Class<T> beanClass, String sql, int page, int count, Object... params) {
		if (page < 0 || count < 0)
			throw new IllegalArgumentException("查询参数错误！");
		count = (count > 0) ? count : getPageSize();
		int len = params.length;
		Object[] newParams = new Object[len - 2];
		for (int i = 0; i < len - 2; i++) {
			newParams[i] = params[i];
		}
		List<T> objList = queryList(beanClass, sql + " LIMIT ?,?",
				ArrayUtils.addAll(newParams, new Integer[] { page, count }));
		if (objList != null && objList.size() > 0) {
			return objList;
		} else {
			return null;
		}
	}

	public <T> List<T> queryFromOracle(Class<T> beanClass, String sql, int startRow, int endRow) {
		return (List<T>) queryFromOracle(beanClass, sql, startRow, endRow, null);
	}

	public <T> List<T> queryFromOracle(Class<T> beanClass, String sql, int startRow, int endRow, Object... params) {
		if (startRow < 0 || endRow < 0)
			throw new IllegalArgumentException("查询参数错误！");
		sql = "SELECT * FROM (SELECT ROWNUM RN, res.* FROM (" + sql + ") res WHERE ROWNUM <=?) WHERE RN >?";
		return queryList(beanClass, sql, params);
	}

	public <T> List<T> queryByPage(Class<T> beanClass, String sql, int condition1) {
		return queryByPage(beanClass, sql, condition1, pageSize, null);
	}

	public <T> List<T> queryByPage(Class<T> beanClass, String sql, int currentPage, Object... params) {
		return queryByPage(beanClass, sql, currentPage, pageSize, params);
	}

	public <T> List<T> queryByPage(Class<T> beanClass, String sql, int condition1, int condition2) {
		return queryByPage(beanClass, sql, condition1, condition2, null);
	}

	public <T> List<T> queryByPage(Class<T> beanClass, String sql, int currentPage, int pageSize, Object... params) {
		int endIndex = currentPage + pageSize;
		int startIndex = (currentPage > 0) ? currentPage : 0;
		Object args[] = null;
		if (params != null) {// 说明是绑定变量的查询
			int n = (params.length) + 2;// 加上两个分页的参数
			args = new Object[n];
			for (int i = 0; i < params.length; i++) {
				args[i] = params[i];
			}
			args[n - 2] = endIndex;
			args[n - 1] = startIndex;
		} else {
			args = new Object[2];
			args[0] = endIndex;
			args[1] = startIndex;
		}
		if ("1".equals(dbType)) {// Oracle数据库
			return queryFromOracle(beanClass, sql, currentPage, pageSize, args);
		} else {// 默认为Mysql数据库
			return queryFromMysql(beanClass, sql, currentPage, pageSize, args);
		}
	}

	public int queryCount(String sql) {
		int total = 0;
		String field = "TOTAL";
		Connection conn = MyDataSource.getConnect();
		String query = "SELECT COUNT(*) AS " + field + " FROM (" + sql + ") t";
		try {
			Map<String, Object> map = (Map<String, Object>) queryRunner.query(conn, query, new MapHandler());
			Set<Entry<String, Object>> set = map.entrySet();// 取得键值对的对象set集合
			for (Entry<String, Object> en : set) {// 遍历键值对的集合
				String n = en.getValue().toString();
				total = Integer.parseInt(n);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return total;
	}

	public int queryCount(String sql, Object... params) {
		int total = 0;
		String field = "TOTAL";
		Connection conn = MyDataSource.getConnect();
		String query = "SELECT COUNT(*) AS " + field + " FROM (" + sql + ") t";
		try {
			Map<String, Object> map = (Map<String, Object>) queryRunner.query(conn, query, new MapHandler(), params);
			Set<Entry<String, Object>> set = map.entrySet();// 取得键值对的对象set集合
			for (Entry<String, Object> en : set) {// 遍历键值对的集合
				String n = en.getValue().toString();
				total = Integer.parseInt(n);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
		return total;
	}

	public int executeSQL(String sql) {
		return executeSQL(sql, null);
	}

	public int excuteSqls(String[] sqls) {
		int result = 0;
		String[] zsqls = sqls;
		try {
			for (int i = 0; i < zsqls.length; i++) {
				executeSQL(zsqls[i]);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

	public int executeSQL(String sql, Object... params) {
		Connection conn = MyDataSource.getConnect();
		try {
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			return queryRunner.update(conn, sql, params);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return -1;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public <T> String updateDataForKeys(Object bean, String pri_name) {
		String keys = "";
		String getMethodName = "";
		String setMethodName = "";
		String sql = "";
		String primaryKey = "";
		SqlBuilder sb = new SqlBuilder();
		if (!"".equals(pri_name)) {
			primaryKey = pri_name.substring(0, 1).toUpperCase() + pri_name.substring(1);
			getMethodName = "get" + primaryKey;
			setMethodName = "set" + primaryKey;
		} else {
			getMethodName = "getRec_id";
			setMethodName = "setRec_id";
		}
		try {
			Method method = bean.getClass().getMethod(getMethodName, new Class[] {});
			Object value = method.invoke(bean, new Object[] {});
			if (value == null || "".equals(value)) {
				keys = StringUtil.generateSequenceNo();
				Method method2 = bean.getClass().getMethod(setMethodName, new Class[] { String.class }); // 得到set方法
				method2.invoke(bean, new Object[] { keys }); // 调用setXXXX方法
				sql = sb.getInsertSQL(bean, tableName);
			} else {
				sql = sb.getUpdateSQL(bean, tableName, pri_name);
				keys = StringUtil.objectToString(value);
			}
			Object[] params = sb.getParams();
			executeSQL(sql, params);
			log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<    "
					+ StringUtil.replaceSql(new StringBuilder(sql), params, params == null ? 0 : params.length)
					+ "     >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return keys;
	};

	public <T> String updateDataForKeys(Object bean) {
		return updateDataForKeys(bean, "");
	};

	public <T> int updateData(Object bean, String pri_name) {
		int result = -1;
		String getMethodName = "";
		String setMethodName = "";
		String sql = "";
		String primaryKey = "";
		SqlBuilder sb = new SqlBuilder();
		if (!"".equals(pri_name)) {
			primaryKey = pri_name.substring(0, 1).toUpperCase() + pri_name.substring(1);
			getMethodName = "get" + primaryKey;
			setMethodName = "set" + primaryKey;
		} else {
			getMethodName = "getRec_id";
			setMethodName = "setRec_id";
		}
		try {
			Method method = bean.getClass().getMethod(getMethodName, new Class[] {});
			Object value = method.invoke(bean, new Object[] {});
			if (value == null || "".equals(value)) {
				String idvalue = StringUtil.generateSequenceNo();
				Method method2 = bean.getClass().getMethod(setMethodName, new Class[] { String.class }); // 得到set方法
				method2.invoke(bean, new Object[] { idvalue }); // 调用setXXXX方法
				sql = sb.getInsertSQL(bean, tableName);
			} else {
				sql = sb.getUpdateSQL(bean, tableName, pri_name);
			}
			Object[] params = sb.getParams();
			result = executeSQL(sql, params);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	};

	public <T> int updateData(Object bean) {
		return updateData(bean, "");
	};

	public <T> int updateData(String table, Object bean) {
		tableName = table;
		return updateData(bean, "");
	};

	public <T> int updateData(String table, Object bean, String pri_name) {
		tableName = table;
		return updateData(bean, pri_name);
	};

	public int[] batch(String sql, Object[][] params) {
		Connection conn = MyDataSource.getConnect();
		try {
			return queryRunner.batch(conn, sql, params);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(), e);
			}
		}
	};

	public <T> int deleteByid(Class<T> beanClass, String idValue, String pri_name) {
		Connection conn = MyDataSource.getConnect();
		try {
			String sql = "delete from " + tableName + " where " + pri_name + " = ?";
			queryRunner.update(conn, sql, idValue);
			return 1;

		} catch (SQLException e) {
			return 0;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
	};

	@SuppressWarnings("unchecked")
	public <T> T queryBeanById(Class<T> beanClass, String id, String pri_name) {
		Connection conn = MyDataSource.getConnect();
		try {
			String sql = "select * from " + tableName + " where " + pri_name + " = ?";
			return (T) queryRunner.query(conn, sql, isPrimitive(beanClass) ? scaleHandler : new BeanHandler(beanClass),
					id);
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage(),e);
			}
		}
	};

	public <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
		List newList = new ArrayList();
		List subList = new ArrayList();
		if (list != null) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object bean = itr.next();
				newList.add(bean);
			}
			subList = newList.subList(fromIndex, toIndex);
		}
		return subList;
	}

	public Map callProcedure(String sql, Object... params) {
		Map map = new HashMap();
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = MyDataSource.getConnect();
			cs = conn.prepareCall(sql);
			// 设置输入参数
			for (int i = 0; i < params.length; i++) {
				cs.setObject(i + 1, params[i]);
			}
			// 设置输出参数
			cs.registerOutParameter(8, Types.INTEGER);
			cs.registerOutParameter(9, Types.INTEGER);
			cs.registerOutParameter(10, Types.INTEGER);
			cs.registerOutParameter(11, Types.INTEGER);
			cs.registerOutParameter(12, Types.INTEGER);
			cs.registerOutParameter(13, Types.INTEGER);
			rs = cs.executeQuery();
			while (rs.next()) {
				map.put("visit_bouce_count", rs.getString("visit_bouce_count"));
				map.put("page_count", rs.getString("page_count"));
				map.put("page_bounce_count", rs.getString("page_bounce_count"));
				map.put("play_bounce_count", rs.getString("play_bounce_count"));
				map.put("visit_prog_count", rs.getString("visit_prog_count"));
				map.put("visit_interval", rs.getString("visit_interval"));
			}
		} catch (SQLException e) {
			log.info(e.getMessage(),e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (cs != null) {
					cs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				log.info(ex.getMessage(),ex);
			}
		}
		return map;
	}
}
