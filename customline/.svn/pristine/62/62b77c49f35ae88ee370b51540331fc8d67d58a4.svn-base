package com.amwell.dao.coupon.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.coupon.ICouponGroupPassengerDetailDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupPassengerDetail;

/**
 * 用户优惠券详情
 * @author gxt
 *
 */
@Repository(value="couponGroupPassengerDetailDaoImpl")
public class CouponGroupPassengerDetailDaoImpl extends DaoSupport implements ICouponGroupPassengerDetailDao {

	/**
	 * 保存或修改用户优惠券详情
	 * @param detail
	 * @return
	 */
	@Override
	public int updateCouponDetail(CouponGroupPassengerDetail detail) {
		if(null!=detail){
			super.finit("coupon_info");
			if(StringUtils.isBlank(detail.getId())){//添加优惠券
				String sql="INSERT INTO coupon_group_passenger_detail (passengerId, grantId, couponGroupId, couponId, getTime, effectiveTime, expirationTime,telephone) VALUES (?,?,?,?,?,?,?,?)";
				args=new Object[8];
				args[0]=detail.getPassengerId();
				args[1]=detail.getGrantId();
				args[2]=detail.getCouponGroupId();
				args[3]=detail.getCouponId();
				args[4]=detail.getGetTime();
				args[5]=detail.getEffectiveTime();
				args[6]=detail.getExpirationTime();
				args[7]=detail.getTelephone();
				return tableDao.executeSQL(sql, args);
			}
			else{//修改用户优惠券详情
				return tableDao.updateData(detail,"id");
			}
		}
		return 0;
	}
	
	/**
	 * 批量添加优惠券
	 * @param sql
	 * @return
	 */
	@Override
	public int updateCouponDetailBatch(String sql){
		return tableDao.executeSQL(sql);
	}
	
	/**
	 * 查询用户已发放的优惠券总数
	 * @param grantId
	 * @param couponGroupId
	 * @param passengerId
	 * @return
	 */
	@Override
	public int getCouponDetail(String grantId,String couponGroupId,String passengerId){
		super.finit("coupon_group_passenger_detail");
		StringBuffer sql=new StringBuffer("SELECT id FROM coupon_group_passenger_detail WHERE couponGroupId = ?");
		List<String> condition=new ArrayList<String>();
		condition.add(couponGroupId);
		if(StringUtils.isNotBlank(grantId)){
			sql.append(" and grantId = ? ");
			condition.add(grantId);
		}
		if(StringUtils.isNotBlank(passengerId)){
			sql.append(" AND passengerId = ? ");
			condition.add(passengerId);
		}
		return tableDao.queryCount(sql.toString(), condition.toArray());
	}
	
	/**
	 * 根据组合券id查询组合券信息
	 * @param couponGroupId
	 * @return
	 */
	@Override
	public CouponGroup getCouponGroupById(String couponGroupId){
		super.finit("coupon_group");
		String sql="SELECT couponGroupID,couponGroupName,couponGroupCount,averageNum FROM coupon_group WHERE couponGroupID = ?";
		args=new Object[1];
		args[0]=couponGroupId;
		return tableDao.queryBean(CouponGroup.class, sql, args);
	}
	
	/**
	 * 多条件查询用户优惠券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize){
		super.finit("coupon_group_passenger_detail a LEFT JOIN coupon_info b ON a.couponId = b.id");
		StringBuffer sql=new StringBuffer(" SELECT a.telephone,a.couponGroupId,b.couponName,b.couponValue,b.couponCon,a.effectiveTime,a.expirationTime,a.getTime,a.useTime,a.orderId,a.useState " +
				"FROM coupon_group_passenger_detail a LEFT JOIN coupon_info b ON a.couponId = b.id");
		List<String> condition=new ArrayList<String>();
		if(null!=search){
			StringBuffer sb=new StringBuffer();
			int tempNum=0;
			//手机号码
			if(StringUtils.isNotBlank(search.getField01())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" a.telephone = ? ");
				condition.add(search.getField01().trim());
				tempNum++;
			}
			//发放id
			if(StringUtils.isNotBlank(search.getField02())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" a.grantId = ? ");
				condition.add(search.getField02().trim());
				tempNum++;
			}
			//发放时段
			if(StringUtils.isNotBlank(search.getField03())&&StringUtils.isNotBlank(search.getField04())){
				if(tempNum>0){
					sb.append(" and ");
				}
				sb.append(" LEFT(a.getTime,10) >=? AND LEFT(a.getTime,10) <=? ");
				condition.add(search.getField03().trim());
				condition.add(search.getField04().trim());
				tempNum++;
			}
			
			if(tempNum>0){
				sql.append(" WHERE ").append(sb);
			}
		}
		sql.append(" ORDER BY a.getTime DESC ");
		if(pageSize>0){//分页查询
			tableDao.setPageSize(pageSize);
			list = tableDao.queryByPage(CouponGroupPassengerDetail.class, sql.toString(),currentPage,condition.toArray());
			page = new Page(list,sql.toString(),currentPage,pageSize,condition.toArray());
			map.put("page", page);
		}
		else{//不分页查询
			list = tableDao.queryList(CouponGroupPassengerDetail.class, sql.toString(), condition.toArray());
		}
		map.put("list", list);
		return map;
	}
}
