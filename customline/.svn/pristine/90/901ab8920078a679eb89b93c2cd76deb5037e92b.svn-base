package com.amwell.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.dao.IPermissionDao;
import com.amwell.vo.SysPermissionVo;

/**
 * 权限Dao_实现类
 * @author 胡双
 *
 */
@Repository(value="permissionDao")
public class PermissionDaoImpl extends DaoSupport implements IPermissionDao {
	
	
	/**
	 * 插入权限数据
	 * @param insertSql 插入语句
	 * @param params 对应的参数
	 * @return true插入成功，false 插入失败
	 */
	public boolean insertPermissionData(String insertSql, Object[] params) {
		super.finit("sys_power");
		
		int status = tableDao.executeSQL(insertSql, params);
		if(-1 == status){
			return false;
		}
		return true;
	}
	
	/**
	 * 根据条件查询权限数据
	 * @param selectSql 查询语句
	 * @param params 参数数组
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysPermissionVo> queryPermission(String selectSql,
			Object[] params) {
		super.finit("sys_power");
		List<SysPermissionVo> sysPermissionList = tableDao.queryList(SysPermissionVo.class,selectSql, params);
		if(null == sysPermissionList){
			return Collections.EMPTY_LIST;
		}
		return sysPermissionList;
	}
	
	
	

	

}
