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
import com.iptv.core.controller.BaseController;
import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.service.SysPermisionComponentService;


@Controller
@RequestMapping("/sys/permisionComponent")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SysPermisionComponentController extends BaseController {
	@Resource
	SysPermisionComponentService sysPermisionComponentService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		
		return view("/sys/permisionComponent/index");
	}
	//查询组件权限中的所有数据
	@RequestMapping("/permisionComponentList")
	public @ResponseBody KendoResult permisionList(@RequestBody Map param){
		KendoResult data = sysPermisionComponentService.getPermisionComponentPaged(param);
		return data;
	}
	
	@RequestMapping("/getPermisionComponent")	
	public @ResponseBody Map getPermision(@RequestParam Map param){
		Map map = sysPermisionComponentService.getPermisionComponentById(param);
		return map;
	}
	
	@RequestMapping("/savePermisionComponent")	
	public @ResponseBody Map savePermisionComponent(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		try {
			sysPermisionComponentService.updatePermisionComponent(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		} catch (Exception ex) {
			log.error("错误信息-组件权限保存修改：" + ex.getMessage());
			BaseUtil.saveLog(0, "组件权限保存修改", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", BaseUtil.toHtml(errmsg));
		}else{
			map.put("result", true);
			map.put("message", "操作成功");
		}
		
		log.info("保存修改组件权限");
		return map;
	}
	@RequestMapping("/deletePermisionComponent")
	public @ResponseBody Map deletePermisionComponent(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		try {
			sysPermisionComponentService.deletePermisionComponent(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		} catch (Exception ex) {
			log.error("错误信息-删除组件权限：" + ex.getMessage());
			BaseUtil.saveLog(0, "删除组件权限", ex.getMessage());
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
