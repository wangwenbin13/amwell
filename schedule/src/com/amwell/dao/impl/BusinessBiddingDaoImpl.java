package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.bc.BusinessBiddingForm;
import com.amwell.base.DaoSupport;
import com.amwell.commons.HttpRequestUtils;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.PropertyManage;
import com.amwell.commons.StringUtil;
import com.amwell.commons.SystemConstants;
import com.amwell.dao.IBusinessBiddingDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.bc.AlreadyQuoteLineVo;
import com.amwell.vo.bc.BcLineVo;
import com.amwell.vo.bc.BusinessCarInfo;
import com.amwell.vo.bc.BusinessOfferLineVo;
import com.amwell.vo.bc.BusinessOrderVo;
import com.amwell.vo.bc.BusinessSchedule;
import com.amwell.vo.bc.BusinessScheduleVo;
import com.amwell.vo.bc.OrderStatusEnum;

@Repository("businessBiddingDao")
public class BusinessBiddingDaoImpl extends DaoSupport implements IBusinessBiddingDao {

	private static final Logger log = Logger.getLogger(BusinessBiddingDaoImpl.class);

	public Map<String, Object> getWaitQuoteList(Search search, int currentPageIndex, int pageSize) {
		if (search == null) {
			throw new NullPointerException("search can't be null.");
		}
		// 商户ID不能为空
		if (false == StringUtils.hasText(search.getField01())) {
			throw new IllegalArgumentException("businessId can't be null.");
		}
		Map<String, Object> res = new HashMap<String, Object>();
		super.finit("bc_business_bidding a inner join bc_line b on a.bc_line_id=b.bc_line_id");
		// 商家报价状态为待报价，且报价时间未超过报价过期时间,且不存在线路支付订单
		StringBuffer sql = new StringBuffer(
				"select b.bc_line_id,a.businessId,b.createOn,b.businessType,b.startTime,b.returnTime,b.beginAddress,b.endAddress,b.totalPassengers,b.totalCars,b.charteredMode,b.lineStatus,a.offerStatus,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AS nowTime,b.expireTime,b.needInvoice from bc_business_bidding a inner join bc_line b on a.bc_line_id=b.bc_line_id where b.lineStatus <> 5 and a.offerStatus=0 and a.businessId=? and CURRENT_TIMESTAMP() BETWEEN b.createOn and b.expireTime and NOT EXISTS (select 1 from bc_order where bc_line_id=b.bc_line_id)");
		if (StringUtils.hasText(search.getField11())) {
			sql.append(" " + search.getField11());
		}
		Object[] params = new Object[] { search.getField01().trim() };
		List<BusinessOfferLineVo> list = super.tableDao.queryByPage(BusinessOfferLineVo.class, sql.toString(),
				currentPageIndex, pageSize, params);
		Page page = new Page(list, sql.toString(), currentPageIndex, pageSize, params);
		res.put("page", page);
		res.put("list", list);
		return res;
	}

	public BcLineVo getWaitQuoteDetail(String businessId, String bc_line_id) {
		if (false == StringUtils.hasText(businessId)) {
			throw new NullPointerException("businessId can't be null.");
		}
		if (false == StringUtils.hasText(bc_line_id)) {
			throw new NullPointerException("bc_line_id can't be null.");
		}
		super.finit("bc_business_bidding a inner join bc_line b on a.bc_line_id=b.bc_line_id");
		// 商家报价状态为待报价，且报价时间未超过报价过期时间
		StringBuffer sql = new StringBuffer(
				"select a.id as businessBiddingId,b.bc_line_id,a.businessId,b.createOn,b.businessType,b.expectPrice,b.startTime,b.returnTime,b.carAge,b.beginAddress,b.endAddress,b.totalPassengers,b.totalCars,b.charteredMode,b.journeyRemark,b.lineStatus,a.offerStatus,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') AS nowTime,b.expireTime,b.needInvoice,b.invoiceHeader from bc_business_bidding a inner join bc_line b on a.bc_line_id=b.bc_line_id where a.offerStatus=0 and a.businessId=? and b.bc_line_id=? and NOW() BETWEEN b.createOn and b.expireTime");
		return super.tableDao.queryBean(BcLineVo.class, sql.toString(),
				new Object[] { businessId.trim(), bc_line_id.trim() });
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public int addBusinessBidding(BusinessBiddingForm biddingForm) {
		int flag = -1;
		Connection conn = null;
		try {
			conn = MyDataSource.getConnect();
			if (null == conn) {
				throw new IllegalArgumentException("conn can't be null.");
			}
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			// 查询报价是否过期
			String sql = "select count(b.id) from bc_line a,bc_business_bidding b where a.bc_line_id=b.bc_line_id and b.offerStatus=0 and NOW()<a.expireTime and b.bc_line_id=? and b.businessId=?";
			Object obj = qr.query(conn, sql, new Object[] { biddingForm.getBc_line_id(), biddingForm.getBusinessId() },
					new ResultSetHandler() {
						public Object handle(ResultSet rs) throws SQLException {
							rs.next();
							return rs.getObject(1);
						}
					});
			Long count = null;
			if (null != obj && obj instanceof Long) {
				count = (Long) obj;
			}
			if (count == null || count.intValue() == 0) {
				// 过期标志位
				return -1;
			}
			String ruleId = StringUtil.generateSequenceNo();
			// 先插入退票规则表
			sql = "insert into bc_return_rule(ruleId,noReturn,returnOneTime,returnOnePer,returnTwoTime,returnTwoPer) values(?,?,?,?,?,?)";
			flag = qr.update(conn, sql,
					new Object[] { ruleId, biddingForm.getNoReturn(), biddingForm.getReturnOneTime(),
							biddingForm.getReturnOnePer(), biddingForm.getReturnTwoTime(),
							biddingForm.getReturnTwoPer() });
			// 再更新报价表
			sql = "update bc_business_bidding set basicCost=?,driverMealCost=?,driverHotelCost=?,oilCost=?,parkingFee=?,tolls=?,driverWorkHour=?,overtimeCost=?,limitMileage=?,overmileageCost=?,totalCost=?,remark=?,additionalServices=?,offerEndTime=?,offerTime=NOW(),offerStatus=1,returnRuleId=? where id=?";
			List<Object> paramList = new ArrayList<Object>();
			String basicCost = (String) (StringUtils.hasText(biddingForm.getBasicCost()) ? biddingForm.getBasicCost()
					: "0.00");
			String driverMealCost = (String) (StringUtils.hasText(biddingForm.getDriverMealCost())
					? biddingForm.getDriverMealCost() : "0.00");
			String driverHotelCost = (String) (StringUtils.hasText(biddingForm.getDriverHotelCost())
					? biddingForm.getDriverHotelCost() : "0.00");
			String oilCost = (String) (StringUtils.hasText(biddingForm.getOilCost()) ? biddingForm.getOilCost()
					: "0.00");
			paramList.add(new BigDecimal(basicCost));
			paramList.add(new BigDecimal(driverMealCost));
			paramList.add(new BigDecimal(driverHotelCost));
			paramList.add(new BigDecimal(oilCost));
			paramList.add(new BigDecimal("0.00"));
			paramList.add(new BigDecimal("0.00"));
			paramList.add(StringUtils.hasText(biddingForm.getDriverWorkHour())
					? new BigDecimal(biddingForm.getDriverWorkHour()) : new BigDecimal("0.00"));
			paramList.add(StringUtils.hasText(biddingForm.getOvertimeCost())
					? new BigDecimal(biddingForm.getOvertimeCost()) : new BigDecimal("0.00"));
			paramList.add(StringUtils.hasText(biddingForm.getLimitMileage())
					? new BigDecimal(biddingForm.getLimitMileage()) : new BigDecimal("0.00"));
			paramList.add(StringUtils.hasText(biddingForm.getOvermileageCost())
					? new BigDecimal(biddingForm.getOvermileageCost()) : new BigDecimal("0.00"));

			BigDecimal totalCost = new BigDecimal(basicCost).add(new BigDecimal(driverMealCost))
					.add(new BigDecimal(driverHotelCost)).add(new BigDecimal(oilCost));
			paramList.add(totalCost);
			paramList.add(biddingForm.getRemark());
			paramList.add(biddingForm.getAdditionalServices());
			// 竞价过期时间等于线路上的过期时间
			paramList.add(biddingForm.getExpireTime());
			paramList.add(ruleId);
			paramList.add(biddingForm.getBusinessBiddingId());
			flag = qr.update(conn, sql, paramList.toArray(new Object[] {}));
			// 再插入车辆信息
			if (ArrayUtils.isNotEmpty(biddingForm.getCarType()) && ArrayUtils.isNotEmpty(biddingForm.getCarSeats())
					&& ArrayUtils.isNotEmpty(biddingForm.getCarAge())
					&& ArrayUtils.isNotEmpty(biddingForm.getTotalCars())) {
				if (biddingForm.getCarType().length == biddingForm.getCarSeats().length
						&& biddingForm.getCarSeats().length == biddingForm.getCarAge().length
						&& biddingForm.getCarAge().length == biddingForm.getTotalCars().length) {
					for (int i = 0; i < biddingForm.getCarType().length; i++) {
						sql = "insert into bc_business_car(info_id,bidding_id,carType,carSeats,carAge,totalCars,createBy,createOn,orderNum) values(?,?,?,?,?,?,?,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'),?)";
						qr.update(conn, sql,
								new Object[] { StringUtil.generateSequenceNo(), biddingForm.getBusinessBiddingId(),
										biddingForm.getCarType()[i], biddingForm.getCarSeats()[i],
										biddingForm.getCarAge()[i], biddingForm.getTotalCars()[i],
										biddingForm.getBusinessId(), i + 1 });
					}
				}
			}

			// 更新包车线路状态
			String upLineSql = "update bc_line set lineStatus = ? where bc_line_id = ? and lineStatus = ?";
			qr.update(conn, upLineSql, new Object[] { 3, biddingForm.getBc_line_id(), 2 });
			// 发送站内信
			// 查询线路站点名称,发布时间

			sql = "select a.beginAddress,a.endAddress,a.startTime,a.createOn,a.passengerId,b.telephone,a.sourceFrom from bc_line a,passenger_info b  where a.passengerId=b.passengerId and a.bc_line_id=?";
			BcLineVo bcLineVo = qr.query(conn, sql, new Object[] { biddingForm.getBc_line_id() },
					new ResultSetHandler<BcLineVo>() {
						public BcLineVo handle(ResultSet rs) throws SQLException {
							if (rs.next()) {
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
			if (bcLineVo != null) {
				// 来源 0:APP 1:微信
				if (bcLineVo.getSourceFrom() == 0) {
					// 发送APP站内信
					if (bcLineVo != null && StringUtils.hasText(bcLineVo.getBeginAddress())
							&& StringUtils.hasText(bcLineVo.getEndAddress())
							&& StringUtils.hasText(bcLineVo.getCreateOn())
							&& StringUtils.hasText(bcLineVo.getStartTime())) {
						String msgId = StringUtil.generateSequenceNo();
						String beginAddress = bcLineVo.getBeginAddress().length() > 5
								? bcLineVo.getBeginAddress().substring(0, 5) : bcLineVo.getBeginAddress();
						String endAddress = bcLineVo.getEndAddress().length() > 5
								? bcLineVo.getEndAddress().substring(0, 5) : bcLineVo.getEndAddress();
						sql = "insert into sys_msg_info(msgId,msgType,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,issend) values(?,?,?,?,?,?,?,?,?)";
						String msgTitle = "包车(" + beginAddress + "-" + endAddress + "线路 您有新的报价)";
						String msgContext = "尊敬的用户您好，您于" + bcLineVo.getCreateOn() + "所发布的发车时间为："
								+ bcLineVo.getStartTime() + ",收到一条新的报价信息。请前往个人中心－包车订单查看详情！"
								+ SystemConstants.COMPANY_INFO;
						qr.update(conn, sql, new Object[] { msgId, 1, 0, msgTitle, msgContext, 0, "SYSTEM",
								MyDate.getMyDateLong(), "0" });
						sql = "insert into sys_msg_user(localId,userId,msgId,readFlag,sendPhoneNo,sendTime) values(?,?,?,?,?,?)";
						qr.update(conn, sql, new Object[] { StringUtil.generateSequenceNo(), bcLineVo.getPassengerId(),
								msgId, 0, bcLineVo.getTelephone(), MyDate.getMyDateLong() });
					}
				}
				if (bcLineVo.getSourceFrom() == 1) {
					// 发送微信推送消息
					String requestUrl = PropertyManage.getInfoProperty("weixin.push.message.url");
					if (StringUtils.hasText(requestUrl)) {
						requestUrl += "?passengerId=" + bcLineVo.getPassengerId() + "&bcLineId="
								+ biddingForm.getBc_line_id() + "&msgType=2";
						HttpRequestUtils.sendGetRequest(requestUrl, "utf-8");
					} else {
						log.error("property weixin.push.message.url can't set value.");
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			flag = -9999;
			e.printStackTrace();
			if (null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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

	/**
	 * 获取包车管理已报价和已过期
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getAlreadyQuoteList(Search search, int currentPageIndex, int pageSize) {
		if (search == null) {
			throw new NullPointerException("search can't be null.");
		}
		// 商户ID不能为空
		if (false == StringUtils.hasText(search.getField01())) {
			throw new IllegalArgumentException("businessId can't be null.");
		}
		Map<String, Object> res = new HashMap<String, Object>();
		super.finit("bc_business_bidding a inner join bc_line b on a.bc_line_id=b.bc_line_id");
		// 商家报价状态为已报价，且报价时间未超过报价过期时间
		StringBuffer sql = new StringBuffer(
				"select a.id,b.bc_line_id,a.businessId,b.createOn,b.businessType,b.startTime,b.returnTime,b.beginAddress,b.endAddress,b.totalPassengers,b.totalCars,b.charteredMode,b.lineStatus,a.offerStatus,b.expireTime,b.needInvoice,a.totalCost from bc_business_bidding a inner join bc_line b on a.bc_line_id=b.bc_line_id where a.businessId=? and a.offerStatus=1 and NOW()<b.expireTime");
		Object[] params = null;
		List<Object> pList = new ArrayList<Object>();
		pList.add(search.getField01());
		// 业务类型
		if (StringUtils.hasText(search.getField02())) {
			sql.append(" and b.businessType = ? ");
			pList.add(search.getField02().trim());
		}
		// 排序字段
		sql.append(" order by a.createOn desc");
		params = pList.toArray();
		List<AlreadyQuoteLineVo> list = super.tableDao.queryByPage(AlreadyQuoteLineVo.class, sql.toString(),
				currentPageIndex, pageSize, params);
		Page page = new Page(list, sql.toString(), currentPageIndex, pageSize, params);
		res.put("page", page);
		res.put("list", list);
		return res;
	}

	/**
	 * 获取包车管理已报价和已过期 详情
	 * 
	 * @param businessId
	 * @param 报价主键ID
	 * @return
	 */
	public AlreadyQuoteLineVo getAlreadytQuoteDetail(String businessId, String id) {
		if (false == StringUtils.hasText(businessId)) {
			throw new NullPointerException("businessId can't be null.");
		}
		if (false == StringUtils.hasText(id)) {
			throw new NullPointerException("di can't be null.");
		}
		super.finit(
				"bc_business_bidding bb INNER JOIN bc_line bl ON bb.bc_line_id = bl.bc_line_id INNER JOIN bc_return_rule br ON bb.returnRuleId = br.ruleId");
		// 商家报价状态为待报价，且报价时间未超过报价过期时间
		StringBuffer sql = new StringBuffer(
				"SELECT bb.id,bl.businessType,bl.totalPassengers,bl.totalCars,bl.createOn,bl.startTime,bl.returnTime,");
		sql.append(
				"bl.carAge,bl.beginAddress,bl.endAddress,bl.charteredMode,bl.journeyRemark,bl.needInvoice,bl.invoiceHeader,");
		sql.append(
				"bb.basicCost,bb.driverMealCost,bb.driverHotelCost,bb.oilCost,bb.totalCost,bb.driverWorkHour,bb.returnRuleId,");
		sql.append(
				"bb.overtimeCost,bb.limitMileage,bb.overmileageCost,br.noReturn,br.returnOneTime,br.returnOnePer,br.returnTwoTime,br.returnTwoPer,bb.returnRuleId,bb.additionalServices,bb.remark");
		sql.append(
				" FROM bc_business_bidding bb LEFT JOIN bc_line bl ON bb.bc_line_id = bl.bc_line_id LEFT JOIN bc_return_rule br ON bb.returnRuleId = br.ruleId");
		sql.append(" WHERE  bb.id = ? AND bb.businessId = ?");
		AlreadyQuoteLineVo vo = super.tableDao.queryBean(AlreadyQuoteLineVo.class, sql.toString(),
				new Object[] { id.trim(), businessId.trim() });
		if (null != vo) {
			// 查询对应的车辆信息
			super.finit("bc_business_car");
			String rSql = "SELECT * FROM bc_business_car bc WHERE bc.bidding_id = ?";
			List<BusinessCarInfo> carInfos = super.tableDao.queryList(BusinessCarInfo.class, rSql,
					new Object[] { id.trim() });
			if (false == CollectionUtils.isEmpty(carInfos)) {
				vo.setCarInfo(carInfos);
			}
		}
		return vo;
	}

	public Map<String, Object> getBCAllLineList(Search search, int currentPageIndex, int pageSize) {
		if (search == null) {
			throw new NullPointerException("search can't be null.");
		}
		if (false == StringUtils.hasText(search.getField01())) {
			throw new IllegalArgumentException("businessId can't be null.");
		}
		Map<String, Object> res = new HashMap<String, Object>();
		super.finit(
				"bc_order a INNER JOIN bc_line b ON a.bc_line_id = b.bc_line_id INNER JOIN passenger_info c ON b.passengerId = c.passengerId");
		String sql = "SELECT a.orderNo,a.payTime, a.orderStatus, a.businessId, a.bc_line_id, c.passengerId, c.telephone, b.businessType, b.startTime, b.returnTime, b.beginAddress, b.endAddress, b.totalPassengers, b.totalCars, b.contactPhone FROM bc_order a INNER JOIN bc_line b ON a.bc_line_id = b.bc_line_id INNER JOIN passenger_info c ON b.passengerId = c.passengerId WHERE a.businessId=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(search.getField01());
		if (StringUtils.hasText(search.getField02())) {
			// 业务类型
			sql += " and b.businessType=?";
			paramList.add(search.getField02().trim());
		}
		if (StringUtils.hasText(search.getField03())) {
			sql += " and b.startTime=?";
			paramList.add(search.getField03().trim());
		}
		if (StringUtils.hasText(search.getField04())) {
			sql += " and a.orderStatus=?";
			paramList.add(Integer.parseInt(search.getField04().trim()));
		}
		Object[] params = paramList.toArray(new Object[] {});
		List<BusinessOrderVo> list = super.tableDao.queryByPage(BusinessOrderVo.class, sql, currentPageIndex, pageSize,
				params);
		Page page = new Page(list, sql.toString(), currentPageIndex, pageSize, params);
		res.put("page", page);
		res.put("list", list);
		return res;
	}

	public BusinessScheduleVo getBusinessOrderDetail(String orderNo) {
		if (false == StringUtils.hasText(orderNo)) {
			throw new IllegalArgumentException("orderNo can't be null.");
		}
		super.finit(
				"bc_order a INNER JOIN bc_line b ON a.bc_line_id = b.bc_line_id INNER JOIN passenger_info c ON b.passengerId = c.passengerId");
		String sql = "SELECT a.orderNo,a.orderStatus,a.totalPrice,b.bc_line_id, b.businessType, b.charteredMode, b.startTime, b.returnTime, b.beginAddress, b.endAddress, b.carAge, b.totalPassengers, b.totalCars, b.needInvoice,b.invoiceHeader, b.journeyRemark, c.passengerId, c.displayId, c.nickName, c.telephone FROM bc_order a INNER JOIN bc_line b ON a.bc_line_id = b.bc_line_id INNER JOIN passenger_info c ON b.passengerId = c.passengerId WHERE a.orderNo =?";
		BusinessScheduleVo result = super.tableDao.queryBean(BusinessScheduleVo.class, sql, new Object[] { orderNo });
		super.finit("bc_business_schedule");
		sql = "SELECT * FROM bc_business_schedule WHERE orderNo=? ORDER BY orderNum;";
		result.setScheduleList(super.tableDao.queryList(BusinessSchedule.class, sql, new Object[] { orderNo }));
		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public int scheduleDriverAndCar(BusinessScheduleVo vo) {
		int flag = -1;
		if (null == vo) {
			throw new NullPointerException("vo can't be null.");
		}
		Connection conn = null;
		try {
			conn = MyDataSource.getConnect();
			if (null == conn) {
				throw new IllegalStateException("connection is null.");
			}
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String sql = "select count(a.bc_line_id) from bc_line a,bc_order b where a.bc_line_id=b.bc_line_id and b.orderNo=? and DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i')<a.startTime";
			// 调度之前先查看当前时间有没有超过线路的发车时间
			Object obj = qr.query(conn, sql, new Object[] { vo.getOrderNo() }, new ResultSetHandler() {
				public Object handle(ResultSet rs) throws SQLException {
					rs.next();
					return rs.getObject(1);
				}
			});
			Long count = null;
			if (null != obj && obj instanceof Long) {
				count = (Long) obj;
			}
			if (count == null || count.intValue() == 0) {
				// 已过期标志位
				return -1;
			}
			if (ArrayUtils.isNotEmpty(vo.getDriverName()) && ArrayUtils.isNotEmpty(vo.getDriverTelephone())
					&& ArrayUtils.isNotEmpty(vo.getCarNumber()) && ArrayUtils.isNotEmpty(vo.getCarSeats())) {
				if (vo.getDriverName().length == vo.getDriverTelephone().length
						&& vo.getDriverTelephone().length == vo.getCarNumber().length
						&& vo.getCarNumber().length == vo.getCarSeats().length) {
					// 如果原有调度信息存在，将原有调度信息删除
					sql = "delete from bc_business_schedule where orderNo = ?";
					qr.update(conn, sql, new Object[] { vo.getOrderNo() });
					// 先写调度表
					for (int i = 0; i < vo.getDriverName().length; i++) {
						sql = "insert into bc_business_schedule(schedule_id,orderNo,driverName,driverTelephone,carNumber,carSeats,orderNum,createBy,createOn) values(?,?,?,?,?,?,?,?,DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s'))";
						flag = qr.update(conn, sql,
								new Object[] { StringUtil.generateSequenceNo(), vo.getOrderNo(), vo.getDriverName()[i],
										vo.getDriverTelephone()[i], vo.getCarNumber()[i], vo.getCarSeats()[i], i + 1,
										vo.getCreateBy() });
					}
				}
			}
			if (flag > 0) {
				// 再修改订单状态
				sql = "update bc_order set orderStatus=? where orderNo=?";
				flag = qr.update(conn, sql,
						new Object[] { OrderStatusEnum.FINISH_SCHEDULE.getValue(), vo.getOrderNo() });
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			flag = -9999;
			if (null != conn) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
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
