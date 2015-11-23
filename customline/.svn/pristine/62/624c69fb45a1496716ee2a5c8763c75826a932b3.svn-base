package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.BcOrderVo;
import com.amwell.entity.BcReturnHistoryVo;
import com.amwell.entity.BcReturnVo;
import com.amwell.entity.Search;
import com.amwell.vo.bc.BcReturnHistoryEntity;

public interface ICharteredOrderDao {

	/**获取包车订单列表**/
	Map<String, Object> getBcOrderList(Search search, Integer currentPageIndex,
			Integer pageSize);

	/**包车订单详情**/
	BcOrderVo getBcOrderDetail(BcOrderVo bcOrderVo);

	/**
	 * 日收入统计列表
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getBcOrderDayInComeList(Search search,
			Integer currentPageIndex, Integer pageSize);

	/**
	 * 财务统计-日支出统计列表
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getBcOrderDayOutComeList(Search search,
			Integer currentPageIndex, Integer pageSize);

	/**
	 * 按日期月度账目统计报表
	 * @param search
	 * @param dateCurrentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> queryDateBcOrderList(Search search,
			Integer dateCurrentPageIndex, Integer pageSize);

	/**
	 * 按商家月度账目统计报表
	 * @param search
	 * @param businessCurrentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> queryBusinessBcOrderList(Search search,
			Integer businessCurrentPageIndex, Integer pageSize);

	/**
	 * 退票订单列表
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getBcReturnOrderList(Search search,
			Integer currentPageIndex, Integer pageSize);

	/**
	 * 操作退票(详情)
	 * @param bcReturnVo
	 * @return
	 */
	BcReturnVo getBcReturnDetail(BcReturnVo bcReturnVo);

	/**
	 * 执行包车退票操作
	 * @param bcReturnHistoryEntity
	 * @return
	 */
	int doBcReturn(BcReturnHistoryEntity bcReturnHistoryEntity);

	/**
	 * 包车退票记录列表
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getBcReturnHistoryList(Search search,
			Integer currentPageIndex, Integer pageSize);

	/**
	 * 包车退票记录列表-退票详情
	 * @param bcReturnHistoryVo
	 * @return
	 */
	BcReturnHistoryVo getBcReturnHistoryDetail(
			BcReturnHistoryVo bcReturnHistoryVo);

	/**包车优惠券列表**/
	Map<String, Object> queryBusCouponStatList(Search search,
			Integer currentPageIndex, Integer pageSize);

	/**检查该订单是否可以退票(如果包含优惠券,不与许退票)**/
	int checkBcOrde(BcReturnVo bcReturnVo);

}
