package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.Sha1Utils;
import com.amwell.commons.StringUtil;
import com.amwell.dao.IPinCheLineImportDao;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.SysDiscountVo;
import com.amwell.vo.pc.LineStationCarVo;

@Repository("pinCheLineImportDao")
public class PinCheLineImportDaoImpl extends DaoSupport implements IPinCheLineImportDao {
		
	private static final Logger log = Logger.getLogger(PinCheLineImportDaoImpl.class);
	
	public int importPinCheLine(List<LineStationCarVo> excelData) {

		int flag = -1;

		Connection conn = MyDataSource.getConnect();

		if (conn == null) {
			throw new IllegalStateException("Connection is null.");
		}

		if (CollectionUtils.isEmpty(excelData)) {
			throw new IllegalArgumentException("Connection is null.");
		}
		
		try {
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			for (LineStationCarVo v : excelData) {
				String passengerId = null;
				// 查询乘客，若不存在，则写乘客
				String queryPassengerSql = "select passengerId,telephone from passenger_info where telephone=?";
                
				List<PassengerInfoEntity> passengerList = qr.query(conn,queryPassengerSql, new BeanListHandler<PassengerInfoEntity>(PassengerInfoEntity.class), new Object[]{v.getTelephone()});
                if(CollectionUtils.isEmpty(passengerList)){
                	String max_displayIdSql = "SELECT MAX(CAST(displayid AS SIGNED INTEGER)) as displayId FROM passenger_info";
                	List<PassengerInfoEntity> maxDisplayIdList = qr.query(conn,max_displayIdSql,  new BeanListHandler<PassengerInfoEntity>(PassengerInfoEntity.class), new Object[]{});
                	String displyId = CollectionUtils.isEmpty(maxDisplayIdList)?"10000":String.valueOf(Integer.parseInt(maxDisplayIdList.get(0).getDisplayId())+1);
                	String passengerSql = "insert into passenger_info(passengerId,displayId,nickName,sex,accountStatus,telephone,blackFlag,passWord,registerTime,twoCodeValue) values(?,?,?,?,?,?,?,?,?,?)";
                	passengerId = StringUtil.generateSequenceNo();
                	String twoCodeValue = StringUtil.generateSequenceNo();
                	//密码默认为手机号后六位
                	String password = Sha1Utils.encrypt(v.getTelephone()+"&"+v.getTelephone().substring(5));
                	qr.update(conn, passengerSql, new Object[]{passengerId,displyId,v.getNickName(),v.getSex(),0,v.getTelephone(),0,password,MyDate.getMyDateLong(),twoCodeValue});
                }else{
                	passengerId = passengerList.get(0).getPassengerId();
                }
                
                String carSql = "insert into pc_car(carId,carModel,carNumber,carColor,carSeats,passengerId) values(?,?,?,?,?,?)";
                String carId = StringUtil.generateSequenceNo();
                qr.update(conn, carSql, new Object[]{carId,v.getCarModel(),v.getCarNumber(),v.getCarColor(),v.getCarSeats(),passengerId});
                //24个字段
				//String lineSql = "insert into pc_line(pcLineId,provinceCode,provinceName,cityCode,cityName,startStationName,endStationName,lineType,lineSubType,realPrice,goTime,returnTime,repeatTime,carId,carModel,carNumber,carSeats,passengerId,createOn,publisherFlag,lineKm,lineTime,lineStatus,extension) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String lineSql = "insert into pc_line(pcLineId,startStationName,endStationName,lineType,lineSubType,realPrice,goTime,returnTime,repeatTime,carId,carModel,carNumber,carSeats,passengerId,createOn,publisherFlag,lineKm,lineTime,lineStatus,extension) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                String pcLineId = StringUtil.generateSequenceNo();
                Integer publisherFlag = v.getPublisherFlag();
                if(publisherFlag==null){
                	publisherFlag= StringUtils.hasText(v.getCarNumber())?2:1;
                }
                qr.update(conn, lineSql, new Object[]{pcLineId,v.getStationName()[0],v.getStationName()[v.getStationName().length-1],v.getLineType(),v.getLineSubType(),v.getRealPrice(),v.getGoTime(),v.getReturnTime(),v.getRepeatTime(),carId,v.getCarModel(),v.getCarNumber(),v.getCarSeats(),passengerId,MyDate.getMyDateLong(),publisherFlag,v.getLineKm(),v.getLineTime(),1,"excelImport"});
                String stationSql = "insert into pc_line_station(pcStationId,stationName,loni,lati,stationOrder,pcLineId) values(?,?,?,?,?,?)";
                if(ArrayUtils.isSameLength(v.getStationName(), v.getLoni())&&ArrayUtils.isSameLength(v.getStationName(), v.getLati())){
                	int len = v.getStationName().length;
					for(int i=0;i<len;i++){
						qr.update(conn, stationSql, new Object[]{StringUtil.generateSequenceNo(),v.getStationName()[i],v.getLoni()[i],v.getLati()[i],i+1,pcLineId});
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			flag =-9999;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e1.getLocalizedMessage());
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return flag;
	}

	public int addLine(LineStationCarVo v) {
		int flag = -1;

		Connection conn = MyDataSource.getConnect();

		if (conn == null) {
			throw new IllegalStateException("Connection is null.");
		}

		if (v==null) {
			throw new IllegalArgumentException("LineStationCarVo is null.");
		}
		
		try {
				conn.setAutoCommit(false);
				QueryRunner qr = new QueryRunner();
				
				String passengerId = null;
				// 查询乘客，若不存在，则写乘客
				String queryPassengerSql = "select passengerId,telephone from passenger_info where telephone=?";
                List<PassengerInfoEntity> passengerList = qr.query(conn,queryPassengerSql, new BeanListHandler<PassengerInfoEntity>(PassengerInfoEntity.class), new Object[]{v.getTelephone()});
                if(CollectionUtils.isEmpty(passengerList)){
                	String max_displayIdSql = "SELECT MAX(CAST(displayid AS SIGNED INTEGER)) as displayId FROM passenger_info";
                	List<PassengerInfoEntity> maxDisplayIdList = qr.query(conn,max_displayIdSql,  new BeanListHandler<PassengerInfoEntity>(PassengerInfoEntity.class), new Object[]{});
                	String displyId = CollectionUtils.isEmpty(maxDisplayIdList)?"10000":String.valueOf(Integer.parseInt(maxDisplayIdList.get(0).getDisplayId())+1);
                	String passengerSql = "insert into passenger_info(passengerId,displayId,nickName,sex,accountStatus,telephone,blackFlag,passWord,registerTime,twoCodeValue) values(?,?,?,?,?,?,?,?,?,?)";
                	passengerId = StringUtil.generateSequenceNo();
                	String twoCodeValue = StringUtil.generateSequenceNo();
                	//密码默认为手机号后六位
                	String password = Sha1Utils.encrypt(v.getTelephone()+"&"+v.getTelephone().substring(5));
                	qr.update(conn, passengerSql, new Object[]{passengerId,displyId,v.getNickName(),v.getSex(),0,v.getTelephone(),0,password,MyDate.getMyDateLong(),twoCodeValue});
                	
                	//新注册用户需要写入活动信息
        			String disSql = "SELECT * FROM sys_discount WHERE iswork = '1' LIMIT 1";
        			List<SysDiscountVo> disCountList = qr.query(conn, disSql, new BeanListHandler<SysDiscountVo>(SysDiscountVo.class), new Object[]{});
        			SysDiscountVo discountVo = null;
        			if(false==CollectionUtils.isEmpty(disCountList)){
        				discountVo=disCountList.get(0);
        			}
        			String insertDisSql ="INSERT INTO passenger_discount_info(id,passengerid,discountid,createOn,disstatus,remark,disprice) VALUES(?,?,?,?,?,?,?)";
        			//为乘客增加活动信息
    				if(discountVo!=null){
    					for (int i = 0; i < Integer.valueOf(discountVo.getDistimes()); i++) {
    						qr.update(conn, insertDisSql, new Object[]{StringUtil.generateSequenceNo(),passengerId,discountVo.getId(),MyDate.getMyDateLong(),0,discountVo.getRemark(),discountVo.getDisprice()});
    					}
    				}
                }else{
                	passengerId = passengerList.get(0).getPassengerId();
                }
                
                
                String carId = null;
                if(StringUtils.hasText(v.getCarNumber())){
                	//仅在填写了车辆号的情况下，插入车辆信息
                    String carSql = "insert into pc_car(carId,carModel,carNumber,carColor,carSeats,passengerId) values(?,?,?,?,?,?)";
                    carId = StringUtil.generateSequenceNo();
                    qr.update(conn, carSql, new Object[]{carId,v.getCarModel(),v.getCarNumber(),v.getCarColor(),v.getCarSeats(),passengerId});
                }
                
                
                //插入拼车线路信息
				String lineSql = "insert into pc_line(pcLineId,startStationName,endStationName,lineType,lineSubType,realPrice,goTime,returnTime,repeatTime,carId,carModel,carNumber,carSeats,passengerId,createOn,publisherFlag,lineKm,lineTime,lineStatus,extension) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                String pcLineId = StringUtil.generateSequenceNo();
                Integer publisherFlag = v.getPublisherFlag();
                if(publisherFlag==null){
                	publisherFlag= StringUtils.hasText(v.getCarNumber())?2:1;
                }
                //标志位以线路成功创建准
                flag=qr.update(conn, lineSql, new Object[]{pcLineId,v.getStationName()[0],v.getStationName()[v.getStationName().length-1],v.getLineType(),v.getLineSubType(),v.getRealPrice(),v.getGoTime(),v.getReturnTime(),v.getRepeatTime(),carId,v.getCarModel(),v.getCarNumber(),v.getCarSeats(),passengerId,MyDate.getMyDateLong(),publisherFlag,v.getLineKm(),v.getLineTime(),1,"operation"});
               
                //插入线路站点信息
                String stationSql = "insert into pc_line_station(pcStationId,cityName,stationName,loni,lati,stationOrder,pcLineId) values(?,?,?,?,?,?,?)";
                if(ArrayUtils.isSameLength(v.getStationName(), v.getLoni())&&ArrayUtils.isSameLength(v.getStationName(), v.getLati())){
                	int len = v.getStationName().length;
					for(int i=0;i<len;i++){
						qr.update(conn, stationSql, new Object[]{StringUtil.generateSequenceNo(),v.getCityName()[i],v.getStationName()[i],v.getLoni()[i],v.getLati()[i],i+1,pcLineId});
					}
				}
    			conn.commit();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			flag =-9999;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e1.getLocalizedMessage());
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		return flag;
	}

}
