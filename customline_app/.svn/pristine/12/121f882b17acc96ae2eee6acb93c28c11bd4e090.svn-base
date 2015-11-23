package com.pig84.ab.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.pig84.ab.dao.BaseDao;
import com.pig84.ab.dao.ICharteredLineDao;
import com.pig84.ab.utils.IdGenerator;
import com.pig84.ab.utils.JPush;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.PropertyManage;
import com.pig84.ab.vo.BcOrderEntiry;
import com.pig84.ab.vo.CharteredLineVo;
import com.pig84.ab.vo.PassengerInfo;
import com.pig84.ab.vo.SysMsgInfo;
import com.pig84.ab.vo.SysMsgUser;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_15;
import com.pig84.ab.vo.bean.AppVo_18;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_25;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_9;

/**
 * 包车相关
 * @author 
 *
 */
@Repository
public class CharteredLineDaoImpl extends BaseDao implements ICharteredLineDao {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**获取包车信息过期时间，格式yyyy-MM-dd hh:mm:ss**/
	public AppVo_1 getExpireTime(String bcLineId){
		String sql = "SELECT expireTime as a1 FROM bc_line WHERE bc_line_id = ?";
		Object[] params = new Object[1];
		params[0]=bcLineId;
		return queryBean(AppVo_1.class, sql,params);
	}

	/**查询当天乘客已发布的包车信息条数**/
	public int getCharteredLineCount(String passengerId){
		//查询创建时间为当天的包车信息条数，控制一天只能发布或重新发布5条数据
		String sql="SELECT bc_line_id FROM bc_line WHERE createOn BETWEEN CONCAT(CURRENT_DATE(),' ','00:00:00') AND CONCAT(CURRENT_DATE(),' ','23:59:59') and passengerId = ?";
		Object[] params = new Object[1];
		params[0] = passengerId;
		int count=queryCount(sql,params);
		return count;
	}

	/**发布包车信息**/
	public String saveCharteredLine(CharteredLineVo charteredLine,String savaType,String bcLineId) {
		
		//查询创建时间为当天的包车信息条数，控制一天只能发布或重新发布5条数据
		int count=getCharteredLineCount(charteredLine.getPassengerId());
		//从配置文件获取发布条数
		String charteredLineNum=PropertyManage.get("charteredLineNum");
		int num=StringUtils.isNotBlank(charteredLineNum)?Integer.parseInt(charteredLineNum):5;
		if(count>=num){
			return "2";//2 表示今天已发布5条，不能再添加
		}
		
		String sql="INSERT INTO bc_line(bc_line_id,businessType,beginAddress,endAddress,journeyRemark,expectPrice,charteredMode,startTime,returnTime,carAge," +
				"totalPassengers,totalCars,needInvoice,invoiceHeader,contacts,contactPhone,passengerId,lineStatus,provinceCode,cityCode,cityName,createOn,expireTime) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),DATE_ADD(NOW(), INTERVAL (SELECT paraValue FROM bc_set LIMIT 0,1) HOUR))";
		Object[] params = new Object[21];
		params[0] = IdGenerator.seq();
		params[1] = charteredLine.getBusinessType();
		params[2] = charteredLine.getBeginAddress();
		params[3] = charteredLine.getEndAddress();
		params[4] = charteredLine.getJourneyRemark();
		params[5] = charteredLine.getExpectPrice();
		params[6] = charteredLine.getCharteredMode();
		params[7] = charteredLine.getStartTime();
		params[8] = charteredLine.getReturnTime();
		params[9] = charteredLine.getCarAge();
		params[10] = charteredLine.getTotalPassengers();
		params[11] = charteredLine.getTotalCars();
		params[12] = charteredLine.getNeedInvoice();
		params[13] = charteredLine.getInvoiceHeader();
		params[14] = charteredLine.getContacts();
		params[15] = charteredLine.getContactPhone();
		params[16] = charteredLine.getPassengerId();
		params[17] = charteredLine.getLineStatus();
		params[18] = charteredLine.getProvinceCode();
		params[19] = charteredLine.getCityCode();
		params[20] = charteredLine.getCityName();

		int flag = executeSQL(sql, params);
		if(flag==1){//包车添加成功
			flag=sendMsg(charteredLine);
		}
		
        if(StringUtils.isNotBlank(savaType)&&savaType.equals("2")&&flag>0){//1.新加  2.覆盖
	    	//覆盖的时候需要将之前的信息删除
        	flag=updateLineStatus(bcLineId,5);//5.已删除
	    }
		return String.valueOf(flag);
	}
	
	/**保存消息并推送消息给用户**/
	private int sendMsg(CharteredLineVo charteredLine){
		int flag=0;
		PassengerInfo passenger=queryBeanById(PassengerInfo.class,charteredLine.getPassengerId(),"passenger_info","passengerId");
		
		//保存站内信息并推送给用户
		SysMsgInfo smi=new SysMsgInfo();
		smi.setTheModule("2");//所属模块（1.小猪巴士 2.包车）
		smi.setMsgType("1");//消息类型 0:短信消息 1:软件消息
		smi.setMsgSubType("0");//消息子类型 --->软件消息:待定 --->系统消息 0:系统消息 1:人工消息
		String beginAddress=charteredLine.getBeginAddress();
		String endAddress=charteredLine.getEndAddress();
		smi.setMsgTitle("包车（"+(beginAddress.length()<5?beginAddress:beginAddress.substring(0,5))+"-"+(endAddress.length()<5?endAddress:endAddress.substring(0,5))+"线路 待审核！）");//标题(标题不超过50个汉字)
		String content="尊敬的用户您好，您于"+charteredLine.getCreateOn()+"所填写的发车时间为："+charteredLine.getStartTime()+"，"+beginAddress+"-"+endAddress+"的包车信息已成功提交，正在等待审核，请稍后！" +
				"您可以通过个人中心-包车订单查看您所提交的包车信息。我们将尽快为您处理本信息，我公司工作时间为"+PropertyManage.get("workingHours")+"，若有任何疑问请拨打客服电话"+PropertyManage.get("companyTel");
		smi.setMsgContext(content);//内容
		smi.setMsgTarget("0");//指定发送对象类型 0：乘客 1：司机 2：商家
		smi.setCreateBy("SYSTEM");
		smi.setCreateOn(MyDate.Format.DATETIME.now());
		smi.setIssend("0");//发送状态（0：已发送 1：未发送）
		flag=updateData(smi,"sys_msg_info","msgId");
		if(flag==1){
			
			SysMsgUser smu=new SysMsgUser();
			smu.setUserId(passenger.getPassengerId());
			smu.setMsgId(smi.getMsgId());
			smu.setReadFlag("0");//--->软件消息:已读标志 0：未读 1：已读 --->短信消息 ：只表示发送对应关系
			smu.setSendPhoneNo(passenger.getTelephone());
			smu.setSendTime(MyDate.Format.DATETIME.now());
			flag=updateData(smu,"sys_msg_user","localId");
			if(flag==1){
				String mobileNo[]=new String[1];
				mobileNo[0]=passenger.getTelephone();
				AppVo_6 vo = new AppVo_6();
				vo.setA1(smi.getMsgTitle());//标题
				vo.setA2(smi.getMsgContext());//消息正文
				vo.setA3("");//图片URL
				vo.setA4(smi.getMsgId());//消息ID
				vo.setA5(PropertyManage.get("companyTel"));//客服电话
				vo.setA6(smi.getTheModule());//所属模块
				
				//调用消息推送的方法
				JPush.sendMessage(vo, mobileNo);
			}
		}
		return flag;
	}
	
	/**查询过期时间配置**/
	public AppVo_1 getExpireTimeSet(){
		String sql = "select paraValue as a1 from bc_set";
		AppVo_1 appVo_1=queryBean(AppVo_1.class, sql);
		if(null==appVo_1||StringUtils.isBlank(appVo_1.getA1())){
			logger.warn("暂无过期时间，请到运营平台app设置处进行设置！");
			appVo_1.setA1("48");//单位小时
		}
		return appVo_1;
	}
	
	/**查询登录用户已发布的包车列表**/
	public List<AppVo_9> getCharteredLineList(String passengerId,String lineStatus,int pageSize,int currentPage){
		String sql = "SELECT bc_line_id as a1,beginAddress as a2,endAddress as a3,startTime as a4,lineStatus as a5,(SELECT COUNT(id) FROM bc_business_bidding b WHERE b.bc_line_id = bl.bc_line_id and b.offerStatus = '1') as a6," +
				"createOn as a7,NOW() AS a8,(UNIX_TIMESTAMP(bl.expireTime) - UNIX_TIMESTAMP(bl.createOn)) as a9 FROM bc_line bl " +
				"WHERE passengerId = ?";
		List<Object> conditions=new ArrayList<Object>();
		conditions.add(passengerId);
		//动态处理包车信息状态条件
		if(StringUtils.isNotBlank(lineStatus)){
			String[] str=lineStatus.split(",");
			StringBuffer sb=new StringBuffer();
			for (String string : str) {
				sb.append("?,");
				conditions.add(string);
			}
			sql+= " and  bl.lineStatus in("+sb.substring(0,sb.length()-1)+")";
		}
		List<AppVo_9> list=queryByPage(AppVo_9.class, sql,currentPage, pageSize,conditions.toArray());
		if(!(null==list||list.size()==0)){
			String temp=null;
			for (AppVo_9 appVo_9 : list) {
				appVo_9.setA8(appVo_9.getA8().substring(0,19));
				temp=StringUtils.isBlank(appVo_9.getA9())?"0":appVo_9.getA9();
				if(temp.indexOf(".")==-1){
					appVo_9.setA9((Integer.parseInt(temp)/60)+"");
				}
				else{
					appVo_9.setA9((Integer.parseInt(temp.substring(0,temp.indexOf(".")))/60)+"");
				}
			}
		}
		else{
			list=new ArrayList<AppVo_9>();
		}
		return list;
	}
	
	/**查询已有报价包车的商家报价列表**/
	public List<AppVo_3> getBusinessBiddingList(String charteredLineId){
		String sql = "SELECT id AS a1,businessName AS a2,totalCost AS a3 FROM bc_business_bidding WHERE bc_line_id = ? and offerStatus = '1'";
		Object[] params = new Object[1];
		params[0]=charteredLineId;
		List<AppVo_3> list=queryList(AppVo_3.class,sql,params);
		if(null==list||list.size()==0){
			list=new ArrayList<AppVo_3>();
		}
		return list;
	}
	
	/**查询包车需求详情**/
	public AppVo_15 getCharteredLineDetail(String charteredLineId){
		String sql = "SELECT beginAddress AS a1,endAddress AS a2,journeyRemark AS a3,charteredMode AS a4,startTime AS a5,returnTime AS a6,carAge AS a7," +
				"totalPassengers AS a8,totalCars AS a9,needInvoice AS a10,contacts AS a11,contactPhone AS a12,bc_line_id as a13,cityName as a14," +
				"rejectContent as a15 FROM bc_line where bc_line_id = ?";
		Object[] params = new Object[1];
		params[0]=charteredLineId;
		return queryBean(AppVo_15.class, sql,params);
	}
	
	/**查询商家报价详情**/
	public AppVo_18 getBcBiddingDetail(String bcBiddingId,String userId){
		String sql = "SELECT basicCost AS a1,oilCost AS a2,driverMealCost AS a3,parkingFee AS a4,tolls AS a5,driverHotelCost AS a6,passengerNotice AS a7," +
				"returnRuleId AS a9,additionalServices AS a10,remark AS a11,totalCost as a12,bc_line_id as a13,businessId as a14," +
				"driverWorkHour as a15,overtimeCost as a16,limitMileage as a17,overmileageCost as a18,businessName as a19,businessId as a20,oilCost as a21 " +
				"FROM bc_business_bidding WHERE id = ?";
		Object[] params = new Object[1];
		params[0]=bcBiddingId;
		AppVo_18 vo=new AppVo_18();
		AppVo_25 vo_temp=queryBean(AppVo_25.class, sql,params);
		vo.setA1(vo_temp.getA1());
		vo.setA2(vo_temp.getA2());
		vo.setA3(vo_temp.getA3());
		vo.setA4(vo_temp.getA4());
		vo.setA5(vo_temp.getA5());
		vo.setA6(vo_temp.getA6());
		vo.setA9(vo_temp.getA9());
		vo.setA10(vo_temp.getA10());
		vo.setA11(vo_temp.getA11());
		vo.setA12(vo_temp.getA12());
		vo.setA13(vo_temp.getA13());
		vo.setA14(vo_temp.getA14());
		vo.setA16(vo_temp.getA19());
		
		//拼接乘客须知
		StringBuffer passengerNotice=new StringBuffer();
		int theTempNum=1;
		passengerNotice.append(theTempNum+"、不计入报价的项，若实际运行时，发生此类费用，将在行程结束时现场结算；<br>");
		//包车方式，1：单趟 2:往返 3:包天
		if(StringUtils.isBlank(vo_temp.getA21())||"0.00".equals(vo_temp.getA21())){
			theTempNum++;
			passengerNotice.append(theTempNum+"、本次报价不包含油费，客户用车完毕后与司机将油箱的油料补满。（以油表为准）；<br>");
		}
		vo.setA7(passengerNotice.toString());
		
		//获取商家电话
		sql = "SELECT contactsPhone as a1 FROM mgr_business WHERE businessId = ?";
		params=new Object[1];
		params[0]=vo_temp.getA20();
		AppVo_1 v1=queryBean(AppVo_1.class, sql,params);
		vo.setA17(v1.getA1());
		
		//根据报价id查询车辆信息
		sql="SELECT carSeats AS a1,carType AS a2,totalCars AS a3,carAge AS a4 FROM bc_business_car WHERE bidding_id = ?";
		params=new Object[1];
		params[0]=bcBiddingId;
		List<AppVo_4> list=queryList(AppVo_4.class, sql,params);
		if(!(null==list||list.size()==0)){
			StringBuffer str=new StringBuffer();
			for (AppVo_4 appVo_4 : list) {
				str.append(appVo_4.getA1()+"座"+appVo_4.getA2()+" "+appVo_4.getA3()+"辆  车龄"+appVo_4.getA4()+"年；\n");
			}
			vo.setA8(str.toString());
		}
		
		//根据退票规则ID查询退票规则信息
		sql="SELECT noReturn AS a1,returnOneTime AS a2,returnOnePer AS a3,returnTwoTime AS a4,returnTwoPer AS a5 FROM bc_return_rule WHERE ruleId = ?";
		params=new Object[1];
		params[0]=vo.getA9();
		AppVo_6 appVo_6=queryBean(AppVo_6.class, sql,params);
		if(null!=appVo_6){
			StringBuffer str=new StringBuffer();
			str.append("1、发车前"+appVo_6.getA1()+"小时不退款；<br>");
			str.append("2、在发车"+appVo_6.getA2()+"小时前可以申请退款，退款手续费为"+appVo_6.getA3()+"%；<br>");
			str.append("3、在发车"+appVo_6.getA4()+"小时前可以申请退款，退款手续费为"+appVo_6.getA5()+"%；<br>");
			vo.setA9(str.toString());
		}
		
		//优惠券最小限度
		   String sql_gif = "SELECT MIN(gg.giftCon) as a1 FROM gf_coupon_passenger gcp,gf_coupon_gift gcg,gf_coupon gc,gf_gifts gg " +
		   		"WHERE gcp.couponGiftId = gcg.couponGiftId  AND gcg.couponId = gc.couponId AND gcg.giftPriId = gg.giftPriId  " +
		   		"AND gcp.getState = '1' AND gcp.useState = '0' AND gg.giftType IN('1','2') AND gc.couponType IN('0','1') AND " +
		   		"(NOW() >= gc.effectiveTime AND NOW() <= gc.expirationTime)  AND gcp.passengerId = ?";
		   params=new Object[1];
		   params[0]=userId;
		   AppVo_1 vo_gif = queryBean(AppVo_1.class, sql_gif,params);
		   if(null!=vo_gif&&null!=vo_gif.getA1()&&!"".equals(vo_gif.getA1())){
			   vo.setA18(vo_gif.getA1());
		   }else{
			   vo.setA18("-1");
		   }
		return vo;
	}
	
	/**支付成功后保存订单信息**/
	public String saveBcOrder(BcOrderEntiry order) {
		//获取不重复的订单编号
		order.setOrderNo(getBcOrderNo());
		int flag = updateData(order,"bc_order","orderId");
		if(flag==1){//订单保存成功，则修改包车信息状态
			flag=updateLineStatus(order.getBc_line_id(),6);//6.已支付
		}
		if(flag==1){//前面的操作成功
			//获取商家电话
			String sql="SELECT contactsPhone AS a1 FROM mgr_business WHERE businessId = ?";
			Object[] params = new Object[1];
			params[0]=order.getBusinessId();
			AppVo_1 vo=queryBean(AppVo_1.class, sql,params);
			
			//获取包车信息
			sql="SELECT beginAddress AS a1,endAddress AS a2,startTime AS a3,createOn as a4 FROM bc_line WHERE bc_line_id = ?";
			params=new Object[1];
			params[0]=order.getBc_line_id();
			AppVo_4 v4=queryBean(AppVo_4.class, sql,params);
			//发送短信给商家
			// XXX Should not send SMS in DAO
			new Message("您好，订单号为%s，出车时间为：%s，%s—%s 的包车已完成支付，请尽快完成调度。", order.getOrderNo(), v4.getA3(), v4.getA1(), v4.getA2())
					.sendTo(vo.getA1());
			
			flag=sendMsgAfterSaveOrder(order.getPassengerId(),v4);
		}
		return String.valueOf(flag);
	}
	
	//保存订单后发送站内信给乘客
	private int sendMsgAfterSaveOrder(String passengerId,AppVo_4 v4){
		PassengerInfo passenger=queryBeanById(PassengerInfo.class,passengerId,"passenger_info","passengerId");
		
		//保存站内信息并推送给用户
		SysMsgInfo smi=new SysMsgInfo();
		smi.setTheModule("2");//所属模块（1.小猪巴士 2.包车）
		smi.setMsgType("1");//消息类型 0:短信消息 1:软件消息
		smi.setMsgSubType("0");//消息子类型 --->软件消息:待定 --->系统消息 0:系统消息 1:人工消息
		smi.setMsgTitle("包车（"+(v4.getA1().length()<5?v4.getA1():v4.getA1().substring(0,5))+"-"+(v4.getA2().length()<5?v4.getA2():v4.getA2().substring(0,5))+"线路 支付成功！）");//标题
		String msgContent="尊敬的用户您好，您于"+v4.getA4()+"所发布的发车时间为："+v4.getA3()+"，"+v4.getA1()+"-"+v4.getA2()+"的包车信息已支付成功！" +
				"请前往个人中心-包车订单查看吧！我公司工作时间为"+PropertyManage.get("workingHours")+"，若有任何疑问请拨打客服电话"+PropertyManage.get("companyTel");
		smi.setMsgContext(msgContent);//内容
		smi.setMsgTarget("0");//指定发送对象类型 0：乘客 1：司机 2：商家
		smi.setCreateBy("SYSTEM");
		smi.setCreateOn(MyDate.Format.DATETIME.now());
		smi.setIssend("0");//发送状态（0：已发送 1：未发送）
		int flag=updateData(smi,"sys_msg_info","msgId");
		if(flag==1){
			
			SysMsgUser smu=new SysMsgUser();
			smu.setUserId(passenger.getPassengerId());
			smu.setMsgId(smi.getMsgId());
			smu.setReadFlag("0");//--->软件消息:已读标志 0：未读 1：已读 --->短信消息 ：只表示发送对应关系
			smu.setSendPhoneNo(passenger.getTelephone());
			smu.setSendTime(MyDate.Format.DATETIME.now());
			flag=updateData(smu,"sys_msg_user","localId");
			if(flag==1){
				String mobileNo[]=new String[1];
				mobileNo[0]=passenger.getTelephone();
				AppVo_6 v_6 = new AppVo_6();
				v_6.setA1(smi.getMsgTitle());//标题
				v_6.setA2(smi.getMsgContext());//消息正文
				v_6.setA3("");//图片URL
				v_6.setA4(smi.getMsgId());//消息ID
				v_6.setA5(PropertyManage.get("companyTel"));//客服电话
				v_6.setA6(smi.getTheModule());//所属模块
				
				//调用消息推送的方法
				JPush.sendMessage(v_6, mobileNo);
			}
		}
		return flag;
	}
	
	//获取订单号
	private String getBcOrderNo(){
		//检查订单号是否重复
		String orderNo = IdGenerator.seq();
		String sql_orderNo = "select orderId from bc_order where orderNo = ?";
		Object[] params = new Object[1];
		params[0]=orderNo;
		int count = queryCount(sql_orderNo,params);
		
		if(count>0){//订单号重复，从新生成
			getBcOrderNo();
		}
		return orderNo;
	}
	
	/**查询商家介绍信息**/
	public AppVo_2 getBusinessRemark(String businessId){
		String sql = "SELECT remark as a1 FROM mgr_business WHERE businessId = ?";
		Object[] params = new Object[1];
		params[0]=businessId;
		return queryBean(AppVo_2.class, sql,params);
	}
	
	/**修改包车信息状态**/
	public int updateLineStatus(String bcLineIds,int lineStatus){
		String sql="UPDATE bc_line SET lineStatus = ?";
		List<Object> conditions=new ArrayList<Object>();
		conditions.add(lineStatus);
		//动态处理多个包车id
		if(StringUtils.isNotBlank(bcLineIds)){
			String[] str=bcLineIds.split(",");
			StringBuffer sb=new StringBuffer();
			for (String string : str) {
				sb.append("?,");
				conditions.add(string);
			}
			sql+= " WHERE  bc_line_id in("+sb.substring(0,sb.length()-1)+")";
		}
		return executeSQL(sql, conditions.toArray());
	}
	
	/**查询已开通包车业务的城市信息**/
	public List<AppVo_3> getBcCityList(){
		String sql = "SELECT DISTINCT provinceCode AS a1,areaCode AS a2,cityName AS a3 FROM mgr_business WHERE businessType IN('2','3')";
		List<AppVo_3> list=queryList(AppVo_3.class,sql);
		if(null==list||list.size()==0){
			list=new ArrayList<AppVo_3>();
		}
		return list;
	}
	
	/**查询包车协议**/
	public AppVo_2 getUserAgreement(){
		String sql = "SELECT agreementContent as a2 FROM user_agreement where agreementTitle = '包车协议'";
		AppVo_2 vo=queryBean(AppVo_2.class, sql);
		if(null==vo||StringUtils.isBlank(vo.getA2())){
			vo.setA2("暂无协议内容，请到运营平台用户协议处进行设置！");
		}
		return vo;
	}
	
	/**获取报价信息**/
	public AppVo_4 getBcBiddingById(String bcBiddingId){
	    Object[] params=null;
		String sql = "SELECT a.bc_line_id as a1,businessid as a2,totalcost as a3,b.passengerId as a4 FROM bc_business_bidding a "
				+ "LEFT JOIN bc_line b ON a.bc_line_id = b.bc_line_id WHERE a.id = ?";
		params = new Object[1];
		params[0] = bcBiddingId;
		return queryBean(AppVo_4.class, sql,params);
	}
}
