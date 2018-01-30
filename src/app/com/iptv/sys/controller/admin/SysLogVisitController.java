package com.iptv.sys.controller.admin;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.core.common.KendoResult;
import com.iptv.sys.controller.admin.AdminBaseController;
import com.iptv.sys.service.SysLogVisitService;

@Controller
@RequestMapping("/sys/logVisit")
@SuppressWarnings({ "rawtypes" })
public class SysLogVisitController extends AdminBaseController {

	@Resource
	private SysLogVisitService sysLogVisitService;

	@RequestMapping("/index")
	public ModelAndView index() {
		return view("/sys/logVisit/index");
	}

	@RequestMapping("/logVisitList")
	public @ResponseBody KendoResult logList(@RequestBody Map param) {
		KendoResult data = sysLogVisitService.getLogVisitPaged(param);
		return data;
	}
}
