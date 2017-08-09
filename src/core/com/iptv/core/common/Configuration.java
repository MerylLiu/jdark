package com.iptv.core.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import com.iptv.core.utils.JsonUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Configuration {
	public static final Properties webCfg = new Properties();

	private static String webserviceUrl;

	static {
		try {
			InputStreamReader inWebCfg = new InputStreamReader(Configuration.class.getResourceAsStream("/webconfig.properties"), "UTF-8");
			Map jsonCfg = from("/webconfig.json");

			webCfg.load(inWebCfg);

			if (jsonCfg != null) {
				webCfg.putAll(jsonCfg);
			}

			webserviceUrl = webCfg.getProperty("cfg.webservice.url");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getWebserviceUrl() {
		return webserviceUrl;
	}

	public static Map from(String fileName) {
		fileName = fileName.startsWith("/") ? fileName : String.format("/%s", fileName);

		try {
			InputStreamReader inputStreamReader = new InputStreamReader(Configuration.class.getResourceAsStream(fileName), "UTF-8");

			if (fileName.endsWith(".properties")) {
				Properties prop = new Properties();
				prop.load(inputStreamReader);
				return prop;
			} else if (fileName.endsWith(".json")) {
				BufferedReader in = new BufferedReader(inputStreamReader);  
				StringBuffer sb = new StringBuffer();  
				String line = "";  
				while ((line = in.readLine()) != null){  
				     sb.append(line);  
				}  

				Map res = (Map) JsonUtil.getObject(sb.toString());
				return res;
			}
		} catch (Exception e) {
			return null;
		}

		return null;
	}
}
