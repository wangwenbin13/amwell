package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IPassengerLineDao;
import com.amwell.entity.Search;
import com.amwell.service.IPassengerLineService;
import com.amwell.vo.pc.PassengerLineVo;

@Service("passengerLineService")
public class PassengerLineServiceImpl implements IPassengerLineService {

	@Autowired
	private IPassengerLineDao passengerLineDao;
	
	public Map<String, Object> getLineList(Search search, int currentPageIndex,
			int pageSize) {
		return passengerLineDao.getLineList(search,currentPageIndex,pageSize);
	}

	public PassengerLineVo getLineDetail(String pcLineId) {
		return passengerLineDao.getPcLineDetail(pcLineId);
	}


	public int updateLineOnOff(String pcLineId, int lineOnOff) {
		return passengerLineDao.updateLineOnOff(pcLineId,lineOnOff);
	}

}
