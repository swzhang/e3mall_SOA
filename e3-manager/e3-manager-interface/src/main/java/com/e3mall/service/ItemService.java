package com.e3mall.service;

import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	DataGridResult getItemList(int page, int rows);
}
