package com.amwell.service.financial.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.financial.IFinancialDao;
import com.amwell.entity.Search;
import com.amwell.service.financial.IFinancialService;
import com.amwell.utils.StringUtils;
/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 新版财务相关
 */
@Service("financialService")
public class FinancialServiceImpl implements IFinancialService {

	@Autowired
	private IFinancialDao financialDao;
	
	/**计算昨天的收入**/
	public int countInCome(String yesterday) {
		return financialDao.countInCome(yesterday);
	}

	/**供应商结算明细表**/
	public Map<String, Object> querySupplierList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		/**
		 * 默认是查询昨天的数据
		 */
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search || (StringUtils.isEmpty(search.getField01()) && StringUtils.isEmpty(search.getField02()))){
			Date date = new Date();
			if(null==search){
				search = new Search();
			}
			Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE,-1);//把日期往前退一天.整数往后推,负数往前移动
		    date=calendar.getTime();
			String date_s = TimeFormat.format(date);
			search.setField01(date_s);
			search.setField02(date_s);
		}
		return financialDao.querySupplierList(search,currentPageIndex,pageSize);
	}

	/**报表**/
	public Map<String, Object> queryReportList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		/**
		 * 默认是查询昨天的数据
		 */
		SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null==search || (StringUtils.isEmpty(search.getField01()) && StringUtils.isEmpty(search.getField02()))){
			Date date = new Date();
			if(null==search){
				search = new Search();
			}
			Calendar calendar = new GregorianCalendar();
		    calendar.setTime(date);
		    calendar.add(calendar.DATE,-1);//把日期往前退一天.整数往后推,负数往前移动
		    date=calendar.getTime();
			String date_s = TimeFormat.format(date);
			search.setField01(date_s);
			search.setField02(date_s);
		}
		return financialDao.queryReportList(search,currentPageIndex,pageSize);
	}

	/**判断是否执行新版财务数据恢复**/
	public String queryIsExecute(String string) {
		return financialDao.queryIsExecute(string);
	}

	/**将是否执行新版财务数据恢复Y改为N**/
	public void updateIsExecute(String string) {
		financialDao.updateIsExecute(string);
	}

	/**添加城市对应的订单金额数据**/
	@Override
	public int addCityOrder(String yesterday) {
		return financialDao.addCityOrder(yesterday);
	}

}
