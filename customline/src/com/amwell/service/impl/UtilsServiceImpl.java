package com.amwell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IUtilsDao;
import com.amwell.entity.Search;
import com.amwell.service.IUtilsService;
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
@Service("utilsService")
public class UtilsServiceImpl implements IUtilsService{

	@Autowired
	private IUtilsDao utilsDao;
	
	/**
	 * 获取购买过指定线路的乘客电话
	 */
	@Override
	public String queryPassengerTelHasBuyLine() {
		return utilsDao.queryPassengerTelHasBuyLine();
	}

	/**将所有的乘客ID放到im_user_info表里面**/
	@Override
	public void fromPassengerToImUserInfo() {
		utilsDao.fromPassengerToImUserInfo();
	}

	/**读取财务数据并保存到数据库**/
	@Override
	public void fromCaiWuExcelToDb(List<AppVo_2> appweixin,
			List<AppVo_2> weixin, List<AppVo_2> yinlian, List<AppVo_2> zhifubao) {
		utilsDao.fromCaiWuExcelToDb(appweixin,weixin,yinlian,zhifubao);
	}

	/**查处存在第三方,但是不存在平台的收入数据**/
	@Override
	public List<AppVo_4> queryNotInLocalLeases(Search search) {
		return utilsDao.queryNotInLocalLeases(search);
	}

	/**清空stat_temp数据**/
	public void deleteDate() {
		utilsDao.deleteDate();
	}

}
