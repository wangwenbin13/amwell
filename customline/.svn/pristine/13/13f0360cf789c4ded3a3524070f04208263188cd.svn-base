package com.amwell.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.amwell.base.DaoSupport;
import com.amwell.dao.ILineCostSetDao;
import com.amwell.entity.Search;
import com.amwell.service.ILineCostSetService;
import com.amwell.vo.LineCostSetVo;

@Repository("lineCostSetService")
public class LineCostSetServiceImpl extends DaoSupport implements ILineCostSetService{

	@Autowired
	private ILineCostSetDao lineCostSetDao;
	
	/**
	 * 多条件查询线路成本设置列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize){
		return lineCostSetDao.getListByCondition(search, currentPage, pageSize);
	}
	
	/**
	 * 多条件查询线路成本设置列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public Map<String, Object> getListAndHistory(Search search,int currentPage, int pageSize){
		return lineCostSetDao.getListAndHistory(search, currentPage, pageSize);
	}

	/**
	 * 添加或修改线路及成本设置
	 * @param vo
	 * @return
	 */
	@Override
	public int updateLineCostSet(LineCostSetVo vo) {
		return lineCostSetDao.updateLineCostSet(vo);
	}
	
	/**
	 * 获取线路及成本设置历史信息
	 * @param lineBaseId
	 * @return
	 */
	@Override
	public List<LineCostSetVo> getLineAndCostHistory(String lineBaseId){
		return lineCostSetDao.getLineAndCostHistory(lineBaseId);
	}
	
}
