package com.amwell.commons;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class KiclkOutMgr {
	private static String url ="http://localhost:8080/schedule/login/kickOutSysMgr.action?businessId=";
	
	public static void main(String[] args) throws Exception{
		getType("140912143503599000");
	}
	
	public static String getType(String businessId){
		String resultStr = "";
		HttpClient httpclient = new HttpClient();
		GetMethod getMethod = new GetMethod(url+businessId);
		
		int statusCode;
		try {
			statusCode = httpclient.executeMethod(getMethod);
			//响应成功
			if(statusCode == 200){
				resultStr = getMethod.getResponseBodyAsString();
			}
			resultStr = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			resultStr = "error";
			throw new RuntimeException("请求异常"+e);
		}finally{
			getMethod.releaseConnection();
		}
		return resultStr;
	}
}
