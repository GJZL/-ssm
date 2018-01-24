package cn.hzxy.rest.service;

import java.util.List;

import cn.hzxy.bean.EUTreeNode;

/**
 * 查询内容分类
 * @author 大闲鱼
 *
 */
public interface ContentCategoryService {
	/**
	 * 查询所有内容分类
	 * @param parentid
	 * @return List<EUTreeNode>
	 */
	List<EUTreeNode> getContentCategoryList(long parentid);
}
