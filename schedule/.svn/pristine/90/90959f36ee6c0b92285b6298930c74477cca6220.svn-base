package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ILeaseOrderDao;
import com.amwell.entity.Search;
import com.amwell.service.ILeaseOrderService;

@Service("leaseOrderService")
public class LeaseOrderServiceImpl implements ILeaseOrderService {

	@Autowired
	private ILeaseOrderDao iLeaseOrderDao;

	/**
	 * 日收入统计
	 */
	public Map<String, Object> queryStatDayStatTotalList(Search search, Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.queryStatDayStatTotalList(search, currentPageIndex, pageSize);
	}

	/**
	 * 日期月度账目统计
	 */
	public Map<String, Object> queryStatDateStatTotalList(Search search, Integer dateCurrentPageIndex,
			Integer pageSize) {
		return iLeaseOrderDao.queryStatDateStatTotalList(search, dateCurrentPageIndex, pageSize);
	}

	/**
	 * 按商家月度账目统计报表
	 */
	public Map<String, Object> queryStatBusinessStatTotalList(Search search, Integer businessCurrentPageIndex,
			Integer pageSize) {
		return iLeaseOrderDao.queryStatBusinessStatTotalList(search, businessCurrentPageIndex, pageSize);
	}

	/**
	 * 按线路月度账目统计报表
	 */
	public Map<String, Object> queryStatLineStatTotalList(Search search, Integer lineCurrentPageIndex,
			Integer pageSize) {
		return iLeaseOrderDao.queryStatLineStatTotalList(search, lineCurrentPageIndex, pageSize);
	}

	/**
	 * 支出统计列表
	 */
	public Map<String, Object> getStatOutListByPage(Search search, Integer currentPageIndex, Integer pageSize) {
		return iLeaseOrderDao.getStatOutListByPage(search, currentPageIndex, pageSize);
	}

}
