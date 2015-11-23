package com.amwell.service.impl;

import java.util.Map;

import com.amwell.dao.IBalanceSetDao;
import com.amwell.entity.Search;
import com.amwell.service.IBalanceSetService;
import com.amwell.vo.BalanceSetVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("balanceSetService")
public class BalanceSetServiceImpl implements IBalanceSetService {

	@Autowired
	private IBalanceSetDao balanceSetDao;
	
	public int addBalance(BalanceSetVo balanceSet) {
		if(getSetNameCount(balanceSet)>0){
			return -1;
		}
		return balanceSetDao.addBalance(balanceSet);
	}

	public int getSetNameCount(BalanceSetVo balanceSet) {
		return balanceSetDao.getSetNameCount(balanceSet);
	}

	public Map<String, Object> getBalanceSetList(Search search,
			int currentPageIndex, int pageSize) {
		return balanceSetDao.getBalanceSetList(search,currentPageIndex,pageSize);
	}
}
