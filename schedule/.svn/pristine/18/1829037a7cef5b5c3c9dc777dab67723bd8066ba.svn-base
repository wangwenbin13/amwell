package com.amwell.service;

import java.util.List;

import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;

/**
 * 角色_权限_Service
 * 
 * @author 胡双
 *
 */

public interface IRolePermissionService {

	/**
	 * 插入角色信息，以及对应的权限
	 * 
	 * @param sysRoleEntity
	 * @param permissinIds
	 * @param sysType
	 * @return
	 */
	boolean insertRolePermissionData(SysRoleEntity sysRoleEntity, List<String> permissinIds, int sysType);

	/**
	 * 根据名字查找角色信息
	 * 
	 * @param roleName
	 *            角色名字
	 * @param sysType
	 *            系统类型
	 * @return 角色信息
	 */
	SysRoleVo querySysRoleInfoByRoleName(String roleName, int sysType);

}
