package cn.hzxy.rest.service;

import cn.hzxy.rest.pojo.ItemCatResult;

/**
 * 处理商品分类
 * @author 大闲鱼
 *
 */
public interface ItemCatService {

	/**
	 * 查询全部商品分类，用于前台页面显示
	 * @return ItemCatResult
	 */
	ItemCatResult queryAllCatogory();
}
