package com.pig84.ab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.IDriverDao;
import com.pig84.ab.service.IDriverService;
import com.pig84.ab.vo.DriverScheduleInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_2_list;
import com.pig84.ab.vo.bean.AppVo_3_list;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_7_list;
import com.pig84.ab.vo.bean.AppVo_8;
import com.pig84.ab.vo.driver.DriverInfoEntity;
import com.pig84.ab.vo.driver.DriverWithdrawAskfor;

@Service("driverService")
public class DriverServiceImpl implements IDriverService {
	@Autowired
	private IDriverDao driverDao;
	/**
	 * 登录
	 */
	@Override
	public DriverInfoEntity login(String telephone, String passWord,String terminal,String passWord_,String imei) {
		return driverDao.login(telephone, passWord, terminal, passWord_, imei);
	}
	
	/** 检查手机号码唯一*/
	@Override
	public String checkMobile(String mobile) {
		return driverDao.checkMobile(mobile);
	}
	
	/**找回密码**/
	@Override
	public String resetPwd(String mobileNo, String pwd,String pwd_) {
		return driverDao.updatePwd(mobileNo,pwd,pwd_);
	}
	
	/**修改司机信息**/
	@Override
	public String updateDriverInfo(String driverId,String headerPicUrl,String sex,String oldPwd,String newPwd){
		return driverDao.updateDriverInfo(driverId, headerPicUrl, sex, oldPwd, newPwd);
	}
	
	/**根据司机id获取司机信息**/
	@Override
	public DriverInfoEntity getDriverById(String driverId){
		return driverDao.getDriverById(driverId);
	}
	
	/**获取司机行程**/
	@Override
	public AppVo_7_list getDriverScheduleById(String driverId,String flag){
		return driverDao.getDriverScheduleById(driverId,flag);
	}
	
	/**获取司机行程详情**/
	@Override
    public DriverScheduleInfo getScheduleDetail(String lineBaseId,String lineClassId){
    	return driverDao.getScheduleDetail(lineBaseId, lineClassId);
    }
    
    /**查询延误消息模版**/
	@Override
    public List<AppVo_4> getDelayMsgList(){
    	return driverDao.getDelayMsgList();
    }
    
    /**修改班次相关信息
     * lineClassId 班次id
     * delayMsgId 延迟消息id
     * dispatchStatus 发车状态（0.未发车 1.已发车 2.行程结束）
     * currentStationName 当前站名
     * **/
    @Override
    public String updateLineClass(String lineBaseId,String lineClassId,String driverId,String delayMsgId,String dispatchStatus,String currentStationName){
    	return driverDao.updateLineClass(lineBaseId,lineClassId,driverId,delayMsgId,dispatchStatus,currentStationName);
    }
    
    /**添加圈子用户关系信息**/
    @Override
    public int addImGroupUser(String orderNo){
    	return driverDao.addImGroupUser(orderNo);
    }
    
    /**添加圈子用户**/
    @Override
    public String addImUser(String userId,String userMark){
    	return driverDao.addImUser(userId,userMark);
    }
    
    /**查询已上车用户信息**/
    @Override
    public AppVo_2_list getAboardUserInfo(String lineBaseId,String lineClassId){
    	return driverDao.getAboardUserInfo(lineBaseId, lineClassId);
    }
    
    /**
	 * 反馈建议
	 */
    @Override
	public String addFeedback(String context, String phoneNo, String userid){
		return driverDao.addFeedback(context, phoneNo, userid);
	}
	
	/**根据app端所传时间取最近未发车的两个班次
	 * appCurTime app端当前时间**/
    @Override
	public List<AppVo_1> getAlarmTime(String driverId,String appCurTime){
		return driverDao.getAlarmTime(driverId, appCurTime);
	}
	
	/**获取司机账户余额**/
	@Override
	public AppVo_1 getDriverAccount(String driverId){
		return driverDao.getDriverAccount(driverId);
	}
	
	/**获取司机账户明细**/
	@Override
	public List<AppVo_5> getDriverAccountDetail(String driverId){
		return driverDao.getDriverAccountDetail(driverId);
	}
	
	/**分页查询司机评论**/
	@Override
	public AppVo_3_list getDriverCommentInfo(String driverId,String currentPage,String pageSize){
		return driverDao.getDriverCommentInfo(driverId, currentPage, pageSize);
	}
	
	/**根据订单号返回订单明细 id和司机im用户id**/
	@Override
	public AppVo_2 getOrderDetailId(String orderNo){
		return driverDao.getOrderDetailId(orderNo);
	}
	
	/**
	 * 获取司机端android版本号
	 */
	@Override
	public AppVo_5 getDriverVersion(){
		return driverDao.getDriverVersion();
	}
	
	/**添加司机提现申请**/
	@Override
	public int addDriverWithdrawAskfor(DriverWithdrawAskfor dwa){
		return driverDao.addDriverWithdrawAskfor(dwa);
	}
	
	/**
	 * 司机端消息列表
	 */
	@Override
	public List<AppVo_8> msgList(String driverId,String currentPage,String pageSize){
		return driverDao.msgList(driverId, currentPage, pageSize);
	}
	
	/**
	 * 重置未加密的司机密码
	 */
	@Override
	public int updateDriverPassword(){
		return driverDao.updateDriverPassword();
	}
	
	/**
	 * 更新设备位置
	 * @param gpsinfo
	 * @return
	 */
	@Override
	public int updateGpsInfo(String gpsNo, String time, String lon, String lat, String speed, String angle){
		return driverDao.updateGpsInfo(gpsNo, time, lon, lat, speed, angle);
	}
}
