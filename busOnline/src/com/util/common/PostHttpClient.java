package com.util.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.util.SidFilter;

/**
 * @author zhangqiang 以二级制流的方式发送数据报文
 */
public class PostHttpClient {
	private static Logger log = Logger.getLogger(PostHttpClient.class.getName());

	/**
	 * 发送post请求,客户端采用二进制流发送,服务端采用二进制流接收
	 * 
	 * @param json : 入参的json格式的报文
	 * @param url : http服务器的地址
	 * @return 返回响应信息
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, Object> postHttpReq(String url, String json)
			throws UnsupportedEncodingException {
		HttpClient httpClient = MyHttpClient.newHttpsClient();
		HttpPost postMethod = new HttpPost(url);
		postMethod.addHeader("Cookie", "sid=" + SidFilter.get());
		if (!StringUtils.isEmpty(json)) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// 建立一个NameValuePair数组，用于存储欲传送的参数
			params.add(new BasicNameValuePair("data", json));
			// 添加参数
			postMethod.addHeader("Content-Type", "text/html;charset=UTF-8");
			StringEntity entity = new StringEntity(json, "utf-8");
			postMethod.setEntity(entity);
		}
		String responseMsg = "";
		int statusCode = 0;
		try {
			HttpResponse response = httpClient.execute(postMethod);// 发送请求
			responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
			statusCode = response.getStatusLine().getStatusCode();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		if (statusCode != HttpStatus.SC_OK) {
			log.info("HTTP服务异常" + statusCode);

		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("responseMsg", responseMsg);
		resultMap.put("statusCode", statusCode);
		return resultMap;
	}

	public static Map<String, Object> postHttpXMLReq(String url, String xml)
			throws UnsupportedEncodingException {
		HttpClient httpClient = MyHttpClient.newHttpsClient();
		HttpPost postMethod = new HttpPost(url);
		postMethod.addHeader("Cookie", "sid=" + SidFilter.get());
		if (!StringUtils.isEmpty(xml)) {
			StringEntity myEntity = new StringEntity(xml, "utf-8");
			postMethod.addHeader("Content-Type", "text/xml");
			postMethod.setEntity(myEntity);
		}
		String responseMsg = "";
		int statusCode = 0;
		try {
			HttpResponse response = httpClient.execute(postMethod);// 发送请求
			responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
			statusCode = response.getStatusLine().getStatusCode();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		if (statusCode != HttpStatus.SC_OK) {
			log.info("HTTP服务异常" + statusCode);

		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("responseMsg", responseMsg);
		resultMap.put("statusCode", statusCode);
		return resultMap;
	}

	/**
	 * 发送get请求,客户端采用二进制流发送,服务端采用二进制流接收
	 * 
	 * @param url
	 *            http服务器的地址
	 * @return 返回响应信息
	 */
	public static String getHttpReq(String url) {
		HttpClient httpClient = MyHttpClient.newHttpsClient();
		HttpGet postMethod = new HttpGet(url);
		postMethod.addHeader("Cookie", "sid=" + SidFilter.get());
		String responseMsg = "";
		int statusCode = 0;
		try {
			HttpResponse response = httpClient.execute(postMethod);// 发送请求
			responseMsg = EntityUtils.toString(response.getEntity());
			statusCode = response.getStatusLine().getStatusCode();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		if (statusCode != HttpStatus.SC_OK) {
			log.info("HTTP服务异常" + statusCode);
		}
		return responseMsg;
	}

	public static String sendPost(String url, Map<String, String> paramMap)
			throws UnsupportedEncodingException {
		HttpClient httpClient = MyHttpClient.newHttpsClient();
		HttpPost postMethod = new HttpPost(url);
		postMethod.addHeader("Cookie", "sid=" + SidFilter.get());
		// 把参数值放入postMethod中
		StringBuffer logUrl = new StringBuffer(url);
		logUrl.append("?");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<String> it = paramMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next(), val = paramMap.get(key);
			logUrl.append(key).append("=").append(val).append("&");
			params.add(new BasicNameValuePair(key, val));
		}
		logUrl.setLength(logUrl.length() - 1);
		// 添加参数
		postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		// 执行
		try {
			HttpResponse response = httpClient.execute(postMethod);
			// 打印返回的信息
			return EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			log.error(logUrl.toString());
			log.error(e.getMessage(), e);
		} finally {
			log.info(logUrl);
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 发送post请求,客户端采用二进制流发送,服务端采用二进制流接收
	 * 
	 * @param json
	 *            入参的json格式的报文
	 * @param url
	 *            http服务器的地址
	 * @return 返回响应信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String sendMsg(String url, String content, String mobileNo)
			throws UnsupportedEncodingException {
		HttpClient httpClient = MyHttpClient.newHttpsClient();
		HttpPost postMethod = new HttpPost(url);
		postMethod.addHeader("Cookie", "sid=" + SidFilter.get());
		// 把参数值放入postMethod中
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("mobileNo", mobileNo));
		// 添加参数
		postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		// 执行
		try {
			HttpResponse response = httpClient.execute(postMethod);
			// 打印结果页面
			String responseStr = EntityUtils.toString(response.getEntity());
			// 打印返回的信息
			return responseStr;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}

	/**
	 * 发送post请求,客户端采用二进制流发送,服务端采用二进制流接收
	 * 
	 * @param json
	 *            入参的json格式的报文
	 * @param url
	 *            http服务器的地址
	 * @return 返回响应信息
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String sendMsgs(String url, List<NameValuePair> params)
			throws UnsupportedEncodingException {
		HttpClient httpClient = MyHttpClient.newHttpsClient();
		HttpPost postMethod = new HttpPost(url);
		postMethod.addHeader("Cookie", "sid=" + SidFilter.get());
		// 把参数值放入postMethod中
		/*
		 * List<NameValuePair> params = new ArrayList<NameValuePair>(); //
		 * 建立一个NameValuePair数组，用于存储欲传送的参数 params.add(new
		 * BasicNameValuePair("content", content)); params.add(new
		 * BasicNameValuePair("mobileNo", mobileNo));
		 */
		// 添加参数
		postMethod.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		// 执行
		try {
			HttpResponse response = httpClient.execute(postMethod);
			// 打印结果页面
			String responseStr = EntityUtils.toString(response.getEntity());
			// 打印返回的信息
			return responseStr;
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
}