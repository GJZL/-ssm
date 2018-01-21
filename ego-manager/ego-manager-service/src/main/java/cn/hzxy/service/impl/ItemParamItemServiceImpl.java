package cn.hzxy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.mapper.TbItemParamItemMapper;
import cn.hzxy.pojo.TbItemParamItem;
import cn.hzxy.pojo.TbItemParamItemExample;
import cn.hzxy.pojo.TbItemParamItemExample.Criteria;
import cn.hzxy.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired	
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public EgoResult findItemParamItemById(long id) {
		TbItemParamItemExample example=new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);
		List<TbItemParamItem> itemParamItemList = itemParamItemMapper.selectByExampleWithBLOBs(example);
		EgoResult egoResult = null;
		if (itemParamItemList!=null&&itemParamItemList.size()>0) {
			egoResult = new EgoResult();
			egoResult.setStatus(200);
			egoResult.setData(itemParamItemList.get(0));
		}
		return egoResult;
	}
}
