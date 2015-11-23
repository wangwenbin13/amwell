package com.amwell.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.dao.IAdminDao;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysPermissionVo;

@Repository(value="adminDaoImpl")
public class AdminDaoImpl extends DaoSupport implements IAdminDao {
	
	/**
	 * 插入管理员(运营平台)
	 * @param sql 插入SQL语句
	 * @param params SQL语句对应的值
	 * @return 返回true成功，返回false失败
	 */
	public boolean insertAdminData(String sql, Object[] params) {
		super.finit("sys_admin");
		int status = tableDao.executeSQL(sql, params);
		if(-1 == status){
			return false;
		}
		return true;
	}
	
	/**
	 * 根据条件查询管理员信息
	 * @param sql 查询SQL
	 * @param params 查询参数
	 * @return null 没有找到
	 */
	public SysAdminVo queryAdminData(String sql, Object[] params) {
		super.finit("sys_admin");
		return tableDao.queryBean(SysAdminVo.class, sql,params);
	}
	
	/**
	 * 插入管理员角色对应关系
	 * @param sql 查询SQL
	 * @param params 查询参数
	 * @return  返回true成功，返回false失败
	 */
	public boolean insertAdminRoleData(String insertSql, Object[] params) {
		super.finit("sys_userrole");
		int status = tableDao.executeSQL(insertSql, params);
		if(-1 == status){
			return false;
		}
		return true;
	}

	/**
	 * 根据userId查询用户的权限
	 * @param sql 查询语句
	 * @param params 参数数组
	 * @return  返回权限路径集合
	 */
	@SuppressWarnings("unchecked")
	public List<SysPermissionVo> queryAdminPermissions(String sql, Object[] params) {
		
		List<SysPermissionVo> permissions = tableDao.queryList(SysPermissionVo.class, sql, params);
		if(null == permissions){
			return Collections.EMPTY_LIST;
		}
		return permissions;
	}
	

	
}
