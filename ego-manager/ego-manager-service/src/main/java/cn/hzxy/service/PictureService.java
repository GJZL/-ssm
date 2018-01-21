package cn.hzxy.service;

import org.springframework.web.multipart.MultipartFile;

import cn.hzxy.bean.PictureResult;

public interface PictureService {

	PictureResult uploadFile(MultipartFile uploadFile);
}
