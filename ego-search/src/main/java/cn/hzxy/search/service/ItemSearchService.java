package cn.hzxy.search.service;

import cn.hzxy.search.pojo.SearchResult;

public interface ItemSearchService {

	/**
	 * 根据搜索词查solr
	 * @param queryString
	 * @param page
	 * @return SearchResult
	 */
	SearchResult searchItem(String queryString, Integer page);
}
