package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.SqlBuilder;
import com.amwell.dao.IMgrBussinessDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.utils.StringUtils;
import com.amwell.vo.MgrBusinessEntity;

@Repository("mgrBussinessDao")
public class MgrBussinessDaoImpl extends DaoSupport implements IMgrBussinessDao{

	/**
	 * 查找商户列表
	 * @param search
	 * @param pageSizeIndex
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> listByPage(Search search, int pageSizeIndex,
			int pageSize) {
		super.finit("mgr_business as mgr, mgr_user as user,sys_admin as sys");
		String cond = " ";
		List<Object> paramList = new ArrayList<Object>();
		if(search!= null){
			if(search.getField01()!= null && !search.getField01().equals("")){
				cond+= " and left(mgr.createOn,10) >=? ";
				paramList.add(search.getField01().trim());
			}
			if(search.getField02()!= null && !search.getField02().equals("")){
				cond+= " and left(mgr.createOn,10) <=? ";
				paramList.add(search.getField02().trim());
			}
			if(search.getField03()!= null && !search.getField03().equals("")){
				cond+= " and user.loginName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField03().trim()));
			}
			if(search.getField04()!= null && !search.getField04().equals("")){
				cond+= " and mgr.brefName like ? ";
				paramList.add(SqlBuilder.getSqlLikeValue(search.getField04().trim()));
			}
			if(search.getField05()!= null && !search.getField05().equals("") && !"all".equals(search.getField05())){
				cond+= " and user.status = ?";
				paramList.add(search.getField05().trim());
			}
			if(!StringUtils.isEmpty(search.getField06()) && !"0".equals(search.getField06())){
				if(!"3".equals(search.getField06())){
					cond+= " and ( mgr.businessType = ? or mgr.businessType = 3 )";
				}else {
					cond+= " and mgr.businessType = ?";
				}
				paramList.add(search.getField06().trim());
			}
			if(!StringUtils.isEmpty(search.getField07()) && !"0".equals(search.getField07())){
				cond+= " and mgr.provinceCode = ?";
				paramList.add(search.getField07().trim());
			}
			if(!StringUtils.isEmpty(search.getField08()) && !"0".equals(search.getField08())){
				cond+= " and mgr.areaCode = ?";
				paramList.add(search.getField08().trim());
			}
			if(!StringUtils.isEmpty(search.getField09())){
				cond +=" and mgr.businessId !=? ";
				paramList.add(search.getField09().trim());
			}
		}
		Object[] params = paramList.toArray(new Object[]{});
		tableDao.setPageSize(pageSize);
		sql = " select mgr.provinceCode,mgr.areaCode,mgr.businessType,mgr.businessId,mgr.brefName,user.loginName,user.userName as contacts,user.telephone as contactsPhone,sys.userName as address,mgr.createOn,user.status as accountStatus,mgr.createBy ";
		sql +=" from mgr_business as mgr, mgr_user as user,sys_admin as sys where 1=1 and mgr.businessId = user.businessId and mgr.createBy = user.createBy and mgr.createBy = sys.userId ";
		sql += cond;
		sql += " order by mgr.createOn desc ";
		List<MgrBusinessEntity> list = tableDao.queryByPage(MgrBusinessEntity.class, sql,pageSizeIndex,params);
		page = new Page(list,sql,pageSizeIndex,pageSize,params);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/**
	 * 添加商户
	 */
	public String addMgrBussiness(MgrBusinessEntity mgrBusinessEntity) {
		super.finit("mgr_business");
		String bussinessId = mgrBusinessEntity.getBusinessId();
		int flag = tableDao.updateData(mgrBusinessEntity,"businessId");
		if(StringUtils.isEmpty(bussinessId)){
			/**
			 * 添加商户
			 */
			if(-1==flag){
				AppendLog(mgrBusinessEntity.getCreateBy(), "商户管理", "添加商户失败");
			}else{
				AppendLog(mgrBusinessEntity.getCreateBy(), "商户管理", "添加商户成功");
			}
		}else{
			/**
			 * 修改商户
			 */
			if(-1==flag){
				AppendLog(mgrBusinessEntity.getUpdateBy(), "商户管理", "修改商户失败");
			}else{
				AppendLog(mgrBusinessEntity.getUpdateBy(), "商户管理", "修改商户成功");
			}
		}
		String bussinessid = mgrBusinessEntity.getBusinessId();
		return bussinessid;
	}
	
	/**
	 * 检查名称是否存在
	 */
	public int checkNameExist(MgrBusinessEntity mgrBusinessEntity) {
		super.finit("mgr_business");
		List<Object> paramList = new ArrayList<Object>();
		String cond = " ";
		if(!StringUtils.isEmpty(mgrBusinessEntity.getLoginName())){
			/**
			 * 登录名
			 */
			cond += " and user.loginName = ? ";
			paramList.add(mgrBusinessEntity.getLoginName());
		}else if(!StringUtils.isEmpty(mgrBusinessEntity.getName())){
			/**
			 * 企业全称
			 */
			cond += " and mgr.name = ? ";
			paramList.add(mgrBusinessEntity.getName());
		}else if(!StringUtils.isEmpty(mgrBusinessEntity.getBrefName())){
			/**
			 * 简称
			 */
			cond += " and mgr.brefName = ? ";
			paramList.add(mgrBusinessEntity.getBrefName());
		} 
		sql = " SELECT mgr.businessId from mgr_business as mgr, mgr_user as user where 1=1 and mgr.businessId = user.businessId and mgr.createBy = user.createBy ";
		sql += cond;
		
		Object[] params = paramList.toArray(new Object[]{});
		int count = tableDao.queryCount(sql, params);
		return count;
	}

	/**
	 *  商户详情
	 */
	public MgrBusinessEntity getMerchantDetail(String businessId) {
		super.finit(" mgr_business ");
		String sql = " select mgr.businessType,mgr.businessId,mgr.brefName,user.loginName,user.userName as contacts,user.telephone as contactsPhone,mgr.createBy,mgr.createOn,user.status as accountStatus,mgr.name,mgr.address,mgr.remark,mgr.provinceCode,mgr.areaCode from mgr_business as mgr, mgr_user as user,sys_admin as sys where 1=1 and mgr.businessId = user.businessId and mgr.createBy = user.createBy and mgr.createBy = sys.userId ";
		List<Object> paramList = new ArrayList<Object>();
		String cond = " ";
		cond += " and mgr.businessId = ? ";
		sql += cond;
		paramList.add(businessId);
		Object[] params = paramList.toArray(new Object[]{});
		MgrBusinessEntity mgrBusinessEntity= tableDao.queryBean(MgrBusinessEntity.class, sql,params);
		return mgrBusinessEntity;
	}

	/**
	 * 根据名称查找对应的商户集合
	 */
	public List<MgrBusinessEntity> getBusinessIds(MgrBusinessEntity mgrBusinessEntity) {
		super.finit("mgr_business");
		List<Object> paramList = new ArrayList<Object>();
		String cond = " where 1 = 1 ";
		if(!StringUtils.isEmpty(mgrBusinessEntity.getName())){
			/**
			 * 企业全称
			 */
			cond += " and name = ? ";
			paramList.add(mgrBusinessEntity.getName());
		}else if(!StringUtils.isEmpty(mgrBusinessEntity.getBrefName())){
			/**
			 * 简称
			 */
			cond += " and brefName = ? ";
			paramList.add(mgrBusinessEntity.getBrefName());
		}else if(!StringUtils.isEmpty(mgrBusinessEntity.getLoginName())){
			/**
			 * 登录名
			 */
			cond += " and loginName = ? ";
			paramList.add(mgrBusinessEntity.getLoginName());
		}
		sql = " select * from mgr_business ";
		sql += cond;
		Object[] params = paramList.toArray(new Object[]{});
		List<MgrBusinessEntity> mgrBusinessEntities = tableDao.queryList(MgrBusinessEntity.class, sql, params);
		return mgrBusinessEntities;
	}

	/**
	 * 获取所有的商家名称
	 * type 
	 * 	1:支持上下班
	 * 	2:支持包车
	 */
	public List<String> getAllBusinessBrefNames(Integer type) {
		super.finit("mgr_business");
		if(type==1){
			sql = " select brefName from mgr_business where 1=1 and businessType in(1,3) ";
		}else if(type==2){
			sql = " select brefName from mgr_business where 1=1 and businessType in(2,3) ";
		}
		List<String> businessBrefNames = tableDao.queryList(String.class, sql);
		return businessBrefNames;
	}

}
