package cn.hzxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbItem;
import cn.hzxy.service.ItemDescService;

@Controller
@RequestMapping("/rest/item")
public class ItemDescController {

	@Autowired
	private ItemDescService itemDescService;

	/**
	 * 查询商品
	 * @param item_id
	 * @return EgoResult
	 */
	@RequestMapping("/query/item/desc/{item_id}")
	@ResponseBody
	public EgoResult getItemCatList(@PathVariable Long item_id) {
		return itemDescService.findItemDescById(item_id);
	}

	/**
	 * 修改商品
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @param itemParamId
	 * @return EgoResult
	 */
	@RequestMapping("/update")
	@ResponseBody
	public EgoResult updateItem(TbItem item, String desc, String itemParams,@RequestParam(value="",required=false) Long itemParamId) {
		return itemDescService.updateItem(item, desc, itemParams, itemParamId);
	}
	/**
	 * 删除商品
	 * @param ids
	 * @return EgoResult
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public EgoResult deleteItem(Long ...ids) {
		return itemDescService.deleteItem(ids);
	}
	/**
	 * 商品下架
	 * @param ids
	 * @return EgoResult
	 */
	@RequestMapping("/instock")
	@ResponseBody
	public EgoResult instockItem(Long ...ids) {
		return itemDescService.instockItem(ids);
	}
	/**
	 * 商品上架
	 * @param ids
	 * @return EgoResult
	 */
	@RequestMapping("/reshelf")
	@ResponseBody
	public EgoResult reshelfItem(Long ...ids) {
		return itemDescService.reshelfItem(ids);
	}

}
