package com.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.utils.IDUtils;
import com.e3mall.mapper.TbItemDescMapper;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.pojo.TbItemExample;
import com.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

	public TbItem getItemById(long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
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

	public E3Result addItem(TbItem item, String desc) {
		// 1、生成商品id
		long itemId = IDUtils.genItemId();
		// 2、补全TbItem对象的属性
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		// 7、调用E3Result.ok()方法
		return E3Result.ok();
	}
	
	public E3Result queryItemDescById(long id) {
		// 获取商品描述TbItemDesc对象
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		// 返回商品描述信息
		return new E3Result(itemDesc);
	}

	public E3Result editItem(TbItem item, String desc) {
		// 获取商品id
		Long id = item.getId();
		TbItem tbItem = itemMapper.selectByPrimaryKey(id);
		// 1、设置商品属性
		item.setCreated(tbItem.getCreated());
		item.setStatus(tbItem.getStatus());
		Date date = new Date();
		item.setUpdated(date);
		// 2、更新商品数据
		itemMapper.updateByPrimaryKey(item);
		// 3、获取TbItemDesc对象
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		// 4、更新TbItemDesc的属性
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);
		// 5、向商品描述表插入数据 
		itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);
		// 6、E3Result.ok()
		return E3Result.ok();
	}

	public E3Result deleteItem(String ids) {
		setItemStatus(ids,3);
		return E3Result.ok();
	}

	public E3Result instockItem(String ids) {
		setItemStatus(ids,2);
		return E3Result.ok();
	}
	
	public E3Result reshelfItem(String ids) {
		setItemStatus(ids,1);
		return E3Result.ok();
	}

	/**
	 * 设置商品状态
	 * @param ids 商品编号
	 * @param status 商品状态
	 */
	private void setItemStatus(String ids,int status) {
		// 获取各个商品id
		String[] itemIds = ids.split(",");
		for (String itemId : itemIds) {
			long id = new Long(itemId);
			// 获取商品信息
			TbItem item = itemMapper.selectByPrimaryKey(id);
			// 设置商品状态，1-正常，2-下架，3-删除
			item.setStatus((byte)status);
			// 设置修改时间
			item.setUpdated(new Date());
			// 更新商品信息
			itemMapper.updateByPrimaryKey(item);
		}
	}

}
