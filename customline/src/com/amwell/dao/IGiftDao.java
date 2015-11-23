package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.gf.GiftVo;

public interface IGiftDao {

	Map<String, Object> listByPage(Search search, int currentPageIndex,
			int pageSize);

	int addGiftPage(GiftVo giftModel);

	int deleteGiftPage(String giftId);

	String getGiftId();

	int addSysLog(int flag, String operatorId, String log);

	int getGiftName(String giftName);

}
