package com.amwell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.dao.ISysAppSendDao;
import com.amwell.service.ISysAppSendService;
import com.amwell.vo.SysAppSend;


/**
 * 发送类型和短信商家信息表service实现类
 * @author 龚雪婷
 *
 */
@Service("sysAppSendService")
public class SysAppSendServiceImpl implements ISysAppSendService {

	@Autowired
	private ISysAppSendDao dao;

	/**
	 * 查询短信发送商家
	 * @return
	 */
	@Override
	public SysAppSend getMessageBusiness() {
		return dao.getMessageBusiness();
	}

}
