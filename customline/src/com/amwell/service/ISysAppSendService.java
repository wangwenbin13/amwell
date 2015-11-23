package com.amwell.service;

import com.amwell.vo.SysAppSend;

/**
 * 发送类型和短信商家信息表service接口类
 * @author 龚雪婷
 *
 */
public interface ISysAppSendService {

	/**
	 * 查询短信发送商家
	 * @return
	 */
	SysAppSend getMessageBusiness();
	
}
