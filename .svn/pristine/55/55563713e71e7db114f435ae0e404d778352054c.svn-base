package com.iptv.core.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {
	public Logger log = Logger.getLogger(this.getClass());

	public ModelAndView view(String view) {
		ModelAndView mv = new ModelAndView(view);

		return mv;
	}

	public ModelAndView view(String view, String attributeName, Object attributeValue) {
		ModelAndView mv = new ModelAndView(view);
		mv.addObject(attributeName, attributeValue);

		return mv;
	}

	public ModelAndView view(String view, Map<String, Object> data) {
		ModelAndView mv = new ModelAndView(view);
		mv.addAllObjects(data);

		return mv;
	}
}
