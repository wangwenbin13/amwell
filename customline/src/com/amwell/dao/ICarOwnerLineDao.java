package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.pc.CarOwnerLineVo;

public interface ICarOwnerLineDao {
	Map<String, Object> getLineList(Search search, int currentPageIndex,
			int pageSize);

	CarOwnerLineVo getPcLineDetail(String pcLineId);


}
