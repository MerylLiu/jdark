package com.iptv.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iptv.core.common.KendoResult;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUtil {
	/**
	 * 查询记录（分页）
	 * 
	 * @param statement
	 *            sql
	 * @param param
	 * @return
	 */
	public static KendoResult getRecordsPaged(String statement, Map param) {
		Integer page = param.get("page") == null ? 1 : Integer.parseInt(param.get("page").toString());
		Integer pageSize = param.get("pageSize") == null ? 20 : Integer.parseInt(param.get("pageSize").toString());
		Integer offset = (page - 1) * pageSize;

		param.put("offset", offset);
		param.put("rows", pageSize);

		List data = BaseUtil.getDao().selectList(statement, param);
		return new KendoResult(data, Integer.valueOf(param.get("total").toString()));
	}

	/**
	 * 获取下拉列表选项
	 * 
	 * @param statement
	 *            sql
	 * @param param
	 * @return
	 */
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
