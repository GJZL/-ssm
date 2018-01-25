package cn.hzxy.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.hzxy.dao.JedisClient;
import cn.hzxy.mapper.TbItemCatMapper;
import cn.hzxy.pojo.TbItemCat;
import cn.hzxy.pojo.TbItemCatExample;
import cn.hzxy.pojo.TbItemCatExample.Criteria;
import cn.hzxy.rest.pojo.ItemCat;
import cn.hzxy.rest.pojo.ItemCatResult;
import cn.hzxy.rest.service.ItemCatService;
import cn.hzxy.utils.JsonUtils;
@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_CAT_KEY}")
	private String ITEM_CAT_KEY;

	@Override
	public ItemCatResult queryAllCatogory() {
		//先查Redis，若有则直接返回
		try {
			String jsonResult = jedisClient.hget(ITEM_CAT_KEY, "itemcat");
			if (!StringUtils.isEmpty(jsonResult)) {
				return JsonUtils.jsonToPojo(jsonResult, ItemCatResult.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//查数据库
		ItemCatResult itemCatResult = new ItemCatResult();
		itemCatResult.setData(getItemCat(0L));
		//设置到Redis
		try {
			jedisClient.hset(ITEM_CAT_KEY, "itemcat", JsonUtils.objectToJson(itemCatResult));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
