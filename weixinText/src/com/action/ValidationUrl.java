package com.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.action.util.Sha1;

/** 
* @author 作者 : wangwenbin
* @version 创建时间：2015年11月9日 下午7:32:35 
* 微信验证URL
*/
@ParentPackage("no-interceptor")
@Namespace("/validation")
public class ValidationUrl extends BaseAction{

	@Action(value="check",results={@Result(name="success",type="json")})
	public String check() throws Exception {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String token = "wangwenbin";
		// 构造参数列表
		List<String> params = new ArrayList<String>();
		params.add(token);
		params.add(timestamp);
		params.add(nonce);
		System.out.println(token+","+timestamp+","+nonce);
		// 将token、timestamp、nonce三个参数进行字典序排序(是三个参数的值进行字典排序,不是这三个英文字母进行字典排序)
		Collections.sort(params, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		// 将三个参数字符串拼接成一个字符串进行sha1加密
		String sign = Sha1.encode(params.get(0) + params.get(1) + params.get(2));
		String result = "false";
		if(sign.equals(signature)){
			result = echostr;
		}
		response.getWriter().write(result);
		return null;
	}
}
