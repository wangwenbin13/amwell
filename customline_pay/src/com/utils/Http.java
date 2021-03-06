package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP Client.
 * 
 * @author GuoLin
 */
public class Http {
	
	private static final Logger logger = LoggerFactory.getLogger(Http.class);

	private static final int MAX_CONNECTIONS_PER_HOST = PropertyManage.getInt("httpclient.pool.max");

	private static final int MAX_CONNECTIONS = PropertyManage.getInt("httpclient.pool.maxPerHost");;

	private static final int WAIT_POOL_TIMEOUT = PropertyManage.getInt("httpclient.pool.timeout");

	private static final int SOCKET_TIMEOUT = PropertyManage.getInt("httpclient.connect.socket.timeout");

	private static final int CONNECT_TIMEOUT = PropertyManage.getInt("httpclient.connect.timeout");;
	
	private static final CloseableHttpClient CLIENT = buildHttpClient();

	private static final RequestConfig DEFAULT_CONF = RequestConfig.custom()
			.setSocketTimeout(SOCKET_TIMEOUT)
			.setConnectTimeout(CONNECT_TIMEOUT)
			.setConnectionRequestTimeout(WAIT_POOL_TIMEOUT)
			.build();

	/**
	 * Send HTTP POST request.
	 * 
	 * @param url URL to request
	 * @param params Parameters
	 * @return Response entity
	 */
	public static String post(String url, String... params) {
		return post(url, toMap(params));
	}

	/**
	 * Send HTTP POST request.
	 * 
	 * @param url URL to request
	 * @param params Parameters
	 * @return Response entity
	 */
	public static String post(String url, Map<String, String> params) {
		HttpPost req = new HttpPost(url);
		req.setConfig(DEFAULT_CONF);
		
		// Assemble entity
		List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
		for (Map.Entry<String, String> entry : params.entrySet()) {
			pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			req.setEntity(new UrlEncodedFormEntity(pairs, Env.DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			// Impossible
		}

		// Send request
		logger.debug("Sending POST request to {} with entity: {}", url, params);
		try (CloseableHttpResponse response = CLIENT.execute(req)) {
			HttpEntity entity = response.getEntity();
			try (InputStream instream = entity.getContent()) {
				return IOUtils.toString(instream, Env.DEFAULT_ENCODING);
			} finally {
				EntityUtils.consume(entity);
			}
		} catch (IOException e) {
			logger.error("Send POST request failed: {}", url, e);
			return null;
		}
	}

	public static String get(String url, String... params) {
		return get(url, toMap(params));
	}

	/**
	 * Send HTTP GET request.
	 * 
	 * @param url URL to request
	 * @param params Parameters to append as query string
	 * @return Response entity
	 */
	public static String get(String url, Map<String, String> params) {
		// Assemble query string
		StringBuilder query = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			query.append('&').append(entry.getKey()).append('=').append(entry.getValue());
		}
		if (!url.contains("?")) {
			query.replace(0, 1, "?");
		}
		url += query.toString();
		
		// Send request
		HttpGet req = new HttpGet(url);
		req.setConfig(DEFAULT_CONF);

		logger.debug("Sending GET request to {}", url);
		try (CloseableHttpResponse response = CLIENT.execute(req)) {
			HttpEntity entity = response.getEntity();
			try (InputStream instream = entity.getContent()) {
				return IOUtils.toString(instream, Env.DEFAULT_ENCODING);
			} finally {
				EntityUtils.consume(entity);
			}
		} catch (IOException e) {
			logger.error("Send GET request failed: {}", url, e);
			return null;
		}
	}

	private static Map<String, String> toMap(String... params) {
		if (params.length % 2 != 0) {
			throw new IllegalArgumentException("Params count must be even.");
		}
		Map<String,String> map = new HashMap<String, String>(params.length / 2);
		for (int i = 0; i < params.length; i += 2) {
			String name = params[i];
			String value = params[i + 1];
			map.put(name, value);
		}
		return map;
	}
	
	private static CloseableHttpClient buildHttpClient() {
		// Build HttpClient with pooling connection manager
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(MAX_CONNECTIONS);
		cm.setDefaultMaxPerRoute(MAX_CONNECTIONS_PER_HOST);
		final CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(cm)
				.build();
		
		// Shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error("Close HttpClient in shutdown hook failed.", e);
				}
			}
		});
		return httpClient;
	}

}
