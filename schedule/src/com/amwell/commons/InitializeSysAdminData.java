package com.amwell.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.amwell.service.IPermissionService;
import com.amwell.service.IRolePermissionService;
import com.amwell.service.ISysAdminService;
import com.amwell.vo.SysMgrAdminEntity;
import com.amwell.vo.SysMgrAdminVo;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;
import com.amwell.vo.SysRoleEntity;
import com.amwell.vo.SysRoleVo;

/**
 * 初始化管理员数据
 * @author Administrator
 *
 */
public class InitializeSysAdminData {
	private static ApplicationContext ac;
	
	public static synchronized ApplicationContext getApplicationContext(String path){
		 if(null == ac){
			ac = new FileSystemXmlApplicationContext(path);
		}
		return ac;
	}
	
	public static void main(String[] args) {
		boolean falg = false;
		ApplicationContext ac = getApplicationContext("classpath:applicationContext.xml");
		//初始化运营平台的权限数据
		IPermissionService ips = (IPermissionService) ac.getBean("permissionService");
		List<SysPermissionEntity> permissionList = getSysPermissionData();
		falg = ips.insertPermissionData(permissionList,1);
		if(!falg){
			throw new RuntimeException("初始化运营平台权限数据错误");
		}
		
		// >> 获取角色Service
		IRolePermissionService irs= (IRolePermissionService) ac.getBean("rolePermissionService");
		
		// >> 查询所有的权限(根据系统类型)
		List<SysPermissionVo>  sysPermissionVo = ips.queryAllSysPermission(1);
		List<String> pid = new ArrayList<String>();
		for(int i=0;i<sysPermissionVo.size();i++){
			pid.add(sysPermissionVo.get(i).getPowerId());
		}
		falg = irs.insertRolePermissionData(getSysRole(), pid,1);
		
		System.out.println("初始化数据成功");
	}
	
	
	/**
	 * 获得给定权限的ID
	 * @param sysPermissionList
	 * @return
	 */
	private static List<String> getSysPermissionIds(
			List<SysPermissionVo> sysPermissionList) {
		List<String> ids = new ArrayList<String>();
		
		for(int i=0;i<sysPermissionList.size();i++){
			ids.add(sysPermissionList.get(i).getPowerId());
		}
		
		return ids;
	}

	/**
	 * 定义默认角色数据
	 */
	public static SysRoleEntity getSysRole(){
		SysRoleEntity role = new SysRoleEntity();
		role.setRoleName("默认角色");
		role.setRemark("");
		role.setSysType(1);
		role.setCreateBy("系统自动生成");
		return role;
	}
	
	/**
	 * 定义权限数据()
	 */
	public static  List<SysPermissionEntity> getSysPermissionData(){
		int oneLevel = 1;
		
		
		List<SysPermissionEntity> permissionList = new ArrayList<SysPermissionEntity>();
		
		//线路管理=======================开始=============================
		SysPermissionEntity permissionline = new SysPermissionEntity();
		permissionline.setCode("line");
		permissionline.setName("线路管理");
		permissionline.setFid("-1");
		permissionline.setUrl("");
		permissionline.setIconUrl("leftIco lineIco");
		permissionline.setCreateBy("系统生成");
		permissionline.setCreateOn(MyDate.getMyDateLong());
		permissionline.setUpdateBy("");
		permissionline.setUpdateOn("");
		permissionline.setSysType(1);
		permissionline.setSortFlag(oneLevel++);
		permissionList.add(permissionline);
		//
		SysPermissionEntity permissionlineone1 = new SysPermissionEntity();
		permissionlineone1.setCode("lineone1");
		permissionlineone1.setName("线路列表");
		permissionlineone1.setFid("line");
		permissionlineone1.setUrl("../line/getAllLineList");
		permissionlineone1.setIconUrl("");
		permissionlineone1.setCreateBy("系统生成");
		permissionlineone1.setCreateOn(MyDate.getMyDateLong());
		permissionlineone1.setUpdateBy("");
		permissionlineone1.setUpdateOn("");
		permissionlineone1.setSysType(1);
		permissionlineone1.setSortFlag(11);
		permissionList.add(permissionlineone1);
		//线路管理=======================结束=============================
		
		
		//包车管理=======================开始=============================
		
		
		
	
		SysPermissionEntity permissionbaoche = new SysPermissionEntity();
		permissionbaoche.setCode("charteredcarmanager");
		permissionbaoche.setName("包车管理");
		permissionbaoche.setFid("-1");
		permissionbaoche.setUrl("");
		permissionbaoche.setIconUrl("leftIco bclineIco");
		permissionbaoche.setCreateBy("系统生成");
		permissionbaoche.setCreateOn(MyDate.getMyDateLong());
		permissionbaoche.setUpdateBy("");
		permissionbaoche.setUpdateOn("");
		permissionbaoche.setSortFlag(oneLevel++);
		permissionbaoche.setSysType(1);
		permissionList.add(permissionbaoche);
			// 包车线路列表
			SysPermissionEntity permissionbaocheone1 = new SysPermissionEntity();
			permissionbaocheone1.setCode("bclinelist");
			permissionbaocheone1.setName("包车线路列表");
			permissionbaocheone1.setFid("charteredcarmanager");
			permissionbaocheone1.setUrl("../businessBidding/getBCAllLineList");
			permissionbaocheone1.setIconUrl("leftIco lineIco");
			permissionbaocheone1.setCreateBy("系统生成");
			permissionbaocheone1.setCreateOn(MyDate.getMyDateLong());
			permissionbaocheone1.setUpdateBy("");
			permissionbaocheone1.setUpdateOn("");
			permissionbaocheone1.setSortFlag(1);
			permissionbaocheone1.setSysType(1);
			permissionList.add(permissionbaocheone1);
			
			// 待报价列表
			SysPermissionEntity permissionbaocheone2 = new SysPermissionEntity();
			permissionbaocheone2.setCode("waitquotelist");
			permissionbaocheone2.setName("待报价列表");
			permissionbaocheone2.setFid("charteredcarmanager");
			permissionbaocheone2.setUrl("../businessBidding/getWaitQuoteList");
			permissionbaocheone2.setIconUrl("leftIco lineIco");
			permissionbaocheone2.setCreateBy("系统生成");
			permissionbaocheone2.setCreateOn(MyDate.getMyDateLong());
			permissionbaocheone2.setUpdateBy("");
			permissionbaocheone2.setUpdateOn("");
			permissionbaocheone2.setSortFlag(2);
			permissionbaocheone2.setSysType(1);
			permissionList.add(permissionbaocheone2);
			
			//	已报价列表
			SysPermissionEntity permissionbaocheone3 = new SysPermissionEntity();
			permissionbaocheone3.setCode("alreadyquotelist");
			permissionbaocheone3.setName("已报价列表");
			permissionbaocheone3.setFid("charteredcarmanager");
			permissionbaocheone3.setUrl("../businessBidding/getAlreadyQuoteList");
			permissionbaocheone3.setIconUrl("leftIco lineIco");
			permissionbaocheone3.setCreateBy("系统生成");
			permissionbaocheone3.setCreateOn(MyDate.getMyDateLong());
			permissionbaocheone3.setUpdateBy("");
			permissionbaocheone3.setUpdateOn("");
			permissionbaocheone3.setSortFlag(3);
			permissionbaocheone3.setSysType(1);
			permissionList.add(permissionbaocheone3);
		
		
		//包车管理=======================结束=============================
		
		
		
		
		//支付结算=======================开始=============================
		SysPermissionEntity permissionuser = new SysPermissionEntity();
		permissionuser.setCode("pay");
		permissionuser.setName("支付结算");
		permissionuser.setFid("-1");
		permissionuser.setUrl("");
		permissionuser.setIconUrl("leftIco payIco");
		permissionuser.setCreateBy("系统生成");
		permissionuser.setCreateOn(MyDate.getMyDateLong());
		permissionuser.setUpdateBy("");
		permissionuser.setUpdateOn("");
		permissionuser.setSortFlag(oneLevel++);
		permissionuser.setSysType(1);
		permissionList.add(permissionuser);
		//
		SysPermissionEntity permissionuserone1 = new SysPermissionEntity();
		permissionuserone1.setCode("payone1");
		permissionuserone1.setName("日收入统计");
		permissionuserone1.setFid("pay");
		permissionuserone1.setUrl("../statDayIncomeAction/getDayIncome");
		permissionuserone1.setIconUrl("");
		permissionuserone1.setCreateBy("系统生成");
		permissionuserone1.setCreateOn(MyDate.getMyDateLong());
		permissionuserone1.setUpdateBy("");
		permissionuserone1.setUpdateOn("");
		permissionuserone1.setSortFlag(21);
		permissionuserone1.setSysType(1);
		permissionList.add(permissionuserone1);
		//
		SysPermissionEntity permissionuserone2 = new SysPermissionEntity();
		permissionuserone2.setCode("payone2");
		permissionuserone2.setName("月收入统计");
		permissionuserone2.setFid("pay");
		permissionuserone2.setUrl("../statMonthIncomeAction/getMonthIncome");
		permissionuserone2.setIconUrl("");
		permissionuserone2.setCreateBy("系统生成");
		permissionuserone2.setCreateOn(MyDate.getMyDateLong());
		permissionuserone2.setUpdateBy("");
		permissionuserone2.setUpdateOn("");
		permissionuserone2.setSortFlag(22);
		permissionuserone2.setSysType(1);
		permissionList.add(permissionuserone2);
		//
		SysPermissionEntity permissionuserone3 = new SysPermissionEntity();
		permissionuserone3.setCode("payone2");
		permissionuserone3.setName("支出统计");
		permissionuserone3.setFid("pay");
		permissionuserone3.setUrl("../statOutAction/getStatOutList");
		permissionuserone3.setIconUrl("");
		permissionuserone3.setCreateBy("系统生成");
		permissionuserone3.setCreateOn(MyDate.getMyDateLong());
		permissionuserone3.setUpdateBy("");
		permissionuserone3.setUpdateOn("");
		permissionuserone3.setSortFlag(23);
		permissionuserone3.setSysType(1);
		permissionList.add(permissionuserone3);
		//支付结算=======================结束=============================
		
		//司机管理=======================开始=============================
		SysPermissionEntity permissionorder = new SysPermissionEntity();
		permissionorder.setCode("dirver");
		permissionorder.setName("司机管理");
		permissionorder.setFid("-1");
		permissionorder.setUrl("");
		permissionorder.setIconUrl("leftIco dirverIco");
		permissionorder.setCreateBy("系统生成");
		permissionorder.setCreateOn(MyDate.getMyDateLong());
		permissionorder.setUpdateBy("");
		permissionorder.setUpdateOn("");
		permissionorder.setSortFlag(oneLevel++);
		permissionorder.setSysType(1);
		permissionList.add(permissionorder);
		//
		SysPermissionEntity permissionorderone1 = new SysPermissionEntity();
		permissionorderone1.setCode("dirverone1");
		permissionorderone1.setName("司机列表");
		permissionorderone1.setFid("dirver");
		permissionorderone1.setUrl("../dispatchDriver/driverInfoList");
		permissionorderone1.setIconUrl("");
		permissionorderone1.setCreateBy("系统生成");
		permissionorderone1.setCreateOn(MyDate.getMyDateLong());
		permissionorderone1.setUpdateBy("");
		permissionorderone1.setUpdateOn("");
		permissionorderone1.setSortFlag(31);
		permissionorderone1.setSysType(1);
		permissionList.add(permissionorderone1);
		
		SysPermissionEntity permissionorderone2 = new SysPermissionEntity();
		permissionorderone2.setCode("dirverone2");
		permissionorderone2.setName("添加司机");
		permissionorderone2.setFid("dirver");
		permissionorderone2.setUrl("../dispatchDriver/toDriverEditPage");
		permissionorderone2.setIconUrl("");
		permissionorderone2.setCreateBy("系统生成");
		permissionorderone2.setCreateOn(MyDate.getMyDateLong());
		permissionorderone2.setUpdateBy("");
		permissionorderone2.setUpdateOn("");
		permissionorderone2.setSortFlag(32);
		permissionorderone2.setSysType(1);
		permissionList.add(permissionorderone2);
		
		//司机管理=======================结束=============================
		
		//车辆管理=======================开始=============================
		SysPermissionEntity permissionmarketing = new SysPermissionEntity();
		permissionmarketing.setCode("car");
		permissionmarketing.setName("车辆管理");
		permissionmarketing.setFid("-1");
		permissionmarketing.setUrl("");
		permissionmarketing.setIconUrl("leftIco carIco");
		permissionmarketing.setCreateBy("系统生成");
		permissionmarketing.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing.setUpdateBy("");
		permissionmarketing.setUpdateOn("");
		permissionmarketing.setSortFlag(oneLevel++);
		permissionmarketing.setSysType(1);
		permissionList.add(permissionmarketing);
		//
		SysPermissionEntity permissionmarketingone1 = new SysPermissionEntity();
		permissionmarketingone1.setCode("carone1");
		permissionmarketingone1.setName("车辆列表");
		permissionmarketingone1.setFid("car");
		permissionmarketingone1.setUrl("../dispatchVehicle/vehicleInfoList");
		permissionmarketingone1.setIconUrl("");
		permissionmarketingone1.setCreateBy("系统生成");
		permissionmarketingone1.setCreateOn(MyDate.getMyDateLong());
		permissionmarketingone1.setUpdateBy("");
		permissionmarketingone1.setUpdateOn("");
		permissionmarketingone1.setSortFlag(41);
		permissionmarketingone1.setSysType(1);
		permissionList.add(permissionmarketingone1);
		//
		SysPermissionEntity permissionmarketingone2 = new SysPermissionEntity();
		permissionmarketingone2.setCode("carone2");
		permissionmarketingone2.setName("添加车辆");
		permissionmarketingone2.setFid("car");
		permissionmarketingone2.setUrl("../dispatchVehicle/toVehicleEditPage");
		permissionmarketingone2.setIconUrl("");
		permissionmarketingone2.setCreateBy("系统生成");
		permissionmarketingone2.setCreateOn(MyDate.getMyDateLong());
		permissionmarketingone2.setUpdateBy("");
		permissionmarketingone2.setUpdateOn("");
		permissionmarketingone2.setSortFlag(42);
		permissionmarketingone2.setSysType(1);
		permissionList.add(permissionmarketingone2);
		//营销管理=======================结束=============================
		
		//系统管理=======================开始=============================
		SysPermissionEntity permissionsys = new SysPermissionEntity();
		permissionsys.setCode("sys");
		permissionsys.setName("系统设置");
		permissionsys.setFid("-1");
		permissionsys.setUrl("");
		permissionsys.setIconUrl("leftIco setIco");
		permissionsys.setCreateBy("系统生成");
		permissionsys.setCreateOn(MyDate.getMyDateLong());
		permissionsys.setUpdateBy("");
		permissionsys.setUpdateOn("");
		permissionsys.setSortFlag(oneLevel++);
		permissionsys.setSysType(1);
		permissionList.add(permissionsys);
		//
		SysPermissionEntity permissionsysone1 = new SysPermissionEntity();
		permissionsysone1.setCode("sysone1");
		permissionsysone1.setName("个人信息");
		permissionsysone1.setFid("sys");
		permissionsysone1.setUrl("../personalInfo/show");
		permissionsysone1.setIconUrl("");
		permissionsysone1.setCreateBy("系统生成");
		permissionsysone1.setCreateOn(MyDate.getMyDateLong());
		permissionsysone1.setUpdateBy("");
		permissionsysone1.setUpdateOn("");
		permissionsysone1.setSortFlag(61);
		permissionsysone1.setSysType(1);
		permissionList.add(permissionsysone1);
		//
		SysPermissionEntity permissionsysone2 = new SysPermissionEntity();
		permissionsysone2.setCode("sysone2");
		permissionsysone2.setName("修改密码");
		permissionsysone2.setFid("sys");
		permissionsysone2.setUrl("../changePassWord/toChangePassWord");
		permissionsysone2.setIconUrl("");
		permissionsysone2.setCreateBy("系统生成");
		permissionsysone2.setCreateOn(MyDate.getMyDateLong());
		permissionsysone2.setUpdateBy("");
		permissionsysone2.setUpdateOn("");
		permissionsysone2.setSortFlag(62);
		permissionsysone2.setSysType(1);
		permissionList.add(permissionsysone2);
		//
		SysPermissionEntity permissionsysone3 = new SysPermissionEntity();
		permissionsysone3.setCode("sysone3");
		permissionsysone3.setName("操作日志");
		permissionsysone3.setFid("sys");
		permissionsysone3.setUrl("../logList/toLogList");
		permissionsysone3.setIconUrl("");
		permissionsysone3.setCreateBy("系统生成");
		permissionsysone3.setCreateOn(MyDate.getMyDateLong());
		permissionsysone3.setUpdateBy("");
		permissionsysone3.setUpdateOn("");
		permissionsysone3.setSortFlag(63);
		permissionsysone3.setSysType(1);
		permissionList.add(permissionsysone3);
		//
		//系统管理=======================结束=============================
		
		
		
		return permissionList;
	}
}











