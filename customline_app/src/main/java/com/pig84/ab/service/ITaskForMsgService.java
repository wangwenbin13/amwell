package com.pig84.ab.service;

public interface ITaskForMsgService {
	
	/**根据消息ID发送消息**/
	public String doTaskById(String msgId);
	
	/**任务调度**/
	public String doJob();

	/**给招募线路发起人发送信息**/
	public String sendApplicationMessage(String id,String msgContext);
}
