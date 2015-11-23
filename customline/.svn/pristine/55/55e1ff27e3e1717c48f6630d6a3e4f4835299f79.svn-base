package com.amwell.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IMgrBussinessDao;
import com.amwell.entity.Search;
import com.amwell.service.IMgrbusinessService;
import com.amwell.utils.StringUtils;
import com.amwell.vo.MgrBusinessEntity;

@Service("mgrbussinessService")
public class MgrbussinessServiceImpl implements IMgrbusinessService{

	@Autowired
	private IMgrBussinessDao iMgrBussinessDao;
	
	public Map<String, Object> listByPage(Search search, int pageSizeIndex,
			int pageSize) {
		return iMgrBussinessDao.listByPage(search, pageSizeIndex, pageSize);
	}

	/**
	 * 添加商户
	 */
	public String addMgrBussiness(MgrBusinessEntity mgrBusinessEntity) {
		return iMgrBussinessDao.addMgrBussiness(mgrBusinessEntity);
	}

	/**
	 * 检查名称是否存在
	 * 0:不存在    1:存在
	 */
	public int checkNameExist(MgrBusinessEntity mgrBusinessEntity) {
		int statu = 0;
		int count = iMgrBussinessDao.checkNameExist(mgrBusinessEntity);
		if(!StringUtils.isEmpty(mgrBusinessEntity.getBusinessId())){
			/**
			 * 带ID检查,说明是修改
			 * */
			List<MgrBusinessEntity> mgrBusinessEntities = iMgrBussinessDao.getBusinessIds(mgrBusinessEntity);
			if(null!=mgrBusinessEntities && mgrBusinessEntities.size()>0){
				if(mgrBusinessEntities.size()>1){
					/**
					 * 记录数大于1,说明有数据存在
					 */
					statu = 1;
				}else if(mgrBusinessEntities.size()==1 && !mgrBusinessEntities.get(0).getBusinessId().equals(mgrBusinessEntity.getBusinessId())){
					/**
					 * 存在1条记录，但是不是当前的数据，说明数据已经存在
					 */
					statu = 1;
				}
			}
		}else{
			/**
			 * 添加
			 */
			if(count>=1){
				/**
				 * 记录数大于等于1,说明有数据存在
				 */
				statu = 1;
			}
		}
		return statu;
	}

	/**
	 * 商户详情
	 */
	public MgrBusinessEntity getMerchantDetail(String businessId) {
		return iMgrBussinessDao.getMerchantDetail(businessId);
	}

	/**
	 * 获取所有的商家名称
	 * type 
	 * 	1:支持上下班
	 * 	2:支持包车
	 */
	public List<String> getAllBusinessBrefNames(Integer type) {
		return iMgrBussinessDao.getAllBusinessBrefNames(type);
	}

}
