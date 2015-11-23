package com.amwell.dao;

import java.util.List;

import com.amwell.entity.LineChangePrice;

/**
 * 线路通票价格修改的dao
 * @author shawn.zheng
 *
 */
public interface ILineChangePriceDao {
	
	/**
	 * 获取待处理的任务列表
	 * @return
	 */
	public List<LineChangePrice> getLineChangePriceList();
	
	/**
	 * 执行修改价格的任务
	 * @param lineChangePriceList 待处理的任务列表
	 */
	public void handleLineChangePrice(List<LineChangePrice> lineChangePriceList);
	
}
