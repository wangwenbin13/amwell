package com.amwell.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.amwell.base.DaoSupport;
import com.amwell.commons.MyDate;
import com.amwell.dao.ILoginDao;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.entity.UserVo;

@Repository
public class ILoginDaoImpl extends DaoSupport implements ILoginDao {

	public int Add(UserVo user) {
		super.finit("uservo");
		user.setCdate(MyDate.getMyDateLong());
		int flag = tableDao.updateData(user);
		return flag;
	}

	public UserVo Login(UserVo user) {
		super.finit("uservo");
		args = new Object[2];
		args[0] = user.getUsername();
		args[1] = user.getPwd();
		sql = "select * from uservo where username = ? and pwd = ?";
		UserVo user_login = tableDao.queryBean(UserVo.class, sql, args);
		if (user_login != null) {
			return user_login;
		} else {
			return null;
		}
	}

	public Map<String, Object> listByPage(Search search, int p, int pageSize) {
		String cond = " where 1 = 1 ";
		if (search != null) {
			if (search.getField01() != null && !search.getField01().equals("")) {
				cond += " and username like '%" + search.getField01() + "%'";
			}
			if (search.getField02() != null && !search.getField02().equals("")) {
				cond += " and pwd like '%" + search.getField02() + "%'";
			}
			if (search.getField03() != null && !search.getField03().equals("")) {
				cond += " and realname like '%" + search.getField03() + "%'";
			}
		}
		tableDao.setPageSize(pageSize);
		sql = "select * from uservo " + cond;
		list = tableDao.queryByPage(UserVo.class, sql, p);
		page = new Page(list, sql, p, pageSize);
		map.put("list", list);
		map.put("page", page);
		return map;
	}

	public int delUserById(String id) {
		super.finit("uservo");
		return tableDao.deleteByid(UserVo.class, id, "test_id");
	}

	public UserVo getUserById(String id) {
		super.finit("uservo");
		return tableDao.queryBean(UserVo.class, "select * from uservo where rec_id = '" + id + "'");
	}
}
