package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.pojo.TbItem;
import com.e3mall.service.ItemService;

/**
 * 商品管理Controller
 * 
 * @author ZNG
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	/*
	 * 查看商品信息
	 */
	@RequestMapping("/item/{itemid}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemid) {
		return itemService.getItemById(itemid);
	}

	/*
	 * 商品列表展示
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public DataGridResult getItemList(Integer page, Integer rows) {
		return itemService.getItemList(page, rows);
	}

	/*
	 * 添加商品
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result saveItem(TbItem item, String desc) {
		return itemService.addItem(item, desc);
	}
	
	/*
	 * 加载商品描述
	 */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public E3Result queryItemDescById(@PathVariable long id) {
		return itemService.queryItemDescById(id);
	}

	/*
	 * 修改商品
	 */
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public E3Result editItem(TbItem item, String desc) {
		return itemService.editItem(item, desc);
	}

	/*
	 * 删除商品
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result deleteItem(String ids) {
		return itemService.deleteItem(ids);
	}

	/*
	 * 商品下架
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result instockItem(String ids) {
		return itemService.instockItem(ids);
	}
	
	/*
	 * 商品上架
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result reshelfItem(String ids) {
		return itemService.reshelfItem(ids);
	}
	
}
