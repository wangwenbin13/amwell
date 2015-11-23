package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.IHelpDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.HelpCentryEntity;
import com.amwell.vo.HelpFeedbackVo;
import com.amwell.vo.UserAgreementVo;

/**
 * 帮助&反馈
 * @author Administrator
 *
 */
@Repository("helpDao")
public class HelpDaoImpl extends DaoSupport implements IHelpDao {

	/* 
	 *查询帮助反馈信息列表
	 */
	public Map<String, Object> getFeedbackList(Search search,int currentPage, int pageSize) {
		System.out.println("HelpDaoImpl.getFeedbackList");
		Map<String,Object> map = new HashMap<String,Object>();
		
		super.finit("passenger_help_feedback help left join passenger_info passenger on help.passengerId=passenger.passengerId ");
		String cond=" where 1=1 ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!=null){
			if(search.getField01()!=null && !"".equals(search.getField01())){
				cond+=" and left(help.feedbackTime,10) >=? ";
				paramList.add(search.getField01());
			}
			if(search.getField02()!=null && !"".equals(search.getField02())){
				cond+= " and left(help.feedbackTime,10) <=? ";
				paramList.add(search.getField02());
			}
			if(search.getField03()!=null && !"".equals(search.getField03())){
				cond+=" and passenger.nickName like ? ";
//				paramList.add(search.getField03());
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!=null && !"".equals(search.getField04())){
				cond+=" and passenger.telephone like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			
			if(search.getField05() != null && !"".equals(search.getField05())){
				cond +=" and help.status = ?";
				paramList.add(search.getField05());
			}
		}
		
			Object[] params = paramList.toArray(new Object[]{});
			tableDao.setPageSize(pageSize);
			
			String sql = " select help.feedbackId,help.feedbackTime,passenger.displayId,help.feedbackContext,passenger.nickName ," +
					"passenger.telephone,help.phoneNo,help.handleFeedbackingRemark,help.handleFeedbackendRemark," +
					"u.userName as handleUser ," +
					"help.handleTime,help.status  ";
			sql+=" from passenger_help_feedback help left join passenger_info passenger on help.passengerId = passenger.passengerId left join sys_admin u on help.handleUser = u.userId ";
			sql+=cond;
			sql+=" order by help.feedbackTime desc  ";
			
			list=tableDao.queryByPage(HelpFeedbackVo.class, sql, currentPage,params);
			page=new Page(list, sql, currentPage,pageSize, params);
			
			map.put("list", list);
			map.put("page", page);
			
			
		return map;
		

	}
	
	/* 
	 *查询帮助反馈信息的详情
	 */
	public Map<String, Object> getFeedbackInfo(String feedbackId){
		
		super.finit("passenger_help_feedback help left join passenger_info passenger on help.passengerId=passenger.passengerId ");
		HelpFeedbackVo model=new HelpFeedbackVo();
		
		
		String sql="select help.feedbackId,help.feedbackTime,help.feedbackContext,passenger.nickName,passenger.telephone,help.handleFeedbackingRemark," +
				"help.handleFeedbackendRemark,help.handleUser,help.handleTime,help.status,help.middleHandleUser,help.middleHandleTime ,u.userName as handleUser ";
		sql+="  from passenger_help_feedback help left join passenger_info passenger on help.passengerId=passenger.passengerId " +
				"left join sys_admin u on help.handleUser = u.userId ";
		String cond = "  where 1=1  ";
		if(feedbackId!=null && !"".equals(feedbackId)){
			cond+="  and help.feedbackId  =  ";
			cond+= "'"+feedbackId+"'";
		}
		sql+=cond;
		model.setFeedbackId(feedbackId);
		list=tableDao.queryList(HelpFeedbackVo.class, sql, new Object[]{});
		map.put("list", list);
		return map;
	}
	
	/**
	 * 更新反馈处理
	 * @param helpFeedbackVo
	 * @return
	 */
	public boolean updateHandleFeedback(HelpFeedbackVo helpFeedbackVo) {
		super.finit("passenger_help_feedback");
		tableDao.updateDataForKeys(helpFeedbackVo, "feedbackId");
		return true;
	}

	/**
	 * 获取帮助实体详情
	 */
	public HelpCentryEntity getHelpCentry(HelpCentryEntity helpCentryEntity) {
		super.finit("help_center");
		return tableDao.queryBeanById(HelpCentryEntity.class, helpCentryEntity.getHelpType().toString(), "helpType");
	}

	/**
	 * 添加/修改帮助信息
	 */
	public int addUpdateHelpCentry(HelpCentryEntity helpCentryEntity) {
		super.finit("help_center");
		return tableDao.updateData(helpCentryEntity, "helpId");
	}
	
	/**
	 * 获得所有的用户协议
	 */
	public List<UserAgreementVo> getAllUserAgreement() {
		super.finit("user_agreement");
		String sql = "select * from user_agreement order by agreementUpdateOn desc";
		return tableDao.queryList(UserAgreementVo.class, sql, new Object[]{});
	}
	
	/**
	 * 修改用户协议(根据协议ID)
	 */
	public UserAgreementVo saveUserAgreement(UserAgreementVo userAgreementVo) {
		super.finit("user_agreement");
		/*if(!"".equals(userAgreementVo.getAgreementId())){
			String sql = "select * from user_agreement where agreementId ='"+userAgreementVo.getAgreementId()+"'";
			UserAgreementVo vo = tableDao.queryBean(UserAgreementVo.class, sql);
			userAgreementVo.setAgreementTitle(vo.getAgreementTitle());
		}*/
		int reStauts = tableDao.updateData(userAgreementVo, "agreementId");
		if(reStauts == 1){
			return userAgreementVo;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据协议标题查询协议
	 * @param agreementTitle
	 * @return
	 */
	public UserAgreementVo getAllUserAgreementByCondition(UserAgreementVo userAgreementVo) {
		super.finit("user_agreement");
		
		String sql = "select * from user_agreement where 1 = 1 ";
		if(null != userAgreementVo.getAgreementId() && !"".equals(userAgreementVo.getAgreementId())){
			sql += " and agreementId = '"+userAgreementVo.getAgreementId()+"' ";
		}
		if(null != userAgreementVo.getAgreementTitle() && !"".equals(userAgreementVo.getAgreementTitle())){
			sql += " and agreementTitle = '"+userAgreementVo.getAgreementTitle()+"'";
		}
		
		List<UserAgreementVo> agreements = tableDao.queryList(UserAgreementVo.class, sql, new Object[]{});
		if(null != agreements && agreements.size()>0){
			return agreements.get(0);
		}else{
			return null;
		}
	}

}
