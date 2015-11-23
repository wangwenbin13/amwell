package com.pig84.ab.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IStationInfoDao;
import com.pig84.ab.dao.ITicketDao;
import com.pig84.ab.vo.StationInfo;
import com.pig84.ab.vo.TicketInfo;
import com.pig84.ab.vo.bean.AppVo_11;
import com.pig84.ab.vo.bean.AppVo_14;
import com.pig84.ab.vo.bean.AppVo_3;
/**
 * 
 * @author wangwenbin
 *
 */
/**
 * 车票相关
 */
@Repository
public class TicketDaoImpl extends BaseDao  implements ITicketDao {
	
	@Autowired
	private IStationInfoDao stationInfoDao;

	/**
	 * 车票列表
	 * 
	 * @param pageSize 一页大小
	 * 
	 * @param currentPage 当前页
	 * 
	 * @param passengerId 乘客id
	 * 
	 * @param date 日期
	 */
	public List<TicketInfo> queryTicketList(int pageSize, int currentPage,String passengerId,String date) {
		StringBuffer tableBuffer = new StringBuffer();
		tableBuffer.append(" lease_base_info as lease left join line_business_order AS businessOrder on lease.leaseOrderNo = businessOrder.leaseOrderId ");
		tableBuffer.append(" left join ( select * from line_class_info where orderDate >=? ) as classInfo on businessOrder.lineClassId = classInfo.lineClassId ");
		tableBuffer.append(" left join line_base_info as line on line.lineBaseId = lease.lineBaseId ");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select lease.slineId as a1,classInfo.price as a6,lease.lineBaseId as a3,line.lineKm as a4,line.lineTime as a5,line.orderPrice as a13,");
		sqlBuffer.append(" classInfo.orderStartTime as a8,lease.ustation as a9,lease.dstation AS a10,lease.leaseOrderNo as a11 ");
		sqlBuffer.append(" from ").append(tableBuffer.toString());
		sqlBuffer.append(" where businessOrder.passengerId = ? and classInfo.orderDate >= ? and lease.ispay = 1 group by a1,a11 order by classInfo.orderDate,a8 ");
		Object[] params = new Object[3];
		params[0] = date;
		params[1] = passengerId;
		params[2] = date;
		List<TicketInfo> ticketList = queryByPage(TicketInfo.class, sqlBuffer.toString(), currentPage, pageSize, params);
		if(ticketList!=null){
			for(TicketInfo ticketInfo : ticketList){
				List<StationInfo> stationInfoList = stationInfoDao.listByLineId(ticketInfo.getA3());
				StringBuffer stationNameArray = new StringBuffer();
				for(StationInfo stationInfo : stationInfoList){
					stationNameArray.append(stationInfo.getId()).append(",");
				}
				ticketInfo.setA7(stationNameArray.toString());
			}
		}
		return ticketList;
	}

	/**
	 * 当天有购买的班次
	 * 
	 * @param passengerId 乘客id
	 * 
	 * @param date 日期
	 */
	public List<AppVo_11> queryHasTickList(String passengerId, String date) {
		StringBuffer tableBuffer = new StringBuffer();
		tableBuffer.append(" lease_base_info as lbi left join line_business_order as lbo on lbi.leaseOrderNo = lbo.leaseOrderId ");
		tableBuffer.append(" left join line_class_info as lci on lbo.lineClassId = lci.lineClassId ");
		tableBuffer.append(" left join line_base_info as line on line.lineBaseId = lbi.lineBaseId ");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select lbi.slineId as a1,lci.price as a2,lbi.lineBaseId as a3,line.lineKm as a4,line.lineTime as a5,");
		sqlBuffer.append(" line.orderPrice as a6,lci.orderStartTime as a8,lbi.ustation as a9,lbi.dstation as a10,lbi.leaseOrderNo as a11");
		sqlBuffer.append(" from "+tableBuffer.toString());
		sqlBuffer.append(" where lbo.passengerId = ? and lci.orderDate = ? and lbi.ispay = 1 group by a1,a11 ");
		Object[] params = new Object[2];
		params[0] = passengerId;
		params[1] = date;
		return queryList(AppVo_11.class, sqlBuffer.toString(), params);
	}

	/**展示电子票V2.3**/
	public AppVo_14 showTicket_V2_3(String leaseOrderNo, String passengerId,String cid){
//		SELECT a.localid AS a2,a.price AS a3,a.status AS a4,b.isdiscount AS a5,b.ustation AS a6,b.dstation AS a7,b.lineBaseId AS a8,(SELECT COUNT(*) FROM passenger_comments t WHERE t.passengerid = ? AND t.leaseorderid = a.leaseorderid AND t.lineclassid = a.lineClassId) AS a13 FROM line_business_order a LEFT JOIN lease_base_info b ON a.leaseOrderId = b.leaseOrderNo WHERE a.passengerid = ? AND a.leaseorderid = ? AND a.lineClassId = ? AND b.ispay = '1'
		String sql_order = "SELECT a.localid AS a2,a.price AS a3,a.status AS a4,b.isdiscount AS a5,b.ustation AS a6,b.dstation AS a7,b.lineBaseId AS a8,(SELECT COUNT(*) FROM passenger_comments t WHERE t.passengerid = ? AND t.leaseorderid = a.leaseorderid AND t.lineclassid = a.lineClassId) AS a13,b.slineid as a14 FROM line_business_order a LEFT JOIN lease_base_info b ON a.leaseOrderId = b.leaseOrderNo WHERE a.passengerid = ? AND a.leaseorderid = ? AND a.lineClassId = ? AND b.ispay = '1'";
		Object[] params = new Object[4];
		params[0] = passengerId;
		params[1] = passengerId;
		params[2] = leaseOrderNo;
		params[3] = cid;
		AppVo_14 vo_order = queryBean(AppVo_14.class, sql_order, params);
		if(vo_order!=null){
			StationInfo uStation = new StationInfo(vo_order.getA6());
			vo_order.setA6(uStation.getName());//上车点
			StationInfo dStation = new StationInfo(vo_order.getA7());
			vo_order.setA7(dStation.getName());//下车点
		}else{
			vo_order = new AppVo_14();
		}
//		SELECT a.orderStartTime as a1,a.orderdate as a2,(SELECT vehicleNumber FROM vehicle_info WHERE vehicleId = a.vehicleid) AS a3 FROM line_class_info a WHERE lineclassid = '150529143255222960'
		String sql_driver = "SELECT a.orderStartTime as a1,a.orderdate as a2,(SELECT vehicleNumber FROM vehicle_info WHERE vehicleId = a.vehicleid) AS a3 FROM line_class_info a WHERE lineclassid = ?";
		Object[] params1 = new Object[1];
		params1[0] = cid;
		AppVo_3 vo_driver = queryBean(AppVo_3.class, sql_driver, params1);
		
		if(vo_driver != null){
			vo_order.setA9(vo_driver.getA1());
			vo_order.setA10(vo_driver.getA2());
			vo_order.setA11(vo_driver.getA3());
		}
		
		return vo_order;
	}

}
