package com.iptv.app.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import com.iptv.app.common.CSPResult;
import com.iptv.app.service.ResultNotifyService;
import com.iptv.app.service.VideoService;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.FtpUtil;
import com.iptv.core.utils.XmlUtil;

@Service
@WebService(serviceName = "CSPResponseService", endpointInterface = "com.iptv.app.service.ResultNotifyService", targetNamespace = "iptv")
@SOAPBinding(style = Style.RPC)
public class ResultNotifyServiceImpl implements ResultNotifyService {

	public Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CSPResult ResultNotify(String CSPID, String LSPID, String CorrelateID, int CmdResult, String ResultFileURL) {
		CSPResult res = new CSPResult();
		res.setResult(0);
		res.setErrorDescription("调用成功。");

		Map map = new HashMap();
		map.put("CorrelateID", CorrelateID);

		String info = "CSPID:" + CSPID + ",LSPID:" + LSPID + ",CorrelateID:" + CorrelateID + ",CmdResult:"
				+ CmdResult + ",ResultFileURL:" + ResultFileURL;
		log.info("IPTV工单处理结果回调："+info);
		BaseUtil.saveLog(8, "IPTV工单处理结果回调", info);

		if (CmdResult == 0) {
			map.put("Status", 3);
			BaseUtil.getDao().update("video.autitSuccess", map);
		} else {
			Map ftpInfo = getFtpInfo(ResultFileURL);

			String fileData = FtpUtil.readFile(ftpInfo.get("ip").toString(),
					Integer.valueOf(ftpInfo.get("port").toString()).intValue(), ftpInfo.get("user").toString(),
					ftpInfo.get("pwd").toString(), ftpInfo.get("fileName").toString(), ftpInfo.get("dir").toString());

			try {
				Map reply = XmlUtil.xml2map(fileData, false);
				String remark = ((Map) reply.get("Reply")).get("Property").toString();

				log.info("IPTV工单处理失败回调:" + remark);
				BaseUtil.saveLog(8, "IPTV工单处理失败回调", remark);

				map.put("Status", 4);
				map.put("Remark", remark);
				BaseUtil.getDao().update("video.autitFail", map);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map getFtpInfo(String resultFileUrl) {
		Map map = new HashMap();

		String user = resultFileUrl.replaceAll("ftp://(\\w+):(\\w+)@(.+):(\\d+)(.*/)(.*)", "$1");
		map.put("user", user);

		String pwd = resultFileUrl.replaceAll("ftp://(\\w+):(\\w+)@(.+):(\\d+)(.*/)(.*)", "$2");
		map.put("pwd", pwd);

		String ip = resultFileUrl.replaceAll("ftp://(\\w+):(\\w+)@(.+):(\\d+)(.*/)(.*)", "$3");
		map.put("ip", ip);

		String port = resultFileUrl.replaceAll("ftp://(\\w+):(\\w+)@(.+):(\\d+)(.*/)(.*)", "$4");
		map.put("port", port);

		String dir = resultFileUrl.replaceAll("ftp://(\\w+):(\\w+)@(.+):(\\d+)(.*/)(.*)", "$5");
		map.put("dir", dir);

		String fileName = resultFileUrl.replaceAll("ftp://(\\w+):(\\w+)@(.+):(\\d+)(.*/)(.*)", "$6");
		map.put("fileName", fileName);

		return map;
	}

	public static void main(String[] args) throws Exception {
		String ftp = "ftp://ftper:ftper@182.138.30.138:21/uploap/notify/002.xml_reply_20170322141821.xml";
		Map ftpInfo = getFtpInfo(ftp);

		String fileData = FtpUtil.readFile(ftpInfo.get("ip").toString(),
				Integer.valueOf(ftpInfo.get("port").toString()).intValue(), ftpInfo.get("user").toString(),
				ftpInfo.get("pwd").toString(), ftpInfo.get("fileName").toString(), ftpInfo.get("dir").toString());
		
		System.out.println(fileData);
	}
}
