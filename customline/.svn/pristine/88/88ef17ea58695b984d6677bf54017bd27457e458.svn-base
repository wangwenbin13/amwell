package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amwell.commons.MyDate;
import com.amwell.dao.ISysLogDao;
import com.amwell.entity.Search;
import com.amwell.service.ISysLogService;
import com.amwell.vo.SysLogVo;

/**
 * 系统操作日志
 * @author Administrator
 *
 */
@Service("sysLogService")
public class SysLogServiceImpl implements ISysLogService{
	
	@Autowired 
	ISysLogDao iSysLogDao;
	
	
	/* 
	 *查询操作日志数据列表
	 */
	public	Map<String, Object> getSysLogList(Search search,int currentPage, int pageSize){
		
		return iSysLogDao.getSysLogList(search, currentPage, pageSize);
	}
	
	
	/* 
	 *  添加操作日志
	 */
	public int  addSysLog(SysLogVo sysLogVo)throws Exception{
		return iSysLogDao.addSysLog(sysLogVo);
	}

	public int appendLog(String userId, String ip, String action,String operateResult) {
		if(StringUtils.hasText(userId)){
			SysLogVo log = new SysLogVo();
			log.setAction(action);
			log.setOperateResult(operateResult);
			log.setUserId(userId);
			log.setUserIp(ip);
			//0:运营平台 1:调度平台
			log.setSysType("0");
			log.setOperateTime(MyDate.getMyDateLong());
			try {
				return this.addSysLog(log);
			} catch (Exception e) {
			}
		}
		return 0;
	}

}
