package cn.hzxy.service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbItem;

/**
 * 后台商品操作：改、删、上下架
 * @author 大闲鱼
 *
 */
public interface ItemDescService {

	/**
	 * 查询商品
	 * @param id
	 * @return EgoResult
	 */
	EgoResult findItemDescById(long id);

	/**
	 * 修改商品
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @param itemParamId
	 * @return EgoResult
	 */
	EgoResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId);

	/**
	 * 删除商品
	 * @param ids
	 * @return EgoResult
	 */
	EgoResult deleteItem(Long[] ids);

	/**
	 * 下架商品
	 * @param ids
	 * @return EgoResult
	 */
	EgoResult instockItem(Long[] ids);

	/**
	 * 上架商品
	 * @param ids
	 * @return EgoResult
	 */
	EgoResult reshelfItem(Long[] ids);
}
