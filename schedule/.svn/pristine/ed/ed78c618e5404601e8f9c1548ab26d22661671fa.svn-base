package com.amwell.dao;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.entity.UserVo;

public interface ILoginDao {

	public int Add(UserVo user);

	public UserVo Login(UserVo user);

	public Map<String, Object> listByPage(Search search, int p, int pageSize);

	public UserVo getUserById(String id);

	public int delUserById(String id);

}
