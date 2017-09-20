package com.iptv.sys.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.common.ResponseWrapper;
import com.iptv.sys.service.SysMenuService;

public class PermisionFilter implements Filter {
	private SysMenuService sysMenuService = (SysMenuService) BaseUtil.getService("sysMenuServiceImpl");

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;

		String servletPath = req.getServletPath();
		String requestType = req.getHeader("X-Requested-With");

		if (servletPath.contains("/sys") || servletPath.contains("/admin") || servletPath.contains("/wf")) {
			if (requestType == null || (!requestType.equals("XMLHttpRequest") && !requestType.startsWith("ShockwaveFlash"))) {
				ResponseWrapper responseWrapper = new ResponseWrapper(res);
				chain.doFilter(req, responseWrapper);

				String html = responseWrapper.getContent();
				Document doc = Jsoup.parse(html);
				Elements buttons = doc.getElementsByAttribute("data-permision");

				HttpSession session = req.getSession();

				String mid = req.getParameter("mid");

				if (session != null && (mid == null || mid.isEmpty())) {
					buttons.remove();
					res.getOutputStream().write(doc.html().getBytes());
					return;
				}

				Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
				Integer menuId = Integer.valueOf(mid);
				List<Map> permisions = sysMenuService.getCurrentPermisions(userId, menuId);

				for (Element el : buttons) {
					Boolean isHava = false;
					String iconCss = "";

					for (Map p : permisions) {
						String e  = el.attr("data-permision").trim();
						String q = p.get("Code").toString();
						if (e.equals(q)) {
							isHava = true;
							iconCss = p.get("IconCss").toString();
							break;
						}else{
							isHava = false;
						}
					}

					if (!isHava) {
						el.remove();
					} else if (!iconCss.trim().isEmpty()) {
						String icon = String.format("<i class='fa %s'></i>%s", iconCss, el.html());
						el.html(icon);
					}
				}

				String resHtml = doc.html();
				res.getOutputStream().write(resHtml.getBytes());
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
