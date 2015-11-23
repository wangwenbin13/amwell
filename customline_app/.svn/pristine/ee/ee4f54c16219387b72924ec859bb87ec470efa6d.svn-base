package com.pig84.ab.dao.impl;

import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IAliPayDao;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.StatPassengerRecharge;
import com.pig84.ab.vo.SysPayLog;
import com.pig84.ab.vo.bean.AppVo_1;

@Repository
public class AliPayDaoImpl extends BaseDao implements IAliPayDao {
	
	/**
	 * 构建支付宝充值订单（充值）
	 */
	public String bulidRechargeOrderByAli(String userid, String money) {
		SysPayLog log = new SysPayLog();
		log.setMoneyLimit(money);
		log.setPassengerid(userid);
		log.setType("2");
		log.setOptime(MyDate.Format.DATETIME.now());
		int flag = updateData(log,"sys_pay_log", "localId");
		if(flag!=-1){//成功
			return log.getLocalId();
		}else{//失败
			return "0";
		}
	}
	
	/**
	 * 支付宝充值成功，添加金额
	 */
	public String addMoney(String orderNo) {
		String sql_log = "select * from sys_pay_log where localId = '"+orderNo+"'";
		SysPayLog log = queryBean(SysPayLog.class, sql_log);
		if(log!=null){
			String money = log.getMoneyLimit();
			StatPassengerRecharge vo = new StatPassengerRecharge();
			vo.setRetype("0");
			vo.setRetime(MyDate.Format.DATETIME.now());
			vo.setRerunNo("0");
			vo.setPassengerId(log.getPassengerid());
			vo.setMoneyLimit(money);
			vo.setReModel("4");
			
			int flag = updateData(vo,"stat_passenger_recharge","rechargeId");
			if(flag!=-1){//充值成功
				return "1";
			}else{//充值失败
				return "0";
			}
		}
		return "0";
	}
	
	/**
	 * 获取订单有效时间
	 */
	public String getTime() {
		String time = "";
		String sql = "SELECT MAX(orderValiteTime) AS a1 FROM sys_app_setting";
		AppVo_1 vo = queryBean(AppVo_1.class, sql);
		if(vo!=null){
			time =  vo.getA1();
		}
		return time;
	}

}
