package com.amwell.dao.impl;

import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.ISysAppSendDao;
import com.amwell.vo.SysAppSend;

/**
 * 发送类型和短信商家信息表dao实现类
 * @author 龚雪婷
 *
 */
@Repository
public class SysAppSendDaoImpl extends DaoSupport implements ISysAppSendDao {

	/**
	 * 查询短信发送商家
	 * @return
	 */
	@Override
	public SysAppSend getMessageBusiness() {
		this.finit("sys_app_send");
		String sql="SELECT business FROM sys_app_send LIMIT 0,1";
		SysAppSend sas=this.tableDao.queryBean(SysAppSend.class, sql);
		return sas;
	}

	
}
