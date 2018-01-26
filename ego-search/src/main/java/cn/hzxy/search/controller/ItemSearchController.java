package cn.hzxy.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.search.pojo.SearchResult;
import cn.hzxy.search.service.ItemSearchService;
import cn.hzxy.utils.ExceptionUtil;

@Controller
public class ItemSearchController {
	@Autowired
	private ItemSearchService itemSearchService;

	@RequestMapping("/query")
	@ResponseBody
	public EgoResult search(@RequestParam(value = "kw") String queryString, @RequestParam(defaultValue = "1") Integer page) {
		if (StringUtils.isEmpty(queryString)) {
			return EgoResult.build(400, "查询条件是必须的参数");
		}
		SearchResult result = null;
		try {
			// 解决传参数据的乱码问题
			queryString = new String(queryString.getBytes("ISO8859-1"), "utf-8");
			result = itemSearchService.searchItem(queryString, page);
		} catch (Exception e) {
			e.printStackTrace();
			return EgoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return EgoResult.ok(result);
	}
}
