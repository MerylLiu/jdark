package com.iptv.app.controller.tv;

import java.util.HashMap;
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
		Map data = new HashMap();

		List categories = categoryService.getAllCategories();
		data.put("categories", categories);

		log.info("访问首页");
		BaseUtil.saveLog(4, "访问首页", "");
		return view("/tv/home/index", data);
	}

	@RequestMapping(value = "/videoList", method = RequestMethod.GET)
	public @ResponseBody KendoResult videoList(@RequestParam Map map) {
		if (map.get("categoryId") == null) {
			map.put("categoryId", 0);
		}
		log.info("获取首页视频" + map);
		BaseUtil.saveLog(4, "获取首页视频", map.toString());
		KendoResult data = videoService.getHomeVideoPaged(map);
		return data;
	}

	@RequestMapping(value = "/previewList", method = RequestMethod.GET)
	public @ResponseBody List previewList(@RequestParam Map map) {
		if(map.get("cid") == null){
			map.put("cid", 0);
		}

		Integer categoryId = Integer.valueOf(map.get("cid").toString());
		List data = videoService.getHomeVideoForPreview(categoryId);

		log.info("获取首页右侧视频" + map);
		BaseUtil.saveLog(4, "获取首页右侧视频", map.toString());
		return data;
	}

	@RequestMapping(value = "/categoryList", method = RequestMethod.GET)
	public @ResponseBody List categoryList() {
		List data = categoryService.getTopCategories();
		return data;
	}

	@RequestMapping("/more")
	public ModelAndView more() {
		log.info("获取首页更多视频");
		return view("/tv/video/more");
	}

	@RequestMapping(value = "/moreList", method = RequestMethod.GET)
	public @ResponseBody KendoResult moreList(@RequestParam Map map) {
		KendoResult data = videoService.getHomeVideoPaged(map);

		log.info("获取首页更多视频" + map);
		BaseUtil.saveLog(4, "获取首页更多视频", map.toString());
		return data;
	}
}
