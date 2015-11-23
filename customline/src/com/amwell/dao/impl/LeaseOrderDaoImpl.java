package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;
import com.amwell.dao.ILeaseOrderDao;
import com.amwell.entity.LeaseOrderModelVo;
import com.amwell.entity.OrderLocalVo;
import com.amwell.entity.Page;
import com.amwell.entity.RechargeVo;
import com.amwell.entity.ReturnTicketVo;
import com.amwell.entity.Search;
import com.amwell.utils.SessionUtils;
import com.amwell.vo.ApplyReturn;
import com.amwell.vo.BusCouponStatVo;
import com.amwell.vo.Errlog;
import com.amwell.vo.LeasePay;
import com.amwell.vo.LineBusinessOrderEntity;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.RechargeEntity;
import com.amwell.vo.ReturnTicket;
import com.amwell.vo.StatOutEntity;
import com.amwell.vo.StatOutLeaCou;
import com.amwell.vo.StatPassengerConsumLeaseEntity;
import com.amwell.vo.StatTotalEntity;
import com.amwell.vo.SysAdminEntity;
import com.amwell.vo.app.StatPassengerConsumDetail;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.been.ErrLog;
import com.amwell.vo.been.LeaseBaseInfo_1;
import com.amwell.vo.been.LineBusinessOrder_1;
@Repository("leaseOrderDao")
public class LeaseOrderDaoImpl extends DaoSupport implements ILeaseOrderDao {

	/**
	 * 查找所有订单列表
	 * @param search
	 * @param pageSizeIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> listByPage(Search search, int pageSizeIndex,
			int pageSize) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select leab.lineBaseId,leab.leaseOrderTime,leab.leaseOrderNo,leab.leaseOrderId,leab.buyType,leab.ispay,leab.ridesInfo,passinfo.displayId,passinfo.nickName,linebase.lineSubType,linebase.lineName,mgr.brefName,pay.payMoney,pay.payModel,leab.sourceFrom,passinfo.telephone,leab.ustation,leab.dstation,linebase.provinceCode,linebase.cityCode ");
		
		Map<String, Object> maps = conPartMaps(search);
		List<String> paramList = (List<String>) maps.get("paramList");
		StringBuilder cond = (StringBuilder) maps.get("cond");
		String table = (String) maps.get("table");
		sql.append(table);
		
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql.append(cond);
		sql.append(" order by leab.leaseOrderTime desc ");
//		String countSql = " select sum(pay.payMoney) from lease_base_info as leab left join mgr_business as mgr on leab.businessId = mgr.businessId left join lease_pay as pay on leab.leaseOrderNo = pay.leaseOrderNo left join passenger_info as passinfo on leab.passengerId = passinfo.passengerId left join line_base_info as linebase on leab.lineBaseId = linebase.lineBaseId ";
		StringBuilder countSql = new StringBuilder(" SELECT leab.leaseOrderNo ");
		countSql.append(table).append(cond);
		List<LeaseOrderModelVo> leaseOrderModelVos = tableDao.queryByPage(LeaseOrderModelVo.class, sql.toString(),pageSizeIndex,params);
		//查询订单班次日期
		if(leaseOrderModelVos != null && !leaseOrderModelVos.isEmpty()){
			for(LeaseOrderModelVo orderVo : leaseOrderModelVos){
				String leaseOrderId = orderVo.getLeaseOrderNo();
				String classTimeSql = "SELECT lci.`orderDate` FROM `line_business_order` AS lbo,`line_class_info` AS lci WHERE leaseOrderId=? AND lbo.`lineClassId`=lci.`lineClassId`";
				List<LineClassEntity> classList = super.tableDao.queryList(LineClassEntity.class, classTimeSql, new Object[]{leaseOrderId});
				orderVo.setLineClassEntities(classList);
			}
		}
		/**如果需要用ACTION中的站点循环,需要把这一部分给注释***/
		
		page = new Page(leaseOrderModelVos,countSql.toString(),pageSizeIndex,pageSize,params);
		map.put("list", leaseOrderModelVos);
		map.put("page", page);
//		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
//		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 充值明细
	 */
	public Map<String, Object> rechargeVoList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		super.finit(" stat_passenger_recharge as stat LEFT JOIN passenger_info as passinfo ON stat.passengerId = passinfo.passengerId ");
		String cond = " where 1 = 1 and retype =0 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(stat.retime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(stat.retime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and stat.rerunNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and passinfo.nickName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and stat.moneyLimit >= ? ";
				paramList.add(search.getField05().trim());
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and stat.moneyLimit <= ? ";
				paramList.add(search.getField06().trim());
			}
			if(search.getField07()!= null && !search.getField07().equals("")  && !"6".equals(search.getField07())){
				cond+= " and stat.reModel = ? ";
				paramList.add(search.getField07().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " select stat.retime,stat.rerunNo,passinfo.displayId,passinfo.nickName,stat.moneyLimit,stat.reModel ";
		sql +=" from stat_passenger_recharge as stat LEFT JOIN passenger_info as passinfo ON stat.passengerId = passinfo.passengerId ";
		sql += cond;
		sql += " order by stat.retime desc ";
		list = tableDao.queryByPage(RechargeVo.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}


	/**
	 *  添加收入统计数据
	 */
	public int saveStatTotal(StatTotalEntity statTotalEntity) {
		super.finit("stat_total");
		int statu = tableDao.updateData(statTotalEntity,"statTotalId");
		return statu;
	}

	/**
	 * 日收入统计
	 */
	public Map<String, Object> queryDayStatTotalList(Search search,
			Integer currentPageIndex, Integer pageSize,BigDecimal totalMoney) {
		super.finit("stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and lineBase.lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and mgr.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and vehicle.vehicleNumber like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and total.orderDate >= ? ";
				paramList.add(search.getField04());
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and total.orderDate < ? ";
				paramList.add(search.getField05());
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and total.orderStartTime = ? ";
				paramList.add(search.getField06());
			}
			if(search.getField07()!= null && !search.getField07().equals("") && !"2".equals(search.getField07())){
				cond+= " and total.buyType = ? " ;
				paramList.add(search.getField07());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " select total.*,passenger.displayId,passenger.nickName,passenger.telephone,lineBase.lineName,mgr.brefName,vehicle.vehicleNumber,driver.driverName ";
		sql += " from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		String countSql = " select sum(total.consumeLimit) from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		sql += cond;
		countSql += cond;
		sql += " order by orderDate ";
		list = tableDao.queryByPage(StatTotalEntity.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		if(null==totalMoney){
			List<String> totalMoneys = tableDao.queryByPage(String.class, countSql,0,params);
			map.put("totalMoney", totalMoneys!=null&& totalMoneys.size()>0?(totalMoneys.get(0)==null?BigDecimal.valueOf(0):totalMoneys.get(0)):BigDecimal.valueOf(0));
		}else{
			map.put("totalMoney", totalMoney);
		}
		map.put("list", list);
		map.put("page", page);
		
		return map;
	}

	/**
	 * 日期月度账目统计
	 */
	
	public Map<String, Object> queryDateStatTotalList(Search search,
			Integer dateCurrentPageIndex, Integer pageSize) {
		super.finit("stat_total");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and orderDate like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()));
			}
			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String date_s = TimeFormat.format(date);
			cond+= " and orderDate < ? ";
			paramList.add(date_s);
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		String sql = " select orderDate,SUM(consumeLimit) as consumeLimit from stat_total ";
		String countSql = " select sum(consumeLimit) from stat_total ";
		sql += cond;
		countSql += cond;
		sql += " GROUP BY orderDate ORDER BY orderDate desc ";
		List<StatTotalEntity> list = tableDao.queryByPage(StatTotalEntity.class, sql,dateCurrentPageIndex,params);
		Page page = new Page(list,sql,dateCurrentPageIndex,pageSize,params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0,params);
		map.put("dataList", list);
		map.put("datePage", page);
		map.put("dateTotalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 按商家月度账目统计报表
	 */
	public Map<String, Object> queryBusinessStatTotalList(Search search,
			Integer businessCurrentPageIndex, Integer pageSize) {
		super.finit("stat_total");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and total.orderDate like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()));
			}
			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String date_s = TimeFormat.format(date);
			cond+= " and orderDate < ? ";
			paramList.add(date_s);
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		String sql = " select mgr.brefName,SUM(consumeLimit) as consumeLimit from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		String countSql = " select sum(total.consumeLimit) from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		sql += cond;
		countSql += cond;
		sql += " GROUP BY mgr.businessId ";
		List<StatTotalEntity> list = tableDao.queryByPage(StatTotalEntity.class, sql,businessCurrentPageIndex,params);
		Page page = new Page(list,sql,businessCurrentPageIndex,pageSize,params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0,params);
		map.put("businessList", list);
		map.put("businessPage", page);
		map.put("businessTotalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 按线路月度账目统计报表
	 */
	public Map<String, Object> queryLineStatTotalList(Search search,
			Integer lineCurrentPageIndex, Integer pageSize) {
		super.finit("stat_total");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and total.orderDate like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()));
			}
			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String date_s = TimeFormat.format(date);
			cond+= " and orderDate < ? ";
			paramList.add(date_s);
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		String sql = " select lineBase.lineName,mgr.brefName,SUM(consumeLimit) as consumeLimit from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		String countSql = " select sum(total.consumeLimit) from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		sql += cond;
		countSql += cond;
		sql += " GROUP BY lineBase.lineBaseId,mgr.businessId  ";
		List<StatTotalEntity> list = tableDao.queryByPage(StatTotalEntity.class, sql,lineCurrentPageIndex,params);
		Page page = new Page(list,sql,lineCurrentPageIndex,pageSize,params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0,params);
		map.put("list", list);
		map.put("page", page);
		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		return map;
	}
	

	/**
	 * 记录错误信息
	 */
	public void addErrLog(Errlog err) {
		super.finit("err_log");
		tableDao.updateData("err_log",err, "errId");
	}

	/**
	 * 获取订单详情
	 */
	public LeaseOrderModelVo getLeaseOrderById(
			LeaseOrderModelVo leaseOrderModelVo) {
		super.finit(" lease_base_info as leab left join mgr_business as mgr on leab.businessId = mgr.businessId left join lease_pay as pay on leab.leaseOrderNo = pay.leaseOrderNo left join passenger_info as passinfo on leab.passengerId = passinfo.passengerId left join line_base_info as linebase on leab.lineBaseId = linebase.lineBaseId ");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		tableDao.setPageSize(1);
		if(null!=leaseOrderModelVo && !StringUtils.isEmpty(leaseOrderModelVo.getLeaseOrderId())){
			cond+= " and leab.leaseOrderId= ? ";
			paramList.add(leaseOrderModelVo.getLeaseOrderId());
		}
		Object[] params = paramList.toArray(new Object[]{});
		sql = " select leab.leaseOrderTime,leab.leaseOrderNo,leab.leaseOrderId,leab.buyType,leab.ispay,passinfo.displayId,passinfo.nickName,linebase.lineSubType,linebase.lineName,mgr.brefName,pay.payMoney,pay.payModel,leab.sourceFrom,passinfo.telephone,leab.ustation,leab.dstation ";
		sql +=" from lease_base_info as leab left join mgr_business as mgr on leab.businessId = mgr.businessId left join lease_pay as pay on leab.leaseOrderNo = pay.leaseOrderNo left join passenger_info as passinfo on leab.passengerId = passinfo.passengerId left join line_base_info as linebase on leab.lineBaseId = linebase.lineBaseId ";
		sql += cond;
		list = tableDao.queryByPage(LeaseOrderModelVo.class, sql,0,params);
		if(null!=list && list.size()>0){
			leaseOrderModelVo = (LeaseOrderModelVo) list.get(0);
			if(null!=leaseOrderModelVo && !StringUtils.isEmpty(leaseOrderModelVo.getLeaseOrderNo())){
				/**
				 * 根据订单的对应的乘车日期集合
				 */
				String line_sql = " select a.* from line_class_info a left JOIN line_business_order b on a.lineClassid = b.lineClassId where b.leaseOrderId = ? order by a.orderDate  ";
				args = new Object[1];
				args[0] = leaseOrderModelVo.getLeaseOrderNo();
				List<LineClassEntity> lineClassEntitys= tableDao.queryList(LineClassEntity.class, line_sql, args);
				if(null!=lineClassEntitys && lineClassEntitys.size()>0){
					//leaseOrderModelVo.setOrderDates(orderDates);
					//leaseOrderModelVo.setOrderStartTime(lineClassEntitys.get(0).getOrderStartTime());
					leaseOrderModelVo.setLineClassEntities(lineClassEntitys);
				}
				
			}
			return leaseOrderModelVo;
		}
		return null;
	}

	/**
	 * 获取车票退票列表
	 */
	public Map<String, Object> returnTicketListByPage(Search search,
			Integer currentPageIndex, Integer pageSize) {
		super.finit(" lease_base_info AS leab LEFT JOIN lease_pay AS pay ON leab.leaseOrderNo = pay.leaseOrderNo LEFT JOIN passenger_info AS passinfo ON leab.passengerId = passinfo.passengerId LEFT JOIN line_base_info AS linebase ON leab.lineBaseId = linebase.lineBaseId LEFT JOIN ( SELECT MIN(lineClass.orderDate) as orderDate,lineBusOrd.leaseOrderId as leaseOrderId,lineClass.orderStartTime FROM line_class_info as  lineClass LEFT JOIN line_business_order as lineBusOrd ON lineClass.lineClassId = lineBusOrd.lineClassId GROUP BY lineBusOrd.leaseOrderId ) as d ON d.leaseOrderId = pay.leaseOrderNo ");
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		String conds = "";
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
	    calendar.add(Calendar.DATE,-8);//一个星期以前
	    Date starttime=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		paramList.add(sdf.format(starttime));
		if(org.springframework.util.StringUtils.hasText(search.getField01())){
			String type = "";
			if(search.getField01().trim().length()==11){
				type = " = ? ";
				paramList.add(search.getField01().trim());
			}else{
				type = " like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			conds = " and passengerId = ( SELECT passengerId FROM passenger_info WHERE telephone "+type+" ) ";
		}
		if(org.springframework.util.StringUtils.hasText(search.getField02())){
			conds += " AND lineBusOrd.leaseOrderId like ? ";
			paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
		}
		sql = " select leab.leaseOrderNo,leab.leaseOrderId,d.orderStartTime,leab.ispay,passinfo.displayId,passinfo.nickName,linebase.lineSubType,linebase.lineName,passinfo.telephone ";
		sql += " from lease_base_info AS leab LEFT JOIN lease_pay AS pay ON leab.leaseOrderNo = pay.leaseOrderNo "
				+ "LEFT JOIN passenger_info AS passinfo ON leab.passengerId = passinfo.passengerId "
				+ "LEFT JOIN line_base_info AS linebase ON leab.lineBaseId = linebase.lineBaseId "
				+ "LEFT JOIN ( "
				+ "SELECT lineClass.orderDate AS orderDate, lineBusOrd.leaseOrderId AS leaseOrderId,lineClass.orderStartTime "
				+ "FROM line_class_info AS lineClass,line_business_order AS lineBusOrd "
				+ "WHERE lineClass.lineClassId = lineBusOrd.lineClassId AND orderDate >=? AND lineBusOrd.status IN ('0','1','2') "
				+conds
				+ "GROUP BY lineBusOrd.leaseOrderId,lineClass.orderStartTime ) as d ON d.leaseOrderId = pay.leaseOrderNo ";
		String cond = " where 1 = 1 AND leab.ispay =1 ";
		cond += " AND d.orderDate >= ? ";
		
		paramList.add(sdf.format(starttime));
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				String type = "";
				if(search.getField01().trim().length()==11){
					type = " = ? ";
					paramList.add(search.getField01());
				}else{
					type = " like ? ";
					paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
				}
				cond+= " and passinfo.telephone "+type+" ";
				
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and leab.leaseOrderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and d.orderStartTime = ? ";
				paramList.add(search.getField03().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql += cond;
		sql += " order by leab.leaseOrderNo ";
		list = tableDao.queryByPage(ReturnTicketVo.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 车票退票详情
	 */
	public ReturnTicketVo getReturnTicket(ReturnTicketVo returnTicketVo) {
		super.finit(" lease_base_info as leab left join mgr_business as mgr on leab.businessId = mgr.businessId left join lease_pay as pay on leab.leaseOrderNo = pay.leaseOrderNo left join passenger_info as passinfo on leab.passengerId = passinfo.passengerId left join line_base_info as linebase on leab.lineBaseId = linebase.lineBaseId ");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		tableDao.setPageSize(1);
		if(null!=returnTicketVo && !StringUtils.isEmpty(returnTicketVo.getLeaseOrderId())){
			cond+= " and leab.leaseOrderId= ? ";
			paramList.add(returnTicketVo.getLeaseOrderId());
		}
		Object[] params = paramList.toArray(new Object[]{});
		sql = " select leab.leaseOrderTime,leab.leaseOrderNo,leab.leaseOrderId,leab.buyType,leab.ispay,passinfo.displayId,passinfo.nickName,linebase.lineSubType,linebase.lineName,mgr.brefName,pay.payMoney,pay.payModel,leab.sourceFrom,passinfo.telephone,passinfo.passengerId,linebase.lineBaseId ";
		sql +=" from lease_base_info as leab left join mgr_business as mgr on leab.businessId = mgr.businessId left join lease_pay as pay on leab.leaseOrderNo = pay.leaseOrderNo left join passenger_info as passinfo on leab.passengerId = passinfo.passengerId left join line_base_info as linebase on leab.lineBaseId = linebase.lineBaseId ";
		sql += cond;
		list = tableDao.queryByPage(ReturnTicketVo.class, sql,0,params);
		if(null!=list && list.size()>0){
			ReturnTicketVo ticketVo = (ReturnTicketVo) list.get(0);
			if(null!=ticketVo && !StringUtils.isEmpty(ticketVo.getLeaseOrderNo())){
				/**
				 * 根据订单的对应的乘车日期集合
				 */
				String line_sql = " select a.orderStartTime,a.orderDate,b.localId,a.lineClassId from line_class_info a left JOIN line_business_order b on a.lineClassid = b.lineClassId where b.leaseOrderId = ? AND b.status IN (0,1,2) ";
				args = new Object[1];
				args[0] = ticketVo.getLeaseOrderNo();
				List<OrderLocalVo> totalOrderLocalVos= tableDao.queryList(OrderLocalVo.class, line_sql, args);
				line_sql +=" and a.orderStartTime = ? ";
				args = new Object[2];
				args[0] = ticketVo.getLeaseOrderNo();
				args[1] = returnTicketVo.getOrderStartTime();
				line_sql +=" order by a.orderDate ";
				List<OrderLocalVo> orderLocalVos= tableDao.queryList(OrderLocalVo.class, line_sql, args);
				List<String> orderDates = new ArrayList<String>();
				if(null!=orderLocalVos && orderLocalVos.size()>0){
					for(int i = 0;i<orderLocalVos.size();i++){
						orderDates.add(orderLocalVos.get(i).getOrderDate());
					}
					ticketVo.setOrderLocalVos(orderLocalVos);
					ticketVo.setOrderStartTime(orderLocalVos.get(0).getOrderStartTime());
					ticketVo.setTotalOrderLocalVos(totalOrderLocalVos);
				}
				
				String buy_sql = " SELECT localId FROM line_business_order WHERE leaseOrderId = ? ";
				Object[] args = new Object[1];
				args[0] = ticketVo.getLeaseOrderNo();
				int count = tableDao.queryCount(buy_sql, args);
				ticketVo.setBuySize(count);
			}
			return ticketVo;
		}
		return null;
	}

	/**
	 * 更改lease_base_info表
	 */
	public void updateLeaseBaseInfo(LeaseOrderModelVo leaseOrderModelVo) {
		super.finit(" lease_base_info ");
		tableDao.updateData(leaseOrderModelVo,"leaseOrderId");
	}

	/**
	 * 先找出该订单对应的支付记录--存在余额支付,把余额支付的数据放在第一位
	 */
	public List<LeasePay> queryLeasePay(LeasePay leasePay) {
		super.finit("lease_pay");
		sql = " select * from lease_pay where leaseOrderNo = ? ORDER BY payModel ";
		args = new Object[1];
		args[0] = leasePay.getLeaseOrderNo();
		List<LeasePay> leasePays= tableDao.queryList(LeasePay.class, sql, args);
		return leasePays;
	}

	
	/**
	 * 更改lease_pay表
	 */
	public void updateLeasePay(LeasePay lpay) {
		super.finit(" lease_pay ");
		tableDao.updateData(lpay,"payId");
	}

	/**
	 * 删除对应的line_business_order
	 */
	public void deleteLineBusinessOrder(
			LineBusinessOrderEntity businessOrderEntity) {
		super.finit(" line_business_order ");
		tableDao.deleteByid(LineBusinessOrderEntity.class, businessOrderEntity.getLocalId(), "localId");
	}
	
	/****
	 * (更新)对应的line_business_order
	 */
	public void updateLineBusinessOrder(LineBusinessOrderEntity businessOrderEntity){
		super.finit("line_business_order");
		sql = " UPDATE line_business_order SET status = 4 WHERE localId = ? ";
		args = new Object[1];
		args[0] =businessOrderEntity.getLocalId();
		tableDao.executeSQL(sql, args);
	}

	/**
	 * stat_passenger_recharge
	 */
	public void updateStatPassengerRecharge(RechargeEntity rechargeEntity) {
		super.finit(" stat_passenger_recharge ");
		tableDao.updateData(rechargeEntity,"rechargeId");
	}

	/**
	 * 找出订单对应的充值记录
	 */
	public List<RechargeEntity> queryRecharge(RechargeEntity rechargeEntity) {
		super.finit(" stat_passenger_recharge ");
		sql = " select * from stat_passenger_recharge where rerunNo = ? ";
		args = new Object[1];
		args[0] = rechargeEntity.getRerunNo();
		List<RechargeEntity> rechargeEntities= tableDao.queryList(RechargeEntity.class, sql, args);
		return rechargeEntities;
	}

	/**
	 * 找出订单对应的消费记录
	 */
	public List<StatPassengerConsumLeaseEntity> queryConsumLease(
			StatPassengerConsumLeaseEntity consumLeaseEntity) {
		super.finit(" stat_passenger_consum_lease ");
		sql = " select * from stat_passenger_consum_lease where leaseOrderNo = ? ";
		args = new Object[1];
		args[0] = consumLeaseEntity.getLeaseOrderNo();
		List<StatPassengerConsumLeaseEntity> consumLeaseEntities= tableDao.queryList(StatPassengerConsumLeaseEntity.class, sql, args);
		return consumLeaseEntities;
	}

	/**
	 * 更改stat_passenger_consum_lease表
	 */
	public void updateStatPassengerConsumLease(
			StatPassengerConsumLeaseEntity entity) {
		super.finit(" stat_passenger_consum_lease ");
		tableDao.updateData(entity,"orderConsumId");
	}

	/**
	 * 添加退票记录
	 * @throws SQLException 
	 */
	public int addReturnTicket(ReturnTicket returnTicket,String userId,String leaseOrderId,String lineClassIds){
		String [] lineClass = lineClassIds.split(",");
		String [] orderDate = returnTicket.getReturnDates().substring(0, returnTicket.getReturnDates().length()-1).split(",");
		Connection conn=null;
		int statu = -1;
		try {
			conn =MyDataSource.getConnect();
			if(conn==null){
				return -1;
			}
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			sql ="INSERT INTO return_ticket(returnId,leaseOrderNo,passengerId,orderStartTime,lineBaseId,oldMoney,realPayMoney,realBackMoney,realPoundage,percent,returnDateNum,returnReson,returnDates,operatior,operTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statu = qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),returnTicket.getLeaseOrderNo(),returnTicket.getPassengerId(),returnTicket.getOrderStartTime(),
					returnTicket.getLineBaseId(),returnTicket.getOldMoney(),returnTicket.getRealPayMoney(),returnTicket.getRealBackMoney(),returnTicket.getRealPoundage(),returnTicket.getPercent(),returnTicket.getReturnDateNum(),returnTicket.getReturnReson(),returnTicket.getReturnDates().endsWith(",")?returnTicket.getReturnDates().substring(0, returnTicket.getReturnDates().length()-1):returnTicket.getReturnDates(),returnTicket.getOperatior(),returnTicket.getOperTime()});
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
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
			if(-1==statu){
				AppendLog(userId, "订单管理", "退票失败");
			}else{
				AppendLog(userId, "订单管理", "退票成功");
				MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
				BigDecimal a = new BigDecimal(returnTicket.getRealBackMoney());
				BigDecimal b=new BigDecimal(lineClass.length);
				for(int i = 0;i<lineClass.length;i++){
					/**
					 * 添加支出记录
					 */
					addStatOut(userId,returnTicket.getLeaseOrderNo(),lineClass[i],a.divide(b,mc),1,orderDate[i],returnTicket.getOrderStartTime());
				}
			}
		}
		
		return statu;
	}
	
//	public static void main(String[] args) {
//		MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
//		BigDecimal a = new BigDecimal("10.20");
//		BigDecimal b=new BigDecimal(2);
//		System.out.println(a.divide(b,mc));
//	}

	/**
	 * 已退票列表
	 */
	public Map<String, Object> hasReturnTicketListByPage(Search search,
			Integer currentPageIndex, Integer pageSize) {
		super.finit(" return_ticket ");
		String cond = " where 1 = 1  ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(operTime,10) >= ? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(operTime,10) <= ? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and leaseOrderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05()));
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and displayId = ? ";
				paramList.add(search.getField06());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " select retick.*,linebase.lineName,passenger.displayId,passenger.nickName,passenger.telephone ";
		sql += " from return_ticket as retick left join line_base_info as linebase on retick.lineBaseId = linebase.lineBaseId left join passenger_info as passenger on passenger.passengerId = retick.passengerId ";
		String countSql = " select sum(retick.realBackMoney) from return_ticket as retick left join line_base_info as linebase on retick.lineBaseId = linebase.lineBaseId left join passenger_info as passenger on passenger.passengerId = retick.passengerId ";
		sql += cond;
		countSql +=cond;
		sql += " order by retick.operTime DESC ";
		list = tableDao.queryByPage(ReturnTicket.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		List<String> totalMoney = tableDao.queryList(String.class, countSql, params);
		BigDecimal money = new BigDecimal("0.00");
		if(null!=totalMoney && totalMoney.size()>0){
			Object str = totalMoney.get(0);
			 if(str instanceof BigDecimal){
				 money =(BigDecimal) str;
			 }
		}
		map.put("totalMoney",money );
		return map;
	}

	/**
	 * 车票退票详情
	 */
	public ReturnTicket getReturnTicketDetail(ReturnTicket returnTicket) {
		super.finit("return_ticket");
		sql =" select retick.*,linebase.lineName,passenger.displayId,passenger.nickName,passenger.telephone from return_ticket as retick left join line_base_info as linebase on retick.lineBaseId = linebase.lineBaseId left join passenger_info as passenger on passenger.passengerId = retick.passengerId where 1=1 and returnId = ? ";
		args = new Object[1];
		args[0] = returnTicket.getReturnId();
		returnTicket = tableDao.queryBean(ReturnTicket.class, sql, args);
		return returnTicket;
	}

	/**
	 * 支出列表
	 */
	public Map<String, Object> getStatOutListByPage(Search search,
			Integer currentPageIndex, Integer pageSize) {
		super.finit(" stat_out AS statOut LEFT JOIN line_base_info AS lineBase ON statOut.lineBaseId = lineBase.lineBaseId LEFT JOIN passenger_info AS passenger ON passenger.passengerId = statOut.passengerId LEFT JOIN driver_info AS driver ON driver.driverId = statOut.driverId LEFT JOIN mgr_business AS mgr ON mgr.businessId = statOut.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = statOut.vehicleId LEFT JOIN sys_admin AS sysAdmin ON sysAdmin.userId = statOut.operater ");
		String cond = " where 1 = 1  ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(statOut.operatTime,10) >= ? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(statOut.operatTime,10) <= ? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and lineBase.lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and mgr.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and passenger.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05()));
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and statOut.leaseOrderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06()));
			}
			if(search.getField07()!= null && !search.getField07().equals("") && !"0".equals(search.getField07())){
				cond+= " and statOut.outType = ? ";
				paramList.add(search.getField07());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT statOut.*,lineBase.lineName,passenger.displayId,passenger.nickName,passenger.telephone,driver.driverName,mgr.brefName,vehicle.vehicleNumber,sysAdmin.userName AS loginName ";
		sql += " from stat_out AS statOut LEFT JOIN line_base_info AS lineBase ON statOut.lineBaseId = lineBase.lineBaseId LEFT JOIN passenger_info AS passenger ON passenger.passengerId = statOut.passengerId LEFT JOIN driver_info AS driver ON driver.driverId = statOut.driverId LEFT JOIN mgr_business AS mgr ON mgr.businessId = statOut.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = statOut.vehicleId LEFT JOIN sys_admin AS sysAdmin ON sysAdmin.userId = statOut.operater ";
		String countSql = " select sum(statOut.outMoney) from stat_out AS statOut LEFT JOIN line_base_info AS lineBase ON statOut.lineBaseId = lineBase.lineBaseId LEFT JOIN passenger_info AS passenger ON passenger.passengerId = statOut.passengerId LEFT JOIN driver_info AS driver ON driver.driverId = statOut.driverId LEFT JOIN mgr_business AS mgr ON mgr.businessId = statOut.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = statOut.vehicleId LEFT JOIN sys_admin AS sysAdmin ON sysAdmin.userId = statOut.operater ";
		sql += cond;
		countSql += cond;
		sql += " order by statOut.operatTime DESC ";
		list = tableDao.queryByPage(StatOutEntity.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0,params);
		map.put("totalMoney", totalMoney!=null&& totalMoney.size()>0?(totalMoney.get(0)==null?BigDecimal.valueOf(0):totalMoney.get(0)):BigDecimal.valueOf(0));
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 添加收入统计记录
	 * @param leaseOrderNo 订单号
	 */
	public int addStatTotal(String leaseOrderNo) {
		super.finit(" lease_base_info AS leab , line_base_info AS lineBase ,line_business_order AS lineOrder ,line_class_info AS lineClass ");
		sql = " SELECT leab.leaseOrderNo,leab.leaseOrderTime AS operatTime,leab.businessId,leab.passengerId,leab.buyType,lineBase.discountRate,lineBase.orderPrice AS linePrice,lineOrder.price AS consumeLimit,lineBase.lineBaseId,lineBase.lineSubType,lineOrder.lineClassId,lineClass.orderDate,lineClass.orderStartTime,lineClass.driverId,lineClass.vehicleId ";
		sql +=" FROM lease_base_info AS leab , line_base_info AS lineBase ,line_business_order AS lineOrder ,line_class_info AS lineClass " +
				" WHERE 1=1 and leab.lineBaseId = lineBase.lineBaseId AND lineOrder.leaseOrderId = leab.leaseOrderNo AND lineClass.lineClassId = lineOrder.lineClassId  AND leab.ispay = 1 and leab.leaseOrderNo = ? ";
		args = new Object[2];
		args[0] = leaseOrderNo;
		args[1] = leaseOrderNo;
		List<StatTotalEntity> statTotalEntities = tableDao.queryList(StatTotalEntity.class,sql, args);
		Connection conn=null;
		int statu = -1;
		try {
			if(null!=statTotalEntities && statTotalEntities.size()>0){
				for(StatTotalEntity sEntity :statTotalEntities){
					StatTotalEntity statTotalEntity = sEntity;
					conn =MyDataSource.getConnect();
					if(conn==null){
						return -1;
					}
					conn.setAutoCommit(false);
					QueryRunner qr = new QueryRunner();
					
					sql ="INSERT INTO stat_total(statTotalId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,consumeLimit,discountRate,buyType,vehicleId,leaseOrderNo,lineClassId,businessId,linePrice,operatTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
					statu = qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),statTotalEntity.getOrderDate(),statTotalEntity.getOrderStartTime(),statTotalEntity.getLineSubType(),
							statTotalEntity.getLineBaseId(),statTotalEntity.getPassengerId(),statTotalEntity.getDriverId(),statTotalEntity.getConsumeLimit(),statTotalEntity.getDiscountRate(),statTotalEntity.getBuyType(),statTotalEntity.getVehicleId(),
							statTotalEntity.getLeaseOrderNo(),statTotalEntity.getLineClassId(),statTotalEntity.getBusinessId(),statTotalEntity.getLinePrice(),statTotalEntity.getOperatTime()});
				}
			}
			if(conn!=null){
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
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
		}
		return statu;
	}

	/**
	 * 取得所有已支付的订单号
	 */
	public List<String> getLeaseOrderNo(Integer currentPageIndex,Integer pageSize) {
		super.finit("lease_base_info");
		sql =" SELECT leaseOrderNo from lease_base_info WHERE 1=1 AND ispay = 1 ";
		tableDao.setPageSize(pageSize);
		List<String> leaseOrderNos = tableDao.queryByPage(String.class, sql, currentPageIndex);
		return leaseOrderNos;
	}

	/**
	 * 检查支付方式
	 * 包含余额支付的都不允许退票
	 */
	public int getLeasePayCount(String leaseOrderId) {
		super.finit("lease_pay");
		sql = " select pay.leaseOrderNo from lease_pay  as pay LEFT JOIN lease_base_info  as leab ON pay.leaseOrderNo = leab.leaseOrderNo where 1=1 and pay.payModel = 1 and leab.leaseOrderId = ? ";
		args = new Object[1];
		args[0] = leaseOrderId;
		int count = tableDao.queryCount(sql, args);
		if(0==count){
			//是否优惠（0:否   1:是）
			sql = " select isdiscount from lease_base_info where leaseOrderId = ? ";
			int c = tableDao.queryBean(Integer.class, sql, args);
			count = c;
		}
		
		return count;
	}

	/**
	 * 判断该订单是否已经添加过收入记录
     * 如果已经存在了，则不再添加
	 */
	public int getStatTotalCountByLeaseOrderNo(String leaseOrderNo) {
		super.finit("stat_total");
		sql = " select leaseOrderNo from stat_total where leaseOrderNo = ? ";
		args = new Object[1];
		args[0] = leaseOrderNo;
		int count = tableDao.queryCount(sql, args);
		return count;
	}

	/**
	 * 从支出表里面还原数据到收入表
	 */
	public void addStatTotalByStatOut(List<StatOutEntity> list) {
		Connection conn=null;
		try {
			if(null!=list && list.size()>0){
				for(StatOutEntity sEntity :list){
					StatOutEntity statTotalEntity = sEntity;
					conn =MyDataSource.getConnect();
					if(conn==null){
						break;
					}
					conn.setAutoCommit(false);
					QueryRunner qr = new QueryRunner();
					
					sql ="INSERT INTO stat_total(statTotalId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,consumeLimit,discountRate,buyType,vehicleId,leaseOrderNo,lineClassId,businessId,linePrice,operatTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
					qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),statTotalEntity.getOrderDate(),statTotalEntity.getOrderStartTime(),statTotalEntity.getLineSubType(),
							statTotalEntity.getLineBaseId(),statTotalEntity.getPassengerId(),statTotalEntity.getDriverId(),statTotalEntity.getOutMoney(),100,1,statTotalEntity.getVehicleId(),
							statTotalEntity.getLeaseOrderNo(),statTotalEntity.getLineClassId(),statTotalEntity.getBusinessId(),statTotalEntity.getOutMoney(),statTotalEntity.getOperatTime()});
				}
			}
			if(conn!=null){
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
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
		}
	}

	/**
	 * 清空收入统计表(小猪巴士)
	 */
	public int truncateTable() {
		super.finit("stat_total");
		sql = " truncate table stat_total ";
		return tableDao.executeSQL(sql);
	}

	/**找出退票的订单 以及该订单退票的张数(张数大于1张)**/
	public List<StatOutLeaCou> queryStatOutLeaCou(Integer page,Integer pageSize) {
		super.finit("stat_out");
		sql = " SELECT * FROM ( SELECT leaseOrderNo,COUNT(1) AS countTimes,outMoney FROM stat_out WHERE outType = 1 GROUP BY leaseOrderNo ) AS leab WHERE 1=1 AND leab.countTimes>1 ";
		tableDao.setPageSize(pageSize);
		List<StatOutLeaCou> list = tableDao.queryByPage(StatOutLeaCou.class, sql,page);
		return list;
	}

	/**比较该订单号支出的价格是否等于该订单号对应的退票记录中的退票金额，如果是，则需要修改**/
	public boolean compareLeaMon(StatOutLeaCou leaCou) {
		super.finit("return_ticket");
		sql = " SELECT leaseOrderNo FROM return_ticket WHERE leaseOrderNo =? AND realBackMoney=? ";
		args = new Object[2];
		args[0] = leaCou.getLeaseOrderNo();
		args[1] = leaCou.getOutMoney();
		int count = tableDao.queryCount(sql, args);
		if(1==count){
			return true;
		}
		return false;
	}

	/***更改对应的支出记录**/
	public int updateStatOut(StatOutLeaCou leaCou) {
		Connection conn=null;
		int statu = -1;
		try {
			conn =MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			sql =" UPDATE stat_out SET outMoney = ? WHERE leaseOrderNo = ?";
			statu = qr.update(conn, sql, new Object[]{leaCou.getOutMoney(),leaCou.getLeaseOrderNo()});
			System.out.println(sql);
			conn.commit();
			return statu;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
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
				statu = -1;
				e.printStackTrace();
			}
		}
//		int statu = -1;
//		super.finit("stat_out");
//		sql =" UPDATE stat_out SET outMoney = ? WHERE leaseOrderNo = ?";
//		args = new Object[2];
//		args[0] = leaCou.getOutMoney();
//		args[1] = leaCou.getLeaseOrderNo();
//		statu = tableDao.executeSQL(sql, args);
		return statu;
	}

	/**找出总共需要修改的退票订单记录数**/
	public int queryStatOutLeaCouAllCount() {
		super.finit("stat_out");
		sql = " SELECT leaseOrderNo FROM ( SELECT leaseOrderNo ,COUNT(1) AS countTimes FROM stat_out WHERE outType = 1 GROUP BY leaseOrderNo ) AS leab WHERE 1=1 AND leab.countTimes>1 ";
		int count = tableDao.queryCount(sql);
		return count;
	}

	/**找出总共需要恢复的订单记录数**/
	public int queryLeaseOrderCount() {
		super.finit("lease_base_info");
		sql = " SELECT leaseOrderNo from lease_base_info WHERE 1=1 AND ispay = 1 ";
		int count = tableDao.queryCount(sql);
		return count;
	}

	/**找出总共需要恢复的支出记录数**/
	public int queryStatOutListCount() {
		super.finit("stat_out");
		sql = " SELECT leaseOrderNo FROM stat_out ";
		int count = tableDao.queryCount(sql);
		return count;
	}

	
	/**事务处理恢复订单**/
	public boolean recordBack() {
		Connection conn=null;
		boolean flag = false;
		
		/**每页记录数**/
		int pageSize = 200;
		/**起始页**/
		int page = 1;
		/**总页数**/
		int totalPage = 1;
		/**总记录数**/
		int totalCount = 0;
		String sql = null;
		
		try {
			conn =MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			/**找出总共需要修改的退票订单记录数**/
			sql = " SELECT count(*) FROM ( SELECT leaseOrderNo ,COUNT(1) AS countTimes FROM stat_out WHERE outType = 1 GROUP BY leaseOrderNo ) AS leab WHERE 1=1 AND leab.countTimes>1 ";
			Long count = (Long)qr.query(conn,sql, new ScalarHandler<Long>(1), new Object[]{});
			totalCount = count.intValue();
			System.out.println("找出总共需要修改的退票订单记录数"+totalCount);
			totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
			/**记录业务相关的数据需求记录数**/
			int dataCount = 0;
			
			/***更新数据记录数*/
			int statu = 0;
			
			/***关于退票的金额错误数据**/
			List<StatOutLeaCou> leaCous =null;
			MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
			StatOutLeaCou leaCou = null;
			BigDecimal a = null;
			BigDecimal b=null;
			
			System.out.println("更新支出表里面关于退票的金额错误数据=====>开始");
			for(page =1;page<=totalPage;page++){
				/***
				 * 更新支出表里面关于退票的金额错误数据
				 */
				/**1  找出退票的订单 以及该订单退票的张数(张数大于1张)
				 * 如果张数大于1张，则需要修改对应的金额，每一张对应的金额应为退票总金额除以张数
				 */
				sql = " SELECT * FROM ( SELECT leaseOrderNo,COUNT(1) AS countTimes,outMoney FROM stat_out WHERE outType = 1 GROUP BY leaseOrderNo ) AS leab WHERE 1=1 AND leab.countTimes>1 LIMIT ?,? ";
				leaCous = qr.query(conn, sql,new BeanListHandler<StatOutLeaCou>(StatOutLeaCou.class),new Object[]{(page-1)*pageSize,pageSize});
				
				/***
				 * 2 更新退票张数大于1的支出数据金额
				 */
				for(int i=0;i<leaCous.size();i++){
					leaCou = leaCous.get(i);
					a = leaCou.getOutMoney();
					/**比较该订单号支出的价格是否等于该订单号对应的退票记录中的退票金额，如果是，则需要修改**/
					sql = " SELECT count(*) FROM return_ticket WHERE leaseOrderNo =? AND realBackMoney=? ";
					dataCount = ((Long)qr.query(conn,sql, new ScalarHandler<Long>(1), new Object[]{leaCou.getLeaseOrderNo(),leaCou.getOutMoney()})).intValue();
					if(dataCount>=1){
						a = leaCou.getOutMoney();
						b=new BigDecimal(leaCou.getCountTimes());
						leaCou.setOutMoney(a.divide(b,mc));
						/***更改对应的支出记录**/
						sql =" UPDATE stat_out SET outMoney = ? WHERE leaseOrderNo = ?";
						statu = qr.update(conn, sql, new Object[]{leaCou.getOutMoney(),leaCou.getLeaseOrderNo()});
						if(statu<=0){
							throw new Exception("更改对应的支出记录失败");
						}
					}
				}
			}
			
			
			/**
			 * 找出总共需要恢复的订单记录数
			 */
			sql = " SELECT count(*) from lease_base_info WHERE 1=1 AND ispay = 1 ";
			count = (Long)qr.query(conn,sql, new ScalarHandler<Long>(1),new Object[]{});
			totalCount = count.intValue();
			totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
			
			List<StatTotalEntity> statTotalEntities = null;
			StatTotalEntity statTotalEntity = null;
			
			if(totalCount>=1){
				for(page = 1;page<=totalPage;page++){
					/**
					 * 还原所有已支付订单的数据(小猪巴士)
					 */
					sql =" SELECT leaseOrderNo,count(*) AS countTimes from lease_base_info WHERE 1=1 AND ispay = 1 GROUP BY leaseOrderNo LIMIT ?,? ";
					leaCous = qr.query(conn, sql,new BeanListHandler<StatOutLeaCou>(StatOutLeaCou.class),new Object[]{(page-1)*pageSize,pageSize});
					if (null != leaCous && leaCous.size() > 0) {
						for(int i = 0;i<leaCous.size();i++){
							sql = " SELECT leab.leaseOrderNo,leab.leaseOrderTime AS operatTime,leab.businessId,leab.passengerId,leab.buyType,lineBase.discountRate,lineBase.orderPrice AS linePrice,lineOrder.price AS consumeLimit,lineBase.lineBaseId,lineBase.lineSubType,lineOrder.lineClassId,lineClass.orderDate,lineClass.orderStartTime,lineClass.driverId,lineClass.vehicleId ";
							sql +=" FROM lease_base_info AS leab , line_base_info AS lineBase ,line_business_order AS lineOrder ,line_class_info AS lineClass " +
//									" ,( SELECT SUM(payMoney) AS payMoney FROM lease_pay WHERE leaseOrderNo = ? GROUP BY leaseOrderNo) AS payMoney " +
									" WHERE 1=1 and leab.lineBaseId = lineBase.lineBaseId AND lineOrder.leaseOrderId = leab.leaseOrderNo AND lineClass.lineClassId = lineOrder.lineClassId  AND leab.ispay = 1 and leab.leaseOrderNo = ? ";
							statTotalEntities =	qr.query(conn,sql,new BeanListHandler<StatTotalEntity>(StatTotalEntity.class),new Object[]{leaCous.get(i).getLeaseOrderNo()});
							if(null!=statTotalEntities && statTotalEntities.size()>0){
								for(StatTotalEntity sEntity :statTotalEntities){
									statTotalEntity = sEntity;
									sql ="INSERT INTO stat_total(statTotalId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,consumeLimit,discountRate,buyType,vehicleId,leaseOrderNo,lineClassId,businessId,linePrice,operatTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
									statu = qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),statTotalEntity.getOrderDate(),statTotalEntity.getOrderStartTime(),statTotalEntity.getLineSubType(),
											statTotalEntity.getLineBaseId(),statTotalEntity.getPassengerId(),statTotalEntity.getDriverId(),statTotalEntity.getConsumeLimit(),statTotalEntity.getDiscountRate(),statTotalEntity.getBuyType(),statTotalEntity.getVehicleId(),
											statTotalEntity.getLeaseOrderNo(),statTotalEntity.getLineClassId(),statTotalEntity.getBusinessId(),statTotalEntity.getLinePrice(),statTotalEntity.getOperatTime()});
									if(statu<=0){
										throw new Exception("还原订单数据失败");
									}
								}
							}
						}
					} 
				}
				System.out.println("订单数据恢复完成");
			}
			else {
				System.out.println("没有需要恢复的订单数据");
			}
			
			/**
			 * 找出总共需要恢复的支出记录数
			 */
			sql = " SELECT count(*) FROM stat_out ";
			count = (Long)qr.query(conn,sql, new ScalarHandler<Long>(1),new Object[]{});
			totalCount = count.intValue();
			System.out.println("找出总共需要恢复的支出记录数"+totalCount);
			totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
			List<StatOutEntity> statOutEntities = null;
			StatOutEntity statOutEntity = null;
			if(totalCount>=1){
				System.out.println("支出订单数据恢复开始");
				for(page=1;page<=totalPage;page++){
					sql = " SELECT statOut.*,lineBase.lineName,passenger.displayId,passenger.nickName,passenger.telephone,driver.driverName,mgr.brefName,vehicle.vehicleNumber,sysAdmin.userName AS loginName ";
					sql += " from stat_out AS statOut LEFT JOIN line_base_info AS lineBase ON statOut.lineBaseId = lineBase.lineBaseId LEFT JOIN passenger_info AS passenger ON passenger.passengerId = statOut.passengerId LEFT JOIN driver_info AS driver ON driver.driverId = statOut.driverId LEFT JOIN mgr_business AS mgr ON mgr.businessId = statOut.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = statOut.vehicleId LEFT JOIN sys_admin AS sysAdmin ON sysAdmin.userId = statOut.operater ";
					sql += " LIMIT ?,? ";
					statOutEntities = qr.query(conn, sql,new BeanListHandler<StatOutEntity>(StatOutEntity.class),new Object[]{(page-1)*pageSize,pageSize});
					if(null!=statOutEntities && statOutEntities.size()>0 ){
						for(StatOutEntity sEntity :statOutEntities){
							statOutEntity = sEntity;
							sql ="INSERT INTO stat_total(statTotalId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,consumeLimit,discountRate,buyType,vehicleId,leaseOrderNo,lineClassId,businessId,linePrice,operatTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
							statu = qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),statOutEntity.getOrderDate(),statOutEntity.getOrderStartTime(),statOutEntity.getLineSubType(),
									statOutEntity.getLineBaseId(),statOutEntity.getPassengerId(),statOutEntity.getDriverId(),statOutEntity.getOutMoney(),100,1,statOutEntity.getVehicleId(),
									statOutEntity.getLeaseOrderNo(),statOutEntity.getLineClassId(),statOutEntity.getBusinessId(),statOutEntity.getOutMoney(),statOutEntity.getOperatTime()});
						}
					}
				}
				System.out.println("支出订单数据恢复结束");
			}else{
				System.out.println("没有需要恢复的支出订单数据");
			}
			
			
			conn.commit();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
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
		}
		return flag;
	}

	
	/**上下班优惠券列表**/
	public Map<String, Object> queryBusCouponStatList(Search search,Integer currentPageIndex, Integer pageSize) {
		
		String selectFileds = " pass.telephone,gif.giftValue,cou.couponName,lineOrder.giftMoney,lineOrder.price,"
							+" lineb.lineName,pass.getTime,pass.userTime,cou.upLineTime,cou.createOn,lineOrder.outMoney,pass.orderId ";
		String tables = " gf_coupon_passenger AS pass LEFT JOIN gf_coupon_gift AS cougif ON pass.couponGiftId = cougif.couponGiftId "
						+" LEFT JOIN gf_gifts AS gif ON gif.giftPriId = cougif.giftPriId LEFT JOIN line_business_order AS lineOrder ON lineOrder.leaseOrderId = pass.orderId "
						+" LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = lineOrder.leaseOrderId LEFT JOIN line_base_info AS lineb ON lineb.lineBaseId = leab.lineBaseId "
						+" LEFT JOIN gf_coupon AS cou ON cou.couponId = cougif.couponId ";
		super.finit(tables);
		String cond = " WHERE pass.theModule = 1 AND pass.useState = 1  ";
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
				cond+= " and lineb.lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and pass.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and cou.couponName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05()));
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and pass.orderId like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06()));
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " SELECT "+selectFileds+" FROM "+tables+cond;
		sql += " ORDER BY pass.userTime DESC ";
		list = tableDao.queryByPage(BusCouponStatVo.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**乘客申请退票列表**/
	public Map<String, Object> applyReturnTicketList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		//SELECT a.applyReturnId, a.lineClassIds, a.leaseOrderNo, a.oldReturn, a.percent, a.realReturn, a.realPoundage, a.type, a.applyTime, b.telephone, a.returnDates, a.operTime, CASE a.payModel WHEN 2 THEN '财付通' WHEN 3 THEN '银联' WHEN 4 THEN '支付宝' WHEN 5 THEN '微信' WHEN 6 THEN '(APP)微信' END payModel FROM apply_return AS a LEFT JOIN passenger_info AS b ON a.passengerId = b.passengerId WHERE 1 = 1 AND a.payModel = '5' GROUP BY a.leaseOrderNo, a.returnDates, a.realReturn ORDER BY a.applyTime DESC, a.type DESC LIMIT 0, 10
		String table = "apply_return AS a LEFT JOIN passenger_info AS b ON a.passengerId = b.passengerId";
		super.finit(table);
		String selectField = " a.applyReturnId,a.lineClassIds,a.leaseOrderNo,a.oldReturn,a.percent,a.realReturn,a.realPoundage,a.type,a.applyTime,b.telephone,a.returnDates,a.operTime,CASE a.payModel WHEN 2 THEN '财付通' WHEN 3 THEN '银联' WHEN 4 THEN '支付宝' WHEN 5 THEN '微信' WHEN 6 THEN '(APP)微信' END payModel ";
		String sql = "";
		sql = " SELECT "+selectField+" FROM "+table+" WHERE 1=1 ";
		StringBuilder sb = new StringBuilder();
		List<Object> paramList = new ArrayList<Object>();
		if (null!=search) {
			if(!StringUtils.isEmpty(search.getField01())){
				//申请时间(开始)
				sb.append(" AND left(a.applyTime,10)>= ? ");
				paramList.add(search.getField01().trim());
			}
			if(!StringUtils.isEmpty(search.getField02())){
				//申请时间(结束)
				sb.append(" AND left(a.applyTime,10)<= ? ");
				paramList.add(search.getField02().trim());
			}
			if(!StringUtils.isEmpty(search.getField03())){
				//订单号
				sb.append(" AND a.leaseOrderNo LIKE ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(!StringUtils.isEmpty(search.getField04())){
				//手机号码
				sb.append(" AND b.telephone LIKE ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(!StringUtils.isEmpty(search.getField05()) && !"2".equals(search.getField05())){
				//类型
				sb.append(" AND a.type = ? ");
				paramList.add(search.getField05().trim());
			}
			if(!StringUtils.isEmpty(search.getField06()) && !"7".equals(search.getField06())){
				//类型
				sb.append(" AND a.payModel = ? ");
				paramList.add(search.getField06().trim());
			}
		}
		sb.append(" GROUP BY a.leaseOrderNo,a.returnDates,a.realReturn  ORDER BY a.applyTime DESC,a.type DESC ");
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(ApplyReturn.class, sql+sb.toString(),currentPageIndex,params);
		page = new Page(list,sql+sb.toString(),currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**修改退票申请状态**/
	public int updateApplyReturnTicketType(ApplyReturn applyReturn) {
		
		super.finit("sys_admin");
		//SELECT userName from sys_admin WHERE userId = '14092514471355024'
		sql = " SELECT userName from sys_admin WHERE userId = ? ";
		args = new Object[1];
		args[0] = applyReturn.getOperatior();
		SysAdminEntity sys = tableDao.queryBean(SysAdminEntity.class, sql, args);
		super.finit("return_ticket");
		//SELECT returnId FROM return_ticket WHERE returnDates = ( SELECT returnDates FROM apply_return WHERE applyReturnId = '150706201947519049' )
		sql = " SELECT returnId FROM return_ticket WHERE returnDates = ( SELECT returnDates FROM apply_return WHERE applyReturnId = ? ) ";
		args = new Object[1];
		args[0] = applyReturn.getApplyReturnId();
		ReturnTicket re = tableDao.queryBean(ReturnTicket.class, sql, args);
		re.setOperatior(sys.getUserName());
		re.setOperTime(applyReturn.getOperTime());
		tableDao.updateData(re,"returnId");
		super.finit("apply_return");
		return tableDao.updateData(applyReturn,"applyReturnId");
	}

	/**计算总计**/
	public String countTotalValue(Search search) {
		super.finit(" lease_base_info as leab, mgr_business AS mgr ,lease_pay AS pay  ,passenger_info AS passinfo ,line_base_info AS linebase ");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(leab.leaseOrderTime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(leab.leaseOrderTime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and leab.leaseOrderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("") && !"3".equals(search.getField04())){
				cond+= " and linebase.lineSubType = ? ";
				paramList.add(search.getField04().trim());
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and linebase.lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and passinfo.nickName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06().trim()));
			}
			if(search.getField07()!= null && !search.getField07().equals("")){
				cond+= " and mgr.brefName like ?";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField07().trim()));
			}
			if(search.getField08()!= null && !search.getField08().equals("") && !"3".equals(search.getField08())){
				cond+= " and leab.buyType = ? ";
				paramList.add(search.getField08().trim());
			}
			if(search.getField09()!= null && !search.getField09().equals("") && !"7".equals(search.getField09())){
				cond+= " and pay.payModel = ? ";
				paramList.add(search.getField09().trim());
			}
			String field10 = search.getField10();
			if(field10!=null && !field10.equals("") && !"5".equals(field10.trim())){
				cond+=" and leab.ispay = ? ";
				paramList.add(search.getField10().trim());
			}
			if(search.getField11()!= null && !search.getField11().equals("") && !"3".equals(search.getField11())){
				cond+= " and leab.sourceFrom = ? ";
				paramList.add(search.getField11());
			}
			if(search.getField12()!= null && !search.getField12().equals("")){
				cond+= " and passinfo.telephone like ?";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField12().trim()));
			}
			if(!StringUtils.isEmpty(search.getField13()) && !"0".equals(search.getField13())){
				cond+= " and linebase.provinceCode = ?";
				paramList.add(search.getField13().trim());
			}
			if(!StringUtils.isEmpty(search.getField14()) && !"0".equals(search.getField14())){
				cond+= " and linebase.cityCode = ?";
				paramList.add(search.getField14().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		String countSql = " select sum(pay.payMoney) AS a1 from lease_base_info as leab left join mgr_business as mgr on leab.businessId = mgr.businessId left join lease_pay as pay on leab.leaseOrderNo = pay.leaseOrderNo left join passenger_info as passinfo on leab.passengerId = passinfo.passengerId left join line_base_info as linebase on leab.lineBaseId = linebase.lineBaseId ";
		countSql += cond;
		AppVo_1 a1 = tableDao.queryBean(AppVo_1.class, countSql, params);
		if(null==a1){
			return "0";
		}
		return a1.getA1();
	}

	/**(APP)支付宝退票列表**/
	public Map<String, Object> appZfbReturnTicketList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		//SELECT leaseOrderNo,SUM(realBackMoney) AS realBackMoney,thirdparty, CASE returnOrNot WHEN 0 THEN '未退款' WHEN 1 THEN '已退款' END returnOrNot,operTime,REPLACE(group_concat(returnDates),',','<br/>')AS returnDates,GROUP_CONCAT(returnId) AS returnId  FROM return_ticket WHERE payType = 4 GROUP BY leaseOrderNo ORDER BY operTime DESC
		super.finit("return_ticket");
		String sql = " SELECT leaseOrderNo,SUM(realBackMoney) AS realBackMoney,thirdparty, CASE returnOrNot WHEN 0 THEN '未退款' WHEN 1 THEN '已退款' END returnOrNot,operTime,REPLACE(group_concat(returnDates),',','<br/>')AS returnDates,GROUP_CONCAT(returnId) AS returnId  FROM return_ticket WHERE payType = 4 ";
		StringBuilder sb = new StringBuilder();
		List<Object> paramList = new ArrayList<Object>();
		if (null!=search) {
			if(!StringUtils.isEmpty(search.getField01())){
				//操作时间(开始)
				sb.append(" AND left(operTime,10)>=? ");
				paramList.add(search.getField01().trim());
			}
			if(!StringUtils.isEmpty(search.getField02())){
				//操作时间(结束)
				sb.append(" AND left(operTime,10)<= ? ");
				paramList.add(search.getField02().trim());
			}
			if(!StringUtils.isEmpty(search.getField03())){
				//订单号
				sb.append(" AND leaseOrderNo LIKE ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(!StringUtils.isEmpty(search.getField04())){
				//支付宝交易号
				sb.append(" AND thirdparty LIKE ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(!StringUtils.isEmpty(search.getField05()) && !"2".equals(search.getField05())){
				//是否退款: 0(未退款) 1(已退款)
				sb.append(" AND returnOrNot = ? ");
				paramList.add(search.getField05().trim());
			}
			if("1".equals(search.getField06())){
				//支付宝交易号是否为空: 0:'是',1:'否'
				sb.append(" AND LENGTH(thirdparty) > 0 ");
			}
			if("0".equals(search.getField06())){
				//支付宝交易号是否为空: 0:'是',1:'否'
				sb.append(" AND thirdparty IS NULL ");
			}
		}
		sb.append(" GROUP BY leaseOrderNo ORDER BY operTime DESC ");
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		list = tableDao.queryByPage(ReturnTicket.class, sql+sb.toString(),currentPageIndex,params);
		page = new Page(list,sql+sb.toString(),currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**将该次需要退款的数据加上批次号**/
	public int addBatchNoToReturnTicket(String returnIds, String batchNo) {
		Connection conn = null;
		int statu = 0;
		try {
			conn =MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String[] returnIdStr = returnIds.split(","); 
			String sql = null;
			Object[] args = null;
			for(String str : returnIdStr){
				if(null!=str && !"".equals(str)){
					sql = " UPDATE return_ticket SET batchNo = ?  WHERE returnId = ? ";
					args = new Object[2];
					args[0] = batchNo;
					args[1] = str;
					statu = qr.update(conn,sql,args);
					if(statu!=1){
						throw new Exception("添加退款批次号异常");
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
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
		}
		return statu;
	}

	/**根据退款批次号修改return_ticket的退款状态**/
	public int updateReturnOrNot(String batchNo) {
		Connection conn = null;
		int statu = 0;
		String sql = "";
		Object[] args = null;
		try {
			conn =MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			//更改apply_return中type的状态为 1   0:未退票  1:已退票
			//UPDATE apply_return SET type = 1 WHERE leaseOrderNo IN ( SELECT DISTINCT leaseOrderNo FROM return_ticket WHERE batchNo = '20150827150224' AND returnOrNot = 1 )
			sql = " UPDATE apply_return SET type = 1 WHERE leaseOrderNo IN ( SELECT DISTINCT leaseOrderNo FROM return_ticket WHERE batchNo = ? AND returnOrNot = 1 ) ";
			args = new Object[1];
			args[0] = batchNo;
			statu = qr.update(conn, sql, args);
			if(statu<1){
				throw new Exception("修改apply_return状态-异常");
			}
			
			sql = " UPDATE return_ticket SET returnOrNot = 1  WHERE batchNo = ? ";
			args = new Object[1];
			args[0] = batchNo;
			statu = qr.update(conn, sql, args);
			if(statu<1){
				throw new Exception("根据退款批次号修改return_ticket的退款状态-异常");
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
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
		}
		return statu;
	}

	/**添加退款记录**/
	public void addReturnStatu(int statu, int type, String batchNo) {
		Connection conn = null;
		if(statu>0){
			statu = 1;
		}else{
			statu = 0;
		}
		try {
			conn =MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			sql = " INSERT INTO return_statu(id,returnStatu,returnType,mes,optime) VALUES (?,?,?,?,?) ";
			Object[] args = new Object[5];
			args[0] = StringUtil.generateSequenceNo();
			args[1] = statu;
			args[2] = type;
			args[3] = batchNo;
			args[4] = MyDate.getMyDateLong();
			statu = qr.update(conn, sql, args);
			if(statu<1){
				throw new Exception("添加退款记录-异常");
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
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
		}
	}

	/**根据条件获取总记录数**/
	public int queryTotal(Search search) {
		super.finit("stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and lineBase.lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and mgr.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and vehicle.vehicleNumber like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and total.orderDate >= ? ";
				paramList.add(search.getField04());
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and total.orderDate < ? ";
				paramList.add(search.getField05());
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " and total.orderStartTime = ? ";
				paramList.add(search.getField06());
			}
			if(search.getField07()!= null && !search.getField07().equals("") && !"2".equals(search.getField07())){
				cond+= " and total.buyType = ? " ;
				paramList.add(search.getField07());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		sql = " select passenger.displayId ";
		sql += " from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		sql += cond;
		sql += " order by orderDate ";
		int count = tableDao.queryCount(sql, params);
		return count;
	}

	/**根据条件获得对应的订单记录数**/
	@Override
	public int queryLeaseNuByCon(Search search) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select leab.leaseOrderId ");
		
		Map<String, Object> maps = conPartMaps(search);
		List<String> paramList = (List<String>) maps.get("paramList");
		StringBuilder cond = (StringBuilder) maps.get("cond");
		String table = (String) maps.get("table");
		sql.append(table);
		sql.append(cond);
		sql.append(" order by leab.leaseOrderTime desc ");
		int count = tableDao.queryCount(sql.toString(), paramList.toArray());
		return count;
	}
	
	/**组装订单sql和条件map**/
	private Map<String, Object> conPartMaps(Search search){
		super.finit(" lease_base_info as leab, mgr_business AS mgr ,lease_pay AS pay  ,passenger_info AS passinfo ,line_base_info AS linebase ");
		String table = " from lease_base_info as leab left join mgr_business as mgr on leab.businessId = mgr.businessId left join lease_pay as pay on leab.leaseOrderNo = pay.leaseOrderNo left join passenger_info as passinfo on leab.passengerId = passinfo.passengerId left join line_base_info as linebase on leab.lineBaseId = linebase.lineBaseId ";
		Map<String, Object> maps = new HashMap<String,Object>();
		StringBuilder cond = new StringBuilder(" where 1 = 1 ");
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond.append(" and left(leab.leaseOrderTime,10) >=? ");
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond.append(" and left(leab.leaseOrderTime,10) <=? ");
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond.append(" and leab.leaseOrderNo like ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("") && !"3".equals(search.getField04())){
				cond.append(" and linebase.lineSubType = ? ");
				paramList.add(search.getField04().trim());
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond.append(" and linebase.lineName like ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond.append(" and passinfo.nickName like ? ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06().trim()));
			}
			if(search.getField07()!= null && !search.getField07().equals("")){
				cond.append(" and mgr.brefName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField07().trim()));
			}
			if(search.getField08()!= null && !search.getField08().equals("") && !"3".equals(search.getField08())){
				cond.append(" and leab.buyType = ? ");
				paramList.add(search.getField08().trim());
			}
			if(search.getField09()!= null && !search.getField09().equals("") && !"7".equals(search.getField09())){
				cond.append(" and pay.payModel = ? ");
				paramList.add(search.getField09().trim());
			}
			String field10 = search.getField10();
			if(field10!=null && !field10.equals("") && !"5".equals(field10.trim())){
				cond.append(" and leab.ispay = ? ");
				paramList.add(search.getField10().trim());
			}
			if(search.getField11()!= null && !search.getField11().equals("") && !"3".equals(search.getField11())){
				cond.append(" and leab.sourceFrom = ? ");
				paramList.add(search.getField11());
			}
			if(search.getField12()!= null && !search.getField12().equals("")){
				cond.append(" and passinfo.telephone like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField12().trim()));
			}
			if(!StringUtils.isEmpty(search.getField13()) && !"0".equals(search.getField13())){
				cond.append(" and linebase.provinceCode = ?");
				paramList.add(search.getField13().trim());
			}
			if(!StringUtils.isEmpty(search.getField14()) && !"0".equals(search.getField14())){
				cond.append(" and linebase.cityCode = ?");
				paramList.add(search.getField14().trim());
			}
			
			maps.put("cond", cond);
			maps.put("paramList", paramList);
			maps.put("table", table);
		}
		return maps;
	}


}