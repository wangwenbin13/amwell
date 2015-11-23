package com.amwell.action.merchant;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.MyDate;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IMgrbusinessService;
import com.amwell.service.ISysAreaService;
import com.amwell.service.ISysMgrAdminService;
import com.amwell.utils.StringUtils;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.EntityVo;
import com.amwell.vo.MgrBusinessEntity;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.SysMgrAdminEntity;

/**
 * @author wangwenbin
 *
 * 2014-8-14
 */
/**
 * 商户管理
 */
@ParentPackage("user-finit")
@Namespace("/merchantAction")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class MerchantAction extends BaseAction{
	
	@Autowired
	private IMgrbusinessService iMgrbusinessService;
	
	/**
	 * 用户
	 */
	@Autowired
	private ISysMgrAdminService iSysMgrAdminService;
	
	/**
	 * 商户
	 */
	private MgrBusinessEntity mgrBusinessEntity;
	
	/**
	* 区域
	*/
	@Autowired
	private ISysAreaService iSysAreaService;
	
	/**
	 * 区域省份集合
	 */
	private List<SysArea> proSysAreas;
	
	/**
	 * 区域城市集合
	 */
	private List<SysArea> citySysAreas;
	
	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 10;
	
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 *集合大小 
	 */
	private Integer listSize = 0;
	
	/**
	 * 商家集合
	 */
	private List<MgrBusinessEntity> mgrBusinessEntities;
	/**
	 * 商户管理
	 */
	@Action(value="getMerchantList",results={@Result(name="success",location="../../view/merchant/merchantManagerList.jsp")})
	public String getMerchantList()throws Exception{
		
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof Integer) {
				this.currentPageIndex = (Integer) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
					: Integer
							.parseInt(request.getParameter("currentPageIndex"));
		}
		
//		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		
		/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
		search = (Search) (search == null?session.get("search"):search);
		if(null==search){
			search=new Search();
		}
		
		if(null!=search && null!=search.getField05() && !"".equals(search.getField05())){
			if(search.getField05().split(",").length>1){
				search.setField05("all");
			}
		}
		if(null!=search && "".equals(search.getField05())){
			search.setField05(null);
		}
		map = iMgrbusinessService.listByPage(search, currentPageIndex, pageSize);
		this.session.put("search",search);
		mgrBusinessEntities = (List) map.get("list");//数据对象
		
		/**
		 * 省份城市maps
		 */
		Map<String,String> proCityMaps = (Map<String, String>) request.getSession().getAttribute("proCityMaps");
		if(null == proCityMaps){
			proCityMaps = new HashMap<String,String>();
			sysArea = new SysArea();
			List<SysArea>  proCities = iSysAreaService.querySysArea(sysArea);
			if(null!=proCities && proCities.size()>0){
				/**
				 * 用FdCode 和 areaCode 为key,name为value
				 */
				for(SysArea area : proCities){
					proCityMaps.put(area.getFdCode()+"/"+area.getArearCode(), area.getAreaName());
				}
			}
			request.getSession().setAttribute("proCityMaps", proCityMaps);
		}
		
		if(null!=mgrBusinessEntities && mgrBusinessEntities.size()>0){
			for(MgrBusinessEntity mgrBusinessEntity : mgrBusinessEntities){
				if(!StringUtils.isEmpty(mgrBusinessEntity.getProvinceCode()) && null!=proCityMaps){
					/**
					 * 省份
					 */
					String proCode = mgrBusinessEntity.getProvinceCode();
					mgrBusinessEntity.setProvinceCode(proCityMaps.get("-1"+"/"+proCode));
					
					
					/**
					 * 城市
					 */
					if(!StringUtils.isEmpty(mgrBusinessEntity.getAreaCode())){
						mgrBusinessEntity.setAreaCode(proCityMaps.get(proCode+"/"+mgrBusinessEntity.getAreaCode()));
					}
				}
			}
		}
		
		page = (Page) map.get("page");//分页对象
		listSize = mgrBusinessEntities==null?0:mgrBusinessEntities.size();
		return SUCCESS;
	}
	
	/**
	 * 添加/修改商户
	 */
	@Action(value="addMerchant",results = { @Result( type = "json") })
	public String addMerchant()throws Exception{
		getResponse().setContentType("text/html;charset=UTF-8");
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		//检查用户名或密码
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		mgrBusinessEntity.setUpdateOn(MyDate.getMyDateLong());
		mgrBusinessEntity.setUpdateBy(sysAdmin.getUserId());
		
		/**
		 * 将对应的数据添加/修改到用户表里面
		 */
		SysMgrAdminEntity sysMgrAdmin = new SysMgrAdminEntity();
		/**
		 * 登录名
		 */
		sysMgrAdmin.setLoginName(mgrBusinessEntity.getLoginName());
		/**
		 * 密码
		 */
		if(!StringUtils.isEmpty(mgrBusinessEntity.getLoginPassword())){
			sysMgrAdmin.setPassWord(mgrBusinessEntity.getLoginPassword());
		}
		/**
		 * 联系人
		 */
		sysMgrAdmin.setUserName(mgrBusinessEntity.getContacts());
		/**
		 *  联系电话
		 */
		sysMgrAdmin.setTelephone(mgrBusinessEntity.getContactsPhone());
		/**
		 *  帐号状态
		 *  1:启用     0:禁用
		 */
		sysMgrAdmin.setStatus(mgrBusinessEntity.getAccountStatus());
		/**
		 *  创建人
		 */
		sysMgrAdmin.setCreateBy(mgrBusinessEntity.getCreateBy());
		/**
		 * 备注
		 */
		sysMgrAdmin.setRemark(mgrBusinessEntity.getRemark());
		String statu = "";
		
		if(null==mgrBusinessEntity.getBusinessId() || "".equals(mgrBusinessEntity.getBusinessId())){
			
			/**再次验证登录名是否存在，防止重复提交**/
			int flag = iMgrbusinessService.checkNameExist(mgrBusinessEntity);
			if(flag==0){
				/**
				 * 添加
				 */
				mgrBusinessEntity.setCreateOn(MyDate.getMyDateLong());
				mgrBusinessEntity.setCreateBy(sysAdmin.getUserId());
				statu = iMgrbusinessService.addMgrBussiness(mgrBusinessEntity);
				/**
				 * 添加运营平台对应的商户信息
				 */
				sysMgrAdmin.setBusinessId(statu);
				sysMgrAdmin.setCreateBy(sysAdmin.getUserId());
				iSysMgrAdminService.insertIntoScheduleAdminInfo(sysMgrAdmin);
			}else{
				statu = "isExist";
			}
		}else{
			/**
			 * 修改运营平台对应的商户信息
			 */
			/**
			 * 修改人
			 */
			sysMgrAdmin.setUpdateBy(sysAdmin.getUserId());
			sysMgrAdmin.setCreateBy(mgrBusinessEntity.getCreateBy());
			sysMgrAdmin.setBusinessId(mgrBusinessEntity.getBusinessId());
			iSysMgrAdminService.updateScheduleAdminInfo(sysMgrAdmin);
			
			statu = iMgrbusinessService.addMgrBussiness(mgrBusinessEntity);
		}
		
		
		if("isExist".equals(statu)){
			getResponse().getWriter().print("isExist");
		}else if(!StringUtils.isEmpty(statu)){
			getResponse().getWriter().print("success");
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**
	 * 检查名称是否存在
	 * @throws IOException 
	 */
	@Action(value="checkNameExist",results = { @Result( type = "json") })
	public String checkNameExist() throws IOException{
		String name = "";
		getResponse().setContentType("text/html;charset=GBK");
		if(!StringUtils.isEmpty(mgrBusinessEntity.getName())){
			/**
			 * 企业名称
			 */
			name = mgrBusinessEntity.getName().trim();
			name = URLDecoder.decode(name,"utf-8");
			mgrBusinessEntity.setName(name);
		}else if(!StringUtils.isEmpty(mgrBusinessEntity.getBrefName())){
			/**
			 * 简称
			 */
			name = mgrBusinessEntity.getBrefName().trim();
			name = URLDecoder.decode(name,"utf-8");
			mgrBusinessEntity.setBrefName(name);
		}else if(!StringUtils.isEmpty(mgrBusinessEntity.getLoginName())){
			/**
			 * 登录名
			 */
			name = mgrBusinessEntity.getLoginName().trim();
			name = URLDecoder.decode(name,"utf-8");
			mgrBusinessEntity.setLoginName(name);
		}
		int statu = iMgrbusinessService.checkNameExist(mgrBusinessEntity);
		if(statu==0){
			getResponse().getWriter().print("success");
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**
	 *添加/编辑商户页面
	 */
	@Action(value="editMerchant",results={@Result(name="success",location="../../view/merchant/merchantEdit.jsp")})
	public String editUser()throws Exception{
		String businessId = request.getParameter("businessId");
		if(businessId!=null && !"".equals(businessId)){
			mgrBusinessEntity = iMgrbusinessService.getMerchantDetail(businessId);
		}
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	
	/**
	 * 根据省份ID获取城市
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getProvince", results = { @Result(type = "json") })
	public void getProvince() throws Exception {
		String proId = request.getParameter("proId");
		SysArea sysArea = new SysArea();
		sysArea.setFdCode(proId);
		citySysAreas = iSysAreaService.querySysArea(sysArea);
		getResponse().setContentType("text/html;charset=GBK");
		JsonWriter.write(citySysAreas);
	}
	
	/**
	 * 商户详情
	 */
	@Action(value="getMerchantDetail",results={@Result(name="success",location="../../view/merchant/merchantDetail.jsp")})
	public String getMerchantDetail()throws Exception{
		String businessId = request.getParameter("businessId");
		mgrBusinessEntity = iMgrbusinessService.getMerchantDetail(businessId);
		SysArea sysArea = new SysArea();
		String proName = "";
		String cityName = "";
		if(!StringUtils.isEmpty(mgrBusinessEntity.getProvinceCode())){
			sysArea.setFdCode("-1");
			sysArea.setArearCode(String.valueOf(mgrBusinessEntity.getProvinceCode()));
			proSysAreas = iSysAreaService.querySysArea(sysArea);
			if(null!=proSysAreas && proSysAreas.size()>0){
				proName = proSysAreas.get(0).getAreaName();
			}
		}
		if(!StringUtils.isEmpty(mgrBusinessEntity.getAreaCode())){
			sysArea.setFdCode(String.valueOf(mgrBusinessEntity.getProvinceCode()));
			sysArea.setArearCode(String.valueOf(mgrBusinessEntity.getAreaCode()));
			citySysAreas = iSysAreaService.querySysArea(sysArea);
			if(null!=citySysAreas && citySysAreas.size()>0){
				cityName = citySysAreas.get(0).getAreaName();
			}
		}
		mgrBusinessEntity.setAddress(proName+cityName+mgrBusinessEntity.getAddress());
		
		super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
		
		return SUCCESS;
	}
	
	/**
	 * 导出Excel
	 */
	@Action(value="exportExcel")
	public void exportExcel(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = iMgrbusinessService.listByPage(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "商户列表"; //导出列表头名称
	    String[] hearders = new String[] {
 	    		"商户简称", "登录名", "联系人","联系电话","操作人","创建时间","帐号状态"};//表头数组
	    //address 临时作为操作人
	    String[] fields = new String[] {
 	    		"brefName", "loginName", "contacts", "contactsPhone", "address","createOn","accounStatu"};//对象属性数组
	    
	    List<MgrBusinessEntity> lists = (List) map.get("list");
	    if(null!=lists && lists.size()>0){
	    	for(MgrBusinessEntity mgrBusinessEntity:lists){
	    		if(1==mgrBusinessEntity.getAccountStatus()){
	    			mgrBusinessEntity.setAccounStatu("启用");
	    		}else{
	    			mgrBusinessEntity.setAccounStatu("禁用");
	    		}
	    	}
	    }
	    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
	    JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 重置密码
	 * @throws IOException 
	 */
	@Action(value="updatePassWord",results = { @Result( type = "json") })
	public String updatePassWord() throws IOException{
		//mgrBusinessEntity
		getResponse().setContentType("text/html;charset=UTF-8");
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		SysMgrAdminEntity sysMgrAdmin = new SysMgrAdminEntity();
		sysMgrAdmin.setBusinessId(mgrBusinessEntity.getBusinessId());
		sysMgrAdmin.setCreateBy(mgrBusinessEntity.getCreateBy());
		sysMgrAdmin.setPassWord(mgrBusinessEntity.getLoginPassword());
		sysMgrAdmin.setLoginName(mgrBusinessEntity.getLoginName());
		boolean flag = iSysMgrAdminService.resertMgrPassword(sysMgrAdmin,sysAdmin.getUserId());
		if(flag){
			getResponse().getWriter().print("success");
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**
	 * 修改状态
	 * @throws IOException 
	 */
	@Action(value="updateStatu",results = { @Result( type = "json") })
	public String updateStatu() throws IOException{
		//mgrBusinessEntity
		getResponse().setContentType("text/html;charset=UTF-8");
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo sysAdmin = (SysAdminVo) httpSession.getAttribute("userInfo");
		SysMgrAdminEntity sysMgrAdmin = new SysMgrAdminEntity();
		sysMgrAdmin.setBusinessId(mgrBusinessEntity.getBusinessId());
		sysMgrAdmin.setStatus(mgrBusinessEntity.getAccountStatus());
		sysMgrAdmin.setUpdateBy(sysAdmin.getUserId());
		boolean flag = iSysMgrAdminService.changeMgrAccountStatus(sysMgrAdmin);
		if(flag){
			getResponse().getWriter().print("success");
		}else{
			getResponse().getWriter().print("error");
		}
		return null;
	}
	
	/**跳转到商户列表窗口**/
	@Action(value = "queryMerchantListJsonManager", results = { @Result(name = "success", location = "../../view/line/pop-businessList.jsp") })
	public String queryMerchantListJsonManager() {
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	
	/**商户列表JSON**/
	@Action(value = "queryMerchantListJson", results = { @Result(type = "json") })
	public void queryMerchantListJson() throws IOException, JSONException {
		String json = "{}";
		String businessId = request.getParameter("businessId");
		if(null==search){
			search = new Search();
		}
		search.setField09(businessId);
		/**启用帐号**/
		search.setField05("1");
		search.setField06("1");// 商户业务类型 1:上下班 2：包车 3.两者都支持
		int currPage = 1;
		String currPageStr = super.request.getParameter("currPage");
		if (!StringUtils.isEmpty(currPageStr)) {
			try {
				currPage = Integer.parseInt(currPageStr);
			} catch (Exception e) {
			}
		}
		this.pageSize = 5;
		int curPageIndex = (currPage - 1) * this.pageSize;
		map = iMgrbusinessService.listByPage(search, curPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		EntityVo mv = new EntityVo();
		mv.setCurrPage(currPage);
		mv.setPageSize(this.pageSize);
		mv.setTotalCount(null != page ? page.getTotalCount() : 0);
		if(null==list){
			list = new ArrayList();
		}
		mv.setResultData(list);
		JsonWriter.write(mv);
	}
	

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public MgrBusinessEntity getMgrBusinessEntity() {
		return mgrBusinessEntity;
	}

	public void setMgrBusinessEntity(MgrBusinessEntity mgrBusinessEntity) {
		this.mgrBusinessEntity = mgrBusinessEntity;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}

	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}

	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}

	public List<SysArea> getCitySysAreas() {
		return citySysAreas;
	}

	public void setCitySysAreas(List<SysArea> citySysAreas) {
		this.citySysAreas = citySysAreas;
	}

	public List<MgrBusinessEntity> getMgrBusinessEntities() {
		return mgrBusinessEntities;
	}

	public void setMgrBusinessEntities(List<MgrBusinessEntity> mgrBusinessEntities) {
		this.mgrBusinessEntities = mgrBusinessEntities;
	}
	
}
