package com.iptv.app.controller.admin;

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

import com.iptv.app.service.CategoryService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.JsonUtil;
import com.iptv.sys.controller.admin.AdminBaseController;



@Controller
@RequestMapping("/admin/category")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AdminCategoryController extends AdminBaseController {
	@Resource
	private CategoryService categoryService;

	@RequestMapping("/index")
	public ModelAndView index(){
		Map map = new HashMap();
		List status = BaseUtil.getSysParam("CategoryStatus");
		map.put("status",JsonUtil.getJson(status));
		
		return view("/admin/category/index",map);
	}
	
	@RequestMapping(value="/categoryNodes",method = RequestMethod.GET)
	public @ResponseBody List categoryNodes(){
		List data = categoryService.getALlCategoryForNode();
		return data;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map save(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			categoryService.save(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("数据库错误：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "保存成功。");
		}

		log.info("添加或者修改档案分类");
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Map delete(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			categoryService.delete(map);
		} catch (BizException biz) {
			messages.addAll(biz.getMessages());
		} catch (Exception ex) {
			log.error("数据库错误：" + ex.getMessage());
			messages.add("未知错误。");
		}

		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "删除成功。");
		}

		log.info("删除档案分类");
		return res;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody KendoResult orgOptions(@RequestBody Map map) {
		KendoResult orgs = categoryService.Category(map);

		log.info("获取档案分类选项:" + JsonUtil.getJson(orgs));
		return orgs;
	}
	
}
