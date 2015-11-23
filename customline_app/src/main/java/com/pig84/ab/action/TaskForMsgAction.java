package com.pig84.ab.action;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.service.ITaskForMsgService;

/**
 * 消息短信发送任务
 * @author Administrator
 *
 */
@ParentPackage("no-interceptor")
@Namespace("/app_MsgTask")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class TaskForMsgAction extends BaseAction {
	
	@Autowired
	private ITaskForMsgService task;
	
	/**
	 * 任务调度
	 */
	public void run(){
		String result = task.doJob();
	}
	
	
	/**
	 * 发送消息 （0：失败      1 ：成功 ）
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "sendMsg",results={@Result(type="json")})
	public String MsgTask() throws IOException{
		String msgId = request.getParameter("msgId"); //消息ID
		String result = task.doTaskById(msgId);
		write(result);
		return null;
	}
	
	
	/**
	 * 发送招募发起人信息--招募申请通过(0：失败      1 ：成功 )
	 */
	@Action(value="sendRelateMsg",results={@Result(type="json")})
	public String sendRelateMsg() throws IOException{
		String applicationId = request.getParameter("applicationId");//申请线路ID
		String auditStatus = request.getParameter("auditStatus");//修改前的状态---审核状态；0:未审核 1:已审核 2:关闭
		String msgContext = "";
		if("1".equals(auditStatus )){
			/**您申请的线路被管理员关闭了**/
			msgContext = "1";
		}else if("0".equals(auditStatus) || "2".equals(auditStatus)){
			/**您申请的线路已经同过审核**/
			msgContext = "2";
		}
		String result = task.sendApplicationMessage(applicationId,msgContext);
		write(result);
		return null;
	}
	
}
