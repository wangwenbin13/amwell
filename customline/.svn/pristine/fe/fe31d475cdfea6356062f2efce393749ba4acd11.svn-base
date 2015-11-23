package com.amwell.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ICharteredOrderDao;
import com.amwell.entity.BcOrderVo;
import com.amwell.entity.BcReturnHistoryVo;
import com.amwell.entity.BcReturnVo;
import com.amwell.entity.Search;
import com.amwell.service.ICharteredOrderService;
import com.amwell.vo.bc.BcReturnHistoryEntity;

@Service("charteredOrderService")
public class CharteredOrderServiceImpl implements ICharteredOrderService{

	@Autowired
	private ICharteredOrderDao charteredOrderDao;
	
	/**获取包车订单列表**/
	public Map<String, Object> getBcOrderList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return charteredOrderDao.getBcOrderList(search,currentPageIndex,pageSize);
	}

	/**包车订单详情**/
	public BcOrderVo getBcOrderDetail(BcOrderVo bcOrderVo) {
		return charteredOrderDao.getBcOrderDetail(bcOrderVo);
	}

	/**
	 * 日收入统计列表
	 */
	public Map<String, Object> getBcOrderDayInComeList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return charteredOrderDao.getBcOrderDayInComeList(search,currentPageIndex,pageSize);
	}

	/**
	 * 财务统计-日支出统计列表
	 */
	public Map<String, Object> getBcOrderDayOutComeList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return charteredOrderDao.getBcOrderDayOutComeList(search,currentPageIndex,pageSize);
	}

	/**
	 * 按日期月度账目统计报表
	 */
	public Map<String, Object> queryDateBcOrderList(Search search,
			Integer dateCurrentPageIndex, Integer pageSize) {
		return charteredOrderDao.queryDateBcOrderList(search,dateCurrentPageIndex,pageSize);
	}

	/**
	 * 按商家月度账目统计报表
	 */
	public Map<String, Object> queryBusinessBcOrderList(Search search,
			Integer businessCurrentPageIndex, Integer pageSize) {
		return charteredOrderDao.queryBusinessBcOrderList(search,businessCurrentPageIndex,pageSize);
	}

	/**
	 * 退票订单列表
	 */
	public Map<String, Object> getBcReturnOrderList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return charteredOrderDao.getBcReturnOrderList(search,currentPageIndex,pageSize);
	}

	/**
	 * 操作退票(详情)
	 */
	public BcReturnVo getBcReturnDetail(BcReturnVo bcReturnVo) {
		return charteredOrderDao.getBcReturnDetail(bcReturnVo);
	}

	/**
	 * 执行包车退票操作
	 */
	public int doBcReturn(BcReturnHistoryEntity bcReturnHistoryEntity) {
		int statu = 0;
		if(null!=bcReturnHistoryEntity && !StringUtils.isEmpty(bcReturnHistoryEntity.getOrderId())){
			statu = charteredOrderDao.doBcReturn(bcReturnHistoryEntity);
		}else{
			return statu;
		}
		
		return statu;
	}

	/**
	 * 包车退票记录列表
	 */
	public Map<String, Object> getBcReturnHistoryList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return charteredOrderDao.getBcReturnHistoryList(search,currentPageIndex,pageSize);
	}

	/**
	 * 包车退票记录列表-退票详情
	 */
	public BcReturnHistoryVo getBcReturnHistoryDetail(
			BcReturnHistoryVo bcReturnHistoryVo) {
		return charteredOrderDao.getBcReturnHistoryDetail(bcReturnHistoryVo);
	}

	/**包车优惠券列表**/
	public Map<String, Object> queryBusCouponStatList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		return charteredOrderDao.queryBusCouponStatList(search,currentPageIndex,pageSize);
	}

	/**检查该订单是否可以退票(如果包含优惠券,不与许退票)**/
	public int checkBcOrde(BcReturnVo bcReturnVo) {
		return charteredOrderDao.checkBcOrde(bcReturnVo);
	}

}
