package com.pig84.ab.dao;

import java.util.List;

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

public interface IDriverDao {
	/**登录**/
	public DriverInfoEntity login(String telephone,String passWord,String terminal,String passWord_,String imei);
	
	/**检查手机号码唯一**/
	public String checkMobile(String mobile);
	
	/**找回密码**/
	public String updatePwd(String mobileNo,String pwd,String pwd_);
	
	/**修改司机信息**/
	public String updateDriverInfo(String driverId,String headerPicUrl,String sex,String oldPwd,String newPwd);
	
	/**根据司机id获取司机信息**/
	public DriverInfoEntity getDriverById(String driverId);
	
	/**获取司机行程**/
	public AppVo_7_list getDriverScheduleById(String driverId,String flag);
	
	/**获取司机行程详情**/
    public DriverScheduleInfo getScheduleDetail(String lineBaseId,String lineClassId);
    
    /**查询延误消息模版**/
    public List<AppVo_4> getDelayMsgList();
    
    /**修改班次相关信息
     * lineClassId 班次id
     * delayMsgId 延迟消息id
     * dispatchStatus 发车状态（0.未发车 1.已发车 2.行程结束）
     * currentStationName 当前站名
     * **/
    public String updateLineClass(String lineBaseId,String lineClassId,String driverId,String delayMsgId,String dispatchStatus,String currentStationName);
    
    /**添加圈子用户关系信息**/
    public int addImGroupUser(String orderNo);
    
    /**添加圈子用户**/
    public String addImUser(String userId,String userMark);
    
    /**查询已上车用户信息**/
    public AppVo_2_list getAboardUserInfo(String lineBaseId,String lineClassId);
    
    /**
	 * 反馈建议
	 */
	public String addFeedback(String context, String phoneNo, String userid);
	
	/**根据app端所传时间取最近未发车的两个班次
	 * appCurTime app端当前时间**/
	public List<AppVo_1> getAlarmTime(String driverId,String appCurTime);
	
	/**获取司机账户余额**/
	public AppVo_1 getDriverAccount(String driverId);
	
	/**获取司机账户明细**/
	public List<AppVo_5> getDriverAccountDetail(String driverId);
	
	/**分页查询司机评论**/
	public AppVo_3_list getDriverCommentInfo(String driverId,String currentPage,String pageSize);
	
	/**根据订单号返回订单明细 id和司机im用户id**/
	public AppVo_2 getOrderDetailId(String orderNo);
	
	/**
	 * 获取司机端android版本号
	 */
	public AppVo_5 getDriverVersion();
	
	/**添加司机提现申请**/
	public int addDriverWithdrawAskfor(DriverWithdrawAskfor dwa);
	
	/**
	 * 司机端消息列表
	 */
	public List<AppVo_8> msgList(String driverId,String currentPage,String pageSize);
	
	/**
	 * 重置未加密的司机密码
	 */
	public int updateDriverPassword();
	
	/**
	 * 更新设备位置
	 * @param gpsinfo
	 * @return
	 */
	public int updateGpsInfo(String gpsNo, String time, String lon, String lat, String speed, String angle);
}
