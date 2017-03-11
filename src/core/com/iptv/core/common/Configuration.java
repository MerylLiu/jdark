package com.iptv.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	public static final Properties webCfg = new Properties();
	
	private static String webserviceUrl;
	private static String ftpUrl;

	static{
		InputStream inWebCfg = Configuration.class.getResourceAsStream("/webconfig.properties");
		
		try {
			webCfg.load(inWebCfg);
			
			webserviceUrl = webCfg.getProperty("cfg.webservice.url");
			ftpUrl = webCfg.getProperty("cfg.ftp.url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getWebserviceUrl(){
		return webserviceUrl;
	}

	public static String getFtpUrl(){
		return ftpUrl;
	}
}