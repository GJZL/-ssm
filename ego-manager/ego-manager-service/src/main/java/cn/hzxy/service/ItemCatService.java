package cn.hzxy.service;

import java.util.List;

import cn.hzxy.bean.EUTreeNode;

public interface ItemCatService {

	List<EUTreeNode> getItemCatList(Long parentId);
}
