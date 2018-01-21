package cn.hzxy.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.hzxy.bean.PictureResult;
import cn.hzxy.service.PictureService;
import cn.hzxy.utils.FtpUtil;
import cn.hzxy.utils.IDUtils;

@Service
public class PictureServiceImpl implements PictureService {
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private int FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public PictureResult uploadFile(MultipartFile uploadFile) {
		PictureResult pictureResult=null;
		if (!uploadFile.isEmpty()) {
			String filename = uploadFile.getOriginalFilename();
			String[] split = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-");
			String afterPath = "/";
			for (String string : split) {
				afterPath = afterPath + string + "/";
			}
			String imageName = IDUtils.genImageName() + filename.substring(filename.indexOf("."));
			try {
				boolean flag = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, afterPath, imageName,
						uploadFile.getInputStream());
				if (flag) {
					pictureResult=new PictureResult(0, IMAGE_BASE_URL+afterPath+imageName);
				}else {
					pictureResult=new PictureResult(1, IMAGE_BASE_URL+"/1.gif", "上传失败");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			pictureResult=new PictureResult(1, IMAGE_BASE_URL+"1.gif", "上传失败");
		}
		return pictureResult;
	}

}
