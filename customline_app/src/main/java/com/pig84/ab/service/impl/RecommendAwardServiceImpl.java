package com.pig84.ab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.IRecommendAwardDao;
import com.pig84.ab.service.IRecommendAwardService;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2;


/**
 * 老拉新推荐有奖相关
 * @author gongxueting
 *
 */
@Service("recommendAwardService")
public class RecommendAwardServiceImpl implements IRecommendAwardService {
	@Autowired
	private IRecommendAwardDao recommendAwardDao;
	
	/**获取有奖推荐开关标识**/
	public AppVo_1 getRecommendAwardSwitch(){
		return recommendAwardDao.getRecommendAwardSwitch(); 
	}
	
	/**获取用户姓名**/
	public AppVo_1 getPassengerName(String telephone){
		return recommendAwardDao.getPassengerName(telephone);
	}
	
	/**获取老拉新推荐有奖活动规则**/
	public AppVo_2 getRecommendAwardRole(){
		return recommendAwardDao.getRecommendAwardRole();
	}
	
	/**添加老拉新推荐有奖信息**/
	public String addRecommendAward(String oldUserPhone,String newUserPhone){
		return recommendAwardDao.addRecommendAward(oldUserPhone, newUserPhone);
	}
}
