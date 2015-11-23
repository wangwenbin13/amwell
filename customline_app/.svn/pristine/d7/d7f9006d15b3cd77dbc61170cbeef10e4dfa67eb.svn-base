package com.pig84.ab.dao;

import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2;

/**
 * 老拉新推荐有奖相关
 * @author gongxueting
 *
 */
public interface IRecommendAwardDao {
	/**获取有奖推荐开关标识**/
	public AppVo_1 getRecommendAwardSwitch();
	
	/**获取用户姓名**/
	public AppVo_1 getPassengerName(String telephone);
	
	/**获取老拉新推荐有奖活动规则**/
	public AppVo_2 getRecommendAwardRole();
	
	/**添加老拉新推荐有奖信息**/
	public String addRecommendAward(String oldUserPhone,String newUserPhone);
}
