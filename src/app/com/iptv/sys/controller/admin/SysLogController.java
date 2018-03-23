package com.iptv.sys.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.sys.service.LogService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.controller.admin.AdminBaseController;

@Controller
@RequestMapping("/sys/log")
@SuppressWarnings({"rawtypes","unchecked"})
public class SysLogController extends AdminBaseController {
	@Resource
	LogService logService;
	
	@RequestMapping("/index")
	public ModelAndView index() {
		return view("sys/log/index");
	}
	
	@RequestMapping("/logList")
	public @ResponseBody KendoResult logList(@RequestBody Map param){
		KendoResult data = logService.getLogPaged(param);
		return data;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Map delete(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			logService.deleteLogById(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("错误信息：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		} else {
			res.put("result", true);
			res.put("message", "删除成功。");
		}

		log.info("删除系统日志");
		return res;
	}
	
	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
	public @ResponseBody Map deleteAll() {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			logService.deleteLogAll();
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("错误信息：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		} else {
			res.put("result", true);
			res.put("message", "删除成功。");
		}

		log.info("删除所有系统日志");
		return res;
	}
}