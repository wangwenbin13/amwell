package com.pig84.ab.action;

import java.io.IOException;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.pig84.ab.service.IGPSService;
import com.pig84.ab.utils.Html;
import com.pig84.ab.utils.Json;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.vo.GPS;

/**
 * GPS定位相关
 * @author zhangqiang
 *
 */
@ParentPackage("no-interceptor")
@Namespace("/app_GPS")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class GPSAction  extends BaseAction {

	@Autowired
	private IGPSService gps;

	/**
	 * 获取车的位置信息
	 * @return
	 * @throws IOException 
	 */
	@Action(value = "getBusPosition",results={@Result(type="json")})
	public String getBusPosition() throws IOException{
		GPS vo_info = new GPS();
		String lineBaseId = request.getParameter("lineBaseId");
		String No = request.getParameter("No");//车牌号码
		No = Html.purge(No);
		String GPS_No = gps.getBusPosition(No);
		if(GPS_No!=null){
			String str = gps.getGpsInfo(GPS_No,lineBaseId);
			if (str!=null && !"".equals(str)) {
				str = str.replace("Time", "time");
				 vo_info = Json.fromJson(str, GPS.class); //cache中GPS位置信息
				 long millis = Long.valueOf(vo_info.getTime()) * 1000L;
				 vo_info.setTime(MyDate.Format.DATETIME.format(new Date(millis)));
			}
		}
		write(vo_info);
		return null;
	}
	
}
