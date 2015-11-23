package com.amwell.dao;

import java.util.List;

import com.amwell.vo.SysArea;

public interface ISysAreaDao {

	/**
	 * 查找区域
	 * @param search
	 * @param pageSizeIndex
	 * @param pageSize
	 * @return
	 */
	public List<SysArea> querySysArea(SysArea sysArea);
	
	/**
	 * 根据地区编码查询地区信息
	 * 
	 * @param arearCode
	 * 
	 * @return
	 */
	public SysArea queryBeanByCode(String arearCode);
	
	/**
	 * 查询所有城市
	 * @return
	 */
	public List<SysArea> querySysArea();
}
