package com.amwell.action.sys;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.service.ISysAreaService;
import com.amwell.vo.SysArea;

@ParentPackage("user-finit")
@Namespace("/sysArea")
@SuppressWarnings("all")
@Scope("prototype")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class SysAreaAction extends BaseAction {

	@Autowired
	private ISysAreaService iSysAreaService;

	private List<SysArea> citySysAreas;

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
		JsonWriter.write(citySysAreas);
	}
	
	public List<SysArea> getCitySysAreas() {
		return citySysAreas;
	}

	public void setCitySysAreas(List<SysArea> citySysAreas) {
		this.citySysAreas = citySysAreas;
	}

}
