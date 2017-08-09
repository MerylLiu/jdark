package com.iptv.app.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.iptv.app.service.CSPRequestServiceStub;
import com.iptv.app.service.CSPRequestServiceStub.CSPResult;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmd;
import com.iptv.app.service.CSPRequestServiceStub.ExecCmdResponse;
import com.iptv.core.common.Configuration;
import com.iptv.core.utils.JsonUtil;

@SuppressWarnings("rawtypes")
public class LocXmlUtil {

	@SuppressWarnings("unchecked")
	public static Map BuildOnlineXml(Map data,Integer status) {
		String ftpUrl = "ftp://" + Configuration.webCfg.getProperty("ftp.user") + ":"
				+ Configuration.webCfg.getProperty("ftp.pwd") + "@" + Configuration.webCfg.getProperty("ftp.ip.net") + ":"
				+ Configuration.webCfg.getProperty("ftp.port") + "/";
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String codeLast = "_" + format.format(new Date());

		String action = "REGIST";
		
		/*if(status.equals(3)){
			action = "UPDATE";
		} else{
			action = "REGIST";
		}*/

		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		xml += "<ADI xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n";
		xml += "  <Objects>\n";
		xml += "     <Object ElementType=\"Program\" ID=\"P" + data.get("Code") + codeLast + "\" Action=\"" + action
				+ "\" Code=\"P" + data.get("Code") + codeLast + "\">\n";
		xml += "	    <Property Name=\"Name\">" + data.get("Name") + "</Property>\n";
		xml += "	    <Property Name=\"Language\">中文</Property>\n";
		xml += "	    <Property Name=\"Type\">时尚生活</Property>\n";
		xml += "	    <Property Name=\"DefinitionFlag\">2</Property>\n";
		// xml += " <Property Name=\"ReleaseYear\">2017</Property>\n";
		xml += "	    <Property Name=\"Description\">" + data.get("Description") + "</Property>\n";
		// xml += " <Property Name=\"LicensingWindowStart\">" +
		// data.get("StartDate") + "</Property>\n";
		// xml += " <Property Name=\"LicensingWindowEnd\">" +
		// data.get("EndDate") + "</Property>\n";
		xml += "	    <Property Name=\"PriceTaxIn\">" + data.get("CostFZ") + "</Property>\n";
		xml += "	    <Property Name=\"Keywords\">" + data.get("Name") + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "     <Object ElementType=\"Movie\" ID=\"M" + data.get("Code") + codeLast + "\" Action=\""+action+"\" Code=\"M"
				+ data.get("Code") + codeLast + "\">\n";
		xml += "	    <Property Name=\"Type\">1</Property>\n";
		xml += "	    <Property Name=\"FileURL\">" + ftpUrl + getSubUrl(data.get("VideoUrl").toString()) + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "     <Object ElementType=\"Picture\" ID=\"C" + data.get("Code") + codeLast + "\" Action=\""+action+"\" Code=\"C"
				+ data.get("Code") + codeLast+ "\">\n";
		xml += "	    <Property Name=\"FileURL\">" + ftpUrl + getSubUrl(data.get("ImageUrl").toString()) + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "  </Objects>\n";
		xml += "  <Mappings>\n";

		/*if(status.equals(3)){
			xml += "      <Mapping ParentType=\"Program\" ParentID=\"P" + data.get("Code")+ codeLast
					+ "\" ElementType=\"Movie\" ElementID=\"M" + data.get("Code") + codeLast+ "\" " + "ParentCode=\"P"
					+ data.get("Code") + codeLast+ "\" ElementCode=\"M" + data.get("Code")+ codeLast + "\" Action=\"DELETE\"></Mapping>\n";
			xml += "      <Mapping ParentType=\"Picture\" ParentID=\"C" + data.get("Code")+ codeLast
					+ "\" ElementType=\"Program\" ElementID=\"P" + data.get("Code")+ codeLast + "\" " + "ParentCode=\"C"
					+ data.get("Code") + codeLast+ "\" ElementCode=\"P" + data.get("Code") + codeLast+ "\" Action=\"DELETE\">\n";
			xml += "	    <Property Name=\"Type\">0</Property>\n";
			xml += "	    <Property Name=\"Sequence\">" + data.get("Id") + "</Property>\n";
			xml += "      </Mapping>\n";
		}*/

		xml += "      <Mapping ParentType=\"Program\" ParentID=\"P" + data.get("Code")+ codeLast
				+ "\" ElementType=\"Movie\" ElementID=\"M" + data.get("Code") + codeLast+ "\" " + "ParentCode=\"P"
				+ data.get("Code") + codeLast+ "\" ElementCode=\"M" + data.get("Code") + codeLast+ "\" Action=\"REGIST\"></Mapping>\n";
		xml += "      <Mapping ParentType=\"Picture\" ParentID=\"C" + data.get("Code")+ codeLast
				+ "\" ElementType=\"Program\" ElementID=\"P" + data.get("Code") + codeLast+ "\" " + "ParentCode=\"C"
				+ data.get("Code") + codeLast+ "\" ElementCode=\"P" + data.get("Code") + codeLast+ "\" Action=\"REGIST\">\n";
		xml += "	    <Property Name=\"Type\">0</Property>\n";
		xml += "	    <Property Name=\"Sequence\">" + data.get("Id") + "</Property>\n";
		xml += "      </Mapping>\n";
		xml += "  </Mappings>\n";
		xml += "</ADI>";
		
		data.put("codeLast", codeLast);
		String xmlForDel = BuildOfflineXml(data);

		Map res = new HashMap();
		res.put("xmlForAdd", xml);
		res.put("xmlForDel", xmlForDel);
		res.put("DxCode", "P"+data.get("Code")+codeLast);

		return res;
	}
	
	public static String buildDelMapping(Map data){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String codeLast = "_" + format.format(new Date());

		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<ADI xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
		xml += "  <Mappings>\n";
		xml += "      <Mapping ParentType=\"Program\" ParentID=\"P" + data.get("Code")+ codeLast
				+ "\" ElementType=\"Movie\" ElementID=\"M" + data.get("Code") + codeLast+ "\" " + "ParentCode=\"P"
				+ data.get("Code") + codeLast+ "\" ElementCode=\"M" + data.get("Code") + codeLast+ "\" Action=\"DELETE\"></Mapping>\n";
		xml += "      <Mapping ParentType=\"Picture\" ParentID=\"C" + data.get("Code")+ codeLast
				+ "\" ElementType=\"Program\" ElementID=\"P" + data.get("Code") + codeLast+ "\" " + "ParentCode=\"C"
				+ data.get("Code") + codeLast+ "\" ElementCode=\"P" + data.get("Code") + codeLast+ "\" Action=\"DELETE\">\n";
		xml += "	    <Property Name=\"Type\">0</Property>\n";
		xml += "	    <Property Name=\"Sequence\">" + data.get("Id") + "</Property>\n";
		xml += "      </Mapping>\n";
		xml += "  </Mappings>\n";
		xml += "</ADI>";
		
		return xml; 
	}

	public static String BuildOfflineXml(Map data) {
		String codeLast = data.get("codeLast").toString();

		String ftpUrl = "ftp://" + Configuration.webCfg.getProperty("ftp.user") + ":"
				+ Configuration.webCfg.getProperty("ftp.pwd") + "@" + Configuration.webCfg.getProperty("ftp.ip.net") + ":"
				+ Configuration.webCfg.getProperty("ftp.port") + "/";

		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		xml += "<ADI xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n";
		xml += "  <Mappings>\n";
		xml += "      <Mapping ParentType=\"Program\" ParentID=\"P" + data.get("Code")+ codeLast
				+ "\" ElementType=\"Movie\" ElementID=\"M" + data.get("Code") + codeLast+ "\" " + "ParentCode=\"P"
				+ data.get("Code")+ codeLast + "\" ElementCode=\"M" + data.get("Code")+ codeLast + "\" Action=\"DELETE\"></Mapping>\n";
		xml += "      <Mapping ParentType=\"Picture\" ParentID=\"C" + data.get("Code")+ codeLast
				+ "\" ElementType=\"Program\" ElementID=\"P" + data.get("Code")+ codeLast + "\" " + "ParentCode=\"C"
				+ data.get("Code") + codeLast+ "\" ElementCode=\"P" + data.get("Code") + codeLast+ "\" Action=\"DELETE\">\n";
		xml += "	    <Property Name=\"Type\">0</Property>\n";
		xml += "	    <Property Name=\"Sequence\">" + data.get("Id") + "</Property>\n";
		xml += "      </Mapping>\n";
		xml += "  </Mappings>\n";
		xml += "  <Objects>\n";
		xml += "     <Object ElementType=\"Program\" ID=\"P" + data.get("Code") + codeLast+ "\" Action=\"DELETE\" Code=\"P"
				+ data.get("Code")+ codeLast + "\">\n";
		xml += "	    <Property Name=\"Name\">" + data.get("Name") + "</Property>\n";
		xml += "	    <Property Name=\"Language\">中文</Property>\n";
		xml += "	    <Property Name=\"Type\">时尚生活</Property>\n";
		xml += "	    <Property Name=\"DefinitionFlag\">2</Property>\n";
		// xml += " <Property Name=\"ReleaseYear\">2017</Property>\n";
		xml += "	    <Property Name=\"Description\">" + data.get("Description") + "</Property>\n";
		// xml += " <Property Name=\"LicensingWindowStart\">" +
		// data.get("StartDate") + "</Property>\n";
		// xml += " <Property Name=\"LicensingWindowEnd\">" +
		// data.get("EndDate") + "</Property>\n";
		xml += "	    <Property Name=\"PriceTaxIn\">" + data.get("CostFZ") + "</Property>\n";
		xml += "	    <Property Name=\"Keywords\">" + data.get("Name") + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "     <Object ElementType=\"Movie\" ID=\"M" + data.get("Code")+ codeLast + "\" Action=\"DELETE\" Code=\"M"
				+ data.get("Code") + codeLast+ "\">\n";
		xml += "	    <Property Name=\"Type\">1</Property>\n";
		xml += "	    <Property Name=\"FileURL\">" + ftpUrl + getSubUrl(data.get("VideoUrl").toString()) + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "     <Object ElementType=\"Picture\" ID=\"C" + data.get("Code") + codeLast+ "\" Action=\"DELETE\" Code=\"C"
				+ data.get("Code")+ codeLast + "\">\n";
		xml += "	    <Property Name=\"FileURL\">" + ftpUrl + getSubUrl(data.get("ImageUrl").toString()) + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "  </Objects>\n";
		xml += "</ADI>";
		
		return xml;
	}
	
	private static String getSubUrl(String url){
		if(url.startsWith("/")){
			return url.substring(1);
		}
		return url;
	}

	public static void main(String[] args) throws Exception {
//		boolean result = FtpUtil.upload("cnshengshiqu.zip", "/Users/mac/Downloads/", "/tests/");
//		System.out.println(result);
//
//		String result = FtpUtil.readFile("mytest.xml", "/xml");
//		Map map = XmlUtil.xml2map(result, false);
//		System.out.println(map);
//		System.out.println(((Map)map.get("Reply")).get("Property"));
//
//		
//		 boolean up = FtpUtil.download("115.28.77.76", 22000, "ftpuser",
//		 "111111", "text.xml", "/Users/mac/Downloads/", "/");
//		 System.out.println(up);
		
//		InputStream result = FtpUtil.readFileStream("/test.png");
//		System.out.println(result.read());

		CSPResult res = new CSPResult();
		CSPRequestServiceStub stub = new CSPRequestServiceStub();

		String cspid = Configuration.webCfg.getProperty("cfg.CSPID");
		String lspid = Configuration.webCfg.getProperty("cfg.LSPID");
		String ftpUrl = "ftp://" + Configuration.webCfg.getProperty("ftp.user") + ":"
				+ Configuration.webCfg.getProperty("ftp.pwd") + "@" + Configuration.webCfg.getProperty("ftp.ip") + ":"
				+ Configuration.webCfg.getProperty("ftp.port");

		try {
			ExecCmd cmd = new ExecCmd();
			cmd.setCSPID(cspid);
			cmd.setLSPID(lspid);
			cmd.setCorrelateID("test_0012");
			cmd.setCmdFileURL(ftpUrl + "/xml/test.xml");

			ExecCmdResponse resp = stub.execCmd(cmd);
			res = resp.getExecCmdReturn();
			
			System.out.println(JsonUtil.getJson(res));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
