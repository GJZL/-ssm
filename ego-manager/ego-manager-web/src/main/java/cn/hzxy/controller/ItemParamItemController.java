package cn.hzxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.service.ItemParamItemService;

@Controller
@RequestMapping("/rest/item")
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;

	@RequestMapping("/param/item/query/{item_id}")
	@ResponseBody
	public EgoResult getItemParamItem(@PathVariable Long item_id) {
		return itemParamItemService.findItemParamItemById(item_id);
	}
}
