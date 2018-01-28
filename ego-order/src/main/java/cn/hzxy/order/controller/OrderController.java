package cn.hzxy.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.order.service.OrderService;
import cn.hzxy.pojo.Order;
import cn.hzxy.utils.ExceptionUtil;
import cn.hzxy.utils.JsonUtils;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public EgoResult createOrder(String json) {
		Order order = JsonUtils.jsonToPojo(json, Order.class);
		EgoResult result = null;
		try {
			result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
		} catch (Exception e) {
			e.printStackTrace();
			return EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}
}