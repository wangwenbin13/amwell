package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.commons.MyDate;
import com.amwell.dao.ILineChangePriceDao;
import com.amwell.entity.LineChangePrice;
import com.amwell.service.ILineChangePriceService;

/**
 * 修改线路通票价格service
 * @author shawn.zheng
 *
 */
public class LineChangePriceServiceImpl implements ILineChangePriceService{
	
	@Autowired
	private ILineChangePriceDao lineChangePriceDao;
	
	/**
	 * 获取可处理的任务列表
	 * @param nowTime 当前时间
	 * @return
	 */
	public List<LineChangePrice> getLineChangePriceList(long nowTime){
		List<LineChangePrice> lineChangePriceList = lineChangePriceDao.getLineChangePriceList();
		if(lineChangePriceList==null){
			return null;
		}
		List<LineChangePrice> targetList = new ArrayList<LineChangePrice>();
		for(LineChangePrice lineChangePrice : lineChangePriceList){
			String effectiveTimeString = lineChangePrice.getEffectiveTime();
			if(StringUtils.isEmpty(effectiveTimeString)){
				continue;
			}
			long effectiveTime = MyDate.strToUTCLong(effectiveTimeString, "yyyy-MM-dd HH:mm:ss");
			if(effectiveTime<nowTime){
				targetList.add(lineChangePrice);
			}
		}
		return targetList;
	}
	
	/**
	 * 处理线路价格变更任务
	 * @param nowTime
	 */
	public void handleLineChangePrice(long nowTime){
		List<LineChangePrice> lineChangePriceList = getLineChangePriceList(nowTime);
		if(lineChangePriceList==null){
			return;
		}
		lineChangePriceDao.handleLineChangePrice(lineChangePriceList);
	}
}
