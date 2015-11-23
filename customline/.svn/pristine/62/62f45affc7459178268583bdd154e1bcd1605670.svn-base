package com.amwell.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ICharteredLineDao;
import com.amwell.entity.Search;
import com.amwell.service.ICharteredLineService;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.bc.BcbusinessBiddingVo;
import com.amwell.vo.bc.CharteredLineVo;

@Service("charteredLineService")
public class CharteredLineServiceImpl implements ICharteredLineService {

	@Autowired
	private ICharteredLineDao charteredLineDao;

	/* 
	 * 获取包车线路列表
	 */
	public Map<String, Object> getCharteredLineList(Search search,int currentPageIndex, int pageSize) {
		return charteredLineDao.getCharteredLineList(search,currentPageIndex, pageSize);
	}

	/*
	 * 包车用户详情
	 */
	public CharteredLineVo getCharteredLineDetail(String bc_line_id) {
		
		return charteredLineDao.getCharteredLineDetail(bc_line_id);
	}
	
	/**
	 * 选择商家弹窗
	 * @return
	 */
	public Map<String, Object> getSelectMerchant (MgrBusinessEntity model){
		return charteredLineDao.getSelectMerchant(model);
	}
	
	

	
	/**
	 * 发送包车信息给商家
	 * @param model
	 * @return
	 */
	public int sendBcToBusiness(BcbusinessBiddingVo model){
		return charteredLineDao.sendBcToBusiness(model);
	}
	
	/**
	 *改变包车线路状态
	 * @param model
	 * @return
	 */
	public Integer setOutAudit(CharteredLineVo model){
		return charteredLineDao.setOutAudit(model);
	}

	/**
	 * 查询商家报价列表
	 * @param bcLineId
	 * @return
	 */
	public Map<String, Object> getBusinessOfferList (String bcLineId) {
		return charteredLineDao.getBusinessOfferList(bcLineId);
	}
	
	
	/**
	 * 查询商家报价详情
	 * @param offerId
	 * @return
	 */
	public BcbusinessBiddingVo getBusinessOfferDetail(String offerId){
		return charteredLineDao.getBusinessOfferDetail(offerId);
	}
	
	/**
	 * 商家报价表里的车辆信息
	 * @param biddingId
	 * @return
	 */
	public Map<String, Object> getBiddingCars (String biddingId){
		return charteredLineDao.getBiddingCars(biddingId);
	}
	
	/**
	 * 报价过期后改变报价状态
	 * @return
	 */
	public int  judgeOfferTime(){
		return charteredLineDao.judgeOfferTime();
	}
	
	/* 
	 * 查询商家电话
	 */
	public String getBusinessPhone(String businessId){
		return charteredLineDao.getBusinessPhone(businessId);
	}

	
	

	public int sendBusinesses(String bc_line_id, List<String> bussinessIds,
			String operatorId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String rejectCharteredLine(String bc_line_id, String rejectRemark,
			String operatorId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
