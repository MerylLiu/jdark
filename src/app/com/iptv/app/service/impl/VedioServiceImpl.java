package com.iptv.app.service.impl;

import org.apache.axis2.AxisFault;

import com.iptv.app.service.CSPRequestServiceStub;
import com.iptv.app.service.CSPRequestServiceStub.CSPResult;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmd;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmdResponse;
import com.iptv.app.service.VedioService;

public class VedioServiceImpl implements VedioService{

	@Override
	public void UploadVedio() throws AxisFault {
		// TODO Auto-generated method stub
	}

	public static void main(String[] args) throws Exception {
		CSPRequestServiceStub stub = new CSPRequestServiceStub();

		try {
			ExecCmd cmd = new ExecCmd();
			cmd.setCSPID("20001041");
			cmd.setLSPID("all");
			cmd.setCorrelateID("001");
			cmd.setCmdFileURL("ftp://ftpuser:111111@139.129.129.184/test.xml");

			ExecCmdResponse resp=stub.execCmd(cmd);
			CSPResult res= resp.getExecCmdReturn();

			System.out.println("调用结果："+res.getResult());
			System.out.println(res.getErrorDescription());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
