package com.e3mall.service;

import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	DataGridResult getItemList(int page, int rows);
	E3Result addItem(TbItem item, String desc);
	E3Result queryItemDescById(long id);
	E3Result editItem(TbItem item, String desc);
	E3Result deleteItem(String ids);
	E3Result instockItem(String ids);
	E3Result reshelfItem(String ids);
}
