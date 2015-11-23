package com.amwell.dao.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.amwell.commons.MyDataSource;
import com.amwell.dao.INewApplicationReportDao;
import com.amwell.domain.NewApplicationReport;
import com.amwell.vo.PassengerInfoEntity;

@Repository(value="newApplicationReportDaoImpl")
public class NewApplicationReportDaoImpl implements INewApplicationReportDao{
	
	private static QueryRunner queryRunner = new QueryRunner();
	
	private static Logger logger = Logger.getLogger(NewApplicationReportDaoImpl.class);
	
	public List<NewApplicationReport> search(String startDate,String endDate,String cityName,String isHandle,int startPage,int pageSize){
		Connection conn = null;
		List<NewApplicationReport> newApplicationReportList = null;
		try{
			List<String> params = new ArrayList<String>();
			conn = MyDataSource.getConnect();
			StringBuilder builder = new StringBuilder();
			builder.append("select ");
			builder.append("luan.applicationId, ");
			builder.append("luan.isHandle, ");
			builder.append("sa.areaName as cityName, ");
			builder.append("(select stationName from line_user_application_station_new where appliStationId=startAddress) as ustation, ");
			builder.append("(select stationName from line_user_application_station_new where appliStationId=endAddress) as dstation, ");
			builder.append("enrollNum ");
			builder.append("from ");
			builder.append("(select * from (select applicationId,count(*) as enrollNum from line_enroll_user_new group by applicationId) as table1 where enrollNum>1) as table2 ");
			builder.append("left join ");
			builder.append("line_user_application_new as luan ");
			builder.append("on table2.applicationId=luan.applicationId ");
			builder.append("left join ");
			builder.append("sys_area as sa ");
			builder.append("on sa.arearCode=luan.cityCode ");
			builder.append("where 1=1 ");
			if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
				builder.append("and luan.applicationTime>? and luan.applicationTime<? ");
				params.add(startDate);
				params.add(endDate);
			}
			if(!StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
				builder.append("and luan.applicationTime>? ");
				params.add(startDate);
			}
			if(!StringUtils.isEmpty(isHandle)&&isHandle.equals("1")){
				builder.append("and luan.isHandle=? ");
				params.add(isHandle);
			}else{
				builder.append("and luan.isHandle is null ");
			}
			if(!StringUtils.isEmpty(cityName)){
				builder.append("and sa.areaName = ? ");
				params.add(cityName);
			}
			builder.append("order by enrollNum desc ");
			builder.append("limit "+startPage+","+pageSize);
			logger.info(builder.toString());
			long s1 = System.currentTimeMillis();
			newApplicationReportList = queryRunner.query(conn, builder.toString(), new BeanListHandler<NewApplicationReport>(NewApplicationReport.class),params.toArray());
			long s2 = System.currentTimeMillis();
			logger.info("s2-s1="+(s2-s1));
 		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		return newApplicationReportList;
	}
	
	public int searchCount(String cityName,String startDate,String endDate,String isHandle){
		Connection conn = null;
		int count = 0;
		try{
			List<String> params = new ArrayList<String>();
			conn = MyDataSource.getConnect();
			StringBuilder builder = new StringBuilder();
			builder.append("select count(*) as num from ");
			builder.append("(select ");
			builder.append("luan.applicationId, ");
			builder.append("sa.areaName, ");
			builder.append("(select stationName from line_user_application_station_new where appliStationId=startAddress) as ustation, ");
			builder.append("(select stationName from line_user_application_station_new where appliStationId=endAddress) as dstation, ");
			builder.append("enrollNum ");
			builder.append("from ");
			builder.append("(select * from (select applicationId,count(*) as enrollNum from line_enroll_user_new group by applicationId) as table1 where enrollNum>1) as table2 ");
			builder.append("left join ");
			builder.append("line_user_application_new as luan ");
			builder.append("on table2.applicationId=luan.applicationId ");
			builder.append("left join ");
			builder.append("sys_area as sa ");
			builder.append("on sa.arearCode=luan.cityCode ");
			builder.append("where 1=1 ");
			if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
				builder.append("and luan.applicationTime>? and luan.applicationTime<? ");
				params.add(startDate);
				params.add(endDate);
			}
			if(!StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
				builder.append("and luan.applicationTime>? ");
				params.add(startDate);
			}
			if(!StringUtils.isEmpty(isHandle)&&isHandle.equals("1")){
				builder.append("and luan.isHandle=? ");
				params.add(isHandle);
			}else{
				builder.append("and luan.isHandle is null ");
			}
			if(!StringUtils.isEmpty(cityName)){
				builder.append("and sa.areaName = ? ");
				params.add(cityName);
			}
			builder.append(") as table3 ");
			logger.info(builder.toString());
			Map<String,Object> map = queryRunner.query(conn, builder.toString(), new MapHandler(), params.toArray());
			count = ((Long)map.get("num")).intValue();
 		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		return count;
	}
	
	public List<PassengerInfoEntity> getTelephoneListByApplicationId(String applicationId){
		Connection conn = null;
		List<PassengerInfoEntity> telephoneList = null;
		try{
			conn = MyDataSource.getConnect();
			StringBuilder builder = new StringBuilder();
			List<String> params = new ArrayList<String>();
			builder.append("select ");
			builder.append("pi.* ");
			builder.append("from ");
			builder.append("line_enroll_user_new as leun ");
			builder.append("left join ");
			builder.append("passenger_info as pi ");
			builder.append("on ");
			builder.append("leun.passengerId=pi.passengerId ");
			builder.append("where ");
			builder.append("applicationId=? ");
			params.add(applicationId);
			telephoneList = queryRunner.query(conn, builder.toString(), new BeanListHandler<PassengerInfoEntity>(PassengerInfoEntity.class), params.toArray());
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
		return telephoneList;
	}
	
	public void setIsHandle(String applicationId,String isHandle){
		Connection conn = null;
		try{
			if(StringUtils.isEmpty(applicationId)){
				return;
			}
			if(StringUtils.isEmpty(isHandle)){
				return;
			}
			conn = MyDataSource.getConnect();
			String sql = "update line_user_application_new set isHandle=? where applicationId=?";
			Object[] params = new Object[2];
			params[0] = isHandle;
			params[1] = applicationId;
			queryRunner.update(conn, sql, params);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				logger.error(e.getMessage(),e);
			}
		}
	}
}
