package com.pig84.ab.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IRecommendAwardDao;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.RecommendAwardInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_3;

/**
 * 老拉新推荐有奖相关
 * @author gongxueting
 *
 */
@Repository
public class RecommendAwardDaoImpl extends BaseDao implements IRecommendAwardDao {
	/**获取有奖推荐开关标识**/
	public AppVo_1 getRecommendAwardSwitch(){
		String sql="SELECT recommendAwardSwitch AS a1 FROM sys_app_setting";
		AppVo_1 vo1=queryBean(AppVo_1.class, sql);
		return vo1;
	}
	
	/**获取用户姓名**/
	public AppVo_1 getPassengerName(String telephone){
		String sql="SELECT nickName AS a1 FROM passenger_info WHERE telephone = ?";
		Object[] params =new Object[1];
		params[0]=telephone;
		AppVo_1 vo1=queryBean(AppVo_1.class, sql,params);
		return vo1;
	}
	
	/**获取老拉新推荐有奖活动规则**/
	public AppVo_2 getRecommendAwardRole(){
		AppVo_2 vo=new AppVo_2();
		//查询当前推荐有奖设置信息
		String sql="SELECT actionRule AS a1,newUserGiftId as a2,oldUserGiftId as a3 FROM recommend_award_set LIMIT 0,1";
		AppVo_3 vo1=queryBean(AppVo_3.class, sql);
		if(null==vo1||StringUtils.isBlank(vo1.getA1())){
			vo.setA1("-1");//未做推荐有奖设置
		}
		else{
			vo.setA1("<div style='color:#ffd59e;'>"+vo1.getA1()+"</div>");
		}
		sql=" SELECT cast(SUM(couponValue*num) as decimal(4,0)) AS a1 FROM coupon_info WHERE couponGroupId in("+vo1.getA2()+","+vo1.getA2()+") ";
		AppVo_1 vo2=queryBean(AppVo_1.class, sql);
		if(null==vo2||StringUtils.isBlank(vo2.getA1())){//根据批次号查询组合券信息，如果不存在，则表示没有设置对应的组合券信息
			vo.setA2("-1");//未添加推荐有奖礼品
		}
		else{
			vo.setA2(vo2.getA1());
		}
		return vo;
	}
	
	/**添加老拉新推荐有奖信息**/
    public String addRecommendAward(String oldUserPhone,String newUserPhone){
    	if(StringUtils.isBlank(oldUserPhone)||StringUtils.isBlank(newUserPhone)){
    		return "0";//新用户获取推荐奖励失败
    	}
		
		//判断新用户号码是否已经注册过
		String sql = "select * from passenger_info where telephone = ?";
		int ishave = queryCount(sql, new Object[] {newUserPhone});
		//判断新用户号码是否已经登记过奖励
		sql="SELECT * FROM recommend_award_info WHERE newUserPhone = ? and delFlag = '0'";	
		int ishave1 = queryCount(sql, new Object[] {newUserPhone});
		if (ishave > 0||ishave1>0) {// 手机号码已注册
			return "-2";
		}
		
		//查询当前推荐有奖设置信息
		sql="SELECT newUserGiftId AS a1,oldUserGiftId AS a2 FROM recommend_award_set LIMIT 0,1";
		AppVo_2 vo2=queryBean(AppVo_2.class, sql);
		if(null==vo2||StringUtils.isBlank(vo2.getA1())||StringUtils.isBlank(vo2.getA2())){
			return "-1";//未做推荐有奖设置
		}
		
		//添加老拉新推荐有奖信息
		RecommendAwardInfo rai=new RecommendAwardInfo();
		rai.setOldUserPhone(oldUserPhone);
		rai.setNewUserPhone(newUserPhone);
		rai.setOldUserGiftId(vo2.getA2());
		rai.setNewUserGiftId(vo2.getA1());
		rai.setCreateOn(MyDate.Format.DATETIME.now());
		int flag=updateData(rai,"recommend_award_info","id");
		if(flag!=1){
			return "0";//新用户获取推荐奖励失败
		}
		else{
			//给新用户发送短信
			String context = "【小猪巴士】"+getTotalValue(vo2.getA1())+"元的奖励已经发放到您的账户，请使用"+newUserPhone+"号登录小猪巴士APP，即可使用。下载http://url.cn/e5nDFr";
			new Message(context).sendTo(newUserPhone);
			return "1";//新用户获取推荐奖励成功
		}
	}
	
	/**
	 * 获取优惠券总面值
	 * @param couponGroupId
	 * @return
	 */
	private String getTotalValue(String couponGroupId){
		//查询组合券总面值
		String sql="SELECT cast(SUM(couponValue*num) as decimal(4,0)) AS a1 FROM coupon_info WHERE couponGroupId = ?";
		Object[] params =new Object[1];
		params[0]=couponGroupId;
		AppVo_1 vo1=queryBean(AppVo_1.class, sql,params);
		if(null==vo1||StringUtils.isBlank(vo1.getA1())){
			vo1=new AppVo_1();
			vo1.setA1("30");
		}
		return vo1.getA1();
	}
}
