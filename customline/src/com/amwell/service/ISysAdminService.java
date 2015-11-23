package com.amwell.service;

import com.amwell.commons.QueryHelper;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysAdminEntity;
import com.amwell.vo.SysAdminVo;

/**
 * 管理员_Service
 * @author Administrator
 *
 */

public interface ISysAdminService {
	
	/**
	 * 添加管理员
	 * @param adminInfo 管理员信息
	 * @param sysType 系统类型
	 * @param roleId  对应的角色ID
	 * @return 插入成功返回true,插入失败返回false
	 */
	boolean insertAdminData(SysAdminEntity adminInfo,int sysType, String roleId);
	
	/**
	 * 添加管理员
	 * @param adminInfo 管理员信息
	 * @param sysType 系统类型
	 * @param roleId  对应的角色ID
	 * @return 插入成功返回true,插入失败返回false
	 */
	boolean initInsertAdminData(SysAdminEntity adminInfo,int sysType, String roleId);
	
	/**
	 * 根据条件查询管理员信息
	 * @param maps1 参数map
	 * @param sysTyp系统类型参数
	 * @return 返回具体管理员，如果没有查到返回null;
	 */
	
	/**
	 * 验证登陆名称是否存在
	 * @param loginName
	 * @param sysType
	 * @return 存在返回true,失败返回false
	 */
	boolean loginNameIsRepetition(String loginName, int sysType);
	
	/**
	 * 查询管理员列表分页
	 */
	PageBean getPageBean(int parseInt, int pageSize, QueryHelper query);
	
	/**
	 * 根据用户ID和系统类型查询管理员信息
	 * @param userId
	 * @param sysType 
	 * @return
	 */
	SysAdminVo queryAdminByUserIdAndSysId(String userId, int sysType);
	
	/**
	 * 修改管理员信息以及对应的角色关系
	 * @param sysAdminEntity
	 * @param sysType
	 * @param roleId
	 * @return 成功返回true，失败返回false
	 */
	boolean updateAdminData(SysAdminEntity sysAdminEntity, int sysType, String roleId);
	
	/**
	 * 重置用户的密码
	 * @param sysAdminEntity
	 * @param sysType
	 * @return 成功返回true,失败返回False
	 */
	boolean resetPassword(SysAdminEntity sysAdminEntity, int sysType);
	
	/**
	 * 根据登录名和密码和系统类型，以及系统类型查询管理员
	 * @param loginName
	 * @param password
	 * @param sysType
	 * @return
	 */
	SysAdminVo queryAdminByLoginNameAndPassword(String loginName,
			String password,int sysType);
	
	/**
	 * 根据登录名和系统类型，以及系统类型查询管理员
	 * @param loginName
	 * @param sysType
	 * @return
	 */
	SysAdminVo queryAdminByLoginNameAndPassword(String loginName,int sysType);
	
	/**
	 * 删除管理员
	 * @param admin 
	 * @param userids
	 * @param sysType
	 * @return
	 */
	String deleteAdmin(SysAdminVo admin, String userids, int sysType);
	
	/**
	 * 修改自己的密码
	 * @param newpassword
	 * @param sysType
	 * @param admin
	 * @return
	 */
	boolean updateUserPassWord(String newpassword, int sysType, SysAdminVo admin);
}
