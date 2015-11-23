package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IGiftDao;
import com.amwell.entity.Search;
import com.amwell.service.IGiftService;
import com.amwell.vo.gf.GiftVo;
@Service("giftService")
public class GiftServiceImpl implements IGiftService {

	@Autowired
	private IGiftDao giftDao;
	
	public Map<String, Object> listByPage(Search search, int currentPageIndex,
			int pageSize) {
		return giftDao. listByPage(search, currentPageIndex,pageSize);
	}

	public int addGiftPage(GiftVo giftModel) {
		return giftDao.addGiftPage(giftModel);
	}

	public int deleteGiftPage(String giftId) {
		return giftDao.deleteGiftPage(giftId);
	}

	public String getGiftId() {
		return giftDao.getGiftId();
	}

	public int addSysLog(int flag, String operatorId,String log) {
		return giftDao.addSysLog(flag,operatorId,log);
		
	}

	public int getGiftName(String giftName) {
		return giftDao.getGiftName(giftName);
	}

}
