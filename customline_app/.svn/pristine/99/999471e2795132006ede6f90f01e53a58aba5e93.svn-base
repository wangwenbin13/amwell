package com.pig84.ab.utils;
import java.util.HashMap;
import java.util.Vector;


class Row {

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
    void put(String name, Object value) {
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
    int length() {
        return map.size();
    }

    /**
     * 根据字段名称取得字段值
     * @param name
     * @return
     */
    Object get(String name) {
        return map.get(name);
    }

    /**
     * 根据字段序号取得字段名称
     * @param which
     * @return
     */
    String getKey(int which) {
        String key = (String) ordering.elementAt(which);
        return key;
    }

    /**
     * 将值对象中属性名转换成对应的行对象中的字段名(因为行对象中的字段名
     * 在更新数据库时必须与数据库表中字段名完全匹配)
     * 一般规则为  fsiId ---> fsi_id(现在假设的情况是如果出现有两个单词
     * 以上的值对象属性名则数据库表中的字段名一定是有下划线的)
     * @param voName
     * @return
     */
    static String toInRowName(String voName) {
    	return voName;
    }
}
