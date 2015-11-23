package com.amwell.commons;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel处理类 在构建List对象时，请按照Excel表格中显示的顺序，依次将值存入到Excel的field字段中
 * 
 * @author zhangqiang
 * 
 */
public class ExcelUtil {

	private static String fileName = "";// 文件名称
	private Workbook wb; // excel模板对象
	private HashMap<String, CellStyle> stylesMap = new HashMap<String, CellStyle>();// 样式map，存放各种样式

	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public HashMap<String, CellStyle> getStylesMap() {
		return stylesMap;
	}

	public void setStylesMap(HashMap<String, CellStyle> stylesMap) {
		this.stylesMap = stylesMap;
	}

	/**
	 * 创建Excel文件
	 * 
	 * @param list
	 *            数据结果集
	 * @param form
	 *            标题bean
	 * @param r
	 *            开始行
	 * @param c
	 *            开始列
	 * @throws IOException
	 */
	public static InputStream createExcelFile(List list, Object bean, int r, int c) throws IOException {
		FileOutputStream fileOut = null;
		// 产生唯一文件名称,并关联文件流
		String file_name = System.currentTimeMillis() + fileName + ".xls";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0, file_name.substring(0, file_name.length() - 4));

		getExcelHeader(bean, sheet, r, c);

		int i = 0;
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object obj = (Object) itr.next();
			getExcelBody(obj, i, sheet, r + 1, c);
			i++;
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		byte[] ba = bos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(ba);
		return bis;

	}

	/**
	 * 根据Excel模板创建新的excel
	 * 
	 * @param list
	 *            数据结果集
	 * @param filePath
	 *            模板路径
	 * @param r
	 *            开始行
	 * @param c
	 *            开始列
	 * @return
	 * @throws IOException
	 */
	public static InputStream createExcelFromTemplete(List list, String excelPath, int r, int c) throws IOException {
		FileInputStream finput = new FileInputStream(excelPath);
		POIFSFileSystem fs = new POIFSFileSystem(finput);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet = workbook.getSheetAt(0);

		// 设置单元格格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor((short) 13);// 设置背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框

		int i = 0;
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object bean = itr.next();
			getExcelBody(bean, i, sheet, r, c);
			i++;
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos);
		byte[] ba = bos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(ba);
		return bis;
	}

	/**
	 * 构建Excel内容
	 * 
	 * @param fb
	 *            Formbean对象
	 * @param i
	 *            当前行
	 * @param sheet
	 *            Excel sheet对象
	 * @param r
	 *            开始行
	 * @param c
	 *            开始列
	 */
	public static void getExcelBody(Object bean, int i, HSSFSheet sheet, int r, int c) {
		int s = i + r - 1;// 开始行
		int xh = i + 1;// 序号
		Field fields[] = bean.getClass().getDeclaredFields();

		HSSFRow row = sheet.createRow(s);

		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue(xh);

		for (int j = 0; j < fields.length; j++) {

			String defaultNodeName = fields[j].getName();// 字段名称

			Object value = invokeGet(bean, defaultNodeName);
			HSSFCell cell = row.createCell(c - 1);
			row.setHeight((short) 400);
			if (value != null) {
				cell.setCellValue(StringUtil.objectToString(value));
			} else {
				cell.setCellValue("");
			}
			cell.setCellType(HSSFCell.ENCODING_UTF_16); // 防止出现中文乱码
			c++;
		}

	}

	/**
	 * 构建Excel表头
	 * 
	 * @param fb
	 *            Formbean对象
	 * @param sheet
	 *            Excel sheet对象
	 * @param r
	 *            开始行
	 * @param c
	 *            开始列
	 */
	public static void getExcelHeader(Object bean, HSSFSheet sheet, int r, int c) {
		HSSFRow row = sheet.createRow(r);
		Method methods[] = bean.getClass().getMethods();
		for (int j = 0; j < methods.length; j++) {
			Method method = methods[j];
			if (!isSetFieldMethod(method))
				continue;

			String defaultNodeName = method.getName().substring(3);

			Object value = invokeGet(bean, defaultNodeName);
			HSSFCell cell = row.createCell(c);
			cell.setCellType(HSSFCell.ENCODING_UTF_16); // 防止出现中文乱码

			cell.setCellValue(StringUtil.objectToString(value));
			sheet.autoSizeColumn(c);

		}
	}

	/**
	 * 得到字段索引
	 * 
	 * @param fieldName
	 * @return
	 */
	public static int getFieldNum(Object fieldName) {
		int num = 0;
		if (!"".equals(fieldName)) {
			num = StringUtil.objectToInt(StringUtil.objectToString(fieldName).substring(5));
		}
		return num;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return s != null && !s.trim().equalsIgnoreCase("");
	}

	/**
	 * 判断是否为setField方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isSetFieldMethod(Method method) {
		String methodName = method.getName();
		if (!methodName.startsWith("setField")) {
			return false;
		}
		Class<?>[] clazzes = method.getParameterTypes();
		if (clazzes.length != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 取obj属性值
	 * 
	 * @param target
	 * @param name
	 * @return
	 */
	private static Object invokeGet(Object target, String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		String getMethodName = "get" + name;

		Object res = null;
		try {
			Method method = target.getClass().getMethod(getMethodName);
			res = method.invoke(target);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 得到classPath绝对路径
	 * 
	 * @param classPath
	 *            相对路径
	 * @return
	 */
	public static String getAbsoluteClassPath(String classPath) {
		String path = null;
		try {
			File file = new File(Thread.currentThread().getContextClassLoader().getResource(classPath).getPath());
			path = URLDecoder.decode(file.getAbsolutePath(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 判断Excel单元格内容格式
	 * 
	 * @param cell
	 * @return
	 */
	public static String readCell(HSSFCell cell) {
		if (null == cell) {
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING: {
			return cell.getStringCellValue();
		}

		case HSSFCell.CELL_TYPE_NUMERIC: {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return sdf.format(cell.getDateCellValue());
			} else {
				return new BigDecimal(cell.getNumericCellValue()).toString();
			}

		}

		case HSSFCell.CELL_TYPE_BOOLEAN: {
			return String.valueOf(cell.getBooleanCellValue());
		}

		case HSSFCell.CELL_TYPE_FORMULA: {
			return String.valueOf(cell.getCellFormula());
		}

		default: {
			return cell.getStringCellValue();
		}
		}
	}

	
	/**
	 * 
	 * @param list 数据源
	 * @param title 导出列表头名称
	 * @param cellNames 导出的字段中文名字
	 * @param fields 导出的字段属性名
	 * @param response
	 * @param request
	 * @param obj 实体
	 */
	public static void export(List list,String title,String[] cellNames,String[] fields,HttpServletResponse response,
			HttpServletRequest request,Object obj){
		response.setContentType("application/msexcel;charset=GBK");
	    HSSFWorkbook wb = new HSSFWorkbook();// 创建新的Excel 工作簿
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet();
		
		//设置单元格长度
		sheet.setDefaultColumnWidth((short)20);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell ;
		cell = row.createCell((short) 0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);
		
		for(int i = 0;i<cellNames.length;i++){
			cell = row.createCell((short) ++i);
			cell.setCellValue(cellNames[--i]);
			cell.setCellStyle(style);
		}
		
		try {
			for(int j = 0;j<list.size();j++){
				obj = (Object) list.get(j);
				Class clazz = obj.getClass();
				Field[] objFields = obj.getClass().getDeclaredFields();//获得属性
				//将实体类的属性的get方法获得的值作为value放到map中,实体类的属性名字作为key
				Map<String, Object>maps = new HashMap<String,Object>();
			    for (Field field : objFields) {
				   if(!"serialVersionUID".equals(field.getName())){
					   PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
					   Method getMethod = pd.getReadMethod();//获得get方法
					   maps.put(field.getName(), getMethod.invoke(obj));
				   }
			    }
			    row = sheet.createRow((int) (j + 1));
				for(int i = 0;i<fields.length;i++){
					cell = row.createCell((short) 0);
					cell.setCellValue((double) j + 1);
					cell.setCellStyle(style);
					
					cell = row.createCell((short) (i+1));
					cell.setCellStyle(style);
					cell.setCellValue(judeNullVal(maps.get(fields[i])));
				}
			}
		
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
			response.setHeader("Content-Disposition", attachment);
			response.setHeader("Connection", "close");
			response.setHeader("Content-Type", "application/vnd.ms-excel");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * null字符串返回""
	 * @param value
	 * @return
	 */
	private static String judeNullVal(Object object){
		String value = String.valueOf(object);
		value = value=="null"?"":value;
		return value;
	}
}
