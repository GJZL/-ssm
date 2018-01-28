package cn.hzxy.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.Order;
import cn.hzxy.pojo.TbUser;
import cn.hzxy.service.OrderService;
import cn.hzxy.utils.ExceptionUtil;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createOrder(HttpServletRequest request, Order order, Model model) {
		// 从request中取用户信息
		TbUser user = (TbUser) request.getAttribute("user");
		// 把用户信息补充到order对象中
		order.setUserId(user.getId());
		order.setBuyerNick(user.getUsername());
		// 提交订单
		EgoResult result = null;
		try {
			result = orderService.createService(order);
			// 订单创建成功
			if (result.getStatus() == 200) {
				model.addAttribute("orderId", result.getData());
				model.addAttribute("payment", order.getPayment());
				// 两天后送达
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Calendar now = Calendar.getInstance();  
		        now.setTime(new Date());  
		        now.set(Calendar.DATE, now.get(Calendar.DATE) + 2); 
				model.addAttribute("date", sdf.format(now.getTime()));
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		// 订单创建失败
		model.addAttribute("message", result.getMsg());
		return "error/exception";
	}
}