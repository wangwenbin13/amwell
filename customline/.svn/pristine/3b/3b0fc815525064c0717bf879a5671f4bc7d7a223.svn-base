package com.amwell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.ISysSensitiveWordDao;
import com.amwell.service.ISysSensitiveWordService;
import com.amwell.vo.SysSensitiveWordVo;

@Service("sysSensitiveWordService")
public class SysSensitiveWordServiceImpl implements ISysSensitiveWordService {

	@Autowired
	private ISysSensitiveWordDao sysSensitiveWordDao;
	
	public List<SysSensitiveWordVo> getSensitiveWordList() {
		return sysSensitiveWordDao.getSensitiveWordList();
	}

	public int updateSensitiveWord(SysSensitiveWordVo sysSensitiveWordVo) {
		return sysSensitiveWordDao.updateSensitiveWord(sysSensitiveWordVo);
	}

}
