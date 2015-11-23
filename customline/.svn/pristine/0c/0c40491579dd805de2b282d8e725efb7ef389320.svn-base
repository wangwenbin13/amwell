package com.amwell.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.IRecommendAwardDao;
import com.amwell.vo.RecommendAwardSet;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.app.bean.AppVo_3;

/**
 * 老拉新推荐有奖相关
 * @author gongxueting
 *
 */
@Repository
public class RecommendAwardDaoImpl extends DaoSupport implements IRecommendAwardDao {
	
	/**设置老拉新推荐有奖活动规则信息**/
	public int addRecommendAwardSet(RecommendAwardSet ras){
		
//		 recommendAwardSwitch推荐有奖开关：0.关 1.开
//		 newUserGiftId拉新用户礼品ID
//		 oldUserGiftId老用户礼品ID
//		 actionRule推荐有奖活动规则
//		 loginUserId登录用户id
		
		if("1".equals(ras.getRecommendAwardSwitch())){//开启推荐有奖
			super.finit("coupon_group");
			String str=ras.getNewUserGiftId()+","+ras.getOldUserGiftId();
			String sql="SELECT couponGroupID AS a1 FROM coupon_group WHERE couponGroupID IN("+str+") ORDER BY FIELD(couponGroupID,"+str+")";
			List<AppVo_1> vl=tableDao.queryList(AppVo_1.class, sql);
			if(null==vl||(ras.getNewUserGiftId().equals(ras.getOldUserGiftId())&&vl.size()==0)||(!ras.getNewUserGiftId().equals(ras.getOldUserGiftId())&&vl.size()<2)){
				return -3;//输入的礼品Id不存在
			}
//			else{
//				if(!"5".equals(vl.get(0).getA1())){
//					return -2;//输入的拉新用户礼品ID不是推荐有奖礼品
//				}
//				if(vl.size()==2){
//					if(!"5".equals(vl.get(1).getA1())){
//						return -1;//输入的老用户礼品ID不是推荐有奖礼品
//					}
//				}
//			}
		}
		
		//设置推荐有奖开关
		super.finit("sys_app_setting");
		String sql="update sys_app_setting set recommendAwardSwitch = ?";
		args=new Object[1];
		args[0]=ras.getRecommendAwardSwitch();
		int flag=tableDao.executeSQL(sql,args);
		if("1".equals(ras.getRecommendAwardSwitch())){//开启推荐有奖
			if(flag==1){
				super.finit("recommend_award_set");
				sql="SELECT id AS a1 FROM recommend_award_set";
				AppVo_1 vo1=tableDao.queryBean(AppVo_1.class,sql);
				if(null==vo1){//不存在设置信息，则添加一条
					sql="INSERT INTO `amwellcustomline`.`recommend_award_set` (`newUserGiftId`, `oldUserGiftId`, `actionRule`, `setBy`, `setOn`) VALUES (?,?,?,?,NOW())";
					
				}
				else{//存在设置信息，则修改
					sql="UPDATE `amwellcustomline`.`recommend_award_set` SET `newUserGiftId` = ? , `oldUserGiftId` = ? , `actionRule` = ? , `setBy` = ? , `setOn` = NOW()";
				}
				args=new Object[4];
				args[0]=ras.getNewUserGiftId();
				args[1]=ras.getOldUserGiftId();
				args[2]=ras.getActionRule();
				args[3]=ras.getSetBy();
				flag=tableDao.executeSQL(sql, args);
			}
		}
		return flag;
	}
	
	/**查询推荐有奖设置信息**/
	public RecommendAwardSet getRecommendAwardSet(){
		RecommendAwardSet ras=new RecommendAwardSet();

		//查询设置推荐有奖开关
		super.finit("sys_app_setting");
		String sql="SELECT recommendAwardSwitch AS a1 FROM sys_app_setting";
		AppVo_1 vo1=tableDao.queryBean(AppVo_1.class, sql);
		if(null!=vo1){
			ras.setRecommendAwardSwitch(vo1.getA1());
		}

		//查询推荐有奖设置信息
		super.finit("recommend_award_set");
		sql="SELECT newUserGiftId AS a1,oldUserGiftId AS a2,actionRule AS a3 FROM recommend_award_set";
		AppVo_3 vo3=tableDao.queryBean(AppVo_3.class,sql);
		if(null!=vo3){
			ras.setNewUserGiftId(vo3.getA1());
			ras.setOldUserGiftId(vo3.getA2());
			ras.setActionRule(vo3.getA3());
		}
		return ras;
	}
	
	/* 
	 * 添加日志
	 */
	public int addSysLog(int flag, String operatorId,String log) {
		if(flag>0){
			AppendLog(operatorId, log, log+"成功");
		}else{
			AppendLog(operatorId, log, log+"失败");
		}
		return 0;
	}
}
