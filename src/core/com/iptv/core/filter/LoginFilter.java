package com.iptv.core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

public class LoginFilter implements Filter {
	PageContext pageContext = null;

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;

		String path = req.getContextPath();
		String servletPath = req.getServletPath();
		
		if (servletPath.contains("/sys") || servletPath.contains("/admin") || servletPath.contains("/wf")) {
			// 从SESSION获取对象
			Object obj = req.getSession().getAttribute("userId");

			String requestType = req.getHeader("X-Requested-With");

			if (obj != null) {
				arg2.doFilter(arg0, arg1);
			} else {
				if (servletPath.contains("/login") || servletPath.contains("/doLogin") || servletPath.contains("/regist")) {
					arg2.doFilter(arg0, arg1);
				} else if (servletPath.contains("/main") || servletPath.contains("/services")) {
					arg2.doFilter(arg0, arg1);
				} else if (requestType != null && "XMLHttpRequest".equalsIgnoreCase(requestType)) {// 重定向ajax
					res.setHeader("sessionstatus", "timeout");
					res.sendError(518, "session timeout.");
				} else {// 重定向
					String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path + "/";

					PrintWriter out = res.getWriter();
					out.println("<script>");  
					out.println("top.location.href='" + basePath + "admin/login'");
				    out.println("</script>"); 
				    out.flush();
				    out.close();
					//res.sendRedirect(contextPath + "/admin/login");
				}
			}
		} else {
			arg2.doFilter(arg0, arg1);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
