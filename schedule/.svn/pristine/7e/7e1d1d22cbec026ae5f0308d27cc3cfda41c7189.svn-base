package com.amwell.listener;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.amwell.commons.InitializeSysAdminData;
import com.amwell.commons.MyDataSource;
import com.amwell.service.IPermissionService;
import com.amwell.service.IRolePermissionService;
import com.amwell.service.InitializationAllDataService;
import com.amwell.vo.InitializationAllData;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;

public class InitializePremissionData implements ServletContextListener {

	/**
	 * 任务数据ID
	 */
	public static final String POWER_DATA = "powerdatatwo";
	
	public void contextInitialized(ServletContextEvent sce) {
		// >> 初始化连接池
		MyDataSource.getConnect();
		//获得容器与相关的Service对象
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		InitializationAllDataService allDataService = (InitializationAllDataService) ac.getBean("allData");
		
		//查询是否需要执行程序
		List<InitializationAllData> allDatas = allDataService.queryAllInitializationData();
		
		for(int i=0;i<allDatas.size();i++){
			InitializationAllData allData = allDatas.get(i);
			// 初始化权限数据
			if(POWER_DATA.equals(allData.getDataId()) && "Y".equalsIgnoreCase(allData.getIsExecute())){
				InitializePowerData(ac);
				// 关闭开关
				allDataService.updateIsExecute("N",POWER_DATA,1);
			}
			
		}
		
	}
	
	
	
	private void InitializePowerData(ApplicationContext ac) {
		
		boolean flag = false;
		//初始化运营平台的权限数据
		IPermissionService ips = (IPermissionService) ac.getBean("permissionService");
		List<SysPermissionEntity> permissionList = InitializeSysAdminData.getSysPermissionData();
		flag = ips.insertPermissionData(permissionList,1);
		if(!flag){
			throw new RuntimeException("初始化运营平台权限数据错误");
		}
		
		flag = false;
		// >> 获取角色Service
		IRolePermissionService irs= (IRolePermissionService) ac.getBean("rolePermissionService");
		
		// >> 查询所有的权限(根据系统类型)
		List<SysPermissionVo>  sysPermissionVo = ips.queryAllSysPermission(1);
		List<String> pid = new ArrayList<String>();
		for(int i=0;i<sysPermissionVo.size();i++){
			pid.add(sysPermissionVo.get(i).getPowerId());
		}
		flag = irs.insertRolePermissionData(InitializeSysAdminData.getSysRole(), pid,1);
		
		System.out.println("初始化数据成功");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}
	

}
