package cn.hzxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EUDataGridResult;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbItem;
import cn.hzxy.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	/**
	 * 根据id查商品
	 * @param id
	 * @return TbItem
	 */
	@RequestMapping("/{id}")
	@ResponseBody
	public TbItem findItemById(@PathVariable Long id) {
		TbItem item = itemService.findItemById(id);
		return item;
	}

	/**
	 * 查询所有商品
	 * @param page
	 * @param rows
	 * @return EUDataGridResult
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItemList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows) {
		return itemService.getItemList(page, rows);

	}
	/**
	 * 保存商品
	 * @param item
	 * @param desc
	 * @return EgoResult
	 */
	@RequestMapping("/save")
	@ResponseBody
	public EgoResult saveItem(TbItem item,String desc) {
		return itemService.saveItem(item, desc,null);
		
	}
}
