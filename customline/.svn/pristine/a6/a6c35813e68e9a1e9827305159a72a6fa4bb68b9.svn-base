package com.amwell.service;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.bc.BcbusinessBiddingVo;
import com.amwell.vo.bc.CharteredLineVo;

public interface ICharteredLineService {

	/**
	 * 获取包车线路列表
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getCharteredLineList(Search search,int currentPageIndex, int pageSize);

	/**
	 * 获取包车线路详情
	 * @param bc_line_id
	 * @return
	 */
	public CharteredLineVo getCharteredLineDetail(String bc_line_id);
	
	/**
	 * 选择商家弹窗
	 * @return
	 */
	Map<String, Object> getSelectMerchant (MgrBusinessEntity model);

	/**
	 * 发送商家
	 * @param bc_line_id
	 * @param bussinessIds
	 * @param operatorId
	 * @return
	 */
	public int sendBusinesses(String bc_line_id, List<String> bussinessIds,String operatorId);
	
	/**
	 * 驳回用户包车线路
	 * @param bc_line_id
	 * @param rejectRemark
	 * @param operatorId
	 * @return
	 */
	public String  rejectCharteredLine(String bc_line_id,String  rejectRemark,String operatorId);
	
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
	 * @param phone
	 * @return
	 */
	String getBusinessPhone(String businessId);
}
