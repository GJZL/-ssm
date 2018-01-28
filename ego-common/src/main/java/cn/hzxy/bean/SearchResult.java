package cn.hzxy.bean;

import java.util.List;

/**
 * solr返回的结果
 * @author 大闲鱼
 *
 */
public class SearchResult {
	private Long recordCount;
	private List<SearchItem> itemList;
	private Integer pageCount;
	private Integer curPage;

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	public List<SearchItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

}
