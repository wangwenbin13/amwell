package com.amwell.service;

import java.util.Map;

import com.amwell.action.bc.BusinessBiddingForm;
import com.amwell.entity.Search;
import com.amwell.vo.bc.AlreadyQuoteLineVo;
import com.amwell.vo.bc.BcLineVo;
import com.amwell.vo.bc.BusinessScheduleVo;

public interface IBusinessBiddingService {

	/**
	 * 获取商户待报价列表
	 * @param search 查询条件
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getWaitQuoteList(Search search, int currentPageIndex,int pageSize);

    /**
     * 获取商户待报价详情
     * @param businessId 商户ID
     * @param bc_line_id 线路ID
     * @return
     */
	BcLineVo getWaitQuoteDetail(String businessId, String bc_line_id);
			
	/**
	 * 添加商户报价
	 * @param biddingForm
	 * @return
	 */
	public int addBusinessBidding(BusinessBiddingForm biddingForm);
	
	/**
	 * 获取包车管理已报价和已过期
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getAlreadyQuoteList(Search search, int currentPageIndex,int pageSize);
			
	
	/**
	 * 获取包车管理已报价和已过期 详情
	 * @param businessId
	 * @param bcLineId
	 * @return
	 */
	AlreadyQuoteLineVo getAlreadytQuoteDetail(String businessId, String bcLineId);

	/**
	 * 获取商户订单各种状态的线路列表
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getBCAllLineList(Search search, int currentPageIndex,int pageSize);

	/**
	 * 获取订单线路详情
	 * @param orderNo
	 * @return
	 */
	BusinessScheduleVo getBusinessOrderDetail(String orderNo);

	/**
	 * 商户调度司机和车辆
	 * @param vo
	 * @return
	 */
	int scheduleDriverAndCar(BusinessScheduleVo vo);
			

}
