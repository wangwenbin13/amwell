package com.amwell.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.JSONException;

import com.amwell.commons.HttpFileUpload;
import com.amwell.commons.JsonWriter;
import com.amwell.commons.PropertyManage;
import com.amwell.vo.ftp.ParamFtp;
/**
 * @author wangwenbin
 *
 * 2014-8-14
 */
/**
 */
@ParentPackage("user-finit")
@Namespace("/ftpUploadAction")
public class FtpUploadAction extends BaseAction {
	
	private static final long serialVersionUID = 2963253995263023966L;
	
	private File photo;
	
	private String photoFileName;
	
	private String photoContentType;
	
	public InputStream reportStream;

	public static final String[] ACCEPT_FILE_TYPES = new String[]{"jpg","png","jpeg","gif","bmp"};
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	@Action(value = "upLoad", results = { @Result(type = "json") })
	public void upLoad() throws IOException, JSONException {
		try {
			super.getResponse().setContentType("text/html;charset=UTF-8");
			String newPath = PropertyManage.getInfoProperty("http.pic.path");
			String httpIp = PropertyManage.getInfoProperty("http.pic.ip");
			ParamFtp retFtp = new ParamFtp();
			FileInputStream fis = new FileInputStream(photo);
			int size = fis.available();
			if (StringUtils.isEmpty(photoFileName)) {
				getResponse().getWriter().print("error");
			} else {
				String lowerPhotoFileName = StringUtils.lowerCase(photoFileName);
				if (!FilenameUtils.isExtension(lowerPhotoFileName, ACCEPT_FILE_TYPES)) {
					getResponse().getWriter().print("error");
				} else if (size >= 500 * 1024) {
					getResponse().getWriter().print("overSize");
				} else {
					String uploadFileType = super.request.getParameter("uploadFileType");
					int type = 0;
					if (StringUtils.isNotEmpty(uploadFileType)) {
						type = Integer.parseInt(uploadFileType);
					}
					String statu = HttpFileUpload.copy(photo, newPath, type);
					if ("0".equals(statu)) {
						getResponse().getWriter().print("error");
					} else {
						retFtp.setDbFileUrl(statu);
						retFtp.setDownFileUrl(httpIp + statu);
						JsonWriter.write(retFtp);
					}
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public InputStream getReportStream() {
		return reportStream;
	}

	public void setReportStream(InputStream reportStream) {
		this.reportStream = reportStream;
	}

}
