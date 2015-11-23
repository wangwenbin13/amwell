package com.amwell.dao;

import java.util.List;

import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysPermissionVo;

public interface IAdminDao {
	
	/**
	 * 插入管理员(运营平台)
	 * @param sql 插入SQL语句
	 * @param params SQL语句对应的值
	 * @return 返回true成功，返回false失败
	 */
	boolean insertAdminData(String sql, Object[] params);
	
	/**
	 * 根据条件查询管理员信息
	 * @param sql 查询SQL
	 * @param params 查询参数
	 * @return
	 */
	SysAdminVo queryAdminData(String sql, Object[] params);
	
	/**
	 * 插入管理员角色对应关系
	 * @param sql 查询SQL
	 * @param params 查询参数
	 * @return  返回true成功，返回false失败
	 */
	boolean insertAdminRoleData(String insertSql, Object[] params);
	
	/**
	 * 根据userId查询用户的权限
	 * @param sql 查询语句
	 * @param params 参数数组
	 * @return  返回权限对象集合
	 */
	List<SysPermissionVo> queryAdminPermissions(String sql, Object[] params);
	
	

}
