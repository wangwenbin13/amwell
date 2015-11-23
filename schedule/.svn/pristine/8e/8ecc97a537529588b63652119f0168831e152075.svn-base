package com.amwell.action.sys;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.JSONUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.vo.MerchantSchedulingVo;
import com.amwell.vo.SysMgrAdminVo;

/**
 * 首页
 * 
 * @author 胡双
 *
 */
@ParentPackage("user-finit")
@Namespace("/homePage")
@SuppressWarnings("all")
public class SystemHomePageAction extends BaseAction {

	private static final Logger log = Logger.getLogger(SystemHomePageAction.class);

	@Autowired
	private ILineService lineService;

	@Action(value = "showHomePage", results = { @Result(name = "success", location = "/WEB-INF/view/homePage.jsp") })
	public String toHomePage() throws Exception {
		if (null == search) {
			search = new Search();
		}
		SysMgrAdminVo admin = super.getSessionUser();
		if (null != admin) {
			search.setField03(admin.getBusinessId());
			search.setField05("1");
			map = this.lineService.getMerchantLineList(search, 0, Integer.MAX_VALUE);
			list = (List) map.get("list");// 数据对象
			page = (Page) map.get("page");// 分页对象
		}
		return SUCCESS;
	}

	@Action(value = "getMerchantSchedulingList", results = { @Result(type = "json") })
	public void getMerchantSchedulingList() throws IOException {
		String json = "[]";
		SysMgrAdminVo admin = super.getSessionUser();
		if (null != admin) {
			String orderDate = super.request.getParameter("orderDate");
			if (StringUtils.isEmpty(orderDate)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date now = new Date();
				orderDate = sdf.format(now);
			}
			List<MerchantSchedulingVo> msList = this.lineService.getMerchantSchedulingList(admin.getBusinessId(),
					orderDate);
			if (false == CollectionUtils.isEmpty(msList)) {
				json = JSONUtil.formObjectsToJSONStr(msList);
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
	}
}
