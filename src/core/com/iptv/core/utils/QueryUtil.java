package com.iptv.core.utils;

import java.util.List;
import java.util.Map;

import com.iptv.core.common.KendoResult;

@SuppressWarnings({"rawtypes","unchecked"})
public class QueryUtil {
	public static KendoResult getRecordsPaged(String dataStatement,String countStatement ,StringBuilder sqlCondition, Map param) {
		String page = param.get("page").toString();
		String pageSize = param.get("pageSize").toString();
		Integer offset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

		param.put("offset", offset);
		param.put("rows", param.get("pageSize"));

		List data = BaseUtil.getDao().selectList(dataStatement, param);
		Integer count = BaseUtil.getDao().selectOne(countStatement, param);
		return new KendoResult(data,count);
	}

	public static Integer getRecordsCount(String statement, StringBuilder sql, Map param) {
		Integer offset = (Integer.parseInt(param.get("page").toString()) - 1)
				* Integer.parseInt(param.get("pageSize").toString());

		param.put("offset", offset);
		param.put("rows", param.get("pageSize"));

		Integer count = BaseUtil.getDao().selectOne(statement, param);
		return count;
	}
}
