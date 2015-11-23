package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.commons.EncryptionUtils;
import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.commons.Sha1Utils;
import com.amwell.commons.StringUtil;
import com.amwell.dao.IAdminDao;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.IPermissionService;
import com.amwell.service.ISysAdminService;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysAdminEntity;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysPermissionVo;

@Service(value="sysAdminService")
public class SysAdminServiceImpl extends SysDaoImpl<SysAdminVo> implements ISysAdminService {
	
	@Autowired
	private IAdminDao adminDao;
	
	@Autowired
	private IPermissionService permissionService;
	
	/**
	 * 添加管理员
	 * @param adminInfo 管理员信息
	 * @param sysType 系统类型(0:运营平台 1:调度平台)
	 * @param roleId 管理员对应的角色ID
	 * @return 插入成功返回true,插入失败返回false
	 */
	public boolean insertAdminData(SysAdminEntity adminInfo,int sysType,String roleId) {
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		// >> 设置主键
		adminInfo.setUserId(StringUtil.generateSequenceNo());
		// >> 设置创建时间
		adminInfo.setCreateOn(MyDate.getMyDateLong());
		adminInfo.setPassWord(Sha1Utils.encrypt(adminInfo.getLoginName()+adminInfo.getPassWord())); // >> 加密(用户名和密码)
		
		String sql = "INSERT INTO sys_admin (userId,userName,loginName,passWord,departmentId,telephone,status,sysType,remark,createBy,createOn,provinceCode,cityCode,cityName)"
			+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params = new Object[]{
				adminInfo.getUserId(), // >> 用户ID
				adminInfo.getUserName(), // >> 用户姓名
				adminInfo.getLoginName(), // >> 登陆名称
				adminInfo.getPassWord(), // >> 密码
				adminInfo.getDepartmentId(), // >> 部门ID
				adminInfo.getTelephone(), // >> 电话号码
				adminInfo.getStatus(), // >> 账号状态
				sysType, // >> 系统类型
				adminInfo.getRemark(), //>> 备注
				adminInfo.getCreateBy(), // >> 操作人
				adminInfo.getCreateOn(),// >> 操作时间
				adminInfo.getProvinceCode(),
				adminInfo.getCityCode(),
				adminInfo.getCityName()
		};
		sqlList.add(sql);
		paramsList.add(params);
		// >>
		
		String userRoleSql = "INSERT INTO sys_userrole (userRoleId,roleId,userId,sysType) VALUES (?,?,?,?)";
		Object[] userRoleParams = new Object[]{
				StringUtil.generateSequenceNo(),// >> 主键
				roleId,// >> 角色ID
				adminInfo.getUserId(),// >> 用户ID
				sysType // >> 系统类型
			};
		sqlList.add(userRoleSql);
		paramsList.add(userRoleParams);
		
		boolean flag = tranOperator(sqlList, paramsList);
		
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(adminInfo.getCreateBy(), "管理员管理", "添加管理员成功");
		}else{
			logObject = getLogObjectArray(adminInfo.getCreateBy(), "管理员管理", "添加管理员失败");
		}
		// >> 记录日志
		insertObject(addUserLogSql, logObject);
		
		return flag;
	}
	
	

	/**
	 * 通过userId查询用户的权限url
	 * @param userId 用户Id
	 * @param sysType 系统类型(0:运营平台 1:调度平台)
	 * @return 返回该用户的权限url集合
	 */
	
	public List<SysPermissionVo> queryAdminPermissions(String userId,int sysType) {
		
		
		StringBuffer buf = new StringBuffer("select p.url,p.powerId,p.code from  sys_role r,sys_userrole ur,sys_roletopower rp,sys_power p where ur.roleId = rp.roleId and rp.powerId = p.powerId ");
		Object[] params = new Object[2];
		int index = 0 ;
		buf.append(" and p.sysType = ? ");
		params[index++] = sysType;
		buf.append(" and ur.userId = ? ");
		params[index++] = userId;
		String sql = buf.toString();
		List<SysPermissionVo> sysPermission =  adminDao.queryAdminPermissions(sql,params);
		
		
		return sysPermission;
	}
	
	/**
	 * 验证登陆名称是否存在
	 * @param loginName 登陆名称
	 * @param sysType 系统类型
	 * @return 存在返回true,失败返回false
	 */
	public boolean loginNameIsRepetition(String loginName, int sysType) {
		String sql = "SELECT COUNT(*) FROM sys_admin WHERE sysType = ? AND loginName = ?";
		Object[] params = new Object[]{sysType,loginName};
		int total = queryCountByCustomSqlAndParams(sql, params);
		if(total > 0 ){
			return true;
		}
		return false;
	}
	
	/**
	 * 查询管理员分页
	 * 
	 */
	public PageBean getPageBean(int parseInt, int pageSize, QueryHelper query) {
		String pageSql = query.getListSql(); // >> 获取分页SQL
		Object[] pageParams = query.getObjectArrayParams(); // >>  对应的参数数组
		List<SysAdminVo> recordList = queryList(pageSql,pageParams); // >> 得到集合
		String countSql = query.getCountSql(); // >>  查询总数的SQL
		int recordCount = queryCountByCustomSqlAndParams(countSql, pageParams); // >>  得到总数
		PageBean pageBean = new PageBean(parseInt, pageSize, recordCount, recordList);
		return pageBean;
	}
	
	/**
	 * 根据用户ID和系统类型查询管理员信息
	 * @param userId
	 * @param sysType 
	 * @return
	 */
	public SysAdminVo queryAdminByUserIdAndSysId(String userId, int sysType) {
		String sql = "SELECT a.passWord, a.userId,a.userName,a.loginName,a.departmentId,a.telephone,a.status,a.sysType,a.remark,a.provinceCode,a.cityCode,a.cityName,a.createBy,a.createOn,a.updateBy,a.updateOn,ur.roleId  " +
				"FROM sys_admin a,sys_userrole ur WHERE a.userId = ur.userId AND a.userId = ? AND a.sysType = ?";
		Object[] params = new Object[]{
				userId, // >> 用户ID
				sysType // >> 系统类型
		}; 
		return queryObject(sql, params);
		//SELECT * FROM sys_admin a,sys_userrole ur WHERE a.userId = ur.userId AND userId = ? AND sysType = ?
	}
	
	/**
	 * 修改管理员信息以及对应的角色关系
	 * @param sysAdminEntity
	 * @param sysType
	 * @param roleId
	 * @return 成功返回true，失败返回false
	 */
	public boolean updateAdminData(SysAdminEntity sysAdminEntity, int sysType,
			String roleId) {
		// >> 设置更新时间
		sysAdminEntity.setUpdateOn(MyDate.getMyDateLong());
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		// >> 修改管理员的信息
		String adminSql = "UPDATE sys_admin SET userName = ?,loginName = ?,telephone = ?,STATUS = ?,remark = ?,updateBy = ?,updateOn = ?,provinceCode=?,cityCode=?,cityName=? WHERE sysType = ? AND userId = ?";
		Object[] adminParams = new Object[]{
				sysAdminEntity.getUserName(), // >> 用户姓名 
				sysAdminEntity.getLoginName(), // >> 登陆名称
				sysAdminEntity.getTelephone(),  // >> 联系电话
				sysAdminEntity.getStatus(), // >> 账号状态
				sysAdminEntity.getRemark(), // >> 备注
				sysAdminEntity.getUpdateBy(), // >> 修改人
				sysAdminEntity.getUpdateOn(), // >>  修改时间
				sysAdminEntity.getProvinceCode(),
				sysAdminEntity.getCityCode(),
				sysAdminEntity.getCityName(),
				sysType,  // >> 系统类型
				sysAdminEntity.getUserId() // >> 用户ID
		};
		sqlList.add(adminSql);
		paramsList.add(adminParams);
		
		// >> 删除管理员与角色的对应关系
		String deleteSql = "DELETE FROM sys_userrole WHERE userId = ? AND sysType = ?";
		Object[] deleteParams = new Object[]{
				sysAdminEntity.getUserId(), // >> 用户ID
				sysType // >> 系统类型
		};
		sqlList.add(deleteSql);
		paramsList.add(deleteParams);
		
		// >> 添加管理员对应的角色
		String userRoleSql = "INSERT INTO sys_userrole (userRoleId,roleId,userId,sysType) VALUES (?,?,?,?)";
		Object[] userRoleParams = new Object[]{
				StringUtil.generateSequenceNo(),// >> 主键
				roleId,// >> 角色ID
				sysAdminEntity.getUserId(),// >> 用户ID
				sysType // >> 系统类型
			};
		sqlList.add(userRoleSql);
		paramsList.add(userRoleParams);
		boolean flag = tranOperator(sqlList, paramsList);
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(sysAdminEntity.getUpdateBy(), "管理员管理", "修改管理员成功");
		}else{
			logObject = getLogObjectArray(sysAdminEntity.getUpdateBy(), "管理员管理", "修改管理员失败");
		}
		// >> 记录日志
		insertObject(addUserLogSql, logObject);
		
		return flag;
	}
	
	/**
	 * 重置用户的密码
	 * @param sysAdminEntity
	 * @param sysType
	 * @return 成功返回true,失败返回False
	 */
	public boolean resetPassword(SysAdminEntity sysAdminEntity, int sysType) {
		sysAdminEntity.setPassWord(Sha1Utils.encrypt(sysAdminEntity.getLoginName()+sysAdminEntity.getPassWord())); // >> 加密(用户名和密码)	
		// >> 设置更新时间
		sysAdminEntity.setUpdateOn(MyDate.getMyDateLong());
		
		String sql = "UPDATE sys_admin SET PASSWORD = ?,updateBy = ?,updateOn = ? WHERE userId = ? AND sysType = ? ";
		Object[] params = new Object[]{
				sysAdminEntity.getPassWord(), // >> 设置密码
				sysAdminEntity.getUpdateBy(), // >> 设置修改人
				sysAdminEntity.getUpdateOn(), // >> 设置修改时间
				sysAdminEntity.getUserId(), // >> 修改的那个管理员的密码
				sysType // >>  系统类型
		};
		boolean flag = updateObject(sql, params);
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(sysAdminEntity.getUpdateBy(), "管理员管理", "重置管理员密码成功");
		}else{
			logObject = getLogObjectArray(sysAdminEntity.getUpdateBy(), "管理员管理", "重置管理员密码失败");
		}
		// >> 记录日志
		insertObject(addUserLogSql, logObject); 
		
		return flag;
	}
	

	/**
	 * 根据登录名和密码，以及系统类型查询管理员
	 * @param loginName
	 * @param password
	 * @param sysType
	 * @return 
	 */
	public SysAdminVo queryAdminByLoginNameAndPassword(String loginName,
			String password, int sysType) {
		
		// >> 查询管理员的信息
		String sql = "SELECT a.userId,a.userName,a.loginName,a.departmentId,a.telephone,a.status,a.remark,a.createBy,a.createOn,a.updateBy,a.updateOn,a.provinceCode,a.cityCode,a.cityName,ur.roleId,r.roleName  " +
					" FROM sys_admin a,sys_userrole ur,sys_role r WHERE a.userId = ur.userId AND ur.roleId = r.roleId AND a.loginName = ? AND a.passWord = ? AND a.sysType = ? AND a.deleteFlag = ?";
		Object[] params = new Object[]{
				loginName,
				EncryptionUtils.encrypt(loginName+password),
				sysType,
				0
		};
		
		SysAdminVo sysAdminVo = queryObject(sql, params);
		// >> 如果该管理员的角色ID不存在，不能登录
		if(null == sysAdminVo || "".equals(sysAdminVo.getRoleId())){
			return null;
		}
		
		// >> 查询管理员的访问权限
		List<SysPermissionVo> sysPermissionVos  = permissionService.queryPermissionInfoByRoleId(sysAdminVo.getRoleId(), sysType);
		
		// >> 设置
		sysAdminVo.setSysPermissions(sysPermissionVos);
		
		return sysAdminVo;
	}
	
	public static void main(String[] args) {
		System.out.println(EncryptionUtils.encrypt("admin"+"123456"));
	}
	
	/**
	 * 删除管理员
	 * @param 管理员ID
	 * @param sysType 系统类型
	 */
	public String deleteAdmin(SysAdminVo admin,String userids, int sysType) {
		
		
		String[] idsA = userids.split(":");
		Object[] selectParams = new Object[idsA.length+2];
		
		StringBuffer buf = new StringBuffer();
		for(int i=1;i<=idsA.length;i++){
			buf.append("?,");
			selectParams[i] = idsA[i-1];
		}
		
		String addSql = buf.substring(0, buf.length()-1);
		
		// >> 
		String selectSql = "UPDATE sys_admin SET deleteFlag = ?  WHERE   userId IN ("+addSql+") AND sysType = ?";
		selectParams[0] = 1; // >> 删除标志
		selectParams[idsA.length+1] = sysType; // >> 系统类型
		
		//boolean flag = updateObject(selectSql, selectParams);//因为要添加日志，改成事务处理
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		sqlList.add(selectSql);
		paramsList.add(selectParams);
		boolean flag  = tranOperator(sqlList, paramsList);
		
		// >> 操作日志
		String addUserLogSql = getLogSql();
		Object[] logObject = null;
		if(flag){
			logObject = getLogObjectArray(admin.getUserId(), "管理员管理", "删除管理员成功");
		}else{
			logObject = getLogObjectArray(admin.getUserId(), "管理员管理", "删除管理员失败");
		}
		// >> 记录日志
		insertObject(addUserLogSql, logObject);
		
		
		if(flag){
			return "ok";
		}else{
			
			return "error";
		}
	}
	
	/**
	 * 修改自己的密码
	 * @param newpassword
	 * @param sysType
	 * @param userId
	 * @return
	 */
	public boolean updateUserPassWord(String newpassword, int sysType,
			 SysAdminVo admin) {
		String sql = "UPDATE sys_admin SET PASSWORD = ? WHERE userId = ? AND sysTYpe = ?";
		Object[] params = new Object[]{
				EncryptionUtils.encrypt(admin.getLoginName()+newpassword),
				admin.getUserId(),
				sysType
		};
		return updateObject(sql, params);
	}



	public boolean initInsertAdminData(SysAdminEntity adminInfo, int sysType,
			String roleId) {
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		// >> 设置主键
		adminInfo.setUserId(StringUtil.generateSequenceNo());
		// >> 设置创建时间
		adminInfo.setCreateOn(MyDate.getMyDateLong());
		adminInfo.setPassWord(Sha1Utils.encrypt(adminInfo.getLoginName()+adminInfo.getPassWord())); // >> 加密(用户名和密码)
		
		String sql = "INSERT INTO sys_admin (userId,userName,loginName,passWord,departmentId,telephone,status,sysType,remark,createBy,createOn)"
			+" VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params = new Object[]{
				adminInfo.getUserId(), // >> 用户ID
				adminInfo.getUserName(), // >> 用户姓名
				adminInfo.getLoginName(), // >> 登陆名称
				adminInfo.getPassWord(), // >> 密码
				adminInfo.getDepartmentId(), // >> 部门ID
				adminInfo.getTelephone(), // >> 电话号码
				adminInfo.getStatus(), // >> 账号状态
				sysType, // >> 系统类型
				adminInfo.getRemark(), //>> 备注
				adminInfo.getCreateBy(), // >> 操作人
				adminInfo.getCreateOn() // >> 操作时间
		};
		sqlList.add(sql);
		paramsList.add(params);
		// >>
		
		String userRoleSql = "INSERT INTO sys_userrole (userRoleId,roleId,userId,sysType) VALUES (?,?,?,?)";
		Object[] userRoleParams = new Object[]{
				StringUtil.generateSequenceNo(),// >> 主键
				roleId,// >> 角色ID
				adminInfo.getUserId(),// >> 用户ID
				sysType // >> 系统类型
			};
		sqlList.add(userRoleSql);
		paramsList.add(userRoleParams);
		
		return tranOperator(sqlList, paramsList);
	}


	/**
	 * 根据管理员登录名和系统类型查询管理员信息
	 * 
	 */
	public SysAdminVo queryAdminByLoginNameAndPassword(String loginName,
			int sysType) {
		// >> 查询管理员的信息
		String sql = "SELECT a.userId,a.userName,a.loginName,a.departmentId,a.telephone,a.status,a.remark,a.createBy,a.createOn,a.updateBy,a.updateOn,a.provinceCode,a.cityCode,a.cityName,ur.roleId,r.roleName  " +
					" FROM sys_admin a,sys_userrole ur,sys_role r WHERE a.userId = ur.userId AND ur.roleId = r.roleId AND a.loginName = ? AND a.sysType = ? AND a.deleteFlag = ?";
		Object[] params = new Object[]{
				loginName,
				sysType,
				0
		};
		
		SysAdminVo sysAdminVo = queryObject(sql, params);
		// >> 如果该管理员的角色ID不存在，不能登录
		if(null == sysAdminVo || "".equals(sysAdminVo.getRoleId())){
			return null;
		}
		
		// >> 查询管理员的访问权限
		List<SysPermissionVo> sysPermissionVos  = permissionService.queryPermissionInfoByRoleId(sysAdminVo.getRoleId(), sysType);
		
		// >> 设置
		sysAdminVo.setSysPermissions(sysPermissionVos);
		
		return sysAdminVo;
	}

}
