package com.amwell.service.impl;

import com.amwell.dao.ISysDiscountDao;
import com.amwell.service.ISysDiscountService;
import com.amwell.vo.SysDiscountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysDiscountService")
public class SysDiscountServiceImpl implements ISysDiscountService {

	@Autowired
	private ISysDiscountDao sysDiscountDao;
	
	public SysDiscountVo getSysDiscountDetail() {
		return sysDiscountDao.getSysDiscountDetail();
	}

	public int updateSysDiscount(SysDiscountVo vo) {
		return sysDiscountDao.updateSysDiscount(vo);
	}

}
