package cn.hzxy.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EUTreeNode;
import cn.hzxy.rest.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 查询商品内容分类
	 * @param parentid
	 * @return List<EUTreeNode>
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCategory(@RequestParam(value = "id", defaultValue = "0") Long parentid) {
		return contentCategoryService.getContentCategoryList(parentid);
	}
}
