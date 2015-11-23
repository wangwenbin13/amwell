package com.pig84.ab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.ICharteredMyCarDao;
import com.pig84.ab.service.ICharteredMyCarService;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_17;
import com.pig84.ab.vo.bean.AppVo_18_list;
import com.pig84.ab.vo.bean.AppVo_22_list;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_9;

@Service("charteredMyCarService")
public class CharteredMyCarServiceImpl implements ICharteredMyCarService{

	@Autowired
	private ICharteredMyCarDao charteredMyCarDao;

	/**
	 * 我的包车---已支付列表
	 */
	public List<AppVo_6> getMyCarHasPayList(String userid,String currentPage,String pageSize) {
		return charteredMyCarDao.getMyCarHasPayList(userid,currentPage,pageSize);
	}

	/**
	 * 包车订单详情
	 */
	public AppVo_18_list getMyCarOrderDetail(String bcLineId) {
		return charteredMyCarDao.getMyCarOrderDetail(bcLineId);
	}

	/**
	 * 我的包车---包车订单详情---需求详情
	 */
	public AppVo_17 getMyCarOrderBcLineDetail(String bcLineId) {
		return charteredMyCarDao.getMyCarOrderBcLineDetail(bcLineId);
	}

	/**
	 * 我的包车---包车详情---订单(报价)详情
	 */
	public AppVo_22_list getMyCarOrderBusinessBidDetail(String businessBid) {
		return charteredMyCarDao.getMyCarOrderBusinessBidDetail(businessBid);
	}

	/**
	 * 我的包车---历史记录
	 */
	public List<AppVo_9> getMyCarOrderHistoryList(String userid, String currentPage,
			String pageSize) {
		return charteredMyCarDao.getMyCarOrderHistoryList(userid,currentPage,pageSize);
	}

	/**
	 * 我的包车---历史记录---删除历史记录
	 */
	public String delMyCarOrderHistoryList(String ids) {
		return charteredMyCarDao.delMyCarOrderHistoryList(ids);
	}
	
	/**查询已有报价包车的商家报价列表**/
	public List<AppVo_3> getBusinessBiddingList(String charteredLineId){
		return charteredMyCarDao.getBusinessBiddingList(charteredLineId);
	}
	
	/**查询包车需求详情**/
	public AppVo_15 getCharteredLineDetail(String charteredLineId){
		return charteredMyCarDao.getCharteredLineDetail(charteredLineId);
	}
}
