package com.iptv.core.service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface SysParamService extends BaseService {
	public void saveLog(String logInfo);

	/**
	 * 保存错误日志到数据库
	 * 
	 * @param opreationType
	 *            0.错误日志,1.插入数据，2.修改数据，3，删除数据，8，其他
	 * @param operation
	 *            错误信息标题
	 * @param remark
	 *            错误信息
	 */
	public void saveLog(int opreationType, String operation, String remark);

	/**
	 * 获取系统参数
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public List getSysParam(String key, Boolean isAll);

	/**
	 * 获取系统参数
	 * 
	 * @param key
	 *            关键字
	 * @param Value
	 *            值
	 * @return
	 */
	public Map getSysParam(String key, String value);
}
