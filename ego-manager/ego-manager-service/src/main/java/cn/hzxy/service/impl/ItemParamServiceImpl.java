package cn.hzxy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.mapper.TbItemParamMapper;
import cn.hzxy.pojo.TbItemParam;
import cn.hzxy.pojo.TbItemParamExample;
import cn.hzxy.pojo.TbItemParamExample.Criteria;
import cn.hzxy.service.ItemParamService;


@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public EgoResult findItemParamById(long id) {
		TbItemParamExample example=new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(id);
		List<TbItemParam> itemParamList = itemParamMapper.selectByExampleWithBLOBs(example);
		EgoResult egoResult = null;
		if (itemParamList!=null&&itemParamList.size()>0) {
			egoResult = new EgoResult();
			egoResult.setStatus(200);
			egoResult.setData(itemParamList.get(0));
		}
		return egoResult;
	}
}
