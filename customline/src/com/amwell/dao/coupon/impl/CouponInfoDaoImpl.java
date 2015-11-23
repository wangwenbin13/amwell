package com.amwell.dao.coupon.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.dao.coupon.ICouponInfoDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.coupon.CouponInfo;
import com.amwell.vo.gf.temp.CouponAndGiftTemp;
import com.amwell.vo.gf.temp.CouponPassengerTemp;
import com.amwell.vo.gf.temp.CouponTemp;
import com.amwell.vo.gf.temp.GiftTemp;

/**
 * 优惠券信息
 * @author gxt
 *
 */
@Repository(value="couponInfoDaoImpl")
public class CouponInfoDaoImpl extends DaoSupport implements ICouponInfoDao {

	/**
	 * 保存或修改优惠券
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateCoupon(CouponInfo coupon) {
		if(null!=coupon){
			super.finit("coupon_info");
			if(StringUtils.isBlank(coupon.getId())){//添加优惠券
				String sql="INSERT INTO coupon_info (couponName, couponValue, couponCon, delayDays, createBy, createOn,effectiveTime,expirationTime,num,couponCount,couponGroupId,couponGroupName) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				args=new Object[12];
				args[0]=coupon.getCouponName();
				args[1]=coupon.getCouponValue();
				args[2]=coupon.getCouponCon();
				args[3]=coupon.getDelayDays();
				args[4]=coupon.getCreateBy();
				args[5]=coupon.getCreateOn();
				args[6]=coupon.getEffectiveTime();
				args[7]=coupon.getExpirationTime();
				args[8]=coupon.getNum();
				args[9]=coupon.getCouponCount();
				args[10]=coupon.getCouponGroupId();
				args[11]=coupon.getCouponGroupName();
				return tableDao.executeSQL(sql, args);
			}
			else{//修改优惠券
				return tableDao.updateData(coupon,"id");
			}
		}
		return 0;
	}

	/**
	 * 删除优惠券（逻辑删除）
	 * @param couponIds
	 * @return
	 */
	@Override
	public int delCoupon(String couponIds) {
		if(StringUtils.isNotBlank(couponIds)){
			//组装in条件
			String[] strs=couponIds.split(",");
			List<String> condition=new ArrayList<String>();
			StringBuffer sb=new StringBuffer();
			for (String s : strs) {
				condition.add(s);
				sb.append("?,");
			}
			super.finit("coupon_info");
			//isDeltinyint(1) NULL使用状态 0:正常 1:已删除(与组合券有关联的优惠券不能删除)
			String sql="UPDATE coupon_info SET isDel = 1 WHERE couponGroupId is null and id IN("+sb.substring(0,sb.length()-1)+")";
			return tableDao.executeSQL(sql, condition.toArray());
		}
		return 0;
	}
	
	/**
	 * 多条件查询优惠券列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search, int currentPage,
			int pageSize) {
		super.finit("coupon_info");
		StringBuffer sql=new StringBuffer(" SELECT ci.*,sya.userName FROM coupon_info ci LEFT JOIN sys_admin sya ON ci.createBy = sya.userId WHERE ci.isDel = 0 ");
		List<String> condition=new ArrayList<String>();
		if(null!=search){
			//批次号
			if(StringUtils.isNotBlank(search.getField01())){
				sql.append(" AND ci.couponGroupId LIKE ? ");
				condition.add("%"+search.getField01().trim()+"%");
			}
			//优惠券面值
			if(StringUtils.isNotBlank(search.getField02())){
				sql.append(" AND ci.couponValue = ? ");
				condition.add(search.getField02().trim());
			}
			//优惠券门槛
			if(StringUtils.isNotBlank(search.getField03())){
				sql.append(" AND ci.couponCon = ? ");
				condition.add(search.getField03().trim());
			}
			//创建人名称
			if(StringUtils.isNotBlank(search.getField04())){
				sql.append(" AND sya.userName LIKE ? ");
				condition.add("%"+search.getField04().trim()+"%");
			}
			//组合券名称
			if(StringUtils.isNotBlank(search.getField05())){
				sql.append(" AND ci.couponGroupName LIKE ? ");
				condition.add("%"+search.getField05().trim()+"%");
			}
			//优惠券ids
			if(StringUtils.isNotBlank(search.getField06())&&StringUtils.isNotBlank(search.getField07())){
				String[] str=search.getField06().split(",");
				StringBuffer temp=new StringBuffer();
				for (String string : str) {
					condition.add(string);
					temp.append("?,");
				}
				//search.getField07()为in或者not in
				sql.append(" AND ci.id "+search.getField07()+" ("+temp.substring(0,temp.length()-1)+") ");
			}
			//组合券id
			if(StringUtils.isNotBlank(search.getField08())){
				sql.append(" AND ci.couponGroupId = ? ");
				condition.add("%"+search.getField08().trim()+"%");
			}
		}
		sql.append(" order by ci.createOn desc ");
		
		if(pageSize>0){//分页查询
			tableDao.setPageSize(pageSize);
			list = tableDao.queryByPage(CouponInfo.class, sql.toString(),currentPage,condition.toArray());
			page = new Page(list,sql.toString(),currentPage,pageSize,condition.toArray());
			map.put("page", page);
		}
		else{//不分页查询
			list = tableDao.queryList(CouponInfo.class, sql.toString(), condition.toArray());
		}
		
		map.put("list", list);
		return map;
	}
	
	/**
	 * 解除组合券与原优惠券的关联
	 * @param coupon
	 * @return
	 */
	@Override
	public int updateRelation(String couponGroupId) {
		super.finit("coupon_info");
		String sql="UPDATE coupon_info SET couponGroupId = (NULL),couponGroupName = (NULL),num = 0 WHERE couponGroupId = ?";
		args=new Object[1];
		args[0]=couponGroupId;
		return tableDao.executeSQL(sql, args);
	}
	
	/**
	 * 查询优惠券对应的组合券信息
	 * @param couponId
	 * @param couponName
	 * @return
	 */
	@Override
	public List<CouponInfo> getCouponGroup(String couponId,String couponName,String effectiveTime,String expirationTime){
		super.finit("coupon_info");
		StringBuffer sql=new StringBuffer("SELECT DISTINCT couponGroupId,couponGroupName,num FROM coupon_info WHERE couponGroupId IS NOT NULL");
		List<Object> condition=new ArrayList<Object>();
		if(StringUtils.isNotBlank(couponId)){
			sql.append(" and id = ? ");
			condition.add(couponId);
		}
		if(StringUtils.isNotBlank(couponName)){
			sql.append(" and couponName LIKE ? ");
			condition.add("%"+couponName+"%");
		}
		if(StringUtils.isNotBlank(effectiveTime)){
			sql.append(" and effectiveTime >= ? ");
			condition.add(effectiveTime);
		}
		if(StringUtils.isNotBlank(expirationTime)){
			sql.append(" and expirationTime <= ? ");
			condition.add(expirationTime);
		}
		return tableDao.queryList(CouponInfo.class, sql.toString(), condition.toArray());
	}
	
	/**
	 * 查询同一组合券中优惠券面值是否已经存在
	 * @param couponName
	 * @return
	 */
	public List<CouponInfo> getCouponInfo(String couponValue,String couponGroupId){
		super.finit("coupon_info");
		StringBuffer sql=new StringBuffer(" SELECT id,couponName FROM coupon_info WHERE couponValue = ? and couponGroupId = ? ");
		args=new Object[2];
		args[0]=couponValue;
		args[1]=couponGroupId;
		return tableDao.queryList(CouponInfo.class, sql.toString(), args);
	}
	
	/**
	 * 根据组合券查询优惠券信息
	 * @param couponId
	 * @param couponName
	 * @return
	 */
	@Override
	public List<CouponInfo> getCouponByGroup(String couponGroupId,String couponGroupName){
		super.finit("coupon_info");
		StringBuffer sql=new StringBuffer(" SELECT couponName,couponValue,num FROM coupon_info WHERE isDel = 0 ");
		List<Object> condition=new ArrayList<Object>();
		if(StringUtils.isNotBlank(couponGroupId)){
			sql.append(" and couponGroupId = ? ");
			condition.add(couponGroupId);
		}
		if(StringUtils.isNotBlank(couponGroupName)){
			sql.append(" and couponGroupName LIKE ? ");
			condition.add("%"+couponGroupName+"%");
		}
		return tableDao.queryList(CouponInfo.class, sql.toString(), condition.toArray());
	}
	
	/**
	 * 获取所有系统发放类型的优惠券
	 * @return
	 */
	@Override
	public List<CouponInfo> getSysSendCoupon(String couponGroupGrantId){
		super.finit("coupon_info");
		String sql="SELECT cgg.id AS grantId,cgg.couponGroupId,ci.id,ci.delayDays,ci.num,ci.effectiveTime,ci.expirationTime FROM" +
				" coupon_group_grant cgg LEFT JOIN coupon_group cg ON cgg.couponGroupId = cg.couponGroupID LEFT JOIN coupon_info ci ON cg.couponGroupID = ci.couponGroupId " +
				"WHERE cgg.sendCouponType = 'sys send' AND cgg.startTime <= '"+MyDate.getMyDate3()+"' AND cgg.endTime >= '"+MyDate.getMyDate3()+"' AND cgg.modeOfExecution = 'closed'";
		List<CouponInfo> list=null;
		if(StringUtils.isNotBlank(couponGroupGrantId)){
			sql=sql+" and cgg.id = ? ";
			args=new Object[1];
			args[0]=couponGroupGrantId;
			list=tableDao.queryList(CouponInfo.class, sql,args);
		}
		else{
			list=tableDao.queryList(CouponInfo.class, sql);
		}
		return list;
	}
	
	/**
	 * 根据优惠券名称查询优惠券id和数量
	 * @param couponName
	 * @return
	 */
	public String getCouponIds(String[] couponName){
		if(null!=couponName&&couponName.length>0){
			List<String> conditionList=new ArrayList<String>();
			StringBuffer sb=new StringBuffer();
			for (String string : couponName) {
				conditionList.add(string);
				sb.append("?,");
			}
			super.finit("coupon_info");
			String sql="SELECT id AS a1,num as a2 FROM coupon_info WHERE couponName IN("+sb.substring(0,sb.length()-1)+")";
			List<AppVo_2> list=tableDao.queryList(AppVo_2.class,sql,conditionList.toArray());
			if(null!=list&&list.size()>0){
				String temp="";
				for (AppVo_2 appVo_1 : list) {
					temp=temp+appVo_1.getA1()+","+appVo_1.getA2()+";";
				}
				return temp.substring(0,temp.length()-1);
			}
		}
		return null;
	}
	
	
	/**优惠券数据迁移**/
	public String updateData(){
		long timeS = new Date().getTime();
		
		Connection conn = MyDataSource.getConnect();
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String str="SELECT count(1) FROM coupon_info";
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Long count = (Long)qr.query(conn,str,new ScalarHandler(1));
			if(count>0){
				conn.setAutoCommit(true);
				conn.close();
				return "-2";
			}
			
			//查询所有老优惠券信息
			List<CouponTemp> couponTempList = qr.query(conn,"SELECT * FROM gf_coupon",new BeanListHandler<CouponTemp>(CouponTemp.class));

		if(null!=couponTempList&&couponTempList.size()>0){
			List<CouponAndGiftTemp> couponAndGiftTempList=new ArrayList<CouponAndGiftTemp>();
			
			CouponAndGiftTemp couponAndGiftTemp=null;
			GiftTemp giftTemp=null;
			List<CouponPassengerTemp> couponPassengerTempList=null;
			int num=0;
			for (CouponTemp couponTemp : couponTempList) {
				num++;
				//根据优惠券查询优惠券礼品关系表
				couponAndGiftTemp=qr.query(conn,"SELECT * FROM gf_coupon_gift WHERE couponId = '"+couponTemp.getCouponId()+"'",new BeanHandler<CouponAndGiftTemp>(CouponAndGiftTemp.class));
				if(null!=couponAndGiftTemp){
					//根据关系表里面的礼品id查询礼品信息
					giftTemp=qr.query(conn,"SELECT * FROM gf_gifts WHERE giftPriId = '"+couponAndGiftTemp.getGiftPriId()+"'",new BeanHandler<GiftTemp>(GiftTemp.class));
					couponAndGiftTemp.setCouponTemp(couponTemp);
					couponAndGiftTemp.setGiftTemp(giftTemp);
					
					//根据关系表id查询用户优惠券信息
					couponPassengerTempList=qr.query(conn,"SELECT * FROM gf_coupon_passenger WHERE couponGiftId = '"+couponAndGiftTemp.getCouponGiftId()+"'",new BeanListHandler<CouponPassengerTemp>(CouponPassengerTemp.class));
					couponAndGiftTemp.setList(couponPassengerTempList);
					
					couponAndGiftTempList.add(couponAndGiftTemp);
					
					System.out.println("num==="+num);
				}
			}
			
			if(null!=couponAndGiftTempList&&couponAndGiftTempList.size()>0){
				for (CouponAndGiftTemp couponAndGift : couponAndGiftTempList) {
					CouponTemp couponTemp1=couponAndGift.getCouponTemp();
					GiftTemp giftTemp1=couponAndGift.getGiftTemp();
					List<CouponPassengerTemp> list1=couponAndGift.getList();
					
					//查询组合券最大批次号
					AppVo_1 vo=qr.query(conn,"SELECT MAX(couponGroupID) AS a1 FROM coupon_group",new BeanHandler<AppVo_1>(AppVo_1.class));
					String theTemp=(null==vo)?null:vo.getA1();
					//生成组合券批次号
					String couponGroupID=getCouponGroupID(theTemp,couponTemp1.getCreateOn());
					
					//添加组合券
					qr.update(conn, "INSERT INTO coupon_group (couponGroupName, couponGroupCount, createBy, createOn,couponGroupID,averageNum) " +
							"VALUES('"+couponGroupID+"组合券',"+couponTemp1.getIssueNum()+",'"+couponTemp1.getCreateBy()+"','"+couponTemp1.getCreateOn()+"',"+couponGroupID+","+couponTemp1.getLimitNum()+")");
					
					//添加优惠券
					qr.update(conn, "INSERT INTO coupon_info " +
							"(couponName, couponValue, couponCon, createBy, createOn,effectiveTime,expirationTime,num,couponCount,couponGroupId,couponGroupName) " +
							"VALUES('"+couponTemp1.getCouponName()+"',"+giftTemp1.getGiftValue()+","+giftTemp1.getGiftCon()+",'"+couponTemp1.getCreateBy()+"'," +
							"'"+couponTemp1.getCreateOn()+"','"+couponTemp1.getEffectiveTime().substring(0,10)+"','"+couponTemp1.getExpirationTime().substring(0,10)+"',1," +
							""+Integer.parseInt(couponTemp1.getLimitNum())*Integer.parseInt(couponTemp1.getIssueNum())+","+couponGroupID+",'"+couponGroupID+"组合券')");
					
					AppVo_1 vo1=qr.query(conn,"SELECT MAX(id) as a1 FROM coupon_info",new BeanHandler<AppVo_1>(AppVo_1.class));
					
					String modeOfExecution=null;
					int flag=MyDate.compare_time_app(couponTemp1.getDownLineTime()+":00",MyDate.getMyDateLong());
					if(flag==-1||"3".equals(couponTemp1.getSelectPass())){//将已过期或发放给自定义用户的优惠券关闭掉
						modeOfExecution="closed";
					}
					else{
						modeOfExecution="send";
					}
					//添加发放
					qr.update(conn, "INSERT INTO coupon_group_grant (taskName,couponGroupId,couponGroupName,sendCouponType,startTime,endTime,createBy,createOn,modeOfExecution) " +
							"VALUES('"+couponTemp1.getCouponName()+"',"+couponGroupID+",'"+couponGroupID+"组合券','"+(couponTemp1.getSendCouponType().equals("1")?"user get":"sys send")+"'," +
							"'"+couponTemp1.getUpLineTime().substring(0,10)+"','"+couponTemp1.getDownLineTime().substring(0,10)+"','"+couponTemp1.getCreateBy()+"','"+couponTemp1.getCreateOn()+"','"+modeOfExecution+"')");
					
					AppVo_1 vo2=qr.query(conn,"SELECT MAX(id) as a1 FROM coupon_group_grant",new BeanHandler<AppVo_1>(AppVo_1.class));
					
					//添加用户优惠券
					if(null!=list1&&list1.size()>0){
						for (CouponPassengerTemp couponPassengerTemp : list1) {
							String useState=null;
//							使用状态 0:未使用 1:已使用 2:已过期
//							使用状态 unused:未使用 used:已使用 expired:已过期
							if(couponPassengerTemp.getUseState().equals("0")){
								useState="unused";
								qr.update(conn, "INSERT INTO coupon_group_passenger_detail " +
										"(passengerId, grantId, couponGroupId, couponId, getTime, effectiveTime, expirationTime,telephone,useState) " +
										"VALUES ('"+couponPassengerTemp.getPassengerId()+"',"+vo2.getA1()+","+couponGroupID+","+vo1.getA1()+"," +
										"'"+couponPassengerTemp.getGetTime()+"','"+couponTemp1.getEffectiveTime().substring(0,10)+"','"+couponTemp1.getExpirationTime().substring(0,10)+"'," +
										"'"+couponPassengerTemp.getTelephone()+"','"+useState+"')");
							}
							else if(couponPassengerTemp.getUseState().equals("1")){
								useState="used";
								qr.update(conn, "INSERT INTO coupon_group_passenger_detail " +
										"(passengerId, grantId, couponGroupId, couponId, getTime, effectiveTime, expirationTime,telephone,useState,orderId,useTime) " +
										"VALUES ('"+couponPassengerTemp.getPassengerId()+"',"+vo2.getA1()+","+couponGroupID+","+vo1.getA1()+"," +
										"'"+couponPassengerTemp.getGetTime()+"','"+couponTemp1.getEffectiveTime().substring(0,10)+"','"+couponTemp1.getExpirationTime().substring(0,10)+"'," +
										"'"+couponPassengerTemp.getTelephone()+"','"+useState+"','"+couponPassengerTemp.getOrderId()+"','"+couponPassengerTemp.getUserTime()+"')");
								
								AppVo_1 vo3=qr.query(conn,"SELECT MAX(id) as a1 FROM coupon_group_passenger_detail",new BeanHandler<AppVo_1>(AppVo_1.class));
								
								//修改订单详情关联的优惠券id
								qr.update(conn, "UPDATE line_business_order SET couponGiftId = '"+vo3.getA1()+"' WHERE leaseOrderId = '"+couponPassengerTemp.getOrderId()+"'");
								
							}
							else{
								useState="expired";
								qr.update(conn, "INSERT INTO coupon_group_passenger_detail " +
										"(passengerId, grantId, couponGroupId, couponId, getTime, effectiveTime, expirationTime,telephone,useState) " +
										"VALUES ('"+couponPassengerTemp.getPassengerId()+"',"+vo2.getA1()+","+couponGroupID+","+vo1.getA1()+"," +
										"'"+couponPassengerTemp.getGetTime()+"','"+couponTemp1.getEffectiveTime().substring(0,10)+"','"+couponTemp1.getExpirationTime().substring(0,10)+"'," +
										"'"+couponPassengerTemp.getTelephone()+"','"+useState+"')");
							}
						}
					}
				}
			}
		}
		conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			if(null!=conn){
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		long timeE = new Date().getTime();
		System.out.println("===========加载【优惠券数据迁移】耗时：" + (timeE - timeS)/1000+"秒===========");
		return "1";
	}
	
	public static void main(String[] args) {
//		System.out.println(MyDate.compare_time_app("2015-07-23 12:20:00",MyDate.getMyDateLong()));
		
//		System.out.println("2015-08-13 12:20:00".substring(0,10));
		
		System.out.println(MyDate.getMyDate());
		
		System.out.println(MyDate.getMyDateLong());
	}
	
	public static String getCouponGroupID(String oldID,String theDate){
		String timeString=null;
		try {
			SimpleDateFormat TimeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=TimeFormat1.parse(theDate);
			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyMMdd");
			timeString = TimeFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//组合券批次号
		String couponGroupId=oldID;
		String currentDate=timeString;//格式150722
		if(null==couponGroupId){
			couponGroupId=currentDate+"0001";
		}
		else{
			String temp=couponGroupId.substring(0,6);
			if(Integer.parseInt(temp)==Integer.parseInt(currentDate)){
				int num=Integer.parseInt(couponGroupId.substring(6,10))+1;
				if(num<10){
					couponGroupId=currentDate+"000"+num;
				}
				else if(num>=10&&num<100){
					couponGroupId=currentDate+"00"+num;
				}
				else if(num>=100&&num<1000){
					couponGroupId=currentDate+"0"+num;
				}
				else if(num>=1000&&num<10000){
					couponGroupId=currentDate+num;
				}
				else{
					couponGroupId="-1";
				}
			}
			else{
				couponGroupId=currentDate+"0001";
			}
		}
		return couponGroupId;
	}
}
