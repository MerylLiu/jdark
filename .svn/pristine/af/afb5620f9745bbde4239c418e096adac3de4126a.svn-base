package com.iptv.core.utils;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.KendoResult;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUtil {
	public static KendoResult getRecordsPaged(String dataStatement, Map param) {
		String page = param.get("page").toString();
		String pageSize = param.get("pageSize").toString();
		Integer offset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

		param.put("offset", offset);
		param.put("rows", param.get("pageSize"));

		List data = BaseUtil.getDao().selectList(dataStatement, param);
		return new KendoResult(data, Integer.valueOf(param.get("total").toString()));
	}
}
