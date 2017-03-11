package com.iptv.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getNow(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
}
