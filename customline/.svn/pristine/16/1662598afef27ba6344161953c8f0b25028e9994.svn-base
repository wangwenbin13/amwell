package com.amwell.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author wangwenbin
 *
 */
/**
 * FTP图片上传
 */
public class HttpFileUpload {

	/***
	 * 图片拷贝
	 * @param oldFilePath
	 * @param newFilePath
	 * @param name
	 * @throws IOException
	 */
	public static String copy(File file, String newFilePath,Integer type) throws IOException {
		File f1 =file; // 源文件的File对象
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String statu = "0";
		String directory = "busonline";//(一级文件目录)
		String secondDir = null;//(二级文件目录)
		if(type == 0){
			secondDir = "busonline";
		}else if(type==1){
			secondDir = "s_pic";
		}else if(type == 5){
			secondDir = "m_pic";
		}else if(type==6){
			secondDir = "customerIntroduce";
		}else if(type==7){
			secondDir = "ad_pic";
		}
		if(StringUtils.isEmpty(secondDir)){
			return statu;
		}
		newFilePath += secondDir;
		
		Calendar now= Calendar.getInstance();
		//目录格式 2015/5-21/2015052120.jpg
		String  catalogue = now.get(Calendar.YEAR)+"/"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH);
		File f2 = null;
		String[] path = {newFilePath,newFilePath+"/"+now.get(Calendar.YEAR),newFilePath+"/"+catalogue};
		for(int i=0;i<3;i++){
			f2 = new File(path[i]); // 目标存放文件夹
			if(!f2.exists()){
				//判断文件夹是否创建，没有创建则创建新文件夹
				f2.mkdirs();
		    }
		}
		String fileNameString = sdf.format(new Date())+".jpg";
		newFilePath = newFilePath+"/"+catalogue+"/"+fileNameString;
		f2 = new File(newFilePath); // 目标文件的File对象

		if (!f2.isFile() || !f2.exists()) {
			InputStream input = null; // 准备好输入流对象，读取源文件
			OutputStream out = null; // 准备好输出流对象，写入目标文件
			try {
				input = new FileInputStream(f1);
				out = new FileOutputStream(f2);
				int temp = 0;
				try {
					while ((temp = input.read()) != -1) { // 开始拷贝
						out.write(temp); // 边读边写
					}
					statu = directory+"/"+secondDir+"/"+catalogue+"/"+fileNameString;
					System.out.println("拷贝完成！");
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("拷贝失败！");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(input!=null){
					input.close();
				}
				if(out!=null){
					out.close();
				}
			}
		}else{
			System.out.println("文件已存在");
		}
		return statu;
	}
}
