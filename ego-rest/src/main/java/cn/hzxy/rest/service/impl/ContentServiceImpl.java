package cn.hzxy.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.mapper.TbContentMapper;
import cn.hzxy.pojo.TbContent;
import cn.hzxy.pojo.TbContentExample;
import cn.hzxy.pojo.TbContentExample.Criteria;
import cn.hzxy.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EgoResult getContentList(long cid) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example);
		return EgoResult.ok(list);
	}

}
