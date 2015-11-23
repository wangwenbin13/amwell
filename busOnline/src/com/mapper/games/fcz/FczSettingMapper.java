package com.mapper.games.fcz;

import java.util.List;

import com.bean.games.fcz.FczSetting;

public interface FczSettingMapper {
	public void add(FczSetting fczSetting);

	public void delete(FczSetting fczSetting);

	public void update(FczSetting fczSetting);

	public List<FczSetting> list(FczSetting fczSetting);

	public FczSetting get(FczSetting fczSetting);
}
