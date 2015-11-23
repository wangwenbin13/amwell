package com.amwell.commons;

import com.amwell.base.BaseDao;

public class ThreadTest extends Thread{

	private String name;
	public ThreadTest(String name){
		this.name=name;	
	}	
	public void run(){
		BaseDao dao = new BaseDao("recidtest");
		for(int i=0;i<100000;i++){
			String id = StringUtil.generateSequenceNo();
			System.out.println(name+":"+id);
			dao.executeSQL("insert into recidtest values('"+id+"')");
		}	
	}
}
