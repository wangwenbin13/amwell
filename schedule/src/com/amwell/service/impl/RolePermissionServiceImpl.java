package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amwell.commons.MyDate;
import com.amwell.commons.StringUtil;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.IRolePermissionService;
import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;

/**
 * 角色_权限_Service
 * 
 * @author 胡双
 *
 */
@Service(value = "rolePermissionService")
public class RolePermissionServiceImpl extends SysDaoImpl<SysRoleVo>implements IRolePermissionService {

	/**
	 * 插入角色信息，以及对应的权限
	 * 
	 * @param sysRoleEntity
	 * @param permissinIds
	 * @param sysType
	 * @return
	 */
	public boolean insertRolePermissionData(SysRoleEntity sysRoleEntity, List<String> permissinIds, int sysType) {
		// 查询角色名称为:默认角色的是否存在
		String selectSql = "SELECT * FROM sys_role WHERE roleName = ?";
		Object[] p = new Object[] { "默认角色" };
		SysRoleVo vo = queryObject(selectSql, p);
		String roleId = "";
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		if (vo == null) {
			roleId = StringUtil.generateSequenceNo();
			// 插入角色SQL
			String roleSql = "INSERT INTO sys_role (roleId,roleName,remark,sysType,createBy,createOn) VALUES (?,?,?,?,?,?)";
			Object[] roleParams = new Object[] { roleId, // 主键
					sysRoleEntity.getRoleName(), // 角色名称
					sysRoleEntity.getRemark(), // 备注
					sysType, // 系统类型
					sysRoleEntity.getCreateBy(), // 创建人
					MyDate.getMyDateLong()// 创建时间
			};
			sqlList.add(roleSql);
			paramsList.add(roleParams);
		} else {
			roleId = vo.getRoleId();
		}
		// 删除原有角色对应的权限
		deleteObject("DELETE FROM sys_roletopower WHERE roleId = ? AND sysType = ?", new Object[] { roleId, 1 });
		// 插入角色权限对应关系
		String rolePerSql = "INSERT INTO sys_roletopower (roleId,powerId,sysType) VALUES (?,?,?)";
		for (int i = 0; i < permissinIds.size(); i++) {
			Object[] rolePerParams = new Object[] { roleId, // 角色ID
					permissinIds.get(i), // 权限ID
					sysType };
			sqlList.add(rolePerSql);
			paramsList.add(rolePerParams);
		}
		return tranOperator(sqlList, paramsList);
	}

	/**
	 * 根据角色名字查询角色信息
	 * 
	 * @param roleName
	 *            角色名字
	 * @param sysType
	 *            系统类型
	 */
	public SysRoleVo querySysRoleInfoByRoleName(String roleName, int sysType) {
		String sql = "SELECT roleId,roleName,remark,sysType,createBy,createOn,updateBy,updateOn FROM sys_role WHERE sysType = ? AND roleName = ?";
		Object[] params = new Object[] { sysType, // >> 系统类型
				roleName // >> 角色名称
		};
		return queryObject(sql, params);
	}

}
