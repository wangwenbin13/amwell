package com.amwell.dao;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.HelpCentryEntity;
import com.amwell.vo.HelpFeedbackVo;
import com.amwell.vo.UserAgreementVo;

/**
 * 帮助&反馈
 * @author Administrator
 *
 */
public interface IHelpDao {

	
	/**
	 * 查询帮助反馈信息列表
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getFeedbackList(Search search,
			int currentPage, int pageSize);
	
	
	/**
	 * 查询帮助反馈信息的详情
	 * @param feedbackId
	 * @return
	 */
	Map<String, Object> getFeedbackInfo(String feedbackId);

	/**
	 * 更新反馈处理
	 * @param helpFeedbackVo
	 * @return
	 */
	boolean updateHandleFeedback(HelpFeedbackVo helpFeedbackVo);


	/**
	 * 获取帮助实体详情
	 * @param helpCentryEntity
	 * @return
	 */
	HelpCentryEntity getHelpCentry(HelpCentryEntity helpCentryEntity);


	/**
	 * 添加/修改帮助信息
	 * @param helpCentryEntity
	 * @return
	 */
	int addUpdateHelpCentry(HelpCentryEntity helpCentryEntity);

	/**
	 * 获得所有的协议
	 * @return
	 */
	List<UserAgreementVo> getAllUserAgreement();
	
	/**
	 * 修改用户协议(根据协议ID)
	 * @param userAgreementVo
	 * @return
	 */
	UserAgreementVo saveUserAgreement(UserAgreementVo userAgreementVo);

	/**
	 * 根据协议标题查询协议
	 * @param agreementTitle
	 * @return
	 */
	UserAgreementVo getAllUserAgreementByCondition(UserAgreementVo userAgreementVo);

}
