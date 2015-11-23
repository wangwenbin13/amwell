package com.amwell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.dao.IRecommendAwardDao;
import com.amwell.service.IRecommendAwardService;
import com.amwell.vo.RecommendAwardSet;


/**
 * 老拉新推荐有奖相关
 * @author gongxueting
 *
 */
@Service("recommendAwardService")
public class RecommendAwardServiceImpl implements IRecommendAwardService {
	@Autowired
	private IRecommendAwardDao recommendAwardDao;
	
	/**设置老拉新推荐有奖活动规则信息**/
	public int addRecommendAwardSet(RecommendAwardSet ras){
		return recommendAwardDao.addRecommendAwardSet(ras);
	}
	
	/**查询推荐有奖设置信息**/
	public RecommendAwardSet getRecommendAwardSet(){
		return recommendAwardDao.getRecommendAwardSet();
	}
	
	/* 
	 * 添加日志
	 */
	public int addSysLog(int flag, String operatorId,String log){
		return recommendAwardDao.addSysLog(flag, operatorId, log);
	}
}
