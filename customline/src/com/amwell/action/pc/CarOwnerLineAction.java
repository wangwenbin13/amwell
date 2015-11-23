package com.amwell.action.pc;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

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
import com.amwell.commons.JSONUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ICarOwnerLineService;
import com.amwell.service.IPassengerLineService;
import com.amwell.service.ISysAreaService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.SysArea;
import com.amwell.vo.pc.CarOwnerLineVo;

/**
 * 拼车车主线路
 * @author huxiaojun
 */
@ParentPackage("user-finit")
@Namespace("/carOwnerLine")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class CarOwnerLineAction extends BaseAction {

	private static final long serialVersionUID = -333851555213874044L;

	private static final Logger log = Logger.getLogger(CarOwnerLineAction.class);
	
	@Autowired
	private ICarOwnerLineService carOwnerLineService;
	
	@Autowired
	private IPassengerLineService passengerLineService;
	@Autowired
	private ISysAreaService sysAreaService;

    private int currentPageIndex=0;
	
	private int pageSize = 10;
	
	private CarOwnerLineVo carOwnerLineVo;
	
	private Integer listSize;
	
	private List<SysArea> proSysAreas;
	/**
	 * 获取车主发布线路列表
	 */
	@Action(value = "getLineList", results = { @Result(name = "success", location = "../../view/pcLine/carOwnerLine.jsp") })
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
		map =  carOwnerLineService.getLineList(search,currentPageIndex,this.pageSize);
		this.session.put("search",search);
		list = (List) map.get("list");//数据对象
		page = (Page) map.get("page");//分页对象
		listSize = list==null?0:list.size();
		//加载省份城市
		SysArea area=new SysArea();
		area.setFdCode("-1");
		proSysAreas=sysAreaService.querySysArea(area);
		return SUCCESS;
	}
	
	/**
	 * 获取车主发布线路详情
	 * @return
	 */
	@Action(value = "getLineDetail", results = { @Result(name = "success", location = "../../view/pcLine/carOwnerLineDetail.jsp") })
	public String getLineDetail(){
		String pcLineId = super.request.getParameter("pcLineId");
		if(StringUtils.hasText(pcLineId)){
			carOwnerLineVo = carOwnerLineService.getLineDetail(pcLineId);
		}
		super.getSession().put("toDetailCurrentPageIndex",this.currentPageIndex);
		return SUCCESS;
	}
	
	/**
	 * 导出数据
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@Action(value="exportCarOwnerLine")
	public void exportCarOwnerLine() throws IOException, ParseException{
		getResponse().setContentType("application/msexcel;charset=GBK");
		map=carOwnerLineService.getLineList(search, 0, Integer.MAX_VALUE);
		List<CarOwnerLineVo> ownerVos=(List) map.get("list");
		if(CollectionUtils.isEmpty(ownerVos)){
			log.debug("无车主发布线路数据");
			return ;
		}
		if(false==CollectionUtils.isEmpty(ownerVos)){
			String title="车主发布线路列表";
		    String[] hearders = new String[] {"提交时间","用户ID","昵称","线路类型","起点","终点", "单程往返","价格","去程时间","返程时间","重复时间","车型","车牌","联系电话","座位","线路状态","线路开关"};
		    String[] fields = new String[] {"createOn","displayId","nickName","lineTypeStr","startStationName", "endStationName","lineSubTypeStr","realPrice","goTime","returnTime","repeatTime","carModel","carNumber","telephone","carSeats","lineStatusStr","lineOnOffStr"};
		    TableData td = ExcelUtils.createTableData(ownerVos, ExcelUtils.createTableHeader(hearders),fields);
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

	public CarOwnerLineVo getCarOwnerLineVo() {
		return carOwnerLineVo;
	}

	public void setCarOwnerLineVo(CarOwnerLineVo carOwnerLineVo) {
		this.carOwnerLineVo = carOwnerLineVo;
	}

	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}

	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}

	public Integer getListSize() {
		return listSize;
	}

	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}
	
	
}
