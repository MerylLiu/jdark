package com.iptv.core.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;

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
import java.lang.reflect.Field;
import java.sql.*;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class PageInterceptor implements Interceptor {
	private static final String pageFlag = "paged";
	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public Object intercept(Invocation arg0) throws Throwable {

		if (arg0.getTarget() instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) arg0.getTarget();
			MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

			MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
			String selectId = mappedStatement.getId();
			SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

			if ((selectId.substring(selectId.lastIndexOf(".") + 1).toLowerCase()).contains(pageFlag)
					&& sqlCommandType == SqlCommandType.SELECT) {
				BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");

				String sql = boundSql.getSql();
				Map param = (Map) boundSql.getParameterObject();

				Connection connection = (Connection) arg0.getArgs()[0];

				// rewrite sql
				String countSql = concatCountSql(sql, param);
				String pageSql = getPageSql(connection, sql, param);

				PreparedStatement statement = null;
				ResultSet rs = null;
				int totalCount = 0;

				try {
					statement = connection.prepareStatement(countSql);

					List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
					Object parameterObject = boundSql.getParameterObject();
					BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
							parameterMappings, parameterObject);

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

				metaObject.setValue("delegate.boundSql.sql", pageSql);
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

	private String getPageSql(Connection conn, String sql, Map param) throws SQLException {
		StringBuffer sqlBuffer = new StringBuffer(sql);

		DatabaseMetaData dbmd = conn.getMetaData();
		String dataBaseType = dbmd.getDatabaseProductName().toLowerCase();

		if (dataBaseType.contains("mysql")) {
			return concatMysqlPageSql(sql, param);
		} else if (dataBaseType.contains("oracle")) {
			return concatOraclePageSql(sql, param);
		}
		return sqlBuffer.toString();
	}

	private String concatMysqlPageSql(String sql, Map param) {
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

		return buffer.toString();
	}

	private String concatOraclePageSql(String sql, Map param) {
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

		return buffer.toString();
	}

	private String concatCountSql(String sql, Map param) {
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

		return buffer.toString();
	}

	/*
	 * -------------------------------sql--------------------------------
	 */
	private static Map buildCondition(Map filter) {
		Map data = new HashMap();
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

					if (i != filters.size() - 1) {
						clause.append(String.format(" %s ", filter.get("logic")));
					}

					continue;
				}

				Map temp = createCondition(f);

				clause.append(temp.get("clause"));

				if (i < (filters.size() - 1)) {
					clause.append(String.format(" %s ", filter.get("logic")));
				}
			}

			clause.append(")");
		}

		data.put("clause", clause.toString());
		return data;
	}

	private static Map createCondition(Map filter) {
		Map data = new HashMap();
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

				if (i < (filters.size() - 1)) {
					clause.append(String.format(" %s ", filter.get("logic")));
				}
			}

			clause.append(")");
		}

		data.put("clause", clause.toString());
		return data;
	}

	private static Map createSimpleCondition(Map filterDesc) {
		Map data = new HashMap();
		String key = String.format("%s_%s", filterDesc.get("field"), UUID.randomUUID());
		String val = filterDesc.get("value") == null ? "" : filterDesc.get("value").toString();
		String value = "'" + val + "'";// StringUtil.isNumeric(val) ? val : "'"
										// + val + "'";

		String op = filterDesc.get("operator") == null ? "" : filterDesc.get("operator").toString();

		if (op.equals("eq")) {
			data.put("clause", filterDesc.get("field") + " = " + value);
		} else if (op.equals("contains")) {
			data.put("clause", filterDesc.get("field") + " LIKE " + "'%" + val + "%'");
		} else if (op.equals("doesnotcontain")) {
			data.put("clause", filterDesc.get("field") + " NOT LIKE " + "'%" + val + "%'");
		} else if (op.equals("startswith")) {
			data.put("clause", filterDesc.get("field") + " LIKE " + "'" + val + "%'");
		} else if (op.equals("endswith")) {
			data.put("clause", filterDesc.get("field") + " LIKE " + "'%" + val + "'");
		} else if (op.equals("iscontainedin")) {
			data.put("clause", filterDesc.get("field") + " IN " + val);
		} else if (op.equals("gt")) {
			data.put("clause", filterDesc.get("field") + " > " + value);
		} else if (op.equals("gte")) {
			data.put("clause", filterDesc.get("field") + " >= " + value);
		} else if (op.equals("lt")) {
			data.put("clause", filterDesc.get("field") + " < " + value);
		} else if (op.equals("lte")) {
			data.put("clause", filterDesc.get("field") + " <= " + value);
		} else if (op.equals("neq")) {
			data.put("clause", filterDesc.get("field") + " != " + value);
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
			data.put("clause", filterDesc.get("field") + " = " + value);
		}

		return data;
	}

	private static Map buildParams(Map params) {
		for (Object item : params.entrySet()) {
			Entry entry = (Entry) item;
			if (entry.getValue().getClass().isArray()) {
				Object[] arr = (Object[]) entry.getValue();
				String res = "";

				for (int i = 0; i < arr.length; i++) {
					if (i < i - 1) {
						res += arr[i] + ",";
					} else {
						res += arr[i];
					}
				}

				params.put(entry.getKey(), res);
			}
		}
		return params;
	}

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
}
