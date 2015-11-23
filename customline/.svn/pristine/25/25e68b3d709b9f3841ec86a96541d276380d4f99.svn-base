package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.bc.BcbusinessBiddingVo;
import com.amwell.vo.bc.CharteredLineVo;

public interface ICharteredLineDao {

	/**
	 *  获取包车线路列表
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getCharteredLineList(Search search,int currentPageIndex, int pageSize);
	
	/**
	 * 查询包车信息详情
	 * @param bcLineId
	 * @return
	 */
	CharteredLineVo getCharteredLineDetail (String bcLineId);
	
	/**
	 * 选择商家弹窗
	 * @return
	 */
	Map<String, Object> getSelectMerchant (MgrBusinessEntity model);
	
	/**
	 * 发送包车信息给商家
	 * @param model
	 * @return
	 */
	int sendBcToBusiness(BcbusinessBiddingVo model);
	
	/**
	 *改变包车线路状态
	 * @param model
	 * @return
	 */
	Integer setOutAudit(CharteredLineVo model);
	
	/**
	 * 查询商家报价列表
	 * @param bcLineId
	 * @return
	 */
	Map<String, Object> getBusinessOfferList(String bcLineId);

	/**
	 * 查询商家报价详情
	 * @param offerId
	 * @return
	 */
	BcbusinessBiddingVo getBusinessOfferDetail(String offerId);
	
	/**
	 * 商家报价表里的车辆信息
	 * @param biddingId
	 * @return
	 */
	Map<String, Object> getBiddingCars (String biddingId);
	
	/**
	 * 报价过期后改变报价状态
	 * @return
	 */
	int  judgeOfferTime();

	/**
	 * 查询商家电话
	 * @return
	 */
	String getBusinessPhone(String businessId);
	

}
