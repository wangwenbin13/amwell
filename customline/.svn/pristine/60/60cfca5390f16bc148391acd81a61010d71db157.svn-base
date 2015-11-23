package com.amwell.service;

import java.util.List;

import com.amwell.commons.QueryHelper;
import com.amwell.vo.InitializationAllData;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;

/**
 * 权限_Service
 * @author 胡双
 *
 */
public interface IPermissionService {
	
	/**
	 * 获得系统所有的权限
	 * @param sysType
	 * @return 
	 */
	List<SysPermissionVo> queryAllSysPermission(int sysType);
	
	/**
	 * 获得左侧的菜单
	 * @param sysType
	 * @param fid
	 * @return
	 */
	List<SysPermissionVo> queryLeftMenu(int sysType, String fid,String userId);
	
	/**
	 * 根据角色ID查询对应的权限
	 * @param roleId
	 * @param sysType
	 * @return
	 */
	List<SysPermissionVo> queryPermissionInfoByRoleId(String roleId,
			int sysType);
	
	/**
	 * 批量插入权限信息
	 * @param permissionList
	 * @param sysType
	 * @return
	 */
	boolean insertPermissionData(List<SysPermissionEntity> permissionList, int sysType);
	/**
	 * 初始化或更新调用的方法
	 * @param permissionList
	 * @param sysType
	 * @return
	 */
	boolean initPermissionData(List<SysPermissionEntity> permissionList, int sysType);
	
	/**
	 * 根据请求路径和系统类型查询权限信息
	 * @param path
	 * @param sysType
	 * @return
	 */
	SysPermissionVo queryPermissionInfoByUrlAndSysType(String path, int sysType);

	/**
	 * 判断请求的nameSpace是否在权限控制范围内
	 * @param nameSpace 命名空间
 	 * @param sysType 系统类型
	 * @return
	 */
	int queryPermissionByNameSpace(String nameSpace, int sysType);
	
	/**
	 * 
	 * @param parseInt
	 * @param pageSize
	 * @param query
	 * @return
	 */
	PageBean getPageBean(int parseInt, int pageSize, QueryHelper query);
	
	/**
	 * 根据权限名称查找权限
	 * @param permissionName
	 * @param sysType
	 * @return
	 */
	SysPermissionVo queryPermissionByName(String permissionName, int sysType);
	
	/**
	 * 根据权限编码查询权限
	 * @param fid
	 * @param sysType
	 * @return
	 */
	SysPermissionVo queryPermissionByCode(String fid, int sysType);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 插入权限数据,powerId不需要指定
	 * @param permissionList 一组权限(SysPermissionEntity)对象
	 * @param sysType 系统类型(0:运营平台 1:调度平台)
	 * @return 表示是否插入成功(成功true,失败false)
	 */
	//boolean insertPermissionData(List<SysPermissionEntity> permissionList,int sysType);
	
	
	/**
	 * 查询权限集合
	 * @param powerIds 要查的权限表的主键集合(对应数据库表的powerId字段)
	 * @param sysType 系统类型(0:运营平台 1:调度平台)
	 * @return List<SysPermissionEntity> 所查权限集合
	 */
	//List<SysPermissionVo> queryPermission(Map<String,String> params,int sysType);
	
	
	
	
}
