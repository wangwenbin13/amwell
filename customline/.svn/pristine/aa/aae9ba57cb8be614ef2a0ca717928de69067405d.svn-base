package com.amwell.service.impl;

import java.util.List;

import com.amwell.dao.IPinCheLineValidDateDao;
import com.amwell.service.IPinCheLineValidDateService;
import com.amwell.vo.pc.PcLineValidityVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service("pinCheLineValidDateService")
public class PinCheLineValidDateServiceImpl implements IPinCheLineValidDateService {
		
	@Autowired
    private IPinCheLineValidDateDao pinCheLineValidDateDao;

	public List<PcLineValidityVo> getValidDateList() {
		return pinCheLineValidDateDao.getValidDateList();
	}

	public int updateValidDate(PcLineValidityVo createPcLineValidityVo) {
		return pinCheLineValidDateDao.updateValidDate(createPcLineValidityVo);
	}

}
