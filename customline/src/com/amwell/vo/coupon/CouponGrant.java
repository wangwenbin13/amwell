package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.amwell.commons.MyDate;
import com.amwell.dao.coupon.ICouponGroupPassengerDetailDao;
import com.amwell.vo.PassengerInfoEntity;


public class CouponGrant {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	private ICouponGroupPassengerDetailDao detailDao;
	
	private List<CouponInfo> couponList;
	
	public int send(List<PassengerInfoEntity> passengerList) {
		int flag=0;
		if(null!=couponList&&couponList.size()>0){
			//发放优惠券
			CouponGroupPassengerDetail detail=null;
			String nowTime=MyDate.getMyDateLong();
//			String temp=nowTime.split(" ")[1];
			
			//组合券中单个券个数
			int couponNum=0;
			for (CouponInfo couponInfo : couponList) {
				couponNum=couponNum+Integer.parseInt(couponInfo.getNum());
			}
			String couponGroupId = couponList.get(0).getCouponGroupId();
			//人均领取量
			int averageNum=Integer.parseInt(detailDao.getCouponGroupById(couponGroupId).getAverageNum());
			
			List<PassengerInfoEntity> tempList=new ArrayList<PassengerInfoEntity>();
			List<String> l=new ArrayList<String>();
			for (PassengerInfoEntity pie : passengerList) {
				int totalCount=detailDao.getCouponDetail(null, couponGroupId, pie.getPassengerId());
				if(averageNum>(totalCount/couponNum)){//判断用户是否还有领取机会
					if(!l.contains(pie.getPassengerId())){
						l.add(pie.getPassengerId());
						tempList.add(pie);
					}
				}
				else{
					System.out.println("============="+pie.getTelephone()+"已无机会领取"+couponGroupId+"=====================");
				}
			}
			if(tempList.size()==0){//所有用户都无领取机会
				flag=-3;
			}
			else{
				StringBuffer sql=new StringBuffer("INSERT INTO coupon_group_passenger_detail (passengerId, grantId, couponGroupId, couponId, getTime, effectiveTime, expirationTime,telephone) VALUES");
				for (PassengerInfoEntity  passenger: tempList) {
					for (CouponInfo couponInfo : couponList) {
						detail=new CouponGroupPassengerDetail();
						detail.setPassengerId(passenger.getPassengerId());
						detail.setTelephone(passenger.getTelephone());
						detail.setGrantId(couponInfo.getGrantId());
						detail.setCouponGroupId(couponInfo.getCouponGroupId());
						detail.setCouponId(couponInfo.getId());
						detail.setGetTime(nowTime);
						if(StringUtils.isNotBlank(couponInfo.getDelayDays())){
							detail.setEffectiveTime(nowTime.split(" ")[0]);
//							String endTime=MyDate.getNextDay(nowTime,couponInfo.getDelayDays())+" "+temp;
							String endTime=MyDate.getNextDay(nowTime,couponInfo.getDelayDays());
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
				flag=detailDao.updateCouponDetailBatch(sql.substring(0,sql.length()-1));
			}
		}
		else{
			logger.info("发放【系统发放】类型的优惠券,优惠券信息为空");
		}
		return flag;
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
	
	public static void main(String[] args) {
		List<Integer> list=new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			list.add(i);
		}
		
		int temp=1;
		int pageSize=3;
		for (int i = temp*pageSize; i < temp*pageSize+pageSize; i++) {
			System.out.println(list.get(i));
		}
		System.out.println("");
		System.out.println(list.get(temp*pageSize));
		System.out.println(list.get(temp*pageSize+pageSize-1));
	}
}
