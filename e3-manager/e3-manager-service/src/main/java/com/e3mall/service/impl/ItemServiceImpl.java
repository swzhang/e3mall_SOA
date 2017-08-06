package com.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemExample;
import com.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;

	public TbItem getItemById(long itemId) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	public DataGridResult getItemList(int page, int rows) {
		// 1、设置分页信息
		PageHelper.startPage(page, rows);
		// 2、执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 3、取查询结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		// 取总记录数
		long total = pageInfo.getTotal();
		// 取商品列表
		// 4、把结果封装到DataGridResult中
		DataGridResult result = new DataGridResult();
		result.setTotal(total);
		result.setRows(list);
		// 5、返回结果
		return result;
	}

}
