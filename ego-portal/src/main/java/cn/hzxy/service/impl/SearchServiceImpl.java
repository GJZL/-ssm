package cn.hzxy.service.impl;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.search.pojo.SearchResult;
import cn.hzxy.service.SearchService;
import cn.hzxy.utils.HttpClientUtil;

@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult searchItemList(String queryString, Integer page) {
		// 查询参数
		Map<String, String> param = new HashMap<>();
		param.put("kw", queryString);
		param.put("page", page == null ? "1" : page.toString());
		// 调用ego-search提供的搜索服务
		String resultString = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		// 转换成egoResult对象
		EgoResult egoResult = EgoResult.formatToPojo(resultString, SearchResult.class);
		SearchResult searchResult = null;
		// 查询成功
		if (egoResult.getStatus() == 200) {
			// 取查询结果
			searchResult = (SearchResult) egoResult.getData();
		}
		return searchResult;
	}
}