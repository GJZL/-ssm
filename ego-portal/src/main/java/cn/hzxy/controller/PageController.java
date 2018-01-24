package cn.hzxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.hzxy.service.AdvertService;

@Controller
public class PageController {

	@Autowired
	private AdvertService advertService;

	/**
	 * 显示门户
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex(Model model) {
		model.addAttribute("advertData", advertService.getBigAdvert());
		return "index";
	}
}
