package com.amwell.action.zfb;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.amwell.action.BaseAction;
import com.amwell.action.zfb.util.AlipayNotify;
import com.amwell.service.ILeaseOrderService;

/**
 * 支付宝回调
 */
@ParentPackage("no-interceptor")
@Namespace("/zfbReturnTicket")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class ZFBReturnAction extends BaseAction{

	
	private static final long serialVersionUID = 1L;
	/**
	 * 订单相关
	 */
	@Autowired
	private ILeaseOrderService iLeaseOrderService;
	
	/***
	 * 支付宝确认信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws UnsupportedEncodingException 
	 */
	@Action(value = "zfbReturnMes")
	public void zfbReturnMes() throws UnsupportedEncodingException{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//批次号

		String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			
			//判断是否在商户网站中已经做过了这次通知返回的处理
				//如果没有做过处理，那么执行商户的业务程序
				//如果有做过处理，那么不执行商户的业务程序
				
//			out.println("success");	//请不要修改或删除

			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			int statu = 0;
			if(null!=batch_no && !"".equals(batch_no)){
				//根据退款批次号修改return_ticket的退款状态
				statu = iLeaseOrderService.updateReturnOrNot(batch_no);
			}
			//添加退款记录
			iLeaseOrderService.addReturnStatu(statu,0,batch_no);

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
		}
	}
}
