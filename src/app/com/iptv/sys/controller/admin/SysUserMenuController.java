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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.core.common.BizException;
import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.service.SysUserMenuService;

@Controller
@RequestMapping("/sys/userMenu")
@SuppressWarnings("rawtypes")
public class SysUserMenuController extends AdminBaseController {
	@Resource
	SysUserMenuService sysUserMenuService;

	@RequestMapping("/index")
	public ModelAndView index() {
		return view();
	}


	@RequestMapping(value = "/userMenuNodes", method = RequestMethod.GET)
	public @ResponseBody List roleMenuNodes() {
		List data = sysUserMenuService.getAllUserMenuForNode();
		return data;
	}
	
	@RequestMapping(value = "/menuList", method = RequestMethod.GET)
	public @ResponseBody List menuList(@RequestParam Map map){
		List list = sysUserMenuService.menuList(map);
		return list;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public @ResponseBody Map save(@RequestBody Map map) {
		List<String> messages = new ArrayList<String>();
		Map res = new HashMap();
		
		try{
			sysUserMenuService.doSave(map);
		}catch(BizException ex){
			messages.addAll(ex.getMessages());
		}catch (Exception ex) {
			log.error("未知错误：" + ex.getMessage());
			BaseUtil.saveLog(0, "添加或者修改用户权限菜单", ex.getMessage());
			messages.add("未知错误。");
		}
		
		if (messages.size() > 0) {
			res.put("result", false);
			res.put("message", BaseUtil.toHtml(messages));
		}else{
			res.put("result", true);
			res.put("message", "保存成功。");
		}

		log.info("添加或者修改权限菜单");
		return res;
	}
}
