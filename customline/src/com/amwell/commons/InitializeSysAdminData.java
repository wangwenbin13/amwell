package com.amwell.commons;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.amwell.service.IPermissionService;
import com.amwell.service.IRolePermissionService;
import com.amwell.vo.SysAdminEntity;
import com.amwell.vo.SysPermissionEntity;
import com.amwell.vo.SysPermissionVo;
import com.amwell.vo.SysRoleEntity;

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
		
		falg = ips.initPermissionData(permissionList,0);
		if(!falg){
			throw new RuntimeException("初始化运营平台权限数据错误");
		}
		System.out.println("初始化运营平台权限数据成功");
		
		//初始化角色信息
		IRolePermissionService irs= (IRolePermissionService) ac.getBean("rolePermissionService");
		
		// >> 查询所有的权限
		List<SysPermissionVo>  sysPermissionVo = ips.queryAllSysPermission(0);
		List<String> pid = new ArrayList<String>();
		for(int i=0;i<sysPermissionVo.size();i++){
			pid.add(sysPermissionVo.get(i).getPowerId());
		}
		falg = irs.initInsertRolePermissionData(getSysRole(), pid,0);
		if(!falg){
			throw new RuntimeException("初始化运营平台角色信息错误");
		}
		
		/**
		//初始化管理员信息
		ISysAdminService ias = (ISysAdminService) ac.getBean("sysAdminService");
		List<SysRoleVo> sysRoleVo = irs.queryAllSysRole(0);
		falg = ias.initInsertAdminData(getSysAdminInfo(), 0, sysRoleVo.get(0).getRoleId());
		if(!falg){
			throw new RuntimeException("初始化运营平台管理员信息错误");
		}
		**/
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
		role.setRoleName("超级管理员");
		role.setRemark("");
		role.setSysType(0);
		role.setCreateBy("系统自动生成");
		return role;
	}
	
	/**
	 * 定义系统管理员数据
	 */
	public static SysAdminEntity getSysAdminInfo(){
		
		SysAdminEntity sysadmin = new SysAdminEntity();
		sysadmin.setUserName("admin");
		sysadmin.setLoginName("admin");
		sysadmin.setPassWord("123456");
		sysadmin.setDepartmentId("");
		sysadmin.setTelephone("");
		sysadmin.setStatus(1);
		sysadmin.setSysType(0);
		sysadmin.setRemark("");
		sysadmin.setCreateBy("系统自动生成");
		sysadmin.setCreateOn(MyDate.getMyDateLong());
		sysadmin.setUpdateBy("");
		sysadmin.setUpdateOn("");
		
		return sysadmin;
	}

	
	
	/**
	 * 定义权限数据()
	 */
	public static  List<SysPermissionEntity> getSysPermissionData(){
		
//		int oneLevel = 1;
		List<SysPermissionEntity> permissionList = new ArrayList<SysPermissionEntity>();
		
		//线路管理=======================开始=============================
		SysPermissionEntity permissionline = new SysPermissionEntity();
		permissionline.setCode("line");
		permissionline.setName("线路管理");
		permissionline.setFid("-1");
		permissionline.setUrl("");
		permissionline.setIconUrl("arrow_slid fr");
		permissionline.setCreateBy("系统生成");
		permissionline.setCreateOn(MyDate.getMyDateLong());
		permissionline.setUpdateBy("");
		permissionline.setUpdateOn("");
		permissionline.setSysType(0);
		permissionline.setSortFlag(1);
		permissionList.add(permissionline);
		//
		SysPermissionEntity permissionline1 = new SysPermissionEntity();
		permissionline1.setCode("line1");
		permissionline1.setName("发布线路");
		permissionline1.setFid("line");
		permissionline1.setUrl("../publishLine/loadLineBaseInfo");
		permissionline1.setIconUrl("leftico leftico2 fl");
		permissionline1.setCreateBy("系统生成");
		permissionline1.setCreateOn(MyDate.getMyDateLong());
		permissionline1.setUpdateBy("");
		permissionline1.setUpdateOn("");
		permissionline1.setSortFlag(2);
		permissionList.add(permissionline1);
		//
		SysPermissionEntity permissionline2 = new SysPermissionEntity();
		permissionline2.setCode("line2");
		permissionline2.setName("线路列表");
		permissionline2.setFid("line");
		permissionline2.setUrl("../line/getLinesList");
		permissionline2.setIconUrl("leftico leftico21 fl");
		permissionline2.setCreateBy("系统生成");
		permissionline2.setCreateOn(MyDate.getMyDateLong());
		permissionline2.setUpdateBy("");
		permissionline2.setUpdateOn("");
		permissionline2.setSortFlag(1);
		permissionList.add(permissionline2);
		
				//线路列表的三级菜单=======================开始=============================
				SysPermissionEntity permissionline21 = new SysPermissionEntity();
				permissionline21.setCode("line21");
				permissionline21.setName("我的定制线路");
				permissionline21.setFid("line2");
				permissionline21.setUrl("../line/getPublishedLines");
				permissionline21.setIconUrl("none");
				permissionline21.setCreateBy("系统生成");
				permissionline21.setCreateOn(MyDate.getMyDateLong());
				permissionline21.setUpdateBy("");
				permissionline21.setUpdateOn("");
				permissionline21.setSortFlag(1);
				permissionList.add(permissionline21);
				
				
				SysPermissionEntity permissionline22 = new SysPermissionEntity();
				permissionline22.setCode("line22");
				permissionline22.setName("所有定制线路");
				permissionline22.setFid("line2");
				permissionline22.setUrl("../line/getAllLines");
				permissionline22.setIconUrl("none");
				permissionline22.setCreateBy("系统生成");
				permissionline22.setCreateOn(MyDate.getMyDateLong());
				permissionline22.setUpdateBy("");
				permissionline22.setUpdateOn("");
				permissionline22.setSortFlag(2);
				permissionList.add(permissionline22);
				
				//线路列表的三级菜单=======================结束=============================
		//
		SysPermissionEntity permissionline3 = new SysPermissionEntity();
		permissionline3.setCode("line3");
		permissionline3.setName("定制记录");
		permissionline3.setFid("line");
		permissionline3.setUrl("../newApplicationReport/toPage");
		permissionline3.setIconUrl("leftico leftico4 fl");
		permissionline3.setCreateBy("系统生成");
		permissionline3.setCreateOn(MyDate.getMyDateLong());
		permissionline3.setUpdateBy("");
		permissionline3.setUpdateOn("");
		permissionline3.setSortFlag(4);
		permissionList.add(permissionline3);
		
				//招募线路的三级菜单=======================开始=============================
				SysPermissionEntity permissionline31 = new SysPermissionEntity();
				permissionline31.setCode("line31");
				permissionline31.setName("定制记录");
				permissionline31.setFid("line3");
				permissionline31.setUrl("../newApplicationReport/search");
				permissionline31.setIconUrl("leftico leftico4 fl");
				permissionline31.setCreateBy("系统生成");
				permissionline31.setCreateOn(MyDate.getMyDateLong());
				permissionline31.setUpdateBy("");
				permissionline31.setUpdateOn("");
				permissionline31.setSortFlag(1);
				permissionList.add(permissionline31);
				//
				//招募线路的三级菜单=======================结束=============================
		
	    
		SysPermissionEntity permissionline4 = new SysPermissionEntity();
		permissionline4.setCode("line4");
		permissionline4.setName("客户专线");
		permissionline4.setFid("line");
		permissionline4.setUrl("../specialLineLine/getCompanyLineList");
		permissionline4.setIconUrl("leftico leftico15 fl");
		permissionline4.setCreateBy("系统生成");
		permissionline4.setCreateOn(MyDate.getMyDateLong());
		permissionline4.setUpdateBy("");
		permissionline4.setUpdateOn("");
		permissionline4.setSortFlag(3);
		permissionList.add(permissionline4);
				
		//线路管理=======================结束=============================
		
				
		// 包车管理=======================开始=============================
		
		SysPermissionEntity permissionbaoche = new SysPermissionEntity();
		permissionbaoche.setCode("baoche");
		permissionbaoche.setName("包车管理");
		permissionbaoche.setFid("-1");
		permissionbaoche.setUrl("");
		permissionbaoche.setIconUrl("arrow_slid fr");
		permissionbaoche.setCreateBy("系统生成");
		permissionbaoche.setCreateOn(MyDate.getMyDateLong());
		permissionbaoche.setUpdateBy("");
		permissionbaoche.setUpdateOn("");
		permissionbaoche.setSortFlag(9);
		permissionList.add(permissionbaoche);
			//包车列表
			SysPermissionEntity permissionbaoche1 = new SysPermissionEntity();
			permissionbaoche1.setCode("baoche1");
			permissionbaoche1.setName("包车列表");
			permissionbaoche1.setFid("baoche");
			permissionbaoche1.setUrl("../charteredLine/getCharteredLineList");
			permissionbaoche1.setIconUrl("leftico leftico4 fl");
			permissionbaoche1.setCreateBy("系统生成");
			permissionbaoche1.setCreateOn(MyDate.getMyDateLong());
			permissionbaoche1.setUpdateBy("");
			permissionbaoche1.setUpdateOn("");
			permissionbaoche1.setSortFlag(1);
			permissionList.add(permissionbaoche1);
			//包车订单
			SysPermissionEntity permissionbaoche2 = new SysPermissionEntity();
			permissionbaoche2.setCode("baoche2");
			permissionbaoche2.setName("包车订单");
			permissionbaoche2.setFid("baoche");
			permissionbaoche2.setUrl("../charteredOrder/charteredOrderList");
			permissionbaoche2.setIconUrl("leftico leftico4 fl");
			permissionbaoche2.setCreateBy("系统生成");
			permissionbaoche2.setCreateOn(MyDate.getMyDateLong());
			permissionbaoche2.setUpdateBy("");
			permissionbaoche2.setUpdateOn("");
			permissionbaoche2.setSortFlag(2);
			permissionList.add(permissionbaoche2);
			//财务统计
			SysPermissionEntity permissionbaoche3 = new SysPermissionEntity();
			permissionbaoche3.setCode("baoche3");
			permissionbaoche3.setName("财务统计");
			permissionbaoche3.setFid("baoche");
			permissionbaoche3.setUrl("../charteredOrder/forwardFinancialPage");
			permissionbaoche3.setIconUrl("leftico leftico4 fl");
			permissionbaoche3.setCreateBy("系统生成");
			permissionbaoche3.setCreateOn(MyDate.getMyDateLong());
			permissionbaoche3.setUpdateBy("");
			permissionbaoche3.setUpdateOn("");
			permissionbaoche3.setSortFlag(3);
			permissionList.add(permissionbaoche3);
				// 日收入统计
				SysPermissionEntity permissionbaoche31 = new SysPermissionEntity();
				permissionbaoche31.setCode("baoche31");
				permissionbaoche31.setName("日收入统计");
				permissionbaoche31.setFid("baoche3");
				permissionbaoche31.setUrl("../charteredOrder/getDayIncomeList");
				permissionbaoche31.setIconUrl("leftico leftico4 fl");
				permissionbaoche31.setCreateBy("系统生成");
				permissionbaoche31.setCreateOn(MyDate.getMyDateLong());
				permissionbaoche31.setUpdateBy("");
				permissionbaoche31.setUpdateOn("");
				permissionbaoche31.setSortFlag(1);
				permissionList.add(permissionbaoche31);
				// 日支出统计
				SysPermissionEntity permissionbaoche32 = new SysPermissionEntity();
				permissionbaoche32.setCode("baoche32");
				permissionbaoche32.setName("日支出统计");
				permissionbaoche32.setFid("baoche3");
				permissionbaoche32.setUrl("../charteredOrder/getDayExpendList");
				permissionbaoche32.setIconUrl("leftico leftico4 fl");
				permissionbaoche32.setCreateBy("系统生成");
				permissionbaoche32.setCreateOn(MyDate.getMyDateLong());
				permissionbaoche32.setUpdateBy("");
				permissionbaoche32.setUpdateOn("");
				permissionbaoche32.setSortFlag(2);
				permissionList.add(permissionbaoche32);
				// 月财务统计
				SysPermissionEntity permissionbaoche33 = new SysPermissionEntity();
				permissionbaoche33.setCode("baoche33");
				permissionbaoche33.setName("月财务统计");
				permissionbaoche33.setFid("baoche3");
				permissionbaoche33.setUrl("../bcMonthIncomeAction/getMonthIncomeList");
				permissionbaoche33.setIconUrl("leftico leftico4 fl");
				permissionbaoche33.setCreateBy("系统生成");
				permissionbaoche33.setCreateOn(MyDate.getMyDateLong());
				permissionbaoche33.setUpdateBy("");
				permissionbaoche33.setUpdateOn("");
				permissionbaoche33.setSortFlag(3);
				permissionList.add(permissionbaoche33);
				// 包车优惠券统计
				SysPermissionEntity permissionbaoche34 = new SysPermissionEntity();
				permissionbaoche34.setCode("baoche34");
				permissionbaoche34.setName("包车优惠券统计");
				permissionbaoche34.setFid("baoche3");
				permissionbaoche34.setUrl("../bcCouponStatAction/bcCouponStatList");
				permissionbaoche34.setIconUrl("leftico leftico4 fl");
				permissionbaoche34.setCreateBy("系统生成");
				permissionbaoche34.setCreateOn(MyDate.getMyDateLong());
				permissionbaoche34.setUpdateBy("");
				permissionbaoche34.setUpdateOn("");
				permissionbaoche34.setSortFlag(4);
				permissionList.add(permissionbaoche34);

			//退票管理
			SysPermissionEntity permissionbaoche4 = new SysPermissionEntity();
			permissionbaoche4.setCode("baoche4");
			permissionbaoche4.setName("退票管理");
			permissionbaoche4.setFid("baoche");
			permissionbaoche4.setUrl("../bcReturnTicket/forwardBcReturnPage");
			permissionbaoche4.setIconUrl("leftico leftico4 fl");
			permissionbaoche4.setCreateBy("系统生成");
			permissionbaoche4.setCreateOn(MyDate.getMyDateLong());
			permissionbaoche4.setUpdateBy("");
			permissionbaoche4.setUpdateOn("");
			permissionbaoche4.setSortFlag(4);
			permissionList.add(permissionbaoche4);
				// 包车订单列表
				SysPermissionEntity permissionbaoche41 = new SysPermissionEntity();
				permissionbaoche41.setCode("baoche41");
				permissionbaoche41.setName("包车订单列表");
				permissionbaoche41.setFid("baoche4");
				permissionbaoche41.setUrl("../charteredOrder/getBcReturnOrderList");
				permissionbaoche41.setIconUrl("leftico leftico4 fl");
				permissionbaoche41.setCreateBy("系统生成");
				permissionbaoche41.setCreateOn(MyDate.getMyDateLong());
				permissionbaoche41.setUpdateBy("");
				permissionbaoche41.setUpdateOn("");
				permissionbaoche41.setSortFlag(1);
				permissionList.add(permissionbaoche41);
				// 包车退票记录
				SysPermissionEntity permissionbaoche42 = new SysPermissionEntity();
				permissionbaoche42.setCode("baoche42");
				permissionbaoche42.setName("包车退票记录");
				permissionbaoche42.setFid("baoche4");
				permissionbaoche42.setUrl("");
				permissionbaoche42.setIconUrl("leftico leftico4 fl");
				permissionbaoche42.setCreateBy("系统生成");
				permissionbaoche42.setCreateOn(MyDate.getMyDateLong());
				permissionbaoche42.setUpdateBy("");
				permissionbaoche42.setUpdateOn("");
				permissionbaoche42.setSortFlag(2);
				permissionList.add(permissionbaoche42);
				
		
		// 包车管理=======================结束=============================
				
				//拼车管理=======================开始=============================
				SysPermissionEntity permissionpinche = new SysPermissionEntity();
				permissionpinche.setCode("pinche");
				permissionpinche.setName("拼车管理");
				permissionpinche.setFid("-1");
				permissionpinche.setUrl("");
				permissionpinche.setIconUrl("arrow_slid fr");
				permissionpinche.setCreateBy("系统生成");
				permissionpinche.setCreateOn(MyDate.getMyDateLong());
				permissionpinche.setUpdateBy("");
				permissionpinche.setUpdateOn("");
				permissionpinche.setSortFlag(10);
				permissionList.add(permissionpinche);
					//乘客发布线路
					SysPermissionEntity permissionpinche1 = new SysPermissionEntity();
					permissionpinche1.setCode("pinche1");
					permissionpinche1.setName("乘客发布线路");
					permissionpinche1.setFid("pinche");
					permissionpinche1.setUrl("../passengerLine/getLineList");
					permissionpinche1.setIconUrl("leftico leftico10 fl");
					permissionpinche1.setCreateBy("系统生成");
					permissionpinche1.setCreateOn(MyDate.getMyDateLong());
					permissionpinche1.setUpdateBy("");
					permissionpinche1.setUpdateOn("");
					permissionpinche1.setSortFlag(1);
					permissionList.add(permissionpinche1);
					
					//车主发布线路
					SysPermissionEntity permissionpinche2 = new SysPermissionEntity();
					permissionpinche2.setCode("pinche2");
					permissionpinche2.setName("车主发布线路");
					permissionpinche2.setFid("pinche");
					permissionpinche2.setUrl("../carOwnerLine/getLineList");
					permissionpinche2.setIconUrl("leftico leftico10 fl");
					permissionpinche2.setCreateBy("系统生成");
					permissionpinche2.setCreateOn(MyDate.getMyDateLong());
					permissionpinche2.setUpdateBy("");
					permissionpinche2.setUpdateOn("");
					permissionpinche2.setSortFlag(2);
					permissionList.add(permissionpinche2);
					
					
					// 线路有效期
					SysPermissionEntity permissionpinche5 = new SysPermissionEntity();
					permissionpinche5.setCode("pinche5");
					permissionpinche5.setName("线路有效期");
					permissionpinche5.setFid("pinche");
					permissionpinche5.setUrl("../lineValidDate/toLineValidDate");
					permissionpinche5.setIconUrl("leftico leftico10 fl");
					permissionpinche5.setCreateBy("系统生成");
					permissionpinche5.setCreateOn(MyDate.getMyDateLong());
					permissionpinche5.setUpdateBy("");
					permissionpinche5.setUpdateOn("");
					permissionpinche5.setSortFlag(5);
					permissionList.add(permissionpinche5);
					
					// 敏感词过滤
					SysPermissionEntity permissionpinche6 = new SysPermissionEntity();
					permissionpinche6.setCode("yunying1");
					permissionpinche6.setName("敏感词过滤");
					permissionpinche6.setFid("pinche");
					permissionpinche6.setUrl("../sensitiveWord/toSensitiveWord");
					permissionpinche6.setIconUrl("leftico leftico10 fl");
					permissionpinche6.setCreateBy("系统生成");
					permissionpinche6.setCreateOn(MyDate.getMyDateLong());
					permissionpinche6.setUpdateBy("");
					permissionpinche6.setUpdateOn("");
					permissionpinche6.setSortFlag(6);
					permissionList.add(permissionpinche6);
					
			//拼车管理=======================结束=============================		
		
		//用户管理=======================开始=============================
		SysPermissionEntity permissionuser = new SysPermissionEntity();
		permissionuser.setCode("user");
		permissionuser.setName("供应商管理");
		permissionuser.setFid("-1");
		permissionuser.setUrl("");
		permissionuser.setIconUrl("arrow_slid fr");
		permissionuser.setCreateBy("系统生成");
		permissionuser.setCreateOn(MyDate.getMyDateLong());
		permissionuser.setUpdateBy("");
		permissionuser.setUpdateOn("");
		permissionuser.setSortFlag(2);
		permissionList.add(permissionuser);
		
		SysPermissionEntity permissionuser2 = new SysPermissionEntity();
		permissionuser2.setCode("user2");
		permissionuser2.setName("司机管理");
		permissionuser2.setFid("user");
		permissionuser2.setUrl("../operationDriver/driverManager");
		permissionuser2.setIconUrl("leftico leftico9 fl");
		permissionuser2.setCreateBy("系统生成");
		permissionuser2.setCreateOn(MyDate.getMyDateLong());
		permissionuser2.setUpdateBy("");
		permissionuser2.setUpdateOn("");
		permissionuser2.setSortFlag(2);
		permissionList.add(permissionuser2);
		    
			SysPermissionEntity permissionuser21 = new SysPermissionEntity();
			permissionuser21.setCode("user21");
			permissionuser21.setName("司机列表");
			permissionuser21.setFid("user2");
			permissionuser21.setUrl("../operationDriver/driverList");
			permissionuser21.setIconUrl("");
			permissionuser21.setCreateBy("系统生成");
			permissionuser21.setCreateOn(MyDate.getMyDateLong());
			permissionuser21.setUpdateBy("");
			permissionuser21.setUpdateOn("");
			permissionuser21.setSortFlag(1);
			permissionList.add(permissionuser21);
			
			SysPermissionEntity permissionuser22 = new SysPermissionEntity();
			permissionuser22.setCode("user22");
			permissionuser22.setName("提现申请");
			permissionuser22.setFid("user2");
			permissionuser22.setUrl("../operationDriver/driverApply");
			permissionuser22.setIconUrl("");
			permissionuser22.setCreateBy("系统生成");
			permissionuser22.setCreateOn(MyDate.getMyDateLong());
			permissionuser22.setUpdateBy("");
			permissionuser22.setUpdateOn("");
			permissionuser22.setSortFlag(2);
			permissionList.add(permissionuser22);
			
			SysPermissionEntity permissionuser23 = new SysPermissionEntity();
			permissionuser23.setCode("user23");
			permissionuser23.setName("发放奖励");
			permissionuser23.setFid("user2");
			permissionuser23.setUrl("../operationDriver/driverPayment");
			permissionuser23.setIconUrl("");
			permissionuser23.setCreateBy("系统生成");
			permissionuser23.setCreateOn(MyDate.getMyDateLong());
			permissionuser23.setUpdateBy("");
			permissionuser23.setUpdateOn("");
			permissionuser23.setSortFlag(3);
			permissionList.add(permissionuser23);
		  
		//
		SysPermissionEntity permissionuser3 = new SysPermissionEntity();
		permissionuser3.setCode("user3");
		permissionuser3.setName("商户管理");
		permissionuser3.setFid("user");
		permissionuser3.setUrl("../merchantAction/getMerchantList");
		permissionuser3.setIconUrl("leftico leftico15 fl");
		permissionuser3.setCreateBy("系统生成");
		permissionuser3.setCreateOn(MyDate.getMyDateLong());
		permissionuser3.setUpdateBy("");
		permissionuser3.setUpdateOn("");
		permissionuser3.setSortFlag(1);
		permissionList.add(permissionuser3);
		
		//
		
		SysPermissionEntity permissionuser4 = new SysPermissionEntity();
		permissionuser4.setCode("line4");
		permissionuser4.setName("线路成本设置");
		permissionuser4.setFid("user");
		permissionuser4.setUrl("../lineCostSet/getAllLineAndCost");
		permissionuser4.setIconUrl("leftico leftico4 fl");
		permissionuser4.setCreateBy("系统生成");
		permissionuser4.setCreateOn(MyDate.getMyDateLong());
		permissionuser4.setUpdateBy("");
		permissionuser4.setUpdateOn("");
		permissionuser4.setSortFlag(3);
		permissionList.add(permissionuser4);
		
		// sjx: gps的地图位置
		SysPermissionEntity permissionuser5 = new SysPermissionEntity();
		permissionuser5.setCode("gps01");
		permissionuser5.setName("车辆位置");
		permissionuser5.setFid("user");
		permissionuser5.setUrl("../line/getDefineCarsMsg");
		permissionuser5.setIconUrl("leftico leftico1 fl");
		permissionuser5.setCreateBy("系统生成");
		permissionuser5.setCreateOn(MyDate.getMyDateLong());
		permissionuser5.setUpdateBy("");
		permissionuser5.setUpdateOn("");
		permissionuser5.setSortFlag(4);
		permissionList.add(permissionuser5);
		
		
		/** 代理商管理还没有做，注释掉(胡双)
		SysPermissionEntity permissionuser4 = new SysPermissionEntity();
		permissionuser4.setCode("user4");
		permissionuser4.setName("代理商管理");
		permissionuser4.setFid("user");
		permissionuser4.setUrl("../agent/getAgentList");
		permissionuser4.setIconUrl("leftico leftico15 fl");
		permissionuser4.setCreateBy("系统生成");
		permissionuser4.setCreateOn(MyDate.getMyDateLong());
		permissionuser4.setUpdateBy("");
		permissionuser4.setUpdateOn("");
		permissionuser4.setSortFlag(4);
		permissionList.add(permissionuser4);
		*/
		//用户管理=======================结束=============================
		
	
		//订单管理=======================开始=============================
		SysPermissionEntity permissionorder = new SysPermissionEntity();
		permissionorder.setCode("order");
		permissionorder.setName("用户、订单管理");
		permissionorder.setFid("-1");
		permissionorder.setUrl("");
		permissionorder.setIconUrl("arrow_slid fr");
		permissionorder.setCreateBy("系统生成");
		permissionorder.setCreateOn(MyDate.getMyDateLong());
		permissionorder.setUpdateBy("");
		permissionorder.setUpdateOn("");
		permissionorder.setSortFlag(3);
		permissionList.add(permissionorder);
		//
		SysPermissionEntity permissionorder1 = new SysPermissionEntity();
		permissionorder1.setCode("order1");
		permissionorder1.setName("所有订单查询");
		permissionorder1.setFid("order");
		permissionorder1.setUrl("../orderAction/getAllOrderList");
		permissionorder1.setIconUrl("leftico leftico5 fl");
		permissionorder1.setCreateBy("系统生成");
		permissionorder1.setCreateOn(MyDate.getMyDateLong());
		permissionorder1.setUpdateBy("");
		permissionorder1.setUpdateOn("");
		permissionorder1.setSortFlag(2);
		permissionList.add(permissionorder1);
		//
		SysPermissionEntity permissionorder2 = new SysPermissionEntity();
		permissionorder2.setCode("order2");
		permissionorder2.setName("改签");
		permissionorder2.setFid("order");
		permissionorder2.setUrl("../changeTicket/changeTicketMainPage");
		permissionorder2.setIconUrl("leftico leftico1 fl");
		permissionorder2.setCreateBy("系统生成");
		permissionorder2.setCreateOn(MyDate.getMyDateLong());
		permissionorder2.setUpdateBy("");
		permissionorder2.setUpdateOn("");
		permissionorder2.setSortFlag(3);
		permissionList.add(permissionorder2);
				//车票改签三级菜=======================结束=============================
				SysPermissionEntity permissionorder21 = new SysPermissionEntity();
				permissionorder21.setCode("order21");
				permissionorder21.setName("订单列表");
				permissionorder21.setFid("order2");
				permissionorder21.setUrl("../changeTicket/getTicketList");
				permissionorder21.setIconUrl("leftico leftico1 fl");
				permissionorder21.setCreateBy("系统生成");
				permissionorder21.setCreateOn(MyDate.getMyDateLong());
				permissionorder21.setUpdateBy("");
				permissionorder21.setUpdateOn("");
				permissionorder21.setSortFlag(1);
				//permissionList.add(permissionorder21);
				//
				SysPermissionEntity permissionorder22 = new SysPermissionEntity();
				permissionorder22.setCode("order22");
				permissionorder22.setName("改签记录");
				permissionorder22.setFid("order2");
				permissionorder22.setUrl("../changeTicket/changedTicketList");
				permissionorder22.setIconUrl("leftico leftico1 fl");
				permissionorder22.setCreateBy("系统生成");
				permissionorder22.setCreateOn(MyDate.getMyDateLong());
				permissionorder22.setUpdateBy("");
				permissionorder22.setUpdateOn("");
				permissionorder22.setSortFlag(2);
				//permissionList.add(permissionorder22);
				//车票改签三级菜=======================结束=============================
		//
		SysPermissionEntity permissionorder3 = new SysPermissionEntity();
		permissionorder3.setCode("order3");
		permissionorder3.setName("退票");
		permissionorder3.setFid("order");
		permissionorder3.setUrl("../returnTicket/returnTicketMainPage");
		permissionorder3.setIconUrl("leftico leftico1 fl");
		permissionorder3.setCreateBy("系统生成");
		permissionorder3.setCreateOn(MyDate.getMyDateLong());
		permissionorder3.setUpdateBy("");
		permissionorder3.setUpdateOn("");
		permissionorder3.setSortFlag(5);
		permissionList.add(permissionorder3);
		
		
			//车票退票三级菜=======================结束=============================
			SysPermissionEntity permissionorder31 = new SysPermissionEntity();
			permissionorder31.setCode("order21");
			permissionorder31.setName("订单列表");
			permissionorder31.setFid("order3");
			permissionorder31.setUrl("../returnTicket/getReturnTicketList");
			permissionorder31.setIconUrl("leftico leftico1 fl");
			permissionorder31.setCreateBy("系统生成");
			permissionorder31.setCreateOn(MyDate.getMyDateLong());
			permissionorder31.setUpdateBy("");
			permissionorder31.setUpdateOn("");
			permissionorder31.setSortFlag(1);
			permissionList.add(permissionorder31);
			//
			SysPermissionEntity permissionorder32 = new SysPermissionEntity();
			permissionorder32.setCode("order32");
			permissionorder32.setName("退票记录");
			permissionorder32.setFid("order3");
			permissionorder32.setUrl("../returnTicket/getRecordTicketList");
			permissionorder32.setIconUrl("leftico leftico1 fl");
			permissionorder32.setCreateBy("系统生成");
			permissionorder32.setCreateOn(MyDate.getMyDateLong());
			permissionorder32.setUpdateBy("");
			permissionorder32.setUpdateOn("");
			permissionorder32.setSortFlag(2);
			permissionList.add(permissionorder32);
			//
			SysPermissionEntity permissionorder33 = new SysPermissionEntity();
			permissionorder33.setCode("order33");
			permissionorder33.setName("乘客申请退票列表");
			permissionorder33.setFid("order3");
			permissionorder33.setUrl("../returnTicket/applyReturnTicketList");
			permissionorder33.setIconUrl("leftico leftico1 fl");
			permissionorder33.setCreateBy("系统生成");
			permissionorder33.setCreateOn(MyDate.getMyDateLong());
			permissionorder33.setUpdateBy("");
			permissionorder33.setUpdateOn("");
			permissionorder33.setSortFlag(3);
			permissionList.add(permissionorder33);
			//
			SysPermissionEntity permissionorder34 = new SysPermissionEntity();
			permissionorder34.setCode("order34");
			permissionorder34.setName("支付宝退票列表");
			permissionorder34.setFid("order3");
			permissionorder34.setUrl("../returnTicket/appZfbReturnTicketList");
			permissionorder34.setIconUrl("leftico leftico1 fl");
			permissionorder34.setCreateBy("系统生成");
			permissionorder34.setCreateOn(MyDate.getMyDateLong());
			permissionorder34.setUpdateBy("");
			permissionorder34.setUpdateOn("");
			permissionorder34.setSortFlag(4);
			permissionList.add(permissionorder34);
			//车票退票三级菜=======================结束=============================
		
		
		//
		SysPermissionEntity permissionorder4 = new SysPermissionEntity();
		permissionorder4.setCode("orderone3");
		permissionorder4.setName("充值明细");
		permissionorder4.setFid("order");
		permissionorder4.setUrl("../orderAction/getRechargeDetails");
		permissionorder4.setIconUrl("leftico leftico23 fl");
		permissionorder4.setCreateBy("系统生成");
		permissionorder4.setCreateOn(MyDate.getMyDateLong());
		permissionorder4.setUpdateBy("");
		permissionorder4.setUpdateOn("");
		permissionorder4.setSortFlag(4);
		permissionList.add(permissionorder4);
		
		SysPermissionEntity permissionorder5 = new SysPermissionEntity();
		permissionorder5.setCode("user1");
		permissionorder5.setName("用户管理");
		permissionorder5.setFid("order");
		permissionorder5.setUrl("../operationPassenger/customManager");
		permissionorder5.setIconUrl("leftico leftico8 fl");
		permissionorder5.setCreateBy("系统生成");
		permissionorder5.setCreateOn(MyDate.getMyDateLong());
		permissionorder5.setUpdateBy("");
		permissionorder5.setUpdateOn("");
		permissionorder5.setSortFlag(1);
		permissionList.add(permissionorder5);
		
				//乘客管单理三级菜=======================开始=============================乘客列表  乘客评论
				SysPermissionEntity permissionuser11 = new SysPermissionEntity();
				permissionuser11.setCode("user11");
				permissionuser11.setName("所有乘客");
				permissionuser11.setFid("user1");
				permissionuser11.setUrl("../operationPassenger/passengerList");
				permissionuser11.setIconUrl("");
				permissionuser11.setCreateBy("系统生成");
				permissionuser11.setCreateOn(MyDate.getMyDateLong());
				permissionuser11.setUpdateBy("");
				permissionuser11.setUpdateOn("");
				permissionuser11.setSortFlag(1);
				permissionList.add(permissionuser11);
				
				SysPermissionEntity permissionuser12 = new SysPermissionEntity();
				permissionuser12.setCode("user12");
				permissionuser12.setName("app乘客");
				permissionuser12.setFid("user1");
				permissionuser12.setUrl("../operationPassenger/appPassengerList");
				permissionuser12.setIconUrl("");
				permissionuser12.setCreateBy("系统生成");
				permissionuser12.setCreateOn(MyDate.getMyDateLong());
				permissionuser12.setUpdateBy("");
				permissionuser12.setUpdateOn("");
				permissionuser12.setSortFlag(2);
				permissionList.add(permissionuser12);
				
				SysPermissionEntity permissionuser13 = new SysPermissionEntity();
				permissionuser13.setCode("user13");
				permissionuser13.setName("微信乘客");
				permissionuser13.setFid("user1");
				permissionuser13.setUrl("../operationPassenger/weiXinPassengerList");
				permissionuser13.setIconUrl("");
				permissionuser13.setCreateBy("系统生成");
				permissionuser13.setCreateOn(MyDate.getMyDateLong());
				permissionuser13.setUpdateBy("");
				permissionuser13.setUpdateOn("");
				permissionuser13.setSortFlag(3);
				permissionList.add(permissionuser13);
				
				//乘客管单理三级菜=======================结束=============================
		//订单管理=======================结束=============================
		
		
		
		//营销管理=======================开始=============================
		SysPermissionEntity permissionmarketing = new SysPermissionEntity();
		permissionmarketing.setCode("marketing");
		permissionmarketing.setName("营销管理");
		permissionmarketing.setFid("-1");
		permissionmarketing.setUrl("");
		permissionmarketing.setIconUrl("arrow_slid fr");
		permissionmarketing.setCreateBy("系统生成");
		permissionmarketing.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing.setUpdateBy("");
		permissionmarketing.setUpdateOn("");
		permissionmarketing.setSortFlag(4);
		permissionList.add(permissionmarketing);
		//
		SysPermissionEntity permissionmarketing1 = new SysPermissionEntity();
		permissionmarketing1.setCode("marketing1");
		permissionmarketing1.setName("短信管理");
		permissionmarketing1.setFid("marketing");
		permissionmarketing1.setUrl("../marketing/marketingManager");
		permissionmarketing1.setIconUrl("leftico leftico16 fl");
		permissionmarketing1.setCreateBy("系统生成");
		permissionmarketing1.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing1.setUpdateBy("");
		permissionmarketing1.setUpdateOn("");
		permissionmarketing1.setSortFlag(2);
		permissionList.add(permissionmarketing1);
				//短信管理三级菜=======================开始=============================
				SysPermissionEntity permissionmarketing11 = new SysPermissionEntity();
				permissionmarketing11.setCode("marketing11");
				permissionmarketing11.setName("短信列表");
				permissionmarketing11.setFid("marketing1");
				permissionmarketing11.setUrl("../marketing/getMarketingList");
				permissionmarketing11.setIconUrl("leftico leftico16 fl");
				permissionmarketing11.setCreateBy("系统生成");
				permissionmarketing11.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing11.setUpdateBy("");
				permissionmarketing11.setUpdateOn("");
				permissionmarketing11.setSortFlag(1);
				permissionList.add(permissionmarketing11);
				//
				SysPermissionEntity permissionmarketing12 = new SysPermissionEntity();
				permissionmarketing12.setCode("marketing12");
				permissionmarketing12.setName("发送短信");
				permissionmarketing12.setFid("marketing1");
				permissionmarketing12.setUrl("../marketing/addMarketing");
				permissionmarketing12.setIconUrl("leftico leftico16 fl");
				permissionmarketing12.setCreateBy("系统生成");
				permissionmarketing12.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing12.setUpdateBy("");
				permissionmarketing12.setUpdateOn("");
				permissionmarketing12.setSortFlag(2);
				permissionList.add(permissionmarketing12);
				//
				SysPermissionEntity permissionmarketing13 = new SysPermissionEntity();
				permissionmarketing13.setCode("marketing13");
				permissionmarketing13.setName("短信模板");
				permissionmarketing13.setFid("marketing1");
				permissionmarketing13.setUrl("../marketing/marketingTemplate");
				permissionmarketing13.setIconUrl("leftico leftico16 fl");
				permissionmarketing13.setCreateBy("系统生成");
				permissionmarketing13.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing13.setUpdateBy("");
				permissionmarketing13.setUpdateOn("");
				permissionmarketing13.setSortFlag(3);
				permissionList.add(permissionmarketing13);
				
		// APP站内消息管理 
		SysPermissionEntity permissionmarketing2 = new SysPermissionEntity();
		permissionmarketing2.setCode("marketing2");
		permissionmarketing2.setName("站内信");
		permissionmarketing2.setFid("marketing");
		permissionmarketing2.setUrl("../insideMessage/insideAPPManager");
		permissionmarketing2.setIconUrl("leftico leftico16 fl");
		permissionmarketing2.setCreateBy("系统生成");
		permissionmarketing2.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing2.setUpdateBy("");
		permissionmarketing2.setUpdateOn("");
		permissionmarketing2.setSortFlag(3);
		permissionList.add(permissionmarketing2);
				//
				SysPermissionEntity permissionmarketing21 = new SysPermissionEntity();
				permissionmarketing21.setCode("marketing21");
				permissionmarketing21.setName("站内信列表");
				permissionmarketing21.setFid("marketing2");
				permissionmarketing21.setUrl("../insideMessage/getAPPInsideList");
				permissionmarketing21.setIconUrl("leftico leftico16 fl");
				permissionmarketing21.setCreateBy("系统生成");
				permissionmarketing21.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing21.setUpdateBy("");
				permissionmarketing21.setUpdateOn("");
				permissionmarketing21.setSortFlag(1);
				permissionList.add(permissionmarketing21);
				//
				SysPermissionEntity permissionmarketing22 = new SysPermissionEntity();
				permissionmarketing22.setCode("marketing22");
				permissionmarketing22.setName("发送站内信");
				permissionmarketing22.setFid("marketing2");
				permissionmarketing22.setUrl("../insideMessage/addInsideApp");
				permissionmarketing22.setIconUrl("leftico leftico16 fl");
				permissionmarketing22.setCreateBy("系统生成");
				permissionmarketing22.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing22.setUpdateBy("");
				permissionmarketing22.setUpdateOn("");
				permissionmarketing22.setSortFlag(2);
				permissionList.add(permissionmarketing22);
				//
				SysPermissionEntity permissionmarketing23 = new SysPermissionEntity();
				permissionmarketing23.setCode("marketing23");
				permissionmarketing23.setName("站内信模板");
				permissionmarketing23.setFid("marketing2");
				permissionmarketing23.setUrl("../insideMessage/insideAPPTemplate");
				permissionmarketing23.setIconUrl("leftico leftico16 fl");
				permissionmarketing23.setCreateBy("系统生成");
				permissionmarketing23.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing23.setUpdateBy("");
				permissionmarketing23.setUpdateOn("");
				permissionmarketing23.setSortFlag(3);
				permissionList.add(permissionmarketing23);
		// APP站内消息管理 
		SysPermissionEntity permissionmarketing3 = new SysPermissionEntity();
		permissionmarketing3.setCode("marketing3");
		permissionmarketing3.setName("APP消息");
		permissionmarketing3.setFid("marketing");
		permissionmarketing3.setUrl("../pushMessage/pushAPPManager");
		permissionmarketing3.setIconUrl("leftico leftico16 fl");
		permissionmarketing3.setCreateBy("系统生成");
		permissionmarketing3.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing3.setUpdateBy("");
		permissionmarketing3.setUpdateOn("");
		permissionmarketing3.setSortFlag(4);
		permissionList.add(permissionmarketing3);
				//
				SysPermissionEntity permissionmarketing31 = new SysPermissionEntity();
				permissionmarketing31.setCode("marketing31");
				permissionmarketing31.setName("推送消息列表");
				permissionmarketing31.setFid("marketing3");
				permissionmarketing31.setUrl("../pushMessage/pushAPPMsgList");
				permissionmarketing31.setIconUrl("leftico leftico16 fl");
				permissionmarketing31.setCreateBy("系统生成");
				permissionmarketing31.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing31.setUpdateBy("");
				permissionmarketing31.setUpdateOn("");
				permissionmarketing31.setSortFlag(1);
				permissionList.add(permissionmarketing31);
				//
				SysPermissionEntity permissionmarketing32 = new SysPermissionEntity();
				permissionmarketing32.setCode("marketing32");
				permissionmarketing32.setName("推送消息");
				permissionmarketing32.setFid("marketing3");
				permissionmarketing32.setUrl("../pushMessage/addPushApp");
				permissionmarketing32.setIconUrl("leftico leftico16 fl");
				permissionmarketing32.setCreateBy("系统生成");
				permissionmarketing32.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing32.setUpdateBy("");
				permissionmarketing32.setUpdateOn("");
				permissionmarketing32.setSortFlag(2);
				permissionList.add(permissionmarketing32);
				//
				SysPermissionEntity permissionmarketing33 = new SysPermissionEntity();
				permissionmarketing33.setCode("marketing33");
				permissionmarketing33.setName("推送消息模板");
				permissionmarketing33.setFid("marketing3");
				permissionmarketing33.setUrl("../pushMessage/pushAPPTemplate");
				permissionmarketing33.setIconUrl("leftico leftico16 fl");
				permissionmarketing33.setCreateBy("系统生成");
				permissionmarketing33.setCreateOn(MyDate.getMyDateLong());
				permissionmarketing33.setUpdateBy("");
				permissionmarketing33.setUpdateOn("");
				permissionmarketing33.setSortFlag(3);
				permissionList.add(permissionmarketing33);
				//短信管理三级菜=======================开始=============================
				
		//广告配置管理
		SysPermissionEntity permissionmarketing4 = new SysPermissionEntity();
		permissionmarketing4.setCode("marketing4");
		permissionmarketing4.setName("广告配置管理");
		permissionmarketing4.setFid("marketing");
		permissionmarketing4.setUrl("../adManageAction/getAdManageList");
		permissionmarketing4.setIconUrl("leftico leftico16 fl");
		permissionmarketing4.setCreateBy("系统生成");
		permissionmarketing4.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing4.setUpdateBy("");
		permissionmarketing4.setUpdateOn("");
		permissionmarketing4.setSortFlag(1);
		permissionList.add(permissionmarketing4);
		
		// 新优惠券
		SysPermissionEntity permissionmarketing7 = new SysPermissionEntity();
		permissionmarketing7.setCode("marketing6");
		permissionmarketing7.setName("创建优惠券");
		permissionmarketing7.setFid("marketing");
		permissionmarketing7.setSysType(0);
		permissionmarketing7.setUrl("../couponGroup/couponGroupList");
		permissionmarketing7.setIconUrl("leftico leftico16 fl");
		permissionmarketing7.setCreateBy("系统生成");
		permissionmarketing7.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing7.setUpdateBy("");
		permissionmarketing7.setUpdateOn("");
		permissionmarketing7.setSortFlag(7);
		permissionList.add(permissionmarketing7);
		
		// 新优惠券发放
		SysPermissionEntity permissionmarketing8 = new SysPermissionEntity();
		permissionmarketing8.setCode("marketing7");
		permissionmarketing8.setName("发放优惠券");
		permissionmarketing8.setFid("marketing");
		permissionmarketing8.setSysType(0);
		permissionmarketing8.setUrl("../couponGroupGrant/couponGroupGrantList");
		permissionmarketing8.setIconUrl("leftico leftico16 fl");
		permissionmarketing8.setCreateBy("系统生成");
		permissionmarketing8.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing8.setUpdateBy("");
		permissionmarketing8.setUpdateOn("");
		permissionmarketing8.setSortFlag(8);
		permissionList.add(permissionmarketing8);
		
		// 新优惠券发放查询
		SysPermissionEntity permissionmarketing9 = new SysPermissionEntity();
		permissionmarketing9.setCode("marketing8");
		permissionmarketing9.setName("优惠券发放查询");
		permissionmarketing9.setFid("marketing");
		permissionmarketing9.setSysType(0);
		permissionmarketing9.setUrl("../couponGroupGrantDetail/couponGroupGrantDetailList");
		permissionmarketing9.setIconUrl("leftico leftico16 fl");
		permissionmarketing9.setCreateBy("系统生成");
		permissionmarketing9.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing9.setUpdateBy("");
		permissionmarketing9.setUpdateOn("");
		permissionmarketing9.setSortFlag(9);
		permissionList.add(permissionmarketing9);
		
		// 新优惠券发放统计
		SysPermissionEntity permissionmarketing10 = new SysPermissionEntity();
		permissionmarketing10.setCode("marketing9");
		permissionmarketing10.setName("优惠券统计");
		permissionmarketing10.setFid("marketing");
		permissionmarketing10.setSysType(0);
		permissionmarketing10.setUrl("../couponStatistics/couponStatisticsList");
		permissionmarketing10.setIconUrl("leftico leftico16 fl");
		permissionmarketing10.setCreateBy("系统生成");
		permissionmarketing10.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing10.setUpdateBy("");
		permissionmarketing10.setUpdateOn("");
		permissionmarketing10.setSortFlag(10);
		permissionList.add(permissionmarketing10);
		
		// 推荐有奖设置
		SysPermissionEntity permissionmarketing111 = new SysPermissionEntity();
		permissionmarketing111.setCode("help4");
		permissionmarketing111.setName("推荐有奖设置");
		permissionmarketing111.setFid("marketing");
		permissionmarketing111.setSysType(0);
		permissionmarketing111.setUrl("../recommendAward/forwardSetPage");
		permissionmarketing111.setIconUrl("leftico leftico19 fl");
		permissionmarketing111.setCreateBy("系统生成");
		permissionmarketing111.setCreateOn(MyDate.getMyDateLong());
		permissionmarketing111.setUpdateBy("");
		permissionmarketing111.setUpdateOn("");
		permissionmarketing111.setSortFlag(11);
		permissionList.add(permissionmarketing111);
		//
		//营销管理=======================结束=============================
	
		//帮助反馈=======================开始=============================
		SysPermissionEntity permissionhelp = new SysPermissionEntity();
		permissionhelp.setCode("help");
		permissionhelp.setName("客户投诉");
		permissionhelp.setFid("-1");
		permissionhelp.setUrl("");
		permissionhelp.setIconUrl("arrow_slid fr");
		permissionhelp.setCreateBy("系统生成");
		permissionhelp.setCreateOn(MyDate.getMyDateLong());
		permissionhelp.setUpdateBy("");
		permissionhelp.setUpdateOn("");
		permissionhelp.setSortFlag(7);
		permissionList.add(permissionhelp);
			//
			SysPermissionEntity permissionhelp1 = new SysPermissionEntity();
			permissionhelp1.setCode("help1");
			permissionhelp1.setName("乘客反馈管理");
			permissionhelp1.setFid("help");
			permissionhelp1.setUrl("../help/feedbackManager");
			permissionhelp1.setIconUrl("leftico leftico19 fl");
			permissionhelp1.setCreateBy("系统生成");
			permissionhelp1.setCreateOn(MyDate.getMyDateLong());
			permissionhelp1.setUpdateBy("");
			permissionhelp1.setUpdateOn("");
			permissionhelp1.setSortFlag(1);
			permissionList.add(permissionhelp1);
				
				//乘客反馈
				SysPermissionEntity permissionhelp11 = new SysPermissionEntity();
				permissionhelp11.setCode("help11");
				permissionhelp11.setName("乘客反馈信息");
				permissionhelp11.setFid("help1");
				permissionhelp11.setUrl("../help/getFeedbackList");
				permissionhelp11.setIconUrl("");
				permissionhelp11.setCreateBy("系统生成");
				permissionhelp11.setCreateOn(MyDate.getMyDateLong());
				permissionhelp11.setUpdateBy("");
				permissionhelp11.setUpdateOn("");
				permissionhelp11.setSortFlag(1);
				permissionList.add(permissionhelp11);
				
				//乘客评论
				SysPermissionEntity permissionhelp12 = new SysPermissionEntity();
				permissionhelp12.setCode("help12");
				permissionhelp12.setName("乘客评论列表");
				permissionhelp12.setFid("help1");
				permissionhelp12.setUrl("../operationComment/commentList");
				permissionhelp12.setIconUrl("");
				permissionhelp12.setCreateBy("系统生成");
				permissionhelp12.setCreateOn(MyDate.getMyDateLong());
				permissionhelp12.setUpdateBy("");
				permissionhelp12.setUpdateOn("");
				permissionhelp12.setSortFlag(2);
				permissionList.add(permissionhelp12);
			//
			SysPermissionEntity permissionhelp2 = new SysPermissionEntity();
			permissionhelp2.setCode("help2");
			permissionhelp2.setName("帮助中心");
			permissionhelp2.setFid("help");
			permissionhelp2.setUrl("../helpCenter/list");
			permissionhelp2.setIconUrl("leftico leftico19 fl");
			permissionhelp2.setCreateBy("系统生成");
			permissionhelp2.setCreateOn(MyDate.getMyDateLong());
			permissionhelp2.setUpdateBy("");
			permissionhelp2.setUpdateOn("");
			permissionhelp2.setSortFlag(2);
			permissionList.add(permissionhelp2);
			// 用户协议
			SysPermissionEntity permissionhelp3 = new SysPermissionEntity();
			permissionhelp3.setCode("help3");
			permissionhelp3.setName("用户协议");
			permissionhelp3.setFid("help");
			permissionhelp3.setUrl("../userAgreement/toList");
			permissionhelp3.setIconUrl("leftico leftico19 fl");
			permissionhelp3.setCreateBy("系统生成");
			permissionhelp3.setCreateOn(MyDate.getMyDateLong());
			permissionhelp3.setUpdateBy("");
			permissionhelp3.setUpdateOn("");
			permissionhelp3.setSortFlag(3);
			permissionList.add(permissionhelp3);
			
		//帮助反馈=======================结束=============================
		
		//系统管理=======================开始=============================
		SysPermissionEntity permissionsys = new SysPermissionEntity();
		permissionsys.setCode("sys");
		permissionsys.setName("系统设置");
		permissionsys.setFid("-1");
		permissionsys.setUrl("");
		permissionsys.setIconUrl("arrow_slid fr");
		permissionsys.setCreateBy("系统生成");
		permissionsys.setCreateOn(MyDate.getMyDateLong());
		permissionsys.setUpdateBy("");
		permissionsys.setUpdateOn("");
		permissionsys.setSortFlag(8);
		permissionList.add(permissionsys);
		//
		SysPermissionEntity permissionsys1 = new SysPermissionEntity();
		permissionsys1.setCode("sys1");
		permissionsys1.setName("管理员管理");
		permissionsys1.setFid("sys");
		permissionsys1.setUrl("../adminManager/toList");
		permissionsys1.setIconUrl("leftico leftico20 fl");
		permissionsys1.setCreateBy("系统生成");
		permissionsys1.setCreateOn(MyDate.getMyDateLong());
		permissionsys1.setUpdateBy("");
		permissionsys1.setUpdateOn("");
		permissionsys1.setSortFlag(1);
		permissionList.add(permissionsys1);
		//
		SysPermissionEntity permissionsys2 = new SysPermissionEntity();
		permissionsys2.setCode("sys2");
		permissionsys2.setName("角色权限管理");
		permissionsys2.setFid("sys");
		permissionsys2.setUrl("../rolePermissionManager/toList");
		permissionsys2.setIconUrl("leftico leftico22 fl");
		permissionsys2.setCreateBy("系统生成");
		permissionsys2.setCreateOn(MyDate.getMyDateLong());
		permissionsys2.setUpdateBy("");
		permissionsys2.setUpdateOn("");
		permissionsys2.setSortFlag(2);
		permissionList.add(permissionsys2);
		//
		SysPermissionEntity permissionsys3 = new SysPermissionEntity();
		permissionsys3.setCode("sys3");
		permissionsys3.setName("操作日志");
		permissionsys3.setFid("sys");
		permissionsys3.setUrl("../logList/toLogList");
		permissionsys3.setIconUrl("leftico leftico17 fl");
		permissionsys3.setCreateBy("系统生成");
		permissionsys3.setCreateOn(MyDate.getMyDateLong());
		permissionsys3.setUpdateBy("");
		permissionsys3.setUpdateOn("");
		permissionsys3.setSortFlag(3);
		permissionList.add(permissionsys3);
		//
		SysPermissionEntity permissionsys4 = new SysPermissionEntity();
		permissionsys4.setCode("sysone4");
		permissionsys4.setName("修改密码");
		permissionsys4.setFid("sys");
		permissionsys4.setUrl("../changePassWord/toChangePassWord");
		permissionsys4.setIconUrl("leftico leftico24 fl");
		permissionsys4.setCreateBy("系统生成");
		permissionsys4.setCreateOn(MyDate.getMyDateLong());
		permissionsys4.setUpdateBy("");
		permissionsys4.setUpdateOn("");
		permissionsys4.setSortFlag(4);
		permissionList.add(permissionsys4);
		//
		SysPermissionEntity permissionsys5 = new SysPermissionEntity();
		permissionsys5.setCode("sysone5");
		permissionsys5.setName("个人信息");
		permissionsys5.setFid("sys");
		permissionsys5.setUrl("../changePassWord/personInfo");
		permissionsys5.setIconUrl("leftico leftico10 fl");
		permissionsys5.setCreateBy("系统生成");
		permissionsys5.setCreateOn(MyDate.getMyDateLong());
		permissionsys5.setUpdateBy("");
		permissionsys5.setUpdateOn("");
		permissionsys5.setSortFlag(5);
		permissionList.add(permissionsys5);
		
		SysPermissionEntity permissionsys7 = new SysPermissionEntity();
		permissionsys7.setCode("order6");
		permissionsys7.setName("APP相关设置");
		permissionsys7.setFid("sys");
		permissionsys7.setUrl("../appAction/appSetting");
		permissionsys7.setIconUrl("leftico leftico7 fl");
		permissionsys7.setCreateBy("系统生成");
		permissionsys7.setCreateOn(MyDate.getMyDateLong());
		permissionsys7.setUpdateBy("");
		permissionsys7.setUpdateOn("");
		permissionsys7.setSortFlag(6);
		permissionList.add(permissionsys7);
		
		SysPermissionEntity permissionsys6 = new SysPermissionEntity();
		permissionsys6.setCode("sysone6");
		permissionsys6.setName("参数设置");
		permissionsys6.setFid("sys");
		permissionsys6.setUrl("../sysParam/getSysParamList");
		permissionsys6.setIconUrl("leftico leftico10 fl");
		permissionsys6.setCreateBy("系统生成");
		permissionsys6.setCreateOn(MyDate.getMyDateLong());
		permissionsys6.setUpdateBy("");
		permissionsys6.setUpdateOn("");
		permissionsys6.setSortFlag(66);
		//permissionList.add(permissionsys6);
		//系统管理=======================结束=============================
		
		
		//财务结算=======================开始=============================
		/*
		SysPermissionEntity permissioncaiwu = new SysPermissionEntity();
		permissioncaiwu.setCode("caiwu");
		permissioncaiwu.setName("财务结算");
		permissioncaiwu.setFid("-1");
		permissioncaiwu.setUrl("");
		permissioncaiwu.setIconUrl("arrow_slid fr");
		permissioncaiwu.setCreateBy("系统生成");
		permissioncaiwu.setCreateOn(MyDate.getMyDateLong());
		permissioncaiwu.setUpdateBy("");
		permissioncaiwu.setUpdateOn("");
		permissioncaiwu.setSortFlag(oneLevel++);
		permissionList.add(permissioncaiwu);
			
			SysPermissionEntity permissioncaiwu1 = new SysPermissionEntity();
			permissioncaiwu1.setCode("caiwu1");
			permissioncaiwu1.setName("结算设置");
			permissioncaiwu1.setFid("caiwu");
			permissioncaiwu1.setUrl("../balanceSet/getBalanceSetList");
			permissioncaiwu1.setIconUrl("leftico leftico10 fl");
			permissioncaiwu1.setCreateBy("系统生成");
			permissioncaiwu1.setCreateOn(MyDate.getMyDateLong());
			permissioncaiwu1.setUpdateBy("");
			permissioncaiwu1.setUpdateOn("");
			permissioncaiwu1.setSortFlag(1);
			permissionList.add(permissioncaiwu1);
			*/
		//财务结算=======================结束=============================
		
		//系统运营功能==========================开始================================
		/*SysPermissionEntity permissionyunying = new SysPermissionEntity();
		permissionyunying.setCode("yunying");
		permissionyunying.setName("系统运营功能");
		permissionyunying.setFid("-1");
		permissionyunying.setUrl("");
		permissionyunying.setIconUrl("arrow_slid fr");
		permissionyunying.setCreateBy("系统生成");
		permissionyunying.setCreateOn(MyDate.getMyDateLong());
		permissionyunying.setUpdateBy("");
		permissionyunying.setUpdateOn("");
		permissionyunying.setSortFlag(oneLevel++);
		permissionList.add(permissionyunying);
		
			*/
		//系统运营功能==========================结束================================
		
		
		//供应商结算功能==========================开始================================
		SysPermissionEntity permissionsupplier = new SysPermissionEntity();
		permissionsupplier.setCode("supplier");
		permissionsupplier.setName("财务管理");
		permissionsupplier.setFid("-1");
		permissionsupplier.setSysType(0);
		permissionsupplier.setUrl("");
		permissionsupplier.setIconUrl("arrow_slid fr");
		permissionsupplier.setCreateBy("系统生成");
		permissionsupplier.setCreateOn(MyDate.getMyDateLong());
		permissionsupplier.setUpdateBy("");
		permissionsupplier.setUpdateOn("");
		permissionsupplier.setSortFlag(5);
		permissionList.add(permissionsupplier);
		
		
			//
			SysPermissionEntity permissionsupplier2 = new SysPermissionEntity();
			permissionsupplier2.setCode("order5");
			permissionsupplier2.setName("收入统计");
			permissionsupplier2.setFid("supplier");
			permissionsupplier2.setUrl("../monthIncomeAction/managerIncome");
			permissionsupplier2.setIconUrl("leftico leftico3 fl");
			permissionsupplier2.setCreateBy("系统生成");
			permissionsupplier2.setCreateOn(MyDate.getMyDateLong());
			permissionsupplier2.setUpdateBy("");
			permissionsupplier2.setUpdateOn("");
			permissionsupplier2.setSortFlag(1);
			permissionList.add(permissionsupplier2);
					//收入统计三级菜=======================开始=============================
					SysPermissionEntity permissionorder51 = new SysPermissionEntity();
					permissionorder51.setCode("order1");
					permissionorder51.setName("日收入统计");
					permissionorder51.setFid("order5");
					permissionorder51.setUrl("../dayIncomeAction/getDayIncome");
					permissionorder51.setIconUrl("leftico leftico3 fl");
					permissionorder51.setCreateBy("系统生成");
					permissionorder51.setCreateOn(MyDate.getMyDateLong());
					permissionorder51.setUpdateBy("");
					permissionorder51.setUpdateOn("");
					permissionorder51.setSortFlag(1);
					permissionList.add(permissionorder51);
					//
					SysPermissionEntity permissionorder52 = new SysPermissionEntity();
					permissionorder52.setCode("order52");
					permissionorder52.setName("月收入统计");
					permissionorder52.setFid("order5");
					permissionorder52.setUrl("../monthIncomeAction/getMonthIncome");
					permissionorder52.setIconUrl("leftico leftico3 fl");
					permissionorder52.setCreateBy("系统生成");
					permissionorder52.setCreateOn(MyDate.getMyDateLong());
					permissionorder52.setUpdateBy("");
					permissionorder52.setUpdateOn("");
					permissionorder52.setSortFlag(2);
					permissionList.add(permissionorder52);
					//
					SysPermissionEntity permissionorder53 = new SysPermissionEntity();
					permissionorder53.setCode("order53");
					permissionorder53.setName("支出统计");
					permissionorder53.setFid("order5");
					permissionorder53.setUrl("../statOutAction/getStatOutList");
					permissionorder53.setIconUrl("leftico leftico3 fl");
					permissionorder53.setCreateBy("系统生成");
					permissionorder53.setCreateOn(MyDate.getMyDateLong());
					permissionorder53.setUpdateBy("");
					permissionorder53.setUpdateOn("");
					permissionorder53.setSortFlag(3);
					permissionList.add(permissionorder53);
					// 上下班优惠券统计
					SysPermissionEntity permissionorder54 = new SysPermissionEntity();
					permissionorder54.setCode("order54");
					permissionorder54.setName("上下班优惠券统计");
					permissionorder54.setFid("order5");
					permissionorder54.setUrl("../busCouponStatAction/busCouponStatList");
					permissionorder54.setIconUrl("leftico leftico3 fl");
					permissionorder54.setCreateBy("系统生成");
					permissionorder54.setCreateOn(MyDate.getMyDateLong());
					permissionorder54.setUpdateBy("");
					permissionorder54.setUpdateOn("");
					permissionorder54.setSortFlag(4);
					permissionList.add(permissionorder54);
					//收入统计三级菜=======================结束=============================
			//
		
			//供应商结算明细表
			SysPermissionEntity permissionsupplier1 = new SysPermissionEntity();
			permissionsupplier1.setCode("supplier1");
			permissionsupplier1.setName("供应商结算表");
			permissionsupplier1.setFid("supplier");
			permissionsupplier1.setSysType(0);
			permissionsupplier1.setUrl("../financial/querySupplierList");
			permissionsupplier1.setIconUrl("leftico leftico16 fl");
			permissionsupplier1.setCreateBy("系统生成");
			permissionsupplier1.setCreateOn(MyDate.getMyDateLong());
			permissionsupplier1.setUpdateBy("");
			permissionsupplier1.setUpdateOn("");
			permissionsupplier1.setSortFlag(2);
			permissionList.add(permissionsupplier1);
			
		//供应商结算功能==========================结束================================
		
		//报表功能==========================开始================================
		SysPermissionEntity permissionreport = new SysPermissionEntity();
		permissionreport.setCode("report");
		permissionreport.setName("运营报表");
		permissionreport.setFid("-1");
		permissionreport.setSysType(0);
		permissionreport.setUrl("");
		permissionreport.setIconUrl("arrow_slid fr");
		permissionreport.setCreateBy("系统生成");
		permissionreport.setCreateOn(MyDate.getMyDateLong());
		permissionreport.setUpdateBy("");
		permissionreport.setUpdateOn("");
		permissionreport.setSortFlag(6);
		permissionList.add(permissionreport);
		
			//报表
			SysPermissionEntity permissionreport1 = new SysPermissionEntity();
			permissionreport1.setCode("report1");
			permissionreport1.setName("线路运营日报");
			permissionreport1.setFid("report");
			permissionreport1.setSysType(0);
			permissionreport1.setUrl("../financial/queryReportList");
			permissionreport1.setIconUrl("leftico leftico16 fl");
			permissionreport1.setCreateBy("系统生成");
			permissionreport1.setCreateOn(MyDate.getMyDateLong());
			permissionreport1.setUpdateBy("");
			permissionreport1.setUpdateOn("");
			permissionreport1.setSortFlag(1);
			permissionList.add(permissionreport1);
			
		//报表功能==========================结束================================
				
		return permissionList;
		
	}
}











