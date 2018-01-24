package cn.hzxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EUDataGridResult;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbContent;
import cn.hzxy.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	/**
	 * 查询内容
	 * @param parentid
	 * @return
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContent(Long categoryId, int page, int rows) {
		return contentService.getContentList(categoryId, page, rows);
	}

	/**
	 * 新增内容
	 * @param content
	 */
	@RequestMapping("/save")
	@ResponseBody
	public EgoResult saveContent(TbContent content) {
		return contentService.saveContent(content);
	}

	/**
	 * 修改内容
	 * @param content
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public EgoResult editContent(TbContent content) {
		return contentService.editContent(content);
	}

	/**
	 * 删除内容
	 * @param content
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public EgoResult deleteContent(Long ids) {
		return contentService.deleteContent(ids);
	}
}
