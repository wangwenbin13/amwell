package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.ILineDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.CarClassVo;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.DriverClassVo;
import com.amwell.vo.DriverVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineDetailVo;
import com.amwell.vo.LineEntity;
import com.amwell.vo.MerchantLineVo;
import com.amwell.vo.MerchantOrderVo;
import com.amwell.vo.MerchantSchedulingVo;
import com.amwell.vo.StationInfo;
import com.amwell.vo.VehicleInfoVo;

@Repository("lineDao")
public class LineDaoImpl extends DaoSupport implements ILineDao {

	public Map<String, Object> getMerchantLineList(Search search, int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				"select a.lineBaseId,a.lineType,a.lineSubType,a.lineName,a.orderPrice,a.lineStatus,a.createOn from line_base_info a inner join mgr_business b on a.businessId = b.businessId inner join sys_admin c on a.createBy = c.userId where 1=1 and a.lineType=1 and c.sysType=0 and a.lineStatus!=5");
		List<Object> paramList = new ArrayList<Object>();
		if (null != search) {
			if (StringUtils.hasText(search.getField01())) {
				sql.append(" and a.lineName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			if (StringUtils.hasText(search.getField02())) {
				super.finit("pb_station");
				// 如果输入有站点名称
				StringBuffer stationSql = new StringBuffer(
						"select * from pb_station where 1=1 and name like ? order by sortNum");
				List<StationInfo> stationList = super.tableDao.queryList(StationInfo.class, stationSql.toString(),
						new Object[] { SqlBuilder.getSqlLikeValue(search.getField02().trim()) });
				if (false == CollectionUtils.isEmpty(stationList)) {
					sql.append(" and (");
					int index = 0;
					for (StationInfo s : stationList) {
						if (index != 0) {
							sql.append(" or ");
						}
						sql.append("a.lineBaseId = ?");
						paramList.add(s.getLineId());
						index++;
					}
					sql.append(")");
				} else {
					// 如果用户输入站点，但无站点结果集，直接返回空
					res.put("page", page);
					res.put("list", list);
					return res;
				}
			}
			// 有商户
			if (StringUtils.hasText(search.getField03())) {
				sql.append(" and b.businessId=?");
				paramList.add(search.getField03().trim());
			}
			if (StringUtils.hasText(search.getField04())) {
				sql.append(" and a.lineSubType=?");
				paramList.add(Integer.parseInt(search.getField04().trim()));
			}
			if (StringUtils.hasText(search.getField05())) {
				sql.append(" and a.lineStatus=?");
				paramList.add(Integer.parseInt(search.getField05().trim()));
			}
		}
		sql.append(" order by a.displayFlag desc,a.updateOn desc");
		super.finit(
				"line_base_info a inner join mgr_business b on a.businessId = b.businessId inner join sys_admin c on a.createBy = c.userId");
		Object[] params = paramList.toArray(new Object[] {});
		// MySQL的分页时,limit从0开始，底层方法为记录数下标
		List<MerchantLineVo> list = super.tableDao.queryByPage(MerchantLineVo.class, sql.toString(), currentPageIndex,
				pageSize, params);
		// 需要重新装list,取出起点，终点名称
		if (false == CollectionUtils.isEmpty(list)) {
			for (MerchantLineVo v : list) {
				List<StationInfo> stationInfoList = getStationListByLineId(v.getLineBaseId());
				if (stationInfoList != null) {
					// 如果站点集合只有一个，可能会造成起始站点和结束站点同名的情况
					v.setStartname(stationInfoList.get(0).getName());
					v.setEndname(stationInfoList.get(stationInfoList.size() - 1).getName());
				}
			}
		}
		Page page = new Page(list, sql.toString(), currentPageIndex, pageSize, params);
		res.put("page", page);
		res.put("list", list);
		return res;
	}

	public LineDetailVo getLineDetail(String lineBaseId) {
		LineDetailVo line = null;
		if (!StringUtils.hasText(lineBaseId)) {
			return line;
		}
		super.finit("line_base_info");
		String line_sql = "select a.lineBaseId,a.lineName,a.lineTime,a.lineType,a.lineSubType,a.orderPrice,a.lineStatus,a.remark from line_base_info a where 1=1 and a.lineType=1 and a.lineBaseId=?";
		line = super.tableDao.queryBean(LineDetailVo.class, line_sql, new Object[] { lineBaseId });
		if (null == line) {
			return line;
		}
		// 获取站点信息，注意：起始站点和首尾站点单独放置，途经站点合成
		List<StationInfo> stationInfoList = getStationListByLineId(line.getLineBaseId());
		List<String> stationList = new ArrayList<String>();
		StringBuffer mStations = new StringBuffer();
		if (stationInfoList != null) {
			int len = stationInfoList.size();
			if (len <= 2) {
				stationList.add(stationInfoList.get(0).getName());
				stationList.add("---");
				stationList.add(stationInfoList.get(1).getName());
			} else {
				for (int i = 0; i < len; i++) {
					String name = stationInfoList.get(i).getName();
					if (i == 0 || i == len - 1) {
						stationList.add(name);
					} else {
						// 增加图片点击事件
						mStations.append(
								"<span class=\"cursor\" onclick=\"viewSitePice('" + name + "');\">" + name + "</span>");
						if (i == len - 2) {
							stationList.add(mStations.toString());
						} else {
							mStations.append("---");
						}
					}
				}
			}
		}
		line.setStationList(stationList);
		// 获取发车时间，座位信息
		super.finit("line_class_info");
		// 调度时，取未删除的班次
		String line_class_sql = "select a.orderStartTime,a.orderSeats from line_class_info a,line_base_info b where a.lineBaseId=b.lineBaseId and a.lineBaseId=? and a.delFlag=0 group by a.orderStartTime,a.orderSeats;";
		List<LineClassEntity> lineClassList = super.tableDao.queryList(LineClassEntity.class, line_class_sql,
				new Object[] { lineBaseId });
		if (false == CollectionUtils.isEmpty(lineClassList)) {
			line.setLineClassList(lineClassList);
		}
		return line;
	}

	private List<StationInfo> getStationListByLineId(String lineId) {
		String station_sql = "select * from pb_station where lineId=? order by sortNum";
		Object[] params = new Object[1];
		params[0] = lineId;
		List<StationInfo> stationInfoList = super.tableDao.queryList(StationInfo.class, station_sql, params);
		return stationInfoList;
	}

	public List<LineClassCarDriverVo> getLineClassCarDriverList(String lineBaseId, String orderStartTime,
			String orderDate) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit(
					"line_class_info a INNER JOIN line_base_info b ON a.lineBaseId = b.lineBaseId LEFT JOIN driver_info c ON a.driverId = c.driverId LEFT JOIN vehicle_info d ON a.vehicleId = d.vehicleId");
			StringBuffer sql = new StringBuffer(
					"SELECT b.lineBaseId, a.lineClassId, a.orderDate, a.driverId, c.driverName, a.vehicleId, d.vehicleBrand, d.vehicleNumber, d.vehicleSeats,(select count(f.leaseOrderId) from line_business_order e,lease_base_info f where e.leaseOrderId = f.leaseOrderNo and f.ispay=1 and e.lineClassId=a.lineClassId and(e.status=0 or e.status=1 or e.status=2)) as orderCount FROM line_class_info a INNER JOIN line_base_info b ON a.lineBaseId = b.lineBaseId LEFT JOIN driver_info c ON a.driverId = c.driverId LEFT JOIN vehicle_info d ON a.vehicleId = d.vehicleId WHERE a.delFlag=0 AND b.lineType=1 AND a.lineBaseId=?");
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(lineBaseId);
			if (StringUtils.hasText(orderStartTime)) {
				sql.append(" and a.orderStartTime=?");
				paramList.add(orderStartTime);
			}
			if (StringUtils.hasText(orderDate)) {
				sql.append(" and a.orderDate like ?");
				paramList.add(orderDate + "%");
			}
			sql.append("order by a.orderDate asc");
			return super.tableDao.queryList(LineClassCarDriverVo.class, sql.toString(), paramList.toArray());
		}
		return new ArrayList<LineClassCarDriverVo>(0);
	}

	public Map<String, Object> getDriverList(String businessId, String orderStartTime, String orderDate,
			String driverName, int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (StringUtils.hasText(businessId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			super.finit("from driver_info a");
			String sql = "select a.driverId,a.driverName,a.sex,a.telephone,(select count(1) from line_class_info b where b.orderStartTime=? and b.orderDate like ? and b.driverId=a.driverId) as scheduleCount from driver_info a,mgr_business m where 1=1 and a.businessId=m.businessId and m.accountStatus=1 and a.accountStatus=0 and m.businessId=?";
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(orderStartTime);
			paramList.add(orderDate + "%");
			paramList.add(businessId);
			if (StringUtils.hasText(driverName)) {
				sql += " and a.driverName like ?";
				paramList.add(SqlBuilder.getSqlLikeValue(driverName));
			}
			Object[] params = paramList.toArray();
			List<DriverVo> list = super.tableDao.queryByPage(DriverVo.class, sql, currentPageIndex, pageSize, params);
			Page page = new Page(list, sql.toString(), currentPageIndex, pageSize, params);
			res.put("page", page);
			res.put("list", list);
		}
		return res;
	}

	public Map<String, Object> getCarList(String businessId, String orderStartTime, String orderDate, String carInfo,
			int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (StringUtils.hasText(businessId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			super.finit("line_class_info b");
			String sql = "select a.vehicleId,a.vehicleBrand,a.vehicleNumber,a.vehicleSeats,(select count(1) from line_class_info b where b.orderStartTime=? and b.orderDate like ? and b.vehicleId=a.vehicleId) as scheduleCount from vehicle_info a,mgr_business m where 1=1 and a.businessId=m.businessId and m.accountStatus=1 and a.delFlag=0 and m.businessId=?";
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(orderStartTime);
			paramList.add(orderDate + "%");
			paramList.add(businessId);
			if (StringUtils.hasText(carInfo)) {
				sql += " and (a.vehicleNumber like ? or a.vehicleBrand like ? or a.vehicleModel like ? )";
				paramList.add(SqlBuilder.getSqlLikeValue(carInfo));
				paramList.add(SqlBuilder.getSqlLikeValue(carInfo));
				paramList.add(SqlBuilder.getSqlLikeValue(carInfo));
			}
			Object[] params = paramList.toArray();
			List<VehicleInfoVo> list = super.tableDao.queryByPage(VehicleInfoVo.class, sql, currentPageIndex, pageSize,
					params);
			Page page = new Page(list, sql.toString(), currentPageIndex, pageSize, params);
			res.put("page", page);
			res.put("list", list);
		}
		return res;
	}

	public List<MerchantSchedulingVo> getMerchantSchedulingList(String businessId, String orderDate) {
		if (StringUtils.hasText(businessId) && StringUtils.hasText(orderDate)) {
			super.finit(
					"line_base_info a INNER JOIN line_class_info b ON a.lineBaseId = b.lineBaseId LEFT JOIN driver_info c ON b.driverId = c.driverId LEFT JOIN vehicle_info d ON d.vehicleId = b.vehicleId");
			String sql = "SELECT b.orderStartTime, a.lineSubType, a.lineName, b.lineClassId, c.driverName, CONCAT( d.vehicleBrand, d.vehicleModel ) AS vehicleBrand, d.vehicleNumber, ( SELECT count(1) FROM lease_base_info e, line_business_order f WHERE e.leaseOrderNo = f.leaseOrderId AND e.lineBaseId = a.lineBaseId AND f.lineClassId = b.lineClassId AND e.ispay = 1 and (f.status=0 or f.status=1 or f.status=2) ) AS orderCount FROM line_base_info a INNER JOIN line_class_info b ON a.lineBaseId = b.lineBaseId LEFT JOIN driver_info c ON b.driverId = c.driverId LEFT JOIN vehicle_info d ON d.vehicleId = b.vehicleId WHERE 1 = 1 AND a.lineType = 1 AND a.lineStatus = 3 AND a.businessId =? AND b.orderDate =? and b.delFlag=0 ORDER BY b.orderStartTime";
			List<MerchantOrderVo> orderList = super.tableDao.queryList(MerchantOrderVo.class, sql,
					new Object[] { businessId, orderDate });
			if (false == CollectionUtils.isEmpty(orderList)) {
				// 按orderStartTime，归并数据
				TreeMap<String, List<MerchantOrderVo>> orderMap = new TreeMap<String, List<MerchantOrderVo>>();
				for (MerchantOrderVo v : orderList) {
					String orderStartTime = v.getOrderStartTime();
					if (orderMap.containsKey(orderStartTime)) {
						orderMap.get(orderStartTime).add(v);
					} else {
						List<MerchantOrderVo> tempList = new ArrayList<MerchantOrderVo>();
						tempList.add(v);
						orderMap.put(orderStartTime, tempList);
					}
				}
				if (false == CollectionUtils.isEmpty(orderMap)) {
					Set<String> keys = orderMap.keySet();
					List<MerchantSchedulingVo> res = new ArrayList<MerchantSchedulingVo>();
					MerchantSchedulingVo vo = null;
					for (String key : keys) {
						vo = new MerchantSchedulingVo();
						vo.setOrderStartTime(key);
						vo.setMerchantOrderList(orderMap.get(key));
						res.add(vo);
					}
					return res;
				}
			}
		}
		return new ArrayList<MerchantSchedulingVo>(0);
	}

	public List<DriverClassVo> getDriverClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String driverId) {
		if (StringUtils.hasText(orderStartTime) && StringUtils.hasText(driverId)) {
			super.finit("line_class_info a,line_base_info b");
			List<Object> paramList = new ArrayList<Object>();
			// 此处并没有判断司机所属商户情形，driverId的归属应由前端控制.调度时，商户只能使用其自身的司机
			StringBuffer sql = new StringBuffer(
					"select a.lineClassId,a.orderDate,b.lineTime,b.lineSubType,b.lineBaseId from line_class_info a,line_base_info b where b.lineType=1 and a.lineBaseId=b.lineBaseId and a.driverId=? and a.orderStartTime=?");
			paramList.add(driverId);
			paramList.add(orderStartTime);
			if (StringUtils.hasText(orderDate)) {
				sql.append(" and a.orderDate like ?");
				paramList.add(orderDate + "%");
			} else {
				if (StringUtils.hasText(beginOrderDate)) {
					if (StringUtils.hasText(endOrderDate)) {
						sql.append(" and a.orderDate between ? and ?");
						paramList.add(beginOrderDate);
						paramList.add(endOrderDate);
					} else {
						sql.append(" and a.orderDate > ?");
						paramList.add(beginOrderDate);
					}
				} else {
					if (StringUtils.hasText(endOrderDate)) {
						sql.append(" and a.orderDate < ?");
						paramList.add(endOrderDate);
					}
				}
			}
			// update
			sql.append(" order by a.orderDate");
			List<DriverClassVo> dcList = super.tableDao.queryList(DriverClassVo.class, sql.toString(),
					paramList.toArray());
			if (false == CollectionUtils.isEmpty(dcList)) {
				for (DriverClassVo v : dcList) {
					List<StationInfo> stationList = getStationListByLineId(v.getLineBaseId());
					if (stationList != null) {
						v.setStartname(stationList.get(0).getName());
						v.setEndname(stationList.get(stationList.size() - 1).getName());
					}
				}
			}
			return dcList;
		}
		return new ArrayList<DriverClassVo>(0);
	}

	public List<CarClassVo> getCarClassList(String orderStartTime, String orderDate, String beginOrderDate,
			String endOrderDate, String vehicleId) {
		if (StringUtils.hasText(orderStartTime) && StringUtils.hasText(vehicleId)) {
			super.finit("line_class_info a,line_base_info b");
			List<Object> paramList = new ArrayList<Object>();
			// 此处并没有判断车辆所属商户情形，vehicleId的归属应由前端控制.调度时，商户只能使用其自身的司机，且车辆状态为有效，至于车辆状态变化，此处未做处理
			StringBuffer sql = new StringBuffer(
					"select a.lineClassId,a.orderDate,b.lineBaseId,b.lineTime,b.lineSubType from line_class_info a,line_base_info b where b.lineType=1 and a.lineBaseId=b.lineBaseId and a.vehicleId=? and a.orderStartTime=?");
			paramList.add(vehicleId);
			paramList.add(orderStartTime);
			if (StringUtils.hasText(orderDate)) {
				sql.append(" and a.orderDate like ?");
				paramList.add(orderDate + "%");
			} else {
				if (StringUtils.hasText(beginOrderDate)) {
					if (StringUtils.hasText(endOrderDate)) {
						sql.append(" and a.orderDate between ? and ?");
						paramList.add(beginOrderDate);
						paramList.add(endOrderDate);
					} else {
						sql.append(" and a.orderDate > ?");
						paramList.add(beginOrderDate);
					}
				} else {
					if (StringUtils.hasText(endOrderDate)) {
						sql.append(" and a.orderDate < ?");
						paramList.add(endOrderDate);
					}
				}
			}
			// update
			sql.append(" order by a.orderDate");
			List<CarClassVo> ccList = super.tableDao.queryList(CarClassVo.class, sql.toString(), paramList.toArray());
			if (false == CollectionUtils.isEmpty(ccList)) {
				for (CarClassVo v : ccList) {
					List<StationInfo> stationList = getStationListByLineId(v.getLineBaseId());
					if (stationList != null) {
						v.setStartname(stationList.get(0).getName());
						v.setEndname(stationList.get(stationList.size() - 1).getName());
					}
				}
			}
			return ccList;
		}
		return new ArrayList<CarClassVo>(0);
	}

	/**
	 * 根据线路id获取线路起始站点和结束站点的名称
	 * 
	 * @param lineBaseId
	 * @return
	 */
	public String getStartAndEndname(String lineBaseId) {
		String result = null;
		String sql = "select name from pb_station where lineId=? order by sortNum";
		Object[] params = new Object[1];
		params[0] = lineBaseId;
		List<StationInfo> stationInfoList = super.tableDao.queryList(StationInfo.class, sql, params);
		if (stationInfoList != null && !stationInfoList.isEmpty()) {
			String startname = stationInfoList.get(0).getName();
			String endname = stationInfoList.get(stationInfoList.size() - 1).getName();
			result = startname + "," + endname;
		}
		return result;
	}

	public ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId, String orderStartTime,
			String orderDate) {
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			LineEntity line = this.getLineById(lineBaseId);
			BigDecimal orderPrice = line.getOrderPrice();
			int discountRate = line.getDiscountRate();
			super.finit("line_class_info");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date date = null;
			try {
				date = sdf.parse(orderDate);
			} catch (Exception e) {
			}
			if (date != null) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(date);
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				String beginOrderDate = sdf2.format(ca.getTime());
				ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
				String endOrderDate = sdf2.format(ca.getTime());
				String sql = "select count(1) as totalDays from line_class_info a where a.delFlag=0 and a.lineBaseId=? and a.orderStartTime=? and a.orderDate between ? and ?";
				List<ClassMonthOrderPriceVo> cmoList = super.tableDao.queryList(ClassMonthOrderPriceVo.class, sql,
						new Object[] { lineBaseId, orderStartTime, beginOrderDate, endOrderDate });
				if (false == CollectionUtils.isEmpty(cmoList)) {
					if (cmoList.get(0).getTotalDays() > 0) {
						ClassMonthOrderPriceVo res = cmoList.get(0);
						boolean supportMonth = supportMonth(lineBaseId, orderStartTime, orderDate);
						float totalPrice = orderPrice.multiply(new BigDecimal(res.getTotalDays())).floatValue();
						res.setDiscountPrice(
								supportMonth ? String.format("%.2f", totalPrice * (float) discountRate / 100f)
										: String.valueOf(totalPrice));
						res.setDiscountRate(supportMonth ? String.valueOf(discountRate) : "100");
						res.setMonth(orderDate);
						res.setTotalPrice(String.valueOf(totalPrice));
						return res;
					}
				}
			}
		}
		return null;
	}

	private boolean supportMonth(String lineBaseId, String orderStartTime, String months) {
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(months)) {
			super.finit("line_passenger_month");
			String sql = "select monthId from line_passenger_month a where a.lineBaseId=? and a.classTime=? and a.months=? and a.delFlag=0";
			int count = super.tableDao.queryCount(sql, new Object[] { lineBaseId, orderStartTime, months });
			return count > 0;
		}
		return false;
	}

	private LineEntity getLineById(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_base_info");
			return super.tableDao.queryBeanById(LineEntity.class, lineBaseId, "lineBaseId");
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public int scheduleClassCarDriver(String lineBaseId, String orderStartTime, String[] orderDates, String[] driverIds,
			String[] vehicleIds) {
		int flag = -9999;
		Connection conn = null;
		try {
			conn = MyDataSource.getConnect();
			QueryRunner qr = new QueryRunner();
			if (conn == null) {
				throw new IllegalStateException("db connection is null.");
			}
			conn.setAutoCommit(false);
			// 查询线路的商户
			String lineBaseSQL = "select businessId,lineStatus from line_base_info where lineBaseId=?";
			List<LineEntity> lineList = (List<LineEntity>) qr.query(conn, lineBaseSQL, new Object[] { lineBaseId },
					new BeanListHandler(LineEntity.class));
			String businessId = CollectionUtils.isEmpty(lineList) ? null : lineList.get(0).getBusinessId();
			int lineStatus = CollectionUtils.isEmpty(lineList) ? 0 : lineList.get(0).getLineStatus();
			int len = orderDates.length;
			String updateClassSQL = "update line_class_info set driverId=?,vehicleId=? where delFlag=0 and lineBaseId=? and orderDate=? and orderStartTime=?";
			String updateStatTotalSQL = "update stat_total set driverId=?,vehicleId=?,businessId=? where lineBaseId=? and orderDate=? and orderStartTime=?";
			int count = 0;// 计数器
			for (int i = 0; i < len; i++) {
				// 先修改班次表，设置车辆和司机
				count += qr.update(conn, updateClassSQL,
						new Object[] { driverIds[i], vehicleIds[i], lineBaseId, orderDates[i], orderStartTime });
				// 再修改收入统计表，按线路，班次修改司机和车辆
				qr.update(conn, updateStatTotalSQL, new Object[] { driverIds[i], vehicleIds[i], businessId, lineBaseId,
						orderDates[i], orderStartTime });
			}
			if (count == len) {
				// 修改线路状态
				if (lineStatus == 1) {
					String updateLineBaseStatusSQL = "update line_base_info set lineStatus=? where lineBaseId=?";
					qr.update(conn, updateLineBaseStatusSQL, new Object[] { 2, lineBaseId });
				}
			}
			conn.commit();
			flag = len;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (Exception e2) {
			}
		} finally {
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return flag;
	}
}
