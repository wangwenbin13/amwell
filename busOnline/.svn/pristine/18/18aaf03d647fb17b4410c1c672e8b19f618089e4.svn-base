package com.util.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理字符串的工具类
 * 
 * @author zhangqiang
 */

public class StringUtil {
	private static int sn = 0;
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
	private final static Format dateFormat = new SimpleDateFormat(
			"yyMMddHHmmssSS");
	private final static NumberFormat numberFormat = new DecimalFormat("000");
	private static int seq = 0;
	private static final int MAX = 999;

	/**
	 * 时间格式生成序列
	 * 
	 * @return String
	 */
	public static synchronized String generateSequenceNo() {

		Calendar rightNow = Calendar.getInstance();

		StringBuffer sb = new StringBuffer();

		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

		numberFormat.format(seq, sb, HELPER_POSITION);

		if (seq == MAX) {
			seq = 0;
		} else {
			seq++;
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(decodeUnicode("\u6df1\u5733\u5e02"));

	}

	/**
	 * 对象转换成字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 对象转换成整型
	 * 
	 * @param obj
	 * @return
	 */
	public static int objectToInt(Object obj) {
		int i = 0;
		if (obj != null) {
			i = Integer.parseInt(objectToString(obj));
		}
		return i;
	}

	/**
	 * 对象转换成长整型
	 * 
	 * @param obj
	 * @return
	 */
	public static Long objectToLong(Object obj) {
		long i = 0;
		if (obj != null) {
			i = Long.parseLong(objectToString(obj));
		}
		return i;
	}

	/**
	 * 对象转换成布尔型
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean objectToBoolean(Object obj) {
		String value = objectToString(obj);
		if (value == null) {
			return false;
		} else {
			if (value.equals("0") || value.equalsIgnoreCase("false")) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 格式化数字，取整
	 * 
	 * @param d
	 * @return
	 */
	public static String transNumber(Object d) {
		return d != null ? d.toString().replaceAll("\\.0", "") : "";
	}

	/**
	 * 把str中的oldStr字符串替换成newStr
	 * 
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static String strReplace(String str, String oldStr, String newStr) {
		int i = -1;
		while ((i = str.indexOf(oldStr)) > -1) {
			str = str.substring(0, i) + newStr
					+ str.substring(i + oldStr.length());
		}
		return str;
	}

	/**
	 * 生成唯一主键（无“-”）
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成唯一主键（有“-”）
	 * 
	 * @return
	 */
	public static String getAllUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成一个新的唯一id，21位：17位时间+四位流水号
	 * 
	 * @return 返回生成的流水号
	 */
	public synchronized static String getNewID() {
		if (sn >= 999) {
			sn = 0;
		}
		// 年4月2日2时2分2秒2毫秒3序列4共21位
		return new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new java.util.Date())
				+ new java.text.DecimalFormat("000").format(sn++);
	}

	/**
	 * 对象转换成日期
	 * 
	 * @param args
	 */

	public static java.sql.Date objectToDate(Object obj) {
		if (obj == null) {
			return null;
		} else {
			java.util.Date d = (java.util.Date) obj;
			return new java.sql.Date(d.getTime());
		}
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return
	 */
	public static java.sql.Date stringToDate(String str) {
		return stringToDate(str, "yyyy-mm-dd");
	}

	/**
	 * 字符串转换成日期（自定义日期格式）
	 * 
	 * @param str
	 * @param simpleFormat
	 * @return
	 */
	public static java.sql.Date stringToDate(String str, String simpleFormat) {
		if (str == null) {
			return null;
		} else {
			SimpleDateFormat dformat = new SimpleDateFormat(simpleFormat);
			Date date = null;
			try {
				date = dformat.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (date != null) {
				return new java.sql.Date(date.getTime());
			} else {
				return null;
			}
		}
	}
	
	

	/**
	 * unicode 转换成 中文
	 * 
	 * @author fanhui 2007-3-15
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param attriName
	 * @return
	 */
	public static String toUpperCaseFirstChar(String value) {
		return (value.charAt(0) + "").toUpperCase() + value.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param attriName
	 * @return
	 */
	public static String toLowerCaseFirstChar(String value) {
		return (value.charAt(0) + "").toLowerCase() + value.substring(1);
	}

	/**
	 * 将二进制流数据按指定编码转换成字符串
	 * 
	 * @param ins
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String getInputStream(InputStream ins, String encoding)
			throws IOException {
		byte[] byteBuffer = new byte[1024];
		int bytesRead = 0;
		int totBytesRead = 0;
		int totBytesWritten = 0;
		ByteArrayOutputStream outs = new java.io.ByteArrayOutputStream();
		while ((bytesRead = ins.read(byteBuffer)) != -1) {
			outs.write(byteBuffer, 0, bytesRead);
			totBytesRead += bytesRead;
			totBytesWritten += bytesRead;
		}
		return outs.toString(encoding);
	}

	/**
	 * 中文货币大写 绝对值小于1.0E15（正负1千万亿以内），小数位2位
	 * 
	 * @param money
	 * @return
	 */
	public static String chineseCapitalCurrency(BigDecimal money) {
		String units[] = { "分", "角", "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟",
				"亿", "拾", "佰", "仟", "万", "拾", "佰" };
		String capNum[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String str;
		boolean bZero;
		int unitIndex;
		str = "";
		bZero = true;
		unitIndex = 0;
		if (money.intValue() == 0)
			return "零圆整";
		try {
			double dm = Math.round(money.doubleValue() * 100D);
			boolean bNegative = dm < 0.0D;
			for (dm = Math.abs(dm); dm > 0.0D;) {
				if (unitIndex == 2 && str.length() == 0)
					str = str + "整";
				if (dm % 10D > 0.0D) {
					str = capNum[(int) (dm % 10)] + units[unitIndex] + str;
					if (unitIndex != 5 && unitIndex != 9)
						bZero = false;
				} else {
					if (unitIndex == 2) {
						if (dm > 0.0D) {
							str = units[unitIndex] + str;
							bZero = true;
						}
					} else if ((unitIndex == 6 || unitIndex == 10)
							&& dm % 1000 > 0.0D) {
						str = units[unitIndex] + str;
					}

					if (!bZero)
						str = capNum[0] + str;
					bZero = true;
				}
				dm = Math.floor(dm / 10D);
				unitIndex++;
			}

			if (bNegative)
				str = "负" + str;
		} catch (Exception e) {
			e.printStackTrace();
			return "#Error";
		}
		return str;
	}

	/**
	 * 获取SourceStr中使用Beg开头，End结尾的内容
	 * 
	 * @param SourceStr
	 * @param Beg
	 * @param End
	 * @return
	 */
	public static String getBeginEndStr(String SourceStr, String Beg, String End) {
		String returnStr = "";
		int n = 0;
		int i = 0;
		int j = 0;
		if ((SourceStr.indexOf(Beg) > -1) && ((SourceStr.indexOf(End) > -1))) {
			if (SourceStr.indexOf(Beg, n) > -1) {
				i = SourceStr.indexOf(Beg);
				j = SourceStr.indexOf(End);
				returnStr = SourceStr.substring((i + Beg.length()), j);
			}
		}
		return returnStr;
	}

	/**
	 * 使用指定的分隔符（mark）分割指定字符串（strOb）
	 * 
	 * @param strOb
	 * @param mark
	 * @return 返回分割的字符串数组
	 */
	public static String[] split(String strOb, String mark) {
		List<String> tmp = new ArrayList<String>();
		for (int op = 0; (op = strOb.indexOf(mark)) != -1;) {
			tmp.add(strOb.substring(0, op));
			strOb = strOb.substring(op + mark.length(), strOb.length());
		}

		String strArr[] = new String[tmp.size() + 1];
		int i;
		for (i = 0; i < tmp.size(); i++)
			strArr[i] = (String) tmp.get(i);

		strArr[i] = strOb;
		return strArr;
	}

	/**
	 * 对文字过多的标题进行截断（使用"\n"），并加上指定后缀
	 * 
	 * @param title
	 *            需分割的标题
	 * @param length
	 *            指定的分割长度
	 * @param postfix
	 *            后缀
	 * @return 处理后的结果
	 */
	public static String substring(String title, int length, String postfix) {
		if (title.length() > length) {
			String temp = title.substring(0, length - 1);
			temp = strReplace(temp, "\n", "");
			return temp + postfix;
		} else {
			return title;
		}
	}

	/**
	 * 获取图形验证码
	 * 
	 * @param width
	 * @param height
	 * @param length
	 * @param useCharacter
	 *            是否包含字母
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List getVerifyImage(int width, int height, int length,
			boolean useCharacter) {
		List list = new ArrayList();
		Random random = new Random();
		String ychar = useCharacter ? "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z"
				: "0,1,2,3,4,5,6,7,8,9";
		String[] yc = ychar.split(",");
		StringBuffer seed = new StringBuffer();
		for (int i = 0; i < length; i++) {
			seed.append(yc[random.nextInt(yc.length)]);
		}
		list.add(seed.toString());

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(new Color(245, 245, 245));
		g.fillRect(0, 0, width, height);
		g.setColor(getRandColor(160, 200));

		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString(seed.toString(), 8, 13);
		g.dispose();
		list.add(image);
		return list;
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * ISO-8859-1转换成GBK
	 * 
	 * @param str
	 * @return
	 */
	public static String ISOtoGBK(String str) {
		try {
			if (str == null)
				str = "";
			else
				str = new String(str.getBytes("ISO-8859-1"), "GBK");
		} catch (Exception e) {
			System.out.println("DealString::toGBK(String)运行时出错：错误为：" + e);
		}
		return str;
	}

	/**
	 * ISO-8859-1转换成GBK
	 * 
	 * @param str
	 * @return
	 */
	public static String ISOtoUTF8(String str) {
		try {
			if (str == null)
				str = "";
			else
				str = new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (Exception e) {
			System.out.println("DealString::toGBK(String)运行时出错：错误为：" + e);
		}
		return str;
	}

	/**
	 * GBK转换成UTF8
	 * 
	 * @param str
	 * @return
	 */
	public static String GBKtoUTF8(String str) {
		try {
			if (str == null)
				str = "";
			else
				str = new String(str.getBytes("GBK"), "utf-8");
		} catch (Exception e) {
			System.out.println("DealString::toGBK(String)运行时出错：错误为：" + e);
		}
		return str;
	}

	/**
	 * UTF8转换成GBK
	 * 
	 * @param str
	 * @return
	 */
	public static String UTF8toGBK(String str) {
		try {
			if (str == null)
				str = "";
			else
				str = new String(str.getBytes("utf-8"), "GBK");
		} catch (Exception e) {
			System.out.println("DealString::toGBK(String)运行时出错：错误为：" + e);
		}
		return str;
	}

	/**
	 * 创建18为由年月日时分秒和4位毫秒组成的主键
	 * 
	 * @return
	 */
	public static String createid() {
		java.util.Date now = new java.util.Date();
		SimpleDateFormat dd = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
		return dd.format(now);
	}

	/**
	 * 字符编码
	 * 
	 * @param src
	 * @return
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 字符解码
	 * 
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
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

	/**
	 * 将预编译sql语句中的？转换成对应的值
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public static String replaceSql(StringBuilder sql, Object[] args, int i) {
		try {
			int row = sql.lastIndexOf("?");
			if (row > -1) {
				i--;
				sql.delete(row, row + 1);
				sql.insert(row, "'" + args[i] + "'");
				replaceSql(sql, args, i);
				return sql.toString();
			} else {
				return sql.toString();
			}
		} catch (Exception e) {
			System.out.println("将预编译sql语句中的？转换成对应的值时出错！");
			return null;
		}

	}

	/**
	 * 将字符串转换成输入流
	 * 
	 * @param str
	 * @return
	 */
	public static InputStream string2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	/**
	 * 将输入流转换成字符串
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static String inputStream2String(InputStream is) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	/**
	 * 清除html标签
	 * 
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr) {
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 字符串截取(含html标签)
	 * 
	 * @param htmlStr
	 * @param length
	 * @param postfix
	 * @return
	 */
	public static String subStringHtml(String htmlStr, int length,
			String postfix) {
		String str = delHTMLTag(htmlStr);// 首先去除html标签
		if (str.length() > length) {
			str = str.substring(0, length);
			if (!"".equals(postfix)) {
				str += postfix;
			} else {
				str += "";
			}
		}
		return str;

	}

	/**
	 * 去掉List中的重复记录
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List removeDuplicate(Collection<?> list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
	}

	/**
	 * 去掉以逗号分隔的字符串中的重复记录
	 * 
	 * @param oldString
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String removeDuplicate(String oldString) {
		Set set = new HashSet();
		StringBuffer newString = new StringBuffer();
		if (!"".equals(oldString)) {
			String[] stringArray = oldString.split(",");
			for (int i = 0; i < stringArray.length; i++) {
				if (set.add(stringArray[i])) {
					newString.append(stringArray[i] + ",");
				}
			}
		}
		return newString.toString();
	}

	/**
	 * 对用逗号分隔的字符串处理 将类似",30,80,"的字符串转换成"'30','80'"
	 * 
	 * @return
	 */
	public static String commaHandle(String commaString) {
		StringBuffer str = new StringBuffer();
		if (commaString.startsWith(",")) {
			commaString = commaString.substring(1);
		}

		if (commaString.endsWith(",")) {
			commaString = commaString.substring(0, commaString.length() - 1);
		}

		String[] strArray = commaString.split(",");
		for (int i = 0; i < strArray.length; i++) {
			str.append("'" + strArray[i] + "',");
		}
		String strContent = str.toString();

		if (strContent.endsWith(",")) {
			strContent = strContent.substring(0, strContent.length() - 1);

		}
		return strContent;
	}

	/**
	 * 判断字符是否存在于字符串中
	 * 
	 * @param str
	 * @param chars
	 * @return
	 */
	public static boolean charIsExists(String str, String chars) {
		boolean flag = false;
		if (!"".equals(str)) {
			String[] strs = str.split(",");
			for (int i = 0; i < strs.length; i++) {
				if (strs[i].equals(chars)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 将'替换成/'
	 * 
	 * @param str
	 * @return
	 */
	public static String danYinHao(String str) {
		// return str.replaceAll("'", "\\\\'");//mysql里的转义
		return str.replaceAll("'", "''");// Oracle里的转义
	}

	/**
	 * 获取随机码
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 获取随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 通过字符串转换成相应的整型，并返回。
	 * 
	 * @param strValue
	 *            String 待转换的字符串
	 * @return int 转换完成的整型
	 * */
	public static int getStrToInt(String strValue) {
		if (null == strValue) {
			return 0;
		}
		int iValue = 0;
		try {
			iValue = new java.lang.Integer(strValue.trim()).intValue();
		} catch (Exception ex) {
			iValue = 0;
		}
		return iValue;
	}

}
