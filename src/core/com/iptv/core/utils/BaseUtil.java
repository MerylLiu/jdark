package com.iptv.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.cache.annotation.Cacheable;

import com.iptv.core.common.ServiceLocator;
import com.iptv.core.dao.BasicDao;
import com.iptv.core.service.SysParamService;

@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
public class BaseUtil {
	protected static ServiceLocator locator = ServiceLocator.getInstance();

	public static Object getService(String serviceName) {
		Object res = locator.getService(serviceName);
		return res;
	}

	public static Object getService(String serviceName, Class clazz) {
		Object res = locator.getService(serviceName, clazz);
		return res;
	}

	public static SqlSessionTemplate getDao() {
		BasicDao basicDao = ((BasicDao) getService("basicDao"));
		SqlSessionTemplate sqlSession = basicDao.getSqlSessionTemplate();
		return sqlSession;
	}

	/*
	 * public static void saveLog(String logInfo) { SysParamService
	 * sysParamService = (SysParamService) getService("sysParamService");
	 * sysParamService.saveLog(logInfo); }
	 */

	/**
	 * 保存错误日志到数据库
	 * 
	 * @param opreationType
	 *            0.错误日志,1.插入数据，2.修改数据，3，删除数据，8，其他
	 * @param operation
	 *            错误信息标题
	 * @param remark
	 *            错误信息
	 */
	public static void saveLog(int opreationType, String operation, String remark) {
		SysParamService sysParamService = (SysParamService) getService("sysParamService");
		sysParamService.saveLog(opreationType, operation, remark);
	}

	@Cacheable
	public static List getSysParam(String key) {
		SysParamService sysParamService = (SysParamService) getService("sysParamService");
		List data = sysParamService.getSysParam(key, false);
		return data;
	}

	/*
	 * public static List getSysParam(String key, Boolean isAll) {
	 * SysParamService sysParamService = (SysParamService)
	 * getService("sysParamService"); List data = new ArrayList(); if (isAll ==
	 * true) { data = sysParamService.getSysParam(key, true); } else { data =
	 * sysParamService.getSysParam(key, false); }
	 * 
	 * return data; }
	 */

	public static String toHtml(List<String> data) {
		String html = "";

		for (int i = 0; i < data.size(); i++) {
			String item = (String) data.get(i);
			if (i == data.size() - 1) {
				html += item;
			} else {
				html += item + "<br/>";
			}
		}
		return html;
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public static Map getRequestParamsMap(String queryString, String encode) {
		Map paramsMap = new HashMap();
		if (queryString != null && queryString.length() > 0) {
			int ampersandIndex, lastAmpersandIndex = 0;
			String subStr, param, value;
			String[] paramPair, values, newValues;
			do {
				ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
				if (ampersandIndex > 0) {
					subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
					lastAmpersandIndex = ampersandIndex;
				} else {
					subStr = queryString.substring(lastAmpersandIndex);
				}
				paramPair = subStr.split("=");
				param = paramPair[0];
				value = paramPair.length == 1 ? "" : paramPair[1];
				try {
					value = URLDecoder.decode(value, encode);
				} catch (UnsupportedEncodingException ignored) {
				}
				if (paramsMap.containsKey(param)) {
					values = (String[]) paramsMap.get(param);
					int len = values.length;
					newValues = new String[len + 1];
					System.arraycopy(values, 0, newValues, 0, len);
					newValues[len] = value;
				} else {
					newValues = new String[] { value };
				}
				paramsMap.put(param, newValues);
			} while (ampersandIndex > 0);
		}
		return paramsMap;
	}
}
