package cn.hzxy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EUTreeNode;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbContentCategory;
import cn.hzxy.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 查询内容分类
	 * @param parentid
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCategory(@RequestParam(value = "id", defaultValue = "0") Long parentid) {
		return contentCategoryService.getContentCategoryList(parentid);
	}
	/**
	 * 添加内容分类
	 * @param contentCategory
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public EgoResult createContentCategory(TbContentCategory contentCategory) {
		return contentCategoryService.createContentCategory(contentCategory);
	}
	/**
	 * 修改内容分类
	 * @param contentCategory
	 * @return EgoResult
	 */
	@RequestMapping("/update")
	@ResponseBody
	public EgoResult updateContentCategory(TbContentCategory contentCategory) {
		return contentCategoryService.updateContentCategory(contentCategory);
	}
	@RequestMapping("/delete")
	@ResponseBody
	public EgoResult deleteContentCategory(Long id) {
		return contentCategoryService.deleteContentCategory(id);
	}
}
