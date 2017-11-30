package com.iptv.core.common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class Log4jConfigServlet
 */
@WebServlet("/Log4jConfigServlet")
public class Log4jConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Log4jConfigServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		String fileName = getInitParameter("fileName");

		if (fileName != null) {
			PropertyConfigurator.configure(this.getClass().getClassLoader().getResource(fileName));
		} else {
			System.out.println("===========没有log4j初始化配置文件！==============");
		}
	}
}
