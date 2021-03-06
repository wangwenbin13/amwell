package com.amwell.action.line;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.amwell.action.BaseAction;
import com.amwell.commons.CalendarHelper;
import com.amwell.commons.JSONUtil;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.Message;
import com.amwell.commons.MyDate;
import com.amwell.commons.PostHttpClient;
import com.amwell.commons.PropertyManage;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.service.ILineService;
import com.amwell.service.IMgrbusinessService;
import com.amwell.service.IPassengerInfoService;
import com.amwell.service.IPublishLineService;
import com.amwell.service.ISysAreaService;
import com.amwell.service.ISysLogService;
import com.amwell.utils.export.ExcelUtils;
import com.amwell.utils.export.JsGridReportBase;
import com.amwell.utils.export.TableData;
import com.amwell.vo.ClassMonthOrderPriceVo;
import com.amwell.vo.LineAllowanceDetailVo;
import com.amwell.vo.LineAllowanceVo;
import com.amwell.vo.LineBusinessVo;
import com.amwell.vo.LineCarMsgVo;
import com.amwell.vo.LineCityVo;
import com.amwell.vo.LineClassCarDriverVo;
import com.amwell.vo.LineClassEntity;
import com.amwell.vo.LineClassMonthOrderCountVo;
import com.amwell.vo.LineClassOrderPassengerVo;
import com.amwell.vo.LineClassVo;
import com.amwell.vo.LineDetailVo;
import com.amwell.vo.LineEntity;
import com.amwell.vo.LineOrderPassengerVo;
import com.amwell.vo.LinePassengerMonthEntity;
import com.amwell.vo.LineScheduleInfoVo;
import com.amwell.vo.LineTimeChange;
import com.amwell.vo.MerchantVo;
import com.amwell.vo.OrderStartTimeVo;
import com.amwell.vo.PassengerStationVo;
import com.amwell.vo.RecruitLineStationVo;
import com.amwell.vo.StationApplyVo;
import com.amwell.vo.StationInfo;
import com.amwell.vo.SysAdminVo;
import com.amwell.vo.SysArea;
import com.amwell.vo.UserLineCallbackVo;
import com.amwell.vo.UserLineEntity;
import com.amwell.vo.UserLineStationCount;
import com.amwell.vo.app.bean.AppVo_2;
import com.amwell.vo.app.bean.AppVo_6;

/**
 * 运营平台线路相关Action
 * 
 * @author huxiaojun
 */
@ParentPackage("user-finit")
@Namespace("/line")
@Scope("prototype")
@SuppressWarnings("all")
public class LineAction extends BaseAction {

	private static final Logger log = Logger.getLogger(LineAction.class);

	private static final long serialVersionUID = -551285202592371884L;

	private static final String KEY_SESSION_USER = "userInfo";

	@Autowired
	private ILineService lineService;

	@Autowired
	private IMgrbusinessService businessService;

	@Autowired
	private ISysLogService logService;

	@Autowired
	private IPassengerInfoService passengerInfoService;

	/**
	 * 区域
	 */
	@Autowired
	private ISysAreaService iSysAreaService;

	private UserLineEntity userLineEntity;

	private StationInfo stationInfo;

	private RecruitLineStationVo recruitLineStationVo;

	private List<String> picUrlList;

	private LineScheduleInfoVo lineScheduleInfo;

	// 线路详情
	private LineDetailVo lineDetailVo;

	private int currentPageIndex = 0;

	private int pageSize = 20;

	private String startYearAndMonth;// 班次对应的开始年月

	private String endYearAndMonth;// 班次对应的结束年月

	@Autowired
	private IPublishLineService publishLineService;
	// 查询车辆位置的api；
	// private final String BUS_POSITION_API =
	// "http://192.168.9.6:10054/customline_app/app_GPS/getBusPosition.action";

	/**
	 * 区域省份集合
	 */
	private List<SysArea> proSysAreas;

	private LineCityVo lineCityVo;

	private LineAllowanceDetailVo lineAllowanceDetailVo;

	private LineAllowanceVo allowanceVo;

	private LineEntity lineEntity;

	/**
	 * 根据关键词获取带有经纬度的站点列表
	 * 
	 * @return
	 */
	@Action(value = "loadStationListByKeyword", results = { @Result(type = "json") })
	public void loadStationListByKeyword() {
		Map<String, Object> resultData = new HashMap<String, Object>();
		try {
			String keyword = request.getParameter("keyword");
			List<StationInfo> stationList = lineService.loadStationByKeywords(keyword);
			resultData.put("a1", "100");
			resultData.put("a2", stationList);
		} catch (Exception e) {
			e.printStackTrace();
			resultData.put("a1", "500");
		}
		JsonWriter.write(resultData);
	}

	/**
	 * 修改发车时间
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "updateLineTime", results = { @Result(type = "json") })
	public String updateLineTime() throws IOException {
		try {
			String cid = request.getParameter("cid");// 线路时间
			String ctime = request.getParameter("ctime");// 修改时间
			String workTime = request.getParameter("workTime");// 生效时间
			String changeMSG = request.getParameter("changeMSG");// 发送短信（0：是
																	// 1：否）
			LineTimeChange lineTimeChange = new LineTimeChange();
			lineTimeChange.setLinebaseid(cid);
			lineTimeChange.setLinetime(ctime);
			lineTimeChange.setWorktime(workTime);
			lineTimeChange.setIsmsgsend(changeMSG);
			lineTimeChange.setCreateOn(MyDate.getMyDateLong());
			SysAdminVo user = (SysAdminVo) session.get("userInfo");
			lineTimeChange.setCreateBy(user.getUserId());
			String result = lineService.updateLineTime(lineTimeChange);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");

			if (changeMSG.equals("0")) {
				String telephones = lineService.getUserTelByLineBaseId(cid);
				if (!telephones.equals("")) {
					String content_ = request.getParameter("content");// 消息正文
					String content = StringUtil.unescape(content_);// 解码后
					new Message(content).sendTo(telephones);
				}
			}

			if (!result.equals("-1")) {
				response.getWriter().print("success");
			} else {
				response.getWriter().print("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取修改发车线路信息
	 * 
	 * @return
	 * @throws IOException
	 */

	@Action(value = "getLineChangeInfo", results = { @Result(type = "json") })
	public String getLineChangeInfo() throws IOException {
		String cid = request.getParameter("cid");
		AppVo_6 vo = lineService.getLineChangeInfo(cid);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(JSONUtil.formObjectToJsonObject(vo));
		return null;
	}

	@Action(value = "syncAllLineTrackMap", results = { @Result(type = "json") })
	public void syncAllLineTrackMap() {
		lineService.syncAllLineTrackMap();
		getResponse().setContentType("text/html;charset=UTF-8");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "success");
		JsonWriter.write(result);
	}

	@Action(value = "forwardAddLinePage", results = {
			@Result(name = "success", location = "../../view/line/addLines.jsp") })
	public String forwardAddLinePage() {
		// 加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		return SUCCESS;
	}

	/**
	 * 用户招募转平台招募
	 * 
	 * @return
	 */
	@Action(value = "forwardAddLinePageFromUserApplication", results = {
			@Result(name = "success", location = "../../view/line/addLinesFromUserApplication.jsp") })
	public String forwardAddLinePageFromUserApplication() {
		// 加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		HttpServletRequest request = super.request;
		String applicationId = request.getParameter("applicationId");
		if (StringUtils.hasText(applicationId)) {
			userLineEntity = lineService.getUserLineDetail(applicationId);
		}
		request.getSession().setAttribute("userLineEntity", userLineEntity);
		request.getSession().setAttribute("classTime", userLineEntity.getStartTime());
		request.getSession().setAttribute("applicationId", applicationId);
		return SUCCESS;
	}

	/**
	 * 创建上下班线路-填写班次
	 */
	@Action(value = "addClass", results = { @Result(name = "success", location = "../../view/line/addClass.jsp") })
	public String addClass() {
		Calendar ca = Calendar.getInstance();
		startYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		ca.add(Calendar.MONTH, 2);
		endYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		return SUCCESS;
	}

	/**
	 * 创建自由行线路-填写班次
	 */
	@Action(value = "addClassFreeLine", results = {
			@Result(name = "success", location = "../../view/line/addClassFreeLine.jsp") })
	public String addClassFreeLine() {
		return SUCCESS;
	}

	/**
	 * 创建招募线路-填写班次
	 */
	@Action(value = "addClassRecruitLine", results = {
			@Result(name = "success", location = "../../view/line/addClassRecruitLine.jsp") })
	public String addClassRecruitLine() {
		return SUCCESS;
	}

	@Action(value = "saveLineData", results = { @Result(type = "json") })
	public String saveLineData() throws IOException {
		String json = "error";
		String lineName = super.request.getParameter("lineName");
		String startArea = super.request.getParameter("startArea");
		String endArea = super.request.getParameter("endArea");
		String provinceCode = super.request.getParameter("provinceCode");
		String cityCode = super.request.getParameter("cityCode");
		String linePicUrl = super.request.getParameter("linePicUrl");
		String[] stationNames = super.request.getParameterValues("stationName");
		String[] latis = super.request.getParameterValues("lati");
		String[] lonis = super.request.getParameterValues("loni");
		String[] picUrls = super.request.getParameterValues("picUrl");
		String lineTime = super.request.getParameter("lineTime");
		String lineTimeType = super.request.getParameter("lineTimeType");
		String lineKm = super.request.getParameter("lineKm");
		// 页面传值 0：上下班，1:自由行,2:招募
		String lineType = super.request.getParameter("lineType");

		String passengerTel = super.request.getParameter("passengerTel");
		String passengerCoupon = super.request.getParameter("passengerCoupon");

		String result = validate(lineName, stationNames, lineTime, lineTimeType, lineKm, lineType);
		if ("success".equals(result)) {
			if (ArrayUtils.isNotEmpty(stationNames) && ArrayUtils.isNotEmpty(latis) && ArrayUtils.isNotEmpty(lonis)
					&& stationNames.length == latis.length && stationNames.length == lonis.length
					&& stationNames.length == picUrls.length) {
				boolean sameRes = this.lineService.checkSameLineName(null, lineName, lineType);
				if (sameRes) {
					// 线路名称重复
					json = "repetition";
				} else {
					json = "success";
					if (lineTimeType.equals("1")) {
						lineTime = Integer.parseInt(lineTime) * 60 + "";
					}
					super.session.put("lineName", lineName);
					super.session.put("startArea", startArea);
					super.session.put("endArea", endArea);
					super.session.put("provinceCode", provinceCode);
					super.session.put("cityCode", cityCode);
					super.session.put("linePicUrl", linePicUrl);
					super.session.put("stationNames", stationNames);
					super.session.put("latis", latis);
					super.session.put("lonis", lonis);
					super.session.put("picUrls", picUrls);
					super.session.put("lineTime", lineTime);
					super.session.put("lineType", lineType);
					super.session.put("lineKm", lineKm);

					super.session.put("passengerTel", passengerTel);
					super.session.put("passengerCoupon", passengerCoupon);
				}
			}
			// 如果是从平台招募转换过来的线路，会在session中存在lineDetailVo对象
			lineDetailVo = (LineDetailVo) request.getSession().getAttribute("lineDetailVo");
			if (lineDetailVo != null) {
				recruitLineStationVo = this.lineService.getRecruitLineStation(lineDetailVo.getLineBaseId());
				if (null != recruitLineStationVo) {
					// 将固定时间格式转换成列表
					List<String> fixTimeList = this.converFixTime(recruitLineStationVo.getRecruitFixTime());
					recruitLineStationVo.setClassTime(recruitLineStationVo.getRecruitStartTime());
					/*
					 * String s = recruitLineStationVo.getRecruitStartTime();
					 * if(-1!=s.indexOf(" ")){
					 * recruitLineStationVo.setRecruitStartTime
					 * (s.substring(0,s.indexOf(" ")));
					 * recruitLineStationVo.setClassTime (s.substring(s.indexOf(
					 * " ")+1)); }
					 */
					if (false == CollectionUtils.isEmpty(fixTimeList)) {
						recruitLineStationVo.setFixTimeList(fixTimeList);
					}
					request.getSession().setAttribute("recruitLineStationVo", recruitLineStationVo);
					request.getSession().setAttribute("classTime", recruitLineStationVo.getClassTime());
				}
			}
		} else {
			json = result;
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	private String validate(String lineName, String[] stationNames, String lineTime, String lineTimeType, String lineKm,
			String lineType) {
		if (false == StringUtils.hasText(lineName)) {
			return "lineNameIsNull";
		} else {
			if (lineName.length() > 24) {
				return "lineNameTooLong";
			}
		}

		if (ArrayUtils.isEmpty(stationNames)) {
			return "stationNameIsNull";
		} else {
			boolean isEmpty = false;
			boolean isTooLong = false;
			for (String s : stationNames) {
				if (false == StringUtils.hasText(s)) {
					isEmpty = true;
					break;
				} else {
					if (s.length() > 24) {
						isTooLong = true;
						break;
					}
				}

			}
			if (isEmpty) {
				return "stationNameIsNull";
			}
			if (isTooLong) {
				return "stationNameTooLong";
			}
		}

		if (false == StringUtils.hasText(lineTime)) {
			return "lineTimeISNull";
		} else {
			if (lineTime.length() > 6) {
				return "lineTimeTooLong";
			} else if (false == StringUtil.isNumeric(lineTime)) {
				return "lineTimeIsNotNumeric";
			}
		}

		if (false == StringUtils.hasText(lineTimeType)) {
			return "lineTimeTypeIsNull";
		} else {
			if (false == ArrayUtils.contains(new String[] { "0", "1" }, lineTimeType)) {
				return "lineTimeTypeError";
			}
		}

		if (false == StringUtils.hasText(lineKm)) {
			return "lineKmIsNull";
		} else {
			if (lineKm.length() > 6) {
				return "lineKmTooLong";
			} else if (false == StringUtil.isNumeric(lineKm)) {
				return "lineKMIsNotNumeric";
			}
		}

		if (false == StringUtils.hasText(lineType)) {
			return "lineTypeIsNull";
		} else {
			if (false == ArrayUtils.contains(new String[] { "0", "1", "2" }, lineType)) {
				return "lineTypeError";
			}
		}

		return "success";
	}

	@Action(value = "showImage", results = {
			@Result(name = "success", location = "../../view/line/pop-addPicView.jsp") })
	public String showImage() {
		String filePath = super.request.getParameter("filePath");
		if (StringUtils.hasText(filePath)) {
			if (false == filePath.startsWith("http://")) {
				// 前端传值为相对路径，需将其拼接成绝对路径
				String httpIp = PropertyManage.getInfoProperty("http.pic.ip");// IP地址
				if (StringUtils.hasText(httpIp)) {
					filePath = httpIp + filePath;
				} else {
					log.error("尚未配置http.pic.ip地址");
				}
			}
		}
		log.debug("filePath:" + filePath);
		super.request.setAttribute("filePath", filePath);
		return SUCCESS;
	}

	@Action(value = "showDetailImage", results = {
			@Result(name = "success", location = "../../view/line/pop-detailPicView.jsp") })
	public String showDetailImage() {
		String filePath = super.request.getParameter("filePath");
		if (StringUtils.hasText(filePath)) {
			if (false == filePath.startsWith("http://")) {
				// 前端传值为相对路径，需将其拼接成绝对路径
				String httpIp = PropertyManage.getInfoProperty("http.pic.ip");// IP地址
				if (StringUtils.hasText(httpIp)) {
					filePath = httpIp + filePath;
				} else {
					log.error("尚未配置http.pic.ip地址");
				}
			}
		}
		log.debug("filePath:" + filePath);
		super.request.setAttribute("filePath", filePath);
		return SUCCESS;
	}

	/**
	 * 创建上下班线路-选择vip客户弹窗
	 */
	@Action(value = "selectVIP", results = {
			@Result(name = "success", location = "../../view/line/pop-selectVIP.jsp") })
	public String selectVIP() {

		return SUCCESS;
	}

	private String[] delEndSemicolon(String[] orderDates) {
		if (ArrayUtils.isNotEmpty(orderDates)) {
			String[] res = new String[orderDates.length];
			for (int i = 0; i < orderDates.length; i++) {
				res[i] = orderDates[i].endsWith(";") ? orderDates[i].substring(0, orderDates[i].length() - 1)
						: orderDates[i];
			}
			return res;
		}
		return orderDates;
	}

	private String validate(String lineType, String lineSubType, String[] supportMonth, String[] orderStartTimes,
			String[] orderSeats, String[] orderDates, String orderPrice, String discountRate, String remark,
			String recruitStartTime, String deadlineTime, String[] recruitFixTimes) {
		if (false == StringUtils.hasText(orderPrice)) {
			return "orderPriceIsNull";
		} else {
			String regex = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(orderPrice);
			if (!matcher.matches() || orderPrice.length() > 6) {
				return "orderPriceError";
			}
		}
		// 上下班，自由行
		if ("0".equals(lineType) || "1".equals(lineType)) {
			if (ArrayUtils.isEmpty(orderStartTimes)) {
				return "orderStartTimeIsNull";
			} else {
				boolean isEmpty = false;
				boolean isError = false;
				for (String s : orderStartTimes) {
					if (false == StringUtils.hasText(s)) {
						isEmpty = true;
						break;
					} else {
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
						try {
							sdf.parse(s);
						} catch (ParseException e) {
							isError = true;
							break;
						}
					}
				}
				if (isEmpty) {
					return "orderStartTimeIsNull";
				}
				if (isError) {
					return "orderStartTimeError";
				}
			}

			if (ArrayUtils.isEmpty(orderSeats)) {
				return "orderSeatIsNull";
			} else {
				boolean isEmpty = false;
				boolean isError = false;
				for (String s : orderSeats) {
					if (false == StringUtils.hasText(s)) {
						isEmpty = true;
						break;
					} else {
						if (s.length() > 3 || false == StringUtil.isNumeric(s)) {
							isError = true;
							break;
						}
					}
				}
				if (isEmpty) {
					return "orderSeatIsNull";
				}
				if (isError) {
					return "orderSeatError";
				}
			}

			if (ArrayUtils.isEmpty(orderDates)) {
				return "orderDateIsNull";
			}

			if ("0".equals(lineType)) {
				boolean hasSupportMonth = false;
				if (ArrayUtils.isNotEmpty(supportMonth)) {
					for (String s : supportMonth) {
						if (StringUtils.hasText(s)) {
							hasSupportMonth = true;
							break;
						}
					}
				}
				if (hasSupportMonth) {
					if (false == StringUtils.hasText(discountRate)) {
						return "discountRateIsNull";
					} else {
						if (false == StringUtil.isNumeric(discountRate) || Integer.parseInt(discountRate) < 1
								|| Integer.parseInt(discountRate) > 100) {
							return "discountRateError";
						}
					}
				}
			}

		} else {
			// 招募
			if (false == StringUtils.hasText(recruitStartTime)) {
				return "recruitStartTimeIsNull";
			}

			if (false == StringUtils.hasText(deadlineTime)) {
				return "deadlineTimeIsNull";
			}

			if (ArrayUtils.isEmpty(recruitFixTimes)) {
				return "recruitFixTimeIsNull";
			} else {
				boolean isError = false;
				String[] values = { "1", "2", "3", "4", "5", "6", "7" };
				for (String s : recruitFixTimes) {
					if (false == ArrayUtils.contains(values, s)) {
						isError = true;
						break;
					}
				}
				if (isError) {
					return "recruitFixTimeError";
				}
			}
		}
		return "success";
	}

	/**
	 * 获取班次包月价格信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getClassMonthOrderPrice", results = { @Result(type = "json") })
	public String getClassMonthOrderPrice() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime)) {
			List<ClassMonthOrderPriceVo> cmoList = this.lineService.getClassMonthOrderPrice(lineBaseId, orderStartTime);
			if (false == CollectionUtils.isEmpty(cmoList)) {
				json = JSONUtil.formObjectsToJSONStr(cmoList);
			}
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * //修改发车时间格式：08:00(包月)
	 * 
	 * @param orderStartTimes
	 * @param supportMonth
	 * @return
	 */
	private String[] changeOrderStartTimes(String[] orderStartTimes, String[] supportMonth) {
		if (ArrayUtils.isNotEmpty(orderStartTimes) && ArrayUtils.isNotEmpty(supportMonth)
				&& orderStartTimes.length == supportMonth.length) {
			List<String> res = new ArrayList<String>();
			int len = orderStartTimes.length;
			String s = null;
			for (int i = 0; i < len; i++) {
				s = orderStartTimes[i];
				if ("1".equals(supportMonth[i])) {
					s += "(包月)";
				}
				res.add(s);
			}
			return res.toArray(new String[] {});
		}
		return null;
	}

	/**
	 * 将数组转换为list
	 * 
	 * @param stationNames
	 * @param latis
	 * @param lonis
	 * @param picUrls
	 * @return
	 */
	private List<StationInfo> buildLineStationList(String[] stationNames, String[] latis, String[] lonis,
			String[] picUrls) {
		if (ArrayUtils.isNotEmpty(stationNames) && ArrayUtils.isNotEmpty(latis) && ArrayUtils.isNotEmpty(lonis)
				&& ArrayUtils.isNotEmpty(picUrls)) {
			int len = stationNames.length;
			if (len == latis.length && len == lonis.length && len == picUrls.length) {
				String createBy = getSessionUserId();
				List<StationInfo> list = new ArrayList<StationInfo>(len);
				StationInfo s = null;
				for (int i = 0; i < len; i++) {
					StationInfo qLineStation = this.lineService.getStationByName(stationNames[i]);
					if (null != qLineStation) {
						if (StringUtils.hasText(latis[i])) {
							qLineStation.setBaiduLat(Double.valueOf(latis[i]));
						}
						if (StringUtils.hasText(lonis[i])) {
							qLineStation.setBaiduLng(Double.valueOf(lonis[i]));
						}
						list.add(qLineStation);
					} else {
						s = new StationInfo();
						s.setBaiduLat(Double.valueOf(latis[i]));
						s.setBaiduLng(Double.valueOf(lonis[i]));
						s.setName(stationNames[i]);
						list.add(s);
					}
				}
				return list;
			}
		}
		return new ArrayList<StationInfo>(0);
	}

	private String getSessionUserId() {
		SysAdminVo sysAdmin = (SysAdminVo) request.getSession().getAttribute(KEY_SESSION_USER);
		return null == sysAdmin ? "" : sysAdmin.getUserId();
	}

	/**
	 * 创建线路-添加站点图片
	 */
	@Action(value = "addLinePic", results = { @Result(name = "success", location = "../../view/line/pop-addPic.jsp") })
	public String addLinePic() {
		return SUCCESS;
	}

	/**
	 * 创建线路-添加站点地图
	 */
	@Action(value = "addLineMap", results = { @Result(name = "success", location = "../../view/line/pop-addMap.jsp") })
	public String addLineMap() {
		request.setAttribute("lon", request.getParameter("lon"));
		request.setAttribute("lat", request.getParameter("lat"));
		return SUCCESS;
	}

	/**
	 * 创建线路-添加日历（工作时间）
	 */
	@Action(value = "addLineDate", results = {
			@Result(name = "success", location = "../../view/line/pop-addDate.jsp") })
	public String addLineDate() {
		Calendar ca = Calendar.getInstance();
		this.startYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		ca.add(Calendar.MONTH, 2);
		this.endYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		return SUCCESS;
	}

	/**
	 * 创建线路-添加日历（工作时间）--编辑线路的时候添加
	 */
	@Action(value = "addLineDateForEdit", results = {
			@Result(name = "success", location = "../../view/line/pop-addDateForEdit.jsp") })
	public String addLineDateForEdit() {
		Calendar ca = Calendar.getInstance();
		this.startYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		ca.add(Calendar.MONTH, 2);
		this.endYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		return SUCCESS;
	}

	/**
	 * 线路详情-添加或修改站点图片
	 */
	@Action(value = "popAddLinePic", results = {
			@Result(name = "success", location = "../../view/line/pop-addLinePic.jsp") })
	public String popAddLinePic() {
		String stationInfoId = super.getRequest().getParameter("stationInfoId");
		if (StringUtils.hasText(stationInfoId)) {
			picUrlList = this.lineService.getStationPicUrlList(stationInfoId);
		}
		return SUCCESS;
	}

	/**
	 * 线路详情-添加站点地图
	 */
	@Action(value = "popAddLineMap", results = {
			@Result(name = "success", location = "../../view/line/pop-addLineMap.jsp") })
	public String popAddLineMap() {
		return SUCCESS;
	}

	/**
	 * 线路详情-添加日历（工作时间）
	 */
	@Action(value = "popAddLineDate", results = {
			@Result(name = "success", location = "../../view/line/pop-addLineDate.jsp") })
	public String popAddLineDate() {
		// 根据线路id和班次时间查询班次的年月信息
		Calendar ca = Calendar.getInstance();
		this.startYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		ca.add(Calendar.MONTH, 2);
		this.endYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		return SUCCESS;
	}

	/**
	 * 线路详情-添加日历（工作时间）【用于线路列表开票】
	 */
	@Action(value = "popAddLineDateForLine", results = {
			@Result(name = "success", location = "../../view/line/pop-addLineDateForLine.jsp") })
	public String popAddLineDateForLine() {
		// 根据线路id和班次时间查询班次的年月信息
		Calendar ca = Calendar.getInstance();
		this.startYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		ca.add(Calendar.MONTH, 2);
		this.endYearAndMonth = new SimpleDateFormat("yyyy-MM").format(ca.getTime());
		return SUCCESS;
	}

	/**
	 * 修改工作时间【用于线路列表开票】
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "updateLineDate", results = { @Result(type = "json") })
	public String updateLineDate() throws IOException {
		String json = "error";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderSeat = super.request.getParameter("orderSeat");
		String orderDate = super.request.getParameter("orderDate");
		String[] orderDates = orderDate.split(";");
		String supportMonth = super.request.getParameter("supportMonth");
		List<LineClassEntity> addClassList = new ArrayList<LineClassEntity>();// 需要新增的班次信息
		List<String> delClassList = new ArrayList<String>();// 需要删除的班次id信息
		List<LinePassengerMonthEntity> addMonthList = new ArrayList<LinePassengerMonthEntity>();// 需要新增的包月信息
		List<String> delMonthList = new ArrayList<String>();// 需要删除的包月id

		// 原始数据修改,发车时间不能修改
		// 处理班次信息
		this.getLineClassChangeData(lineBaseId, orderStartTime, orderSeat, orderDates, addClassList, delClassList);
		// 处理包月信息
		this.getClassMonthChangeData(lineBaseId, orderStartTime, supportMonth, addMonthList, delMonthList);

		LineEntity rs = this.lineService.getLineByLineBaseId(lineBaseId);
		rs.setUpdateBy(this.getSessionUserId());
		rs.setUpdateOn(MyDate.getMyDateLong());

		int flag = lineService.updateLine(rs, delClassList, addClassList, delMonthList, addMonthList, null);
		if (flag == 1) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路更新",
					"线路更新[" + lineBaseId + "]成功");
			json = "success";
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路更新",
					"线路更新[" + lineBaseId + "]失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取所有线路总页面
	 */
	@Action(value = "getLinesList", results = { @Result(name = "success", location = "../../view/line/linesList.jsp") })
	public String getLinesList() {
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		Object theTab = super.session.get("line_theTab");
		// 判断是否是从详情返回
		// String goback = (String) super.session.get("goback");
		if (null == theTab) {
			theTab = "1";
		}
		if (theTab instanceof String) {
			super.request.setAttribute("line_theTab", theTab);
			super.session.remove("line_theTab");
		}
		return SUCCESS;
	}

	/**
	 * 获取商家弹出页面
	 */
	@Action(value = "selectMerchant", results = {
			@Result(name = "success", location = "../../view/line/pop-selectMerchant.jsp") })
	public String selectMerchant() {
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			this.lineCityVo = this.lineService.getLineCity(lineBaseId);
		}
		// 加载省数据
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);

		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "getMerchantList", results = { @Result(type = "json") })
	public String getMerchantList() throws IOException {
		String json = "{}";
		int currPage = 1;
		String currPageStr = super.request.getParameter("currPage");
		if (StringUtils.hasText(currPageStr)) {
			try {
				currPage = Integer.parseInt(currPageStr);
			} catch (Exception e) {
			}
		}
		this.pageSize = 10;
		int curPageIndex = (currPage - 1) * this.pageSize;
		if (null == search) {
			search = new Search();
		}
		String brefName = super.request.getParameter("brefName");
		if (StringUtils.hasText(brefName)) {
			search.setField04(URLDecoder.decode(brefName, "utf-8"));
		}
		search.setField05("1");
		search.setField06("1");// 商户业务类型 1:上下班 2：包车 3.两者都支持
		String provinceCode = super.request.getParameter("provinceCode");
		if (StringUtils.hasText(provinceCode)) {
			search.setField07(provinceCode);
		}
		String areaCode = super.request.getParameter("areaCode");
		if (StringUtils.hasText(areaCode)) {
			search.setField08(areaCode);
		}

		map = businessService.listByPage(search, curPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		MerchantVo mv = new MerchantVo();
		mv.setCurrPage(currPage);
		mv.setPageSize(this.pageSize);
		mv.setTotalCount(null != page ? page.getTotalCount() : 0);
		mv.setResultData(list);
		json = JSONUtil.formObjectToJsonObject(mv);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取所有线路
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getAllLines", results = {
			@Result(name = "success", location = "../../view/line/linesListAll.jsp") })
	public String getAllLines() {

		/**
		 * 省份
		 */
		session.put("line_theTab", "2");
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		String goback = (String) super.session.get("goback");
		Object cpi = super.session.get("toDetailCurrentPageIndex");
		if (null != cpi) {
			if (cpi instanceof Integer) {
				this.currentPageIndex = (Integer) cpi;
				super.session.remove("toDetailCurrentPageIndex");
			}
		} else {
			currentPageIndex = request.getParameter("p") == null ? 0 : Integer.parseInt(request.getParameter("p"));
			if (currentPageIndex == 0 && null != search && null != search.getField10()) {
				currentPageIndex = Integer.parseInt(search.getField10());
			}
		}
		if (null == search && request.getParameter("p") != null) {
			Object lineSearch = super.session.get("lineSearch");
			if (lineSearch instanceof Search) {
				this.search = (Search) lineSearch;
			}
		}
		// goback不为空时为详情返回,读取查询分页信息
		if (null == search && super.session.get("goback") != null) {
			Object lineSearch = super.session.get("lineSearch");
			if (lineSearch instanceof Search) {
				this.search = (Search) lineSearch;
			}
			super.session.put("goback", null);
		}
		if (search != null && search.getField07() != null && !search.getField07().isEmpty()) {
			pageSize = Integer.parseInt(search.getField07());
		}
		super.session.put("lineSearch", search);
		map = lineService.getAllLines(search, currentPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	/**
	 * 获取我定制的线路
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getPublishedLines", results = {
			@Result(name = "success", location = "../../view/line/linesListOwn.jsp") })
	public String getPublishedLines() {
		try {

			/**
			 * 省份
			 */
			session.put("line_theTab", "1");
			SysArea sysArea = new SysArea();
			sysArea.setFdCode("-1");
			proSysAreas = iSysAreaService.querySysArea(sysArea);
			// 登录用户
			SysAdminVo admin = (SysAdminVo) session.get("userInfo");
			String goback = (String) super.session.get("goback");
			// 只查已发布
			if (null == search && request.getParameter("p") != null) {
				Object lineSearch = (Search) session.get("lineSearch");
				if (lineSearch instanceof Search) {
					this.search = (Search) lineSearch;
				}
				// goback不为空时为详情返回,读取查询分页信息
			} else if (null == search && goback != null) {
				Object lineSearch = super.session.get("lineSearch");
				if (lineSearch instanceof Search) {
					this.search = (Search) lineSearch;
				}
				super.session.put("goback", null);
			} else {
				if (null == search) {
					search = new Search();
				}
			}
			String p = request.getParameter("p");
			if (null == p) {
				p = "0";
			}
			currentPageIndex = Integer.parseInt(p);
			if (admin != null) {
				search.setField15(admin.getUserId());
			}
			Object cpi = super.session.get("toDetailCurrentPageIndex");
			if (null != cpi) {
				if (cpi instanceof Integer) {
					this.currentPageIndex = (Integer) cpi;
					super.session.remove("toDetailCurrentPageIndex");
				}
			}
			if (search != null && search.getField07() != null && !search.getField07().isEmpty()) {
				pageSize = Integer.parseInt(search.getField07());
			}
			super.session.put("lineSearch", search);
			map = lineService.getAllLines(search, this.currentPageIndex, pageSize);
			list = (List) map.get("list");// 数据对象
			page = (Page) map.get("page");// 分页对象
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 发送给商家
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "sendToMerchant", results = { @Result(type = "json") })
	public String sendToMerchant() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		String businessId = super.request.getParameter("businessId");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(businessId)) {
			LineEntity lineEntity = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != lineEntity) {
				// 1（非招募） 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除
				if (lineEntity.getLineStatus() == 0 && false == StringUtils.hasText(lineEntity.getBusinessId())) {
					lineEntity.setBusinessId(businessId);
					// 改变状态为调度中
					lineEntity.setLineStatus(1);
					String sessionUserId = this.getSessionUserId();
					if (StringUtils.hasText(sessionUserId)) {
						lineEntity.setUpdateBy(sessionUserId);
					}
					lineEntity.setUpdateOn(MyDate.getMyDateLong());
					flag = this.lineService.updateLine(lineEntity);
					if (flag == 1) {
						json = "success";
						logService.appendLog(this.getSessionUserId(), super.getClientIp(request),
								"线路[" + lineBaseId + "]发送给商家", "线路[" + lineBaseId + "]发送给商家成功");
					} else {
						logService.appendLog(this.getSessionUserId(), super.getClientIp(request),
								"线路[" + lineBaseId + "]发送给商家", "线路[" + lineBaseId + "]发送给商家失败");
					}
				}
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 删除线路，将线路的状态修改为5
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "deleteLine", results = { @Result(type = "json") })
	public String deleteLine() throws IOException {
		String json = "success";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			// 删除线路之前先判断是否与订单挂钩
			int count = this.lineService.getEffectiveOrderCount(lineBaseId);
			if (count > 0) {
				json = "此线路存在有效订单，暂时无法删除!";
			} else {
				LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
				if (null != line) {
					// 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除
					int lineStatus = line.getLineStatus();
					if (1 == line.getLineType()) {
						// 线路类型 0:招募 1:非招募
						if (0 == lineStatus || 1 == lineStatus || 2 == lineStatus || 4 == lineStatus) {
							// 可删除线路的前置状态为：待调度，调度中，待发布，已下线
							line.setUpdateBy(this.getSessionUserId());
							line.setUpdateOn(MyDate.getMyDateLong());
							line.setLineStatus(5);
							flag = this.lineService.updateLine(line);
							if (flag != 1) {
								json = "删除线路失败！";
							}
						} else {
							log.error("线路删除时，前置状态错误：" + lineStatus);
						}
					} else {
						log.error("线路删除时，线路类型错误：" + line.getLineType());
					}
				} else {
					log.error("线路删除时，线路信息不存在：" + lineBaseId);
				}
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路删除",
					"线路删除[" + lineBaseId + "]成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路删除",
					"线路删除[" + lineBaseId + "]失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 发布线路,将线路的状态修改为3
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "publishLine", results = { @Result(type = "json") })
	public String publishLine() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != line) {
				// 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除
				int lineStatus = line.getLineStatus();
				if (1 == line.getLineType()) {
					if (1 == lineStatus || 2 == lineStatus || 4 == lineStatus) {
						// 发布的前置状态为 待发布、已下线
						line.setUpdateBy(this.getSessionUserId());
						line.setUpdateOn(MyDate.getMyDateLong());
						line.setLineStatus(3);
						SysAdminVo sysAdmin = (SysAdminVo) session.get("userInfo");
						publishLineService.saveCommit(null, lineBaseId, "3", sysAdmin);
						flag = 1;
						// flag = this.lineService.updateLine(line);

						// List<LineClassVo> lineClassEntityList =
						// lineService.getLineClassList(line.getLineBaseId());
						// lineService.addImGroupInfo(sysAdmin.getUserId(),
						// line.getLineName(),
						// lineClassEntityList.get(lineClassEntityList.size()-1).getOrderSeats(),
						// lineBaseId);
						if (flag == 1) {
							json = "success";
						}
					} else {
						log.error("线路发布时，前置状态错误：" + lineStatus);
					}
				} else {
					log.error("线路发布时，线路类型错误：" + line.getLineType());
				}
			} else {
				log.error("线路发布时，线路信息不存在：" + lineBaseId);
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路发布",
					"线路发布[" + lineBaseId + "]成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路发布",
					"线路发布[" + lineBaseId + "]失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 线路置顶,将线路的displayFlag修改为1
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "topLine", results = { @Result(type = "json") })
	public String topLine() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != line) {
				// 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除
				int lineStatus = line.getLineStatus();
				if (1 == line.getLineType()) {
					if (3 == lineStatus) {
						// 置顶的前置状态为已发布
						line.setUpdateBy(this.getSessionUserId());
						line.setUpdateOn(MyDate.getMyDateLong());
						line.setDisplayFlag(1);
						flag = this.lineService.updateLine(line);
						if (flag == 1) {
							json = "success";
						}
					} else {
						log.error("线路置顶时，前置状态错误：" + lineStatus);
					}
				} else {
					log.error("线路置顶时，线路类型错误：" + line.getLineType());
				}
			} else {
				log.error("线路置顶时，线路信息不存在：" + lineBaseId);
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路置顶",
					"线路置顶[" + lineBaseId + "]成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路置顶",
					"线路置顶[" + lineBaseId + "]失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 发布线路,将线路的状态修改为4
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "offLine", results = { @Result(type = "json") })
	public String offLine() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != line) {
				// 0：待调度 1：调度中 2：待发布 3：已发布 4：已下线 5：删除
				int lineStatus = line.getLineStatus();
				if (1 == line.getLineType()) {
					if (3 == lineStatus) {
						line.setUpdateBy(this.getSessionUserId());
						line.setUpdateOn(MyDate.getMyDateLong());
						line.setLineStatus(4);
						flag = this.lineService.updateLine(line);
						SysAdminVo sysAdmin = (SysAdminVo) session.get("userInfo");
						publishLineService.saveCommit(null, lineBaseId, "4", sysAdmin);
						if (flag == 1) {
							json = "success";
						}
					} else {
						log.error("线路下线时，前置状态错误：" + lineStatus);
					}
				} else {
					log.error("线路下线时，线路类型错误：" + line.getLineType());
				}
			} else {
				log.error("线路下线时，线路信息不存在：" + lineBaseId);
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路下线",
					"线路下线[" + lineBaseId + "]成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "线路下线",
					"线路下线[" + lineBaseId + "]失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 线路详情总页面
	 */
	@Action(value = "getLinesDetailManager", results = {
			@Result(name = "success", location = "../../view/line/linesDetailManager.jsp") })
	public String getLineDetailManager() {
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			super.getSession().put("lineBaseId", lineBaseId);
		}
		super.getSession().put("toDetailCurrentPageIndex", this.currentPageIndex);

		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		String theTab = super.request.getParameter("line_theTab");
		if (StringUtils.hasText(theTab)) {
			super.getSession().put("line_theTab", theTab);
		}

		return SUCCESS;
	}

	private void getClassMonthChangeData(String lineBaseId, String orderStartTime, String supportMonth,
			List<LinePassengerMonthEntity> addMonthList, List<String> delMonthList) {
		List<String> quarterMonths = CalendarHelper.getQuarterMonthsFromNow();
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(supportMonth)
				&& supportMonth.contains(",")) {
			String[] supportMonthArr = supportMonth.split(",");
			if (ArrayUtils.isNotEmpty(supportMonthArr) && supportMonthArr.length == 3
					&& supportMonthArr.length == quarterMonths.size()) {
				for (int i = 0; i < quarterMonths.size(); i++) {
					String month = quarterMonths.get(i);
					LinePassengerMonthEntity lpme = this.lineService.getLinePassengerMonth(lineBaseId, orderStartTime,
							month);
					if (null == lpme) {
						if ("1".equals(supportMonthArr[i])) {
							// 表记录不存在，且页面已勾选则新增包月
							LinePassengerMonthEntity linePassengerMonth = new LinePassengerMonthEntity();
							linePassengerMonth.setMonthId(StringUtil.generateSequenceNo());// 获取id
							linePassengerMonth.setClassTime(orderStartTime);
							linePassengerMonth.setMonths(month);
							linePassengerMonth.setLineBaseId(lineBaseId);
							linePassengerMonth.setCreateBy(this.getSessionUserId());
							linePassengerMonth.setCreateOn(MyDate.getMyDateLong());
							addMonthList.add(linePassengerMonth);
						}
					} else {
						if ("0".equals(supportMonthArr[i])) {
							// 如果包月记录存在，但页面已去除勾选，则设为删除
							delMonthList.add(lpme.getMonthId());
						}
					}
				}
			}
		}

	}

	private void getLineClassChangeData(String lineBaseId, String orderStartTime, String orderSeat,
			String[] splitOrderDateArr, List<LineClassEntity> addClassList, List<String> delClassList) {
		// 从当前月开始，查询一季度的班次数据
		List<LineClassEntity> lineClassList = this.lineService.getQuarterLineClassList(lineBaseId, orderStartTime);

		// 给新添加的工作日期添加一个默认的司机和车辆
		// 司机和车辆来自于上次排班的最后一次工作日
		AppVo_2 vo_2 = getDriverAndCar(lineBaseId);

		if (!(null == lineClassList || lineClassList.size() == 0)) {
			List<String> l = new ArrayList<String>();
			for (LineClassEntity lce : lineClassList) {
				l.add(lce.getOrderDate());
				// 原日期不在所选日期内,说明已在页面勾除,可删除
				if (!Arrays.asList(splitOrderDateArr).contains(lce.getOrderDate())) {
					delClassList.add(lce.getLineClassId());
				}
			}

			for (String s : splitOrderDateArr) {
				// 所选日期不在原日期内,说明是页面新加的,可新增
				if (!l.contains(s)) {
					LineClassEntity lineClass = new LineClassEntity();
					lineClass.setLineClassId(StringUtil.generateSequenceNo());// 获取id
					lineClass.setOrderStartTime(orderStartTime);
					lineClass.setOrderSeats(Integer.parseInt(orderSeat));
					lineClass.setOrderDate(s);
					lineClass.setLineBaseId(lineBaseId);
					if (null != vo_2) {
						lineClass.setDriverId(vo_2.getA1());
						lineClass.setVehicleId(vo_2.getA2());
					} else {
						lineClass.setDriverId("");
						lineClass.setVehicleId("");
					}
					addClassList.add(lineClass);
				}
			}
		} else {
			// 从当前月开始，如果无数据，且预定日期数据为当前季度视为新增
			for (String s : splitOrderDateArr) {
				if (checkAfter(s)) {
					LineClassEntity lineClass = new LineClassEntity();
					lineClass.setLineClassId(StringUtil.generateSequenceNo());// 获取id
					lineClass.setOrderStartTime(orderStartTime);
					lineClass.setOrderSeats(Integer.parseInt(orderSeat));
					lineClass.setOrderDate(s);
					lineClass.setLineBaseId(lineBaseId);
					if (null != vo_2) {
						lineClass.setDriverId(vo_2.getA1());
						lineClass.setVehicleId(vo_2.getA2());
					} else {
						lineClass.setDriverId("");
						lineClass.setVehicleId("");
					}
					addClassList.add(lineClass);
				}
			}
		}
	}

	/**
	 * 判断输入日期是否从本月一号开始
	 * 
	 * @param inputDate
	 * @return
	 */
	private static boolean checkAfter(String inputDate) {
		Calendar now = Calendar.getInstance();
		// 获取上个月最后一天
		now.add(Calendar.MONTH, -1);
		now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date inDate;
		try {
			inDate = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
			return inDate.after(now.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取线路调度信息,只返回线路基本信息，商家基本信息，调度信息通过Ajax调用获取
	 */
	@Action(value = "getScheduleInfo", results = {
			@Result(name = "success", location = "../../view/line/linesScheduleInfo.jsp") })
	public String getScheduleInfo() {
		Object obj = super.session.get("lineBaseId");
		if (obj instanceof String) {
			lineScheduleInfo = this.lineService.getLineScheduleInfo((String) obj);
		}
		return SUCCESS;
	}

	/**
	 * 获取月份工作日的车辆司机信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getClassCarDriver", results = { @Result(type = "json") })
	public String getClassCarDriver() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			if (orderDate.length() == 6) {
				int index = orderDate.indexOf("-");
				orderDate = orderDate.substring(0, index + 1) + "0" + orderDate.substring(index + 1);
			}
			List<LineClassCarDriverVo> ccdList = this.lineService.getLineClassCarDriverList(lineBaseId, orderStartTime,
					orderDate);
			if (false == CollectionUtils.isEmpty(ccdList)) {
				// 转成json
				json = JSONUtil.formObjectsToJSONStr(ccdList);
			}
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 根据线路id、发车时间、所选年月查询班次信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getLineClass", results = { @Result(type = "json") })
	public String getLineClass() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			List<LineClassEntity> ccdList = this.lineService.getLineClassByConditoins(lineBaseId, orderStartTime,
					orderDate);
			if (false == CollectionUtils.isEmpty(ccdList)) {
				List<Map<String, Object>> mapL = new ArrayList<Map<String, Object>>();
				Map<String, Object> m = null;
				for (LineClassEntity l : ccdList) {
					m = new HashMap<String, Object>();
					m.put("dateTime", l.getOrderDate());
					mapL.add(m);
				}
				// 转成json
				json = JSONUtil.formObjectsToJSONStr(mapL);
			}
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 根据线路id、日期、发车时间判断对应班次是否已经被订座
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "judgeLineClassUsed", results = { @Result(type = "json") })
	public String judgeLineClassUsed() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			int flag = this.lineService.judgeLineClassUsed(lineBaseId, orderStartTime, orderDate);
			// 转成json
			json = JSONUtil.formObjectToJSONStr(flag);
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 根据线路ID、班次时间查询订单总数
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "judgeLineClassOrderCount", results = { @Result(type = "json") })
	public String judgeLineClassOrderCount() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime)) {
			int count = this.lineService.judgeLineClassOrderCount(lineBaseId, orderStartTime);
			json = JSONUtil.formObjectToJSONStr(count);
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 根据线路id、日期、发车时间判断是否包月
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "judgeLinePassengerMonth", results = { @Result(type = "json") })
	public String judgeLinePassengerMonth() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			LinePassengerMonthEntity lpme = this.lineService.getLinePassengerMonth(lineBaseId, orderStartTime,
					orderDate);
			int flag = 0;
			if (null == lpme || !StringUtils.hasText(lpme.getMonthId())) {
				flag = 0;// 没有包月信息
			} else {
				flag = 1;// 有包月信息
			}
			// 转成json
			json = JSONUtil.formObjectToJSONStr(flag);
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	@Action(value = "judgeLineClassOrderSeats", results = { @Result(type = "json") })
	public String judgeLineClassOrderSeats() throws IOException {
		String json = "0";
		int theNewSeats = Integer.parseInt(super.request.getParameter("theNewSeats"));
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime)) {
			int count = lineService.getLineClassOrderSeats(lineBaseId, orderStartTime);
			if (count == 0 || (theNewSeats >= count)) {
				// 直接修改
				int flag = lineService.updateOrderSeats(lineBaseId, orderStartTime, theNewSeats);
				json = flag + ",";
			} else {
				// 提示不能修改
				json = "-1," + count;
			}
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取线路订购人数
	 */
	@Action(value = "getOrderNum", results = {
			@Result(name = "success", location = "../../view/line/linesOrderNum.jsp") })
	public String getOrderNum() {
		String lineBaseId = request.getParameter("lineBaseId");
		if (lineBaseId == null || lineBaseId.isEmpty()) {
			lineBaseId = (String) super.session.get("lineBaseId");
		}
		lineScheduleInfo = this.lineService.getLineScheduleInfo(lineBaseId);
		return SUCCESS;
	}

	@Action(value = "getClassMonthOrderCount", results = { @Result(type = "json") })
	public String getClassMonthOrderCount() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			if (orderDate.length() == 6) {
				int index = orderDate.indexOf("-");
				orderDate = orderDate.substring(0, index + 1) + "0" + orderDate.substring(index + 1);
			}
			List<LineClassMonthOrderCountVo> ccdList = this.lineService.getClassMonthOrderCount(lineBaseId, orderDate,
					orderStartTime);
			if (false == CollectionUtils.isEmpty(ccdList)) {
				// 转成json
				json = JSONUtil.formObjectsToJSONStr(ccdList);
			}
		}
		System.out.println("json:" + json);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取线路订购人数-详情
	 */
	@Action(value = "getOrderNumDetail", results = {
			@Result(name = "success", location = "../../view/line/pop-orderNumDetail.jsp") })
	public String getOrderNumDetail() {
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "getLineOrderPassengerList", results = { @Result(type = "json") })
	public String getLineOrderPassengerList() throws IOException {
		String json = "{}";
		int currPage = 1;
		String currPageStr = super.request.getParameter("currPage");
		if (StringUtils.hasText(currPageStr)) {
			try {
				currPage = Integer.parseInt(currPageStr);
			} catch (Exception e) {
			}
		}
		this.pageSize = 10;
		int curPageIndex = (currPage - 1) * this.pageSize;
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderDate = super.request.getParameter("orderDate");
		String orderStartTime = super.request.getParameter("orderStartTime");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			if (orderDate.length() < 10) {
				// 修正日期数据
				String[] arr = orderDate.split("-");
				StringBuffer res = new StringBuffer();
				if (ArrayUtils.isNotEmpty(arr) && arr.length == 3) {
					res.append(arr[0]);
					res.append("-");
					if (arr[1].length() < 2) {
						res.append("0");
					}
					res.append(arr[1]);
					res.append("-");
					if (arr[2].length() < 2) {
						res.append("0");
					}
					res.append(arr[2]);
					orderDate = res.toString();
				}
			}
			map = lineService.getOrderPassengerList(lineBaseId, orderDate, orderStartTime, curPageIndex, pageSize);
			list = (List) map.get("list");// 数据对象
			page = (Page) map.get("page");// 分页对象
			LineOrderPassengerVo mv = new LineOrderPassengerVo();
			mv.setCurrPage(currPage);
			mv.setPageSize(this.pageSize);
			mv.setTotalCount(null != page ? page.getTotalCount() : 0);
			mv.setResultData(list);
			json = JSONUtil.formObjectToJsonObject(mv);
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取招募线路总页面
	 */
	@Action(value = "recruitLinesManager", results = {
			@Result(name = "success", location = "../../view/line/recruitLinesManager.jsp") })
	public String recruitLinesManager() {
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		Object theTab = super.session.get("line_theTab");
		if (theTab instanceof String) {
			super.request.setAttribute("line_theTab", theTab);
			super.session.remove("line_theTab");
		}
		return SUCCESS;
	}

	/**
	 * 线路详情-查看最近的包月的价格
	 */
	@Action(value = "viewMonthPrice", results = {
			@Result(name = "success", location = "../../view/line/pop-viewMonthPrice.jsp") })
	public String viewMonthPrice() {
		// 先查询出
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			List<LineClassVo> classList = this.lineService.getLineClassList(lineBaseId);
			super.request.setAttribute("lineBaseId", lineBaseId);
			super.request.setAttribute("classList", classList);
		}
		return SUCCESS;
	}

	/**
	 * 获取班次单月价格信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getClassOneMonthOrderPrice", results = { @Result(type = "json") })
	public String getClassOneMonthOrderPrice() throws IOException {
		String json = "[]";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String orderStartTime = super.request.getParameter("orderStartTime");
		// 参数要求：2014-09
		String orderDate = super.request.getParameter("orderDate");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTime) && StringUtils.hasText(orderDate)) {
			ClassMonthOrderPriceVo cmo = this.lineService.getClassOneMonthOrderPrice(lineBaseId, orderStartTime,
					orderDate);
			if (null != cmo) {
				json = JSONUtil.formObjectToJSONStr(cmo);
			}

		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取招募线路列表
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getRecruitLines", results = {
			@Result(name = "success", location = "../../view/line/recruitLinesList.jsp") })
	public String getRecruitLines() {
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);

		if (null == session.get("toDetailCurrentPageIndex")) {
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
					: Integer.parseInt(request.getParameter("currentPageIndex"));
		}

		/* 将条件存到session,便于刷新后任然存在页面而不会丢失 */
		search = (Search) (search == null ? session.get("search") : search);
		if (null == search) {
			search = new Search();
		}

		map = lineService.getRecruitLines(search, currentPageIndex, this.pageSize);
		this.session.put("search", search);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	/**
	 * 开启招募线路
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "openRecruitLine", results = { @Result(type = "json") })
	public String openRecruitLine() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != line) {
				// 线路类型：0（招募） 0:招募前 1：招募中 2：关闭 3：删除
				int lineStatus = line.getLineStatus();
				if (0 == line.getLineType()) {
					if (2 == lineStatus) {
						// 线路开启之前,其前置状态为关闭状态，状态变更为招募中
						line.setUpdateBy(this.getSessionUserId());
						line.setUpdateOn(MyDate.getMyDateLong());
						line.setLineStatus(1);
						flag = this.lineService.updateLine(line);
						if (flag == 1) {
							json = "success";
						}
					} else {
						log.error("线路开启时，前置状态错误：" + lineStatus);
					}
				} else {
					log.error("线路开启时，线路类型错误：" + line.getLineType());
				}
			} else {
				log.error("线路下线时，线路信息不存在：" + lineBaseId);
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路开启",
					"招募线路[" + lineBaseId + "]开启成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路开启",
					"招募线路[" + lineBaseId + "]开启失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 关闭招募线路
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "closeRecruitLine", results = { @Result(type = "json") })
	public String closeRecruitLine() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != line) {
				// 线路类型：0（招募） 0:招募前 1：招募中 2：关闭 3：删除
				int lineStatus = line.getLineStatus();
				if (0 == line.getLineType()) {
					if (1 == lineStatus) {
						// 其前置状态为招募中状态，状态变更为关闭
						line.setUpdateBy(this.getSessionUserId());
						line.setUpdateOn(MyDate.getMyDateLong());
						line.setLineStatus(2);
						flag = this.lineService.updateLine(line);
						if (flag == 1) {
							json = "success";
						}
					} else {
						log.error("线路关闭时，前置状态错误：" + lineStatus);
					}
				} else {
					log.error("线路关闭时，线路类型错误：" + line.getLineType());
				}
			} else {
				log.error("线路下线时，线路信息不存在：" + lineBaseId);
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路关闭",
					"招募线路[" + lineBaseId + "]关闭成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路关闭",
					"招募线路[" + lineBaseId + "]关闭失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 删除招募线路
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "deleteRecruitLine", results = { @Result(type = "json") })
	public String deleteRecruitLine() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != line) {
				// 线路类型：0（招募） 0:招募前 1：招募中 2：关闭 3：删除
				int lineStatus = line.getLineStatus();
				if (0 == line.getLineType()) {
					if (2 == lineStatus) {
						// 其前置状态为关闭状态，状态变更为删除状态
						line.setUpdateBy(this.getSessionUserId());
						line.setUpdateOn(MyDate.getMyDateLong());
						line.setLineStatus(3);
						flag = this.lineService.updateLine(line);
						if (flag == 1) {
							json = "success";
						}
					} else {
						log.error("线路删除时，前置状态错误：" + lineStatus);
					}
				} else {
					log.error("线路删除时，线路类型错误：" + line.getLineType());
				}
			} else {
				log.error("线路删除时，线路信息不存在：" + lineBaseId);
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路删除",
					"招募线路[" + lineBaseId + "]删除成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路删除",
					"招募线路[" + lineBaseId + "]删除失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 招募线路置顶
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "topRecruitLine", results = { @Result(type = "json") })
	public String topRecruitLine() throws IOException {
		String json = "error";
		int flag = 0;
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (StringUtils.hasText(lineBaseId)) {
			LineEntity line = this.lineService.getLineByLineBaseId(lineBaseId);
			if (null != line) {
				// 线路类型：0（招募） 0:招募前 1：招募中 2：关闭 3：删除
				int lineStatus = line.getLineStatus();
				if (0 == line.getLineType()) {
					if (1 == lineStatus) {
						// 其前置状态为招募中状态
						line.setUpdateBy(this.getSessionUserId());
						line.setUpdateOn(MyDate.getMyDateLong());
						// 1为置顶
						line.setDisplayFlag(1);
						flag = this.lineService.updateLine(line);
						if (flag == 1) {
							json = "success";
						}
					} else {
						log.error("线路置顶时，前置状态错误：" + lineStatus);
					}
				} else {
					log.error("线路置顶时，线路类型错误：" + line.getLineType());
				}
			} else {
				log.error("线路置顶时，线路信息不存在：" + lineBaseId);
			}
		}
		if ("success".equals(json)) {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路置顶",
					"招募线路[" + lineBaseId + "]置顶成功");
		} else {
			logService.appendLog(this.getSessionUserId(), super.getClientIp(request), "招募线路置顶",
					"招募线路[" + lineBaseId + "]置顶失败");
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取招募线路详情总页面
	 */
	@Action(value = "recruitLinesDetailManeager", results = {
			@Result(name = "success", location = "../../view/line/recruitLinesDetailManeager.jsp") })
	public String recruitLinesDetailManeager() {
		String recLineBaseId = super.request.getParameter("recLineBaseId");
		if (StringUtils.hasText(recLineBaseId)) {
			super.getSession().put("recLineBaseId", recLineBaseId);
		}
		super.getSession().put("toDetailCurrentPageIndex", this.currentPageIndex);
		return SUCCESS;
	}

	private List<String> converFixTime(String recruitFixTime) {
		List<String> list = Arrays.asList(new String[] { "", "", "", "", "", "", "" });
		if (StringUtils.hasText(recruitFixTime)) {
			String[] arr = recruitFixTime.split(",");
			for (String s : arr) {
				list.set(Integer.parseInt(s) - 1, s);
			}
		}
		return list;
	}

	/**
	 * 获取招募线路详情
	 */
	@Action(value = "recruitLinesDetail", results = {
			@Result(name = "success", location = "../../view/line/recruitLinesDetail.jsp") })
	public String recruitLinesDetail() {
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);
		Object obj = super.session.get("recLineBaseId");
		if (obj instanceof String) {
			recruitLineStationVo = this.lineService.getRecruitLineStation((String) obj);
			if (null != recruitLineStationVo) {
				// 将固定时间格式转换成列表
				List<String> fixTimeList = this.converFixTime(recruitLineStationVo.getRecruitFixTime());
				recruitLineStationVo.setClassTime(recruitLineStationVo.getRecruitStartTime());
				/*
				 * String s = recruitLineStationVo.getRecruitStartTime();
				 * if(-1!=s.indexOf(" ")){
				 * recruitLineStationVo.setRecruitStartTime
				 * (s.substring(0,s.indexOf(" ")));
				 * recruitLineStationVo.setClassTime (s.substring(s.indexOf(" "
				 * )+1)); }
				 */
				if (false == CollectionUtils.isEmpty(fixTimeList)) {
					recruitLineStationVo.setFixTimeList(fixTimeList);
				}
			}
		}
		// super.session.remove("recLineBaseId");
		return SUCCESS;
	}

	/**
	 * 获取招募线路-预定人数
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "recruitLinesOrderNum", results = {
			@Result(name = "success", location = "../../view/line/recruitLinesOrderNum.jsp") })
	public String recruitLinesOrderNum() {
		Object obj = super.session.get("recLineBaseId");
		// 先查出线路信息
		recruitLineStationVo = this.lineService.getRecruitLineStation((String) obj);
		// 再根据线路查询出预定乘客列表
		if (null != recruitLineStationVo) {
			String lineBaseId = recruitLineStationVo.getLineBaseId();
			if (StringUtils.hasText(lineBaseId)) {
				// 查询招募线路的报名列表
				map = this.lineService.getApplyPassengerList(lineBaseId, currentPageIndex, this.pageSize);
				list = (List) map.get("list");// 数据对象
				page = (Page) map.get("page");// 分页对象
				recruitLineStationVo.setApplyTotal(page.getTotalCount());
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "exportTelephone")
	public void exportTelephone() throws IOException {
		getResponse().setContentType("application/msexcel;charset=GBK");
		String lineBaseId = super.request.getParameter("lineBaseId");
		if (false == StringUtils.hasText(lineBaseId)) {
			log.error("lineBaseId is empty.");
			return;
		}
		LineEntity qLine = this.lineService.getLineByLineBaseId(lineBaseId);
		if (null == qLine) {
			log.error("qLine is null.");
			return;
		}
		Map<String, Object> pMap = this.lineService.getApplyPassengerList(lineBaseId, 0, 65535);
		List<PassengerStationVo> pList = null;
		if (false == CollectionUtils.isEmpty(pMap)) {
			pList = (List<PassengerStationVo>) pMap.get("list");
		}
		if (false == CollectionUtils.isEmpty(pList)) {
			for (PassengerStationVo p : pList) {
				String sex = p.getSex();
				if (StringUtils.hasText(sex)) {
					if ("0".equals(sex)) {
						p.setSex("男");
					} else if ("1".equals(sex)) {
						p.setSex("女");
					}
				}
			}
			String title = qLine.getLineName() + "-乘客列表";
			String[] hearders = new String[] { "报名时间", "用户ID", "昵称", "性别", "手机号", "上车点", "下车点", "乘座人数", "备注" };
			String[] fields = new String[] { "presetTime", "displayId", "nickName", "sex", "telephone", "ustationName",
					"dstationName", "applyNum", "remark" };
			TableData td = ExcelUtils.createTableData(pList, ExcelUtils.createTableHeader(hearders), fields);
			JsGridReportBase report;
			try {
				report = new JsGridReportBase(request, getResponse());
				String agent = request.getHeader("User-Agent");
				report.exportToExcel(title, td, agent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取用户申请线路统计
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getUserLinesCount", results = {
			@Result(name = "success", location = "../../view/line/userLinesCount.jsp") })
	public String getUserLinesCount() {
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		// 统计类型 1:上车点 2:下车点
		if (search == null) {
			search = new Search();
			search.setField01("1");
		}
		map = lineService.getUserLinesStationCount(search, currentPageIndex, this.pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	@Action(value = "selectStationApply", results = {
			@Result(name = "success", location = "../../view/line/pop-stationApplyList.jsp") })
	public String selectStationApply() {
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "getStationApplyList", results = { @Result(type = "json") })
	public String getStationApplyList() throws IOException {
		String json = "{}";
		int currPage = 1;
		String currPageStr = super.request.getParameter("currPage");
		if (StringUtils.hasText(currPageStr)) {
			try {
				currPage = Integer.parseInt(currPageStr);
			} catch (Exception e) {
			}
		}
		this.pageSize = 10;
		int curPageIndex = (currPage - 1) * this.pageSize;
		if (null == search) {
			search = new Search();
		}
		// 上下站点类型 1：上车点 2：下车点
		String type = super.request.getParameter("type");
		if (StringUtils.hasText(type)) {
			search.setField01(type);
		}
		String stationName = super.request.getParameter("stationName");
		if (StringUtils.hasText(stationName)) {
			search.setField02(URLDecoder.decode(stationName, "utf-8"));
		}
		map = lineService.getStationApplyList(search, curPageIndex, pageSize);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		StationApplyVo mv = new StationApplyVo();
		mv.setCurrPage(currPage);
		mv.setPageSize(this.pageSize);
		mv.setTotalCount(null != page ? page.getTotalCount() : 0);
		mv.setResultData(list);
		json = JSONUtil.formObjectToJsonObject(mv);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获取用户申请线路-发布
	 */
	@Action(value = "getUserLinesPublish", results = {
			@Result(name = "success", location = "../../view/line/userLinesPublish.jsp") })
	public String getUserLinesPublish() {
		String applicationId = super.request.getParameter("applicationId");
		if (StringUtils.hasText(applicationId)) {
			userLineEntity = lineService.getUserLineDetailByKey(applicationId);
		}
		return SUCCESS;
	}

	/**
	 * 审核用户申请线路
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "changeUserLineStatus", results = { @Result(type = "json") })
	public String changeUserLineStatus() {
		try {
			String returnStr = null;

			String applicationId = request.getParameter("applicationId");
			String auditStatus = request.getParameter("auditStatus");

			if (!StringUtils.hasText(applicationId) || !StringUtils.hasText(auditStatus)) {
				returnStr = "信息丢失，请刷新列表重新操作！";
			} else {
				UserLineEntity userLine = lineService.getUserLineById(applicationId);
				if (null == userLine) {
					returnStr = "信息丢失，请刷新列表重新操作！";
				} else {
					if ("0".equals(auditStatus) || "2".equals(auditStatus)) {// 未审核
						userLine.setAuditStatus(1);
					} else {// 已审核
						userLine.setAuditStatus(0);
					}

					SysAdminVo sysAdmin = (SysAdminVo) this.request.getSession().getAttribute("userInfo");
					int returnNum = lineService.updateUserLine(userLine,
							null == sysAdmin ? null : sysAdmin.getUserId());
					if (returnNum > 0) {
						returnStr = "ok";
						sendMessage(applicationId, auditStatus);
					} else {
						returnStr = "操作失败，请刷新列表重新操作！";
					}
				}
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(returnStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户申请线路审核时，调用app接口发送站内信
	 */
	private void sendMessage(String applicationId, String auditStatus) {
		try {
			// 发送站内信
			String applicationOpenUrl = PropertyManage.getInfoProperty("app_back_root")+"app_MsgTask/sendRelateMsg.action";
			if (StringUtils.hasText(applicationOpenUrl)) {
				applicationOpenUrl += "?applicationId=" + applicationId + "&auditStatus=" + auditStatus;
				String result = PostHttpClient.getHttpReq(applicationOpenUrl, "");
				if (StringUtils.hasText(result)) {
					if ("0".equals(result)) {
						log.info("send failure.");
					} else if ("1".equals(result)) {
						log.info("send success.");
					} else {
						log.info("unknown result=" + result);
					}
				} else {
					log.error("no return value.");
				}
			} else {
				log.error("applicationOpenUrl参数的URL地址尚未配置.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Action(value = "exportUserLineCount")
	public void exportUserLineCount() throws IOException {
		getResponse().setContentType("application/msexcel;charset=GBK");
		// 统计类型 1:上车点 2:下车点
		if (search == null) {
			search = new Search();
			search.setField01("1");
		}
		map = lineService.getUserLinesStationCount(search, 0, 65535);
		List<UserLineStationCount> pList = null;
		if (false == CollectionUtils.isEmpty(map)) {
			pList = (List<UserLineStationCount>) map.get("list");
		}
		if (false == CollectionUtils.isEmpty(pList)) {
			String title = search.getField01().equals("1") ? "按上车点统计" : "按下车点统计";
			String[] hearders = new String[] { search.getField01().equals("1") ? "上车点" : "下车点", "申请人数" };
			String[] fields = new String[] { "address", "applyCount" };
			TableData td = ExcelUtils.createTableData(pList, ExcelUtils.createTableHeader(hearders), fields);
			JsGridReportBase report;
			try {
				report = new JsGridReportBase(request, getResponse());
				String agent = request.getHeader("User-Agent");
				report.exportToExcel(title, td, agent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取用户线路列表
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getUserLines", results = { @Result(name = "success", location = "../../view/line/userLines.jsp") })
	public String getUserLines() {
		/**
		 * 省份
		 */
		SysArea sysArea = new SysArea();
		sysArea.setFdCode("-1");
		proSysAreas = iSysAreaService.querySysArea(sysArea);

		if (null == session.get("toDetailCurrentPageIndex")) {
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
					: Integer.parseInt(request.getParameter("currentPageIndex"));
		}

		/* 将条件存到session,便于刷新后任然存在页面而不会丢失 */
		search = (Search) (search == null ? session.get("search") : search);
		if (null == search) {
			search = new Search();
		}

		if (null != search) {
			if (StringUtils.hasText(search.getField06())) {
				try {
					search.setField06(URLDecoder.decode(search.getField06(), "utf-8"));
				} catch (Exception e) {
				}
			}
			if (StringUtils.hasText(search.getField07())) {
				try {
					search.setField07(URLDecoder.decode(search.getField07(), "utf-8"));
				} catch (Exception e) {
				}
			}
		}
		map = lineService.getUserLines(search, currentPageIndex, this.pageSize);
		this.session.put("search", search);
		list = (List) map.get("list");// 数据对象
		page = (Page) map.get("page");// 分页对象
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Action(value = "exportUserLines")
	public void exportUserLines() throws IOException {
		getResponse().setContentType("application/msexcel;charset=GBK");
		map = lineService.getUserLines(search, 0, 65535);
		List<UserLineEntity> pList = null;
		if (false == CollectionUtils.isEmpty(map)) {
			pList = (List<UserLineEntity>) map.get("list");
		}
		if (false == CollectionUtils.isEmpty(pList)) {
			for (UserLineEntity p : pList) {
				int callbackStatus = p.getCallbackStatus();
				if (callbackStatus == 0) {
					p.setCallbackStatusStr("未回访");
				}
				if (callbackStatus == 1) {
					p.setCallbackStatusStr("已回访");
				}
				int lineType = p.getLineType();
				if (lineType == 0) {
					p.setLineTypeStr("上下班");
				}
				if (lineType == 1) {
					p.setLineTypeStr("自由行");
				}
			}
			String title = "用户申请线路";
			String[] hearders = new String[] { "申请时间", "申请人", "联系电话", "线路类型", "上车点", "下车点", "发车时间", "备注", "回访状态" };
			String[] fields = new String[] { "applicationTime", "nickName", "telephone", "lineTypeStr", "startAddress",
					"endAddress", "startTime", "remark", "callbackStatusStr" };
			TableData td = ExcelUtils.createTableData(pList, ExcelUtils.createTableHeader(hearders), fields);
			JsGridReportBase report;
			try {
				report = new JsGridReportBase(request, getResponse());
				String agent = request.getHeader("User-Agent");
				report.exportToExcel(title, td, agent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Action(value = "getUserLineDetailManager", results = {
			@Result(name = "success", location = "../../view/line/userLineDetailManager.jsp") })
	public String getUserLineDetailManager() {
		String applicationId = super.request.getParameter("applicationId");
		super.session.put("applicationId", applicationId);
		super.getSession().put("toDetailCurrentPageIndex", this.currentPageIndex);
		// 从已发布列表进入详情时传过来的标识，用于已发布列表详情页面点击上一页按钮时判断显示哪一个列表tab
		String theTab = super.request.getParameter("line_theTab");
		if (StringUtils.hasText(theTab)) {
			super.getSession().put("line_theTab", theTab);
		}
		return SUCCESS;
	}

	/**
	 * 获取用户线路详情
	 */
	@Action(value = "getUserLineDetail", results = {
			@Result(name = "success", location = "../../view/line/userLineDetail.jsp") })
	public String getUserLineDetail() {
		String applicationId = null;
		Object obj = super.session.get("applicationId");
		if (obj instanceof String) {
			applicationId = (String) obj;
			userLineEntity = lineService.getUserLineDetail(applicationId);
		}
		return SUCCESS;
	}

	/**
	 * 用户申请线路报名列表
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "getUserLineApplyList", results = {
			@Result(name = "success", location = "../../view/line/userLineApplyList.jsp") })
	public String getUserLineApplyList() {
		currentPageIndex = request.getParameter("currentPageIndex") == null ? 0
				: Integer.parseInt(request.getParameter("currentPageIndex"));
		Object obj = super.session.get("applicationId");
		if (obj instanceof String) {
			String applicationId = (String) obj;
			map = lineService.getUserLineApplyList(applicationId, currentPageIndex, pageSize);
			list = (List) map.get("list");// 数据对象
			page = (Page) map.get("page");// 分页对象
		}
		return SUCCESS;
	}

	/**
	 * 创建用户申请线路回访信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "addUserLineCallBack", results = { @Result(type = "json") })
	public String addUserLineCallBack() throws IOException {
		String json = "error";
		String applicationId = super.request.getParameter("applicationId");
		String callbackContent = super.request.getParameter("callbackContent2");
		if (StringUtils.hasText(applicationId) && StringUtils.hasText(callbackContent)) {
			UserLineCallbackVo callbackVo = new UserLineCallbackVo();
			callbackVo.setApplicationId(applicationId);
			callbackVo.setCallbackContent(callbackContent);
			callbackVo.setCreateBy(this.getSessionUserId());
			callbackVo.setCreateOn(MyDate.getMyDateLong());
			callbackVo.setUpdateBy(this.getSessionUserId());
			callbackVo.setUpdateOn(MyDate.getMyDateLong());
			int flag = this.lineService.addUserLineCallBack(callbackVo);
			if (flag > 0) {
				json = "success";
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 检查线路名称是否重复
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "checkSameLineName", results = { @Result(type = "json") })
	public String checkSameLineName() throws IOException {
		String json = "error";
		String lineBaseId = super.request.getParameter("lineBaseId");
		String lineName = super.request.getParameter("lineName");
		String lineType = super.request.getParameter("lineType");
		// lineName为必传字段，修改数据时，lineBaseId为必传字段
		if (StringUtils.hasText(lineName)) {
			// 含有重名时，返回true,反之返回false
			lineName = URLDecoder.decode(lineName, "utf-8");
			boolean res = this.lineService.checkSameLineName(lineBaseId, lineName, lineType);
			if (!res) {
				json = "success";
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 与删除的班次发车时间比对，检测发车时间是否重复
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "checkSameClassTime", results = { @Result(type = "json") })
	public String checkSameClassTime() throws IOException {
		String json = "success";
		String lineBaseId = super.request.getParameter("lineBaseId");
		// 前端传值必须以逗号隔开
		String orderStartTimes = super.request.getParameter("orderStartTimes");
		if (StringUtils.hasText(lineBaseId) && StringUtils.hasText(orderStartTimes)) {
			List<OrderStartTimeVo> sList = this.lineService.checkSameClassTime(lineBaseId, orderStartTimes);
			if (false == CollectionUtils.isEmpty(sList)) {
				int size = sList.size();
				StringBuffer msg = new StringBuffer();
				for (int i = 0; i < size; i++) {
					msg.append(sList.get(i).getOrderStartTime());
					if (i < size - 1) {
						msg.append(",");
					}
				}
				json = msg.toString();
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	@Action(value = "getAllLinesJson", results = { @Result(type = "json") })
	public String getAllLinesJson() throws IOException {
		List<LineEntity> lineList = this.lineService.getAllLineList();
		String json = JSONUtil.formObjectsToJSONStr(lineList);
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	@Action(value = "forwardLineAllowance", results = {
			@Result(name = "success", location = "../../view/line/lineAllowance.jsp") })
	public String forwardLineAllowance() {
		String lineBaseId = super.request.getParameter("lineBaseId");
		LineAllowanceDetailVo tempLineAllowanceDetailVo = this.lineService.getLineAllowance(lineBaseId);
		if (false == StringUtils.hasText(tempLineAllowanceDetailVo.getAllowanceId())) {
			// 如果线路没有设置过补贴，则默认为启用状态
			tempLineAllowanceDetailVo.setStatusFlag(1);
		}
		this.lineAllowanceDetailVo = tempLineAllowanceDetailVo;
		return SUCCESS;
	}

	@Action(value = "setLineAllowance", results = { @Result(type = "json") })
	public String setLineAllowance() throws IOException {
		String json = "error";
		LineAllowanceVo v = this.createLineAllowanceVo();
		int flag = this.lineService.setLineAllowance(v);
		if (flag > 0) {
			json = "success";
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	private LineAllowanceVo createLineAllowanceVo() {
		LineAllowanceVo v = new LineAllowanceVo();
		v.setAllowanceId(super.request.getParameter("allowanceId"));
		v.setLineBaseId(super.request.getParameter("lineBaseId"));
		String ownAllowance = super.request.getParameter("ownAllowance");
		if (StringUtils.hasText(ownAllowance)) {
			v.setOwnAllowance(new BigDecimal(ownAllowance));
		}
		String otherAllowance = super.request.getParameter("otherAllowance");
		if (StringUtils.hasText(otherAllowance)) {
			v.setOtherAllowance(new BigDecimal(otherAllowance));
		}
		v.setStatusFlag(Integer.parseInt(super.request.getParameter("statusFlag")));
		v.setOperateBy(this.getSessionUserId());
		v.setOperateOn(MyDate.getMyDateLong());
		return v;
	}

	@Action(value = "exportLinePassengers")
	public void exportLinePassengers() throws IOException {
		HttpServletRequest r = super.request;
		String lineBaseId = r.getParameter("lineBaseId");
		String orderStartTime = r.getParameter("orderStartTime");
		String startTime = r.getParameter("startTime");
		if (StringUtils.isEmpty(lineBaseId) || StringUtils.isEmpty(orderStartTime) || StringUtils.isEmpty(startTime)) {
			return;
		}
		LineEntity lineEntity = lineService.getLineByLineBaseId(lineBaseId);
		String endTime = r.getParameter("endTime");
		List<LineClassOrderPassengerVo> pList = lineService.getLineClassOrderPassengers(lineBaseId, orderStartTime,
				startTime, endTime);
		if (null == lineEntity || false != CollectionUtils.isEmpty(pList)) {
			return;
		}
		String isRomoveRepeat = r.getParameter("isRomoveRepeat");
		if ("1".equals(isRomoveRepeat)) {
			// 去除重复数据
			pList = this.removeRepeat(pList);
		}
		String title = lineEntity.getLineName() + "-" + orderStartTime.replace(":", "点") + "分-" + startTime + "至"
				+ endTime + "乘客订购列表";
		String[] hearders = new String[] { "下单时间","乘坐日期", "用户ID", "昵称", "手机号" };
		String[] fields = new String[] { "leaseOrderTime","orderDate", "displayId", "nickName", "telephone"};
		TableData td = ExcelUtils.createTableData(pList, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report;
		HttpServletResponse resp = getResponse();
		try {
			resp.setContentType("application/msexcel;charset=GBK");
			report = new JsGridReportBase(request, resp);
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td, agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据手机号去除重复乘客，LineClassOrderPassengerVo实体需要实现equals(),hashCode()方法
	 * 
	 * @param pList
	 * @return
	 */
	private List<LineClassOrderPassengerVo> removeRepeat(List<LineClassOrderPassengerVo> pList) {
		List<LineClassOrderPassengerVo> result = new ArrayList<LineClassOrderPassengerVo>();
		for (LineClassOrderPassengerVo t : pList) {
			if (!result.contains(t)) {
				result.add(t);
			}
		}
		return result;
	}

	@Action(value = "checkTelephone", results = { @Result(type = "json") })
	public String checkTelephone() throws IOException {
		String json = "error";
		String telephone = request.getParameter("passengerTel");
		json = passengerInfoService.checktelephone(telephone, "");
		if (json.isEmpty()) {
			json = "success";
		} else {
			json = "error";
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/** 判断线路是否有排班 **/
	@Action(value = "judgeLineClass", results = { @Result(type = "json") })
	public String judgeLineClass() throws IOException {
		String json = "1"; // 0:没有排班 1：有排班

		String lineBaseId = super.request.getParameter("lineBaseId");

		if (StringUtils.hasText(lineBaseId)) {
			json = lineService.judgeLineClass(lineBaseId);
		}

		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/** 把线路指定给新的商家 **/
	@Action(value = "updateLineToNewBusiness", results = { @Result(type = "json") })
	public String updateLineToNewBusiness() throws IOException {
		String json = "1"; // 0:没有排班 1：有排班
		String lineBaseId = super.request.getParameter("lineBaseId");
		String businessId = super.request.getParameter("businessId");

		if (StringUtils.hasText(lineBaseId)) {
			json = lineService.judgeLineClass(lineBaseId);
		}
		if (!StringUtils.hasText(businessId) || !StringUtils.hasText(lineBaseId)) {
			json = "2"; // 异常
		} else {
			/** 再次判断线路是否有排班 **/
			if ("0".equals(json)) {
				int statu = lineService.updateLineToNewBusiness(lineBaseId, businessId);
				if (statu >= 1) {
					json = "3"; // 执行成功
				} else {
					json = "2";
				}
			}
		}
		getResponse().setContentType("text/html;charset=UTF-8");
		getResponse().getWriter().print(json);
		return null;
	}

	/**
	 * 获得上次排班的最后一次工作日的司机和车辆
	 * 
	 * @param lineBaseId
	 * @return
	 */
	private AppVo_2 getDriverAndCar(String lineBaseId) {
		AppVo_2 vo_2 = lineService.getDriverAndCar(lineBaseId);
		return vo_2;
	}

	/**
	 * 获取线路上下车点信息
	 * 
	 * @return
	 */
	@Action(value = "getLineMsg", results = { @Result(type = "json") })
	public String getLineMsg() {
		String lineBaseId = request.getParameter("lineBaseId");
		String json = "error";
		if (!StringUtils.hasText(lineBaseId)) {
			throw new RuntimeException("lineBaseId is null");
		}
		List<StationInfo> lineStationVos = publishLineService.getStationListById(lineBaseId);
		List<String> stationList = new ArrayList<String>();
		// 拼接上车点
		stationList.add("上车点:");
		for (StationInfo vo : lineStationVos) {
			if (vo.getType() == 1) {
				stationList.add(vo.getName());
			}
		}
		// 拼接下车点
		stationList.add("下车点:");
		for (StationInfo vo : lineStationVos) {
			if (vo.getType() == 0) {
				stationList.add(vo.getName());
			}
		}
		JsonWriter.write(stationList);
		return null;
	}

	/**
	 * 线路操作记录
	 * 
	 * @return
	 */
	@Action(value = "lineLog", results = {
			@Result(name = "success", location = "../../view/line/operationLineLog.jsp") })
	public String lineLog() {
		int currentPageIndex = Integer.valueOf(request.getParameter("p") == null ? "0" : request.getParameter("p"));
		int flag = 0;
		String lineBaseId = request.getParameter("lineBaseId");
		// 分页时,从session里取出lineBaseId
		if (lineBaseId != null) {
			super.session.put("logLineBaseId", lineBaseId);
		} else {
			lineBaseId = (String) super.session.get("logLineBaseId");
		}
		if (lineBaseId != null && !"".equals(lineBaseId)) {
			map = lineService.getLineLog(lineBaseId, currentPageIndex, pageSize);
			list = (List) map.get("list");
			page = (Page) map.get("page");
		}
		return SUCCESS;
	}

	/**
	 * 获取修改发车线路信息
	 * 
	 * @return 车辆的经纬度坐标信息；
	 * @throws IOException
	 */
	@Action(value = "getLineGps", results = { @Result(type = "json") })
	public String getLineGps() throws IOException {
		String lineId = request.getParameter("lineId");
		String vehicle = request.getParameter("vehicle");
		try {
			StringBuffer url = new StringBuffer();
			url.append(PropertyManage.getInfoProperty("app_back_root")+"app_GPS/getBusPosition.action");
			Map<String, String> postParam = new HashMap<String, String>();
			postParam.put("lineBaseId", lineId);
			postParam.put("No", vehicle);
			String result = PostHttpClient.post(url.toString(), postParam);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询定制线路车辆信息
	 * 
	 * @return
	 */
	@Action(value = "getDefineCarsMsg", results = {
			@Result(name = "success", location = "../../view/line/defineCars.jsp") })
	public String getDefineCarsMsg() {
		List<LineCarMsgVo> list = lineService.getDefineCarMsg();
		request.setAttribute("cars", list);
		return SUCCESS;
	}

	/**
	 * 导出搜索的线路
	 */
	@Action(value = "toExport")
	public void toExport() {
		getResponse().setContentType("application/msexcel;charset=GBK");
		Object lineSearch = super.session.get("lineSearch");
		if (lineSearch instanceof Search) {
			this.search = (Search) lineSearch;
		}
		map = lineService.getAllLines(search, 0, Integer.MAX_VALUE);
		String title = "线路信息列表";
		String[] hearders = new String[] { "所属地区", "供应商", "线路类型", "线路名称", "发车时间", "剩余购票天数", "起点", "终点", "通票价格", "归属人",
				"发布状态" };
		String[] fields = new String[] { "cityName", "brefName", "lineSubType_str", "lineName", "stime",
				"availableBuyDays", "startStationName", "endStationName", "orderPrice", "userName", "lineStatus_str" };
		List<LineBusinessVo> lineList = (List<LineBusinessVo>) map.get("list");
		for (LineBusinessVo vo : lineList) {
			if (vo.getLineSubType() == 0) {
				vo.setLineSubType_str("上下班");
			} else {
				vo.setLineSubType_str("旅游");
			}
			if (vo.getLineStatus() == 0) {
				vo.setLineStatus_str("待调度");
			} else if (vo.getLineStatus() == 1) {
				vo.setLineStatus_str("调度中");
			} else if (vo.getLineStatus() == 2) {
				vo.setLineStatus_str("待发布");
			} else if (vo.getLineStatus() == 3) {
				vo.setLineStatus_str("已发布");
			} else {
				vo.setLineStatus_str("已下线");
			}
		}
		TableData td = ExcelUtils.createTableData(lineList, ExcelUtils.createTableHeader(hearders), fields);
		JsGridReportBase report;
		try {
			report = new JsGridReportBase(request, getResponse());
			String agent = request.getHeader("User-Agent");
			report.exportToExcel(title, td, agent);

		} catch (Exception e) {
			e.printStackTrace();
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

	public UserLineEntity getUserLineEntity() {
		return userLineEntity;
	}

	public void setUserLineEntity(UserLineEntity userLineEntity) {
		this.userLineEntity = userLineEntity;
	}

	public StationInfo getStationEntity() {
		return stationInfo;
	}

	public void setStationEntity(StationInfo stationEntity) {
		this.stationInfo = stationEntity;
	}

	public RecruitLineStationVo getRecruitLineStationVo() {
		return recruitLineStationVo;
	}

	public void setRecruitLineStationVo(RecruitLineStationVo recruitLineStationVo) {
		this.recruitLineStationVo = recruitLineStationVo;
	}

	public List<String> getPicUrlList() {
		return picUrlList;
	}

	public void setPicUrlList(List<String> picUrlList) {
		this.picUrlList = picUrlList;
	}

	public LineScheduleInfoVo getLineScheduleInfo() {
		return lineScheduleInfo;
	}

	public void setLineScheduleInfo(LineScheduleInfoVo lineScheduleInfo) {
		this.lineScheduleInfo = lineScheduleInfo;
	}

	public LineDetailVo getLineDetailVo() {
		return lineDetailVo;
	}

	public void setLineDetailVo(LineDetailVo lineDetailVo) {
		this.lineDetailVo = lineDetailVo;
	}

	public String getStartYearAndMonth() {
		return startYearAndMonth;
	}

	public void setStartYearAndMonth(String startYearAndMonth) {
		this.startYearAndMonth = startYearAndMonth;
	}

	public String getEndYearAndMonth() {
		return endYearAndMonth;
	}

	public void setEndYearAndMonth(String endYearAndMonth) {
		this.endYearAndMonth = endYearAndMonth;
	}

	public List<SysArea> getProSysAreas() {
		return proSysAreas;
	}

	public void setProSysAreas(List<SysArea> proSysAreas) {
		this.proSysAreas = proSysAreas;
	}

	public LineCityVo getLineCityVo() {
		return lineCityVo;
	}

	public void setLineCityVo(LineCityVo lineCityVo) {
		this.lineCityVo = lineCityVo;
	}

	public LineAllowanceVo getAllowanceVo() {
		return allowanceVo;
	}

	public void setAllowanceVo(LineAllowanceVo allowanceVo) {
		this.allowanceVo = allowanceVo;
	}

	public LineAllowanceDetailVo getLineAllowanceDetailVo() {
		return lineAllowanceDetailVo;
	}

	public void setLineAllowanceDetailVo(LineAllowanceDetailVo lineAllowanceDetailVo) {
		this.lineAllowanceDetailVo = lineAllowanceDetailVo;
	}

	public LineEntity getLineEntity() {
		return lineEntity;
	}

	public void setLineEntity(LineEntity lineEntity) {
		this.lineEntity = lineEntity;
	}

}
