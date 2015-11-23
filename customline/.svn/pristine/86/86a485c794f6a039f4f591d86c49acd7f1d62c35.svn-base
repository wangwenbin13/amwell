package com.amwell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amwell.commons.QueryHelper;
import com.amwell.dao.IPublishLineDao;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.ILineCustomService;
import com.amwell.vo.LineEntity;
import com.amwell.vo.PageBean;
import com.amwell.vo.StationInfo;

@Service(value="LineCustomService")
public class LineCustomServiceImpl extends SysDaoImpl<LineEntity> implements ILineCustomService {
	
	@Autowired
	private IPublishLineDao publishLineDao;

	public PageBean getPageBean(int parseInt, int pageSize, QueryHelper query) {
		String pageSql = query.getListSql(); // >> 获取分页SQL
		Object[] pageParams = query.getObjectArrayParams(); // >>  对应的参数数组
		List<LineEntity> recordList = queryList(pageSql,pageParams); // >> 得到集合
		String countSql = query.getCountSql(); // >>  查询总数的SQL
		int recordCount = queryCountByCustomSqlAndParams(countSql, pageParams); // >>  得到总数
		PageBean pageBean = new PageBean(parseInt, pageSize, recordCount, recordList);
		return pageBean;
	}
	
	/**
	 * 得到公司对应的线路
	 */
	public List<LineEntity> queryCompanyLines(List<String> lineIdList) {
		Object[] params = new Object[lineIdList.size()];
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<lineIdList.size();i++){
			buf.append("?");
			if(i< lineIdList.size()-1){
				buf.append(",");
			}
			
			params[i] = lineIdList.get(i);
		}
		String sql = "SELECT c.lineBaseId,c.lineName,c.startArea,c.endArea FROM line_base_info c WHERE c.lineBaseId IN ("+buf.toString()+")";
		List<LineEntity> lineEntitieList = queryList(sql, params);
		for(LineEntity lineEntity : lineEntitieList){
			List<StationInfo> stationInfoList = publishLineDao.listStationInfoByLineId(lineEntity.getLineBaseId());
			StringBuffer nameArray = new StringBuffer();
			int index = 0;
			for(StationInfo stationInfo : stationInfoList){
				if(index!=0){
					nameArray.append(",");
				}
				index++;
				nameArray.append(stationInfo.getName());
			}
			lineEntity.setStationInfoes(nameArray.toString());
		}
		return lineEntitieList;
	}

}
