package com.amwell.commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * @author zhangqiang 以二级制流的方式发送数据报文
 */
public class PostHttpClient {

	private static final Logger log = Logger.getLogger(PostHttpClient.class);

	/**
	 * 发送post请求,客户端采用二进制流发送,服务端采用二进制流接收
	 * 
	 * @param json
	 *            入参的json格式的报文
	 * @param url
	 *            http服务器的地址
	 * @return 返回响应信息
	 */
	public static String postHttpReq(String url, String json) {
		HttpClient httpClient = new HttpClient();
		EntityEnclosingMethod postMethod = new PostMethod();

		byte b[] = json.getBytes();// 把字符串转换为二进制数据
		RequestEntity requestEntity = new ByteArrayRequestEntity(b);

		postMethod.setRequestEntity(requestEntity);// 设置数据

		postMethod.setPath(url);// 设置服务的url
		postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");// 设置请求头编码

		// 设置连接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);

		String responseMsg = "";
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);// 发送请求
			responseMsg = postMethod.getResponseBodyAsString();// 获取返回值
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
System.out.println(statusCode + " : " + responseMsg);

		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("HTTP服务异常" + statusCode);
		}
		return responseMsg;
	}
	
	public static String post(String url, String... params) {
		return post(url, toMap(params));
	}
	
	/**
	 * 发送post请求,客户端采用二进制流发送,服务端采用二进制流接收
	 * 
	 * @param json
	 *            入参的json格式的报文
	 * @param url
	 *            http服务器的地址
	 * @return 返回响应信息
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String post(String url, Map<String, String> params) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		// 设置参数编码为UTF-8
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		// 构造键值对参数
		NameValuePair[] data = new NameValuePair[params.keySet().size()];
		Iterator<String> keys = params.keySet().iterator();
		int index = 0;
		while(keys.hasNext()) {
			String key = keys.next();
			data[index ++] = new NameValuePair(key, params.get(key));
		}
		// 把参数值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行
		try {
			client.executeMethod(postMethod);
			// 读取内容
			String response = new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
			return response;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	/**
	 * app发送、获取验证码
	 * 
	 * @param url
	 * @param mobileNo
	 *            手机号码
	 * @return 返回响应信息
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String sendMsgByApp(String url, String mobileNo) {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		// 设置参数编码为UTF-8
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		// 构造键值对参数
		NameValuePair[] data = { new NameValuePair("mobileNo", mobileNo) };
		// 把参数值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行
		try {
			client.executeMethod(postMethod);
			// 读取内容
			// byte[] responseBody = postMethod.getResponseBody();
			// 处理内容
			// System.out.println(new String(responseBody));
			// System.out.println("getStatusLine:"+postMethod.getStatusLine());
			// System.out.println("~~~"+postMethod.getResponseBodyAsString());
			// 打印结果页面
			String response = new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
			// 打印返回的信息
			// System.out.println("response:"+response);
			// 释放连接

			return response;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}

		return null;
	}

	/**
	 * 发送短信，jpush推送
	 * 
	 * @param url
	 * @param mobileNo
	 *            手机号码
	 * @return 返回响应信息
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String sendMsgAndJpush(String url, String msgId) {
		String response = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		// 设置参数编码为UTF-8
		HttpMethodParams params = postMethod.getParams();
		params.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		// 设置连接超时时间为30秒
		// params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,30000);
		// 设置Socket连接超时时间为30秒
		// params.setParameter(CoreConnectionPNames.SO_TIMEOUT,30000);
		// 构造键值对参数
		NameValuePair[] data = { new NameValuePair("msgId", msgId) };
		// 把参数值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行
		try {
			client.executeMethod(postMethod);
			response = new String(postMethod.getResponseBodyAsString()
					.getBytes("utf-8"));
		} catch (HttpException e) {
			log.error(e.getLocalizedMessage());
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		} finally {
			// 释放连接
			postMethod.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return response;
	}
	
	/**
	 * 发送get请求,客户端采用二进制流发送,服务端采用二进制流接收
	 * @param url：http服务器的地址
	 * @return 返回响应信息
	 */
	public static String getHttpReq(String url, String sid) {
		HttpClient httpClient = new HttpClient();
		EntityEnclosingMethod postMethod = new PostMethod();
		String sessionStr = "PHPSESSID=" + sid + "";

		postMethod.setPath(url);// 设置服务的url
		postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");// 设置请求头编码
		postMethod.setRequestHeader("Cookie", sessionStr);// 设置Cookie

		// 设置连接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000);
		// 设置读取超时
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);

		String responseMsg = "";
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);// 发送请求
			responseMsg = postMethod.getResponseBodyAsString();// 获取返回值
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();// 释放连接
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}

		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("HTTP服务异常" + statusCode);
		}
		return responseMsg;
	}
	
	private static Map<String, String> toMap(String... params) {
		if (params.length % 2 != 0) {
			throw new IllegalArgumentException("Params count must be even.");
		}
		Map<String, String> map = new HashMap<String, String>(params.length / 2);
		for (int i = 0; i < params.length; i += 2) {
			String name = params[i];
			String value = params[i + 1];
			map.put(name, value);
		}
		return map;
	}

}