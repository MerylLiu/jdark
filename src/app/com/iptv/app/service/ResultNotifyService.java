package com.iptv.app.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.iptv.app.common.CSPResult;

@WebService(targetNamespace="iptv")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ResultNotifyService {
	@WebMethod
	@WebResult(name="CSPResult")
	public CSPResult ResultNotify (@WebParam(name = "CSPID")String CSPID,
			@WebParam(name = "LSPID")String LSPID,
			@WebParam(name = "CorrelateID")String CorrelateID,
			@WebParam(name = "CmdResult")int CmdResult,
			@WebParam(name = "ResultFileURL")String ResultFileURL);
}
