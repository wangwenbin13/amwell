package com.pig84.ab.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ICharteredMyCarDao;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_17;
import com.pig84.ab.vo.bean.AppVo_18_list;
import com.pig84.ab.vo.bean.AppVo_22_list;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_9;

@Repository
public class CharteredMyCarDaoImpl extends BaseDao implements ICharteredMyCarDao{

	/**
	 * 我的包车---已支付列表
	 */
	public List<AppVo_6> getMyCarHasPayList(String userid, String currentPage,
			String pageSize) {
		String table = " bc_order AS bcOrder LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = bcOrder.bc_line_id LEFT JOIN mgr_business AS business ON business.businessId = bcOrder.businessId ";
		String selectFiles = " bcLine.bc_line_id AS a1,bcLine.beginAddress AS a2,bcLine.endAddress AS a3,bcLine.startTime AS a4,business.brefName AS a5,business.contactsPhone AS a6 ";
		String sql = " SELECT "+selectFiles+" FROM "+table+" WHERE 1=1 AND orderStatus !=4 AND orderStatus !=3 AND bcOrder.passengerId = ? ";
		Object[] params = new Object[1];
		params[0] = userid;
		List<AppVo_6> list = queryByPage(AppVo_6.class, sql,Integer.valueOf(currentPage), Integer.valueOf(pageSize),params);
		return list;
	}

	/**
	 * 包车订单详情
	 */
	public AppVo_18_list getMyCarOrderDetail(String bcLineId) {
		
		/**查找包车订单有没有使用优惠卷
		 * 如果使用了优惠卷,订单的金额就是bcOrder.price
		 * 否则,订单的金额就是bcOrder.totalPrice
		 * **/
		String sql = " SELECT couponGiftId AS a1 FROM bc_order WHERE bc_line_id = ? ";
		Object[] params = new Object[1];
		params[0] = bcLineId;
		AppVo_1 vo1 = queryBean(AppVo_1.class, sql,params);
		String priceSelect = null;
		if(null!=vo1){
			priceSelect = " bcOrder.price AS a6 ";
		}else{
			priceSelect = " bcOrder.totalPrice AS a6 ";
		}
		
		String table = " bc_order AS bcOrder LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = bcOrder.bc_line_id LEFT JOIN mgr_business AS business ON bcOrder.businessId = business.businessId LEFT JOIN bc_business_bidding AS busBid ON busBid.bc_line_id = bcOrder.bc_line_id ";
		String selectFileds = " bcLine.beginAddress AS a1,bcLine.endAddress AS a2,bcLine.charteredMode AS a3,bcLine.startTime AS a4,business.brefName AS a5,"+
            priceSelect+" ,busBid.basicCost AS a7,busBid.oilCost AS a8,busBid.parkingFee AS a9,busBid.tolls AS a10,"+
            " busBid.driverMealCost AS a11,busBid.driverHotelCost AS a12,busBid.additionalServices AS a13,busBid.id AS a14,busBid.bc_line_id AS a15,business.contactsPhone AS a16,bcLine.returnTime as a17,bcLine.cityName as a18 ";
		sql = " SELECT "+selectFileds+" FROM "+table+" WHERE 1=1 AND bcOrder.businessId = busBid.businessId AND bcLine.bc_line_id = ? ";
		params = new Object[1];
		params[0] = bcLineId;
		AppVo_18_list vo = null;
		vo = queryBean(AppVo_18_list.class, sql, params);
		if(null!=vo && !StringUtils.isEmpty(vo.getA14())){
			sql = " SELECT carSeats AS a1,carType AS a2,totalCars AS a3,carAge AS a4 FROM bc_business_car WHERE 1=1 AND bidding_id = ? ORDER BY orderNum ";
			params = new Object[1];
			params[0] = vo.getA14();
			List<AppVo_4> list = queryList(AppVo_4.class, sql, params);
			vo.setList(list);
		}else{
			vo = new AppVo_18_list();
		}
		return vo;
	}

	/**
	 * 我的包车---包车订单详情---需求详情
	 */
	public AppVo_17 getMyCarOrderBcLineDetail(String bcLineId) {
		String table = "bc_line";
		String selectFileds = " bc_line_id AS a1,beginAddress AS a2,endAddress AS a3,journeyRemark AS a4,charteredMode AS a5,startTime AS a6,returnTime AS a7,"+
                " carAge AS a8,totalPassengers AS a9,totalCars AS a10,needInvoice AS a11,contacts AS a12,contactPhone AS a13,cityName as a14,provinceCode as a15,cityCode as a16,invoiceHeader as a17 ";
		String sql = " SELECT "+selectFileds+" FROM "+table+" WHERE 1=1 AND bc_line_id = ? ";
		Object[] params = new Object[1];
		params[0] = bcLineId;
		AppVo_17 vo = new AppVo_17();
		vo = queryBean(AppVo_17.class, sql, params);
		return vo;
	}

	/**
	 * 我的包车---包车详情---订单(报价)详情
	 */
	public AppVo_22_list getMyCarOrderBusinessBidDetail(String businessBid) {
		String table = " bc_business_bidding AS busBid LEFT JOIN mgr_business AS business ON business.businessId = busBid.businessId LEFT JOIN bc_return_rule AS rule ON rule.ruleId = busBid.returnRuleId ";
		String selectFileds = " business.brefName AS a1,business.contactsPhone AS a2,busBid.basicCost AS a3,busBid.oilCost AS a4,busBid.parkingFee AS a5," +
				" busBid.tolls AS a6,busBid.driverMealCost AS a7,busBid.driverHotelCost AS a8,busBid.driverWorkHour AS a9,"+
                " busBid.overtimeCost AS a10,busBid.limitMileage AS a11,busBid.overmileageCost AS a12,rule.noReturn AS a13,rule.returnOneTime AS a14," +
                " rule.returnOnePer AS a15,rule.returnTwoTime AS a16,rule.returnTwoPer AS a17,busBid.additionalServices AS a18,busBid.remark AS a19 ";
		String sql = " SELECT "+selectFileds+" FROM "+table+" WHERE 1=1 AND busBid.id= ? ";
		Object[] params = new Object[1];
		params[0] = businessBid;
		AppVo_22_list vo = new AppVo_22_list();
		vo = queryBean(AppVo_22_list.class, sql, params);
		if(null!=vo && !StringUtils.isEmpty(businessBid)){
			sql = " SELECT carSeats AS a1,carType AS a2,totalCars AS a3,carAge AS a4 FROM bc_business_car WHERE 1=1 AND bidding_id = ? ORDER BY orderNum ";
			params = new Object[1];
			params[0] = businessBid;
			List<AppVo_4> list = queryList(AppVo_4.class, sql, params);
			vo.setList(list);
		}
		return vo;
	}

	/**
	 * 我的包车---历史记录
	 */
	public List<AppVo_9> getMyCarOrderHistoryList(String userid,
			String currentPage, String pageSize) {
		String table = " bc_line AS bcLine LEFT JOIN bc_business_bidding AS busBid ON busBid.bc_line_id = bcLine.bc_line_id LEFT JOIN bc_order AS bcOrder ON bcOrder.bc_line_id = bcLine.bc_line_id LEFT JOIN ( SELECT bc_line_id,count(*) as num FROM bc_business_bidding WHERE offerStatus = '2' AND totalCost IS NOT NULL GROUP BY bc_line_id ) AS bcCount ON bcCount.bc_line_id = bcLine.bc_line_id ";
		String selectFiles = " bcLine.bc_line_id AS a1,beginAddress AS a2,endAddress AS a3,startTime AS a4,lineStatus AS a5,bcCount.num AS a6,bcLine.rejectContent AS a7,bcOrder.orderStatus AS a8 ";
		String sql = " SELECT "+selectFiles+" FROM "+table+" WHERE 1=1 AND (bcLine.lineStatus =4 or bcOrder.orderStatus = 3) AND bcLine.lineStatus = 4 AND bcLine.passengerId = ? GROUP BY bcLine.bc_line_id ORDER BY a6 DESC,a4 DESC ";
		Object[] params = new Object[1];
		params[0] = userid;
		List<AppVo_9> list = queryByPage(AppVo_9.class, sql,Integer.valueOf(currentPage), Integer.valueOf(pageSize),params);
		if(null!=list && list.size()>0){
			for(AppVo_9 vo : list){
				if(StringUtils.isEmpty(vo.getA7())){
					vo.setA7("");
				}
			}
		}
		return list;
	}

	/**
	 * 我的包车---历史记录---删除历史记录
	 */
	public String delMyCarOrderHistoryList(String ids) {
		if(ids!=null && !"".equals(ids)){
			List<String> condition=new ArrayList<String>();
			String[] strs=ids.split(",");
			StringBuffer sb=new StringBuffer();
			for (String s : strs) {
				condition.add(s);
				sb.append("?,");
			}
			String sql = "UPDATE bc_line SET lineStatus = '5' where bc_line_id in ("+sb.substring(0,sb.length()-1)+")";
			int flag = executeSQL(sql,condition.toArray());
			if(flag!=-1){
				return "1";
			}else{
				return "2";
			}
		}else{
			return null;
		}
	}
	
	/**查询已有报价包车的商家报价列表**/
	public List<AppVo_3> getBusinessBiddingList(String charteredLineId){
		String sql = "SELECT id AS a1,businessName AS a2,totalCost AS a3 FROM bc_business_bidding WHERE bc_line_id = ? and offerStatus = '2' and totalCost is not null";
		Object[] params = new Object[1];
		params[0]=charteredLineId;
		List<AppVo_3> list=queryList(AppVo_3.class,sql,params);
		if(null==list||list.size()==0){
			list=new ArrayList<AppVo_3>();
		}
		return list;
	}
	
	/**查询包车需求详情**/
	public AppVo_15 getCharteredLineDetail(String charteredLineId){
		String sql = "SELECT beginAddress AS a1,endAddress AS a2,journeyRemark AS a3,charteredMode AS a4,startTime AS a5,returnTime AS a6,carAge AS a7," +
				"totalPassengers AS a8,totalCars AS a9,needInvoice AS a10,contacts AS a11,contactPhone AS a12,bc_line_id as a13,cityName as a14," +
				"rejectContent as a15 FROM bc_line where bc_line_id = ?";
		Object[] params = new Object[1];
		params[0]=charteredLineId;
		return queryBean(AppVo_15.class, sql,params);
	}

}
