package com.iptv.app.controller.tv;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.iptv.app.service.CategoryService;
import com.iptv.core.controller.BaseController;

@Controller
public class CategoryController extends BaseController {
	@Resource
	private CategoryService categoryService;
}
