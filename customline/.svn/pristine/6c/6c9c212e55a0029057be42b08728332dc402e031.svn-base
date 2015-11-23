package com.amwell.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestUtils {

	public static String sendGetRequest(String url, String charSet) {
		HttpURLConnection connection = null;
		BufferedReader reader = null;
		String content = "";
		try {
			URL address_url = new URL(url);
			connection = (HttpURLConnection) address_url.openConnection();
			connection.setRequestMethod("GET");
			// 设置访问超时时间及读取网页流的超市时间,毫秒值
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			// 得到访问页面的返回值
			int response_code = connection.getResponseCode();
			if (response_code == HttpURLConnection.HTTP_OK) {
				InputStream in = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in, charSet));
				String line = null;
				while ((line = reader.readLine()) != null) {
					content += line;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
		} finally {
			try {
				if(null != reader){
					reader.close();
				}
			} catch (IOException e) {
			}
			if (connection != null) {
				connection.disconnect();
			}
		}
		return content;
	}
}
