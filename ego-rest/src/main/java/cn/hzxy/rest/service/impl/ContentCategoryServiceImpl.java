package cn.hzxy.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EUTreeNode;
import cn.hzxy.mapper.TbContentCategoryMapper;
import cn.hzxy.pojo.TbContentCategory;
import cn.hzxy.pojo.TbContentCategoryExample;
import cn.hzxy.pojo.TbContentCategoryExample.Criteria;
import cn.hzxy.rest.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getContentCategoryList(long parentid) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> treeNodeList = new ArrayList<>();
		for (TbContentCategory contentCategory : contentCategoryList) {
			EUTreeNode treeNode = new EUTreeNode();
			treeNode.setId(contentCategory.getId());
			treeNode.setText(contentCategory.getName());
			treeNode.setState(contentCategory.getIsParent() ? "closed" : "open");
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}

}
