package com.amwell.action.sys;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.service.ISysDiscountService;
import com.amwell.vo.SysDiscountVo;

@ParentPackage("user-finit")
@Namespace("/sysDiscount")
@SuppressWarnings("all")
@Scope("prototype")
public class SysDiscountAction extends BaseAction {

	@Autowired
	private ISysDiscountService discountService;

	private SysDiscountVo discountVo;

	@Action(value="getSysDiscountDetail",results={@Result(name="success",location="../../view/user/newUserSet.jsp")})
	public String getSysDiscountDetail() {
		this.discountVo = this.discountService.getSysDiscountDetail();
		return SUCCESS;
	}

	@Action(value = "updateSysDiscount", results = { @Result(type = "json") })
	public String updateSysDiscount() throws IOException {
		String json = "error";
		SysDiscountVo vo = this.createSysDiscountVo();
		if (null != vo) {
			int flag = this.discountService.updateSysDiscount(vo);
			if (flag > 0) {
				json = "success";
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	private SysDiscountVo createSysDiscountVo() {
		SysDiscountVo v = new SysDiscountVo();
    	v.setDisprice(super.request.getParameter("disprice"));
        v.setDistimes(super.request.getParameter("distimes"));
        v.setId(super.request.getParameter("id"));
        v.setIswork(super.request.getParameter("iswork"));
		return v;
	}

	public SysDiscountVo getDiscountVo() {
		return discountVo;
	}

	public void setDiscountVo(SysDiscountVo discountVo) {
		this.discountVo = discountVo;
	}
	
	
}
