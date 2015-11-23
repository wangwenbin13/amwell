package com.amwell.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.IDriverInfoDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.DriverBroadcastVo;
import com.amwell.vo.DriverInfoEntity;
import com.amwell.vo.DriverWithdrawAskforVo;
import com.amwell.vo.app.bean.AppVo_3;

/**
 * 司机dao实现类
 * @author 龚雪婷
 *
 */
@Repository
public class DriverInfoDaoImpl extends DaoSupport implements IDriverInfoDao {

	/**
	 * 分页查询司机列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> listByPage(Search search,int p,int pageSize){
		super.finit("driver_info di left join mgr_business mb on di.businessId = mb.businessId");
		sql = "select di.*,mb.brefName as businessName from driver_info di left join mgr_business mb on di.businessId = mb.businessId where di.accountStatus <> 2 ";
//		String cond = " where di.accountStatus <> 2 ";
		List<Object> conditions=new ArrayList<Object>();
		if(search!= null){
			String cond = "";
			//商户简称
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and mb.brefName like ?";
				conditions.add("%"+search.getField01().trim()+"%");
			}
			//司机姓名
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and di.driverName like ?";
				conditions.add("%"+search.getField02().trim()+"%");
			}
			//手机号码
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and di.telephone like ?";
				conditions.add("%"+search.getField03().trim()+"%");
			}
			//帐号状态
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and di.accountStatus = ?";
				conditions.add(search.getField04().trim());
			}
			
			sql = sql+cond;
			
			//排序列名
			if(StringUtils.isNotBlank(search.getField08())){
				sql=sql+" order by "+search.getField08().trim();
//				conditions.add(search.getField08().trim());
			}
			else{
				sql=sql+" order by createOn";
			}
			
			//排序方式
			if(StringUtils.isNotBlank(search.getField07())){
				if(search.getField07().equals("ASC")){
					sql=sql+" ASC";
				}
                if(search.getField07().equals("DESC")){
                	sql=sql+" DESC";
				}
//				sql=sql+" ?";
//				conditions.add(search.getField07().trim());
			}
			else{
				sql=sql+" desc";
			}
		}
		else{
			sql=sql+" order by createOn desc ";
		}
		
		tableDao.setPageSize(pageSize);
		
		list = tableDao.queryByPage(DriverInfoEntity.class, sql,p,conditions.toArray());
		page = new Page(list,sql,p,pageSize,conditions.toArray());
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 分页查询司机提现申请列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> driverApplyListByPage(Search search,int currentPage, int pageSize){
		super.finit("driver_withdraw_askfor dwa,driver_info d,mgr_business mb");
		sql = "SELECT dwa.id as askforId,left(dwa.askforTime,16) AS askforTime,dwa.driverName AS driverName,d.telephone AS telephone,mb.name AS businessName," +
				"dwa.amount AS amount,dwa.withdrawType AS withdrawType,dwa.withdrawAccount AS withdrawAccount,dwa.handleType AS handleType " +
				"FROM driver_withdraw_askfor dwa,driver_info d,mgr_business mb WHERE dwa.driverId = d.driverId AND d.businessId = mb.businessId ";
		List<Object> conditions=new ArrayList<Object>();
		if(search!= null){
			String cond = "";
			//所属商家
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and mb.brefName like ?";
				conditions.add("%"+search.getField01().trim()+"%");
			}
			//司机姓名
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and d.driverName like ?";
				conditions.add("%"+search.getField02().trim()+"%");
			}
			//手机号码
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and d.telephone like ?";
				conditions.add("%"+search.getField03().trim()+"%");
			}
			//提现方式
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and dwa.withdrawType = ?";
				conditions.add(search.getField04().trim());
			}
			//支付状态
			if(search.getField05()!= null && !search.getField05().equals("")){
				cond+= " and dwa.handleType = ?";
				conditions.add(search.getField05().trim());
			}
			
			sql = sql+cond;
			
			//排序列名
			if(StringUtils.isNotBlank(search.getField08())){
				sql=sql+" order by "+search.getField08().trim();
			}
			else{
				sql=sql+" order by dwa.askforTime";
			}
			
			//排序方式
			if(StringUtils.isNotBlank(search.getField07())){
				if(search.getField07().equals("ASC")){
					sql=sql+" ASC";
				}
                if(search.getField07().equals("DESC")){
                	sql=sql+" DESC";
				}
			}
			else{
				sql=sql+" desc";
			}
		}
		else{
			sql=sql+" order by dwa.askforTime desc ";
		}
		
		tableDao.setPageSize(pageSize);
		
		list = tableDao.queryByPage(DriverWithdrawAskforVo.class, sql,currentPage,conditions.toArray());
		page = new Page(list,sql,currentPage,pageSize,conditions.toArray());
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	/**
	 * 分页查询司机奖励列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> driverPaymentListByPage(Search search,int currentPage, int pageSize){
		map.put("list", null);
		map.put("page", null);
		return map;
	}
	
	/**
	 * 分页查询司机报站明细
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> driverBroadcastListByPage(Search search,int currentPage, int pageSize){
		super.finit("driver_info a LEFT JOIN line_class_info b ON a.driverId = b.driverId LEFT JOIN line_base_info c ON b.lineBaseId = c.lineBaseId");
		String sql = "SELECT b.orderDate,mb.brefName,a.driverName,a.telephone,c.lineName,b.orderStartTime," +
			  "LEFT(TIME(DATE_ADD(CONCAT(b.orderDate,' ',b.orderStartTime), INTERVAL b.delayTime MINUTE)),5) AS actualStartTime," +
			  "b.dispatchStatus,b.currentStationName,c.lineBaseId,b.lineClassId,"
			  + "(SELECT NAME FROM pb_station WHERE lineId=c.lineBaseId ORDER BY sortNum LIMIT 0,1) AS startStation,"
			  + "(SELECT NAME FROM pb_station WHERE lineId=c.lineBaseId ORDER BY sortNum DESC LIMIT 0,1) AS endStation,"
			  + "b.orderSeats FROM " +
			  "driver_info a left join mgr_business mb on a.businessId = mb.businessId "+
			  "LEFT JOIN line_class_info b ON a.driverId = b.driverId "+
			  "LEFT JOIN line_base_info c ON b.lineBaseId = c.lineBaseId" +
			  " WHERE a.accountStatus = '0' and b.delFlag = '0' and c.lineType = '1' and c.lineStatus = '3' and LEFT(b.orderDate,7) = '"+search.getField01()+"'"
			  + " ORDER BY b.orderDate,mb.brefName,a.driverId,b.orderStartTime ";//查询非招募已发布状态的线路
		List<DriverBroadcastVo> list = tableDao.queryList(DriverBroadcastVo.class, sql);
		if(null!=list&&list.size()>0){
			
			//查询所有线路各班次对应的买票数
			super.finit("line_business_order lbo LEFT JOIN lease_base_info lbi ON lbi.leaseOrderNo = lbo.leaseOrderId");
			StringBuffer sqlBuffer = new StringBuffer(
			" SELECT lbi.lineBaseId as a1,lbo.lineClassId as a2,COUNT(lbi.leaseOrderId) as a3 FROM"
			+ " line_business_order lbo LEFT JOIN lease_base_info lbi ON lbi.leaseOrderNo = lbo.leaseOrderId"
			+ " WHERE lbi.ispay = '1' AND lbo.status IN('0','2') GROUP BY lbi.lineBaseId,lbo.lineClassId ");
			List<AppVo_3> voList= tableDao.queryList(AppVo_3.class, sqlBuffer.toString());
			
			int zNum=0;
			int oNum=0;
			int tNum=0;
			for (DriverBroadcastVo vo : list) {
				/*if(StringUtils.isBlank(vo.getStartStation())){
					System.out.println("lineBaseId==="+vo.getLineBaseId());
				}
				else{
					String[] strs=vo.getStartStation().split("-");
					vo.setStartStation(strs[0]);
					vo.setEndStation(strs[strs.length-1]);
				}*/
				
				//dispatchStatustinyint(4) NULL发车状态（0.未发车 1.已发车 2.行程结束）
				if(vo.getDispatchStatus().equals("0")){
					zNum++;
					vo.setStartStationB("N");
					vo.setEndStationB("N");
				}
				if(vo.getDispatchStatus().equals("1")){
					oNum++;
					vo.setStartStationB("Y");
					vo.setEndStationB("N");
				}
				if(vo.getDispatchStatus().equals("2")){
					tNum++;
					vo.setStartStationB("Y");
					vo.setEndStationB("Y");
				}
				
//				if(StringUtils.isBlank(vo.getActualStartTime())){
//					vo.setActualStartTime(vo.getOrderStartTime());
//				}
				
				//查询上座率
				/*super.finit("lease_base_info lbi,line_business_order lbo");
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer.append(" select lbi.leaseOrderId from lease_base_info lbi,line_business_order lbo ");
				sqlBuffer.append(" where lbi.leaseOrderNo = lbo.leaseOrderId and lbi.lineBaseId = ? and lbo.lineClassId = ? and lbi.ispay = '1' and lbo.status in('0','2')");
				args = new Object[2];
				args[0] = vo.getLineBaseId();
				args[1] = vo.getLineClassId();
				//买票数
				int totalBuyPeople=tableDao.queryCount(sqlBuffer.toString(), args);*/
				
				//买票数
				int totalBuyPeople=0;
				for (AppVo_3 appVo_3 : voList) {
					if(vo.getLineBaseId().equals(appVo_3.getA1())&&vo.getLineClassId().equals(appVo_3.getA2())){
						totalBuyPeople=Integer.parseInt(appVo_3.getA3());
					}
				}
				
//				sqlBuffer = new StringBuffer();
//				sqlBuffer.append(" select lbi.leaseOrderId from lease_base_info lbi,line_business_order lbo ");
//				sqlBuffer.append(" where lbi.leaseOrderNo = lbo.leaseOrderId and lbi.lineBaseId = ? and lbo.lineClassId = ? and lbi.ispay = '1' and lbo.isAboard = '1'");
//				args = new Object[2];
//				args[0] = vo.getLineBaseId();
//				args[1] = vo.getLineClassId();
//				//上车数
//				int totalAboardPeople = tableDao.queryCount(sqlBuffer.toString(), args);
//				IF(TOTALBUYPEOPLE==0){
//					VO.SETUPPERLIMB("0%");
//				}
//				ELSE{
//					BIGDECIMAL BD=BIGDECIMAL.VALUEOF(TOTALABOARDPEOPLE).DIVIDE(BIGDECIMAL.VALUEOF(TOTALBUYPEOPLE),2,BIGDECIMAL.ROUND_HALF_UP);
//					VO.SETUPPERLIMB(BD.MULTIPLY(BIGDECIMAL.VALUEOF(100))+"%");
//				}
				vo.setTotalBuyPeople(totalBuyPeople+"");
				if(totalBuyPeople==0){
					vo.setUpperLimb("0.00%");
				}
				else{
					//买票数除以总座位数
					BigDecimal bd=BigDecimal.valueOf(totalBuyPeople).divide(BigDecimal.valueOf(Integer.parseInt(vo.getOrderSeats())),2,BigDecimal.ROUND_HALF_EVEN);
					vo.setUpperLimb(bd.multiply(BigDecimal.valueOf(100))+"%");
				}
			}
			System.out.println("zNum=="+zNum);
			System.out.println("oNum=="+oNum);
			System.out.println("tNum=="+tNum);
		}
		map.put("list", list);
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(40/51);
		System.out.println(BigDecimal.valueOf(30).divide(BigDecimal.valueOf(51),2,BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(100)));
	}
	
	
	/**
	 * 根据id查询司机对象
	 * @param id
	 * @return
	 */
	public DriverInfoEntity getDriverById(String id) {
		super.finit("driver_info di left join mgr_business mb on di.businessId = mb.businessId");
		args = new Object[1];
		args[0] = id;
		String sql="select di.*,mb.brefName as businessName from driver_info di left join mgr_business mb on di.businessId = mb.businessId where di.driverId = ?";
		return tableDao.queryBean(DriverInfoEntity.class,sql,args);
	}
	
	/**
	 * 根据id查询司机提现申请详情
	 * @param id
	 * @return
	 */
	public DriverWithdrawAskforVo getDriverApplyById(String askforId){
		super.finit("driver_info di left join mgr_business mb on di.businessId = mb.businessId");
		args = new Object[1];
		args[0] = askforId;
		String sql = "SELECT left(dwa.askforTime,16) AS askforTime,dwa.driverName AS driverName,d.telephone AS telephone,mb.name AS businessName," +
		"dwa.amount AS amount,dwa.withdrawType AS withdrawType,dwa.withdrawAccount AS withdrawAccount,dwa.handleType AS handleType,dwa.handleBy as handleBy,left(dwa.handleOn,16) as handleOn " +
		"FROM driver_withdraw_askfor dwa,driver_info d,mgr_business mb WHERE dwa.driverId = d.driverId AND d.businessId = mb.businessId and dwa.id = ?";
		return tableDao.queryBean(DriverWithdrawAskforVo.class,sql,args);
	}
	
	/**
	 * 后台处理提现申请信息
	 * @param userName
	 * @param askforId
	 * @return
	 */
	public int updateDriverWithdrawAskfor(String userName,String askforId){
		super.finit("driver_withdraw_askfor");
		String sql="UPDATE `driver_withdraw_askfor` SET handleType = '1',`handleBy` = ? , `handleOn` = NOW() WHERE `id` = ?";
		args=new Object[2];
		args[0]=userName;
		args[1]=askforId;
		return tableDao.executeSQL(sql, args);
	}
	
	/**
	 * 保存或者修改司机信息
	 * @param driverInfo
	 * @return
	 */
	public int saveOrUpdateDriver(DriverInfoEntity driverInfo){
		return 0;
	}
	
	/**
	 * 根据id删除司机信息，id可以是单个id或多个id用逗号拼接的(只改变信息状态，不物理删除)
	 * @param id
	 * @return
	 */
	public int deleteDriverById(String id){
		return 0;
	}
}
