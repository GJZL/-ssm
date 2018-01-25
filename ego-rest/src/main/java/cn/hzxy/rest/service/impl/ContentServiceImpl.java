package cn.hzxy.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.dao.JedisClient;
import cn.hzxy.mapper.TbContentMapper;
import cn.hzxy.pojo.TbContent;
import cn.hzxy.pojo.TbContentExample;
import cn.hzxy.pojo.TbContentExample.Criteria;
import cn.hzxy.rest.service.ContentService;
import cn.hzxy.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public EgoResult getContentList(long cid) {
		//先查Redis，若有则直接返回
		try {
			String jsonResult = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, cid+"");
			if (!StringUtils.isEmpty(jsonResult)) {
				List<TbContent> contentList = JsonUtils.jsonToList(jsonResult, TbContent.class);
				return EgoResult.ok(contentList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//查数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example);
		//设置到Redis
		try {
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EgoResult.ok(list);
	}

}
