package com.amwell.action.help;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.Message;
import com.amwell.commons.Message.Provider;
import com.amwell.commons.MyDate;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IHelpService;
import com.amwell.service.IMarketingService;
import com.amwell.vo.HelpFeedbackVo;
import com.amwell.vo.SysAdminVo;
/**
 * 帮助反馈Action类
 */
@ParentPackage("user-finit")
@Namespace("/help")
@SuppressWarnings("rawtypes")

//@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })

public class HelpAction extends BaseAction {

	
	private static final long serialVersionUID = -4475423716783464455L;

	private static final Logger log = Logger.getLogger(HelpAction.class);
	
	@Autowired 
	private IHelpService helpService;//注入业务类
	
	@Autowired
	private IMarketingService marketingService;

	/**
	 * 列表查询对象
	 */
	private FeedbackSearch feedbackSearch;
	
	/**
	 * 当前页
	 */
	private Integer currentPageIndex = 0;
	
	private HelpFeedbackVo helpFeedbackVo;
	
	/**
	 * 每页大小
	 */
	private int pageSize=10;
	
	
	@Action(value = "feedbackManager", results = { @Result(name = "success", location = "../../view/help/feedbackManager.jsp") })
	public String feedbackManager(){
		return SUCCESS;
	}
	
	
	@Action(value = "getFeedbackList", results = { @Result(name = "success", location = "../../view/help/feedbackList.jsp") })
    public String getFeedbackList() throws Exception{
		log.debug("getFeedbackList...");
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		if(search==null){
			search = new Search();
		}
		map =  helpService.getFeedbackList(search,currentPageIndex,pageSize);
		list = (List) map.get("list");//数据对象
		
		page = (Page) map.get("page");//分页对象
		return SUCCESS;
    }
	
	@Action(value = "getFeedbackDetail", results = { @Result(name = "success", location = "../../view/help/feedbackDetail.jsp") })
	public String getFeedbackDetail(){
		//反馈主键ID，页面传值与数据库表字段无关
		String feedbackId = super.request.getParameter("feedbackId");
		if(feedbackId==null && "".equals(feedbackId)){
			String errorStr="获取信息失败，请刷新列表重新操作";
			request.setAttribute("errorStr", errorStr);
		}
		map=helpService.getFeedbackInfo(feedbackId);
		list=(List)map.get("list");
		
		return SUCCESS;
	}
	
	/**
	 * 处理反馈
	 * @return
	 */
	@Action(value = "handleFeedback", results = { @Result( type = "json") })
	public String handleFeedback()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");
		if(null != helpFeedbackVo){
			
			String time = MyDate.getMyDate();
			// 如果处理状态是处理中
			if(1 == helpFeedbackVo.getStatus()){  //张三(2012-12-12&nbsp;12:12)
				helpFeedbackVo.setHandleFeedbackingRemark(helpFeedbackVo.getHandleFeedbackendRemark());
				helpFeedbackVo.setMiddleHandleUser(admin.getUserName());
				helpFeedbackVo.setHandleUser(admin.getUserId());
				helpFeedbackVo.setMiddleHandleTime(time);
				helpFeedbackVo.setHandleTime(time);
			}
			//已处理步骤
			if(2 == helpFeedbackVo.getStatus()){  //张三(2012-12-12&nbsp;12:12)
				helpFeedbackVo.setHandleUser(admin.getUserId());
				helpFeedbackVo.setHandleTime(time);
			}
			
			helpFeedbackVo.setHandleUser(admin.getUserId());
			
			helpService.updateHandleFeedback(helpFeedbackVo);
			if(helpFeedbackVo.getHandleFeedbackendRemark()!=null){
				String content = "[小猪巴士]"+helpFeedbackVo.getHandleFeedbackendRemark();
				new Message(content).sendTo(Provider.CHUANGLAN, helpFeedbackVo.getPhoneNo());  // XXX Should not write hard code
			}
			pw.write("yes");
		}else{
			pw.write("no");
		}
		
		return null;
	}
	
	//设置消息实体类
//		private MarketingVo setMsgModel(String context){
//			MarketingVo model = new MarketingVo();
//			model.setMsgContext(context);
//			model.setTheModule(2);
//			model.setMsgSubType(0);
//			model.setIssend("1");
//			model.setCreateBy("SYSTEM");
//			model.setCreateOn(MyDate.getMyDate());
//			model.setMsgTiTLE("乘客反馈");
//			model.setMsgType(0);
//			model.setMsgTarget(0);
//			return model;
//		}
	
	
	
	public FeedbackSearch getFeedbackSearch() {
		return feedbackSearch;
	}

	public void setFeedbackSearch(FeedbackSearch  feedbackSearch) {
		this.feedbackSearch = feedbackSearch;
	}

	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public HelpFeedbackVo getHelpFeedbackVo() {
		return helpFeedbackVo;
	}

	public void setHelpFeedbackVo(HelpFeedbackVo helpFeedbackVo) {
		this.helpFeedbackVo = helpFeedbackVo;
	}
	
}
