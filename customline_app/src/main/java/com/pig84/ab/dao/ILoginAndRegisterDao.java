package com.pig84.ab.dao;

import java.util.List;

import com.pig84.ab.vo.ErrLog;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.SysMsgInfo;
import com.pig84.ab.vo.SysMsgUser;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_8;

/**
 * 登录注册相关接口
 * @author zhangqiang
 *
 */
public interface ILoginAndRegisterDao {
	
	public String checkMobile(String mobile);

	/**注册**/
	public String register(PassengerInfo user);
	
	/**登录**/
	public PassengerInfo login(String telephone,String passWord,String terminal,String passWord_,String imei);
	
	/**修改用户信息**/
	public String updateUserInfo(String passengerId,String nickName,String headerPicUrl,String sex);
	
	/**反馈建议**/
	public String help(String context,String phoneNo,String userid);
	
	/**根据用户ID获取用户信息**/
	public PassengerInfo updateUserForSession(String userid);
	
	/**根据微信openId获取用户信息**/
	public PassengerInfo getPassengerByOpenId(String uid,String type);
	
	/**消息列表**/
	public List<AppVo_8> msgList(String userid);
	
	/**消息详细**/
	public AppVo_5 msgInfo(String msgid,String userid);
	
	/**删除消息**/
	public String delMsg(String msgids,String userid);
	
	/**app错误日志添加**/
	public String addAppLog(ErrLog err);
	
	/**获取app版本号**/
	public AppVo_5 getVersion();

	/**添加发送信息的内容**/
	public int addSysMsgInfo(SysMsgInfo msgInfo);

	/**添加系统_消息对应表sys_msg_user**/
	public void addSysMsgUser(SysMsgUser msgUser);
	
	/**判断该用户是不是华为员工**/
	public boolean judgeUserType(String telephone);

	/**判断是否送对应的补贴信息**/
	public AppVo_3 juderSendAllowance(String telephone,String orderNo);
	
	/**获取城市信息**/
	public AppVo_4 getSysArea(String cityName);
	
	/**获取IOS版本**/
	public AppVo_5 getIOSVersion(String appType);
	
	/**根据用户ID修改openid**/
	public String updateOpenId(String openid,String uid);
	
	/**根据手机号码获取用户信息**/
	public PassengerInfo getPassengerByTel(String tel);
	
	/**查询广告信息**/
	public List<AppVo_5> getAdInfoList(String clientType,String imageResolution,String versionId);
	
	/**根据手机号码更改sessionId**/
	public String updateSessionIdByTel(String tel);

}