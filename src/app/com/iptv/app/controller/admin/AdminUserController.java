package com.iptv.app.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends AdminBaseController{
	@RequestMapping("/index")
	public ModelAndView index(){
		return view("admin/user/index");
	}
	
	
}
