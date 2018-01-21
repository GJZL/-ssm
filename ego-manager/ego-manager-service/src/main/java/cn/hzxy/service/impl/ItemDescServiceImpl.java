package cn.hzxy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.EgoResult;
import cn.hzxy.mapper.TbItemDescMapper;
import cn.hzxy.mapper.TbItemMapper;
import cn.hzxy.mapper.TbItemParamItemMapper;
import cn.hzxy.pojo.TbItem;
import cn.hzxy.pojo.TbItemDesc;
import cn.hzxy.pojo.TbItemParamItem;
import cn.hzxy.pojo.TbItemParamItemExample;
import cn.hzxy.pojo.TbItemParamItemExample.Criteria;
import cn.hzxy.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public EgoResult findItemDescById(long id) {
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		EgoResult egoResult = null;
		if (itemDesc != null) {
			egoResult = new EgoResult();
			egoResult.setStatus(200);
			egoResult.setData(itemDesc);
		}
		return egoResult;
	}

	@Override
	public EgoResult updateItem(TbItem item, String desc, String itemParams, Long itemParamId) {
		Date date = new Date();
		item.setUpdated(date);
		item.setStatus((byte) 1);
		int itemFlag = itemMapper.updateByPrimaryKeySelective(item);
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);
		int itemDescFlag = itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		TbItemParamItem itemParamItem = new TbItemParamItem();
		int itemParamItemFlag = 0;
		itemParamItem.setParamData(itemParams);
		if (itemParamId == null) {
			itemParamItem.setItemId(item.getId());
			itemParamItem.setCreated(date);
			itemParamItem.setUpdated(date);
			itemParamItemFlag = itemParamItemMapper.insertSelective(itemParamItem);
		} else {
			itemParamItem.setId(itemParamId);
			itemParamItemFlag = itemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);
		}
		EgoResult egoResult = null;
		if (itemFlag == 1 && itemDescFlag == 1 && itemParamItemFlag == 1) {
			egoResult = new EgoResult();
			egoResult.setStatus(200);
		}
		return egoResult;
	}

	@Override
	public EgoResult deleteItem(Long[] ids) {
		EgoResult egoResult = null;
		int itemFlag = 0;
		if (ids.length > 0 && ids != null) {
			for (Long id : ids) {
				itemMapper.deleteByPrimaryKey(id);
				if (null != itemDescMapper.selectByPrimaryKey(id)) {
					itemDescMapper.deleteByPrimaryKey(id);
				}
				TbItemParamItemExample example = new TbItemParamItemExample();
				Criteria criteria = example.createCriteria();
				criteria.andItemIdEqualTo(id);
				List<TbItemParamItem> itemParamItemList = itemParamItemMapper.selectByExample(example);
				if (itemParamItemList != null && itemParamItemList.size() > 0) {
					TbItemParamItem itemParamItem = itemParamItemList.get(0);
					itemParamItemMapper.deleteByPrimaryKey(itemParamItem.getId());
				}
				itemFlag++;
			}
			if (ids.length == itemFlag) {
				egoResult = new EgoResult();
				egoResult.setStatus(200);
			}
		}
		return egoResult;
	}

	@Override
	public EgoResult instockItem(Long[] ids) {
		EgoResult egoResult = null;
		int itemFlag = 0;
		if (ids.length > 0 && null != ids) {
			for (Long id : ids) {
				TbItem record = new TbItem();
				record.setId(id);
				record.setStatus((byte) 2);
				itemMapper.updateByPrimaryKeySelective(record);
				itemFlag++;
			}
			if (ids.length == itemFlag) {
				egoResult = new EgoResult();
				egoResult.setStatus(200);
			}
		}
		return egoResult;
	}

	@Override
	public EgoResult reshelfItem(Long[] ids) {
		EgoResult egoResult = null;
		int itemFlag = 0;
		if (ids.length > 0 && null != ids) {
			for (Long id : ids) {
				TbItem record = new TbItem();
				record.setId(id);
				record.setStatus((byte) 1);
				itemMapper.updateByPrimaryKeySelective(record);
				itemFlag++;
			}
			if (ids.length == itemFlag) {
				egoResult = new EgoResult();
				egoResult.setStatus(200);
			}
		}
		return egoResult;
	}
}
