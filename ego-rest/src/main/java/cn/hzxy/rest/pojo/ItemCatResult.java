package cn.hzxy.rest.pojo;

import java.util.List;

/**
 * 将所以商品分类放入list中
 * @author 大闲鱼
 *
 */
public class ItemCatResult {

	private List<?> data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
}
