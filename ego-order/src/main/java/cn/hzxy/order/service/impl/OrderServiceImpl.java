package cn.hzxy.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.dao.JedisClient;
import cn.hzxy.mapper.TbOrderItemMapper;
import cn.hzxy.mapper.TbOrderMapper;
import cn.hzxy.mapper.TbOrderShippingMapper;
import cn.hzxy.order.service.OrderService;
import cn.hzxy.pojo.TbOrder;
import cn.hzxy.pojo.TbOrderItem;
import cn.hzxy.pojo.TbOrderShipping;
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ORDER_ID_KEY}")
	private String ORDER_ID_KEY;
	@Value("${ORDER_BEGIN_ID}")
	private Long ORDER_BEGIN_ID;
	
	@Override
	public EgoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping) {
		String json = jedisClient.get(ORDER_ID_KEY);
		Long orderId=null;
		if (StringUtils.isEmpty(json)) {
			jedisClient.set(ORDER_ID_KEY, ORDER_BEGIN_ID.toString());
			orderId=ORDER_BEGIN_ID;
		}else {
			orderId = jedisClient.incr(ORDER_ID_KEY);
		}
		order.setOrderId(orderId.toString());
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		orderMapper.insert(order);
		for (TbOrderItem tbOrderItem : orderItemList) {
			Long orderItemId = jedisClient.incr("ORDER_ITEM_ID");
			tbOrderItem.setId(orderItemId.toString());
			tbOrderItem.setOrderId(orderId.toString());
			orderItemMapper.insert(tbOrderItem);
		}
		orderShipping.setOrderId(orderId.toString());
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		return EgoResult.ok(orderId.toString());
	}
}