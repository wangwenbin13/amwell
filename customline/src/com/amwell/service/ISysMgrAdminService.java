package com.amwell.service;

import com.amwell.vo.SysMgrAdminEntity;

public interface ISysMgrAdminService {
	
	
	
	/**
	 * 运营平台商户添加，同步到调度平台的管理员信息
	 * 需要的信息：联系人的名称:userName,登陆名:loginName,密码:passWord,
	 * 联系电话:telephone,账号状态:status,备注:remark,创举人:createBy,
	 * 商户ID:businessId
	 * @param  sysMgrAdmin 商户管理员的实体对象
	 * 
	 * @return true插入成功，false 插入失败
	 */
	boolean insertIntoScheduleAdminInfo(SysMgrAdminEntity sysMgrAdmin);
	
	
	/**
	 * 更新
	 * 需要更新的信息：
	 * 密码:passWord，账号状态:status，联系电话:telephone，联系人名称:userName，备注:remark，修改人:updateBy，
	 * @param  sysMgrAdmin 商户管理员的实体对象
	 * 
	 * @return true插入成功，false 插入失败
	 */
	boolean updateScheduleAdminInfo(SysMgrAdminEntity sysMgrAdmin);
	
	/**
	 * 重置商户的密码
	 * 需要设置:创建人:createBy,商户ID：businessId,密码:passWord
	 */
	boolean resertMgrPassword(SysMgrAdminEntity sysMgrAdmin,String userId);
	
	/**
	 * 禁用或启用商户账号
	 * 需要的参数:商户ID:businessId，状态:status
	 */
	boolean changeMgrAccountStatus(SysMgrAdminEntity sysMgrAdmin);
}
