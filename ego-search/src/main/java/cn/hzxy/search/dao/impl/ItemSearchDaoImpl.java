package cn.hzxy.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.hzxy.search.dao.ItemSearchDao;
import cn.hzxy.search.pojo.SearchItem;
import cn.hzxy.search.pojo.SearchResult;
@Repository
public class ItemSearchDaoImpl implements ItemSearchDao {

	@Autowired
	private HttpSolrServer httpSolrServer;

	@Override
	public SearchResult searchItem(SolrQuery solrQuery) {
		List<SearchItem> itemList=null;
		SolrDocumentList results=null;
		try {
			QueryResponse queryResponse = httpSolrServer.query(solrQuery);
			results = queryResponse.getResults();
			itemList=new ArrayList<>();
			for (SolrDocument document : results) {
				SearchItem item = new SearchItem();
				item.setId((Long) document.get("id"));
				item.setImage((String) document.get("item_image"));
				item.setPrice((Long) document.get("item_price"));
				item.setSell_point((String) document.get("item_sell_point"));
				item.setCategory_name((String) document.get("item_category_name"));
				// 取高亮显示
				Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
				List<String> list = highlighting.get(document.get("id")).get("item_title");
				String title = "";
				if (null != list && !list.isEmpty()) {
					title = list.get(0);
				} else {
					title = (String) document.get("item_title");
				}
				item.setTitle(title);
				itemList.add(item);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecordCount(results.getNumFound());
		return result;

	}

}
