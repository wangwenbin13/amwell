package com.amwell.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.InitializationAllDataService;
import com.amwell.vo.InitializationAllData;
@Service(value="allData")
@Transactional
public class InitializationAllDataImpl extends SysDaoImpl<InitializationAllData> implements InitializationAllDataService {
	
	
	/**
	 * 查询所有初始化数据列表
	 */
	public List<InitializationAllData> queryAllInitializationData() {
		String sql = "SELECT dataId, dataAlias, isExecute, sysType FROM sys_init_data";
		List<InitializationAllData> allDatas = queryList(sql, new Object[]{});
		
		return allDatas;
		
	}
	
	/**
	 * 关闭执行按钮
	 */
	public void updateIsExecute(String isExcete,String dataId,int sysType) {
		String sql = "UPDATE sys_init_data SET isExecute = ? WHERE dataId = ? and sysType = ?";
		Object[] params = {
				isExcete,
				dataId,
				sysType
		};
		
		updateObject(sql, params);
	}
}
