package com.amwell.service;

import java.util.List;
import java.util.Map;
import com.amwell.entity.Search;
import com.amwell.vo.LineCostSetVo;

/**
 * 线路成本设置信息
 * @author gxt
 *
 */
public interface ILineCostSetService {
	
	
	/**
	 * 多条件查询线路成本设置列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListByCondition(Search search,int currentPage, int pageSize);
	
	/**
	 * 多条件查询线路成本设置列表(pageSize>0分页查询，pageSize=0不分页查询)
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> getListAndHistory(Search search,int currentPage, int pageSize);
	
	/**
	 * 添加或修改线路及成本设置
	 * @param vo
	 * @return
	 */
	int updateLineCostSet(LineCostSetVo vo);
	
	/**
	 * 获取线路及成本设置历史信息
	 * @param lineBaseId
	 * @return
	 */
	List<LineCostSetVo> getLineAndCostHistory(String lineBaseId);
}
