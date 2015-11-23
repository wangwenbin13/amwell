package com.amwell.service;

import java.util.List;
import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.vo.MgrBusinessEntity;

public interface IMgrbusinessService {

	/**
	 * 查找商户列表
	 * @param search
	 * @param pageSizeIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> listByPage(Search search,int pageSizeIndex,int pageSize);

	/**
	 * 添加商户
	 * @param mgrBusinessEntity
	 */
	public String addMgrBussiness(MgrBusinessEntity mgrBusinessEntity);

	/**
	 * 检查名称是否存在
	 * 0:不存在    1:存在
	 * @param bussinessId
	 * @param name
	 * @return
	 */
	public int checkNameExist(MgrBusinessEntity mgrBusinessEntity);

	/**
	 * 商户详情
	 * @param businessId
	 * @return
	 */
	public MgrBusinessEntity getMerchantDetail(String businessId);

	/**
	 * 获取所有的商家名称
	 * type 
	 * 	1:支持上下班
	 * 	2:支持包车
	 * @return
	 */
	public List<String> getAllBusinessBrefNames(Integer type);
}
