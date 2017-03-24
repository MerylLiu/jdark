package com.iptv.app.controller.tv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.service.CategoryService;
import com.iptv.app.service.VideoService;
import com.iptv.core.common.Configuration;
import com.iptv.core.common.KendoResult;
import com.iptv.core.controller.BaseController;
import com.iptv.core.utils.BaseUtil;

@Controller
@RequestMapping("/video")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class VideoController extends BaseController {
	@Resource
	private CategoryService categoryService;
	@Resource
	private VideoService videoService;

	@RequestMapping("/player")
	public ModelAndView player(@RequestParam Map param) {
		return view("/tv/video/player", param);
	}

	@RequestMapping("/detail")
	public ModelAndView detail(@RequestParam Map param) {
		Integer videoId = Integer.valueOf(param.get("id").toString());
		Map data = videoService.getDetail(videoId);

		log.info("获取视频详细数据");
		BaseUtil.saveLog(4, "获取视频详细数据", param.toString());
		return view("/tv/video/detail", data);
	}

	@RequestMapping("/qrcode")
	public ModelAndView qrcode(@RequestParam Map param) {
		Integer videoId = Integer.valueOf(param.get("id").toString());
		Map data = videoService.getDetail(videoId);

		log.info("获取视频详细数据-二维码");
		BaseUtil.saveLog(4, "获取视频详细数据-二维码", param.toString());
		return view("/tv/video/qrcode", data);
	}

	@RequestMapping("/search")
	public ModelAndView search() {
		Map data = new HashMap();
		data.put("staticUrl", Configuration.webCfg.getProperty("static.url").toString());
		return view("/tv/video/search", data);
	}

	@RequestMapping("/searchList")
	public @ResponseBody KendoResult searchList(@RequestParam Map param) {
		KendoResult data = videoService.getSearched(param.get("name").toString());

		log.info("获取视频搜索结果");
		BaseUtil.saveLog(4, "获取视频搜索结果", param.toString());
		return data;
	}

	@RequestMapping("/more")
	public ModelAndView more(@RequestParam Map param) {
		Map data = new HashMap();

		List categories = categoryService.getAllCategories();
		data.put("categories", categories);
		data.putAll(param);
		data.put("staticUrl", Configuration.webCfg.getProperty("static.url").toString());

		log.info("获取更多视频(搜索)");
		return view("/tv/video/more", data);
	}

	@RequestMapping("/moreList")
	public @ResponseBody KendoResult moreList(@RequestParam Map param) {
		KendoResult data = videoService.getMoreVideoPaged(param);

		log.info("获取更多视频(搜索)" + param);
		BaseUtil.saveLog(4, "获取更多视频(搜索)", param.toString());
		return data;
	}
}