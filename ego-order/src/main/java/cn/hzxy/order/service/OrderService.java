package cn.hzxy.order.service;

import java.util.List;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbOrder;
import cn.hzxy.pojo.TbOrderItem;
import cn.hzxy.pojo.TbOrderShipping;

public interface OrderService {
	/**
	 * 创建订单，将订单号存入cookie中
	 * @param order
	 * @param o
	 * @param orderShipping
	 * @return EgoResult
	 */
	EgoResult createOrder(TbOrder order, List<TbOrderItem> o, TbOrderShipping orderShipping);
}
