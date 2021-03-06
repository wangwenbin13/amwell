package com.amwell.dao.coupon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.coupon.ICouponGroupPassengerDetailDao;
import com.amwell.dao.coupon.ICouponInfoDao;
import com.amwell.dao.coupon.ICouponRuleDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_3;
import com.amwell.vo.coupon.CouponGrant;
import com.amwell.vo.coupon.CouponInfo;
import com.amwell.vo.coupon.CouponRule;
import com.amwell.vo.coupon.GrantInfo;
import com.amwell.vo.coupon.Rule;
import com.amwell.vo.coupon.RuleSet;

/**
 * 优惠券发放规则信息
 * @author gxt
 *
 */
@Repository(value="couponRuleDaoImpl")
public class CouponRuleDaoImpl extends DaoSupport implements ICouponRuleDao {
	
	@Autowired
	private ICouponInfoDao couponDao;
	@Autowired
	private ICouponGroupPassengerDetailDao detailDao;
	
	/**
	 * 保存或修改组合券
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCouponRule(CouponRule couponRule){
		if(null!=couponRule){
			//保存规则信息
			super.finit("coupon_rule");
			if(StringUtils.isBlank(couponRule.getId())){//添加
				String sql="INSERT INTO coupon_rule (ruleName,theCondition,theValue,couponGroupGrantId,createBy,createOn) VALUES(?,?,?,?,?,?)";
				args=new Object[6];
				args[0]=couponRule.getRuleName();
				args[1]=couponRule.getTheCondition();
				args[2]=couponRule.getTheValue();
				args[3]=couponRule.getCouponGroupGrantId();
				args[4]=couponRule.getCreateBy();
				args[5]=couponRule.getCreateOn();
				return tableDao.executeSQL(sql, args);
			}
			else{//修改
				return tableDao.updateData(couponRule,"id");
			}
		}
		return 0;
	}
	
	/**
	 * 删除规则信息
	 * @param grantId
	 * @return
	 */
	@Override
	public int deleteCouponRule(String grantId){
		String sql="delete FROM coupon_rule WHERE couponGroupGrantId = ?";
		args=new Object[1];
		args[0]=grantId;
		return tableDao.executeSQL(sql, args);
	}
	
	/**
	 * 多条件查询组合券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search, int currentPage,
			int pageSize) {
		super.finit("coupon_rule");
		StringBuffer sql=new StringBuffer(" SELECT cr.*,cgg.couponGroupName,sya.userName FROM coupon_rule cr LEFT JOIN coupon_group_grant cgg ON " +
				"cr.couponGroupGrantId = cgg.id LEFT JOIN sys_admin sya ON cr.createBy = sya.userId ");
		List<String> condition=new ArrayList<String>();
		if(null!=search){
			StringBuffer sb=new StringBuffer();
			int tempNum=0;
			//规则名称
			if(StringUtils.isNotBlank(search.getField01())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" cr.ruleName LIKE ? ");
				condition.add("%"+search.getField01().trim()+"%");
				tempNum++;
			}
			//配置人
			if(StringUtils.isNotBlank(search.getField02())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" sya.userName LIKE ? ");
				condition.add("%"+search.getField02().trim()+"%");
				tempNum++;
			}
			//组合券名称
			if(StringUtils.isNotBlank(search.getField03())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" cgg.couponGroupName LIKE ? ");
				condition.add("%"+search.getField03().trim()+"%");
				tempNum++;
			}
			
			if(tempNum>0){
				sql.append(" WHERE ").append(sb);
			}
		}
		sql.append(" order by cr.createOn desc ");
		
		if(pageSize>0){//分页查询
			tableDao.setPageSize(pageSize);
			list = tableDao.queryByPage(CouponRule.class, sql.toString(),currentPage,condition.toArray());
			page = new Page(list,sql.toString(),currentPage,pageSize,condition.toArray());
			map.put("list", list);
			map.put("page", page);
		}
		else{//不分页查询
			list = tableDao.queryList(CouponRule.class, sql.toString(), condition.toArray());
			map.put("list", list);
		}
		return map;
	}
	
	/**
	 * 根据城市id获取买过票的用户id
	 * @param lineBaseId
	 * @return
	 */
	@Override
	public List<AppVo_1> getLeaseBase(String lineBaseId){
		super.finit("lease_base_info");
		String sql="SELECT distinct passengerId AS a1 FROM lease_base_info WHERE lineBaseId = ? AND ispay='1'";
		args=new Object[1];
		args[0]=lineBaseId;
		return tableDao.queryList(AppVo_1.class, sql, args);
	}
	
	/**
	 * 根据注册时间、条件获取在指定时间范围内注册的用户id
	 * @param registTime
	 * @param theCondition
	 * @return
	 */
	public List<AppVo_1> getPassenger(String registTime,String theCondition){
		super.finit("lease_base_info");
		String sql="SELECT passengerId as a1 FROM passenger_info WHERE left(registerTime,10) "+theCondition+" ?";
		args=new Object[1];
		args[0]=registTime;
		return tableDao.queryList(AppVo_1.class, sql, args);
	}
	
	/**
	 * 查询规则信息
	 * @return
	 */
	/*public List<GrantInfo> getGrantInfo(String couponGroupGrantId){
		List<GrantInfo> list=new ArrayList<GrantInfo>();
		List<AppVo_1> vo1=null;
		if(StringUtils.isBlank(couponGroupGrantId)){
			//查询所有的优惠券发放id
			super.finit("coupon_group_grant");
			String sql="SELECT id AS a1 FROM coupon_group_grant WHERE sendCouponType = 'sys send' AND startTime <= '"+MyDate.getMyDate3()+"' AND endTime >= '"+MyDate.getMyDate3() +
					"' AND modeOfExecution = 'send'";
			vo1=tableDao.queryList(AppVo_1.class, sql);
		}
		else{
			vo1=new ArrayList<AppVo_1>();
			AppVo_1 vo=new AppVo_1();
			vo.setA1(couponGroupGrantId);
			vo1.add(vo);
		}
		if(null!=vo1&&vo1.size()>0){
			RuleSet ruleSet=null;
			GrantInfo grant=null;
			for (AppVo_1 appVo_1 : vo1) {
				
				List<CouponInfo> couponList=couponDao.getSysSendCoupon(appVo_1.getA1());
//				if(doValidateCount(couponList,null)){
//					
//				}
				super.finit("coupon_rule");
				//根据优惠券发放id查询对应的规则
				sql="SELECT ruleName as a1,theCondition as a2,theValue as a3 FROM coupon_rule WHERE couponGroupGrantId = ?";
				args=new Object[1];
				args[0]=appVo_1.getA1();
				List<AppVo_3> ruleList=tableDao.queryList(AppVo_3.class, sql, args);
				if(null!=ruleList&&ruleList.size()>0){
					List<Rule> l=new ArrayList<Rule>();
					Rule rule=null;
					for (AppVo_3 r : ruleList) {
						rule=Rule.create(r);
						l.add(rule);
					}
					ruleSet=new RuleSet();
					ruleSet.setRules(l);
					ruleSet.setCouponRuleDao(this);
					grant=new GrantInfo();
					grant.setRuleSet(ruleSet);
					CouponGrant couponGrant=new CouponGrant();
					couponGrant.setDetailDao(detailDao);
					//查询系统发放类型的优惠券
					couponGrant.setCouponList(couponList);
					grant.setCouponGrant(couponGrant);
					list.add(grant);
				}
			}
		}
		return list;
	}*/
	
	public GrantInfo getGrantInfo(String couponGroupGrantId){
		if(StringUtils.isBlank(couponGroupGrantId)){
			return null;
		}
		
		//根据优惠券发放id查询对应的规则
		super.finit("coupon_rule");
		sql="SELECT ruleName as a1,theCondition as a2,theValue as a3 FROM coupon_rule WHERE couponGroupGrantId = ?";
		args=new Object[1];
		args[0]=couponGroupGrantId;
		List<AppVo_3> ruleList=tableDao.queryList(AppVo_3.class, sql, args);
		if(null==ruleList||ruleList.isEmpty()){
			return null;
		}

		//封装grant对象
		GrantInfo grant=new GrantInfo();
		List<Rule> l=new ArrayList<Rule>();
		Rule rule=null;
		for (AppVo_3 r : ruleList) {
			rule=Rule.create(r);
			l.add(rule);
		}
		RuleSet ruleSet=new RuleSet();
		ruleSet.setRules(l);
		ruleSet.setCouponRuleDao(this);
		grant.setRuleSet(ruleSet);
		
		CouponGrant couponGrant=new CouponGrant();
		couponGrant.setDetailDao(detailDao);
		//查询需要发放的优惠券
	    List<CouponInfo> couponList=couponDao.getSysSendCoupon(couponGroupGrantId);
		couponGrant.setCouponList(couponList);
		grant.setCouponGrant(couponGrant);
		
		return grant;
	}
	
	/**
	 * 验证是否还有领取机会(以后待用)
	 * @param couponList
	 * @param passengerId
	 * @return
	 */
    @SuppressWarnings("unused")
	private boolean doValidateCount(List<CouponInfo> couponList,String passengerId){
		
		//组合券中但个券个数
		int couponNum=0;
		for (CouponInfo couponInfo : couponList) {
			couponNum=couponNum+Integer.parseInt(couponInfo.getNum());
		}
		
		String couponGroupID=couponList.get(0).getCouponGroupId();
		
		//根据批次号获取组合券人均领取量
		super.finit("coupon_group");
		String sql="SELECT averageNum AS a1,couponGroupCount as a2 FROM coupon_group WHERE couponGroupID = ?";
		args=new Object[1];
		args[0]=couponGroupID;
		AppVo_2 vo=tableDao.queryBean(AppVo_2.class, sql, args);
		/*int couponGroupCount=0;
		if(null!=vo&&StringUtils.isNotBlank(vo.getA2())){
			couponGroupCount = Integer.parseInt(vo.getA2());
		}
		
		//查询用户已发放的优惠券总数
		int groupTotalCount=detailDao.getCouponDetail(null,couponGroupID,null);
		if(couponGroupCount>(groupTotalCount/couponNum)){//当同一个批次号已发放优惠券数量小于总发行量，则可以继续进行发放
			return true;
		}以后待用*/
		
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
	
	/**
	 * 根据购票次数、条件获取在指定购票数范围内的用户id
	 * @param ticketNumber
	 * @param theCondition
	 * @return
	 */
	public List<AppVo_1> getTicketNumber(String ticketNumber,String theCondition){
		super.finit("lease_base_info");
		String sql=null;
		if((ticketNumber.equals("0")&&theCondition.equals("="))||((Integer.parseInt(ticketNumber)<=1)&&theCondition.equals("<"))){
			//查询没买过票的用户和买票了未付款的用户
			sql="SELECT passengerId AS a1 FROM passenger_info WHERE passengerId NOT IN(SELECT DISTINCT passengerId FROM lease_base_info WHERE ispay = '1')";
			return tableDao.queryList(AppVo_1.class, sql);
		}
		else if((Integer.parseInt(ticketNumber)>1)&&theCondition.equals("<")){
			//小于一个买票次数时，要查没买过票的用户和部分买票用户
			sql="SELECT passengerId AS a1 FROM passenger_info WHERE passengerId NOT IN(SELECT DISTINCT passengerId FROM lease_base_info WHERE ispay = '1')";
			List<AppVo_1> list1=tableDao.queryList(AppVo_1.class, sql);
			sql="SELECT passengerId AS a1 FROM lease_base_info WHERE ispay = '1' GROUP BY passengerId HAVING COUNT(leaseOrderId) "+theCondition+" ? ";
			args=new Object[1];
			args[0]=ticketNumber;
			List<AppVo_1> list2= tableDao.queryList(AppVo_1.class, sql, args);
			List<AppVo_1> list3=new ArrayList<AppVo_1>();
			if(null!=list1&&list1.size()>0){
				list3.addAll(list1);
			}
			if(null!=list2&&list2.size()>0){
				list3.addAll(list2);
			}
			return list3;
		}
		else{
			//等于或大于指定买票数的用户
			sql="SELECT passengerId AS a1 FROM lease_base_info WHERE ispay = '1' GROUP BY passengerId HAVING COUNT(leaseOrderId) "+theCondition+" ? ";
			args=new Object[1];
			args[0]=ticketNumber;
			return tableDao.queryList(AppVo_1.class, sql, args);
		}
	}
	
	/**
	 * 根据推荐人数、条件获取在指定推荐人数范围内的用户id
	 * @param recommendNumber
	 * @param theCondition
	 * @return
	 */
	public List<AppVo_1> getRecommendNumber(String recommendNumber,String theCondition){
		super.finit("recommend_award_info rai,passenger_info p");
		String sql=null;
		if(recommendNumber.equals("0")&&theCondition.equals("=")){
			sql="SELECT passengerId AS a1 FROM passenger_info WHERE passengerId NOT IN(SELECT DISTINCT p.passengerId FROM recommend_award_info rai,passenger_info p WHERE rai.oldUserPhone = p.telephone AND rai.delFlag IN('2','3'))";
			return tableDao.queryList(AppVo_1.class, sql);
		}
		else{
			sql="SELECT p.passengerId AS a1 FROM recommend_award_info rai,passenger_info p WHERE rai.oldUserPhone = p.telephone AND rai.delFlag IN('2','3') GROUP BY p.passengerId HAVING COUNT(rai.id) "+theCondition+" ? ";
			args=new Object[1];
			args[0]=recommendNumber;
			return tableDao.queryList(AppVo_1.class, sql, args);
		}
	}
	
	/**
	 * 获取被推荐用户id
	 * @return
	 */
	public List<AppVo_1> getIsRecommended(){
		super.finit("recommend_award_info rai,passenger_info p");
		String sql="SELECT p.passengerId AS a1 FROM recommend_award_info rai,passenger_info p WHERE rai.newUserPhone = p.telephone and rai.delFlag = '2'";
		return tableDao.queryList(AppVo_1.class, sql);
	}
	
	/**
	 * 根据发放id查询发放规则
	 * @param couponGroupGrantId
	 * @return
	 */
	public List<CouponRule> getCouponRule(String couponGroupGrantId){
		super.finit("coupon_rule");
		String sql="SELECT * FROM coupon_rule WHERE couponGroupGrantId = ?";
		args=new Object[1];
		args[0]=couponGroupGrantId;
		List<CouponRule> list=tableDao.queryList(CouponRule.class, sql, args);
		if(null!=list&&list.size()>0){
			for (CouponRule couponRule : list) {
				if(couponRule.getRuleName().equals("cityCode")){
					//根据cityCode查询城市名称
					super.finit("sys_area");
					sql="SELECT areaName AS a1 FROM sys_area WHERE arearCode = ?";
					args=new Object[1];
					args[0]=couponRule.getTheValue();
					AppVo_1 vo=tableDao.queryBean(AppVo_1.class, sql, args);
					if(null!=vo&&StringUtils.isNotBlank(vo.getA1())){
						couponRule.setTheValue(vo.getA1());
					}
				}
				if(couponRule.getRuleName().equals("lineBaseId")){
					//根据lineBaseId查询线路名称
					super.finit("sys_area");
					sql="SELECT lineName AS a1 FROM line_base_info WHERE lineBaseId = ?";
					args=new Object[1];
					args[0]=couponRule.getTheValue();
					AppVo_1 vo=tableDao.queryBean(AppVo_1.class, sql, args);
					if(null!=vo&&StringUtils.isNotBlank(vo.getA1())){
						couponRule.setTheValue(vo.getA1());
					}
				}
				if(couponRule.getRuleName().equals("terminal")){
					//设备类型
					if(couponRule.getTheValue().equals("1")){
						couponRule.setTheValue("android");
					}
					if(couponRule.getTheValue().equals("2")){
						couponRule.setTheValue("ios");
					}
					if(couponRule.getTheValue().equals("3")){
						couponRule.setTheValue("微信");
					}
				}
				if(couponRule.getRuleName().equals("sourcefrom")){
					//用户来源
					if(couponRule.getTheValue().equals("0")){
						couponRule.setTheValue("小猪巴士");
					}
					if(couponRule.getTheValue().equals("1")){
						couponRule.setTheValue("蛇口");
					}
					if(couponRule.getTheValue().equals("2")){
						couponRule.setTheValue("彩生活");
					}
					if(couponRule.getTheValue().equals("3")){
						couponRule.setTheValue("微信");
					}
				}
				if(couponRule.getRuleName().equals("sex")){
					//性别
					if(couponRule.getTheValue().equals("0")){
						couponRule.setTheValue("男");
					}
					if(couponRule.getTheValue().equals("1")){
						couponRule.setTheValue("女");
					}
				}
				if(couponRule.getRuleName().equals("isRecommended")||couponRule.getRuleName().equals("isDoRecommend")){
					//是否被推荐
					if(couponRule.getTheValue().equals("0")){
						couponRule.setTheValue("否");
					}
					if(couponRule.getTheValue().equals("1")){
						couponRule.setTheValue("是");
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据推荐时间、条件获取在指定时间范围内推荐的用户id
	 * @param recommendTime
	 * @param theCondition
	 * @return
	 */
	@Override
	public List<AppVo_1> getRePassenger(String type,String recommendTime,String theCondition){
		super.finit("lease_base_info");
		StringBuffer sql=null;
		if(type.equals("old")){
			sql=new StringBuffer("SELECT DISTINCT p.passengerId AS a1 FROM recommend_award_info rai LEFT JOIN passenger_info p ON " +
					"rai.oldUserPhone = p.telephone WHERE left(rai.createOn,10) "+theCondition+" ? and rai.delFlag = '3'");
		}
		else{
			sql=new StringBuffer("SELECT DISTINCT p.passengerId AS a1 FROM recommend_award_info rai LEFT JOIN passenger_info p ON " +
					"rai.newUserPhone = p.telephone WHERE left(rai.createOn,10) "+theCondition+" ? and rai.delFlag = '2'");
		}
		List<String> condition=new ArrayList<String>();
		condition.add(recommendTime);
		return tableDao.queryList(AppVo_1.class, sql.toString(), condition.toArray());
	}
	
	/**
	 * 获取推荐用户id
	 * @return
	 */
	@Override
	public List<AppVo_1> getIsDoRecommend(String passengerId){
		super.finit("recommend_award_info rai,passenger_info p");
		String sql=null;
		if(StringUtils.isNotBlank(passengerId)){
			sql="SELECT DISTINCT p.passengerId AS a1 FROM recommend_award_info rai,passenger_info p WHERE rai.oldUserPhone = p.telephone and p.passengerId = ? and rai.delFlag = '3'";
			args=new Object[1];
			args[0]=passengerId;
			return tableDao.queryList(AppVo_1.class, sql,args);
		}
		else{
			sql="SELECT DISTINCT p.passengerId AS a1 FROM recommend_award_info rai,passenger_info p WHERE rai.oldUserPhone = p.telephone and rai.delFlag = '3'";
			return tableDao.queryList(AppVo_1.class, sql);
		}
	}
	
	/**
	 * 根据购票时间、条件获取在指定时间范围内买票的用户id
	 * @param ticketTime
	 * @param theCondition
	 * @return
	 */
	public List<AppVo_1> getTicketPassenger(String ticketTime,String theCondition){
		String sql="SELECT DISTINCT lbo.passengerId as a1 FROM"
				+ " lease_base_info lbi LEFT JOIN line_business_order lbo ON lbi.leaseOrderNo = lbo.leaseOrderId"
				+ " LEFT JOIN line_class_info lci ON lbo.lineClassId = lci.lineClassId"
				+ " WHERE lbi.ispay = '1' AND lbo.status IN('0','2') AND lci.orderDate "+theCondition+" ?";
		args=new Object[1];
		args[0]=ticketTime;
		return tableDao.queryList(AppVo_1.class, sql,args);
	}
}
