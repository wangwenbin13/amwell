package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.dao.ISysAreaDao;
import com.amwell.utils.StringUtils;
import com.amwell.vo.SysArea;

@Repository("sysAreaDao")
public class SysAreaDaoImpl extends DaoSupport implements ISysAreaDao{

	public List<SysArea> querySysArea(SysArea sysArea){
		super.finit("sys_area");
		List<Object> paramList = new ArrayList<Object>();
		String cond = " where 1 = 1 ";
		if(!StringUtils.isEmpty(sysArea.getArearCode())){
			/**
			 * 地区编码
			 */
			cond += " and arearCode = ? ";
			paramList.add(sysArea.getArearCode());
		}
		if(!StringUtils.isEmpty(sysArea.getAreaName())){
			/**
			 * 地区名称
			 */
			cond += " and areaName = ? ";
			paramList.add(sysArea.getAreaName());
		}
		if(!StringUtils.isEmpty(sysArea.getFdCode())){
			/**
			 * 上级地区编码 省份父级ID默认为－1
			 */
			cond += " and fdCode = ? ";
			paramList.add(sysArea.getFdCode());
		}
		sql = " select * from sys_area ";
		sql += cond;
		Object[] params = paramList.toArray(new Object[]{});
		List<SysArea> sysAreas = tableDao.queryList(SysArea.class,sql, params);
		return sysAreas;
	}
	
	/**
	 * 根据地区编码查询地区信息
	 * 
	 * @param arearCode
	 * 
	 * @return
	 */
	public SysArea queryBeanByCode(String arearCode){
		super.finit("sys_area");
		String sql = "select * from sys_area where arearCode=?";
		Object[] params = new Object[1];
		params[0] = arearCode;
		return super.tableDao.queryBean(SysArea.class, sql, params);
	}
	
	/**
	 * 查询所有城市
	 * @return
	 */
	public List<SysArea> querySysArea(){
		super.finit("sys_area");
		String sql = " select * from sys_area where fdCode <> '-1' AND areaLevel = '2'";
		List<SysArea> sysAreas = tableDao.queryList(SysArea.class,sql);
		return sysAreas;
	}

}
