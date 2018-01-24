package cn.hzxy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EUTreeNode;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.mapper.TbContentCategoryMapper;
import cn.hzxy.pojo.TbContentCategory;
import cn.hzxy.pojo.TbContentCategoryExample;
import cn.hzxy.pojo.TbContentCategoryExample.Criteria;
import cn.hzxy.service.ContentCategoryService;
import cn.hzxy.utils.HttpClientUtil;
import cn.hzxy.utils.JsonUtils;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${CONTENT_URL}")
	private String CONTENT_URL;
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getContentCategoryList(long parentid) {
		Map<String, String> param = new HashMap<>();
		param.put("id", parentid + "");
		String doGet = HttpClientUtil.doGet(REST_BASE_URL + CONTENT_URL, param);
		return JsonUtils.jsonToList(doGet, EUTreeNode.class);
	}

	@Override
	public EgoResult createContentCategory(TbContentCategory contentCategory) {
		Date date = new Date();
		contentCategory.setIsParent(false);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setCreated(date);
		contentCategory.setUpdated(date);
		int insertFalg = contentCategoryMapper.insertSelective(contentCategory);
		if (insertFalg == 1) {
			TbContentCategory contentCategoryParent = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
			if (!contentCategoryParent.getIsParent()) {
				contentCategoryParent.setIsParent(true);
				contentCategoryParent.setUpdated(date);
				contentCategoryMapper.updateByPrimaryKeySelective(contentCategoryParent);
			}
		}
		return EgoResult.ok(contentCategory);
	}

	@Override
	public EgoResult updateContentCategory(TbContentCategory contentCategory) {
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return EgoResult.ok(contentCategory);
	}

	@Override
	public EgoResult deleteContentCategory(Long id) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//先判断当前删除节点是子节点还是父节点
		if (contentCategory.getIsParent()) {
			//当前节点是父节点时，递归删除它的子节点
			criteria.andParentIdEqualTo(id);
			List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
			for (TbContentCategory category : contentCategoryList) {
				if (category.getIsParent()) {
					deleteContentCategory(category.getId());
				}else {
					contentCategoryMapper.deleteByPrimaryKey(category.getId());
				}
			}
		}else {
			//当前节点为子节点，判断它的父节点是否只有它一个子节点，若是：则将父节点变为子节点
			criteria.andParentIdEqualTo(contentCategory.getParentId());
			List<TbContentCategory> contentCategoryList = contentCategoryMapper.selectByExample(example);
			if (contentCategoryList.size()<=1) {
				TbContentCategory contentCategoryParent = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
				contentCategoryParent.setIsParent(false);
				contentCategoryMapper.updateByPrimaryKey(contentCategoryParent);
			}
		}
		contentCategoryMapper.deleteByPrimaryKey(id);
		return EgoResult.ok();
	}

}
