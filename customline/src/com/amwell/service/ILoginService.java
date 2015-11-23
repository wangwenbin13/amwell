package com.amwell.service;

import java.util.Map;

import com.amwell.entity.Search;
import com.amwell.entity.UserVo;

public interface ILoginService {
	
	/*注册*/
	public int Add(UserVo user);
	
	/*登录*/
	public UserVo Login(UserVo user);
	
	/*列表*/
	public Map<String,Object> listByPage(Search search,int p,int pageSize);
	
	/*根据ID获取对象*/
	public UserVo getUserById(String id);
	
	/*根据ID删除对象*/
	public int delUserById(String id);
}
