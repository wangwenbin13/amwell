package com.amwell.vo;

/**
 * 操作线路枚举类
 */
public enum LineOperateLogEnum {
	
	LineName("lineName","线路名称修改"),LineStation("lineStation","站点修改"),LineTime("lineTime","行程时间修改"),LineKm("lineKm","里程修改"),	
	OriginalPrice("originalPrice","通用票价修改"),LineStatus("lineStatus","线路类型修改"),OrderStartTime("orderStartTime","班次时间修改"),
	OrderSeats("orderSeats","座位数修改,"),OrderDate("orderDate","放票修改,"),Price("price","改价"),BusinessId("businessId","切换供应商");
	
	/**
	 * 显示文本		
	 */
	private String text;
	
	/**
	 * 实际存储值
	 */
	private String value;
	
	//构造方法
	private LineOperateLogEnum(String text,String value){
		this.text = text;
		this.value = value;
	}
	
	
	/**
	 * 获取VALUE值
	 */
	public String getText(String value){
		for(LineOperateLogEnum vo : LineOperateLogEnum.values()){
			if(value.equals(vo.value)){
				return vo.text;
			}
		}
		return null;
	}


	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
