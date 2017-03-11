package com.iptv.app.controller.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.AxisFault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iptv.app.service.CSPRequestServiceStub;
import com.iptv.app.service.CSPRequestServiceStub.CSPResult;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmd;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmdResponse;
import com.iptv.app.service.CategoryService;
import com.iptv.core.service.SysParamService;

@Controller
@RequestMapping("/admin/vedio")
public class AdminVedioController extends AdminBaseController {
	@Resource
	private SysParamService sysParamService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		return view("/admin/vedio/index");
	}
		
	
	

	/*
	 * ------------------------------------------------------------------------------
	 * */
	@RequestMapping("/vedio")
	public ModelAndView callApi(HttpServletRequest req) {
		log.info("访问Controller:admin/vedio");
		sysParamService.saveLog("访问Controller:admin/vedio");
		return view("admin/vedio");
	}

	@RequestMapping(value = "/pubVedio", method = RequestMethod.POST)
	public @ResponseBody CSPResult pubVedio(@RequestBody Map map) throws AxisFault {
		CSPResult res = new CSPResult();
		CSPRequestServiceStub stub = new CSPRequestServiceStub();

		try {
			ExecCmd cmd = new ExecCmd();
			cmd.setCSPID("20001041");
			cmd.setLSPID("all");
			cmd.setCorrelateID(map.get("cid").toString());
			cmd.setCmdFileURL("ftp://ftpuser:111111@115.28.77.76:22000/"+map.get("xmlurl"));

			ExecCmdResponse resp = stub.execCmd(cmd);
			res = resp.getExecCmdReturn();

			String logInfo = "访问JSONController:admin/vedio；调用结果编码：" + res.getResult() + "；结果描述："
					+ res.getErrorDescription();
			sysParamService.saveLog(logInfo);
			log.info(logInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
}
