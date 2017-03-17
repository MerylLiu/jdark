package com.iptv.core.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

@SuppressWarnings("deprecation")
public class UrlUtil {
	public static String encode(String url){
		return URLEncoder.encode(url);
	}

	public static String decode(String url){
		return URLDecoder.decode(url);
	}
}
