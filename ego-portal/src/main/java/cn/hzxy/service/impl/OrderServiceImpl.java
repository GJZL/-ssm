package cn.hzxy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.Order;
import cn.hzxy.service.OrderService;
import cn.hzxy.utils.HttpClientUtil;
import cn.hzxy.utils.JsonUtils;

@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;

	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	@Override
	public EgoResult createService(Order order) {
		String json = JsonUtils.objectToJson(order);
		Map<String, String> map=new HashMap<>();
		map.put("json", json);
		String resultStr = HttpClientUtil.doPost(ORDER_BASE_URL + ORDER_CREATE_URL, map);
		EgoResult egoResult = EgoResult.format(resultStr);
		return egoResult;
	}
}