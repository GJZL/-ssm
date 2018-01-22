package cn.hzxy.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.hzxy.mapper.TbItemCatMapper;
import cn.hzxy.pojo.TbItemCat;
import cn.hzxy.pojo.TbItemCatExample;
import cn.hzxy.pojo.TbItemCatExample.Criteria;
import cn.hzxy.rest.pojo.ItemCat;
import cn.hzxy.rest.pojo.ItemCatResult;
import cn.hzxy.rest.service.ItemCatService;

public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public ItemCatResult queryAllCatogory() {
		ItemCatResult itemCatResult = new ItemCatResult();
		itemCatResult.setData(getItemCat(0L));
		return itemCatResult;
	}

	private List<?> getItemCat(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCatList = itemCatMapper.selectByExample(example);
		for (TbItemCat tbitemCat : itemCatList) {
			if (tbitemCat.getIsParent()) {
				ItemCat itemCat = new ItemCat();
				itemCat.setName(tbitemCat.getName());
				itemCat.setUrl("/products/"+tbitemCat.getId()+".html");
			}
		}
		return null;
	}

}
