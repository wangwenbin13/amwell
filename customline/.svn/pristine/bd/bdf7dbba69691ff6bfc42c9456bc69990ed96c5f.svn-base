package com.amwell.action.line;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.JSONUtil;
import com.amwell.commons.MyDate;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.service.IPublishLineService;
import com.amwell.vo.LineEnrollVo;
import com.amwell.vo.RecruitLineForOuterContainer;
import com.amwell.vo.StationInfo;
/**
 * 外围线路action,供官网使用
 * 
 * @author Administrator
 * 
 */
@ParentPackage("json-default")
@Namespace("/outerLine")
@Scope("prototype")
public class OuterLineAction extends BaseAction {

	private static final long serialVersionUID = -1509844060516004026L;

	@Autowired
	private ILineService lineService;
	
	@Autowired
	private IPublishLineService publishLineService;

	private int pageSize=10;

	/**
	 * 查询线路信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getLinesForOuter",results = { @Result( type = "json") })
	public String getLinesForOuter() throws IOException{
	   String json ="{}";
	   int currPage = 1;
	   String currPageStr = super.request.getParameter("currPage");
	   if(StringUtils.hasText(currPageStr)){
		   try {
			   currPage = Integer.parseInt(currPageStr);
		   } catch (Exception e) {
			   currPage = 1;
		   }
		   
	   }
	   String pageSizeStr=super.request.getParameter("pageSize");
	   if(StringUtils.hasText(pageSizeStr)){
		   try {
			   this.pageSize = Integer.parseInt(pageSizeStr);
		   } catch (Exception e) {
			   this.pageSize=10;
		   }
	   }
	   int curPageIndex = (currPage-1)*this.pageSize;
	   if(null==search){
			search = new Search();
	   }
	   String lineType = super.request.getParameter("lineType");
	   if(StringUtils.hasText(lineType)){
		   search.setField01(lineType);
	   }
	   String lineBaseId=super.request.getParameter("lineBaseId");
	   if(StringUtils.hasText(lineBaseId)){
		   search.setField02(lineBaseId);
	   }
	   map= lineService.getLinesForOuter(search, curPageIndex, pageSize);
	   if(false==CollectionUtils.isEmpty(map)){
		   RecruitLineForOuterContainer container =(RecruitLineForOuterContainer) map.get("container");//数据对象
		   container.setCurrPage(currPage);
		   String callbackFunName = request.getParameter("callbackparam");
		   json = callbackFunName+"("+JSONUtil.formObjectToJsonObject(container)+")";
	   }
	   System.out.println("json:"+json);
	   getResponse().setContentType("text/html;charset=UTF-8");
	   getResponse().getWriter().print(json);
	   return null;
	}

	@Action(value="getStationList",results={@Result(type="json")})
	public String getStationList() throws IOException{
		   String json ="[]";
		   String lineBaseId = super.request.getParameter("lineBaseId");
		   if(StringUtils.hasText(lineBaseId)){
			   List<StationInfo> stationList = publishLineService.getStationListById(lineBaseId);
			   String callbackFunName = request.getParameter("callbackparam");
			   json = callbackFunName+"("+JSONUtil.formObjectsToJSONStr(stationList)+")";
		   }
		   System.out.println("json:"+json);
		   getResponse().setContentType("text/html;charset=UTF-8");
		   getResponse().getWriter().print(json);
		   return null;
	}
	
	@Action(value="register",results={@Result(type="json")})
	public String register() throws IOException{
		String json ="{\"data\":\"error\"}";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String applyName = URLDecoder.decode(StringUtil.replaceHtmlTag(super.request.getParameter("registerName")), "utf-8");
		String applyTel = super.request.getParameter("registerTell");
		String ustation = URLDecoder.decode(super.request.getParameter("registerSatrtPoint"), "utf-8");
		String dstation = URLDecoder.decode(super.request.getParameter("registerEndPoint"), "utf-8");
		String applyNum = super.request.getParameter("registerNum");
		String remark = URLDecoder.decode(StringUtil.replaceHtmlTag(super.request.getParameter("remark")),"utf-8");
		String source = super.request.getParameter("source");
		String callbackFunName = request.getParameter("callbackparam");
		LineEnrollVo lineEnrollVo = new LineEnrollVo();
		lineEnrollVo.setApplyName(applyName);
		if(StringUtils.hasText(applyNum)){
			try {
				lineEnrollVo.setApplyNum(Integer.parseInt(applyNum));
			} catch (Exception e) {
			}
		}
		lineEnrollVo.setApplyTel(applyTel);
		lineEnrollVo.setDstation(dstation);
		lineEnrollVo.setLineBaseId(lineBaseId);
		lineEnrollVo.setPassengerId("0");
		lineEnrollVo.setPresetTime(MyDate.getMyDateLong());
		lineEnrollVo.setUstation(ustation);
		if(StringUtils.hasText(source)){
			lineEnrollVo.setSource(Integer.parseInt(source));
		}
//		是否愿意乘坐0:未知 1:无意向 2:无意向
		lineEnrollVo.setWstatus(1);
		lineEnrollVo.setRemark(remark);
		//根据手机号和线路ID查询用户是否已报名
		int count = lineService.getLineEnrollNum(lineBaseId,applyTel);
		if(count>0){
			json ="{\"data\":\"repeatApply\"}";
		}else{
			int flag = lineService.addLineEnrol(lineEnrollVo);
			if(flag>0){
				json ="{\"data\":\"success\"}";
			}
		}
		json = callbackFunName+"("+json+")";
		System.out.println("json:"+json);
	   	getResponse().setContentType("text/html;charset=UTF-8");
	   	getResponse().getWriter().print(json);
		return null;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
