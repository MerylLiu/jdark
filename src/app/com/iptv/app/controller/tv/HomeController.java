package com.iptv.app.controller.tv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.service.CategoryService;
import com.iptv.app.service.VideoService;
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
		List videos = videoService.getHomeVideo();

		Map data = new HashMap();
		data.put("categories", categories);
		data.put("videos", videos);
		data.put("nextVideos", videos);

		log.info("访问首页");
		BaseUtil.saveLog(4, "访问首页", "");
		return view("/tv/home/index", data);
	}

	public @ResponseBody List videoList() {
		return null;
	}
}
