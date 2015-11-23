package com.pig84.ab.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.IBookDao;
import com.pig84.ab.dao.ICouponDao;
import com.pig84.ab.dao.IDriverDao;
import com.pig84.ab.dao.IRecommendAwardDao;
import com.pig84.ab.utils.Conn;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.ApplyReturn;
import com.pig84.ab.vo.ChangeTicketVo;
import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.LineBaseInfo;
import com.pig84.ab.vo.LineClassEntity;
import com.pig84.ab.vo.LineClassInfo;
import com.pig84.ab.vo.LineSplitInfo;
import com.pig84.ab.vo.PassengerComments;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.SeatInfoTemp;
import com.pig84.ab.vo.StatOutEntity;
import com.pig84.ab.vo.StatTotalEntity;
import com.pig84.ab.vo.StationInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_10;
import com.pig84.ab.vo.bean.AppVo_10_list;
import com.pig84.ab.vo.bean.AppVo_15_list;
import com.pig84.ab.vo.bean.AppVo_18;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_25;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_7;

/**
 * 预定相关
 * @author zhangqiang
 *
 */
@Repository
@SuppressWarnings("all")
public class BookDaoImpl extends BaseDao implements IBookDao {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverDao driverDao;
	
	@Autowired
	private StationInfoDao stationInfoDao;
	
	@Autowired
	private IRecommendAwardDao recommendAwardDao;
	
	@Autowired
	private ICouponDao couponDao;
	
	/**
	 * 订单详细（按次订单）
	 */
	public AppVo_15_list orderInfoByDays(String orderNo) {
		
		AppVo_15_list vo = new AppVo_15_list();//返回结果
	     Object[] params=null;
		//获取订单信息
//		SELECT a.*,(SELECT COUNT(*) FROM passenger_comments b WHERE a.leaseOrderId = b.leaseOrderId) AS comment FROM lease_base_info a WHERE  a.leaseOrderNo = '140826859312'
		String sql_order = "SELECT a.*,(SELECT COUNT(*) FROM passenger_comments b WHERE a.leaseOrderId = b.leaseOrderId) AS comment FROM lease_base_info a WHERE  a.leaseOrderNo = ?";
		params = new Object[1];
		params[0] = orderNo;
		LeaseBaseInfo orderinfo = queryBean(LeaseBaseInfo.class, sql_order,params);
		
		if(orderinfo==null){
			vo.setA1("0");//订单不存在
			return vo;
		}
		if(!"0".equals(orderinfo.getBuyType())){
			vo.setA1("1");//非按次订单
			return vo;
		}
		if("0".equals(orderinfo.getPayStatus())){
			vo.setA1("2");//待支付
		}
		if("1".equals(orderinfo.getPayStatus())){
			vo.setA1("3");//交易成功
		}
		if("2".equals(orderinfo.getPayStatus())){
			vo.setA1("4");//已失效
		}
		if("3".equals(orderinfo.getPayStatus())){
			vo.setA1("5");//已取消
		}
		if("1".equals(orderinfo.getPayStatus())){ //只有支付成功才有这两种状态
			if(Integer.valueOf(orderinfo.getComment())>0){
				vo.setA1("6");//已评价
			}else{
				vo.setA1("7");//待评价
			}
		}
		
		vo.setA2(orderinfo.getLeaseOrderId());//订单ID
		vo.setA3(orderinfo.getLeaseOrderNo());//订单号
		vo.setA4(orderinfo.getLeaseOrderTime());//下单时间
		vo.setA5(orderinfo.getRealprice());
		vo.setA6(orderinfo.getRidesInfo());		//乘车次数	
		
		String sql_line = "SELECT * FROM line_base_info where lineBaseId = ? ";
		params = new Object[1];
		params[0] = orderinfo.getLineBaseId();
		LineBaseInfo line = queryBean(LineBaseInfo.class, sql_line,params);
		vo.setA7(line.getLineName());			//线路名称
		vo.setA8(MyDate.Format.DATETIME.now());		//当前系统时间
		
		String sql_setting = "SELECT MIN(orderValiteTime)as a1 FROM sys_app_setting";
		AppVo_1 set = queryBean(AppVo_1.class, sql_setting);
		vo.setA9(set.getA1());					//订单有效时长
		vo.setA10(line.getLineBaseId());//线路ID
		
		//订单对应班次明细
//		SELECT DISTINCT a.orderStarttime as a1 FROM line_class_info a WHERE a.lineBaseId =(SELECT b.linebaseid FROM lease_base_info b WHERE b.leaseOrderNo = '8888') AND a.lineClassId IN(SELECT c.lineClassID FROM line_business_order c WHERE c.leaseOrderId = '8888') ORDER BY a.orderStarttime ASC
		String sql_time = "SELECT DISTINCT a.orderStarttime AS a1 FROM line_class_info a LEFT JOIN line_business_order b ON a.lineClassId = b.lineclassid WHERE b.leaseOrderId = ? ORDER BY a.orderStarttime ASC";
		params = new Object[1];
		params[0] = orderNo;
		
		//订单对应的班次发车时间
		List<AppVo_1> time_list = queryList(AppVo_1.class, sql_time,params);
		if(time_list!=null){
			
			//返回订单班次信息结果集
			List<AppVo_2> classinfo = new ArrayList<AppVo_2>();
			
			for (int i = 0; i < time_list.size(); i++) {
//				SELECT * FROM line_class_info WHERE orderStartTime = '09:00' AND linebaseid = '14082514192432002' AND lineclassid IN(SELECT lineClassid FROM line_business_order WHERE leaseOrderid = '8888')
				AppVo_2 temp = new AppVo_2();
				String time = time_list.get(i).getA1();
				temp.setA1(time);
				
				//时间串
				String dateStr = "";
				
//				String sql = "SELECT * FROM line_class_info WHERE orderStartTime = '"+time+"' AND linebaseid = '"+orderinfo.getLineBaseId()+"' AND lineclassid IN(SELECT lineClassid FROM line_business_order WHERE leaseOrderid = ? ) order by orderDate asc";
				String sql = "SELECT a.*,b.status as temp FROM line_class_info a LEFT JOIN line_business_order b ON a.lineclassid = b.lineclassid WHERE a.orderStartTime = ? AND a.linebaseid = ? AND b.leaseOrderid = ? ORDER BY a.orderdate ASC";
				
				params = new Object[3];
				params[0] = time;
				params[1] = orderinfo.getLineBaseId();
				params[2] = orderNo;
				List<LineClassInfo>  lci = queryList(LineClassInfo.class, sql, params);
				if(lci!=null && lci.size()!=0){
					for (int j = 0; j < lci.size(); j++) {
						String temp_time = lci.get(j).getOrderDate();
						String ctime = temp_time.substring(5, temp_time.length()).replace("-", ".");
						dateStr+=ctime+"#"+MyDate.getweekofdayByShot(lci.get(j).getOrderDate())+"#"+lci.get(j).getTemp()+",";
					}
				}
				
				if(!"".equals(dateStr)){
					dateStr = dateStr.substring(0, dateStr.length()-1);
				}
				temp.setA2(dateStr);
				
				classinfo.add(temp);
			}
			vo.setList(classinfo);
		}
		
		String sql_maxDate = "SELECT MAX(a.orderdate) as a1 FROM line_class_info a LEFT JOIN line_business_order b ON a.lineClassId = b.lineclassid WHERE b.leaseOrderId = ? ";
		params = new Object[1];
		params[0] = orderNo;
		AppVo_1 maxDate = queryBean(AppVo_1.class, sql_maxDate,params);
		if(maxDate!=null){
			vo.setA11(maxDate.getA1());//订单有效期
		}
		vo.setA12(orderinfo.getSlineId());//子线路ID
		return vo;
	}
	
	/**
	 * 订单评价V2
	 */
	public String orderCommentsV2(String orderNo,PassengerComments pc) {
		String sql_order = "select * from lease_base_info where leaseOrderNo = ? ";
		Object[] args = new Object[1];
		args[0] = orderNo;
		LeaseBaseInfo order = queryBean(LeaseBaseInfo.class, sql_order,args);
		// 订单不存在
		if(order ==null){
			return "1";
		}
		String querySql = "select * from passenger_comments where lineBaseId=? and leaseOrderId=? and passengerId=? and lineClassId = ?";
		args = new Object[4];
		args[0] = order.getLineBaseId();
		args[1] = order.getLeaseOrderNo();
		args[2] = pc.getPassengerId();
		args[3] = pc.getLineClassId();
		int rows = super.queryCount(querySql, args);
		if(rows>0){
			return "6";
		}
		// 线路ID
		pc.setLineBaseId(order.getLineBaseId());
		pc.setSlineId(order.getSlineId());
		// 订单ID（实际存的是订单号）
		pc.setLeaseOrderId(order.getLeaseOrderNo());
		int flag = updateData(pc,"passenger_comments","commentId");
		if(flag==1){
			//成功
			return "2";
		}else{
			//失败
			return "5";
		}
	}

	/**
	 * 取消订单
	 */
	public String cancelOrder(String orderNo) {
	    Object[] params=null;
		String sql_ispay = "select * from lease_base_info where leaseOrderNo = ?";
		params = new Object[1];
		params[0] = orderNo;
		LeaseBaseInfo vo = queryBean(LeaseBaseInfo.class, sql_ispay,params);

		if(vo!=null && !"1".equals(vo.getPayStatus())){
			String sql = "update lease_base_info set payStatus = '4' where leaseOrderNo = ?";
			params = new Object[1];
			params[0] = orderNo;
			int flag = executeSQL(sql,params);
			
			String sql_dis = "update passenger_discount_info set disstatus = '0', orderno = '' where orderno = ? ";
			params = new Object[1];
			params[0] = orderNo;
			executeSQL(sql_dis,params);
			
			//如果使用的不是老优惠券，则用的是新优惠券，修改新优惠券状态
			couponDao.updateCouponState(null,null,"unused",orderNo,null,null);
			return String.valueOf(flag);
		}else{
			return "-1";
		}
	}

	/**
	 * 电子票详细（按次购买）
	 */
	public AppVo_10_list ticketInfoByDays(String orderNo) {
	    Object[] params=null;
		String sql = "SELECT a.leaseOrderNo AS a1, a.realprice AS a2 ,b.linesubtype AS a3,c.brefName AS a4 ,a.lineBaseId AS a5,a.leaseOrderId AS a6 FROM lease_base_info a,line_base_info b,mgr_business c  WHERE a.leaseorderno = ? AND b.lineBaseId = a.lineBaseId AND c.businessId = a.businessId";
		params = new Object[1];
		params[0] = orderNo;
		AppVo_10 vo = queryBean(AppVo_10.class, sql,params);
		if(vo!=null){
			
			//订单对应班次明细
//			SELECT DISTINCT a.orderStarttime as a1 FROM line_class_info a WHERE a.lineBaseId =(SELECT b.linebaseid FROM lease_base_info b WHERE b.leaseOrderNo = '8888') AND a.lineClassId IN(SELECT c.lineClassID FROM line_business_order c WHERE c.leaseOrderId = '8888') ORDER BY a.orderStarttime ASC
			String sql_time = "SELECT DISTINCT a.orderStarttime AS a1 FROM line_class_info a LEFT JOIN line_business_order b ON a.lineClassId = b.lineclassid WHERE b.leaseOrderId = ? ORDER BY a.orderStarttime ASC";
			params = new Object[1];
			params[0] = orderNo;
			
			//订单对应的班次发车时间
			List<AppVo_2> time_list = queryList(AppVo_2.class, sql_time,params);
			//返回订单班次信息结果集
			List<AppVo_3> classinfo = new ArrayList<AppVo_3>();
			
			if(time_list!=null){
				
				                                         
				for (int i = 0; i < time_list.size(); i++) {
					AppVo_3 temp = new AppVo_3();
					String time = time_list.get(i).getA1();
					temp.setA1(time);
					
					//时间串
					String dateStr = "";
					String sql_D = "SELECT a.*,b.status as temp FROM line_class_info a LEFT JOIN line_business_order b ON a.lineclassid = b.lineclassid WHERE a.orderStartTime = ? AND a.linebaseid = ? AND b.leaseOrderid = ? ORDER BY a.orderdate ASC";
					params = new Object[3];
					params[0] = time;
					params[1] = vo.getA5();
					params[2] = orderNo;
					List<LineClassInfo>  lci = queryList(LineClassInfo.class, sql_D,params);
					if(lci!=null && lci.size()!=0){
						for (int j = 0; j < lci.size(); j++) {
							String temp_time = lci.get(j).getOrderDate();
							String ctime = temp_time.substring(5, temp_time.length()).replace("-", ".");
							dateStr+=ctime+"#"+MyDate.getweekofdayByShot(lci.get(j).getOrderDate())+"#"+lci.get(j).getTemp()+",";
						}
					}
					
					if(!"".equals(dateStr)){
						dateStr = dateStr.substring(0, dateStr.length()-1);
					}
					temp.setA2(dateStr);
					
					classinfo.add(temp);
				}
			}
			
			AppVo_10_list result = new AppVo_10_list();
			result.setA1(vo.getA1());//订单号
			result.setA2(vo.getA2());//价格
			result.setA3(vo.getA3());//线路类型  （0:上下班 1:旅游）
			result.setA4(vo.getA4());//商家名称
			String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(vo.getA5());
			result.setA5(stationNameArray.substring(0,stationNameArray.indexOf("-")));//起点站
			result.setA6(stationNameArray.substring(stationNameArray.lastIndexOf("-")+1, stationNameArray.length()));//终点站
			result.setA8(stationNameArray);//途径站点
			result.setA10(vo.getA5());//线路ID
			result.setList(classinfo);//班次明细
			
//			SELECT b.*,c.vehicleNumber FROM line_business_order a , line_class_info b,vehicle_info c WHERE a.lineClassId = b.lineClassId AND c.vehicleId = b.vehicleId AND a.leaseOrderid = '141013364508' AND b.orderdate = '2014-10-13' ORDER BY b.orderStartTime ASC
			String sql_BNO = "SELECT c.vehicleNumber as a1 FROM line_business_order a , line_class_info b,vehicle_info c WHERE a.lineClassId = b.lineClassId AND c.vehicleId = b.vehicleId AND a.leaseOrderid = ? AND b.orderdate = ? ORDER BY b.orderStartTime ASC";
			params = new Object[2];
			params[0] = orderNo;
			params[1] = MyDate.Format.DATE.now();
			List<AppVo_1> b_list = queryList(AppVo_1.class, sql_BNO,params);

			String no = "";
			if(b_list!=null && b_list.size()!=0){
				for (int i = 0; i < b_list.size(); i++){
					no+=b_list.get(i).getA1()+"#";
				}
			}
			
			if("".equals(no)){
				result.setA9("");
			}else{
				result.setA9(no.substring(0,no.length()-1));
			}
			
			return result;
		}
		return null;
	}

	/**
	 * 获取用户余额
	 */
	public String getUserBalance(String userId) {
		return "0.00";
	}

	
	/**
	 * 获取订单价格
	 */
	public String getOrderPrice(String orderNo) {
	    Object[] params=null;
		String sql = "select * from lease_base_info where leaseOrderNo = ? ";
		params = new Object[1];
		params[0] = orderNo;
		String money = "0.00";
		
		LeaseBaseInfo vo = queryBean(LeaseBaseInfo.class, sql,params);
		if(vo==null){
			return "0.00";
		}
		money = vo.getRealprice();
		
		return money;
	}
	
	/**获取报价信息**/
	public AppVo_4 getBcBiddingById(String bcBiddingId){
	    Object[] params=null;
//		String sql = "SELECT bc_line_id AS a1,businessId AS a2,totalCost AS a3 FROM bc_business_bidding WHERE id = ?";
		String sql = "SELECT a.bc_line_id as a1,businessid as a2,totalcost as a3,b.passengerId as a4 FROM bc_business_bidding a LEFT JOIN bc_line b ON a.bc_line_id = b.bc_line_id WHERE a.id = ?";
		params = new Object[1];
		params[0] = bcBiddingId;
		return queryBean(AppVo_4.class, sql,params);
	}

	/**
	 * 获取用户未支付订单数
	 */
	public String getUnPayOrderCount(String userId) {
	    Object[] params=null;
		String sql = "select * from lease_base_info where passengerId = ? and payStatus = '0'";
		params = new Object[1];
		params[0] = userId;
		int count = queryCount(sql,params);
		return String.valueOf(count);
	}

	/**
	 * 获取用户未读消息数
	 */
	public String getUnReadMsgCount(String userId) {
	    Object[] params=null;
		String sql = "SELECT a.* FROM sys_msg_user a LEFT JOIN sys_msg_info b ON a.msgId = b.msgId WHERE a.userId = ? AND a.readFlag = '0' AND b.msgType = '1' and b.issend = '0' AND LEFT(a.sendtime,10)>= ?";
		params = new Object[2];
		params[0] = userId;
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-30);//30天以前
		Date starttime=calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newDate = sdf.format(starttime);
		params[1] = newDate;
		int count = queryCount(sql,params);
		return String.valueOf(count);
	}
	
	/**根据订单号获取订单对应的发车时间与用户手机号码**/
	public AppVo_4 getOrderTimeAndUserPhone(String orderNo) {
	    Object[] params=null;
		String times = "";
		String phone = "";
		String line = "";
		//订单信息
		String sql_order = "SELECT *,(select name from pb_station where id=ustation) AS ustationName,(select name from pb_station where id=dstation) AS dstationName FROM lease_base_info WHERE leaseorderno = ?";
		params = new Object[1];
		params[0] = orderNo;
		LeaseBaseInfo order = queryBean(LeaseBaseInfo.class, sql_order,params);
		String sql = "SELECT DISTINCT a.orderStarttime AS a1 FROM line_class_info a WHERE a.lineBaseId = ? AND a.lineClassId IN(SELECT c.lineClassID FROM line_business_order c WHERE c.leaseOrderId = ? ) ORDER BY a.orderStarttime ASC";
		params = new Object[2];
		params[0] = order.getLineBaseId();
		params[1] = orderNo;
		List<AppVo_1> list = queryList(AppVo_1.class, sql,params);
		if(list!=null && list.size()!=0){
			for (int i = 0; i < list.size(); i++) {
				times+=list.get(i).getA1()+"、";
			}
		}
		if (!"".equals(times)) {
			times = times.substring(0,times.length()-1);
		}
		String sql_ = "SELECT telephone as a1 FROM passenger_info WHERE passengerId = ? ";
		params = new Object[1];
		params[0] = order.getPassengerId();
		AppVo_1 vo = queryBean(AppVo_1.class, sql_,params);
		if(vo!=null){
			phone = vo.getA1();
		}
		String sql__ = "SELECT lineBaseId AS a1 FROM line_base_info WHERE lineBaseId = ? ";
		params = new Object[1];
		params[0] = order.getLineBaseId();
		AppVo_1 station = queryBean(AppVo_1.class, sql__,params);
		AppVo_4 result = new AppVo_4();
		if(station!=null){
			String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(station.getA1());
			if(!StringUtils.isEmpty(stationNameArray)){
				String[] stations = stationNameArray.split("-");
				result.setA4(stations[0]);
			}
			
		}
		result.setA1(phone);
		result.setA2(times);
		result.setA3(order.getUstationName()+"-"+order.getDstationName());
		return result;
	}
	
	
	/**根据线路ID获取班次线路详细（NEW）**/
	public AppVo_15_list LineAndClassInfo(String lineBaseId){
	   Object[] params=null;
		params = new Object[1];
		params[0] = lineBaseId;
		String sql = "SELECT * FROM line_base_info WHERE lineType = '1' AND lineStatus = '3' and lineBaseId = ?";
		LineBaseInfo line = queryBean(LineBaseInfo.class,sql,params);
		
		if(line != null){
			String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(lineBaseId);
			line.setStationNames(stationNameArray);
			AppVo_15_list vo  = new AppVo_15_list();
			vo.setA1(line.getStationNames().substring(0, line.getStationNames().indexOf("-",1)));//起点站
			vo.setA2(line.getStationNames().substring(line.getStationNames().lastIndexOf("-")+1,line.getStationNames().length()));//终点站
			vo.setA3(line.getRemark()==null?"":line.getRemark());//乘客须知
			vo.setA4(line.getStationNames());//站台名称
			vo.setA5(line.getLineTime());//预计行程时间
			vo.setA6(line.getOrderPrice());//价格
			
			// A7:站点图片
			vo.setA7("");
			
			//A8:商家介绍
			String m_sql = "SELECT remark AS a1 FROM mgr_business WHERE businessId = (SELECT businessId FROM line_base_info WHERE lineBaseId = ?)";
			params = new Object[1];
			params[0] = lineBaseId;
			AppVo_1 remark  = queryBean(AppVo_1.class, m_sql,params);
			if(remark!=null){
				vo.setA8(remark.getA1()==null?"":remark.getA1());
			}else{
				vo.setA8("");
			}
			if(line.getLinePicUrl()!=null && !"".equals(line.getLinePicUrl())){
				String url = PropertyManage.get("http.root.url")+"/";  //ftp地址
				vo.setA9(url+line.getLinePicUrl());//线路图片
			}else{
				vo.setA9("");
			}			
			//班次时间
			String c_sql = "SELECT orderStartTime as a1 FROM line_class_info WHERE lineBaseId = ? AND delflag = '0' AND orderdate >= CURDATE() GROUP BY orderStartTime ORDER BY orderStartTime ASC";
			params = new Object[1];
			params[0] = lineBaseId;
			List<AppVo_1> clazz = queryList(AppVo_1.class, c_sql,params);
			
			/*****************************************************/
			if(null==clazz||clazz.size()==0){//如果不存在当前时间及之后的可用班次日期，则去掉大于等于当前时间条件，查询一个班次时间用于app端页面显示
				c_sql="SELECT orderStartTime AS a1 FROM line_class_info WHERE lineBaseId = ? AND delflag = '0' GROUP BY orderStartTime ORDER BY orderStartTime ASC";
				clazz = queryList(AppVo_1.class, c_sql,params);
			}
			vo.setList(clazz);
			
			String sql_today = "SELECT vehicleNumber as a1 FROM vehicle_info WHERE vehicleId = (SELECT vehicleid FROM line_class_info WHERE linebaseid = ? AND delFlag = '0' AND orderdate = ? LIMIT 1)";
			params = new Object[2];
			params[0] = lineBaseId;
			params[1] = MyDate.Format.DATE.now();
			AppVo_1 vo_toDay = queryBean(AppVo_1.class, sql_today, params);
			if(vo_toDay!=null){
				vo.setA11(vo_toDay.getA1());
			}
			return vo;
		}
		
		return null;
	}
	
	/**评论列表**/
	public List<AppVo_6> commentList(int currentPage,int pageSize,String lineBaseId){
		  Object[] params=null;
		String sql = "SELECT b.displayid AS a1,b.nickName AS a2,a.commentTime AS a3,a.commentContext AS a4,b.headerPicUrl as a5 FROM passenger_comments a LEFT JOIN passenger_info b " +
				"ON a.passengerId = b.passengerId WHERE commentStatus = '0' AND a.lineBaseId = ? ORDER BY a.commentTime DESC";
		params = new Object[1];
		params[0] = lineBaseId;
		List<AppVo_6> vo = queryByPage(AppVo_6.class, sql,currentPage, pageSize, params);
		
		if(null!=vo&&vo.size()>0){
			String headerIp = PropertyManage.get("header.pic.ip");//IP地址	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
			String directory = PropertyManage.get("header.pic.directory");//文件	
			for (int i = 0; i < vo.size(); i++) {
				if(vo.get(i).getA5()!=null && !"".equals(vo.get(i).getA5())){
					vo.get(i).setA5(headerIp+"/"+directory+vo.get(i).getA5());
				}
			}
			
			//查询评论总数
			sql = "SELECT a.commentId FROM passenger_comments a WHERE a.commentStatus = '0' AND a.lineBaseId = ?";
			params = new Object[1];
			params[0] = lineBaseId;
			int commentCount=queryCount(sql, params);
			AppVo_6 vo6=vo.get(0);
			vo6.setA6(commentCount+"");//在第一个对象的a6属性中保存评论总数
			vo.set(0, vo6);
		}
		return vo;
	}

	/***查看记录是否存在**/
	public int isExitsInStatTotal(String outTradeNo) {
		  Object[] params=null;
		String sql = " SELECT leaseOrderNo FROM stat_total WHERE leaseOrderNo = ?  ";
		params = new Object[1];
		params[0] = outTradeNo;
		int count = queryCount(sql, params);
		return count;
	}

	/**添加收入统计**/
	public int addStatTotal(String leaseOrderNo) {
		Connection conn = null;
		int statu = 0;
		  Object[] params=null;
		try {
			
			conn = Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			String sql = " select count(1) from stat_total where leaseOrderNo = ? ";
			params = new Object[1];
			params[0] = leaseOrderNo;
			Long count = (Long)qr.query(conn,sql, params, new ScalarHandler(1));
			if(count<=0L){
				sql = " SELECT leab.leaseOrderNo,leab.leaseOrderTime AS operatTime,leab.businessId,leab.passengerId,leab.buyType,lineBase.discountRate,lineBase.orderPrice AS linePrice,lineOrder.price AS consumeLimit,lineBase.lineBaseId,lineBase.lineSubType,lineOrder.lineClassId,lineClass.orderDate,lineClass.orderStartTime,lineClass.driverId,lineClass.vehicleId ";
				sql +=" FROM lease_base_info AS leab , line_base_info AS lineBase ,line_business_order AS lineOrder ,line_class_info AS lineClass " +
						" WHERE 1=1 and leab.lineBaseId = lineBase.lineBaseId AND lineOrder.leaseOrderId = leab.leaseOrderNo AND lineClass.lineClassId = lineOrder.lineClassId  AND leab.ispay = 1 and leab.leaseOrderNo = ? ";
				params = new Object[1];
				params[0] = leaseOrderNo;
				List<StatTotalEntity> statTotalEntities = qr.query(conn,sql,new BeanListHandler<StatTotalEntity>(StatTotalEntity.class),params);
				if(null!=statTotalEntities && statTotalEntities.size()>0){
					for(StatTotalEntity statTotalEntity : statTotalEntities){
						sql ="INSERT INTO stat_total(statTotalId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,consumeLimit,discountRate,buyType,vehicleId," +
								"leaseOrderNo,lineClassId,businessId,linePrice,operatTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
						statu = qr.update(conn, sql, new Object[]{IdGenerator.seq(),statTotalEntity.getOrderDate(),statTotalEntity.getOrderStartTime(),statTotalEntity.getLineSubType(),
								statTotalEntity.getLineBaseId(),statTotalEntity.getPassengerId(),statTotalEntity.getDriverId(),statTotalEntity.getConsumeLimit(),statTotalEntity.getDiscountRate(),statTotalEntity.getBuyType(),statTotalEntity.getVehicleId(),
								statTotalEntity.getLeaseOrderNo(),statTotalEntity.getLineClassId(),statTotalEntity.getBusinessId(),statTotalEntity.getLinePrice(),statTotalEntity.getOperatTime()});
						if(statu<=0){
							throw new Exception("添加收入统计数据失败");
						}
					}
				}
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return statu;
	}
	
	/**
	 * 可用优惠券列表
	 */
	public List<AppVo_10> getMyGif(String orderPrice, String userid,int currentPage,int pageSize,String type){
		return couponDao.getUsableCoupon(userid, null, orderPrice, pageSize,currentPage);
	}
	
	/**根据订单号检查订单是否有误**/
	public boolean checkOrder(String orderNo){
		  Object[] params=null;
		boolean flag = false;
		Connection conn = null;
		try {
			conn = Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			String sql = "select ridesInfo as a1 from lease_base_info where leaseOrderNo = ?";
			params = new Object[1];
			params[0] = orderNo;
//			AppVo_1 vo = queryBean(AppVo_1.class, sql,args);
			AppVo_1 vo = qr.query(conn, sql, new BeanHandler<AppVo_1>(AppVo_1.class), params);
			
			String sql_count = "select count(*) as a1 from line_business_order where leaseOrderId = ?";
			params = new Object[1];
			params[0] = orderNo;
//			AppVo_1 vo_count = queryBean(AppVo_1.class, sql_count,args);
			AppVo_1 vo_count = qr.query(conn, sql, new BeanHandler<AppVo_1>(AppVo_1.class), params);
			
			if(vo!=null && vo_count!= null){
				if(vo.getA1().equals(vo_count.getA1())){
					flag = true;
				}else{
					
					flag = false;
					
					String sql_del_pay = "delete from lease_pay where leaseOrderNo = ?";
					params = new Object[1];
					params[0] = orderNo;
//					executeSQL(sql_del_pay,args);
					qr.update(conn, sql_del_pay, params);
					
					
					String sql_del_lease = "delete from stat_passenger_consum_lease where leaseOrderNo = ?";
					params = new Object[1];
					params[0] = orderNo;
//					executeSQL(sql_del_lease,args);
					qr.update(conn, sql_del_lease, params);
					
					String sql_del_recharge = "delete from stat_passenger_recharge where rerunNo = ?";
					params = new Object[1];
					params[0] = orderNo;
//					executeSQL(sql_del_recharge,args);
					qr.update(conn, sql_del_recharge, params);
					
					String sql_del_total = "delete from stat_total where leaseOrderNo = ?";
					params = new Object[1];
					params[0] = orderNo;
//					executeSQL(sql_del_total,args);
					qr.update(conn, sql_del_total, params);
					
					String sql_del_info = "delete from lease_base_info where leaseOrderNo = ?";
					params = new Object[1];
					params[0] = orderNo;
//					executeSQL(sql_del_info,args);
					qr.update(conn, sql_del_info, params);
					
					String sql_del_class = "delete from line_business_order where leaseOrderId = ?";
					params = new Object[1];
					params[0] = orderNo;
//					executeSQL(sql_del_class,args);
					qr.update(conn, sql_del_class, params);
					
					//如果使用的不是老优惠券，则用的是新优惠券，修改新优惠券状态
					couponDao.updateCouponState(conn,qr,"unused",orderNo,null,null);
					
					flag = true;
				}
				
			}else{
				flag = false;
			}
			
			conn.commit();
		}catch(Exception e){
			try {
				conn.rollback();
				logger.error("Check order failed.", e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	/**可改签列表**/
	public List<AppVo_6> changeList(String orderNo,String userid){
		  Object[] params=null;
		List<AppVo_6> result = new ArrayList<AppVo_6>();
		String sql = "SELECT a.localid AS a1, a.lineclassid AS a2,a.passengerid AS a3,a.leaseorderid AS a4,a.oldclassid AS a5,a.status AS a6,a.price AS a7,(a.price+a.giftMoney) AS a8 ,b.orderDate AS a9,b.orderStartTime AS a10 FROM line_business_order a LEFT JOIN line_class_info b ON a.lineClassId = b.lineClassId WHERE leaseorderid = ? ORDER BY a.lineClassId ASC";
		params = new Object[1];
		params[0] = orderNo;
		List<AppVo_10> list = queryList(AppVo_10.class, sql, params);
		
		if(list!=null && list.size()!=0){
			for (int i = 0; i < list.size(); i++) {
				if(!userid.equals(list.get(i).getA3())){//非法改签
					result = null;
					return result;
				}else{
					AppVo_6 vo = new AppVo_6();
					vo.setA1(list.get(i).getA1());
					vo.setA2(list.get(i).getA2());
					vo.setA3(list.get(i).getA8());
					vo.setA4(list.get(i).getA9().substring(5,list.get(i).getA9().length()).replace("-","."));
					vo.setA5(MyDate.getweekofday(list.get(i).getA9()).replace("星期","周"));
					if("1".equals(list.get(i).getA6())){
						vo.setA6("1");//改签中，不可改签
					}else if("2".equals(list.get(i).getA6())){
						vo.setA6("2");//已改签过，不可改签
					}else if(Float.valueOf(list.get(i).getA8())<1){
						vo.setA6("3");//票价低于一块钱，不可改签
					}else if(MyDate.compare_date_time(list.get(i).getA9()+" "+list.get(i).getA10()+":00", MyDate.Format.DATETIME.now())==-1){
						vo.setA6("5");//车票已过期，不可改签
					}else if(!MyDate.isAfterNMinute(list.get(i).getA9()+" "+list.get(i).getA10()+":00", 30)){
						vo.setA6("4");//离发车时间小于半小时，不可改签
					}else{
						vo.setA6("0");//正常情况
					}
					result.add(vo);
				}
			}
			return result;
		}else{
			return null;
		}
	}
	
	/**改签日期列表**/
	public List<AppVo_6> changeDateList(String orderNo,int currentPage, int pageSize){
		  Object[] params=null;
		String sql_line = "select linebaseid as a1 from line_base_info where lineType = '1' and lineStatus = '3' and linebaseid = (SELECT linebaseid FROM lease_base_info WHERE leaseorderno = ? )";
		params = new Object[1];
		params[0] = orderNo;
		AppVo_1 line = queryBean(AppVo_1.class, sql_line,params);
		
		if(line!=null){
			String sql = "SELECT t.* FROM line_class_info t WHERE t.linebaseid = (SELECT linebaseid FROM lease_base_info WHERE leaseorderno = ? )  AND t.lineclassid NOT IN(SELECT lineClassId FROM line_business_order WHERE leaseorderid = ?  AND STATUS NOT IN ('3','4')) AND t.delflag = '0' AND t.orderdate > ? AND f_getFreeSeatCount(t.orderstarttime,t.orderDate,t.lineBaseId) > 0 ORDER BY t.orderDate ASC";
			params = new Object[3];
			params[0] = orderNo;
			params[1] = orderNo;
			params[2] = MyDate.Format.DATE.now();
			List<LineClassInfo> list = queryByPage(LineClassInfo.class, sql, currentPage,pageSize, params);
			if(list!=null && list.size()!=0){
				List<AppVo_6> result = new ArrayList<AppVo_6>();
				
				for (int i = 0; i < list.size(); i++) {
					AppVo_6 vo = new AppVo_6();
					vo.setA1(list.get(i).getLineClassId());
					vo.setA2(list.get(i).getOrderDate().substring(5,list.get(i).getOrderDate().length()).replace("-","."));
					vo.setA3(MyDate.getweekofday(list.get(i).getOrderDate()).replace("星期","周"));
					result.add(vo);
				}
				
				return result;
			}else{
				return null;
			}
		}else{
			return null;
		}
		
	}
	
	/**获取线路相关信息**/
	public AppVo_6 getLineInfo(String orderNo){
		  Object[] params=null;
		String sql = "SELECT lineBaseId AS a1,(SELECT orderStartTime FROM line_class_info WHERE lineClassId = (SELECT lineClassId FROM line_business_order WHERE leaseorderid = ? LIMIT 1)) AS a2 FROM line_base_info WHERE linebaseid = (SELECT linebaseid FROM lease_base_info WHERE leaseorderno = ?)";
		params = new Object[2];
		params[0] = orderNo;
		params[1] = orderNo;
		AppVo_6 vo = queryBean(AppVo_6.class, sql,params);
		if(vo!=null){
			String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(vo.getA1());
			vo.setA1(stationNameArray);
		} 
		return vo;
	}
	
	/**新版改签(APP前端直接改签,不需要通过平台审批)**/
	public String changeTicket(String orderNo, String cids) {
		Connection conn = null;
		String statu = "-1";
		int value = 0;
		try {
			Object[] params=null;
			conn = Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			String cid[] = cids.split(",");
			Date date = null;//当前时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			int diffTime = 0;//时间差
			ChangeTicketVo ticketVo = null;//改签Vo
			ChangeTicketVo ticketVo_new = null;//改签Vo
			String localId  = null;
			String newLineClassId = null;
			List<StatTotalEntity> statTotalList = null;//收入统计集合
			StatTotalEntity statTotal = null;
			LineClassEntity lineClassEntity = null;
			String msgId = null;
			String msgIds = "";
			
			//存放以站点ID为key,站点名字为value的maps
			Map<String, String> maps = new HashMap<String, String>();
			AppVo_2 v2 = null;
			//存在子线路
			if(!StringUtils.isEmpty(orderNo)){
			
				String sql = " SELECT bstation AS a1,estation AS a2 FROM  line_split_info WHERE sid = ( SELECT slineId FROM lease_base_info WHERE leaseOrderNo = ? ) ";
				params = new Object[1];
				params[0] = orderNo;
				v2 = qr.query(conn, sql, new BeanHandler<AppVo_2>(AppVo_2.class),params);
				sql = " SELECT id AS a1,name AS a2 FROM pb_station WHERE id IN (?,?)  ";
				params = new Object[2];
				params[0] = v2.getA1();
				params[1] = v2.getA2();
				List<AppVo_2> v2list = qr.query(conn, sql, new BeanListHandler<AppVo_2>(AppVo_2.class),params);
				if(null!=v2list && v2list.size()>0){
					for(AppVo_2 v :v2list){
						maps.put(v.getA1(), v.getA2());
					}
				}
			}
			
			for(int i =0;i<cid.length;i++){
				//根据localId查找申请改签的班次的信息
				String sql = " SELECT linb.lineClassId,linb.passengerId,linb.oldclassid,linc.orderDate,linc.orderStartTime,linc.lineBaseId,linb.businessId,linc.driverId,linc.vehicleId,linb.status,linb.classprice as price,linb.couponGiftId ";
				sql += " FROM line_business_order AS linb LEFT JOIN line_class_info AS linc ON linb.lineClassId = linc.lineClassId ";
				sql += " WHERE linb.localId = ? AND linc.delFlag = 0 ";
				localId = cid[i].substring(0,cid[i].indexOf("@"));
				newLineClassId = cid[i].substring(cid[i].indexOf("@")+1,cid[i].length());
				ticketVo = qr.query(conn,sql,new BeanHandler<ChangeTicketVo>(ChangeTicketVo.class), localId);
				ticketVo.setOldclassid(newLineClassId);
				//必需在发车的前10分钟发起申请改签,少于10分钟不允许改签
				date = new Date();
				diffTime = MyDate.diffMinutes(ticketVo.getOrderDate()+" "+ticketVo.getOrderStartTime(), sdf.format(date));
				if(diffTime<10){
					statu =  "2";
					throw new Exception("离发车少于10分钟,不允许改签");
				}
				
				//一张票只能改签一次
				if(2==ticketVo.getStatus()){
					statu = "3";
					throw new Exception("一张票只能改签一次");
				}
				
				//低于1元的票价不能改签
				//compareTo 的结果:-1表示小于,0是等于,1是大于
				if(ticketVo.getPrice().compareTo(new BigDecimal("1.00"))==-1){
					statu = "4";
					throw new Exception("小于1元无法改签");
				}
				
				//活动的特惠票无法改签
				
				//判断新的线路班次是否还在运营
				sql = "  SELECT linc.orderStartTime,linc.orderDate,linc.lineBaseId,linc.vehicleId,linb.lineName,linc.price ";
				sql += " FROM line_class_info AS linc LEFT JOIN line_base_info AS linb ON linc.lineBaseId = linb.lineBaseId ";
				sql += " WHERE linc.lineClassId = ? AND linb.lineType = 1 AND linb.lineStatus = 3 AND linc.delFlag = 0 ";
				
				ticketVo_new = qr.query(conn,sql,new BeanHandler<ChangeTicketVo>(ChangeTicketVo.class), newLineClassId);
				if(null==ticketVo_new || "".equals(ticketVo_new.getLineBaseId())){
					statu = "5";
					throw new Exception("新的线路班次现在没有运营");
				}
				
				//只能改签到同一条线路
				if(!ticketVo.getLineBaseId().equals(ticketVo_new.getLineBaseId())){
					statu = "6";
					throw new Exception("只能改签到同一条线路");
				}
				
				//判断申请改签的那天价格和期望乘坐班次的价格是否一样
				if(ticketVo.getPrice().compareTo(ticketVo_new.getPrice())!=0){
					statu = "7";
					throw new Exception("价格不一致");
				}
				
				//判断期望乘坐的那天班次是否还有余坐
				sql = " SELECT f_getFreeSeatCount(?,?,?) AS a1 from DUAL ";
				AppVo_1 vo_1 = qr.query(conn,sql, new BeanHandler<AppVo_1>(AppVo_1.class), ticketVo_new.getOrderStartTime(),ticketVo_new.getOrderDate(),
						ticketVo_new.getLineBaseId());
				if(null==vo_1 || Integer.valueOf(vo_1.getA1())<=0){
					statu = "noSeat";
					throw new Exception("没有余座");
				}
				
				 //修改line_business_order 将新旧班次ID对换
				sql ="update line_business_order set lineClassId=?,oldclassid=?,status=2 where localId=?";
				value = qr.update(conn, sql, new Object[]{ticketVo.getOldclassid(),ticketVo.getLineClassId(),localId});
				if(value<0){
					statu = "updateError";
					throw new Exception("更新line_business_order异常");
				}
				
				//查询乘客信息
				sql = "select p.passengerId,p.displayId,p.nickName,p.telephone from passenger_info p  where p.passengerId=?";
				PassengerInfo passengerInfo = qr.query(conn, sql, new BeanHandler<PassengerInfo>(PassengerInfo.class), ticketVo.getPassengerId());
				
				//插入改签记录
				sql ="INSERT INTO passenger_change_order(id,orgLineBaseId,orgLineName,passengerId,displayId,nickName,telephone,orgLeaseOrderId,orgClassId,"
					+"orgOrderStartTime,orgOrderDate,newLineBaseId,newLineName,newLeaseOrderId,newClassId,newOrderStartTime,newOrderDate,createBy,createOn,type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
				value = qr.update(conn, sql, new Object[]{IdGenerator.seq(),ticketVo_new.getLineBaseId(),ticketVo_new.getLineName(),passengerInfo.getPassengerId(),
						passengerInfo.getDisplayId(),passengerInfo.getNickName(),passengerInfo.getTelephone(),orderNo,
						ticketVo.getLineClassId(),ticketVo.getOrderStartTime(),ticketVo.getOrderDate(),ticketVo.getLineBaseId(),ticketVo_new.getLineName(),
						orderNo,ticketVo.getOldclassid(),ticketVo_new.getOrderStartTime(),ticketVo_new.getOrderDate(),passengerInfo.getPassengerId(),MyDate.Format.DATETIME.now(),1});
				if(value<1){
					statu = "insertError";
					throw new Exception("插入改签记录失败");
				}
				
				
				//添加支出记录
				//查询原始收入统计
			    sql="select a.consumeLimit,a.discountRate,a.leaseOrderNo,a.businessId,a.linePrice from stat_total a where a.passengerId=? and a.lineBaseId=? and a.orderDate=? and a.orderStartTime=? and a.lineClassId=? and a.leaseOrderNo=?";
			    statTotalList = qr.query(conn, sql, new BeanListHandler<StatTotalEntity>(StatTotalEntity.class), passengerInfo.getPassengerId(),ticketVo.getLineBaseId(),
			    		ticketVo.getOrderDate(),ticketVo.getOrderStartTime(),ticketVo.getLineClassId(),orderNo);
			    if(false==CollectionUtils.isEmpty(statTotalList)){
			    	//存在记录,说明实际支付金额大于0元
			    	statTotal = statTotalList.get(0);
			    	if(null!=statTotal){
			    		//添加支出记录
						StatOutEntity outEntity = new StatOutEntity();
						outEntity.setStatOutId(IdGenerator.seq());
						outEntity.setLeaseOrderNo(orderNo);
						outEntity.setOperatTime(MyDate.Format.DATETIME.now());
						outEntity.setOperater("");
						//取收入统计的消费金额
						outEntity.setOutMoney(statTotal.getConsumeLimit());
						//支出类型 1:退票   2:改签
						outEntity.setOutType(2);
						outEntity.setOrderDate(ticketVo.getOrderDate());
						outEntity.setOrderStartTime(ticketVo.getOrderStartTime());
						outEntity.setPassengerId(passengerInfo.getPassengerId());
						outEntity.setLineBaseId(ticketVo.getLineBaseId());
						outEntity.setLineSubType(0);
						outEntity.setDriverId(ticketVo.getDriverId());
						outEntity.setVehicleId(ticketVo.getVehicleId());
						outEntity.setLineClassId(ticketVo.getLineClassId());
						outEntity.setBusinessId(statTotal.getBusinessId());
						sql = "insert into stat_out(statOutId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,outMoney,businessId,vehicleId,operatTime,operater,leaseOrderNo,lineClassId,outType) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					    value = qr.update(conn, sql,new Object[]{outEntity.getStatOutId(),outEntity.getOrderDate(),outEntity.getOrderStartTime(),outEntity.getLineSubType(),outEntity.getLineBaseId(),outEntity.getPassengerId(),outEntity.getDriverId(),outEntity.getOutMoney(),outEntity.getBusinessId(),outEntity.getVehicleId(),outEntity.getOperatTime(),outEntity.getOperater(),outEntity.getLeaseOrderNo(),outEntity.getLineClassId(),outEntity.getOutType()});
					    if(value<1){
							statu = "insertError";//添加支出统计失败
							throw new Exception("加支出统计失败");
						}
					    
					    //7 添加收入统计 --记录新的商家
					    sql = " SELECT lineclass.driverId,lineclass.vehicleId,lineorder.businessId AS lineClassId "
					    		+" FROM line_class_info AS  lineclass LEFT JOIN line_business_order AS lineorder ON lineclass.lineClassId = lineorder.lineClassId "
					    		+" WHERE lineclass.lineClassId = ? AND lineclass.delFlag = 0 AND lineclass.orderDate = ? AND lineclass.orderStartTime = ? ";
						lineClassEntity = qr.query(conn,sql,new BeanHandler<LineClassEntity>(LineClassEntity.class), ticketVo.getOldclassid(),ticketVo_new.getOrderDate(),ticketVo_new.getOrderStartTime());
					    
					    sql="INSERT INTO stat_total(statTotalId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,consumeLimit,discountRate,buyType,vehicleId,leaseOrderNo,lineClassId,businessId,linePrice,operatTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
						value = qr.update(conn, sql, new Object[]{IdGenerator.seq(),ticketVo_new.getOrderDate(),ticketVo_new.getOrderStartTime(),0,
								ticketVo.getLineBaseId(),passengerInfo.getPassengerId(),lineClassEntity.getDriverId(),statTotal.getConsumeLimit(),statTotal.getDiscountRate(),2,lineClassEntity.getVehicleId(),statTotal.getLeaseOrderNo(),ticketVo.getOldclassid(),
								lineClassEntity.getLineClassId(),statTotal.getLinePrice(),MyDate.Format.DATETIME.now()});  //lineClassEntity.getLineClassId() 是lineorder.businessId AS lineClassId
						if(value<1){
							statu = "insertError";//添加收入统计失败
							throw new Exception("添加收入统计失败");
						}
						
						
						
//						String msgContext="尊敬的用户，"+ticketVo_new.getLineName()+"，"+ticketVo.getOrderDate()+" "+ticketVo.getOrderStartTime()+"发车的订单，现已成功改签至"+ticketVo_new.getLineName()+" "+ticketVo_new.getOrderDate()+" "+ticketVo_new.getOrderStartTime()+"。请悉知。"+SystemConstants.COMPANY_INFO;
						String msgContext="亲，您于"+ticketVo.getOrderDate()+"乘坐的车票( "+ticketVo.getOrderStartTime();
						if(null!=maps && null!=v2){
							msgContext +="，"+maps.get(v2.getA1())+"-"+maps.get(v2.getA2());
						}
						msgContext += ")已改签成功了，改签后的乘坐日期为"+ticketVo_new.getOrderDate()+"。";
						//发送站内消息
						msgId = IdGenerator.seq();
						sql ="insert into sys_msg_info(msgId,msgType,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,issend) values(?,?,?,?,?,?,?,?,?)";
//						String msgTitle ="改签成功("+ticketVo_new.getLineName()+")";
						String msgTitle ="改签成功";
						qr.update(conn, sql, new Object[]{msgId,1,0,msgTitle,msgContext,0,"SYSTEM",MyDate.Format.DATETIME.now(),"1"});
						sql="insert into sys_msg_user(localId,userId,msgId,readFlag,sendPhoneNo,sendTime) values(?,?,?,?,?,?)";
						qr.update(conn, sql, new Object[]{IdGenerator.seq(),passengerInfo.getPassengerId(),msgId,0,passengerInfo.getTelephone(),MyDate.Format.DATETIME.now()});
						msgIds +=msgId+";";
			    	}
			    }else{
			    	//发送站内消息
					msgId = IdGenerator.seq();
					sql ="insert into sys_msg_info(msgId,msgType,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,issend) values(?,?,?,?,?,?,?,?,?)";
//					String msgTitle ="改签成功("+ticketVo_new.getLineName()+")";
					String msgTitle ="改签成功";
					String msgContext="亲，您于"+ticketVo.getOrderDate()+"乘坐的车票( "+ticketVo.getOrderStartTime();
					if(null!=maps && null!=v2){
						msgContext +="，"+maps.get(v2.getA1())+"-"+maps.get(v2.getA2());
					}
					msgContext += ")已改签成功了，改签后的乘坐日期为"+ticketVo_new.getOrderDate()+"。";
					qr.update(conn, sql, new Object[]{msgId,1,0,msgTitle,msgContext,0,"SYSTEM",MyDate.Format.DATETIME.now(),"1"});
					sql="insert into sys_msg_user(localId,userId,msgId,readFlag,sendPhoneNo,sendTime) values(?,?,?,?,?,?)";
					qr.update(conn, sql, new Object[]{IdGenerator.seq(),passengerInfo.getPassengerId(),msgId,0,passengerInfo.getTelephone(),MyDate.Format.DATETIME.now()});
			    }
				
			}
			statu = "1@"+msgId;
			conn.commit();
		} catch (Exception e) {
			if(null!=conn){
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			}
		}
		return statu;
	}

	/**计算退票的相关数据**/
	public Integer queryReturnPercent() {
		String sql = " SELECT returnTicketFree AS a1 FROM sys_app_setting ";
		AppVo_1 vo = queryBean(AppVo_1.class, sql);
		Integer returnTicketFree =10;
		if(null!=vo){
			returnTicketFree =  Integer.parseInt(vo.getA1());
		}
		return returnTicketFree;
	}

	/**退票**/
	public String returnTicket(String localIdStr, String lineClassIdStr, String orderNo,AppVo_5 vo_4,String payType) {
		String returnValue = "0";
		Integer statu = 0;
		Connection conn = null;
		if(!StringUtils.isEmpty(localIdStr) && !StringUtils.isEmpty(lineClassIdStr) && !StringUtils.isEmpty(orderNo)){
			//线路_乘客班次表 的localIds
			String[] localIds = localIdStr.split(",");
			//班次ID
			String[] lineClassIds = lineClassIdStr.split(",");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				conn = Conn.get();
				conn.setAutoCommit(false);
				QueryRunner qr = new QueryRunner();
				Object[] params=null;
				//找出该订单号对应的订单ID以及有效的天数(班次)
				//SELECT leaseOrderId as a1,( SELECT count(1) FROM line_class_info WHERE lineClassId IN ( SELECT lineClassId FROM line_business_order WHERE leaseOrderId = '15081421500235097' AND status <> '4' ) AND orderDate >= '2014-10-30' ) AS a2,realprice AS a3,ridesInfo AS a4,slineId AS a5,thirdparty AS a6  FROM lease_base_info WHERE leaseOrderNo = '15081421500235097'
				String sql = " SELECT leaseOrderId as a1,( SELECT count(1) FROM line_class_info WHERE lineClassId IN ( SELECT lineClassId FROM line_business_order WHERE leaseOrderId = ? AND status <> '4' ) AND orderDate >= ? ) AS a2,realprice AS a3,ridesInfo AS a4,slineId AS a5,thirdparty AS a6  FROM lease_base_info WHERE leaseOrderNo = ? ";
				params = new Object[3];
				params[0] = orderNo;
				params[1] = sdf.format(new Date());
				params[2] = orderNo;
				AppVo_6 vo = qr.query(conn,sql,new BeanHandler<AppVo_6>(AppVo_6.class), params);
				if(null==vo){
					//数据不存在
					return returnValue;
				}
				//有效的天数(班次)
				Integer dates = Integer.parseInt(vo.getA2());
				//退票的天数(班次)
				Integer reDates = localIds.length;
				
				
				BigDecimal oldMoney = new BigDecimal(vo.getA3());//原订单实际支付价格
				BigDecimal oldBackMon = new BigDecimal(vo_4.getA1());//原退票总金额(扣手续费以前)
				BigDecimal payMoney = new BigDecimal("0.00");//lease_pay对应的支付金额
				BigDecimal left = new BigDecimal("0");//扣除余额以后剩下的金额
				BigDecimal changMoney = null;//改变以后的金额
				BigDecimal nowRealPay = oldMoney.subtract(oldBackMon);//退完票后剩下的金额

				//------------------退票不再修改订单金额-----------------------------------------------------
				
					//删除对应的line_business_order(更新状态)
				for(int i = 0;i<localIds.length;i++){
					if(!StringUtils.isEmpty(localIds[i])){
//							sql = " DELETE FROM line_business_order WHERE localId = ? ";
						sql = " UPDATE line_business_order SET status = 4 WHERE localId = ? ";
						params = new Object[1];
						params[0] = localIds[i];
						statu = qr.update(conn, sql,params);
						if(1!=statu){
							throw new Exception("更新line_business_order失败");
						}
					}
				}
				
				//修改lease_base_info的ridesInfo购买信息(乘车次数),费用总计和实际支付金额
//				sql = " UPDATE lease_base_info SET ridesInfo = ?,totalCount = ?,realprice = ? WHERE leaseOrderNo = ? ";
//				params = new Object[4];
//				params[0] = Integer.parseInt(vo.getA4())- reDates;
//				params[1] = params[2] = nowRealPay;
//				params[3] = orderNo;
//				statu = qr.update(conn, sql,params);
//				if(1!=statu){
//					throw new Exception("更新lease_base_info失败");
//				}
				
				//修改消费表stat_passenger_consum_lease金额
				sql = " UPDATE stat_passenger_consum_lease SET leaseLimit = ? WHERE passengerId = ? AND leaseOrderNo = ? ";
				params = new Object[3];
				params[0] = nowRealPay;
				params[1] = vo_4.getA4();
				params[2] = orderNo;
				statu = qr.update(conn, sql,params);
				if(statu<0){
					statu = 0;
					throw new Exception("更新stat_passenger_consum_lease失败");
				}
				
				//修改充值表stat_passenger_recharge金额
				sql = " UPDATE stat_passenger_recharge SET moneyLimit = ? WHERE  passengerId = ? AND rerunNo = ? ";
				params = new Object[3];
				params[0] = nowRealPay;
				params[1] = vo_4.getA4();
				params[2] = orderNo;
				statu = qr.update(conn, sql,params);
				if(statu<0){
					throw new Exception("更新stat_passenger_recharge失败");
				}
				
				List<String> con = new ArrayList<String>();
				StringBuilder sb = new StringBuilder();
				
				for(int i = 0;i<lineClassIds.length;i++){
					if(!StringUtils.isEmpty(lineClassIds[i])){
						con.add(lineClassIds[i]);
						sb.append("?,");
					}
				}
				
				SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//根据班次ID获取对应的信息
				sql = " SELECT orderStartTime AS a1,orderDate AS a2,lineBaseId AS a3,lineBusinessId AS a4,lineClassId AS a5,driverId AS a6,vehicleId AS a7 FROM line_class_info WHERE lineClassId IN ("+sb.substring(0,sb.length()-1)+") ";
				List<AppVo_7> classv4 = qr.query(conn, sql, new BeanListHandler<AppVo_7>(AppVo_7.class), con.toArray());
				
				String returnDates = "";
				if(null==classv4 || classv4.size()==0){
					throw new Exception("获取班次信息失败");
				}
				
				List<StatTotalEntity> statTotalList = null;
				StatTotalEntity statTotal = null;
				for(AppVo_7 classv : classv4){
					returnDates += classv.getA2()+",";
					
					//添加支出统计stat_out
					//添加支出记录
					//查询原始收入统计
				    sql="select a.consumeLimit,a.discountRate,a.leaseOrderNo,a.businessId,a.linePrice from stat_total a where a.passengerId=? and a.lineBaseId=? and a.orderDate=? and a.orderStartTime=? and a.lineClassId=? and a.leaseOrderNo=?";
				    statTotalList = qr.query(conn, sql, new BeanListHandler<StatTotalEntity>(StatTotalEntity.class), vo_4.getA4(),classv4.get(0).getA3(),
				    		classv.getA2(),classv.getA1(),classv.getA5(),orderNo);
				    if(false==CollectionUtils.isEmpty(statTotalList)){
				    	//存在记录,说明实际支付金额大于0元
				    	statTotal = statTotalList.get(0);
				    	if(null!=statTotal){
				    		//添加支出记录
							StatOutEntity outEntity = new StatOutEntity();
							outEntity.setStatOutId(IdGenerator.seq());
							outEntity.setLeaseOrderNo(orderNo);
							outEntity.setOperatTime(MyDate.Format.DATETIME.now());
							outEntity.setOperater("");
							//取收入统计的消费金额
							outEntity.setOutMoney(statTotal.getConsumeLimit());
							//支出类型 1:退票   2:改签
							outEntity.setOutType(1);
							outEntity.setOrderDate(classv.getA2());
							outEntity.setOrderStartTime(classv.getA1());
							outEntity.setPassengerId(vo_4.getA4());
							outEntity.setLineBaseId(classv4.get(0).getA3());
							outEntity.setLineSubType(0);
							outEntity.setDriverId(classv.getA6());
							outEntity.setVehicleId(classv.getA7());
							outEntity.setLineClassId(classv.getA5());
							outEntity.setBusinessId(statTotal.getBusinessId());
							sql = "insert into stat_out(statOutId,orderDate,orderStartTime,lineSubType,lineBaseId,passengerId,driverId,outMoney,businessId,vehicleId,operatTime,operater,leaseOrderNo,lineClassId,outType) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						    statu = qr.update(conn, sql,new Object[]{outEntity.getStatOutId(),outEntity.getOrderDate(),outEntity.getOrderStartTime(),outEntity.getLineSubType(),outEntity.getLineBaseId(),outEntity.getPassengerId(),outEntity.getDriverId(),outEntity.getOutMoney(),outEntity.getBusinessId(),outEntity.getVehicleId(),outEntity.getOperatTime(),outEntity.getOperater(),outEntity.getLeaseOrderNo(),outEntity.getLineClassId(),outEntity.getOutType()});
						    if(statu<1){
								throw new Exception("加支出统计失败");
							}
					
				    	}
				    }
				}
				
				returnDates = returnDates.substring(0, returnDates.length()-1);
				//添加退票记录return_ticket
				sql = " INSERT INTO return_ticket (returnId,leaseOrderNo,passengerId,orderStartTime,lineBaseId,oldMoney,realPayMoney,realBackMoney,realPoundage,percent,returnDateNum,returnReson,returnDates,operatior,operTime,payType,thirdparty) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				params = new Object[17];
				params[0] = IdGenerator.seq();
				params[1] = orderNo;
				params[2] = vo_4.getA4();                  //passengerId
				params[3] = classv4.get(0).getA1();        //orderStartTime
				params[4] = classv4.get(0).getA3();        //lineBaseId
				params[5] = vo.getA3();                    //oldMoney
				params[6] = nowRealPay;                    //realPayMoney
				params[7] = vo_4.getA3();                  //realBackMoney
				params[8] = vo_4.getA5();                  //realPoundage
				params[9] = vo_4.getA2();                  //percent
				params[10] = reDates;                      //returnDateNum
				params[11] = 1;                            //returnReson
				params[12] = returnDates;                  //returnDates
//				args[13] =  vo_4.getA4();                  //operatior
				params[13] =  "";                          //operatior
				params[14] = sdfs.format(new Date());      //operTime
				params[15] = payType;                      //payType
				params[16] = vo.getA6();                   //thirdparty
				statu = qr.update(conn, sql,params);
				if(1!=statu){
					throw new Exception("添加return_ticket失败");
				}
				
				//查询线路名称
				sql = " SELECT lineName AS a1 FROM line_base_info WHERE lineBaseId = ? ";
				params = new Object[1];
				params[0] = classv4.get(0).getA3();
				AppVo_1 vline = qr.query(conn, sql, new BeanHandler<AppVo_1>(AppVo_1.class), params);
				if(null==vline){
					throw new Exception("获取线路名称异常");
				}
				
				//查询手机号码
				sql = " SELECT telephone AS a1 FROM passenger_info WHERE passengerId = ?  ";
				params = new Object[1];
				params[0] = vo_4.getA4();
				AppVo_1 vpass = qr.query(conn, sql, new BeanHandler<AppVo_1>(AppVo_1.class), params);
				if(null==vpass){
					throw new Exception("获取用户手机号码异常");
				}
				
				//存放以站点ID为key,站点名字为value的maps
				Map<String, String> maps = new HashMap<String, String>();
				AppVo_2 v2 = null;
				//存在子线路
				if(!StringUtils.isEmpty(vo.getA5())){
					sql = " SELECT bstation AS a1,estation AS a2 FROM  line_split_info WHERE sid = ? ";
					params = new Object[1];
					params[0] = vo.getA5();
					v2 = qr.query(conn, sql, new BeanHandler<AppVo_2>(AppVo_2.class),params);
					sql = " SELECT id AS a1,name AS a2 FROM pb_station WHERE id IN (?,?)  ";
					params = new Object[2];
					if(null!=v2){
						params[0] = v2.getA1();
						params[1] = v2.getA2();
						List<AppVo_2> v2list = qr.query(conn, sql, new BeanListHandler<AppVo_2>(AppVo_2.class),params);
						if(null!=v2list && v2list.size()>0){
							for(AppVo_2 v :v2list){
								maps.put(v.getA1(), v.getA2());
							}
						}
					}
				}
				//发送站内消息
				//发送站内消息
				String msgId = IdGenerator.seq();
				sql ="insert into sys_msg_info(msgId,msgType,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,issend) values(?,?,?,?,?,?,?,?,?)";
//				String msgTitle ="退票申请";
//				String msgContext="尊敬的用户，"+vline.getA1()+"，["+returnDates+"] "+classv4.get(0).getA1()+"发车的订单，现已成功申请退票。请悉知。"+SystemConstants.COMPANY_INFO;
				String msgTitle ="退票成功";
				String msgContext="亲，您于 "+returnDates+" 乘坐 的车票("+classv4.get(0).getA1();
				if(null!=maps && null!=v2){
					msgContext += " "+maps.get(v2.getA1())+"-"+maps.get(v2.getA2());
				}
				msgContext +=")已经退票成功啦，我们将在7个工作日退款到您的账户，请注意查收。";
				qr.update(conn, sql, new Object[]{msgId,1,0,msgTitle,msgContext,0,"SYSTEM",MyDate.Format.DATETIME.now(),"1"});
				sql="insert into sys_msg_user(localId,userId,msgId,readFlag,sendPhoneNo,sendTime) values(?,?,?,?,?,?)";
				qr.update(conn, sql, new Object[]{IdGenerator.seq(),vo_4.getA4(),msgId,0,vpass.getA1(),MyDate.Format.DATETIME.now()});
				returnValue = "1@"+msgId+"@"+returnDates;
				
				conn.commit();
			}catch (Exception e) {
				logger.error("Refund failed.", e);
				if(null!=conn){
					try {
						conn.rollback();
					} catch (SQLException e1) {
					}
				}
			}
		}
		return returnValue;
	}
	
/*************************************************************************新增接口（V2.0）***********************************************************************/
	
	/**班车详情**/
	public AppVo_15_list getLineAndClassInfo(String lineBaseId,String slineId,String passengerid){
		
		AppVo_15_list result = new AppVo_15_list();
		Object[] params=null;
		//线路信息
		String sql_line = "SELECT linebaseid,businessid,orderprice,linekm,linetime FROM line_base_info WHERE linebaseid = ?";
		params = new Object[1];
		params[0] = lineBaseId;
		LineBaseInfo line = queryBean(LineBaseInfo.class, sql_line,params);
		
		//子线路信息
		String sql_split = "SELECT a.*,(SELECT name FROM pb_station WHERE id = a.bstation) AS temp1, (SELECT name FROM pb_station WHERE id = a.estation) AS temp2 FROM line_split_info a WHERE a.sid = ? ";
		params = new Object[1];
		params[0] = slineId;
		LineSplitInfo split = queryBean(LineSplitInfo.class, sql_split,params);
		
		//班次信息
		String sql_class = "SELECT t.*,(SELECT vehicleNumber FROM vehicle_info WHERE t.vehicleId = vehicleId) AS dispatchStatus FROM line_class_info t WHERE t.linebaseid = ? AND t.delflag = '0' AND t.orderdate >= '"+MyDate.Format.DATE.now()+"' ORDER BY t.orderdate ASC LIMIT 1";
//		String sql_class = "SELECT * FROM line_class_info WHERE linebaseid = ? AND delflag = '0' AND orderdate >= '"+MyDate.Format.DATE.now()+"' ORDER BY orderdate ASC LIMIT 1";
		params = new Object[1];
		params[0] = lineBaseId;
		LineClassInfo clazz = queryBean(LineClassInfo.class, sql_class,params);
		if(clazz==null){//当天没有排班，去最近一次班次信息
			String sql_temp = "SELECT t.*,(SELECT vehicleNumber FROM vehicle_info WHERE t.vehicleId = vehicleId) AS dispatchStatus FROM line_class_info t WHERE t.linebaseid = ? AND t.delflag = '0' ORDER BY t.orderdate DESC LIMIT 1";
//			String sql_temp = "SELECT * FROM line_class_info WHERE linebaseid = ? AND delflag = '0' ORDER BY orderdate DESC LIMIT 1";
			params = new Object[1];
			params[0] = lineBaseId;
			clazz = queryBean(LineClassInfo.class, sql_temp,params);
		}
		if(MyDate.Format.DATE.now().equals(clazz.getOrderDate())){
			result.setA7(clazz.getPrice());
		}else{
			result.setA7(line.getOrderPrice());//票价
		}
		result.setA1(clazz.getOrderStartTime());//发车时间
		String stationNameArray = stationInfoDao.generateStationNameArrayByLineId(line.getLineBaseId());
		if(stationNameArray!=null && !"".equals(stationNameArray)){
			result.setA2(stationNameArray.substring(0,stationNameArray.indexOf("-")));//始发站
		}else{
			result.setA2("");
		}
		
		if(null!=split){
			result.setA3(split.getTemp1());//上车点
			result.setA4(split.getTemp2());//下车点
			result.setA9(split.getSid());//子线路ID
		}
		
		result.setA5(line.getLineKm());//全程公里
		result.setA6(line.getLineTime());//行程时间
		result.setA8(line.getBusinessId());//商户ID
		
		result.setA10(line.getOrderPrice());//原价
		result.setA12(clazz.getDispatchStatus());//车牌号
		
		//站点集合信息（包含非站点）
		
		String sql_station = "SELECT id as a1,name as a2,baiduLng as a3,baiduLat as a4,type as a5 FROM pb_station WHERE lineId = ? and available=0 order by sortNum";
		 params = new Object[1];
		params[0] = line.getLineBaseId();
		List<AppVo_5> stations = queryList(AppVo_5.class, sql_station,params);//a5:(0:拐点，1：上车点：2：下车点)
		for (AppVo_5 station : stations) {
			if(station.getA5().equals("2")){
				station.setA5("0");
			}else if(station.getA5().equals("1")){
				station.setA5("1");
			}else{
				station.setA5("2");
			}
		}
		result.setList(stations);//站点信息
		List<SeatInfoTemp> seat = getSeat(lineBaseId, clazz.getOrderStartTime(),passengerid,30);//座位信息
		
		//判断是否购买
		String classids = "";
		if(seat!=null){
			for (int i = 0; i < seat.size(); i++) {
				classids+=seat.get(i).getLineClassid()+",";
			}
		}
		
		if(!StringUtils.isEmpty(passengerid)){
			//登录状态,需要判断是否购票
			if(!"".equals(classids)){
				classids = "'"+classids.substring(0,classids.length()-1).replace(",","','")+"'";
//			SELECT a.lineClassId FROM line_business_order a LEFT JOIN lease_base_info b ON a.leaseorderid = b.leaseorderno WHERE a.lineClassId IN('150609154907324973','150609154907324974') AND b.ispay = '1' AND b.passengerid = '150617172430457027' AND a.status = '0' GROUP BY a.lineclassid
				String sql_buy = "SELECT a.lineClassId as a1 FROM line_business_order a LEFT JOIN lease_base_info b ON a.leaseorderid = b.leaseorderno WHERE a.lineClassId IN("+classids+") AND b.ispay = '1' AND b.passengerid = ? AND a.status not in ('1','4') GROUP BY a.lineclassid";
				params = new Object[1];
				params[0] = passengerid;
				List<AppVo_1> isbuy = queryList(AppVo_1.class, sql_buy,params);
				if(isbuy!=null&& isbuy.size()!=0){
					
					for (int i = 0; i < isbuy.size(); i++) {
						for (int j = 0; j < seat.size(); j++) {
							if(isbuy.get(i).getA1().equals(seat.get(j).getLineClassid())){
								seat.get(j).setIsbook("1");
							}
						}
					}
				}
			}
			for (int i = 0; i < seat.size(); i++) {
				if (!"1".equals(seat.get(i).getIsbook())) {
					seat.get(i).setIsbook("0");
				}
			}
		}else{
			//未登录状态
			for (int i = 0; i < seat.size(); i++) {
				seat.get(i).setIsbook("0");
			}
		}
		
		
		//限购时间
		String sql_time = "select delayTime as a1 from sys_app_setting limit 1";
		AppVo_1 delayTime = queryBean(AppVo_1.class, sql_time);
		String orderlimit = "30";
		if(delayTime!=null){
			orderlimit = delayTime.getA1();
		}
		result.setA11(orderlimit);
		
		//老版本，处理当天票数状态问题
		if(seat!=null){
			
			result.setA13(seat.get(0).getFreeSeat());/*当天原始票数*/
	    	//查询购票推迟时间，如果数据库未设置，则默认给5分钟
			int theDelayTime = 5;//默认推迟5分钟
			if(StringUtils.isNotBlank(orderlimit)&&Integer.parseInt(orderlimit)>=0){
				theDelayTime=Integer.parseInt(orderlimit);
			}
			
			String Otime = MyDate.Format.DATE.now()+" "+clazz.getOrderStartTime()+":00";   
			Otime = MyDate.addTimeByMin(Otime,theDelayTime);//往后加30分钟
			String Ntime = MyDate.Format.DATETIME.now();
			int flag = MyDate.compare_date_time(Otime, Ntime);
			
			if(flag == -1){
				seat.get(0).setFreeSeat("-1");
			}
	    }
		
		
		result.setList1(seat);
		return result;
	}
	
	
	//根据时间、线路，获取今天往后15天的座位信息
	public List<SeatInfoTemp> getSeat(String lineid,String time,String passengerid,int count){
		List<SeatInfoTemp> seat_info_list = new ArrayList<SeatInfoTemp>();//当前班次十五天后的座位信息
		List<Object> paramlist=new ArrayList<Object>();
		
		String sql_seat_info = "";//sql
		for (int i = 0; i < count; i++) {
			sql_seat_info +=" SELECT ? AS orderStartTime,? AS orderDate,? AS linebaseid ,f_getFreeSeatCount(?,?,?)  AS freeSeat, ";
	   			paramlist.add(time); paramlist.add(MyDate.getPriorDay1(i).substring(0, 10));paramlist.add(lineid);paramlist.add(time); paramlist.add(MyDate.getPriorDay1(i).substring(0, 10));paramlist.add(lineid);
	   		sql_seat_info +=" (SELECT lineClassId FROM line_class_info WHERE delflag = '0' and orderStartTime = ? AND orderDate = ? AND linebaseid = ?) AS lineclassid, ";
	   			paramlist.add(time); paramlist.add(MyDate.getPriorDay1(i).substring(0, 10));paramlist.add(lineid);
  			sql_seat_info +=" (SELECT orderSeats FROM line_class_info WHERE delflag = '0' and orderStartTime = ? AND orderDate = ? AND linebaseid = ?) AS orderSeats, ";
   				paramlist.add(time); paramlist.add(MyDate.getPriorDay1(i).substring(0, 10));paramlist.add(lineid);
   			sql_seat_info +="(SELECT price FROM line_class_info WHERE delflag = '0' AND orderStartTime = ? AND orderDate = ? AND linebaseid = ?) AS price ";
   				paramlist.add(time); paramlist.add(MyDate.getPriorDay1(i).substring(0, 10));paramlist.add(lineid);
   			sql_seat_info+=" UNION ";
		}
			sql_seat_info = sql_seat_info.substring(0, sql_seat_info.length()-6);
		    Object[] params = paramlist.toArray(new Object[]{});
		    seat_info_list = queryList(SeatInfoTemp.class, sql_seat_info,params);
		    if(seat_info_list == null){
		    	seat_info_list =  new ArrayList<SeatInfoTemp>();
		    }
		    
		return seat_info_list;
	}
	
	
	/**可用优惠卷列表(v2.0新接口)**/
	public List<AppVo_10> getUsableGif(String userid,String type){
		return couponDao.getUsableCoupon(userid, null, null, 0, 0);
	}

	
	
	/**车票预定（按次购买V2.0新增接口）**/
	public String bookLineByDays_new(String lineBaseid,String lineSplitId,String lineClassIds,String userid,String userType,String gifId){
		// 1 ：票价低于一元钱的班次只能购买一次   2:无效班次  3：余座不足   4:线路信息有误    5:系统错误   6:无效优惠券   其他：订单NO
		
//		删除待支付订单（V2.0不需要待支付状态）
		Object[] params=null;
		String sql_order = "select leaseOrderNo as a1 from lease_base_info where passengerId = ? and payStatus = '0' and ispay = '0'";
		params = new Object[1];
		params[0] = userid;
		AppVo_1 order = queryBean(AppVo_1.class, sql_order,params);
		if(order!=null){
			String flag = cancelOrder(order.getA1());
			if("-1".equals(flag)){
				return "5";
			}
		}
		String returnValue = "5";
		Float gif_value = 0.00f;//红包金额
		Float balance = 0.00f;//用户余额
		Float totalPrcie = getLineClassesPrice(lineClassIds);//票价
		
		Connection conn = null;
		try {
			
			conn = Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			
			if(lineClassIds==null || "".equals(lineClassIds) || lineSplitId == null || "".equals(lineSplitId) || lineBaseid == null || "".equals(lineBaseid)){
				return "2";//无效班次
			}
			
			//检查票价低于1元钱是否购买
			String[] strs_price=lineClassIds.split(",");
			StringBuffer sb_price=new StringBuffer();
			List<String> condition_price=new ArrayList<String>();
			condition_price.add(userid);
			for (int i = 0; i < strs_price.length; i++) {
				condition_price.add(strs_price[i]);
				sb_price.append("?,");
			}
			String sql_price = "SELECT a.lineclassid as a1,a.classprice as a2 FROM line_business_order a LEFT JOIN lease_base_info b ON a.leaseOrderId = b.leaseOrderNo WHERE a.passengerid = ? AND a.lineClassId IN("+sb_price.substring(0,sb_price.length()-1)+")  AND b.ispay = '1' AND a.status <> '4' and a.classprice < 1";
			List<AppVo_2> list_price = qr.query(conn, sql_price, new BeanListHandler<AppVo_2>(AppVo_2.class), condition_price.toArray());
			if(list_price != null && list_price.size()!=0){
				return "1";
			}
			
			//检查线路信息
			String sql_line = "select * from line_base_info where lineBaseId = ? and lineStatus = '3' and lineType = '1'";
			params = new Object[1];
			params[0] = lineBaseid;
			LineBaseInfo line = qr.query(conn, sql_line, new BeanHandler<LineBaseInfo>(LineBaseInfo.class), params);
			
			//线路信息有误
			if(line==null){
				return "4";
			}
			
			//根据ids查询对应班次
			String[] strs=lineClassIds.split(",");
			StringBuffer sb=new StringBuffer();
			List<String> condition=new ArrayList<String>();
			for (int i = 0; i < strs.length; i++) {
				condition.add(strs[i]);
				sb.append("?,");
			}
			for (int i = 0; i < strs.length; i++) {
				condition.add(strs[i]);
			}
			String sql_class = "select *,f_getFreeSeatCount(orderStartTime,orderdate,linebaseid) as freeSeat from line_class_info where lineClassid IN ("+sb.substring(0,sb.length()-1)+") order by FIELD(lineClassId,"+sb.substring(0,sb.length()-1)+")";
			List<LineClassInfo> list_class = qr.query(conn, sql_class, new BeanListHandler<LineClassInfo>(LineClassInfo.class), condition.toArray());
			
			//检查座位数是否充足
			for (int i = 0; i < list_class.size(); i++) {
				if(list_class.get(i).getFreeSeat()==null){
					return "3";
				}else{
					int freeSeat = Integer.valueOf(list_class.get(i).getFreeSeat());
					if(freeSeat<=0){
						return "3";
					}
				}
			}
			//无效班次（查不到班次）
			if(list_class==null || list_class.size()==0){
				return "2";
			}
			//无效班次（班次ID个数与查询结果集个数不匹配）
			if(list_class.size() != lineClassIds.split(",").length){
				return "2";
			}

			//检查红包信息
			if(gifId!=null && !"".equals(gifId)){
					List<AppVo_6> list=couponDao.getCoupon(conn,qr,gifId, userid, null, 0, 0,totalPrcie+"");
					if(null==list||list.size()==0){
						logger.warn("系统错误：根据红包ID {} 查询不到相关数据！", gifId);
						return "6";
					}
					else{
						gif_value=Float.valueOf(list.get(0).getA1());
					}
			}
			
			//检查子线路信息
			String sql_splitInfo = "select * from line_split_info where sid = ? and iswork = '1' ";
			params = new Object[1];
			params[0] = lineSplitId;
			LineSplitInfo slineInfo = qr.query(conn, sql_splitInfo, new BeanHandler<LineSplitInfo>(LineSplitInfo.class), params);
			if(slineInfo==null){
				return "4";
			}
			
			//下单

			String orderNo = "BS"+IdGenerator.seq();//订单号(BS用于区分巴士订单)
			
			/**情况1：票价为0，直接下单返回单号**/
			if(totalPrcie==0.00f){
				String sql = " INSERT INTO lease_base_info(leaseOrderId,leaseOrderNo,lineBaseId,slineId,ridesInfo,buyType,discountRate,totalCount,leaseOrderTime,payStatus,businessId,passengerId,sourceFrom,ustation,dstation,realprice,isdiscount,remark,ispay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				params = new Object[19];
				params[0] = IdGenerator.seq();
				params[1] = orderNo;
				params[2] = lineBaseid;
				params[3] = lineSplitId;
				params[4] = String.valueOf(lineClassIds.split(",").length);
				params[5] = "0";
				params[6] = "100";
				params[7] = String.valueOf(totalPrcie);
				params[8] = MyDate.Format.DATETIME.now();
				params[9] = "1";
				params[10] = line.getBusinessId();
				params[11] = userid;
				params[12] = "1".equals(userType)?"0":"1";
				params[13] = slineInfo.getBstation();
				params[14] = slineInfo.getEstation();
				params[15] = "0.00";
				params[16] = "1";
				params[17] = "票价为0";
				params[18] = "1";
				int flag = qr.update(conn, sql, params);
				if(flag == -1){
					throw new Exception("添加lease_base_info异常");
				}
				
				sql = " INSERT INTO line_business_order(localId,lineClassId,passengerId,leaseOrderId,businessId,tickets,doCount,price,classprice,giftMoney,type,remark,outMoney) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				for (int i = 0; i < list_class.size(); i++) {
					params = new Object[13];
					params[0] = IdGenerator.seq();
					params[1] = list_class.get(i).getLineClassId();
					params[2] = userid;
					params[3] = orderNo;
					params[4] = list_class.get(i).getLineBusinessId();
					params[5] = "1";
					params[6] = "0";
					params[7] = "0.00";
					params[8] = "0.00";
					params[9] = "0.00";
					params[10] = "0";
					params[11] = "票价为0";
					params[12] = "0.00";
					flag = qr.update(conn, sql, params);
					if(flag<1){
						throw new Exception("添加line_business_order异常");
					}
				}
				
				 sql = "insert into lease_pay values(?,?,?,?)";
				params = new Object[4];
				params[0] = IdGenerator.seq();
				params[1] = "0";
				params[2] = "0.00";
				params[3] = orderNo;
				flag = qr.update(conn, sql, params);
				if(flag<1){
					throw new Exception("添加lease_pay异常");
				}
				conn.commit();
				return orderNo+"#0";
			}
			
			/**情况2：全部使用优惠券**/
			if(gif_value >= totalPrcie && totalPrcie!=0.00f && gif_value != 0.00f){
				
			String 	sql = " INSERT INTO lease_base_info(leaseOrderId,leaseOrderNo,lineBaseId,slineId,ridesInfo,buyType,discountRate,totalCount,leaseOrderTime,payStatus,businessId,passengerId,sourceFrom,ustation,dstation,realprice,isdiscount,remark,ispay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				params = new Object[19];
				params[0] = IdGenerator.seq();
				params[1] = orderNo;
				params[2] = lineBaseid;
				params[3] = lineSplitId;
				params[4] = String.valueOf(lineClassIds.split(",").length);
				params[5] = "0";
				params[6] = "100";
				params[7] = String.valueOf(totalPrcie);
				params[8] = MyDate.Format.DATETIME.now();
				params[9] = "1";
				params[10] = line.getBusinessId();
				params[11] = userid;
				params[12] = "1".equals(userType)?"0":"1";
				params[13] = slineInfo.getBstation();
				params[14] = slineInfo.getEstation();
				params[15] = "0.00";
				params[16] = "1";
				params[17] = "优惠券购买，优惠券ID："+gifId;
				params[18] = "1";
				int flag = qr.update(conn, sql, params);
				if(flag == -1){
					throw new Exception("添加lease_base_info异常");
				}
				
				
				sql = " INSERT INTO line_business_order(localId,lineClassId,passengerId,leaseOrderId,businessId,tickets,doCount,price,classprice,giftMoney,type,remark,outMoney,couponGiftId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				for (int i = 0; i < list_class.size(); i++) {
					
					params = new Object[14];
					params[0] = IdGenerator.seq();
					params[1] = list_class.get(i).getLineClassId();
					params[2] = userid;
					params[3] = orderNo;
					params[4] = list_class.get(i).getLineBusinessId();
					params[5] = "1";
					params[6] = "0";
					params[7] = "0.00";
					params[8] = list_class.get(i).getPrice();
					params[9] = list_class.get(i).getPrice();
					params[10] = "2";
					params[11] = "优惠券购买，优惠券ID："+gifId;
					if(i==strs.length-1){
						params[12] = String.valueOf(gif_value-totalPrcie);//优惠券剩余金额
					}else{
						params[12] = "0.00";
					}
					params[13] = gifId;
					flag = qr.update(conn, sql, params);
					if(flag<1){
						throw new Exception("添加line_business_order异常");
					}
					
				}
				
				//如果使用的不是老优惠券，则用的是新优惠券，修改新优惠券状态
				couponDao.updateCouponState(conn,qr,"used",orderNo,MyDate.Format.DATETIME.now(), gifId);
				sql = "insert into lease_pay values(?,?,?,?)";
				params = new Object[4];
				params[0] = IdGenerator.seq();
				params[1] = "0";
				params[2] = "0.00";
				params[3] = orderNo;
				flag = qr.update(conn, sql, params);
				if(flag<1){
					throw new Exception("添加lease_pay异常");
				}
				
				conn.commit();
				return orderNo+"#0";
			}
			
			/**情况3：现金+优惠券组合支付**/
			if(totalPrcie!=0.00f && gif_value !=0.00f && totalPrcie>gif_value){
				
				String sql = " INSERT INTO lease_base_info(leaseOrderId,leaseOrderNo,lineBaseId,slineId,ridesInfo,buyType,discountRate,totalCount,leaseOrderTime,payStatus,businessId,passengerId,sourceFrom,ustation,dstation,realprice,isdiscount,remark,ispay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				params = new Object[19];
				params[0] = IdGenerator.seq();
				params[1] = orderNo;
				params[2] = lineBaseid;
				params[3] = lineSplitId;
				params[4] = String.valueOf(lineClassIds.split(",").length);
				params[5] = "0";
				params[6] = "100";
				params[7] = String.valueOf(totalPrcie);
				params[8] = MyDate.Format.DATETIME.now();
				params[9] = "0";                                        //状态为待支付
				params[10] = line.getBusinessId();
				params[11] = userid;
				params[12] = "1".equals(userType)?"0":"1";
				params[13] = slineInfo.getBstation();
				params[14] = slineInfo.getEstation();
				params[15] = String.valueOf(totalPrcie-gif_value);
				params[16] = "1";
				params[17] = "优惠券购买，优惠券ID："+gifId;
				params[18] = "0";
				int flag = qr.update(conn, sql, params);
				if(flag == -1){
					throw new Exception("添加lease_base_info异常");
				}
				
				
				Float money = gif_value;//优惠券金额
				Float temp = 0.00f;//临时计算金额
				Integer no = 0;//排列标识
				
				for (int i = 0; i < list_class.size(); i++) {
					temp = temp+Float.valueOf(list_class.get(i).getPrice());
					if(temp >= money){
						no = i;
						break;
					}
				}
				
				sql = " INSERT INTO line_business_order(localId,lineClassId,passengerId,leaseOrderId,businessId,tickets,doCount,price,classprice,giftMoney,type,remark,outMoney,couponGiftId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				//全部优惠券部分
				for (int i = 0; i < no; i++) {
					
					params = new Object[14];
					params[0] = IdGenerator.seq();
					params[1] = list_class.get(i).getLineClassId();
					params[2] = userid;
					params[3] = orderNo;
					params[4] = list_class.get(i).getLineBusinessId();
					params[5] = "1";
					params[6] = "0";
					params[7] = "0.00";
					params[8] = list_class.get(i).getPrice();
					params[9] = list_class.get(i).getPrice();
					params[10] = "2";
					params[11] = "优惠券购买，优惠券ID："+gifId;
					params[12] = "0.00";   //优惠券剩余金额
					params[13] = gifId;
					flag = qr.update(conn, sql, params);
					if(flag<1){
						throw new Exception("添加line_business_order异常");
					}
					
					
				}
				
				//优惠券+现金部分
				
				params = new Object[14];
				params[0] = IdGenerator.seq();
				params[1] = list_class.get(no).getLineClassId();
				params[2] = userid;
				params[3] = orderNo;
				params[4] = list_class.get(no).getLineBusinessId();
				params[5] = "1";
				params[6] = "0";
				params[7] = String.valueOf(temp-gif_value);
				params[8] = list_class.get(no).getPrice();
				params[9] = String.valueOf(Float.valueOf(list_class.get(no).getPrice())-(temp-gif_value));
				params[10] = "2";
				params[11] = "优惠券购买，优惠券ID："+gifId;
				params[12] = "0.00";   //优惠券剩余金额
				params[13] = gifId;
				flag = qr.update(conn, sql, params);
				if(flag<1){
					throw new Exception("添加line_business_order异常");
				}
				
				
				
				//现金部分
				for (int i = no+1; i < list_class.size(); i++) {
					
					params = new Object[14];
					params[0] = IdGenerator.seq();
					params[1] = list_class.get(i).getLineClassId();
					params[2] = userid;
					params[3] = orderNo;
					params[4] = list_class.get(i).getLineBusinessId();
					params[5] = "1";
					params[6] = "0";
					params[7] = list_class.get(i).getPrice();
					params[8] = list_class.get(i).getPrice();
					params[9] = "0.00";
					params[10] = "0";
					params[11] = "正常购票";
					params[12] = "0.00";   //优惠券剩余金额
					params[13] = null;
					flag = qr.update(conn, sql, params);
					if(flag<1){
						throw new Exception("添加line_business_order异常");
					}
					
				}
				//如果使用的不是老优惠券，则用的是新优惠券，修改新优惠券状态
				couponDao.updateCouponState(conn,qr,"used",orderNo,MyDate.Format.DATETIME.now(), gifId);
				sql = "insert into lease_pay values(?,?,?,?)";
				params = new Object[4];
				params[0] = IdGenerator.seq();
				params[1] = "0";
				params[2] = String.valueOf(totalPrcie-gif_value);
				params[3] = orderNo;
				flag = qr.update(conn, sql, params);
				if(flag<1){
					throw new Exception("添加lease_pay异常");
				}
				
				conn.commit();
				return orderNo;
			}
			
			/**情况4：全部使用现金支付**/
			if(totalPrcie!=0.00f && gif_value == 0.00f){
				
				String sql = " INSERT INTO lease_base_info(leaseOrderId,leaseOrderNo,lineBaseId,slineId,ridesInfo,buyType,discountRate,totalCount,leaseOrderTime,payStatus,businessId,passengerId,sourceFrom,ustation,dstation,realprice,isdiscount,remark,ispay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				params = new Object[19];
				params[0] = IdGenerator.seq();
				params[1] = orderNo;
				params[2] = lineBaseid;
				params[3] = lineSplitId;
				params[4] = String.valueOf(lineClassIds.split(",").length);
				params[5] = "0";
				params[6] = "100";
				params[7] = String.valueOf(totalPrcie);
				params[8] = MyDate.Format.DATETIME.now();
				params[9] = "0";                                        //状态为待支付
				params[10] = line.getBusinessId();
				params[11] = userid;
				params[12] = "1".equals(userType)?"0":"1";
				params[13] = slineInfo.getBstation();
				params[14] = slineInfo.getEstation();
				params[15] = String.valueOf(totalPrcie-gif_value);
				params[16] = "0";
				params[17] = "正常购票";
				params[18] = "0";
				int flag = qr.update(conn, sql, params);
				if(flag == -1){
					throw new Exception("添加lease_base_info异常");
				}
				
				
				sql = " INSERT INTO line_business_order(localId,lineClassId,passengerId,leaseOrderId,businessId,tickets,doCount,price,classprice,giftMoney,type,remark,outMoney,couponGiftId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
				for (int i = 0; i < list_class.size(); i++) {
					
					params = new Object[14];
					params[0] = IdGenerator.seq();
					params[1] = list_class.get(i).getLineClassId();
					params[2] = userid;
					params[3] = orderNo;
					params[4] = list_class.get(i).getLineBusinessId();
					params[5] = "1";
					params[6] = "0";
					params[7] = list_class.get(i).getPrice();
					params[8] = list_class.get(i).getPrice();
					params[9] = "0.00";
					params[10] = "0";
					params[11] = "正常购票";
					params[12] = "0.00";   //优惠券剩余金额
					params[13] = null;
					flag = qr.update(conn, sql, params);
					if(flag<1){
						throw new Exception("添加line_business_order异常");
					}
				}
				
				 sql = "insert into lease_pay values(?,?,?,?)";
				params = new Object[4];
				params[0] = IdGenerator.seq();
				params[1] = "0";
				params[2] = String.valueOf(totalPrcie-gif_value);
				params[3] = orderNo;
				flag = qr.update(conn, sql, params);
				if(flag<1){
					throw new Exception("添加lease_pay异常");
				}
				
				conn.commit();
				return orderNo;
			}
			
			conn.commit();
		}catch(Exception e){
			logger.error("Book ticket failed.", e);
			try {
				if(null!=conn && !conn.isClosed()){
					try {
						conn.rollback();
					} catch (SQLException e1) {
						logger.error("Rollback failed.", e1);
					}
				}
			} catch (SQLException e1) {
				logger.error("Check isClosed failed.", e1);
			}
		}
		return returnValue;
	}
	
	/**根据班次ID获取价格**/
	public Float getLineClassesPrice(String classids){
		//检查票价低于1元钱是否购买
		String[] strs=classids.split(",");
		StringBuffer sb=new StringBuffer();
		List<String> condition=new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			condition.add(strs[i]);
			sb.append("?,");
		}
		String sql = "SELECT SUM(price) as a1 FROM line_class_info WHERE lineClassId IN("+sb.substring(0,sb.length()-1)+")";
		AppVo_1 price = queryBean(AppVo_1.class, sql,condition.toArray());
		if(price!=null){
			return Float.valueOf(price.getA1());
		}else{
			return 0.00f;
		}
	}
	
	/**获取订单对应的优惠券金额**/
	public AppVo_2 getOrderGifMoney(String orderNo){
		AppVo_2 result = new AppVo_2();
		Object[] params=null;
		String sql = "SELECT SUM(giftmoney) as a1 FROM line_business_order WHERE leaseOrderId = ? ";
		params = new Object[1];
		params[0] = orderNo;
		AppVo_1 vo = queryBean(AppVo_1.class, sql,params);
		if(vo==null || vo.getA1()==null){
			result.setA1("0");
		}else{
			result.setA1(vo.getA1());
		}
		
		String sql_order = "select totalCount as a1 from lease_base_info where leaseOrderNo = ?";
		params = new Object[1];
		params[0] = orderNo;
		AppVo_1 orderprice = queryBean(AppVo_1.class, sql_order,params);
		if(null!=orderprice && !StringUtils.isEmpty(orderprice.getA1())){
			result.setA2(orderprice.getA1());
		}else{
			result.setA2("0");
		}
		return result;
	}
	
	/**获取包车订单对应的优惠券金额**/
	public AppVo_2 getBcOrderGifMoney(String orderNo){
		Object[] params=null;
		String sql = "SELECT giftMoney AS a1,totalPrice AS a2 FROM bc_order WHERE orderNo = ?";
		params = new Object[1];
		params[0] = orderNo;
		AppVo_2 vo = queryBean(AppVo_2.class, sql,params);
		return vo;
	}
	

	/**申请退票**/
	public int applyReturnTicket(ApplyReturn applyReturn) {
		return updateData(applyReturn,"apply_return", "applyReturnId");
	}

	/**添加发送信息**/
	public String addMsgInfo(ApplyReturn applyReturn) {
		Connection conn = null;
		String msgId = null;
		Object[] params=null;
		try {
			conn = Conn.get();
			conn.setAutoCommit(false);
			QueryRunner qr = new QueryRunner();
			
			//根据订单号获取子线路的起点,终点
			//SELECT a.leaseOrderNo,b.bstation AS a1,b.estation AS a2 from lease_base_info AS a LEFT JOIN line_split_info AS b ON a.slineId = b.sid WHERE a.leaseOrderNo = '141011293564' 
			String sql = " SELECT b.bstation AS a1,b.estation AS a2 from lease_base_info AS a LEFT JOIN line_split_info AS b ON a.slineId = b.sid WHERE a.leaseOrderNo = ? ";
			params = new Object[1];
			AppVo_2 vo = qr.query(conn, sql, new BeanHandler<AppVo_2>(AppVo_2.class), params);
			if(null==vo){
				throw new Exception("根据订单查询子线路起点终点异常");
			}
			sql = " SELECT id AS a1,name AS a2 FROM pb_station WHERE id IN(?,?) ";
			params = new Object[2];
			params[0] = vo.getA1();
			params[1] = vo.getA2();
			List<AppVo_2> vo2list = qr.query(conn, sql, new BeanListHandler<AppVo_2>(AppVo_2.class), params);
			if(null == vo2list || vo2list.size()==0){
				throw new Exception("查询站点信息异常");
			}
			Map<String, String> maps = new HashMap<String, String>();
			for(AppVo_2 v2:vo2list){
				maps.put(v2.getA1(), v2.getA2());
			}
			
			//发送站内消息
			msgId = IdGenerator.seq();
			sql ="insert into sys_msg_info(msgId,msgType,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,issend) values(?,?,?,?,?,?,?,?,?)";
			String msgTitle ="退票申请提交成功";
//			String msgContext="尊敬的用户，["+maps.get(vo.getA1())+" 到 ["+maps.get(vo.getA2())+"] 班车的订单退票申请已提交。请悉知。"+SystemConstants.COMPANY_INFO;
			String msgContext="退票申请提交成功，我们将在7个工作日退款到您的账户，如有任何疑问，请拨打客服电话400-168-1866。";
			int statu =  qr.update(conn, sql, new Object[]{msgId,1,0,msgTitle,msgContext,0,"SYSTEM",MyDate.Format.DATETIME.now(),"1"});
			if(statu<=0){
				throw new Exception("插入sys_msg_info信息异常");
			}
			sql="insert into sys_msg_user(localId,userId,msgId,readFlag,sendPhoneNo,sendTime) values(?,?,?,?,?,?)";
			statu = qr.update(conn, sql, new Object[]{IdGenerator.seq(),applyReturn.getPassengerId(),msgId,0,applyReturn.getTelephone(),MyDate.Format.DATETIME.now()});
			if(statu<=0){
				throw new Exception("插入sys_msg_user信息异常");
			}
			conn.commit();
		}catch (Exception e) {
			if(null!=conn){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					
				}
			}
		}
		return msgId;
	}
	
	/**订单列表v2.1**/
	public List<AppVo_18> getOrderListByV2_1(String userid,String orderType,String currentPage,String pageSize){
		String sql = "SELECT a.leaseorderNo,a.linebaseid,a.slineid,a.ridesInfo,a.totalcount,a.realprice ,a.leaseordertime FROM lease_base_info a WHERE a.passengerId = ? AND a.ispay = '1' AND (a.payStatus = '1' or a.payStatus ='2') order by a.leaseordertime desc";
		Object[] params=null;
		params = new Object[1];
		params[0] = userid;
		List<LeaseBaseInfo> orderList = queryByPage(LeaseBaseInfo.class, sql, Integer.valueOf(currentPage), Integer.valueOf(pageSize),params);
		if(orderList!=null){
			List<AppVo_18> result = new ArrayList<AppVo_18>();
			for (int i = 0; i < orderList.size(); i++) {
				 AppVo_18 vo = new AppVo_18();
				 vo.setA1(orderList.get(i).getLeaseOrderNo());
				 vo.setA2(orderList.get(i).getLeaseOrderTime());
				 vo.setA3(orderList.get(i).getRidesInfo());
				 vo.setA4(orderList.get(i).getTotalCount());
				 vo.setA5(orderList.get(i).getRealprice());
				 
				 
				 //查询发车时间，公里数，行程时间
				 String sql_line = "SELECT lineTime AS a1,lineKm AS a2,(SELECT orderStartTime FROM line_class_info WHERE lineBaseId = '"+orderList.get(i).getLineBaseId()+"' AND delFlag = '0' ORDER BY orderDate DESC LIMIT 1) AS a3 FROM line_base_info WHERE lineBaseId = '"+orderList.get(i).getLineBaseId()+"'";
				 AppVo_3 vo_line = queryBean(AppVo_3.class, sql_line);
				 if(vo_line!=null){
					 vo.setA6(vo_line.getA3());//发车时间
					 vo.setA7(vo_line.getA2());//公里数
					 vo.setA8(vo_line.getA1());//行程时间
				 }
				 
				String sql_station = "select (select name from pb_station where id = ustation) as a2, (select name from pb_station where id = dstation) as a3,(select name from pb_station where lineId = '"+orderList.get(i).getLineBaseId()+"' order by sortNum limit 0,1) as a1 from lease_base_info where leaseOrderNo = '"+orderList.get(i).getLeaseOrderNo()+"'";
				AppVo_3 vo_station = queryBean(AppVo_3.class, sql_station);
				if(vo_station!=null){
					vo.setA9(vo_station.getA1());//始发站
					vo.setA10(vo_station.getA2());//起点
					vo.setA11(vo_station.getA3());//终点
				}
				
				result.add(vo);
			}
			
			return result;
		}
		return null;
	}

	/**订单详细v2.1**/
	public AppVo_15_list getOrderInfoByV2_1(String orderNo){
		AppVo_15_list result = new AppVo_15_list();
		String sql_order = "SELECT a.leaseorderNo,a.linebaseid,a.slineid,a.ridesInfo,a.totalcount,a.realprice ,a.leaseordertime FROM lease_base_info a WHERE a.leaseorderno = ? and a.ispay = '1'";
		Object[] params=null;
		params = new Object[1];
		params[0] = orderNo;
		LeaseBaseInfo order = queryBean(LeaseBaseInfo.class, sql_order,params);
		if(order!=null){
			result.setA1(order.getLeaseOrderNo());
			result.setA2(order.getLeaseOrderTime());
			result.setA3(order.getTotalCount());
			result.setA4(order.getRealprice());
			result.setA11(order.getLineBaseId());
			result.setA12(order.getSlineId());
			
			//查询发车时间，公里数，行程时间
			 String sql_line = "SELECT lineTime AS a1,lineKm AS a2,(SELECT orderStartTime FROM line_class_info WHERE lineBaseId = '"+order.getLineBaseId()+"' AND delFlag = '0' ORDER BY orderDate DESC LIMIT 1) AS a3,lineStatus as a4 FROM line_base_info WHERE lineBaseId = '"+order.getLineBaseId()+"'";
			 AppVo_4 vo_line = queryBean(AppVo_4.class, sql_line);
			 if(vo_line!=null){
				 result.setA5(vo_line.getA3());//发车时间
				 result.setA6(vo_line.getA2());//公里数
				 result.setA7(vo_line.getA1());//行程时间
				 result.setA13(vo_line.getA4());//线路状态 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除
			 }
			 
			StringBuffer sql_stationBuffer = new StringBuffer();
			sql_stationBuffer.append(" select (select name FROM pb_station where id = ustation) as a2,");
			sql_stationBuffer.append(" (select name from pb_station where id = dstation) as a3,");
			sql_stationBuffer.append(" (select name from pb_station where lineId = '").append(order.getLineBaseId()).append("' order by sortNum limit 0,1) as a1");
			sql_stationBuffer.append(" from lease_base_info where leaseOrderNo = '").append(order.getLeaseOrderNo()).append("'");		
			AppVo_3 vo_station = queryBean(AppVo_3.class, sql_stationBuffer.toString());
			if(vo_station!=null){
				result.setA8(vo_station.getA1());//始发站
				result.setA9(vo_station.getA2());//起点
				result.setA10(vo_station.getA3());//终点
			}
			
			String sql_seat = "SELECT a.lineClassId AS a1,b.orderDate AS a2,a.status AS a3 FROM line_business_order a LEFT JOIN line_class_info b ON a.lineClassId = b.lineClassId WHERE  a.leaseorderid = ? order by b.orderdate asc";
			params = new Object[1];
			params[0] = orderNo;
			List<AppVo_3> vo_seat = queryList(AppVo_3.class, sql_seat,params);
			if(vo_seat!=null){
				result.setList(vo_seat);//a1:班次ID a2:发车时间  a3:状态（0:正常  1:改签中   2:已改签  3:退票中  4:已退票）
			}
			return result;
		}
		return null;
	}
	
	/**改签详情(2.1)**/
	public List<AppVo_6> changeTicketDetail_V2_1(String localId) {
		List<AppVo_6> result = new ArrayList<AppVo_6>();
		Object[] params=null;
		String sql = "SELECT a.localid AS a1, a.lineclassid AS a2,a.passengerid AS a3,a.leaseorderid AS a4,a.oldclassid AS a5,a.status AS a6,a.classprice AS a7,(a.price+a.giftMoney) AS a8 ,b.orderDate AS a9,b.orderStartTime AS a10 FROM line_business_order a LEFT JOIN line_class_info b ON a.lineClassId = b.lineClassId WHERE a.localid = ? ORDER BY a.lineClassId ASC";
		params = new Object[1];
		params[0] = localId;
		List<AppVo_10> list = queryList(AppVo_10.class, sql, params);
		
		if(list!=null && list.size()!=0){
			for (int i = 0; i < list.size(); i++) {
				AppVo_6 vo = new AppVo_6();
				vo.setA1(list.get(i).getA1());
				vo.setA2(list.get(i).getA2());
				vo.setA3(list.get(i).getA7());//下单时的班次价格
				vo.setA4(list.get(i).getA9().substring(5,list.get(i).getA9().length()).replace("-","."));
				vo.setA5(MyDate.getweekofday(list.get(i).getA9()).replace("星期","周"));
				if("1".equals(list.get(i).getA6())){
					vo.setA6("1");//改签中，不可改签
				}else if("2".equals(list.get(i).getA6())){
					vo.setA6("2");//已改签过，不可改签
				}else if(Float.valueOf(list.get(i).getA8())<1){
					vo.setA6("3");//票价低于一块钱，不可改签
				}else if(MyDate.compare_date_time(list.get(i).getA9()+" "+list.get(i).getA10()+":00", MyDate.Format.DATETIME.now())==-1){
					vo.setA6("5");//车票已过期，不可改签
				}else if(!MyDate.isAfterNMinute(list.get(i).getA9()+" "+list.get(i).getA10()+":00", 30)){
					vo.setA6("4");//离发车时间小于半小时，不可改签
				}else{
					vo.setA6("0");//正常情况
				}
				result.add(vo);
			}
			return result;
		}else{
			return null;
		}
	}

	/**退票详情(2.1)**/
	public List<AppVo_7> returnTicketDetail_V2_1(String localId) {
		List<AppVo_7> result = new ArrayList<AppVo_7>();
		Object[] params=null;
		String sql = "SELECT a.localid AS a1, a.lineclassid AS a2,a.passengerid AS a3,a.leaseorderid AS a4,c.isdiscount AS a5,a.status AS a6,a.price AS a7 ,b.orderDate AS a9,b.orderStartTime AS a10 " 
				+" FROM line_business_order a LEFT JOIN line_class_info b ON a.lineClassId = b.lineClassId LEFT JOIN lease_base_info c ON c.leaseOrderNo = a.leaseOrderId " 
				+ " WHERE a.localid = ?  ORDER BY a.lineClassId ASC";
		params = new Object[1];
		params[0] = localId;
		List<AppVo_10> list = queryList(AppVo_10.class, sql, params);
		
		if(list!=null && list.size()!=0){
			for (int i = 0; i < list.size(); i++) {
				AppVo_7 vo = new AppVo_7();
				vo.setA1(list.get(i).getA1());//localid
				vo.setA2(list.get(i).getA2());//lineclassid
				vo.setA3(list.get(i).getA7());//实际支付金额
				vo.setA4(list.get(i).getA9().substring(5,list.get(i).getA9().length()).replace("-","."));
				vo.setA5(MyDate.getweekofday(list.get(i).getA9()).replace("星期","周"));
				if("1".equals(list.get(i).getA5())){
					vo.setA6("2");//有优惠,不允许退票
				}else if(MyDate.diffMinutes(MyDate.Format.DATETIME.now(),list.get(i).getA9()+" "+list.get(i).getA10()+":00")>=0){
					vo.setA6("4");//已过了发车时间,车票已过期
				}else if(!MyDate.isAfterNMinute(list.get(i).getA9()+" "+list.get(i).getA10()+":00", 60)){
					vo.setA6("1");//离发车时间小于1小时，不可退票
				}else{
					vo.setA6("0");//正常情况
				}
				result.add(vo);
			}
			return result;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 电子票列表v2.3
	 * @return
	 */
	public List<AppVo_25> getTicktListByV2_3(String userId,String type,String currentPage,String pageSize){
//		SELECT a.lineclassid AS a1,a.passengerId AS a2,a.leaseOrderId AS a3,a.classprice AS a4,a.status AS a5,b.slineId AS a6,b.lineBaseId AS a7,b.ustation AS a8,b.dstation AS a9,b.leaseOrderTime AS a10,c.orderDate AS a11,c.orderStartTime AS a12,(SELECT COUNT(*) FROM passenger_comments t WHERE t.passengerid = a.passengerId AND t.leaseorderid = a.leaseorderid AND t.lineclassid = a.lineClassId) AS a13 FROM line_business_order a LEFT JOIN lease_base_info b ON a.leaseOrderId = b.leaseOrderNo LEFT JOIN line_class_info c ON a.lineClassId = c.lineClassId WHERE a.passengerid = '140930133643936510' AND b.ispay = '1' ORDER BY c.orderdate DESC, c.orderStartTime asc
		String sql = "SELECT a.lineclassid AS a1,a.passengerId AS a2,a.leaseOrderId AS a3,a.classprice AS a4,a.status AS a5,b.slineId AS a6,b.lineBaseId AS a7,b.ustation AS a8,b.dstation AS a9,b.leaseOrderTime AS a10,c.orderDate AS a11,c.orderStartTime AS a12,(SELECT COUNT(*) FROM passenger_comments t WHERE t.passengerid = a.passengerId AND t.leaseorderid = a.leaseorderid AND t.lineclassid = a.lineClassId) AS a13 FROM line_business_order a LEFT JOIN lease_base_info b ON a.leaseOrderId = b.leaseOrderNo LEFT JOIN line_class_info c ON a.lineClassId = c.lineClassId ";
		String orderBy = " ORDER BY ";
		String checkTicketETime = PropertyManage.get("init.checkTicketETime")==null?"0":PropertyManage.get("init.checkTicketETime");//发车后多少分钟显示车票
		int min = Integer.valueOf(checkTicketETime)+120;//默认往后2小时
		List<AppVo_25> orderList = null;
		if("1".equals(type)){//有效
			String where = " WHERE a.passengerid = ? AND b.ispay = '1' and (DATE_ADD(CONCAT(c.orderDate,' ',c.orderStartTime,':00'),INTERVAL "+min+" MINUTE) >='"+MyDate.Format.DATETIME.now()+"' and a.status in('0','2'))";
			orderBy+= " c.orderdate asc, c.orderStartTime asc ";
			Object[] params = new Object[1];
			params[0] = userId;
			LoggerFactory.getLogger(BookDaoImpl.class).info("sql="+sql+where+orderBy);
			LoggerFactory.getLogger(BookDaoImpl.class).info("uid="+userId);
			orderList = queryByPage(AppVo_25.class, sql+where+orderBy, Integer.valueOf(currentPage), Integer.valueOf(pageSize),params);
		}else{//过往
			String where = " WHERE (a.passengerid = ? AND b.ispay = '1'  and (DATE_ADD(CONCAT(c.orderDate,' ',c.orderStartTime,':00'),INTERVAL "+min+" MINUTE) <'"+MyDate.Format.DATETIME.now()+"')) or (a.status = '4'  AND a.passengerid = ? AND b.ispay = '1')";
			orderBy+= " c.orderdate desc, c.orderStartTime asc ";
			Object[] params = new Object[2];
			params[0] = userId;
			params[1] = userId;
			orderList = queryByPage(AppVo_25.class, sql+where+orderBy, Integer.valueOf(currentPage), Integer.valueOf(pageSize),params);
		}
		if (orderList!=null && orderList.size()!=0) {
			for (int i = 0; i < orderList.size(); i++) {
				LineBaseInfo lineBaseInfo = new LineBaseInfo(orderList.get(i).getA7());
				StationInfo firstStation = lineBaseInfo.getFirstStation();
				orderList.get(i).setA14(firstStation.getName());//始发站
				StationInfo uStation = new StationInfo(orderList.get(i).getA8());
				orderList.get(i).setA15(uStation.getName());//起点站
				StationInfo dStation = new StationInfo(orderList.get(i).getA9());
				orderList.get(i).setA16(dStation.getName());//终点站
				orderList.get(i).setA17(MyDate.addTimeByMin(orderList.get(i).getA11()+" "+orderList.get(i).getA12()+":00",120));//模拟到站时间
			}
		}
		return orderList;
	}

	/**查找原实际金额**/
	public String queryPriceByLocalIds(String localIds) {
		String sql = " SELECT sum(price)AS a1 FROM line_business_order WHERE 1=1 ";
		String[] localIdStr = {};
		if(StringUtils.isEmpty(localIds)){
			return null;
		}
		localIdStr = localIds.split(",");
		String conds = "";
		List<String> params = new ArrayList<String>();
		for(int i = 0;i<localIdStr.length;i++){
			if(!StringUtils.isEmpty(localIdStr[i])){
				conds +=" ?,";
				params.add(localIdStr[i]);
			}
		}
		if(StringUtils.isEmpty(conds)){
			return null;
		}
		sql += " AND  localId IN ("+conds.substring(0, conds.length()-1)+")";
		AppVo_1 vo = queryBean(AppVo_1.class, sql, params.toArray());
		if(null!=vo){
			return vo.getA1();
		}
		return null;
	}
}
