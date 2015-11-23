package com.pig84.ab.dao;

import com.pig84.ab.vo.UserIdMap;

public interface UserIdMapDao {

	public void add(UserIdMap userIdMap);

	public UserIdMap getByClientIdAndChannelId(String clientId,String channelId);
	
}
