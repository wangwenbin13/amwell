package com.pig84.ab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pig84.ab.dao.ILineDao;
import com.pig84.ab.dao.ILoginAndRegisterDao;
import com.pig84.ab.dao.ITaskForMsgDao;
import com.pig84.ab.service.ITaskForMsgService;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.LineUserApplication;
import com.pig84.ab.vo.SysMsgInfo;
import com.pig84.ab.vo.SysMsgUser;
import com.pig84.ab.vo.bean.AppVo_2;

@Service("taskForMsgService")
public class TaskForMsgServiceImpl implements ITaskForMsgService{

	@Autowired
	private ITaskForMsgDao taskDao;
	
	@Autowired
	private ILineDao lineDao;
	
	@Autowired
	private ILoginAndRegisterDao loginAndRegisterDao;
	
	
	
	/**根据消息ID发送消息**/
	public String doTaskById(String msgId){
		return taskDao.doTaskById(msgId);
	}
	
	/**任务调度**/
	public String doJob(){
		return taskDao.doJob();
	}
	
	/**
	 * 给招募线路发起人发送信息
	 */
	public String sendApplicationMessage(String applicationId,String type){
		String result = "0";
		try {
			/**
			 * 给线路招募发起人发送一条站内信息
			 * 1,找出线路发起人的信息
			 */
			AppVo_2 appvo = lineDao.getApplicationPassenger(applicationId);
			/**
			 * 获取招募信息
			 */
			LineUserApplication lineUserApplication = getLineUserApplication(applicationId);
			
			/**
			 * 先找出报名该线路的所有乘客
			 */
			List<AppVo_2> appVo_2s = lineDao.getAllAppliTelephoneByLineId(applicationId);
			
			/**
			 * type 
			 * 0:新申请
			 * 1:被关闭--未通过
			 * 2:通过申请
			 * 3:有人报名
			 */
			String title ="";
			String msgContext = "";
			if("0".equals(type)){
				title = "待审核";
				msgContext = "已提交，目前正在审核中。我们将尽快完成审核，请稍后。";
			}else if("1".equals(type)){
				title = "审核未通过";
				String mes = lineUserApplication.getRemark()==null?"无":lineUserApplication.getRemark();
				msgContext = "，审核未通过！驳回原因："+mes;
			}else if("2".equals(type)){
				title = "审核通过";
				msgContext = "已审核通过，正在等待其他用户报名。若此线路有其他用户报名，我们将在第一时间将该信息反馈给您，请留意。";
			}else if("3".equals(type)){
				msgContext = "，目前已报名人数为"+appVo_2s.size()+"人。";
			}
			SysMsgInfo msgInfo = new SysMsgInfo();
			msgInfo = getSysMsgInfo(msgInfo);
			msgInfo.setMsgContext("尊敬的用户，您于"+lineUserApplication.getApplicationTime()+
					"所发布的"+lineUserApplication.getStartAddress()+"至"+lineUserApplication.getEndAddress()+"，"+lineUserApplication.getStartTime()+"发车的线路"+msgContext);
			msgInfo.setMsgTitle("个人招募（"+lineUserApplication.getStartAddress()+"-"+lineUserApplication.getEndAddress()+"线路 "+title+"）");
			int flag = loginAndRegisterDao.addSysMsgInfo(msgInfo);
			if(1==flag){
				//添加成功
				SysMsgUser msgUser = getSysMsgUser(msgInfo.getMsgId(),appvo.getA1(),appvo.getA2());
				loginAndRegisterDao.addSysMsgUser(msgUser);
				
				/**推送站内信息**/
				result = taskDao.doTaskById(msgInfo.getMsgId());
			}
			return result;
		} catch (Exception e) {
		}
		return result;
	}
	
	
	/**
	 * 组装SysMsgInfo实体类
	 * @param sysMsgInfo
	 * @return
	 */
	private SysMsgInfo getSysMsgInfo(SysMsgInfo sysMsgInfo){
		sysMsgInfo.setMsgType("1");//软件消息
		sysMsgInfo.setMsgSubType("0");//系统消息
		sysMsgInfo.setMsgTarget("0");//发送对象:乘客
		sysMsgInfo.setCreateBy("SYSTEM");
		sysMsgInfo.setCreateOn(MyDate.Format.DATETIME.now());
		sysMsgInfo.setIssend("1");
		return sysMsgInfo;
	}
	
	/**
	 * 组装SysMsgUser实体类
	 * @param msgId 信息ID
	 * @param passengerId 乘客ID
	 * @param telephone 手机号码
	 * @return
	 */
	private SysMsgUser getSysMsgUser(String msgId,String passengerId,String telephone){
		SysMsgUser sysMsgUser = new SysMsgUser();
		sysMsgUser.setMsgId(msgId);
		sysMsgUser.setUserId(passengerId);
		sysMsgUser.setSendPhoneNo(telephone);
		sysMsgUser.setReadFlag("0");//未读
		sysMsgUser.setSendTime(MyDate.Format.DATETIME.now());
		return sysMsgUser;
	}
	
	/**
	 * 获取招募线路信息
	 */
	private LineUserApplication getLineUserApplication(String applicationId){
		return lineDao.getLineUserApplication(applicationId);
	}
	
}
