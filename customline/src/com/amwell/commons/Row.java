package com.amwell.commons;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;


public class Row {
    /**
     * 排序,由于Hashtable不提供通过索引取得值的方法，并且其中的键值对也不是按照put上去时的顺序排列的。
     * 注意：Vector中加入的对象是有序的，即按加入的顺序排列并且能够根据索引访问，可以看成是可变大小的数组
     * List可以取代Vector

     */
    private Vector ordering = new Vector();
    /**
     * 存放键值对（表中字段名称与字段值）
     */
    private HashMap map = new HashMap();
    public Row() {
    }

    /**
     * 向HashMap中追加键值对，即字段名称与字段值
     * @param name
     * @param value
     */
    public void put(String name, Object value) {

        if (!map.containsKey(name)) {
            ordering.addElement(name); //将键保存起来
        }
        map.put(name, value);
    }

    /**
     *
     * @return maphash
     */
    public HashMap getAll() {
        return this.map;
    }


    /**
     * 得到行对象中字段的个数
     * @return
     */
    public int length() {
        return map.size();
    }

    /**
     * 根据字段名称取得字段值
     * @param name
     * @return
     */
    public Object get(String name) {
        return map.get(name);
    }

    /**
     * 根据字段在HashMap中的编号取得字段值
     * @param which
     * @return
     */
    public Object get(int which) {
        String key = (String) ordering.elementAt(which);
        return map.get(key);
    }

    /**
     * 根据字段序号取得字段名称
     * @param which
     * @return
     */
    public String getKey(int which) {
        String key = (String) ordering.elementAt(which);
        return key;
    }

    /**
     * 打印，用于调试
     */
    public void dump() {
        for (Iterator e = map.keySet().iterator(); e.hasNext(); ) {
            String name = (String) e.next();
            Object value = map.get(name);
            //System.out.print(name + "=" + value + ", ");
        }
       // System.out.println("");
    }

    /**
     * 将行对象转换成值对象
     * @param row
     * @param type值对象类型
     * @return
     * @throws java.lang.Exception 这里的异常一般在DAO中处理，因为DAO调用
     * 这个方法进行Row和ValueObject的转换
     */
    public static Object toValueObject(Row row, Class type) throws Exception {
        Object vo = type.newInstance(); //创建一个值对象
        Field[] fields = type.getDeclaredFields(); //得到值对象中所有字段
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName(); //得到JavaBean的字段名
            //System.out.println("fidldname:"+name+"[]");

            String nameInRow = toInRowName(name); //在此进行值对象名称到行对象名称的转换
            nameInRow = nameInRow.toUpperCase();
            //System.out.println(nameInRow);
            Object value = row.get(nameInRow); //得到从数据库中取出的与字段名对应的值
            //System.out.println(value);

            String methodName = "set" + Character.toUpperCase(name.charAt(0)) +
                                name.substring(1); //得到setXXXX方法名

            //System.out.println(methodName);

            Class argClass = null;
          if(value==null)
              continue;
            if (value instanceof Class) {
                argClass = (Class) value;
                value = null;
            } else {
                //在提取给vo显示时，做适当的类型转换，便于读取和显示
                if(value.getClass().getName().equals("java.sql.Date"))
                {   
                	argClass =  Class.forName ("java.sql.Date");
                	Method method = type.getMethod(methodName, new Class[] {String.class}); //得到set方法
                    method.invoke(vo, new Object[] {value.toString()}); //调用setXXXX方法
                    
                } 
                if(value.getClass().getName().equals("java.lang.String"))
                {
                	argClass = value.getClass();
                	Method method = type.getMethod(methodName, new Class[] {argClass}); //得到set方法
                    method.invoke(vo, new Object[] {value}); //调用setXXXX方法
                    
                }
                if(value.getClass().getName().equals("java.math.BigDecimal"))
                {
                	if(methodName.equals("setScore") || methodName.equals("setScore_k") || methodName.equals("setScore_t"))
                	{
                	argClass =  Class.forName ("java.math.BigDecimal");
                	Method method = type.getMethod(methodName, new Class[] {argClass}); //得到set方法
                    method.invoke(vo, new Object[] {value}); //调用setXXXX方法
                	} else
                	{
                		argClass =  Class.forName ("java.lang.String");
                    	Method method = type.getMethod(methodName, new Class[] {argClass}); //得到set方法
                        method.invoke(vo, new Object[] {value.toString()}); //调用setXXXX方法
                	}
                    
                }
                
            }
            
            
        }
        return vo;
    }

    /**
     * 根据传过来的值对象和类型把值对象转换到行对象中
     * @param vo
     * @return
     * @throws java.lang.Exception 这里的异常一般在DAO中处理，因为DAO调用
     * 这个方法进行Row和ValueObject的转换
     */
    public static Row fromValueObject(Object vo) throws Exception {
        Row row = new Row();
        Class type = vo.getClass(); //得到Class用于进行反射处理
        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            String methodName = "get" + Character.toUpperCase(name.charAt(0)) +
                                name.substring(1);
            Method method = type.getMethod(methodName, new Class[] {});
            Object value = method.invoke(vo, new Object[] {});
            if(value!=null)
            {
                String nameInRow = toInRowName(name); //在此进行值对象中的名称向行对象中的名称转换
                row.put(nameInRow, value);
            }
        }
        return row;
    }

    /**
     * 将值对象中属性名转换成对应的行对象中的字段名(因为行对象中的字段名
     * 在更新数据库时必须与数据库表中字段名完全匹配)
     * 一般规则为  fsiId ---> fsi_id(现在假设的情况是如果出现有两个单词
     * 以上的值对象属性名则数据库表中的字段名一定是有下划线的)
     * @param voName
     * @return
     */
    public static String toInRowName(String voName) {
    	return voName;
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < voName.length(); i++) { //遍历voName如果有大写字母则将大写字母转换为_加小写
//            char cur = voName.charAt(i);
//            if (Character.isUpperCase(cur)) {
//                sb.append("_");
//                sb.append(Character.toLowerCase(cur));
//            } else {
//                sb.append(cur);
//            }
//        }
//        return sb.toString();
    }
}