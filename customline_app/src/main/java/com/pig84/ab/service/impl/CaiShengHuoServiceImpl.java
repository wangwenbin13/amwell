package com.pig84.ab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.ICaiShengHuoDao;
import com.pig84.ab.service.ICaiShengHuoService;
/**
 * 彩生活相关
 * @author zhangqiang
 *
 */
@Service
public class CaiShengHuoServiceImpl implements ICaiShengHuoService {

	@Autowired
	private ICaiShengHuoDao caishenghuo;

	/**用户登录**/
	public String login(String username,String mobile,String password,String cityName){
		return caishenghuo.login(username,mobile,password,cityName);
	}
}
