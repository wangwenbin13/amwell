package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;

public interface ILeaseOrderDao {

	/**
	 * 日收入统计
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryStatDayStatTotalList(Search search, Integer currentPageIndex, Integer pageSize);

	/**
	 * 日期月度账目统计
	 * 
	 * @param search
	 * @param dateCurrentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryStatDateStatTotalList(Search search, Integer dateCurrentPageIndex,
			Integer pageSize);

	/**
	 * 按商家月度账目统计报表
	 * 
	 * @param search
	 * @param businessCurrentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryStatBusinessStatTotalList(Search search, Integer businessCurrentPageIndex,
			Integer pageSize);

	/**
	 * 按线路月度账目统计报表
	 * 
	 * @param search
	 * @param lineCurrentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> queryStatLineStatTotalList(Search search, Integer lineCurrentPageIndex,
			Integer pageSize);

	/**
	 * 支出统计列表
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getStatOutListByPage(Search search, Integer currentPageIndex, Integer pageSize);
}
