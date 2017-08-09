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
import com.iptv.core.common.KendoResult;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.EncryptUtil;
import com.iptv.core.utils.JsonUtil;
import com.iptv.sys.service.SysOrganizationService;
import com.iptv.sys.service.SysRoleService;
import com.iptv.sys.service.SysSynchronizeService;
import com.iptv.sys.service.SysUserService;

@Controller
@RequestMapping("/sys/user")
@SuppressWarnings({"rawtypes","unchecked"})
public class SysUserController extends AdminBaseController{
	@Resource
	SysUserService sysUserService;
	@Resource
	SysRoleService sysRoleService;
	@Resource
	SysOrganizationService sysOrganizationService;
	@Resource
	SysSynchronizeService sysSynchronizeService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		Map res = new HashMap();
		List sex = BaseUtil.getSysParam("Sex");
		res.put("sex", JsonUtil.getJson(sex));

		return view(res);
	}
	
	@RequestMapping("/userList")
	public @ResponseBody KendoResult userList(@RequestBody Map param){
		KendoResult data = sysUserService.getUserPaged(param);
		return data;
	}
	
	@RequestMapping("/getUser")
	public @ResponseBody Map getUser(@RequestParam Map param){
		Map map = sysUserService.findUserById(param);

		try {
			map.put("Password", EncryptUtil.decrypt(map.get("Password").toString()));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过id获得用户信息时发生错误 :" + e.getMessage());
			BaseUtil.saveLog(0, "通过id获得用户信息时发生错误", e.getMessage());
		}
		
		return map;
	}
	
	
	@RequestMapping("/roleList")
	public @ResponseBody KendoResult roleList(){
		List list = sysRoleService.getRoleList();
		return new KendoResult(list);
	}
	
	@RequestMapping("/save")
	public @ResponseBody Map save(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();

		try {
			sysUserService.save(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		} catch (Exception ex) {
			log.error("错误信息-用户保存修改：" + ex.getMessage());
			BaseUtil.saveLog(0, "用户保存修改", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", BaseUtil.toHtml(errmsg));
		}else{
			map.put("result", true);
			map.put("message", "操作成功");
		}
		
		log.info("用户保存修改");
		return map;
	}
	
	
	@RequestMapping("/delete")
	public @ResponseBody Map delete(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		
		try {
			sysUserService.delete(param);
		} catch (BizException e) {
			errmsg.addAll(e.getMessages());
		} catch (Exception ex) {
			log.error("错误信息-用户删除：" + ex.getMessage());
			BaseUtil.saveLog(0, "用户删除", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", errmsg);
		}else{
			map.put("result", true);
			map.put("message", "删除成功");
		}
		
		log.info("用户删除");
		return map;
	}

	@RequestMapping(value = "/orgOptions", method = RequestMethod.POST)
	public @ResponseBody KendoResult orgOptions(@RequestBody Map map) {
		KendoResult orgs = sysOrganizationService.getOrgOptions(map);

		log.info("获取组织机构选项:" + JsonUtil.getJson(orgs));
		return orgs;
	}
	
	@RequestMapping("/passwordModify")
	public @ResponseBody Map passwordModify(@RequestBody Map param){
		List errmsg = new ArrayList();
		Map map = new HashMap();
		
		try{
			sysUserService.passwordModiy(param);
		}catch(BizException e){
			errmsg.addAll(e.getMessages());
		}catch(Exception ex){
			log.error("错误信息-用户密码修改：" + ex.getMessage());
			BaseUtil.saveLog(0, "用户密码修改", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", BaseUtil.toHtml(errmsg));
		}else{
			map.put("result", true);
			map.put("message", "修改成功。");
		}
		
		log.info("用户密码修改");
		return map;
	}
	
	@RequestMapping("/synchronize")
	public @ResponseBody Map synchronize(){
		Map map = new HashMap();
		List errmsg = new ArrayList();
		try{
			sysSynchronizeService.doSynchronize();
		}catch(Exception ex){
			log.error("错误信息-用户同步：" + ex.getMessage());
			BaseUtil.saveLog(0, "用户同步", ex.getMessage());
			errmsg.add("未知错误。");
		}
		
		if(errmsg.size()>0){
			map.put("result", false);
			map.put("message", errmsg);
		}else{
			map.put("result", true);
			map.put("message", "同步成功。");
		}
		
		return map;
	}
}
