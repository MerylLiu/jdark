package com.iptv.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iptv.core.common.KendoResult;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUtil {
	public static KendoResult getRecordsPaged(String statement, Map param) {
		String page = param.get("page").toString();
		String pageSize = param.get("pageSize").toString();
		Integer offset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

		param.put("offset", offset);
		param.put("rows", param.get("pageSize"));

		List data = BaseUtil.getDao().selectList(statement, param);
		return new KendoResult(data, Integer.valueOf(param.get("total").toString()));
	}

	public static KendoResult getSelectOptions(String statement, Map param) {
		Map map = new HashMap();
		if (param.get("filter") != null) {
			Map filter = (Map) param.get("filter");
			ArrayList filters = (ArrayList) filter.get("filters");
			if (filters.size() > 0) {
				Map f = (Map) filters.get(0);
				String value = f.get("value").toString();

				if (!value.isEmpty()) {
					map.put("text", value);
				}
			}
		}

		List data = BaseUtil.getDao().selectList(statement, map);
		return new KendoResult(data, data.size());
	}
}
