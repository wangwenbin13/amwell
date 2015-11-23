package com.amwell.service;

import java.util.List;

import com.amwell.vo.InitializationAllData;

public interface InitializationAllDataService {
	
	/**
	 * 查询所有初始化的数据列表
	 * @return
	 */
	List<InitializationAllData> queryAllInitializationData();
	
	/**
	 * 改变执行按钮
	 * @param string
	 */
	void updateIsExecute(String isExecute,String dataId,int sysType);

}
