package com.iptv.sys.filter;

import com.iptv.core.utils.BaseUtil;
import com.iptv.sys.common.ResponseWrapper;
import com.iptv.sys.service.SysMenuService;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.utils.ByteArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.ls.LSInput;

public class PermisionFilter implements Filter {
	private SysMenuService sysMenuService = (SysMenuService) BaseUtil.getService("sysMenuServiceImpl");

	@Override
	public void destroy() {
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;

		String servletPath = req.getServletPath();
		String requestType = req.getHeader("X-Requested-With");
		if ((servletPath.contains("/sys")) || (servletPath.contains("/admin")) || (servletPath.contains("/wf"))) {
			if ((requestType == null)
					|| ((!requestType.equals("XMLHttpRequest")) && (!requestType.startsWith("ShockwaveFlash")))) {
				ResponseWrapper responseWrapper = new ResponseWrapper(res);
				chain.doFilter(req, responseWrapper);

				Map content = responseWrapper.getContent();
				String html = "";

				String resType = responseWrapper.getHeader("X-Responsed-With");
				if (resType != null && resType.equals("file-stream")) {
					byte[] buffer = (byte[]) content.get("ByteData");

					Map<String, String> headers = responseWrapper.getHeaders();
					for (Entry<String, String> header : headers.entrySet()) {
						res.setHeader(header.getKey(), header.getValue());
					}

					res.getOutputStream().write(buffer);
					return;
				} else {
					html = content.get("StringData").toString();
				}

				Document doc = Jsoup.parse(html);
				Elements buttons = doc.getElementsByAttribute("data-permision");

				HttpSession session = req.getSession();

				String mid = req.getParameter("mid");
				if ((session != null) && ((mid == null) || (mid.isEmpty()))) {
					buttons.remove();

					res.getWriter().write(doc.html());
					return;
				}
				Integer userId = Integer.valueOf(session.getAttribute("userId").toString());
				Integer menuId = Integer.valueOf(mid);
				List<Map> permisions = this.sysMenuService.getCurrentPermisions(userId, menuId);
				for (Element el : buttons) {
					Boolean isHave = Boolean.valueOf(false);
					String iconCss = "";
					for (Map p : permisions) {
						String e = el.attr("data-permision").trim();
						String q = p.get("Code").toString();
						if (e.equals(q)) {
							isHave = Boolean.valueOf(true);
							iconCss = p.get("IconCss").toString();
							break;
						}
						isHave = Boolean.valueOf(false);
					}
					if (!isHave.booleanValue()) {
						el.remove();
					} else if (!iconCss.trim().isEmpty()) {
						String icon = String.format("<i class='fa %s'></i>%s", new Object[] { iconCss, el.html() });
						el.html(icon);
					}
				}
				String resHtml = doc.html();

				res.getWriter().write(resHtml);
			} else {
				chain.doFilter(req, res);
			}
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
