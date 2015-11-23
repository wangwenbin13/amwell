package com.pig84.ab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.IISheKouDao;
import com.pig84.ab.service.IISheKouService;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_3;
/**
 * i蛇口相关
 * @author zhangqiang
 *
 */
@Service
public class ISheKouServiceImpl implements IISheKouService {

	@Autowired
	private IISheKouDao ishekou;

	/**用户登录**/
	public PassengerInfo login(String telephone) {
		return ishekou.login(telephone);
	}
	
	/**获取蛇口APP最新版本**/
	public AppVo_3 getVersion(){
		return ishekou.getVersion();
	}
}
