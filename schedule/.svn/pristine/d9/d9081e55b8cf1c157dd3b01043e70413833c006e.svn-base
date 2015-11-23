package com.amwell.dao;

import java.util.Map;

import com.amwell.action.bc.BusinessBiddingForm;
import com.amwell.entity.Search;
import com.amwell.vo.bc.AlreadyQuoteLineVo;
import com.amwell.vo.bc.BcLineVo;
import com.amwell.vo.bc.BusinessScheduleVo;

public interface IBusinessBiddingDao {

	/**
	 * 获取商户待报价列表
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getWaitQuoteList(Search search, int currentPageIndex, int pageSize);

	/**
	 * 获取商户待报价详情
	 * 
	 * @param businessId
	 * @param bc_line_id
	 * @return
	 */
	BcLineVo getWaitQuoteDetail(String businessId, String bc_line_id);

	/**
	 * 新增报价
	 * 
	 * @param biddingForm
	 * @return
	 */
	int addBusinessBidding(BusinessBiddingForm biddingForm);

	/**
	 * 获取包车管理已报价和已过期
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getAlreadyQuoteList(Search search, int currentPageIndex, int pageSize);

	/**
	 * 获取包车管理已报价和已过期 详情
	 * 
	 * @param businessId
	 * @param bcLineId
	 * @return
	 */
	AlreadyQuoteLineVo getAlreadytQuoteDetail(String businessId, String bcLineId);

	/**
	 * 获取商户订单各种状态的线路列表
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getBCAllLineList(Search search, int currentPageIndex, int pageSize);

	BusinessScheduleVo getBusinessOrderDetail(String orderNo);

	int scheduleDriverAndCar(BusinessScheduleVo vo);

}
