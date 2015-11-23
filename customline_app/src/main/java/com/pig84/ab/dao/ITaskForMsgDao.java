package com.pig84.ab.dao;

public interface ITaskForMsgDao {

	/**根据消息ID发送消息**/
	public String doTaskById(String msgId);
	
	/**任务调度**/
	public String doJob();
}
