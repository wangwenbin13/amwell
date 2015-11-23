package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.QueryHelper;
import com.amwell.commons.StringUtil;
import com.amwell.commons.baiduAPI.BaiduAPIUtils;
import com.amwell.commons.baiduAPI.Point;
import com.amwell.dao.IPublishLineDao;
import com.amwell.entity.LineSplitInfo;
import com.amwell.vo.Company;
import com.amwell.vo.CompanyLine;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassScheduleVo;
import com.amwell.vo.LineOperateLogVo;
import com.amwell.vo.LineStationInfo;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.PageBean;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.app.LeaseBaseInfo;
import com.amwell.vo.app.LineBaseInfo;

@Repository(value = "publishLineDao")
public class PublishLineDaoImpl extends DaoSupport implements IPublishLineDao{
	private static final String LINE_SPLIT_INFO = "line_split_info";

	private static final Logger LOG = Logger.getLogger(PublishLineDaoImpl.class);
	
	private final static QueryRunner QUERY_RUNNER = new QueryRunner(true);
	
	public void setAutoPutTicket(String lineBaseId,String autoPutTicket){
		Connection conn = MyDataSource.getConnect();
		try{
			String sql = "update line_base_info set autoPutTicket=? where lineBaseId=?";
			Object[] params = new Object[2];
			params[0] = autoPutTicket;
			params[1] = lineBaseId;
			LOG.info("sql="+sql);
			LOG.info("params[0]="+params[0]);
			LOG.info("params[1]="+params[1]);
			QUERY_RUNNER.update(conn, sql, params);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}finally{
			try{
			    if(conn!=null){	
			    	conn.close();
			    }
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
	}
	
	public int getMaxOrderNumByLineId(String lineId){
		Connection conn = null;
		try{
			conn = MyDataSource.getConnect();
			Object[] params = new Object[2];
			StringBuilder builder = new StringBuilder();
			builder.append("SELECT t.orderDate AS dateTime,count(*) AS lineNum FROM ( ");
			builder.append("SELECT b.lineBaseId,b.lineClassId,b.orderStartTime,b.orderDate,d.leaseOrderId,d.ispay AS payStatus ");
			builder.append("FROM line_base_info a,line_class_info b,line_business_order c,lease_base_info d ");
			builder.append("WHERE a.lineBaseId = b.lineBaseId AND b.lineClassId = c.lineClassId ");
			builder.append("AND (c. STATUS = 0 OR c. STATUS = 1 OR c. STATUS = 2) AND c.leaseOrderId = d.leaseOrderNo AND a.lineType = 1 AND d.ispay = 1 ");
			builder.append("AND b.lineBaseId = ? AND b.orderDate >= ? ");
			builder.append(") t GROUP BY t.orderDate order by lineNum desc limit 0,1");
			params[0] = lineId;
			params[1] = MyDate.formatTime(System.currentTimeMillis(), "yyyy-MM-dd");
			Map<String,Object> map = QUERY_RUNNER.query(conn, builder.toString(), new MapHandler(), params);
			if(map!=null){
				Long lineNum = (Long)map.get("lineNum");
				if(lineNum!=null){
					return lineNum.intValue();
				}
			}
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		return 0;
	}
	
	/**
	 * 获取所有的站点
	 */
	public List<StationInfo> getAllStations(){
		List<StationInfo> stationList = null;
		Connection conn = MyDataSource.getConnect();
		try{
			String sql = "select * from pb_station";
		    stationList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<StationInfo>(StationInfo.class));
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				LOG.error(e.getMessage());
			}
		}
		return stationList;
	}
	
	/**
	 * 根据id删除站点
	 * @param id 站点Id
	 */
	public void deleteStationById(String id){
		Connection conn = MyDataSource.getConnect();
		try{
			String sql = "update pb_station set available=1 where id=?";
			Object[] params = new Object[1];
			params[0] = id;
			QUERY_RUNNER.update(conn,sql, params);
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				LOG.error(e.getMessage());
			}
		}
	}
	
	public void deleteStationByLineId(String lineId){
		Connection conn = MyDataSource.getConnect();
		try{
			String sql = "update pb_station set available=1 where lineId=?";
			Object[] params = new Object[1];
			params[0] = lineId;
			QUERY_RUNNER.update(conn,sql,params);
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				LOG.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 根据线路id获取站点列表
	 * @param lineId 线路Id
	 */
	public List<StationInfo> listStationInfoByLineId(String lineId){
		Connection conn = MyDataSource.getConnect();
		List<StationInfo> stationInfoList = null;
		try{
			String sql = "select * from pb_station where lineId=? and available=0 order by sortNum";
			Object[] params = new Object[1];
			params[0] = lineId;
			stationInfoList = QUERY_RUNNER.query(conn,sql, new BeanListHandler<StationInfo>(StationInfo.class), params);
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				LOG.error(e.getMessage());
			}
		}
		return stationInfoList;
	}
	
	/**
	 * 根据线路id获取全部站点列表(包括不使用)
	 * @param lineId 线路Id
	 */
	public List<StationInfo> queryAllListStationInfoByLineId(String lineId){
		Connection conn = MyDataSource.getConnect();
		List<StationInfo> stationInfoList = null;
		try{
			String sql = "select * from pb_station where lineId=? order by sortNum";
			Object[] params = new Object[1];
			params[0] = lineId;
			stationInfoList = QUERY_RUNNER.query(conn,sql, new BeanListHandler<StationInfo>(StationInfo.class), params);
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				LOG.error(e.getMessage());
			}
		}
		return stationInfoList;
	}
	
	public boolean isBuyForStation(String stationId){
		Connection conn = null;
		StringBuilder sqlBuilder = null;
		Object[] params = null;
		QueryRunner queryRunner = null;
		try{
			conn = MyDataSource.getConnect();
			queryRunner = new QueryRunner();
			sqlBuilder = new StringBuilder();
			sqlBuilder.append(" select count(orderDate) as num from (SELECT lci.orderDate FROM lease_base_info AS lease LEFT JOIN line_business_order AS lbo ON lease.leaseOrderNo = lbo.leaseOrderId ");
			sqlBuilder.append(" LEFT JOIN line_class_info AS lci ON lbo.lineClassId = lci.lineClassId");
			sqlBuilder.append(" WHERE 1 = 1 AND (lease.ustation =? or lease.dstation =?) AND ispay = 1");
			sqlBuilder.append(" ORDER BY lci.orderDate DESC LIMIT 0,50) as lease where orderDate >= ?");
			params = new Object[3];
			String now = MyDate.formatTime(System.currentTimeMillis(), "yyyy-MM-dd");
			params[0] = stationId;
			params[1] = stationId;
			params[2] = now;
			Map<String,Object> resultMap = queryRunner.query(conn, sqlBuilder.toString(),new MapHandler(), params);
			long num = (Long)resultMap.get("num");
			if(num>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		return true;
	}
	
	/**
	 * 根据线路id获取站点列表
	 * @param lineId 线路Id
	 */
	public List<StationInfo> listStationInfoByLineId(Connection conn,String lineId)throws SQLException{
		List<StationInfo> stationInfoList = null;
		String sql = "select * from pb_station where lineId=? and available=0 order by sortNum";
		Object[] params = new Object[1];
		params[0] = lineId;
		stationInfoList = QUERY_RUNNER.query(conn,sql, new BeanListHandler<StationInfo>(StationInfo.class), params);
		return stationInfoList;
	}

	/**
	 * 同步站点（新增和修改）
	 * 
	 * @param lineStationEntity 站点信息
	 * 
	 * @return
	 */
	public void syncLineStation(StationInfo stationInfo) {
		Point point = BaiduAPIUtils.convertToGPS(stationInfo.getBaiduLng(), stationInfo.getBaiduLat());
		if (point != null) {
			stationInfo.setGpsLng(point.getLng());
			stationInfo.setGpsLat(point.getLat());
		} 
		Connection conn = MyDataSource.getConnect();
		try{
			String sql = null;
			Object[] params = null;
			sql = "select * from pb_station where lineId=? and name=? and available=0";
			params = new Object[2];
			params[0] = stationInfo.getLineId();
			params[1] = stationInfo.getName();
			StationInfo oldStation = QUERY_RUNNER.query(conn, sql, new BeanHandler<StationInfo>(StationInfo.class), params);
			if(oldStation!=null){
				stationInfo.setId(oldStation.getId());
			}
			if(StringUtils.isEmpty(stationInfo.getId())){
				stationInfo.setId(StringUtil.generateSequenceNo());
				sql = "insert into pb_station(id,name,arriveTime,lineId,baiduLng,baiduLat,gpsLng,gpsLat,type,sortNum,available,tipdesc) values(?,?,?,?,?,?,?,?,?,?,?,?)";
				params = new Object[12];
				params[0] = stationInfo.getId();
				params[1] = stationInfo.getName();
				params[2] = stationInfo.getArriveTime();
				params[3] = stationInfo.getLineId();
				params[4] = stationInfo.getBaiduLng();
				params[5] = stationInfo.getBaiduLat();
				params[6] = stationInfo.getGpsLng();
				params[7] = stationInfo.getGpsLat();
				params[8] = stationInfo.getType();
				params[9] = stationInfo.getSortNum();
				params[10] = stationInfo.getAvailable();
				params[11] = stationInfo.getTipdesc();
				QUERY_RUNNER.update(conn, sql, params);
			}else{
				sql = "update pb_station set name=?,arriveTime=?,lineId=?,baiduLng=?,baiduLat=?,gpsLng=?,gpsLat=?,type=?,sortNum=?,available=?,tipdesc=? where id=?";
				params = new Object[12];
				params[0] = stationInfo.getName();
				params[1] = stationInfo.getArriveTime();
				params[2] = stationInfo.getLineId();
				params[3] = stationInfo.getBaiduLng();
				params[4] = stationInfo.getBaiduLat();
				params[5] = stationInfo.getGpsLng();
				params[6] = stationInfo.getGpsLat();
				params[7] = stationInfo.getType();
				params[8] = stationInfo.getSortNum();
				params[9] = stationInfo.getAvailable();
				params[10] = stationInfo.getTipdesc();
				params[11] = stationInfo.getId();
				QUERY_RUNNER.update(conn,sql,params);
			}
		}catch(Exception e){
			LOG.error(e.getMessage());
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				LOG.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 同步线路信息
	 * 
	 * @param lineBaseInfo 线路信息
	 * 
	 */
	public void syncLineBaseInfo(LineBaseInfo lineBaseInfo){
		Connection conn = MyDataSource.getConnect();
		try {
			String sql = null;
			Object[] params = null;
			String cityName = null;
			if (!StringUtils.isEmpty(lineBaseInfo.getProvinceCode())
					&& !StringUtils.isEmpty(lineBaseInfo.getCityCode())) {
				StringBuffer citySql = new StringBuffer("select areaName from sys_area where arearCode=? and fdCode=?");
				List<SysArea> areaList = QUERY_RUNNER.query(conn, citySql.toString(),
						new BeanListHandler<SysArea>(SysArea.class), new Object[] {
								Integer.parseInt(lineBaseInfo.getCityCode()),
								Integer.parseInt(lineBaseInfo.getProvinceCode()) });
				cityName = CollectionUtils.isEmpty(areaList) ? "" : areaList.get(0).getAreaName();
			}
			lineBaseInfo.setCityName(cityName);
			if(StringUtils.isEmpty(lineBaseInfo.getLineBaseId())){
				lineBaseInfo.setLineBaseId(StringUtil.generateSequenceNo());
				sql = "insert into line_base_info (lineBaseId,lineName,lineType,lineSubType,provinceCode,"+
				"cityCode,lineStatus,orderPrice,createBy,createOn,updateBy,updateOn,lineKm,lineTime,cityName,lineManager,originalPrice,originalFlag)  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				params = new Object[18];
				params[0] = lineBaseInfo.getLineBaseId();
				params[1] = lineBaseInfo.getLineName();
				params[2] = lineBaseInfo.getLineType();
				params[3] = lineBaseInfo.getLineSubType();
				params[4] = lineBaseInfo.getProvinceCode();
				params[5] = lineBaseInfo.getCityCode();
				params[6] = lineBaseInfo.getLineStatus();
				params[7] = lineBaseInfo.getOrderPrice();
				params[8] = lineBaseInfo.getCreateBy();
				params[9] = lineBaseInfo.getCreateOn();
				params[10] = lineBaseInfo.getUpdateBy();
				params[11] = lineBaseInfo.getUpdateOn();
				params[12] = lineBaseInfo.getLineKm();
				params[13] = lineBaseInfo.getLineTime();
				params[14] = lineBaseInfo.getCityName();
				params[15] = lineBaseInfo.getLineManager();
				params[16] = lineBaseInfo.getOriginalPrice();
				params[17] = lineBaseInfo.getOriginalFlag();
			}else{
				sql = "update line_base_info set lineName=?,lineType=?,lineSubType=?,provinceCode=?,"+
				"cityCode=?,lineStatus=?,orderPrice=?,updateBy=?,updateOn=?,lineKm=?,"+
				"lineTime=?,cityName=?,lineManager=?,originalPrice=?,originalFlag=? where lineBaseId=?";
				params = new Object[16];
				params[0] = lineBaseInfo.getLineName();
				params[1] = lineBaseInfo.getLineType();
				params[2] = lineBaseInfo.getLineSubType();
				params[3] = lineBaseInfo.getProvinceCode();
				params[4] = lineBaseInfo.getCityCode();
				params[5] = lineBaseInfo.getLineStatus();
				params[6] = lineBaseInfo.getOrderPrice();
				params[7] = lineBaseInfo.getUpdateBy();
				params[8] = lineBaseInfo.getUpdateOn();
				params[9] = lineBaseInfo.getLineKm();
				params[10] = lineBaseInfo.getLineTime();
				params[11] = lineBaseInfo.getCityName();
				params[12] = lineBaseInfo.getLineManager();
				params[13] = lineBaseInfo.getOriginalPrice();
				params[14] = lineBaseInfo.getOriginalFlag();
				params[15] = lineBaseInfo.getLineBaseId();
			}
			QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 同步拆分线路信息
	 * 
	 * @param lineSplitInfo 拆分线路信息
	 * 
	 */
	public void syncLineSplitInfo(LineSplitInfo lineSplitInfo){
		super.finit(LINE_SPLIT_INFO);
		super.tableDao.updateData(lineSplitInfo,"sid");
	}
	
	/**
	 * 根据id获取线路信息
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @return
	 * 
	 */
	public LineBaseInfo getLineBaseInfoById(String lineBaseId){
		super.finit("line_base_info,sys_admin");
		String lineSql = "SELECT sa.userName,lb.* FROM line_base_info AS lb LEFT JOIN sys_admin AS sa ON lb.`lineManager`=sa.`userId` WHERE lb.`lineBaseId`= ? ";
		LineBaseInfo lineBaseInfo = super.tableDao.queryBean(LineBaseInfo.class, lineSql, new Object[]{lineBaseId});
		return lineBaseInfo;
	}
	
	/**
	 * 根据线路名称获取线路信息
	 * 
	 * @param lineName 线路名称
	 * 
	 * @return
	 * 
	 */
	public LineBaseInfo getLineBaseInfoByLineName(String lineName){
		super.finit("line_base_info");
		String sql = "select * from line_base_info where lineName=? and lineType=1 and (lineStatus=0 or lineStatus=1 or lineStatus=2 or lineStatus=3 or lineStatus=4)";
		Object[] params = new Object[1];
		params[0] = lineName;
		return super.tableDao.queryBean(LineBaseInfo.class, sql, params);
	}
	
	/**
	 * 获取线路列表
	 * @return
	 */
	public List<LineBaseInfo> getLineList(){
		super.finit("line_base_info");
		String sql = "select * from line_base_info";
		return super.tableDao.queryList(LineBaseInfo.class, sql);
	}
	
	/**
	 * 获取订单列表
	 * @return
	 */
	public List<LeaseBaseInfo> getLeaseBaseInfoList(){
		super.finit("lease_base_info");
		String sql = "select * from lease_base_info";
		return super.tableDao.queryList(LeaseBaseInfo.class, sql);
	}
	
	/**
	 * 删除拆分线路 根据上车点
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @param upStation 上车点Id
	 * 
	 * @return
	 */
	public int deleteLineSplitInfoByUpStation1(String lineBaseId,String upStation){
		Connection conn = MyDataSource.getConnect();
		try {
			String sql="delete from line_split_info where lineBaseId=? and bstation=?";
			Object[] params = new Object[2];
			params[0] = lineBaseId;
			params[1] = upStation;
			QUERY_RUNNER.update(conn, sql, params);
			return 1;
		} catch (SQLException e) {
			return 0;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 更新订单数据
	 * @param leaseBaseInfo
	 */
	public void syncLeaseBaseInfo(LeaseBaseInfo leaseBaseInfo){
		super.finit("lease_base_info");
		super.tableDao.updateData(leaseBaseInfo, "leaseOrderId");
	}
	
	/**
	 * 删除拆分线路 根据下车点
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @param downStation 下车点
	 * 
	 * @return
	 */
	public int deleteLineSplitInfoByDownStation(String lineBaseId,String downStation){
		Connection conn = MyDataSource.getConnect();
			try{String sql = "delete from line_split_info where lineBaseId=? and estation=?";
			Object[] params = new Object[2];
			params[0] = lineBaseId;
			params[1] = downStation;
			QUERY_RUNNER.update(conn, sql, params);
			return 1;
		}catch(SQLException e){
			return 0;
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 根据线路Id获取班次信息列表
	 * @param lineBaseId
	 * @return
	 */
	public List<LineClassEntity> getLineClassEntityListByLineBaseId(String lineBaseId){
		super.finit("line_class_info");
		String sql = "select * from line_class_info where lineBaseId=? and delFlag=0 order by orderDate desc limit 0,100";
		Object[] params = new Object[1];
		params[0] = lineBaseId;
		return super.tableDao.queryList(LineClassEntity.class, sql, params);
	}
	
	public String batchSplitInfo() {
		StringBuffer message = new StringBuffer();
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		Object[] params = null;
		try {
			sql = "select * from line_base_info";
			List<LineBaseInfo> lineBaseInfoList = QUERY_RUNNER.query(conn, sql,new BeanListHandler<LineBaseInfo>(LineBaseInfo.class));
			if (lineBaseInfoList != null && !lineBaseInfoList.isEmpty()) {
				for (LineBaseInfo lineBaseInfo : lineBaseInfoList) {
					// 初始化line_split_info
					List<String> upStationList = new ArrayList<String>();
					List<String> downStationList = new ArrayList<String>();
					List<StationInfo> stationInfoList = listStationInfoByLineId(lineBaseInfo.getLineBaseId());
					for(StationInfo stationInfo : stationInfoList){
						if(stationInfo.getType()==1){
							upStationList.add(stationInfo.getId());
						}else if(stationInfo.getType()==0){
							downStationList.add(stationInfo.getId());
						}
					}
					for (String upStation : upStationList) {
						for (String downStation : downStationList) {
							LineSplitInfo lineSplitInfo = new LineSplitInfo();
							lineSplitInfo.setLinebaseid(lineBaseInfo.getLineBaseId());
							lineSplitInfo.setBstation(upStation);
							lineSplitInfo.setEstation(downStation);
							if (lineBaseInfo.getLineStatus().equals("3")) {
								lineSplitInfo.setIswork("1");
							} else {
								lineSplitInfo.setIswork("0");
							}
							sql = "select * from line_split_info where lineBaseId=? and bstation=? and estation=?";
							params = new Object[3];
							params[0] = lineBaseInfo.getLineBaseId();
							params[1] = lineSplitInfo.getBstation();
							params[2] = lineSplitInfo.getEstation();
							LineSplitInfo oldLineSplitInfo = QUERY_RUNNER.query(conn, sql, new BeanHandler<LineSplitInfo>(LineSplitInfo.class), params);
							if (oldLineSplitInfo == null) {
								sql = "insert into line_split_info(sid,bstation,estation,linebaseid,iswork) values(?,?,?,?,?)";
								params = new Object[5];
								params[0] = StringUtil.generateSequenceNo();
								params[1] = lineSplitInfo.getBstation();
								params[2] = lineSplitInfo.getEstation();
								params[3] = lineSplitInfo.getLinebaseid();
								if (lineBaseInfo.getLineStatus().equals("3")) {
									params[4] = "1";
								} else {
									params[4] = "0";
								}
								QUERY_RUNNER.update(conn, sql, params);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				LOG.info(e.getMessage(),e);
			}
		}
		return message.toString();
	}
	
	public void batchSplitInfoByLineId(String lineBaseId) {
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		Object[] params = null;
		try {
			sql = "select * from line_base_info where lineBaseId='"
					+ lineBaseId + "'";
			List<LineBaseInfo> lineBaseInfoList = QUERY_RUNNER.query(conn, sql,
					new BeanListHandler<LineBaseInfo>(LineBaseInfo.class));
			if (lineBaseInfoList != null && !lineBaseInfoList.isEmpty()) {
				for (LineBaseInfo lineBaseInfo : lineBaseInfoList) {
					// 初始化line_split_info
					List<String> upStationList = new ArrayList<String>();
					List<String> downStationList = new ArrayList<String>();
					sql = "update line_split_info set isWork=0 where lineBaseId=?";
					params = new Object[1];
					params[0] = lineBaseId;
					conn = MyDataSource.getConnect();
					QUERY_RUNNER.update(conn,sql,params);
					List<StationInfo> stationInfoList = listStationInfoByLineId(conn,lineBaseInfo.getLineBaseId());
					for(StationInfo stationInfo : stationInfoList){
						if(stationInfo.getType()==1){
							upStationList.add(stationInfo.getId());
						}else if(stationInfo.getType()==0){
							downStationList.add(stationInfo.getId());
						}
					}
					for (String upStation : upStationList) {
						for (String downStation : downStationList) {
							LineSplitInfo lineSplitInfo = new LineSplitInfo();
							lineSplitInfo.setLinebaseid(lineBaseInfo
									.getLineBaseId());
							lineSplitInfo.setBstation(upStation);
							lineSplitInfo.setEstation(downStation);
							if (lineBaseInfo.getLineStatus().equals("3")) {
								lineSplitInfo.setIswork("1");
							} else {
								lineSplitInfo.setIswork("0");
							}
							sql = "select * from line_split_info where lineBaseId=? and bstation=? and estation=?";
							params = new Object[3];
							params[0] = lineBaseInfo.getLineBaseId();
							params[1] = lineSplitInfo.getBstation();
							params[2] = lineSplitInfo.getEstation();
							conn = MyDataSource.getConnect();
							LineSplitInfo oldLineSplitInfo = QUERY_RUNNER.query(
									conn, sql, new BeanHandler<LineSplitInfo>(
											LineSplitInfo.class), params);
							if (oldLineSplitInfo == null) {
								sql = "insert into line_split_info(sid,bstation,estation,linebaseid,iswork) values(?,?,?,?,?)";
								params = new Object[5];
								params[0] = StringUtil.generateSequenceNo();
								params[1] = lineSplitInfo.getBstation();
								params[2] = lineSplitInfo.getEstation();
								params[3] = lineSplitInfo.getLinebaseid();
								if (lineBaseInfo.getLineStatus().equals("3")) {
									params[4] = "1";
								} else {
									params[4] = "0";
								}
								QUERY_RUNNER.update(conn, sql, params);
							}else{
								sql = "update line_split_info set bstation=?,estation=?,linebaseid=?,iswork=? where sid=?";
								params = new Object[5];
								params[0] = lineSplitInfo.getBstation();
								params[1] = lineSplitInfo.getEstation();
								params[2] = lineSplitInfo.getLinebaseid();
								if (lineBaseInfo.getLineStatus().equals("3")) {
									params[3] = "1";
								} else {
									params[3] = "0";
								}
								params[4] = oldLineSplitInfo.getSid();
								QUERY_RUNNER.update(conn, sql, params);
							}
						}
						/**
						if (lineBaseInfo.getLineStatus().equals("3")) {
							sql = "update line_split_info set isWork=2  where lineBaseId=? and isWork=0";
							params = new Object[1];
							params[0] = lineBaseId;
							QUERY_RUNNER.update(conn,sql,params);
						}
						*/
					}
				}
			}
		} catch (Exception e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 根据条件查询班次信息
	 * @param lineBaseId  线路id
	 * @param orderStartTime 发车时间
	 * @param orderDate 班次日期
	 * @return
	 */
	public LineClassEntity getLineClassEntityByCondition(String lineBaseId,String orderStartTime,String orderDate){
		String sql = "select * from line_class_info where lineBaseId=? and delFlag=0 and orderStartTime=? and orderDate=?";
		Object[] params = new Object[3];
		params[0] = lineBaseId;
		params[1] = orderStartTime;
		params[2] = orderDate;
		return super.tableDao.queryBean(LineClassEntity.class, sql, params);
	}
	
	/**
	 * 同步班次信息
	 * 
	 * @param lineClassEntity 班次信息
	 * 
	 */
	public void syncLineClassInfo(LineClassEntity lineClassEntity){
		super.finit("line_class_info");
		super.tableDao.updateData(lineClassEntity, "lineClassId");
	}
	
	/**
	 * 获取最大日期的班次
	 * @param lineBaseId 线路id
	 * @return
	 */
	public LineClassEntity getMaxDateClassEntity(String lineBaseId){
		Connection conn = null;
		LineClassEntity lineClassEntity = null;
		try{
			conn = MyDataSource.getConnect();
			String sql = "select * from line_class_info where lineBaseId=? and delFlag=0 order by orderDate desc limit 0,1";
			Object[] params = new Object[1];
			params[0] = lineBaseId;
			lineClassEntity = QUERY_RUNNER.query(conn, sql, new BeanHandler<LineClassEntity>(LineClassEntity.class), params);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		return lineClassEntity;
	}
	
	public LineClassEntity getByLineBaseIdAndOrderDate(String lineBaseId,String orderDate){
		Connection conn = null;
		LineClassEntity lineClassEntity = null;
		try{
			conn = MyDataSource.getConnect();
			String sql = "select * from line_class_info where lineBaseId=? and delFlag=0 and orderDate=? limit 0,1";
			Object[] params = new Object[2];
			params[0] = lineBaseId;
			params[1] = orderDate;
			lineClassEntity = QUERY_RUNNER.query(conn, sql, new BeanHandler<LineClassEntity>(LineClassEntity.class), params);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		return lineClassEntity;
	}
	
	public List<LineBaseInfo> listAutoPutTicketLine(){
		Connection conn = null;
		List<LineBaseInfo> lineBaseInfoList = null;
		try{
			conn = MyDataSource.getConnect();
			String sql = "select * from line_base_info where lineType=1 and autoPutTicket is not null and autoPutTicket!='false'";
			Object[] params = new Object[0];
			lineBaseInfoList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<LineBaseInfo>(LineBaseInfo.class), params);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
		return lineBaseInfoList;
	}
	
	/**
	 * 同步班次列表
	 * 
	 * @param lineClassEntityList 班次列表
	 * 
	 */
	public void syncLineClassInfoList(List<LineClassEntity> lineClassEntityList){
		for(LineClassEntity lineClassEntity : lineClassEntityList){
			syncLineClassInfo(lineClassEntity);
		}
	}
	
	/**
	 * 删除班次信息
	 * @param lineBaseId 线路id
	 * @param orderStartTime 发车时间
	 * @param orderDate 排班日期
	 */
	public void deleteClassInfoList(String lineBaseId, String orderStartTime, String orderDate) {
		Connection conn = null;
		try {
			LineClassEntity lineClassEntity = getLineClassEntityByCondition(lineBaseId, orderStartTime, orderDate);
			conn = MyDataSource.getConnect();
			String sql = "select count(*) as num from line_business_order where lineClassId=? and status=0";
			Object[] params = new Object[1];
			params[0] = lineClassEntity.getLineClassId();
			Map<String, Object> map = QUERY_RUNNER.query(conn, sql, new MapHandler(),params);
			int num = ((Long)map.get("num")).intValue();
			//if(num==0){
				sql = "update line_class_info set delFlag=1 where lineBaseId=? and orderStartTime=? and orderDate=?";
				params = new Object[3];
				params[0] = lineBaseId;
				params[1] = orderStartTime;
				params[2] = orderDate;
				QUERY_RUNNER.update(conn, sql, params);
			//}
		} catch (Exception e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 更新班次的价格
	 * 
	 * @param lineClassEntityList 班次信息列表
	 * 
	 */
	public void updateLineClassPrice(List<LineClassEntity> lineClassEntityList){
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		try {
			sql = "update line_class_info set price=? where lineBaseId=? and orderStartTime=? and orderDate=?";
			Object[][] params = new Object[lineClassEntityList.size()][4];
			int index = 0;
			for(LineClassEntity lineClassEntity : lineClassEntityList){
				params[index] = new Object[4];
				params[index][0] = lineClassEntity.getPrice();
				params[index][1] = lineClassEntity.getLineBaseId();
				params[index][2] = lineClassEntity.getOrderStartTime();
				params[index][3] = lineClassEntity.getOrderDate();
				index++;
			}
			QUERY_RUNNER.batch(conn, sql, params);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 更新班次的座位数
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @param seats 座位数
	 * 
	 */
	public void updateLineClassSeats(String lineBaseId,String seats){
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		try {
			sql = "update line_class_info set orderSeats=? where lineBaseId=? and delFlag=0 and orderDate >?";
			Object[] params = new Object[3];
			params[0] = seats;
			params[1] = lineBaseId;
			params[2] = MyDate.formatTime(System.currentTimeMillis(), "yyyy-MM-dd");
			QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 添加专车线路关联信息
	 * 
	 * @param companyLine
	 */
	public void addCompanyLine(CompanyLine companyLine){
		CompanyLine oldCompanyLine = getCompanyLine(companyLine);
		if(oldCompanyLine==null){
			super.finit("company_line");
			super.tableDao.updateData(companyLine, "companyLineId");
		}
	}
	
	/**
	 * 获取专车线路关信息
	 * 
	 * @param companyLine
	 * 
	 * @return
	 * 
	 */
	public CompanyLine getCompanyLine(CompanyLine companyLine){
		super.finit("company_line");
		String sql = "select * from company_line where lineBaseId=? and companyId=?";
		Object[] params = new Object[2];
		params[0] = companyLine.getLineBaseId();
		params[1] = companyLine.getCompanyId();
		return super.tableDao.queryBean(CompanyLine.class, sql, params);
	}
	
	/**
	 * 获取专车线路相关信息
	 * 
	 * @param lineBaseId 线路id
	 * 
	 * @return
	 * 
	 */
	public CompanyLine getCompanyLine(String lineBaseId){
		super.finit("company_line");
		String sql = "select * from company_line where lineBaseId=?";
		Object[] params = new Object[1];
		params[0] = lineBaseId;
		return super.tableDao.queryBean(CompanyLine.class, sql, params);
	}
	
	/**
	 * 根据id获取公司信息
	 * 
	 * @param companyId 公司id
	 * 
	 * @return
	 * 
	 */
	public Company getCompanyById(String companyId){
		super.finit("company");
		String sql = "select * from company where companyId=?";
		Object[] params = new Object[1];
		params[0] = companyId;
		return super.tableDao.queryBean(Company.class, sql, params);
	}
	
	/**
	 * 变更线路状态
	 * 
	 * @param lineBaseId 线路id
	 * 
	 * @param status 线路状态
	 * 
	 */
	public void updateLineStatus(String lineBaseId,String status){
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		try {
				sql = "update line_base_info set lineStatus=? where lineBaseId=?";
				Object[] params = new Object[2];
				params[0]=status;
				params[1]=lineBaseId;
				QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	public void turnOnStationByLineId(String lineBaseId){
		Connection conn = MyDataSource.getConnect();
		try{
			String sql = "update pb_station set available=? where lineId=?";
			Object[] params = new Object[2];
			params[0] = "0";
			params[1] = lineBaseId;
			QUERY_RUNNER.update(conn,sql,params);
		}catch(SQLException e){
			LOG.error(e.getMessage(),e);
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				LOG.error(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 获取商家列表
	 * 
	 * @param parseInt 页码
	 * 
	 * @param pageSize 一页大小
	 * 
	 * @param query 查询条件
	 * 
	 * @return
	 * 
	 */
	public PageBean getMgrBusinessPageBean(int parseInt, int pageSize, QueryHelper query) {
		String pageSql = query.getListSql(); // >> 获取分页SQL
		Object[] pageParams = query.getObjectArrayParams(); // >>  对应的参数数组
		List<MgrBusinessEntity> recordList = super.tableDao.queryList(MgrBusinessEntity.class, pageSql, pageParams);
		String countSql = query.getCountSql(); // >>  查询总数的SQL
		int recordCount = super.tableDao.queryCountByCustomSqlAndParams(countSql, pageParams); // >>  得到总数
		PageBean pageBean = new PageBean(parseInt, pageSize, recordCount, recordList);
		return pageBean;
	}
	
	/**
	 * 更新线路的商家
	 * 
	 * @param lineBaseId 线路id
	 *  
	 * @param businessId 商家id
	 * 
	 */
	public void setLineBusiness(String lineBaseId,String businessId){
		LineBaseInfo line = getLineBaseInfoById(lineBaseId);
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		try {
				sql = "update line_base_info set businessId=?,lineStatus=? where lineBaseId=?";
				Object[] params = new Object[3];
				params[0]=businessId;
				if(line!=null){
					String oldBusinessId = line.getBusinessId();
					if(!StringUtils.isEmpty(businessId)){
						if(!businessId.equals(oldBusinessId)){
							params[1] = "1";
						}else{
							params[1] = oldBusinessId;
						}
					}else{
						params[1] = oldBusinessId;
					}
				}else{
					params[1] = "0";
				}
				params[2]=lineBaseId;
				QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
		setLineClassBusinessId(lineBaseId,businessId);
	}
	
	/**
	 * 修改线路班次的商家id
	 * 
	 * @param lineBaseId 线路id
	 *  
	 * @param businessId 商家id
	 * 
	 */
	public void setLineClassBusinessId(String lineBaseId,String businessId){
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		try {
				sql = "update line_class_info set lineBusinessId=? where lineBaseId=?";
				Object[] params = new Object[2];
				params[0]=businessId;
				params[1]=lineBaseId;
				QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 获取排班信息
	 * 
	 * @param lineBaseId 线路id
	 * 
	 * @return
	 * 
	 */
	public List<LineClassScheduleVo> getLineClassScheduleVoList(String lineBaseId){
		super.finit("line_class_info");
		String sql = "select lci.lineClassId,lci.orderStartTime,lci.orderSeats,lci.orderDate,lci.lineBusinessId,lci.lineBaseId,lci.driverId,lci.vehicleId,lci.delFlag,lci.price,di.driverName,vi.vehicleNumber,vi.vehicleSeats "+
			" from line_class_info lci left join driver_info di on lci.driverId=di.driverId left join vehicle_info vi on lci.vehicleId=vi.vehicleId where lci.lineBaseId=?";
		Object[] params = new Object[1];
		params[0] = lineBaseId;
		return super.tableDao.queryList(LineClassScheduleVo.class, sql, params);
	}
	
	/**
	 * 更新拆分线路的状态
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @param status 状态
	 * 
	 */
	public void setLineSplitStatus(String lineBaseId,String status){
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		try {
				sql = "update line_split_info set isWork=? where lineBaseId=?";
				Object[] params = new Object[2];
				params[0]=status;
				params[1]=lineBaseId;
				QUERY_RUNNER.update(conn, sql, params);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 获取拆分线路列表根据线路Id
	 * 
	 * @param lineBaseId 线路Id
	 * 
	 * @return
	 * 
	 */
	public List<LineSplitInfo> getLineSplitByLineBaseId(String lineBaseId){
		super.finit(LINE_SPLIT_INFO);
		String sql = "select * from line_split_info where lineBaseId=?";
		Object[] params = new Object[1];
		params[0] = lineBaseId;
		return super.tableDao.queryList(LineSplitInfo.class, sql, params);
	}
	
	/**
	 * 获取拆分线路id
	 * @param lineBaseId 线路Id
	 * @param uStation 上车点
	 * @param dStation 下车点
	 * @return
	 */
	public LineSplitInfo getLineSplitByCondition(String lineBaseId,String uStation,String dStation){
		super.finit(LINE_SPLIT_INFO);
		String sql = "select * from line_split_info where lineBaseId=? and bstation=? and estation=?";
		Object[] params = new Object[3];
		params[0] = lineBaseId;
		params[1] = uStation;
		params[2] = dStation;
		return super.tableDao.queryBean(LineSplitInfo.class, sql, params);
	}
	
	/**
	 * 获取全部拆分线路
	 * @return
	 */
	public List<LineSplitInfo> getAllLineSplitInfoList(){
		super.finit(LINE_SPLIT_INFO);
		String sql = "select * from line_split_info";
		return super.tableDao.queryList(LineSplitInfo.class, sql);
	}
	
	/**
	 * 同步订单的拆分信息
	 */
	public void syncLeaseBaseSplitInfo(){
		//用作数据迁移，暂时保留
		Connection conn = MyDataSource.getConnect();
		String sql = null;
		try {
			sql = "update lease_base_info b set b.ustation = (SELECT  SUBSTRING_INDEX(a.stationInfoes, ',', 1)  FROM line_base_info a where a.lineBaseId = b.linebaseid);";
			QUERY_RUNNER.update(conn, sql);
			sql = "UPDATE lease_base_info b SET b.dstation = (SELECT  SUBSTRING_INDEX(a.stationInfoes, ',', -1)  FROM line_base_info a WHERE a.lineBaseId = b.linebaseid);";
			QUERY_RUNNER.update(conn, sql);
			sql = "UPDATE lease_base_info b SET b.slineId = (select a.sid from line_split_info a where a.bstation = b.ustation and a.estation = b.dstation and a.linebaseid = b.lineBaseId);";
			QUERY_RUNNER.update(conn , sql);
		} catch (SQLException e) {
			LOG.info(e.getMessage(),e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 
	 * 根据条件获取站点信息
	 * @param stationName 站点名称
	 * @param lng 经度
	 * @param lat 纬度
	 * @return
	 */
	public StationInfo getStationEntityByCondition(String stationName,String lng,String lat){
		super.finit("pb_station");
		String sql = "select * from pb_station where name=? and baiduLng=? and baiduLat=?";
		Object[] params = new Object[3];
		params[0] = stationName;
		params[1] = lng;
		params[2] = lat;
		return super.tableDao.queryBean(StationInfo.class, sql, params);
	}
	
	/**
	 * 拆分站点
	 */
	public void splitAllLineStation()throws Exception{
		Connection conn = MyDataSource.getConnect();
		try{
			handleSplitAllStation(conn);
		}catch(Exception e){
			LOG.info(e.getMessage(),e);
			throw e;
		}finally{
			try{
				conn.close();
			}catch(Exception e){
				LOG.info(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 处理拆分
	 * @param conn
	 * @throws Exception
	 */
	private void handleSplitAllStation(Connection conn)throws Exception{
		List<LineBaseInfo> lineList = listAllLine(conn);
		Map<String,LineStationInfo> stationMap = new HashMap<String,LineStationInfo>();
		Map<String,Map<String,String>> stationTransMap = new HashMap<String,Map<String,String>>();
		for(LineBaseInfo lineBaseInfo : lineList){
			splitStation(conn, lineBaseInfo,stationMap,stationTransMap);
		}
	}
	
	private void syncLease(Connection conn,String lineBaseId,String oldStation,String newStation)throws Exception{
		String sql = "update lease_base_info set ustation=? where lineBaseId=? and ustation=?";
		Object[] params = new Object[3];
		params[0] = newStation;
		params[1] = lineBaseId;
		params[2] = oldStation;
		QUERY_RUNNER.update(conn, sql, params);
		sql = "update lease_base_info set dstation=? where lineBaseId=? and dstation=?";
		params[0] = newStation;
		params[1] = lineBaseId;
		params[2] = oldStation;
		QUERY_RUNNER.update(conn,sql,params);
	}
	
	private void syncLineSplit(Connection conn,String lineBaseId,String oldStation,String newStation)throws Exception{
		String sql = "update line_split_info set bstation=? where lineBaseId=? and bstation=?";
		Object[] params = new Object[3];
		params[0] = newStation;
		params[1] = lineBaseId;
		params[2] = oldStation;
		QUERY_RUNNER.update(conn,sql,params);
		sql = "update line_split_info set estation=? where lineBaseId=? and estation=?";
		params = new Object[3];
		params[0] = newStation;
		params[1] = lineBaseId;
		params[2] = oldStation;
		QUERY_RUNNER.update(conn,sql,params);
	}
	
	
	/**
	 * 拆分一条线路的站点
	 * @param conn
	 * @param lineBaseInfo
	 */
	private void splitStation(Connection conn,LineBaseInfo lineBaseInfo,Map<String,LineStationInfo> stationMap,Map<String,Map<String,String>> stationTransMap)throws Exception{
		//用于数据迁移，暂时保留stationInfoes
		String stationInfoes = lineBaseInfo.getStationInfoes();
		Map<String,String> lineMap = new HashMap<String,String>();
		stationTransMap.put(lineBaseInfo.getLineBaseId(), lineMap);
		if(!StringUtils.isEmpty(stationInfoes)){
			String[] stationInfoesArr = stationInfoes.split(",");
			for(int index=0;index<stationInfoesArr.length;index++){
				String oldStationId = stationInfoesArr[index];
				LineStationInfo oldStationInfo = getOldStationById(conn, oldStationId, stationMap);
				if(oldStationInfo!=null){
					boolean isExist = existStationByLineIdAndName(conn,lineBaseInfo.getLineBaseId(),oldStationInfo.getStationName());
					if(!isExist){
						StationInfo stationInfo = new StationInfo();
						stationInfo.setId(StringUtil.generateSequenceNo());
						stationInfo.setName(oldStationInfo.getStationName());
						stationInfo.setLineId(lineBaseInfo.getLineBaseId());
						if(!StringUtils.isEmpty(oldStationInfo.getLoni())){
							stationInfo.setBaiduLng(Double.valueOf(oldStationInfo.getLoni()));
						}
						if(!StringUtils.isEmpty(oldStationInfo.getLati())){
							stationInfo.setBaiduLat(Double.valueOf(oldStationInfo.getLati()));
						}
						if(!StringUtils.isEmpty(oldStationInfo.getLoni_gps())){
							stationInfo.setGpsLng(Double.valueOf(oldStationInfo.getLoni_gps()));
						}
						if(!StringUtils.isEmpty(oldStationInfo.getLati_gps())){
							stationInfo.setGpsLat(Double.valueOf(oldStationInfo.getLati_gps()));
						}
						stationInfo.setAvailable(0);
						stationInfo.setSortNum(index);
						stationInfo.setType(culStationType(stationInfoesArr,lineBaseInfo,oldStationId));
						addStation(conn,stationInfo);
						lineMap.put(oldStationId, stationInfo.getId());
						// 同步订单的站点数据
						syncLease(conn,lineBaseInfo.getLineBaseId(),oldStationId,stationInfo.getId());
						// 同步线路拆分表
						syncLineSplit(conn,lineBaseInfo.getLineBaseId(),oldStationId,stationInfo.getId());
					}
				}
			}
		}
	}
	
	private boolean existStationByLineIdAndName(Connection conn,String lineId,String name)throws Exception{
		String sql = "select * from pb_station where lineId=? and name=?";
		Object[] params = new Object[2];
		params[0] = lineId;
		params[1] = name;
		return QUERY_RUNNER.query(conn, sql, new BeanHandler<StationInfo>(StationInfo.class), params) !=null ? true : false;
	}
	
	private void addStation(Connection conn,StationInfo stationInfo)throws Exception{
		String sql = "insert into pb_station(id,name,arriveTime,lineId,baiduLng,baiduLat,gpsLng,gpsLat,available,sortNum,type,tipdesc) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = new Object[11];
		params[0] = stationInfo.getId();
		params[1] = stationInfo.getName();
		params[2] = stationInfo.getArriveTime();
		params[3] = stationInfo.getLineId();
		params[4] = stationInfo.getBaiduLng();
		params[5] = stationInfo.getBaiduLat();
		params[6] = stationInfo.getGpsLng();
		params[7] = stationInfo.getGpsLat();
		params[8] = stationInfo.getAvailable();
		params[9] = stationInfo.getSortNum();
		params[10] = stationInfo.getType();
		params[11] = stationInfo.getTipdesc();
		QUERY_RUNNER.update(conn, sql, params);
	}
	
	private int culStationType(String[] stationInfoesArr,LineBaseInfo lineBaseInfo,String stationId){
		//用于数据迁移，暂时保留
		String stationInfoesMask = lineBaseInfo.getStationInfoesMask();
		if(!StringUtils.isEmpty(stationInfoesMask)){
			String[] stationInfoesMaskArr = stationInfoesMask.split(",");
			for(int index=0;index<stationInfoesArr.length;index++){
				if(stationInfoesArr[index].equals(stationId)){
					String mask = stationInfoesMaskArr[index];
					if("1".equals(mask)){
						return 1;
					}else if("0".equals(mask)){
						return 0;
					}else{
						return 2;
					}
				}
			}
		}
		return 2;
	}
	
	private List<LineBaseInfo> listAllLine(Connection conn)throws Exception{
		String sql = "select * from line_base_info where lineStatus!=5";
		return QUERY_RUNNER.query(conn, sql, new BeanListHandler<LineBaseInfo>(LineBaseInfo.class));
	}
	
	private LineStationInfo getOldStationById(Connection conn,String id,Map<String,LineStationInfo> stationMap)throws Exception{
		LineStationInfo oldStation = null;
		if(stationMap.get(id)==null){
			String sql = "select * from line_station_info where stationInfoId=?";
			Object[] params = new Object[1];
			params[0] = id;
		    oldStation = QUERY_RUNNER.query(conn, sql, new BeanHandler<LineStationInfo>(LineStationInfo.class),params);
		    stationMap.put(id,oldStation);
		}else{
			oldStation = stationMap.get(id);
		}
		return oldStation;
	}
	
	/**修改商户**/
	public String changeBusiness(String oldBusiness,String newBusiness,String seatCount,String lineId){
		//更改线路供应商ID
		super.finit("line_base_info");
		String sql_line = "update line_base_info set businessId = ?,lineStatus=? where lineBaseId = ?";
		args = new Object[3];
		args[0] = newBusiness;
		args[1] = "1";
		args[2] = lineId;
		tableDao.executeSQL(sql_line,args);
		
		//更改班次信息
		super.finit("line_class_info");
		String sql_class = "update line_class_info set lineBusinessId = ?,driverId = '',vehicleId = '',orderSeats = ? WHERE orderDate >= '"+MyDate.getMyDate3()+"' AND linebaseid = ? AND delFlag = '0'";
		args = new Object[3];
		args[0] = newBusiness;
		args[1] = seatCount;
		args[2] = lineId;
		tableDao.executeSQL(sql_class,args);
		
		//更改车票信息
		super.finit("line_business_order");
		String sql_order = "UPDATE line_business_order SET businessId = ? WHERE lineClassId IN (SELECT lineclassid FROM line_class_info WHERE orderDate >= '"+MyDate.getMyDate3()+"' AND linebaseid = ? AND delFlag = '0')";
		args = new Object[2];
		args[0] = newBusiness;
		args[1] = lineId;
		tableDao.executeSQL(sql_order,args);
		
		return "1";
	}
	
	/**
	 * 获取线路的班次的最大日期
	 * @param lineId 线路id
	 * @return
	 */
	public String getMaxOrderDateByLineId(String lineId){
		Connection conn = null;
		String maxOrderDate = null;
		try{
			conn = MyDataSource.getConnect();
			String sql = "select orderDate from line_class_info where lineBaseId=? order by orderDate desc limit 0,1";
			Object[] params = new Object[1];
			params[0] = lineId;
			LineClassEntity lineClassEntity = QUERY_RUNNER.query(conn, sql, new BeanHandler<LineClassEntity>(LineClassEntity.class), params);
			if(lineClassEntity!=null){
				maxOrderDate = lineClassEntity.getOrderDate();
			}
		}catch(SQLException e){
			LOG.info(e.getMessage(),e);
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}catch(Exception e){
					LOG.info(e.getMessage(),e);
				}
			}
		}
		return maxOrderDate;
	}
	
	/**
	 * 获取某条线路今天起排的班次列表
	 * @param lineBaseId 线路列表
	 * @return
	 */
	public List<LineClassEntity> getLineClassListAfterToday(String lineBaseId){
		Connection conn = null;
		List<LineClassEntity> lineClassEntityList = null;
		try{
			conn = MyDataSource.getConnect();
			String sql = "select * from line_class_info where lineBaseId=? and orderDate >= ? and delFlag=0";
			Object[] params = new Object[2];
			params[0] = lineBaseId;
			params[1] = MyDate.formatTime(System.currentTimeMillis(), "yyyy-MM-dd");
			lineClassEntityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<LineClassEntity>(LineClassEntity.class), params);
		}catch(SQLException e){
			LOG.info(e.getMessage(),e);
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}catch(Exception e){
					LOG.info(e.getMessage(),e);
				}
			}
		}
		return lineClassEntityList;
	}
	
	/**
	 * 修改班次价格
	 * @param lineClassId 班次Id
	 * @param price 价格
	 */
	public void updateClassPrice(String lineClassId,String price){
		Connection conn = null;
		try{
			conn = MyDataSource.getConnect();
			String sql = "update line_class_info set price=? where lineClassId=?";
			Object[] params = new Object[2];
			params[0] = price;
			params[1] = lineClassId;
			QUERY_RUNNER.update(conn, sql, params);
		}catch(SQLException e){
			LOG.info(e.getMessage(),e);
		}finally{
			if(conn!=null){
				try{
					conn.close();
				}catch(Exception e){
					LOG.info(e.getMessage(),e);
				}
			}
		}
	}

	/**
	 * 记录线路操作记录
	 * @param operaVo
	 * @return
	 */
	public int logLineOperate(LineOperateLogVo operaVo) {
		super.finit("line_operation_log");
		super.tableDao.updateData(operaVo, "operationId");
		return 0;
	}

	/**根据用户名查找运营人员信息
	 * @param lineManager
	 * @return
	 */
	public SysAdminVo getlineManagerInfoByName(String lineManager) {
		super.finit("sys_admin");
		String lineMgeSql = "SELECT * FROM `sys_admin` WHERE userName=? AND deleteFlag=0";
		return super.tableDao.queryBean(SysAdminVo.class, lineMgeSql, new Object[]{lineManager});
	}
}
