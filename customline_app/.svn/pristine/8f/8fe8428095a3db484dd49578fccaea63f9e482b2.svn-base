package com.pig84.ab.action;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Refund;
import com.paypal.api.payments.Sale;
import com.paypal.base.ConfigManager;
import com.paypal.base.Constants;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import com.pig84.ab.cache.UserCache;
import com.pig84.ab.dao.impl.RePeat;
import com.pig84.ab.service.IBookService;
import com.pig84.ab.service.ICouponService;
import com.pig84.ab.service.IDriverService;
import com.pig84.ab.service.IRecommendAwardService;
import com.pig84.ab.service.IWeiXinPayService;
import com.pig84.ab.utils.CacheUtil;
import com.pig84.ab.utils.Event;
import com.pig84.ab.utils.Html;
import com.pig84.ab.utils.Json;
import com.pig84.ab.utils.Message;
import com.pig84.ab.utils.MyDate;
import com.pig84.ab.utils.MyDate.Format;
import com.pig84.ab.vo.ApplyReturn;
import com.pig84.ab.vo.LeaseBaseInfo;
import com.pig84.ab.vo.PassengerComments;
import com.pig84.ab.vo.SeatInfoTemp;
import com.pig84.ab.vo.User;
import com.pig84.ab.vo.bean.AppVo_1;
import com.pig84.ab.vo.bean.AppVo_10;
import com.pig84.ab.vo.bean.AppVo_10_list;
import com.pig84.ab.vo.bean.AppVo_15_list;
import com.pig84.ab.vo.bean.AppVo_18;
import com.pig84.ab.vo.bean.AppVo_1_list;
import com.pig84.ab.vo.bean.AppVo_2;
import com.pig84.ab.vo.bean.AppVo_25;
import com.pig84.ab.vo.bean.AppVo_2_list;
import com.pig84.ab.vo.bean.AppVo_3;
import com.pig84.ab.vo.bean.AppVo_3_list;
import com.pig84.ab.vo.bean.AppVo_4;
import com.pig84.ab.vo.bean.AppVo_5;
import com.pig84.ab.vo.bean.AppVo_6;
import com.pig84.ab.vo.bean.AppVo_7;

/**
 * 预定相关
 * 
 * @author zhangqiang
 * 
 */
@ParentPackage("no-interceptor")
@Namespace("/app_book")
@SuppressWarnings("all")
@ExceptionMappings({ @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })
public class BookAction extends BaseAction {

	@Autowired
	private IBookService bookService;

	@Autowired
	private ICouponService giftService;

	@Autowired
	private IDriverService driverService;

	@Autowired
	private IWeiXinPayService weiXinPayService;


	/**
	 * 小猪巴士2.0评价接口
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 */
	@Action(value = "orderCommentsV2", results = { @Result(type = "json") })
	public void orderCommentsV2() {
		AppVo_1 vo = new AppVo_1();
		try {
			// 订单号
			String orderNo = request.getParameter("orderNo");
			if (StringUtils.isEmpty(orderNo)) {
				throw new IllegalArgumentException("orderNo");
			}
			// 星级评分
			String starPoint = request.getParameter("starPoint");
			if (StringUtils.isEmpty(starPoint)) {
				throw new IllegalArgumentException("starPoint");
			}
			int starPointValue = Integer.parseInt(starPoint);
			if (starPointValue < 1 || starPointValue > 5) {
				throw new IllegalArgumentException("starPoint");
			}
			// 多选评价
			String advinces = request.getParameter("advinces");
			if (!StringUtils.isEmpty(advinces)) {
				String[] advinceArr = advinces.split(",");
				if (advinceArr.length > 3) {
					throw new IllegalArgumentException("advinces");
				}
			}
			// 评论类容
			String context = request.getParameter("context");
			context = filterErrorChar(context);
			if (!StringUtils.isEmpty(context)) {
				context = Html.purge(context);
			}
			// 班次Id
			String lineClassId = request.getParameter("lineClassId");

			// 当前登录用户信息
			User appUser = UserCache.getUser();
			if (appUser != null) {
				// 用户ID
				String userid = appUser.getPassengerId();
				// 1：订单不存在 2：成功 3-星级评分参数错误 4-多选评价超过最大可选数 5：其他失败 6-已评论过 7-班次Id不正确
				PassengerComments pc = new PassengerComments();
				pc.setAdvinces(advinces);
				pc.setCommentContext(context);
				pc.setCommentStatus("0");
				pc.setCommentTime(MyDate.Format.DATETIME.now());
				pc.setPassengerId(userid);
				pc.setStarPoint(starPointValue);
				pc.setLineClassId(lineClassId);
				String flag = bookService.orderCommentsV2(orderNo, pc);
				vo.setA1(flag);
			} else {
				// 未登录
				vo.setA1("0");
			}
		} catch (IllegalArgumentException pe) {
			if (pe.getMessage().equals("orderNo")) {
				vo.setA1("1");
			}
			if (pe.getMessage().equals("starPoint")) {
				vo.setA1("3");
			}
			if (pe.getMessage().equals("advinces")) {
				vo.setA1("4");
			}
			if (pe.getMessage().equals("lineClassId")) {
				vo.setA1("7");
			}
		} catch (Exception e) {
			e.printStackTrace();
			vo.setA1("5");
		}
		write(vo);
	}

	private String filterErrorChar(String orgnal) {
		byte[] b_text = orgnal.getBytes();
		for (int i = 0; i < b_text.length; i++) {
			if ((b_text[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					b_text[i + j] = 0x30;
				}
				i += 3;
			}
		}
		return new String(b_text);
	}

	/**
	 * 小猪巴士2.0评价接口
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 */
	@Action(value = "getAdvincesOptionList", results = { @Result(type = "json") })
	public void getAdvincesOptionList() {
		AppVo_1_list vo = new AppVo_1_list();
		try {
			vo.setA1("0");// 成功
			List<String> list = new ArrayList<String>();
			Random r = new Random();
			int index = r.nextInt(3);
			if (index == 0) {
				list.add("司机服务态度差");
				list.add("司机迟到");
				list.add("车辆脏乱差");
				list.add("堵车 建议优化线路");
				list.add("司机 车辆不固定");
				list.add("车辆出现故障");
				list.add("无法定位车辆位置");
			} else if (index == 1) {
				list.add("未按规定站点停靠");
				list.add("司机服务态度差");
				list.add("车辆脏乱差");
				list.add("堵车 建议优化线路");
				list.add("司机 车辆不固定");
				list.add("车辆出现故障");
				list.add("无法定位车辆位置");
			} else if (index == 2) {
				list.add("未按规定站点停靠");
				list.add("司机服务态度差");
				list.add("司机迟到");
				list.add("车辆脏乱差");
				list.add("司机 车辆不固定");
				list.add("车辆出现故障");
				list.add("无法定位车辆位置");
			} else if (index == 3) {
				list.add("未按规定站点停靠");
				list.add("司机服务态度差");
				list.add("司机迟到");
				list.add("车辆脏乱差");
				list.add("堵车 建议优化线路");
				list.add("司机 车辆不固定");
				list.add("无法定位车辆位置");
			} else {
				list.add("未按规定站点停靠");
				list.add("司机服务态度差");
				list.add("司机迟到");
				list.add("车辆脏乱差");
				list.add("堵车 建议优化线路");
				list.add("司机 车辆不固定");
				list.add("车辆出现故障");
			}
			list.add("其他");
			vo.setList(list);
		} catch (Exception e) {
			logger.error("Get advinces option list failed.", e);
			vo.setA1("1");// 失败
		}
		write(vo);
	}

	/**
	 * 取消订单
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "cancelOrder", results = { @Result(type = "json") })
	public String cancelOrder() {
		String orderNo = request.getParameter("orderNo");// 订单号

		String flag = bookService.cancelOrder(orderNo);

		AppVo_1 vo = new AppVo_1();
		vo.setA1(flag);// 1：成功 其他：失败
		write(vo);
		return null;
	}

	/**
	 * 电子票详情（按次购买）
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "ticketInfoByDays", results = { @Result(type = "json") })
	public String ticketInfoByDays() {
		String orderNo = request.getParameter("orderNo");// 订单号

		AppVo_10_list orderinfo = bookService.ticketInfoByDays(orderNo); // a1订单状态
		// 0:订单不存在
		// 1：非按次订单
		// 2：待支付
		// 3：交易成功
		// 4：已失效
		// 5：已取消
		// 6：已评价
		// 7：待评价

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		AppVo_15_list vl15 = new AppVo_15_list();
		if (orderinfo != null) {
			orderinfo.setA7(MyDate.Format.DATETIME.now());// 当前系统时间
			vl15.setA1(orderinfo.getA1());
			vl15.setA2(orderinfo.getA2());
			vl15.setA3(orderinfo.getA3());
			vl15.setA4(orderinfo.getA4());
			vl15.setA5(orderinfo.getA5());
			vl15.setA6(orderinfo.getA6());
			vl15.setA7(orderinfo.getA7());
			vl15.setA8(orderinfo.getA8());
			vl15.setA9(orderinfo.getA9());
			vl15.setA10(orderinfo.getA10());
			vl15.setList(orderinfo.getList());

			AppVo_2 vo2 = driverService.getOrderDetailId(orderNo);
			if (null != vo2) {
				vl15.setA11(vo2.getA1());// 订单明细id
				vl15.setA12(vo2.getA2());// 司机im用户id
			}

		}
		write(vl15);
		return null;
	}

	/**
	 * 支付详情
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "payInfo", results = { @Result(type = "json") })
	public String payInfo() {

		String payType = request.getParameter("payType");// 支付类别（1.表示包车支付，空表示小猪巴士支付）
		String orderNo = request.getParameter("orderNo");// 订单号
		String counponTeleId = request.getParameter("counponTeleId");// 用户优惠券id
		String userid = "";// 用户ID
		User appUser = UserCache.getUser();// 当前登录用户信息
		AppVo_6 vo = new AppVo_6();
		if (appUser != null) {
			vo.setA1("1");// 已登录
			userid = appUser.getPassengerId();
			String money = bookService.getUserBalance(userid);// 用户余额

			String orderPrice = "0.00";// 订单价格
			if ("1".equals(payType)) {
				// 根据报价id查询报价总价
				AppVo_4 v_3 = bookService.getBcBiddingById(orderNo);
				orderPrice = v_3.getA3();

				vo.setA2(orderPrice);// 订单价格
				vo.setA3(money);// 用户余额

				if (null != counponTeleId && !"".equals(counponTeleId)) {
					vo.setA4(counponTeleId);

					// 获取优惠券面值
					AppVo_1 vo_1 = giftService.getCouponValueById(counponTeleId);

					vo.setA5(vo_1.getA1());

					BigDecimal leftPrice = new BigDecimal(orderPrice).subtract(new BigDecimal(vo_1.getA1()));

					orderPrice = leftPrice.toString();
				}

				vo.setA6(orderPrice);
			} else {
				// 根据订单编号查询订单价格
				orderPrice = bookService.getOrderPrice(orderNo);
				vo.setA2(orderPrice);// 订单价格
				vo.setA3(money);// 用户余额
				AppVo_2 vo2 = bookService.getOrderGifMoney(orderNo);
				vo.setA5(vo2.getA1());
				vo.setA6(vo2.getA2());
			}
		} else {
			vo.setA1("0");// 未登录
		}
		write(vo);
		return null;
	}

	/**
	 * 获取用户余额
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getUserBalance", results = { @Result(type = "json") })
	public String getUserBalance() {
		User appUser = UserCache.getUser();// 当前登录用户信息
		String userid = "";
		AppVo_4 vo = new AppVo_4();
		if (appUser != null) {
			vo.setA1("1");
			userid = appUser.getPassengerId();
			String money = bookService.getUserBalance(userid);// 余额
			String msg = bookService.getUnReadMsgCount(userid);// 未读消息条数
			String order = bookService.getUnPayOrderCount(userid);// 未支付订单条数

			if (!"".equals(money) && money != null) {
				vo.setA2(money);
			} else {
				vo.setA2("0");
			}

			if (!"".equals(msg) && msg != null) {
				vo.setA3(msg);
			} else {
				vo.setA3("0");
			}

			if (!"".equals(order) && order != null) {
				vo.setA4(order);
			} else {
				vo.setA4("0");
			}

		} else {
			vo.setA1("0");// 未登录
			vo.setA2("0");
			vo.setA3("0");
			vo.setA4("0");
		}
		write(vo);
		return null;
	}

	/**
	 * 评论列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "commentList", results = { @Result(type = "json") })
	public String commentList() {
		String pageSize_ = request.getParameter("pageSize");// 每页显示条数
		String currentPage_ = request.getParameter("currentPage");// 当前页数
		String lineBaseId = request.getParameter("lineBaseId");// 线路ID

		int currentPage = 0;
		int pageSize = 5;

		if (currentPage_ != null && !currentPage_.equals("")) {
			currentPage = Integer.valueOf(currentPage_);
		}

		if (pageSize_ != null && !pageSize_.equals("")) {
			pageSize = Integer.valueOf(pageSize_);
		}
		List<AppVo_6> list = bookService.commentList(currentPage, pageSize, lineBaseId);
		write(list);
		return null;
	}

	/**
	 * 微信分享
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "shareByWx", results = { @Result(name = "success", location = "/shareByWx.jsp") })
	public String shareByWx() {

		String lineBaseId = request.getParameter("lineBaseId");
		AppVo_15_list vo = bookService.LineAndClassInfo(lineBaseId);
		if (vo != null) {
			request.setAttribute("sstation", vo.getA1());
			request.setAttribute("estation", vo.getA2());
			request.setAttribute("station", vo.getA4().replaceAll("-", "<br/>"));
			request.setAttribute("price", vo.getA6());
			if (vo.getList() != null && vo.getList().size() != 0) {
				List<AppVo_1> list = vo.getList();
				String str = "";
				for (int i = 0; i < list.size(); i++) {
					str += "" + list.get(i).getA1() + " ";
				}
				request.setAttribute("times", str);
			}
		}

		return SUCCESS;
	}

	/**
	 * 其他分享
	 * 
	 * @return
	 */
	@Action(value = "shareOther", results = { @Result(name = "success", location = "/shareOther.jsp") })
	public String shareOther() {

		String lineBaseId = request.getParameter("lineBaseId");
		AppVo_15_list vo = bookService.LineAndClassInfo(lineBaseId);
		if (vo != null) {
			request.setAttribute("sstation", vo.getA1());
			request.setAttribute("estation", vo.getA2());
			request.setAttribute("station", vo.getA4().replaceAll("-", "<br/>"));
			request.setAttribute("price", vo.getA6());
			if (vo.getList() != null && vo.getList().size() != 0) {
				List<AppVo_1> list = vo.getList();
				String str = "";
				for (int i = 0; i < list.size(); i++) {
					str += "" + list.get(i).getA1() + " ";
				}
				request.setAttribute("times", str);
			}
		}

		return SUCCESS;
	}

	/**
	 * 可用优惠卷列表
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getMyGif", results = { @Result(type = "json") })
	public String getMyGif() {

		String orderPrice = request.getParameter("orderPrice");// 订单价格
		String pageSize_ = request.getParameter("pageSize");// 每页显示条数
		String currentPage_ = request.getParameter("currentPage");// 当前页数
		String type = request.getParameter("type");// 类型 （1：上下班 2：包车）

		String uid = request.getParameter("uid");// 微信端传过来的用户id

		int currentPage = 0;
		int pageSize = 5;

		if (currentPage_ != null && !currentPage_.equals("")) {
			currentPage = Integer.valueOf(currentPage_);
		}

		if (pageSize_ != null && !pageSize_.equals("")) {
			pageSize = Integer.valueOf(pageSize_);
		}

		String userid = "";
		if (StringUtils.isNotBlank(uid)) {
			userid = uid;
		} else {
			User appUser = UserCache.getUser();// 当前登录用户信息
			if (appUser != null) {
				userid = appUser.getPassengerId();
			}
		}

		List<AppVo_10> list = bookService.getMyGif(orderPrice, userid, currentPage, pageSize, type);
		write(list);
		return null;
	}

	/**
	 * 可改签列表
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	@Action(value = "changeList", results = { @Result(type = "json") })
	public String changeList() {
		AppVo_1_list vo = new AppVo_1_list();
		String orderNo = request.getParameter("orderNo");// 订单号
		User appUser = UserCache.getUser();// 当前登录用户信息
		String userid = "";
		if (appUser != null) {
			userid = appUser.getPassengerId();
			List<AppVo_6> list = bookService.changeList(orderNo, userid);
			if (list == null || list.size() == 0) {
				vo.setA1("1");// 数据有误
			} else {
				vo.setA1("0");// 正常
				vo.setList(list);
			}
		} else {
			vo.setA1("2");// 未登录
		}
		write(vo);
		return null;
	}

	/**
	 * 改签日期列表
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "changeDateList", results = { @Result(type = "json") })
	public String changeDateList() {
		String orderNo = request.getParameter("orderNo");// 订单号
		String pageSize_ = request.getParameter("pageSize");// 每页显示条数
		String currentPage_ = request.getParameter("currentPage");// 当前页数
		int currentPage = 0;
		int pageSize = 9;

		if (currentPage_ != null && !currentPage_.equals("")) {
			currentPage = Integer.valueOf(currentPage_);
		}
		if (pageSize_ != null && !pageSize_.equals("")) {
			pageSize = Integer.valueOf(pageSize_);
		}
		List<AppVo_6> list = bookService.changeDateList(orderNo, currentPage, pageSize); // a1:班次ID
																							// a2:发车日期
																							// a3:周几
		AppVo_6 vo = bookService.getLineInfo(orderNo);
		AppVo_2_list result = new AppVo_2_list();
		if (vo != null) {
			String t[] = vo.getA1().split("-");
			result.setA1(t[0] + "-" + t[t.length - 1]);// 线路名称
			result.setA2(vo.getA2());// 发车时间
		}
		if (list != null && list.size() != 0) {
			result.setList(list);
		}
		write(result);
		return null;
	}

	/**
	 * 新版改签(APP前端直接改签,不需要通过平台审批)
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "newChangeTicket", results = { @Result(type = "json") })
	public String newChangeTicket() {
		AppVo_2 vo = new AppVo_2();
		String orderNo = request.getParameter("orderNo");// 订单号
		String cids = request.getParameter("cids");// 样式 "主键@新班次ID,主键@新班次ID"
		if (cids != null && !"".equals(cids)) {
			String result = bookService.changeTicket(orderNo, cids);
			if ("1".equals(result)) {
				vo.setA1("1");// 成功
				// 返回新版次的ID给APP
				String newclds = "";
				String[] newClids = cids.split(",");
				String loCid = null;
				for (int i = 0; i < newClids.length; i++) {
					loCid = newClids[0];
					if (!StringUtils.isEmpty(loCid)) {
						newclds += loCid.split("@")[1] + ",";
					}
				}
				if (!StringUtils.isEmpty(newclds)) {
					newclds = newclds.substring(0, newclds.length() - 1);
					vo.setA2(newclds);
				}
			} else {
				vo.setA1(result);// 失败
			}
		}
		write(vo);
		return null;

	}

	/**
	 * 计算退票的相关数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "returnTicketCount", results = { @Result(type = "json") })
	public String returnTicketCount() {
		User appUser = UserCache.getUser();// 当前登录用户信息
		AppVo_5 vo = new AppVo_5();
		if (null != appUser && !StringUtils.isEmpty(appUser.getPassengerId())) {
			String price = request.getParameter("price");// 金额eg:1.0,2.0,3.0
			Integer percent = bookService.queryReturnPercent();// 获取退票的手续费的百分比:eg:10(百分之10)
			vo = RePeat.countTickertMoney(price, percent);
			vo.setA4(appUser.getPassengerId());
			CacheUtil.setCache("returnTicketCount_vo" + appUser.getPassengerId(), Json.toJson(vo), 1800);
		} else {
			vo.setA1("-1");// 没有登录
		}
		write(vo);
		return null;
	}

	/**
	 * 执行退票
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "returnTicket", results = { @Result(type = "json") })
	public String returnTicket() {
		CacheUtil.getCache("returnTicketCount_vo140930131719787189");
		String version = request.getParameter("version");// version(空:旧版本 1：新版本)

		// 线路_乘客班次表 的localIds eg:1222222,5555555
		String localIds = request.getParameter("localIds");
		// 班次ID eg:100000,2000000
		String lineClassIds = request.getParameter("lineClassIds");
		// 订单号
		String orderNo = request.getParameter("orderNo");

		User appUser = UserCache.getUser();// 当前登录用户信息
		String userid = "";
		int statu = 0;
		AppVo_1 vo = new AppVo_1();

		String payType = "0";
		try {
			if (appUser != null && !StringUtils.isEmpty(appUser.getPassengerId())) {

				// 检查是否已经申请过退票---如果申请了,则不再重复申请
				int count = bookService.qeryReturnTicket(lineClassIds, orderNo, appUser.getPassengerId());
				if (count >= 1) {
					vo.setA1("1");
					write(vo);
					return null;
				}

				String temp = CacheUtil.getCache("returnTicketCount_vo" + appUser.getPassengerId());
				AppVo_5 vo_4 = Json.fromJson(temp, AppVo_5.class);
				// 实际退款金额
				if (null == vo_4 || StringUtils.isEmpty(vo_4.getA3())) {
					statu = 0;// 异常
				} else {

					// 核对一下金额是否正确
					// 查找原实际金额
					String price = bookService.queryPriceByLocalIds(localIds);
					if (null == price) {
						vo.setA1("1");
						write(vo);
						return null;
					}
					Integer percent = bookService.queryReturnPercent();// 获取退票的手续费的百分比:eg:10(百分之10)
					AppVo_5 v5 = RePeat.countTickertMoney(price, percent);
					if (!v5.getA3().equals(vo_4.getA3())) {
						vo.setA1("1");
						write(vo);
						return null;
					}
					// 获取订单的支付方式 支付方式 0：无 1：余额支付，2：财付通 3：银联 4：支付宝 5:微信
					// 6:(APP)微信
					payType = bookService.queryLeasePayType(orderNo);

					BigDecimal realBack = new BigDecimal(vo_4.getA3());
					// 退款金额大于0元
					if (realBack.compareTo(new BigDecimal("0.00")) > 0) {
						// 微信支付--(APP)微信
						if ("6".equals(payType)) {
							// 新版本的微信支付可以直接退款
							if (null != version && "1".equals(version)) {
								// 6:(APP)微信--支持微信退款
								HttpServletResponse response = ServletActionContext.getResponse();
								// 检查该退款记录是否存在
								boolean flag = bookService.checkReturnCount(localIds, lineClassIds, orderNo,
										appUser.getPassengerId(), vo_4.getA3());
								if (flag) {
									statu = weiXinPayService.returnFromWX(orderNo, vo_4.getA3(), request, response);
								}
								if (statu == 1) {
									// 添加退款记录信息
									bookService.addReturnMoneyCount(localIds, lineClassIds, orderNo,
											appUser.getPassengerId(), vo_4.getA3());
									
								}
							}
						} else if ("7".equals(payType)) {
							boolean flag = bookService.checkReturnCount(localIds, lineClassIds, orderNo,
									appUser.getPassengerId(), vo_4.getA3());
							boolean result = false;
							if (flag) {
								result = payPalReturn(orderNo, vo_4.getA3());
								// 检查该退款记录是否存在
							}
							if (result) {
								statu = 1;
								// 添加退款记录信息
								bookService.addReturnMoneyCount(localIds, lineClassIds, orderNo,
										appUser.getPassengerId(), vo_4.getA3());
							}
						}
					}
					if (statu != 2) {
						if (realBack.compareTo(new BigDecimal("0.00")) <= 0
								|| !("6".equals(payType) || "7".equals(payType)) || StringUtils.isEmpty(version)) {
							statu = 1;
						}
					}
					if (statu == 1) {
						// 退票
						statu = bookService.returnTicket(localIds, lineClassIds, orderNo, vo_4, payType, version);
					}
					if (statu == 2) {
						// 订单号不存在,说明是旧版本购票,新版本申请退票
						statu = bookService.returnTicket(localIds, lineClassIds, orderNo, vo_4, payType, null);
					}
					if (statu == 1 || statu == 2) {
						// 发送短信
						new Message("【小猪巴士】退款提醒：您申请的%s元退款，近期将退回至您的付款账户，请注意查收。", vo_4.getA3())
								.sendTo(appUser.getTelephone());
						CacheUtil.removeCache("returnTicketCount_vo" + appUser.getPassengerId());
					}
				}
			} else {
				statu = 2;// 没有登录
			}
		} catch (Exception e) {
		}
		vo.setA1(String.valueOf(statu));
		write(vo);
		return null;
	}

	private boolean payPalReturn(String orderId, String price) {
		InputStream is = BookAction.class.getClassLoader().getResourceAsStream("sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			return false;
		}
		LeaseBaseInfo info = bookService.getLeaseInfoByOrderId(orderId);
		String txtId = info.getThirdparty();
		Sale sale = new Sale();
		sale.setId(txtId);
		Refund refund = new Refund();
		Amount amount = new Amount();
		amount.setCurrency("SGD");
		amount.setTotal(price);
		refund.setAmount(amount);
		try {
			String accessToken = getAccessToken();
			APIContext apiContext = new APIContext(accessToken);
			sale.refund(apiContext, refund);
			String request = Sale.getLastRequest();
			String response = Sale.getLastResponse();
		} catch (PayPalRESTException e) {
			return false;
		}
		return true;
	}

	/**
	 * 申请退票
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "applyReturnTicket", results = { @Result(type = "json") })
	public String applyReturnTicket() {
		String localIds = request.getParameter("localIds");// 线路_乘客班次表 的localIds
		// eg:100,2000,300
		String lineClassIds = request.getParameter("lineClassIds");// 班次ID
		// eg:2000,5000,300
		String leaseOrderNo = request.getParameter("leaseOrderNo");// 订单号
		String oldReturn = request.getParameter("oldReturn");// 原退票的总价
		String percent = request.getParameter("percent");// 手续费百分比
		String realReturn = request.getParameter("realReturn");// 实际退票金额
		String realPoundage = request.getParameter("realPoundage");// 手续费

		AppVo_1 vo = new AppVo_1();
		User appUser = UserCache.getUser();// 当前登录用户信息
		if (null == appUser || StringUtils.isEmpty(appUser.getPassengerId())) {
			vo.setA1("0");// 没有登陆
		} else {
			if (StringUtils.isEmpty(localIds) || StringUtils.isEmpty(lineClassIds) || StringUtils.isEmpty(leaseOrderNo)
					|| StringUtils.isEmpty(oldReturn) || StringUtils.isEmpty(percent) || StringUtils.isEmpty(realReturn)
					|| StringUtils.isEmpty(realPoundage)) {
				vo.setA1("-1");// 申请失败
			} else {
				ApplyReturn applyReturn = new ApplyReturn();
				applyReturn.setLocalIds(localIds);
				applyReturn.setLineClassIds(lineClassIds);
				applyReturn.setLeaseOrderNo(leaseOrderNo);
				applyReturn.setOldReturn(oldReturn);
				applyReturn.setPercent(percent);
				applyReturn.setRealReturn(realReturn);
				applyReturn.setRealPoundage(realPoundage);
				applyReturn.setPassengerId(appUser.getPassengerId());
				applyReturn.setTelephone(appUser.getTelephone());

				int statu = bookService.applyReturnTicket(applyReturn);
				if (statu >= 0) {
					vo.setA1("1");// 申请成功
				} else {
					vo.setA1("-1");// 申请失败
				}
			}

		}
		write(vo);
		return null;
	}

	/**
	 * 微信退款
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "returnFromWX", results = { @Result(type = "json") })
	public String returnFromWX() throws IOException {
		String leaseOrderNo = request.getParameter("leaseOrderNo");// 订单号
		String realReturn = request.getParameter("realReturn");// 实际退票金额
		HttpServletResponse response = ServletActionContext.getResponse();
		AppVo_1 vo = new AppVo_1();
		int statu = weiXinPayService.returnFromWX(leaseOrderNo, realReturn, request, response);
		System.out.print(statu);
		vo.setA1("1");
		write(vo);
		return null;
	}

	/**
	 * 班车详细（V2.0）
	 * 
	 * @throws IOException
	 */
	@Action(value = "getLineAndClassInfo", results = { @Result(type = "json") })
	public String getLineAndClassInfo() {
		String lineBaseId = request.getParameter("lineBaseId");
		String slineId = request.getParameter("slineId");
		String passengerid = request.getParameter("uid");// 用户ID（微信参数）
		AppVo_15_list vo = new AppVo_15_list();
		User appUser = UserCache.getUser();// 当前登录用户信息
		if (appUser != null) {
			passengerid = appUser.getPassengerId();
		}

		// 未登录
		if (passengerid == null || "".equals(passengerid)) {
			vo.setA1("-1");
		}

		// /////////领取系统发放方式的优惠券///////////////////////////////////////////////////////
		// this.giftService.addSysGiftInfo(appUser.getPassengerId(),
		// appUser.getTelephone(),"1");
		// /////////领取系统发放方式的优惠券///////////////////////////////////////////////////////

		vo = bookService.getLineAndClassInfo(lineBaseId, slineId, passengerid);
		write(vo);
		return null;
	}

	/**
	 * 可用优惠卷列表(v2.0新接口)
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "getUsableGif", results = { @Result(type = "json") })
	public String getUsableGif() {
		String type = request.getParameter("type");// 类型 （1：上下班 2：包车）

		String uid = request.getParameter("uid");// 微信端传过来的用户id

		String userid = "";
		if (StringUtils.isNotBlank(uid)) {
			userid = uid;
		} else {
			User appUser = UserCache.getUser();// 当前登录用户信息
			if (appUser != null) {
				userid = appUser.getPassengerId();
			}
		}
		List<AppVo_10> list = bookService.getUsableGif(userid, type);
		write(list);
		return null;
	}

	/**
	 * 车票预定（按次购买V2.0）
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	@Action(value = "bookLineByDays_new", results = { @Result(type = "json") })
	public String bookLineByDays_new() {
		String lineBaseid = request.getParameter("lineBaseid"); // 线路ID
		String lineClassIds = request.getParameter("lineClassIds"); // 班次ID串
		String gifId = request.getParameter("gifId");// 优惠卷ID
		String lineSplitId = request.getParameter("lineSplitId");// 子线路ID
		String userid = "";
		String userType = "1";// 来源类型（1：app 2:微信）
		User appUser = UserCache.getUser();// 当前登录用户信息
		if (appUser != null) {
			userid = appUser.getPassengerId();
		} else {
			userid = request.getParameter("passengerId");
			userType = "2";
		}

		AppVo_3 vo = new AppVo_3();
		if (StringUtils.isEmpty(userid)) {
			vo.setA1("0"); // 用户未登录
		} else {
			String orderID = bookService.bookLineByDays_new(lineBaseid, lineSplitId, lineClassIds, userid, userType,gifId);
			// 1：票价低于一元钱的班次只能购买一次
			// 2:无效班次
			// 3：余座不足
			// 4:线路信息有误
			// 5:系统错误
			// 6:无效优惠券
			// 其他：订单NO
			if (orderID.length() > 1) {
				boolean flag = bookService.checkOrder(orderID.replace("#0", ""));// 检查创建订单流程是否出错
				if (flag == false) {
					orderID = "5";
				}
			}
			vo.setA1("1");
			vo.setA2(orderID.replace("#0", ""));
			if (!orderID.equals(orderID.replace("#0", ""))) {
				vo.setA3("0");// 无需支付

				int result = driverService.addImGroupUser(orderID.replace("#0", ""));
				if (result == 1) {
					logger.info("0元支付成功将乘客放入群组");
				}

				Event.BUYTICKET.triggerAsynchronous("userId", userid);
			} else {
				vo.setA3("1");// 需要支付
			}
		}
		write(vo);
		return null;
	}

	/**
	 * 获取订单列表（V2.1）
	 * 
	 * @return
	 */
	@Action(value = "getOrderListByV2_1", results = { @Result(type = "json") })
	public String getOrderListByV2_1() {
		User appUser = UserCache.getUser();// 当前登录用户信息
		String orderType = request.getParameter("orderType");
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		String userid = "";
		AppVo_1_list vo = new AppVo_1_list();
		if (appUser != null) {
			userid = appUser.getPassengerId();
			List<AppVo_18> list = bookService.getOrderListByV2_1(userid, orderType, currentPage, pageSize);
			vo.setA1("1");// 已经登录
			vo.setList(list);
		} else {
			vo.setA1("0");// 未登录
		}
		write(vo);
		return null;
	}

	/**
	 * 获取订单详细（V2.1）
	 * 
	 * @return
	 */
	@Action(value = "getOrderInfoByV2_1", results = { @Result(type = "json") })
	public String getOrderInfoByV2_1() {
		String orderNo = request.getParameter("orderNo");
		AppVo_15_list vo = bookService.getOrderInfoByV2_1(orderNo);
		write(vo);
		return null;
	}

	/**
	 * 电子票列表v2.3
	 * 
	 * @return
	 */
	@Action(value = "getTicktListByV2_3", results = { @Result(type = "json") })
	public String getTicktListByV2_3() {
		String userId = "";
		String type = request.getParameter("type");// 类型 1：待乘车 2：过往
		String currentPage = request.getParameter("currentPage");
		String pageSize = request.getParameter("pageSize");
		AppVo_1_list vo = new AppVo_1_list();
		User appUser = UserCache.getUser();// 当前登录用户信息
		LoggerFactory.getLogger(BookAction.class).info("#########################################################");
		if (appUser != null) {
				userId = appUser.getPassengerId();
			List<AppVo_25> list = bookService.getTicktListByV2_3(userId, type, currentPage, pageSize);
			vo.setList(list);
			vo.setA1("1");// 已经登录
			vo.setList(list);
		} else {
			vo.setA1("0");// 未登录
		}
		write(vo);
		return null;
	}
	
	/**
	 * 订单详细（按次订单）
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "orderInfoByDays", results = { @Result(type = "json") })
	public String orderInfoByDays() {
		String orderNo = request.getParameter("orderNo");// 订单号
		AppVo_15_list orderinfo = bookService.orderInfoByDays(orderNo); // a1订单状态
		// 0:订单不存在
		// 1：非按次订单
		// 2：待支付
		// 3：交易成功
		// 4：已失效
		// 5：已取消
		// 6：已评价
		// 7：待评价
		write(orderinfo);
		return null;
	}

	private static String getAccessToken() throws PayPalRESTException {
		String clientID = ConfigManager.getInstance().getValue(Constants.CLIENT_ID);
		String clientSecret = ConfigManager.getInstance().getValue(Constants.CLIENT_SECRET);
		return new OAuthTokenCredential(clientID, clientSecret).getAccessToken();
	}

}
