package com.amwell.service;

import java.util.List;

import com.amwell.entity.LineChangePrice;

/**
 * 修改线路通票价格service
 * @author shawn.zheng
 *
 */

public interface ILineChangePriceService {
	
	/**
	 * 获取可处理的任务列表
	 * @param nowTime 当前时间
	 * @return
	 */
	public List<LineChangePrice> getLineChangePriceList(long nowTime);
	
	/**
	 * 处理线路价格变更任务
	 * @param nowTime
	 */
	public void handleLineChangePrice(long nowTime);

}
