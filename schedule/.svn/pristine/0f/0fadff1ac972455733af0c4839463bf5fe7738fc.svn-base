package com.amwell.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ILoginDao;
import com.amwell.entity.Search;
import com.amwell.entity.UserVo;
import com.amwell.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private ILoginDao ser;

	/**
	 * 注册
	 */
	public int Add(UserVo user) {
		return ser.Add(user);
	}

	/**
	 * 登录
	 */

	public UserVo Login(UserVo user) {

		return ser.Login(user);
	}

	/**
	 * 列表
	 */

	public Map<String, Object> listByPage(Search search, int p, int pageSize) {

		return ser.listByPage(search, p, pageSize);
	}

	public int delUserById(String id) {
		return ser.delUserById(id);
	}

	public UserVo getUserById(String id) {
		return ser.getUserById(id);
	}
}
