package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.gf.GiftVo;

public interface IGiftService {

	/**
	 * 查询礼品列表
	 */
	Map<String, Object> listByPage(Search search, int currentPageIndex,int pageSize);

	/**
	 * 添加礼品
	 */
	int addGiftPage(GiftVo giftModel);

	/**
	 * 删除礼品
	 */
	int deleteGiftPage(String giftId);

	/**
	 * 获取礼品ID
	 */
	String getGiftId();

	/**
	 * 添加操作日志
	 * @param log 
	 */
	int addSysLog(int flag, String operatorId, String log);

	/**
	 * 查询礼品名称
	 * @param giftName 
	 */
	int getGiftName(String giftName);
			

}
