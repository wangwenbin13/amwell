package com.pig84.ab.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ILoginAndRegisterDao;
import com.pig84.ab.utils.DESCryptUtil;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.JPush;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.AppVersion;
import com.pig84.ab.vo.ErrLog;
import com.pig84.ab.vo.PassengerDiscountInfo;
import com.pig84.ab.vo.PassengerHelpFeedback;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.SysDiscount;
import com.pig84.ab.vo.SysMsgInfo;
import com.pig84.ab.vo.SysMsgUser;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_8;

/**
 * 登录注册相关
 * 
 * @author zhangqiang
 * 
 */
@Repository
public class LoginAndRegisterDaoImpl extends BaseDao implements
		ILoginAndRegisterDao {
	
	/**
	 * 检查手机号是否注册
	 * 
	 * @param mobile
	 * @return
	 */
	public String checkMobile(String mobile) {
		String sql = "select passengerid as a1 from passenger_info where telephone = ? limit 1";
		Object[] params = new Object[1];
		params[0] = mobile;
		AppVo_1 vo = queryBean(AppVo_1.class,sql,params);
		if (vo == null) {
			return "0";
		} else {
			return vo.getA1();
		}
	}

	/**
	 * 注册
	 */
	public String register(PassengerInfo user) {
		String sql = "select * from passenger_info where telephone = ?";
		int ishave = queryCount(sql, new Object[] { user.getTelephone() });
		if (ishave > 0) {// 手机号码已注册
			return "0";
		}

		PassengerInfo p = queryBean(PassengerInfo.class,"SELECT MAX(CAST(displayid AS SIGNED INTEGER)) as displayId FROM passenger_info");
		if (p == null || p.getDisplayId() == null|| "".equals(p.getDisplayId())) {
			user.setDisplayId("10000");
		} else {
			user.setDisplayId(String.valueOf(Integer.valueOf(p.getDisplayId()) + 1));
		}

		user.setComments(DESCryptUtil.des(user.getTelephone() + "&"+ user.getComments()));
		int flag = updateData(user,"passenger_info","passengerId");

		if (flag != -1) {
			
			// 注册成功，添加特权
			String sql_dis = "SELECT * FROM sys_discount WHERE iswork = '1' LIMIT 1";
			SysDiscount dis = queryBean(SysDiscount.class, sql_dis);
			if (dis != null) {
				PassengerDiscountInfo vo = new PassengerDiscountInfo();
				vo.setCreateOn(MyDate.Format.DATETIME.now());
				vo.setDiscountid(dis.getId());
				vo.setDisprice(dis.getDisprice());
				vo.setPassengerid(user.getPassengerId());
				vo.setRemark(dis.getRemark());
				vo.setDisstatus("0");

				for (int i = 0; i < Integer.valueOf(dis.getDistimes()); i++) {
					vo.setId(null);
					updateData(vo,"passenger_discount_info", "id");
				}
			}
		}

		return String.valueOf(flag);
	}

	/**
	 * 登录
	 */
	public PassengerInfo login(String telephone, String passWord,String terminal, String passWord_, String imei) {
		String sql = "select * from passenger_info where telephone = ? ";
		Object[] params = new Object[1];
		params[0] = telephone;
		PassengerInfo p = queryBean(PassengerInfo.class, sql, params);
		PassengerInfo user = new PassengerInfo();
		if (p == null) {
			user.setFlag("1");// 用户不存在
			return user;
		}
		if (!p.getPassWord().equals(passWord)) {
			user.setFlag("2");// 密码错误
			return user;
		}

		if ("1".equals(p.getAccountStatus())) {
			user.setFlag("3");// 用户已禁用
			return user;
		}
		if ("1".equals(p.getBlackFlag())) {
			user.setFlag("4");// 用户已拉黑
			return user;
		}
		if (p.getImei() != null && !"".equals(p.getImei()) && imei != null&& !imei.equals(p.getImei())
				&& !p.getTelephone().equals("18520826570")&& !p.getTelephone().equals("15827126859")&& !p.getTelephone().equals("13028872323")
				&& !p.getTelephone().equals("13265830480")&& !p.getTelephone().equals("18664301785")&& !p.getTelephone().equals("18675505850")) {
			user.setFlag("5");// 设备编号已经跟换，需要重新验证
			return user;
		}
		p.setFlag("0"); // 正常

		String sql_u = "";
		String uuid = IdGenerator.uuid();
		p.setSessionId(uuid);
		if (p.getImei() == null || p.getImei().equals("")) {
			if (imei != null && !"null".equals(imei)) {
				sql_u = "update passenger_info set terminal = ?,imei = ?,comments=?,sessionId = ? where telephone = ?";
				params = new Object[5];
				params[0] = terminal;
				params[1] = imei;
				params[2] = DESCryptUtil.des(telephone + "&" + passWord_);
				params[3] = uuid;
				params[4] = telephone;
				executeSQL(sql_u, params);
			}
		} else {
			sql_u = "update passenger_info set terminal = ?,comments=?,sessionId = ? where telephone = ?";
			params = new Object[4];
			params[0] = terminal;
			params[1] = DESCryptUtil.des(telephone + "&" + passWord_);
			params[2] = uuid;
			params[3] = telephone;
			executeSQL(sql_u, params);
		}

		String sql_dis = "select * from passenger_discount_info where passengerid = ? and disstatus = '0'";
		int ishave = queryCount(sql_dis, new Object[] { p.getPassengerId() });
		if (ishave > 0) {
			String sql_msg = "select * from sys_msg_user where msgId = '1001' and userId = ?";
			SysMsgUser msg = queryBean(SysMsgUser.class, sql_msg,new Object[] { p.getPassengerId() });
			if (msg == null) {
				// 消息内容
				String sql_ = "select * from sys_msg_info where msgId = '1001'";
				SysMsgInfo msg_ = queryBean(SysMsgInfo.class, sql_);
				if (msg_ != null) {

					AppVo_4 vo = new AppVo_4();
					vo.setA1(msg_.getMsgTitle());// 标题
					vo.setA2(msg_.getMsgContext());// 消息正文
					vo.setA3("");
					vo.setA4(msg_.getMsgId());// 消息ID
					JPush.sendMessage(vo, telephone.split(","));

					SysMsgUser temp = new SysMsgUser();
					temp.setMsgId("1001");
					temp.setReadFlag("0");
					temp.setSendPhoneNo(telephone);
					temp.setSendTime(MyDate.Format.DATETIME.now());
					temp.setUserId(p.getPassengerId());
					updateData(temp,"sys_msg_user","localId");
				}
			}
		}

		return p;
	}

	/**根据手机号码获取用户信息**/
	public PassengerInfo getPassengerByTel(String tel){
		String sql = "select * from passenger_info where telephone = ? ";
		Object[] params = new Object[1];
		params[0] = tel;
		PassengerInfo user = new PassengerInfo();
		PassengerInfo p = queryBean(PassengerInfo.class, sql, params);
		if (p == null) {
			user.setFlag("1");// 用户不存在
			return user;
		}
		if ("1".equals(p.getAccountStatus())) {
			user.setFlag("3");// 用户已禁用
			return user;
		}
		if ("1".equals(p.getBlackFlag())) {
			user.setFlag("4");// 用户已拉黑
			return user;
		}
		// 正常
		p.setFlag("0");
		return p;
	}
	
	/**
	 * 修改用户信息
	 */
	public String updateUserInfo(String passengerId, String nickName,String headerPicUrl, String sex) {
		String sql = "select * from passenger_info where passengerId = ? ";
		Object[] params = new Object[1];
		params[0] = passengerId;
		PassengerInfo p = queryBean(PassengerInfo.class, sql, params);

		p.setNickName(nickName);
		p.setHeaderPicUrl(headerPicUrl);
		p.setSex(sex);
		int flag = updateData(p,"passenger_info", "passengerId");
		return String.valueOf(flag);
	}

	/**
	 * 反馈建议
	 */
	public String help(String context, String phoneNo, String userid) {
		PassengerHelpFeedback vo = new PassengerHelpFeedback();
		vo.setPassengerId(userid);
		vo.setFeedbackTime(MyDate.Format.DATETIME.now());
		context = filterErrorChar(context);
		vo.setFeedbackContext(context);
		vo.setPhoneNo(phoneNo);
		vo.setStatus("0");

		int flag = updateData(vo,"passenger_help_feedback", "feedbackId");

		if (flag == 1) {
			return "1";// 成功
		} else {
			return "2";// 失败
		}
	}
	
	private String filterErrorChar(String orgnal){
		byte[] b_text = orgnal.getBytes();
		for (int i = 0; i < b_text.length; i++)  
		{  
		    if((b_text[i] & 0xF8)== 0xF0){  
		        for (int j = 0; j < 4; j++) {                          
		        b_text[i+j]=0x30;                     
		    }  
		    i+=3;  
		    }  
		} 
		return new String(b_text);
	}

	/**
	 * 根据用户ID获取用户信息
	 */
	public PassengerInfo updateUserForSession(String userid) {
		String sql = "select * from passenger_info where passengerId = ? ";
		Object[] params = new Object[1];
		params[0] = userid;
		PassengerInfo vo = queryBean(PassengerInfo.class, sql, params);
		return vo;
	}

	/** 根据微信openId获取用户信息 **/
	public PassengerInfo getPassengerByOpenId(String uid,String type) {
		PassengerInfo vo = new PassengerInfo();
		if("1".equals(type)){
			String sql = "select * from passenger_info where openId = ? ";
			Object[] params = new Object[1];
			params[0] = uid;
			vo = queryBean(PassengerInfo.class, sql, params);
		}else{
			String sql = "select * from passenger_info where passengerId = ? ";
			Object[] params = new Object[1];
			params[0] = uid;
			vo = queryBean(PassengerInfo.class, sql, params);
		}
		
		if (vo == null) {
			PassengerInfo vo1 = new PassengerInfo();
			vo1.setFlag("1");// 用户不存在
			return vo1;
		}

		if ("1".equals(vo.getAccountStatus())) {
			vo.setFlag("3");// 用户已禁用
			return vo;
		}
		if ("1".equals(vo.getBlackFlag())) {
			vo.setFlag("4");// 用户已拉黑
			return vo;
		}
		vo.setFlag("0");
		
		return vo;
	}

	/**
	 * 消息列表
	 */
	public List<AppVo_8> msgList(String userid) {
		String sql = "SELECT a.localid AS a1 ,a.userid AS a2,a.msgid AS a3,a.readflag AS a4,a.sendtime AS a5,b.msgtitle AS a6,b.msgcontext AS a7,theModule as a8 FROM sys_msg_user a ,sys_msg_info b WHERE a.msgid = b.msgid AND a.userid = ? AND b.msgType = '1' AND b.issend = '0'  AND LEFT(a.sendtime,10)>= ? ORDER BY a.readFlag ASC, a.sendtime DESC";
		Object[] params = new Object[2];
		params[0] = userid;
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-30);//30天以前
		Date starttime=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = sdf.format(starttime);
		params[1] = newDate;
		List<AppVo_8> list = queryList(AppVo_8.class, sql, params);
		return list;
	}

	/**
	 * 消息详细
	 */
	public AppVo_5 msgInfo(String msgid, String userid) {
		String sql = "SELECT a.sendtime AS a1,b.msgtitle AS a2,b.msgcontext AS a3,b.picUrl AS a4,theModule as a5 FROM sys_msg_user a,sys_msg_info b WHERE a.msgId = b.msgId AND a.userid = ? AND a.msgId = ? ";
		AppVo_5 vo = queryBean(AppVo_5.class, sql, userid, msgid);
		if (vo == null) {
			return null;
		}
		String url = PropertyManage.get("http.root.url");
		if (vo.getA4() != null && !"".equals(vo.getA4())) {
			vo.setA4(url + "/" + vo.getA4());
		} else {
			vo.setA4("");
		}

		String sql_read = "update sys_msg_user set readFlag = '1' where userId = ? and msgid = ? ";
		executeSQL(sql_read, userid, msgid);
		return vo;
	}

	/**
	 * 删除消息
	 */
	public String delMsg(String msgids, String userid) {
		if (msgids != null && !"".equals(msgids)) {
			List<Object> conds = new ArrayList<Object>();
			StringBuilder strs = new StringBuilder();
			String[] ids = msgids.split(",");
			conds.add(userid);
			for (String str : ids) {
				strs.append("?,");
				conds.add(str);
			}
			String sql = "delete from sys_msg_user where userId = ? and msgId in("+ strs.substring(0, strs.length() - 1) + ")";
			int flag = executeSQL(sql, conds.toArray());
			if (flag != -1) {
				return "1";// 成功
			} else {
				return "2";// 失败
			}
		}
		return "2";
	}

	/**
	 * app错误日志添加
	 */
	public String addAppLog(ErrLog err) {
		int flag = updateData(err,"err_log", "errId");
		return flag != -1 ? "1" : "0";
	}

	/**
	 * 获取app版本号
	 */
	public AppVo_5 getVersion() {
		String sql = "SELECT * FROM app_version WHERE TYPE = '0' and apptype = '1' ORDER BY flag DESC LIMIT 0,1";
		AppVersion app = queryBean(AppVersion.class, sql);

		AppVo_5 vo = new AppVo_5();
		vo.setA1(app.getVsn());
		vo.setA2(app.getUrl());
		vo.setA3(app.getFlag());
		vo.setA4(app.getIsmust());
		vo.setA5(app.getInfo());
		
		return vo;
	}

	/** 添加发送信息的内容 **/
	public int addSysMsgInfo(SysMsgInfo msgInfo) {
		int flag = updateData(msgInfo,"sys_msg_info", "msgId");
		if (flag != -1) {
			return 1;
		} else {
			return 0;
		}
	}

	/** 添加系统_消息对应表sys_msg_user **/
	public void addSysMsgUser(SysMsgUser msgUser) {
		updateData(msgUser, "sys_msg_user","localId");
	}

	/** 判断该用户是不是华为员工 **/
	public boolean judgeUserType(String telephone) {
		try {
			String sql = " SELECT telephone from company_passenger WHERE telephone = ? ";
			Object[] params = new Object[1];
			params[0] = telephone;
			int count = queryCount(sql, params);
			if (count > 0) {
				/** 是华为员工 **/
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/** 判断是否送对应的补贴信息 **/
	public AppVo_3 juderSendAllowance(String telephone, String orderNo) {
		String table = " line_allowance AS allow LEFT JOIN company_line AS comline ON allow.lineBaseId = comline.lineBaseId LEFT JOIN company_passenger AS compass ON comline.companyId = comline.companyId ";
		String selectFileds = " allow.statusFlag AS a1,allow.ownAllowance AS a2,allow.otherAllowance AS a3 ";
		AppVo_3 vo = new AppVo_3();
		try {
			String sql = " SELECT "+ selectFileds+ " FROM "+ table+ " WHERE compass.telephone = ? AND allow.lineBaseId in ( SELECT lineBaseId FROM lease_base_info WHERE leaseOrderNo = ?)";
			Object[] params = new Object[2];
			params[0] = telephone;
			params[1] = orderNo;
			vo = queryBean(AppVo_3.class, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return vo;
	}

	/** 获取城市信息 **/
	public AppVo_4 getSysArea(String cityName) {
		String sql = "select arearCode as a1,areaName as a2,fdCode as a3,areaLevel as a4 from sys_area where areaLevel = '2' and areaName like ? limit 1";
		AppVo_4 vo = queryBean(AppVo_4.class, sql, new Object[] { "%"+ cityName + "%" });
		return vo;
	}

	/**
	 * 获取IOS版本
	 */
	public AppVo_5 getIOSVersion(String appType) {
		AppVo_5 result = new AppVo_5();
		String sql = null;
		if (StringUtils.isNotBlank(appType) && "2".equals(appType)) {// 查询司机端应用版本
			sql = "select version as a1,ismust as a2,info as a3 from ios_version where appType = '2' limit 1";
		} else {// 查询乘客端应用版本
			sql = "select version as a1,ismust as a2,info as a3 from ios_version where appType = '1' limit 1";
		}
		AppVo_3 vo = queryBean(AppVo_3.class, sql);
		if (vo == null || vo.getA1() == null || "".equals(vo.getA1())) {
			result.setA1("1.7");
		/*	result.setA2(vo.getA2());
			result.setA3(vo.getA3());*/
		} else {
			result.setA1(vo.getA1());
			result.setA2(vo.getA2());
			result.setA3(vo.getA3());
		}
		return result;
	}

	/**根据用户ID修改openid**/
	public String updateOpenId(String openid,String uid){
		String sql_update = "update passenger_info set openId = '' where openId = ?";
		Object[] params = new Object[1];
		params[0] = openid;
		int result_update = executeSQL(sql_update,params);

		String sql_update_openid = "update passenger_info set openId = ? where passengerId = ?";
		params = new Object[2];
		params[0] = openid;
		params[1] = uid;
		int result_update_openid = executeSQL(sql_update_openid,params);

		if(result_update!= -1 && result_update_openid!= -1){
			return "1";
		}else{
			return "0";
		}
	}
	
	/**查询广告信息**/
	public List<AppVo_5> getAdInfoList(String clientType,String imageResolution,String versionId){
		
		//广告信息存在一下三种情况：
		//1.后台未设置1.6版本的广告，则继续使用1.6版本以前的未过期的广告，如果都过期了，则试用默认广告
		//2.后台设置了1.6版本的广告，则使用1.6的及以前版本未过期的广告
		//3.1.5版本只能使用1.5版本及以前版本的广告
		String sql="";
		if("1".equals(clientType)){
			sql="SELECT vsn AS a1 FROM app_version WHERE flag = ? AND state=0";
		}else{
			sql="SELECT version AS a1 FROM ios_version WHERE flag = ? AND state=0";
		}
		Object[] params=new Object[1];
		params[0]=versionId;
		List<AppVo_1> list=queryList(AppVo_1.class, sql, params);
		if(null!=list&&list.size()>0){
			StringBuffer str = new StringBuffer("SELECT aci.imageUrl AS a1,ac.urlLink AS a2,ac.versionNO AS a3,ac.adTitle AS a4,ac.urlSign AS a5 FROM ad_config ac LEFT JOIN ad_config_image aci ON ac.ad_id = aci.adConfigId " +
					"WHERE ac.adStatus = '1' AND ac.clientType = ? AND aci.imageResolution = ? AND aci.`imageUrl` !='' ");
			List<String> conditionList=new ArrayList<String>();
			conditionList.add(clientType);
			conditionList.add(imageResolution);
			StringBuffer s=new StringBuffer();
			for (AppVo_1 vo : list) {
				s.append(" ac.versionNO like ? or");
				conditionList.add("%"+vo.getA1()+"%");
			}
			str.append(" and ("+s.substring(0,s.length()-2)+") order by ac.operateOn desc ");
			return queryList(AppVo_5.class,str.toString(),conditionList.toArray());
		}
		else{
			return null;
		}
	}
	
	/**根据手机号码更改sessionId**/
	public String updateSessionIdByTel(String tel){
		String uuid = IdGenerator.uuid();
		String sql_u = "update passenger_info set sessionId = ? where telephone = ?";
		Object[] params = new Object[2];
		params[0] = uuid;
		params[1] = tel;
		executeSQL(sql_u, params);
		return uuid;
	}
}
