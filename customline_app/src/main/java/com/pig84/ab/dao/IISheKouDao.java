package com.pig84.ab.dao;

import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_3;


/**
 * i蛇口相关接口
 * @author zhangqiang
 *
 */
public interface IISheKouDao {

	/**用户登录**/
	public PassengerInfo login(String telephone);
	
	/**获取蛇口APP最新版本**/
	public AppVo_3 getVersion();
}
