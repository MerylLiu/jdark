package com.iptv.core.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.iptv.core.common.Configuration;
import com.iptv.core.service.ApiService;

import api.CgiServiceImplProxy;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class WebServiceUtil {
	private static final String apiUrl = Configuration.webCfg.getProperty("cfg.webservice.api");

	public static String cgi(Map params) {
		try {
			Integer dataType = 1;
			if (params.get("DataType") == null) {
				dataType = 1;
			} else if (params.get("DataType").equals("json")) {
				dataType = 1;
			} else if (params.get("DataType").equals("xml")) {
				dataType = 2;
			} else {
				dataType = 1;
			}

			String paramsStr = dataType == 1 ? JsonUtil.getJson(params) : XmlUtil.map2xml(params).asXML();

			CgiServiceImplProxy proxy = new CgiServiceImplProxy();
			String ret = proxy.cgi(paramsStr);
			return ret;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public static String cgi(String url, Map params) {
		try {
			URL urlAddr = new URL(url);
			Service s = Service.create(urlAddr, new QName("api", "ApiService"));
			ApiService hs = s.getPort(new QName("api", "ApiServiceImplPort"), ApiService.class);

			Integer dataType = 1;
			if (params.get("DataType") == null) {
				dataType = 1;
			} else if (params.get("DataType").equals("json")) {
				dataType = 1;
			} else if (params.get("DataType").equals("xml")) {
				dataType = 2;
			} else {
				dataType = 1;
			}

			String paramsStr = dataType == 1 ? JsonUtil.getJson(params) : XmlUtil.map2xml(params).asXML();

			CgiServiceImplProxy proxy = new CgiServiceImplProxy();
			proxy.setEndpoint(url);
			String ret = proxy.cgi(paramsStr);

			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			return null;
		}
	}

	public static String cgi(String params) {
		try {
			CgiServiceImplProxy proxy = new CgiServiceImplProxy();
			String ret = proxy.cgi(params);
			return ret;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public static String cgi(String url, String params) {
		try {
			CgiServiceImplProxy proxy = new CgiServiceImplProxy();
			proxy.setEndpoint(url);
			String ret = proxy.cgi(params);

			return ret;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("Code", "A00102");
		map.put("Token", "^#(hHD(^#)D_Wjgndhh#$^729%5w0e00%%#@(02^%W");
		map.put("DataType", "json");
		map.put("LoginName", "wanghan");

		// System.out.println(cgi(map));

		Map params = new HashMap();
		params.put("AppId", "qq");
		params.put("AppSecret", "ww");
		params.put("Code", "A001");
		params.put("CorrelateID", "A001");
		params.put("Action", "DELETE");
		params.put("CustomerID", "A001");
		params.put("Name", "teste");
		params.put("AreaID", 1144);
		String res = cgi("http://localhost:8080/sync/services/apiService?wsdl", params);
		// String res =
		// cgi("http://localhost:8080/csfc/services/apiService?wsdl",params);
		System.out.println(res);
	}
}
