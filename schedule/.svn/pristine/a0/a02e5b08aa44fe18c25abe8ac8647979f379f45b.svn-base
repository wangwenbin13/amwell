package com.amwell.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.commons.EncryptionUtils;
import com.amwell.dao.IBusinessDao;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.IPermissionService;
import com.amwell.service.ISysAdminService;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.SysMgrAdminVo;
import com.amwell.vo.SysPermissionVo;

@Service(value = "sysAdminService")
public class SysAdminServiceImpl extends SysDaoImpl<SysMgrAdminVo>implements ISysAdminService {

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IBusinessDao businessDao;

	/**
	 * 根据用户ID和系统类型查询管理员信息 只能查询未删除的用户的信息
	 * 
	 * @param userId
	 * @param sysType
	 * @return
	 */
	public SysMgrAdminVo queryAdminByUserIdAndSysId(String userId, int sysType) {
		String sql = "SELECT u.userId,u.userName,u.passWord,u.loginName,u.telephone,u.status,u.sysType,u.status,u.createBy,u.createOn,u.businessId "
				+ ",m.name,m.brefName,ur.roleId" + " FROM mgr_user u,mgr_business m,sys_userrole ur"
				+ " WHERE u.businessId = m.businessId" + " AND u.userId = ur.userId" + " AND u.sysType = ur.sysType"
				+ " AND u.userId = ?" // >> 用户ID
				+ " AND u.deleteFlag = ?" // >> 删除标志
				+ " AND u.sysType = ?";// >> 系统类型
		Object[] params = new Object[] { userId, // >> 用户ID
				0, sysType // >> 系统类型
		};
		return queryObject(sql, params);
	}

	public MgrBusinessEntity getBusinessById(String id) throws SQLException {
		return businessDao.getBusinessById(id);
	}

	/**
	 * 根据登录名和密码，以及系统类型查询管理员
	 * 
	 * @param loginName
	 * @param password
	 * @param sysType
	 * @return
	 */
	public SysMgrAdminVo queryAdminByLoginNameAndPassword(String loginName, String password, int sysType) {
		// >> 查询管理员的信息
		String sql = "SELECT u.userId,u.userName,u.loginName,u.telephone,u.status,u.sysType,u.status,u.createBy,u.createOn,u.businessId,u.remark"
				+ ",m.name,m.brefName,ur.roleId" + " FROM mgr_user u,mgr_business m,sys_userrole ur"
				+ " WHERE u.businessId = m.businessId" + " AND u.userId = ur.userId" + " AND u.sysType = ur.sysType"
				+ " AND u.deleteFlag = ?" // >> 删除标志
				+ " AND u.sysType = ?" // >> 系统类型
				+ " AND u.loginName = ?" // >> 登陆名
				+ " AND u.passWord = ?"; // >> 密码
		Object[] params = new Object[] { 0, // >> 删除标志
				sysType, // >> 登陆名称
				loginName, // >> 系统类型
				EncryptionUtils.encrypt(loginName + password) // >> 密码
		};
		SysMgrAdminVo sysAdminVo = queryObject(sql, params);
		// >> 如果该管理员的角色ID不存在，不能登录
		if (null == sysAdminVo || "".equals(sysAdminVo.getRoleId())) {
			return null;
		}
		// >> 查询管理员的访问权限
		List<SysPermissionVo> sysPermissionVos = permissionService.queryPermissionInfoByRoleId(sysAdminVo.getRoleId(),
				sysType);
		sysAdminVo.setSysPermissions(sysPermissionVos);
		return sysAdminVo;
	}

	/**
	 * 修改自己的密码
	 * 
	 * @param newpassword
	 * @param sysType
	 * @param userId
	 * @return
	 */
	public boolean updateUserPassWord(String newpassword, int sysType, SysMgrAdminVo admin) {
		String sql = "UPDATE mgr_user SET PASSWORD = ? WHERE userId = ? AND sysTYpe = ?";
		Object[] params = new Object[] { EncryptionUtils.encrypt(admin.getLoginName() + newpassword), admin.getUserId(),
				sysType };
		return updateObject(sql, params);
	}

	/**
	 * 根据商户ID查询所有的管理员(不包含角色和权限)
	 */
	public List<SysMgrAdminVo> queryAdminByBusinessId(String businessId, int sysType) {
		// >> 查询管理员的信息
		String sql = "SELECT u.userId,u.userName,u.loginName,u.telephone,u.status,u.sysType,u.status,u.createBy,u.createOn,u.businessId,u.remark"
				+ ",m.name,m.brefName,ur.roleId" + " FROM mgr_user u,mgr_business m,sys_userrole ur"
				+ " WHERE u.businessId = m.businessId" + " AND u.userId = ur.userId" + " AND u.sysType = ur.sysType"
				+ " AND u.deleteFlag = ?" // >> 删除标志
				+ " AND u.sysType = ?" // >> 系统类型
				+ " AND u.businessId = ?"; // >> 用户ID
		Object[] params = new Object[] { 0, // >> 删除标志
				sysType, // >> 登陆名称
				businessId, // >> 系统类型
		};
		return queryList(sql, params);
	}

}
