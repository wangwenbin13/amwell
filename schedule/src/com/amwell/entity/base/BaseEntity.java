package com.amwell.entity.base;

import java.io.Serializable;

import com.amwell.commons.JSONUtil;

public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -330438826779353995L;

	public String toString(){
		return JSONUtil.formObjectToJSONStr(this);
	}
	
}
