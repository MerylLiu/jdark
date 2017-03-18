package com.iptv.app.controller.tv;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.core.controller.BaseController;

@Controller
@RequestMapping("/video")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class VideoController extends BaseController {

	@RequestMapping("/player")
	public ModelAndView player(@RequestParam Map param) {
		return view("/tv/video/player", param);
	}
}
