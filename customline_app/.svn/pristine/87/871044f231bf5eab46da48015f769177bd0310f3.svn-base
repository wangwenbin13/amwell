package com.pig84.ab.dao.impl;

import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IISheKouDao;
import com.pig84.ab.utils.DESCryptUtil;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.Sha1Utils;
import com.pig84.ab.vo.AppVersion;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_3;

/**
 * i蛇口业务相关
 * @author zhangqiang
 *
 */
@Repository
public class ISheKouDaoImpl extends BaseDao implements IISheKouDao {

	/**用户登录**/
	public PassengerInfo login(String telephone){
		String sql = "select * from passenger_info where telephone = ?";
		Object[] params = new Object[1];
		params[0] = telephone;
		PassengerInfo user = queryBean(PassengerInfo.class, sql,params);
		
		if(user == null){//用户在小猪巴士未注册
			PassengerInfo newUser = new PassengerInfo();
			newUser.setNickName(telephone);//用户昵称
			newUser.setSex("0");//性别
			newUser.setTelephone(telephone);//手机号码
			newUser.setPassWord(Sha1Utils.encrypt(telephone+"&123456"));//密码加密规则：手机号码+"&"+密码
			newUser.setRegisterTime(MyDate.Format.DATETIME.now());//注册时间
			newUser.setTwoCodeValue("");//二维码
			newUser.setHeaderPicUrl("");//头像地址
			newUser.setAccountStatus("0");//帐号状态
			newUser.setComments(DESCryptUtil.des(telephone+"&123456"));//备用字段
			newUser.setSourcefrom("1");//用户来源
			//回显ID
			PassengerInfo p = queryBean(PassengerInfo.class,"SELECT MAX(CAST(displayid AS SIGNED INTEGER)) as displayId FROM passenger_info");
			if(p==null || p.getDisplayId()==null || "".equals(p.getDisplayId())){
				newUser.setDisplayId("10000");
			}else{
				newUser.setDisplayId(String.valueOf(Integer.valueOf(p.getDisplayId())+1));
			}
			int flag = updateData(newUser,"passenger_info","passengerId");
			
			if(flag!=-1){//注册成功
				return newUser;
			}else{
				return null;
			}
			
		}else{
			return user;
		}
	}
	
	/**
	 * 获取app版本号
	 */
	public AppVo_3 getVersion() {
		String sql = "SELECT * FROM app_version WHERE TYPE = '1' ORDER BY flag DESC LIMIT 0,1";
		AppVersion app = queryBean(AppVersion.class, sql);
		
		AppVo_3 vo = new AppVo_3();
		vo.setA1(app.getVsn());
		vo.setA2(app.getUrl());
		vo.setA3(app.getFlag());
		return vo;
	}
}
