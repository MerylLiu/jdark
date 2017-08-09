package com.iptv.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取当前时间
	 * 
	 * @return 返回字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 返回指定格式的时间字符串
	 * 
	 * @param format
	 *            格式字符串
	 * @return
	 */
	public static String getDateFormat(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date());
	}
}
