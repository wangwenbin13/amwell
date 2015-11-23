package com.amwell.service.coupon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amwell.base.DaoSupport;
import com.amwell.dao.IPassengerInfoDao;
import com.amwell.dao.coupon.ICouponGroupDao;
import com.amwell.dao.coupon.ICouponGroupGrantDao;
import com.amwell.dao.coupon.ICouponRuleDao;
import com.amwell.entity.Search;
import com.amwell.service.coupon.ICouponGroupGrantService;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupGrant;
import com.amwell.vo.coupon.CouponRule;
import com.amwell.vo.coupon.GrantInfo;

/**
 * 组合券发放信息
 * @author gxt
 *
 */
@Service("couponGroupGrantService")
public class CouponGroupGrantServiceImpl extends DaoSupport implements ICouponGroupGrantService {
	
	@Autowired
	private ICouponGroupGrantDao grantDao;
	
	@Autowired
	private ICouponGroupDao groupDao;
	
	@Autowired
	private ICouponRuleDao couponRuleDao;
	
	@Autowired
	private IPassengerInfoDao dao;
	
	/**
	 * 查询最大id
	 * @return
	 */
	public String getMaxId(){
		return grantDao.getMaxId();
	}

	/**
	 * 保存或修改组合券
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCouponGroupGrant(CouponGroupGrant couponGroupGrant){
		if(!"closed".equals(couponGroupGrant.getModeOfExecution())){
			CouponGroup cg=groupDao.getCouponGroupById(couponGroupGrant.getCouponGroupId());
			if(null==cg||StringUtils.isBlank(cg.getCouponGroupID())){
				return -2;//批次号不存在
			}
			couponGroupGrant.setCouponGroupName(cg.getCouponGroupName());
		}
		String rules=couponGroupGrant.getRules();
		couponGroupGrant.setRules(null);
		
		String couponGroupGrantId = null;
		if(StringUtils.isNotBlank(couponGroupGrant.getId())){//修改时，先将原来的规则信息删除
			couponGroupGrantId = couponGroupGrant.getId();
			couponRuleDao.deleteCouponRule(couponGroupGrantId);
		}
		else{
			couponGroupGrantId = grantDao.getMaxId();
			couponGroupGrantId = (Integer.parseInt(couponGroupGrantId)+1)+"";
		}
		
		int flag=grantDao.updateCouponGroupGrant(couponGroupGrant);
		if(flag<1){
			return flag;
		}
		if(StringUtils.isNotBlank(rules)){//添加规则信息
			//格式：规则名1,条件1,条件值1#规则名2,条件2,条件值2
			CouponRule cr=null;
			String[] obj=rules.split("#");
			for (String string : obj) {
				String[] strs=string.split(",");
				cr=new CouponRule(); 
				String temp=null;
				if(strs[0].equals("telephone")){
					//把手机号码去重
					String[] ss=strs[2].split(";");
					List<String> l=new ArrayList<String>();
					for (String string2 : ss) {
						if(!l.contains(string2)){
							l.add(string2);
						}
					}
					for (String string3 : l) {
						if(null==temp){
							temp=string3;
						}
						else{
							temp=temp+";"+string3;
						}
					}
				}
				else{
					temp=strs[2];
				}
				cr.setRuleName(strs[0]);
				cr.setTheCondition(strs[1]);
				cr.setTheValue(temp);
				cr.setCouponGroupGrantId(couponGroupGrantId);
				cr.setCreateBy(couponGroupGrant.getCreateBy());
				cr.setCreateOn(couponGroupGrant.getCreateOn());
				flag=couponRuleDao.updateCouponRule(cr);
			}
		}
		return flag;
	}
	
	public int updateGrant(CouponGroupGrant couponGroupGrant){
		return grantDao.updateCouponGroupGrant(couponGroupGrant);
	}
	
	/**
	 * 保存或修改组合券发放(带事物处理)
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCouponGroupGrantT(CouponGroupGrant couponGroupGrant){
		return grantDao.updateCouponGroupGrantT(couponGroupGrant);
	}
	
	/**
	 * 发放优惠券
	 * @return
	 */
	public int addCouponDetail(CouponGroupGrant couponGroupGrant){
		//修改发放状态
		int flag=grantDao.updateCouponGroupGrant(couponGroupGrant);
		
		if(flag==1){
			CouponGroupGrant cgg=grantDao.getInfoById(couponGroupGrant.getId());
			 if(cgg.getSendCouponType().equals("sys send")){//系统发放类型的优惠券需要即时发放
				//查询所有用户
//					List<PassengerInfoEntity> passengerList=dao.getAllPassenger();
					//查询所有符合条件的规则
					/*List<GrantInfo> grantList=couponRuleDao.getGrantInfo(couponGroupGrant.getId());
					if((null!=passengerList&&passengerList.size()>0)&&(null!=grantList&&grantList.size()>0)){
						//发放优惠券
						for (GrantInfo grantInfo : grantList) {
							flag=grantInfo.promote(passengerList);
						}
					}*/
			}
		}
		return flag;
	}
	
	public int addCouponDetail(String couponGroupGrantId){
		int flag=0;
		//查询所有用户
		List<PassengerInfoEntity> passengerList=dao.getAllPassenger();
		//查询所有符合条件的规则
		GrantInfo grant=couponRuleDao.getGrantInfo(couponGroupGrantId);
		if(null!=passengerList&&passengerList.size()>0&&null!=grant){
			//发放优惠券
			flag=grant.promote(passengerList);
		}
		return flag;
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
		return grantDao.getListByCondition(search, currentPage, pageSize);
	}
	
	/**
	 * 根据发放id查询发放对象
	 * @param couponGroupGrantId
	 * @return
	 */
	public CouponGroupGrant getInfoById(String couponGroupGrantId){
		return grantDao.getInfoById(couponGroupGrantId);
	}
	
	/**
	 * 根据发放id查询用户发放详情
	 * @param couponGroupGrantId
	 * @return
	 */
	public Map<String, Object> getDetail(String couponGroupGrantId,int currentPage, int pageSize){
		return grantDao.getDetail(couponGroupGrantId,currentPage,pageSize);
	}
}
