package com.pig84.ab.service;

import java.util.List;

import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_17;
import com.pig84.ab.vo.bean.AppVo_18_list;
import com.pig84.ab.vo.bean.AppVo_22_list;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_9;

public interface ICharteredMyCarService {

	/**
	 * 我的包车---已支付列表
	 * @param userid
	 * @param valueOf
	 * @param valueOf2
	 * @return
	 */
	List<AppVo_6> getMyCarHasPayList(String userid, String currentPage,String pageSize);

	/**
	 * 包车订单详情
	 * @param bcOrderId
	 * @return
	 */
	AppVo_18_list getMyCarOrderDetail(String bcLineId);

	/**
	 * 我的包车---包车订单详情---需求详情
	 * @param bcLineId
	 * @return
	 */
	AppVo_17 getMyCarOrderBcLineDetail(String bcLineId);

	/**
	 * 我的包车---包车详情---订单(报价)详情
	 * @param businessBid
	 * @return
	 */
	AppVo_22_list getMyCarOrderBusinessBidDetail(String businessBid);

	/**
	 * 我的包车---历史记录
	 * @param userid
	 * @param valueOf
	 * @param valueOf2
	 * @return
	 */
	List<AppVo_9> getMyCarOrderHistoryList(String userid, String currentPage,
			String pageSize);

	/**
	 * 我的包车---历史记录---删除历史记录
	 * @param ids
	 * @return
	 */
	String delMyCarOrderHistoryList(String ids);
	
	/**查询已有报价包车的商家报价列表**/
	public List<AppVo_3> getBusinessBiddingList(String charteredLineId);
	
	/**查询包车需求详情**/
	public AppVo_15 getCharteredLineDetail(String charteredLineId);

}
