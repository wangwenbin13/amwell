package com.amwell.action.gf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.log4j.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.commons.JSONUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ICouponStatService;
import com.amwell.vo.gf.CouponContainerVo;
import com.amwell.vo.gf.CouponStatVo;

/**
 * 
 * @author wangwenbin
 *
 * 2015-3-10
 */
/**
 * 优惠券统计
 */
@ParentPackage("user-finit")
@Namespace("/couponStat")
@Scope("prototype")
public class CouponStatAction extends BaseAction{
	
	private static final long serialVersionUID = 2128390298990509732L;

	private static final Logger log = Logger.getLogger(CouponStatAction.class);

	@Autowired
	private ICouponStatService couponStatService;
	
	private CouponStatVo couponStatVo;
	
	/**优惠券主键ID**/
	private String couponId;
	
	/**优惠券名称**/
	private String couponName;
	
	private int currentPageIndex=0;
	
	private int pageSize = 10;
	
	/**
	 * 统计优惠券数量
	 * @return
	 */
	@Action(value="stat",results={@Result(name="success",location="/WEB-INF/view/gf/couponCount.jsp")})
	public String stat(){
		if(null!=search && !StringUtils.isEmpty(search.getField01())){
			try {
				search.setField01(URLDecoder.decode(search.getField01(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				log.info("优惠券中文转码异常");
				e.printStackTrace();
			}
		}
		couponStatVo = couponStatService.queryCouponStatVo(search);
		return SUCCESS;
	}
	
	/**
	 * 数据统计页面
	 * @return
	 */
	@Action(value="toCouponDate",results={@Result(name="success",location="/WEB-INF/view/gf/couponDate.jsp")})
	public String toCouponDate(){
		if(StringUtils.isEmpty(couponId)){
			log.info("优惠券主键ID为空");
		}else{
			if(!StringUtils.isEmpty(couponName)){
				try {
					couponName =  URLDecoder.decode(couponName,"utf-8");
					if(null==search){
						search = new Search();
					}
					search.setField01(couponId);
					couponStatVo = couponStatService.queryCouponStatVoByCouponId(search);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 优惠券使用记录
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action(value = "toCouponUserRecord",results = { @Result( type = "json") })  
	public String toCouponUserRecord() throws IOException{
		String json ="{}";
		int currPage = 1;
		String currPageStr = super.request.getParameter("currPage");
		if(!StringUtils.isEmpty(currPageStr)){
			try {
				currPage = Integer.parseInt(currPageStr);
			} catch (Exception e) {
			}
		}
		this.pageSize = 10;
		int curPageIndex = (currPage-1)*this.pageSize;
		if(!StringUtils.isEmpty(search.getField02())){
			search.setField02(	URLDecoder.decode(search.getField02(), "utf-8"));
		}
		map =  couponStatService.queryCouponUserRecordList(search,curPageIndex,this.pageSize);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		CouponContainerVo mv = new CouponContainerVo();
		mv.setCurrPage(currPage);
		mv.setPageSize(this.pageSize);
		mv.setTotalCount(null!=page?page.getTotalCount():0);
		mv.setResultData(list);
		json = JSONUtil.formObjectToJsonObject(mv);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	

	public CouponStatVo getCouponStatVo() {
		return couponStatVo;
	}

	public void setCouponStatVo(CouponStatVo couponStatVo) {
		this.couponStatVo = couponStatVo;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
	
	
}
