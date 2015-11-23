/*********************************************
 *  3des 加密解密 注意 密钥只能为8位长度
 *  soft456@gmail.com 2009-09-05
 ********************************************/
package com.amwell.commons;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Mcrypt {
	private byte[] DESkey = null;// 设置密钥，略去
	private byte[] DESIV = { 1, 2, 3, 4, 5, 6, 7, 8 };// 设置向量，略去
	private AlgorithmParameterSpec iv = null;// 加密算法的参数接口，IvParameterSpec是它的一个实现
	private Key key = null;	
	private Long timeOut = new Long(600000);//默认加密串失效时间为10分钟

	public Mcrypt(String sKey, Long tOut) throws Exception {
		DESkey = sKey.getBytes();
		DESKeySpec keySpec = new DESKeySpec(DESkey);// 设置密钥参数
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
		iv = new IvParameterSpec(DESIV);// 设置向量
		key = keyFactory.generateSecret(keySpec);// 得到密钥对象
		timeOut = tOut;
	}

	/**
	 * 字符串加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String Encode(String data) throws Exception {
		String str = null;
		
		Long curTime = getMicroTime();	
		str = Long.toString(curTime) + "$" + data;
		String digByte = new SHA1().getDigestOfString(str.getBytes("UTF-8"));		
	
		//生成不可逆的sha1验证串
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String dig = base64Encoder.encode(digByte.getBytes("UTF-8"));		
		str = str + "$" + dig; 		
		Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
		enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
		byte[] pasByte = enCipher.doFinal(str.getBytes("UTF-8"));
		
		str = base64Encoder.encode(pasByte);
		
		try {
			str = java.net.URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			
		}		
		return str;
	}

	/**
	 * 字符串解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String Decode(String data) throws Exception {
		String str = null;
	
		Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		deCipher.init(Cipher.DECRYPT_MODE, key, iv);
		BASE64Decoder base64Decoder = new BASE64Decoder();
		//data = java.net.URLDecoder.decode(data, "UTF-8");
		byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
		str = new String(pasByte, "UTF-8");
		
		//得到加密串里的时间
		Long uTime = Long.valueOf(str.substring(0,str.indexOf("$")));	
		Integer lastPoint = str.lastIndexOf("$");
		Integer firstPoint = str.indexOf("$");
		
		// 加密串带过来的sha串
		String uSha = str.substring(lastPoint + 1, str.length());		
		//base64Decode
		byte[]uDigByte = base64Decoder.decodeBuffer(uSha); 
		String uDig = new String(uDigByte, "UTF-8");	
		uDig = uDig.toUpperCase();
		
		//该方法返回值
		String rt = str.substring(0, lastPoint);	
				
		// 根据明文生成的sha串	
		String nDig = new SHA1().getDigestOfString(rt.getBytes("UTF-8"));	

		Long tttTime = getMicroTime();
		if (nDig.equals(uDig.toUpperCase())){
			//检测是否失效
			if ((tttTime - uTime) > timeOut){
				//超时，则返回NULL
				return null;
			}else{
				return rt.substring(firstPoint + 1);
			}			
		} else {
			return null;
		}

	}	
	

	/**
	 * 将当前时间放入加密串，以免加密串失效
	 * @param str
	 * @return
	 */
	public String refreshTimeOut(String str){
		String rt = null;	
		try {
			rt = this.Decode(str);
			rt = rt.substring(rt.indexOf("$")+1, rt.length());			
			rt = String.valueOf(getMicroTime()) + "$" + rt;			
			rt = this.Encode(rt);			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return rt;		
	}
	

	/**
	 * 得到自2000年1月1日0点0分0秒以来的毫秒数
	 * @return
	 */
	public Long getMicroTime(){
		//946684800000 为 2000年1月1日0时0分0秒的PHP时间戳;
		//Date d1 = new GregorianCalendar(2000,1,1,0,0,0).getTime();
		//Long tttt = System.currentTimeMillis();
		Date today = new Date();	
		Long dodayTime = today.getTime();
		return  dodayTime/1000;		
		//return today.getTime() - d1.getTime();		
	}
	

	
	
	/**
	 * 将 s 进行 BASE64 编码
	 * @param s
	 * @return
	 */
	public static String getBASE64(String s) { 
		if (s == null) return null; 
		return (new sun.misc.BASE64Encoder()).encode( s.getBytes() ); 
	}


	/**
	 * 将 BASE64 编码的字符串 s 进行解码 
	 * @param s
	 * @return
	 */
	public static String getFromBASE64(String s) { 
		if (s == null) return null; 
		BASE64Decoder decoder = new BASE64Decoder(); 
		try { 
			byte[] b = decoder.decodeBuffer(s); 
			return new String(b); 
		} catch (Exception e) {
			return null; 
		}
	}
	
	public static void main(String args[]) throws Exception{
		
//		String url = "http://localhost:8080/suite/portal/portalView.do?s=FqieGZr0RiBVcqLcuT%2Fhm%2B2RZheb3L%2Btg3jGnnai8rba0h41Abs7eZD9x8mj9VSJJn4WM1PIZsPn%0D%0A73EYV8nYQHh4QpYO2RWpB5UWikz9bSUrO2dFNR3f17EJz0W6zMh1SCseNW7gwjePPo6PySrROx%2BV%0D%0ADbn1lTOU";
//		System.out.println("待解密的地址" + url);
//		String[] urlArray = url.split("=");
//		String tempUrl = urlArray[1];
		
		Long timeOut = new Long(60000);

		Mcrypt my = new Mcrypt("e21DESHI",timeOut);
		
		String x1 = "super";
		System.out.println("加密后的结果：" + my.Encode(x1));
		System.out.println("解密后的结果：" + my.Decode(x1));
	}

}