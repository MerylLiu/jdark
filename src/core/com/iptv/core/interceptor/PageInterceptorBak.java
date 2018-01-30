package com.iptv.core.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;
import com.iptv.core.utils.BaseUtil;
import com.iptv.core.utils.JsonUtil;
import java.lang.reflect.Field;
import java.sql.*;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class PageInterceptorBak implements Interceptor {
	private static final String PAGE_FLAG = "paged";
	private Logger log = Logger.getLogger(this.getClass());

	private static Integer keyIndex = 1;
	private static String keyName = "";

	@Override
	public Object intercept(Invocation arg0) throws Throwable {

		if (arg0.getTarget() instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) arg0.getTarget();
			MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

			MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
			String statementId = mappedStatement.getId();
			SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

			if ((statementId.substring(statementId.lastIndexOf(".") + 1).toLowerCase()).contains(PAGE_FLAG)
					&& sqlCommandType == SqlCommandType.SELECT) {
				BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");

				String sql = boundSql.getSql();
				Map param = (Map) boundSql.getParameterObject();

				Connection connection = (Connection) arg0.getArgs()[0];

				// rewrite sql
				Map countSql = concatCountSql(sql, param);
				Map pageSql = getPageSql(connection, sql, param);

				PreparedStatement statement = null;
				ResultSet rs = null;
				int totalCount = 0;

				try {
					List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
					Map parameterObject = (Map) boundSql.getParameterObject();
					parameterObject.putAll((Map) countSql.get("param"));

					Map countSqlInfo = buildParameters(countSql.get("clause").toString(), parameterObject);
					statement = connection.prepareStatement(countSqlInfo.get("sql").toString());

					BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(),
							countSqlInfo.get("sql").toString(), parameterMappings, parameterObject);

					Field additionalParametersField = BoundSql.class.getDeclaredField("additionalParameters");
					additionalParametersField.setAccessible(true);
					Map<String, Object> additionalParameters = (Map<String, Object>) additionalParametersField
							.get(boundSql);
					for (String key : additionalParameters.keySet()) {
						countBoundSql.setAdditionalParameter(key, additionalParameters.get(key));
					}

					ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
							countBoundSql);
					parameterHandler.setParameters(statement);

					rs = statement.executeQuery();
					if (rs.next()) {
						totalCount = rs.getInt(1);
					}
				} catch (SQLException e) {
					log.error("执行sql时发生错误:" + e.getMessage());
					BaseUtil.saveLog(0, "执行sql时发生错误", e.getMessage());
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}

						if (statement != null) {
							statement.close();
						}
					} catch (SQLException e) {
						log.error("执行sql时发生错误:" + e.getMessage());
						BaseUtil.saveLog(0, "执行sql时发生错误", e.getMessage());
					}
				}

				metaObject.setValue("delegate.boundSql.sql", pageSql.get("clause"));
				param.put("total", totalCount);
			}
		}

		return arg0.proceed();
	}

	@Override
	public Object plugin(Object arg0) {
		if (arg0 instanceof StatementHandler) {
			return Plugin.wrap(arg0, this);
		} else {
			return arg0;
		}
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub

	}

	private Map getPageSql(Connection conn, String sql, Map param) throws SQLException {
		DatabaseMetaData dbmd = conn.getMetaData();
		String dataBaseType = dbmd.getDatabaseProductName().toLowerCase();

		if (dataBaseType.contains("mysql")) {
			return concatMysqlPageSql(sql, param);
		} else if (dataBaseType.contains("oracle")) {
			return concatOraclePageSql(sql, param);
		}

		Map data = new HashMap();
		data.put("clause", sql);
		data.put("param", param);
		return data;
	}

	private Map concatMysqlPageSql(String sql, Map param) {
		Map data = new HashMap();
		data.put("param", new HashMap());

		StringBuffer buffer = new StringBuffer();
		// sql = sql.toLowerCase();

		Map filter = (Map) param.get("filter");
		Map condition = buildCondition(filter);
		String clauseSql = condition.get("clause").toString();

		if (clauseSql.length() > 0) {
			buffer.append("select * from (");
			buffer.append(sql);
			buffer.append(") v where 1 = 1 and ");
			buffer.append(clauseSql);

			ArrayList sortField = (ArrayList) param.get("sort");
			if (sortField != null && sortField.size() > 0) {
				String sortStr = "";

				for (Object item : sortField) {
					Map field = (Map) item;
					sortStr += String.format("%s %s,", field.get("field"), field.get("dir"));
				}

				String sort = String.format(" order by %s", sortStr.substring(0, sortStr.length() - 1));
				buffer.append(sort);
			}
		} else {
			buffer.append("select * from (");
			buffer.append(sql);
			buffer.append(") v");

			ArrayList sortField = (ArrayList) param.get("sort");
			if (sortField != null && sortField.size() > 0) {
				String sortStr = "";

				for (Object item : sortField) {
					Map field = (Map) item;
					sortStr += String.format("%s %s,", field.get("field"), field.get("dir"));
				}

				String sort = String.format(" order by %s", sortStr.substring(0, sortStr.length() - 1));
				buffer.append(sort);
			}
		}

		String limit = String.format(" limit %d,%d", param.get("offset"), param.get("rows"));
		buffer.append(limit);

		data.put("clause", buffer.toString());
		data.put("param", condition.get("param"));
		return data;
	}

	private Map concatOraclePageSql(String sql, Map param) {
		Map data = new HashMap();
		data.put("param", new HashMap());

		StringBuffer buffer = new StringBuffer();
		sql = sql.toLowerCase();

		Map filter = (Map) param.get("filter");
		Map condition = buildCondition(filter);
		String clauseSql = condition.get("clause").toString();

		if (clauseSql.length() > 0) {
			buffer.append("select * from (");
			buffer.append(sql);
			buffer.append(") v where 1 = 1 and ");
			buffer.append(clauseSql);
		} else {
			buffer.append(sql);
		}

		/*
		 * String limit = String.format(" limit %d,%d", param.get("offset"),
		 * param.get("rows")); buffer.append(limit);
		 */

		data.put("clause", buffer.toString());
		data.put("param", condition.get("param"));
		return data;
	}

	private Map concatCountSql(String sql, Map param) {
		Map data = new HashMap();
		data.put("param", new HashMap());

		StringBuffer buffer = new StringBuffer();

		Map filter = (Map) param.get("filter");
		Map condition = buildCondition(filter);
		String clauseSql = condition.get("clause").toString();

		if (clauseSql.length() > 0) {
			buffer.append("select count(1) from (");
			buffer.append(sql);
			buffer.append(") v where 1 = 1 and ");
			buffer.append(clauseSql);
		} else {
			// buffer.append("select count(1) ");
			// buffer.append(sql.substring(sql.indexOf("from")));

			buffer.append("select count(1) from (");
			buffer.append(sql);
			buffer.append(") v");
		}

		data.put("clause", buffer);
		data.put("param", condition.get("param"));
		return data;
	}

	/*
	 * -------------------------------sql--------------------------------
	 */
	private static Map buildCondition(Map filter) {
		Map data = new HashMap();
		Map param = new HashMap();
		data.put("param", param);

		if (filter == null) {
			data.put("clause", "");
			return data;
		}

		StringBuilder clause = new StringBuilder();
		ArrayList filters = (ArrayList) filter.get("filters");

		if (filters != null) {
			clause.append("(");

			for (int i = 0; i < filters.size(); i++) {
				Map f = (Map) filters.get(i);

				if (f.get("logic") == null && f.get("filters") == null) {
					Map fl = (Map) filters.get(i);
					Map temp = createSimpleCondition(fl);

					clause.append(temp.get("clause"));
					param.putAll((Map) temp.get("param"));

					if (i != filters.size() - 1) {
						clause.append(String.format(" %s ", filter.get("logic")));
					}

					continue;
				}

				Map temp = createCondition(f);
				param.putAll((Map) temp.get("param"));

				clause.append(temp.get("clause"));

				if (i < (filters.size() - 1)) {
					clause.append(String.format(" %s ", filter.get("logic")));
				}
			}

			clause.append(")");
		}

		data.put("clause", clause.toString());
		data.put("param", param);
		return data;
	}

	private static Map createCondition(Map filter) {
		Map data = new HashMap();
		Map param = new HashMap();
		data.put("param", param);

		if (filter == null) {
			data.put("clause", "");
			return data;
		}
		ArrayList filters = (ArrayList) filter.get("filters");
		StringBuilder clause = new StringBuilder();

		if (filters != null) {
			clause.append("(");

			for (int i = 0; i < filters.size(); i++) {
				Map f = (Map) filters.get(i);
				Map temp = createSimpleCondition(f);

				clause.append(temp.get("clause"));
				param.putAll((Map) temp.get("param"));

				if (i < (filters.size() - 1)) {
					clause.append(String.format(" %s ", filter.get("logic")));
				}
			}

			clause.append(")");
		}

		data.put("clause", clause.toString());
		data.put("param", param);
		return data;
	}

	private static Map createSimpleCondition(Map filterDesc) {
		Map data = new HashMap();
		Map param = new HashMap();
		data.put("param", param);

		if (filterDesc.get("field").toString() != keyName) {
			keyIndex = 1;
			keyName = filterDesc.get("field").toString();
		} else {
			keyIndex++;
		}

		String key = String.format("%s_%s", filterDesc.get("field"), keyIndex);
		String val = filterDesc.get("value") == null ? "" : filterDesc.get("value").toString();
		String value = "'" + val + "'";// StringUtil.isNumeric(val) ? val : "'"
										// + val + "'";

		String op = filterDesc.get("operator") == null ? "" : filterDesc.get("operator").toString();

		if (op.equals("eq")) {
			data.put("clause", filterDesc.get("field") + " = #{" + key + "}");
			param.put(key, val);
		} else if (op.equals("contains")) {
			data.put("clause", filterDesc.get("field") + " LIKE #{" + key + "}");
			param.put(key, "%" + val + "%");
		} else if (op.equals("doesnotcontain")) {
			data.put("clause", filterDesc.get("field") + " NOT LIKE #{" + key + "}");
			param.put(key, "%" + val + "%");
		} else if (op.equals("startswith")) {
			data.put("clause", filterDesc.get("field") + " LIKE #{" + key + "}");
			param.put(key, val + "%");
		} else if (op.equals("endswith")) {
			data.put("clause", filterDesc.get("field") + " LIKE #{" + key + "}");
			param.put(key, "%" + val);
		} else if (op.equals("iscontainedin")) {
			data.put("clause", filterDesc.get("field") + " IN #{" + key + "}");
			param.put(key, val);
		} else if (op.equals("gt")) {
			data.put("clause", filterDesc.get("field") + " > #{" + key + "}");
			param.put(key, val);
		} else if (op.equals("gte")) {
			data.put("clause", filterDesc.get("field") + " >= #{" + key + "}");
			param.put(key, val);
		} else if (op.equals("lt")) {
			data.put("clause", filterDesc.get("field") + " < #{" + key + "}");
			param.put(key, val);
		} else if (op.equals("lte")) {
			data.put("clause", filterDesc.get("field") + " <= #{" + key + "}");
			param.put(key, val);
		} else if (op.equals("neq")) {
			data.put("clause", filterDesc.get("field") + " != ?" + key);
			param.put(key, val);
		} else if (op.equals("in")) {
			if (filterDesc.get("value").getClass().getName() == "java.util.ArrayList") {
				ArrayList array = (ArrayList) filterDesc.get("value");
				String str = "";

				for (int i = 0; i < array.size(); i++) {
					if (i == array.size() - 1) {
						str += "'" + array.get(i).toString() + "'";
					} else {
						str += "'" + array.get(i).toString() + "',";
					}
				}

				data.put("clause", filterDesc.get("field") + " in (" + str + ")");
			} else if (filterDesc.get("value").getClass().getName() == "java.lang.String") {
				data.put("clause", filterDesc.get("field") + " in (" + value + ")");
			}
		} else {
			data.put("clause", filterDesc.get("field") + " = ?#{" + key + "}");
			param.put(key, val);
		}

		data.put("param", param);
		return data;
	}

	/*
	 * private static Map buildParams(Map params) { for (Object item :
	 * params.entrySet()) { Entry entry = (Entry) item; if
	 * (entry.getValue().getClass().isArray()) { Object[] arr = (Object[])
	 * entry.getValue(); String res = "";
	 * 
	 * for (int i = 0; i < arr.length; i++) { if (i < i - 1) { res += arr[i] +
	 * ","; } else { res += arr[i]; } }
	 * 
	 * params.put(entry.getKey(), res); } } return params; }
	 */

	/*
	 * private static Map buildCondition(Map filter) { Map data = new HashMap();
	 * if (filter == null) { data.put("clause", ""); return data; }
	 * 
	 * StringBuilder clause = new StringBuilder(); ArrayList filters =
	 * (ArrayList) filter.get("filters");
	 * 
	 * if (filters != null) { for (int i = 0; i < filters.size(); i++) { Map f =
	 * (Map) filters.get(i);
	 * 
	 * if (f.get("logic") == null && f.get("filters") == null) {
	 * 
	 * Map fl = (Map) filters.get(i); Map temp = createSimpleCondition(fl);
	 * 
	 * clause.append(temp.get("clause")); if (i != filter.size() - 1) {
	 * clause.append(String.format(" %s ", filter.get("logic"))); }
	 * 
	 * temp.remove("clause"); data.putAll(temp);
	 * 
	 * continue; }
	 * 
	 * Map temp = createCondition(f);
	 * 
	 * clause.append(temp.get("clause")); if (i != filter.size() - 1) {
	 * clause.append(String.format(" %s ", filter.get("logic"))); }
	 * 
	 * temp.remove("clause"); data.putAll(temp); } }
	 * 
	 * data.put("clause", clause.toString()); return data; }
	 * 
	 * private static Map createCondition(Map filter) { Map data = new
	 * HashMap(); if (filter == null) { data.put("clause", ""); return data; }
	 * ArrayList filters = (ArrayList) filter.get("filters"); StringBuilder
	 * clause = new StringBuilder();
	 * 
	 * if (filters != null) { clause.append("("); for (int i = 0; i <
	 * filters.size(); i++) { Map f = (Map) filters.get(i); Map temp =
	 * createSimpleCondition(f);
	 * 
	 * clause.append(temp.get("clause")); if (i != filter.size() - 1) {
	 * clause.append(String.format(" %s ", filter.get("logic"))); }
	 * 
	 * temp.remove("clause"); data.putAll(temp); } clause.append(")"); }
	 * 
	 * data.put("clause", clause.toString()); return data; }
	 * 
	 * private static Map createSimpleCondition(Map filterDesc) { Map data = new
	 * HashMap(); String key = String.format("%s_%s", filterDesc.get("field"),
	 * UUID.randomUUID()); String value = filterDesc.get("value").toString();
	 * 
	 * if (filterDesc.get("operator").toString() == "eq") { data.put("clause",
	 * filterDesc.get("field") + " = " + "#{" + key + "}"); data.put(key,
	 * value); } else if (filterDesc.get("operator").toString() == "contains") {
	 * data.put("clause", filterDesc.get("field") + " LIKE " + "'%#{" + key +
	 * "}%'"); data.put(key, value); } else if
	 * (filterDesc.get("operator").toString() == "doesnotcontain") {
	 * data.put("clause", filterDesc.get("field") + " NOT LIKE " + "'%#{" + key
	 * + "}%'"); data.put(key, value); } else if
	 * (filterDesc.get("operator").toString() == "startswith") {
	 * data.put("clause", filterDesc.get("field") + " LIKE " + "'#{" + key +
	 * "}%'"); data.put(key, value); } else if
	 * (filterDesc.get("operator").toString() == "endswith") {
	 * data.put("clause", filterDesc.get("field") + " LIKE " + "'%#{" + key +
	 * "}'"); data.put(key, value); } else if
	 * (filterDesc.get("operator").toString() == "iscontainedin") {
	 * data.put("clause", filterDesc.get("field") + " IN " + "#{" + key + "}");
	 * data.put(key, value); } else if (filterDesc.get("operator").toString() ==
	 * "gt") { data.put("clause", filterDesc.get("field") + " > " + "#{" + key +
	 * "}"); data.put(key, value); } else if
	 * (filterDesc.get("operator").toString() == "gte") { data.put("clause",
	 * filterDesc.get("field") + " >= " + "#{" + key + "}"); data.put(key,
	 * value); } else if (filterDesc.get("operator").toString() == "lt") {
	 * data.put("clause", filterDesc.get("field") + " < " + "#{" + key + "}");
	 * data.put(key, value); } else if (filterDesc.get("operator").toString() ==
	 * "lte") { data.put("clause", filterDesc.get("field") + " <= " + "#{" + key
	 * + "}"); data.put(key, value); } else if
	 * (filterDesc.get("operator").toString() == "neq") { data.put("clause",
	 * filterDesc.get("field") + " != " + "#{" + key + "}"); data.put(key,
	 * value); } else { data.put("clause", filterDesc.get("field") + " = " +
	 * "#{" + key + "}"); data.put(key, value); }
	 * 
	 * return data; }
	 */

	private Map buildParameters(String sql, Map parameters) {
		Map res = new HashMap();
		LinkedHashMap params = new LinkedHashMap();

		String reg = "(#\\{\\w+\\})+";
		String regP = "\\?+";

		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(sql);
		List<String> replaceFiled = new ArrayList<String>();
		while (matcher.find()) {
			int n = matcher.groupCount();
			System.out.println(n);
			for (int i = 0; i < n; i++) {
				String output = matcher.group(i);
				if (output != null) {
					replaceFiled.add(output.trim());
				}
			}
		}

		pattern = Pattern.compile(regP, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(sql);
		int startIndex = 0;
		while (matcher.find()) {
			int n = matcher.groupCount();
			startIndex += n;
		}

		System.out.println(JsonUtil.getJson(replaceFiled));

		for (int i = 0; i < replaceFiled.size(); i++) {
			String key = replaceFiled.get(i).replace("#{", "").replace("}", "");
			Object val = parameters.get(key);

			if (val != null) {
				sql = sql.replace(replaceFiled.get(i), "?");
				params.put(key, val);
			}
		}

		res.put("sql", sql);
		res.put("params", params);
		res.put("startIndex", startIndex);

		return res;
	}

	public static void main(String[] args) {
		String sql = "select * from test where id = #{id} and test = #{test}";
		Map pm = new HashMap();
		// String reString = new PageInterceptor().(null, sql, pm);

		// System.out.println(reString);
	}
}
