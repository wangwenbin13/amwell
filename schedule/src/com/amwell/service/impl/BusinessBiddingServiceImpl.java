package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.action.bc.BusinessBiddingForm;
import com.amwell.dao.IBusinessBiddingDao;
import com.amwell.entity.Search;
import com.amwell.service.IBusinessBiddingService;
import com.amwell.vo.bc.AlreadyQuoteLineVo;
import com.amwell.vo.bc.BcLineVo;
import com.amwell.vo.bc.BusinessScheduleVo;

@Service("businessBiddingService")
public class BusinessBiddingServiceImpl implements IBusinessBiddingService {

	@Autowired
	private IBusinessBiddingDao businessBiddingDao;

	public Map<String, Object> getWaitQuoteList(Search search, int currentPageIndex, int pageSize) {
		return businessBiddingDao.getWaitQuoteList(search, currentPageIndex, pageSize);
	}

	public BcLineVo getWaitQuoteDetail(String businessId, String bc_line_id) {
		return businessBiddingDao.getWaitQuoteDetail(businessId, bc_line_id);
	}

	public int addBusinessBidding(BusinessBiddingForm biddingForm) {
		return businessBiddingDao.addBusinessBidding(biddingForm);
	}

	/**
	 * 获取包车管理已报价和已过期
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getAlreadyQuoteList(Search search, int currentPageIndex, int pageSize) {
		return businessBiddingDao.getAlreadyQuoteList(search, currentPageIndex, pageSize);
	}

	/**
	 * 获取包车管理已报价和已过期 详情
	 * 
	 * @param businessId
	 * @param bcLineId
	 * @return
	 */
	public AlreadyQuoteLineVo getAlreadytQuoteDetail(String businessId, String bcLineId) {
		return businessBiddingDao.getAlreadytQuoteDetail(businessId, bcLineId);
	}

	public Map<String, Object> getBCAllLineList(Search search, int currentPageIndex, int pageSize) {
		return businessBiddingDao.getBCAllLineList(search, currentPageIndex, pageSize);
	}

	public BusinessScheduleVo getBusinessOrderDetail(String orderNo) {
		return businessBiddingDao.getBusinessOrderDetail(orderNo);
	}

	public int scheduleDriverAndCar(BusinessScheduleVo vo) {
		return businessBiddingDao.scheduleDriverAndCar(vo);
	}

}
