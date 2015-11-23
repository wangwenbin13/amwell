package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.amwell.dao.ISysParamDAO;
import com.amwell.entity.Search;
import com.amwell.service.ISysParamService;
import com.amwell.vo.SysParamVo;

@Service("sysParamService")
public class SysParamServiceImpl implements ISysParamService {

	@Autowired
	private ISysParamDAO sysParamDAO;
	
	public Map<String, Object> getSysParamList(Search search,int currentPageIndex, int pageSize) {
		return sysParamDAO.getSysParamList(search,currentPageIndex, pageSize);
	}

	public int addSysParam(SysParamVo sysParamVo) {
		return sysParamDAO.addSysParam(sysParamVo);
	}

	public String addSysParamWithValidate(SysParamVo sysParamVo) {
		String validateRes = validateSysParam(sysParamVo);
		if("OK".equals(validateRes)){
			if(this.addSysParam(sysParamVo)>0){
				return "OK";
			}else{
				return "error";
			}
		}else{
			return validateRes;
		}
	}

	/**
	 * 同种类型之下参数名称不能相同
	 */
	public String validateSysParam(SysParamVo sysParam) {
		if(null==sysParam){
			return "SysParamVoIsNull";
		}else{
			short paramType = sysParam.getParamType();
			if(paramType<=0){
				return "paramTypeIsEmpty";
			}
			String paraName = sysParam.getParamName();
			if(StringUtils.hasText(paraName)){
				int count = sysParamDAO.getSameSysParamCount(sysParam.getId(),sysParam.getParamType(),sysParam.getParamName());
				if(count>0){
					return "paraNameReplication";
				}
			}else{
				return "paraNameIsEmpty";
			}
			if(false==StringUtils.hasText(sysParam.getParamValue())){
				return "paramValueIsEmpty";
			}
			if(false==StringUtils.hasText(sysParam.getCreateBy())){
				return "createByIsEmpty";
			}
			if(false==StringUtils.hasText(sysParam.getUpdateBy())){
				return "updateByIsEmpty";
			}
			if(false==StringUtils.hasText(sysParam.getCreateOn())){
				return "createOnIsEmpty";
			}
			if(false==StringUtils.hasText(sysParam.getUpdateOn())){
				return "updateOnIsEmpty";
			}
		}
		return "OK";
	}

	public int updateSysParam(SysParamVo sysParamVo) {
		return sysParamDAO.updateSysParam(sysParamVo);
	}

	
}
