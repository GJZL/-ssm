package cn.hzxy.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hzxy.mapper.TbItemCatMapper;
import cn.hzxy.pojo.TbItemCat;
import cn.hzxy.pojo.TbItemCatExample;
import cn.hzxy.pojo.TbItemCatExample.Criteria;
import cn.hzxy.rest.pojo.ItemCat;
import cn.hzxy.rest.pojo.ItemCatResult;
import cn.hzxy.rest.service.ItemCatService;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public ItemCatResult queryAllCatogory() {
		ItemCatResult itemCatResult = new ItemCatResult();
		itemCatResult.setData(getItemCat(0L));
		return itemCatResult;
	}

	/**
	 * 回调查询商品分类
	 * @param parentId
	 * @return List<?>
	 */
	private List<?> getItemCat(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCatList = itemCatMapper.selectByExample(example);
		List<Object> datalist = new ArrayList<>();
		for (TbItemCat tbitemCat : itemCatList) {
			if (tbitemCat.getIsParent()) {
				ItemCat itemCat = new ItemCat();
				itemCat.setName(tbitemCat.getName());
				itemCat.setUrl("/category/" + tbitemCat.getId() + ".html");
				itemCat.setItem(getItemCat(tbitemCat.getId()));
				datalist.add(itemCat);
			} else {
				String catItem = "/item/" + tbitemCat.getId() + ".html|" + tbitemCat.getName();
				datalist.add(catItem);
			}
		}
		return datalist;
	}

}
