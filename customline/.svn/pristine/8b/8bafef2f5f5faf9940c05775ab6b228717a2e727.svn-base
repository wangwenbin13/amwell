package com.amwell.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.amwell.base.DaoSupport;
import com.amwell.vo.PageBean;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用于辅助拼接Sql语句
 * 
 * @author 胡双
 *
 */
public class QueryHelper {

	private String selectClause = "SELECT * ";// select子句
	private String fromClause; // from 子句
	private String whereClause = "";// where子句
	private String orderByClause = "";// Ordery By子句
	private String limitClause = "";// 分页字句
	private StringBuffer insertSql=new StringBuffer();
	
	private List<Object> parameters = new ArrayList<Object>();

	/**
	 * From子句
	 * 
	 * @param fromClause
	 *            from子句
	 */
	public QueryHelper(String fromClause) {
		this.fromClause = " " + fromClause;

	}

	/**
	 * 拼接limit子句
	 */
	public QueryHelper addLimitClause(int currentPage, int pageSize) {
		currentPage = (currentPage - 1) * pageSize;
		limitClause = " LIMIT " + currentPage + "," + pageSize;
		return this;
	}

	/**
	 * 设置selectClause
	 */
	public QueryHelper addSelectClause(String selectClause) {
		this.selectClause = " " + selectClause;
		return this;
	}

	/**
	 * 拼接Where子句
	 */
	public QueryHelper addCondition(String condition, Object... params) {
		if (whereClause.length() == 0) {
			whereClause = " WHERE " + condition;
		} else {
			whereClause += " AND " + condition;
		}

		if (params != null) {
			for (Object p : params) {
				parameters.add(p);
			}
		}
		return this;
	}

	/**
	 * 如果第一个参数为true,则拼接where字句
	 */
	public QueryHelper addCondition(boolean append, String condition, Object... params) {
		if (append) {
			addCondition(condition, params);
		}
		return this;
	}

	/**
	 * 拼接ORDER BY
	 * 
	 * @param propertyName
	 *            参与排序的属性名
	 * @param asc
	 *            true表示升序，false表示降序
	 * 
	 */
	public QueryHelper addOrderProperty(String propertyName, boolean asc) {

		if (orderByClause.length() == 0) {
			orderByClause = " ORDER BY " + propertyName + " " + (asc ? "ASC" : "DESC");
		} else {
			orderByClause += " , " + propertyName + " " + (asc ? "ASC" : "DESC");
		}
		return this;
	}

	/**
	 * 如果第一个参数为true，则拼接OrderBy子句
	 * 
	 * @param append
	 * @param propertyName
	 * @param asc
	 */
	public QueryHelper addOrderProperty(boolean append, String propertyName, boolean asc) {
		if (append) {
			addOrderProperty(propertyName, asc);
		}
		return this;
	}

	/**
	 * 获取参数列表
	 * 
	 * @return
	 */
	public List<Object> getParameters() {

		return parameters;
	}

	/**
	 * 获取Object[]数组的参数列表
	 */
	public Object[] getObjectArrayParams() {
		Object[] obj = parameters.toArray();
		for (int i = 0; i < obj.length; i++) {
			System.out.println(obj[i]);
		}
		return parameters.toArray();
	}

	/**
	 * 获取查询列表SQL语句
	 */
	public String getListSql() {
		return selectClause + fromClause + whereClause + orderByClause + limitClause;
	}

	/**
	 * 获取查询总数的SQL
	 */
	public String getCountSql() {
		return "select count(*) " + fromClause + whereClause;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		QueryHelper query = new QueryHelper("From sys_admin a,sys_role r").addSelectClause("SELECT * ")
				.addCondition("a.userName in (?) ", "(1,2)").addCondition(" r.roleId = ?", "xxxx")
				.addOrderProperty("a.createOn", false).addOrderProperty("r.createOn", true);

		System.out.println(query.getListSql());
		System.out.println(query.getCountSql());
		System.out.println(query.getParameters());
	}

	/**
	 * 获得插入SQL
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getInsertSql() {
		return insertSql.toString();
	}
}
