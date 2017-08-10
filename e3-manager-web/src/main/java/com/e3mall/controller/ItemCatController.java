package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.TreeNode;
import com.e3mall.service.ItemCatService;

/**
 * 商品类别选择Controller
 * @author ZNG
 *
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	/*
	 * 商品类别tree
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<TreeNode> getCatList(@RequestParam(value="id",defaultValue="0")long parentId){
		return itemCatService.getItemCatList(parentId);
	}
}
