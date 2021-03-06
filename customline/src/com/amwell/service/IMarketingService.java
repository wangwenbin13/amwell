package com.amwell.service;

import java.util.List;
import java.util.Map;

import com.amwell.entity.MarketingModel;
import com.amwell.entity.Search;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MessageLogVo;
import com.amwell.vo.MsgTemplateVo;
import com.amwell.vo.app.PassengerInfo;

/**
 * 短信信息模块
 * @author Administrator
 *
 */
public interface IMarketingService {
	/**
	 * 查询短信信息列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getMarketingList(Search search,int currentPage, int pageSize);
	
	/**
	 * 条件查询乘客信息列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getPassengerList(String telephone,String classTime,String lineBase,String startDate);
	
	/**
	 * 添加短信
	 * @param marketingVo
	 * @return
	 */
	String addMarketing(MarketingVo marketingVo);
	
	/**
	 * 记录发出的每条短信信息（发送的乘客、时间）
	 * @param messageLogVo
	 * @return
	 */
	int showMarketingLog(MessageLogVo messageLogVo);

	
	/**
	 * 通过电话号码查询乘客信息
	 * @param telephone
	 * @return
	 */
	PassengerInfo getPassengerId(String telephone);
	
	/**
	 * 添加短信模板
	 * @param msgTemplateVo
	 * @return
	 */
	String addSMSModer(MsgTemplateVo msgTemplateVo);
	
	/**
	 * 查询短信模板列表
	 * @param  
	 * @return
	 */
	Map<String, Object> msgtemplateModelList(int templateType);
	
	/**
	 * 短信详情信息
	 * @param msgId
	 * @return
	 */
	MarketingModel marketingDetail(String msgId);
	
	/**
	 * 查询所有班次列表
	 * @return
	 */
	Map<String, Object> getClassList();
	/**
	 * 根据模板ID和类型查找
	 * @param id
	 * @param i
	 * @return
	 */
	MsgTemplateVo queryMsgTemplateVo(String id, int i);
	
	/**
	 * 删除短信模板
	 * @param id
	 * @param type
	 * @return
	 */
	String deleteSMSModer(String id,int type);
	
	/**
	 * 发送短信消息
	 * @return
	 */
	String addMessage(MarketingVo msg,List<String> telephones,String time);
	
	int sendAPPMsg(String msgId);
	
}
