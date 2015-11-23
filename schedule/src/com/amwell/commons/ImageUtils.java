package com.amwell.commons;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * @author Administrator
 */
public class ImageUtils {
	/**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop
    static Logger log = Logger.getLogger(ImageUtils.class.getName());
    /**
     * 程序入口：用于测试
     * @param args
     */
    public static void main(String[] args) {
        // 1-缩放图像：
        // 方法一：按比例缩放
        //ImageUtils.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);//测试OK
        // 方法二：按高度和宽度缩放
        //ImageUtils.scale2("e:/abc_pressImage.jpg", "e:/abc_pressImage.jpg2.jpg", 128, 128, true);//测试OK
        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
       // ImageUtils.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK
        // 方法二：指定切片的行数和列数
       // ImageUtils.cut2("e:/abc.jpg", "e:/", 2, 2 );//测试OK
        // 方法三：指定切片的宽度和高度
       // ImageUtils.cut3("e:/abc.jpg", "e:/", 300, 300 );//测试OK
        // 3-图像类型转换：
       // ImageUtils.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");//测试OK
        // 4-彩色转黑白：
       // ImageUtils.gray("e:/abc.jpg", "e:/abc_gray.jpg");//测试OK
        // 5-给图片添加文字水印：
        // 方法一：
       // ImageUtils.pressText("我是水印文字","e:/abc.jpg","e:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);//测试OK
        // 方法二：
       // ImageUtils.pressText2("我也是水印文字", "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
        
        // 6-给图片添加图片水印：
        //ImageUtils.pressImage("e:/logo.png", "e:/2012100814218229.jpg","e:/abc_pressImage.jpg", 0, 0, 0.8f);//测试OK
    }
    
    public final static void saveUserSayPic(String srcImageFile,String extName,String srcImageLogo,int c_width, int c_height){
    	try{
    		BufferedImage pic_src = ImageIO.read(new File(srcImageFile+extName)); // 读入文件
    		int width = pic_src.getWidth(); // 得到源图宽
            int height = pic_src.getHeight(); // 得到源图高
            if(width>800||height>600){
                if(width>800){
                	//长图
                	int scale = (width/800)+1;
                	ImageUtils.scale(srcImageFile+extName, srcImageFile+"a.jpg", scale, false);//缩小
                	width = width/scale;
                	height = height/scale;
                }else{
                	//宽图
                	int scale = (height/600)+1;
                	ImageUtils.scale(srcImageFile+extName, srcImageFile+"a.jpg", scale, false);//缩小
                	height = width/scale;
                	width = height/scale;
                }
                if(width>800||height>600){
                	//仍然超宽高
                	if(width>800){
                		ImageUtils.cut(srcImageFile+"a.jpg", srcImageFile+"a.jpg", 0, 0, 800, height );//测试OK
                		width = 800;
                	}else{
                		ImageUtils.cut(srcImageFile+"a.jpg", srcImageFile+"a.jpg", 0, 0, width, 600 );//测试OK
                		height = 600;
                	}
                }
                
            }else{
            	ImageUtils.scale(srcImageFile+extName, srcImageFile+"a.jpg", 1, false);//缩小
            }
            if(width>300&&height>150){
            	ImageUtils.pressImage(srcImageLogo+"logo.png",  srcImageFile+extName, srcImageFile+".jpg", 0, 0, 0.8f);//添加水印
            }else if(width>50&&height>70){
            	ImageUtils.pressImage(srcImageLogo+"logo_small.png",  srcImageFile+extName, srcImageFile+".jpg", 0, 0, 0.8f);//添加水印
            }else{
            	ImageUtils.scale(srcImageFile+extName, srcImageFile+".jpg", 1, false);//缩小
            }
            
            /*
            if(width>1024){
            	int scalint = width/512;
            	width = width/scalint; // 新宽
                height = height/scalint; // 新高
            	ImageUtils.scale(pic_src, srcImageFile+extName, srcImageFile+extName, scalint, false);//按比例缩小
            }
            
            int scalint_s = width/170;
            	if(scalint_s>0&&((height-width)/scalint_s>(width/scalint_s)/4)){
            		ImageUtils.scale(srcImageFile+".jpg", srcImageFile+"a.jpg", scalint_s, false);//按比例缩小
            		//ImageUtils.cut(srcImageFile+"a.jpg", srcImageFile+"a.jpg", 0, 0, c_width, c_height );//切割
            	}else{
            		//ImageUtils.cut(srcImageFile+".jpg", srcImageFile+"a.jpg", 0, 0, 170, 130 );//切割
            		//ImageUtils.cut(srcImageFile+".jpg", srcImageFile+"a.jpg", 0, 0, c_width, c_height );//切割
            		ImageUtils.scale(srcImageFile+".jpg", srcImageFile+"a.jpg", 1, false);//按比例缩小
            	}
    		*/
            //非jpg格式转jpg
            /*
            if(!".jpg".equalsIgnoreCase(extName)){
            	ImageUtils.convert(srcImageFile+extName, "JPG", srcImageFile+".jpg");//转换格式
            }
            if(width>300&&height>150){
            	ImageUtils.pressImage(srcImageLogo+"logo.png",  srcImageFile+".jpg", srcImageFile+".jpg", 0, 0, 0.8f);//添加水印
            }else if(width>50&&height>70){
            	ImageUtils.pressImage(srcImageLogo+"logo_small.png",  srcImageFile+".jpg", srcImageFile+".jpg", 0, 0, 0.8f);//添加水印
            }
            if(width>600){
            	int scale = width/200;
            	ImageUtils.scale(srcImageFile+".jpg", srcImageFile+"a.jpg", scale, false);//缩小
            	width = width/scale;
            	height = height/scale;
            	if(height>200){
            		ImageUtils.cut(srcImageFile+"a.jpg", srcImageFile+"a.jpg", 0, 0, width, 200 );//切割
            	}
            }else if(height>600){
            	int scale = height/200;
            	ImageUtils.scale(srcImageFile+".jpg", srcImageFile+"a.jpg", scale, false);//缩小
            	height = width/scale;
            	width = height/scale;
            	if(height>200){
            		ImageUtils.cut(srcImageFile+"a.jpg", srcImageFile+"a.jpg", 0, 0, width, 200 );//切割
            	}
            }else{
            	ImageUtils.scale(srcImageFile+".jpg", srcImageFile+"a.jpg", 1, false);//缩小
            }
            */
    	}catch (Exception e) {
    		//System.out.println("////Exception by ImageUtils:"+e.getMessage());
    		log.error("图片后台处理出错（frame.commons.ImageUtils（129行））："+e);
		}
    	
    	//ImageUtils.scale2(savePath + name + extName, savePath + name +"a"+ extName, 170, 170, false);//按比例缩放
		//ImageUtils.cut(savePath + name + extName, savePath + name +"b"+ extName, 0, 0, 519, 231 );//测试OK
		//ImageUtils.pressImage(base_savePath+"imageupload/logo.png", savePath + name + extName,savePath + name + extName, 0, 0, 0.8f);//添加水印
    }
    /**
     * 缩放图像（按比例缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param scale 缩放比例
     * @param flag 缩放选择:true 放大; false 缩小;
     */
    public final static void scale(String srcImageFile, String result,
            int scale, boolean flag) {
        try {
        	BufferedImage src = ImageIO.read(new File(srcImageFile)); 
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {// 放大
                width = width * scale;
                height = height * scale;
            } else {// 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（165行））："+e);
        }
    }
    /**
     * 缩放图像（按比例缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param scale 缩放比例
     * @param flag 缩放选择:true 放大; false 缩小;
     */
    public final static void scale(BufferedImage src,String srcImageFile, String result,
            int scale, boolean flag) {
        try {
            int width = src.getWidth(); // 得到源图宽
            int height = src.getHeight(); // 得到源图长
            if (flag) {// 放大
                width = width * scale;
                height = height * scale;
            } else {// 缩小
                width = width / scale;
                height = height / scale;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        } catch (IOException e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（196行））："+e);
        }
    }
    /**
     * 缩放图像（按高度和宽度缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     */
    public final static void scale2(String srcImageFile, String result, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(srcImageFile);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
        } catch (IOException e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（244行））："+e);
        }
    }
    
    /**
     * 图像切割(对本图进行切割)
     * @param srcImageFile 源图像地址
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param x2
     * @param y2
     */
    public final static void cut(String srcImageFile, 
            int x, int y, int x2, int y2,int w_end ,int h_end) {
        try {
        	int width = x2-x;
        	int height = y2-y;
        	String result = srcImageFile;
            // 读取源图像
        	BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcHeight = bi.getHeight(); // 源图宽度
            int srcWidth = bi.getWidth(); // 源图高度
            if (srcWidth >= width && width > 0 && srcHeight >= height && height > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(),
                                cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(result));
                //压缩
                if(h_end==142){
                	scale2(srcImageFile,srcImageFile,h_end,w_end,true);
                }
            }
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（281行））："+e);
        }
    }
    
    /**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param srcImageFile 源图像地址
     * @param result 切片后的图像地址
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     */
    public final static void cut(String srcImageFile, String result,
            int x, int y, int width, int height) {
        try {
            // 读取源图像
        	BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcHeight = bi.getHeight(); // 源图宽度
            int srcWidth = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter((srcWidth-width)/2-x, (srcHeight-height)/2-y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(),
                                cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(result));
            }
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（281行））："+e);
        }
    }
    
    /**
     * 图像切割（指定切片的行数和列数）
     * @param srcImageFile 源图像地址
     * @param descDir 切片目标文件夹
     * @param rows 目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols 目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public final static void cut2(String srcImageFile, String descDir,
            int rows, int cols) {
        try {
            if(rows<=0||rows>20) rows = 2; // 切片行数
            if(cols<=0||cols>20) cols = 2; // 切片列数
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcHeight = bi.getHeight(); // 源图宽度
            int srcWidth = bi.getWidth(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int destWidth = srcWidth; // 每张切片的宽度
                int destHeight = srcHeight; // 每张切片的高度
                // 计算切片的宽度和高度
                if (srcWidth % cols == 0) {
                    destWidth = srcWidth / cols;
                } else {
                    destWidth = (int) Math.floor(srcWidth / cols) + 1;
                }
                if (srcHeight % rows == 0) {
                    destHeight = srcHeight / rows;
                } else {
                    destHeight = (int) Math.floor(srcWidth / rows) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                                destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir
                                + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（341行））："+e);
        }
    }
    /**
     * 图像切割（指定切片的宽度和高度）
     * @param srcImageFile 源图像地址
     * @param descDir 切片目标文件夹
     * @param destWidth 目标切片宽度。默认200
     * @param destHeight 目标切片高度。默认150
     */
    public final static void cut3(String srcImageFile, String descDir,
            int destWidth, int destHeight) {
        try {
            if(destWidth<=0) destWidth = 200; // 切片宽度
            if(destHeight<=0) destHeight = 150; // 切片高度
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcImageFile));
            int srcHeight = bi.getHeight(); // 源图宽度
            int srcWidth = bi.getWidth(); // 源图高度
            if (srcWidth > destWidth && srcHeight > destHeight) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int cols = 0; // 切片横向数量
                int rows = 0; // 切片纵向数量
                // 计算切片的横向和纵向数量
                if (srcWidth % destWidth == 0) {
                    cols = srcWidth / destWidth;
                } else {
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0) {
                    rows = srcHeight / destHeight;
                } else {
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                                destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(descDir
                                + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（400行））："+e);
        }
    }
    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param srcImageFile 源图像地址
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImageFile 目标图像地址
     */
    public final static void convert(String srcImageFile, String formatName, String destImageFile) {
        try {
            File f = new File(srcImageFile);
            f.canRead();
            f.canWrite();
            BufferedImage src = ImageIO.read(f);
            ImageIO.write(src, formatName, new File(destImageFile));
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（417行））："+e);
        }
    }
    /**
     * 彩色转为黑白 
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     */
    public final static void gray(String srcImageFile, String destImageFile) {
        try {
            BufferedImage src = ImageIO.read(new File(srcImageFile));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            src = op.filter(src, null);
            ImageIO.write(src, "JPEG", new File(destImageFile));
        } catch (IOException e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（433行））："+e);
        }
    }
    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText,
            String srcImageFile, String destImageFile, String fontName,
            int fontStyle, Color color, int fontSize,int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));// 输出到文件流
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（472行））："+e);
        }
    }
    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param fontName 字体名称
     * @param fontStyle 字体样式
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param x 修正值
     * @param y 修正值
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText2(String pressText, String srcImageFile,String destImageFile,
            String fontName, int fontStyle, Color color, int fontSize, int x,
            int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(destImageFile));
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（510行））："+e);
        }
    }
    /**
     * 给图片添加图片水印
     * @param pressImg 水印图片
     * @param srcImageFile 源图像地址
     * @param destImageFile 目标图像地址
     * @param x 修正值。 默认在中间
     * @param y 修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(String pressImg, String srcImageFile,String destImageFile,
            int x, int y, float alpha) {
        try {
            File img = new File(srcImageFile);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            g.drawImage(src_biao, (wideth - wideth_biao),
                    (height - height_biao) , wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image,  "JPEG", new File(destImageFile));
        } catch (Exception e) {
        	log.error("图片后台处理出错（frame.commons.ImageUtils（545行））："+e);
        }
    }
    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return
     */
    public final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }
}