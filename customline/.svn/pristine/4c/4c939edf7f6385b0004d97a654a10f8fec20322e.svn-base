package com.amwell.dao;

import java.util.List;

import com.amwell.entity.Search;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_4;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 获取特殊数据的工具
 */
public interface IUtilsDao {

	/**
	 * 获取购买过指定线路的乘客电话
	 */
	String queryPassengerTelHasBuyLine();

	/**将所有的乘客ID放到im_user_info表里面**/
	void fromPassengerToImUserInfo();

	/**读取财务数据并保存到数据库**/
	void fromCaiWuExcelToDb(List<AppVo_2> appweixin, List<AppVo_2> weixin,
			List<AppVo_2> yinlian, List<AppVo_2> zhifubao);

	/**查处存在第三方,但是不存在平台的收入数据**/
	List<AppVo_4> queryNotInLocalLeases(Search search);

	/**清空stat_temp数据**/
	void deleteDate();

}
