package com.iptv.core.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "api", serviceName = "ApiService", name = "API")
@SOAPBinding(style = SOAPBinding.Style.RPC)
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
