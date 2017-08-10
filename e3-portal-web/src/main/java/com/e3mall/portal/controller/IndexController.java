package com.e3mall.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商城首页Controller
 * @author ZNG
 *
 */

@Controller
public class IndexController {
	
	@RequestMapping("/default")
	public String showIndex(){
		return "index";
	}
}
