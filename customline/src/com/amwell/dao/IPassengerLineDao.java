package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.pc.PassengerLineVo;

public interface IPassengerLineDao {

	Map<String, Object> getLineList(Search search, int currentPageIndex,
			int pageSize);

	PassengerLineVo getPcLineDetail(String pcLineId);

	int updateLineOnOff(String pcLineId, int lineOnOff);

}
