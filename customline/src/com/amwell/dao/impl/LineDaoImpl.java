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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;
import com.amwell.commons.baiduAPI.BaiduAPIUtils;
import com.amwell.commons.baiduAPI.Point;
import com.amwell.dao.ILineDao;
import com.amwell.dao.IPublishLineDao;
import com.amwell.dto.LineClassDTO;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.ImGroupInfo;
import com.amwell.vo.LineAllowanceDetailVo;
import com.amwell.vo.LineAllowanceVo;
import com.amwell.vo.LineBusinessVo;
import com.amwell.vo.LineCarMsgVo;
import com.amwell.vo.LineCityVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassMonthOrderCountVo;
import com.amwell.vo.LineClassOrderPassengerVo;
import com.amwell.vo.LineClassVo;
import com.amwell.vo.LineDetailVo;
import com.amwell.vo.LineEnrollVo;
import com.amwell.vo.LineEntity;
import com.amwell.vo.LineOperateLogVo;
import com.amwell.vo.LinePassengerMonthEntity;
import com.amwell.vo.LineScheduleInfoVo;
import com.amwell.vo.LineTimeChange;
import com.amwell.vo.OrderMaxTicketCountVo;
import com.amwell.vo.OrderStartTimeVo;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.PassengerInfoVo;
import com.amwell.vo.PassengerStationVo;
import com.amwell.vo.RecruitLineForOuterContainer;
import com.amwell.vo.RecruitLineForOuterVo;
import com.amwell.vo.RecruitLineStationVo;
import com.amwell.vo.RecruitLineVo;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.UserLineCallbackVo;
import com.amwell.vo.UserLineEntity;
import com.amwell.vo.UserLineStationCount;
import com.amwell.vo.app.LineBaseInfo;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_3;
import com.amwell.vo.app.bean.AppVo_6;


@Repository("lineDao")
@SuppressWarnings( { "unchecked" })
public class LineDaoImpl extends DaoSupport implements ILineDao {
	
	@Autowired
	private IPublishLineDao publishLineDao;
	
	Logger logger = Logger.getLogger(LineDaoImpl.class);
	
	/**
	 * 根据关键词获取有经度的站点列表
	 * 
	 * @param keywords
	 *            关键词
	 * @return
	 */
	public List<StationInfo> loadStationByKeywords(String keywords) {
		List<StationInfo> stationList = null;
		if (keywords != null && !keywords.isEmpty()) {
			if(keywords.indexOf("'")>-1){//拼音里面包含“'”符号
				String[] s=keywords.split("'");
				String temp="";
				for (String string : s) {
					
					temp=temp+string;
				}
				keywords=temp;
			}
			Connection conn = MyDataSource.getConnect();
			try {
				QueryRunner qr = new QueryRunner();
				String sql = "select * from pb_station where baiduLng<>'' and baiduLng<>0 and baiduLat<>'' and baiduLat<>0 and name like '%"
						+ keywords + "%'";
				stationList = qr.query(conn, sql, new BeanListHandler<StationInfo>(StationInfo.class));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return stationList;
	}

	/** 修改发车时间 **/
	public String updateLineTime(LineTimeChange lineTimeChange) {
		super.finit("line_time_change");
		String sql = "update line_time_change set isfinish = '1' where linebaseid = ? ";
		args = new Object[1];
		args[0] = lineTimeChange.getLinebaseid();
		tableDao.executeSQL(sql, args);// 将原来的任务作废

		int flag = tableDao.updateData(lineTimeChange, "changeid");
		return String.valueOf(flag);
	}

	/** 获取所有购买过该路线的用户手机号码 **/
	public String getUserTelByLineBaseId(String lineBaseId) {
		super
				.finit("lease_base_info a LEFT JOIN passenger_info b ON a.passengerId = b.passengerId");
		String sql = "SELECT b.telephone AS a1 FROM lease_base_info a LEFT JOIN passenger_info b ON a.passengerId = b.passengerId WHERE a.ispay = '1' AND a.linebaseid = ? GROUP BY a.passengerid";
		args = new Object[1];
		args[0] = lineBaseId;
		List<AppVo_1> vo = tableDao.queryList(AppVo_1.class, sql, args);
		String tels = "";
		if (vo != null && vo.size() != 0) {
			for (int i = 0; i < vo.size(); i++) {
				tels += vo.get(i).getA1() + ",";
			}
		}
		return tels;
	}

	/** 修改发车时间任务计划 **/
	public void changeLineTimeByTask() {
		super.finit("line_time_change");
		String sql = "SELECT * FROM line_time_change WHERE isfinish = '0'";
		List<LineTimeChange> list = tableDao.queryList(LineTimeChange.class,
				sql);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				int temp = MyDate.compare_time_app(MyDate.getMyDateLong(), list
						.get(i).getWorktime()
						+ ":00");
				if (temp == 1) {
					String sql_u = "update line_class_info set orderStartTime = '"
							+ list.get(i).getLinetime()
							+ "' where lineBaseId = '"
							+ list.get(i).getLinebaseid() + "'";
					super.finit("line_class_info");
					tableDao.executeSQL(sql_u);
					super.finit("line_time_change");
					String sql_s = "update line_time_change set isfinish = '1' where changeid = '"
							+ list.get(i).getChangeid() + "'";
					tableDao.executeSQL(sql_s);
				}
			}
		}
	}

	/** 获取修改发车信息 **/
	public AppVo_6 getLineChangeInfo(String lineBaseId) {
		super.finit("line_time_change");
		String sql = "SELECT a.linetime as a1,a.worktime as a2,b.userName as a3 FROM line_time_change a LEFT JOIN sys_admin b ON a.createBy = b.userId WHERE a.isfinish = '0' and a.linebaseid = ?";
		args = new Object[1];
		args[0] = lineBaseId;
		AppVo_6 vo = tableDao.queryBean(AppVo_6.class, sql, args);
		return vo;
	}

	public Map<String, Object> getUserLines(Search search,
			int currentPageIndex, int pageSize) {
		long timeS = new Date().getTime();
		super.finit("line_user_application");
		String listSql="select f_changeIdsToValues_application(appliStations) as stationNames,";
		String pageSql="select ";
		StringBuffer sql = new StringBuffer(
				" a.cityName,a.applicationId,a.applicationTime,b.passengerId,b.nickName,b.telephone,a.lineType,a.startAddress,a.endAddress,a.startTime,a.remark,a.callbackStatus,a.auditStatus,(select count(eu.eid) from line_enroll_user eu where eu.applicationId=a.applicationId) as applyCount from line_user_application a inner join passenger_info b on a.passengerId=b.passengerId where 1=1");
		List<Object> paramList = new ArrayList<Object>();
		if (null != search) {
			if (StringUtils.hasText(search.getField01())) {
				if (StringUtils.hasText(search.getField02())) {
					sql.append(" and a.applicationTime between ? and ?");
					paramList.add(search.getField01().trim());
					paramList.add(search.getField02().trim());
				} else {
					sql.append(" and a.applicationTime > ?");
					paramList.add(search.getField01().trim());
				}
			} else {
				if (StringUtils.hasText(search.getField02())) {
					sql.append(" and a.applicationTime < ?");
					paramList.add(search.getField02().trim());
				}
			}
			if (StringUtils.hasText(search.getField03())) {
				// 需要反查乘客
				sql.append(" and b.nickName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03()
						.trim()));
			}
			if (StringUtils.hasText(search.getField04())) {
				sql.append(" and b.telephone like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04()
						.trim()));
			}
			if (StringUtils.hasText(search.getField05())) {
				sql.append(" and a.lineType=?");
				paramList.add(search.getField05().trim());
			}
			if (StringUtils.hasText(search.getField06())) {
				sql.append(" and a.startAddress like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06()
						.trim()));
			}
			if (StringUtils.hasText(search.getField07())) {
				sql.append(" and a.endAddress like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField07()
						.trim()));
			}
			if (StringUtils.hasText(search.getField08())) {
				sql.append(" and a.startTime like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField08()
						.trim()));
			}
			if (StringUtils.hasText(search.getField09())) {
				// 回访状态
				sql.append(" and a.callbackStatus=?");
				paramList.add(search.getField09().trim());
			}
			if (StringUtils.hasText(search.getField12())) {
				// 城市名称
				String cityName=search.getField12();
				cityName="%"+cityName.substring(0,cityName.indexOf("市"))+"%";
				sql.append(" and a.cityName like ?");
				paramList.add(cityName.trim());
			}
		}
		sql.append(" order by a.applicationTime desc");
		Object[] params = paramList.toArray(new Object[] {});
		List<UserLineEntity> list = super.tableDao.queryByPage(
				UserLineEntity.class, listSql+sql.toString(), currentPageIndex,
				pageSize, params);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String[] stationNames = {};
				String viaPoint = null; // 途经点
				if (StringUtils.hasText(list.get(i).getStationNames())) {
					stationNames = list.get(i).getStationNames().split("-");
					list.get(i).setStartAddress(stationNames[0]); // 上车点
					list.get(i).setEndAddress(
							stationNames[stationNames.length - 1]);// 下车点
					/** 拼接途经点 **/
					for (int j = 0; j < stationNames.length; j++) {
						if (j != 0 && j != (stationNames.length - 1)) {
							if (null == viaPoint) {
								viaPoint = stationNames[j];
							} else {
								viaPoint = viaPoint + "-" + stationNames[j];
							}
						}
					}
					list.get(i).setStationNames(viaPoint);
				}
			}
		}
		Map<String, Object> res = new HashMap<String, Object>();
		Page page = new Page(list, pageSql+sql.toString(), currentPageIndex, pageSize,params);
		res.put("page", page);
		res.put("list", list);
		return res;
	}
	
	/**
	 * 新增站点信息的时候同步线路轨迹表
	 * 
	 * @param lineBaseId 线路Id
	 * @param stationList 站点列表
	 * @param newStationIdList 站点id列表
	 */
	public void syncLineTrack(String lineBaseId, List<StationInfo> stationList, List<String> newStationIdList) {
		if (newStationIdList == null || newStationIdList.isEmpty()) {
			return;
		}
		if (com.amwell.utils.StringUtils.isEmpty(lineBaseId)) {
			return;
		}
		if (stationList == null || stationList.isEmpty()) {
			return;
		}
		// 把新增的站点的站点id赋值给站点对象
		for (int index = 0; index < newStationIdList.size(); index++) {
			stationList.get(index).setId(newStationIdList.get(index));
		}
		Connection conn = MyDataSource.getConnect();
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			// 删除轨迹表中该线路的所有站点记录
			String sql = "delete from line_track_map where isStation='1' and lineBaseId="
					+ lineBaseId;
			qr.update(conn, sql);
			// 把该线路的新的站点插入轨迹表
			for (int index = 0; index < stationList.size(); index++) {
				StationInfo stationName = stationList.get(index);
				sql = "insert into line_track_map(lineTrackId,lineBaseId,stationInfoId,lon,lat,lon_gps,lat_gps,sort,isStation) values(?,?,?,?,?,?,?,?,?)";
				List<String> paramList = new ArrayList<String>();
				paramList.add(StringUtil.generateSequenceNo());
				paramList.add(lineBaseId);
				paramList.add(stationName.getId());
				paramList.add(String.valueOf(stationName.getBaiduLng()));
				paramList.add(String.valueOf(stationName.getBaiduLat()));
				Point point = BaiduAPIUtils.convertToGPS(stationName.getBaiduLng(), stationName.getBaiduLat());
				if (point != null) {
					paramList.add(String.valueOf(point.getLng()));
					paramList.add(String.valueOf(point.getLat()));
				} else {
					paramList.add("");
					paramList.add("");
				}
				paramList.add("1");
				paramList.add("1");
				qr.update(conn, sql, paramList.toArray());
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteTrack(Connection conn, QueryRunner qr, String lineBaseId,
			String stationInfoId) throws SQLException {
		String sql = null;
		if (lineBaseId != null) {
			sql = "delete from line_track_map where lineBaseId=" + lineBaseId
					+ " and stationInfoId=" + stationInfoId
					+ " and isStation=0";
		} else {
			sql = "delete from line_track_map where " + "stationInfoId="
					+ stationInfoId + " and isStation=0";
		}
		qr.update(conn, sql);
	}

	public void deleteTrackAndStation(Connection conn, QueryRunner qr,
			String lineBaseId, String stationInfoId) throws SQLException {
		String sql = null;
		if (lineBaseId != null) {
			sql = "delete from line_track_map where lineBaseId=" + lineBaseId
					+ " and stationInfoId=" + stationInfoId;
		} else {
			sql = "delete from line_track_map where stationInfoId="
					+ stationInfoId;
		}
		qr.update(conn, sql);
	}

	/**
	 * 保存线路信息，返回线路主健
	 * 
	 * @param line
	 * @return
	 */
	public String addLine(LineEntity line) {
		if (line != null) {
			super.finit("line_base_info");
			super.tableDao.updateData(line, "lineBaseId");
			return line.getLineBaseId();
		}
		return null;
	}

	/**
	 * 保存线路班次信息
	 * 
	 * @param lineClassList
	 * @return
	 */
	public List<String> addLineClassList(List<LineClassEntity> lineClassList) {
		if (false == CollectionUtils.isEmpty(lineClassList)) {
			super.finit("line_class_info");
			List<String> lineClassIds = new ArrayList<String>(lineClassList
					.size());
			for (LineClassEntity c : lineClassList) {
				super.tableDao.updateData(c, "lineClassId");
				lineClassIds.add(c.getLineClassId());
			}
			return lineClassIds;
		}
		return new ArrayList<String>(0);
	}

	public void syncAllLineTrackMap() {
		Map<String, Object> map = getAllLines(null, 0, 100000);
		List<LineBusinessVo> lineList = (List<LineBusinessVo>) map.get("list");
		for (LineBusinessVo lineBusinessVo : lineList) {
			String lineBaseId = lineBusinessVo.getLineBaseId();
			LineDetailVo lineDetail = getLineDetailByIdForSyncTrack(lineBaseId);
			List<StationInfo> stationList = lineDetail.getStationList();
			List<String> stationIdList = new ArrayList<String>();
			for(StationInfo stationInfo : stationList){
				stationIdList.add(stationInfo.getId());
			}
			// 同步线路轨迹表
			syncLineTrack(lineBaseId, stationList, stationIdList);
		}
	}

	/**
	 * 查询所有非招募线路
	 * 
	 * @return
	 */
	public Map<String, Object> getAllLines(Search search, int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		String today = MyDate.formatTime(System.currentTimeMillis(),"yyyy-MM-dd");
		String listSql = "select a.lineBaseId,a.provinceCode,a.cityName,b.businessId,";
		String pageSql = "select a.lineBaseId,a.provinceCode,";
		StringBuffer sql = new StringBuffer(
				"b.brefName,a.lineType,a.lineSubType,a.lineName,a.orderPrice,a.updateOn,a.lineManager,a.lineStatus,(select count(*) from line_class_info where delFlag=0 and lineBaseId=a.lineBaseId and orderDate>='"
						+ today
						+ "') as availableBuyDays ,(SELECT g.orderStartTime FROM line_class_info g WHERE g.linebaseid = a.`lineBaseId` AND g.delflag = '0' LIMIT 1) AS stime"
						+ ",(SELECT COUNT(*) FROM line_time_change WHERE  linebaseid = a.lineBaseId AND isfinish ='0') AS ischange"
						+ " from line_base_info a left join mgr_business b on a.businessId = b.businessId inner join sys_admin c on a.createBy = c.userId where 1=1 and a.lineType=1 and c.sysType=0 and a.lineStatus!=5");
		List<Object> paramList = new ArrayList<Object>();
		if (null != search) {
			if (StringUtils.hasText(search.getField01())) {
				sql.append(" and a.lineName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
			}
			if (StringUtils.hasText(search.getField02())) {
				// 如果输入有站点名称
				StringBuffer stationSql = new StringBuffer("select lineId from pb_station where 1=1 and name like ?");
				List<StationInfo> stationList = super.tableDao.queryList(StationInfo.class, stationSql.toString(),new Object[] { SqlBuilder.getSqlLikeValue(search.getField02().trim()) });
				logger.info("stationList="+stationList);
				if (false == CollectionUtils.isEmpty(stationList)) {
					sql.append(" and (");
					int index = 0;
					for (StationInfo stationInfo : stationList) {
						if (index != 0) {
							sql.append(" or ");
						}
						sql.append("a.lineBaseId = ?");
						paramList.add(stationInfo.getLineId());
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
////////////下车点查询		
			if (StringUtils.hasText(search.getField08())) {
				// 如果输入有站点名称
				StringBuffer stationSql = new StringBuffer("select lineId from pb_station where 1=1 and name like ?");
				List<StationInfo> stationList = super.tableDao.queryList(StationInfo.class, stationSql.toString(),new Object[] { SqlBuilder.getSqlLikeValue(search.getField08().trim()) });
				if (false == CollectionUtils.isEmpty(stationList)) {
					sql.append(" and (");
					int index = 0;
					for (StationInfo stationInfo : stationList) {
						if (index != 0) {
							sql.append(" or ");
						}
						sql.append("a.lineBaseId = ?");
						paramList.add(stationInfo.getLineId());
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
				sql.append(" and b.brefName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if (StringUtils.hasText(search.getField04())) {
				sql.append(" and a.lineSubType=?");
				paramList.add(Integer.parseInt(search.getField04().trim()));
			}
			if (StringUtils.hasText(search.getField05())) {
				sql.append(" and a.lineStatus=?");
				paramList.add(Integer.parseInt(search.getField05().trim()));
			}
			if (StringUtils.hasText(search.getField11())) {
				sql.append(" and a.provinceCode=?");
				paramList.add(Integer.parseInt(search.getField11().trim()));
			}
			if (StringUtils.hasText(search.getField12())) {
				sql.append(" and a.cityCode=?");
				paramList.add(Integer.parseInt(search.getField12().trim()));
			}
			if(StringUtils.hasText(search.getField13())){
				String nowTime = MyDate.getMyDate3();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = MyDate.strToDate(nowTime);
				long overDay = (d.getTime()/1000) - 30*24*60*60;
				d.setTime(overDay*1000);
				String overDay1 = sdf.format(d);
				sql.append(" AND a.`lineBaseId` IN (SELECT DISTINCT(lineBaseId) FROM `line_class_info` AS lc LEFT JOIN vehicle_info AS vi ON lc.`vehicleId`=vi.`vehicleId` WHERE vi.`vehicleNumber` LIKE ? AND lc.`orderDate` <= ? AND lc.`orderDate` >= ? ) ");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField13().trim()));
				paramList.add(nowTime);
				paramList.add(overDay1);
			}
			if(StringUtils.hasText(search.getField15())){
				sql.append(" and (a.lineManager=? )");
				paramList.add(search.getField15());
			}

		}else{
			sql.append(" and a.lineStatus = '3'");
		}
		sql.append(" order by");
		if (null != search && StringUtils.hasText(search.getField09())) {
			sql.append(" a.orderPrice ").append(search.getField09()).append(",");
		}
		if (null != search && StringUtils.hasText(search.getField06())) {
			if (search.getField06().equals("0")) {
				sql.append(" a.createOn desc  ");
			} else if (search.getField06().equals("1")) {
				sql.append("  a.orderPrice  desc  ");
			} else if (search.getField06().equals("2")) {
				sql.append(" a.updateOn desc  ");
			} else if (search.getField06().equals("3")) {
				sql.append(" a.lineStatus asc ");
			} else if (search.getField06().equals("4")) {
				sql.append(" availableBuyDays asc");
			}
		} else {
			sql.append(" a.lineStatus asc, a.displayFlag desc,availableBuyDays asc");
		}
		super.finit("line_base_info a left join mgr_business b on a.businessId = b.businessId inner join sys_admin c on a.createBy = c.userId");
		Object[] params = paramList.toArray(new Object[] {});
	    logger.info("listSql="+listSql);
		List<LineBusinessVo> list = super.tableDao.queryByPage(LineBusinessVo.class, listSql+sql.toString(), currentPageIndex,pageSize, params);
		// 需要重新装list,取出起点，终点名称
		if (false == CollectionUtils.isEmpty(list)) {
			for (LineBusinessVo v : list) {
				//归属人查询
				if(v.getLineManager()!=null && !"".equals(v.getLineManager())){
					String lineManSQL="select * from sys_admin where userId=?";
					SysAdminVo adminVo = super.tableDao.queryBean(SysAdminVo.class, lineManSQL, new Object[]{v.getLineManager()});
					v.setUserName(adminVo.getUserName());
				}else{
					v.setUserName("--");
				}
				
				// 根据线路id获取班次信息
				String sql_t = " SELECT orderStartTime AS a1,orderSeats AS a2,lineBaseId AS a3 FROM line_class_info WHERE delFlag = '0' and lineBaseId=? order by orderDate desc limit 0,1";
				Object[] classParams = new Object[1];
				classParams[0] = v.getLineBaseId();
				AppVo_3 vo3 = tableDao.queryBean(AppVo_3.class, sql_t, classParams);
				if(vo3!=null){
					v.setOrderStartTime(vo3.getA1());
					v.setOrderSeat(vo3.getA2());
				}
				List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(v.getLineBaseId());
				if(stationInfoList!=null&&stationInfoList.size()>=2){
					v.setStartStationName(stationInfoList.get(0).getName());
					v.setEndStationName(stationInfoList.get(stationInfoList.size()-1).getName());
				}
				//获取上下车点集合
				List<String> upList = new ArrayList<String>();
				List<String> downList = new ArrayList<String>();
				if(stationInfoList!=null){
					for(StationInfo stationVo : stationInfoList){
						if(stationVo.getType()==0){
							downList.add(stationVo.getName());
						}else if(stationVo.getType()==1){
							upList.add(stationVo.getName());
						}
					}
				}
				v.setUpStation(upList);
				v.setDownStation(downList);
			}
		}
		Page page = new Page(list, pageSql+sql.toString(), currentPageIndex, pageSize,params);
		res.put("page", page);
		res.put("list", list);
		return res;
	}

	/**
	 * 获取招募线路列表
	 * 
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getRecruitLines(Search search,
			int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(
				"select a.lineBaseId,a.cityName,a.lineSubType,a.lineName,a.orderPrice,a.updateOn, (select count(1) from line_enroll where lineBaseId=a.lineBaseId)as applyTotal,b.userName,a.lineStatus from line_base_info a inner join sys_admin b on a.createBy = b.userId where 1=1 and a.lineType=0 and (a.lineStatus=1 or a.lineStatus=2)");
		List<Object> paramList = new ArrayList<Object>();
		if (null != search) {
			if (StringUtils.hasText(search.getField01())) {
				sql.append(" and a.lineName like ?");
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField01()
						.trim()));
			}
			if (StringUtils.hasText(search.getField02())) {
				// 如果输入有站点名称
				StringBuffer stationSql = new StringBuffer(
						"select lineId from pb_station name like ?");
				List<StationInfo> stationList = super.tableDao.queryList(
						StationInfo.class, stationSql.toString(),
						new Object[] { SqlBuilder.getSqlLikeValue(search
								.getField02().trim()) });
				if (false == CollectionUtils.isEmpty(stationList)) {
					sql.append(" and (");
					int index = 0;
					for (StationInfo stationInfo : stationList) {
						if (index != 0) {
							sql.append(" or ");
						}
						sql.append("a.lineBaseId = ?");
						paramList.add(stationInfo.getLineId());
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
			if (StringUtils.hasText(search.getField03())) {
				sql.append(" and a.lineStatus=?");
				paramList.add(Integer.parseInt(search.getField03().trim()));
			}
			if (StringUtils.hasText(search.getField11())) {
				sql.append(" and a.provinceCode=?");
				paramList.add(Integer.parseInt(search.getField11().trim()));
			}
			if (StringUtils.hasText(search.getField12())) {
				sql.append(" and a.cityCode=?");
				paramList.add(Integer.parseInt(search.getField12().trim()));
			}
		}
		sql.append(" order by a.displayFlag desc,a.lineStatus asc,a.updateOn desc");
		super.finit("line_base_info a inner join sys_admin b on a.createBy = b.userId");
		Object[] params = paramList.toArray(new Object[] {});
		// MySQL的分页时,limit从0开始，底层方法为记录数下标
		List<RecruitLineVo> list = super.tableDao.queryByPage(
				RecruitLineVo.class, sql.toString(), currentPageIndex,
				pageSize, params);
		// 需要重新装list,取出起点，终点名称
		if (false == CollectionUtils.isEmpty(list)) {
			for (RecruitLineVo recruitLineVo : list) {
				List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(recruitLineVo.getLineBaseId());
				if (stationInfoList!=null) {
					recruitLineVo.setStartStationName(stationInfoList.get(0).getName());
					recruitLineVo.setEndStationName(stationInfoList.get(stationInfoList.size() - 1).getName());
				}
			}
		}
		Page page = new Page(list, sql.toString(), currentPageIndex, pageSize, params);
		res.put("page", page);
		res.put("list", list);
		return res;
	}

	public UserLineEntity getUserLineDetail(String applicationId) {
		if (StringUtils.hasText(applicationId)) {
			super.finit("line_user_application");
			StringBuffer sql = new StringBuffer(
					"select f_changeIdsToValues_application(a.appliStations) as stationNames,a.appliStations as stationIds,a.applicationId,a.applicationTime,b.passengerId,b.nickName,b.telephone,a.lineType,a.startAddress,a.endAddress,a.startTime,a.remark,a.callbackStatus from line_user_application a inner join passenger_info b on a.passengerId=b.passengerId where 1=1 and a.applicationId=?");
			UserLineEntity userLine = super.tableDao.queryBean(
					UserLineEntity.class, sql.toString(),
					new Object[] { applicationId });
			if(userLine!=null){
				String[] stationNames = {};
				String viaPoint = null;
				if (StringUtils.hasText(userLine.getStationNames())) {
					userLine.initStationList();
					stationNames = userLine.getStationNames().split("-");
					userLine.setStartAddress(stationNames[0]); // 上车点
					userLine.setEndAddress(stationNames[stationNames.length - 1]);// 下车点
					/** 拼接途经点 **/
					for (int j = 0; j < stationNames.length; j++) {
						if (j != 0 && j != (stationNames.length - 1)) {
							if (null == viaPoint) {
								viaPoint = stationNames[j];
							} else {
								viaPoint = viaPoint + "-" + stationNames[j];
							}
						}
					}
					userLine.setStationNames(viaPoint);
				}
				if (null != userLine && userLine.getCallbackStatus() == 1) {
					// 查询最后的回访内容
					super.finit("line_user_callback");
					sql = new StringBuffer(
							"select a.callbackContent,b.loginName as createBy,a.updateOn from line_user_callback a,sys_admin b where a.createBy=b.userId and a.applicationId=? order by a.updateOn desc");
					List<UserLineCallbackVo> callbackList = super.tableDao
							.queryList(UserLineCallbackVo.class, sql.toString(),
									new Object[] { applicationId });
					if (false == CollectionUtils.isEmpty(callbackList)) {
						// 取最后回复的内容
						userLine.setCallbackContent(callbackList.get(0).getCallbackContent());
						// 取回访人
						userLine.setCallbackLoginName(callbackList.get(0).getCreateBy());
						// 取回访时间
						userLine.setCallbackDateTime(callbackList.get(0).getUpdateOn());
					}
				}
				return userLine;
			}
		}
		return null;
	}

	public LineEntity getLineById(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_base_info");
			return super.tableDao.queryBeanById(LineEntity.class, lineBaseId,
					"lineBaseId");
		}
		return null;
	}

	public LineDetailVo getLineDetailByIdForSyncTrack(String lineBaseId) {
		LineDetailVo vo = new LineDetailVo();
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = getLineById(lineBaseId);
			vo.setLineBaseId(line.getLineBaseId());
			vo.setLineName(line.getLineName());
			vo.setProvinceCode(line.getProvinceCode());
			vo.setCityCode(line.getCityCode());
			vo.setStartArea(line.getStartArea());
			vo.setEndArea(line.getEndArea());
			vo.setLinePicUrl(line.getLinePicUrl());
			// 获取站点信息
			// 站点名称可以重复,in查询只能取到一条数据
			List<StationInfo> stationList = publishLineDao.listStationInfoByLineId(line.getLineBaseId());
			vo.setStationList(stationList);
			vo.setLineTime(line.getLineTime());
			vo.setLineKm(line.getLineKm());
			vo.setLineType(line.getLineType());
			vo.setLineSubType(line.getLineSubType());
			vo.setOriginalPrice(line.getOriginalPrice() == null ? "" : line.getOriginalPrice().toString());
			vo.setOrderPrice(line.getOrderPrice().toString());
			vo.setDiscountRate(line.getDiscountRate());
			vo.setRemark(line.getRemark());
		}
		return vo;
	}

	public int updateLine(LineEntity lineEntity) {
		if (null != lineEntity) {
			
			super.finit("lease_base_info");
			String sql_orderString = "update lease_base_info set payStatus = '2' where lineBaseId = '"+lineEntity.getLineBaseId()+"' and payStatus = '0'";
			tableDao.executeSQL(sql_orderString);
			super.finit("line_base_info");
			String sql = "update line_base_info set displayFlag = '0' where linebaseid <> '"+lineEntity.getLineBaseId()+"'";
			tableDao.executeSQL(sql);
			return super.tableDao.updateData(lineEntity, "lineBaseId");
		}
		return 0;
	}

	/**
	 * 修改班次座位数
	 * 
	 * @param line
	 * @return
	 */
	public int updateOrderSeats(String lineBaseId, String orderStartTime,
			int theNewSeats) {
		List<Object> conditions = new ArrayList<Object>();
		conditions.add(theNewSeats);
		conditions.add(lineBaseId);
		conditions.add(orderStartTime);
		String sql = "update line_class_info set orderSeats=? where delFlag=0 and lineBaseId=? and orderStartTime=?";
		return tableDao.executeSQL(sql, conditions.toArray());
	}

	public int updateLine(LineEntity line, List<String> delClassList,
			List<LineClassEntity> addClassList, List<String> delMonthList,
			List<LinePassengerMonthEntity> addMonthList,
			String delOrderStartTimes) {
		int flag = 0;
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		if (StringUtils.hasText(delOrderStartTimes)) {
			StringBuffer res = new StringBuffer();
			List<Object> pList = new ArrayList<Object>();
			pList.add(line.getLineBaseId());
			String[] arr = delOrderStartTimes.split(",");
			for (String s : arr) {
				res.append("?,");
				pList.add(s);
			}
			String addSql = res.substring(0, res.length() - 1);
			String sql = "update line_class_info set delFlag=1 where lineBaseId=? and orderStartTime in("
					+ addSql + ")";
			sqlList.add(sql);
			paramsList.add(pList.toArray());

			// 修改包月信息
			sql = "update line_passenger_month set delFlag=1 where lineBaseId=? and classTime in("
					+ addSql + ")";
			sqlList.add(sql);
			paramsList.add(pList.toArray());
		}
		if (false == CollectionUtils.isEmpty(delMonthList)) {
			// 删除包月信息
			Object[] selectParams = new Object[delMonthList.size()];
			StringBuffer buf = new StringBuffer();
			for (int j = 0; j < delMonthList.size(); j++) {
				buf.append("?,");
				selectParams[j] = delMonthList.get(j);
			}
			String addSql = buf.substring(0, buf.length() - 1);
			String sql = "update line_passenger_month set delFlag=1 where monthId in("
					+ addSql + ")";
			sqlList.add(sql);
			paramsList.add(selectParams);

		}

		if (addMonthList.size() > 0) {
			// 添加包月前，须判断原有数据
			String sql = "INSERT INTO line_passenger_month(monthId,classTime,months,lineBaseId,createBy,createOn) VALUES(?,?,?,?,?,?)";
			for (LinePassengerMonthEntity linePassengerMonth : addMonthList) {
				Object[] o = new Object[] { StringUtil.generateSequenceNo(),
						linePassengerMonth.getClassTime(),
						linePassengerMonth.getMonths(),
						linePassengerMonth.getLineBaseId(),
						linePassengerMonth.getCreateBy(),
						linePassengerMonth.getCreateOn() };
				sqlList.add(sql);
				paramsList.add(o);
			}
		}

		if (false == CollectionUtils.isEmpty(delClassList)) {
			// 删除班次,此处为逻辑删除，仅修改标志位
			Object[] selectParams = new Object[delClassList.size()];
			StringBuffer buf = new StringBuffer();
			for (int j = 0; j < delClassList.size(); j++) {
				buf.append("?,");
				selectParams[j] = delClassList.get(j);
			}
			String addSql = buf.substring(0, buf.length() - 1);
			// 删除班次前，还要判断有无下单
			String sql = "update line_class_info set delFlag=1 where lineClassId in("
					+ addSql + ")";
			sqlList.add(sql);
			paramsList.add(selectParams);
		}

		if (addClassList.size() > 0) {
			// 添加班次
			String sql = "INSERT INTO line_class_info(lineClassId,orderStartTime,orderSeats,orderDate,lineBaseId,driverId,vehicleId) VALUES(?,?,?,?,?,?,?)";
			for (LineClassEntity lineClassEntity : addClassList) {
				Object[] o = new Object[] { lineClassEntity.getLineClassId(),
						lineClassEntity.getOrderStartTime(),
						lineClassEntity.getOrderSeats(),
						lineClassEntity.getOrderDate(),
						lineClassEntity.getLineBaseId(),
						lineClassEntity.getDriverId(),      //司机ID
						lineClassEntity.getVehicleId()};    //车辆ID
				sqlList.add(sql);
				paramsList.add(o);
			}
		}

		// 修改线路信息
		String sql = "UPDATE line_base_info SET lineName = ? ,provinceCode=?,cityCode=?,cityName=?,startArea=?,endArea=?,linePicUrl=?,lineTime = ? ,originalPrice=?,orderPrice = ? , discountRate = ? , remark = ?, updateBy = ? , updateOn = ? , lineKm =? WHERE lineBaseId = ?";
		Object[] o = new Object[] {
				line.getLineName(),
				StringUtils.hasText(line.getProvinceCode()) ? Integer.parseInt(line.getProvinceCode()) : null,
				StringUtils.hasText(line.getCityCode()) ? Integer.parseInt(line.getCityCode()) : null, line.getCityName(),
				line.getStartArea(), line.getEndArea(), line.getLinePicUrl(),
				line.getLineTime(), line.getOriginalPrice(),
				line.getOrderPrice(), line.getDiscountRate(), line.getRemark(),
				line.getUpdateBy(), line.getUpdateOn(), line.getLineKm(),
				line.getLineBaseId() };
		sqlList.add(sql);
		paramsList.add(o);

		boolean boolFlag = tranOperator(sqlList, paramsList);
		if (boolFlag) {
			flag = 1;
		}
		return flag;
	}

	public boolean tranOperator(List<String> sqlList, List<Object[]> paramsList) {
		Connection conn = MyDataSource.getConnect();
		QueryRunner qr = new QueryRunner();
		int size = sqlList.size();
		try {
			conn.setAutoCommit(false);
			for (int i = 0; i < size; i++) {
				qr.update(conn, sqlList.get(i), paramsList.get(i));
			}
			conn.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public RecruitLineStationVo getRecruitLineStation(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_base_info");
			String sql = "select  a.lineBaseId,a.lineName,a.provinceCode,a.cityCode,a.startArea,a.endArea,a.linePicUrl,a.lineTime,a.lineKm,a.lineType,a.lineSubType,a.passengers,a.recruitStartTime,a.fixTimeFlag,a.recruitFixTime,a.deadlineTime,a.originalPrice,a.orderPrice,a.remark from line_base_info a where 1=1 and a.lineType=0 and a.lineBaseId=?";
			RecruitLineStationVo res = super.tableDao.queryBean(
					RecruitLineStationVo.class, sql,
					new Object[] { lineBaseId });
			if (null != res) {
				List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(res.getLineBaseId());
				res.setStationList(stationInfoList);
			}
			return res;
		}
		return null;
	}

	/**
	 * 根据站点ID查询站点信息
	 * 
	 * @param stationInfoId
	 * @return
	 */
	public StationInfo getStation(String stationInfoId) {
		if (StringUtils.hasText(stationInfoId)) {
			super.finit("pb_station");
			return super.tableDao.queryBeanById(StationInfo.class,stationInfoId, "id");
		}
		return null;
	}

	public int getSameLineNameCount(String lineBaseId, String lineName,
			String lineType) {
		if (StringUtils.hasText(lineName)) {
			super.finit("line_base_info");
			List<Object> paramList = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer(
					"select * from line_base_info where 1=1 and lineName=?");
			paramList.add(lineName);
			if (StringUtils.hasText(lineBaseId)) {
				sql.append(" and lineBaseId!=?");
				paramList.add(lineBaseId);
			}
			if (StringUtils.hasText(lineType)) {
				if ("2".equals(lineType)) {
					// 招募线路
					sql.append(" and lineStatus!=3");
					sql.append(" and lineType=0");
				} else {
					// 上下班，自由行线路
					sql.append(" and lineStatus!=5");
					sql.append(" and lineType=1");
				}
			}
			return super.tableDao.queryCount(sql.toString(), paramList.toArray());
		}
		return 0;
	}

	public int deleteStations(List<String> list) {
		super.finit("line_station_info");
		if (false == CollectionUtils.isEmpty(list)) {
			// 重新封装字符串
			StringBuffer ids = new StringBuffer("");
			int size = list.size();
			for (int i = 0; i < size; i++) {
				ids.append("'").append(list.get(i)).append("'");
				if (i < size - 1) {
					ids.append(",");
				}
			}
			String sql = "delete from line_station_info where 1=1 and stationInfoId in ("
					+ ids.toString() + ")";
			return super.tableDao.executeSQL(sql);
		}
		return 0;
	}

	public Map<String, Object> getApplyPassengerList(String lineBaseId,
			int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (StringUtils.hasText(lineBaseId)) {
			// super.finit("line_base_info a inner join line_enroll b on a.lineBaseId=b.lineBaseId inner join passenger_info c on b.passengerId=c.passengerId");
			// StringBuffer sql = new
			// StringBuffer("select b.passengerId,b.presetTime,c.displayId,c.nickName,c.sex,c.telephone from line_base_info a inner join line_enroll b on a.lineBaseId=b.lineBaseId inner join passenger_info c on b.passengerId=c.passengerId where 1=1 and a.lineType=0 and a.lineBaseId=? order by b.presetTime desc");
			super
					.finit("(( SELECT b.passengerId, b.presetTime, c.displayId, c.nickName, c.sex, c.telephone, ( SELECT name FROM pb_station WHERE id = b.ustation ) AS ustationName, ( SELECT name FROM pb_station WHERE id = b.dstation ) AS dstationName FROM line_base_info a left join line_enroll b on a.lineBaseId = b.lineBaseId left join passenger_info c on b.passengerId = c.passengerId WHERE a.lineType = 0 AND b.source=0 AND a.lineBaseId ='"
							+ lineBaseId
							+ "' ) UNION ( SELECT '--' AS passengerId, enroll.presetTime, '--' AS displyId, enroll.applyName AS nickName, '--' AS sex, enroll.applyTel AS telephone, ( SELECT name FROM pb_station WHERE id = enroll.ustation ) AS ustationName, ( SELECT name FROM pb_station WHERE id = enroll.dstation ) AS dstationName FROM line_enroll enroll WHERE enroll.source = 1 AND enroll.lineBaseId = '"
							+ lineBaseId + "'))");
			String sql = "SELECT t.passengerId, t.presetTime, t.displayId, t.nickName, t.sex, t.telephone, t.ustationName, t.dstationName,t.applyNum,t.remark FROM (( SELECT b.passengerId, b.presetTime, c.displayId, c.nickName, c.sex, c.telephone, ( SELECT name FROM pb_station WHERE id = b.ustation ) AS ustationName, ( SELECT name FROM pb_station WHERE id = b.dstation ) AS dstationName,b.applyNum,b.remark FROM line_base_info a left join line_enroll b on a.lineBaseId = b.lineBaseId left join passenger_info c on b.passengerId = c.passengerId WHERE a.lineType = 0 AND b.source=0 AND a.lineBaseId = '"
					+ lineBaseId
					+ "') UNION ( SELECT '--' AS passengerId, enroll.presetTime, '--' AS displyId, enroll.applyName AS nickName, '--' AS sex, enroll.applyTel AS telephone, ( SELECT name FROM pb_station WHERE id = enroll.ustation ) AS ustationName, ( SELECT name FROM pb_station WHERE id = enroll.dstation ) AS dstationName,enroll.applyNum,enroll.remark  FROM line_enroll enroll WHERE enroll.source = 1 AND enroll.lineBaseId = '"
					+ lineBaseId + "')) t ORDER BY t.presetTime desc";
			List<PassengerStationVo> list = super.tableDao.queryByPage(
					PassengerStationVo.class, sql.toString(), currentPageIndex,
					pageSize);
			// Page page = new
			// Page(list,sql.toString(),currentPageIndex,pageSize,new
			// Object[]{lineBaseId});
			Page page = new Page(list, sql.toString(), currentPageIndex,
					pageSize);
			res.put("page", page);
			res.put("list", list);
			return res;
		}
		return null;
	}

	public List<PassengerInfoVo> getRecruitLinePassengers(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super
					.finit("line_base_info a inner join line_enroll b on a.lineBaseId=b.lineBaseId inner join passenger_info c on b.passengerId=c.passengerId");
			StringBuffer sql = new StringBuffer(
					"select b.passengerId,b.presetTime,c.displayId,c.nickName,c.sex,c.telephone from line_base_info a inner join line_enroll b on a.lineBaseId=b.lineBaseId inner join passenger_info c on b.passengerId=c.passengerId where 1=1 and a.lineType=0 and a.lineBaseId=? order by b.presetTime desc");
			return super.tableDao.queryList(PassengerInfoVo.class, sql
					.toString(), new Object[] { lineBaseId });
		}
		return new ArrayList<PassengerInfoVo>(0);
	}

	public LineScheduleInfoVo getLineScheduleInfo(String lineBaseId) {
		LineScheduleInfoVo res = null;
		if (StringUtils.hasText(lineBaseId)) {
			super
					.finit("line_base_info a left join mgr_business b on a.businessId=b.businessId");
			String sql = "select a.lineBaseId,a.lineName,a.lineType,a.lineSubType,a.lineStatus,b.businessId,b.brefName,b.contacts,b.contactsPhone from line_base_info a left join mgr_business b on a.businessId=b.businessId where a.lineType=1 and a.lineBaseId=?";
			res = super.tableDao.queryBean(LineScheduleInfoVo.class, sql,
					new Object[] { lineBaseId });
			if (null != res) {
				// 只取未删除的班次
				String classSQL = "select a.orderStartTime,a.orderSeats from line_class_info a,line_base_info b where 1=1 and a.lineBaseId=b.lineBaseId and a.lineBaseId=? and a.delFlag=0 group by a.orderStartTime,a.orderSeats order by a.orderStartTime asc";
				// 查询班次信息
				List<LineClassVo> lineClassList = super.tableDao.queryList(
						LineClassVo.class, classSQL,
						new Object[] { lineBaseId });
				if (false == CollectionUtils.isEmpty(lineClassList)) {
					res.setLineClassList(lineClassList);
				}
			}
		}
		return res;
	}

	public List<LineClassCarDriverVo> getLineClassCarDriverList(
			String lineBaseId, String orderStartTime, String orderDate) {
		if (StringUtils.hasText(lineBaseId)) {
			StringBuffer sql = new StringBuffer(
					"select lci.lineBaseId,lci.lineClassId,lci.orderDate,lci.driverId,di.driverName,lci.vehicleId,vi.vehicleId,vi.vehicleBrand,vi.vehicleNumber,vi.vehicleSeats,mb.brefName as businessName");
			sql.append(" FROM line_class_info lci inner join driver_info di on lci.driverId=di.driverId inner join vehicle_info vi on lci.vehicleId=vi.vehicleId INNER JOIN mgr_business mb ON lci.lineBusinessId = mb.businessId ");
			sql.append(" WHERE lci.lineBaseId = ? and");
			sql.append(" (exists(select * from line_business_order AS a LEFT JOIN lease_base_info AS b ON a.leaseOrderId = b.leaseOrderNo where a.lineClassId=lci.lineClassId and (a.status=0 or a.status=1 or a.status=2) AND b.ispay = 1  GROUP  BY lineBaseId) or lci.delFlag=0)");
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(lineBaseId);
			if (StringUtils.hasText(orderStartTime)) {
				sql.append(" and lci.orderStartTime=?");
				paramList.add(orderStartTime);
			}
			if (StringUtils.hasText(orderDate)) {
				sql.append(" and lci.orderDate like ?");
				paramList.add(orderDate + "%");
			}
			sql.append("order by lci.orderDate asc");
			return super.tableDao.queryList(LineClassCarDriverVo.class, sql.toString(), paramList.toArray());
		}
		return new ArrayList<LineClassCarDriverVo>(0);
	}

	/**
	 * 根据线路id、发车时间查询班次年月信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public List<LineClassVo> getLineClassYearAndMonth(String lineBaseId,
			String orderStartTime) {
		super.finit("line_class_info");
		String sql = "SELECT DISTINCT LEFT(orderDate,7) as orderDates FROM line_class_info WHERE orderStartTime = ? and lineBaseId = ? ORDER BY LEFT(orderDate,7)";
		return super.tableDao.queryList(LineClassVo.class, sql, new Object[] {
				orderStartTime, lineBaseId });
	}

	/**
	 * 根据线路id、发车时间、所选年月查询班次信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public List<LineClassEntity> getLineClassByConditoins(String lineBaseId,
			String orderStartTime, String orderDate) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_class_info");
			StringBuffer sql = new StringBuffer(
					"select lineClassId,orderDate from line_class_info where 1 = 1 and delFlag=0");
			if (StringUtils.hasText(lineBaseId)) {
				sql.append(" and lineBaseId = '" + lineBaseId + "'");
			}
			if (StringUtils.hasText(orderStartTime)) {
				sql.append(" and orderStartTime = '" + orderStartTime + "'");
			}
			if (StringUtils.hasText(orderDate)) {
				sql.append(" and left(orderDate,7) = '" + orderDate + "'");
			}
			return super.tableDao.queryList(LineClassEntity.class, sql
					.toString());
		}
		return new ArrayList<LineClassEntity>(0);
	}

	public List<LineClassEntity> getQuarterLineClassList(String lineBaseId,
			String orderStartTime) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_class_info");
			StringBuffer sql = new StringBuffer(
					"select lineClassId,orderDate from line_class_info where 1 = 1 and delFlag=0");
			if (StringUtils.hasText(lineBaseId)) {
				sql.append(" and lineBaseId = '" + lineBaseId + "'");
			}
			if (StringUtils.hasText(orderStartTime)) {
				sql.append(" and orderStartTime = '" + orderStartTime + "'");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.DAY_OF_MONTH, ca
					.getActualMinimum(Calendar.DAY_OF_MONTH));
			String startOrderDate = sdf.format(ca.getTime());
			ca.add(Calendar.MONTH, 2);
			ca.set(Calendar.DAY_OF_MONTH, ca
					.getActualMaximum((Calendar.DAY_OF_MONTH)));
			String endOrderDate = sdf.format(ca.getTime());
			sql.append(" and orderDate between '" + startOrderDate + "' and '"
					+ endOrderDate + "'");
			return super.tableDao.queryList(LineClassEntity.class, sql
					.toString());
		}
		return new ArrayList<LineClassEntity>(0);
	}

	public String addLinePassengerMonth(LinePassengerMonthEntity entity) {
		if (null != entity) {
			super.finit("line_passenger_month");
			super.tableDao.updateData(entity, "monthId");
			return entity.getMonthId();
		}
		return null;
	}

	public int deleteLinePassengerMonth(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_passenger_month");
			String sql = "delete from line_passenger_month  where lineBaseId=?";
			return super.tableDao.executeSQL(sql, new Object[] { lineBaseId });
		}
		return 0;
	}

	public List<LineClassMonthOrderCountVo> getClassMonthOrderCount(
			String lineBaseId, String orderDate, String orderStartTime) {
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderDate)
				&& StringUtils.hasText(orderStartTime)) {
			super
					.finit("(select b.lineBaseId,b.lineClassId,b.orderStartTime,b.orderDate,d.leaseOrderId,d.payStatus from line_base_info a,line_class_info b,line_business_order c,lease_base_info d where a.lineBaseId=b.lineBaseId and b.lineClassId=c.lineClassId and c.leaseOrderId=d.leaseOrderNo and a.lineType=1 and d.ispay=1  and b.lineBaseId=? and b.orderDate like ? and b.orderStartTime=?) t");
			String sql = "select t.orderDate as dateTime,count(*) as lineNum from (select b.lineBaseId,b.lineClassId,b.orderStartTime,b.orderDate,d.leaseOrderId,d.ispay as payStatus from line_base_info a,line_class_info b,line_business_order c,lease_base_info d where a.lineBaseId=b.lineBaseId and b.lineClassId=c.lineClassId and (c.status=0 or c.status=1 or c.status=2) and c.leaseOrderId=d.leaseOrderNo and a.lineType=1 and d.ispay=1  and b.lineBaseId=? and b.orderDate like ? and b.orderStartTime=?) t group by t.orderDate";
			return super.tableDao.queryList(LineClassMonthOrderCountVo.class,
					sql, new Object[] { lineBaseId, orderDate + "%",
							orderStartTime });
		}
		return new ArrayList<LineClassMonthOrderCountVo>(0);
	}

	public Map<String, Object> getOrderPassengerList(String lineBaseId,
			String orderDate, String orderStartTime, int currentPageIndex,
			int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderDate)
				&& StringUtils.hasText(orderStartTime)) {
			super
					.finit("line_base_info a,line_class_info b,line_business_order c,lease_base_info d,passenger_info e");
			String sql = "select e.passengerId,d.leaseOrderTime as presetTime,e.displayId,e.nickName,e.sex,e.telephone  from line_base_info a,line_class_info b,line_business_order c,lease_base_info d,passenger_info e where a.lineBaseId=b.lineBaseId and (c.status=0 or c.status=1 or c.status=2) and b.lineClassId=c.lineClassId and c.leaseOrderId=d.leaseOrderNo and c.passengerId=e.passengerId and a.lineType=1 and d.ispay=1 and b.lineBaseId=? and b.orderDate=? and b.orderStartTime=? and(c.status=0 or c.status=1 or c.status=2)";
			List<PassengerInfoVo> list = super.tableDao.queryByPage(
					PassengerInfoVo.class, sql, currentPageIndex, pageSize,
					new Object[] { lineBaseId, orderDate, orderStartTime });
			Page page = new Page(list, sql, currentPageIndex, pageSize,
					new Object[] { lineBaseId, orderDate, orderStartTime });
			res.put("page", page);
			res.put("list", list);
		}
		return res;
	}

	/**
	 * 根据线路id、日期、发车时间判断对应班次是否已经被订座
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public int judgeLineClassUsed(String lineBaseId, String orderStartTime,
			String orderDate) {
		int theResultNum = 0;// 0表示未订座
		super.finit("lease_base_info a,line_business_order b");
		String sql = "select a.leaseOrderId from lease_base_info a,line_business_order b where a.leaseOrderNo=b.leaseOrderId and (a.payStatus = '0' or a.ispay = '1') and b.lineClassId=(select lineClassId from line_class_info where delFlag=0 and lineBaseId=? and orderStartTime=? and orderDate=?)";
		theResultNum = super.tableDao.queryCount(sql, new Object[] {
				lineBaseId, orderStartTime, orderDate });
		return theResultNum;
	}

	/**
	 * 根据条件查询包月信息
	 * 
	 * @param lineBaseId
	 * @param orderStartTime
	 * @param orderDate
	 * @return
	 */
	public LinePassengerMonthEntity getLinePassengerMonth(String lineBaseId,
			String orderStartTime, String orderDate) {
		super.finit("line_passenger_month");
		String sql = "select * from line_passenger_month  where lineBaseId = ? and classTime = ? and months=? and delFlag=0";
		return super.tableDao.queryBean(LinePassengerMonthEntity.class, sql,
				new Object[] { lineBaseId, orderStartTime, orderDate });
	}

	public List<StationInfo> getStationByName(String stationName) {
		if (StringUtils.hasText(stationName)) {
			super.finit("pb_station");
			String sql = "select * from pb_station where name=?";
			return super.tableDao.queryList(StationInfo.class, sql,new Object[] { stationName });
		}
		return new ArrayList<StationInfo>(0);
	}

	public List<ClassMonthOrderPriceVo> getClassMonthOrderPrice(
			String lineBaseId, String orderStartTime) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)) {
			LineEntity line = this.getLineById(lineBaseId);
			BigDecimal orderPrice = line.getOrderPrice();
			int discountRate = line.getDiscountRate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
			// 获取本月第一天
			String firstBeginDay = sdf.format(ca.getTime());
			// 获取本月最后一天
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			String firstEndDay = sdf.format(ca.getTime());
			// 获取第二个月第一天
			ca.add(Calendar.MONTH, 1);
			ca.set(Calendar.DAY_OF_MONTH, ca
					.getActualMinimum(Calendar.DAY_OF_MONTH));
			String secondBeginDay = sdf.format(ca.getTime());
			// 获取第二个月最后一天
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			String secondEndDay = sdf.format(ca.getTime());
			// 获取第三个月第一天
			ca.add(Calendar.MONTH, 1);
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
			String thirdBeginDay = sdf.format(ca.getTime());
			// 获取第三个月最后一天
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			String thirdEndDay = sdf.format(ca.getTime());
			super.finit("line_class_info");
			String sql = "SELECT count(1) AS totalDays FROM line_class_info a WHERE a.lineBaseId = ? AND a.orderStartTime = ? AND a.orderDate BETWEEN ? AND ? UNION ALL SELECT count(1) AS totalDays FROM line_class_info a WHERE a.lineBaseId=? AND a.orderStartTime=? AND a.orderDate BETWEEN ? AND ? UNION ALL SELECT count(1) AS totalDays FROM line_class_info a WHERE a.lineBaseId=? AND a.orderStartTime=? AND a.orderDate BETWEEN ? AND ?";
			List<ClassMonthOrderPriceVo> res = super.tableDao.queryList(
					ClassMonthOrderPriceVo.class, sql, new Object[] {
							lineBaseId, orderStartTime, firstBeginDay,
							firstEndDay, lineBaseId, orderStartTime,
							secondBeginDay, secondEndDay, lineBaseId,
							orderStartTime, thirdBeginDay, thirdEndDay });
			if (false == CollectionUtils.isEmpty(res)) {
				if (res.size() == 3) {
					String month = firstBeginDay.substring(0, 7);
					boolean supportMonth = supportMonth(lineBaseId,
							orderStartTime, month);
					ClassMonthOrderPriceVo firstMonthPrice = res.get(0);
					firstMonthPrice.setDiscountRate(supportMonth ? String
							.valueOf(discountRate) : String.valueOf("100"));
					firstMonthPrice.setMonth(month);

					// float firstTotalPrice =
					// (float)orderPrice*firstMonthPrice.getTotalDays();
					float firstTotalPrice = orderPrice.multiply(
							new BigDecimal(firstMonthPrice.getTotalDays())).floatValue();
					firstMonthPrice.setTotalPrice(String.valueOf(firstTotalPrice));
					// 折扣价
					firstMonthPrice.setDiscountPrice(supportMonth ? String.format("%.2f", firstTotalPrice
									* (float) discountRate / 100f) : String.valueOf(firstTotalPrice));

					month = secondBeginDay.substring(0, 7);
					supportMonth = supportMonth(lineBaseId, orderStartTime,
							month);
					ClassMonthOrderPriceVo secondMonthPrice = res.get(1);
					secondMonthPrice.setDiscountRate(supportMonth ? String.valueOf(discountRate) : String.valueOf("100"));
					secondMonthPrice.setMonth(month);
					// float secondTotalPrice =
					// (float)orderPrice*secondMonthPrice.getTotalDays();
					float secondTotalPrice = orderPrice.multiply(
							new BigDecimal(secondMonthPrice.getTotalDays())).floatValue();
					secondMonthPrice.setTotalPrice(String.valueOf(secondTotalPrice));
					secondMonthPrice.setDiscountPrice(supportMonth ? String.format("%.2f", secondTotalPrice
									* (float) discountRate / 100f) : String.valueOf(secondTotalPrice));

					month = thirdBeginDay.substring(0, 7);
					supportMonth = supportMonth(lineBaseId, orderStartTime,
							month);
					ClassMonthOrderPriceVo thirdMonthPrice = res.get(2);
					thirdMonthPrice.setDiscountRate(supportMonth ? String.valueOf(discountRate) : String.valueOf("100"));
					thirdMonthPrice.setMonth(month);
					// float thirdTotalPrice =
					// (float)orderPrice*thirdMonthPrice.getTotalDays();
					float thirdTotalPrice = orderPrice.multiply(
							new BigDecimal(thirdMonthPrice.getTotalDays())).floatValue();
					thirdMonthPrice.setTotalPrice(String.valueOf(thirdTotalPrice));
					thirdMonthPrice.setDiscountPrice(supportMonth ? String.format("%.2f", thirdTotalPrice
									* (float) discountRate / 100f) : String.valueOf(thirdTotalPrice));

					List<ClassMonthOrderPriceVo> newList = new ArrayList<ClassMonthOrderPriceVo>();
					newList.add(firstMonthPrice);
					newList.add(secondMonthPrice);
					newList.add(thirdMonthPrice);

					return newList;
				}
			}
		}
		return new ArrayList<ClassMonthOrderPriceVo>(0);
	}

	public boolean supportMonth(String lineBaseId, String orderStartTime,
			String months) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)
				&& StringUtils.hasText(months)) {
			super.finit("line_passenger_month");
			String sql = "select monthId from line_passenger_month a where a.lineBaseId=? and a.classTime=? and a.months=? and a.delFlag=0";
			int count = super.tableDao.queryCount(sql, new Object[] {
					lineBaseId, orderStartTime, months });
			return count > 0;
		}
		return false;
	}

	public int getLineClassOrderCount(String lineBaseId, String orderStartTime) {
		int count = 0;
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)) {
			super.finit("lease_base_info a,line_business_order b");
			// String sql =
			// "select a.leaseOrderId from lease_base_info a,line_business_order b where a.leaseOrderNo=b.leaseOrderId and a.payStatus in (0,1) and b.lineClassId in (select lineClassId from line_class_info where lineBaseId=? and orderStartTime=?)";
			String sql = "select a.localId from line_business_order a,lease_base_info b,line_class_info c where a.leaseOrderId=b.leaseOrderNo and a.lineClassId=c.lineClassId and (b.payStatus = '0' or b.ispay = '1') and c.lineBaseId=? and c.orderStartTime=? and CONCAT(c.orderDate,' ',c.orderStartTime)>CURRENT_TIMESTAMP()";
			count = super.tableDao.queryCount(sql, new Object[] { lineBaseId,
					orderStartTime });
		}
		return count;
	}

	public List<OrderStartTimeVo> getSameClassTime(String lineBaseId,
			String orderStartTimes) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTimes)) {
			super.finit("line_class_info a");
			// 查询线路下所有的发车时间
			String sql = "select distinct a.orderStartTime from line_class_info a where a.lineBaseId=? order by a.orderStartTime";
			List<OrderStartTimeVo> allList = super.tableDao.queryList(
					OrderStartTimeVo.class, sql, new Object[] { lineBaseId });
			// 查询线路下未做删除或部分工作日未删除的发车时间
			sql = "select distinct a.orderStartTime from line_class_info a where a.lineBaseId=? and a.delFlag=0 order by a.orderStartTime";
			List<OrderStartTimeVo> unDelList = super.tableDao.queryList(
					OrderStartTimeVo.class, sql, new Object[] { lineBaseId });
			// 所有发车时间-未删除发车时间＝已删除发车时间 取集合差集
			List<OrderStartTimeVo> oList = new ArrayList<OrderStartTimeVo>();
			if (CollectionUtils.isEmpty(unDelList)) {
				oList = allList;
			} else {
				oList = (List<OrderStartTimeVo>) CollectionUtils.subtract(allList, unDelList);
			}

			if (false == CollectionUtils.isEmpty(oList)) {
				List<OrderStartTimeVo> res = new ArrayList<OrderStartTimeVo>();
				String[] arr = orderStartTimes.split(",");
				// 数据匹配，执行次数为两个集合大小的乘积
				for (OrderStartTimeVo v : oList) {
					for (String s : arr) {
						if (v.getOrderStartTime().equals(s)) {
							res.add(new OrderStartTimeVo(s));
						}
					}
				}
				return res;
			}
		}
		return new ArrayList<OrderStartTimeVo>(0);
	}

	public List<LineClassVo> getLineClassList(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_class_info");
			String sql = "select distinct a.orderStartTime,a.orderSeats from line_class_info a where a.delFlag=0 and a.lineBaseId=? order by a.orderStartTime asc";
			return super.tableDao.queryList(LineClassVo.class, sql,
					new Object[] { lineBaseId });
		}
		return new ArrayList<LineClassVo>(0);
	}

	public ClassMonthOrderPriceVo getClassOneMonthOrderPrice(String lineBaseId,
			String orderStartTime, String orderDate) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)
				&& StringUtils.hasText(orderDate)) {
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
				List<ClassMonthOrderPriceVo> cmoList = super.tableDao
						.queryList(ClassMonthOrderPriceVo.class, sql,
								new Object[] { lineBaseId, orderStartTime,
										beginOrderDate, endOrderDate });
				if (false == CollectionUtils.isEmpty(cmoList)) {
					if (cmoList.get(0).getTotalDays() > 0) {
						ClassMonthOrderPriceVo res = cmoList.get(0);
						boolean supportMonth = supportMonth(lineBaseId,
								orderStartTime, orderDate);
						// float totalPrice =
						// (float)orderPrice*res.getTotalDays();
						float totalPrice = orderPrice.multiply(
								new BigDecimal(res.getTotalDays()))
								.floatValue();
						res.setDiscountPrice(supportMonth ? String.format(
								"%.2f", totalPrice * (float) discountRate
										/ 100f) : String.valueOf(totalPrice));
						res.setDiscountRate(supportMonth ? String
								.valueOf(discountRate) : "100");
						res.setMonth(orderDate);
						res.setTotalPrice(String.valueOf(totalPrice));
						return res;
					}
				}
			}

		}
		return null;
	}

	public int getEffectiveOrderCount(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("lease_base_info a,line_base_info b");
			String sql = "select a.leaseOrderId from lease_base_info a,line_base_info b where a.lineBaseId=b.lineBaseId and (a.payStatus = '0' or a.ispay = '1') and a.lineBaseId=?";
			return super.tableDao.queryCount(sql, new Object[] { lineBaseId });
		}
		return 0;
	}

	public String createLine(LineClassDTO lcd, String applicationId) {
		String lineBaseId = null;
		return lineBaseId;
	}

	public List<LineClassEntity> getLineClassList(String lineName,
			String orderDate) {
		super.finit("line_class_info a,line_base_info b");
		StringBuffer sql = new StringBuffer(
				"select distinct orderStartTime from line_class_info a,line_base_info b where a.lineBaseId=b.lineBaseId and a.delFlag=0 ");
		List<Object> paramList = new ArrayList<Object>();
		if (StringUtils.hasText(lineName)) {
			sql.append(" and b.lineName like ?");
			paramList.add(SqlBuilder.getSqlLikeValue(lineName.trim()));
		}
		if (StringUtils.hasText(orderDate)) {
			sql.append(" and a.orderDate=?");
			paramList.add(orderDate);
		}
		sql.append(" order by a.`orderStartTime` asc");
		return super.tableDao.queryList(LineClassEntity.class, sql.toString(),
				paramList.toArray(new Object[] {}));
	}

	public int getLineClassOrderSeats(String lineBaseId, String orderStartTime) {
		if (StringUtils.hasText(lineBaseId)
				&& StringUtils.hasText(orderStartTime)) {
			super
					.finit("line_business_order a, lease_base_info b, line_class_info c");
			String sql = "SELECT max(t.orderCount) as orderMaxTicketCount FROM ( SELECT count(a.lineClassId) AS orderCount FROM line_business_order a, lease_base_info b, line_class_info c WHERE 1 = 1 AND a.leaseOrderId = b.leaseOrderNo AND a.lineClassId = c.lineClassId AND (b.payStatus = '0' or b.ispay = '1') AND CONCAT( c.orderDate, ' ', c.orderStartTime )>CURRENT_TIMESTAMP() and c.lineBaseId = ? and c.orderStartTime=? GROUP BY a.lineClassId) t";
			OrderMaxTicketCountVo v = super.tableDao.queryBean(
					OrderMaxTicketCountVo.class, sql, new Object[] {
							lineBaseId, orderStartTime });
			if (null != v) {
				return v.getOrderMaxTicketCount();
			}
		}
		return 0;
	}

	public Map<String, Object> getLinesForOuter(Search search,
			int curPageIndex, int pageSize) {
		Connection conn = null;
		try {
			conn = MyDataSource.getConnect();
			if (null == conn || null == search) {
				return null;
			}
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer(
					"select lineBase.lineBaseId,lineBase.lineName,lineBase.recruitStartTime,lineBase.orderPrice,lineBase.lineKm,lineBase.lineTime from line_base_info lineBase where 1=1");
			// 线路类型 0:招募 1:非招募
			if ("0".equals(search.getField01())) {
				sql
						.append(" and lineBase.lineType=0 and lineBase.lineStatus=1");
			} else if ("1".equals(search.getField01())) {
				sql
						.append(" and lineBase.lineType=1 and lineBase.lineStatus=3");
			}
			if (StringUtils.hasText(search.getField02())) {
				sql.append(" and lineBase.lineBaseId='"
						+ search.getField02().trim() + "'");
			}
			// 排序与线路列表保持一致
			sql
					.append(" order by lineBase.displayFlag desc,lineBase.updateOn desc");
			// 查询线路总数
			List<LineEntity> lineList = qr.query(conn, sql.toString(),
					new BeanListHandler<LineEntity>(LineEntity.class));
			int total = CollectionUtils.isEmpty(lineList) ? 0 : lineList.size();
			// 分页查询数据
			sql.append(" limit " + curPageIndex + "," + pageSize);
			lineList = qr.query(conn, sql.toString(), new BeanListHandler<LineEntity>(
					LineEntity.class));
			RecruitLineForOuterContainer container = new RecruitLineForOuterContainer();
			container.setPageSize(pageSize);
			container.setTotalCount(total);
			List<RecruitLineForOuterVo> resultData = new ArrayList<RecruitLineForOuterVo>();
			if (false == CollectionUtils.isEmpty(lineList)) {
				RecruitLineForOuterVo outerLine = null;
				// 线路循环开始
				for (LineEntity line : lineList) {
					outerLine = new RecruitLineForOuterVo();
					outerLine.setLineBaseId(line.getLineBaseId());
					outerLine.setOrderPrice(line.getOrderPrice().toString());
					outerLine.setLineKm(line.getLineKm() + "");
					outerLine.setLineTime(line.getLineTime() + "");
					String stationSQL = "select stationName from pb_station where lineId=?";
					Object[] params = new Object[1];
					params[0] = line.getLineBaseId();
					List<StationInfo> stationList = qr.query(conn,
							stationSQL, new BeanListHandler<StationInfo>(
									StationInfo.class),params);
					setRecruitLineForOuterVo(stationList, outerLine);
					// 按线路查询发车时间
					if ("0".equals(search.getField01())) {
						// 招募线路
						outerLine
								.setRecruitStartTime(line.getRecruitStartTime()
										.substring(
												line.getRecruitStartTime()
														.indexOf(" ") + 1));
					} else if ("1".equals(search.getField01())) {
						// 非招募线路
						String classSql = "select distinct orderStartTime,orderSeats from line_class_info where delFlag=0 and lineBaseId='"
								+ line.getLineBaseId()
								+ "' order by orderStartTime asc";
						List<LineClassEntity> classList = qr.query(conn,
								classSql, new BeanListHandler<LineClassEntity>(
										LineClassEntity.class));
						StringBuffer recruitStartTime = new StringBuffer();
						StringBuffer orderSeats = new StringBuffer();
						if (false == CollectionUtils.isEmpty(classList)) {
							int classListSize = classList.size();
							for (int c = 0; c < classListSize; c++) {
								recruitStartTime.append(classList.get(c)
										.getOrderStartTime());
								orderSeats.append(classList.get(c)
										.getOrderSeats()
										+ "");
								if (c != classListSize - 1) {
									recruitStartTime.append(",");
									orderSeats.append(",");
								}
							}
						}
						outerLine.setRecruitStartTime(recruitStartTime
								.toString());
						outerLine.setOrderSeats(orderSeats.toString());
					}
					// //按线路查询发车时间结束
					resultData.add(outerLine);
				}
				// for循环结束
			}
			conn.commit();
			Map<String, Object> result = new HashMap<String, Object>();
			container.setResultData(resultData);
			result.put("container", container);
			return result;
		} catch (Exception e) {
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

		return null;
	}

	private void setRecruitLineForOuterVo(List<StationInfo> stationList,
			RecruitLineForOuterVo outerLine) {
		if (false == CollectionUtils.isEmpty(stationList) && null != outerLine) {
			int size = stationList.size();
			if (size < 3) {
				outerLine.setStartPoint(stationList.get(0).getName());
				outerLine.setEndPoint(stationList.get(size - 1)
						.getName());
				outerLine.setAccessPoint("");
			} else {
				outerLine.setStartPoint(stationList.get(0).getName());
				outerLine.setEndPoint(stationList.get(size - 1)
						.getName());
				StringBuffer accessPoints = new StringBuffer();
				for (int j = 1; j <= (size - 2); j++) {
					accessPoints.append(stationList.get(j).getName());
					if (j != (size - 2)) {
						accessPoints.append("---");
					}
				}
				outerLine.setAccessPoint(accessPoints.toString());
			}
		}
	}

	public int addLineEnrol(LineEnrollVo lineEnrollVo) {
		if (null != lineEnrollVo) {
			super.finit("line_enroll");
			return super.tableDao.updateData(lineEnrollVo, "enrollId");
		}
		return 0;
	}

	public int addUserLineCallBack(UserLineCallbackVo callbackVo) {
		int flag = 0;
		if (null != callbackVo) {
			Connection conn = MyDataSource.getConnect();
			if (null == conn) {
				return -9999;
			}
			try {
				conn.setAutoCommit(false);
				QueryRunner qr = new QueryRunner();
				String applicationId = callbackVo.getApplicationId();
				if (StringUtils.hasText(applicationId)) {
					String sql = "update line_user_application set callbackStatus=1 where applicationId=?";
					flag = qr.update(conn, sql, new Object[] { applicationId });
					if (flag > 0) {
						sql = "insert into line_user_callback(id,applicationId,callbackContent,createBy,createOn,updateBy,updateOn) values(?,?,?,?,?,?,?)";
						flag = qr.update(conn, sql, new Object[] {
								StringUtil.generateSequenceNo(), applicationId,
								callbackVo.getCallbackContent(),
								callbackVo.getCreateBy(),
								callbackVo.getCreateOn(),
								callbackVo.getUpdateBy(),
								callbackVo.getUpdateOn() });
					}
				}
				conn.commit();
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return flag;
	}

	public Map<String, Object> getUserLinesStationCount(Search search,
			int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (null != search) {
			String countType = search.getField01();
			String sql = null;
			super.finit("line_user_application a");
			if ("1".equals(countType)) {
				sql = "select b.stationName as address,count(a.startAddress) as applyCount from line_user_application a LEFT JOIN line_user_application_station as b ON a.startAddress = b.appliStationId GROUP BY a.startAddress ORDER BY count(a.startAddress) desc";
			}
			if ("2".equals(countType)) {
				sql = "select b.stationName as address,count(a.endAddress) as applyCount from line_user_application a LEFT JOIN line_user_application_station as b ON a.endAddress = b.appliStationId GROUP BY a.endAddress ORDER BY count(a.endAddress) desc";
			}
			List<UserLineStationCount> list = super.tableDao
					.queryByPage(UserLineStationCount.class, sql,
							currentPageIndex, pageSize);
			Page page = new Page(list, sql, currentPageIndex, pageSize);
			res.put("page", page);
			res.put("list", list);
		}
		return res;
	}

	public List<LineEntity> getAllLineList() {
		super.finit("line_base_info");
		String sql = "select lineBaseId,lineName from line_base_info where lineType=1 and lineStatus!=5";
		return super.tableDao.queryList(LineEntity.class, sql);
	}

	public UserLineEntity getUserLineDetailByKey(String applicationId) {
		if (StringUtils.hasText(applicationId)) {
			super.finit("line_user_application");
			String sql = "select a.*,b.nickName,b.telephone from line_user_application a left join passenger_info b on a.passengerId=b.passengerId where a.applicationId=?";
			return super.tableDao.queryBean(UserLineEntity.class, sql,
					new Object[] { applicationId });
		}
		return null;
	}

	/**
	 * 根据id查询用户申请线路信息
	 * 
	 * @param applicationId
	 * @return
	 */
	public UserLineEntity getUserLineById(String applicationId) {
		super.finit("line_user_application");
		String sql = "select a.applicationId as applicationId from line_user_application a where a.applicationId=?";
		return super.tableDao.queryBean(UserLineEntity.class, sql,
				new Object[] { applicationId });
	}

	/**
	 * 修改用户申请线路信息
	 * 
	 * @param ule
	 * @return
	 */
	public int updateUserLine(UserLineEntity ule, String userId) {
		super.finit("line_user_application");
		String sql = "update line_user_application set auditStatus = ? where applicationId=?";
		Object[] arr = new Object[2];
		arr[0] = ule.getAuditStatus();
		arr[1] = ule.getApplicationId();
		int flag = tableDao.executeSQL(sql, arr);

		String str = null;
		if (flag > 0) {
			if (ule.getAuditStatus() == 0) {
				str = "用户申请线路审核成功";
			} else {
				str = "用户申请线路取消审核成功";
			}
		} else {
			if (ule.getAuditStatus() == 0) {
				str = "用户申请线路审核失败";
			} else {
				str = "用户申请线路取消审核失败";
			}
		}
		AppendLog(userId, "用户申请线路", str);
		return flag;
	}

	public String getCityName(String provinceCode, String cityCode) {
		if (StringUtils.hasText(provinceCode) && StringUtils.hasText(cityCode)) {
			super.finit("sys_area");
			StringBuilder sql = new StringBuilder(
					"select areaName from sys_area where arearCode=? and fdCode=?");
			List<SysArea> areaList = super.tableDao.queryList(SysArea.class,
					sql.toString(), new Object[] { Integer.parseInt(cityCode),
							Integer.parseInt(provinceCode) });
			return CollectionUtils.isEmpty(areaList) ? "" : areaList.get(0)
					.getAreaName();
		}
		return "";
	}

	public Map<String, Object> getUserLineApplyList(String applicationId,
			int currentPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		super.finit("line_enroll_user a,passenger_info p");
		String sql = "select p.nickName,p.displayId,p.telephone from line_enroll_user a,passenger_info p where a.passengerId=p.passengerId and a.applicationId='"
				+ applicationId + "' ORDER BY a.apptime desc";
		list = super.tableDao.queryByPage(PassengerInfoEntity.class, sql,
				currentPageIndex, pageSize);
		Page page = new Page(list, sql, currentPageIndex, pageSize);
		res.put("page", page);
		res.put("list", list);
		return res;
	}

	public List<LineEnrollVo> getLineEnrollList(String lineBaseId,
			String applyTel) {
		super.finit("line_enroll");
		String sql = "select a.* from line_enroll a where a.lineBaseId=? and a.applyTel=?";
		return super.tableDao.queryList(LineEnrollVo.class, sql, new Object[] {
				lineBaseId, applyTel });
	}

	/**
	 * 获取班次
	 */
	public List<String> getAllLineOrderStartTime() {
		super.finit("line_class_info");
		sql = " SELECT orderStartTime from line_class_info GROUP BY orderStartTime ORDER BY orderStartTime ";
		List<String> orderStartTimes = tableDao.queryList(String.class, sql);
		return orderStartTimes;
	}

	public Map<String, Object> getStationApplyList(Search search,
			int curPageIndex, int pageSize) {
		Map<String, Object> res = new HashMap<String, Object>();
		if (null != search && StringUtils.hasText(search.getField01())
				&& StringUtils.hasText(search.getField02())) {
			super
					.finit("line_user_application a LEFT JOIN passenger_info b on a.passengerId=b.passengerId");
			String sql = "select b.displayId,b.nickName,b.telephone from line_user_application a LEFT JOIN passenger_info b on a.passengerId=b.passengerId ";
			if (search.getField01().equals("1")) {
				sql += " LEFT JOIN line_user_application_station AS c ON c.appliStationId = a.startAddress where 1=1  AND c.stationName = ? ";
			} else if (search.getField01().equals("2")) {
				sql += " LEFT JOIN line_user_application_station AS c ON c.appliStationId = a.endAddress where 1=1  AND c.stationName = ? ";
			} else {
				throw new IllegalArgumentException();
			}
			Object[] params = new Object[] { search.getField02() };
			list = super.tableDao.queryByPage(PassengerInfoEntity.class, sql,
					curPageIndex, pageSize, params);
			Page page = new Page(list, sql.toString(), curPageIndex, pageSize,
					params);
			res.put("page", page);
			res.put("list", list);
		}
		return res;
	}

	public LineCityVo getLineCity(String lineBaseId) {
		if (StringUtils.hasText(lineBaseId)) {
			super.finit("line_base_info");
			String sql = "select lineBaseId,provinceCode,cityCode,cityName from line_base_info where lineBaseId=?";
			return super.tableDao.queryBean(LineCityVo.class, sql,
					new Object[] { lineBaseId });
		}
		return null;
	}

	public int setLineAllowance(LineAllowanceVo v) {
		int flag = -1;
		Connection conn = MyDataSource.getConnect();
		if (conn != null && v != null) {
			try {
				conn.setAutoCommit(false);
				QueryRunner qr = new QueryRunner();
				if (StringUtils.hasText(v.getAllowanceId())) {
					// 更新
					String updateSql = "update line_allowance set ownAllowance=?,otherAllowance=?,statusFlag=?,operateBy=?,operateOn=? where allowanceId=?";
					flag = qr.update(conn, updateSql, new Object[] {
							v.getOwnAllowance(), v.getOtherAllowance(),
							v.getStatusFlag(), v.getOperateBy(),
							v.getOperateOn(), v.getAllowanceId() });
				} else {
					// 新增
					String insertSql = "insert into line_allowance(allowanceId,lineBaseId,ownAllowance,otherAllowance,statusFlag,operateBy,operateOn) values(?,?,?,?,?,?,?)";
					flag = qr.update(conn, insertSql, new Object[] {
							StringUtil.generateSequenceNo(), v.getLineBaseId(),
							v.getOwnAllowance(), v.getOtherAllowance(),
							v.getStatusFlag(), v.getOperateBy(),
							v.getOperateOn() });
				}
				conn.commit();
			} catch (Exception e) {
				flag = -9999;
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
		return flag;
	}

	public LineAllowanceDetailVo getLineAllowance(String lineBaseId) {
		super.finit("line_allowance a,line_base_info b");
		String sql = "select a.allowanceId,a.ownAllowance,a.otherAllowance,a.statusFlag,a.operateBy,a.operateOn,b.lineBaseId,b.lineName,b.originalPrice,b.orderPrice from line_base_info b left join line_allowance a on b.lineBaseId=a.lineBaseId where b.lineBaseId=?";
		return super.tableDao.queryBean(LineAllowanceDetailVo.class, sql,
				new Object[] { lineBaseId });
	}

	public List<LineClassOrderPassengerVo> getLineClassOrderPassengers(
			String lineBaseId, String orderStartTime, String startTime,
			String endTime) {
		if (StringUtils.hasText(lineBaseId)) {
			super
					.finit("line_base_info a, line_class_info b, line_business_order c, lease_base_info d, passenger_info e");
			StringBuffer sql = new StringBuffer(
					"SELECT b.orderDate, e.displayId, e.nickName, e.telephone,d.leaseOrderTime FROM line_base_info a, line_class_info b, line_business_order c, lease_base_info d, passenger_info e WHERE a.lineBaseId = b.lineBaseId AND b.lineClassId = c.lineClassId AND c.leaseOrderId = d.leaseOrderNo AND e.passengerId = c.passengerId AND a.lineType = 1 AND d.ispay = 1 AND c.status !=4 and a.lineBaseId=?");
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(lineBaseId);
			if (StringUtils.hasText(orderStartTime)) {
				sql.append(" AND b.orderStartTime=?");
				paramList.add(orderStartTime);
			}
			if (StringUtils.hasText(startTime)) {
				sql.append(" AND b.orderDate>=?");
				paramList.add(startTime);
			}
			if (StringUtils.hasText(endTime)) {
				sql.append(" AND b.orderDate<=?");
				paramList.add(endTime);
			}
			return super.tableDao.queryList(LineClassOrderPassengerVo.class,
					sql.toString(), paramList.toArray());
		}
		return null;
	}

	/** 判断线路是否有排班 **/
	@Override
	public String judgeLineClass(String lineBaseId) {
		super.finit("line_class_info");
		sql = " SELECT lineClassId FROM line_class_info WHERE lineBaseId = ? AND orderDate>= ? ";
		args = new Object[2];
		args[0] = lineBaseId;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		args[1] = sdf.format(date);
		int count = tableDao.queryCount(sql, args);
		if (count >= 1) {
			/** 有排班 **/
			return "1";
		}
		/** 没排班 **/
		return "0";
	}

	/** 把线路指定给新的商家 **/
	@Override
	public int updateLineToNewBusiness(String lineBaseId, String businessId) {
		super.finit("line_base_info");
		sql = " UPDATE line_base_info SET businessId = '" + businessId
				+ "' WHERE lineBaseId = '" + lineBaseId + "'";
		return tableDao.executeSQL(sql);
	}

	/**获得上次排班的最后一次工作日的司机和车辆**/
	@Override
	public AppVo_2 getDriverAndCar(String lineBaseId) {
		super.finit("line_class_info");
		sql = " SELECT driverId AS a1,vehicleId AS a2  FROM line_class_info WHERE lineBaseId = ? AND delFlag = 0 " +
				" AND ( driverId <>'' OR driverId <>NULL) AND ( vehicleId <>'' OR vehicleId <>NULL) ORDER BY orderDate DESC LIMIT 0,1 ";
		List<AppVo_2> lists = tableDao.queryList(AppVo_2.class, sql,new Object[]{lineBaseId});
		AppVo_2 vo_2 = null;
		if(null!=lists && lists.size()>0){
			vo_2 = lists.get(0);
		}
		return vo_2;
	}
	
	/**添加圈子信息**/
	public int addImGroupInfo(String userId,String lineName,String orderSeats,String lineBaseId){
		int flag = 0;
		super.finit("im_group_info");
		String sql = "select * from im_group_info where createBy=? and gName=? and gmaxNum=? and lineBaseId=?";
		args = new Object[4];
		args[0]=userId;
		args[1]=lineName;
		args[2]=orderSeats;
		args[3]=lineBaseId;
		ImGroupInfo imGroupInfo = super.tableDao.queryBean(ImGroupInfo.class, sql, args);
		if(imGroupInfo==null){
			sql="INSERT INTO `im_group_info` (`createBy`,`gName`,`gmaxNum`,`createTime`,`isVerify`,`lineBaseId`) " +
			"VALUES(?,?,?,UNIX_TIMESTAMP(),'0',?)";
			args=new Object[4];
			args[0]=userId;
			args[1]=lineName;
			args[2]=orderSeats;
			args[3]=lineBaseId;
			flag = tableDao.executeSQL(sql, args);
		}
		return flag;
	}
	
	/**
	 * 查询所有线路id和名称
	 * @return
	 */
	public List<LineBaseInfo> getLineInfo(){
		super.finit("line_base_info");
		String sql = "SELECT lineBaseId,lineName FROM line_base_info WHERE lineType = '1' AND lineSubType = '0' AND lineStatus = '3'";
		return tableDao.queryList(LineBaseInfo.class, sql);
	}
	
	/**获取线路运营人员名称列表
	 * @return
	 */
	public List<SysAdminVo> getAdminName(){
		super.finit("line_base_info,sys_admin");
		StringBuffer sql = new StringBuffer("SELECT DISTINCT sa.`userName`, sa.`userId` FROM sys_admin AS sa, sys_userrole AS su,  ");
		sql.append(" (SELECT roleId FROM sys_role WHERE roleName = '运营主管'  OR roleName = '客服人员' OR roleName = '客服主管' OR roleName = '线路运营人员' OR roleName = '运营专员' OR roleName = '城市经理' )  ");
		sql.append("  t WHERE 1 = 1  AND sa.`status` = 1  AND sa.`sysType` = 0  AND t.roleId = su.`roleId` AND su.`userId` = sa.`userId` AND sa.`deleteFlag`=0");
		List<SysAdminVo> list = super.tableDao.queryList(SysAdminVo.class, sql.toString());
		return list;
	}

	/**
	 * 查询线路操作日志
	 * @param lineBaseId
	 * @return
	 */
	public Map<String, Object> getLineLog(String lineBaseId,int currentPageIndex,int pageSize) {
		super.finit("line_operation_log");
		String sql = "SELECT lol.*,sa.userName FROM line_operation_log AS lol LEFT JOIN sys_admin AS sa ON lol.updateBy = sa.userId where lineBaseId=? ORDER BY lol.`updateOn` DESC ";
		List<LineOperateLogVo> list = super.tableDao.queryByPage(LineOperateLogVo.class, sql, currentPageIndex, pageSize, new Object[]{lineBaseId});
		Page page = new Page(list, sql, currentPageIndex, pageSize, new Object[]{lineBaseId});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 查询定制线路车辆信息
	 * @return
	 */
	public List<LineCarMsgVo> getDefineCarMsg() {
		String orderDate = MyDate.formatTime(System.currentTimeMillis(), "yyyy-MM-dd");
		if(org.apache.commons.lang.StringUtils.isEmpty(orderDate))
			return null;
		StringBuffer sql = new StringBuffer("SELECT  a.`lineBaseId`,a.`orderStartTime`,b.`lineName` ,c.`GPSNo`, c.`vehicleNumber` FROM line_class_info a LEFT JOIN");
		sql.append(" line_base_info b ON  a.`lineBaseId`=b.`lineBaseId` LEFT JOIN vehicle_info c ON  c.`vehicleId`=a.`vehicleId` LEFT JOIN");
		sql.append(" gps_info d ON d.gpsNo=c.gpsNo WHERE a.orderDate=? AND a.delFlag = 0");
		List<LineCarMsgVo> list = super.tableDao.queryList(LineCarMsgVo.class, sql.toString(), new Object[]{orderDate});
		return list;
	}
}
