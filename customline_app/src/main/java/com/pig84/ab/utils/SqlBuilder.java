package com.pig84.ab.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangqiang
 * Sql构建类
 *
 */
@SuppressWarnings("all")
public class SqlBuilder {
	
	private static final Logger logger = LoggerFactory.getLogger(SqlBuilder.class);
	
	private Field[] fields;
	private Object[] params;

	public Object[] getParams() {
		return params;
	}


	public void setParams(Object[] params) {
		this.params = params;
	}
	
	/**
	 * 拼接插入语句
	 * @param obj
	 * @return
	 */
	public String getInsertSQL(Object obj,String tableName) {
		Row row = new Row();
		row = fromValueObject(obj);
		params = new Object[row.length()];
		String value = "";
		// 拼SQL语句
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append("" + tableName + "");
		sql.append(" (");
		for (int i = 0; i < row.length(); i++) {
			sql.append("" + row.getKey(i) + "");
			if (i < row.length() - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		sql.append(" VALUES(");
		for (int i = 0; i < row.length(); i++) {
			value = Objects.toString(row.get(row.getKey(i)) == null?"":row.get(row.getKey(i)));
			params[i] = value;
			sql.append("?");
			if (i < row.length() - 1) {
				sql.append(",");
			}
		}
		sql.append(") ");
		this.setParams(params);
		
		return sql.toString();
	}

	/**
	 * 拼接更新语句
	 * @param obj
	 * @return
	 */
	public String getUpdateSQL(Object obj,String tableName,String pri_name) {
		Row row = new Row();
		row = fromValueObject(obj);
		params = new Object[row.length()];
		String value = "";
		// 拼SQL语句
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ");
		sql.append("" + tableName + "");
		sql.append(" SET ");
		for (int i = 0; i < row.length(); i++) {
			value = Objects.toString(row.get(row.getKey(i)) == null?"":row.get(row.getKey(i)));
			sql.append("" + row.getKey(i) + "");
			sql.append("=?");
			params[i] = value;
			if (i < row.length() - 1) {
				sql.append(",");
			}
		}
		sql.append(" WHERE ");
		if(!"".equals(pri_name)){
			sql.append(pri_name);
			sql.append("='"+row.get(pri_name)+"'");
		}else{
			sql.append("rec_id");
			sql.append("='"+row.get("rec_id")+"'");
		}
		this.setParams(params);
		return sql.toString();
	}

	/**
	 * 去掉Bean对象中的null
	 * @param obj
	 * @return
	 */
	private Row fromValueObject(Object obj){
		Row row = new Row();
        Class type = obj.getClass(); //得到Class用于进行反射处理
        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if(!name.equals("serialVersionUID")){
	            String methodName = "get" + Character.toUpperCase(name.charAt(0)) +
	                                name.substring(1);
				try {
					Method method = type.getMethod(methodName, new Class[] {});
					Object value = method.invoke(obj, new Object[] {});
		            if(value!=null)
		            {
		                String nameInRow = row.toInRowName(name); //在此进行值对象中的名称向行对象中的名称转换
		                row.put(nameInRow, value);
		            }
				} catch (Exception e) {  // XXX Use multi-exception(jdk7) would be better
					logger.error("Invoke method {} on {}", new Object[] { methodName, obj }, e);
				}
            }
			
        }
        return row;
	}
	
}
