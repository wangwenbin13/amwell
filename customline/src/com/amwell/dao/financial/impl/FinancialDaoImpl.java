package com.amwell.dao.financial.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.RePeat;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;
import com.amwell.dao.IStationInfoDao;
import com.amwell.dao.financial.IFinancialDao;
import com.amwell.entity.FinancialEntity;
import com.amwell.entity.LineClassCount;
import com.amwell.entity.Page;
import com.amwell.entity.ReportAvgMoney;
import com.amwell.entity.Search;
import com.amwell.vo.AppVo_Comp;
import com.amwell.vo.StationInfo;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.app.bean.AppVo_2;
/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 新版财务相关
 */
@Repository(value = "financialDaoImpl")
public class FinancialDaoImpl extends  DaoSupport implements IFinancialDao {
	
	@Autowired
	private IStationInfoDao stationInfoDao;
	
	/**计算昨天的收入**/
	public synchronized int countInCome(String yesterday) {
		Connection conn = null;
		int statu = 0;
		String sql = null;
		/**每页记录数**/
		int pageSize = 200;
		/**起始页**/
		int page = 1;
		/**总页数**/
		int totalPage = 1;
		/**总记录数**/
		int totalCount = 0;
		try {
			conn = MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			//先判断该天的数据是否已经统计,如果已经统计了,就不需要再统计了----只需要根据日期的数据是否存在
			sql = " SELECT COUNT(1) FROM stat_financial WHERE orderDate = ? ";
			args = new Object[1];
			args[0] = yesterday;
			Long count = (Long)qr.query(conn,sql, args, new ScalarHandler(1));
			if(count>0L){
				return 0;
			}
			System.out.println(yesterday);
			
			String selFields = "SELECT lineclass.lineClassId,"
						+ "lineclass.orderDate, lineclass.orderSeats, lineclass.orderStartTime, mgr.brefName, veh.vehicleId, veh.vehicleNumber, dri.driverId, dri.driverName, linb.lineName, "
						+ "linb.lineBaseId, linb.cityCode, linb.cityName, linb.provinceCode, dri.telephone ";
			String selTable = "FROM line_class_info AS lineclass LEFT JOIN line_business_order AS lineOrder ON lineclass.lineClassId = lineOrder.lineClassId "
						+ "LEFT JOIN line_base_info AS linb ON linb.lineBaseId = lineclass.lineBaseId "
						+ "LEFT JOIN mgr_business AS mgr ON mgr.businessId = lineOrder.businessId "
						+ "LEFT JOIN vehicle_info AS veh ON veh.vehicleId = lineclass.vehicleId "
						+ "LEFT JOIN driver_info AS dri ON dri.driverId = lineclass.driverId LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = lineOrder.leaseOrderId "
						+ "WHERE lineclass.orderDate = ?  AND linb.lineType = 1 AND linb.lineSubType = 0 AND linb.lineStatus = 3 AND lineclass.delFlag = 0 ";
			
			//考虑分页插入数据---减少一次插入的数据量
			
			sql = " SELECT COUNT(*) FROM ( SELECT lineOrder.localId FROM line_class_info AS lineclass LEFT JOIN line_business_order AS lineOrder ON lineclass.lineClassId = lineOrder.lineClassId LEFT JOIN line_base_info AS linb ON linb.lineBaseId = lineclass.lineBaseId WHERE lineclass.orderDate = ?  AND linb.lineType = 1 AND linb.lineSubType = 0 AND linb.lineStatus = 3 AND lineclass.delFlag = 0 AND lineOrder.`status` IN ('0', '2') GROUP BY lineOrder.lineClassId ) AS a  ";
			args = new Object[1];
			args[0] = yesterday;
			count = (Long)qr.query(conn,sql, args, new ScalarHandler(1));
			totalCount = count.intValue();
			totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
			
			for(page =1;page<=totalPage;page++){
				//统计当天的所有收入-支出
				//SELECT lineOrder.lineClassId,lineOrder.businessId,sum(lineOrder.price) AS price,lineOrder.classprice AS classPrice,sum(lineOrder.giftMoney) AS todayGifMoney,lineclass.orderDate,lineclass.orderSeats,lineclass.orderStartTime,mgr.brefName,veh.vehicleId,veh.vehicleNumber,dri.driverId,dri.driverName,linb.lineName,linb.lineBaseId,linb.cityCode,linb.cityName,linb.provinceCode,dri.telephone FROM line_class_info AS lineclass LEFT JOIN line_business_order AS lineOrder ON lineclass.lineClassId = lineOrder.lineClassId LEFT JOIN line_base_info AS linb ON linb.lineBaseId = lineclass.lineBaseId LEFT JOIN mgr_business AS mgr ON mgr.businessId = lineOrder.businessId LEFT JOIN vehicle_info AS veh ON veh.vehicleId = lineclass.vehicleId LEFT JOIN driver_info AS dri ON dri.driverId = lineclass.driverId LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = lineOrder.leaseOrderId WHERE lineclass.orderDate = '2015-06-25' AND leab.ispay = 1 AND lineOrder.`status` IN ('0','2') GROUP BY lineOrder.lineClassId
				sql =  selFields+" ,sum(lineOrder.price) AS price,lineOrder.businessId, lineOrder.classprice AS classPrice,sum(lineOrder.giftMoney) AS todayGifMoney  " +selTable + " AND leab.ispay = 1 AND lineOrder.`status` IN ('0', '2') GROUP BY lineOrder.lineClassId LIMIT ?,? ";
				args = new Object[3];
				args[0] = yesterday;
				args[1] = (page-1)*pageSize;
				args[2] = pageSize;
				List<FinancialEntity> financils = qr.query(conn,sql,new BeanListHandler<FinancialEntity>(FinancialEntity.class), args);
				if(null==financils || financils.size()==0){
					return statu;
				}
				statu = addDate(qr,conn,financils,yesterday);
				
			}
			
			//获取售票为0的线路
			//SELECT a.lineClassId AS a1 FROM ( SELECT lineclass.lineClassId FROM line_class_info AS lineclass LEFT JOIN line_base_info AS linb ON linb.lineBaseId = lineclass.lineBaseId WHERE lineclass.orderDate = '2015-09-14' AND linb.lineType = 1 AND linb.lineSubType = 0 AND linb.lineStatus = 3 AND lineclass.delFlag = 0 ) AS a WHERE a.lineClassId NOT IN ( SELECT lineOrder.lineClassId FROM line_class_info AS lineclass LEFT JOIN line_business_order AS lineOrder ON lineclass.lineClassId = lineOrder.lineClassId LEFT JOIN line_base_info AS linb ON linb.lineBaseId = lineclass.lineBaseId LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = lineOrder.leaseOrderId WHERE lineclass.orderDate = '2015-09-14' AND leab.ispay = 1 AND lineOrder.`status` IN ('0', '2') AND linb.lineType = 1 AND linb.lineSubType = 0 AND linb.lineStatus = 3 AND lineclass.delFlag = 0 GROUP BY lineOrder.lineClassId )
			sql = " SELECT a.lineClassId AS a1 FROM ( SELECT lineclass.lineClassId FROM line_class_info AS lineclass LEFT JOIN line_base_info AS linb ON linb.lineBaseId = lineclass.lineBaseId WHERE lineclass.orderDate = ? AND linb.lineType = 1 AND linb.lineSubType = 0 AND linb.lineStatus = 3 AND lineclass.delFlag = 0 ) AS a WHERE a.lineClassId NOT IN ( SELECT lineOrder.lineClassId FROM line_class_info AS lineclass LEFT JOIN line_business_order AS lineOrder ON lineclass.lineClassId = lineOrder.lineClassId LEFT JOIN line_base_info AS linb ON linb.lineBaseId = lineclass.lineBaseId LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = lineOrder.leaseOrderId WHERE lineclass.orderDate = ? AND leab.ispay = 1 AND lineOrder.`status` IN ('0', '2') AND linb.lineType = 1 AND linb.lineSubType = 0 AND linb.lineStatus = 3 AND lineclass.delFlag = 0 GROUP BY lineOrder.lineClassId ) ";
			Object[] args = new Object[2];
			args[0] = args[1] = yesterday;
			List<AppVo_1> v1s = qr.query(conn, sql, args, new BeanListHandler<AppVo_1>(AppVo_1.class));
			if(null!=v1s && v1s.size()>0){
				StringBuilder strsql = new StringBuilder( "AND lineclass.lineClassId IN ( ");
				List<String> conds = new ArrayList<String>();
				StringBuilder cs = new StringBuilder();
				conds.add(yesterday);
				for(AppVo_1 v1 : v1s){
					if(null!=v1 && null!=v1.getA1() && !"".equals(v1.getA1())){
						cs.append("?,");
						conds.add(v1.getA1());
					}
				}
				if(null!=cs){
					strsql.append(cs.toString().substring(0, cs.length()-1));
				}
				strsql.append(" ) GROUP BY lineclass.lineClassId ");
				String al_sql = selFields+" ,0 AS price,linb.businessId, lineclass.price AS classPrice,0 AS todayGifMoney  " +selTable +strsql.toString();
				al_sql = al_sql.replace("LEFT JOIN mgr_business AS mgr ON mgr.businessId = lineOrder.businessId", "LEFT JOIN mgr_business AS mgr ON mgr.businessId = linb.businessId");
				if(conds.size()>0){
					List<FinancialEntity> financils = qr.query(conn,al_sql,conds.toArray(),new BeanListHandler<FinancialEntity>(FinancialEntity.class));
					if(null==financils || financils.size()==0){
						return statu;
					}
					statu = addDate(qr,conn,financils,yesterday);
				}
			}
			System.out.println("添加完成");
			conn.commit();
		} catch (Exception e) {
			try {
				if(conn!=null){
					conn.rollback();
					e.printStackTrace();
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
	 * 组装ID
	 * @param conn
	 * @param financils
	 * @return
	 * @throws SQLException
	 */
	private Map<String, Object> putIds(Connection conn,List<FinancialEntity> financils) throws SQLException{
		Map<String, Object> maps = new HashMap<String,Object>();
		String lineClassIds = "";//班次ID
		String stationIds = "";//站点ID
		String[] stations = {};
		for(FinancialEntity fin : financils){
			lineClassIds += fin.getLineClassId()+",";//拼接lineClassIdsID
			String stationInfoes = stationInfoDao.generateStationIdArrayByLineId(conn,fin.getLineBaseId());
				stations = stationInfoes.split(",");//切分站点,取起点终点
				fin.setBstation(stations[0]);
				if(!StringUtils.isEmpty(stations[stations.length-1])){
					fin.setEstation(stations[stations.length-1]);
				}else{
					if(stations.length>=2){
						fin.setEstation(stations[stations.length-2]);
					}
				}
				stationIds += stationInfoes+",";//拼接站点
		}
		maps.put("lineClassIds", lineClassIds);
		maps.put("stationIds", stationIds);
		return maps;
	}
	
	/**
	 * 添加数据
	 * @param qr
	 * @param conn
	 * @param financils
	 * @param yesterday
	 * @return
	 * @throws Exception
	 */
	private int addDate(QueryRunner qr,Connection conn,List<FinancialEntity>financils,String yesterday) throws Exception{
		
		int statu = 0;
		
		Map<String, Object> idmaps = putIds(conn,financils);
		String lineClassIds = (String) idmaps.get("lineClassIds");//班次ID
		String stationIds = (String) idmaps.get("stationIds");//站点ID
		if(StringUtils.isEmpty(lineClassIds)){
			return statu;
		}
		
		financils = putAdvice(lineClassIds,conn,qr,financils);
		
		Map<String, Object> classMap = joinTogeter(lineClassIds, conn, qr, yesterday,1);
		
		stationIds = RePeat.quChong(stationIds);
		Map<String, Object> stationMap = joinTogeter(stationIds, conn, qr, yesterday,2);
		
		//班次的实际上座人数的map
		Map<String, Integer> byBusNumMap = (Map<String, Integer>) classMap.get("byBusNumMap");
		//班次对应的实际改签数的map  outType:支出类型 1:退票   2:改签  
		Map<String, Integer> changeNumMap = (Map<String, Integer>) classMap.get("changeNumMap");
		//班次对应的实际退票数的map
		Map<String, Integer> returnNumMap = (Map<String, Integer>) classMap.get("returnNumMap");
		//从收入统计中获取的总购票数的map
		Map<String, Integer> statTotalNumMap = (Map<String, Integer>) classMap.get("statTotalNumMap");
		//线路平均分
		Map<String, String> scoreMap = (Map<String, String>) classMap.get("scoreMap");
		String bstation = null;
		String estation = null;
		Integer byBusNum = 0;
		Integer changeNum = 0;
		Integer returnNum = 0;
		Integer statTotalNum = 0;
		Integer buyNum = 0;
		String attendance = null;//上座率
		String score = "0";
		BigDecimal big_attendance = null;
		if(null!=classMap){
			for(FinancialEntity fin : financils){
				if(null!=stationMap){
					bstation = (String) stationMap.get(fin.getBstation());
					estation = (String) stationMap.get(fin.getEstation());
					fin.setBstation(bstation);//起点
					fin.setEstation(estation);//终点
				}
				if(null!=byBusNumMap){
					byBusNum = byBusNumMap.get(fin.getLineClassId());//实际上座人数
				}
				if(null==byBusNum){
					byBusNum = 0;
				}
				fin.setByBusNum(byBusNum);
				if(null!=changeNumMap){
					changeNum = changeNumMap.get(fin.getLineClassId());//实际改签数
				}
				if(null==changeNum){
					changeNum = 0;
				}
				fin.setChangeNum(changeNum);
				if(null!=returnNumMap){
					returnNum = returnNumMap.get(fin.getLineClassId());//实际退票数
				}
				if(null==returnNum){
					returnNum = 0;
				}
				fin.setReturnNum(returnNum);
				if(null!=statTotalNumMap){
					statTotalNum = statTotalNumMap.get(fin.getLineClassId());//从收入统计中获取的总购票数
				}
				if(null==statTotalNum){
					statTotalNum = 0;
				}
				fin.setStatTotalNum(statTotalNum);
				buyNum = byBusNum + changeNum + returnNum;
				fin.setBuyNum(buyNum);//(相加得到的)班次总购票数
				
				big_attendance = new BigDecimal(byBusNum).divide(new BigDecimal(fin.getOrderSeats()),2, BigDecimal.ROUND_HALF_EVEN);
				attendance = big_attendance.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).toString();//BigDecimal的除法--结果保留两位小数
				fin.setAttendance(attendance+"%");//上座率
				if(null!=scoreMap){
					score = scoreMap.get(fin.getLineClassId());//平均分
				}
				fin.setScore(score);
				
				//添加数据
				sql = " INSERT INTO stat_financial(finacialId,lineBaseId,lineName,businessId,brefName,classPrice,price,todayGifMoney,"
					+"bstation,estation,orderStartTime,orderDate,orderSeats,driverId,vehicleId,vehicleNumber,driverName,lineClassId,"
					+"byBusNum,changeNum,returnNum,statTotalNum,buyNum,attendance,score,advOne,advTwo,advThree,provinceCode,cityCode,cityName,telephone) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				List<String> conds = new ArrayList<String>();
				conds.add(StringUtil.generateSequenceNo());//主键
				conds.add(fin.getLineBaseId());//线路ID
				conds.add(fin.getLineName());//线路名称
				conds.add(fin.getBusinessId());//商家ID
				conds.add(fin.getBrefName());//商家名称
				conds.add(fin.getClassPrice());//班次价格
				conds.add(fin.getPrice());//实际支付价格
				conds.add(fin.getTodayGifMoney());//当天班次优惠金额
				conds.add(fin.getBstation());//起点
				conds.add(fin.getEstation());//终点
				conds.add(fin.getOrderStartTime());//发车时间
				conds.add(fin.getOrderDate());//发车日期
				conds.add(fin.getOrderSeats());//座位数
				conds.add(fin.getDriverId());//司机ID
				conds.add(fin.getVehicleId());//车辆ID
				conds.add(fin.getVehicleNumber());//车牌
				conds.add(fin.getDriverName());//司机名称
				conds.add(fin.getLineClassId());//班次ID
				conds.add(String.valueOf(fin.getByBusNum()));//实际上座人数
				conds.add(String.valueOf(fin.getChangeNum()));//实际改签数
				conds.add(String.valueOf(fin.getReturnNum()));//实际退票数
				conds.add(String.valueOf(fin.getStatTotalNum()));//从收入统计中获取的总购票数
				conds.add(String.valueOf(fin.getBuyNum()));//(相加得到的)班次总购票数
				conds.add(fin.getAttendance());//上座率
				conds.add(fin.getScore());//平均分
				conds.add(fin.getAdvOne());//建议一
				conds.add(fin.getAdvTwo());//建议二
				conds.add(fin.getAdvThree());//建议三
				conds.add(fin.getProvinceCode());//省份编码
				conds.add(fin.getCityCode());//城市编码
				conds.add(fin.getCityName());//城市名称
				conds.add(fin.getTelephone());//司机电话
				statu = qr.update(conn, sql, conds.toArray());
				if(statu<1){
					throw new Exception("添加新版收入统计异常");
				}
			}
		}
		return statu;
	}
	
	/**拼接数据
	 * @throws SQLException **/
	private Map<String, Object> joinTogeter(String str,Connection conn,QueryRunner qr,String yesterday,int type) throws SQLException{
		
		if(StringUtils.isEmpty(str)){
			return null;
		}
		String sql = null;
		
		String[] lineIds = str.split(",");
		
		
		//为防止sql语句过长,将lineIds分多段
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(type==1){
			//根据日期和线路ID获取当天班次的价格 --以lineBaseId为主键,班次价格为value
			// times = 1;
			String lineClassIds = "";
			for(int i=0;i<lineIds.length;i++){
				if(!StringUtils.isEmpty(lineIds[i])){
					lineClassIds += lineIds[i]+",";
				}
			}
			if(!StringUtils.isEmpty(lineClassIds)){
				
				if(!StringUtils.isEmpty(lineClassIds)){
					List<String> paramList = new ArrayList<String>();
					lineClassIds = lineClassIds.substring(0, lineClassIds.length()-1);
					String[]strs=lineClassIds.split(",");
					StringBuffer sb=new StringBuffer();
					for (String s : strs) {
						if(!StringUtils.isEmpty(s)){
							paramList.add(s);
							sb.append("?,");
						}
					}
					
					Map<String, Integer> byBusNumMap = new HashMap<String, Integer>();
					//班次的实际上座人数
					//SELECT lineOrder.lineClassId,COUNT(1) AS byBusNum FROM line_business_order AS lineOrder LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = lineOrder.leaseOrderId WHERE lineOrder.lineClassId IN ( '150628191701554763','150705225125158433') AND leab.ispay = 1 AND lineOrder.status IN ('0','2') GROUP BY lineOrder.lineClassId
					sql = " SELECT lineOrder.lineClassId,COUNT(1) AS byBusNum FROM line_business_order AS lineOrder LEFT JOIN lease_base_info AS leab ON leab.leaseOrderNo = lineOrder.leaseOrderId WHERE lineOrder.lineClassId IN ("+sb.substring(0,sb.length()-1)+") AND leab.ispay = 1 AND lineOrder.status IN ('0','2') GROUP BY lineOrder.lineClassId ";
					List<LineClassCount> byBusNum = qr.query(conn, sql, new BeanListHandler<LineClassCount>(LineClassCount.class),paramList.toArray());
					if(null!=byBusNum && byBusNum.size()>0){
						for(LineClassCount by :byBusNum){
							byBusNumMap.put(by.getLineClassId(), by.getByBusNum());
						}
						map.put("byBusNumMap", byBusNumMap);
					}
					
					Map<String, Integer> changeNumMap = new HashMap<String, Integer>();
					//获取班次对应的实际改签数  outType:支出类型 1:退票   2:改签
					//SELECT lineClassId,COUNT(1) AS changeNum FROM stat_out WHERE outType = 2 AND lineClassId IN ('150722095807580004') GROUP BY lineClassId
					sql = " SELECT lineClassId,COUNT(1) AS changeNum FROM stat_out WHERE outType = 2 AND lineClassId IN ("+sb.substring(0,sb.length()-1)+") GROUP BY lineClassId ";
					List<LineClassCount> changeNum = qr.query(conn, sql, new BeanListHandler<LineClassCount>(LineClassCount.class),paramList.toArray());
					if(null!=changeNum && changeNum.size()>0){
						for(LineClassCount change :changeNum){
							changeNumMap.put(change.getLineClassId(), change.getChangeNum());
						}
						map.put("changeNumMap", changeNumMap);
					}
					
					Map<String, Integer> returnNumMap = new HashMap<String, Integer>();
					//获取班次对应的实际退票数
					//SELECT lineClassId,COUNT(1) AS returnNum FROM stat_out WHERE outType = 1 AND lineClassId IN ('150722095807580004') GROUP BY lineClassId
					sql = " SELECT lineClassId,COUNT(1) AS returnNum FROM stat_out WHERE outType = 1 AND lineClassId IN ("+sb.substring(0,sb.length()-1)+") GROUP BY lineClassId ";
					List<LineClassCount> returnNum = qr.query(conn, sql, new BeanListHandler<LineClassCount>(LineClassCount.class),paramList.toArray());
					if(null!=returnNum && returnNum.size()>0){
						for(LineClassCount ret :returnNum){
							returnNumMap.put(ret.getLineClassId(), ret.getReturnNum());
						}
						map.put("returnNumMap", returnNumMap);
					}
					
					
					//班次总购票数 = 班次的实际上座人数  + 班次的实际改签数 + 班次的实际退票数
					
					Map<String, Integer> statTotalNumMap = new HashMap<String, Integer>();
					//从收入统计中获取的总购票数
					//SELECT lineClassId,count(1) AS statTotalNum FROM stat_total WHERE lineClassId IN ( '150722095807580004') GROUP BY lineClassId
					sql = " SELECT lineClassId,count(1) AS statTotalNum FROM stat_total WHERE lineClassId IN ("+sb.substring(0,sb.length()-1)+") GROUP BY lineClassId ";
					List<LineClassCount> statTotalNum = qr.query(conn, sql, new BeanListHandler<LineClassCount>(LineClassCount.class),paramList.toArray());
					if(null!=statTotalNum && statTotalNum.size()>0){
						for(LineClassCount total :statTotalNum){
							statTotalNumMap.put(total.getLineClassId(), total.getStatTotalNum());
						}
						map.put("statTotalNumMap", statTotalNumMap);
					}
					
					Map<String, String> scoreMap = new HashMap<String, String>();
					//平均分
					//SELECT lineClassId,round(avg(starPoint),2) AS score FROM passenger_comments WHERE lineClassId IN ('15072711050226125','150729142836245092','150731155352443364') GROUP BY lineClassId 
					sql = " SELECT lineClassId,round(avg(starPoint),2) AS score FROM passenger_comments WHERE lineClassId IN ("+sb.substring(0,sb.length()-1)+") GROUP BY lineClassId  ";
					List<LineClassCount> scores = qr.query(conn, sql, new BeanListHandler<LineClassCount>(LineClassCount.class),paramList.toArray());
					if(null!=scores && scores.size()>0){
						for(LineClassCount score :scores){
							scoreMap.put(score.getLineClassId(), score.getScore());
						}
						map.put("scoreMap", scoreMap);
					}
				}
			}
		}else if(type==2){
			//以站点ID(stationInfoId)为主键,站点名称(stationName)为value
			String fen_lineBaseIds = "";
			for(int i=0;i<lineIds.length;i++){
				if(!StringUtils.isEmpty(lineIds[i])){
					fen_lineBaseIds += lineIds[i]+",";
				}
			}
			if(!StringUtils.isEmpty(fen_lineBaseIds)){
				//SELECT stationInfoId,stationName FROM line_station_info WHERE 1=1 AND stationInfoId IN ('150615162252983575') 
				sql = " SELECT id,name FROM pb_station WHERE 1=1   ";
				List<String> paramList=new ArrayList<String>();
				fen_lineBaseIds = fen_lineBaseIds.substring(0, fen_lineBaseIds.length()-1);
				String[] strs=fen_lineBaseIds.split(",");
				StringBuffer sb=new StringBuffer();
				for (String s : strs) {
					if(!StringUtils.isEmpty(s)){
						paramList.add(s);
						sb.append("?,");
					}
				}
				sql += " AND id IN ("+sb.substring(0,sb.length()-1)+") ";
				List<StationInfo> stations = qr.query(conn, sql, new BeanListHandler<StationInfo>(StationInfo.class), paramList.toArray());
				if(null!=stations && stations.size()>0){
					for(StationInfo station : stations){
						map.put(station.getId(), station.getName());
					}
				}
			}
		}
		
		return map;
	}
	
	/**获取前三项评论建议
	 * @throws SQLException **/
	private List<FinancialEntity> putAdvice(String str,Connection conn,QueryRunner qr,List<FinancialEntity> financils) throws SQLException{
		
		if(StringUtils.isEmpty(str)){
			return null;
		}
		String sql = null;
		String[] lineIds = str.split(",");
		
		//以lineclass为key,advicesMap为value
		Map<String, Object> lineAdvsMap = new HashMap<String, Object>();
		//以advice为key,次数为alue
		Map<String,Integer> advicesMap = new HashMap<String, Integer>();
		
		String lineClassIds = "";
		for(int i =0;i<lineIds.length;i++){
			if(!StringUtils.isEmpty(lineIds[i])){
				lineClassIds += lineIds[i]+",";
			}
		}
		
		List<String> paramList=new ArrayList<String>();
		lineClassIds = lineClassIds.substring(0, lineClassIds.length()-1);
		String[] strs=lineClassIds.split(",");
		StringBuffer sb=new StringBuffer();
		for (String s : strs) {
			if(!StringUtils.isEmpty(s)){
				paramList.add(s);
				sb.append("?,");
			}
		}
		
		//获取6项建议中评论做多的前三项
		//SELECT lineClassId,advinces FROM passenger_comments WHERE lineClassId IN ('150710160241271197')
		sql = " SELECT lineClassId,advinces FROM passenger_comments WHERE lineClassId IN ("+sb.substring(0,sb.length()-1)+") ";
		List<LineClassCount> advices = qr.query(conn, sql, new BeanListHandler<LineClassCount>(LineClassCount.class),paramList.toArray());
		if(null!=advices && advices.size()>0){
			String[] advicesStr = {};
			Integer advTimes = 0;
			for(LineClassCount line:advices){
				//根据班次ID获取对应的advicesMap
				advicesMap = (Map<String, Integer>) lineAdvsMap.get(line.getLineClassId());
				if(null==advicesMap){
					advicesMap = new HashMap<String, Integer>();
				}
				if(!StringUtils.isEmpty(line.getAdvinces())){
					advicesStr = line.getAdvinces().split(",");
					for(int k=0;k<advicesStr.length;k++){
						if(!StringUtils.isEmpty(advicesStr[k])){
							//根据评论建议内容获取出现的次数
							advTimes = advicesMap.get(advicesStr[k]);
							if(null==advTimes){
								advicesMap.put(advicesStr[k], 1);
							}else{
								advTimes++;
								advicesMap.put(advicesStr[k], advTimes);
							}
						}
					}
					lineAdvsMap.put(line.getLineClassId(), advicesMap);
				}
			}
		}
		
		for(FinancialEntity financial: financils){
			advicesMap = (Map<String, Integer>) lineAdvsMap.get(financial.getLineClassId());
			List<AppVo_2> list = new ArrayList<AppVo_2>();
			if(null!=advicesMap){
				Set<Map.Entry<String,Integer>> entry = advicesMap.entrySet(); 
				Iterator<Map.Entry<String,Integer>> it = entry.iterator(); 
				while (it.hasNext()){  
					//将键值关系取出存入Map.Entry这个映射关系集合接口中  
					Map.Entry<String,Integer>  me = it.next();  
					//使用Map.Entry中的方法获取键和值  
					String key = me.getKey();  
					String value = "0";
					if(null!=me.getValue()){
						value = String.valueOf(me.getValue());
					}
					AppVo_2 vo = new AppVo_2();
					vo.setA1(key);
					vo.setA2(value);
					list.add(vo);
				} 
				if(null!=list && list.size()>0){
					//将建议最多的前三项列出来
					AppVo_Comp comparator=new AppVo_Comp();
					Collections.sort(list, comparator);
					financial.setAdvOne(list.get(0).getA1());
					if(list.size()>=2){
						financial.setAdvTwo(list.get(1).getA1());
					}
					if(list.size()>=3){
						financial.setAdvThree(list.get(2).getA1());
					}
				}
			}
			
		}
		return financils;
	}

	/**供应商结算明细表**/
	public Map<String, Object> querySupplierList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		super.finit("stat_financial");
		String sql = " SELECT orderDate,brefName,lineName,concat(bstation,'-',estation) AS bstation,vehicleNumber,classPrice,price,todayGifMoney,byBusNum,cityName FROM stat_financial ";
		String cond = " WHERE 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " AND orderDate >= ? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " AND orderDate <= ? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " AND brefName LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " AND lineName LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " AND vehicleNumber LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " AND cityName LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField06().trim()));
			}
		}
		sql += cond;
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql += " ORDER BY orderDate DESC ";
		list = tableDao.queryByPage(FinancialEntity.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		map.put("search", search);
		return map;
	}

	/**报表**/
	public Map<String, Object> queryReportList(Search search,
			Integer currentPageIndex, Integer pageSize) {
		super.finit("stat_financial");
		String sql = " SELECT orderDate,brefName,lineName,vehicleNumber,driverName,classPrice,price,todayGifMoney,byBusNum,cityName,orderStartTime,bstation,estation,buyNum,changeNum,returnNum,attendance,score,advOne,advTwo,advThree,telephone,orderSeats FROM stat_financial ";
		String cond = " WHERE 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " AND orderDate >= ? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " AND orderDate <= ? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " AND brefName LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " AND lineName LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " AND vehicleNumber LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField05().trim()));
			}
			if(search.getField06()!= null && !search.getField06().equals("")){
				cond+= " AND score >= ? ";
				paramList.add(search.getField06().trim());
			}
			if(search.getField07()!= null && !search.getField07().equals("")){
				cond+= " AND cityName LIKE ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField07().trim()));
			}
		}
		sql += cond;
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql += " ORDER BY orderDate DESC ";
		list = tableDao.queryByPage(FinancialEntity.class, sql,currentPageIndex,params);
		page = new Page(list,sql,currentPageIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		map.put("search", search);
		return map;
	}

	/**判断是否执行新版财务数据恢复**/
	public String queryIsExecute(String string) {
		super.finit("sys_init_data");
		//SELECT isExecute AS a1 FROM sys_init_data WHERE dataId = 'xinbancaiwu'
		String sql = " SELECT isExecute AS a1 FROM sys_init_data WHERE dataId = ? ";
		args = new Object[1];
		args[0] = string;
		AppVo_1 vo = tableDao.queryBean(AppVo_1.class, sql, args);
		String statu = "N";
		if(null!=vo){
			statu = vo.getA1();
		}
		return statu;
	}

	/**将是否执行新版财务数据恢复Y改为N**/
	public void updateIsExecute(String string) {
		super.finit("sys_init_data");
		String sql = " UPDATE sys_init_data SET isExecute = 'N' WHERE dataId = ? ";
		args = new Object[1];
		args[0] = string;
		tableDao.executeSQL(sql, args);
	}

	/**添加城市对应的订单金额数据**/
	@Override
	public int addCityOrder(String yesterday) {
		Connection conn = null;
		int statu = 0;
		try{
			conn = MyDataSource.getConnect();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			StringBuilder sql = new StringBuilder();
			List<String> params = new ArrayList<String>();
			
			String sqlStr = " SELECT COUNT(1) FROM city_order WHERE orderDate = ? ";
			Object[] args = new Object[1];
			args[0] = yesterday;
			Long count = (Long)qr.query(conn,sqlStr, args, new ScalarHandler(1));
			if(count>0L){
				return 0;
			}
			System.out.println(yesterday);
			
			
			String table = "";
			String select = "";
			String ispay = "";
			sql.append(" SELECT a.total,a.cityName,a.orderDate,b.pay,b.payNum,truncate(b.avgpay,2) AS avgpay,c.unpay,c.unpayNum,truncate(c.avgunpay,2) AS avgunpay,a.cityCode,a.provinceCode FROM  ");
			for(int i=0;i<3;i++){
				if(0==i){
					select = " sum(a.monry) AS total, b.cityName, b.orderDate,b.cityCode,b.provinceCode ";
					table = " AS a LEFT JOIN ";
				}else if(1==i){
					select = " sum(a.monry) AS pay, b.cityName, b.orderDate, avg(a.monry) AS avgpay, count(a.leaseOrderTime) AS payNum ";
					table = " AS b ON a.cityName = b.cityName LEFT JOIN ";
					ispay = " AND ispay = 1 ";
				}else if(2==i){
					select = " sum(a.monry) AS unpay, b.cityName, b.orderDate, avg(a.monry) AS avgunpay, count(a.leaseOrderTime) AS unpayNum ";
					table = " AS c ON a.cityName = c.cityName ";
					ispay = " AND ispay = 0 ";
				}
				
				sql.append(" ( SELECT "+select+" FROM ( SELECT totalCount AS monry, lineBaseId, leaseOrderTime FROM lease_base_info WHERE 1=1 ");
				sql.append(ispay);
				sql.append(" AND leaseOrderTime BETWEEN ? AND ? ) ");
				params.add(yesterday+" 00:00:00");
				params.add(yesterday+" 23:59:59");
				sql.append(" AS a RIGHT JOIN ( SELECT * FROM stat_financial WHERE 1=1  ");
				sql.append(" AND orderDate = ?  ");
				params.add(yesterday);
				sql.append(" ) AS b ON a.lineBaseId = b.lineBaseId GROUP BY b.cityCode,b.orderDate ) ");
				sql.append(table);
			}
			sql.append(" WHERE a.orderDate = b.orderDate AND a.orderDate = c.orderDate ");
			List<ReportAvgMoney> lists = qr.query(conn,sql.toString(),new BeanListHandler<ReportAvgMoney>(ReportAvgMoney.class),params.toArray());
			if(!lists.isEmpty()){
				for(ReportAvgMoney avg :lists){
					sqlStr = " INSERT INTO city_order(id,cityName,cityCode,provinceCode,orderDate,total,pay,payNum,"
							+ "avgpay,unpay,unpayNum,avgunpay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";
					params = new ArrayList<String>();
					params.add(StringUtil.generateSequenceNo());
					params.add(avg.getCityName());
					params.add(avg.getCityCode());
					params.add(avg.getProvinceCode());
					params.add(avg.getOrderDate());
					params.add(avg.getTotal());
					params.add(avg.getPay());
					params.add(avg.getPayNum());
					params.add(avg.getAvgpay());
					params.add(avg.getUnpay());
					params.add(avg.getUnpayNum());
					params.add(avg.getAvgunpay());
					statu = qr.update(conn, sqlStr, params.toArray());
					if(statu<1){
						throw new Exception("city_order 添加异常");
					}
				}
			}
			System.out.println("添加完成");
			conn.commit();
		}catch(Exception e){
			try {
				if(conn!=null){
					conn.rollback();
					e.printStackTrace();
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
}
