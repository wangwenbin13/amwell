package com.amwell.action.pc;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.IPassengerLineService;
import com.amwell.service.ISysAreaService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.SysArea;
import com.amwell.vo.pc.PassengerLineVo;

/**
 * 拼车乘客发线路
 * @author huxiaojun
 */
@ParentPackage("user-finit")
@Namespace("/passengerLine")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class PassengerLineAction extends BaseAction{

	private static final long serialVersionUID = -6147659040892456198L;
	
	private static final Logger log = Logger.getLogger(PassengerLineAction.class);
	
	@Autowired
	private IPassengerLineService passengerLineService;
	
	@Autowired
	private ISysAreaService iSysAreaService;
	
    private int currentPageIndex=0;
	
	private int pageSize = 10;
	
	private PassengerLineVo passengerLineVo;
	
	/**
	 * 区域省份集合
	 */
	private List<SysArea> proSysAreas;
	
	/**
	 * 获取乘客发布线路
	 */
	@Action(value = "getLineList", results = { @Result(name = "success", location = "../../view/pcLine/passengerLine.jsp") })
	public String getLineList(){
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
			currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
					: Integer
							.parseInt(request.getParameter("currentPageIndex"));
		}
		/*将条件存到session,便于刷新后任然存在页面而不会丢失*/
		search = (Search) (search == null?session.get("search"):search);
		if(null==search){
			search=new Search();
		}
		map =  passengerLineService.getLineList(search,currentPageIndex,this.pageSize);
		this.session.put("search", search);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		//加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}
	
	/**
	 * 获取乘客线路详情
	 * @return
	 */
	@Action(value = "getLineDetail", results = { @Result(name = "success", location = "../../view/pcLine/passengerLineDetail.jsp") })
	public String getLineDetail(){
		String pcLineId = super.request.getParameter("pcLineId");
		if(StringUtils.hasText(pcLineId)){
			passengerLineVo = passengerLineService.getLineDetail(pcLineId);
		}
		super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	@Action(value = "exportPassengerLine")
	public void exportPassengerLine(){
		getResponse().setContentType("application/msexcel;charset=GBK");
		Map<String,Object> pMap =  passengerLineService.getLineList(search,0,Integer.MAX_VALUE);
		List<PassengerLineVo> pList =null;
		if(false==CollectionUtils.isEmpty(pMap)){
			pList = (List<PassengerLineVo>) pMap.get("list");
		}
		if(false==CollectionUtils.isEmpty(pList)){
			String title = "乘客发布线路列表";
		    String[] hearders = new String[] {"提交时间","用户ID","昵称","线路类型","起点","终点", "单程往返","去程时间","返程时间","重复时间","联系电话","座位","线路状态","线路开关"};
		    String[] fields = new String[] {"createOn","displayId","nickName","lineType","startStationName", "endStationName","lineSubType","goTime","returnTime","repeatTime","telephone","carSeats","lineStatusStr","lineOnOffStr"};
		    TableData td = ExcelUtils.createTableData(pList, ExcelUtils.createTableHeader(hearders),fields);
		    JsGridReportBase report;
			try {
				report = new JsGridReportBase(request, getResponse());
				String agent = request.getHeader("User-Agent");
				report.exportToExcel(title, td,agent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Action(value = "open",results = { @Result( type = "json") })
	public String open() throws IOException{
		String pcLineId =super.request.getParameter("pcLineId");
		int flag = passengerLineService.updateLineOnOff(pcLineId,1);
		String json =flag>0?"success":"error";
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	@Action(value = "close",results = { @Result( type = "json") })
	public String close() throws IOException{
		String pcLineId =super.request.getParameter("pcLineId");
		int flag = passengerLineService.updateLineOnOff(pcLineId,0);
		String json =flag>0?"success":"error";
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}
	
	
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PassengerLineVo getPassengerLineVo() {
		return passengerLineVo;
	}

	public void setPassengerLineVo(PassengerLineVo passengerLineVo) {
		this.passengerLineVo = passengerLineVo;
	}

	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}

	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}
	
}
