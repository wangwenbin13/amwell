package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.SysLogVo;

/**
 * 操作日志列表
 * @author Administrator
 *
 */
public interface ISysLogDao {
	/**
	 * 操作是志列表查询
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
	 */
	int  addSysLog(SysLogVo sysLogVo)throws Exception;

}
