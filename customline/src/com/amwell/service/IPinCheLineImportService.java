package com.amwell.service;

import java.util.List;

import com.amwell.vo.pc.LineStationCarVo;

public interface IPinCheLineImportService {

	int importPinCheLine(List<LineStationCarVo> excelData);

	int addLine(LineStationCarVo lineStationCar);

}
