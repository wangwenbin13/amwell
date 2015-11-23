package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;
import com.amwell.dao.ICharteredLineDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.bc.BcBiddingCarsLineVo;
import com.amwell.vo.bc.BcbusinessBiddingVo;
import com.amwell.vo.bc.CharteredLineVo;
import com.amwell.vo.bc.LineStatusEnum;

@Repository("charteredLineDao")
public class CharteredLineDaoImpl extends DaoSupport implements ICharteredLineDao{

	/* 
	 *  获取包车线路列表
	 */
	public Map<String, Object> getCharteredLineList(Search search,int currentPageIndex, int pageSize) {
		super.finit("bc_line,passenger_info");
		Map<String,Object> map=new HashMap<String, Object>();
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT bl.bc_line_id,bl.createOn,pg.nickName,pg.telephone,bl.businessType,bl.charteredMode, ");
		sql.append("bl.startTime,bl.returnTime,bl.contacts,bl.contactPhone,bl.lineStatus,ar.areaName,bl.cityName  ");
		sql.append(" FROM bc_line AS bl  LEFT JOIN passenger_info pg ON bl.`passengerId`=pg.`passengerId` ");
		sql.append("  LEFT JOIN `sys_area` AS ar ON bl.provinceCode=ar.arearCode  ");
		sql.append("  WHERE 1=1 AND  bl.lineStatus !=5  AND  bl.lineStatus !=6 ");
		List<Object> paramlist=new ArrayList<Object>();
		if(search!=null){
			if(search.getField01()!=null && !"".equals(search.getField01())){
				sql.append(" and bl.createOn >= ? ");
				paramlist.add(search.getField01().trim()+" 00:00:00");
			}
			if(search.getField02()!=null && !"".equals(search.getField02())){
				sql.append(" and bl.createOn <= ?  ");
				paramlist.add(search.getField02().trim()+" 23:59:59");
			}
			if(search.getField03()!=null && !"".equals(search.getField03())){
				sql.append(" and pg.telephone like ? ");
				paramlist.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!=null && !"".equals(search.getField04())){
				sql.append(" and bl.businessType = ? ");
				paramlist.add(search.getField04());
			}
			if(search.getField05()!=null && !"".equals(search.getField05())){
				sql.append(" and bl.lineStatus = ? ");
				paramlist.add(search.getField05());
			}
		}
		
		sql.append(" order by bl.createOn desc");
		Object[] params = paramlist.toArray(new Object[]{});
		List<CharteredLineVo> list=super.tableDao.queryByPage(CharteredLineVo.class, sql.toString(),currentPageIndex,pageSize, params);
		Page page =new Page(list, sql.toString(), currentPageIndex, pageSize, params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 查询包车信息详情
	 * @param bcLineId
	 * @return
	 */
	public CharteredLineVo getCharteredLineDetail (String bcLineId){
		super.finit("bc_line,passenger_info");
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT bl.*,pg.nickName,pg.displayId,pg.telephone,pg.passengerId, ar.`areaName`,NOW() AS nowtime FROM bc_line AS bl  LEFT JOIN passenger_info pg ON  ");
		sql.append(" bl.`passengerId`=pg.`passengerId`  LEFT JOIN `sys_area` AS ar ON bl.provinceCode=ar.arearCode ");
		sql.append(" WHERE 1=1 AND bl.bc_line_id= ?");
		return super.tableDao.queryBean(CharteredLineVo.class, sql.toString(),new Object[]{bcLineId});
	}
	
	
	/**
	 * 改变包车线路状态
	 * @param model
	 * @return
	 */
	public Integer setOutAudit(CharteredLineVo model){
		int flag=-1;
		Connection conn = MyDataSource.getConnect();
		if(conn == null){
			throw new IllegalStateException("connnection is null");
		}
		try{
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();

			String sql="update  bc_line set lineStatus=?,rejectContent=? where bc_line_id = ?";
			flag = qr.update(conn, sql, new Object[]{model.getLineStatus(),model.getRejectContent(),model.getBc_line_id()});

			conn.commit();
		}catch (Exception e) {
			flag=-999;
			e.printStackTrace();
			if(conn != null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			}
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return flag;
	}
	
	/**
	 * 选择商家弹窗
	 * @return
	 */
	public Map<String, Object> getSelectMerchant (MgrBusinessEntity model){
		super.finit("mgr_business");
		Map<String, Object> map=new HashMap<String, Object>();
		List<Object> parameters=new ArrayList<Object>();
		String sql="select businessId,name,contacts,contactsPhone,cityName from mgr_business where 1=1 and businessType in(2,3) and accountStatus=1 ";// where certifyStatus=1 and accountStatus=0";
		if(model!=null){
			if(model.getName()!=null && !"".equals(model.getName())){
				sql+=" and name like ?";
				parameters.add(SqlBuilder.getSqlLikeValue(model.getName().trim()));
			}
			if(model.getProvinceCode()!=null && !"".equals(model.getProvinceCode())){
				sql+=" and provinceCode like ?";
				parameters.add(model.getProvinceCode());
			}
			if(model.getAreaCode()!=null && !"".equals(model.getAreaCode())){
				sql+=" and areaCode like ? ";
				parameters.add(model.getAreaCode());
			}
		}
		Object[] parames=parameters.toArray(new Object[]{});
		List<MgrBusinessEntity> list= super.tableDao.queryList(MgrBusinessEntity.class, sql, parames);
		
		map.put("list", list);
		return map;
	}
	
	/**
	 * 发送包车信息给商家
	 * @param model
	 * @return
	 */
	public int sendBcToBusiness(BcbusinessBiddingVo model){
		int flag = -1;
		Connection conn = MyDataSource.getConnect();
		if(conn == null){
			throw new IllegalStateException("connnection is null");
		}
		
		if(null == model.getBusinessIds() || model.getBusinessIds().length == 0){
			throw new IllegalStateException("businessIds is null");
		}
		try{
			conn.setAutoCommit(false);
			
			QueryRunner qr = new QueryRunner();
			
			String insertSql = "insert into bc_business_bidding (id,bc_line_id,businessId,createOn,offerEndTime,businessName) values(?,?,?,?,?,?)";
			
			for(int i=0;i<model.getBusinessIds().length;i++){
				String busId=model.getBusinessIds()[i];
				String busNameSql="SELECT brefName FROM mgr_business WHERE 1=1 AND businessId= ? ";
				MgrBusinessEntity busModel=qr.query(conn, busNameSql, new BeanHandler<MgrBusinessEntity>(MgrBusinessEntity.class), new Object[]{busId});
				flag = qr.update(conn, insertSql, new Object[]{StringUtil.generateSequenceNo(),model.getBc_line_id(),busId,MyDate.getMyDateLong(),model.getOfferEndTime(),busModel.getBrefName()});
			}
			
			String sql="update  bc_line set lineStatus=?,publishTime=NOW() where bc_line_id = ?";
			flag = qr.update(conn, sql, new Object[]{LineStatusEnum.WAIT_OFFER.getValue(),model.getBc_line_id()});
			
			conn.commit();
		}catch (Exception e) {
			flag=-9999;
			e.printStackTrace();
			
			if(conn != null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					
				}
			}
		}finally{
			
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return flag;
	}
	
	/**
	 * 查询商家报价列表
	 * @param bcLineId
	 * @return
	 */
	public Map<String, Object> getBusinessOfferList(String bcLineId){
		super.finit("bc_business_bidding");
		Map<String, Object> map=new HashMap<String, Object>();
		String sql="select bc.*,mgr.name from bc_business_bidding as bc left join mgr_business as mgr on bc.businessId=mgr.businessId  where 1=1 and bc_line_id = ? ";
		List<BcbusinessBiddingVo> list=super.tableDao.queryList(BcbusinessBiddingVo.class, sql,new Object[]{bcLineId});
		map.put("list", list);
		return map;
	}
	
	/**
	 * 查询商家报价详情
	 * @param offerId
	 * @return
	 */
	public BcbusinessBiddingVo getBusinessOfferDetail(String offerId){
		super.finit("bc_business_bidding");
		String sql="select * from bc_business_bidding as bid left join bc_return_rule as rul on bid.returnRuleId=rul.ruleId where id = ?";
		
		return super.tableDao.queryBean(BcbusinessBiddingVo.class, sql, new Object[]{offerId});
	}
	
	/**
	 * 商家报价表里的车辆信息
	 * @param biddingId
	 * @return
	 */
	public Map<String, Object> getBiddingCars (String biddingId){
		super.finit("bc_business_car");
		Map< String, Object> map=new HashMap<String, Object>();
		String sql="select * from bc_business_car where bidding_id = ?  ";
		List<BcBiddingCarsLineVo> list = super.tableDao.queryList(BcBiddingCarsLineVo.class, sql, new Object[]{biddingId});
		map.put("list", list);
		return map;
	} 
	
	/**
	 * 报价过期后改变报价状态
	 * @return
	 */
	public int  judgeOfferTime(){
		Connection conn=MyDataSource.getConnect();

		if(conn==null){
			throw new IllegalStateException("connection is null");
		}
		try {
			conn.setAutoCommit(false);
			QueryRunner qr=new QueryRunner(); 
			String sql="UPDATE bc_line SET lineStatus=? WHERE  TIMESTAMPDIFF(MINUTE,NOW(),expireTime)<0 and ( lineStatus=2 or lineStatus=3 or lineStatus=0 )  ";
			qr.update(conn, sql, LineStatusEnum.EXPIRED.getValue());
			String sql2="UPDATE  bc_business_bidding SET offerStatus=2 WHERE  TIMESTAMPDIFF (MINUTE,NOW(), offerEndTime)<0";
			qr.update(conn, sql2);
			conn.commit();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			if(conn!=null){
				try {
					conn.rollback();
					
				} catch (Exception e2) {
				}
			}
		}finally{
			
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
		return 1;
	}
	
	
	/*
	 * 查询商家电话
	 */
	public String getBusinessPhone(String businessId){
		super.finit("mgr_business");
		Connection conn=MyDataSource.getConnect();
		String phone="";
		if(conn==null){
			throw new IllegalStateException("connection is null");
		}
		if(businessId==null){
			throw new IllegalAccessError("businessId is null");
		}
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String sql="select contactsPhone from mgr_business where businessId like ? ";
			MgrBusinessEntity model=qr.query(conn, sql,new BeanHandler<MgrBusinessEntity>(MgrBusinessEntity.class), new Object[]{businessId});
			phone=model.getContactsPhone();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		}finally{
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
		return phone;
	}
	


}
