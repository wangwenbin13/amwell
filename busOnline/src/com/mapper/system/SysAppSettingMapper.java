package com.mapper.system;

import java.util.List;

import com.bean.system.SysAppSetting;

public interface SysAppSettingMapper {

	public int add(SysAppSetting sysAppSetting);

	public int delete(SysAppSetting sysAppSetting);

	public int update(SysAppSetting sysAppSetting);

	public SysAppSetting get(SysAppSetting sysAppSetting);

	public List<SysAppSetting> list(SysAppSetting sysAppSetting);

}
