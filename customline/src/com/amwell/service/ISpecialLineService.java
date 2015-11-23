package com.amwell.service;

import java.util.List;

import com.amwell.commons.QueryHelper;
import com.amwell.vo.Company;
import com.amwell.vo.PageBean;

/**
 * 客户专线Service
 * @author 胡双
 *
 */
public interface ISpecialLineService {
	
	/**
	 * 查询所有客户专线公司
	 * @param parseInt
	 * @param pageSize
	 * @param query
	 * @return
	 */
	PageBean getPageBean(int parseInt, int pageSize, QueryHelper query);
	
	/**
	 * 查询号码是否存在
	 * @param telPhone
	 * @return
	 */
	boolean queryTelPhone(String telPhone);
	
	/**
	 * 添加入库
	 * @param c
	 * @return
	 */
	int addCompanyByLinesByEmploy(Company c);
	
	/**
	 * 删除公司
	 * @param cIds
	 * @return
	 */
	boolean deleteCompany(List<String> cIds);
	
	/**
	 * 查询公司信息
	 * @param c
	 * @return
	 */
	Company queryCompany(Company c,int count);
	
	/**
	 * 查询公司名称是否存在
	 * @param company
	 * @return
	 */
	Company companyNameIsExist(Company company);
	
	/**
	 * 修改公司信息
	 * @param c
	 * @return
	 */
	int updateCompany(Company c);

}
