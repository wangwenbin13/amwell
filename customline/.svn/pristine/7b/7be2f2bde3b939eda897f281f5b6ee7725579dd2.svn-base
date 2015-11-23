package com.amwell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.dao.IPinCheLineImportDao;
import com.amwell.service.IPinCheLineImportService;
import com.amwell.vo.pc.LineStationCarVo;

@Service("pinCheLineImportService")
public class PinCheLineImportServiceImpl implements IPinCheLineImportService {

	@Autowired
	private IPinCheLineImportDao pinCheLineImportDao;
	
	public int importPinCheLine(List<LineStationCarVo> excelData) {
		return pinCheLineImportDao.importPinCheLine(excelData);
	}

	public int addLine(LineStationCarVo lineStationCar) {
		return pinCheLineImportDao.addLine(lineStationCar);
	}

}
