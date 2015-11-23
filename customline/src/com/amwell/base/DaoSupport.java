package com.amwell.base;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.amwell.action.BaseAction;
import com.amwell.commons.MyDate;
import com.amwell.commons.StringUtil;
import com.amwell.entity.Formbean;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.entity.UserVo;
import com.amwell.vo.StatOutEntity;
import com.amwell.vo.SysLogVo;
import com.opensymphony.xwork2.ActionContext;
@SuppressWarnings("all")
public class DaoSupport {

	static Logger log = Logger.getLogger(BaseAction.class.getName());
	private static final long serialVersionUID = 1L;
	protected Page page;
	protected Search search;
	protected List excelList;
	protected List list;
	protected BaseDao tableDao;
	protected Formbean form;
	protected String sql;
	protected Object[] args;
	protected UserVo uservo;
	protected Formbean searchform;
	protected BaseDao_trc tableDao_trc;
	protected Map<String,Object> map = new HashMap<String, Object>();;
	
	public UserVo getUservo() {
		return uservo;
	}
	public void setUservo(UserVo uservo) {
		this.uservo = uservo;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Formbean getForm() {
		return form;
	}
	public void setForm(Formbean form) {
		this.form = form;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public List getExcelList() {
		return excelList;
	}
	public void setExcelList(List excelList) {
		this.excelList = excelList;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	
	public DaoSupport()
	{
		
	}

	public Formbean getSearchform() {
		return searchform;
	}

	public void setSearchform(Formbean searchform) {
		this.searchform = searchform;
	}
	
	
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	/**
	 * 前台操作专用(前台不需要登录,不需要验证用户登录)
	 * @param tableName
	 */
	public void finit(String tableName){
		if(tableDao == null){
			tableDao = new BaseDao(tableName);
		}else{
			tableDao.setTableName(tableName);
		}
	}
	
	private String getRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(36)]);
		}
		return buffer.toString();
	}
	public static String delHTMLTag(String htmlStr){ 
    	String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
        String regEx_str = "\\t*|\n";
        //htmlStr = htmlStr.replaceAll("\"", "’");  
        htmlStr = htmlStr.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");  
        //htmlStr = htmlStr.replaceAll("[(/>)<]", "");  
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
         
        Pattern p_str=Pattern.compile(regEx_str,Pattern.CASE_INSENSITIVE); 
        Matcher m_str=p_str.matcher(htmlStr); 
        htmlStr=m_str.replaceAll(""); 
        return htmlStr.trim(); //返回文本字符串 s
    } 
	
	
	public static String unescape(String src) {
		if (src == null)
			return "";
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	//获取订单号
	public String getOrderNo(){
		//检查订单号是否重复
		finit("lease_base_info");
		String orderNo = StringUtil.getOrderNo();
		String sql_orderNo = "select * from lease_base_info where leaseOrderNo = '"+orderNo+"'";
		int count = tableDao.queryCount(sql_orderNo);
		
		if(count>0){//订单号重复，从新生成
			getOrderNo();
		}
		return orderNo;
	}
	
	/**
	 * 
	 * @param userId 用户ID
	 * @param action 执行动作
	 * @param operateResult 操作结果
	 */
	public void AppendLog(String userId, String action,String operateResult)
	{
		try{
			finit("sys_log");
			SysLogVo log = new SysLogVo();
			log.setAction(action);
			log.setOperateResult(operateResult);
			log.setUserId(userId);
			log.setUserIp(getIpAddr());
			log.setSysType("0");
			log.setOperateTime(MyDate.getMyDateLong());
			tableDao.updateData(log,"logId");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加支出记录
	 * @param userId  操作人
	 * @param leaseOrderNo 订单号
	 * @param lineClassId 班次ID
	 * @param outMoney 支出额度
	 * @param outType 支出类型 1:退票   2:改签
	 * @param orderDate  乘车日期
	 * @param orderStartTime 发车时间
	 */
	public void addStatOut(String userId,String leaseOrderNo,String lineClassId,BigDecimal outMoney,Integer outType,String orderDate,String orderStartTime){
		try{
			finit(" line_class_info AS lineClass LEFT JOIN line_base_info AS lineBase ON lineClass.lineBaseId = lineBase.lineBaseId ");
			sql = " SELECT lineClass.lineClassId,lineClass.lineBaseId,lineBase.businessId,lineClass.driverId,lineClass.vehicleId ";
			sql +=" from  line_class_info AS lineClass LEFT JOIN line_base_info AS lineBase ON lineClass.lineBaseId = lineBase.lineBaseId where 1=1 AND lineClass.lineClassId = ? ";
			StatOutEntity outEntity = new StatOutEntity();
			args = new Object[1];
			args[0] = lineClassId;
			outEntity = tableDao.queryBean(StatOutEntity.class, sql, args);
			sql =" SELECT passengerId as field01,businessId as field02 from lease_base_info WHERE leaseOrderNo = ? ";
			args = new Object[1];
			args[0] = leaseOrderNo;
			Search search = tableDao.queryBean(Search.class, sql,args);
			if(null!=outEntity){
				outEntity.setLeaseOrderNo(leaseOrderNo);
				outEntity.setOperatTime(MyDate.getMyDateLong());
				outEntity.setOperater(userId);
				outEntity.setOutMoney(outMoney);
				outEntity.setOutType(outType);
				outEntity.setOrderDate(orderDate);
				outEntity.setOrderStartTime(orderStartTime);
				outEntity.setPassengerId(search.getField01());
				outEntity.setBusinessId(search.getField02());
				finit(" stat_out ");
				tableDao.updateData(outEntity, "statOutId");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getIpAddr() {
		
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request =(HttpServletRequest)ac.get(ServletActionContext.HTTP_REQUEST);
		
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
