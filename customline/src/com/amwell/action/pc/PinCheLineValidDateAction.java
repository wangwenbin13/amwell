package com.amwell.action.pc;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.service.IPinCheLineValidDateService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.pc.PcLineValidityVo;


/**
 * 拼车线路有效期
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/lineValidDate")
@Scope("prototype")
public class PinCheLineValidDateAction extends BaseAction {
	
	private static final long serialVersionUID = 6955529737519365140L;
	
	private static final Logger log = Logger.getLogger(PinCheLineValidDateAction.class);

	private static final String KEY_SESSION_USER="userInfo";
	
	@Autowired
	private IPinCheLineValidDateService pinCheLineValidDateService;
	
	private PcLineValidityVo pcLineValidityVo;
	
	@Action(value = "toLineValidDate", results = { @Result(name = "success", location = "../../view/pcLine/validDate.jsp") })
	public String toLineValidDate(){
		List<PcLineValidityVo> list = pinCheLineValidDateService.getValidDateList();
		if(false==CollectionUtils.isEmpty(list)&&list.size()>1){
			log.error("拼车有效期列表只能包含一条记录");
			throw new IllegalStateException("敏感词列表只能包含一条记录");
		}else{
			this.pcLineValidityVo = CollectionUtils.isEmpty(list)?null:list.get(0);
		}
		return SUCCESS;
	}

	
	@Action(value = "updateValidDate",results = { @Result( type = "json") })
	public String updateValidDate() throws IOException{
		String json = "error";
		String validityDays = super.request.getParameter("validityDays");
		if(StringUtils.hasText(validityDays)){
			Integer validityDay4I = null;
			try {
				validityDay4I = Integer.parseInt(validityDays);
			} catch (Exception e) {
			}
			if(null!=validityDay4I&&validityDay4I>0){
				PcLineValidityVo v = new PcLineValidityVo();
				v.setId(super.request.getParameter("id"));
				v.setValidityDays(validityDay4I);
				v.setOperateBy(this.getSessionUserId());
				v.setOperateOn(MyDate.getMyDateLong());
				int flag = pinCheLineValidDateService.updateValidDate(v);
				json = flag>0?"success":"error";
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	private String getSessionUserId(){
		SysAdminVo sysAdmin= (SysAdminVo) request.getSession().getAttribute(KEY_SESSION_USER);
		return null==sysAdmin?"":sysAdmin.getUserId();
	}



	public PcLineValidityVo getPcLineValidityVo() {
		return pcLineValidityVo;
	}

	public void setPcLineValidityVo(PcLineValidityVo pcLineValidityVo) {
		this.pcLineValidityVo = pcLineValidityVo;
	}
}
