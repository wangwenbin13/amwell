package com.amwell.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.base.DaoSupport;
import com.amwell.dao.IBalanceSetDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.vo.BalanceSetVo;

@Repository(value="balanceSetDao")
public class BalanceSetDaoImpl extends DaoSupport implements IBalanceSetDao{

	/*
	 * 创建保底结算模板
	 */
	public int addBalance(BalanceSetVo balanceSet) {
		super.finit("balance_set");
		return super.tableDao.updateData(balanceSet, "balance_set_id");
	}
	
	/* 
	 * 查询保底结算
	 */
	public Map<String, Object> getBalanceSetList(Search search,int currentPageIndex, int pageSize) {
		super.finit("balance_set");
		String sql="select * from balance_set where 1=1  ";
		List<Object> parames=new ArrayList<Object>();
		if(search!=null){
			if(search.getField01()!=null){
				sql+="and setName = ?";
				parames.add(search.getField01());
			}
		}
		Object[] param=parames.toArray(new Object[]{});
		List<BalanceSetVo> list=super.tableDao.queryByPage(BalanceSetVo.class, sql, currentPageIndex, pageSize, param);
		Page page=new Page(list, sql, currentPageIndex, pageSize, param);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	/* 
	 * 验证是否有模板名称重名
	 */
	public int getSetNameCount(BalanceSetVo balanceSet) {
		if(null==balanceSet||!StringUtils.hasText(balanceSet.getSetName())){
			throw new IllegalArgumentException("invalid parameter.");
		}
		super.finit("balance_set");
		String sql ="select balance_set_id from balance_set where 1=1 and setName=?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(balanceSet.getSetName());
		if(StringUtils.hasText(balanceSet.getBalance_set_id())){
			sql+=" and balance_set_id!=?";
			paramList.add(balanceSet.getBalance_set_id());
		}
		List<BalanceSetVo> resList = super.tableDao.queryList(BalanceSetVo.class, sql, paramList.toArray(new Object[]{}));
		return CollectionUtils.isEmpty(resList)?0:resList.size();
	}

}
