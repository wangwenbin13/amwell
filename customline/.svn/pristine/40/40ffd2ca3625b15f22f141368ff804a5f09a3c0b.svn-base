package com.amwell.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDataSource;
import com.amwell.commons.MyDate;
import com.amwell.commons.PropertyManage;
import com.amwell.commons.SqlBuilder;
import com.amwell.commons.StringUtil;
import com.amwell.dao.IMarketingDao;
import com.amwell.entity.MarketingModel;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.LineBusinessVo;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MessageLogVo;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.MsgTemplateVo;
import com.amwell.vo.PassengerInfoVo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.app.LineClassInfo;
import com.amwell.vo.app.PassengerInfo;

/**
 * 短信信息管理
 * @author Administrator
 *
 */
@Repository("marketingDao")
public class MarketingDaoImpl extends DaoSupport implements IMarketingDao {
	
	/* 
	 * 查询短信信息列表
	 * @see com.amwell.dao.IMarketingDao#getMarketingList(com.amwell.entity.Search, int, int)
	 */
	public Map<String, Object> getMarketingList(Search search,int currentPage, int pageSize){
		super.finit("sys_msg_info");
		System.out.println("MarketingDaoImpl.getMarketingList");
		Map<String,Object> map = new HashMap<String,Object>();
		//以下两行根据实际修改
		StringBuffer sql=new StringBuffer("select *,(select COUNT(1) FROM sys_msg_user users WHERE  users.msgId=info.msgId ) AS msgCount from sys_msg_info info where 1=1 and info.issend like '0' ");
	    List<Object> paramList = new ArrayList<Object>();
	    StringBuffer whereClause = new StringBuffer();
	    //查询条件，配置SQL语句
	    String msgContext=search.getField01();
	    if(msgContext!=null && !"".equals(msgContext)){
	    	whereClause.append(" and msgContext like ? ");
	    	paramList.add(SqlBuilder.getSqlLikeValue(msgContext.trim()));
	    }
	    String createOn = search.getField02();
	    if(createOn!=null && !"".equals(createOn)){
	    	whereClause.append("  and left(info.createOn,10) >=? ");
	    	paramList.add(createOn.trim());
	    }
	    String createOut = search.getField03();
	    if(createOut!=null && !"".equals(createOut)){
	    	whereClause.append(" and left(info.createOn,10) <=? ");
	    	paramList.add(createOut.trim());
	    }
	    String msgType=search.getField07();//消息类型 0:短信消息 1:软件站内消息 2：软件推送消息
	    whereClause.append(" and msgType like ? ");
	    paramList.add(msgType);
	    sql.append(whereClause);
	    Object[] params = paramList.toArray(new Object[]{});
	    sql.append("  order by info.createOn desc ");
	    
		tableDao.setPageSize(pageSize);
		List<MarketingModel> list = super.tableDao.queryByPage(MarketingModel.class, sql.toString(), currentPage,params);
		StringBuffer totalCountSql = new StringBuffer("select * from sys_msg_info info where 1=1 and info.issend like '0' ");
		totalCountSql.append(whereClause);
		Page page=new Page(list, totalCountSql.toString(), currentPage, pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}
	
	
	/**
	 * 发送短信消息
	 * @return
	 */
	public String addMessage(MarketingVo msg,List<String> telephones,String time){
		Connection conn=MyDataSource.getConnect();
//		int flag =-1;
		String msgId="";
		if(conn==null){
			throw new IllegalStateException("connection is null!");
		}
		if(msg==null){
			throw new IllegalAccessError("msgModel is null");
		}
		if(telephones.size() ==0){
			throw new IllegalAccessError("msgList is null");
		}
		try {
			conn.setAutoCommit(false);
			QueryRunner qr =new QueryRunner();
			msgId=StringUtil.generateSequenceNo();
			String msgSql="INSERT INTO sys_msg_info (msgId,msgType,theModule,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,updateBy,updateOn,initTime,issend,picUrl,orderDate,orderStartTime,lineName)  ";
			msgSql+=" VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    qr.update(conn, msgSql, new Object[]{msgId,msg.getMsgType(),msg.getTheModule(),msg.getMsgSubType(),msg.getMsgTiTLE(),msg.getMsgContext(),msg.getMsgTarget(),msg.getCreateBy(),msg.getCreateOn(),msg.getUpdateBy(),msg.getUpdateOn(),
					msg.getInitTime(),msg.getIssend(),msg.getPicUrl(),msg.getOrderDate(),msg.getOrderStartTime(),msg.getLineName()});
		    for(String phone : telephones){
				
				String useIdSql="select passengerId from passenger_info where telephone=? ";
				
				PassengerInfoVo vo = qr.query(conn, useIdSql, new BeanHandler<PassengerInfoVo>(PassengerInfoVo.class),new Object[]{phone});
//				System.out.println(vo.getPassengerId());
				if(vo==null){
					vo=new PassengerInfoVo();
					vo.setPassengerId("");
				}
				String userSql="INSERT INTO  sys_msg_user (localId,userId,msgId,readFlag,sendPhoneNo,sendTime) VALUE(?,?,?,?,?,?) ";
				qr.update(conn, userSql, new Object[]{StringUtil.generateSequenceNo(),vo.getPassengerId(),msgId,0,phone,time});
				
			}
			
			conn.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (Exception e2) {
				}
			}
		}finally{
			try {
				conn.close();
			} catch (Exception e2) {
			}
		}
		
		return msgId;
	}
	
	/**
	 * 添加短信
	 * @param marketingVo
	 * @return
	 */
	public String addMarketing(MarketingVo marketingVo){
//		int count=0;
//		super.finit("sys_msg_info");

//		count=tableDao.updateData(marketingVo, "msgId");
		String msgId = null;
		
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo msgUser = (SysAdminVo) httpSession.getAttribute("userInfo");//获取登录ID
		
		Connection conn=null;
		QueryRunner qr = new QueryRunner();
		int statu = -1;
		try {
			conn =MyDataSource.getConnect();
			if(conn==null){
				throw new RuntimeException("连接异常");
			}
			conn.setAutoCommit(false);
			msgId = StringUtil.generateSequenceNo();
			sql = " INSERT INTO sys_msg_info (msgId,theModule,msgType,msgSubType,msgTitle,msgContext,msgTarget,createBy,createOn,updateBy,updateOn,initTime,issend,picUrl,orderDate,orderStartTime,lineName)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			statu = qr.update(conn, sql, new Object[]{msgId,marketingVo.getTheModule(),marketingVo.getMsgType(),marketingVo.getMsgSubType(),marketingVo.getMsgTiTLE(),
					marketingVo.getMsgContext(),marketingVo.getMsgTarget(),marketingVo.getCreateBy(),marketingVo.getCreateOn(),marketingVo.getUpdateBy(),marketingVo.getUpdateOn(),
					marketingVo.getInitTime(),marketingVo.getIssend(),marketingVo.getPicUrl(),marketingVo.getOrderDate(),marketingVo.getOrderStartTime(),marketingVo.getLineName()});
			if(statu<=0){
				throw new RuntimeException("添加系统_消息短信管理记录失败");
			}
			sql = " INSERT INTO sys_log (logId,userId,userIp,action,operateTime,operateResult,sysType)  VALUES (?,?,?,?,?,?,?) ";
			statu = qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),msgUser.getUserId(),getIpAddr(),"短信管理",MyDate.getMyDateLong(),"添加短信成功","0"});
			conn.commit();
		}catch(Exception e){
			statu = 0;
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
						sql = " INSERT INTO sys_log (logId,userId,userIp,action,operateTime,operateResult,sysType)  VALUES (?,?,?,?,?,?,?) ";
						qr.update(conn, sql, new Object[]{StringUtil.generateSequenceNo(),msgUser.getUserId(),getIpAddr(),"短信管理",MyDate.getMyDateLong(),"退添加短信失败","0"});
						conn.commit();
					} catch (SQLException e1) {
						statu = -1;
						e1.printStackTrace();
					}
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
//		//添加日志
//		
//	if (count > 0) {
//		AppendLog(msgUser.getUserId(), "短信管理", "添加短信成功");
//	} else {
//		AppendLog(msgUser.getUserId(), "短信管理", "添加短信失败");
//	}
		return msgId;
	}
	
	/**
	 * 添加信息模板
	 * @param msgTemplateVo
	 * @return
	 */
	public String addSMSModer(MsgTemplateVo msgTemplateVo){
		super.finit("msg_template");
		tableDao.updateData(msgTemplateVo, "id");
		return null;
	}
	
	/**
	 * 记录发出的每条短信信息（发送的乘客、时间）
	 * @param messageLogVo
	 * @return
	 */
	public int showMarketingLog(MessageLogVo messageLogVo){
//		super.finit("sys_msg_user");
//		int count=0;
//		count =	tableDao.updateData(messageLogVo,"localId");
		Connection conn=null;
		QueryRunner qr = new QueryRunner();
		int statu = -1;
		try{
			conn =MyDataSource.getConnect();
			if(conn==null){
				throw new RuntimeException("连接异常");
			}
			conn.setAutoCommit(false);
			
			sql = " INSERT INTO sys_msg_user (localId,userId,msgId,readFlag,sendPhoneNo,sendTime)  VALUES (?,?,?,?,?,?) ";
			statu = qr.update(conn,sql,new Object[]{StringUtil.generateSequenceNo(),messageLogVo.getUserId(),messageLogVo.getMsgId(),messageLogVo.getReadFlag(),messageLogVo.getSendPhoneNo(),messageLogVo.getSendTime()});
			conn.commit();
		}catch(Exception e){
			statu = 0;
			e.printStackTrace();
			try {
				if(null!=conn && conn.isClosed()==false){
					try {
						conn.rollback();
					} catch (SQLException e1) {
						statu = -1;
						e1.printStackTrace();
					}
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
	 * 查询乘客列表（条件查询）
	 * @param search
	 * @param currentPageIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getPassengerList(String telephone,String classTime,String lineBase,String startDate){
		super.finit("line_base_info,line_class_info,line_business_order,passenger_info");
		Map<String, Object> map=new HashMap<String, Object>();
//		StringBuffer sql=new StringBuffer("SELECT p2.* FROM passenger_info p2,( SELECT DISTINCT p.telephone FROM passenger_info p LEFT JOIN line_business_order bOrder ON p.passengerId = bOrder.passengerId LEFT JOIN line_class_info lc ON bOrder.lineClassId = lc.lineClassId LEFT JOIN line_base_info lb ON lc.lineBaseId = lb.lineBaseId    WHERE 1 = 1   ");
		StringBuffer sql=new StringBuffer("SELECT p2.* FROM passenger_info p2,( SELECT DISTINCT p.telephone FROM passenger_info p LEFT JOIN line_business_order bOrder ON p.passengerId = bOrder.passengerId LEFT JOIN line_class_info lc ON bOrder.lineClassId = lc.lineClassId LEFT JOIN line_base_info lb ON lc.lineBaseId = lb.lineBaseId    WHERE 1 = 1    ");
		List<Object> parameters=new ArrayList<Object>();
		if(telephone!=null && !"".equals(telephone)){
			sql.append(" and p.telephone like ? ");
			parameters.add(SqlBuilder.getSqlLikeValue(telephone).trim());
		}
		if(classTime!=null && !"".equals(classTime)){
			sql.append(" and lc.orderStartTime like ? ");
			parameters.add(classTime);
		}
		if(lineBase!=null && !"".equals(lineBase)){
			sql.append(" and lb.lineName like ? ");
			parameters.add(lineBase);
		}
		if(startDate!=null && !"".equals(startDate)){
			sql.append(" and lc.orderDate like ?");
			parameters.add(startDate);
		}
		sql.append("  ) t WHERE t.telephone = p2.telephone ");
		Object[] params=parameters.toArray(new Object[]{});
		List<PassengerInfo> list=super.tableDao.queryList(PassengerInfo.class, sql.toString(), params);
		map.put("list", list);
		return map;

	}
	
	
	/**
	 * 通过电话号码查询乘客信息
	 * @param telephone
	 * @return
	 */
	public PassengerInfo getPassengerId(String telephone){
		super.finit("passenger_info");
		String sql="select * from passenger_info where telephone = '"+telephone+"'";
		PassengerInfo passengerInfo=tableDao.queryBean(PassengerInfo.class, sql);
		return passengerInfo;
	}
	

	
	
	
	/**
	 * 查询短信模板列表
	 * @return
	 */
	public Map<String, Object> msgtemplateModelList(int templateType){
		super.finit("msg_template");
		Map<String, Object> map=new HashMap<String, Object>();
		String sql="SELECT m.id,m.template_name,m.template_type,m.template_content,m.template_status,a.userName AS createBy,m.createOn,m.updateBy,m.updateOn,m.remark  " +
				"FROM msg_template m , sys_admin a WHERE m.createBy = a.userId  AND m.template_type = "+templateType+" ORDER BY m.createOn DESC";
		List<MsgTemplateVo> list=super.tableDao.queryList(MsgTemplateVo.class, sql);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 短信详情信息
	 * @param msgId
	 * @return
	 */
	public MarketingModel marketingDetail(String msgId){
		super.finit("sys_msg_user,sys_msg_user,passenger_info");
		String sql=" SELECT info.createBy,info.picUrl,info.msgContext,(SELECT COUNT(1) FROM sys_msg_user users WHERE  users.msgId=info.msgId ) AS msgCount ,info.createOn,info.orderDate,info.orderStartTime,info.lineName,info.theModule,info.msgTarget FROM sys_msg_info info  WHERE 1=1   ";
		sql+=" and info.`msgId`= '"+msgId+" ' ";
		System.out.println(sql);
		MarketingModel msgModel=super.tableDao.queryBean(MarketingModel.class, sql);
		String phoneUrl = PropertyManage.getInfoProperty("http.pic.ip");
		if(msgModel.getPicUrl()!=null && !"".equals(msgModel.getPicUrl())){
			phoneUrl=phoneUrl+"/"+msgModel.getPicUrl();
			msgModel.setPicUrl(phoneUrl);
		}
		if(msgModel.getMsgTarget()==0){
			String sqlUsers="SELECT passenger.`nickName`,passenger.`sex`,msg.`sendPhoneNo`  FROM sys_msg_user msg LEFT JOIN passenger_info passenger ON msg.`userId`=passenger.`passengerId` WHERE 1=1  ";
			sqlUsers+=" and msg.`msgId`= '"+msgId+" ' ";
			List<PassengerInfoVo> passengerList=super.tableDao.queryList(PassengerInfoVo.class, sqlUsers);
			msgModel.setPassengerList(passengerList);
		}else {
			String sqlBusin="SELECT * FROM `mgr_business` AS bus LEFT JOIN `sys_msg_user` AS msg ON bus.contactsPhone=msg.sendPhoneNo WHERE msg.msgId = ?";
			List<MgrBusinessEntity> businessList=super.tableDao.queryList(MgrBusinessEntity.class, sqlBusin, new Object[]{msgId});
			msgModel.setBusinessList(businessList);
		}
		return msgModel;
	}
	
	/**
	 * 查询所有班次时刻
	 * @return
	 */
	public Map<String, Object> getClassList(){
		super.finit("line_class_info");
		Map<String, Object> maps = new HashMap<String, Object>();
		String sql="SELECT DISTINCT orderStartTime FROM line_class_info where 1=1 and delFlag=0 order by orderStartTime desc";
		List<LineClassInfo> list=super.tableDao.queryList(LineClassInfo.class, sql);
		maps.put("list", list);
		
		return maps;
	}
	
	

	/* 
	 * 查询消息模板详情
	 */
	public MsgTemplateVo queryMsgTemplateVo(String id, int templeateType) {
		super.finit("msg_template");
		
		String sql="SELECT m.id,m.template_name,m.template_type,m.template_content,m.template_status,createBy,m.createOn,m.updateBy,m.updateOn,m.remark  " +
				"FROM msg_template m  WHERE m.id='"+id+"'  AND m.template_type = "+templeateType;
		
		return tableDao.queryBean(MsgTemplateVo.class, sql);
	}
	
	/**
	 * 删除消息模板
	 */
	public String deleteSMSModer(String id, int type) {
		super.finit("msg_template");
		MsgTemplateVo vo = new MsgTemplateVo();
		vo.setTemplate_status(0);
		vo.setId(id);
		int i = tableDao.updateData(vo,"id");
		return i+"";
	}
	

}
