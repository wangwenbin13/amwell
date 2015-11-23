package com.pig84.ab.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ITaskForMsgDao;
import com.pig84.ab.utils.JPush;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.SysMsgInfo;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_4;

@Repository
public class TaskForMsgDaoImpl extends BaseDao implements ITaskForMsgDao {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**任务调度**/
	public String doJob(){
		String sql = "SELECT * FROM sys_msg_info WHERE issend = '1' AND initTime IS NOT NULL AND initTime <> '' AND initTime <> '0'";
		List<SysMsgInfo> vo = queryList(SysMsgInfo.class, sql);
		
		if(vo!=null && vo.size()!=0){
			
			for (int i = 0; i < vo.size(); i++) {
				
				String nowTime = MyDate.Format.DATETIME.now();
				int temp = MyDate.compare_date_time(nowTime,vo.get(i).getInitTime());
				if(temp==1){
					logger.debug("自动信息发送开始");
					String flag = doTaskById(vo.get(i).getMsgId());
					if(!"1".equals(flag)){
						logger.warn("自动信息发送失败，消息ID: {}", vo.get(i).getMsgId());
					}
				}
				
			}
		}
		
		return null;
	}
	
	/**
	 * 根据消息id发送消息
	 */
	public String doTaskById(String msgId) {
		
		//消息内容
		String sql = "select * from sys_msg_info where msgId = ? and issend = '1'";
		Object[] params = new Object[1];
		params[0] = msgId;
		SysMsgInfo msg = queryBean(SysMsgInfo.class, sql,params);
		
		
		//发送对象
		if(msg!=null){
			String sql_u = "select sendPhoneNo as a1 from sys_msg_user where msgId = ?";
			params = new Object[1];
			params[0] = msgId;
			List<AppVo_1> No = queryList(AppVo_1.class, sql_u,params);
			
			String mobile = "";
			
			if(No!=null && No.size()!=0){
				for (int i = 0; i < No.size(); i++) {
					mobile += No.get(i).getA1()+",";
				}
				
				mobile = mobile.substring(0,mobile.length()-1);
			
			
			if("0".equals(msg.getMsgType())){//短信消息
					String temp[] = mobile.split(",");
					String a = "";
					for (int i = 0; i < temp.length; i++) {
						a += temp[i] + ",";
						if (a.split(",").length == 200) {
							new Message(msg.getMsgContext()).sendTo(a);
							a = "";
						} else if (a.split(",").length < 200 && i+1 == temp.length) {
							new Message(msg.getMsgContext()).sendTo(a);
						}
					}
					
					//发送成功，修改状态为已发送
					String sql_s = "update sys_msg_info set issend = '0' where msgId = ?";
					params = new Object[1];
					params[0] = msgId;
					executeSQL(sql_s,params);
					return "1";
				}else if("1".equals(msg.getMsgType())){//站内消息
					String url=PropertyManage.get("http.root.url");
					AppVo_4 vo = new AppVo_4();
					vo.setA1(msg.getMsgTitle());//标题
					vo.setA2(msg.getMsgContext());//消息正文
					if(msg.getPicUrl()!=null && !"".equals(msg.getPicUrl())){
						vo.setA3(url+"/"+msg.getPicUrl());//图片URL
					}else{
						vo.setA3("");
					}
					vo.setA4(msg.getMsgId());//消息ID
					
					String temp[] = mobile.split(",");
					String a = "";
					for (int i = 0; i < temp.length;i++) {
						a+=temp[i]+",";
						if(a.split(",").length==200){
							JPush.sendMessage(vo, a.split(","));
							a = "";
						}else if(a.split(",").length<200 && i+1 == temp.length){
							JPush.sendMessage(vo, a.split(","));
						}
					}
					
					//发送成功，修改状态为已发送
					String sql_s = "update sys_msg_info set issend = '0' where msgId = ?";
					params = new Object[1];
					params[0] = msgId;
					executeSQL(sql_s,params);
					
					return "1";
				}else{ //推送消息
					try {
						
						
						String temp[] = mobile.split(",");
						String a = "";
						for (int i = 0; i < temp.length;i++) {
							a+=temp[i]+",";
							if(a.split(",").length==200){
								JPush.alert(msg.getMsgContext(), a.split(","));
								a = "";
							}else if(a.split(",").length<200 && i+1 == temp.length){
								JPush.alert(msg.getMsgContext(), a.split(","));
							}
						}
						
						
						
						//发送成功，修改状态为已发送
						String sql_s = "update sys_msg_info set issend = '0' where msgId = ?";
						params = new Object[1];
						params[0] = msgId;
						executeSQL(sql_s,params);
						
						return "1";
					} catch (Exception e) {
						//e.printStackTrace();
					}
				}
			}
		}
		
		return "0";
	}
	
}
