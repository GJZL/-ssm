package cn.hzxy.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbItem;

public interface CartService {
	/**
	 * 添加商品至购物车
	 * @param itemId
	 * @param request
	 * @param response
	 * @return EgoResult
	 */
	EgoResult addItem(Long itemId, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 从cookie中获取商品
	 * @param request
	 * @return List<TbItem>
	 */
	List<TbItem> getCartItemsList(HttpServletRequest request);

	/**
	 * 修改商品数量
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return EgoResult
	 */
	EgoResult changeItemNum(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

	/**
	 * 删除购物车商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @return List<TbItem>
	 */
	List<TbItem> deleteItem(Long itemId, HttpServletRequest request, HttpServletResponse response);
}
