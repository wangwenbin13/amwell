package com.amwell.action.sys;

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

import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.service.ISysSensitiveWordService;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysSensitiveWordVo;
/**
 * 拼车敏感词过滤
 * @author Administrator
 *
 */
@ParentPackage("user-finit")
@Namespace("/sensitiveWord")
@Scope("prototype")
public class SysSensitiveWordAction extends BaseAction{
	
	private static final long serialVersionUID = 2894403510544644482L;
	
	private static final Logger log = Logger.getLogger(SysSensitiveWordAction.class);
	
	private static final String KEY_SESSION_USER="userInfo";
	
	@Autowired
	private ISysSensitiveWordService sysSensitiveWordService;

	private SysSensitiveWordVo sysSensitiveWordVo;
	
	@Action(value = "toSensitiveWord", results = { @Result(name = "success", location = "../../view/sysoperation/sensitiveWord.jsp") })
	public String toSensitiveWord(){
		List<SysSensitiveWordVo> sensitiveWordList = sysSensitiveWordService.getSensitiveWordList();
		if(false==CollectionUtils.isEmpty(sensitiveWordList)&&sensitiveWordList.size()>1){
			log.error("敏感词列表只能包含一条记录");
			throw new IllegalStateException("敏感词列表只能包含一条记录");
		}else{
			this.sysSensitiveWordVo = CollectionUtils.isEmpty(sensitiveWordList)?null:sensitiveWordList.get(0);
		}
		return SUCCESS;
	}

	@Action(value = "updateSensitiveWord",results = { @Result( type = "json") })
	public String updateSensitiveWord() throws IOException{
		int flag = sysSensitiveWordService.updateSensitiveWord(this.createSysSensitiveWordVo());
		String json = flag>0?"success":"error";
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	private SysSensitiveWordVo createSysSensitiveWordVo() {
		SysSensitiveWordVo v = new SysSensitiveWordVo();
		v.setWordId(super.request.getParameter("wordId"));
		v.setSensitiveWord(super.request.getParameter("sensitiveWord"));
		v.setOperateBy(this.getSessionUserId());
		v.setOperateOn(MyDate.getMyDateLong());
		return v;
	}
	
	private String getSessionUserId(){
		SysAdminVo sysAdmin= (SysAdminVo) request.getSession().getAttribute(KEY_SESSION_USER);
		return null==sysAdmin?"":sysAdmin.getUserId();
	}

	public SysSensitiveWordVo getSysSensitiveWordVo() {
		return sysSensitiveWordVo;
	}

	public void setSysSensitiveWordVo(SysSensitiveWordVo sysSensitiveWordVo) {
		this.sysSensitiveWordVo = sysSensitiveWordVo;
	}
	
	
}
