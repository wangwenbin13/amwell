package com.pig84.ab.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ICouponGroupPassengerDetailDao;
import com.pig84.ab.dao.ICouponRuleDao;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.coupon.CouponGrant;
import com.pig84.ab.vo.coupon.CouponInfo;
import com.pig84.ab.vo.coupon.GrantInfo;
import com.pig84.ab.vo.coupon.Rule;
import com.pig84.ab.vo.coupon.RuleSet;

/**
 * 优惠券发放规则信息
 * @author gxt
 *
 */
@Repository(value="couponRuleDaoImpl")
public class CouponRuleDaoImpl extends BaseDao implements ICouponRuleDao {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICouponGroupPassengerDetailDao detailDao;
	
	/**
	 * 根据城市id获取买过票的用户id
	 * @param lineBaseId
	 * @return
	 */
	@Override
	public List<AppVo_1> getLeaseBase(String passengerId,String lineBaseId){
		StringBuffer sql=new StringBuffer("SELECT distinct passengerId AS a1 FROM lease_base_info WHERE lineBaseId = ? AND ispay='1'");
		List<String> condition=new ArrayList<String>();
		condition.add(lineBaseId);
		if(StringUtils.isNotBlank(passengerId)){
			sql.append(" and passengerId = ? ");
			condition.add(passengerId);
		}
		return queryList(AppVo_1.class, sql.toString(), condition.toArray());
	}
	
	/**
	 * 根据注册时间、条件获取在指定时间范围内注册的用户id
	 * @param registTime
	 * @param theCondition
	 * @return
	 */
	@Override
	public List<AppVo_1> getPassenger(String passengerId,String registTime,String theCondition){
		StringBuffer sql=new StringBuffer("SELECT passengerId as a1 FROM passenger_info WHERE left(registerTime,10) "+theCondition+" ?");
		List<String> condition=new ArrayList<String>();
		condition.add(registTime);
		if(StringUtils.isNotBlank(passengerId)){
			sql.append(" and passengerId = ? ");
			condition.add(passengerId);
		}
		return queryList(AppVo_1.class, sql.toString(), condition.toArray());
	}
	
	/**
	 * 根据推荐时间、条件获取在指定时间范围内推荐的用户id
	 * @param recommendTime
	 * @param theCondition
	 * @return
	 */
	@Override
	public List<PassengerInfo> getReOldPassenger(String passengerId,String recommendTime,String theCondition){
		
		List<AppVo_2> list=new ArrayList<AppVo_2>();
		
		String sql="SELECT lb.leaseOrderId FROM lease_base_info lb WHERE lb.ispay = '1' AND lb.passengerId = ?";
		Object[] params = new Object[1];
		params[0]=passengerId;
		int orderCount=queryCount(sql,params);
		if(orderCount==1){//完成支付的订单数为1时即为首次购票
			
			sql="select telephone as a1 from passenger_info where passengerId = ?";
			params=new Object[1];
			params[0]=passengerId;
			AppVo_1 v=queryBean(AppVo_1.class, sql, params);
			String telephone=v.getA1();
			
			//1.检查用户是否是被拉新用户
			sql="SELECT oldUserPhone AS a1,oldUserGiftId AS a2,newUserGiftId AS a3 FROM recommend_award_info WHERE newUserPhone = ? and delFlag = '2'";	
			AppVo_3 vo3=queryBean(AppVo_3.class, sql,new Object[] {telephone});
			if(null!=vo3){//是被拉新用户
				
				//判断老用户是否还有推荐有奖优惠券领取机会
				sql="SELECT id FROM coupon_group_passenger_detail WHERE telephone = ? AND couponGroupId = ?";
				params=new Object[2];
				params[0]=vo3.getA1();
				params[1]=vo3.getA2();
				int count=queryCount(sql, params);//查询已领取数量
				boolean flag =false;
				if(count>0){
					sql="SELECT cg.averageNum as a1,COUNT(ci.id) as a2 FROM coupon_group cg LEFT JOIN coupon_info ci ON cg.couponGroupID = ci.couponGroupId WHERE cg.couponGroupID = ?";
					params=new Object[1];
					params[0]=vo3.getA2();
					AppVo_2 vv=queryBean(AppVo_2.class, sql,params);//查询人均限领量和批次号对应的优惠券个数
					if(null!=vv){
						if(Integer.parseInt(vv.getA1())>(count)/Integer.parseInt(vv.getA2())){
							flag=true;//表示还有领取机会
						}
					}
				}
				else{
					flag=true;//老用户对应批次没有领取过，则标识还有领取机会
				}
				
				if(flag){//有领取机会时才给老用户发优惠券
					//1.给老用户发短信
					String context = "【小猪巴士】1位好友已经通过您的邀请注册了小猪巴士，"+getTotalValue(vo3.getA2())+"元奖励已经发放到您的账户。";
					new Message(context).sendTo(vo3.getA1());
				}
				
				//修改推荐有奖记录状态
				sql="UPDATE recommend_award_info SET delFlag = '3' WHERE oldUserPhone = ? and newUserPhone = ? AND delFlag = '2'";
				params=new Object[2];
				params[0]=vo3.getA1();
				params[1]=telephone;
				if(executeSQL(sql, params)>0){
					sql="SELECT p.passengerId AS a1,p.telephone as a2 FROM recommend_award_info rai LEFT JOIN passenger_info p ON " +
							"rai.oldUserPhone = p.telephone WHERE rai.delFlag = '3' and left(rai.createOn,10) "+theCondition+" ? and rai.newUserPhone = ? ";
					list=queryList(AppVo_2.class, sql.toString(),new Object[] {recommendTime,telephone});
				}
				
				//被推荐的新用户符合首次买票要求时，给他的推荐用户发送优惠券
				logger.info("给推荐有奖【老用户】发优惠券，用户手机号码：{}",vo3.getA1());
			}
		}
		
		return putResult(list);
	}
	
	@Override
	public List<PassengerInfo> getReNewPassenger(String telephone,String recommendTime,String theCondition){
		
		List<AppVo_2> list=new ArrayList<AppVo_2>();
		//1.检查用户是否是被拉新用户
		String sql="SELECT oldUserPhone AS a1,oldUserGiftId AS a2,newUserGiftId AS a3 FROM recommend_award_info WHERE newUserPhone = ? and delFlag = '0'";	
		AppVo_3 vo3=queryBean(AppVo_3.class, sql,new Object[] {telephone});
		if(null!=vo3){//是被拉新用户
			//给新用户发优惠券
			logger.info("给推荐有奖【新用户】发优惠券，用户手机号码：{}",telephone);

			//给新用户发短信
			String context = "【小猪巴士】推荐有奖活动"+getTotalValue(vo3.getA3())+"元奖励已经发放到您的账户。";
			new Message(context).sendTo(telephone);
			
			//修改推荐有奖记录状态
			sql="UPDATE recommend_award_info SET delFlag = '2' WHERE newUserPhone = ? AND delFlag = '0'";
			Object[] params = new Object[1];
			params[0]=telephone;
			
			if(executeSQL(sql, params)>0){
				sql="SELECT DISTINCT p.passengerId AS a1,p.telephone as a2 FROM recommend_award_info rai LEFT JOIN passenger_info p ON " +
						"rai.newUserPhone = p.telephone WHERE rai.delFlag = '2' and left(rai.createOn,10) "+theCondition+" ? and rai.newUserPhone = ? ";
				list=queryList(AppVo_2.class, sql.toString(),new Object[] {recommendTime,telephone});
			}
		}
		return putResult(list);
	}
	
	private List<PassengerInfo> putResult(List<AppVo_2> list){
		List<PassengerInfo> result=new ArrayList<PassengerInfo>();
		if(null!=list&&list.size()>0){
			AppVo_2 vo=list.get(0);
			PassengerInfo p=new PassengerInfo();
			p.setPassengerId(vo.getA1());
			p.setTelephone(vo.getA2());
			result.add(p);
		}
		return result;
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
	
	/**
	 * 根据购票次数、条件获取在指定购票数范围内的用户id
	 * @param ticketNumber
	 * @param theCondition
	 * @return
	 */
	@Override
	public List<AppVo_1> getTicketNumber(String passengerId,String ticketNumber,String theCondition){
		String sql=null;
		Object[] params;
		if(StringUtils.isNotBlank(passengerId)){
			sql="SELECT passengerId AS a1 FROM lease_base_info WHERE ispay = '1' AND passengerId = ? GROUP BY passengerId HAVING COUNT(leaseOrderId) "+theCondition+" ? ";
			params=new Object[2];
			params[0]=passengerId;
			params[1]=ticketNumber;
		}
		else{
			sql="SELECT passengerId AS a1 FROM lease_base_info WHERE ispay = '1' GROUP BY passengerId HAVING COUNT(leaseOrderId) "+theCondition+" ? ";
			params=new Object[1];
			params[0]=ticketNumber;
		}
		
		List<AppVo_1> list=queryList(AppVo_1.class, sql, params);
		if(theCondition.equals("=")&&ticketNumber.equals("0")&&null==list){//当条件为0，且list为null，说明用户从来没有买过票，与买票数为0匹配
			list=new ArrayList<AppVo_1>();
			AppVo_1 vo=new AppVo_1();
			vo.setA1(passengerId);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 根据购票时间、条件获取在指定时间范围内买票的用户id
	 * @param ticketTime
	 * @param theCondition
	 * @return
	 */
	public List<AppVo_1> getTicketPassenger(String passengerId,String ticketTime,String theCondition){
		String sql="SELECT DISTINCT lbo.passengerId as a1 FROM"
				+ " lease_base_info lbi LEFT JOIN line_business_order lbo ON lbi.leaseOrderNo = lbo.leaseOrderId"
				+ " LEFT JOIN line_class_info lci ON lbo.lineClassId = lci.lineClassId"
				+ " WHERE lbi.ispay = '1' AND lbo.status IN('0','2') AND lci.orderDate "+theCondition+" ?";
		List<String> condition=new ArrayList<String>();
		condition.add(ticketTime);
		if(StringUtils.isNotBlank(passengerId)){
			sql = sql+" and lbo.passengerId = ? ";
			condition.add(passengerId);
		}
		return queryList(AppVo_1.class, sql,condition.toArray());
	}
	
	/**
	 * 根据推荐人数、条件获取在指定推荐人数范围内的用户id
	 * @param recommendNumber
	 * @param theCondition
	 * @return
	 */
	@Override
	public List<AppVo_1> getRecommendNumber(String passengerId,String recommendNumber,String theCondition){
		String sql=null;
		Object[] params;
		if(StringUtils.isNotBlank(passengerId)){
			sql="SELECT p.passengerId AS a1 FROM recommend_award_info rai,passenger_info p WHERE rai.oldUserPhone = p.telephone " +
			"AND rai.delFlag IN('2','3') and p.passengerId = ? GROUP BY p.passengerId HAVING COUNT(rai.id) "+theCondition+" ? ";
			params=new Object[2];
			params[0]=passengerId;
			params[1]=recommendNumber;
		}
		else{
			sql="SELECT p.passengerId AS a1 FROM recommend_award_info rai,passenger_info p WHERE rai.oldUserPhone = p.telephone " +
			"AND rai.delFlag IN('2','3') GROUP BY p.passengerId HAVING COUNT(rai.id) "+theCondition+" ? ";
			params=new Object[1];
			params[0]=recommendNumber;
		}
		
		List<AppVo_1> list=queryList(AppVo_1.class, sql, params);
		if(theCondition.equals("=")&&recommendNumber.equals("0")&&null==list){//当条件为0，且list为null，说明用户从来没有推荐过，与推荐数为0匹配
			list=new ArrayList<AppVo_1>();
			AppVo_1 vo=new AppVo_1();
			vo.setA1(passengerId);
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 获取所有系统发放和app操作触发发放类型的优惠券
	 * @return
	 */
	@Override
	public List<CouponInfo> getSysSendCoupon(String couponGroupGrantId){
		String sql="SELECT cgg.id AS grantId,cgg.couponGroupId,ci.id,ci.delayDays,ci.num,ci.effectiveTime,ci.expirationTime FROM coupon_group_grant cgg LEFT JOIN coupon_info ci ON cgg.couponGroupId = ci.couponGroupId " +
				"WHERE cgg.sendCouponType = 'sys send' AND cgg.startTime <= '"+MyDate.Format.DATE.now()+"' AND cgg.endTime >= '"+MyDate.Format.DATE.now()+"' AND cgg.modeOfExecution = 'send'";
		List<CouponInfo> list=null;
		if(StringUtils.isNotBlank(couponGroupGrantId)){
			sql=sql+" and cgg.id = ? ";
			Object[] params=new Object[1];
			params[0]=couponGroupGrantId;
			list=queryList(CouponInfo.class, sql,params);
		}
		else{
			list=queryList(CouponInfo.class, sql);
		}
		return list;
	}
	
	/**
	 * 获取用户领取类型的优惠券
	 * @param couponGroupGrantId
	 * @return
	 */
	private List<CouponInfo> getUserGetCoupon(String couponGroupGrantId){
		String sql="SELECT cgg.id AS grantId,cgg.couponGroupId,ci.id,ci.delayDays,ci.num,ci.effectiveTime,ci.expirationTime FROM coupon_group_grant cgg LEFT JOIN coupon_info ci ON cgg.couponGroupId = ci.couponGroupId " +
				"WHERE cgg.sendCouponType = 'user get' AND cgg.startTime <= '"+MyDate.Format.DATE.now()+"' AND cgg.endTime >= '"+MyDate.Format.DATE.now()+"' AND cgg.modeOfExecution = 'send'";
		List<CouponInfo> list=null;
		if(StringUtils.isNotBlank(couponGroupGrantId)){
			sql=sql+" and cgg.id = ? ";
			Object[] params=new Object[1];
			params[0]=couponGroupGrantId;
			list=queryList(CouponInfo.class, sql,params);
		}
		else{
			list=queryList(CouponInfo.class, sql);
		}
		return list;
	}
	
	/**
	 * 查询规则信息
	 * isValidateTotalCount  是否需要验证优惠券总数
	 * @return
	 */
	@Override
	public List<GrantInfo> getGrantInfo(String passengerId,boolean isValidateTotalCount){
		//查询所有的优惠券发放id
		String sql="SELECT id AS a1 FROM coupon_group_grant WHERE sendCouponType = 'sys send' AND startTime <= '"+MyDate.Format.DATE.now()+"' AND endTime >= '"+MyDate.Format.DATE.now() +
				"' AND modeOfExecution = 'send' and selectPass = 'newUser'";
		List<AppVo_1> vo1=queryList(AppVo_1.class, sql);
			
		if(null==vo1||vo1.isEmpty()){
			return null;
		}
		
		List<GrantInfo> list=new ArrayList<GrantInfo>();
		RuleSet ruleSet=null;
		GrantInfo grant=null;
		for (AppVo_1 appVo_1 : vo1) {
			
			//查询系统发放类型的优惠券
			List<CouponInfo> couponList=getSysSendCoupon(appVo_1.getA1());
			
			//需要验证优惠券总发行量时进行验证
			boolean flag=false;
			if(isValidateTotalCount){
				if(doValidateCount(couponList,passengerId)){
					flag=true;
				}
			}
			else{
				flag=true;
			}
			if(!flag){
				return null;
			}
			
			//根据优惠券发放id查询对应的规则
			sql="SELECT ruleName as a1,theCondition as a2,theValue as a3 FROM coupon_rule WHERE couponGroupGrantId = ?";
			Object[] params=new Object[1];
			params[0]=appVo_1.getA1();
			List<AppVo_3> ruleList=queryList(AppVo_3.class, sql, params);
			if(null!=ruleList&&ruleList.size()>0){
				List<Rule> l=new ArrayList<Rule>();
				Rule rule=null;
				for (AppVo_3 r : ruleList) {
					rule=Rule.create(r);
					if(null==rule){
						logger.info("search coupon grant info,create rule instance failed,rule name :{},grantId :{}",r.getA1(),appVo_1.getA1());
						break;
					}
					l.add(rule);
				}
				if(!l.isEmpty()){
					ruleSet=new RuleSet();
					ruleSet.setRules(l);
					ruleSet.setCouponRuleDao(this);
					grant=new GrantInfo();
					grant.setRuleSet(ruleSet);
					
					CouponGrant couponGrant=new CouponGrant();
					couponGrant.setDetailDao(detailDao);
					couponGrant.setCouponList(couponList);
					grant.setCouponGrant(couponGrant);
					
					list.add(grant);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<GrantInfo> getGrantInfo(String gameName){
		//查询所有的优惠券发放id
		String sql="SELECT id AS a1 FROM coupon_group_grant WHERE sendCouponType = 'user get' AND startTime <= '"+MyDate.Format.DATE.now()+"' AND endTime >= '"+MyDate.Format.DATE.now() +
				"' AND modeOfExecution = 'send' and selectPass = 'newUser'";
		List<AppVo_1> vo1=queryList(AppVo_1.class, sql);
			
		if(null==vo1||vo1.isEmpty()){
			return null;
		}
		
		List<GrantInfo> list=new ArrayList<GrantInfo>();
		RuleSet ruleSet=null;
		GrantInfo grant=null;
		for (AppVo_1 appVo_1 : vo1) {
			//根据优惠券发放id查询对应的规则
			sql="SELECT ruleName as a1,theCondition as a2,theValue as a3 FROM coupon_rule WHERE couponGroupGrantId = ?";
			Object[] params=new Object[1];
			params[0]=appVo_1.getA1();
			List<AppVo_3> ruleList=queryList(AppVo_3.class, sql, params);
			if(null!=ruleList&&ruleList.size()>0){
				List<Rule> l=new ArrayList<Rule>();
				Rule rule=null;
				for (AppVo_3 r : ruleList) {
					if(gameName.equals(r.getA3())){
						rule=Rule.create(r);
						if(null==rule){
							logger.info("search coupon grant info,create rule instance failed,rule name :{},grantId :{}",r.getA1(),appVo_1.getA1());
							break;
						}
						l.add(rule);
					}
				}
				if(!l.isEmpty()){
					ruleSet=new RuleSet();
					ruleSet.setRules(l);
					ruleSet.setCouponRuleDao(this);
					grant=new GrantInfo();
					grant.setRuleSet(ruleSet);
					
					CouponGrant couponGrant=new CouponGrant();
					couponGrant.setDetailDao(detailDao);
					
					//查询用户领取类型的优惠券
					List<CouponInfo> couponList=getUserGetCoupon(appVo_1.getA1());
					couponGrant.setCouponList(couponList);
					grant.setCouponGrant(couponGrant);
					
					list.add(grant);
				}
			}
		}
		return list;
	}
	
	/**
	 * 验证是否还有领取机会(以后待用)
	 * @param couponList
	 * @param passengerId
	 * @return
	 */
	private boolean doValidateCount(List<CouponInfo> couponList,String passengerId){
		
		//组合券中单个券个数
		int couponNum=0;
		for (CouponInfo couponInfo : couponList) {
			couponNum=couponNum+Integer.parseInt(couponInfo.getNum());
		}
		
		String couponGroupID=couponList.get(0).getCouponGroupId();
		
		//根据批次号获取组合券人均领取量
		String sql="SELECT averageNum AS a1,couponGroupCount as a2 FROM coupon_group WHERE couponGroupID = ?";
		Object[] params=new Object[1];
		params[0]=couponGroupID;
		AppVo_2 vo=queryBean(AppVo_2.class, sql, params);
		
		int couponGroupCount=0;
		if(null!=vo&&StringUtils.isNotBlank(vo.getA2())){
			couponGroupCount = Integer.parseInt(vo.getA2());
		}
		
		//查询用户已发放的优惠券总数
		int groupTotalCount=detailDao.getCouponDetail(null,couponGroupID,null);
		if(couponGroupCount<=(groupTotalCount/couponNum)){//当同一个批次号已发放优惠券数量小于总发行量，则可以继续进行发放
			return false;
		}
		
		int averageNum=0;
		if(null!=vo&&StringUtils.isNotBlank(vo.getA1())){
			averageNum = Integer.parseInt(vo.getA1());
		}
		//查询用户已发放的优惠券总数
		int totalCount=detailDao.getCouponDetail(null, couponGroupID, passengerId);
		
		//判断是否还有组合券的人均领取机会
		if(averageNum > (totalCount/couponNum)){
			return true;
		}
		return false;
    }
	
}
