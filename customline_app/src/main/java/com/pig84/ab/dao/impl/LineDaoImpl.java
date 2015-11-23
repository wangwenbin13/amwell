package com.pig84.ab.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ILineDao;
import com.pig84.ab.dao.IStationInfoDao;
import com.pig84.ab.service.ILineService;
import com.pig84.ab.utils.Conn;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.Coord;
import com.pig84.ab.vo.LineBaseInfo;
import com.pig84.ab.vo.LineEnrollUserNew;
import com.pig84.ab.vo.LineSearchCondition;
import com.pig84.ab.vo.LineSplitInfo;
import com.pig84.ab.vo.LineUnion;
import com.pig84.ab.vo.LineUserApplication;
import com.pig84.ab.vo.LineUserApplicationNew;
import com.pig84.ab.vo.LineUserApplicationStationNew;
import com.pig84.ab.vo.ParamVo;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.PassengerSearchLine;
import com.pig84.ab.vo.StationInfo;
import com.pig84.ab.vo.StationUnion;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_11;
import com.pig84.ab.vo.bean.AppVo_14;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_15_list;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_6;

/**
 * 线路相关
 * @author zhangqiang
 *
 */
@Repository
public class LineDaoImpl extends BaseDao implements ILineDao {

	private static final BigDecimal TICKET_PRICE_MAX = BigDecimal.valueOf(15.0);

	private static final BigDecimal TICKET_PRICE_MIN = BigDecimal.valueOf(8.0);

	@Autowired
	private IStationInfoDao stationInfoDao;

	/**
	 * 获取线路信息列表
	 */
	public List<LineBaseInfo> getLineInfoByPage(LineSearchCondition searchCondition) {
		String userid = searchCondition.getUserId();
		String sql_user = "select * from passenger_info where passengerId = ?";
		Object[] params = new Object[1];
		params[0] = userid;
		PassengerInfo user =  queryBean(PassengerInfo.class, sql_user,params);
		List<String> condition=new ArrayList<String>();
		//获取线路信息
		String sql = "";
		if(!"".equals(userid)&&userid!=null){
			sql = "SELECT a.*,(SELECT COUNT(*) FROM line_enroll t WHERE t.lineBaseId = a.lineBaseId) AS pCount," +
					"(SELECT COUNT(*) FROM line_enroll t WHERE t.lineBaseId = a.lineBaseId and t.applyTel = ?) as isSign FROM line_base_info a ";
			condition.add(user.getTelephone());
		}else{
			sql = "SELECT a.*,(SELECT COUNT(*) FROM line_enroll t WHERE t.lineBaseId = a.lineBaseId) AS pCount FROM line_base_info a ";
		}
		String cond = " where 1 = 1 ";
		String lineType = searchCondition.getLineType();
		String cityName = searchCondition.getCityName();
		String areaName = searchCondition.getAreaName();
		//上下班
		if("1".equals(lineType)){
			cond+=" and a.lineType = '1' and a.lineSubType = '0' and a.lineStatus = '3' and a.cityName like ?";
			condition.add("%"+cityName+"%");
			if(areaName!=null && !"".equals(areaName) && !"全部线路".equals(areaName)){
				cond+=" and startArea =? and endArea = ?";
				condition.add(areaName.substring(0,areaName.indexOf("-")));
				condition.add(areaName.substring(areaName.indexOf("-")+1,areaName.length()));
			}
		}
		//自由行
		if("2".equals(lineType)){
			cond+=" and a.lineType = '1' and a.lineSubType = '1' and a.lineStatus = '3' and a.cityName like ?";
			condition.add("%"+cityName+"%");
		}
		//招募
		if("3".equals(lineType)){
			cond+=" and a.lineType = '0' and a.lineStatus = '1' and a.cityName like ?";
			condition.add("%"+cityName+"%");
		}
		String anotherCond = "";
		String stationName = searchCondition.getStationName();
		//站点搜索
		if(!StringUtils.isEmpty(stationName)){
			anotherCond =  " WHERE b.stationNames like ? ";
			condition.add("%"+stationName+"%");
		}
		String sStation = searchCondition.getsStation();
		String eStation = searchCondition.geteStation();
		if(!StringUtils.isEmpty(sStation) || !StringUtils.isEmpty(eStation)){
			if(StringUtils.isEmpty(sStation)){
				sStation = "";
			}
			if(StringUtils.isEmpty(eStation)){
				eStation = "";
			}
			cond +=" HAVING ((stationNames LIKE ? AND stationNames LIKE ?)) ";
			condition.add("%"+sStation+"%");
			condition.add("%"+eStation+"%");
		}
		int currentPage = searchCondition.getCurrentPage();
		int pageSize = searchCondition.getPageSize();
		List<LineBaseInfo> list = queryByPage(LineBaseInfo.class, "SELECT * FROM ("+sql+cond+" ORDER BY a.displayFlag desc,createOn desc"+") AS b"+anotherCond, currentPage, pageSize,condition.toArray());
		//获取站台信息
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(list.get(i).getLineBaseId());
				list.get(i).setFromStation(stationNameArray.substring(0,list.get(i).getStationNames().indexOf("-")));
				list.get(i).setToStation(stationNameArray.substring(list.get(i).getStationNames().lastIndexOf("-")+1,list.get(i).getStationNames().length()));
			}
		}
		return list;
	}

	/**先找出报名该线路的所有乘客**/
	public List<AppVo_2> getAllAppliTelephoneByLineId(String lineId) {
		String sql = " select passengerId as a1,applyTel as a2 from line_enroll_user where applicationId = ? ";
		Object[] params = new Object[1];
		params[0] = lineId;
		List<AppVo_2> telephones = queryList(AppVo_2.class, sql, params);
		return telephones;
	}
	
	/**找出线路发起人的信息**/
	public AppVo_2 getApplicationPassenger(String id) {
		String sql = " SELECT pass.passengerId AS a1,pass.telephone AS a2 from line_user_application AS appli LEFT JOIN passenger_info AS pass ON " +
				"appli.passengerId = pass.passengerId where appli.applicationId = ?  ";
		Object[] params = new Object[1];
		params[0] = id;
		AppVo_2 appvo_2 = queryBean(AppVo_2.class, sql, params);
		return appvo_2;
	}
	
	/**获取招募线路信息**/
	public LineUserApplication getLineUserApplication(String applicationId) {
		String sql = " SELECT appli.*,f_changeIdsToValues_application(appli.appliStations) as stationNames from line_user_application AS appli LEFT JOIN " +
				"passenger_info AS pass ON appli.passengerId = pass.passengerId where appli.applicationId = ?  ";
		Object[] params = new Object[1];
		params[0] = applicationId;
		LineUserApplication lineUserApplication = queryBean(LineUserApplication.class, sql,params);
		if (null!=lineUserApplication && !StringUtils.isEmpty(lineUserApplication.getStationNames())) {
			String[] stationNames = lineUserApplication.getStationNames().split("-");
			lineUserApplication.setStartAddress(stationNames[0]);
			lineUserApplication.setEndAddress(stationNames[stationNames.length-1]);
		}
		return lineUserApplication;
	}
	
	/**获取城市列表**/
	public List<AppVo_1> getCityList(String cityName){
		String sql = "SELECT cityName as a1 FROM line_base_info WHERE cityName IS NOT NULL and lineStatus = '1' or lineStatus = '3' GROUP BY cityName";
		List<AppVo_1> list = queryList(AppVo_1.class, sql);
		return list;
	}
	
	/**一般用户或没登陆用户**/
	public List<LineBaseInfo> getLineInfoByPageNotIncludeHW(LineSearchCondition searchCondition) {
		String userid = searchCondition.getUserId();
		String sql_user = "select * from passenger_info where passengerId = ?";
		Object[] params = new Object[1];
		params[0] = userid;
		PassengerInfo user =  queryBean(PassengerInfo.class, sql_user,params);
		//获取线路信息
		List<String> condition=new ArrayList<String>();
		String sql = "";
		if(!"".equals(userid)&&userid!=null){
			sql = "SELECT a.*,f_changeIdsToValues(a.stationInfoes) AS stationNames,(SELECT COUNT(*) FROM line_enroll t WHERE t.lineBaseId = a.lineBaseId) AS pCount," +
					"(SELECT COUNT(*) FROM line_enroll t WHERE t.lineBaseId = a.lineBaseId and t.applyTel = ?) as isSign FROM line_base_info a ";
			condition.add(user.getTelephone());
		}else{
			sql = "SELECT a.*,f_changeIdsToValues(a.stationInfoes) AS stationNames,(SELECT COUNT(*) FROM line_enroll t WHERE t.lineBaseId = a.lineBaseId) AS pCount FROM " +
					"line_base_info a ";
		}
		String cond = " where 1 = 1 ";
		String lineType = searchCondition.getLineType();
		String cityName = searchCondition.getCityName();
		String areaName = searchCondition.getAreaName();
		//上下班
		if("1".equals(lineType)){
			cond+=" and a.lineType = '1' and a.lineSubType = '0' and a.lineStatus = '3' and a.cityName like ?";
			condition.add("%"+cityName+"%");
			if(areaName!=null && !"".equals(areaName) && !"全部线路".equals(areaName)){
				cond+=" and startArea =? and endArea = ?";
				condition.add(areaName.substring(0,areaName.indexOf("-")));
				condition.add(areaName.substring(areaName.indexOf("-")+1,areaName.length()));
			}
			//只有上下班才区分华为员工
			cond+=" AND a.lineBaseId NOT IN ( SELECT lineBaseId FROM company_line ) ";
		}
		//自由行
		if("2".equals(lineType)){
			cond+=" and a.lineType = '1' and a.lineSubType = '1' and a.lineStatus = '3' and a.cityName like ?";
			condition.add("%"+cityName+"%");
		}
		//招募
		if("3".equals(lineType)){
			cond+=" and a.lineType = '0' and a.lineStatus = '1' and a.cityName like ?";
			condition.add("%"+cityName+"%");
		}
		String anotherCond = "";
		String stationName = searchCondition.getStationName();
		String sStation = searchCondition.getsStation();
		String eStation = searchCondition.getsStation();
		//站点搜索
		if(!StringUtils.isEmpty(stationName)){
			anotherCond =  " WHERE b.stationNames like ? ";
			condition.add("%"+stationName+"%");
		}
		if(!StringUtils.isEmpty(sStation) || !StringUtils.isEmpty(eStation)){
			if(StringUtils.isEmpty(sStation)){
				sStation = "";
			}
			if(StringUtils.isEmpty(eStation)){
				eStation = "";
			}
			cond +=" HAVING ((stationNames LIKE ? AND stationNames LIKE ?)) ";
			condition.add("%"+sStation+"%");
			condition.add("%"+eStation+"%");
		}
		int currentPage = searchCondition.getCurrentPage();
		int pageSize = searchCondition.getPageSize();
		List<LineBaseInfo> list = queryByPage(LineBaseInfo.class, "SELECT * FROM ("+sql+cond+" ORDER BY a.displayFlag desc,updateon desc "+") AS b"+anotherCond, currentPage, pageSize,condition.toArray());
		//获取站台信息
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				LineBaseInfo lineBaseInfo = list.get(i);
				String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(lineBaseInfo.getLineBaseId());
				if(stationNameArray!=null && !"".equals(stationNameArray)){
					lineBaseInfo.setFromStation(stationNameArray.substring(0,stationNameArray.indexOf("-")));
					lineBaseInfo.setToStation(stationNameArray.substring(stationNameArray.lastIndexOf("-")+1,stationNameArray.length()));
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取线路发车时间
	 */
	public String getStartTime(String linebaseid){
		String sql = "SELECT orderStarttime as a1 FROM line_class_info WHERE delFlag = '0' AND lineBaseId = ? ORDER BY orderStarttime DESC LIMIT 0,1";
//		String sql = "SELECT orderStarttime as a1 FROM line_class_info WHERE delFlag = '0' AND orderdate = '"+MyDate.Format.DATE.now()+"' AND lineBaseId = ? ORDER BY orderStarttime DESC LIMIT 0,1";
		Object[] params = new Object[1];
		params[0] = linebaseid;
		AppVo_1 vo = queryBean(AppVo_1.class, sql,params);
		if(vo!=null && vo.getA1() != null){
			return vo.getA1();
		}else{
			return "";
		}
	}
	
	/**获取线路的条数**/
	@Override
	public int queryLineCount(boolean flag,String lineType,String cityName,String areaName,String stationName) {
		String lineSubType = "0";         /**线路子类型 0:上下班 1:旅游**/
		String lineStatus = "";          /**线路类型：1（非招募） 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除 --->线路类型：0（招募） 0:招募前 1：招募中 2：关闭 3：删除**/
		if("1".equals(lineType)){
			/**线路类型 0:招募 1:非招募**/
			lineStatus = "3";
		}else {
			lineType = "0";
			lineStatus = "1";
		}
		List<String> condition=new ArrayList<String>();
		/**查询记录数的sql**/
		String sql;
		if(flag){
			/**华为员工**/
			sql = " SELECT * FROM line_base_info a where 1=1 and a.lineType = ? and " +
					"a.lineSubType = ? and a.lineStatus = ? AND a.cityName LIKE ?";
		}else{
			/**非华为员工**/
			sql = " SELECT * FROM line_base_info a where 1=1 and a.lineType = ? and " +
					"a.lineSubType = ? and a.lineStatus = ? AND a.lineBaseId NOT IN ( SELECT lineBaseId FROM company_line ) AND a.cityName LIKE ? ";
		}
		condition.add(lineType);
		condition.add(lineSubType);
		condition.add(lineStatus);
		condition.add("%"+cityName+"%");
		
		if(areaName!=null && !"".equals(areaName) && !"全部线路".equals(areaName)){
			sql+=" and a.startArea =? and a.endArea = ?";
			condition.add(areaName.substring(0,areaName.indexOf("-")));
			condition.add(areaName.substring(areaName.indexOf("-")+1,areaName.length()));
		}
		if(!StringUtils.isEmpty(stationName)){
			List<StationInfo> stationInfoList = stationInfoDao.listByName(stationName);
			StringBuffer lineIdWhere = new StringBuffer();
			for(int index=0;index<stationInfoList.size();index++){
				StationInfo stationInfo = stationInfoList.get(index);
				lineIdWhere.append(stationInfo.getLineId());
				if(index!=stationInfoList.size()-1){
					lineIdWhere.append(",");
				}
			}
			sql = " SELECT * FROM ( "+sql+" ) as b where 1=1 and b.lineBaseId in ("+lineIdWhere+")";
		}
		int count = queryCount(sql,condition.toArray());
		return count;
	}
	
	
/**************************************************************************新增接口（V2.0）***************************************************************************/

	/**根据起点和终点查询线路拆分表line_split_info的主键ID：sid不重复**/
	public List<LineSplitInfo> querySplieSid(List<StationInfo> sList,
			List<StationInfo> eList,boolean flag) {
		String condition = "";
		List<String> con = new ArrayList<String>();
		String sql = " SELECT sid,bstation,estation,linebaseid FROM line_split_info WHERE 1=1 AND iswork = 1 ";
		
		for(int i = 0;i<sList.size();i++){
			condition += " bstation = ? OR";
			con.add(sList.get(i).getId());
		}
		if(!StringUtils.isEmpty(condition)){
			condition = condition.substring(0, condition.length()-2);
			sql += " AND ( ";
			sql += condition+" ) ";
		}
		condition = "";
		for(int i=0;i<eList.size();i++){
			condition += " estation = ? OR";
			con.add(eList.get(i).getId());
		}
		if(!StringUtils.isEmpty(condition)){
			condition = condition.substring(0, condition.length()-2);
			sql += " AND ( ";
			sql += condition+" ) ";
		}
		if(!flag){
			//不是华为员工
			sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
		}
		List<LineSplitInfo> sids = queryList(LineSplitInfo.class, sql,con.toArray());
		return sids;
	}
	
	
	/**根据起点信息和条件获得线路拆分表line_split_info的主键ID：sid不重复**/
	public List<LineSplitInfo> querySplieSid(List<StationInfo> list, int i,boolean flag) {
		if(null==list || list.size()==0){
			return null;
		}
		String condition = "";
		List<String> con = new ArrayList<String>();
		String sql = " SELECT sid,bstation,estation,linebaseid FROM line_split_info WHERE 1=1 AND iswork = 1 AND ";
		if(i==1){
			//起点信息存在
			sql += " ( ";
			for(StationInfo stationInfo : list){
				condition += " bstation=? OR";
				con.add(stationInfo.getId());
			}
			condition = condition.substring(0, condition.length()-2);
			sql += condition+" ) ";
			
		}else if(i==2){
			//终点信息存在
			sql += " ( ";
			for(StationInfo stationInfo : list){
				condition += " estation=? OR";
				con.add(stationInfo.getId());
			}
			condition = condition.substring(0, condition.length()-2);
			sql += condition+" ) ";
		}
		if(!flag){
			//不是华为员工
			sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
		}
		sql += " ORDER BY linebaseid ";
		List<LineSplitInfo> sids = queryList(LineSplitInfo.class, sql,con.toArray());
		return sids;
	}


	/**根据站点线路ID获取对应的线路信息**/
	public List<LineBaseInfo> queryLineBaseInfoById(String lineBaseIds,String cityName) {
		List<LineBaseInfo> list = null;
		Connection conn = null;
		try {
			conn = Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			if(!StringUtils.isEmpty(lineBaseIds)){
				//SELECT a.lineBaseId,a.lineTime,a.lineKm,a.lineType,a.lineSubType,a.lineStatus,b.orderStartTime,b.price FROM line_base_info AS a LEFT JOIN line_class_info AS b ON a.lineBaseId = b.lineBaseId WHERE b.orderDate IN (SELECT max(orderDate) FROM line_class_info GROUP BY lineBaseId,orderStartTime ) GROUP BY b.lineBaseId,b.orderStartTime
				String sql = " SELECT a.lineBaseId,a.lineTime,a.lineKm,a.lineType,a.lineSubType,a.lineStatus,a.orderPrice,a.lineName,a.originalPrice "
					+" FROM line_base_info AS a "
					+" WHERE 1=1 AND a.lineType = 1 AND a.lineSubType = 0 AND a.lineStatus = 3 ";
				List<String> paramList=new ArrayList<String>();
				if(!StringUtils.isEmpty(lineBaseIds)){
					lineBaseIds = lineBaseIds.substring(0, lineBaseIds.length()-1);
					String[] strs=lineBaseIds.split(",");
					StringBuffer sb=new StringBuffer();
					for (String s : strs) {
						if(!StringUtils.isEmpty(s)){
							paramList.add(s);
							sb.append("?,");
						}
					}
					sql += " AND a.lineBaseId in ("+sb.substring(0,sb.length()-1)+") ";
				}
				if(!StringUtils.isEmpty(cityName)){
					sql += " AND cityName LIKE ?  ";
					paramList.add("%"+cityName+"%");
				}
				list = qr.query(conn, sql, new BeanListHandler<LineBaseInfo>(LineBaseInfo.class), paramList.toArray());
				if(null==list || list.size()==0){
					return list;
				}
				//根据线路获取当天的票价,如果当天没排班,则获取线路价格
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				LineBaseInfo newLi = null;
				for(LineBaseInfo li : list){
					sql = " SELECT orderStartTime,price FROM line_class_info WHERE orderDate = ? AND lineBaseId = ? AND delFlag = 0 GROUP  BY lineBaseId  ";
					Object[] params = new Object[2];
					params[0] = sdf.format(new Date());
					params[1] = li.getLineBaseId();
					newLi = qr.query(conn, sql, new BeanHandler<LineBaseInfo>(LineBaseInfo.class), params);
					if(null==newLi){
						//SELECT a.orderStartTime,b.orderPrice AS price FROM line_class_info AS a LEFT JOIN line_base_info AS b ON a.lineBaseId = b.lineBaseId WHERE  a.lineBaseId = '150320163604997107' AND a.delFlag = 0 GROUP  BY a.lineBaseId limit 1
						sql = " SELECT a.orderStartTime,b.orderPrice AS price FROM line_class_info AS a LEFT JOIN line_base_info AS b ON a.lineBaseId = b.lineBaseId WHERE  a.lineBaseId = ? AND a.delFlag = 0 GROUP  BY a.lineBaseId limit 1 ";
						params = new Object[1];
						params[0] = li.getLineBaseId();
						newLi = qr.query(conn, sql, new BeanHandler<LineBaseInfo>(LineBaseInfo.class), params);
					}
					li.setPrice(newLi.getPrice());//班次价格
					li.setOrderStartTime(newLi.getOrderStartTime());
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**根据招募站点ID获取站点信息**/
	public List<StationUnion> queryZhaoMuStationById(String allStationIds) {
		List<StationUnion> list = null;
		String sql = null;
		if(!StringUtils.isEmpty(allStationIds)){
			sql = " SELECT appliStationId as id,stationName,loni,lati FROM line_user_application_station_new WHERE 1=1 ";
			allStationIds = allStationIds.substring(0, allStationIds.length()-1);
			String[] strs=allStationIds.split(",");
			StringBuffer sb=new StringBuffer();
			List<String> paramList=new ArrayList<String>();
			for (String s : strs) {
				paramList.add(s);
				sb.append("?,");
			}
			sql += " AND appliStationId in ("+sb.substring(0,sb.length()-1)+") ";
			list = queryList(StationUnion.class, sql, paramList.toArray());
		}
		return list;
	}
	
	@Autowired
	private ILineService lineService;
	
	/**
	 * 购票历史
	 * 
	 * @param imei
	 * 
	 * @param lat
	 * 
	 * @param lon 
	 * 
	 * @param cityName
	 * 
	 * @param passengerId
	 */
	public List<AppVo_15> getHistoryLine(String imei,Coord coord,String cityName,String passengerid){
		List<AppVo_15> result=new ArrayList<AppVo_15>();
		List<AppVo_11> list=null;
		Connection conn = null;
		try {
			conn = Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			if(passengerid!=null && !"".equals(passengerid)){
				//有用户信息
				//1.如果用户登录，存在购票记录，则返回最近的五条购票对应的线路
				StringBuffer sqlBuffer=new StringBuffer();
				sqlBuffer.append(" select distinct lbi.slineId as a1,lbi.ustation as a2,lbi.dstation as a3,lci.orderStartTime as a4,'1' as a5,lbi.lineBaseId as a6,");
				sqlBuffer.append(" lb.lineKm AS a7,lb.lineTime as a8,lb.orderPrice as a9,lb.originalPrice AS a11 ");
				sqlBuffer.append(" from lease_base_info lbi,line_business_order lbo,line_class_info lci,line_split_info lsi,line_base_info lb ");
			    sqlBuffer.append(" where lbi.leaseOrderNo = lbo.leaseOrderId and lbi.passengerId = lbo.passengerId and lbo.lineClassId = lci.lineClassId and lbi.slineId = lsi.sid ");
				sqlBuffer.append(" and lbi.lineBaseId = lb.lineBaseId and ");
				sqlBuffer.append(" (lbi.ispay = '1' or lbi.lineBaseId in(select distinct lineBaseId from return_ticket where passengerId = ?)) ");
				sqlBuffer.append(" and lci.delFlag = '0' and lsi.iswork = '1' and lbi.passengerId = ? ");
				sqlBuffer.append(" and lb.lineType = '1' and lb.lineSubType = '0' and lb.lineStatus = '3' ");
				sqlBuffer.append(" group by lbi.slineId order by max(leaseOrderTime) desc limit 0,10");
				Object[] params=new Object[2];
				params[0]=passengerid;
				params[1]=passengerid;
				list = qr.query(conn, sqlBuffer.toString(), new BeanListHandler<AppVo_11>(AppVo_11.class), params);
				if(null==list||list.size()==0){
					//2.如果用户登录，不存在购票记录，则返回经纬附近的五条已购票线路
					return getInfo(coord,cityName,passengerid);
				}
			}
			else{
				//3.如果用户未登录，则返回经纬附近的五条已购票线路
				return getInfo(coord,cityName,null);
			}
			
			if(null!=list&&list.size()>0){
				for(AppVo_11 appVo10 : list){
					String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(conn,appVo10.getA6());
					appVo10.setA10(stationNameArray);
				}
				List<String> condition=new ArrayList<String>();
				StringBuffer sb=new StringBuffer();
				for (AppVo_11 appVo_10 : list) {
					condition.add(appVo_10.getA2());
					condition.add(appVo_10.getA3());
					sb.append("?,?,");
				}
				for (AppVo_11 appVo_10 : list) {
					condition.add(appVo_10.getA2());
					condition.add(appVo_10.getA3());
				}
				//查询站点名称
				String sql="SELECT DISTINCT lsi.id AS a1,lsi.name AS a2 FROM pb_station lsi WHERE lsi.id IN ("+sb.substring(0,sb.length()-1)+") ORDER BY FIELD(lsi.id,"+sb.substring(0,sb.length()-1)+")";
				List<AppVo_2> temp=qr.query(conn, sql, new BeanListHandler<AppVo_2>(AppVo_2.class), condition.toArray());
				if(null!=temp&&temp.size()>0){
					
					//封装返回结果
					AppVo_15 vo15=null;
					AppVo_1 vo1=null;
					for (AppVo_11 appVo_10 : list) {
						vo15=new AppVo_15();
						vo15.setA1(appVo_10.getA6());//主线路id
						vo15.setA2(appVo_10.getA1());//子线路id
						vo15.setA3(appVo_10.getA4());//班次时间
						vo15.setA4(appVo_10.getA2());//起点id
						String ustationName=null;
						for (AppVo_2 appVo_2 : temp) {
							if (appVo_10.getA2()!=null && appVo_10.getA2().equals(appVo_2.getA1())) {
								ustationName=appVo_2.getA2();
								break;
							}
						}
						vo15.setA5(ustationName);//起点名称
						vo15.setA6(appVo_10.getA3());//终点id
						String dstationName=null;
						for (AppVo_2 appVo_2 : temp) {
							if (appVo_10.getA2()!=null && appVo_10.getA3().equals(appVo_2.getA1())) {
								dstationName=appVo_2.getA2();
								break;
							}
						}
						vo15.setA7(dstationName);//终点名称
						//4.标识线路自己购买过的还是附近的(1.自己购买过  2.附近的)
						vo15.setA8(appVo_10.getA5());
						vo15.setA9(appVo_10.getA7());//行程公里数
						vo15.setA10(appVo_10.getA8());//预计行程时间
						vo15.setA11(appVo_10.getA9());//票价
						vo15.setA12(StringUtils.isBlank(appVo_10.getA10())?"":appVo_10.getA10().split("-")[0]);//始发站名
						vo15.setA14(appVo_10.getA11());//原价
						
						//查询当前线路对应最大日期的单价
						//先查看当天的票价
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						sql = " SELECT price AS a1 FROM line_class_info WHERE orderDate = ? AND lineBaseId = ? AND delFlag = 0 GROUP  BY lineBaseId LIMIT 0,1 ";
						Object[] params = new Object[2];
						params[0] = sdf.format(new Date());
						params[1] = appVo_10.getA6();
						vo1=qr.query(conn, sql, new BeanHandler<AppVo_1>(AppVo_1.class), params);
						if(null==vo1){
							//当天没有则查询最大的日期单价
							sql="SELECT price AS a1 FROM line_class_info WHERE lineBaseId = ? AND delFlag = '0' ORDER BY orderDate DESC LIMIT 0,1";
							params = new Object[1];
							params[0] = appVo_10.getA6();
							vo1=qr.query(conn, sql, new BeanHandler<AppVo_1>(AppVo_1.class), params);
						}
						if(null==vo1||StringUtils.isBlank(vo1.getA1())){
							vo15.setA13("0");//0.没优惠
						}
						else{
							BigDecimal bd1=new BigDecimal(appVo_10.getA9());
							BigDecimal bd2=new BigDecimal(vo1.getA1());
							if(bd2.compareTo(bd1)==-1){
								vo15.setA13("1");//1.有优惠
								vo15.setA11(vo1.getA1());
							}
							else{
								vo15.setA13("0");//0.没优惠
							}
						}
						result.add(vo15);
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}




	/**查询经纬附近的五条已购票线路**/
	private List<AppVo_15> getInfo(Coord coord,String cityName,String passengerid){
		List<AppVo_15> list = new ArrayList<AppVo_15>();
		
		boolean flag = false;
		String sql = null;
		if(StringUtils.isNotBlank(passengerid)){
			sql = " SELECT cp.telephone from company_passenger cp,passenger_info pi WHERE cp.telephone = pi.telephone and pi.passengerId = ? ";
			Object[] params = new Object[1];
			params[0] = passengerid;
			int count = queryCount(sql, params);
			if (count > 0) {
				/** 是华为员工 **/
				flag = true;
			}
		}
		
		ParamVo paramVo = new ParamVo();
		paramVo.setsLat(String.valueOf(coord.getLat()));
		paramVo.setsLon(String.valueOf(coord.getLng()));
		paramVo.setsName(null);
		paramVo.setCityName(cityName);
		paramVo.setFlag(flag);
		paramVo.setCurrentPage(0);
		paramVo.setPageSize(10);
		List<AppVo_14> l = lineService.getNearSplitLine(paramVo);
//		a1:线路ID  a2:线路起始点   a3:发车时间   a4:票价   a5:上车点  a6:下车点
//		a7:全程距离   a8:行程时间   a9:拆分线路ID   a10:是否优惠(1:有优惠 0：无优惠) a12:原价
		
		if(null!=l&&l.size()>0){
			AppVo_15 vo15=null;
//			list(a1：主线路id  a2：子线路id  a3：班次时间  a4：起点id  a5：起点名称  a6：终点id  a7：终点名称  a8：标识(1.自己购买过  2.附近的)  
//					a9：行程公里数  a10：预计行程时间  a11：票价  a12：始发站名  a13：是否优惠（0.没优惠 1.有优惠）a14:原价)
			
			for (AppVo_14 appVo_10 : l) {
				vo15=new AppVo_15();
				vo15.setA1(appVo_10.getA1());
				vo15.setA2(appVo_10.getA9());
				vo15.setA3(appVo_10.getA3());
				vo15.setA5(appVo_10.getA5());
				vo15.setA7(appVo_10.getA6());
				vo15.setA8("2");
				vo15.setA9(appVo_10.getA7());
				vo15.setA10(appVo_10.getA8());
				vo15.setA11(appVo_10.getA4());
				vo15.setA12(appVo_10.getA2());
				vo15.setA13(appVo_10.getA10());
				vo15.setA14(appVo_10.getA12());
				list.add(vo15);
			}
		}
		
		return list;
	}

	/**找出存在这些站点的线路**/
	public List<LineSplitInfo> getNearSplitLine(List<StationInfo> poiStar,List<AppVo_3> appv3s,ParamVo paramVo) {
		if(null==poiStar || poiStar.size()==0){
			return null;
		}
		String condition = "";
		List<String> con = new ArrayList<String>();
		String sql = null;
		//起点在前面,终点在后面  --用UNION 关联两张表
		sql = " SELECT sid,bstation,estation,linebaseid FROM line_split_info WHERE 1=1 AND iswork = 1 AND ";
		sql += " ( ";
		for(StationInfo stationInfo : poiStar){
			condition += " bstation=? OR";
			con.add(stationInfo.getId());
		}
		condition = condition.substring(0, condition.length()-2);
		sql += condition+" ) ";
		if(null!=appv3s && appv3s.size()>0){
			StringBuffer sb=new StringBuffer();
			for(AppVo_3 v3 : appv3s){
				sb.append("?,");
				con.add(v3.getA1());
			}
			sql += " AND ( bstation IN("+sb.substring(0,sb.length()-1)+")";
			for(AppVo_3 v3 : appv3s){
				con.add(v3.getA1());
			}
			sql += " OR estation IN("+sb.substring(0,sb.length()-1)+"))";
		}
		if(!paramVo.getFlag()){
			//不是华为员工
			sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
		}
		condition = "";
		sql += " UNION ";
		sql += " SELECT sid,bstation,estation,linebaseid FROM line_split_info WHERE 1=1 AND iswork = 1 AND ";
		sql += " ( ";
		for(StationInfo stationInfo : poiStar){
			condition += " estation=? OR";
			con.add(stationInfo.getId());
		}
		condition = condition.substring(0, condition.length()-2);
		sql += condition+" ) ";
		if(null!=appv3s && appv3s.size()>0){
			StringBuffer sb=new StringBuffer();
			for(AppVo_3 v3 : appv3s){
				sb.append("?,");
				con.add(v3.getA1());
			}
			sql += " AND ( estation IN("+sb.substring(0,sb.length()-1)+"))";
		}
		if(!paramVo.getFlag()){
			//不是华为员工
			sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
		}
		
		List<LineSplitInfo> sids = queryByPage(LineSplitInfo.class, sql,paramVo.getCurrentPage(), paramVo.getPageSize(),con.toArray());
		
		return sids;
	}
	
	/**根据线路ID获取线路班次信息**/
	public AppVo_15_list queryLineBaseAndClass(String lineBaseId) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select a.lineKm as a2,a.lineTime as a3,b.orderStartTime as a4 ");
		sqlBuffer.append(" from line_base_info as a left join line_class_info as b on a.lineBaseId = b.lineBaseId ");
		sqlBuffer.append(" where a.lineBaseId = ? group by a.lineBaseId ");
		Object[] params = new Object[1];
		params[0] = lineBaseId;
		AppVo_15_list vo = queryBean(AppVo_15_list.class, sqlBuffer.toString(), params);
		String stationIdArray = stationInfoDao.generateStationIdArrayByLineId(lineBaseId);
		if(vo!=null){
			vo.setA1(stationIdArray);
			return vo;
		}else{
			return new AppVo_15_list();
		}
	}
	
	/**商家介绍**/
	public AppVo_1 geBusinessRemark(String businessId) {
		//A8:商家介绍
		String m_sql = "SELECT remark AS a1 FROM mgr_business WHERE businessId = ?";
		Object[] params = new Object[1];
		params[0] = businessId;
		AppVo_1 vo  = queryBean(AppVo_1.class, m_sql,params);
		if(null==vo || StringUtils.isEmpty(vo.getA1())){
			vo = new AppVo_1();
			vo.setA1("");
		}
		return vo;
	}
	
	/**申请添加班车**/
	public int addApplyClass(String passengerId, String lineClassId) {
		String sql = " SELECT applyClassId FROM apply_add_class WHERE passengerId = ? AND lineClassId = ? ";
		Object[] params = new Object[2];
		params[0] = passengerId;
		params[1] = lineClassId;
		int count = queryCount(sql, params);
		int statu = 3;
		if(count<=0){
			sql = " INSERT INTO apply_add_class(applyClassId,passengerId,lineClassId) VALUES(?,?,?) ";
			params = new Object[3];
			params[0] = IdGenerator.seq();
			params[1] = passengerId;
			params[2] = lineClassId;
			statu = executeSQL(sql, params);
		}
		return statu;
	}
	
	/**根据线路ID获取站点信息**/
	public List<AppVo_5> getStationsByLineId(String lineId){
		String sql_station = "select id as a1,name as a2,baiduLng as a3,baiduLat as a4,type as a5 from pb_station where lineId = ?";
		Object[] params = new Object[1];
		params[0] = lineId;
		//a5:(0:拐点，1：上车点：2：下车点)
		List<AppVo_5> stations = queryList(AppVo_5.class, sql_station,params);
		for (AppVo_5 station : stations) {
			if(station.getA5().equals("2")){
				station.setA5("0");
			}else if(station.getA5().equals("1")){
				station.setA5("1");
			}else{
				station.setA5("2");
			}
		}
		return stations;
	}
	
	/**获取车辆当前站点信息**/
	public AppVo_6 getBusPosition(String lineId,String stationId){
		AppVo_6 result = new AppVo_6();
		List<StationInfo> stationInfoList = stationInfoDao.listByLineId(lineId);
		for (int index = 0; index < stationInfoList.size(); index++) {
			if(index!=stationInfoList.size()-1){
				StationInfo stationInfo = stationInfoList.get(index);
				if(stationInfo.getId().equals(stationId)){
					result.setA1(stationInfoList.get(index-1).getName());
					result.setA2(stationInfo.getName());
					result.setA3(stationInfoList.get(index+1).getName());
				}
			}
		}
		String sql_bus = "SELECT vehiclenumber as a1 FROM vehicle_info WHERE vehicleid IN ( SELECT vehicleId FROM line_class_info WHERE linebaseid = ? AND delFlag = '0' AND orderdate = '"+MyDate.Format.DATE.now()+"')";
		Object[] params = new Object[1];
		params[0] = lineId;
		AppVo_1 bus = queryBean(AppVo_1.class, sql_bus,params);
		if(bus!=null && bus.getA1()!=null){
			result.setA4(bus.getA1());
		}else{
			result.setA4("");
		}
		return result;
	}
	
	/**添加乘客线路搜索记录**/
	public int addPassengerSearchLine(PassengerSearchLine pa) {
		return updateData(pa,"passenger_search_line", "searchLineId");
	}
	
	/**先判断数据是否存在--存在了则不添加**/
	public int isexist(ParamVo paramVo) {
		String sql = " SELECT searchLineId FROM passenger_search_line WHERE 1=1 ";
		List<String> con = new ArrayList<String>();
		if(!StringUtils.isEmpty(paramVo.getsName())){
			sql += " AND startName = ? ";
			con.add(paramVo.getsName());
		}
		if(!StringUtils.isEmpty(paramVo.getsLon())){
			sql += " AND sloni = ? ";
			con.add(paramVo.getsLon());
		}
		if(!StringUtils.isEmpty(paramVo.getsLat())){
			sql += " AND slati = ? ";
			con.add(paramVo.getsLat());
		}
		if(!StringUtils.isEmpty(paramVo.geteName())){
			sql += " AND endName = ? ";
			con.add(paramVo.geteName());
		}
		if(!StringUtils.isEmpty(paramVo.geteLon())){
			sql += " AND eloni = ? ";
			con.add(paramVo.geteLon());
		}
		if(!StringUtils.isEmpty(paramVo.geteLat())){
			sql += " AND elati = ? ";
			con.add(paramVo.geteLat());
		}
		int count = queryCount(sql, con.toArray());
		return count;
	}
	
	/**用户发起线路**/
	public String addUserLine(String passengerid ,String stime,String xtime,String haddr,String caddr, Coord hco, Coord cco, String km_t,String cityName){
		
		//判断是否超过5条
		String sql_count = "select count(*) as a1 from line_user_application_new where passengerId = ? and applicationTime like '%"+MyDate.Format.DATE.now()+"%'";
		AppVo_1 count = queryBean(AppVo_1.class, sql_count, passengerid);
		if(Integer.valueOf(count.getA1())>=10){
			return "2";//当天超过5条
		}

		//判断线路是否重复申请
		String ishStation = "SELECT appliStationId as a1 FROM line_user_application_station_new WHERE loni = ? AND lati = ? ";
		AppVo_1 ishStation_vo = queryBean(AppVo_1.class, ishStation, hco.getLng(), hco.getLat());

		String iscStation = "SELECT appliStationId as a1 FROM line_user_application_station_new WHERE loni = ? AND lati = ? ";
		AppVo_1 iscStation_vo = queryBean(AppVo_1.class, iscStation, cco.getLng(), cco.getLat());

		if(ishStation_vo != null && iscStation_vo != null){
			String sql_isbook = "SELECT COUNT(*) as a1 FROM line_user_application_new WHERE startAddress = ? AND endAddress = ? AND passengerId = ? AND startTime = ?";
			AppVo_1 is_book = queryBean(AppVo_1.class, sql_isbook, ishStation_vo.getA1(), iscStation_vo.getA1(), passengerid, stime);
			if(Integer.valueOf(is_book.getA1())>0){
				return "3";//已经申请过
			}
		}

		String cityCode = "1607";//默认深圳
		Float speed = 25f;//默认25km/h
		Float km = Float.valueOf(km_t);
		if(cityName!=null && !"".equals(cityName)){
		String sql_city = "SELECT arearCode as a1 FROM sys_area WHERE areaName LIKE ? limit 1";
		Object[] params = new Object[1];
		params[0] = "%"+cityName.replace("市","")+"%";
		AppVo_1 city = queryBean(AppVo_1.class, sql_city,params);
			cityCode = city.getA1();
		}
		
		//添加公司站点
		LineUserApplicationStationNew compay = new LineUserApplicationStationNew();
		if(caddr!=null && !"".equals(caddr)){
			compay.setCreateOn(MyDate.Format.DATETIME.now());
			compay.setLati(String.valueOf(cco.getLat()));
			compay.setLoni(String.valueOf(cco.getLng()));
			compay.setStationName(caddr);
			updateData(compay,"line_user_application_station_new","appliStationId");
		}

		//添加家站点
		LineUserApplicationStationNew home = new LineUserApplicationStationNew();
		if(haddr!=null && !"".equals(haddr)){
			home.setCreateOn(MyDate.Format.DATETIME.now());
			home.setLati(String.valueOf(hco.getLat()));
			home.setLoni(String.valueOf(hco.getLng()));
			home.setStationName(haddr);
			updateData(home,"line_user_application_station_new","appliStationId");
		}
		
		//添加线路：家---公司
		if(stime!=null && !"".equals(stime)){
			LineUserApplicationNew vo = new LineUserApplicationNew();
			vo.setPassengerId(passengerid);
			vo.setLineType("0");
			vo.setStartAddress(home.getAppliStationId());
			vo.setEndAddress(compay.getAppliStationId());
			vo.setStartTime(stime);
			Float result = (km/speed)*60;
			vo.setCostTime(String.valueOf(String.valueOf((int) Math.rint(result))));
			vo.setLineKm(km_t);
			vo.setLinePrice(calcLinePrice(km_t));
			vo.setCityCode(cityCode);
			vo.setApplicationTime(MyDate.Format.DATETIME.now());
			updateData(vo,"line_user_application_new","applicationId");
			enrollLine(passengerid, vo.getApplicationId());
		}
		
		//添加线路：公司---家
		if(xtime!=null && !"".equals(xtime)){
			LineUserApplicationNew vo = new LineUserApplicationNew();
			vo.setPassengerId(passengerid);
			vo.setLineType("0");
			vo.setStartAddress(compay.getAppliStationId());
			vo.setEndAddress(home.getAppliStationId());
			vo.setStartTime(xtime);
			Float result = (km/speed)*60;
			vo.setCostTime(String.valueOf(String.valueOf((int) Math.rint(result))));
			vo.setLineKm(km_t);
			vo.setLinePrice(calcLinePrice(km_t));
			vo.setCityCode(cityCode);
			vo.setApplicationTime(MyDate.Format.DATETIME.now());
			updateData(vo,"line_user_application_new","applicationId");
			enrollLine(passengerid, vo.getApplicationId());
		}
		
		return "1";
	}
	
	/**
	 * 招募报名
	 * @return
	 */
	public String enrollLine(String passengerId,String lineId){
		LineEnrollUserNew vo = new LineEnrollUserNew();
		vo.setApptime(MyDate.Format.DATETIME.now());
		vo.setApplicationId(lineId);
		vo.setPassengerId(passengerId);
		int result = updateData(vo,"line_enroll_user_new","eid");
		if (result!=-1) {
			return "1";
		}else{
			return "0";
		}
	}
	
	/**
	 * 根据里程获取价格
	 */
	public static String calcLinePrice(String km_t){
		BigDecimal price = new BigDecimal(km_t)
			.multiply(BigDecimal.valueOf(0.3))
			.setScale(1, RoundingMode.HALF_UP);
		if (price.compareTo(TICKET_PRICE_MIN) < 0) {
			return TICKET_PRICE_MIN.toString();
		} else if (price.compareTo(TICKET_PRICE_MAX) > 0) {
			return TICKET_PRICE_MAX.toString();
		} else {
			return price.toString();
		}
	}

	/**招募线路详细**/
	public AppVo_15 enrollLineInfo(String passengerid,String lineId){
		String sql_line = "SELECT *,(SELECT a.stationName FROM line_user_application_station_new a WHERE a.appliStationId = startaddress) AS saddr,(SELECT a.stationName FROM line_user_application_station_new a WHERE a.appliStationId = endaddress) AS eaddr FROM line_user_application_new where applicationId = ? ";
		Object[] params = new Object[1];
		params[0] = lineId;
		LineUserApplicationNew line = queryBean(LineUserApplicationNew.class, sql_line,params);
		AppVo_15 vo = new AppVo_15();
		if(line!=null){
			vo.setA1("1");//线路正常
			vo.setA2(line.getStartTime());
			vo.setA3(line.getLinePrice());
			vo.setA4(line.getSaddr());
			vo.setA5(line.getEaddr());
			vo.setA6(line.getLineKm());
			vo.setA7(line.getCostTime());
			
			//报名人数
			String sql_count = "SELECT COUNT(*) as a1 FROM line_enroll_user_new WHERE applicationId = ?";
			params = new Object[1];
			params[0] = lineId;
			AppVo_1 count = queryBean(AppVo_1.class, sql_count,params);

			vo.setA8(count.getA1());
			
			if(passengerid!=null && !"".equals(passengerid)){
				//是否已报名
				String sql_isenroll = "SELECT COUNT(*) as a1 FROM line_enroll_user_new WHERE applicationId = ? and passengerId = ?";
				params = new Object[2];
				params[0] = lineId;
				params[1] = passengerid;
				AppVo_1 isenroll = queryBean(AppVo_1.class, sql_isenroll,params);
				if(isenroll!=null){
					if(!"0".equals(isenroll.getA1())){
						vo.setA9("1");//已报名
					}else{
						vo.setA9("0");
					}
				}else{
					vo.setA9("0");
				}
			}else{
				vo.setA9("0");
			}
			
			
			//上车点经纬度
			String sql_s = "SELECT * FROM line_user_application_station_new WHERE appliStationId = ?";
			params = new Object[1];
			params[0] = line.getStartAddress();
			LineUserApplicationStationNew start = queryBean(LineUserApplicationStationNew.class, sql_s,params);
			if(start!=null){
				vo.setA10(start.getLati());
				vo.setA11(start.getLoni());
			}
			
			//上车点经纬度
			String sql_e = "SELECT * FROM line_user_application_station_new WHERE appliStationId = ?";
			params = new Object[1];
			params[0] = line.getEndAddress();
			LineUserApplicationStationNew end = queryBean(LineUserApplicationStationNew.class, sql_e,params);
			if(start!=null){
				vo.setA12(end.getLati());
				vo.setA13(end.getLoni());
			}
		}else{
			vo.setA1("0");//线路有误
		}
		return vo;
	}
	
	/**我的招募线路**/
	public List<AppVo_15> enrollLineList(String passengerid,String type,String currentPage, String pageSize){
		List<AppVo_15> list = new ArrayList<AppVo_15>();
		List<LineUserApplicationNew> lines = new ArrayList<LineUserApplicationNew>();
		if("0".equals(type)){
			String sql = "SELECT b.*,(SELECT a.stationName FROM line_user_application_station_new a WHERE a.appliStationId = b.startaddress) AS saddr,(SELECT a.stationName FROM line_user_application_station_new a WHERE a.appliStationId = b.endaddress) AS eaddr,(SELECT COUNT(*) FROM line_enroll_user_new a WHERE a.applicationId = b.applicationId) AS temp FROM line_user_application_new b WHERE b.passengerid = ? and b.auditStatus = '1' ORDER BY b.applicationTime DESC";
			Object[] params = new Object[1];
			params[0] = passengerid;
			lines = queryByPage(LineUserApplicationNew.class, sql,Integer.valueOf(currentPage), Integer.valueOf(pageSize), params);
		}else{
			String sql = "SELECT b.*,(SELECT c.stationName FROM line_user_application_station_new c WHERE c.appliStationId = b.startaddress) AS saddr,(SELECT c.stationName FROM line_user_application_station_new c WHERE c.appliStationId = b.endaddress) AS eaddr,(SELECT COUNT(*) FROM line_enroll_user_new c WHERE c.applicationId = b.applicationId) AS  temp FROM line_enroll_user_new a LEFT JOIN line_user_application_new b ON a.applicationId = b.applicationId WHERE a.passengerId = ? AND b.passengerId <> ? ";
			Object[] params = new Object[2];
			params[0] = passengerid;
			params[1] = passengerid;
			lines = queryByPage(LineUserApplicationNew.class, sql,Integer.valueOf(currentPage), Integer.valueOf(pageSize), params);
		}
		if(lines!=null && lines.size()!=0){
			for (int i = 0; i < lines.size(); i++) {
				AppVo_15 vo = new AppVo_15();
				vo.setA1(lines.get(i).getStartTime());
				vo.setA2(lines.get(i).getLinePrice());
				vo.setA3(lines.get(i).getSaddr());
				vo.setA4(lines.get(i).getEaddr());
				vo.setA5(lines.get(i).getLineKm());
				vo.setA6(lines.get(i).getCostTime());
				vo.setA7(lines.get(i).getTemp());
				vo.setA8(lines.get(i).getApplicationId());
				list.add(vo);
			}
		}
		return list;
	}
	
	/**根据起点信息和条件--两表联合查询 **/
	public List<LineUnion> queryUnionSplieAndUserApp(
			List<StationUnion> list, int i, boolean flag) {
		if(null==list || list.size()==0){
			return null;
		}
		String sql = null;
		//看是否有line_split_info 或者 line_user_application_new  
		Map<String,String> maps = new HashMap<String, String>();
		for(StationUnion stationUnion : list){
			if("1".equals(stationUnion.getType())){
				maps.put("1", "1");
			}else if("2".equals(stationUnion.getType())){
				maps.put("2", "2");
			}
		}
		List<LineUnion> sids = new ArrayList<LineUnion>();
		//类型  1:发布线路   2:招募线路
		if(!StringUtils.isEmpty(maps.get("1"))){
			String condition = "";
			List<String> con = new ArrayList<String>();
			sql = " SELECT sid AS id,bstation,estation,linebaseid,'1' AS type FROM line_split_info WHERE 1=1 AND iswork = 1  ";
			if(i==1){
				//起点信息存在
				sql += " AND ( ";
				for(StationUnion stationInfo : list){
					condition += " bstation=? OR";
					con.add(stationInfo.getId());
				}
				condition = condition.substring(0, condition.length()-2);
				sql += condition+" ) ";
				
			}else if(i==2){
				//终点信息存在
				sql += " AND ( ";
				for(StationUnion stationInfo : list){
					condition += " estation=? OR";
					con.add(stationInfo.getId());
				}
				condition = condition.substring(0, condition.length()-2);
				sql += condition+" ) ";
			}
			if(!flag){
				//不是华为员工
				sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
			}
			sql += " ORDER BY linebaseid ";
			List<LineUnion> sidFaBu = queryList(LineUnion.class, sql,con.toArray());
			if(null!=sidFaBu && sidFaBu.size()>0){
				sids.addAll(sidFaBu);
			}
		}
		//招募线路
		if(!StringUtils.isEmpty(maps.get("2"))){
			String condition = "";
			List<String> con = new ArrayList<String>();
			sql = " SELECT a.applicationId AS id,startAddress AS bstation,endAddress AS estation,startTime,'2' AS type,costTime,lineKm,linePrice,( SELECT count(1) FROM line_enroll_user_new WHERE applicationId = a.applicationId ) AS applyNum FROM line_user_application_new as a WHERE 1=1  ";
			if(i==1){
				//起点信息存在
				if(null!=list && list.size()>0){
					sql += " AND ( ";
					for(StationUnion stationInfo : list){
						condition += " startAddress=? OR";
						con.add(stationInfo.getId());
					}
					condition = condition.substring(0, condition.length()-2);
					sql += condition+" ) ";
				}
				
			}else if(i==2){
				//终点信息存在
				if(null!=list && list.size()>0){
					sql += " AND ( ";
					for(StationUnion stationInfo : list){
						condition += " endAddress=? OR";
						con.add(stationInfo.getId());
					}
					condition = condition.substring(0, condition.length()-2);
					sql += condition+" ) ";
				}
			}
			List<LineUnion> zhaomu = queryList(LineUnion.class, sql,con.toArray());
			if(null!=zhaomu && zhaomu.size()>0){
				sids.addAll(zhaomu);
			}
		}
		
		return sids;
	}
	
	/**根据起点和终点查询线路--两表联合查询 **/
	public List<LineUnion> queryUnionSplieAndUserApp(List<StationUnion> sList,
			List<StationUnion> eList, boolean flag) {
		
		//看起点是否有line_split_info 或者 line_user_application_new  
		Map<String,String> smaps = new HashMap<String, String>();
		for(StationUnion stationUnion : sList){
			if("1".equals(stationUnion.getType())){
				smaps.put("1", "1");
			}else if("2".equals(stationUnion.getType())){
				smaps.put("2", "2");
			}
		}
		
		//看终点是否有line_split_info 或者 line_user_application_new  
		Map<String,String> emaps = new HashMap<String, String>();
		for(StationUnion stationUnion : eList){
			if("1".equals(stationUnion.getType())){
				emaps.put("1", "1");
			}else if("2".equals(stationUnion.getType())){
				emaps.put("2", "2");
			}
		}
		
		String sql = null;
		
		List<LineUnion> sids = new ArrayList<LineUnion>();
		List<LineUnion> fabu = new ArrayList<LineUnion>();//发布线路的集合
		List<LineUnion> zhaomu = new ArrayList<LineUnion>();//招募线路的集合
		
		//先查询发布线路
		sql = " SELECT sid AS id,bstation,estation,linebaseid,'1' AS type FROM line_split_info WHERE 1=1 AND iswork = 1 ";
		if(!StringUtils.isEmpty(smaps.get("1")) && StringUtils.isEmpty(emaps.get("1"))){
			//起点有信息,终点无信息
			
			String condition = "";
			List<String> con = new ArrayList<String>();
			
			for(int i = 0;i<sList.size();i++){
				condition += " bstation = ? OR";
				con.add(sList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			if(!flag){
				//不是华为员工
				sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
			}
			fabu = queryList(LineUnion.class, sql,con.toArray());
			
		}else if(StringUtils.isEmpty(smaps.get("1")) && !StringUtils.isEmpty(emaps.get("1"))){
			//起点无信息,终点有信息
			
			String condition = "";
			List<String> con = new ArrayList<String>();
			
			for(int i=0;i<eList.size();i++){
				condition += " estation = ? OR";
				con.add(eList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			if(!flag){
				//不是华为员工
				sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
			}
			fabu = queryList(LineUnion.class, sql,con.toArray());
			
		}else if(!StringUtils.isEmpty(smaps.get("1")) && !StringUtils.isEmpty(emaps.get("1"))){
			//起点终点都有信息
			String condition = "";
			List<String> con = new ArrayList<String>();
			
			for(int i = 0;i<sList.size();i++){
				condition += " bstation = ? OR";
				con.add(sList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			condition = "";
			for(int i=0;i<eList.size();i++){
				condition += " estation = ? OR";
				con.add(eList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			if(!flag){
				//不是华为员工
				sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
			}
			fabu = queryList(LineUnion.class, sql,con.toArray());
		}
		
		
		
		//再查询招募线路
		sql = " SELECT a.applicationId AS id,startAddress AS bstation,endAddress AS estation,startTime,'2' AS type,costTime,lineKm,linePrice,( SELECT count(1) FROM line_enroll_user_new WHERE applicationId = a.applicationId ) AS applyNum FROM line_user_application_new as a WHERE 1=1  ";
		if(!StringUtils.isEmpty(smaps.get("2")) && StringUtils.isEmpty(emaps.get("2"))){
			//起点有信息,终点无信息
			
			String condition = "";
			List<String> con = new ArrayList<String>();
			
			for(int i = 0;i<sList.size();i++){
				condition += " startAddress = ? OR";
				con.add(sList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			zhaomu = queryList(LineUnion.class, sql,con.toArray());
			
		}else if(StringUtils.isEmpty(smaps.get("2")) && !StringUtils.isEmpty(emaps.get("2"))){
			//起点无信息,终点有信息
			
			String condition = "";
			List<String> con = new ArrayList<String>();
			
			for(int i=0;i<eList.size();i++){
				condition += " endAddress = ? OR";
				con.add(eList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			zhaomu = queryList(LineUnion.class, sql,con.toArray());
			
		}else if(!StringUtils.isEmpty(smaps.get("2")) && !StringUtils.isEmpty(emaps.get("2"))){
			//起点终点都有信息
			String condition = "";
			List<String> con = new ArrayList<String>();
			
			for(int i = 0;i<sList.size();i++){
				condition += " startAddress = ? OR";
				con.add(sList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			condition = "";
			for(int i=0;i<eList.size();i++){
				condition += " endAddress = ? OR";
				con.add(eList.get(i).getId());
			}
			if(!StringUtils.isEmpty(condition)){
				condition = condition.substring(0, condition.length()-2);
				sql += " AND ( ";
				sql += condition+" ) ";
			}
			zhaomu = queryList(LineUnion.class, sql,con.toArray());
		}
		
		if(null!=fabu && fabu.size()>0){
			sids.addAll(fabu);
		}
		if(null!=zhaomu && zhaomu.size()>0){
			sids.addAll(zhaomu);
		}
		
		return sids;
	}
	
	/**查看用户是否有登录,如果有的话,查看是否对招募线路进行了报名**/
	public List<LineUnion> queryUserApplyLine(String passengerId) {
		List<LineUnion> list = new ArrayList<LineUnion>();
		if(!StringUtils.isEmpty(passengerId)){
			String sql = " SELECT applicationId AS id FROM line_enroll_user_new WHERE passengerId = ? ";
			Object[] params = new Object[1];
			params[0] = passengerId;
			list = queryList(LineUnion.class, sql, params);
		}
		return list;
	}
	
	/**
	 * 根据起点信息条件--查询
	 * @param stat 站点
	 * @param i  1：(起点有信息) 2:(终点有信息)
	 * @param flag true:(华为员工)  false:(不是华为员工)
	 * @param j 1:(发布线路)  2：(招募线路)
	 * @param currentPage 当前页
	 * @param pageSize 每页显示的记录数
	 * @return
	 */
	public List<LineUnion> queryUnionSplieAndUserAppByPage(List<StationUnion> stat, int i, boolean flag, int j,int currentPage,int pageSize) {
		if(null==stat || stat.size()==0){
			return null;
		}
		String sql = null;
		List<LineUnion> list = null;
		if(1==j){
			//发布线路
			sql = " SELECT sid AS id,bstation,estation,linebaseid,'1' AS type FROM line_split_info WHERE 1=1 AND iswork = 1 ";
			if(1==i){
				//起点有信息,终点无信息
				list = querySpiltListByPage(sql,stat,flag,currentPage,pageSize,"bstation",1);
			}else if(2==i){
				//起点无信息,终点有信息
				list = querySpiltListByPage(sql,stat,flag,currentPage,pageSize,"estation",1);
			}
		}else if(2==j){
			//招募线路
			sql = " SELECT a.applicationId AS id,startAddress AS bstation,endAddress AS estation, '0' as linebaseid,startTime,'2' AS type,costTime,lineKm,linePrice,( SELECT count(1) FROM line_enroll_user_new WHERE applicationId = a.applicationId ) AS applyNum FROM line_user_application_new as a WHERE 1=1  ";
			if(1==i){
				//起点有信息,终点无信息
				list = querySpiltListByPage(sql,stat,flag,currentPage,pageSize,"startAddress",2);
			}else if(2==i){
				//起点无信息,终点有信息
				list = querySpiltListByPage(sql,stat,flag,currentPage,pageSize,"endAddress",2);
			}
		}
		
		return list;
	}
	
	/**组装线路查询sql**/
	private List<LineUnion> querySpiltListByPage(String sql,List<StationUnion> stat, boolean flag,int currentPage,int pageSize,String field,int type){
		List<LineUnion> list = null; 
		String condition = "";
		String bstation = "";
		List<String> con = new ArrayList<String>();
		StationUnion station = null;
		for(int k = 0;k<stat.size();k++){
			station = stat.get(k);
			if(null!=station && !StringUtils.isEmpty(station.getId())){
				condition += " "+field+" = ? OR";
				con.add(station.getId());
			}
		}
		for(int k=0;k<stat.size();k++){
			station = stat.get(k);
			if(null!=station && !StringUtils.isEmpty(station.getId())){
				bstation +="?,";
				con.add(station.getId());
			}
		}
		if(!StringUtils.isEmpty(condition)){
			condition = condition.substring(0, condition.length()-2);
			sql += " AND ( ";
			sql += condition+" ) ";
		}
		
		//不是华为员工
		if (1==type && !flag){
			sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
		}
		if(!StringUtils.isEmpty(bstation)){
			bstation = bstation.substring(0, bstation.length()-1);
			sql += " ORDER BY FIELD ("+field+",";
			sql += bstation+")";
		}
		list = queryByPage(LineUnion.class, sql, currentPage, pageSize, con.toArray());
		return list;
	}

	
	/**
	 * 根据站点点信息条件--查询(起点终点都有)
	 * @param poiStar 起点
	 * @param poiEnd  终点
	 * @param flag true:(华为员工)  false:(不是华为员工)
	 * @param j 1:(发布线路)  2：(招募线路)
	 * @param currentPage 当前页
	 * @param pageSize 每页显示的记录数
	 * @return
	 */
	public List<LineUnion> queryUnionSplieAndUserAppByPage(List<StationUnion> poiStar, List<StationUnion> poiEnd,
			boolean flag, int j, Integer currentPage, Integer pageSize) {
		String sql = null;
		List<LineUnion> list = null;
		if(1==j){
			//发布线路
			sql = " SELECT sid AS id,bstation,estation,linebaseid,'1' AS type FROM line_split_info WHERE 1=1 AND iswork = 1 ";
			list = querySESplieAndUserAppLine(poiStar,poiEnd,flag,j,currentPage,pageSize,sql,"bstation","estation");
		}else if(2==j){
			//招募线路
			sql = " SELECT a.applicationId AS id,startAddress AS bstation,endAddress AS estation, '0' as linebaseid,startTime,'2' AS type,costTime,lineKm,linePrice,( SELECT count(1) FROM line_enroll_user_new WHERE applicationId = a.applicationId ) AS applyNum FROM line_user_application_new as a WHERE 1=1  ";
			list = querySESplieAndUserAppLine(poiStar,poiEnd,flag,j,currentPage,pageSize,sql,"startAddress","endAddress");
		}
		return list;
	}
	
	private List<LineUnion> querySESplieAndUserAppLine(List<StationUnion> poiStar, List<StationUnion> poiEnd,
			boolean flag, int j, Integer currentPage, Integer pageSize,String sql,String starStation,String endStation){
		
		List<LineUnion> list = null;
		
		//起点终点都有信息
		String statCondition = "";
		String endCon = "";
		String bstation = "";
		List<String> con = new ArrayList<String>();
		StationUnion station = null;
		for(int i = 0;i<poiStar.size();i++){
			statCondition += " "+starStation+" = ? OR";
			con.add(poiStar.get(i).getId());
		}
		if(!StringUtils.isEmpty(statCondition)){
			statCondition = statCondition.substring(0, statCondition.length()-2);
			sql += " AND ( ";
			sql += statCondition+" ) ";
		}
		for(int i=0;i<poiEnd.size();i++){
			endCon += " "+endStation+" = ? OR";
			con.add(poiEnd.get(i).getId());
		}
		if(!StringUtils.isEmpty(endCon)){
			endCon = endCon.substring(0, endCon.length()-2);
			sql += " AND ( ";
			sql += endCon+" ) ";
		}
		//不是华为员工
		if(1==j && !flag){
			sql += " AND linebaseid NOT IN ( SELECT lineBaseId FROM company_line ) ";
		}
		
		for(int k=0;k<poiStar.size();k++){
			station = poiStar.get(k);
			if(null!=station && !StringUtils.isEmpty(station.getId())){
				bstation +="?,";
				con.add(station.getId());
			}
		}
		if(!StringUtils.isEmpty(bstation)){
			bstation = bstation.substring(0, bstation.length()-1);
			sql += " ORDER BY FIELD ("+starStation+",";
			sql += bstation+")";
		}
		list = queryByPage(LineUnion.class, sql, currentPage, pageSize, con.toArray());
		return list;
	}

}
