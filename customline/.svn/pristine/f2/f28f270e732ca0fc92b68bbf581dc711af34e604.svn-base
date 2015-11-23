package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.HttpRequestUtils;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.PropertyManage;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;
import com.amwell.commons.SystemConstants;
import com.amwell.dao.ICharteredOrderDao;
import com.amwell.entity.BcOrderVo;
import com.amwell.entity.BcOutVo;
import com.amwell.entity.BcReturnHistoryVo;
import com.amwell.entity.BcReturnVo;
import com.amwell.entity.LeaseOrderModelVo;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.BusCouponStatVo;
import com.amwell.vo.bc.BcCouponStatVo;
import com.amwell.vo.bc.BcLineVo;
import com.amwell.vo.bc.BcReturnHistoryEntity;

@Repository("charteredOrderDao")
public class CharteredOrderDaoImpl extends DaoSupport implements ICharteredOrderDao{

	private static final Logger log = Logger.getLogger(CharteredOrderDaoImpl.class);
	/**获取包车订单列表**/
	public Map<String, Object> getBcOrderList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		String table = " bc_order as bcOrder LEFT JOIN bc_line AS bcLine ON bcOrder.bc_line_id = bcLine.bc_line_id LEFT JOIN passenger_info AS pass ON bcOrder.passengerId = pass.passengerId LEFT JOIN mgr_business AS business ON bcOrder.businessId = business.businessId ";
		String selectFields = " bcOrder.orderId,bcOrder.orderNo,pass.nickName,pass.displayId,pass.telephone,bcLine.businessType,bcLine.charteredMode,bcLine.startTime,bcLine.returnTime,business.brefName,bcOrder.totalPrice,bcOrder.orderStatus,bcOrder.payModel,bcOrder.payTime ";
		super.finit(table);
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(bcOrder.payTime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(bcOrder.payTime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and bcOrder.orderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and pass.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and business.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("") && !"0".equals(search.getField06())){
				cond+= " and bcLine.businessType = ? ";
				paramList.add(search.getField06().trim());
			}
			if(search.getField07()!= null && !search.getField07().equals("") && !"0".equals(search.getField07())){
				cond+= " and bcOrder.orderStatus = ?";
				paramList.add(search.getField07().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT "+selectFields;
		sql +=" from "+table;
		String countSql = " select sum(bcOrder.totalPrice) from "+table;
		sql += cond;
		countSql +=cond;
		sql += " order by bcOrder.payTime desc ";
		List<BcOrderVo> bcOrderVos = tableDao.queryByPage(BcOrderVo.class, sql,currentPageIndex,params);
		page = new Page(bcOrderVos,sql,currentPageIndex,pageSize,params);
		map.put("list", bcOrderVos);
		map.put("page", page);
		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**包车订单详情**/
	public BcOrderVo getBcOrderDetail(BcOrderVo bcOrderVo) {
		String table = " bc_order as bcOrder LEFT JOIN bc_line AS bcLine ON bcOrder.bc_line_id = bcLine.bc_line_id LEFT JOIN passenger_info AS pass ON bcOrder.passengerId = pass.passengerId LEFT JOIN mgr_business AS business ON bcOrder.businessId = business.businessId ";
		String selectFields = " bcOrder.orderId,bcOrder.orderNo,pass.nickName,pass.displayId,pass.telephone,bcLine.businessType,bcLine.charteredMode,bcLine.startTime,bcLine.returnTime,business.brefName,bcOrder.totalPrice,bcOrder.orderStatus,bcOrder.payModel,bcOrder.payTime," +
				"bcLine.contacts,bcLine.contactPhone,bcLine.beginAddress,bcLine.endAddress,bcLine.totalPassengers,bcLine.totalCars,bcLine.carAge,bcLine.needInvoice,bcLine.invoiceHeader,bcLine.journeyRemark,bcLine.remark ";
		super.finit(table);
		sql = " select "+selectFields +" from "+table +" where bcOrder.orderId = ? ";
		args = new Object[1];
		args[0] = bcOrderVo.getOrderId();
		bcOrderVo = tableDao.queryBean(BcOrderVo.class, sql, args);
		return bcOrderVo;
	}

	/**
	 * 日收入统计列表
	 */
	public Map<String, Object> getBcOrderDayInComeList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		String table = " bc_order as bcOrder LEFT JOIN bc_line AS bcLine ON bcOrder.bc_line_id = bcLine.bc_line_id LEFT JOIN passenger_info AS pass ON bcOrder.passengerId = pass.passengerId LEFT JOIN mgr_business AS business ON bcOrder.businessId = business.businessId ";
		String selectFields = " bcOrder.orderId,substring(bcOrder.payTime,1,11) AS payTime,bcOrder.orderNo,pass.nickName,pass.telephone,bcLine.businessType,bcLine.beginAddress,bcLine.endAddress,bcLine.totalPassengers,bcLine.totalCars,bcLine.startTime,bcLine.returnTime,business.brefName,bcOrder.totalPrice ";
		super.finit(table);
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(bcOrder.payTime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(bcOrder.payTime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and bcOrder.orderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and pass.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and business.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("") && !"0".equals(search.getField06())){
				cond+= " and bcLine.businessType = ? ";
				paramList.add(search.getField06().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT "+selectFields;
		sql +=" from "+table;
		String countSql = " select sum(bcOrder.totalPrice) from "+table;
		sql += cond;
		countSql +=cond;
		sql += " order by bcLine.startTime desc ";
		List<BcOrderVo> bcOrderVos = tableDao.queryByPage(BcOrderVo.class, sql,currentPageIndex,params);
		page = new Page(bcOrderVos,sql,currentPageIndex,pageSize,params);
		map.put("list", bcOrderVos);
		map.put("page", page);
		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 财务统计-日支出统计列表
	 */
	public Map<String, Object> getBcOrderDayOutComeList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		String table = " bc_stat_out AS bcOut LEFT JOIN bc_order AS bcOrder ON bcOrder.orderNo = bcOut.bc_orderNo LEFT JOIN passenger_info AS pass ON pass.passengerId = bcOut.passengerId LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = bcOut.bc_line_id LEFT JOIN mgr_business AS business ON business.businessId = bcOut.businessId ";
		String selectFields = " substring(bcOut.operateTime,1,11) AS operateTime,bcOut.bc_orderNo AS orderNo,pass.nickName,pass.telephone,bcLine.businessType,bcLine.beginAddress,bcLine.endAddress,bcLine.totalPassengers,bcLine.totalCars,bcLine.charteredMode,bcLine.startTime,bcLine.returnTime,business.brefName,bcOut.bc_out_money,bcOut.bc_out_type ";
		super.finit(table);
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(bcOut.operateTime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(bcOut.operateTime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and bcOrder.orderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and pass.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and business.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("") && !"0".equals(search.getField06())){
				cond+= " and bcLine.businessType = ? ";
				paramList.add(search.getField06().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT "+selectFields;
		sql +=" from "+table;
		String countSql = " select sum(bcOut.bc_out_money) from "+table;
		sql += cond;
		countSql +=cond;
		sql += " order by bcOut.operateTime desc ";
		List<BcOutVo> bcOutVos = tableDao.queryByPage(BcOutVo.class, sql,currentPageIndex,params);
		page = new Page(bcOutVos,sql,currentPageIndex,pageSize,params);
		map.put("list", bcOutVos);
		map.put("page", page);
		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 按日期月度账目统计报表
	 */
	public Map<String, Object> queryDateBcOrderList(Search search,
			Integer dateCurrentPageIndex, Integer pageSize) {
		String table = " bc_order AS bcOrder LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = bcOrder.bc_line_id ";
		super.finit(table);
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(bcOrder.payTime,7) =? ";
				paramList.add(search.getField01());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT substring(bcOrder.payTime,1,11) as payTime,SUM(totalPrice) AS totalPrice from  "+table;;
		sql += cond;
		sql += "  GROUP  BY substring(bcOrder.payTime,1,11) order by payTime desc ";
		String countSql = " select sum(totalPrice) from "+table;
		countSql +=cond;
		List<BcOrderVo> bcOrderVos = tableDao.queryByPage(BcOrderVo.class, sql,dateCurrentPageIndex,params);
		Page page = new Page(bcOrderVos,sql,dateCurrentPageIndex,pageSize,params);
		map.put("dataList", bcOrderVos);
		map.put("datePage", page);
		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
		map.put("dateTotalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	
	/**
	 * 按商家月度账目统计报表
	 */
	public Map<String, Object> queryBusinessBcOrderList(Search search,
			Integer businessCurrentPageIndex, Integer pageSize) {
		String table = " bc_order AS bcOrder LEFT JOIN mgr_business AS business ON bcOrder.businessId = business.businessId LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = bcOrder.bc_line_id ";
		super.finit(table);
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(bcOrder.payTime,7) =? ";
				paramList.add(search.getField01());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT business.brefName,SUM(bcOrder.totalPrice) AS totalPrice from "+table;
		sql += cond;
		sql += "  GROUP  BY bcOrder.businessId order by bcOrder.payTime desc ";
		String countSql = " select sum(bcOrder.totalPrice) from  "+table;
		countSql +=cond;
		List<BcOrderVo> bcOrderVos = tableDao.queryByPage(BcOrderVo.class, sql,businessCurrentPageIndex,params);
		Page page = new Page(bcOrderVos,sql,businessCurrentPageIndex,pageSize,params);
		map.put("businessList", bcOrderVos);
		map.put("businessPage", page);
		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
		map.put("businessTotalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 退票订单列表
	 */
	public Map<String, Object> getBcReturnOrderList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		String table = " bc_order AS bcOrder LEFT JOIN bc_business_bidding AS bcBid ON bcOrder.bc_line_id = bcBid.bc_line_id LEFT JOIN bc_return_rule AS bcReturn ON bcReturn.ruleId = bcBid.returnRuleId LEFT JOIN passenger_info AS pass ON pass.passengerId = bcOrder.passengerId LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = bcOrder.bc_line_id LEFT JOIN mgr_business AS business ON business.businessId = bcOrder.businessId ";
		String selectFields = " bcOrder.orderId,bcOrder.payTime,bcOrder.orderNo,pass.nickName,pass.telephone,bcLine.businessType,bcLine.charteredMode,bcLine.startTime,bcLine.returnTime,business.brefName,bcOrder.totalPrice,bcOrder.orderStatus,bcReturn.* ";
		super.finit(table);
		String cond = " where 1 = 1 AND bcOrder.businessId = bcBid.businessId AND bcOrder.orderStatus !=4 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(bcOrder.payTime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(bcOrder.payTime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and bcOrder.orderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and pass.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and business.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("") && !"0".equals(search.getField06())){
				cond+= " and bcLine.businessType = ? ";
				paramList.add(search.getField06().trim());
			}
			if(search.getField07()!= null && !search.getField07().equals("") && !"0".equals(search.getField07())){
				cond+= " and bcOrder.orderStatus = ?";
				paramList.add(search.getField07().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT "+selectFields;
		sql +=" from "+table;
//		String countSql = " select sum(bcOrder.totalPrice) from "+table;
		sql += cond;
//		countSql +=cond;
		sql += " order by bcOrder.payTime desc ";
		List<BcReturnVo> bcReturnVos = tableDao.queryByPage(BcReturnVo.class, sql,currentPageIndex,params);
		page = new Page(bcReturnVos,sql,currentPageIndex,pageSize,params);
		map.put("list", bcReturnVos);
		map.put("page", page);
//		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
//		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 操作退票(详情)
	 */
	public BcReturnVo getBcReturnDetail(BcReturnVo bcReturnVo) {
		String table = " bc_order AS bcOrder LEFT JOIN bc_business_bidding AS bcBid ON bcOrder.bc_line_id = bcBid.bc_line_id LEFT JOIN bc_return_rule AS bcReturn ON bcReturn.ruleId = bcBid.returnRuleId LEFT JOIN passenger_info AS pass ON pass.passengerId = bcOrder.passengerId LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = bcOrder.bc_line_id LEFT JOIN mgr_business AS business ON business.businessId = bcOrder.businessId ";
		String selectFields = " bcOrder.orderId,bcOrder.orderNo,bcOrder.payTime,bcOrder.orderNo,pass.nickName,pass.displayId, business.contactsPhone AS telephone,bcLine.businessType,bcLine.charteredMode,bcLine.startTime,bcLine.returnTime,bcLine.contacts,bcLine.contactPhone,business.brefName,bcOrder.totalPrice,bcOrder.orderStatus,bcLine.beginAddress,bcLine.endAddress,bcLine.totalPassengers,bcLine.totalCars,bcLine.carAge,bcLine.needInvoice,bcLine.invoiceHeader,bcReturn.*,bcLine.bc_line_id,pass.passengerId,business.businessId ";
		super.finit(table);
		String cond = " where 1 = 1 AND bcOrder.businessId = bcBid.businessId AND bcOrder.orderStatus !=4 ";
		sql = " SELECT "+selectFields +" FROM "+table + cond + " AND bcOrder.orderId = ? ";
		args = new Object[1];
		args[0] = bcReturnVo.getOrderId();
		bcReturnVo = tableDao.queryBean(BcReturnVo.class, sql, args);
		return bcReturnVo;
	}

	/**
	 * 执行包车退票操作
	 */
	@SuppressWarnings("deprecation")
	public int doBcReturn(BcReturnHistoryEntity bcReturnHistoryEntity) {
		Connection conn=null;
		QueryRunner qr = new QueryRunner();
		int statu = -1;
		try {
			conn =MyDataSource.getConnect();
			if(conn==null){
				return -1;
			}
			conn.setAutoCommit(false);
			/**
			 * 修改包车订单状态
			 * orderStatus
			 * 订单状态,1:待调度 2：已调度(同时输入司机、车辆) 3：已完成 4:已退票
			 * 改为4
			 */
			sql = " UPDATE bc_order SET orderStatus = 4 WHERE orderId = ? ";
			statu = qr.update(conn,sql, new Object[]{bcReturnHistoryEntity.getOrderId()});
			
			if(statu<=0){
				throw new RuntimeException("更改订单状态失败");
			}
			/**
			 * 添加退票记录
			 */
			sql ="INSERT INTO bc_return_history(bcReturnId,orderId,passengerId,businessId,bc_line_id,oldMoney,bcReturnMoney,realPer,operatorId,operateTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statu = qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),bcReturnHistoryEntity.getOrderId(),bcReturnHistoryEntity.getPassengerId(),bcReturnHistoryEntity.getBusinessId(),
					bcReturnHistoryEntity.getBc_line_id(),bcReturnHistoryEntity.getOldMoney(),bcReturnHistoryEntity.getBcReturnMoney(),bcReturnHistoryEntity.getRealPer(),bcReturnHistoryEntity.getOperatorId(),bcReturnHistoryEntity.getOperateTime()});
			if(statu<=0){
				throw new RuntimeException("添加退票记录失败");
			}
			
			/**
			 * 添加包车支出记录
			 */
			sql = " INSERT INTO bc_stat_out(bc_outId,bc_line_id,businessId,passengerId,bc_out_money,bc_orderNo,operatorId,operateTime,bc_out_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);  ";
			statu = qr.update(conn,sql,new Object[]{StringUtil.generateSequenceNo(),bcReturnHistoryEntity.getBc_line_id(),bcReturnHistoryEntity.getBusinessId(),bcReturnHistoryEntity.getPassengerId(),
					bcReturnHistoryEntity.getBcReturnMoney(),bcReturnHistoryEntity.getOrderNo(),bcReturnHistoryEntity.getOperatorId(),bcReturnHistoryEntity.getOperateTime(),1});
			if(statu<=0){
				throw new RuntimeException("添加包车支出记录失败");
			}else{
				sql = " INSERT INTO sys_log (logId,userId,userIp,action,operateTime,operateResult,sysType)  VALUES (?,?,?,?,?,?,?) ";
				statu = qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),bcReturnHistoryEntity.getOperatorId(),getIpAddr(),"包车管理",MyDate.getMyDateLong(),"退票成功","0"});
			}
			
			//发送站内信
            //查询线路站点名称,发布时间
            sql="select a.beginAddress,a.endAddress,a.startTime,a.createOn,a.passengerId,b.telephone,a.sourceFrom from bc_line a,passenger_info b  where a.passengerId=b.passengerId and a.bc_line_id=?";
            BcLineVo bcLineVo =  qr.query(conn,sql, new Object[]{bcReturnHistoryEntity.getBc_line_id()}, new ResultSetHandler<BcLineVo>() {
				public BcLineVo handle(ResultSet rs) throws SQLException {
					if(rs.next()){
						BcLineVo tempBcLineVo = new BcLineVo();
						tempBcLineVo.setBeginAddress(rs.getString(1));
						tempBcLineVo.setEndAddress(rs.getString(2));
						tempBcLineVo.setStartTime(rs.getString(3));
						tempBcLineVo.setCreateOn(rs.getString(4));
						tempBcLineVo.setPassengerId(rs.getString(5));
						tempBcLineVo.setTelephone(rs.getString(6));
						tempBcLineVo.setSourceFrom(rs.getInt(7));
						return tempBcLineVo;
					}
					return null;
				}
			});
            if(bcLineVo!=null){
            	if(0==bcLineVo.getSourceFrom().intValue()){
               	 if(bcLineVo!=null&&StringUtils.isNotEmpty(bcLineVo.getBeginAddress())&&StringUtils.isNotBlank(bcLineVo.getEndAddress())&&StringUtils.isNotEmpty(bcLineVo.getCreateOn())&&StringUtils.isNotEmpty(bcLineVo.getStartTime())){
                    	String msgId = StringUtil.generateSequenceNo();
                    	String beginAddress = bcLineVo.getBeginAddress().length()>5?bcLineVo.getBeginAddress().substring(0,5):bcLineVo.getBeginAddress();
                    	String endAddress = bcLineVo.getEndAddress().length()>5?bcLineVo.getEndAddress().substring(0,5):bcLineVo.getEndAddress();
            			sql ="insert into sys_msg_info(msgId,msgType,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,issend) values(?,?,?,?,?,?,?,?,?)";
            			String msgTitle ="包车("+beginAddress+"-"+endAddress+"线路 已退票)";
            			String msgContext="尊敬的用户您好，您于"+bcLineVo.getCreateOn()+"所发布的发车时间为："+bcLineVo.getStartTime()+"的包车信息已退票。"+SystemConstants.COMPANY_INFO;
            			qr.update(conn, sql, new Object[]{msgId,1,0,msgTitle,msgContext,0,"SYSTEM",MyDate.getMyDateLong(),"0"});
            			sql="insert into sys_msg_user(localId,userId,msgId,readFlag,sendPhoneNo,sendTime) values(?,?,?,?,?,?)";
            			qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),bcLineVo.getPassengerId(),msgId,0,bcLineVo.getTelephone(),MyDate.getMyDateLong()});	
                    }
               }else if(1==bcLineVo.getSourceFrom().intValue()){
               	String requestUrl = PropertyManage.getInfoProperty("wechat_back_root")+"sendPushMessageAction/sendBcLineMessage";
   				if(StringUtils.isNotBlank(requestUrl)){
   					requestUrl+="?passengerId="+bcLineVo.getPassengerId()+"&bcLineId="+bcReturnHistoryEntity.getBc_line_id()+"&msgType=6";
   					HttpRequestUtils.sendGetRequest(requestUrl);
   				}else{
   					log.error("property weixin.push.message.url can't set value.");
   				}
               }
            }
            //给商家发送短信
            if(conn!=null){
            	conn.commit();
            }
		} catch (Exception e) {
			statu = 0;
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
						sql = " INSERT INTO sys_log (logId,userId,userIp,action,operateTime,operateResult,sysType)  VALUES (?,?,?,?,?,?,?) ";
						qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),bcReturnHistoryEntity.getOperatorId(),getIpAddr(),"包车管理",MyDate.getMyDateLong(),"退票失败","0"});
						conn.commit();
					} catch (SQLException e1) {
						statu = -1;
						e1.printStackTrace();
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				if(null!=conn && conn.isClosed()==false){
					conn.setAutoCommit(true);
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(statu<=0){
//				AppendLog(bcReturnHistoryEntity.getOperatorId(), "包车管理", "退票失败");
			}else{
//				AppendLog(bcReturnHistoryEntity.getOperatorId(), "包车管理", "退票成功");
			}
		}
		
		return statu;
	}

	
	/**
	 * 包车退票记录列表
	 */
	public Map<String, Object> getBcReturnHistoryList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		String table = " bc_return_history as history LEFT JOIN bc_order AS bcOrder ON bcOrder.orderId = history.orderId LEFT JOIN passenger_info AS pass ON pass.passengerId = history.passengerId LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = history.bc_line_id LEFT JOIN mgr_business AS business ON business.businessId = history.businessId LEFT JOIN sys_admin AS admin ON admin.userId = history.operatorId ";
		String selectFields = " history.bcReturnId,bcOrder.orderNo,pass.nickName,pass.telephone,bcLine.businessType,bcLine.charteredMode,bcLine.startTime,bcLine.returnTime,business.brefName,history.bcReturnMoney,history.operateTime,admin.userName ";
		super.finit(table);
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(history.operateTime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(history.operateTime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and bcOrder.orderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and pass.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and business.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("") && !"0".equals(search.getField06())){
				cond+= " and bcLine.businessType = ? ";
				paramList.add(search.getField06().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT "+selectFields;
		sql +=" from "+table;
		String countSql = " select sum(history.bcReturnMoney) from "+table;
		sql += cond;
		countSql +=cond;
		sql += " order by history.operateTime desc ";
		List<BcReturnHistoryVo> bcReturnHistoryVos = tableDao.queryByPage(BcReturnHistoryVo.class, sql,currentPageIndex,params);
		page = new Page(bcReturnHistoryVos,sql,currentPageIndex,pageSize,params);
		map.put("list", bcReturnHistoryVos);
		map.put("page", page);
		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	
	/**
	 * 包车退票记录列表-退票详情
	 */
	public BcReturnHistoryVo getBcReturnHistoryDetail(
			BcReturnHistoryVo bcReturnHistoryVo) {
		String table = " bc_return_history as history LEFT JOIN bc_order AS bcOrder ON bcOrder.orderId = history.orderId LEFT JOIN passenger_info AS pass ON pass.passengerId = history.passengerId LEFT JOIN bc_line AS bcLine ON bcLine.bc_line_id = history.bc_line_id LEFT JOIN mgr_business AS business ON business.businessId = history.businessId LEFT JOIN sys_admin AS admin ON admin.userId = history.operatorId ";
		String selectFields = " history.bcReturnId,bcOrder.orderNo,pass.nickName,pass.displayId,pass.telephone,bcLine.businessType,bcLine.charteredMode,bcLine.startTime,bcLine.returnTime,business.brefName,history.bcReturnMoney,history.operateTime,admin.userName, " +
				" bcLine.beginAddress,bcLine.endAddress,bcLine.totalPassengers,bcLine.totalCars,bcLine.contacts,bcLine.contactPhone,bcLine.needInvoice,history.realPer,bcLine.carAge ";
		super.finit(table);
		sql = " SELECT "+selectFields+" FROM "+table+" WHERE history.bcReturnId = ? ";
		args = new Object[1];
		args[0] = bcReturnHistoryVo.getBcReturnId();
		bcReturnHistoryVo = tableDao.queryBean(BcReturnHistoryVo.class, sql, args);
		return bcReturnHistoryVo;
	}

	/**包车优惠券列表**/
	public Map<String, Object> queryBusCouponStatList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		String selectFileds = " pass.telephone,pass.orderId,pass.userTime,pass.getTime,cou.couponName,gif.giftValue,"+
			" cou.upLineTime,cou.createOn,bcOrder.giftMoney,bcOrder.price,bcOrder.giftLeftMoney,bcOrder.totalPrice ";
	String tables = " gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON pass.couponGiftId = cougif.couponGiftId "+
		" LEFT JOIN gf_gifts AS gif ON gif.giftPriId = cougif.giftPriId LEFT JOIN gf_coupon AS cou ON cou.couponId = cougif.couponId "+
		" LEFT JOIN bc_order AS bcOrder ON bcOrder.couponGiftId = pass.counponTeleId ";
	super.finit(tables);
	String cond = " WHERE pass.theModule = 2 AND pass.useState = 1  ";
	List<Object> paramList = new ArrayList<Object>();
	if(search!= null){
		if(search.getField01()!= null && !search.getField01().equals("")){
			cond+= " and left(pass.userTime,10) >= ? ";
			paramList.add(search.getField01());
		}
		if(search.getField02()!= null && !search.getField02().equals("")){
			cond+= " and left(pass.userTime,10) <= ? ";
			paramList.add(search.getField02());
		}
		if(search.getField03()!= null && !search.getField03().equals("")){
			cond+= " and cou.couponName like ? ";
			paramList.add(SqlBuilder.getSqlLikeValue(search.getField03()));
		}
		if(search.getField04()!= null && !search.getField04().equals("")){
			cond+= " and pass.telephone like ? ";
			paramList.add(SqlBuilder.getSqlLikeValue(search.getField04()));
		}
		if(search.getField05()!= null && !search.getField05().equals("")){
			cond+= " and pass.orderId like ? ";
			paramList.add(SqlBuilder.getSqlLikeValue(search.getField05()));
		}
	}
	Object[] params = paramList.toArray(new Object[]{});
	tableDao.setPageSize(pageSize);
	sql = " SELECT "+selectFileds+" FROM "+tables+cond;
	sql += " ORDER BY pass.userTime DESC ";
	list = tableDao.queryByPage(BcCouponStatVo.class, sql,currentPageIndex,params);
	page = new Page(list,sql,currentPageIndex,pageSize,params);
	map.put("list", list);
	map.put("page", page);
	return map;
	}

	/**检查该订单是否可以退票(如果包含优惠券,不与许退票)**/
	public int checkBcOrde(BcReturnVo bcReturnVo) {
		super.finit("gf_coupon_passenger");
		sql = " SELECT counponTeleId FROM gf_coupon_passenger WHERE theModule = 2 AND useState = 1 AND orderId= ? ";
		args = new Object[1];
		args[0] = bcReturnVo.getOrderNo();
		int count = tableDao.queryCount(sql, args);
		if(count>=1){
			return 1;
		}
		return 0;
	}

}
