package com.amwell.dao;

import java.util.List;

public interface ISysDao<T> {
	
	//返回一个集合
	List<T> queryList(String sql,Object... params);
	
	//返回一个对象
	T queryObject(String sql,Object... params);
	
	//修改一个对象
	boolean updateObject(String sql,Object... params);
	
	//插入一个对象
	boolean insertObject(String sql,Object... params);
	
	//批量操作
	boolean batchOperator(String sql,Object[]... params);
	
	//删除一个对象
	boolean deleteObject(String sql,Object ... params);
	
	/**
	 * 事务的处理
	 * @param sql
	 * @param paramsList
	 * @return 执行成功返回true，否则返回false
	 */
	boolean tranOperator(List<String> sql,List<Object[]> paramsList);
	
	/**
	 * 查询总数
	 * @param query
	 * @param params
	 * @return 
	 */
	int queryCountByCustomSqlAndParams(String query, Object... params);
	
}
