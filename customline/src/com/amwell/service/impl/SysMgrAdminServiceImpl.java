package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.commons.MyDate;
import com.amwell.commons.Sha1Utils;
import com.amwell.commons.StringUtil;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.IPermissionService;
import com.amwell.service.IRolePermissionService;
import com.amwell.service.ISysMgrAdminService;
import com.amwell.vo.SysMgrAdminEntity;
import com.amwell.vo.SysMgrAdminVo;
import com.amwell.vo.SysPermissionVo;
import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;

@Service("sysMgrAdminService")
public class SysMgrAdminServiceImpl extends SysDaoImpl<SysMgrAdminVo> implements ISysMgrAdminService {
	
	@Autowired
	private IPermissionService permissionService;
	@Autowired
	private IRolePermissionService rolePermissionService;
	
	/**
	 * 运营平台商户添加，同步到调度平台的管理员信息
	 * 需要的信息：联系人的名称:userName,登陆名:loginName,密码:passWord,
	 * 联系电话:telephone,账号状态:status,备注:remark,创举人:createBy,
	 * 商户ID:businessId
	 * @param  sysMgrAdmin 商户管理员的实体对象
	 * 
	 * @return true插入成功，false 插入失败
	 */
	public boolean insertIntoScheduleAdminInfo(SysMgrAdminEntity sysMgrAdmin) {
		
		String roleId = "";
		String userId = StringUtil.generateSequenceNo();
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		String sql = "INSERT INTO mgr_user " +
				"(userId,userName,loginName,passWord,telephone,status,sysType,remark,createBy,createOn,deleteFlag,businessId)"
			+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params = new Object[]{
				userId, // >> 用户ID(主键)
				sysMgrAdmin.getUserName(), // >> 用户姓名
				sysMgrAdmin.getLoginName(), // >> 登陆名称
				Sha1Utils.encrypt(sysMgrAdmin.getLoginName()+sysMgrAdmin.getPassWord()), // >> 密码 加密(用户名和密码)
				sysMgrAdmin.getTelephone(), // >> 电话号码
				sysMgrAdmin.getStatus(), // >> 账号状态
				1, // >> 系统类型
				sysMgrAdmin.getRemark(), //>> 备注
				sysMgrAdmin.getCreateBy(), // >> 操作人
				MyDate.getMyDateLong(), // >> 操作时间
				0, // >> 删除标志
				sysMgrAdmin.getBusinessId() // >> 商户ID
		};
		sqlList.add(sql);
		paramsList.add(params);
		
		// >>
		
			// >>　默认角色
			 SysRoleVo sysRole = rolePermissionService.querySysRoleInfoByRoleName("默认角色", 1);
			 //说明该角色已经存在，就不需要创建角色了
			 if(null == sysRole){
				 // >> 获得所有调度平台的权限
				 List<SysPermissionVo> sysPermissionVo = permissionService.queryAllSysPermission(1);
				 List<String> pid = new ArrayList<String>();
				 for(int i=0;i<sysPermissionVo.size();i++){
					pid.add(sysPermissionVo.get(i).getPowerId());
				}
				
				 //创建角色
				 SysRoleEntity role = new SysRoleEntity();
				 role.setRoleName("默认角色");
				 role.setRemark("");
				 role.setSysType(1);
				 role.setCreateBy("系统自动生成");
				 boolean falg = rolePermissionService.insertRolePermissionData(role, pid,1);
				 sysRole = rolePermissionService.querySysRoleInfoByRoleName("默认角色", 1);
			 }
			 roleId = sysRole.getRoleId();
		
		// >> 插入用户角色对应关系
		String userRoleSql = "INSERT INTO sys_userrole (userRoleId,roleId,userId,sysType) VALUES (?,?,?,?)";
		Object[] userRoleParams = new Object[]{
				StringUtil.generateSequenceNo(),// >> 主键
				roleId,// >> 角色ID
				userId,// >> 用户ID
				1 // >> 系统类型
			};
		sqlList.add(userRoleSql);
		paramsList.add(userRoleParams);
		
		
		
		return tranOperator(sqlList, paramsList);
		
	}
	
	/**
	 * 更新
	 * 需要更新的信息：
	 * 密码:passWord，账号状态:status，联系电话:telephone，联系人名称:userName，备注:remark，修改人:updateBy
	 * @param  sysMgrAdmin 商户管理员的实体对象
	 * 
	 * @return true插入成功，false 插入失败
	 */
	public boolean updateScheduleAdminInfo(SysMgrAdminEntity sysMgrAdmin) {
		
		String sql ="UPDATE mgr_user SET loginName=?, status = ?,telephone = ?,userName = ?,remark = ?,updateBy = ?,updateOn = ? " +
				" WHERE businessId = ? AND createBy = ?";
		Object[] params = new Object[]{
				sysMgrAdmin.getLoginName(), // >> 登录名
				//Sha1Utils.encrypt(sysMgrAdmin.getLoginName()+sysMgrAdmin.getPassWord()), // >> 密码
				sysMgrAdmin.getStatus(),
				sysMgrAdmin.getTelephone(),
				sysMgrAdmin.getUserName(),
				sysMgrAdmin.getRemark(),
				sysMgrAdmin.getUpdateBy(),
				MyDate.getMyDateLong(), // >> 修改时间
				sysMgrAdmin.getBusinessId(),
				sysMgrAdmin.getCreateBy()
		};
		
		return insertObject(sql, params);
	}
	
	/**
	 * 重置商户的密码
	 * 
	 */
	public boolean resertMgrPassword(SysMgrAdminEntity sysMgrAdmin,String userId) {
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		String sql = "UPDATE mgr_user SET passWord = ? WHERE businessId = ? AND createBy = ?";
		
		Object[] params = new Object[]{
				Sha1Utils.encrypt(sysMgrAdmin.getLoginName()+sysMgrAdmin.getPassWord()),
				sysMgrAdmin.getBusinessId(),
				sysMgrAdmin.getCreateBy()
		};	
		sqlList.add(sql);
		paramsList.add(params);
		
		boolean flag = tranOperator(sqlList, paramsList);
		
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		
		if(flag){
			logObject = getLogObjectArray(sysMgrAdmin.getCreateBy(), "商户管理", "重置密码成功");
		}else{
			logObject = getLogObjectArray(sysMgrAdmin.getCreateBy(), "商户管理", "重置密码失败");
		}
		//记录日志
		insertObject(addUserLogSql, logObject);
		
		return flag;
	}
	
	/**
	 * 禁用或启用商户账号
	 * 需要的参数:商户ID:businessId，状态:status
	 */
	public boolean changeMgrAccountStatus(SysMgrAdminEntity sysMgrAdmin) {
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		String sql = "UPDATE mgr_user SET status = ? WHERE businessId = ?";
		Object[] params = new Object[]{
				sysMgrAdmin.getStatus(),
				sysMgrAdmin.getBusinessId()
		};
		sqlList.add(sql);
		paramsList.add(params);
		
		String sql_l = "UPDATE mgr_business SET accountStatus = ? WHERE businessId = ? ";
		sqlList.add(sql_l);
		Object[] params_1 = params;
		paramsList.add(params_1);
		
		boolean flag = tranOperator(sqlList, paramsList);
		
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(sysMgrAdmin.getUpdateBy(), "商户管理", "修改账号状态成功");
		}else{
			logObject = getLogObjectArray(sysMgrAdmin.getUpdateBy(), "商户管理", "修改账号状态失败");
		}
		
		//记录日志
		insertObject(addUserLogSql, logObject);
		
		return flag;
	}

}
