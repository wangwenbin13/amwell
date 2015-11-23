package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.SysParamVo;

public interface ISysParamDAO {

	Map<String, Object> getSysParamList(Search search, int currentPageIndex,int pageSize);
			

	int getSameSysParamCount(String id, short paramType, String paramName);


	int addSysParam(SysParamVo sysParamVo);


	int updateSysParam(SysParamVo sysParamVo);

}
