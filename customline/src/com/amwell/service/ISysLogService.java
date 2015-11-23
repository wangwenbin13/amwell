package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.SysLogVo;

public interface ISysLogService {
	
	/**
	 * 查询操作日志数据列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getSysLogList(Search search,int currentPage, int pageSize);
	
	
	/**
	 *  添加操作日志
	 * @param sysLogVo
	 * @return
	 * @throws Exception
	 */
	int  addSysLog(SysLogVo sysLogVo)throws Exception;

	int appendLog(String userId, String ip,String action,String operateResult);
}
