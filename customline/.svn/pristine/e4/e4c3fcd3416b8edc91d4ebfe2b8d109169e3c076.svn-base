package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.QueryHelper;
import com.amwell.dao.IRolePermissionDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;

@Repository
public class RolePermissionDaoImpl extends DaoSupport implements IRolePermissionDao {
	
	/**
	 * 执行角色表(sys_role)的SQL
	 * @param sql执行的SQL
	 * @param SQL对应的参数
	 * @return 插入成功返回true,插入失败返回false
	 */
	public boolean executeRoleTableSql(String sql, Object[] roleParams){
		super.finit("sys_role");
		int status = tableDao.executeSQL(sql, roleParams);
		if(-1 == status){
			return false;
		}
		return true;
	}
	
	/**
	 * 执行角色表(sys_role)的SQL
	 * @param sql执行的SQL
	 * @param SQL对应的参数
	 * @return 插入成功返回true,插入失败返回false
	 */
	public boolean executeRolePermissionTableSql(String sql, Object[] roleParams){
		super.finit("sys_roletopower");
		int status = tableDao.executeSQL(sql, roleParams);
		if(-1 == status){
			return false;
		}
		return true;
	}
	
	/**
	 * 根据条件查询角色信息
	 * @param sql
	 * @param params
	 * @return
	 */
	public SysRoleEntity querySysRoleData(String sql, Object[] params) {
		super.finit("sys_role");
		return tableDao.queryBean(SysRoleEntity.class,sql, params);
	}
	
	/**
	 * 查询角色列表
	 * @param search
	 * @param crrentPage 当前页
	 * @param pageSize 每页记录数
	 * @return
	 */
	public Map<String, Object> listByPage(Search search, int crrentPage,
			int pageSize) {
		super.finit("sys_role");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		//条件参数
		if(search!= null){
			
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = "select roleId,roleName,remark,sysType,createBy,createOn,updateBy,updateOn from sys_role"
			+cond;
		
		list = tableDao.queryByPage(SysRoleEntity.class, sql, crrentPage,pageSize);
		page = new Page(list,sql,crrentPage,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 查询角色列表分页数据
	 * @param currentPage 当前页
	 * @param pageSize 每页数量
	 * @param query queryHelper对象
	 * @return
	 */
	public PageBean getPageBean(int currentPage, int pageSize,
			QueryHelper query) {
		super.finit("sys_role r,sys_admin a");
		int recordCount =  tableDao.queryCountByCustomSqlAndParams(query.getCountSql(), query.getObjectArrayParams());
		List<SysRoleVo> recordList = tableDao.queryListByCustomSqlAndParams(SysRoleVo.class,query.getListSql(),query.getObjectArrayParams());
		PageBean pageBean = new PageBean(currentPage, pageSize, recordCount, recordList);
		return pageBean;
	}

	@SuppressWarnings("unchecked")
	public List<SysPermissionEntity> querySysRolePermissions(String sql,
			Object[] params) {
		super.finit("sys_power p,sys_roletopower rp");
		List<SysPermissionEntity> sysPermissionList = tableDao.queryList(SysPermissionEntity.class,sql, params);
		if(null == sysPermissionList){
			return Collections.EMPTY_LIST;
		}
		return sysPermissionList;
		
	}
	
	
	
	
	
	
}
