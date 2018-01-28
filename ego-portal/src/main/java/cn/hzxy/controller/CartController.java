package cn.hzxy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbItem;
import cn.hzxy.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	/**
	 * 添加商品至购物车
	 * @param itemId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping("/add/{itemId}")
	public String addItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response, Model model) {
		EgoResult result = cartService.addItem(itemId, request, response);
		if (result.getStatus() != 200) {
			model.addAttribute("message", result.getMsg());
			return "error/exception";
		}
		model.addAttribute("cartList", result.getData());
		return "cart";
	}

	/**
	 * 获取购物车商品列表
	 * @param request
	 * @param mode
	 * @return String
	 */
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, Model mode) {
		// 取购物车信息
		List<TbItem> list = cartService.getCartItemsList(request);
		mode.addAttribute("cartList", list);
		return "cart";
	}

	/**
	 * 修改购物车商品数量
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return EgoResult
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public EgoResult updateNumById(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response) {
		EgoResult result = cartService.changeItemNum(itemId, num, request, response);
		return result;
	}

	/**
	 * 删除购物车商品
	 * @param itemId
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteItemById(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TbItem> list = cartService.deleteItem(itemId, request, response);
		model.addAttribute("cartList", list);
		return "cart";
	}

}
