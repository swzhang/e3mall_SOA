package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.E3Result;
import com.e3mall.common.pojo.TreeNode;
import com.e3mall.content.service.ContentCatService;

/**
 * 内容分类管理Controller
 * 
 * @author ZNG
 *
 */
@Controller
public class ContentCatController {

	@Autowired
	private ContentCatService contentCatService;

	/*
	 * 内容分类列表
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<TreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") long parentId) {
		return contentCatService.getContentCatList(parentId);
	}

	/*
	 * 添加内容分类
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public E3Result addContentCategory(long parentId, String name) {
		return contentCatService.addContentCategory(parentId, name);
	}

	/*
	 * 内容分类重命名
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCategory(long id, String name) {
		return contentCatService.updateContentCategory(id, name);
	}

	/*
	 * 内容分类删除
	 */
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public E3Result deleteContentCategory(long id) {
		return contentCatService.deleteContentCategory(id);
	}
}
