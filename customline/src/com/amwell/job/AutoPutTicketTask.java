package com.amwell.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amwell.commons.MyDate;
import com.amwell.infrastructure.config.HolidayConfig;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.app.LineBaseInfo;

@Component
public class AutoPutTicketTask {
	private final static Logger log = Logger.getLogger(TaskForChangeLinePrice.class);
	
	@Scheduled(cron="* * 0 * * * ")   //每天的凌晨执行一次定时任务
	public void autoPutTicket(){
		log.info("auto put ticket task run");
		long nowTimeValue = MyDate.strToUTCLong(MyDate.formatTime(System.currentTimeMillis(),"yyyy-MM-dd"), "yyyy-MM-dd") ;
		List<LineBaseInfo> autoPutTicketLineList = LineBaseInfo.listAutoPutTicketLine();
		log.info("autoPutTicketLineList="+autoPutTicketLineList);
		if(autoPutTicketLineList==null){
			return;
		}
		for(LineBaseInfo lineBaseInfo : autoPutTicketLineList){
			LineClassEntity maxLineClassEntity = LineClassEntity.getMaxDateClassEntity(lineBaseInfo.getLineBaseId());
			String maxOrderDate = maxLineClassEntity.getOrderDate();
			log.info("maxOrderDate="+maxOrderDate);
			long maxOrderDateValue = MyDate.strToUTCLong(maxOrderDate, "yyyy-MM-dd");
			long endDate = 0L;
			String autoPutTicket = lineBaseInfo.getAutoPutTicket();
			if("true".equals(autoPutTicket)||"30".equals(autoPutTicket)){
				endDate = nowTimeValue +(3600*24*30*1000L);
			}else if("7".equals(autoPutTicket)){
				endDate = nowTimeValue + (3600*24*7*1000L);
			}else if("3".equals(autoPutTicket)){
				endDate = nowTimeValue + (3600*24*3*1000L);
			}else{
				continue;
			}
			while((maxOrderDateValue+(3600*24*1000L))<=endDate){
				maxOrderDateValue = maxOrderDateValue + (3600*24*1000L);
				String maxOrderDateStr = MyDate.formatTime(maxOrderDateValue, "yyyy-MM-dd");
				if(!HolidayConfig.isHoliday(maxOrderDateStr)&&!HolidayConfig.isWeekend(maxOrderDateStr)){
					LineClassEntity oldEntity = LineClassEntity.getByLineBaseIdAndOrderDate(lineBaseInfo.getLineBaseId(), maxOrderDateStr);
					if(oldEntity!=null){
						continue;
					}
					LineClassEntity lineClassEntity = new LineClassEntity();
					lineClassEntity.setLineBaseId(lineBaseInfo.getLineBaseId());
					lineClassEntity.setOrderDate(maxOrderDateStr);
					lineClassEntity.setLineBusinessId(lineBaseInfo.getBusinessId());
					lineClassEntity.setOrderSeats(maxLineClassEntity.getOrderSeats());
					lineClassEntity.setPrice(lineBaseInfo.getOrderPrice());
					lineClassEntity.setOrderStartTime(maxLineClassEntity.getOrderStartTime());
					lineClassEntity.setDelFlag(0);
					lineClassEntity.setDriverId(maxLineClassEntity.getDriverId());
					lineClassEntity.setVehicleId(maxLineClassEntity.getVehicleId());
					lineClassEntity.save();
				}
			}
		}
	}
}
