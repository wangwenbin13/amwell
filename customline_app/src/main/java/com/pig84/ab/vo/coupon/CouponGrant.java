package com.pig84.ab.vo.coupon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pig84.ab.dao.ICouponGroupPassengerDetailDao;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.PassengerInfo;


public class CouponGrant {
	
	private Logger logger = LoggerFactory.getLogger(CouponGrant.class);
	private ICouponGroupPassengerDetailDao detailDao;
	
	private List<CouponInfo> couponList;
	
	public int send(PassengerInfo pie) {
		if(null == couponList || couponList.size() <= 0) {
			logger.info("发放【系统发放】类型的优惠券,优惠券信息为空");
			return 0;
		}
		//发放优惠券
		String nowTime=MyDate.Format.DATETIME.now();
		
		//组合券中单个券个数
		int couponNum=0;
		for (CouponInfo couponInfo : couponList) {
			couponNum=couponNum+Integer.parseInt(couponInfo.getNum());
		}
		String couponGroupId = couponList.get(0).getCouponGroupId();
		
		//人均领取量
		int averageNum=Integer.parseInt(detailDao.getCouponGroupById(couponGroupId).getAverageNum());
		
		List<PassengerInfo> tempList=new ArrayList<PassengerInfo>();
		List<String> l=new ArrayList<String>();
		int totalCount=detailDao.getCouponDetail(null, couponGroupId, pie.getPassengerId());
		if(averageNum>(totalCount/couponNum)){//判断用户是否还有领取机会
			if(!l.contains(pie.getPassengerId())){
				l.add(pie.getPassengerId());
				tempList.add(pie);
			}
		}
		else{
			logger.info("============="+pie.getTelephone()+"已无机会领取"+couponGroupId+"=====================");
		}
		if(tempList.size()==0){//所有用户都无领取机会
			return -3;
		}
		else{
			StringBuffer sql=new StringBuffer("INSERT INTO coupon_group_passenger_detail (passengerId, grantId, couponGroupId, couponId, getTime, effectiveTime, expirationTime,telephone) VALUES");
			for (PassengerInfo  passenger: tempList) {
				for (CouponInfo couponInfo : couponList) {
					CouponGroupPassengerDetail detail=new CouponGroupPassengerDetail();
					detail.setPassengerId(passenger.getPassengerId());
					detail.setTelephone(passenger.getTelephone());
					detail.setGrantId(couponInfo.getGrantId());
					detail.setCouponGroupId(couponInfo.getCouponGroupId());
					detail.setCouponId(couponInfo.getId());
					detail.setGetTime(nowTime);
					if(StringUtils.isNotBlank(couponInfo.getDelayDays())){
						detail.setEffectiveTime(nowTime.split(" ")[0]);
						
						Integer days = Integer.valueOf(couponInfo.getDelayDays());
						Date d = DateUtils.addDays(MyDate.Format.DATE.parse(nowTime), days);
						String endTime= MyDate.Format.DATE.format(d);
						detail.setExpirationTime(endTime);
					}
					if(StringUtils.isNotBlank(couponInfo.getEffectiveTime())&&StringUtils.isNotBlank(couponInfo.getExpirationTime())){
						detail.setEffectiveTime(couponInfo.getEffectiveTime());
						detail.setExpirationTime(couponInfo.getExpirationTime());
					}
					for(int i=0;i<Integer.parseInt(couponInfo.getNum());i++){//每个人按限领份数发放多个优惠券
						sql.append("('"+detail.getPassengerId()+"',"+detail.getGrantId()+","+detail.getCouponGroupId()+","+detail.getCouponId()+",'"+detail.getGetTime()+"','"+detail.getEffectiveTime()
								+"','"+detail.getExpirationTime()+"','"+detail.getTelephone()+"'),");
					}
				}
			}
			//批量添加优惠券
			return detailDao.updateCouponDetailBatch(sql.substring(0,sql.length()-1));
		}
	}

	public ICouponGroupPassengerDetailDao getDetailDao() {
		return detailDao;
	}

	public void setDetailDao(ICouponGroupPassengerDetailDao detailDao) {
		this.detailDao = detailDao;
	}

	public List<CouponInfo> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<CouponInfo> couponList) {
		this.couponList = couponList;
	}
	
}
