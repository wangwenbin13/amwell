package com.amwell.vo.coupon;

import java.util.ArrayList;
import java.util.List;
import com.amwell.vo.PassengerInfoEntity;
import com.amwell.vo.app.bean.AppVo_1;
import com.amwell.vo.app.bean.AppVo_3;

public abstract class Rule {
	private String ruleName;//varchar(35) NOT NULL规则名称
	private String theCondition;//varchar(5) NOT NULL条件：gt:大于 equal:等于 lt:小于
	private String theValue;//varchar(35) NOT NULL规则内容
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getTheCondition() {
		return theCondition;
	}

	public void setTheCondition(String theCondition) {
		this.theCondition = theCondition;
	}

	public String getTheValue() {
		return theValue;
	}

	public void setTheValue(String theValue) {
		this.theValue = theValue;
	}

	public Rule(String ruleName, String theCondition, String theValue) {
		super();
		this.ruleName = ruleName;
		this.theCondition = theCondition;
		this.theValue = theValue;
	}
	
	//通过工厂方法创建规则子对象
	public static Rule create(AppVo_3 vo3){
		if(vo3.getA1().equals("cityCode")){//城市规则
			return new CityRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("lineBaseId")){//线路规则
			return new LineRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("telephone")){//手机号规则
			return new TelephoneRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("registTime")){//注册时间规则
			return new RegistTimeRule(vo3.getA1(),getCondition(vo3.getA2()), vo3.getA3());
		}
		if(vo3.getA1().equals("ticketNumber")){//购票次数规则
			return new TicketNumberRule(vo3.getA1(),getCondition(vo3.getA2()), vo3.getA3());
		}
		if(vo3.getA1().equals("terminal")){//设备类型规则
			return new TerminalRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("sourcefrom")){//用户来源规则
			return new SourceFromRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("sex")){//性别规则
			return new SexRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("recommendNumber")){//推荐人数规则
			return new RecommendNumberRule(vo3.getA1(),getCondition(vo3.getA2()), vo3.getA3());
		}
		if(vo3.getA1().equals("isRecommended")){//是否被推荐规则
			return new IsRecommendedRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("isDoRecommend")){//是否推荐规则
			return new IsDoRecommendRule(vo3.getA1(), vo3.getA2(), vo3.getA3());
		}
		if(vo3.getA1().equals("recommendTime")){//推荐时间规则
			return new RecommendTimeRule(vo3.getA1(),getCondition(vo3.getA2()), vo3.getA3());
		}
		if(vo3.getA1().equals("recommendedTime")){//被推荐时间规则
			return new RecommendedTimeRule(vo3.getA1(),getCondition(vo3.getA2()), vo3.getA3());
		}
		if(vo3.getA1().equals("ticketTime")){//购票时间规则
			return new TicketTimeRule(vo3.getA1(),getCondition(vo3.getA2()), vo3.getA3());
		}
		return null;
	}
	
	private static String getCondition(String theCondition){
		String temp="";
		if("gt".equals(theCondition)){
			temp=">";
		}
		else if("equal".equals(theCondition)){
			temp="=";
		}
		else{
			temp="<";
		}
		return temp;
	}
	
	abstract public List<PassengerInfoEntity> filter(Object obj,List<PassengerInfoEntity> passengers);
	
	/**
	 * 过滤用户
	 * @param voList
	 * @param passengers
	 * @return
	 */
	public List<PassengerInfoEntity> getResult(List<AppVo_1> voList,List<PassengerInfoEntity> passengers){
		List<PassengerInfoEntity> list=new ArrayList<PassengerInfoEntity>();
		if(null!=voList&&voList.size()>0){
			for (AppVo_1 appVo_1 : voList) {
				for (PassengerInfoEntity passenger : passengers) {
					if(passenger.getPassengerId().equals(appVo_1.getA1())){
						list.add(passenger);
					}
				}
			}
		}
		return list;
	}
}
