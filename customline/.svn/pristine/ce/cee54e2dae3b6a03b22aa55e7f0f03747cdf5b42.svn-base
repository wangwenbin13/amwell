package com.amwell.commons;
import java.io.*;
import java.text.SimpleDateFormat;
public class UploadUtil {

 private static final String DATABASE_DEST = "database";

 private static final String FILE_DEST = "file";

 private static final int MAX_SIZE = 20480 * 1024;

private static final String[] TYPES = { ".jpg", ".gif", ".zip", ".rar",".doc",".docx","." };



 public static String saveFile(String front,String path,String fileName, byte[] fileData, int size,
         String dest,int maxSize) throws FileNotFoundException, IOException {
     if (!checkSize(size,maxSize)) {
    	 return "too large";
         
     }

     //if (!checkType(fileName)) {

     //    throw new IOException("Unvaildate type !");

   //  }
   String fjname="";
     if (dest.equals(DATABASE_DEST)) {

         saveToDb(fileName, fileData);

     }

     if (dest.equals(FILE_DEST)) {

         fjname=saveToFile(front,path,fileName, fileData);

     }
     return fjname;
 }



 private static void saveToDb(String fileName, byte[] fileData) {

 }



 private static String saveToFile(String front,String path,String fileName, byte[] fileData)
         throws FileNotFoundException, IOException {
     String ext=fileName.substring(fileName.indexOf("."));
     java.util.Date now = new java.util.Date();
     SimpleDateFormat dd = new SimpleDateFormat("yyyyMMddHHmmss");
     String newfile=front+dd.format(now)+ext;
     System.out.println("path+newfile === "+path+newfile);
     OutputStream o = new FileOutputStream(path+newfile);
     o.write(fileData);
     o.close();
     return newfile;

 }



 public static void delFile(String fileName, String dest)

         throws NullPointerException, SecurityException {

     if (dest.equals(DATABASE_DEST)) {

         delFromDb(fileName);

     }

     if (dest.equals(FILE_DEST)) {

         delFromFile(fileName);

     }

 }



 private static void delFromDb(String fileName) {

 }



 private static void delFromFile(String fileName)

         throws NullPointerException, SecurityException {

     File file = new File(fileName);

     if (file.exists())

         file.delete();

 }



 private static boolean checkSize(int size,int maxSize) {

     if (size > maxSize)

         return false;

     return true;

 }



 private static boolean checkType(String fileName) {

     for (int i = 0; i < TYPES.length; i++) {

         if (fileName.toLowerCase().endsWith(TYPES[i])) {

             return true;

         }

     }

     return false;

 }



}


