package com.amwell.service.financial;

import java.util.Date;
import java.util.Map;

import com.amwell.entity.Search;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 新版财务相关
 */
public interface IFinancialService {

	/**计算昨天的收入**/
	public int countInCome(String yesterday);

	/**供应商结算明细表**/
	public Map<String, Object> querySupplierList(Search search,
			Integer currentPageIndex, Integer pageSize);

	/**报表**/
	public Map<String, Object> queryReportList(Search search,
			Integer currentPageIndex, Integer pageSize);

	/**判断是否执行新版财务数据恢复**/
	public String queryIsExecute(String string);

	/**将是否执行新版财务数据恢复Y改为N**/
	public void updateIsExecute(String string);

	/**添加城市对应的订单金额数据**/
	public int addCityOrder(String yesterday);

}
