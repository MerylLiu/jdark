package com.iptv.sys.controller.admin;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.core.common.KendoResult;
import com.iptv.sys.service.SysLogLoginService;

@Controller
@RequestMapping("/sys/logLogin")
@SuppressWarnings({ "rawtypes" })
public class SysLogLoginController extends AdminBaseController{
	@Resource
	SysLogLoginService sysLogLoginService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		return view();
	}
	
	@RequestMapping("/logLoginList")
	public @ResponseBody KendoResult dictionaryList(@RequestBody Map param){
		KendoResult data = sysLogLoginService.getLogLoginPaged(param);
		return data;
	}
}
