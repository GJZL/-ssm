package cn.hzxy.service;

import java.util.List;

import cn.hzxy.bean.EUTreeNode;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbContentCategory;

/**
 * 内容分类处理
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

	/**
	 * 添加内容分类
	 * @param contentCategory
	 * @return EgoResult
	 */
	EgoResult createContentCategory(TbContentCategory contentCategory);

	/**
	 * 修改内容分类
	 * @param contentCategory
	 * @return EgoResult
	 */
	EgoResult updateContentCategory(TbContentCategory contentCategory);

	/**
	 * 删除内容分类
	 * @param id
	 * @return EgoResult
	 */
	EgoResult deleteContentCategory(Long id);
}
