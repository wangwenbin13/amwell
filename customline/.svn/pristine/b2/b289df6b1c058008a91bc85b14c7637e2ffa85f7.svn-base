package com.amwell.dao;

import java.util.List;
import java.util.Map;

import com.amwell.commons.QueryHelper;
import com.amwell.entity.Search;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysRoleEntity;

public interface IRolePermissionDao {
	
	
	/**
	 * 执行角色表(sys_role)的SQL
	 * @param sql sql执行的SQL
	 * @param params SQL对应的参数
	 * @return 插入成功返回true,插入失败返回false
	 */
	boolean executeRoleTableSql(String sql, Object[] params);
	
	/**
	 * 执行角色表(sys_role)的SQL
	 * @param sql sql执行的SQL
	 * @param params SQL对应的参数
	 * @return 插入成功返回true,插入失败返回false
	 */
	boolean executeRolePermissionTableSql(String sql, Object[] params);
	
	/**
	 * 根据条件查询角色信息
	 * @param sql
	 * @param params
	 * @return
	 */
	SysRoleEntity querySysRoleData(String sql, Object[] params);
	
	/**
	 * 查询角色列表
	 * @param search
	 * @param crrentPage 当前页
	 * @param pageSize 每页记录数
	 * @return
	 */
	Map<String, Object> listByPage(Search search, int crrentPage, int pageSize);
	
	/**
	 * 查询角色列表分页数据
	 * @param currentPage 当前页
	 * @param pageSize 每页数量
	 * @param query queryHelper对象
	 * @return
	 */
	PageBean getPageBean(int currentPage, int pageSize,
			QueryHelper query);
	
	/**
	 * 根据Sql和参数查询角色对应的权限
	 * @param sql
	 * @param params
	 * @return
	 */
	List<SysPermissionEntity> querySysRolePermissions(String sql,
			Object[] params);
	
	
	
	
	
	
}
