package com.iptv.core.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

public interface ApiService {

	@WebMethod
	@WebResult(name = "Result")
	/**
	 * 数据交换接口
	 * 
	 * @param params
	 *            参数字符串(json或xml)
	 * @return
	 */
	public String cgi(@WebParam(name = "params") String params);
}
