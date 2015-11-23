package com.amwell.action.gf;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
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
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ICouponService;
import com.amwell.service.IGiftService;
import com.amwell.service.IPassengerInfoService;
import com.amwell.service.ISysAreaService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.gf.CouponContainerVo;
import com.amwell.vo.gf.CouponVo;
import com.amwell.vo.gf.GiftContainerVo;

/**
 * 优惠券Action
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/coupon")
@Scope("prototype")
public class CouponAction extends BaseAction {

	private static final long serialVersionUID = 3093668163275405509L;

	private static final Logger log = Logger.getLogger(CouponAction.class);
	
	private static final String KEY_SESSION_USER="userInfo";
	
	@Autowired
	private ICouponService couponService;
	
	@Autowired
	private IGiftService giftService;
	
	@Autowired
	private IPassengerInfoService iPassengerInfoService;
	
	/**
	* 区域
	*/
	@Autowired
	private ISysAreaService iSysAreaService;
	
	private int currentPageIndex=0;
	
	private int pageSize = 10;
	
	/**
	 * 区域省份集合
	 */
	private List<SysArea> proSysAreas;
	
	private CouponVo couponVo;
	
	private String woringPhones;
	
	private String noPhones;
	
	private String checkedGiftId;
	
	private String nowTime;
	
	
	/**
	 * 优惠券列表页面
	 * @return
	 */
	@Action(value="toList",results={@Result(name="success",location="/WEB-INF/view/gf/couponList.jsp")})
	public String toList(){
		nowTime=MyDate.getMyDateLong();
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes"})
	@Action(value = "getCouponList",results = { @Result( type = "json") })  
	public String getCouponList() throws IOException{
		String json ="{}";
		int currPage = 1;
		String currPageStr = super.request.getParameter("currPage");
		if(StringUtils.hasText(currPageStr)){
			try {
				currPage = Integer.parseInt(currPageStr);
			} catch (Exception e) {
			}
		}
		this.pageSize = 10;
		int curPageIndex = (currPage-1)*this.pageSize;
		if(null==search){
			search = new Search();
			String couponName = super.request.getParameter("couponName");
			if(StringUtils.hasText(couponName)){
				couponName = URLDecoder.decode(couponName, "utf-8");
				search.setField01(couponName);
			}
			String effectiveTime =super.request.getParameter("effectiveTime");
			if(StringUtils.hasText(effectiveTime)){
				effectiveTime+=" 00:00";
				search.setField02(effectiveTime);
			}
			String expirationTime =super.request.getParameter("expirationTime");
			if(StringUtils.hasText(expirationTime)){
				expirationTime+=" 23:59";
				search.setField03(expirationTime);
			}
			
		}
		map =  couponService.getList(search,curPageIndex,this.pageSize);
		list = (List) map.get("list");//数据对象
		List<CouponVo> newList = new ArrayList<CouponVo>();
		if(false==CollectionUtils.isEmpty(list)){
			for(Object obj : list){
				CouponVo c = (CouponVo)obj;
				//查询优惠券发行数量
				long issueNum = this.couponService.getIssueNum(c);
				c.setIssueNum(issueNum);
				newList.add(c);
			}
		}
		page = (Page) map.get("page");//分页对象
		CouponContainerVo mv = new CouponContainerVo();
		mv.setCurrPage(currPage);
		mv.setPageSize(this.pageSize);
		mv.setTotalCount(null!=page?page.getTotalCount():0);
		mv.setResultData(newList);
		json = JSONUtil.formObjectToJsonObject(mv);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	@Action(value = "checkCouponGetState",results = { @Result( type = "json") })  
	public String checkCouponGetState() throws IOException{
		String couponId = super.request.getParameter("couponId");
		boolean getState = this.couponService.checkCouponGetState(couponId);
		String json = getState?"true":"false";
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
    	return null;
	}
	
	
	/**
	 * 预修改页面
	 * @return
	 */
	@Action(value="toUpdate",results={@Result(name="success",location="/WEB-INF/view/gf/updateCoupon.jsp")})
	public String toUpdate(){
		//加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		//如果优惠券未被领取，则可以修改
		String couponId = super.request.getParameter("couponId");
		boolean getState = this.couponService.checkCouponGetState(couponId);
		if(!getState){
			this.couponVo = this.couponService.getCoupon(couponId);
			return SUCCESS;
		}else{
			log.error(couponId+"已被领取");
			return INPUT;
		}
		
	}
	
	
	
	/**
	 * 关闭优惠券，将优惠券设为已关闭状态，且乘客领取的优惠券使用状态为已过期
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "closeCoupon",results = { @Result( type = "json") })  
    public String closeCoupon() throws IOException{
		String json ="error";
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		SysAdminVo admin = (SysAdminVo) httpSession.getAttribute("userInfo");// 登录用帐号资料
		String userId = admin == null ? "" : admin.getUserId();
		
		String couponId = super.request.getParameter("couponId");
		if(StringUtils.hasText(couponId)){
			int flag = this.couponService.closeCoupon(couponId);
			if(flag>0){
				json ="success";
				//添加日志
				giftService.addSysLog(flag, userId, "关闭优惠卷");
			}else{
				giftService.addSysLog(flag, userId, "关闭优惠卷");
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
    	return null;
    }
    
    
	/**
	 * 数据统计页面
	 * @return
	 */
	@Action(value="toCouponDate",results={@Result(name="success",location="/WEB-INF/view/gf/couponDate.jsp")})
	public String toCouponDate(){
		
		return SUCCESS;
	}
	
	/**
	 * 关闭优惠券弹窗
	 * @return
	 */
	@Action(value="toDelCoupon",results={@Result(name="success",location="/WEB-INF/view/gf/pop-delCp.jsp")})
	public String toDelCoupon(){
		
		return SUCCESS;
	}
		
	/**
	 * 跳转到优惠券配置页面
	 * @return
	 */
	@Action(value="setCoupon",results={@Result(name="success",location="/WEB-INF/view/gf/setCoupon.jsp")})
	public String setCoupon(){
		//加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	
	/**
	 * 跳转到选择礼品页面
	 * @return
	 */
	@Action(value="addSelectGift",results={@Result(name="success",location="/WEB-INF/view/gf/pop_addGift.jsp")})
	public String addSelectGift(){
		//选择礼品
		checkedGiftId = super.request.getParameter("checkBoxId");
		return SUCCESS;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "selectGift",results = { @Result( type = "json") })  
	public String selectGift() throws IOException{
		String json ="{}";
		int currPage = 1;
		String currPageStr = super.request.getParameter("currPage");
		if(StringUtils.hasText(currPageStr)){
			try {
				currPage = Integer.parseInt(currPageStr);
			} catch (Exception e) {
			}
		}
		this.pageSize = 10;
		int curPageIndex = (currPage-1)*this.pageSize;
		if(null==search){
			search = new Search();
		}
		map = giftService.listByPage(search, curPageIndex, pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		GiftContainerVo mv = new GiftContainerVo();
		mv.setCurrPage(currPage);
		mv.setPageSize(this.pageSize);
		mv.setTotalCount(null!=page?page.getTotalCount():0);
		mv.setResultData(list);
		json = JSONUtil.formObjectToJsonObject(mv);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	@Action(value = "addCoupon",results = { @Result( type = "json") })
	public String addCoupon() throws IOException{
		CouponVo vo = this.createCoupon();
		String json ="error";
		if(vo.getLimitNum()>vo.getIssueNum() && vo.getSendCouponType()==1){
			json = "countError";
		}else{
			if(null!=vo){
			      int flag = couponService.addCoupon(vo);
			      if(flag>0){
			    	  json ="success";
						//添加日志
						giftService.addSysLog(flag, vo.getCreateBy(), "优惠卷添加");
					}else{
						giftService.addSysLog(flag, vo.getCreateBy(), "优惠卷添加");
			      }
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	
	@Action(value = "updateCoupon",results = { @Result( type = "json") })
	public String updateCoupon() throws IOException{
		CouponVo vo = this.createCoupon();
		String json ="error";
		if(null!=vo){
			if(vo.getLimitNum()>vo.getIssueNum() && vo.getSendCouponType()==1){
				json = "countError";
			}else{
			      int flag = couponService.updateCoupon(vo);
			      if(flag>0){
			    	  json ="success";
						//添加日志
						giftService.addSysLog(flag, vo.getCreateBy(), "优惠卷修改");
					}else{
						giftService.addSysLog(flag, vo.getCreateBy(), "优惠卷修改");
			      }
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	/**
	 * 检测号码
	 */
	@Action(value = "checkTelephoneExist",results = { @Result( type = "json") })
	public String checkTelephoneExist() throws IOException{
		String json="success";
		String cityCode = super.request.getParameter("cityCode");
		String telephone = super.request.getParameter("telephones");//以;分隔手机号
		if(telephone==null ||"".equals(telephone) ||telephone.split(";").length<1){
			json="eorro";
		}else{
			String flag = iPassengerInfoService.checktelephone(telephone,cityCode);
			if(!"/".equals(flag)){
				json=flag;
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	/**
	 * 错误号码弹窗
	 * @return
	 */
	@Action(value="wrongPhonePage",results={@Result(name="success",location="../../view/gf/pop-telephoneList.jsp")})
	public String wrongPhonePage(){
		getResponse().setContentType("text/html;charset=UTF-8");
		String phone=request.getParameter("phones");
		String[] phones=phone.split("/");
		woringPhones=phones[0];
		if(phones.length==2){
			noPhones=phones[1];
		}else{
			noPhones="";
		}
		return SUCCESS;
	}
	
	private CouponVo createCoupon() {
		CouponVo v = new CouponVo();
		String couponId = super.request.getParameter("couponId");
		if(StringUtils.hasText(couponId)){
			v.setCouponId(couponId);
		}
		v.setCouponName(super.request.getParameter("couponName"));
		v.setCouponType(Integer.parseInt(super.request.getParameter("couponType")));
		v.setDownLineTime(super.request.getParameter("downLineTime"));
		v.setEffectiveTime(super.request.getParameter("effectiveTime"));
		v.setExpirationTime(super.request.getParameter("expirationTime"));
		v.setGiftIds(new String[]{super.request.getParameter("giftId")});
		v.setSendCouponType(Integer.parseInt(super.request.getParameter("sendCouponType")));//发放方式  1:用户领取  2:系统发放
		v.setUpLineTime(super.request.getParameter("upLineTime"));
		if(1==v.getSendCouponType()){
			v.setIssueNum(Integer.parseInt(super.request.getParameter("issueNum")));//发行数量，用户领取时填写
		}
		String limitNum = super.request.getParameter("limitNum");
		if(StringUtils.hasText(limitNum)){
			try {
				v.setLimitNum(Integer.parseInt(limitNum));
			} catch (Exception e) {
			}
		}
		v.setLink(super.request.getParameter("link"));
		String selectPass = super.request.getParameter("selectPass");
		if(StringUtils.hasText(selectPass)){
			v.setSelectPass(Integer.parseInt(selectPass));
		}
		if(3==v.getSelectPass()){
			//自定义用户
		   v.setTeles(super.request.getParameter("teles"));
		}
		String provinceCode = super.request.getParameter("provinceCode");
		String cityCode = super.request.getParameter("cityCode");
 		if(StringUtils.hasText(provinceCode)&&StringUtils.hasText(cityCode)){
 			v.setProvinceCode(Integer.parseInt(provinceCode));
 			v.setCityCode(Integer.parseInt(cityCode));
 			SysArea qArea = new SysArea();
 			qArea.setArearCode(cityCode);
 			qArea.setFdCode(provinceCode);
 			List<SysArea> qAreaList = iSysAreaService.querySysArea(qArea);
 			if(false==CollectionUtils.isEmpty(qAreaList)){
 				v.setCityName(qAreaList.get(0).getAreaName());
 			}
 		}
		v.setCreateOn(MyDate.getMyDateLong());
		v.setCreateBy(this.getSessionUserId());
		return v;
	}

	private String getSessionUserId(){
		SysAdminVo sysAdmin= (SysAdminVo) request.getSession().getAttribute(KEY_SESSION_USER);
		return null==sysAdmin?"":sysAdmin.getUserId();
	}
	
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}

	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}

	public CouponVo getCouponVo() {
		return couponVo;
	}

	public void setCouponVo(CouponVo couponVo) {
		this.couponVo = couponVo;
	}

	public String getWoringPhones() {
		return woringPhones;
	}

	public void setWoringPhones(String woringPhones) {
		this.woringPhones = woringPhones;
	}

	public String getNoPhones() {
		return noPhones;
	}

	public void setNoPhones(String noPhones) {
		this.noPhones = noPhones;
	}

	public String getCheckedGiftId() {
		return checkedGiftId;
	}

	public void setCheckedGiftId(String checkedGiftId) {
		this.checkedGiftId = checkedGiftId;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	
	
}
