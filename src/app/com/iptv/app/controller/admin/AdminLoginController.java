package com.iptv.app.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.service.LoginService;
import com.iptv.app.service.SysMenuService;
import com.sun.tools.doclets.formats.html.HelpWriter;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AdminLoginController extends AdminBaseController {
	@Resource
	private LoginService loginServiceImpl;
	@Resource
	SysMenuService sysMenuService;

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req, Map user) {
		Map map = new HashMap();

		if (user.equals(null)) {
			Map u = loginServiceImpl.Login(user.get("username").toString(), user.get("password").toString());

			if (u != null) {
				req.getSession().setAttribute("AdminUser", u);
				map.put("username", u.get("username"));

				System.out.println(u.get("username"));
			}
		}

		map.put("username", "test");

		log.info("访问Controller:admin/login");
		return view("admin/index", map);
	}

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest req) {
		List data = sysMenuService.getAllMenus();

		log.info("访问Controller:admin/main,加载系统菜单");
		Map map = new HashMap();
		map.put("menus", data);
		return view(map);
	}

	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest req) {
		List data = sysMenuService.getAllMenus();

		log.info("访问Controller:admin/main,加载系统菜单");
		return view("admin/home", "menus", data);
	}
}
