package com.pig84.ab.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.security.KeyStore;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.pig84.ab.weixin.conf.WeiXinPayConfig;

/**
 * post传XML
 * 
 * @author wangwenbin
 *
 */
public class PostXmlUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PostXmlUtil.class);
	
	public static int doPost(String urlStr,String xmlInfo)  throws Exception{
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		String cert=PropertyManage.get("weixin.cert");
		InputStream instream = null;
		instream = Thread.currentThread().getContextClassLoader().getResourceAsStream(cert);
        if(null==instream){
        	instream = new FileInputStream(new File(cert));
        }
        int statu = 0;
        try {
            keyStore.load(instream, WeiXinPayConfig.KEY_PARTNER.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WeiXinPayConfig.KEY_PARTNER.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,new String[] { "TLSv1" },null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        String xml = "";
        try {
//            PostMethod postMethod = new PostMethod(urlStr);
//            postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
//            postMethod.setRequestBody(xmlInfo);
//            CloseableHttpResponse response = httpclient.execute((HttpUriRequest) postMethod);
        	HttpPost httppost = new HttpPost(urlStr);
        	httppost.setHeader("contentType", "text/html;charset=utf-8");
            StringEntity myEntity = new StringEntity(xmlInfo); 
            httppost.setEntity(myEntity); 
            logger.debug("executing request {}", httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                response.setHeader("contentType", "text/html;charset=utf-8");
                logger.debug("Response status: {}", response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                	logger.debug("Response content length: {}", resEntity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resEntity.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                    	text = new String(text.getBytes(),"utf-8");
                    	xml += text;
                    }
                   
                }
                EntityUtils.consume(resEntity);
                statu = xmlElements(xml);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

		return statu;
	}

	/**支付
	 * @throws IOException 
	 * @throws ClientProtocolException **/
	public static String doPostPay(String urlStr, String xmlInfo) throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost(urlStr);
        StringEntity myEntity = new StringEntity(xmlInfo); 
        httppost.setHeader("contentType", "text/html;charset=utf-8");
        httppost.setEntity(myEntity);
        String text = "";
        StringBuffer sb = new StringBuffer();
        try {  
        	//使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。  
        	HttpResponse httpResponse = new DefaultHttpClient().execute(httppost);//其中HttpGet是HttpUriRequst的子类  
        	httpResponse.setHeader("contentType", "text/html;charset=utf-8");
        	if(httpResponse.getStatusLine().getStatusCode() == 200)  {  
        		HttpEntity httpEntity = httpResponse.getEntity();  
        		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
        		while ((text = bufferedReader.readLine()) != null) {
        			text = new String(text.getBytes(),"utf-8");
        			sb.append(text);
        		}
        	}else{
        		httppost.abort();  
        	}   
        } catch (ClientProtocolException e) {  
        	e.printStackTrace();  
       } catch (IOException e) {  
    	   e.printStackTrace();  
       }
       return sb.toString();
		
	}
	
	
	/***解析xml字符串**/
	private static int xmlElements(String xmlDoc) {
		
		int statu  = 0;
		
        //创建一个新的字符串
        StringReader read = new StringReader(xmlDoc);
        //创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        //创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //通过输入源构造一个Document
            Document doc = sb.build(source);
            //取的根元素
            Element root = doc.getRootElement();
            //得到根元素所有子元素的集合
            List jiedian = root.getChildren();
            Element et = null;
            for(int i=0;i<jiedian.size();i++){
                et = (Element) jiedian.get(i);//循环依次得到子元素
                if("result_code".equals(et.getName())){
                	if("SUCCESS".equals(et.getValue())){
                		statu = 1;
                	}
                }
                if("err_code".equals(et.getName())){
                	if("ORDERNOTEXIST".equals(et.getValue())){
                		statu = 2;//订单号不存在
                	}
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statu;
    }
}
