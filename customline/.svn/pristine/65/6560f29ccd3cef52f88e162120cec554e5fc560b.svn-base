package com.amwell.action.user;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IPassengerCommentsService;
import com.amwell.vo.PassengerCommentsEntity;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.SysAdminVo;

/**
 * 运营平台-用户管理-乘客管理-评论
 * @author 龚雪婷
 *
 */
@ParentPackage("user-finit")
@Namespace("/operationComment")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CommentAction extends BaseAction {
	//评论service
	@Autowired
	private IPassengerCommentsService service;
	
	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 5;
	
	/**
	 * 加载评论列表
	 */
	@Action(value = "commentList", results = { @Result(name = "success", location = "../../view/user/commentList.jsp") })
	public String list(){
		try {
			if(request.getParameter("p")==null){//第一次访问该列表时清空session中的查询条件信息
				session.remove("commentList_Cond");
			}
			/*当前页*/
			currentPageIndex = StringUtil.objectToInt(request.getParameter("currentPageIndex")!=null?request.getParameter("currentPageIndex"):"0");
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("commentList_Cond"):search);
			session.put("commentList_Cond", search);
			/*每页显示条数*/
			pageSize = 10;
			//集合对象
			map = service.listByPage(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 乘客评论屏蔽或显示操作
	 * @return
	 * @throws Exception
	 */
	@Action(value = "commentChangeStatus", results = { @Result(type="json") })
	public String changeStatus(){
		try {
			String returnStr=null;
			
			String commentId=request.getParameter("commentId");
			String commentStatus=request.getParameter("commentStatus");
			
			if(StringUtils.isBlank(commentId)||StringUtils.isBlank(commentStatus)){
				returnStr="信息丢失，请刷新列表重新操作！";
			}
			else{
				
				PassengerCommentsEntity comment=service.getPassengerCommentsById(commentId);
				if(null==comment){
					returnStr="信息丢失，请刷新列表重新操作！";
				}
				else{
					if("0".equals(commentStatus)){//显示
						comment.setCommentStatus(new Short("1"));
					}
					else{//屏蔽
						comment.setCommentStatus(new Short("0"));
					}
					int returnNum = 0;
					SysAdminVo sysAdmin = (SysAdminVo) this.request.getSession().getAttribute("userInfo");
					if(sysAdmin!=null){
						comment.setUpdateBy(sysAdmin.getLoginName());
						comment.setUpdateOn(MyDate.getMyDateLong());
						returnNum = service.updatePassengerComment(comment,null==sysAdmin?null:sysAdmin.getUserId());
					}
					if(returnNum>0){
						returnStr="ok";
					}
					else{
						returnStr="操作失败，请刷新列表重新操作！";
					}
				}
			}
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(returnStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
