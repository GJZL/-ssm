package cn.hzxy.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbItem;
import cn.hzxy.service.CartService;
import cn.hzxy.utils.CookieUtils;
import cn.hzxy.utils.HttpClientUtil;
import cn.hzxy.utils.JsonUtils;

@Service
public class CartServiceImpl implements CartService {

	@Value("${ITEM_BASE_URL}")
	private String ITEM_BASE_URL;
	@Value("${ADD_ITEM_URL}")
	private String ADD_ITEM_URL;
	@Value("${ITEM_COOKIE_KEY}")
	private String ITEM_COOKIE_KEY;
	@Value("${COOKIE_TIME}")
	private int COOKIE_TIME;

	@Override
	public EgoResult addItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		TbItem item = getItem(itemId);
		if (item == null)
			return EgoResult.build(400, "未查询到该商品信息");
		List<TbItem> cartItems = getItemListFromCookie(request);
		boolean flag = false;
		for (TbItem tbItem : cartItems) {
			if (tbItem.getId().longValue() == itemId.longValue()) {
				tbItem.setNum(tbItem.getNum() + 1);
				flag = true;
				break;
			}
		}
		if (!flag) {
			item.setNum(1);
			cartItems.add(item);
		}
		CookieUtils.setCookie(request, response, ITEM_COOKIE_KEY, JsonUtils.objectToJson(cartItems), COOKIE_TIME);
		return EgoResult.ok(cartItems);
	}

	@Override
	public List<TbItem> getCartItemsList(HttpServletRequest request) {
		List<TbItem> itemsList = getItemListFromCookie(request);
		return itemsList;
	}

	@Override
	public EgoResult changeItemNum(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
		List<TbItem> cartItems = getItemListFromCookie(request);
		for (TbItem item : cartItems) {
			if (item.getId() == itemId) {
				item.setNum(num);
				break;
			}
		}
		CookieUtils.setCookie(request, response, ITEM_COOKIE_KEY, JsonUtils.objectToJson(cartItems), COOKIE_TIME);
		return EgoResult.ok();
	}

	@Override
	public List<TbItem> deleteItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		List<TbItem> cartItems = getCartItemsList(request);
		for (TbItem item : cartItems) {
			if (item.getId().longValue() == itemId.longValue()) {
				cartItems.remove(item);
				break;
			}
		}
		CookieUtils.setCookie(request, response, ITEM_COOKIE_KEY, JsonUtils.objectToJson(cartItems), COOKIE_TIME);
		return cartItems;
	}

	/**
	 * 获取cookie中商品信息
	 * @param request
	 * @return List<TbItem>
	 */
	private List<TbItem> getItemListFromCookie(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, ITEM_COOKIE_KEY, true);
		List<TbItem> cartItems = null;
		if (!StringUtils.isEmpty(cookieValue)) {
			cartItems = JsonUtils.jsonToList(cookieValue, TbItem.class);
		} else {
			cartItems = new ArrayList<>();
		}
		return cartItems;
	}

	/**
	 * 跨域请求获取商品信息
	 * @param itemId
	 * @return TbItem
	 */
	private TbItem getItem(Long itemId) {
		String json = HttpClientUtil.doGet(ITEM_BASE_URL + ADD_ITEM_URL + itemId.toString());
		return JsonUtils.jsonToPojo(json, TbItem.class);
	}

}
