package cn.hzxy.service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.Order;

public interface OrderService {
	/**
	 * 创建订单
	 * @param order
	 * @return EgoResult
	 */
	EgoResult createService(Order order);
}
