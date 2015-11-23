package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.ISysParamDAO;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.SysParamVo;

@Repository("sysParamDAO")
public class SysParamDAOImpl extends DaoSupport implements ISysParamDAO {

	public Map<String, Object> getSysParamList(Search search,int currentPageIndex, int pageSize) {
		super.finit("sys_param");
		StringBuffer sql = new StringBuffer("select * from sys_param a where 1=1");
	    List<Object> paramList = new ArrayList<Object>();
	    if(null!=search){
	    	if(StringUtils.hasText(search.getField01())){
	    			sql.append(" and a.paramName like ?");
	    			paramList.add(SqlBuilder.getSqlLikeValue(search.getField01().trim()));
		    }
	    	if(StringUtils.hasText(search.getField02())){
    			sql.append(" and a.paramValue like ?");
    			paramList.add(SqlBuilder.getSqlLikeValue(search.getField02().trim()));
	        }
	    	if(StringUtils.hasText(search.getField03())){
	    		sql.append(" and a.paramType=?");
	    		paramList.add(search.getField03().trim());
		    }
	    	if(StringUtils.hasText(search.getField04())){
	    		sql.append(" and a.paramStatus=?");
	    		paramList.add(search.getField04().trim());
	    	}
	    	
	    }
	    sql.append(" order by a.updateOn desc");
        Object[] params = paramList.toArray(new Object[]{});
		List<SysParamVo> list = super.tableDao.queryByPage(SysParamVo.class, sql.toString(), currentPageIndex,pageSize, params);
		Map<String, Object> res = new HashMap<String,Object>();
		Page page = new Page(list,sql.toString(),currentPageIndex,pageSize,params);
		res.put("page", page);
		res.put("list",list);
		return res;
	}

	public int getSameSysParamCount(String id, short paramType, String paramName) {
		if(StringUtils.hasText(paramName)&&paramType>0){
			super.finit("sys_param");
			StringBuffer sql = new StringBuffer("select 1 from sys_param a where 1=1 and a.paramStatus=1 and a.paramType=? and a.paramName=?");
			List<Object> paramList = new ArrayList<Object>();
			paramList.add(paramType);
			paramList.add(paramName);
			if(StringUtils.hasText(id)){
			   //更新
				sql.append(" a.id!=?");
				paramList.add(id);
			}
			return super.tableDao.queryCount(sql.toString(),paramList.toArray(new Object[]{}) );
		}
		throw new IllegalArgumentException("paramType and paramName can't be empty.");
	}

	public int addSysParam(SysParamVo sysParamVo) {
		if(null!=sysParamVo){
			super.finit("sys_param");
			return super.tableDao.updateData(sysParamVo,"id");
		}
		return 0;
	}

	public int updateSysParam(SysParamVo sysParamVo) {
		if(null!=sysParamVo){
			super.finit("sys_param");
			return super.tableDao.updateData(sysParamVo,"id");
		}
		return 0;
	}

}
