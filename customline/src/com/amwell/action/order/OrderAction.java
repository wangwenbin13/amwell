package com.amwell.action.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.amwell.action.BaseAction;
import com.amwell.commons.JsonWriter;
import com.amwell.entity.LeaseOrderModelVo;
import com.amwell.entity.Page;
import com.amwell.entity.RechargeVo;
import com.amwell.entity.Search;
import com.amwell.entity.StatTotalVo;
import com.amwell.service.ILeaseOrderService;
import com.amwell.service.ILineService;
import com.amwell.service.IMgrbusinessService;
import com.amwell.service.IPublishLineService;
import com.amwell.service.ISysAreaService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.LineEntity;
import com.amwell.vo.StatTotalEntity;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;

/**
 * @author wangwenbin
 *
 * 2014-8-14
 */
/**
 * 订单ACTION
 */
@ParentPackage("user-finit")
@Namespace("/orderAction")
@SuppressWarnings("all")
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class OrderAction extends BaseAction{
	
	private static final long serialVersionUID = 7670917133890965731L;
	
	@Autowired
	private IPublishLineService publishLineService;

	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize = 10;
	
	/**
	 * 记录数的下标起始数 limit 0,10中的0
	 */
	private Integer currentPageIndex = 0;
	
	/**
	 *集合大小 
	 */
	private Integer listSize = 0;
	
	@Autowired
	private ILeaseOrderService iLeaseOrderService;
	
	@Autowired
	private ILineService lineService;
	
	private List<LeaseOrderModelVo> leaseOrderModelVos;
	
	/**
	 * 金额合计
	 */
	private BigDecimal totalMoney;
	
	@Autowired
	private IMgrbusinessService iMgrbusinessService;
	
	/**
	 * 订单Vo
	 */
	private LeaseOrderModelVo leaseOrderModelVo;
	
	/**
	* 区域
	*/
	@Autowired
	private ISysAreaService iSysAreaService;
	
	/**
	 * 区域省份集合
	 */
	private List<SysArea> proSysAreas;
	
	/**
	 * 区域城市集合
	 */
	private List<SysArea> citySysAreas;
	
	/**
	 * 所有订单查询
	 */
	@Action(value="getAllOrderList",results={@Result(name="success",location="../../view/order/allOrderList.jsp")})
	public String getAllOrderList()throws Exception{
		
		long timeS=new Date().getTime();
		
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		
		if(null==session.get("toDetailCurrentPageIndex")){
			this.session.remove("search");
		}
		
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof Integer) {
				this.currentPageIndex = (Integer) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPageIndex = request.getParameter("currentPageIndex") == null ? 0: Integer.parseInt(request.getParameter("currentPageIndex"));
		}
		
//		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		
		/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
		search = (Search) (search == null?session.get("order_search"):search);
		if(null==search){
			search = new Search();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isEmpty(search.getField01())){
			search.setField01(sdf.format(new Date()));
		}
		if(StringUtils.isEmpty(search.getField02())){
			search.setField02(sdf.format(new Date()));
		}
		
		SysAdminVo sysAdmin = (SysAdminVo) request.getSession().getAttribute("userInfo");
		request.setAttribute("provinceCode", sysAdmin.getProvinceCode());
		request.setAttribute("cityCode", sysAdmin.getCityCode());
		if(!StringUtils.isEmpty(sysAdmin.getProvinceCode())&&!StringUtils.isEmpty(sysAdmin.getCityCode())){
			if(search==null){
				search = new Search();
			}
			search.setField13(sysAdmin.getProvinceCode());
			search.setField14(sysAdmin.getCityCode());
		}
		
		map = iLeaseOrderService.listByPage(search, currentPageIndex, pageSize);
		this.session.put("order_search", search);
		leaseOrderModelVos = (List) map.get("list");//数据对象
		if(null!=leaseOrderModelVos && leaseOrderModelVos.size()>0){
			/**
			 * 获取所有的站点
			 */
//			List<StationInfo> lineStationEntities  = lineService.getAllStations();
//			Map<String,String> maps = new HashMap<String,String>();
//			if(null!=lineStationEntities && lineStationEntities.size()>0){
//				for(StationInfo entity :lineStationEntities){
//					maps.put(entity.getStationInfoId(), entity.getStationName());
//				}
//			}
			
			/**
			 * 省份城市maps
			 */
			Map<String,String> proCityMaps = (Map<String, String>) request.getSession().getAttribute("proCityMaps");
			if(null == proCityMaps){
				proCityMaps = new HashMap<String,String>();
				sysArea = new SysArea();
				List<SysArea>  proCities = iSysAreaService.querySysArea(sysArea);
				if(null!=proCities && proCities.size()>0){
					/**
					 * 用FdCode 和 areaCode 为key,name为value
					 */
					for(SysArea area : proCities){
						proCityMaps.put(area.getFdCode()+"/"+area.getArearCode(), area.getAreaName());
					}
				}
				request.getSession().setAttribute("proCityMaps", proCityMaps);
			}
			
			for(LeaseOrderModelVo leaseOrderModelVo : leaseOrderModelVos){
//				if(!StringUtils.isEmpty(leaseOrderModelVo.getUstation())){
//					/**
//					 * 上车点
//					 */
//					leaseOrderModelVo.setUstation(maps.get(leaseOrderModelVo.getUstation()));
//				}
//				if(!StringUtils.isEmpty(leaseOrderModelVo.getDstation())){
//					/**
//					 * 下车点
//					 */
//					leaseOrderModelVo.setDstation(maps.get(leaseOrderModelVo.getDstation()));
//				}
				if(!StringUtils.isEmpty(leaseOrderModelVo.getProvinceCode()) && null!=proCityMaps){
					/**
					 * 省份
					 */
					String proCode = leaseOrderModelVo.getProvinceCode();
					leaseOrderModelVo.setProvinceCode(proCityMaps.get("-1"+"/"+proCode));
					
					
					/**
					 * 城市
					 */
					if(!StringUtils.isEmpty(leaseOrderModelVo.getCityCode())){
						leaseOrderModelVo.setCityCode(proCityMaps.get(proCode+"/"+leaseOrderModelVo.getCityCode()));
					}
				}
			}
		}
		
		page = (Page) map.get("page");//分页对象
		listSize = leaseOrderModelVos==null?0:leaseOrderModelVos.size();
//		totalMoney =(BigDecimal) map.get("totalMoney");
		
		long timeE=new Date().getTime();
		System.out.println("===========加载【订单列表】耗时："+(timeE-timeS)+"毫秒（1000毫秒=1秒）===========");
		return SUCCESS;
	}
	
	
	/**
	 * 获取订单详情
	 */
	@Action(value="orderDetail",results={@Result(name="success",location="../../view/order/orderDetail.jsp")})
	public String orderDetail(){
		leaseOrderModelVo = iLeaseOrderService.getLeaseOrderById(leaseOrderModelVo);
		List<StationInfo> lineStationEntities  = publishLineService.getAllStations();
		Map<String,String> maps = new HashMap<String,String>();
		if(null!=lineStationEntities && lineStationEntities.size()>0){
			for(StationInfo entity :lineStationEntities){
				maps.put(entity.getId(), entity.getName());
			}
		}
		if(!StringUtils.isEmpty(leaseOrderModelVo.getUstation())){
			leaseOrderModelVo.setUstation(maps.get(leaseOrderModelVo.getUstation()));
		}
		if(!StringUtils.isEmpty(leaseOrderModelVo.getDstation())){
			leaseOrderModelVo.setDstation(maps.get(leaseOrderModelVo.getDstation()));
		}
		
		super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
		
		return SUCCESS;
	}
	
	
	
	/**
	 * 导出 所有订单Excel
	 * @throws IOException 
	 */
	@Action(value="exportExcel")
	public void exportExcel() throws IOException{
		getResponse().setContentType("application/msexcel;charset=GBK");
		
		if(null==search){
			search = new Search();
		}
		
		//根据条件获得对应的订单记录数
		int size = iLeaseOrderService.queryLeaseNuByCon(search); 
		if(size>0){
			int everySize = 50000;
			int totalPageSize = size%everySize==0?size/everySize:(size/everySize)+1;
			List<LeaseOrderModelVo> lists = new ArrayList<LeaseOrderModelVo>();
			HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
			String title = "所有订单列表"; //导出列表头名称
			for(int k=1;k<=totalPageSize;k++){
				map = iLeaseOrderService.listByPage(search,(k-1)*everySize, everySize);
				
			    lists = (List) map.get("list");
			    lists = setListValue(lists);
				// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
				
				HSSFSheet sheet = wb.createSheet();
				wb.setSheetName((k-1), "列表"+k);
				//设置单元格长度
				sheet.setDefaultColumnWidth((short) 20);
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow row = sheet.createRow((int) 0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

				HSSFCell cell ;
				String[] cellNames = {"序号","时间", "订单号", "乘客","线路类型","服务商家","线路名称","上车点","下车点","省份城市","金额(元)","订单来源","支付方式","支付状态","联系电话"};
				for(int i = 0;i<cellNames.length;i++){
					cell = row.createCell((short) i);
					cell.setCellValue(cellNames[i]);
					cell.setCellStyle(style);
				}

				// 第五步，写入实体数据 实际应用中这些数据从数据库得到，
				for (int i = 0; i < lists.size(); i++) {
					row = sheet.createRow((int) (i + 1));
					LeaseOrderModelVo stat = (LeaseOrderModelVo) lists.get(i);
					// 第四步，创建单元格，并设置值
					cell = row.createCell((short) 0);
					cell.setCellValue((double) i + 1);
					cell.setCellStyle(style);
					
					cell = row.createCell((short) 1);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getLeaseOrderTime());
					
					cell = row.createCell((short) 2);
					cell.setCellValue(stat.getLeaseOrderNo());
					cell.setCellStyle(style);
					
					cell = row.createCell((short) 3);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getNickName());
					
					cell = row.createCell((short) 4);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getLineSubType());
					
					
					cell = row.createCell((short) 5);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getBrefName());
					
					cell = row.createCell((short) 6);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getLineName());
					
					cell = row.createCell((short) 7);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getUstation());
					
					cell = row.createCell((short) 8);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getDstation());
					
					cell = row.createCell((short) 9);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getProvinceCode());
					
					cell = row.createCell((short) 10);
					cell.setCellStyle(style);
					if(null==stat.getPayMoney()){
						cell.setCellValue("0.00");
					}else{
						cell.setCellValue(stat.getPayMoney().toString());
					}
					
					cell = row.createCell((short) 11);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getSourceFrom_s());
					
					cell = row.createCell((short) 12);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getPayModel_s());
					
					cell = row.createCell((short) 13);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getPayStatus_s());
					
					cell = row.createCell((short) 14);
					cell.setCellStyle(style);
					cell.setCellValue(stat.getTelephone());
				}
				
				row = sheet.createRow((int) (lists.size() + 1));
				cell = row.createCell((short)0);
				cell.setCellValue((double) lists.size() + 1);
				cell.setCellStyle(style);
				
				cell = row.createCell((short)13);
				cell.setCellValue("合计");
				cell.setCellStyle(style);
				
				cell = row.createCell((short)14);
				cell.setCellValue(String.valueOf(session.get("count_leaseOrder")));
				cell.setCellStyle(style);
				
			}
			
			//前面的数据都分页处理完了，统一导出到一个excel
			String agent = request.getHeader("User-Agent");
			 if (null!=agent && agent.indexOf("Firefox") > -1) 
	         { 
			    	title = new String(title.getBytes("utf-8"),"iso-8859-1");
	         } 
	         else
	         {
	         	title = String.valueOf(URLEncoder.encode(title, "UTF-8"));
	         }
			String sFileName = title + ".xls";
			String attachment = "attachment;filename=\""+sFileName + "\"";
			getResponse().setHeader("Content-Disposition", attachment);
			getResponse().setHeader("Connection", "close");
			getResponse().setHeader("Content-Type", "application/vnd.ms-excel");
			
			wb.write(getResponse().getOutputStream());
		}
	}
	
	/**
	 * 所有订单集合赋值
	 * @param lists
	 * @return
	 */
	private List setListValue(List<LeaseOrderModelVo> lists){
		if(null!=lists && lists.size()>0){
			/**
			 * 获取所有的站点
			 */
			List<StationInfo> lineStationEntities  = publishLineService.getAllStations();
			Map<String,String> maps = new HashMap<String,String>();
			if(null!=lineStationEntities && lineStationEntities.size()>0){
				for(StationInfo entity :lineStationEntities){
					maps.put(entity.getId(), entity.getName());
				}
			}
			
			SysArea sysArea = new SysArea();
			/**
			 * 省份城市maps
			 */
			Map<String,String> proCityMaps = (Map<String, String>) request.getSession().getAttribute("proCityMaps");
			if(null == proCityMaps){
				proCityMaps = new HashMap<String,String>();
				sysArea = new SysArea();
				List<SysArea>  proCities = iSysAreaService.querySysArea(sysArea);
				if(null!=proCities && proCities.size()>0){
					/**
					 * 用FdCode 和 areaCode 为key,name为value
					 */
					for(SysArea area : proCities){
						proCityMaps.put(area.getFdCode()+"/"+area.getArearCode(), area.getAreaName());
					}
				}
				request.getSession().setAttribute("proCityMaps", proCityMaps);
			}
			
			
	    	for(LeaseOrderModelVo leaseOrderModelVo:lists){
	    		/**
	    		 * 线路类型
	    		 * 0:上下班  1:旅游(自由行)   
	    		 */
	    		if(null!=leaseOrderModelVo.getLineSubType()){
	    			if("0".equals(leaseOrderModelVo.getLineSubType())){
	    				leaseOrderModelVo.setLineSubType("上下班");
	    			}
	    			if("1".equals(leaseOrderModelVo.getLineSubType())){
	    				leaseOrderModelVo.setLineSubType("自由行");
	    			}
	    		}
	    		/**
	    		 * 上车点
	    		 */
	    		String ustation = "";
	    		if(!StringUtils.isEmpty(leaseOrderModelVo.getUstation())){
	    			ustation = maps.get(leaseOrderModelVo.getUstation());
	    		}
	    		leaseOrderModelVo.setUstation(ustation);
	    		
	    		/**
	    		 * 下车点
	    		 */
	    		String dstation = "";
	    		if(!StringUtils.isEmpty(leaseOrderModelVo.getDstation())){
	    			dstation = maps.get(leaseOrderModelVo.getDstation());
	    		}
	    		leaseOrderModelVo.setDstation(dstation);
	    		
	    		/**
	    		 * 订单来源
	    		 * 订单来源表 0:APP 1:微信 2：pc
	    		 */
	    		String sourceFrom_s = "";
	    		if(null!=leaseOrderModelVo.getSourceFrom()){
	    			if(0==leaseOrderModelVo.getSourceFrom()){
	    				sourceFrom_s = "APP";
	    			}else if(1==leaseOrderModelVo.getSourceFrom()){
	    				sourceFrom_s = "微信";
	    			}else if(2==leaseOrderModelVo.getSourceFrom()){
	    				sourceFrom_s = "pc";
	    			}
	    		}
	    		leaseOrderModelVo.setSourceFrom_s(sourceFrom_s);
	    		
	    		/**
	    		 * 乘客
	    		 */
	    		String displayId = "";
	    		if(null!=leaseOrderModelVo.getDisplayId()){
	    			displayId = leaseOrderModelVo.getDisplayId();
	    		}
	    		String nickName = "";
	    		if(null!=leaseOrderModelVo.getNickName()){
	    			nickName = leaseOrderModelVo.getNickName();
	    		}
	    		leaseOrderModelVo.setNickName(displayId+"/"+nickName);
	    		
	    		/**
	    		 * 购买方式
	    		 * 0:按次购买        1:包月购买
	    		 */
	    		if(null!=leaseOrderModelVo.getBuyType()){
	    			if(0==leaseOrderModelVo.getBuyType()){
	    				leaseOrderModelVo.setBuyType_s("按次");
	    			}
	    			if(1==leaseOrderModelVo.getBuyType()){
	    				leaseOrderModelVo.setBuyType_s("包月");
	    			}
	    		}
	    		
	    		/**
	    		 * 支付方式
	    		 * 0:无              1:余额支付        2:财付通          3:银联            4:支付宝   5:微信   6:(APP)微信 
	    		 */
	    		if(null!=leaseOrderModelVo.getPayModel()){
	    			if(0==leaseOrderModelVo.getPayModel()){
	    				leaseOrderModelVo.setPayModel_s("无");
	    			}
	    			if(1==leaseOrderModelVo.getPayModel()){
	    				leaseOrderModelVo.setPayModel_s("余额支付");
	    			}
	    			if(2==leaseOrderModelVo.getPayModel()){
	    				leaseOrderModelVo.setPayModel_s("财付通");
	    			}
	    			if(3==leaseOrderModelVo.getPayModel()){
	    				leaseOrderModelVo.setPayModel_s("银联");
	    			}
	    			if(4==leaseOrderModelVo.getPayModel()){
	    				leaseOrderModelVo.setPayModel_s("支付宝");
	    			}
	    			if(5==leaseOrderModelVo.getPayModel()){
	    				leaseOrderModelVo.setPayModel_s("微信");
	    			}
	    			if(6==leaseOrderModelVo.getPayModel()){
	    				leaseOrderModelVo.setPayModel_s("(APP)微信");
	    			}
	    		}
	    		/**
	    		 * 支付状态
	    		 * 0:待支付          1:交易成功        2:已失效      3:已取消        4:已删除
	    		 */
//	    		if(null!=leaseOrderModelVo.getPayStatus()){
//	    			if(0==leaseOrderModelVo.getPayStatus()){
//	    				leaseOrderModelVo.setPayStatus_s("待支付");
//	    			}
//	    			if(1==leaseOrderModelVo.getPayStatus()){
//	    				leaseOrderModelVo.setPayStatus_s("交易成功");
//	    			}
//	    			if(2==leaseOrderModelVo.getPayStatus()){
//	    				leaseOrderModelVo.setPayStatus_s("已失效");
//	    			}
//	    			if(3==leaseOrderModelVo.getPayStatus()){
//	    				leaseOrderModelVo.setPayStatus_s("已取消");
//	    			}
//	    			if(4==leaseOrderModelVo.getPayStatus()){
//	    				leaseOrderModelVo.setPayStatus_s("已删除");
//	    			}
//	    		}
	    		if(null!=leaseOrderModelVo.getIspay()){
	    			if(0==leaseOrderModelVo.getIspay()){
	    				leaseOrderModelVo.setPayStatus_s("未支付");
	    			}
	    			if(1==leaseOrderModelVo.getIspay()){
	    				leaseOrderModelVo.setPayStatus_s("已支付");
	    			}
	    		}
	    		
	    		
	    		if(!StringUtils.isEmpty(leaseOrderModelVo.getProvinceCode()) && null!=proCityMaps){
					/**
					 * 省份
					 */
					String proCode = leaseOrderModelVo.getProvinceCode();
					leaseOrderModelVo.setProvinceCode(proCityMaps.get("-1"+"/"+proCode));
					
					
					/**
					 * 城市
					 */
					if(!StringUtils.isEmpty(leaseOrderModelVo.getCityCode())){
						leaseOrderModelVo.setCityCode(proCityMaps.get(proCode+"/"+leaseOrderModelVo.getCityCode()));
					}
					
					leaseOrderModelVo.setProvinceCode(leaseOrderModelVo.getProvinceCode()+"/"+leaseOrderModelVo.getCityCode());
				}
	    	}
	    }
		return lists;
	}
	
	/**
	 * 所有乘客查询
	 */
	@Action(value="getAllCustomList",results={@Result(name="success",location="../../view/order/allCustomList.jsp")})
	public String getAllCustomList()throws Exception{
		
		
		return SUCCESS;
	}
	
	/**
	 *  充值明细
	 */
	@Action(value="getRechargeDetails",results={@Result(name="success",location="../../view/order/rechargeDetails.jsp")})
	public String getRechargeDetails()throws Exception{
		currentPageIndex = request.getParameter("currentPageIndex")==null?0:Integer.parseInt(request.getParameter("currentPageIndex"));
		String searchDay = "";
//		if(null!=search && !StringUtils.isEmpty(search.getField02())){
//			/**
//			 * 将查询的时间往后推一天  比如：查询时间截止2014-09-23
//			 * 实际查询时间为：2014-09-24
//			 * 显示的时间段包括23号
//			 */
//			searchDay = search.getField02();
//			String date = "";
//			SimpleDateFormat TimeFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Date selectDate = TimeFormat.parse(searchDay);
//			Calendar calendar = new GregorianCalendar();
//		    calendar.setTime(selectDate);
//		    calendar.add(calendar.DATE,1);//把日期往前退一天.整数往后推,负数往前移动
//		    selectDate=calendar.getTime();
//		    /**
//		     * 明天的时间
//		     */
//		    date = TimeFormat.format(selectDate);
//		    search.setField02(date);
//		}
		map = iLeaseOrderService.rechargeVoList(search, currentPageIndex, pageSize);
		if(null!=search && !StringUtils.isEmpty(search.getField02())){
			/**
			 * 查询完以后把时间变回来
			 */
		    search.setField02(searchDay);
		}
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		return SUCCESS;
	}
	
	/**
	 * 导出 充值明细Excel
	 */
	@Action(value="exportRechargeDetails")
	public void exportRechargeDetails(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = iLeaseOrderService.rechargeVoList(search, currentPageIndex, Integer.MAX_VALUE);
		String title = "充值明细列表"; //导出列表头名称
	    String[] hearders = new String[] {
 	    		"时间", "流水号", "乘客","金额(元)","充值方式"};//表头数组
	    String[] fields = new String[] {
 	    		"retime", "rerunNo", "nickName", "moneyLimit", "reModel_s"};//对象属性数组
	    List<RechargeVo> lists = (List) map.get("list");
	    if(null!=lists && lists.size()>0){
	    	for(RechargeVo rechargeVo :lists){
	    		/**
	    		 * 乘客
	    		 */
	    		String displayId = "";
	    		if(null!=rechargeVo.getDisplayId()){
	    			displayId = rechargeVo.getDisplayId();
	    		}
	    		String nickName = "";
	    		if(null!=rechargeVo.getNickName()){
	    			nickName = rechargeVo.getNickName();
	    		}
	    		rechargeVo.setNickName(displayId+"/"+nickName);
	    		
	    		/**
	    		 * 支付方式
	    		 * 0:无              1:余额支付        2:财付通          3:银联            4:支付宝
	    		 */
	    		if(null!=rechargeVo.getReModel()){
	    			if(0==rechargeVo.getReModel()){
	    				rechargeVo.setReModel_s("无");
	    			}
	    			if(1==rechargeVo.getReModel()){
	    				rechargeVo.setReModel_s("余额支付");
	    			}
	    			if(2==rechargeVo.getReModel()){
	    				rechargeVo.setReModel_s("财付通");
	    			}
	    			if(3==rechargeVo.getReModel()){
	    				rechargeVo.setReModel_s("银联");
	    			}
	    			if(4==rechargeVo.getReModel()){
	    				rechargeVo.setReModel_s("支付宝");
	    			}
	    			if(5==rechargeVo.getReModel()){
	    				rechargeVo.setReModel_s("微信");
	    			}
	    		}
	    	}
	    }
	    TableData td = ExcelUtils.createTableData(lists, ExcelUtils.createTableHeader(hearders),fields);
	    JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td,agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有的线路名称
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getAllLineNames", results = { @Result(type = "json") })
	public void getAllLineNames() throws Exception {
		List<LineEntity> lineEntities = lineService.getAllLineList();
		JsonWriter.write(lineEntities);
	}
	
	/**
	 * 获取支持包车的商家名称
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getSupportBcMgrBusinBref", results = { @Result(type = "json") })
	public void getSupportBcMgrBusinBref() throws Exception {
		List<String> businessNames = iMgrbusinessService.getAllBusinessBrefNames(2);
		JsonWriter.write(businessNames);
	}
	
	/**
	 * 获取支持上线班的商家名称
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getSupportUpDownMgrBusinBref", results = { @Result(type = "json") })
	public void getSupportUpDownMgrBusinBref() throws Exception {
		List<String> businessNames = iMgrbusinessService.getAllBusinessBrefNames(1);
		JsonWriter.write(businessNames);
	}
	
	/**
	 * 获取班次
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getAllClass", results = { @Result(type = "json") })
	public void getAllClass() throws Exception {
		List<String> orderStartTimes = lineService.getAllLineOrderStartTime();
		JsonWriter.write(orderStartTimes);
	}
	
	/**
	 * 计算总计
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "countTotalValue", results = { @Result(type = "json") })
	public void countTotalValue() throws Exception {
		search = (Search) session.get("order_search");
		String total = iLeaseOrderService.countTotalValue(search);
		session.put("count_leaseOrder", total);
		JsonWriter.write(total);
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}
	public LeaseOrderModelVo getLeaseOrderModelVo() {
		return leaseOrderModelVo;
	}
	public void setLeaseOrderModelVo(LeaseOrderModelVo leaseOrderModelVo) {
		this.leaseOrderModelVo = leaseOrderModelVo;
	}
	public List<LeaseOrderModelVo> getLeaseOrderModelVos() {
		return leaseOrderModelVos;
	}
	public void setLeaseOrderModelVos(List<LeaseOrderModelVo> leaseOrderModelVos) {
		this.leaseOrderModelVos = leaseOrderModelVos;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}

	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}

	public List<SysArea> getCitySysAreas() {
		return citySysAreas;
	}
	public void setCitySysAreas(List<SysArea> citySysAreas) {
		this.citySysAreas = citySysAreas;
	}
	
}
