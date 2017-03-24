package com.iptv.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.iptv.core.common.KendoResult;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryUtil {
	public static KendoResult getRecordsPaged(String dataStatement, String countStatement, String key, Map param) {
		String page = param.get("page").toString();
		String pageSize = param.get("pageSize").toString();
		Integer offset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

		param.put("offset", offset);
		param.put("rows", param.get("pageSize"));


		Map filter = (Map) param.get("filter");
		Map condition = buildCondition(filter);

		String clauseSql = condition.get("clause").toString();
		param.put(key, " AND " + clauseSql);
		condition.remove("clause");
		param.putAll(condition);

		List data = BaseUtil.getDao().selectList(dataStatement, param);
		Integer count = BaseUtil.getDao().selectOne(countStatement, param);
		return new KendoResult(data, count);
	}
	
	private static Map buildCondition(Map filter){
		StringBuilder clause = new StringBuilder();
		ArrayList filters = (ArrayList) filter.get("filters");

		Map data = new HashMap();

		if (filters != null) {
			for (int i = 0; i < filters.size(); i++) {
				Map f = (Map) filters.get(i);

				if (f.get("logic") == null && f.get("filters") == null) {

					Map fl = (Map) filters.get(i);
					Map temp = createSimpleCondition(fl);

					clause.append(temp.get("clause"));
					if (i != filter.size() - 1) {
						clause.append(String.format(" %s ", filter.get("logic")));
					}

					temp.remove("clause");
					data.putAll(temp);

					continue;
				}

				Map temp = createCondition(f);

				clause.append(temp.get("clause"));
				if (i != filter.size() - 1) {
					clause.append(String.format(" %s ", filter.get("logic")));
				}

				temp.remove("clause");
				data.putAll(temp);
			}
		}

		data.put("clause", clause.toString());
		return data;
	}

	private static Map createCondition(Map filter) {
		ArrayList filters = (ArrayList) filter.get("filters");
		StringBuilder clause = new StringBuilder();

		Map data = new HashMap();

		if (filters != null) {
			clause.append("(");
			for (int i = 0; i < filters.size(); i++) {
				Map f = (Map) filters.get(i);
				Map temp = createSimpleCondition(f);

				clause.append(temp.get("clause"));
				if (i != filter.size() - 1) {
					clause.append(String.format(" %s ", filter.get("logic")));
				}

				temp.remove("clause");
				data.putAll(temp);
			}
			clause.append(")");
		}

		data.put("clause", clause.toString());
		return data;
	}

	private static Map createSimpleCondition(Map filterDesc) {
		Map data = new HashMap();
		String key = String.format("%s_%s", filterDesc.get("field"), UUID.randomUUID());
		String value = filterDesc.get("value").toString();

		if (filterDesc.get("operator").toString() == "eq") {
			data.put("clause", filterDesc.get("field") + " = " + "#{" + key + "}");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "contains") {
			data.put("clause", filterDesc.get("field") + " LIKE " + "'%#{" + key + "}%'");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "doesnotcontain") {
			data.put("clause", filterDesc.get("field") + " NOT LIKE " + "'%#{" + key + "}%'");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "startswith") {
			data.put("clause", filterDesc.get("field") + " LIKE " + "'#{" + key + "}%'");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "endswith") {
			data.put("clause", filterDesc.get("field") + " LIKE " + "'%#{" + key + "}'");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "iscontainedin") {
			data.put("clause", filterDesc.get("field") + " IN " + "#{" + key + "}");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "gt") {
			data.put("clause", filterDesc.get("field") + " > " + "#{" + key + "}");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "gte") {
			data.put("clause", filterDesc.get("field") + " >= " + "#{" + key + "}");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "lt") {
			data.put("clause", filterDesc.get("field") + " < " + "#{" + key + "}");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "lte") {
			data.put("clause", filterDesc.get("field") + " <= " + "#{" + key + "}");
			data.put(key, value);
		} else if (filterDesc.get("operator").toString() == "neq") {
			data.put("clause", filterDesc.get("field") + " != " + "#{" + key + "}");
			data.put(key, value);
		} else {
			data.put("clause", filterDesc.get("field") + " = " + "#{" + key + "}");
			data.put(key, value);
		}

		return data;
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
