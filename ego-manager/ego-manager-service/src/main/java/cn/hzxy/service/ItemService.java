package cn.hzxy.service;

import cn.hzxy.bean.EUDataGridResult;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbItem;

public interface ItemService {

	/**
	 * 根据id查商品
	 * @param id 商品id
	 * @return TbItem 商品对象
	 */
	TbItem findItemById(long id);

	/**
	 * 查询所有商品
	 * @param page 查询商品下标
	 * @param rows 分页行数
	 * @return EUDataGridResult 商品集合
	 */
	EUDataGridResult getItemList(Integer page, Integer rows);

	/**
	 * 保存商品
	 * @param item 商品对象
	 * @param desc 商品描述
	 * @param object 商品规格
	 * @return EgoResult 操作状态
	 */
	EgoResult saveItem(TbItem item, String desc, Object object);
}
