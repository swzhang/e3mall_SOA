package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.DataGridResult;
import com.e3mall.common.pojo.E3Result;
import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;


/**
 * 内容管理controller
 * @author ZNG
 *
 */
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public DataGridResult getContetList(Long categoryId, Integer page, Integer rows) {
		return contentService.getContentList(categoryId, page, rows);
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content) {
		return contentService.addContent(content);
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result editContent(TbContent content) {
		return contentService.editContent(content);
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(@RequestParam("ids")long id) {
		return contentService.deleteContent(id);
	}
}
