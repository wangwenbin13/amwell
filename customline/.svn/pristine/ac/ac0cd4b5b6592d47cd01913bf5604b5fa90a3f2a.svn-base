package com.amwell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.amwell.service.ILeaseOrderService;
import com.amwell.service.IUtilsService;
import com.amwell.vo.StatOutEntity;
import com.amwell.vo.StatOutLeaCou;
import com.amwell.vo.app.bean.AppVo_2;

/**
 * 
 * @author wangwenbin
 *
 * 2014-8-20
 */
/**
 * 定时执行的统计业务
 */
public class Begin{

	public static void main(String[] args) {
		//excute(null);
		//recordBack(null);
		queryPassengerTelHasBuyLine();
		//fromPassengerToImUserInfo();
		//fromCaiWuExcelToDb();
	}
	
	public static boolean  excute(ILeaseOrderService iLeaseOrderService){
		
		try{
			if(null==iLeaseOrderService){
				ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
				iLeaseOrderService = (ILeaseOrderService) ctx.getBean("leaseOrderService");
			}
					
			/**
			 * 清空收入统计表(小猪巴士)
			 */
			int statu = iLeaseOrderService.truncateTable();
//			int statu = 0;
			if(statu!=0){
				System.out.println("清空表数据出现异常");
				return false;
			}else{
				
				System.out.println("清空表数据正常");
				
				
				/**每页记录数**/
				int pageSize = 200;
				/**起始页**/
				int page = 1;
				/**总页数**/
				int totalPage = 1;
				
				/**
				 * 找出总共需要修改的退票订单记录数
				 */
				int totalCount = iLeaseOrderService.queryStatOutLeaCouAllCount();
				
				totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
				
				System.out.println("更新支出表里面关于退票的金额错误数据=====>开始");
				
				List<StatOutLeaCou> leaCous =null;
				for(page = 1;page<=totalPage;page++){
					
					/***
					 * 更新支出表里面关于退票的金额错误数据
					 */
					/**1  找出退票的订单 以及该订单退票的张数(张数大于1张)
					 * 如果张数大于1张，则需要修改对应的金额，每一张对应的金额应为退票总金额除以张数
					 * ***/
					leaCous = iLeaseOrderService.queryStatOutLeaCou((page-1)*pageSize,pageSize);
					
					/***
					 * 2 更新退票张数大于1的支出数据金额
					 */
					if(leaCous!=null && leaCous.size()>0){
						MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
						for(int i=0;i<leaCous.size();i++){
							StatOutLeaCou leaCou = leaCous.get(i);
							BigDecimal a = leaCou.getOutMoney();
							/**比较该订单号支出的价格是否等于该订单号对应的退票记录中的退票金额，如果是，则需要修改**/
							boolean flag = iLeaseOrderService.compareLeaMon(leaCou);
							if(flag){
								BigDecimal b=new BigDecimal(leaCou.getCountTimes());
								System.out.println(a.divide(b,mc));
								/***更改对应的支出记录**/
								leaCou.setOutMoney(a.divide(b,mc));
								int count = iLeaseOrderService.updateStatOut(leaCou);
								System.out.println(leaCou.getLeaseOrderNo());
								System.out.println(count);
							}
						}
					}
					
				}
				
				
				System.out.println("更新支出表里面关于退票的金额错误数据=====>结束");
				
				
				/**
				 * 找出总共需要恢复的订单记录数
				 */
				totalCount = iLeaseOrderService.queryLeaseOrderCount();
				totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
				
				System.out.println("订单数据恢复开始");
				List<String> leaseOrderNos = null;
				if(totalCount>=1){
					for(page = 1;page<=totalPage;page++){
						/**
						 * 还原所有已支付订单的数据(小猪巴士)
						 */
						leaseOrderNos = iLeaseOrderService.getLeaseOrderNo((page-1)*pageSize,pageSize);
						if (null != leaseOrderNos && leaseOrderNos.size() > 0) {
							for (String leaseOrderNo : leaseOrderNos) {
								iLeaseOrderService.addStatTotal(leaseOrderNo);
							}
						} 
					}
					System.out.println("订单数据恢复完成");
				}
				else {
					System.out.println("没有需要恢复的订单数据");
				}
				
				
				
				/**
				 * 找出总共需要恢复的支出记录数
				 */
				totalCount = iLeaseOrderService.queryStatOutListCount();
				totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
				/**
				 * 从支出表里面还原数据到收入表(小猪巴士)
				 */
				if(totalCount>=1){
					System.out.println("支出订单数据恢复开始");
					for(page=1;page<=totalPage;page++){
						Map<String, Object> map = iLeaseOrderService.getStatOutListByPage(null,(page-1)*pageSize,pageSize);
						List<StatOutEntity> list = (List<StatOutEntity>) map.get("list");
						iLeaseOrderService.addStatTotalByStatOut(list);
					}
					System.out.println("支出订单数据恢复结束");
				}else{
					System.out.println("没有需要恢复的支出订单数据");
				}
				
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	/**
	 * 事务处理恢复订单
	 */
	public static boolean recordBack(ILeaseOrderService iLeaseOrderService){
		try {
			if(null==iLeaseOrderService){
				ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
				iLeaseOrderService = (ILeaseOrderService) ctx.getBean("leaseOrderService");
			}
			int statu = iLeaseOrderService.truncateTable();
//			int statu = 0;
			if(statu!=0){
				System.out.println("清空表数据出现异常");
				return false;
			}else{
				System.out.println("清空表数据正常");
				boolean flag = iLeaseOrderService.recordBack();
				if(flag){
					return true;
				}
			}
			
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	/**
	 * 修改所有的乘客密码
	 */
//	public static void main(String[] args){
////		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
////		IPassengerInfoService iLeaseOrderService = (IPassengerInfoService) ctx.getBean("passengerInfoService");
////		List<PassengerInfoEntity> telephonesAndId = iLeaseOrderService.getAllPassengerInfo();
////		if(null!=telephonesAndId && telephonesAndId.size()>0){
////			for(PassengerInfoEntity passinfo : telephonesAndId){
////				PassengerInfo passengerInfo = new PassengerInfo();
////				passengerInfo.setPassengerId(passinfo.getPassengerId());
////				passengerInfo.setPassWord(Sha1Utils.encrypt(passinfo.getTelephone()+"&"+"123456"));
////				iLeaseOrderService.updatePass(passengerInfo);
////			}
////		}
//		//String password = (Sha1Utils.encrypt(telephone+"&"+passWord));
//		String old = "2014-11-26 15:30:30";
//		String newTime = "2014-11-25 16:30:20";
//		int result = MyDate.diffHours(MyDate.strToUTCLong(newTime, "yyyy-MM-dd HH:mm:ss"), MyDate.strToUTCLong(old, "yyyy-MM-dd HH:mm:ss"));
//		System.out.println(result);
//		System.out.println(result>0);
//	}
	
	
	/**
	 * 获取购买过指定线路的乘客电话
	 */
	public static void queryPassengerTelHasBuyLine(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IUtilsService utilsService = (IUtilsService) ctx.getBean("utilsService");
		String telephones = utilsService.queryPassengerTelHasBuyLine();
		System.out.println(telephones);
	}
	
	/***
	 * 将所有的乘客ID放到im_user_info表里面
	 */
	public static void fromPassengerToImUserInfo(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IUtilsService utilsService = (IUtilsService) ctx.getBean("utilsService");
		utilsService.fromPassengerToImUserInfo();
	}
	
	/**
	 * 读取财务数据并保存到数据库
	 */
	public static void fromCaiWuExcelToDb(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IUtilsService utilsService = (IUtilsService) ctx.getBean("utilsService");
		Workbook wb = null;
		Map<Integer,String> map = new HashMap<Integer,String>();
		String[] filePahts = {"C:\\Users\\Administrator\\Desktop\\账单\\新建文件夹\\财付通4月对账单.xls","C:\\Users\\Administrator\\Desktop\\账单\\新建文件夹\\微信支付4月对账单.xls",
				"C:\\Users\\Administrator\\Desktop\\账单\\新建文件夹\\银联.xls","C:\\Users\\Administrator\\Desktop\\账单\\新建文件夹\\支付宝.xls"};
		List<AppVo_2> appweixin = new ArrayList<AppVo_2>();  //财付通-微信APP
		List<AppVo_2> weixin = new ArrayList<AppVo_2>();  //微信支付
		List<AppVo_2> yinlian = new ArrayList<AppVo_2>();  //银联
		List<AppVo_2> zhifubao = new ArrayList<AppVo_2>();  //支付宝
		for(int k = 0;k<filePahts.length;k++){
			File excelFile = new File(filePahts[k]);
			try {
				wb = WorkbookFactory.create(new FileInputStream(excelFile));
				Sheet sheet = wb.getSheetAt(0);
				// 得到总行数
				int rowNum = sheet.getLastRowNum();
				Row row = sheet.getRow(0);
				Cell cell = null;
				String cellValue = null;
				
				// 正文内容应该从第二行开始,第一行为表头的标题
				for (int i = 1; i <= rowNum; i++) {
					row = sheet.getRow(i);
					if(null==row){
						continue;
					}
					for(int j = 0;j<row.getLastCellNum();j++){
						cell = row.getCell(j);
						if(null!=cell){
							switch (cell.getCellType()){
							  case Cell.CELL_TYPE_STRING:
								   cellValue = cell.getRichStringCellValue().getString();
								   break;
							  case Cell.CELL_TYPE_NUMERIC:
								  //此处只处理一种数字数据格式
								  cellValue = String.valueOf(cell.getNumericCellValue());
								  break;
							default:cellValue = "0";
							}
						}else{
							cellValue = "0";
						}
						map.put(j, cellValue);
					}
				   AppVo_2 part = new AppVo_2();
				   if(k==0){
					   //财付通-微信APP
					   part.setA1(map.get(2).replace("`", "").trim());
					   part.setA2(map.get(6));
					   if(isNum(map.get(6))){
						   appweixin.add(part);
					   }
				   }else if(k==1){
					   //微信支付
					   part.setA1(map.get(2).replace("`", "").trim());
					   part.setA2(map.get(6));
					   if(isNum(map.get(6))){
						   weixin.add(part);
					   }
				   }else if(k==2){
					 //银联
					   part.setA1(map.get(1).replace("`", "").trim());
					   part.setA2(map.get(4));
					   if(isNum(map.get(4))){
						   yinlian.add(part);
					   }
				   }else if(k==3){
					 //支付宝
					   part.setA1(map.get(2).replace("`", "").trim());
					   part.setA2(map.get(6));
					   if(isNum(map.get(6))){
						   zhifubao.add(part);
					   }
				   }
				}
				
				
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		utilsService.fromCaiWuExcelToDb(appweixin,weixin,yinlian,zhifubao);
	}
	
	/**判断一个字符串是不是数字**/
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	 
}
