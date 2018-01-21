package cn.hzxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.hzxy.bean.PictureResult;
import cn.hzxy.service.PictureService;

@Controller
@RequestMapping("/pic")
public class PictureController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/{page}")
	@ResponseBody
	public PictureResult uploadFile(MultipartFile uploadFile) {
		return pictureService.uploadFile(uploadFile);
	}
}
