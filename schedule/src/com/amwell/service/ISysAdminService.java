package com.amwell.service;

import java.sql.SQLException;
import java.util.List;

import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.SysMgrAdminVo;

/**
 * 管理员_Service
 * 
 * @author Administrator
 *
 */

public interface ISysAdminService {

	/**
	 * 根据用户ID和系统类型查询管理员信息
	 * 
	 * @param userId
	 * @param sysType
	 * @return
	 */
	SysMgrAdminVo queryAdminByUserIdAndSysId(String userId, int sysType);

	/**
	 * 根据登录名和密码和系统类型，以及系统类型查询管理员
	 * 
	 * @param loginName
	 * @param password
	 * @param sysType
	 * @return
	 */
	SysMgrAdminVo queryAdminByLoginNameAndPassword(String loginName, String password, int sysType);

	public MgrBusinessEntity getBusinessById(String id) throws SQLException;

	/**
	 * 修改自己的密码
	 * 
	 * @param newpassword
	 * @param sysType
	 * @param admin
	 * @return
	 */
	boolean updateUserPassWord(String newpassword, int sysType, SysMgrAdminVo admin);

	/**
	 * 根据商户Id查询所有的管理员(不包含角色和权限)
	 * 
	 * @param businessId
	 * @param i
	 * @return
	 */
	List<SysMgrAdminVo> queryAdminByBusinessId(String businessId, int i);
}
