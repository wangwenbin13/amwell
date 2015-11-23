package com.pig84.ab.dao.impl;

import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ICaiShengHuoDao;
import com.pig84.ab.utils.DESCryptUtil;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.Sha1Utils;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_3;

/**
 * i蛇口业务相关
 * @author zhangqiang
 *
 */
@Repository
public class CaiShengHuoDaoImpl extends BaseDao implements ICaiShengHuoDao {

	/**用户登录**/
	public String login(String username,String mobile,String password,String cityName){
		String sql = "select * from passenger_info where telephone = ?";
		Object[] params = new Object[1];
		params[0] = mobile;
		PassengerInfo user = queryBean(PassengerInfo.class, sql,params);
		
		if(user == null){//用户在小猪巴士未注册
			PassengerInfo newUser = new PassengerInfo();
			newUser.setNickName(username);//用户昵称
			newUser.setSex("0");//性别
			newUser.setTelephone(mobile);//手机号码
			newUser.setPassWord(Sha1Utils.encrypt(mobile+"&123456"));//密码加密规则：手机号码+"&"+密码
			newUser.setRegisterTime(MyDate.Format.DATETIME.now());//注册时间
			newUser.setTwoCodeValue("");//二维码
			newUser.setHeaderPicUrl("");//头像地址
			newUser.setAccountStatus("0");//帐号状态
			newUser.setComments(DESCryptUtil.des(mobile+"&123456"));//备用字段
			newUser.setSourcefrom("2");//用户来源
			newUser.setCaishenghuoremark(password); //彩生活备用字段
			
			String sql_cityname = "select arearCode as a1,areaName as a2,fdCode as a3 from sys_area where areaLevel = '2' and areaName like ? limit 1";
			Object[] params1 = new Object[1];
			params1[0] = "'%"+cityName.replace("市","")+"%'";
			AppVo_3 vo_cityName = queryBean(AppVo_3.class, sql_cityname,params1);
			if(vo_cityName!=null){
				newUser.setCityName(vo_cityName.getA2());
				newUser.setCityCode(vo_cityName.getA1());
				newUser.setProvinceCode(vo_cityName.getA3());
			}else{
				newUser.setCityName("深圳市");
				newUser.setCityCode("1607");
				newUser.setProvinceCode("19");
			}
			//回显ID
			PassengerInfo p = queryBean(PassengerInfo.class,"SELECT MAX(CAST(displayid AS SIGNED INTEGER)) as displayId FROM passenger_info");
			if(p==null || p.getDisplayId()==null || "".equals(p.getDisplayId())){
				newUser.setDisplayId("10000");
			}else{
				newUser.setDisplayId(String.valueOf(Integer.valueOf(p.getDisplayId())+1));
			}
			int flag = updateData(newUser,"passenger_info","passengerId");
			
			if(flag!=-1){//注册成功
				return newUser.getPassengerId();
			}else{
				return "0";
			}
			
		}else{
			if(user.getCaishenghuoremark() !=null && user.getCaishenghuoremark().equals(password)){
				return user.getPassengerId();
			}else{
				//如果是彩生活第一次登录 则保存其密码
				if(user.getCaishenghuoremark()==null||user.getCaishenghuoremark().isEmpty()){
					sql = "update passenger_info set caishenghuoremark=? where passengerId=?";
					Object[] params1 = new Object[2];
					params1[0] = password;
					params1[1] = user.getPassengerId();
					executeSQL(sql, params1);
					return user.getPassengerId();
				}else{
					return "2"; //密码错误
				}
			}
		}
	}
	
}
