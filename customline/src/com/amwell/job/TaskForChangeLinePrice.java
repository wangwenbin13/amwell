package com.amwell.job;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amwell.service.ILineChangePriceService;

/**
 * 修改线路通票价格的定时任务
 * 
 * @author shawn.zheng
 *
 */
@Component
public class TaskForChangeLinePrice {
	private final static Logger log = Logger.getLogger(TaskForChangeLinePrice.class);
	
	/**
	 * 修改线路通票价格的service
	 */
	private ILineChangePriceService lineChangePriceService;
	
	/**
	 * 定时器开关 ON开 OFF关
	 */
	private final static String SWITCH_FLAG = "OFF";
	
	@Scheduled(cron="* * 0 * * * ")   //每10分钟调度一次   
	public void changeLineTimeTask(){
		if(SWITCH_FLAG.equals("OFF")){
			return;
		}
		log.info("--->开始执行修改线路通票价格的定时任务.");
		long nowTime = System.currentTimeMillis();
		lineChangePriceService.handleLineChangePrice(nowTime);
	}
	
}
