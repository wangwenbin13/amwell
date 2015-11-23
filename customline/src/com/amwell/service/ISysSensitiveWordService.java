package com.amwell.service;

import java.util.List;

import com.amwell.vo.SysSensitiveWordVo;

public interface ISysSensitiveWordService {

	List<SysSensitiveWordVo> getSensitiveWordList();

	int updateSensitiveWord(SysSensitiveWordVo sysSensitiveWordVo);

}
