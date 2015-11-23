package com.pig84.ab.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.service.ILoginAndRegisterService;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.bean.AppVo_5;

/**
 * 登录注册相关
 *    
 * @author zhangqiang
 * 
 */
@ParentPackage("no-interceptor")
@Namespace("/app_carpooling")
 @SuppressWarnings("all")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class AdManageAction extends BaseAction {

	@Autowired
	private ILoginAndRegisterService loginAndRegisterService;

	/**
	 * 查询广告信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getAdInfoList", results = { @Result(type = "json") })
	public String getAdInfoList() {
		// 客户端类型
		// 1:andriod
		// 2:ios
		// 3:anroid+ios
		String clientType = request.getParameter("clientType");
		String imageResolution = request.getParameter("imageResolution");// 图片分辨率
		String versionId = request.getParameter("versionId");// 版本号
		List<AppVo_5> list = loginAndRegisterService.getAdInfoList(clientType, imageResolution, versionId);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String imageUrl = list.get(i).getA1();
				String urlLink = list.get(i).getA2();
				String httpId = PropertyManage.get("header.pic.ip");// IP地址
				list.get(i).setA1(httpId + "/" + imageUrl);
				list.get(i).setA2(urlLink);
			}
		}
		write(list);
		return null;
	}
}
