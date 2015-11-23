package com.amwell.dao;

import java.sql.SQLException;

import com.amwell.vo.MgrBusinessEntity;

public interface IBusinessDao {
	public MgrBusinessEntity getBusinessById(String id) throws SQLException;
}
