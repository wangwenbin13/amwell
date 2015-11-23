package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.BalanceSetVo;

public interface IBalanceSetService {

	/*
	 * 创建保底结算模板
	 */
	int addBalance(BalanceSetVo balanceSet);

	/* 
	 * 查询保底结算
	 */
	Map<String, Object> getBalanceSetList(Search search,int currentPageIndex, int pageSize);
	

	/* 
	 * 验证是否有模板名称重名
	 */
	int getSetNameCount(BalanceSetVo balanceSet);
	
}
