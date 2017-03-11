package com.iptv.app.service.impl;

import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.iptv.app.common.CSPResult;
import com.iptv.app.service.ResultNotifyService;
import com.iptv.core.utils.BaseUtil;

@Service
@WebService(serviceName = "CSPResponseService", endpointInterface = "com.iptv.app.service.ResultNotifyService",targetNamespace="iptv")
@SOAPBinding(style=Style.RPC)
public class ResultNotifyServiceImpl implements ResultNotifyService{

	public Logger log = Logger.getLogger(this.getClass());

	@Override
	public CSPResult ResultNotify(String CSPID, String LSPID, String CorrelateID, int CmdResult,
			String ResultFileURL) {
		CSPResult res = new CSPResult();
		res.setResult(0);
		res.setErrorDescription("调用成功。");
		
		String info = "IPTV回调：CSPID:" + CSPID + ",LSPID:" + LSPID + ",CorrelateID:" + CorrelateID + ",CmdResult:" + CmdResult
				+ ",ResultFileURL:" + ResultFileURL;
		
		log.info(info);
		BaseUtil.saveLog(info);

		return res;
	}


}
