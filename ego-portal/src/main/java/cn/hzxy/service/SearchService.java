package cn.hzxy.service;

import cn.hzxy.bean.SearchResult;

public interface SearchService {

	/**
	 * 调用search查询商品
	 * @param queryString
	 * @param page
	 * @return SearchResult
	 */
	SearchResult searchItemList(String queryString, Integer page);
}
