package com.amwell.base;

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
import com.amwell.entity.Formbean;
import com.amwell.entity.Page;
import com.amwell.entity.Search;
import com.amwell.entity.UserVo;
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
	protected Map<String, Object> map = new HashMap<String, Object>();;

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

	public DaoSupport() {

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
	 * 
	 * @param tableName
	 */
	public void finit(String tableName) {
		if (tableDao == null) {
			tableDao = new BaseDao(tableName);
		} else {
			tableDao.setTableName(tableName);
		}
	}

	private String getRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(36)]);
		}
		return buffer.toString();
	}

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String regEx_str = "\\t*|\n";
		htmlStr = htmlStr.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签
		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		Pattern p_str = Pattern.compile(regEx_str, Pattern.CASE_INSENSITIVE);
		Matcher m_str = p_str.matcher(htmlStr);
		htmlStr = m_str.replaceAll("");
		return htmlStr.trim(); // 返回文本字符串 s
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
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
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

	/**
	 * 根据用户ID初始化用户vo
	 * 
	 * @param user_recid
	 * @return
	 * @throws Exception
	 */
	public UserVo initUservo(String user_recid) {
		finit("user");
		sql = " select * from user where rec_id = ? ";
		args = new Object[1];
		args[0] = user_recid;
		UserVo form_uservo = tableDao.queryBean(UserVo.class, sql, args);
		if (form_uservo == null) {
			return null;
		}
		return form_uservo;
	}

	public String getIpAddr(HttpServletRequest request) {
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

	/**
	 * 
	 * @param userId
	 *            用户ID
	 * @param action
	 *            执行动作
	 * @param operateResult
	 *            操作结果
	 */
	public void AppendLog(String userId, String action, String operateResult) {
		ActionContext ac = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);

		try {
			finit("sys_log");
			SysLogVo log = new SysLogVo();
			log.setAction(action);
			log.setOperateResult(operateResult);
			log.setUserId(userId);
			log.setUserIp(getIpAddr(request));
			log.setSysType("1");
			log.setOperateTime(MyDate.getMyDateLong());
			tableDao.updateData(log, "logId");
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
	}
}
