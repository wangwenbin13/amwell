package com.amwell.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.amwell.action.order.FinancialAction;
import com.amwell.service.ILineService;
import com.amwell.service.financial.IFinancialService;


/**
 * 修改发车时间任务计划
 * @author zhangqiang
 *
 */
public class TaskForChangeLineTime {
	
	@Autowired
	private ILineService lineService;
	
	@Autowired
	private IFinancialService financialService;
	
	@Scheduled(cron="0 0/10 * * * ? ")   //每10分钟调度一次   
	public void changeLineTimeTask(){
		lineService.changeLineTimeByTask();
	}
	
	@Scheduled(cron="0 15 4 * * ? ")   //每天4点15分调度一次   
	public void countInCome(){
		
		//--删除重复的财务总表数据
		//delete from stat_financial where finacialId not in ( select * from(select finacialId from stat_financial group by lineClassId,orderDate)b);
		
		//判断是否执行新版财务数据恢复
		String isExecute = financialService.queryIsExecute("xinbancaiwu");
		int statu = 0;
		
		if("Y".equalsIgnoreCase(isExecute)){
			for(int i=-1;i>-300;i--){
				Date date = new Date();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(Calendar.DATE,i);//昨天
				Date starttime=calendar.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String yesterday = sdf.format(starttime);
				statu = financialService.countInCome(yesterday);
			}
			//将是否执行新版财务数据恢复Y改为N
			financialService.updateIsExecute("xinbancaiwu");
		}else{
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE,-1);//昨天
			Date starttime=calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yesterday = sdf.format(starttime);
			statu = financialService.countInCome(yesterday);
		}
		
		//添加城市对应的订单金额数据
		if(statu>=1){
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.DATE,-1);//昨天
			Date starttime=calendar.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String yesterday = sdf.format(starttime);
			statu = financialService.addCityOrder(yesterday);
		}
	}
}
