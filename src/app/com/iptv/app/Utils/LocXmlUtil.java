package com.iptv.app.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import com.iptv.core.common.Configuration;
import com.iptv.core.utils.FtpUtil;
import com.iptv.core.utils.XmlUtil;

@SuppressWarnings("rawtypes")
public class LocXmlUtil {

	public static String BuildXml(Map data) {
		String ftpUrl = "ftp://" + Configuration.webCfg.getProperty("ftp.user") + ":"
				+ Configuration.webCfg.getProperty("ftp.pwd") + "@" + Configuration.webCfg.getProperty("ftp.ip") + ":"
				+ Configuration.webCfg.getProperty("ftp.port") + "/";

		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<ADI xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">";
		xml += "  <Objects>\n";
		xml += "     <Object ElementType=\"Program\" ID=\"" + data.get("Code") + "\" Action=\"REGIST\" Code=\"P"
				+ data.get("Code") + "\">\n";
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
		xml += "     <Object ElementType=\"Movie\" ID=\"" + data.get("Code") + "_1\" Action=\"REGIST\" Code=\"M"
				+ data.get("Code") + "_1\">\n";
		xml += "	    <Property Name=\"Type\">1</Property>\n";
		xml += "	    <Property Name=\"FileURL\">" + ftpUrl + getSubUrl(data.get("VideoUrl").toString()) + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "     <Object ElementType=\"Picture\" ID=\"" + data.get("Code") + "_3\" Action=\"REGIST\" Code=\"C"
				+ data.get("Code") + "_1\">\n";
		xml += "	    <Property Name=\"FileURL\">" + ftpUrl + getSubUrl(data.get("ImageUrl").toString()) + "</Property>\n";
		xml += "	 </Object>\n";
		xml += "  </Objects>\n";
		xml += "  <Mappings>\n";
		xml += "      <Mapping ParentType=\"Program\" ParentID=\"" + data.get("Code")
				+ "\" ElementType=\"Movie\" ElementID=\"" + data.get("Code") + "_1\" " + "ParentCode=\"P"
				+ data.get("Code") + "\" ElementCode=\"M" + data.get("Code") + "_1\" Action=\"REGIST\"></Mapping>\n";
		xml += "      <Mapping ParentType=\"Picture\" ParentID=\"" + data.get("Code")
				+ "_3\" ElementType=\"Program\" ElementID=\"" + data.get("Code") + "\" " + "ParentCode=\"C"
				+ data.get("Code") + "_1\" ElementCode=\"P" + data.get("Code") + "\" Action=\"REGIST\">\n";
		xml += "	    <Property Name=\"Type\">0</Property>\n";
		xml += "	    <Property Name=\"Sequence\">" + data.get("Id") + "</Property>\n";
		xml += "      </Mapping>\n";
		xml += "  </Mappings>\n";
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

		InputStream result = FtpUtil.readFileStream("/test.png");
		System.out.println(result.read());
	}
}
