package com.iptv.app.controller.tv;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iptv.app.service.CategoryService;
import com.iptv.core.controller.BaseController;

@Controller
@SuppressWarnings({ "rawtypes"})
public class CategoryController extends BaseController {
	@Resource
	private CategoryService categoryService;

	public @ResponseBody List topCategories(){
		List data  = categoryService.getAllCategories();
		return data;
	}
}
