package com.amwell.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IHelpDao;
import com.amwell.entity.Search;
import com.amwell.service.IHelpService;
import com.amwell.vo.HelpCentryEntity;
import com.amwell.vo.HelpFeedbackVo;
import com.amwell.vo.UserAgreementVo;

/**
 * 帮助&反馈
 * @author Administrator
 *
 */
@Service("helpService")
public class HelpServiceImpl implements IHelpService {
	
	@Autowired
	private IHelpDao helpDao;
	
	/**
	 * 查询帮助反馈信息列表
	 */
	public Map<String, Object> getFeedbackList(Search search,int currentPage, int pageSize) {
		
		return helpDao.getFeedbackList(search,currentPage,pageSize);
	}
	
	
	/**
	 * 查询帮助反馈信息的详情
	 */
	public Map<String, Object> getFeedbackInfo(String feedbackId){
		return helpDao.getFeedbackInfo(feedbackId);
	}

	
	/**
	 * 更新反馈处理
	 * @param helpFeedbackVo
	 * @return
	 */
	public boolean updateHandleFeedback(HelpFeedbackVo helpFeedbackVo) {
		// TODO Auto-generated method stub
		return helpDao.updateHandleFeedback(helpFeedbackVo);
	}


	/**
	 * 获取帮助实体详情
	 */
	public HelpCentryEntity getHelpCentry(HelpCentryEntity helpCentryEntity) {
		return helpDao.getHelpCentry(helpCentryEntity);
	}


	/**
	 * 添加/修改帮助信息
	 */
	public int addUpdateHelpCentry(HelpCentryEntity helpCentryEntity) {
		return helpDao.addUpdateHelpCentry(helpCentryEntity);
	}

	
	/**
	 * 获得所有的用户协议
	 */
	public List<UserAgreementVo> getAllUserAgreement() {
		
		return helpDao.getAllUserAgreement();
	}

	/**
	 * 修改用户协议(根据协议ID)
	 */
	public UserAgreementVo saveUserAgreement(UserAgreementVo userAgreementVo) {

		return helpDao.saveUserAgreement(userAgreementVo);
	}

	/**
	 * 根据协议标题/或ID查询协议
	 */
	public UserAgreementVo getAllUserAgreementByCondition(UserAgreementVo userAgreementVo) {
		return helpDao.getAllUserAgreementByCondition(userAgreementVo);
	}

}
