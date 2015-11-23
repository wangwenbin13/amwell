package com.amwell.service;

import java.util.List;

import com.amwell.vo.SysArea;

public interface ISysAreaService {


	/**
	 * 查找区域
	 * @param search
	 * @param pageSizeIndex
	 * @param pageSize
	 * @return
	 */
	public List<SysArea> querySysArea(SysArea sysArea);
	
	public SysArea queryBeanByCode(String arearCode);
	
	/**
	 * 查询所有城市
	 * @return
	 */
	public List<SysArea> querySysArea();
}
