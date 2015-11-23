package com.amwell.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.amwell.commons.ApplicationContextHolder;
import com.amwell.commons.InitializeSysAdminData;
import com.amwell.commons.MyDataSource;
import com.amwell.service.IPermissionService;
import com.amwell.service.IRolePermissionService;
import com.amwell.service.InitializationAllDataService;
import com.amwell.vo.InitializationAllData;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;

public class InitializePremissionData implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(InitializePremissionData.class);

	/**
	 * 任务数据ID
	 */
	public static final String POWER_DATA = "powerdataone";
	public static final String DATA_HUIFU = "datahuifu";
	
	public void contextInitialized(ServletContextEvent sce) {
		//初始化连接池
		MyDataSource.getConnect();
		//获得容器与相关的Service对象
		InitializationAllDataService allDataService = ApplicationContextHolder.getBean(InitializationAllDataService.class);
		//查询是否需要执行程序
		List<InitializationAllData> allDatas = allDataService.queryAllInitializationData();
		for(int i=0;i<allDatas.size();i++){
			InitializationAllData allData = allDatas.get(i);
			// 初始化权限数据
			if(POWER_DATA.equals(allData.getDataId()) && "Y".equalsIgnoreCase(allData.getIsExecute())){
				InitializePowerData();
				// 关闭开关
				allDataService.updateIsExecute("N",POWER_DATA,0);
			}
		}
	}

	/**初始化权限数据*/
	public void InitializePowerData(){
		IPermissionService ips = ApplicationContextHolder.getBean(IPermissionService.class);
		List<SysPermissionEntity> permissionList = InitializeSysAdminData.getSysPermissionData();
		boolean flag = ips.initPermissionData(permissionList,0);
		if(!flag){
			throw new RuntimeException("初始化运营平台权限数据错误");
		}
		logger.info("初始化运营平台权限数据成功");
		//查询所有的权限
		List<SysPermissionVo>  sysPermissionVo = ips.queryAllSysPermission(0);
		List<String> pid = new ArrayList<String>();
		for(int i=0;i<sysPermissionVo.size();i++){
			pid.add(sysPermissionVo.get(i).getPowerId());
		}
		IRolePermissionService irs = ApplicationContextHolder.getBean(IRolePermissionService.class);
		flag = irs.initInsertRolePermissionData(InitializeSysAdminData.getSysRole(), pid,0);
		if(!flag){
			throw new RuntimeException("初始化运营平台角色信息错误");
		}
		logger.info("初始化数据成功");
	}
	
	public void contextDestroyed(ServletContextEvent sce) {}
}
