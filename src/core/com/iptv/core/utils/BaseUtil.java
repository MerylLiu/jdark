package com.iptv.core.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;

import com.iptv.core.common.ServiceLocator;
import com.iptv.core.dao.BasicDao;
import com.iptv.core.service.SysParamService;

public class BaseUtil {
	protected static ServiceLocator locator = ServiceLocator.getInstance();		

	@SuppressWarnings("static-access")
	public static Object getService(String serviceName){
		Object res = locator.getService(serviceName);
		return res;
	}
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	public static Object getService(String serviceName, Class clazz) {
		Object res = locator.getService(serviceName,clazz);
		return res;
	}
	
	public static SqlSessionTemplate getDao(){
		BasicDao basicDao = ((BasicDao)getService("basicDao"));
		SqlSessionTemplate sqlSession = basicDao.getSqlSessionTemplate();
		return sqlSession;
	}
	
	public static void saveLog(String logInfo){
		SysParamService sysParamService = (SysParamService)getService("sysParamService");
		sysParamService.saveLog(logInfo);
	}
	
	public static void getSysParam(String key){
		SysParamService sysParamService = (SysParamService)getService("sysParamService");
		sysParamService.getSysParam(key);
	}
	
	public static String toHtml(List<String> data){
		String html = "";

		for(int i=0;i<data.size();i++){
			String item = (String)data.get(i);
			if(i == data.size()-1){
				html += item;
			}else{
				html += item +"<br/>";
			}
		}
		return html;
	}
	
	public static String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	} 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getParameterMap(HttpServletRequest request) {
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map returnMap = new HashMap();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
	}
}
