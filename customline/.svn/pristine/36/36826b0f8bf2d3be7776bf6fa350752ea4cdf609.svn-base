package com.amwell.commons;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.amwell.vo.SysAdminVo;

/**
 * 踢掉在线用户
 * @author 胡双
 *
 */
public class KickOutLoginUser {
	
	@SuppressWarnings("unchecked")
	public static void kickOutByRoleId(ServletContext context,String roleId){
		Map<SysAdminVo,HttpSession> map = (Map<SysAdminVo, HttpSession>) context.getAttribute("usermap");
		
		for(Map.Entry<SysAdminVo,HttpSession> entry:map.entrySet()){
			SysAdminVo admin = entry.getKey();
			if(roleId.equals(admin.getRoleId())){
				HttpSession session = entry.getValue();
				session.invalidate();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void kickOutByUserId(ServletContext context,String userId){
		Map<SysAdminVo,HttpSession> map = (Map<SysAdminVo, HttpSession>) context.getAttribute("usermap");
		for(Map.Entry<SysAdminVo,HttpSession> entry:map.entrySet()){
			SysAdminVo admin = entry.getKey();
			if(userId.equals(admin.getUserId())){
				HttpSession session = entry.getValue();
				session.invalidate();
			}
		}
	}
	
}
