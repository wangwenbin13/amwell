package com.amwell.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.commons.PostHttpClient;
import com.amwell.commons.PropertyManage;
import com.amwell.dao.IMarketingDao;
import com.amwell.entity.MarketingModel;
import com.amwell.entity.Search;
import com.amwell.service.IMarketingService;
import com.amwell.vo.MarketingVo;
import com.amwell.vo.MessageLogVo;
import com.amwell.vo.MsgTemplateVo;
import com.amwell.vo.app.PassengerInfo;


@Service ("marketingService")
public class MarketingServiceImpl implements IMarketingService {
	
	private static Logger logger = Logger.getLogger(MarketingServiceImpl.class);
	
	@Autowired
	private IMarketingDao iMarketingDao;
	
	
	/* 
	 * 查询短信消息列表
	 * 
	 */
	public Map<String, Object> getMarketingList(Search search,int currentPage, int pageSize){
		return iMarketingDao.getMarketingList(search, currentPage, pageSize);
	}

	
	/**
	 * 条件查询乘客信息列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getPassengerList(String telephone,String classTime,String lineBase,String startDate){
		return iMarketingDao.getPassengerList(telephone, classTime, lineBase, startDate);
	}
	
	/* 
	 * 
	 *添加短信
	 */
	public String addMarketing(MarketingVo marketingVo){
		return iMarketingDao.addMarketing(marketingVo);
	}
	
	/**
	 * 记录发出的每条短信信息（发送的乘客、时间）
	 * @param messageLogVo
	 * @return
	 */
	public int showMarketingLog(MessageLogVo messageLogVo){
		return iMarketingDao.showMarketingLog(messageLogVo);
	}
	
	/* 
	 * 通过电话号码查询乘客信息
	 * @param telephone
	 * @return
	 */
	public PassengerInfo getPassengerId(String telephone){
		return iMarketingDao.getPassengerId(telephone);
	}
	
	/* 
	 *添加短信模板
	 */
	public String addSMSModer(MsgTemplateVo msgTemplateVo){
		return iMarketingDao.addSMSModer(msgTemplateVo);
		
	}
	
	/* 
	 * 查询短信模板列表
	 */
	public Map<String, Object> msgtemplateModelList(int templateType){
		return iMarketingDao.msgtemplateModelList(templateType);
	}
	
	/* 
	 * 查询短信详情信息
	 */
	public MarketingModel marketingDetail(String msgId){
		return iMarketingDao.marketingDetail(msgId);
	}
	
	/* 
	 * 查询所有班次列表
	 */
	public Map<String, Object> getClassList(){
		return iMarketingDao.getClassList();
	}	
	
	/**
	 * 模板
	 */
	public MsgTemplateVo queryMsgTemplateVo(String id, int templeateType) {
		return iMarketingDao.queryMsgTemplateVo(id, templeateType);
	}
	
	/**
	 * 根据ID和类型删除模板
	 */
	public String deleteSMSModer(String id, int type) {
		return iMarketingDao.deleteSMSModer(id,type);
	}
	
	/**
	 * 发送短信消息
	 * @return
	 */
	public String addMessage(MarketingVo msg,List<String> telephones,String time){
		return iMarketingDao.addMessage(msg, telephones, time);
	}

	/**
	 * 发送短信，APP消息(传参为信息ID)
	 * 
	 */
	public int sendAPPMsg(String msgId){
		int flag=-1;
		String phoneUrl = PropertyManage.getInfoProperty("app_back_root")+"app_MsgTask/sendMsg.action";
		String result = PostHttpClient.sendMsgAndJpush(phoneUrl, msgId);
		System.out.println("result ="+result);
		if(result==null){
			return flag;
		}
		boolean state = result.matches("[0-9]+");
		if(state){
			flag=1;
		}
		return flag;
	}

}
