package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.pc.PassengerLineVo;

public interface IPassengerLineService {

	Map<String, Object> getLineList(Search search, int currentPageIndex,int pageSize);
			
	PassengerLineVo getLineDetail(String pcLineId);

	int updateLineOnOff(String pcLineId, int lineOnOff);

}
