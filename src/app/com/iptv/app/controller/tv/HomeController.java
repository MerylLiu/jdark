package com.iptv.app.controller.tv;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.service.CategoryService;
import com.iptv.app.service.VideoService;
import com.iptv.core.common.KendoResult;
import com.iptv.core.controller.BaseController;
import com.iptv.core.utils.BaseUtil;

@Controller
@RequestMapping(value = { "/", "", "/home" })
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HomeController extends BaseController {
	@Resource
	private CategoryService categoryService;
	@Resource
	private VideoService videoService;

	@RequestMapping(value = { "/", "", "/index" })
	public ModelAndView index() {
		List categories = categoryService.getAllCategories();

		log.info("访问首页");
		BaseUtil.saveLog(4, "访问首页", "");
		return view("/tv/home/index");
	}

	@RequestMapping(value = "/videoList", method = RequestMethod.GET)
	public @ResponseBody KendoResult videoList(@RequestParam Map map) {
		KendoResult data = videoService.getHomeVideoPaged(map);
		return data;
	}
	
	@RequestMapping(value = "/previewList", method = RequestMethod.GET)
	public @ResponseBody List previewList(@RequestParam Map map) {
		Integer categoryId = Integer.valueOf(map.get("cid").toString());
		List data = videoService.getHomeVideoForPreview(categoryId);
		return data;
	}
}
