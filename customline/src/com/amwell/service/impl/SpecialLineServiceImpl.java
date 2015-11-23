package com.amwell.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import com.amwell.commons.QueryHelper;
import com.amwell.commons.StringUtil;
import com.amwell.dao.impl.SysDaoImpl;
import com.amwell.service.ISpecialLineService;
import com.amwell.vo.Company;
import com.amwell.vo.PageBean;
import com.amwell.vo.SysAdminVo;

/**
 * 客户专线Service
 * @author 胡双
 *
 */
@Service(value="specialLine")
public class SpecialLineServiceImpl extends SysDaoImpl<Company> implements ISpecialLineService {

	
	public PageBean getPageBean(int parseInt, int pageSize, QueryHelper query) {
		String pageSql = query.getListSql(); // >> 获取分页SQL
		Object[] pageParams = query.getObjectArrayParams(); // >>  对应的参数数组
		List<Company> recordList = queryList(pageSql,pageParams); // >> 得到集合
		String countSql = query.getCountSql(); // >>  查询总数的SQL
		int recordCount = queryCountByCustomSqlAndParams(countSql, pageParams); // >>  得到总数
		PageBean pageBean = new PageBean(parseInt, pageSize, recordCount, recordList);
		return pageBean;
	}
	
	//查询号码是否存在
	public boolean queryTelPhone(String telPhone) {
		String sql = "SELECT COUNT(*)  FROM company_passenger WHERE telephone = ?";
		int count = queryCountByCustomSqlAndParams(sql, new Object[]{telPhone});
		if(count >0 ){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 添加入库
	 */
	public int addCompanyByLinesByEmploy(Company c) {
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		//如果有公司ID
		if(null == c.getCompanyId() || "".equals(c.getCompanyId())){
			c.setCompanyId(StringUtil.generateSequenceNo());
			//添加公司
			String insertCompanySql = "INSERT INTO company (companyId,companyName,companyProvince,companyCity,createBy,createOn) VALUES (?,?,?,?,?,?)";
			Object[] companyObj = new Object[]{
					c.getCompanyId(),
					c.getCompanyName(),
					c.getCompanyProvince(),
					c.getCompanyCity(),
					c.getCreateBy(),
					c.getCreateOn()
			};
			sqlList.add(insertCompanySql);
			paramsList.add(companyObj);
		}
		
		//添加公司线路关系
		String companyLineSql = "INSERT INTO company_line (companyLineId,companyId,lineBaseId) VALUES (?,?,?)";
		for(int i=0;i<c.getLineIdList().size();i++){
			String lineId = c.getLineIdList().get(i);
			Object[] companyLineObj = new Object[]{
					StringUtil.generateSequenceNo(),
					c.getCompanyId(),
					lineId
			};
			sqlList.add(companyLineSql);
			paramsList.add(companyLineObj);
		}
		
		//添加公司员工关系
		String employTelSql = "INSERT INTO company_passenger (companyPassengerId,companyId,telephone) VALUES (?,?,?)";
		for(int i=0;i<c.getPassengerTelList().size();i++){
			String employTel = c.getPassengerTelList().get(i);
			Object[] employComObj = new Object[]{
					StringUtil.generateSequenceNo(),
					c.getCompanyId(),
					employTel
			};
			sqlList.add(employTelSql);
			paramsList.add(employComObj);
		}
		
		boolean flag = tranOperator(sqlList, paramsList);
		if(flag){
			return sqlList.size();
		}
		return 0;
	}
	
	/**
	 * 删除公司
	 */
	public boolean deleteCompany(List<String> cIds) {

		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		String questionMark = "";
		Object[] params = new Object[cIds.size()];
		for(int i=0;i<cIds.size();i++){
			questionMark +="?,";
			params[i] = cIds.get(i);
		}
		questionMark = questionMark.substring(0, questionMark.length()-1);
		
		// 删除公司线路
		String delCompanyLine = "DELETE FROM company_line WHERE companyId IN ("+questionMark+")";
		sqlList.add(delCompanyLine);
		paramsList.add(params);
		
		// 删除公司员工
		String delCompanyEmploy = "DELETE FROM company_passenger WHERE companyId IN ("+questionMark+")";
		sqlList.add(delCompanyEmploy);
		paramsList.add(params);
		
		//删除公司
		String delCompany = "DELETE FROM company WHERE companyId IN ("+questionMark+")";
		sqlList.add(delCompany);
		paramsList.add(params);
		
		return tranOperator(sqlList, paramsList);
	}

	/**
	 * 查询公司信息
	 * @param c 公司信息
	 * @param count  参数的个数
	 */
	public Company queryCompany(Company c,int count) {
		
		Object[] params = new Object[count];
		
		StringBuffer buf = new StringBuffer("SELECT * FROM company WHERE 1= 1 ");
		
		int index = 0;
		// 公司ID
		if(null != c.getCompanyId() && !"".equals(c.getCompanyId())){
			params[index++] = c.getCompanyId();
			buf.append("AND companyId = ?");
		}
		
		//公司名称
		if(null != c.getCompanyName() && !"".equals(c.getCompanyName())){
			params[index++] = c.getCompanyName();
			buf.append("AND companyName =  ?");
		}
		
		
		//查询公司
		Company rCompany = queryObject(buf.toString(), params);
		
		
		//查询拥有哪些线路
		List<Company> lines = queryList("SELECT lineBaseId FROM company_line WHERE companyId = ?", new Object[]{rCompany.getCompanyId()});
		
		for(int i=0;i<lines.size();i++){
			Company cline = lines.get(i);
			rCompany.getLineIdList().add(cline.getLineBaseId());
		}
		
		// 查询拥有哪些员工号码
		List<Company> tels = queryList("SELECT telephone FROM company_passenger WHERE  companyId = ?", new Object[]{rCompany.getCompanyId()});
		for(int i=0;i<tels.size();i++){
			Company cTels = tels.get(i);
			rCompany.getPassengerTelList().add(cTels.getTelephone());
		}

		return rCompany;
	}

	public Company companyNameIsExist(Company company) {
		Object[] params = new Object[1];
		
		StringBuffer buf = new StringBuffer("SELECT * FROM company WHERE 1= 1 ");
		
		int index = 0;
		
		//公司名称
		if(null != company.getCompanyName() && !"".equals(company.getCompanyName())){
			params[index++] = company.getCompanyName();
			buf.append("AND companyName =  ?");
		}
		
		//查询公司
		Company rCompany = queryObject(buf.toString(), params);
		
		return rCompany;
	}

	/**
	 * 修改公司
	 */
	public int updateCompany(Company c) {
		
		List<String> sqlList = new ArrayList<String>();
		List<Object[]> paramsList = new ArrayList<Object[]>();
		
		// 删除公司线路
		String sql1 = "DELETE FROM company_line WHERE companyId = ?";
		Object[] param1 = new Object[]{c.getCompanyId()};
		sqlList.add(sql1);
		paramsList.add(param1);
		// 删除公司员工关系
		String sql2 = "DELETE FROM company_passenger WHERE companyId = ?";
		Object [] param2 = new Object[]{c.getCompanyId()};
		sqlList.add(sql2);
		paramsList.add(param2);
		// 更新公司信息
		String sql3 = "UPDATE company SET companyName = ?,companyProvince = ?,companyCity = ?,updateBy = ?,updateOn = ? WHERE companyId = ?";
		Object[] param3 = new Object[]{
				
				c.getCompanyName(),
				c.getCompanyProvince(),
				c.getCompanyCity(),
				c.getUpdateBy(),
				c.getUpdateOn(),
				c.getCompanyId()
		};
		sqlList.add(sql3);
		paramsList.add(param3);
		//添加公司线路关系
		String companyLineSql = "INSERT INTO company_line (companyLineId,companyId,lineBaseId) VALUES (?,?,?)";
		for(int i=0;i<c.getLineIdList().size();i++){
			String lineId = c.getLineIdList().get(i);
			Object[] companyLineObj = new Object[]{
					StringUtil.generateSequenceNo(),
					c.getCompanyId(),
					lineId
			};
			sqlList.add(companyLineSql);
			paramsList.add(companyLineObj);
		}
		
		//添加公司员工关系
		String employTelSql = "INSERT INTO company_passenger (companyPassengerId,companyId,telephone) VALUES (?,?,?)";
		for(int i=0;i<c.getPassengerTelList().size();i++){
			String employTel = c.getPassengerTelList().get(i);
			Object[] employComObj = new Object[]{
					StringUtil.generateSequenceNo(),
					c.getCompanyId(),
					employTel
			};
			sqlList.add(employTelSql);
			paramsList.add(employComObj);
		}
		
		
		boolean flag = tranOperator(sqlList, paramsList);
		if(flag){
			return 1;
		}
		
		return 0;
	}
	
	
}
