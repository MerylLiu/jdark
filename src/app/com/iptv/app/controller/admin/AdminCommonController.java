package com.iptv.app.controller.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iptv.core.common.KendoResult;
import com.iptv.core.service.SysParamService;

@Controller
@RequestMapping("/admin/common")
@SuppressWarnings({"rawtypes"})
public class AdminCommonController extends AdminBaseController {
	@Resource
	private SysParamService sysParamService;

	@RequestMapping(value="/sysParams",method = RequestMethod.GET)
	public @ResponseBody KendoResult sysParams(@RequestParam Map<String,Object> param){
		List data = sysParamService.getSysParam(param.get("key").toString(), false);
		return new KendoResult(data);
	}
}