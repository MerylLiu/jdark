package com.iptv.app.controller.admin;

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

import com.iptv.app.service.UserService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.KendoResult;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.JsonUtil;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends AdminBaseController{
	
	@Resource
	UserService us;
	
	@RequestMapping("/index")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView index(){
		Map res = new HashMap();
		List sex = BaseUtil.getSysParam("Sex");
		res.put("sex", JsonUtil.getJson(sex));
		List genderCount = us.findAllGenderCount();
		res.put("genderCount", JsonUtil.getJson(genderCount));
		List data = us.findOrganizationIdCount();
		res.put("OrganizationIdCount", JsonUtil.getJson(data));
		return view("admin/user/index",res);
	}
	
	@RequestMapping("/userList")
	public @ResponseBody KendoResult userList(@RequestBody Map param){
		System.out.println(param);
		KendoResult data = us.getIdPaged(param);
		return data;
	}
	
	@RequestMapping("/getUser")
	@ResponseBody
	public Map getUser(@RequestParam Map param){
		System.out.println(param);
		Map map = us.findUserById(param);
		return map;
	}
	
	
	
	@RequestMapping("/OrganizationIdList")
	public @ResponseBody List getOrganizationIdCount(){
		System.out.println("111111111111111111111111111111111");
		List data = us.findOrganizationIdCount();
		
		Map res = new HashMap();
		res.put("data", data);
		
		return data;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map update(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		try {
			us.update(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		}
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", errmsg);
		}else{
			map.put("result", true);
			map.put("message", "操作成功");
		}
		return map;
	}
	
	
	@RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		try {
			us.delete(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
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
