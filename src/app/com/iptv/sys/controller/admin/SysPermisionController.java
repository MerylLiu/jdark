package com.iptv.sys.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.service.SysPermisionService;

@Controller
@RequestMapping("/sys/permision")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SysPermisionController extends AdminBaseController{
	@Resource
	SysPermisionService sysPermisionService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		
		return view();
	}
	
	@RequestMapping("/permisionList")
	public @ResponseBody KendoResult permisionList(@RequestBody Map param){
		KendoResult data = sysPermisionService.getPermisionPaged(param);
		return data;
	}
	
	@RequestMapping("/getPermision")	
	public @ResponseBody Map getPermision(@RequestParam Map param){
		Map map = sysPermisionService.findPermisionById(param);
		return map;
	}
	
	@RequestMapping("/save")	
	public @ResponseBody Map save(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		try {
			sysPermisionService.update(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		} catch (Exception ex) {
			log.error("错误信息-权限保存修改：" + ex.getMessage());
			BaseUtil.saveLog(0, "权限保存修改", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", BaseUtil.toHtml(errmsg));
		}else{
			map.put("result", true);
			map.put("message", "操作成功");
		}
		
		log.info("保存修改权限");
		return map;
	}
	
	
	@RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		try {
			sysPermisionService.delete(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		} catch (Exception ex) {
			log.error("错误信息-删除权限：" + ex.getMessage());
			BaseUtil.saveLog(0, "删除权限", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", errmsg);
		}else{
			map.put("result", true);
			map.put("message", "删除成功");
		}
		
		log.info("删除权限");
		return map;
	}
}
