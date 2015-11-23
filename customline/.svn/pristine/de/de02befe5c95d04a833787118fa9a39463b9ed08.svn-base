package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.SysParamVo;

public interface ISysParamService {

	Map<String, Object> getSysParamList(Search search, int currentPageIndex,int pageSize);

	int addSysParam(SysParamVo sysParamVo);
	
	String addSysParamWithValidate(SysParamVo sysParamVo);

	public String validateSysParam(SysParamVo sysParam);

	int updateSysParam(SysParamVo sysParamVo);
}
