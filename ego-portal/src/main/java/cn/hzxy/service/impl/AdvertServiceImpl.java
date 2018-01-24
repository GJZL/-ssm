package cn.hzxy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.hzxy.bean.AdvertResult;
import cn.hzxy.bean.EgoResult;
import cn.hzxy.pojo.TbContent;
import cn.hzxy.service.AdvertService;
import cn.hzxy.utils.HttpClientUtil;
import cn.hzxy.utils.JsonUtils;

@Service
public class AdvertServiceImpl implements AdvertService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;

	@Override
	public String getBigAdvert() {
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		EgoResult egoResult = EgoResult.formatToList(result, TbContent.class);
		List<AdvertResult> itemList = new ArrayList<>();
		if (egoResult.getStatus() == 200) {
			List<TbContent> contentList = (List<TbContent>) egoResult.getData();
			for (TbContent tbContent : contentList) {
				AdvertResult item = new AdvertResult();
				item.setSrc(tbContent.getPic());
				item.setHeight(240d);
				item.setWidth(670d);
				item.setSrcB(tbContent.getPic2());
				item.setHeightB(240d);
				item.setWidthB(550d);
				item.setAlt(tbContent.getTitleDesc());
				item.setHref(tbContent.getUrl());
				itemList.add(item);
			}

		}
		return JsonUtils.objectToJson(itemList);

	}

}
