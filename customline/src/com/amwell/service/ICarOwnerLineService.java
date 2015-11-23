package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.pc.CarOwnerLineVo;

public interface ICarOwnerLineService {

	Map<String, Object> getLineList(Search search, int currentPageIndex,
			int pageSize);

	CarOwnerLineVo getLineDetail(String pcLineId);

}
