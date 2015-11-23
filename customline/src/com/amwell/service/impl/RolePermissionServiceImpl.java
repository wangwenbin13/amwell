package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.commons.StringUtil;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.IPermissionService;
import com.amwell.service.IRolePermissionService;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysPermissionVo;
import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;
/**
 * 角色_权限_Service
 * @author 胡双
 *
 */
@Service(value="rolePermissionService")
public class RolePermissionServiceImpl extends SysDaoImpl<SysRoleVo> implements IRolePermissionService {
	
	@Autowired
	private IPermissionService permissionService;
	/**
	 * 角色，权限列表
	 * @param parseInt
	 * @param pageSize
	 * @param query
	 * @return
	 */
	public PageBean getPageBean(int parseInt, int pageSize, QueryHelper query) {
		
		//String sql = "SELECT 	r.roleId,r.roleName,r.remark,a.userName createby,r.createOn FROM sys_role r,sys_admin a"
			//		+"WHERE r.createBy = a.userId AND r.sysType = ? ORDER BY r.createOn DESC LIMIT ?,?";
		String pageSql = query.getListSql();
		Object[] pageParams = query.getObjectArrayParams();
		List<SysRoleVo> recordList = queryList(pageSql,pageParams);
		
		
		String countSql = query.getCountSql();
		int recordCount = queryCountByCustomSqlAndParams(countSql, pageParams);
		PageBean pageBean = new PageBean(parseInt, pageSize, recordCount, recordList);
		
		return pageBean;
	}
	
	/**
	 * 插入角色信息，以及对应的权限
	 * @param sysRoleEntity
	 * @param permissinIds
	 * @param sysType
	 * @return
	 */
	public boolean insertRolePermissionData(SysRoleEntity sysRoleEntity,
			List<String> permissinIds, int sysType) {
		sysRoleEntity.setRoleId(StringUtil.generateSequenceNo());
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		//插入角色SQL
		String roleSql = "INSERT INTO sys_role (roleId,roleName,remark,sysType,createBy,createOn) VALUES (?,?,?,?,?,?)";
		Object[] roleParams = new Object[]{
				sysRoleEntity.getRoleId(),//主键
				sysRoleEntity.getRoleName(),//角色名称
				sysRoleEntity.getRemark(),//备注
				sysType,//系统类型
				sysRoleEntity.getCreateBy(),//创建人
				MyDate.getMyDateLong()//创建时间
				};
		sqlList.add(roleSql);
		paramsList.add(roleParams);
		
		
		//插入角色权限对应关系
		String rolePerSql = "INSERT INTO sys_roletopower (roleId,powerId,sysType) VALUES (?,?,?)";
		for(int i=0;i<permissinIds.size();i++){
			Object[] rolePerParams = new Object[]{
					sysRoleEntity.getRoleId(),//角色ID
					permissinIds.get(i),//权限ID
					sysType
					};
			sqlList.add(rolePerSql);
			paramsList.add(rolePerParams);
		}
		
		boolean flag = tranOperator(sqlList, paramsList);
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(sysRoleEntity.getCreateBy(), "角色权限管理", "增加角色成功");
		}else{
			logObject = getLogObjectArray(sysRoleEntity.getCreateBy(), "角色权限管理", "增加角色失败");
		}
		// >> 记录日志
		insertObject(addUserLogSql, logObject);
		
		return flag;
	}
	
	
	/**
	 * 查询角色以及角色对应的权限
	 * @param roleId
	 * @param sysType
	 * @return
	 */
	public SysRoleVo querySysRoleInfoByRoleId(String roleId,int sysType) {
		
		//根据角色ID查询角色信息
		String roleSql = "SELECT roleId,roleName,remark,sysType,createBy,createOn,updateBy,updateOn FROM sys_role WHERE roleId = ? AND sysType = ?";
		Object[] roleParams = new Object[]{roleId,sysType};
		SysRoleVo sysRole = queryObject(roleSql, roleParams);
		
		//根据角色Id查询权限信息
		List<SysPermissionVo> sysPermissionList = permissionService.queryPermissionInfoByRoleId(roleId,sysType);
		sysRole.setSysPermissionList(sysPermissionList);
		
		
		return sysRole;
	}
	
	/**
	 * 修改角色以及角色对应的权限
	 * @param sysRoleEntity
	 * @param sysType
	 * @param permissinIds
	 * 
	 */
	public boolean updateRolePermissionData(SysRoleEntity sysRoleEntity,
			List<String> permissinIds, int sysType) {
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		//修改角色信息
		String roleSql = "UPDATE sys_role SET roleName = ?,remark = ? ,updateBy = ?,updateOn = ? WHERE roleId = ? AND sysType = ? ";
		Object[] roleParam = new Object[]{
				sysRoleEntity.getRoleName(),//角色名称
				sysRoleEntity.getRemark(),//备注
				sysRoleEntity.getUpdateBy(),//修改人
				MyDate.getMyDateLong(),//修改时间
				sysRoleEntity.getRoleId(),//修改的角色ID
				sysType//系统类型
				};
		sqlList.add(roleSql);
		paramsList.add(roleParam);
		
		//删除原来对应的权限
		String deleteSql = "DELETE FROM sys_roletopower WHERE roleId = ? AND sysType = ?";
		Object[] deleteParam = new Object[]{
					sysRoleEntity.getRoleId(),//角色ID
					sysType//系统类型
				};
		sqlList.add(deleteSql);
		paramsList.add(deleteParam);
		
		//添加角色对应的新权限
		//插入角色权限对应关系
		String rolePerSql = "INSERT INTO sys_roletopower (roleId,powerId,sysType) VALUES (?,?,?)";
		for(int i=0;i<permissinIds.size();i++){
			Object[] rolePerParams = new Object[]{
					sysRoleEntity.getRoleId(),//角色ID
					permissinIds.get(i),//权限ID
					sysType
					};
			sqlList.add(rolePerSql);
			paramsList.add(rolePerParams);
		}
		boolean flag = tranOperator(sqlList, paramsList);
		
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(sysRoleEntity.getUpdateBy(), "角色权限管理", "修改角色成功");
		}else{
			logObject = getLogObjectArray(sysRoleEntity.getUpdateBy(), "角色权限管理", "修改角色失败");
		}
		// >> 记录日志
		insertObject(addUserLogSql, logObject);
		
		return flag;
	}
	
	/**
	 * 删除角色
	 */
	public String deleteRoleAndPermissionByRoleIds(String userId,String ids,int sysType) {
		
		String[] idsA = ids.split(",");
		Object[] selectParams = new Object[idsA.length+1];
		selectParams[idsA.length] = sysType;
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<idsA.length;i++){
			buf.append("?,");
			selectParams[i] = idsA[i];
		}
		
		
		String addSql = buf.substring(0, buf.length()-1);
		
		// >> 验证角色是否绑定了用户
		String selectSql = "SELECT COUNT(*) FROM sys_userrole WHERE   roleId IN ("+addSql+") AND sysType = ?";
		
		
		int toutal = queryCountByCustomSqlAndParams(selectSql, selectParams);
		if(toutal > 0 ){
			return "isUse";
		}
		
		// >> 没有绑定用户
		
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		//删除角色关系
		String deleteRoleSql = "DELETE FROM sys_role WHERE  roleId IN ("+addSql+") AND sysType = ?";
		sqlList.add(deleteRoleSql);
		paramsList.add(selectParams);
		
		//删除角色对应的权限
		String deleteRolePerSql = "DELETE FROM sys_roletopower WHERE  roleId IN ("+addSql+") AND sysType = ?";
		sqlList.add(deleteRolePerSql);
		paramsList.add(selectParams);
		
		boolean flag = tranOperator(sqlList, paramsList);
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(userId, "角色权限管理", "删除角色成功");
		}else{
			logObject = getLogObjectArray(userId, "角色权限管理", "删除角色失败");
		}
		// >> 记录日志
		insertObject(addUserLogSql, logObject);
		
		
		if(flag){
			return "ok";
		}
		return "error";
	}
	
	/**
	 * 验证角色名称是否存在
	 * @param roleName
	 * @param sysType
	 * @return
	 */
	public boolean roleNameIsRepetition(String roleName, int sysType) {
		String selectSql = "SELECT COUNT(*) FROM sys_role r WHERE r.sysType = ? AND  r.roleName = ?";
		Object[] selectParams = new Object[]{sysType,roleName};
		
		int toutal = queryCountByCustomSqlAndParams(selectSql, selectParams);
		if(toutal>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询系统下面所有的角色
	 * @param sysType 系统类型
	 * @return
	 */
	public List<SysRoleVo> queryAllSysRole(int sysType) {
		String sql = "SELECT roleId,roleName,remark,sysType,createBy,createOn,updateBy,updateOn FROM sys_role WHERE sysType = ?";
		Object[] params = new Object[]{sysType};
		
		return queryList(sql, params);
	}
	
	/**
	 * 根据角色名字查询角色信息
	 * @param roleName 角色名字
	 * @param sysType 系统类型
	 */
	public SysRoleVo querySysRoleInfoByRoleName(String roleName, int sysType) {
		String sql = "SELECT roleId,roleName,remark,sysType,createBy,createOn,updateBy,updateOn FROM sys_role WHERE sysType = ? AND roleName = ?";
		Object[] params = new Object[]{
				sysType, // >> 系统类型
				roleName // >> 角色名称
			};
		
		return queryObject(sql, params) ;
	}
	
	/**
	 * 初始化插入角色权限信息
	 */
	public boolean initInsertRolePermissionData(SysRoleEntity sysRoleEntity,
			List<String> permissinIds, int sysType) {
		
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		// 查询该角色是否存在
		SysRoleVo vo = querySysRoleInfoByRoleName(sysRoleEntity.getRoleName(), sysType);
		if(vo == null){
			sysRoleEntity.setRoleId(StringUtil.generateSequenceNo());
			//插入角色SQL
			String roleSql = "INSERT INTO sys_role (roleId,roleName,remark,sysType,createBy,createOn) VALUES (?,?,?,?,?,?)";
			Object[] roleParams = new Object[]{
					sysRoleEntity.getRoleId(),//主键
					sysRoleEntity.getRoleName(),//角色名称
					sysRoleEntity.getRemark(),//备注
					sysType,//系统类型
					sysRoleEntity.getCreateBy(),//创建人
					MyDate.getMyDateLong()//创建时间
					};
			sqlList.add(roleSql);
			paramsList.add(roleParams);
		}else{
			sysRoleEntity.setRoleId(vo.getRoleId());
		}
		// 删除原有的角色权限对应关系
		//删除原来对应的权限
		String deleteSql = "DELETE FROM sys_roletopower WHERE roleId = ? AND sysType = ?";
		Object[] deleteParam = new Object[]{
					sysRoleEntity.getRoleId(),//角色ID
					sysType//系统类型
				};
		sqlList.add(deleteSql);
		paramsList.add(deleteParam);
		
		//插入角色权限对应关系
		String rolePerSql = "INSERT INTO sys_roletopower (roleId,powerId,sysType) VALUES (?,?,?)";
		for(int i=0;i<permissinIds.size();i++){
			Object[] rolePerParams = new Object[]{
					sysRoleEntity.getRoleId(),//角色ID
					permissinIds.get(i),//权限ID
					sysType
					};
			sqlList.add(rolePerSql);
			paramsList.add(rolePerParams);
		}
		return tranOperator(sqlList, paramsList);
	}
	
}
