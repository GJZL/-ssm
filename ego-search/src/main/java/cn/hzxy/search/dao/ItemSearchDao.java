package cn.hzxy.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import cn.hzxy.search.pojo.SearchResult;

public interface ItemSearchDao {
	/**
	 * 查询solr
	 * @param solrQuery
	 * @return
	 */
	SearchResult searchItem(SolrQuery solrQuery);
}
