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

import com.iptv.app.service.AdminBusinessNodeService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.JsonUtil;
import com.iptv.sys.controller.admin.AdminBaseController;

@Controller
@RequestMapping("/admin/businessNode")
@SuppressWarnings("rawtypes")
public class AdminBusinessNodeController extends AdminBaseController {
	@Resource
	AdminBusinessNodeService sysBusinessNodeService;

	@RequestMapping("/index")
	public ModelAndView index() {
		Map res = new HashMap();
		res.put("materials", JsonUtil.getJson(sysBusinessNodeService.getMaterialList()));
		res.put("categorys", JsonUtil.getJson(sysBusinessNodeService.getCategoryList()));
		res.put("categoryTree", JsonUtil.getJson(sysBusinessNodeService.getAllCategoryForNode()));
		
		return view(res);
	}
	
	@RequestMapping("/businessList")
	public @ResponseBody KendoResult businessList(@RequestBody Map map){
		KendoResult kr = sysBusinessNodeService.getbusinessListPaged(map);
		return kr;
	}
	
	@RequestMapping("/getBusiness")
	public @ResponseBody Map getUser(@RequestParam Map param){
		Map map = sysBusinessNodeService.getBusinessById(param);
		
		return map;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public @ResponseBody Map save(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();
		
		try{
			sysBusinessNodeService.doSave(map);
		}catch(BizException ex){
			messages.addAll(ex.getMessages());
		}catch (Exception ex) {
			log.error("未知错误：" + ex.getMessage());
			BaseUtil.saveLog(0, "添加或者修改机构权限菜单", ex.getMessage());
			messages.add("未知错误。");
		}
		
		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "保存成功。");
		}

		log.info("添加或者修改机构权限菜单");
		return res;
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		
		try {
			sysBusinessNodeService.doDelete(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		}catch (Exception ex) {
			log.error("未知错误：" + ex.getMessage());
			BaseUtil.saveLog(0, "删除机构权限菜单", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", errmsg);
		}else{
			map.put("result", true);
			map.put("message", "删除成功");
		}
		
		return map;
	}
}
