package com.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e3mall.content.service.ContentService;
import com.e3mall.pojo.TbContent;

/**
 * 商城首页Controller
 * @author ZNG
 *
 */

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@Value("${index.slider.cid}")
	private long indexSliderCid;
	
	@RequestMapping("/default")
	public String showIndex(Model model){
		// 展示首页之前查询数据库
		List<TbContent> ad1List = contentService.getContentListByCid(indexSliderCid);
		// 结果传给页面
		model.addAttribute("ad1List", ad1List);
		// 返回逻辑视图
		return "index";
	}
}
