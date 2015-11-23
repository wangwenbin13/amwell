package com.amwell.action.order;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.service.ILeaseOrderService;
import com.amwell.utils.StringUtil;

/**
 * 
 * @author wangwenbin
 *
 * 2014-10-27
 */
/**
 * 添加收入统计记录的HTTP请求
 */
@ParentPackage("no-interceptor")
@Namespace("/addStatTotalHttpAction")
public class AddStatTotalHttpAction extends BaseAction{

	@Autowired
	private ILeaseOrderService iLeaseOrderService;
	
	@Action(value="addStatTotal",results = { @Result( type = "json") })
	public String addStatTotal() throws IOException{
		
		try {
			String leaseOrderNo = request.getParameter("leaseOrderNo");
			/**
			 * 判断该订单是否已经添加过收入记录
			 * 如果已经存在了，则不再添加
			 */
			int count = iLeaseOrderService.getStatTotalCountByLeaseOrderNo(leaseOrderNo);
			if(count==0){
				iLeaseOrderService.addStatTotal(leaseOrderNo);
			}
//			if(StringUtil.isEmpty(leaseOrderNo)){
//				getResponse().getWriter().print("error");
//			}else{
//				int statu = iLeaseOrderService.addStatTotal(leaseOrderNo);
//				if(-1==statu){
//					getResponse().getWriter().print("error");
//				}else{
//					getResponse().getWriter().print("success");
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			getResponse().getWriter().print("success");
		}
		return null;
	}
}
