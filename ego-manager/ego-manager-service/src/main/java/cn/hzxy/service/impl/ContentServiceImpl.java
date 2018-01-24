package cn.hzxy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hzxy.bean.EUDataGridResult;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.mapper.TbContentMapper;
import cn.hzxy.pojo.TbContent;
import cn.hzxy.pojo.TbContentExample;
import cn.hzxy.pojo.TbContentExample.Criteria;
import cn.hzxy.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public EUDataGridResult getContentList(Long categoryId, int page, int rows) {
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> contentList = contentMapper.selectByExample(example);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(contentList);
		PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public EgoResult saveContent(TbContent content) {
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		int insertFlag = contentMapper.insertSelective(content);
		if (insertFlag == 1) {
			return EgoResult.ok();
		}
		return null;
	}

	@Override
	public EgoResult editContent(TbContent content) {
		int updateFlag = contentMapper.updateByPrimaryKey(content);
		if (updateFlag == 1) {
			return EgoResult.ok();
		}
		return null;
	}

	@Override
	public EgoResult deleteContent(Long ids) {
		int deleteFlag = contentMapper.deleteByPrimaryKey(ids);
		if (deleteFlag == 1) {
			return EgoResult.ok();
		}
		return null;
	}

}
