package com.amwell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ISysAreaDao;
import com.amwell.service.ISysAreaService;
import com.amwell.vo.SysArea;

@Service("sysAreaService")
public class SysAreaServiceImpl implements ISysAreaService {

	@Autowired
	private ISysAreaDao iSysAreaDao;
	
	public List<SysArea> querySysArea(SysArea sysArea) {
		return iSysAreaDao.querySysArea(sysArea);
	}
	
	
	public SysArea queryBeanByCode(String arearCode){
		return iSysAreaDao.queryBeanByCode(arearCode);
	}
	
	/**
	 * 查询所有城市
	 * @return
	 */
	public List<SysArea> querySysArea(){
		return iSysAreaDao.querySysArea();
	}

}
