package com.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bean.system.SysAppSetting;
import com.mapper.system.SysAppSettingMapper;

/**
 * App系统设置服务类
 * 
 * @author shawn.zheng
 * 
 */
@Service
@Transactional
public class SysAppSettingService {

	/**
	 * App系统设置Mapper
	 */
	@Autowired
	private SysAppSettingMapper sysAppSettingMapper;

	/**
	 * 获取app系统设置列表
	 * 
	 * @param sysAppSetting
	 *            app系统设置信息
	 * @return List<SysAppSetting>
	 */
	public List<SysAppSetting> list(SysAppSetting sysAppSetting) {
		return sysAppSettingMapper.list(sysAppSetting);
	}

}
