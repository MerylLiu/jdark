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

import com.iptv.core.common.BizException;
import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.service.SysOrganizationService;

@Controller
@RequestMapping("/sys/organization")
@SuppressWarnings("rawtypes")
public class SysOrganizationController extends AdminBaseController {
	@Resource
	SysOrganizationService sysOrganizationService;

	@RequestMapping("/index")
	public ModelAndView index() {
		return view();
	}

	@RequestMapping(value = "/organizationNodes", method = RequestMethod.GET)
	public @ResponseBody List organizationNodes() {
		List data = sysOrganizationService.getALlOrganizationsForNode();
		return data;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public @ResponseBody Map save(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			sysOrganizationService.save(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("数据库错误：" + ex.getMessage());
			BaseUtil.saveLog(0, "添加或者修改机构", ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		} else {
			res.put("result", true);
			res.put("message", "保存成功。");
		}

		log.info("添加或者修改机构");
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public @ResponseBody Map delete(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			sysOrganizationService.delete(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("数据库错误：" + ex.getMessage());
			BaseUtil.saveLog(0, "删除机构", ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		} else {
			res.put("result", true);
			res.put("message", "删除成功。");
		}

		log.info("删除机构");
		return res;
	}
}
