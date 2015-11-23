package com.amwell.action.coupon;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import com.amwell.action.BaseAction;
import com.amwell.commons.HttpFileUpload;
import com.amwell.commons.JSONUtil;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.commons.PropertyManage;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.service.IPassengerInfoService;
import com.amwell.service.ISysAreaService;
import com.amwell.service.coupon.ICouponGroupGrantService;
import com.amwell.service.coupon.ICouponGroupPassengerDetailService;
import com.amwell.service.coupon.ICouponGroupService;
import com.amwell.service.coupon.ICouponRuleService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.app.LineBaseInfo;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.coupon.CouponGroup;
import com.amwell.vo.coupon.CouponGroupGrant;
import com.amwell.vo.coupon.CouponGroupPassengerDetail;
import com.amwell.vo.coupon.CouponRule;
import com.amwell.vo.ftp.ParamFtp;

/**
 * 组合券发放信息
 * @author gxt
 *
 */
@ParentPackage("user-finit")
@Namespace("/couponGroupGrant")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class CouponGroupGrantAction extends BaseAction {
	
	private static final Logger log = Logger.getLogger(CouponGroupGrantAction.class);
	
	@Autowired
	private ICouponGroupGrantService grantService;
	@Autowired
	private ICouponGroupService groupService;
	@Autowired
	private ICouponRuleService ruleService;
	@Autowired
	private ISysAreaService iSysAreaService;
	@Autowired
	private ILineService lineService;
	@Autowired
	private IPassengerInfoService iPassengerInfoService;
	@Autowired
	private ICouponGroupPassengerDetailService detailService;

	/**
	 * 记录数的下标记数，0开始，到10
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 15;
	
	private List<CouponGroup> couponGroupList;
	
	private CouponGroupGrant couponGroupGrant;
	
	private File telephoneFile;

	/**
	 * 组合券发放列表
	 */
	@Action(value = "couponGroupGrantList", results = { @Result(name = "success", location = "../../view/coupon/couponGroupGrantList.jsp") })
	public String list(){
		if(request.getParameter("p")==null&&null==this.getRequest().getSession().getAttribute("detailGrantId")){
			session.remove("couponGroupGrantList_Cond");
		}
		else{
			this.getRequest().getSession().removeAttribute("detailGrantId");//用于详情页面分页的id
		}
		
		try {
			
			/*当前页*/
			if(null!=session.get("myPageIndex")){
				this.currentPageIndex = Integer.valueOf(session.get("myPageIndex").toString());
				super.session.remove("myPageIndex");
			}
			else{
				currentPageIndex = StringUtil.objectToInt(request.getParameter("p")!=null?request.getParameter("p"):"0");
			}
			
			/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
			search = (Search) (search == null?session.get("couponGroupGrantList_Cond"):search);
			if(null==search){
				//获取用户的登陆信息
				SysAdminVo admin = (SysAdminVo) session.get("userInfo");
				search=new Search();
				search.setField06(admin.getUserName());
			}
			session.put("couponGroupGrantList_Cond", search);
			//集合对象
			map = grantService.getListByCondition(search,currentPageIndex,pageSize);
			list = (List) map.get("list");//数据对象
			page = (Page) map.get("page");//分页对象
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 进入组合券发放添加页
	 */
	@Action(value = "toCouponGroupGrantAddPage", results = { @Result(name = "success", location = "../../view/coupon/couponGroupGrantAdd.jsp") })
	public String toAddPage(){
		search=new Search();
		search.setField05("send");
		map=groupService.getListByCondition(search, 0, 0);
		couponGroupList=(List<CouponGroup>) map.get("list");
		return SUCCESS;
	}
	
	/**
	 * 进入组合券发放编辑页
	 */
	@Action(value = "toCouponGroupGrantEditPage", results = { @Result(name = "success", location = "../../view/coupon/couponGroupGrantEdit.jsp") })
	public String toEditPage(){
		session.put("myPageIndex",request.getParameter("myPageIndex"));
		String grantId=request.getParameter("grantId");
		this.getRequest().getSession().setAttribute("detailGrantId",grantId);
		this.couponGroupGrant=grantService.getInfoById(grantId);
		List<CouponRule> ruleList=ruleService.getCouponRule(grantId);
		request.setAttribute("ruleList",ruleList);
		return SUCCESS;
	}
	
	/**
	 * 进入组合券发放复制页
	 */
	@Action(value = "toCouponGroupGrantCopyPage", results = { @Result(name = "success", location = "../../view/coupon/couponGroupGrantCopy.jsp") })
	public String toCopyPage(){
		session.put("myPageIndex",request.getParameter("myPageIndex"));
		String grantId=request.getParameter("grantId");
		this.getRequest().getSession().setAttribute("detailGrantId",grantId);
		this.couponGroupGrant=grantService.getInfoById(grantId);
		List<CouponRule> ruleList=ruleService.getCouponRule(grantId);
		request.setAttribute("ruleList",ruleList);
		return SUCCESS;
	}
	
	/**
	 * 添加组合券发放
	 * @return
	 * @throws Exception
	 */
	@Action(value="couponGroupGrantAdd",results = { @Result( type = "json") })
	public String couponGroupGrantAdd()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		String ruleName[] = request.getParameterValues("ruleName");
		String theCondition[] = request.getParameterValues("theCondition");
		String theValue[] = request.getParameterValues("theValue");
		if(ruleName.length==theCondition.length&&theCondition.length==theValue.length){
			String temp = "";
			for (int i = 0; i < ruleName.length; i++) {
				temp+=ruleName[i]+","+theCondition[i]+","+theValue[i]+"#";
			}
			this.couponGroupGrant.setRules(temp.substring(0,temp.length()-1));
		}

		//获取用户的登陆信息
		SysAdminVo admin = (SysAdminVo) session.get("userInfo");
		
		this.couponGroupGrant.setCreateBy(admin.getUserId());
		this.couponGroupGrant.setCreateOn(MyDate.getMyDateLong());
		if("oldUser".equals(this.couponGroupGrant.getSelectPass())){//发放用户为现有用户
			this.couponGroupGrant.setModeOfExecution("closed");
		}
		else{//发放用户为新进用户
			this.couponGroupGrant.setModeOfExecution("send");
		}
		int flag=grantService.updateCouponGroupGrantT(this.couponGroupGrant);

		if(flag==1){
			//发放用户为现有用户且为系统发放类型，则扫描现有用户发放优惠券
			if("oldUser".equals(this.couponGroupGrant.getSelectPass())&&"sys send".equals(this.couponGroupGrant.getSendCouponType())){
				//添加时计算出最大的发放id
				if(StringUtils.isBlank(this.couponGroupGrant.getId())){
					this.couponGroupGrant.setId(grantService.getMaxId());
				}
				flag=grantService.addCouponDetail(this.couponGroupGrant.getId());
			}
		}
		pw.print(flag);
		return null;
	}
	
	/**
	 * 修改组合券发放
	 * @return
	 * @throws Exception
	 */
	@Action(value="couponGroupGrantEdit",results = { @Result( type = "json") })
	public String couponGroupGrantEdit()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		String ruleName[] = request.getParameterValues("ruleName");
		String theCondition[] = request.getParameterValues("theCondition");
		String theValue[] = request.getParameterValues("theValue");
		if(ruleName.length==theCondition.length&&theCondition.length==theValue.length){
			String temp = "";
			for (int i = 0; i < ruleName.length; i++) {
				temp+=ruleName[i]+","+theCondition[i]+","+theValue[i]+"#";
			}
			this.couponGroupGrant.setRules(temp.substring(0,temp.length()-1));
		}

		//获取用户的登陆信息
		SysAdminVo admin = (SysAdminVo) session.get("userInfo");
		
		this.couponGroupGrant.setUpdateBy(admin.getUserId());
		this.couponGroupGrant.setUpdateOn(MyDate.getMyDateLong());
		int flag=grantService.updateCouponGroupGrantT(this.couponGroupGrant);
		pw.print(flag);
		return null;
	}
	
	/**
	 * 执行优惠券发放
	 * @return
	 * @throws Exception
	 */
	@Action(value="couponDetailAdd",results = { @Result( type = "json") })
	public String couponDetailAdd()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		session.put("myPageIndex",request.getParameter("myPageIndex"));
		
		//获取用户的登陆信息
		SysAdminVo admin = (SysAdminVo) session.get("userInfo");
		
		this.couponGroupGrant=new CouponGroupGrant();
		String couponGroupGrantId=request.getParameter("grantId");
		this.couponGroupGrant.setId(couponGroupGrantId);
		this.couponGroupGrant.setModeOfExecution("send");
		this.couponGroupGrant.setUpdateBy(admin.getUserId());
		this.couponGroupGrant.setUpdateOn(MyDate.getMyDateLong());
		int flag=grantService.updateGrant(this.couponGroupGrant);
		pw.print(flag);
		return null;
	}
	
	/**
	 * 关闭优惠券发放
	 * @return
	 * @throws Exception
	 */
	@Action(value="couponGrantClose",results = { @Result( type = "json") })
	public String couponGrantClose()throws Exception{
		HttpServletResponse response = getResponse();
		response.setContentType("text/html;charset=GBK");
		PrintWriter pw = response.getWriter();
		
		session.put("myPageIndex",request.getParameter("myPageIndex"));
		
		//获取用户的登陆信息
		SysAdminVo admin = (SysAdminVo) session.get("userInfo");
		
		String grantId=request.getParameter("grantId");
		this.couponGroupGrant=new CouponGroupGrant();
		this.couponGroupGrant.setId(grantId);
		this.couponGroupGrant.setModeOfExecution("closed");
		this.couponGroupGrant.setUpdateBy(admin.getUserId());
		this.couponGroupGrant.setUpdateOn(MyDate.getMyDateLong());
		int flag=grantService.updateGrant(this.couponGroupGrant);

		pw.print(flag);
		return null;
	}
	
	/**
	 * 查询城市信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getCityInfo",results={@Result(type="json")})
	public String getCityInfo() throws Exception{
		List<SysArea> citySysAreas=iSysAreaService.querySysArea();
		String json = "[]";
		if (false == CollectionUtils.isEmpty(citySysAreas)) {
			json = JSONUtil.formObjectsToJSONStr(citySysAreas);
		}
		log.debug("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	/**
	 * 查询线路信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getLineInfo",results={@Result(type="json")})
	public String getLineInfo() throws Exception{
		List<LineBaseInfo> lineList=lineService.getLineInfo();
		String json = "[]";
		if (false == CollectionUtils.isEmpty(lineList)) {
			json = JSONUtil.formObjectsToJSONStr(lineList);
		}
		log.debug("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	/**
	 * 进入组合券发放详情页
	 */
	@Action(value = "toCouponGroupGrantDetailPage", results = { @Result(name = "success", location = "../../view/coupon/couponGroupGrantDetail.jsp") })
	public String toCouponGroupGrantDetailPage(){
		session.put("myPageIndex",request.getParameter("myPageIndex"));

		String grantId=request.getParameter("grantId");
        if(null!=this.getRequest().getSession().getAttribute("detailGrantId")){
        	grantId=(String) this.getRequest().getSession().getAttribute("detailGrantId");
        	this.getRequest().getSession().removeAttribute("detailGrantId");
		} 
		
		//查询优惠券信息
		CouponGroup couponGroup=this.groupService.getCouponGroup(grantId);
		//查询规则信息
		List<CouponRule> couponRuleList=this.ruleService.getCouponRule(grantId);
		//查询发放信息
		CouponGroupGrant couponGroupGrant=this.grantService.getInfoById(grantId);
		
		this.pageSize=10;
		currentPageIndex = StringUtil.objectToInt(request.getParameter("p")!=null?request.getParameter("p"):"0");
		
		//查询用户优惠券发放详情
//		String telephone=this.request.getParameter("telephone");
//		search=new Search();
//		search.setField01(telephone);
		if(currentPageIndex==0){
			session.remove("couponGroupGrantDetail_Cond");
		}
		search = (Search) (search == null?session.get("couponGroupGrantDetail_Cond"):search);
		if(null==search){
			search=new Search();
		}
		search.setField02(grantId);
		session.put("couponGroupGrantDetail_Cond", search);
		
		Map<String,Object> map=detailService.getListByCondition(search, currentPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		
		request.setAttribute("couponGroup",couponGroup);
		request.setAttribute("couponRuleList",couponRuleList);
		request.setAttribute("couponGroupGrant",couponGroupGrant);
		return SUCCESS;
	}
	
	/**
	 * 检测号码
	 */
	@Action(value = "checkTelephoneExist",results = { @Result( type = "json") })
	public String checkTelephoneExist() throws IOException{
		String json="success";
		String telephone = super.request.getParameter("telephones");//以;分隔手机号
		if(telephone==null ||"".equals(telephone) ||telephone.split(";").length<1){
			json="error";
		}else{
			String flag = iPassengerInfoService.checktelephone(telephone,null);
			if(!"/".equals(flag)){
				json=flag;
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	/**
	 * 上传
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@Action(value = "upLoad", results = { @Result(type = "json") })
	public void upLoad() throws IOException {
		try {
			String encoding="GBK";
			String resultStr=null;
            if(telephoneFile.isFile() && telephoneFile.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(telephoneFile),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(null==resultStr){
                		resultStr=lineTxt;
                	}
                	else{
                		resultStr=resultStr+";"+lineTxt;
                	}
                }
                read.close();
                
                if(null==resultStr){
                	resultStr="-2";
                }
            }
            else{
            	resultStr="-1";
            }
            
			getResponse().setContentType("text/html;charset=UTF-8");
			getResponse().getWriter().print(resultStr);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		} 
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

	public List<CouponGroup> getCouponGroupList() {
		return couponGroupList;
	}

	public void setCouponGroupList(List<CouponGroup> couponGroupList) {
		this.couponGroupList = couponGroupList;
	}

	public CouponGroupGrant getCouponGroupGrant() {
		return couponGroupGrant;
	}

	public void setCouponGroupGrant(CouponGroupGrant couponGroupGrant) {
		this.couponGroupGrant = couponGroupGrant;
	}

	public File getTelephoneFile() {
		return telephoneFile;
	}

	public void setTelephoneFile(File telephoneFile) {
		this.telephoneFile = telephoneFile;
	}
}
