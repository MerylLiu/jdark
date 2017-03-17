package com.iptv.core.service;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface SysParamService extends BaseService {
	public void saveLog(String logInfo);

	/*
	 * opreationType:0.错误日志,1.插入数据，2.修改数据，3，删除数据，8，其他
	 */
	public void saveLog(int opreationType,String operation,String remark);
	
	public List getSysParam(String key,Boolean isAll);
}
