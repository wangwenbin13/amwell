package com.amwell.commons;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class SendValue {
	public SendValue() {
	}

	/**
	 * form到vo的值传递
	 * 
	 * @param votype
	 * @param form
	 * @throws Exception
	 */
	public static void transform(Object form, Object votype) throws Exception {
		Field[] vofield = votype.getClass().getDeclaredFields();
		Field[] formfield = form.getClass().getDeclaredFields();
		String voname = "";
		String name = "";
		String setmethodName = "";
		String getmethodName = "";
		for (int i = 0; i < formfield.length; i++) {
			name = formfield[i].getName(); // 得到JavaBean的字段名
			setmethodName = "set" + Character.toUpperCase(name.charAt(0))
					+ name.substring(1); // 得到setXXXX方法名
			Class field = formfield[i].getType();
			for (int b = 0; b < vofield.length; b++) {
				voname = vofield[b].getName();
				if (name.equals(voname)) {
					getmethodName = "get"
							+ Character.toUpperCase(name.charAt(0))
							+ name.substring(1); // 得到setXXXX方法名
					Method method1 = votype.getClass().getMethod(getmethodName,
							new Class[] {});
					Object value = method1.invoke(votype, new Object[] {});
					if (value == null)
						continue;
					Class argClass = null;
					if (value instanceof Class) {
						argClass = (Class) value;
						value = null;
					} else {

						// 加入类型转换的情况
						if (field.getName().equals("java.math.BigDecimal")
								&& !value.toString().equals("")) {
							argClass = Class.forName("java.math.BigDecimal");
							Method method2 = form.getClass().getMethod(
									setmethodName, new Class[] { argClass }); // 得到set方法
							java.math.BigDecimal tvalue = new BigDecimal(value
									.toString());
							method2.invoke(form, new Object[] { tvalue }); // 调用setXXXX方法
						}
						if (field.getName().equals("java.sql.Date")
								&& !value.toString().equals("")) {
							argClass = Class.forName("java.sql.Date");
							Method method2 = form.getClass().getMethod(
									setmethodName, new Class[] { argClass }); // 得到set方法
							java.util.Date st=new java.util.Date();
							SimpleDateFormat TimeFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
							st=TimeFormat.parse(value.toString());
							java.sql.Date tvalue = new Date(st.getTime());
							method2.invoke(form, new Object[] { tvalue }); // 调用setXXXX方法
						}
						if (field.getName().equals("java.lang.String")) {
							argClass = Class.forName("java.lang.String");
							Method method2 = form.getClass().getMethod(
									setmethodName, new Class[] { argClass }); // 得到set方法
							String tvalue = new String(value.toString());
							method2.invoke(form, new Object[] { tvalue }); // 调用setXXXX方法
						}
					}

				}
			}
		}
	}
	
	/** 将数组分解成多重并列条件sql生成
	 * @param beanname
	 * @param orgin
	 * @return
	 */
	public static String splitSql(String beanname,String orgin)
	{
		String result="";
		if(orgin.toString().indexOf(",")>-1)
		{
			String[] tp=orgin.toString().split(",");
			String t="";
			for(int m=0;m<tp.length;m++)
			{
				t+=beanname+"='"+tp[m]+"' or ";
			}
			t=t.substring(0,t.length()-4);
			result+="("+t+")";
		} else
			result+=beanname+"='"+orgin.toString()+"'";
		return result;
	}
	
	
	public static String splitSqlByarg(String beanname,String orgin)
	{
		String result="";
		if(orgin.toString().indexOf(",")>-1)
		{
			String[] tp=orgin.toString().split(",");
			String t="";
			for(int m=0;m<tp.length;m++)
			{
				t+=beanname+"=? or ";
			}
			t=t.substring(0,t.length()-4);
			result+="("+t+")";
		} else
			result+=beanname+"=?";
		return result;
	}
	
	//转码
	public static String unescape(String src) {
		if (src==null) return "";
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
}