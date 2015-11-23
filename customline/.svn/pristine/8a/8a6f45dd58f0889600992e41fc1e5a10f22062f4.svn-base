package com.amwell.dao;

import java.util.List;

import com.amwell.vo.SysPermissionVo;

/**
 * 权限Dao
 * @author 胡双
 *
 */
public interface IPermissionDao {
	
	/**
	 * 插入权限数据
	 * @param insertSql 插入语句
	 * @param params 对应的参数
	 * @return true插入成功，false 插入失败
	 */
	boolean insertPermissionData(String insertSql,
			Object[] params);
	
	/**
	 * 根据条件查询权限数据
	 * @param selectSql 查询语句
	 * @param params 参数数组
	 * @return
	 */
	List<SysPermissionVo> queryPermission(String selectSql, Object[] params);

}
