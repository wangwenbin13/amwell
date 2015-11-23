package com.amwell.dao;

import java.util.List;

import com.amwell.vo.pc.PcLineValidityVo;

public interface IPinCheLineValidDateDao {

	List<PcLineValidityVo> getValidDateList();

	int updateValidDate(PcLineValidityVo createPcLineValidityVo);

}
