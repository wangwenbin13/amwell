package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amwell.base.DaoSupport;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.ILeaseOrderDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.StatOutEntity;
import com.amwell.vo.StatTotalEntity;

@Service("leaseOrderDao")
public class LeaseOrderDaoImpl extends DaoSupport implements ILeaseOrderDao {

	/**
	 * 日收入统计
	 */
	public Map<String, Object> queryStatDayStatTotalList(Search search, Integer currentPageIndex, Integer pageSize) {
		super.finit(
				"stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if (search != null) {
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and lineBase.lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and mgr.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
			}
			if (search.getField03() != null && !search.getField03().equals("")) {
				cond += " and vehicle.vehicleNumber like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if (search.getField04() != null && !search.getField04().equals("")) {
				cond += " and total.orderDate >= ? ";
				paramList.add(search.getField04());
			}
			if (search.getField05() != null && !search.getField05().equals("")) {
				cond += " and total.orderDate < ? ";
				paramList.add(search.getField05());
			}
			if (search.getField06() != null && !search.getField06().equals("")) {
				cond += " and mgr.businessId = ? ";
				paramList.add(search.getField06());
			}
		}
		Object[] params = paramList.toArray(new Object[] {});
		tableDao.setPageSize(pageSize);
		sql = " select total.*,passenger.displayId,passenger.nickName,passenger.telephone,lineBase.lineName,mgr.brefName,vehicle.vehicleNumber,driver.driverName ";
		sql += " from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		String countSql = " select sum(total.consumeLimit) from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		sql += cond;
		countSql += cond;
		sql += " order by orderStartTime desc ";
		list = tableDao.queryByPage(StatTotalEntity.class, sql, currentPageIndex, params);
		page = new Page(list, sql, currentPageIndex, pageSize, params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0, params);
		map.put("list", list);
		map.put("page", page);
		map.put("totalMoney", totalMoney != null && totalMoney.size() > 0
				? (totalMoney.get(0) == null ? BigDecimal.valueOf(0) : totalMoney.get(0)) : BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 日期月度账目统计
	 */
	public Map<String, Object> queryStatDateStatTotalList(Search search, Integer dateCurrentPageIndex,
			Integer pageSize) {
		super.finit("stat_total");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if (search != null) {
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and orderDate like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()));
			}
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and businessId = ? ";
				paramList.add(search.getField02());
			}
			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String date_s = TimeFormat.format(date);
			cond += " and orderDate < ? ";
			paramList.add(date_s);
		}
		Object[] params = paramList.toArray(new Object[] {});
		tableDao.setPageSize(pageSize);
		String sql = " select orderDate,SUM(consumeLimit) as consumeLimit from stat_total ";
		String countSql = " select sum(consumeLimit) from stat_total ";
		sql += cond;
		countSql += cond;
		sql += " GROUP BY orderDate ORDER BY orderDate desc ";
		List<StatTotalEntity> list = tableDao.queryByPage(StatTotalEntity.class, sql, dateCurrentPageIndex, params);
		Page page = new Page(list, sql, dateCurrentPageIndex, pageSize, params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0, params);
		map.put("dataList", list);
		map.put("datePage", page);
		map.put("dateTotalMoney", totalMoney != null && totalMoney.size() > 0
				? (totalMoney.get(0) == null ? BigDecimal.valueOf(0) : totalMoney.get(0)) : BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 按车辆月度账目统计报表
	 */
	public Map<String, Object> queryStatBusinessStatTotalList(Search search, Integer businessCurrentPageIndex,
			Integer pageSize) {
		super.finit("stat_total");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if (search != null) {
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and total.orderDate like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()));
			}
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and mgr.businessId = ? ";
				paramList.add(search.getField02());
			}
			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String date_s = TimeFormat.format(date);
			cond += " and orderDate < ? ";
			paramList.add(date_s);
		}
		Object[] params = paramList.toArray(new Object[] {});
		tableDao.setPageSize(pageSize);
		String sql = " select vehicle.vehicleNumber,vehicle.vehicleType,vehicle.vehicleBrand,SUM(consumeLimit) as consumeLimit from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		String countSql = " select sum(total.consumeLimit) from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		sql += cond;
		countSql += cond;
		sql += " GROUP BY vehicle.vehicleId ";
		List<StatTotalEntity> list = tableDao.queryByPage(StatTotalEntity.class, sql, businessCurrentPageIndex, params);
		Page page = new Page(list, sql, businessCurrentPageIndex, pageSize, params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0, params);
		map.put("businessList", list);
		map.put("businessPage", page);
		map.put("businessTotalMoney", totalMoney != null && totalMoney.size() > 0
				? (totalMoney.get(0) == null ? BigDecimal.valueOf(0) : totalMoney.get(0)) : BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 按线路月度账目统计报表
	 */
	public Map<String, Object> queryStatLineStatTotalList(Search search, Integer lineCurrentPageIndex,
			Integer pageSize) {
		super.finit("stat_total");
		String cond = " where 1 = 1 ";
		List<Object> paramList = new ArrayList<Object>();
		if (search != null) {
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and total.orderDate like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()));
			}
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and mgr.businessId = ? ";
				paramList.add(search.getField02());
			}
			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String date_s = TimeFormat.format(date);
			cond += " and orderDate < ? ";
			paramList.add(date_s);
		}
		Object[] params = paramList.toArray(new Object[] {});
		tableDao.setPageSize(pageSize);
		String sql = " select lineBase.lineName,mgr.brefName,SUM(consumeLimit) as consumeLimit from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		String countSql = " select sum(total.consumeLimit) from stat_total as total LEFT JOIN passenger_info AS passenger ON total.passengerId = passenger.passengerId LEFT JOIN line_base_info AS lineBase ON total.lineBaseId = lineBase.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = total.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = total.vehicleId LEFT JOIN driver_info AS driver ON driver.driverId = total.driverId ";
		sql += cond;
		countSql += cond;
		sql += " GROUP BY lineBase.lineBaseId,mgr.businessId  ";
		List<StatTotalEntity> list = tableDao.queryByPage(StatTotalEntity.class, sql, lineCurrentPageIndex, params);
		Page page = new Page(list, sql, lineCurrentPageIndex, pageSize, params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0, params);
		map.put("list", list);
		map.put("page", page);
		map.put("totalMoney", totalMoney != null && totalMoney.size() > 0
				? (totalMoney.get(0) == null ? BigDecimal.valueOf(0) : totalMoney.get(0)) : BigDecimal.valueOf(0));
		return map;
	}

	/**
	 * 支出统计列表
	 */
	public Map<String, Object> getStatOutListByPage(Search search, Integer currentPageIndex, Integer pageSize) {
		super.finit(
				" stat_out AS statOut LEFT JOIN line_base_info AS lineBase ON statOut.lineBaseId = lineBase.lineBaseId LEFT JOIN passenger_info AS passenger ON passenger.passengerId = statOut.passengerId LEFT JOIN driver_info AS driver ON driver.driverId = statOut.driverId LEFT JOIN mgr_business AS mgr ON mgr.businessId = statOut.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = statOut.vehicleId LEFT JOIN sys_admin AS sysAdmin ON sysAdmin.userId = statOut.operater ");
		String cond = " where 1 = 1  ";
		List<Object> paramList = new ArrayList<Object>();
		if (search != null) {
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and left(statOut.operatTime,10) >= ? ";
				paramList.add(search.getField01());
			}
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and left(statOut.operatTime,10) <= ? ";
				paramList.add(search.getField02());
			}
			if (search.getField03() != null && !search.getField03().equals("")) {
				cond += " and lineBase.lineName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03()));
			}
			if (search.getField04() != null && !search.getField04().equals("")) {
				cond += " and mgr.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04()));
			}
			if (search.getField05() != null && !search.getField05().equals("")) {
				cond += " and passenger.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05()));
			}
			if (search.getField06() != null && !search.getField06().equals("")) {
				cond += " and statOut.leaseOrderNo like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06()));
			}
			if (search.getField07() != null && !search.getField07().equals("") && !"0".equals(search.getField07())) {
				cond += " and statOut.outType = ? ";
				paramList.add(search.getField07());
			}
			if (search.getField08() != null && !search.getField08().equals("")) {
				cond += " and mgr.businessId = ? ";
				paramList.add(search.getField08());
			}
		}
		Object[] params = paramList.toArray(new Object[] {});
		tableDao.setPageSize(pageSize);
		sql = " SELECT statOut.*,lineBase.lineName,passenger.displayId,passenger.nickName,passenger.telephone,driver.driverName,mgr.brefName,vehicle.vehicleNumber,sysAdmin.userName AS loginName ";
		sql += " from stat_out AS statOut LEFT JOIN line_base_info AS lineBase ON statOut.lineBaseId = lineBase.lineBaseId LEFT JOIN passenger_info AS passenger ON passenger.passengerId = statOut.passengerId LEFT JOIN driver_info AS driver ON driver.driverId = statOut.driverId LEFT JOIN mgr_business AS mgr ON mgr.businessId = statOut.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = statOut.vehicleId LEFT JOIN sys_admin AS sysAdmin ON sysAdmin.userId = statOut.operater ";
		String countSql = " select sum(statOut.outMoney) from stat_out AS statOut LEFT JOIN line_base_info AS lineBase ON statOut.lineBaseId = lineBase.lineBaseId LEFT JOIN passenger_info AS passenger ON passenger.passengerId = statOut.passengerId LEFT JOIN driver_info AS driver ON driver.driverId = statOut.driverId LEFT JOIN mgr_business AS mgr ON mgr.businessId = statOut.businessId LEFT JOIN vehicle_info AS vehicle ON vehicle.vehicleId = statOut.vehicleId LEFT JOIN sys_admin AS sysAdmin ON sysAdmin.userId = statOut.operater ";
		sql += cond;
		countSql += cond;
		sql += " order by statOut.operatTime DESC ";
		list = tableDao.queryByPage(StatOutEntity.class, sql, currentPageIndex, params);
		page = new Page(list, sql, currentPageIndex, pageSize, params);
		List<String> totalMoney = tableDao.queryByPage(String.class, countSql, 0, params);
		map.put("totalMoney", totalMoney != null && totalMoney.size() > 0
				? (totalMoney.get(0) == null ? BigDecimal.valueOf(0) : totalMoney.get(0)) : BigDecimal.valueOf(0));
		map.put("list", list);
		map.put("page", page);
		return map;
	}

}
