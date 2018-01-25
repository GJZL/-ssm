package cn.hzxy.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.hzxy.bean.EUTreeNode;
import cn.hzxy.dao.JedisClient;
import cn.hzxy.mapper.TbContentCategoryMapper;
import cn.hzxy.pojo.TbContentCategory;
import cn.hzxy.pojo.TbContentCategoryExample;
import cn.hzxy.pojo.TbContentCategoryExample.Criteria;
import cn.hzxy.rest.service.ContentCategoryService;
import cn.hzxy.utils.JsonUtils;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_CATEGORY_KEY}")
	private String CONTENT_CATEGORY_KEY;

	@Override
	public List<EUTreeNode> getContentCategoryList(long parentid) {
		//先查Redis，若有则直接返回
		try {
			String jsonResult = jedisClient.hget(CONTENT_CATEGORY_KEY, "contentcategory");
			if (!StringUtils.isEmpty(jsonResult)) {
				return JsonUtils.jsonToList(jsonResult, EUTreeNode.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//查数据库
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
		//设置到Redis
		try {
			jedisClient.hset(CONTENT_CATEGORY_KEY, "contentcategory", JsonUtils.objectToJson(treeNodeList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treeNodeList;
	}

}
