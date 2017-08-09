package com.iptv.core.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.swing.text.html.HTML;

import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iptv.core.service.ApiService;
import com.iptv.core.common.BizException;
import com.iptv.core.common.Configuration;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.JsonUtil;
import com.iptv.core.utils.StringUtil;
import com.iptv.core.utils.XmlUtil;

@Service
@WebService(serviceName = "ApiService", endpointInterface = "com.iptv.core.service.ApiService", targetNamespace = "api")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@SuppressWarnings("rawtypes")
public class ApiServiceImpl implements ApiService {
	private static final Map config = Configuration.from("/service.json");

	@Override
	/**
	 * 数据交换接口
	 * 
	 * @param params
	 *            参数字符串(json或xml)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String cgi(String params) {
		ArrayList<Map> services = (ArrayList<Map>) config.get("Services");
		String serviceType = "";
		Map paramsMap = new HashMap();

		if (serviceType.equals("")) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				paramsMap = objectMapper.readValue(params, Map.class);
				serviceType = "json";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (serviceType.equals("")) {
			try {
				paramsMap = XmlUtil.xml2map(params, false);
				serviceType = "xml";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (!serviceType.equals("json") && !serviceType.equals("xml")) {
			return "参数格式不正确,请传入xml或json格式的参数。";
		}

		Map result = new HashMap();
		if (paramsMap.get("Token") == null || !config.get("Token").equals(paramsMap.get("Token"))) {
			result.put("ResultCode", 0);
			result.put("Message", "您不具有该接口的访问权限。");

			if (serviceType.equals("json")) {
				return JsonUtil.getJson(result);
			} else if (serviceType.equals("xml")) {
				return XmlUtil.map2xml(result).asXML();
			}
		}

		if (paramsMap.get("Code") == null) {
			result.put("ResultCode", 0);
			result.put("Message", "请传入接口编号。");

			if (serviceType.equals("json")) {
				return JsonUtil.getJson(result);
			} else if (serviceType.equals("xml")) {
				return XmlUtil.map2xml(result).asXML();
			}
		}

		for (Map item : services) {
			if (paramsMap.get("Code") != null && paramsMap.get("Code").equals(item.get("Code"))) {
				String service = item.get("Service").toString();
				String method = item.get("Method").toString();

				try {
					if (serviceType.equals("json")) {
						Map map = (Map) JsonUtil.getObject(params);
						Object data = BaseUtil.invokeMethod(service, method,map);
						return JsonUtil.getJson(data);
					} else if (serviceType.equals("xml")) {
						Map map = (Map) XmlUtil.xml2map(params, false);
						Object data = BaseUtil.invokeMethod(service, method, map);

						if (data instanceof List) {
							return XmlUtil.list2xml((List) data).asXML();
						} else {
							return XmlUtil.map2xml((Map) data).asXML();
						}
					}
				} catch (Exception e) {
					result.put("ResultCode", 0);
					result.put("Message", e.getMessage());

					if (serviceType.equals("json")) {
						return JsonUtil.getJson(result);
					} else if (serviceType.equals("xml")) {
						return XmlUtil.map2xml(result).asXML();
					}
				}
			}
		}

		return null;
	}
}
