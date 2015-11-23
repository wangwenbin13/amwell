package com.amwell.commons;
import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;


/**
* @author zhangqiang
*以二级制流的方式发送数据报文
*/
public class PostHttpClient {

    /**
     * 发送post请求,客户端采用二进制流发送,服务端采用二进制流接收
     * @param json  入参的json格式的报文
     * @param url    http服务器的地址
     * @return  返回响应信息
     */
    public static String postHttpReq(String url,String json) {
        HttpClient httpClient = new HttpClient();
        EntityEnclosingMethod postMethod = new PostMethod();
        
        byte b[] = json.getBytes();//把字符串转换为二进制数据
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
            postMethod.releaseConnection();// 释放连接
        }

        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("HTTP服务异常" + statusCode);
        }
        return responseMsg;
    }
    
    
    /**
     * 发送get请求,客户端采用二进制流发送,服务端采用二进制流接收
     * @param url    http服务器的地址
     * @return  返回响应信息
     */
   public static String getHttpReq(String url,String sid){
   	 HttpClient httpClient = new HttpClient();
        EntityEnclosingMethod postMethod = new PostMethod();
        String sessionStr = "PHPSESSID="+sid+"";
        
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
        }

        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("HTTP服务异常" + statusCode);
        }
        return responseMsg;
    }
}