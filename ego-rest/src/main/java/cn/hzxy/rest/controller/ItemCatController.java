package cn.hzxy.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.rest.pojo.ItemCatResult;
import cn.hzxy.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 使用MappingJacksonValue对象包装返回结果，并设置jsonp的回调方法
	 * @param callback
	 * @return MappingJacksonValue
	 * @throws Exception
	 */
	@RequestMapping("/item/all")
	@ResponseBody
	public MappingJacksonValue queryAll(String callback) {
		ItemCatResult result = itemCatService.queryAllCatogory();
		//包装jsonp	
		MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
		//设置包装的回调方法名
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}

}
