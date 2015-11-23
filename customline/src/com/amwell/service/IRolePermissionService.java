package com.amwell.service;

import java.util.List;

import com.amwell.commons.QueryHelper;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;

/**
 * 角色_权限_Service
 * @author 胡双
 *
 */

public interface IRolePermissionService {
	
	/**
	 * 角色，权限列表
	 * @param parseInt
	 * @param pageSize
	 * @param query
	 * @return
	 */
	PageBean getPageBean(int parseInt, int pageSize, QueryHelper query);
	
	/**
	 * 插入角色信息，以及对应的权限
	 * @param sysRoleEntity
	 * @param permissinIds
	 * @param sysType
	 * @return
	 */
	boolean insertRolePermissionData(SysRoleEntity sysRoleEntity,
			List<String> permissinIds, int sysType);
	
	/**
	 * 初始化数据的时候调用，插入角色信息，以及对应的权限(日志表不插入数据)
	 * @param sysRoleEntity
	 * @param permissinIds
	 * @param sysType
	 * @return
	 */
	boolean initInsertRolePermissionData(SysRoleEntity sysRoleEntity,
			List<String> permissinIds, int sysType);
	
	/**
	 * 查询角色以及角色对应的权限
	 * @param roleId
	 * @param sysType 
	 * @return
	 */
	SysRoleVo querySysRoleInfoByRoleId(String roleId,int sysType);
	
	/**
	 * 修改角色信息以及对应的权限
	 * @param sysRoleEntity
	 * @param permissinIds
	 * @param sysType
	 * @return
	 */
	boolean updateRolePermissionData(SysRoleEntity sysRoleEntity,
			List<String> permissinIds, int sysType);
	
	/**
	 * 删除角色
	 * @param ids
	 * @param userId 
	 * @param sysType
	 * @return
	 */
	String deleteRoleAndPermissionByRoleIds(String userId,String ids, int sysType);
	
	/**
	 * 验证角色名称是否存在
	 * @param roleName
	 * @param sysType
	 * @return
	 */
	boolean roleNameIsRepetition(String roleName, int sysType);
	
	/**
	 * 查询系统下面所有的角色
	 * @param sysType 系统类型
	 * @return
	 */
	List<SysRoleVo> queryAllSysRole(int sysType);

	
	/**
	 * 根据名字查找角色信息
	 * @param roleName 角色名字
	 * @param sysType 系统类型
	 * @return 角色信息
	 */
	SysRoleVo querySysRoleInfoByRoleName(String roleName, int sysType);
	
	
	
	
	
}
