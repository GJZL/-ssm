package cn.hzxy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EUTreeNode;
import cn.hzxy.mapper.TbItemCatMapper;
import cn.hzxy.pojo.TbItemCat;
import cn.hzxy.pojo.TbItemCatExample;
import cn.hzxy.pojo.TbItemCatExample.Criteria;
import cn.hzxy.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EUTreeNode> getItemCatList(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCatList = itemCatMapper.selectByExample(example);
		List<EUTreeNode> treeNodeList = new ArrayList<>();
		EUTreeNode treeNode = null;
		for (TbItemCat itemCat : itemCatList) {
			treeNode=new EUTreeNode();
			treeNode.setId(itemCat.getId());
			treeNode.setText(itemCat.getName());
			treeNode.setState(itemCat.getIsParent() ? "closed" : "open");
			treeNodeList.add(treeNode);
		}
		return treeNodeList;
	}

}
