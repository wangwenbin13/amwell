package com.amwell.dao;

import java.util.List;
import java.util.Map;

import com.amwell.entity.MarketingModel;
import com.amwell.entity.Search;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MessageLogVo;
import com.amwell.vo.MsgTemplateVo;
import com.amwell.vo.app.PassengerInfo;

public interface IMarketingDao {
	
	/**
	 * 查询短信信息列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getMarketingList(Search search,int currentPage, int pageSize);
	
	/**
	 * 查询乘客信息列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getPassengerList(String telephone,String classTime,String lineBase,String startDate);
	
	/**
	 * 添加短信
	 * @param marketing
	 * @return
	 */
	String addMarketing(MarketingVo marketing);

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
	 * 通过班次查询乘客信息
	 * @param lineClass
	 * @return
	 */
//	Map<String, Object> orderPassengerInfo (Search search,int currentPage, int pageSize);
	
	/**
	 * 添加短信模板
	 * @param msgTemplateVo
	 * @return
	 */
	String addSMSModer(MsgTemplateVo msgTemplateVo);
	
//	/**
//	 * 查询当天乘客信息
//	 * @param date
//	 * @return
//	 */
//	Map<String, Object> datePassengerListInfo(String date);
	
	/**
	 * 查询短信模板列表
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
	 * 查找模板类型
	 * @param id
	 * @param templeateType
	 * @return
	 */
	MsgTemplateVo queryMsgTemplateVo(String id, int templeateType);
	
	/**
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	String deleteSMSModer(String id, int type);
	
	/**
	 * 发送短信消息
	 * @return
	 */
	String addMessage(MarketingVo msg,List<String> telephones,String time);
	
}
