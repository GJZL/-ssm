package cn.hzxy.service;

import cn.hzxy.bean.EUDataGridResult;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbContent;

/**
 * 内容处理
 * @author 大闲鱼
 *
 */
public interface ContentService {
	/**
	 * 分页查询所有内容
	 * @param parentid
	 * @return List<EUTreeNode>
	 */
	EUDataGridResult getContentList(Long categoryId,int page,int rows);

	/**
	 * 新增内容
	 * @param content
	 * @return EgoResult
	 */
	EgoResult saveContent(TbContent content);

	/**
	 * 修改内容
	 * @param content
	 * @return EgoResult
	 */
	EgoResult editContent(TbContent content);

	/**
	 * 删除内容
	 * @param ids
	 * @return EgoResult
	 */
	EgoResult deleteContent(Long ids);

}
