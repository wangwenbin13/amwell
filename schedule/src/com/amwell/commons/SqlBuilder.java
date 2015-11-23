package com.amwell.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangqiang
 * Sql构建类
 *
 */
@SuppressWarnings("all")
public class SqlBuilder {
	private Field[] fields;
	private Object[] params;

	public Object[] getParams() {
		return params;
	}


	public void setParams(Object[] params) {
		this.params = params;
	}


	/**
	 * 获取字段
	 * @param obj
	 * @return
	 */
	public List getFields(Object obj) {
		Class c = obj.getClass();
		// 获得所有的属性
		fields = c.getDeclaredFields();
		Method[] method = c.getMethods();
		List list = new ArrayList();
		for (int j = 0; j < fields.length; j++) {
			String m = "get" + fields[j].getName().toUpperCase().charAt(0)+ fields[j].getName().substring(1);
			for (int i = 0; i < method.length; i++) {
				if (method[i].getName().endsWith(m)) {
					try {
						list.add(method[i].invoke(obj, null));
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return list;
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
			value = StringUtil.objectToString(row.get(row.getKey(i)) == null?"":row.get(row.getKey(i)));
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
			value = StringUtil.objectToString(row.get(row.getKey(i)) == null?"":row.get(row.getKey(i)));
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
	 * 拼接查询语句
	 * @param obj
	 * @return
	 */
	public String getSelectSQL(Object obj,String tableName) {
		getFields(obj);
		// 拼SQL语句
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ");
		sql.append("`" + tableName + "` ");
		return sql.toString();
	}
	
	/**
	 * 拼接删除语句
	 * @param obj
	 * @return
	 */
	public String getDeleteSQL(Object obj,String tableName) {
		getFields(obj);
		// 拼SQL语句
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ");
		sql.append("" + tableName + "");
		sql.append(" WHERE ");
		sql.append(fields[0].getName());
		sql.append("=?");
		return sql.toString();
	}
	
	/**
	 * 去掉Bean对象中的null
	 * @param obj
	 * @return
	 */
	public Row fromValueObject(Object obj){
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
			
        }
        return row;
	}
	
	/**
	 * 获取更新数据的值
	 * @param obj
	 * @return
	 */
	public String getUpdateId(Object obj,String tableName,String pri_name) {
		String updateIdValue = "";
		Row row = new Row();
		row = fromValueObject(obj);
		params = new Object[row.length()];
		String value = "";
		if(!"".equals(pri_name)){
			updateIdValue = StringUtil.objectToString(row.get(pri_name));
		}else{
			updateIdValue = StringUtil.objectToString(row.get("rec_id"));
		}
		
		return updateIdValue;
	}
	
	public static String getSqlLikeValue(String value){
		return "%"+value+"%";
	}
}
