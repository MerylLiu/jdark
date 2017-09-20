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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.service.MaterialService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.controller.admin.AdminBaseController;



@Controller
@RequestMapping("/admin/material")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AdminMaterialController extends AdminBaseController {
	@Resource
	private MaterialService materialService;

	@RequestMapping("/index")
	public ModelAndView index(){
		
		return view("/admin/material/index");
	}
	
	@RequestMapping("/materialList")
	public @ResponseBody KendoResult materialList(@RequestBody Map param){
		KendoResult data = materialService.getMaterialListPaged(param);
		
		return data;
	}
	
	@RequestMapping("/getMaterial")	
	public @ResponseBody Map getMaterial(@RequestParam Map param){
		Map map = materialService.getMaterialById(param);
		return map;
	}
	
	@RequestMapping(value = "/saveMaterial", method = RequestMethod.POST)
	public @ResponseBody Map save(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			materialService.saveMaterial(map);
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

	@RequestMapping(value = "/deleteMaterial", method = RequestMethod.POST)
	public @ResponseBody Map delete(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();

		try {
			materialService.doDeleteMaterial(map);
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

		log.info("删除分类");
		return res;
	}
}