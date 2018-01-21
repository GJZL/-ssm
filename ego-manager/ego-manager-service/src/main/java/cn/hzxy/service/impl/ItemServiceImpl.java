package cn.hzxy.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hzxy.bean.EUDataGridResult;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.mapper.TbItemDescMapper;
import cn.hzxy.mapper.TbItemMapper;
import cn.hzxy.pojo.TbItem;
import cn.hzxy.pojo.TbItemDesc;
import cn.hzxy.pojo.TbItemExample;
import cn.hzxy.service.ItemService;
import cn.hzxy.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public TbItem findItemById(long id) {
		return itemMapper.selectByPrimaryKey(id);
	}

	@Override
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(itemList);
		PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public EgoResult saveItem(TbItem item, String desc, Object object) {
		EgoResult egoResult = null;
		Date date = new Date();
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte) 1);
		int itemFlag = itemMapper.insert(item);
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		int itemDescFlag = itemDescMapper.insert(itemDesc);
		if (1 == itemFlag && 1 == itemDescFlag) {
			egoResult = new EgoResult();
			egoResult.setStatus(200);
		}
		return egoResult;
	}

}
